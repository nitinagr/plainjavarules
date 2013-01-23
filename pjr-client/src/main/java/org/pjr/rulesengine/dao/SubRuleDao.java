package org.pjr.rulesengine.dao;

import javax.sql.DataSource;

import org.pjr.rulesengine.dbmodel.Subrule;

public class SubRuleDao {

	private DataSource dataSource;
	
	public SubRuleDao(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public Subrule fetchSubrule(String id) throws Exception{
		return null;
		
	}
	
}
