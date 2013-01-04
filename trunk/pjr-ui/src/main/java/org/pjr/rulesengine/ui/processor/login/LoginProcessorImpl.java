package org.pjr.rulesengine.ui.processor.login;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.dbmodel.RuleEngineUser;
import org.pjr.rulesengine.ui.daos.GeneralDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("loginProcessor")
public class LoginProcessorImpl implements LoginProcessor{
	
	private static final Log logger = LogFactory.getLog(LoginProcessorImpl.class);
	
	@Autowired
	private GeneralDao generalDao;
	
	@Override
	public boolean validateLogin(String userId, String password) throws Exception {
		boolean result = false;

		RuleEngineUser pacUser = null;
		try {
			pacUser = generalDao.getPacUser(userId);
			
			if(null != pacUser && pacUser.getPassword().equals(password))result = true;
			
			logger.debug("pacUser :"+pacUser);
		} catch (TechnicalException e) {
			logger.error("unable to fetch user data from Db",e)	;
		}
		
		return result;
	}

}
