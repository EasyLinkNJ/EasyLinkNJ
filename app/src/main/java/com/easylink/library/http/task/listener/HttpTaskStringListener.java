package com.easylink.library.http.task.listener;

public interface HttpTaskStringListener<T> extends HttpTaskListener<String, T> {

    boolean onTaskSaveCache(T result);
}
