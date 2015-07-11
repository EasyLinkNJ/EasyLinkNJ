package com.easylink.library.http.exception;

public class ServerErrorException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServerErrorException(int statusCode){
		
		super("server error statusCode = "+statusCode);
	}
	
	public ServerErrorException(String info){
		super(info);
	}
}
