package org.pjr.rulesengine.ui;

import org.pjr.rulesengine.NonTechnicalException;

/**
 * RuleAlreadyExistExceptions are thrown in case of any business rule violations.
 *
 * @author Infosys
 *
 */
public class RuleAlreadyExistException extends NonTechnicalException{

	/**
	 *
	 */
	private static final long serialVersionUID = 2535289723874845692L;

	/**
	 *
	 */
	public RuleAlreadyExistException() {
	}

	/**
	 * @param arg0
	 */
	public RuleAlreadyExistException(String msg) {
		super(msg);

	}

	/**
	 * @param arg0
	 */
	public RuleAlreadyExistException(Throwable arg0) {
		super(arg0);

	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public RuleAlreadyExistException(String arg0, Throwable arg1) {
		super(arg1, arg0);

	}


}
