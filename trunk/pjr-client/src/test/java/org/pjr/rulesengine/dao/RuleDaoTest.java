package org.pjr.rulesengine.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pjr.rulesengine.CommonConstants;
import org.pjr.rulesengine.dbmodel.Rule;
import org.pjr.rulesengine.propertyloader.PropertyLoader;

public class RuleDaoTest {
	
	
	private DataSource dataSource;
	private RuleDao ruleDao;
	
	
	public static DataSource setupDataSource(String connectURI,String driverClass, String username,String pwd) {
		BasicDataSource ds = new BasicDataSource();
			ds.setDriverClassName(driverClass);
		 	ds.setUsername(username);
		 	ds.setPassword(pwd);
		 	ds.setUrl(connectURI);
		 	return ds;
	}	
	
	@Before
	public void setup(){
		System.out.println("Setting up datasource");
		dataSource = setupDataSource("jdbc:oracle:thin:@//10.81.162.44:1521/FIELDSERVICESMOBILITY", "oracle.jdbc.driver.OracleDriver", "plainjavarules_usr", "plainjavarules_pwd");		
		ruleDao = new RuleDao(dataSource);
		
	}
	
	@Test
	public void testConnection() throws SQLException{
		Connection conn =  dataSource.getConnection();
		System.out.println("is connection active :"+ !conn.isClosed());
		conn.close();
	}
	
	@Test
	public void testPropertyLoader(){
		String sql=PropertyLoader.getProperty(CommonConstants.QUERY_FETCHRULE_SELECTRULE);
		System.out.println(" sql :"+ sql);
	}

	@Test
	public void fetchRule() throws Exception{		
		Rule rule = ruleDao.fetchRule("2007");
		System.out.println("Rule :"+ rule);
	}
	
	@Test
	public void fetchRuleByModel() throws Exception{		
		String modelName="org.ui.me";
		List<Rule> rules = ruleDao.fetchAllRulesBYExecutionOrder(modelName);
		
		for(Rule rule:rules){
			System.out.println("Rule :"+ rule);
		}
	}
	
	
	@After
	public void cleanUp(){
		dataSource = null;
	}
	
}
