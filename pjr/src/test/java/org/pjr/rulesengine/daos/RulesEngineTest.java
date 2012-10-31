package org.pjr.rulesengine.daos;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.processor.RulesEngine;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:pjrContext.xml")
public class RulesEngineTest {
	private Log log = LogFactory.getLog(RulesEngineTest.class);

	@Resource(name="rulesEngine")
	private RulesEngine rulesEngine;

	@Test
	public void printAllRulesInMvelFormat() throws TechnicalException{
		/*Map<Rule,Serializable > expressions = rulesEngine.getCompiledExpressions();
		log.debug("Testing mvel fetch .....");
		Iterator<Rule> iterate = expressions.keySet().iterator();
		while(iterate.hasNext()){
			Rule tempRule = iterate.next();
			String ruleId = tempRule.getId();
			Serializable compileExpr = expressions.get(tempRule);		   	
			log.debug("1- compileExpr for RuleId:"+ruleId+"  is :"+compileExpr.toString());
 
		}*/
	
	}
}
