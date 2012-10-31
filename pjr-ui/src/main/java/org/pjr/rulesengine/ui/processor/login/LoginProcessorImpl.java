package org.pjr.rulesengine.ui.processor.login;

import org.springframework.stereotype.Component;

@Component("loginProcessor")
public class LoginProcessorImpl implements LoginProcessor{

	@Override
	public boolean validateLogin(String userId, String password) throws Exception {
		return true;
	}

}
