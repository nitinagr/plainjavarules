package org.pjr.rulesengine;

public class DataLayerException extends TechnicalException{

	/**
	 *
	 */
	private static final long serialVersionUID = 6227710097631966074L;

	/**
	 *
	 */
	public DataLayerException() {
	}

	/**
	 * @param arg0
	 */
	public DataLayerException(String msg) {
		super(msg);

	}

	/**
	 * @param arg0
	 */
	public DataLayerException(Throwable arg0) {
		super(arg0);

	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DataLayerException(String msg, Throwable e) {
		super(e, msg);

	}






}
