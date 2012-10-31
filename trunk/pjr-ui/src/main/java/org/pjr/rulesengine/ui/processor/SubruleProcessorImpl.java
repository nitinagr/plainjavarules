/**
 *
 */
package org.pjr.rulesengine.ui.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.daos.SubruleDao;
import org.pjr.rulesengine.daos.SubruleRuleMappingDao;
import org.pjr.rulesengine.dbmodel.RuleSubruleMapping;
import org.pjr.rulesengine.dbmodel.Subrule;
import org.pjr.rulesengine.dbmodel.SubruleAttributeMapping;
import org.pjr.rulesengine.dbmodel.SubruleLogic;
import org.pjr.rulesengine.dbmodel.SubruleOperatorMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.pjr.rulesengine.ui.processor.admin.DataTransformer;
import org.pjr.rulesengine.ui.uidto.RuleSubruleMappingDto;
import org.pjr.rulesengine.ui.uidto.SubRuleLogicItem;
import org.pjr.rulesengine.ui.uidto.SubruleDto;

/**
 * The Class SubruleProcessorImpl.
 *
 * @author Anubhab(Infosys)
 */
@Component(value="subruleProcessor")
@Transactional(propagation=Propagation.REQUIRED)
public class SubruleProcessorImpl implements SubruleProcessor {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(SubruleProcessorImpl.class);

	/** The subrule dao. */
	@Autowired
	private SubruleDao subruleDao;

	/** The subrule rule mapping dao. */
	@Autowired
	private SubruleRuleMappingDao subruleRuleMappingDao;

	/**
	 * Gets the subrule rule mapping dao.
	 *
	 * @return the subrule rule mapping dao
	 */
	public SubruleRuleMappingDao getSubruleRuleMappingDao() {
		return subruleRuleMappingDao;
	}

	/**
	 * Sets the subrule rule mapping dao.
	 *
	 * @param subruleRuleMappingDao the new subrule rule mapping dao
	 */
	public void setSubruleRuleMappingDao(SubruleRuleMappingDao subruleRuleMappingDao) {
		this.subruleRuleMappingDao = subruleRuleMappingDao;
	}

	/**
	 * Gets the subrule dao.
	 *
	 * @return the subruleDao
	 */
	public SubruleDao getSubruleDao() {
		return subruleDao;
	}

