package com.easylink.library.activity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.annotation.SuppressLint;

import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.http.task.HttpTask;
import com.easylink.library.http.task.listener.HttpTaskStringListener;

/**
 * 负责管理HttpTask
 * @author yhb
 *
 */
public class HttpTaskExecuter {

	private Map<Integer, HttpTask> mHttpTaskMap;

	@SuppressLint("UseSparseArrays")
	public HttpTaskExecuter() {
		
		mHttpTaskMap = new HashMap<Integer, HttpTask>();
	}

	public <T> boolean executeHttpTask(int what, HttpTaskParams params, final HttpTaskStringListener<T> lisn){
		
		return executeHttpTask(what, params, false, lisn);
	}
	
	public <T> boolean executeHttpTaskCache(int what, HttpTaskParams params, final HttpTaskStringListener<T> lisn){
		
		return executeHttpTask(what, params, true, lisn);
	}
	
	public <T> boolean executeHttpTask(int what, HttpTaskParams params, boolean cacheOnly, final HttpTaskStringListener<T> lisn){
		
		if(isHttpTaskRunning(what))
			return false;
		
		final HttpTask ht = new HttpTask(params);
		ht.setCacheOnly(cacheOnly);
		ht.setListener(new HttpTaskStringListener<T>() {
			
			@Override
			public void onTaskPre() {
				
				mHttpTaskMap.put(ht.getWhat(), ht);
				if(lisn != null)
					lisn.onTaskPre();
			}
			
			@Override
			public T onTaskResponse(String remoteData) throws Exception {
				
				return lisn == null ? null : lisn.onTaskResponse(remoteData);
			}
			
			public boolean onTaskSaveCache(T result){
				
				return lisn == null ? false : lisn.onTaskSaveCache(result);
			}
		
			@Override
			public void onTaskSuccess(T result) {
				
				mHttpTaskMap.remove(ht.getWhat());
				if(lisn != null)
					lisn.onTaskSuccess(result);
			}
			
			@Override
			public void onTaskFailed(int failedCode) {
				
				mHttpTaskMap.remove(ht.getWhat());
				if(lisn != null)
					lisn.onTaskFailed(failedCode);
			}			
			
			@Override
			public void onTaskAbort() {
				
				if(lisn != null)
					lisn.onTaskAbort();
			}

		});		
		
		ht.execute(what);
		
		return true;
	}	
	
	public HttpTask getHttpTask(int what){
		
		return mHttpTaskMap.get(what);
	}
	
	public void abortHttpTask(int what) {
		
		HttpTask ht = mHttpTaskMap.remove(what);
		if(ht != null)
			ht.abort();
	}

	public void abortAllHttpTask() {
		
		if (mHttpTaskMap.size() == 0)
			return;

		Collection<HttpTask> tasks = mHttpTaskMap.values();
		Iterator<HttpTask>  iterator = tasks.iterator();
		while (iterator.hasNext()) {
			
			iterator.next().abort();
		}
		
		mHttpTaskMap.clear();
	}

	public boolean isHttpTaskRunning(int what) {
		
		return mHttpTaskMap.get(what) != null ? true : false;
	}

	public boolean isEmpty() {
		
		return mHttpTaskMap.size() == 0;
	}
}
