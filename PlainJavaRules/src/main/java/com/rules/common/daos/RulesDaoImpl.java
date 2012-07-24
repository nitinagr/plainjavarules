package com.rules.common.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.rules.common.TechnicalException;
import com.rules.common.dbmodel.Condition;
import com.rules.common.dbmodel.RulesConditionMapping;
import com.rules.common.dbmodel.UserRules;

@Component
public class RulesDaoImpl implements RulesDao{
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean save(UserRules rule){
		boolean isSaved = false;
		String sql ="update RULES  set RULE_NAME= ?, RULE_DESCRIPTION = ?,ENABLE  = ? where RULE_ID=?"; 
		jdbcTemplate.update(sql, new Object[] {rule.getUserRuleName(), rule.getUserRuleDes(), rule.isEnable(),rule.getUserRuleId()});
		
		return isSaved;
	}
	
	@Override
	public UserRules getUserRule(int id) throws TechnicalException{		
		String sql = "Select RULE_ID,RULE_NAME,RULE_DESCRIPTION,ENABLE from RULES where RULE_ID = ?";
		
		UserRules userRule;
		try {
			
			userRule = (UserRules)jdbcTemplate.queryForObject(sql, new Object[]{new Integer(id)},new RowMapper<UserRules>() {

					@Override
					public UserRules mapRow(ResultSet rs, int rowNum) throws SQLException {
						UserRules userRule = new UserRules();
						userRule.setUserRuleId(rs.getInt(1));
						userRule.setUserRuleName(rs.getString(2));
						userRule.setUserRuleDes(rs.getString(3));
						userRule.setEnable(rs.getBoolean(4));				
						return userRule;
					}
					
				});
			String conditionSql="select c.CONDITION_ID,c.CONDITION_NAME,c.CONDITION_DISPLAY_NAME,c.DEFAULT_VALUE ,rcm.RULE_ID,rcm.ENABLE,rcm.CONDITION_DISPLAY,rcm.CONDITION_MVEL_FORMAT from RULES_CONDITION_MAPPING rcm join CONDITIONS c on rcm.USER_CONDITION_ID = c.CONDITION_ID and rcm.RULE_ID = ?";
			
			List<RulesConditionMapping> rulesConditionMappingList = jdbcTemplate.query(conditionSql,new Object[]{new Integer(1)}, new RowMapper<RulesConditionMapping>(){

				@Override
				public RulesConditionMapping mapRow(ResultSet rs, int rowCount)
						throws SQLException {
					RulesConditionMapping rulesConditionMapping = new RulesConditionMapping();
					
					Condition condition = new Condition();
					condition.setConditionId((rs.getInt(1)));
					condition.setConditionName(rs.getString(2));
					condition.setDisplayName(rs.getString(3));
					condition.setDefaultvalue(rs.getBoolean(4));
					
					rulesConditionMapping.setRuleId(rs.getInt(5));
					rulesConditionMapping.setEnable(rs.getBoolean(6));
					
					rulesConditionMapping.setConditionDisplay(rs.getString(7));
					rulesConditionMapping.setConditionMvel(rs.getString(8));
					rulesConditionMapping.setCondition(condition);
					return rulesConditionMapping;
				}
				
			});
			userRule.setRulesConditionMappingList(rulesConditionMappingList);
						
		} catch (Exception e) {
			throw new TechnicalException(e);
		}		
		
		return userRule;
	}
	
	
	@Override
	public List<UserRules> getAllUserRules() throws TechnicalException{
		List<UserRules> rulesList = new ArrayList<UserRules>();
		try {
			String sql = "Select RULE_ID,RULE_NAME,RULE_DESCRIPTION,ENABLE from RULES";
			rulesList = jdbcTemplate.query(sql, new RowMapper<UserRules>() {

				@Override
				public UserRules mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserRules userRule = new UserRules();
					userRule.setUserRuleId(rs.getInt(1));
					userRule.setUserRuleName(rs.getString(2));
					userRule.setUserRuleDes(rs.getString(3));
					userRule.setEnable(rs.getBoolean(4));
					return userRule;
				}
				
			});
			
			ArrayList<Integer> userRuleIdList = new ArrayList<Integer>();
			for(UserRules userRules : rulesList){
				int ruleId = userRules.getUserRuleId();				
				userRuleIdList.add(ruleId);
			}			
			String conditionSql="select c.CONDITION_ID,c.CONDITION_NAME,c.CONDITION_DISPLAY_NAME,c.DEFAULT_VALUE ,rcm.RULE_ID,rcm.ENABLE,rcm.CONDITION_DISPLAY,rcm.CONDITION_MVEL_FORMAT from RULES_CONDITION_MAPPING rcm join CONDITIONS c on rcm.USER_CONDITION_ID = c.CONDITION_ID ";
			List<RulesConditionMapping> rulesConditionMappingList = jdbcTemplate.query(conditionSql, new RowMapper<RulesConditionMapping>(){

				@Override
				public RulesConditionMapping mapRow(ResultSet rs, int rowCount)
						throws SQLException {
					RulesConditionMapping rulesConditionMapping = new RulesConditionMapping();
					
					Condition condition = new Condition();
					condition.setConditionId((rs.getInt(1)));
					condition.setConditionName(rs.getString(2));
					condition.setDisplayName(rs.getString(3));
					condition.setDefaultvalue(rs.getBoolean(4));					
					rulesConditionMapping.setRuleId(rs.getInt(5));					
					rulesConditionMapping.setEnable(rs.getBoolean(6));
					
					rulesConditionMapping.setConditionDisplay(rs.getString(7));
					rulesConditionMapping.setConditionMvel(rs.getString(8));
					rulesConditionMapping.setCondition(condition);
					return rulesConditionMapping;
				}
				
			});
			
			for(UserRules userRules : rulesList){				
				for(RulesConditionMapping rcm : rulesConditionMappingList){
					if(userRules.getUserRuleId() == rcm.getRuleId())userRules.getRulesConditionMappingList().add(rcm);					
				}				
			}
			
			
			
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
		return rulesList;
	}


	@Override
	public void saveRulesConditionMapping(
			final List<RulesConditionMapping> rulesConditionMappingList)
			throws TechnicalException {
			
		String sql ="update RULES_CONDITION_MAPPING rcm set rcm.ENABLE = ? where rcm.RULE_ID=? and rcm.USER_CONDITION_ID=?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {				
					RulesConditionMapping rulesConditionMapping = rulesConditionMappingList.get(index);
					ps.setBoolean(1,rulesConditionMapping.isEnable());
					ps.setInt(2, rulesConditionMapping.getRuleId());
					ps.setInt(3, rulesConditionMapping.getCondition().getConditionId());
			}
			
			@Override
			public int getBatchSize() {
				return rulesConditionMappingList.size();
			}
		});
		
	}


	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}



	
}
