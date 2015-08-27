package com.easylink.library.http.task.listener;

public interface HttpTaskListener<RemoteData, Result> {

    int TASK_SUCCESS = -1;
    int TASK_FAILED_NETWORK_ERROR = -2;
    int TASK_FAILED_SETPARAMS_ERROR = -3;
    int TASK_FAILED_TIMEOUT_ERROR = -4;
    int TASK_FAILED_CONNECT_ERROR = -5;
    int TASK_FAILED_SHUTDOWN_ERROR = -6;
    int TASK_FAILED_SERVER_ERROR = -7;
    int TASK_FAILED_CLIENT_ERROR = -8;
    int TASK_FAILED_RESPONSE_PARSE_ERROR = -9;

    void onTaskPre();

    Result onTaskResponse(RemoteData remoteData) throws Exception;

    void onTaskSuccess(Result result);

    void onTaskFailed(int failedCode);

    void onTaskAbort();
}
