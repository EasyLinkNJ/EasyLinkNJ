package com.easylink.library.http.exception;

public class RequestSetParamsException extends Exception{

	private static final long serialVersionUID = 1L;

	
	public RequestSetParamsException(){
		
		super("request set params exception");
	}
	
	public RequestSetParamsException(String info){
		
		super(info);
	}
}
