package com.rules.common.daos;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rules.common.TechnicalException;
import com.rules.common.dbmodel.Condition;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (value="classpath:applicationContext.xml")
public class RulesDaoTest {
	
	private Log log = LogFactory.getLog(RulesDaoTest.class);
	
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	
	@Resource
	private DataSource dataSource;

	@Test
	public void testConnection() {
		
		Connection conn;
		try {
			conn = dataSource.getConnection();
			log.debug("Is connected :"+ conn.isValid(5));
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
	}
	
	

	@Test
	public void getAllConditions() throws TechnicalException{
		List<Condition> conditionsList = null;
		String sql ="select c.CONDITION_ID,c.CONDITION_NAME,c.CONDITION_DISPLAY_NAME,c.CONDITION_DISPLAY,c.CONDITION_MVEL_FORMAT,c.ENABLE,c.DEFAULT_VALUE from CONDITIONS c";
		
		conditionsList = jdbcTemplate.query(sql,new RowMapper<Condition> (){

			@Override
			public Condition mapRow(ResultSet rs, int rowCount)
					throws SQLException {
				
				Condition condition = new Condition();
				/*
				condition.setConditionId((rs.getInt(1)));
				condition.setConditionName(rs.getString(2));
				condition.setDisplayName(rs.getString(3));
				condition.setConditionDisplay(rs.getString(4));
				condition.setConditionMvel(rs.getString(5));
				condition.setEnable(rs.getBoolean(6));
				condition.setDefaultvalue(rs.getBoolean(7));
				*/
				return condition;
				
				
			}
			
		});
		
		
		
	}
	
	@Test
	public void getConditionWithRuleText() throws TechnicalException {
		int ruleId =1 ; int conditionId =1;
		
		String sql ="select c.CONDITION_ID,c.CONDITION_NAME,c.CONDITION_DISPLAY_NAME,rcm.CONDITION_DISPLAY,rcm.CONDITION_MVEL_FORMAT,c.ENABLE," +
				"c.DEFAULT_VALUE from RULES_CONDITION_MAPPING rcm join CONDITIONS C on c.CONDITION_ID=rcm.USER_CONDITION_ID where rcm.RULE_ID=? and rcm.USER_CONDITION_ID =?";
		
		Condition condition = jdbcTemplate.queryForObject(sql, new Object[]{new Integer(ruleId),new Integer(conditionId)}, new RowMapper<Condition>(){

			
			@Override
			public Condition mapRow(ResultSet rs, int rowNum) throws SQLException {
				Condition condition = new Condition();
				
				/*
				condition.setConditionId((rs.getInt(1)));
				condition.setConditionName(rs.getString(2));
				condition.setDisplayName(rs.getString(3));
				condition.setConditionDisplay(rs.getString(4));
				condition.setConditionMvel(rs.getString(5));
				condition.setEnable(rs.getBoolean(6));
				condition.setDefaultvalue(rs.getBoolean(7));
				*/
				return condition;
			}
			
		});
			
		
		log.debug("Condition Fetched : "+ condition);
	}
	
	

	
}
