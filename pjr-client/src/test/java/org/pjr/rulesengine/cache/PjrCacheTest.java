package org.pjr.rulesengine.cache;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.pjr.rulesengine.client.PjrCachemanager;

public class PjrCacheTest {

	
	private PjrCachemanager pjrCacheManager;
	
	
	@Before
	public void setUp(){
		pjrCacheManager = PjrCachemanager.getInstance();
	}
	
	@Test
	public void testPjrCache(){
		String cachename= "pjr.cache";
		pjrCacheManager.put("hi", "Sudhakar", cachename);
		
		String message = (String) pjrCacheManager.get("hi", cachename);

		System.out.println("message :"+ message);
		
		Assert.assertNotNull("the cache is absent", message);
	}
	
	
	@After
	public void shutDown(){
		pjrCacheManager.shutdown();
	}
	
}
