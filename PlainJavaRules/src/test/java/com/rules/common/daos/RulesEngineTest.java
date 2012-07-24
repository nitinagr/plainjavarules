package com.rules.common.daos;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rules.common.TechnicalException;
import com.rules.common.processor.RulesEngine;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RulesEngineTest {
	private Log log = LogFactory.getLog(RulesEngineTest.class);

	@Resource(name="rulesEngine")
	private RulesEngine rulesEngine;
	
	
	@Test
	public void testRulesEngine() throws TechnicalException{
		int ruleId = 1;
		
		
		UnitTestModel model = new UnitTestModel();
		model.setTechnicianId(1010);	
		model.setAppointmentType("install");
		model.setRegion(10);
		model.setCustomerType("res");
		model.setAppointmentNo(1);
		
		
		
		log.debug("OUTPUT : "+ rulesEngine.processRule(model, ruleId));
		
	}
}
