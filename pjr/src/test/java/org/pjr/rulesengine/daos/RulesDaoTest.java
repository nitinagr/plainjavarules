package org.pjr.rulesengine.daos;


import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Subrule;
import org.pjr.rulesengine.processor.RulesEngine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

 


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = { "classpath:pjrContext.xml" })
public class RulesDaoTest {
	
	private Log log = LogFactory.getLog(RulesDaoTest.class);
	
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	
	@Resource
	private DataSource dataSource;

	@Resource
	private SubruleDao subruleDao;
	
	@Resource
	private RulesEngine rulesEngine;
	
	@Test
	public void testIsValidExpression() throws DataLayerException{
		Subrule subrule = subruleDao.fetchSubrule("23");
		rulesEngine.isExpressionValid(subrule);
	}

	@Test
	public void testConnection() {
		
		Connection conn;
		try {
			conn = dataSource.getConnection();			
			log.debug("Test completed");
			log.debug("Is connected :"+ conn.isValid(5));
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
	}
	
	

	
}
