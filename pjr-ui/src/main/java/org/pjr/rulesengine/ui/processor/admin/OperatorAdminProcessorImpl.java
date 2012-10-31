/**
 *
 */
package org.pjr.rulesengine.ui.processor.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.daos.OperatorDao;
import org.pjr.rulesengine.dbmodel.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.pjr.rulesengine.ui.uidto.OperatorDto;

/**
 * The Class OperatorAdminProcessorImpl.
 *
 * @author Anubhab(Infosys)
 */
@Component(value="operatorAdminProcessor")
@Transactional(propagation=Propagation.REQUIRED)
public class OperatorAdminProcessorImpl implements OperatorAdminProcessor {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(OperatorAdminProcessorImpl.class);

	/** The operator dao. */
	@Autowired
	private OperatorDao operatorDao;

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#insertOperator(java.util.List)
	 */
	@Override
	public int insertOperator(List<OperatorDto> operatorDtoList) throws TechnicalException {
		log.debug("Entering processor's insertOperator..");
		int[] i=null;
		int rows=0;
		List<Operator> operatorList=new ArrayList<Operator>();

		if(null!=operatorDtoList && !operatorDtoList.isEmpty()){
			log.debug("Converting UITO object to DTO object");
			for(OperatorDto temp:operatorDtoList){
				Operator op=DataTransformer.convert(temp);
				operatorList.add(op);
			}
			try{
				i=operatorDao.insertIntoOperator(operatorList);
				rows=i.length;
			}catch(Exception e){
				throw new TechnicalException(e);
			}
		}
		return rows;
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#updateOperator(java.util.List)
	 */
	@Override
	public boolean updateOperator(List<OperatorDto> operatorToList) throws TechnicalException {
		log.debug("Entering processor's updateOperator..");
		boolean result=false;
		List<Operator> operatorList=new ArrayList<Operator>();

		if(null!=operatorToList && !operatorToList.isEmpty()){
			log.debug("Converting UITO object to DTO object");
			for(OperatorDto temp:operatorToList){
				Operator op=DataTransformer.convert(temp);
				operatorList.add(op);
			}
			try{
				result=operatorDao.updateOperator(operatorList);
			}catch(Exception e){
				throw new TechnicalException(e);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#deleteOperator(long)
	 */
	@Override
	public int deleteOperator(String operatorId) throws TechnicalException {
		log.debug("Entering processor's deleteOperator..");
		int[] i=null;
		int rows=0;
		List<String> operatorIds=new ArrayList<String>();
		operatorIds.add(operatorId);
		if(null!=operatorIds && operatorIds.size()>0){
			try{
				i=operatorDao.deleteOperator(operatorIds);
				if(null!=i)	rows=i.length;
			}catch(Exception e){
				throw new TechnicalException(e);
			}
		}
		return rows;
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#fetchAllOperators()
	 */
	@Override
	public List<OperatorDto> fetchAllOperators() throws TechnicalException {
		log.debug("Entering processor's fetchAllOperators..");
		List<OperatorDto> opToList=new ArrayList<OperatorDto>();
		List<Operator> opList=null;
		try{
			opList = operatorDao.fetchAllOperators();
			if(null!=opList && opList.size()>0) {
				for(Operator temp:opList){
					opToList.add(DataTransformer.convertToUI(temp));
				}
			}
		}catch(Exception e){
			throw new TechnicalException(e);
		}
		return opToList;
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#fetchOperator(long)
	 */
	@Override
	public OperatorDto fetchOperator(String operatorId) throws TechnicalException {
		log.debug("Entering processor's fetchOperator..");
		OperatorDto operatorDto=null;
		Operator operator=null;
		if(null!=operatorId && !operatorId.isEmpty()){
			try{
				operator=operatorDao.fetchOperator(operatorId);
				if(null!=operator) operatorDto=DataTransformer.convertToUI(operator);
			}catch(Exception e){
				throw new TechnicalException(e);
			}
		}
		return operatorDto;
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#fetchOperatorsAssignedToRule(java.lang.String)
	 */
	@Override
	public List<OperatorDto> fetchOperatorsAssignedToRule(String ruleId) throws TechnicalException {

		List<Operator> operators = operatorDao.fetchOperatorsAllowedForRule(ruleId);
		List<OperatorDto>  operatorDtos = DataTransformer.convertOprToRuleUI(operators);

		return operatorDtos;
	}



	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#fetchOperatorsAssignedToRule(java.util.List, java.lang.String)
	 */
	@Override
	public void saveAssignOperatorsToRule(String ruleId, List<String> operatorIds,List<String> operatorIdToUnAssign) throws TechnicalException {
		operatorDao.saveAssignOperatorsToRule(ruleId, operatorIds,operatorIdToUnAssign);

	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#fetchOperatorsAssignedToSubrule(java.lang.String)
	 */
	@Override
	public List<OperatorDto> fetchOperatorsAssignedToSubrule(String subruleId) throws TechnicalException {

		List<Operator> operators=operatorDao.fetchOperatorsAllowedForSubrule(subruleId);
		List<OperatorDto> operatorDtos=DataTransformer.convertOperatorsToUI(operators);
		return operatorDtos;
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#operatorsUsedInSubruleLogic(java.lang.String, java.util.List)
	 */
	@Override
	public List<String> operatorsUsedInSubruleLogic(String subruleid, List<String> operatorIdsUnassigned) {
		log.debug("Entered operatorsUsedInSubruleLogic method in processor");
		List<String> result=new ArrayList<String>();
		if(operatorIdsUnassigned.size()>0) result=operatorDao.operatorNamesInSubruleLogic(subruleid,operatorIdsUnassigned);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#checkOprRuleRef(long)
	 */
	@Override
	public List<String> getOprRuleRef(String operatorId) {
		List<String> result=new ArrayList<String>();
		result=operatorDao.getRulesForOperator(operatorId);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#checkOprSubruleRef(long)
	 */
	@Override
	public List<String> getOprSubruleRef(String operatorId) {
		List<String> result=new ArrayList<String>();
		result=operatorDao.getSubrulesForOperator(operatorId);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#saveAssignOperatorsToSubrule(java.lang.String, java.util.List, java.util.List)
	 */
	@Override
	public boolean saveAssignOperatorsToSubrule(String subruleid, List<String> operatorIds, List<String> operatorIdsUnassigned)
			throws TechnicalException {
		log.debug("Entered saveAssignOperatorsToSubrule method");
		return operatorDao.saveAssignOperatorsToSubrule(subruleid, operatorIds,operatorIdsUnassigned);
	}

	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.OperatorAdminProcessor#fetchOperatorsAssignedToRule(java.util.List, java.lang.String)
	 */
	@Override
	public List<String> fetchOperatorsAssignedToRule(List<String> operatorIdsToUnassign, String ruleId) throws TechnicalException {
		List<String> operatorNames=new ArrayList<String>();
		operatorNames=operatorDao.fetchOperatorsAssignedToRule(operatorIdsToUnassign, ruleId);
		return operatorNames;
	}

}
