package org.pjr.rulesengine.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pjr.rulesengine.CommonConstants;
import org.pjr.rulesengine.dbmodel.Model;
import org.pjr.rulesengine.dbmodel.Rule;
import org.pjr.rulesengine.propertyloader.PropertyLoader;

/**
 * The Class RuleDaoTest.
 *
 * @author Sudhakar
 */
public class RuleDaoTest {
	private static final Log  log = LogFactory.getLog(RuleDaoTest.class);
	
	/** The data source. */
	private DataSource dataSource;
	
	/** The rule dao. */
	private RuleDao ruleDao;
	
	/** The model dao. */
	private ModelDao modelDao ;
	
	
	/**
	 * Setup data source.
	 *
	 * @param connectURI the connect uri
	 * @param driverClass the driver class
	 * @param username the username
	 * @param pwd the pwd
	 * @return the data source
	 * @author  Sudhakar
	 */
	public static DataSource setupDataSource(String connectURI,String driverClass, String username,String pwd) {
		BasicDataSource ds = new BasicDataSource();
			ds.setDriverClassName(driverClass);
		 	ds.setUsername(username);
		 	ds.setPassword(pwd);
		 	ds.setUrl(connectURI);
		 	return ds;
	}	
	
	/**
	 * Setup.
	 *
	 * @author  Sudhakar
	 */
	@Before
	public void setup(){
		log.debug("Setting up datasource");
		dataSource = setupDataSource("jdbc:oracle:thin:@//10.81.162.44:1521/FIELDSERVICESMOBILITY", "oracle.jdbc.driver.OracleDriver", "plainjavarules_usr", "plainjavarules_pwd");		
		ruleDao = new RuleDao(dataSource);
		modelDao = new ModelDao(dataSource);
	}
	
	/**
	 * Test connection.
	 *
	 * @author  Sudhakar
	 */
	@Test
	public void testConnection() {
		Connection conn =  null;
		try {
			conn =  dataSource.getConnection();
			log.debug("is connection active :"+ !conn.isClosed());
			
			Assert.assertTrue("Connection is closed ",!conn.isClosed());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(null != conn )
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * Test property loader.
	 *
	 * @author  Sudhakar
	 */
	@Test
	public void testPropertyLoader(){
		String sql=PropertyLoader.getProperty(CommonConstants.QUERY_FETCHRULE_SELECTRULE);
		Assert.assertNotNull("Sql is null ", sql);
	}

	/**
	 * Fetch rule.
	 *
	 * @throws Exception the exception
	 * @author  Sudhakar
	 */
	@Test
	public void fetchRule() throws Exception{		
		Rule rule = ruleDao.fetchRule("2007");
		log.debug("Rule :"+ rule);
		Assert.assertNotNull("Rule is not available in DB ", rule);
	}
	
	/**
	 * Fetch rule by model.
	 *
	 * @throws Exception the exception
	 * @author  Sudhakar
	 */
	@Test
	public void fetchRuleByModel() throws Exception{		
		String modelName="org.ui.me";
		List<Rule> rules = ruleDao.fetchAllRulesBYExecutionOrder(modelName);
		log.debug("Rules fetched :"+rules.size());
		
		for(Rule rule:rules){
			log.debug("Rule :"+ rule);
		}
	}
	
	/**
	 * Checks if is model available.
	 *
	 * @throws Exception the exception
	 * @author  Sudhakar
	 */
	@Test
	public void isModelAvailable() throws Exception{
		String fullyQualifiedClassName ="org.ui.me";		
		Model model = modelDao.isModelNameAlreadyExists(fullyQualifiedClassName);
		Assert.assertNotNull(fullyQualifiedClassName+ " model is not set in DB ", model);
	}
	
	
	/**
	 * Clean up.
	 *
	 * @author  Sudhakar
	 */
	@After
	public void cleanUp(){
		dataSource = null;
	}
	
}
