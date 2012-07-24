package com.rules.common;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mvel2.MVEL;

import com.rules.common.daos.UnitTestModel;
import com.rules.testDataModel.User;

public class MvelTest {
	
	Logger log = Logger.getLogger(MvelTest.class);
		
	@Test
	public void testMvel(){
		log.debug("great ");
		User user = new User();
		user.setUserName("sudhakar");
		user.setAge(25);
		
		String mvelCondition = "age > 40";
		
		Object result = MVEL.eval(mvelCondition,user) ;
		
		System.out.println("Result : "+result);
		
		
	}
	
	@Test
	public void testCollection(){

		
		UnitTestModel model = new UnitTestModel();
		model.setTechnicianId(1010);

		
		String mvelCondition = "(technicianId == '' || true ) && (true) && (true) && (true)";
		Object result = MVEL.eval(mvelCondition,model) ;
		
		System.out.println("Result : "+result);
		
		
	}
	@Test
	public void testCollectionString(){
		Integer i = 10;
		User user = new User();
		user.setUserName("sudhakar");
		user.setAge(25);
		
		
		
		String mvelCondition = "['aa','ff','sudhakar'] contains userName";
		Object result = MVEL.eval(mvelCondition,(Object)user) ;
		
		System.out.println("Result : "+result);
		
		
	}
}
