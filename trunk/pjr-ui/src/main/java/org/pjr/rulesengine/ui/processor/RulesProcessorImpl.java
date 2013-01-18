package org.pjr.rulesengine.ui.processor;

import java.util.ArrayList;
import java.util.List;

import org.pjr.rulesengine.NonTechnicalException;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.daos.OperatorDao;
import org.pjr.rulesengine.daos.RuleDao;
import org.pjr.rulesengine.daos.SubruleDao;
import org.pjr.rulesengine.dbmodel.Rule;
import org.pjr.rulesengine.dbmodel.RuleLogic;
import org.pjr.rulesengine.dbmodel.RuleOperatorMapping;
import org.pjr.rulesengine.dbmodel.RuleSubruleMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.pjr.rulesengine.ui.processor.admin.DataTransformer;
import org.pjr.rulesengine.ui.uidto.RuleDto;
import org.pjr.rulesengine.ui.uidto.RuleLogicUi;

/**
 * The Class RulesProcessorImpl.
 *
 * @author Sudhakar
 */
@Component(value="rulesProcessor")
@Transactional(propagation=Propagation.REQUIRED)
public class RulesProcessorImpl implements RulesProcessor{

	@Autowired
	private RuleDao ruleDao;
	@Autowired
	private OperatorDao operatorDao;
	@Autowired
	private SubruleDao subRuleDao;


	@Override
	public List<RuleDto> fetchAllRules() throws TechnicalException {

		List<RuleDto> ruleDtoList = new ArrayList<RuleDto>();
		try {
			List<Rule> rulesList = ruleDao.fetchAllRules();
			ruleDtoList = DataTransformer.convertToUI(rulesList);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}

		return ruleDtoList;
	}

	@Override
	public RuleDto fetchRule(String ruleId) throws TechnicalException {

		RuleDto ruleDto = null;
		try {
			Rule rule = ruleDao.fetchRule(ruleId);
			ruleDto = DataTransformer.convertToUI(rule);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
		return ruleDto;
	}

	@Override
	public RuleDto fetchRuleByRuleName(String ruleName) throws TechnicalException {
		Rule rule = ruleDao.fetchRuleByName(ruleName);
		return DataTransformer.convertToUI(rule);
	}


	@Override
	public void updateRule(RuleDto ruleDto) throws TechnicalException {

		try {
			Rule rule = DataTransformer.convert(ruleDto);
			List<Rule> ruleList = new ArrayList<Rule>();
			ruleList.add(rule);

			ruleDao.updateRule(ruleList);

			//Update the Rule logic Mappings
			ruleDao.deleteRuleLogic(ruleDto.getRuleId());
			List<RuleLogic> ruleLogicDbList = DataTransformer.convertFromLogicText(ruleDto.getUpdatedLogicText(),ruleDto.getRuleId());
			ruleDao.insertRuleLogic(ruleDto.getRuleId(), ruleLogicDbList);

		} catch (Exception e) {
			throw new TechnicalException(e);
		}

	}

	@Override
	public void deleteRule(String ruleId) throws TechnicalException {

		try {
			ruleDao.deleteFromRules(ruleId);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}

	}

	@Override
	public List<RuleLogicUi> getAllRuleLogicItems(String ruleId) throws TechnicalException {
		List<RuleLogicUi> rl = new ArrayList<RuleLogicUi>();

		/*
		List<Operator> operators = operatorDao.fetchOperatorsAllowedForRule(ruleId);
		List<Subrule>  subRules = subRuleDao.fetchSubRulesAllowedForRule(ruleId);
		*/

		List<RuleOperatorMapping>  operatorMappings = operatorDao.fetchRuleOperatorMappingForRule(ruleId);
		List<RuleSubruleMapping>  subruleMappings = subRuleDao.fetchRuleOperatorMappingForRule(ruleId);

		rl.addAll(DataTransformer.convertRuleOperatorMappingToUI(operatorMappings));
		rl.addAll(DataTransformer.convertRuleSubruleMappingUI(subruleMappings));
		return rl;
	}

	@Override
	public void createRuleDefinition(RuleDto ruleDto) throws TechnicalException,NonTechnicalException {

		Rule rule = DataTransformer.convert(ruleDto);

		ruleDao.insertIntoRules(rule);

	}

	@Override
	public void duplicateRuleDefinition(RuleDto ruleDto,String ruleIdToCopy) throws TechnicalException,NonTechnicalException {

		Rule rule = DataTransformer.convert(ruleDto);

		ruleDao.duplicateRuleAndRuleLogic(rule,ruleIdToCopy);

	}

	@Override
	public List<RuleDto> fetchAllRulesByModel(String modelId) throws TechnicalException {
		List<RuleDto> ruleDtoList = new ArrayList<RuleDto>();
		try {
			List<Rule> rulesList = ruleDao.fetchAllRulesByModel(modelId);
			ruleDtoList = DataTransformer.convertToUI(rulesList);
		} catch (Exception e) {
			throw new TechnicalException(e);
		}

		return ruleDtoList;
	}



}
