package com.easylink.library.http.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;

import android.os.Environment;
import android.text.TextUtils;

import com.easylink.library.http.exception.ClientErrorException;
import com.easylink.library.http.exception.HttpClientShutdownException;
import com.easylink.library.http.exception.NetworkDisableException;
import com.easylink.library.http.exception.RequestSetParamsException;
import com.easylink.library.http.exception.ResponseParseException;
import com.easylink.library.http.exception.ServerErrorException;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.http.params.NameByteValuePair;
import com.easylink.library.http.params.NameFileValuePair;
import com.easylink.library.http.task.HttpTaskClient.InputStreamCallback;
import com.easylink.library.http.task.listener.HttpTaskByteListener;
import com.easylink.library.http.task.listener.HttpTaskListener;
import com.easylink.library.http.task.listener.HttpTaskStreamListener;
import com.easylink.library.http.task.listener.HttpTaskStringListener;
import com.easylink.library.util.IOUtil;
import com.easylink.library.util.LogMgr;

public class HttpTask extends AsyncHttpTask<Void, Void, Object>{

	protected static HttpTaskClient mHttpTaskClient = HttpTaskClient.newThreadSafeHttpClient(10 * 1000);//超时时间 10s;
	protected static File mCacheFileDir = 	new File(Environment.getExternalStorageDirectory(), 
			 										  "android"+File.separator+"urlCache"+File.separator);
	
	private HttpTaskParams mHttpTaskParams;
	private HttpUriRequest mHttpUriRequest;
	private HttpTaskListener mHttpTaskLisn;
	private int mTaskStatus = HttpTaskListener.TASK_SUCCESS;
	private int mWhat;
	private boolean mCacheOnly;

	/*
	 * construct method part -------------------------------------
	 */
	
	public HttpTask(){
		
	}
	
	public HttpTask(HttpTaskParams httpTaskParams){
		
		setHttpTaskParams(httpTaskParams);
	}
	
	/*
	 * static new instance method part ------------------------------
	 */
	
	public static HttpTask newGet(String url){
		
		return new HttpTask(HttpTaskParams.newGet(url));
	}
	
	public static HttpTask newPost(String url){
		
		return new HttpTask(HttpTaskParams.newPost(url));
	}
	
	public static HttpTask newUpload(String url){
		
		return new HttpTask(HttpTaskParams.newUpload(url));
	}
	
	/*
	 * params setter part ---------------------------------------------------
	 */
	
	public void setHttpTaskParams(HttpTaskParams httpTaskParams){
		
		mHttpTaskParams = httpTaskParams;
		
		if(mHttpTaskParams == null){
			
			mHttpUriRequest = null;
		}else if(mHttpTaskParams.isGetMethod()){
			
			mHttpUriRequest = new HttpGet();
		}else{
			
			mHttpUriRequest = new HttpPost();
		}
	}
	
	public void setStringParams(List<NameValuePair> stringParams){
		
		if(mHttpTaskParams != null)
			mHttpTaskParams.setStringParams(stringParams);
	}
	
	public void setByteParams(List<NameByteValuePair> byteParams){
		
		if(mHttpTaskParams != null)
			mHttpTaskParams.setByteParams(byteParams);
	}
	
	public void setFileParams(List<NameFileValuePair> fileParams){
		
		if(mHttpTaskParams != null)
			mHttpTaskParams.setFileParams(fileParams);
	}
	
	/*
	 * param adder part -----------------------------------------------
	 */
	
	public void addParam(String key, String value){
		
		if(mHttpTaskParams != null)
			mHttpTaskParams.addParam(key, value);
	}
	
	public void addByteParam(String key, byte[] value){
		
		if(mHttpTaskParams != null)
			mHttpTaskParams.addByteParam(key, value);
	}
	
	public void addFileParam(String key, String filePath){
		
		if(mHttpTaskParams != null)
			mHttpTaskParams.addFileParam(key, filePath);
	}
	
