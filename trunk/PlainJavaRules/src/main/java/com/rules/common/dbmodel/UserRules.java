package com.rules.common.dbmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class UserRules {

	private int userRuleId;
	private String userRuleName;
	private String userRuleDes;
	private boolean enable;
	private boolean defaultValue;
	private List<RulesConditionMapping> rulesConditionMappingList = new ArrayList<RulesConditionMapping>();
	
	private static final String OPEN_BRACE = "(";
	private static final String CLOSE_BRACE = ")";
	
	public String getMvelTextFromAllTheRuleConditions(){
		StringBuffer sf = new StringBuffer();
		
		
		Iterator<RulesConditionMapping> iterator = rulesConditionMappingList.listIterator();
		while(iterator.hasNext()){
			RulesConditionMapping rcm = iterator.next();
			Condition condition = rcm.getCondition();
			if(null != condition){			
					if(rcm.isEnable()){
						if(StringUtils.isNotBlank(rcm.getConditionMvel())){
							sf.append(OPEN_BRACE);
							sf.append(rcm.getConditionMvel());
							sf.append(CLOSE_BRACE);
						}else{
							sf.append(OPEN_BRACE);
							sf.append(condition.isDefaultvalue());
							sf.append(CLOSE_BRACE);
						}						
					}else{
						sf.append("");
					}
				
				if(iterator.hasNext()) sf.append(" && ");
			}			
		}		
		return sf.toString();
	}
	
	
	
	public int getUserRuleId() {
		return userRuleId;
	}
	public void setUserRuleId(int userRuleId) {
		this.userRuleId = userRuleId;
	}
	public String getUserRuleName() {
		return userRuleName;
	}
	public void setUserRuleName(String userRuleName) {
		this.userRuleName = userRuleName;
	}
	public String getUserRuleDes() {
		return userRuleDes;
	}
	public void setUserRuleDes(String userRuleDes) {
		this.userRuleDes = userRuleDes;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public List<RulesConditionMapping> getRulesConditionMappingList() {
		return rulesConditionMappingList;
	}
	public void setRulesConditionMappingList(
			List<RulesConditionMapping> rulesConditionMappingList) {
		this.rulesConditionMappingList = rulesConditionMappingList;
	}
	public boolean getDefaultValue() {
		return defaultValue;
	}	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRules [userRuleId=");
		builder.append(userRuleId);
		builder.append(", userRuleName=");
		builder.append(userRuleName);
		builder.append(", userRuleDes=");
		builder.append(userRuleDes);
		builder.append(", enable=");
		builder.append(enable);
		builder.append(", defaultValue=");
		builder.append(defaultValue);
		builder.append(", rulesConditionMappingList=");
		builder.append(rulesConditionMappingList);
		builder.append("]");
		return builder.toString();
	}





	
	
}
