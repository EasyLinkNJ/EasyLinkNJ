package com.easylink.library.http.task;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.easylink.library.util.LogMgr;

import android.os.Handler;
import android.os.Message;
import android.os.Process;

public abstract class AsyncHttpTask<Params, Progress, Result> {

	private static final String TAG = "AsyncHttpTask";

	private static final int CORE_POOL_SIZE = 3;
	private static final int MAXIMUM_POOL_SIZE = 128;
	private static final int KEEP_ALIVE = 1;

	private static final int MESSAGE_POST_RESULT = 0x1;
	private static final int MESSAGE_POST_PROGRESS = 0x2;

	private static final InternalHandler sHandler = new InternalHandler();

	private static volatile Executor mRemoteWorkExecutor, mLocalWorkExecutor;
	private final WorkerRunnable<Params, Result> mWorker;
	private final FutureTask<Result> mFuture;

	private volatile Status mStatus = Status.PENDING;

	private final AtomicBoolean mTaskInvoked = new AtomicBoolean();

	public enum Status {
		PENDING, RUNNING, FINISHED,
	}

	public AsyncHttpTask() {

		//初始化网络工作线程池
		if (mRemoteWorkExecutor == null) {
			mRemoteWorkExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
					MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {
						private final AtomicInteger mCount = new AtomicInteger(
								1);

						public Thread newThread(Runnable r) {
							return new Thread(r, "AsyncHttpTask remote#"
									+ mCount.getAndIncrement());
						}
					});
		}
		
		//初始化本地工作线程池，负责查缓存
		if(mLocalWorkExecutor == null){
			mLocalWorkExecutor = new ThreadPoolExecutor(1,
					MAXIMUM_POOL_SIZE, 0, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>(10), new ThreadFactory() {
						private final AtomicInteger mCount = new AtomicInteger(
								1);

						public Thread newThread(Runnable r) {
							return new Thread(r, "AsyncHttpTask local#"
									+ mCount.getAndIncrement());
						}
					});			
			
		}
		
		mWorker = new WorkerRunnable<Params, Result>() {
			public Result call() throws Exception {
				mTaskInvoked.set(true);

				Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
				return postResult(doInBackground(mParams));
			}
		};
		
		mFuture = new FutureTask<Result>(mWorker) {
			@Override
			protected void done() {
				try {
					final Result result = get();

					postResultIfNotInvoked(result);
				} catch (InterruptedException e) {
					android.util.Log.w(TAG, e);
				} catch (ExecutionException e) {
					throw new RuntimeException(
							"An error occured while executing doInBackground()",
							e.getCause());
				} catch (CancellationException e) {
					postResultIfNotInvoked(null);
				} catch (Throwable t) {
					throw new RuntimeException(
							"An error occured while executing "
									+ "doInBackground()", t);
				}
			}
		};
	}

	private void postResultIfNotInvoked(Result result) {
		final boolean wasTaskInvoked = mTaskInvoked.get();
		if (!wasTaskInvoked) {
			postResult(result);
		}
	}

	private Result postResult(Result result) {
		Message message = sHandler.obtainMessage(MESSAGE_POST_RESULT,
				new AsyncTaskResult<Result>(this, result));
		message.sendToTarget();
		return result;
	}

	protected final Status getStatus() {
		return mStatus;
	}

	protected abstract Result doInBackground(Params... params);

	protected void onPreExecute() {
	}

	protected void onPostExecute(Result result) {
	}

	protected void onProgressUpdate(Progress... values) {
	}

	private void onCancelled(Result result) {
		onCancelled();
	}

	protected void onCancelled() {
	}

	protected final boolean isCancelled() {
		return mFuture.isCancelled();
	}

	protected final boolean cancel(boolean mayInterruptIfRunning) {
		return mFuture.cancel(mayInterruptIfRunning);
	}

	protected final AsyncHttpTask<Params, Progress, Result> executeRemote(
			Params... params) {
		return executeOnExecutor(mRemoteWorkExecutor, params);
	}
	
	protected final AsyncHttpTask<Params, Progress, Result> executeLocal(
			Params... params) {
		return executeOnExecutor(mLocalWorkExecutor, params);
	}

	protected static void shutdown() {
		
		if (mRemoteWorkExecutor != null) {
			((ExecutorService) mRemoteWorkExecutor).shutdown();
			mRemoteWorkExecutor = null;
		}
		
		if(mLocalWorkExecutor != null){
			
			((ExecutorService) mLocalWorkExecutor).shutdown();
			mLocalWorkExecutor = null;
		}
	}

	protected final AsyncHttpTask<Params, Progress, Result> executeOnExecutor(
			Executor exec, Params... params) {
		
		if (exec == null)// thread pool shutdowned
			return this;
		
		if(LogMgr.isDebug()){
			
			ThreadPoolExecutor tpe = (ThreadPoolExecutor) exec;
			LogMgr.w(TAG, "async http task getActiveCount="+tpe.getActiveCount()+", getTaskCount = "+tpe.getTaskCount());
		}
		
		if (mStatus != Status.PENDING) {
			switch (mStatus) {
			case RUNNING:
				throw new IllegalStateException("Cannot execute task:"
						+ " the task is already running.");
			case FINISHED:
				throw new IllegalStateException("Cannot execute task:"
						+ " the task has already been executed "
						+ "(a task can be executed only once)");
			}
		}

		mStatus = Status.RUNNING;

		onPreExecute();

		mWorker.mParams = params;
		exec.execute(mFuture);

		return this;
	}

	protected final void publishProgress(Progress... values) {
		if (!isCancelled()) {
			sHandler.obtainMessage(MESSAGE_POST_PROGRESS,
					new AsyncTaskResult<Progress>(this, values)).sendToTarget();
		}
	}

	private void finish(Result result) {
		if (isCancelled()) {
			onCancelled(result);
		} else {
			onPostExecute(result);
		}
		mStatus = Status.FINISHED;
	}

	private static class InternalHandler extends Handler {
		@SuppressWarnings({ "unchecked", "RawUseOfParameterizedType" })
		@Override
		public void handleMessage(Message msg) {
			AsyncTaskResult result = (AsyncTaskResult) msg.obj;
			switch (msg.what) {
			case MESSAGE_POST_RESULT:
				// There is only one result
				result.mTask.finish(result.mData[0]);
				break;
			case MESSAGE_POST_PROGRESS:
				result.mTask.onProgressUpdate(result.mData);
				break;
			}
		}
	}

	private static abstract class WorkerRunnable<Params, Result> implements
			Callable<Result> {
		Params[] mParams;
	}

	private static class AsyncTaskResult<Data> {
		final AsyncHttpTask mTask;
		final Data[] mData;

		AsyncTaskResult(AsyncHttpTask task, Data... data) {
			mTask = task;
			mData = data;
		}
	}
}
