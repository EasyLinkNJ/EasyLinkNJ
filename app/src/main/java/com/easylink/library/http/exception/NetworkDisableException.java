package com.easylink.library.http.exception;

public class NetworkDisableException extends Exception{

	private static final long serialVersionUID = 1L;

	public NetworkDisableException(){
		super(" device Network is Disabled");
	}
}
