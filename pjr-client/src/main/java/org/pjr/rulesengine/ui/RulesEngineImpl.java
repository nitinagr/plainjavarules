package org.pjr.rulesengine.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mvel2.MVEL;
import org.pjr.rulesengine.dao.RuleDao;
import org.pjr.rulesengine.dbmodel.Rule;

public class RulesEngineImpl implements RulesEngine{
	
	private RuleDao ruleDao;
	
	public RulesEngineImpl(DataSource dataSource){
		ruleDao=new RuleDao(dataSource);
	}
	
	@Override
	public Object process(ExecutionMode executionMode, String fullyQualifiedClassName, Object object) {
		String ruleId = "0";
		Rule rule = null;
		List<Rule> rules=null;
		
		try {
			Map<Rule,Serializable > expressions;
			{
				System.out.println("Preparing Rules Engine and Generating Expressions for Processing .....");
				long start = System.currentTimeMillis();
				expressions = getCompiledExpressions(fullyQualifiedClassName);
				System.out.println("Rules Engine preparation Completed in(ms) :"+(System.currentTimeMillis() - start));
			}
			
			Iterator<Rule> iterate = expressions.keySet().iterator();
			while(iterate.hasNext()){
				Rule tempRule = iterate.next();
				ruleId = tempRule.getId();
				Serializable compileExpr = expressions.get(tempRule);
			   	Boolean result = (Boolean) MVEL.executeExpression(compileExpr, object);

			    if(null != result && result){			    	
			    	rule = tempRule;
			    	if(executionMode.equals(ExecutionMode.ELSEIF_MODE)){
			    		break;
			    	}else if(executionMode.equals(ExecutionMode.EVAULATE_ALL_MODE)){
			    		if(null==rules){
			    			rules=new ArrayList<Rule>();
			    			rules.add(rule);
			    		} else {
			    			rules.add(rule);
			    		}
			    	}
			    }
			}
		} catch (Exception e) {
			System.out.println("Error processing RuleId :"+ruleId);
		}
		
		if(null!=rules){
			return rules;
		}else {
			return rule;
		}
	}

	@Override
	public Object processSingleRule(String fullyQualifiedClassName, Object object, String ruleId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public LinkedHashMap<Rule,Serializable > getCompiledExpressions(String modelClass){
		LinkedHashMap<Rule,Serializable > expressions = new LinkedHashMap<Rule, Serializable>();
		try {
			List<Rule> rules;
			if(null!=modelClass && !modelClass.isEmpty()){
				rules = ruleDao.fetchAllRulesBYExecutionOrder();
			} else {
				rules = ruleDao.fetchAllRulesBYExecutionOrder(modelClass);
			}
			for(Rule rule : rules){
				Rule tempRule = ruleDao.fetchRule(rule.getId());
				String strExpression =  tempRule.toMvelExpression();
				Serializable serializableExpr = MVEL.compileExpression(strExpression);
				expressions.put(tempRule, serializableExpr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return expressions;
	}
}