	/*
	 * cache setter part ----------------------------------------------------
	 */
	
	public void setCacheOnly(boolean cacheOnly){
		
		mCacheOnly = cacheOnly;
	}
	
	public void setCacheKey(String cacheKey){
		
		if(mHttpTaskParams != null)
			mHttpTaskParams.setCacheKey(cacheKey);
	}
	
	/*
	 * listener setter part ---------------------------------------------
	 */
	
	public <T> void setListener(HttpTaskStringListener<T> lisn){
		
		mHttpTaskLisn = lisn;
	}
	
	public <T> void setListener(HttpTaskStreamListener<T> lisn){
		
		mHttpTaskLisn = lisn;
	}
	
	public <T> void setListener(HttpTaskByteListener<T> lisn){
		
		mHttpTaskLisn = lisn;
	}
	
	/*
	 * status getter part ----------------------------------------------
	 */
	
	public boolean isCacheOnly(){
		
		return mCacheOnly;
	}
	
	public String getCacheKey(){
		
		return mHttpTaskParams == null ? null : mHttpTaskParams.getCacheKey();
	}
	
	public final int getWhat(){
		
		return mWhat;
	}
	
	public final boolean isAbort() {
		
		return isCancelled();
	}

	public final boolean isRunning() {
		
		return getStatus() == Status.RUNNING;
	}
	
	public final boolean isFinished(){
		
		return getStatus() == Status.FINISHED;
	}
	
	/*
	 * actioner part ----------------------------------------------------
	 */
	
	public final HttpTask execute(int what){
		
		mWhat = what;
		return this.execute();
	}
	
	public final HttpTask execute(){
		
		if(mCacheOnly){
			
			super.executeLocal();
		}else{
			
			super.executeRemote();
		}
		
		return this;
	}
	
	public final void abort() {
		
		try {

			if(isCancelled())
				return;
			
			cancel(true);
			
			if(mHttpUriRequest != null && !mHttpUriRequest.isAborted())
				mHttpUriRequest.abort();

		} catch (Exception e) {
			
			if(LogMgr.isDebug())
				e.printStackTrace();
		}

		//callback onTaskAbort
		if(mHttpTaskLisn != null)
			mHttpTaskLisn.onTaskAbort();
	}
	
	
	/*
	 *  callback methods part -----------------------------------------------
	 */
	
	protected final void onPreExecute() {
		
		if(mHttpTaskLisn != null)
			mHttpTaskLisn.onTaskPre();
	}

	@Override
	protected final Object doInBackground(Void... params) {
		
		try{
			
			return executeHttpUriRequest();
		
		}catch(Throwable e){
			
			exception2FailedCode(e);
			return null;
		}
	}
	
	@Override
	protected final void onPostExecute(Object t) {
		
		if (mTaskStatus == HttpTaskListener.TASK_SUCCESS) {
			
			if(mHttpTaskLisn != null)
				mHttpTaskLisn.onTaskSuccess(t);
		
		} else {
			
			if(mHttpTaskLisn != null)
				mHttpTaskLisn.onTaskFailed(mTaskStatus);
		}
	}
	
	@Override
	protected final void onCancelled() {
		
		//if(mHttpTaskLisn != null)
		//mHttpTaskLisn.onTaskAborted();
	}
	
	/*
	 * help method part -------------------------------------------
	 */
	
	private Object executeHttpUriRequest() throws ClientProtocolException, IOException, HttpClientShutdownException, RequestSetParamsException, ServerErrorException, ClientErrorException, ResponseParseException{
		
		fillParamsToUriRequest();
		return executeUriRequestAndCallback();
	}
	
