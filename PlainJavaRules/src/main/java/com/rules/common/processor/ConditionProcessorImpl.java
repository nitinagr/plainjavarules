package com.rules.common.processor;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rules.common.TechnicalException;
import com.rules.common.daos.ConditionsDao;
import com.rules.common.dbmodel.ConditionPtyMapping;
import com.rules.common.dbmodel.RulesConditionMapping;
import com.rules.common.uidto.ConditionDto;
import com.rules.common.uidto.ViewDtoTransformer;

@Component
public class ConditionProcessorImpl implements ConditionProcessor{

	@Resource
	private ConditionsDao conditionsDao;
	
	@Resource
	private ViewDtoTransformer dtoTransformer;
	
	@Override
	@Transactional(readOnly=true)
	public ConditionDto getConditionWithRuleText(int ruleId,int conditionId) throws TechnicalException {		
		RulesConditionMapping rcm = conditionsDao.getConditionWithRuleText(ruleId,conditionId);
		return dtoTransformer.getConditionDto(rcm);		
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public List<ConditionPtyMapping> getConditionPtyMapping(int conditionId) throws TechnicalException{
		return conditionsDao.getConditionPtyMapping(conditionId);
	}
	
	


	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveCondition(int ruleId,ConditionDto conditionDto)
			throws TechnicalException {
		conditionsDao.saveCondition(ruleId, conditionDto);		
	}

}
