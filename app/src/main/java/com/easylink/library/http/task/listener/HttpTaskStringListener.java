package com.easylink.library.http.task.listener;

public interface HttpTaskStringListener<T> extends HttpTaskListener<String, T>{

	public boolean onTaskSaveCache(T result);
}