	private void fillParamsToUriRequest() throws RequestSetParamsException{
		
		if(mHttpTaskParams == null)
			throw new RequestSetParamsException("HttpTaskParams is null");
		
		try{
		
			switch(mHttpTaskParams.getMethod()){
				case HttpTaskParams.METHOD_GET:
					
					HttpTaskUtil.setParamsByGetRequest((HttpGet) mHttpUriRequest,
							mHttpTaskParams.getUrl(),
							mHttpTaskParams.getStringParams());
					
					break;
				case HttpTaskParams.METHOD_POST:
					HttpTaskUtil.setParamsByPostRequest((HttpPost) mHttpUriRequest,
							mHttpTaskParams.getUrl(),
							mHttpTaskParams.getStringParams());
					break;
				case HttpTaskParams.METHOD_UPLOAD:
					HttpTaskUtil.setParamsByUploadRequest((HttpPost) mHttpUriRequest,
							mHttpTaskParams.getUrl(),
							mHttpTaskParams.getStringParams(),
							mHttpTaskParams.getByteParams(),
							mHttpTaskParams.getFileParams());
					break;
			}
		
			if(LogMgr.isDebug())
				LogMgr.d(HttpTaskUtil.createGetUrl(mHttpTaskParams.getUrl(), mHttpTaskParams.getStringParams()));
		
		}catch(Exception e){
		
			throw new RequestSetParamsException(e.toString());
		}
	}
	
	private Object executeUriRequestAndCallback() throws ClientProtocolException, IOException, ServerErrorException, ClientErrorException, ResponseParseException {
		
		if(mHttpTaskLisn instanceof HttpTaskStringListener){
			
			return getStringListenerResult();
			
		}else if(mHttpTaskLisn instanceof HttpTaskStreamListener){
			
			return getStreamListenerResult();
			
		}else if(mHttpTaskLisn instanceof HttpTaskByteListener){
			
			return getByteListenerResult();
		}else{
				
			mHttpTaskClient.executeHttpResponse(mHttpUriRequest);	
			return null;
		}
	}

	private Object getStringListenerResult() throws ResponseParseException, ClientProtocolException, IOException, ServerErrorException, ClientErrorException {
		
		String text = null;
		if(mCacheOnly){
			
			text = loadStringCache(mHttpTaskParams.getCacheKey() == null ? 
								   HttpTaskUtil.createGetUrl(mHttpTaskParams.getUrl(), mHttpTaskParams.getStringParams()) :
								   mHttpTaskParams.getCacheKey());
			if(LogMgr.isDebug())
				LogMgr.d("HttpTask", "http task load data from cache, key =  "+mHttpTaskParams.getCacheKey());
			
		}else{	

			if(mHttpTaskParams.getTestResult() == null)
				text = mHttpTaskClient.executeText(mHttpUriRequest);
			else
				text = (String) mHttpTaskParams.getTestResult();
		}
		
		Object result = null;
		try{

			result = ((HttpTaskStringListener<?>) mHttpTaskLisn).onTaskResponse(text);
		}catch(Exception e){
			
			throw new ResponseParseException(e.getMessage());
		}
		
		if(mCacheOnly)
			return result;
		
		boolean needCache = ((HttpTaskStringListener) mHttpTaskLisn).onTaskSaveCache(result);
		if(needCache)
			saveStringCache(mHttpTaskParams.getCacheKey() == null ? 
						  	HttpTaskUtil.createGetUrl(mHttpTaskParams.getUrl(), mHttpTaskParams.getStringParams()) :
						  	mHttpTaskParams.getCacheKey(), text);
		
		return result;		
	}

	private Object getStreamListenerResult() throws IOException, ServerErrorException, ClientErrorException, ResponseParseException {
		
		HttpTaskInputStreamCallback callback = new HttpTaskInputStreamCallback((HttpTaskStreamListener<?>)mHttpTaskLisn);
		mHttpTaskClient.executeInputStream(mHttpUriRequest, callback);
		if(TextUtils.isEmpty(callback.getExceptionInfo()))
			return callback.getResultObj();
		else
			throw new ResponseParseException(callback.getExceptionInfo());
	}
	
