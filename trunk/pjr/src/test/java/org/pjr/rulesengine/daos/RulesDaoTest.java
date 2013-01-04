package org.pjr.rulesengine.daos;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Operator;
import org.pjr.rulesengine.dbmodel.Subrule;
import org.pjr.rulesengine.processor.RulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

 


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = { "classpath:pjrContext.xml" })
@TransactionConfiguration(defaultRollback=false)
public class RulesDaoTest {
	
	private Log log = LogFactory.getLog(RulesDaoTest.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private OperatorDao operatorDao;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private SubruleDao subruleDao;
	
	@Autowired
	private RulesEngine rulesEngine;
	
	@Test
	public void testIsValidExpression() throws DataLayerException{
		Subrule subrule = subruleDao.fetchSubrule("23");
		rulesEngine.isExpressionValid(subrule);
		
		List<Operator> operatorList = new ArrayList<Operator>();
		
		Operator o = new Operator();
		o.setName("test");
		o.setValue("vlue");
		operatorList.add(o);
		operatorDao.insertIntoOperator(operatorList);
		
		log.debug("test completed");
	}
	
	@Test	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testCraeOperator() throws DataLayerException{
		
		
		List<Operator> operatorList = new ArrayList<Operator>();
		
		Operator o = new Operator();
		o.setName("test");
		o.setValue("vlue");
		operatorList.add(o);
		operatorDao.insertIntoOperator(operatorList);
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
