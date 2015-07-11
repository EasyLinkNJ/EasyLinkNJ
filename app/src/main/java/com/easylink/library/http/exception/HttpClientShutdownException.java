package com.easylink.library.http.exception;

public class HttpClientShutdownException extends Exception {

	private static final long serialVersionUID = 1L;

	public HttpClientShutdownException() {
		super("HttpTaskClient aldready Released");
	}
}
