package com.rules.common.processor;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rules.common.TechnicalException;
import com.rules.common.daos.RulesDao;
import com.rules.common.dbmodel.RulesConditionMapping;
import com.rules.common.dbmodel.UserRules;
import com.rules.common.uidto.RuleDto;
import com.rules.common.uidto.ViewDtoTransformer;

@Component(value="rulesProcessor")
public class RulesProcessorImpl implements RulesProcessor{
	private static final Log log = LogFactory.getLog(RulesProcessorImpl.class);
	
	@Resource
	private RulesDao rulesDao;

	
	@Resource
	private ViewDtoTransformer dtoTransformer;
	
	@Override
	@Transactional(readOnly=true)
	public List<RuleDto> getAllRules() throws TechnicalException {
		List<UserRules> userRuleList = null;
		userRuleList = rulesDao.getAllUserRules();		
		return dtoTransformer.getRuleDtoList(userRuleList);
	}


	
	
	@Override
	@Transactional(readOnly=true)
	public RuleDto getRule(int ruleId) throws TechnicalException{		
		UserRules userRule = null;
		userRule = rulesDao.getUserRule(ruleId);
		if(userRule == null ) userRule = new UserRules();		
		return dtoTransformer.getRuleDto(userRule) ;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean saveRule(RuleDto rule) throws TechnicalException{
		boolean isSaved = false;
		UserRules userRules = dtoTransformer.getUserRules(rule);
		rulesDao.save(userRules);
		return isSaved;
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveRulesConditionMapping(
			List<RulesConditionMapping> rulesConditionMappingList)
			throws TechnicalException {		
		rulesDao.saveRulesConditionMapping(rulesConditionMappingList);
	}
	



	public RulesDao getRulesDao() {
		return rulesDao;
	}


	public void setRulesDao(RulesDao rulesDao) {
		this.rulesDao = rulesDao;
	}


	
}
