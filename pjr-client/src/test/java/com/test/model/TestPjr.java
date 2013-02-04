package com.test.model;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.NonTechnicalException;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.client.RulesEngine;
import org.pjr.rulesengine.client.RulesEngineImpl;
import org.pjr.rulesengine.dbmodel.Model;

public class TestPjr {
	
	public static void main(String[] args) throws DataLayerException {
		
		DataSource dataSource = null;
		
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        ds.setUsername("plainjavarules_usr");
        ds.setPassword("plainjavarules_pwd");
        ds.setUrl("jdbc:oracle:thin:@10.81.162.44:1521:FIELDSERVICESMOBILITY");
        
        dataSource = ds;
		
		RulesEngine engine = new RulesEngineImpl(dataSource) ;
		
		
		User user = new User();
		user.setAge(25);
		user.setCitizenship("american");
		user.setHas_ssid(true);
		user.setName("Sudhakar");
		/*
		
		RulesProcessorImpl rulesProcessor = RulesProcessorImpl.getInstance(dataSource);
		
		Model model = rulesProcessor.isModelNameAlreadyExists("com.test.model.User");
		
		
		
		System.out.println("model :"+model);
		*/
		
		try {
			Object o = engine.processSingleRule("com.test.model.User", user, "2010");
			
			Model b = (Model)o;
			
			System.out.println("Output :"+b);
			
			
		} catch (TechnicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonTechnicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
