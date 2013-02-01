/**
 *
 */
package org.pjr.rulesengine.ui.processor.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.daos.OperatorDao;
import org.pjr.rulesengine.daos.SubruleDao;
import org.pjr.rulesengine.dbmodel.Subrule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.pjr.rulesengine.ui.uidto.SubruleDto;

/**
 * @author Anubhab(Infosys)
 *
 */
@Component(value="subruleAdminProcessor")
@Transactional(propagation=Propagation.REQUIRED)
public class SubruleAdminProcessorImpl implements SubruleAdminProcessor {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(SubruleAdminProcessorImpl.class);

	@Autowired
	private SubruleDao subruleDao;
	@Autowired
	private OperatorDao operatorDao;


	/**
	 * @return the subruleDao
	 */
	public SubruleDao getSubruleDao() {
		return subruleDao;
	}

	/**
	 * @param subruleDao the subruleDao to set
	 */
	public void setSubruleDao(SubruleDao subruleDao) {
		this.subruleDao = subruleDao;
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.SubruleAdminProcessor#createsubruleDefinition(java.util.List)
	 */
	@Override
	public int createsubruleDefinition(List<SubruleDto> subruleDtoList) throws TechnicalException {
		log.info("Entered createsubruleDefinition processor");
		int i=0;
		try{
			List<Subrule> subrules=new ArrayList<Subrule>();
			for(SubruleDto temp:subruleDtoList){
				subrules.add(DataTransformer.convert(temp));
			}
			i=subruleDao.insertSubrule(subrules).length;
			log.info("Rows inserted:"+i);
		} catch(Exception e){
			throw new TechnicalException(e);
		}
		log.info("Exiting createsubruleDefinition processor");
		return i;
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.SubruleAdminProcessor#deletesubruleDefinition(java.util.List)
	 */
	@Override
	public boolean deletesubruleDefinition(List<String> subruleidList) throws TechnicalException {
		log.info("Entered deletesubruleDefinition processor");
		boolean result=false;
		try{
			result=subruleDao.deleteSubrule(subruleidList);
			log.info("Deletion of subrules status:"+result);
		} catch(Exception e){
			throw new TechnicalException(e);
		}
		log.info("Exiting deletesubruleDefinition processor");
		return result;
	}



	@Override
	public void saveAssignSubRulesToRule(List<String> subruleidListToAssign, List<String> subruleidListToUnAssign , String ruleId) throws TechnicalException {

		subruleDao.saveAssignSubRulesToRule(subruleidListToAssign,subruleidListToUnAssign, ruleId);
	}

	@Override
	public List<String>  subRulesUsedInRulesLogic(List<String> subRuleIdsToUnAssign,String ruleId) throws TechnicalException {

		return subruleDao.subRulesUsedInRulesLogic(subRuleIdsToUnAssign,ruleId);
	}

	@Override
	public void unassignSubRulesFromRule(List<String> subruleidList, String ruleId) throws TechnicalException {

	}

	@Override
	public void saveAssignAllOperatorsToSubrule(String subruleName) throws TechnicalException {
		if(!StringUtils.isBlank(subruleName)){
			Subrule subrule=subruleDao.fetchSubruleByName(subruleName);
			operatorDao.assignAllOperatorsToSubrule(subrule.getId());
		}
	}
}