	/**
	 * Sets the subrule dao.
	 *
	 * @param subruleDao the subruleDao to set
	 */
	public void setSubruleDao(SubruleDao subruleDao) {
		this.subruleDao = subruleDao;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.SubruleProcessor#updateSubrule()
	 */
	@Override
	public void updateSubrule(SubruleDto subruleDto) throws TechnicalException {
		log.info("Entered subrule processor:updateSubrule");
		try{
			Subrule subrule=DataTransformer.convert(subruleDto);
			List<Subrule> subruleList=new ArrayList<Subrule>();
			subruleList.add(subrule);
			subruleDao.updateSubrule(subruleList);

			//clean up before inserting
			subruleDao.deleteSubRuleLogic(subruleDto.getId());
			List<SubruleLogic> logicDb = DataTransformer.convertFromSubRuleLogicText(subruleDto.getUpdatedLogicText(),subruleDto.getId());
			subruleDao.insertSubRuleLogic(subruleDto.getId(), logicDb);

		} catch (Exception e) {
			throw new TechnicalException(e);
		}
		log.info("Exiting subrule processor:updateSubrule");
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.SubruleProcessor#fetchAllSubrules()
	 */
	@Override
	public List<SubruleDto> fetchAllSubrules() throws TechnicalException {
		log.info("Entered subrule processor:fetchAllSubrules");
		List<SubruleDto> subruleDtos=new ArrayList<SubruleDto>();

		try{
			List<Subrule> subrules=subruleDao.fetchAllSubrules();
			for(Subrule temp:subrules){
				subruleDtos.add(DataTransformer.convertToUI(temp));
			}
		} catch(Exception e) {
			throw new TechnicalException(e);
		}
		log.info("Entered subrule processor:fetchAllSubrules");
		return subruleDtos;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.SubruleProcessor#fetchSubrule(java.lang.String)
	 */
	@Override
	public SubruleDto fetchSubrule(String id) throws TechnicalException {
		log.info("Entered subrule processor:fetchSubrule");
		SubruleDto subruleDto=null;
		try{
			Subrule subrule=subruleDao.fetchSubrule(id);
			if(null!=subrule) subruleDto=DataTransformer.convertToUI(subrule);
		}catch(Exception e) {
			throw new TechnicalException(e);
		}

		log.info("Exiting subrule processor:fetchSubrule");
		return subruleDto;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.SubruleProcessor#fetchAllSubrules(java.lang.String)
	 */
	@Override
	public List<SubruleDto> fetchAllSubrules(String ruleId) throws TechnicalException {
		List<SubruleDto> subruleDtos=new ArrayList<SubruleDto>();

		try{
			List<Subrule> subrules=subruleDao.fetchAllSubrules(ruleId);
			for(Subrule temp:subrules){
				subruleDtos.add(DataTransformer.convertToUI(temp));
			}
		} catch(Exception e) {
			throw new TechnicalException(e);

		}

		return subruleDtos;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.SubruleProcessor#fetchAllSubrulesMapping()
	 */
	@Override
	public List<RuleSubruleMappingDto> fetchAllSubrulesMapping() throws TechnicalException {
		List<RuleSubruleMappingDto> ruleSubruleMappingDtoList = new ArrayList<RuleSubruleMappingDto>();

		List<RuleSubruleMapping> subRuleMappings = subruleRuleMappingDao.fetchAllRuleSubruleMapping();

		for(RuleSubruleMapping rsm : subRuleMappings){
			ruleSubruleMappingDtoList.add(DataTransformer.convertToUI(rsm));
		}

		return ruleSubruleMappingDtoList;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.SubruleProcessor#fetchSubrulesMapping(java.lang.String)
	 */
	@Override
	public List<RuleSubruleMappingDto> fetchSubrulesMapping(String ruleId) throws TechnicalException {
		List<RuleSubruleMappingDto> ruleSubruleMappingDtoList = new ArrayList<RuleSubruleMappingDto>();

		List<RuleSubruleMapping> subRuleMappings = subruleRuleMappingDao.fetchAllRuleSubruleMapping(ruleId);
		for(RuleSubruleMapping rsm : subRuleMappings){
			ruleSubruleMappingDtoList.add(DataTransformer.convertToUI(rsm));
		}

		return ruleSubruleMappingDtoList;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.SubruleProcessor#getAllSubRuleLogicItems(java.lang.String)
	 */
	@Override
	public List<SubRuleLogicItem> getAllSubRuleLogicItems(String subRuleid) throws TechnicalException {

		List<SubRuleLogicItem> logicItems = new ArrayList<SubRuleLogicItem>();

		List<SubruleAttributeMapping> subruleAttributeMappings = subruleRuleMappingDao.fetchAllAttributeMapping(subRuleid);
		List<SubruleOperatorMapping>  subruleOperatorMappings = subruleRuleMappingDao.fetchAllOperatorMapping(subRuleid);

		List<SubRuleLogicItem> oprItems=  DataTransformer.convertSubRuleOperatorMappingToUI(subruleOperatorMappings);
		List<SubRuleLogicItem> attrItems=  DataTransformer.convertSubruleAttributeMappingUI(subruleAttributeMappings);

		logicItems.addAll(oprItems);
		logicItems.addAll(attrItems);

		return logicItems;
	}


	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.SubruleProcessor#fetchRuleNamesForSubrule(java.lang.String)
	 */
	@Override
	public List<String> fetchRuleNamesForSubrule(String id) {
		List<String> ruleNames=new ArrayList<String>();
		ruleNames=subruleRuleMappingDao.fetchRuleNamesForSubrule(id);
		return ruleNames;
	}

}
