package com.rules.common.uidto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rules.common.dbmodel.RulesConditionMapping;
import com.rules.common.dbmodel.UserRules;

@Component
public class ViewDtoTransformer {
	
	public  List<RuleDto> getRuleDtoList(List<UserRules> userRuleList){
		List<RuleDto> ruleDtoList = new ArrayList<RuleDto>();
		for(UserRules userRule : userRuleList){
			RuleDto ruleDto = getRuleDto(userRule);
			ruleDtoList.add(ruleDto);
		}
		return ruleDtoList;		
	}
	
	
	public  RuleDto getRuleDto(UserRules userRule){
		RuleDto ruleDto = null;
		if(null != userRule){	
			ruleDto = new RuleDto();
			ruleDto.setRuleId(userRule.getUserRuleId());
			ruleDto.setRuleName(userRule.getUserRuleName());
			ruleDto.setRuleDes(userRule.getUserRuleDes());
			ruleDto.setEnable(userRule.isEnable());
			ruleDto.setDefaultValue(userRule.getDefaultValue());			
			ruleDto.setConditionList(getConditions(userRule.getRulesConditionMappingList()));			
		}		
		return ruleDto;
		
	}
	
	public  List<ConditionDto> getConditions(List<RulesConditionMapping>  rulesConditionMappingList){
		List<ConditionDto> conditionList = new ArrayList<ConditionDto>();
		
		for(RulesConditionMapping rulesConditionMapping :rulesConditionMappingList){
			ConditionDto conditionDto = new ConditionDto();
			
			conditionDto.setRuleId(rulesConditionMapping.getRuleId());
			conditionDto.setConditionId(rulesConditionMapping.getCondition().getConditionId());
			conditionDto.setDisplayName(rulesConditionMapping.getCondition().getDisplayName());
			conditionDto.setConditionDisplay(rulesConditionMapping.getConditionDisplay());
			conditionDto.setConditionMvel(rulesConditionMapping.getConditionMvel());
			conditionDto.setEnable(rulesConditionMapping.isEnable());
			conditionDto.setDefaultvalue(rulesConditionMapping.getCondition().isDefaultvalue());
			
			conditionList.add(conditionDto);
		}
		
		return conditionList; 
	}
	
	
	
	public  UserRules getUserRules(RuleDto ruleDto){
		UserRules userRules = null;
		if(null != ruleDto){	
			userRules = new UserRules();
			userRules.setUserRuleId(ruleDto.getRuleId());
			userRules.setUserRuleName(ruleDto.getRuleName());
			userRules.setUserRuleDes(ruleDto.getRuleDes());
			userRules.setEnable(ruleDto.isEnable());						
						
		}		
		return userRules;
		
	}
	
	
	public ConditionDto getConditionDto(RulesConditionMapping rcm ){
		ConditionDto conditionDto = null;
		if(null != rcm){
			conditionDto = new ConditionDto();
			
			conditionDto.setRuleId(rcm.getRuleId());
			conditionDto.setConditionId(rcm.getCondition().getConditionId());
			conditionDto.setDisplayName(rcm.getCondition().getDisplayName());
			conditionDto.setConditionDisplay(rcm.getConditionDisplay());
			conditionDto.setConditionMvel(rcm.getConditionMvel());
			conditionDto.setEnable(rcm.getCondition().isEnable());
		}
	
		return conditionDto;
	}
	


}
