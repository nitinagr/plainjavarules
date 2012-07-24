package com.rules.common.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.rules.common.TechnicalException;
import com.rules.common.dbmodel.Condition;
import com.rules.common.dbmodel.ConditionPtyMapping;
import com.rules.common.dbmodel.RulesConditionMapping;
import com.rules.common.uidto.ConditionDto;

@Component
public class ConditionsDaoImpl implements ConditionsDao{
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	
	
	
	@Override
	public RulesConditionMapping getConditionWithRuleText(int ruleId , int conditionId) throws TechnicalException {
		String sql ="select c.CONDITION_ID,c.CONDITION_NAME,c.CONDITION_DISPLAY_NAME,rcm.CONDITION_DISPLAY,rcm.CONDITION_MVEL_FORMAT,rcm.ENABLE," +
				"c.DEFAULT_VALUE ,rcm.RULE_ID from RULES_CONDITION_MAPPING rcm join CONDITIONS C on c.CONDITION_ID=rcm.USER_CONDITION_ID where rcm.RULE_ID=? and rcm.USER_CONDITION_ID =?";
		
		RulesConditionMapping condition = jdbcTemplate.queryForObject(sql, new Object[]{new Integer(ruleId),new Integer(conditionId)}, new RowMapper<RulesConditionMapping>(){

			
			@Override
			public RulesConditionMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
				RulesConditionMapping rcm = new RulesConditionMapping();
				Condition condition = new Condition();
				
				condition.setConditionId((rs.getInt(1)));
				condition.setConditionName(rs.getString(2));
				condition.setDisplayName(rs.getString(3));

				rcm.setConditionDisplay(rs.getString(4));
				rcm.setConditionMvel(rs.getString(5));
				
				condition.setEnable(rs.getBoolean(6));
				rcm.setEnable(rs.getBoolean(6));
				condition.setDefaultvalue(rs.getBoolean(7));
				rcm.setRuleId(rs.getInt(8));
				rcm.setCondition(condition);
				
				return rcm;
			}
			
		});
			
		
		return condition;
	}	
	
	public List<ConditionPtyMapping> getConditionPtyMapping(int conditionId) throws TechnicalException{
		
		String sql="select p.PTY_OPR_ID,p.DISPLAY_NAME,p.OBJECT_NAME from CONDITIONS_PTY_OPERATOR_LIST_MAPPING cpm join PTY_OPERATOR_LIST p on cpm.PTY_OPR_ID = p.PTY_OPR_ID where cpm.CONDITION_ID=?";
		
		List<ConditionPtyMapping> conditionPtyMappingList = jdbcTemplate.query(sql, new Object[]{new Integer(conditionId)}, new RowMapper<ConditionPtyMapping>(){

			@Override
			public ConditionPtyMapping mapRow(ResultSet rs, int rowCount)
					throws SQLException {
				ConditionPtyMapping conditionPtyMapping = new ConditionPtyMapping();
				
				conditionPtyMapping.setId(rs.getInt(1));
				conditionPtyMapping.setDisplayName(rs.getString(2));
				conditionPtyMapping.setObjectName(rs.getString(3));				
				
				return conditionPtyMapping;
			}
			
		});
		
		return conditionPtyMappingList;
	}

	@Override
	public void saveCondition(int ruleId, ConditionDto conditionDto)
			throws TechnicalException {		
		String sql= "update RULES_CONDITION_MAPPING rcm set CONDITION_DISPLAY =?, CONDITION_MVEL_FORMAT=?,ENABLE=? where RULE_ID =? and USER_CONDITION_ID =?";		
		jdbcTemplate.update(sql, new Object[]{conditionDto.getConditionDisplay(),conditionDto.getConditionMvel(),conditionDto.isEnable(), new Integer(ruleId),new Integer(conditionDto.getConditionId())});
		
	}


}
