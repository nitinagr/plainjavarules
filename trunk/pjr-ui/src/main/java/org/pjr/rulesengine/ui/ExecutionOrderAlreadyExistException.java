package org.pjr.rulesengine.ui;

import org.pjr.rulesengine.NonTechnicalException;

/**
 * ExecutionOrderAlreadyExistExceptions are thrown in case of any business rule violations.
 *
 * @author Infosys
 *
 */
public class ExecutionOrderAlreadyExistException extends NonTechnicalException{

	/**
	 *
	 */
	private static final long serialVersionUID = 2535289723874845692L;

	/**
	 *
	 */
	public ExecutionOrderAlreadyExistException() {
	}

	/**
	 * @param arg0
	 */
	public ExecutionOrderAlreadyExistException(String msg) {
		super(msg);

	}

	/**
	 * @param arg0
	 */
	public ExecutionOrderAlreadyExistException(Throwable arg0) {
		super(arg0);

	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ExecutionOrderAlreadyExistException(String arg0, Throwable arg1) {
		super(arg1, arg0);

	}


}
