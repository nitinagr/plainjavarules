package org.pjr.rulesengine;

/**
 * The Class TechnicalException.
 *
 * @author Sudhakar
 */
public class TechnicalException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5874673386778538603L;

	/**
	 * Instantiates a new technical exception.
	 *
	 * @author  Sudhakar (pjr.org)
	 */
	public TechnicalException(){
		super();
	}


	/**
	 * Instantiates a new technical exception.
	 *
	 * @param message the message
	 * @author  Sudhakar (pjr.org)
	 */
	public TechnicalException(String message){
		super(message);
	}

	/**
	 * Instantiates a new technical exception.
	 *
	 * @param e the e
	 * @author  Sudhakar (pjr.org)
	 */
	public TechnicalException(Throwable e ){
		super(e);
	}

	/**
	 * Instantiates a new technical exception.
	 *
	 * @param e the e
	 * @param message the message
	 * @author  Sudhakar (pjr.org)
	 */
	public TechnicalException(Throwable e , String message){
		super(message,e);
	}

}
