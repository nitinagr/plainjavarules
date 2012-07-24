package com.rules.common;

public class TechnicalException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5874673386778538603L;

	public TechnicalException(){
		super();
	}

	
	public TechnicalException(String message){
		super(message);
	}
	
	public TechnicalException(Throwable e ){
		super(e);
	}
	
	public TechnicalException(Throwable e , String message){
		super(message,e);
	}
	
}
