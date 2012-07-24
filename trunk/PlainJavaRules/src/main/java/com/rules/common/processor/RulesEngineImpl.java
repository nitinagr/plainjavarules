package com.rules.common.processor;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mvel2.MVEL;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rules.common.TechnicalException;
import com.rules.common.daos.RulesDao;
import com.rules.common.dbmodel.UserRules;

@Component("rulesEngine")
public class RulesEngineImpl implements RulesEngine{
	
	private static final Log log = LogFactory.getLog(RulesEngineImpl.class);
	
	@Resource
	private RulesDao rulesDao;
	
	@Transactional(readOnly=true)
	public boolean processRule(Object object , int ruleId) throws TechnicalException {
		boolean result = false;
		UserRules userRules = rulesDao.getUserRule(ruleId);
		String mvelExpression = userRules.getMvelTextFromAllTheRuleConditions();
		
		log.debug("MVEL Expression  :"+ mvelExpression);
		
		result = (Boolean) MVEL.eval(mvelExpression, object);
		
		return result;		
	}
	
	

}
