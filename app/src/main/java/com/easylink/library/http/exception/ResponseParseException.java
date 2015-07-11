package com.easylink.library.http.exception;

public class ResponseParseException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ResponseParseException() {
		
		super("response parse exception");
	}
	
	public ResponseParseException(String info){
		
		super(info);
	}
}