	private Object getByteListenerResult() throws ClientProtocolException, IOException, ServerErrorException, ClientErrorException, ResponseParseException {
		
		byte[] bytes = mHttpTaskClient.executeByteArray(mHttpUriRequest);
		try{
			
			return ((HttpTaskByteListener<?>)mHttpTaskLisn).onTaskResponse(bytes);
		}catch(Exception e){
			
			throw new ResponseParseException(e.getMessage());
		}
	}
	
	private String loadStringCache(String cacheKey) {
		
		String text = null;
		try{
			
			if(!mCacheFileDir.exists())
				mCacheFileDir.mkdirs();
			
			text = (String) IOUtil.readObj(mCacheFileDir, String.valueOf(cacheKey.hashCode()));
			
		}catch(Exception e){
			
			if(LogMgr.isDebug())
				e.printStackTrace();
		}
		
		return text == null ? "" : text;
	}

	private void saveStringCache(String cachekey, String text){
		
		try{
			
			if(!mCacheFileDir.exists())
				mCacheFileDir.mkdirs();
			
			IOUtil.writeObj(text, mCacheFileDir, String.valueOf(cachekey.hashCode()));
			
		}catch(Exception e){
			
			if(LogMgr.isDebug())
				e.printStackTrace();
		}
	}
	
	private void exception2FailedCode(Throwable e) {
		
		if(e instanceof NetworkDisableException){
			
			mTaskStatus = HttpTaskListener.TASK_FAILED_NETWORK_ERROR;
		}else if (e instanceof RequestSetParamsException){
			
			mTaskStatus = HttpTaskListener.TASK_FAILED_SETPARAMS_ERROR;
		}else if (e instanceof IllegalStateException){
			
			mTaskStatus = HttpTaskListener.TASK_FAILED_SHUTDOWN_ERROR;
		}else if(e instanceof SocketTimeoutException){
				
			mTaskStatus = HttpTaskListener.TASK_FAILED_TIMEOUT_ERROR;
		}else if (e instanceof ConnectTimeoutException) {
			
			mTaskStatus = HttpTaskListener.TASK_FAILED_TIMEOUT_ERROR;
		}else if(e instanceof ServerErrorException){
			
			mTaskStatus = HttpTaskListener.TASK_FAILED_SERVER_ERROR;
		}else if(e instanceof ClientErrorException){
			
			mTaskStatus = HttpTaskListener.TASK_FAILED_CLIENT_ERROR;
		}else if(e instanceof ResponseParseException){
			
			mTaskStatus = HttpTaskListener.TASK_FAILED_RESPONSE_PARSE_ERROR;
		}else{
			
			mTaskStatus = HttpTaskListener.TASK_FAILED_CONNECT_ERROR;
		}
		
		if(LogMgr.isDebug())
			e.printStackTrace();
	}
	
	/*
	 * static method part
	 */
	
	public static void setCacheDir(File cacheDir){
		
		if(cacheDir != null)
			mCacheFileDir = cacheDir;
	}	
	
	public static final void shutdown(){
		
		if(mHttpTaskClient != null){
			
			mHttpTaskClient.shutdown();
			mHttpTaskClient = null;
		}
	}
	
	/*
	 * httpclient inputstream callback
	 */
	private class HttpTaskInputStreamCallback implements InputStreamCallback{
		
		Object resultObj;
		HttpTaskStreamListener<?> streamLisn;
		String exceptionInfo;
		
		public HttpTaskInputStreamCallback(HttpTaskStreamListener<?> lisn){
			
			this.streamLisn = lisn;
		}
		
		@Override
		public void onInputStream(InputStream input) {
			
			try{
				resultObj = streamLisn.onTaskResponse(input);
			}catch(Exception e){
				
				exceptionInfo = e.getMessage();
			}
			streamLisn = null;
		}
		
		public Object getResultObj(){
			
			return resultObj;
		}
		
		public String getExceptionInfo(){
			
			return exceptionInfo;
		}
	}	
}