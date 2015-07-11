package com.easylink.library.http.task.listener;

public interface HttpTaskListener<RemoteData, Result>{

	public static final int TASK_SUCCESS = -1;
	public static final int TASK_FAILED_NETWORK_ERROR = -2;
	public static final int TASK_FAILED_SETPARAMS_ERROR = -3;
	public static final int TASK_FAILED_TIMEOUT_ERROR = -4;
	public static final int TASK_FAILED_CONNECT_ERROR = -5;
	public static final int TASK_FAILED_SHUTDOWN_ERROR = -6;
	public static final int TASK_FAILED_SERVER_ERROR = -7;
	public static final int TASK_FAILED_CLIENT_ERROR = -8;
	public static final int TASK_FAILED_RESPONSE_PARSE_ERROR = -9;
	
	public void onTaskPre();
	
	public abstract Result onTaskResponse(RemoteData remoteData)throws Exception;
	
	public void onTaskSuccess(Result result);
	
	public void onTaskFailed(int failedCode);
		
	public void onTaskAbort();
}
