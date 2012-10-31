package org.pjr.rulesengine;

/**
 * The Class NonTechnicalException.
 *
 * @author Sudhakar
 */
public class NonTechnicalException extends Exception{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5459394262646445480L;

	/**
	 * Instantiates a new non technical exception.
	 *
	 * @author  Sudhakar (pjr.org)
	 */
	public NonTechnicalException(){
		super();
	}


	/**
	 * Instantiates a new non technical exception.
	 *
	 * @param message the message
	 * @author  Sudhakar (pjr.org)
	 */
	public NonTechnicalException(String message){
		super(message);
	}

	/**
	 * Instantiates a new non technical exception.
	 *
	 * @param e the e
	 * @author  Sudhakar (pjr.org)
	 */
	public NonTechnicalException(Throwable e ){
		super(e);
	}

	/**
	 * Instantiates a new non technical exception.
	 *
	 * @param e the e
	 * @param message the message
	 * @author  Sudhakar (pjr.org)
	 */
	public NonTechnicalException(Throwable e , String message){
		super(message,e);
	}


}
