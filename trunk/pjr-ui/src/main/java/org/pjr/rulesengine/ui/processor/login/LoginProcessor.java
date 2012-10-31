package org.pjr.rulesengine.ui.processor.login;



/**
 * The Interface LoginProcessor.
 *
 * @author Sudhakar
 */
public interface LoginProcessor {

	/**
	 * Validate login.
	 *
	 * @param userId the user id
	 * @param password the password
	 * @return true, if successful
	 * @throws Exception the exception
	 * @author  Sudhakar (Infosys)
	 */

	public boolean validateLogin(String userId , String password) throws Exception;
}
