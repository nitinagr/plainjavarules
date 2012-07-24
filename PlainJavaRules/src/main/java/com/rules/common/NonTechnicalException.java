package com.rules.common;

public class NonTechnicalException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5459394262646445480L;

	public NonTechnicalException(){
		super();
	}

	
	public NonTechnicalException(String message){
		super(message);
	}

	public NonTechnicalException(Throwable e ){
		super(e);
	}
	
	public NonTechnicalException(Throwable e , String message){
		super(message,e);
	}
	
	
}
