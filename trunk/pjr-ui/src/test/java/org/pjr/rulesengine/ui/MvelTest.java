package org.pjr.rulesengine.ui;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mvel2.MVEL;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

//TODO : Sudhakar : Json Support

public class MvelTest {
	
	Logger log = Logger.getLogger(MvelTest.class);
		
	@Test
	public void testMvel() throws JsonSyntaxException, ClassNotFoundException{
		log.debug("great ");
		User user = new User();
		user.setUserName("sudhakar");
		user.setAge(25);
		
		Gson g = new Gson();
		System.out.println("json format :"+g.toJson(user));
		
		
		
		Object jsonObject = g.fromJson("{\"userName\":\"sudhakar\",\"age\":25}",Class.forName("org.pjr.rulesengine.ui.Gen"));
		
		
		
		//Object o = (Object)user;
		
		String mvelCondition = "age > 40";
		
		Object result = MVEL.eval(mvelCondition,jsonObject) ;
		
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
		
		
		mvelCondition = "['1','25','4'] contains age";
		result = MVEL.eval(mvelCondition,(Object)user) ;
		System.out.println("Result 2 : "+result);
	}
	
}
