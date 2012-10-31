package org.pjr.rulesengine.ui.processor.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.pjr.rulesengine.dbmodel.Attribute;
import org.pjr.rulesengine.dbmodel.Operator;
import org.pjr.rulesengine.dbmodel.Rule;
import org.pjr.rulesengine.dbmodel.RuleLogic;
import org.pjr.rulesengine.dbmodel.RuleOperatorMapping;
import org.pjr.rulesengine.dbmodel.RuleSubruleMapping;
import org.pjr.rulesengine.dbmodel.Subrule;
import org.pjr.rulesengine.dbmodel.SubruleAttributeMapping;
import org.pjr.rulesengine.dbmodel.SubruleLogic;
import org.pjr.rulesengine.dbmodel.SubruleOperatorMapping;

import org.pjr.rulesengine.ui.uidto.AttributeDto;
import org.pjr.rulesengine.ui.uidto.OperatorDto;
import org.pjr.rulesengine.ui.uidto.RuleDto;
import org.pjr.rulesengine.ui.uidto.RuleSubruleMappingDto;
import org.pjr.rulesengine.ui.uidto.SubRuleLogicItem;
import org.pjr.rulesengine.ui.uidto.SubruleDto;

public class DataTransformer {

	//operator
	public static Operator convert(OperatorDto operatorDto){
		Operator operator=null;
		if(null!=operatorDto){
			operator=new Operator();
			operator.setId(operatorDto.getOperatorId());
			operator.setName(operatorDto.getOperatorName());
			operator.setValue(operatorDto.getValue());
		}
		return operator;
	}
	public static OperatorDto convertToUI(Operator operator){
		OperatorDto operatorDto=null;
		if(null!=operator){
		operatorDto=new OperatorDto();
		operatorDto.setOperatorId(operator.getId());
		operatorDto.setOperatorName(operator.getName());
		operatorDto.setValue(operator.getValue());
		}
		return operatorDto;
	}
	public static List<OperatorDto> convertOperatorsToUI(List<Operator> operators){
		List<OperatorDto> operatorDtos=new ArrayList<OperatorDto>();
		if(null!=operators && operators.size()>0){
			operatorDtos=new ArrayList<OperatorDto>();
			for(Operator temp:operators){
				OperatorDto operatorDto=convertToUI(temp);
				operatorDtos.add(operatorDto);
			}
		}
		return operatorDtos;
	}
	public static Attribute convert(AttributeDto attributeDto){
		Attribute attribute=null;
		if(null!=attributeDto){
			attribute=new Attribute();
			attribute.setId(attributeDto.getAttributeId());
			attribute.setName(attributeDto.getAttributeName());
			attribute.setValue(attributeDto.getValue());
		}
		return attribute;
	}
	public static AttributeDto convertToUI(Attribute attribute){
		AttributeDto attributeDto=null;
		if(null!=attribute){
			attributeDto= new AttributeDto();
			attributeDto.setAttributeId(attribute.getId());
			attributeDto.setAttributeName(attribute.getName());
			attributeDto.setValue(attribute.getValue());
		}
		return  attributeDto;
	}
	/**
	 * @param attributes
	 * @return
	 *
	 * @Author Anubhab(Infosys)
	 * Created: Sep 7, 2012
	 */
	public static List<AttributeDto> convertAttributesToUI(List<Attribute> attributes) {
		List<AttributeDto> attributeDtos=new ArrayList<AttributeDto>();
		if(null!=attributes && attributes.size()>0){
			attributeDtos=new ArrayList<AttributeDto>();
			for(Attribute temp:attributes){
				attributeDtos.add(convertToUI(temp));
			}
		}
		return attributeDtos;
	}

	//Sub Rule
	public static  Subrule convert(SubruleDto subruleDto){
		Subrule subrule=null;
		if(null!=subruleDto){
			subrule=new Subrule();
			subrule.setId(subruleDto.getId());
			subrule.setName(subruleDto.getName());
			subrule.setDescription(subruleDto.getDescription());
			subrule.setDefaultValue(subruleDto.isDefaultValue());
			subrule.setActive(subruleDto.isActive());
		}
		return subrule;
	}
	public static SubruleDto convertToUI(Subrule subrule){
		SubruleDto subruleDto=null;
		if(null!=subrule){
			subruleDto=new SubruleDto();
			subruleDto.setId(subrule.getId());
			subruleDto.setName(subrule.getName());
			subruleDto.setDescription(subrule.getDescription());
			subruleDto.setDefaultValue(subrule.isDefaultValue());
			subruleDto.setActive(subrule.isActive());

			List<SubruleLogic>   subRuleLogic = subrule.getLogic();

			List<SubRuleLogicItem> subRuleLogicItems = new ArrayList<SubRuleLogicItem>();
			for(SubruleLogic srl :subRuleLogic){
				SubRuleLogicItem subRuleLogicItem = new SubRuleLogicItem();
				if(StringUtils.isNotBlank(srl.getAttributeMapId()) ){
					subRuleLogicItem.setAttribute(true);
					subRuleLogicItem.setAttrMapIdOrOprMapId(srl.getAttributeMapId());

					subRuleLogicItem.setName(srl.getAttribute().getName());
				}else{
					subRuleLogicItem.setOperator(true);
					subRuleLogicItem.setAttrMapIdOrOprMapId(srl.getOperatorMapId());
					subRuleLogicItem.setName(srl.getOperator().getName());
				}
				subRuleLogicItems.add(subRuleLogicItem);

			}
			subruleDto.setLogic(subRuleLogicItems);

		}
		return subruleDto;
	}


	//Rules
	public static List<RuleDto> convertToUI(List<Rule> ruleList){
		List<RuleDto>  ruleDtoList = new ArrayList<RuleDto>();

		for(Rule rule :ruleList){
			RuleDto ruleDto = convertToUI(rule);
			ruleDtoList.add(ruleDto);
		}

		return ruleDtoList;
	}

	public static RuleDto convertToUI(Rule rule){
		RuleDto ruleDto = null;
		if (null != rule) {
			ruleDto = new RuleDto();
			ruleDto.setRuleId(rule.getId());
			ruleDto.setRuleName(rule.getRuleName());
			ruleDto.setRuleDes(rule.getRuleDescription());
			ruleDto.setActive(rule.isActive());
			ruleDto.setReturnValue(rule.getReturnValue());
			ruleDto.setExecutionOrder(String.valueOf(rule.getExecutionOrder()) );
			List<org.pjr.rulesengine.ui.uidto.RuleLogicUi> uiLogic = new ArrayList<org.pjr.rulesengine.ui.uidto.RuleLogicUi>();
			Set<RuleLogic> logic = rule.getLogic();
			if (null != logic) {
				for (RuleLogic rLogic : logic) {

					org.pjr.rulesengine.ui.uidto.RuleLogicUi rl = new org.pjr.rulesengine.ui.uidto.RuleLogicUi();
					rl.setRuleLogicId(rLogic.getId());
					if (null != rLogic.getRuleSubRuleMappingId()) {
						rl.setSubRuleMapIdOrOprMapId(rLogic.getSubRule().getId());
						rl.setName(rLogic.getSubRule().getName());
						rl.setSubRule(true);
					} else {
						rl.setSubRuleMapIdOrOprMapId(rLogic.getOperator().getId());
						rl.setName(rLogic.getOperator().getName());
						rl.setOperator(true);
					}

					uiLogic.add(rl);
				}

			}
			ruleDto.setLogic(uiLogic);
		}
		return ruleDto;
	}

	public static Rule convert(RuleDto ruleDto){
		Rule rule = null;

		if(null != ruleDto){
			rule = new Rule();

			rule.setId(ruleDto.getRuleId());
			rule.setRuleName(ruleDto.getRuleName());
			rule.setRuleDescription(ruleDto.getRuleDes());
			rule.setActive(ruleDto.isActive());
			rule.setReturnValue(ruleDto.getReturnValue());
			rule.setExecutionOrder(Integer.parseInt(ruleDto.getExecutionOrder()) );
		}
		return rule;
	}


	public static List<org.pjr.rulesengine.ui.uidto.RuleLogicUi> convertToRuleUI(List<Operator> operators ){
		List<org.pjr.rulesengine.ui.uidto.RuleLogicUi>  rlList =  new ArrayList<org.pjr.rulesengine.ui.uidto.RuleLogicUi>();

		if(null != operators){
			for(Operator op : operators){
				org.pjr.rulesengine.ui.uidto.RuleLogicUi rl = new org.pjr.rulesengine.ui.uidto.RuleLogicUi();

				rl.setSubRuleMapIdOrOprMapId(op.getId());
				rl.setName(op.getName());
				rl.setOperator(true);
				rlList.add(rl);
			}
		}

		return rlList;

	}


	public static List<org.pjr.rulesengine.ui.uidto.RuleLogicUi> convertRuleSubruleMappingUI(List<RuleSubruleMapping>   subRuleMappings ){
		List<org.pjr.rulesengine.ui.uidto.RuleLogicUi>  rlList =  new ArrayList<org.pjr.rulesengine.ui.uidto.RuleLogicUi>();

		if(null != subRuleMappings){
			for(RuleSubruleMapping rsm : subRuleMappings){
				org.pjr.rulesengine.ui.uidto.RuleLogicUi rl = new org.pjr.rulesengine.ui.uidto.RuleLogicUi();

				rl.setSubRuleMapIdOrOprMapId(rsm.getId());
				rl.setName(rsm.getSubRuleName());
				rl.setSubRule(true);
				rlList.add(rl);
			}
		}

		return rlList;

	}

	public static List<org.pjr.rulesengine.ui.uidto.RuleLogicUi> convertRuleOperatorMappingToUI(List<RuleOperatorMapping>  operatorMappings ){
		List<org.pjr.rulesengine.ui.uidto.RuleLogicUi>  rlList =  new ArrayList<org.pjr.rulesengine.ui.uidto.RuleLogicUi>();

		if(null != operatorMappings){
			for(RuleOperatorMapping opm : operatorMappings){
				org.pjr.rulesengine.ui.uidto.RuleLogicUi rl = new org.pjr.rulesengine.ui.uidto.RuleLogicUi();

				rl.setSubRuleMapIdOrOprMapId(opm.getId());
				rl.setName(opm.getOperator().getName());
				rl.setOperator(true);
				rlList.add(rl);
			}
		}

		return rlList;

	}



	public static List<OperatorDto> convertOprToRuleUI(List<Operator> operators ){
		List<OperatorDto>  oprs = new ArrayList<OperatorDto>();

		if(null != operators){
			for(Operator op : operators){
				OperatorDto oprDto = new OperatorDto();

				oprDto.setOperatorId((op.getId()));
				oprDto.setOperatorName(op.getName());
				oprDto.setValue(op.getValue());

				oprs.add(oprDto);

			}
		}

		return oprs;

	}


	public static List<org.pjr.rulesengine.ui.uidto.RuleLogicUi> convertsubruleToRuleLogicUI(List<Subrule> subrules ){
		List<org.pjr.rulesengine.ui.uidto.RuleLogicUi>  rlList =  new ArrayList<org.pjr.rulesengine.ui.uidto.RuleLogicUi>();

		if(null != subrules){
			for(Subrule sr : subrules){
				org.pjr.rulesengine.ui.uidto.RuleLogicUi rl = new org.pjr.rulesengine.ui.uidto.RuleLogicUi();

				rl.setSubRuleMapIdOrOprMapId(sr.getId());
				rl.setName(sr.getName());
				rl.setSubRule(true);
				rlList.add(rl);
			}
		}

		return rlList;

	}


	public static List<RuleLogic> convertFromLogicText (String logicText, String ruleId){
		List<RuleLogic> logicList = new ArrayList<RuleLogic>();

		if(StringUtils.isNotBlank(logicText)){

			String[] logicItems = logicText.split(org.pjr.rulesengine.ui.uidto.RuleLogicUi.ITEMS_SEPERATOR);

			for(int i=0 ; i<logicItems.length ; i++){
				String logicItemStr = logicItems[i];

				System.out.println("logicItemStr :"+ logicItemStr);

				RuleLogic rl = new RuleLogic();
				rl.setRuleId(ruleId);
				if(logicItemStr.startsWith(org.pjr.rulesengine.ui.uidto.RuleLogicUi.SUBRULE_ID_PREFIX)){
					 String subRuleId = logicItemStr.replaceAll(org.pjr.rulesengine.ui.uidto.RuleLogicUi.SUBRULE_ID_PREFIX+
							 org.pjr.rulesengine.ui.uidto.RuleLogicUi.PREFIX_SEPERATOR	 , "");
					rl.setRuleSubRuleMapping(subRuleId);
					rl.setOrderno((i+1));
					logicList.add(rl);
				}else if (logicItemStr.startsWith(org.pjr.rulesengine.ui.uidto.RuleLogicUi.OPERATOR_ID_PREFIX)){
					String operatorId = logicItemStr.replaceAll(org.pjr.rulesengine.ui.uidto.RuleLogicUi.OPERATOR_ID_PREFIX
							+org.pjr.rulesengine.ui.uidto.RuleLogicUi.PREFIX_SEPERATOR, "");
					rl.setRuleOperatorIdMapping(operatorId);
					rl.setOrderno((i+1));
					logicList.add(rl);
				}

			}

		}
		return logicList;

	}


	public static List<SubruleLogic> convertFromSubRuleLogicText (String logicText, String subRuleId){
		List<SubruleLogic> logicList = new ArrayList<SubruleLogic>();

		if(StringUtils.isNotBlank(logicText)){

			String[] logicItems = logicText.split(org.pjr.rulesengine.ui.uidto.RuleLogicUi.ITEMS_SEPERATOR);

			for(int i=0 ; i<logicItems.length ; i++){
				String logicItemStr = logicItems[i];

				SubruleLogic rl = new SubruleLogic();
				rl.setSubRuleId(subRuleId);
				if(logicItemStr.startsWith(SubRuleLogicItem.ATTRIBUTE_ID_PREFIX)){
					 String attributeMapId = logicItemStr.replaceAll(SubRuleLogicItem.ATTRIBUTE_ID_PREFIX+ SubRuleLogicItem.PREFIX_SEPERATOR	 , "");
					rl.setAttributeMapId(attributeMapId);
					rl.setOrderno(String.valueOf(i+1));
					logicList.add(rl);
				}else if(logicItemStr.startsWith(SubRuleLogicItem.OPERATOR_ID_PREFIX)){
					String operatorId = logicItemStr.replaceAll(SubRuleLogicItem.OPERATOR_ID_PREFIX	+SubRuleLogicItem.PREFIX_SEPERATOR, "");
					rl.setOperatorMapId(operatorId);
					rl.setOrderno(String.valueOf(i+1));
					logicList.add(rl);
				}
			}
		}
		return logicList;

	}

	public static RuleSubruleMappingDto convertToUI(RuleSubruleMapping ruleSubruleMapping){

		RuleSubruleMappingDto ruleSubruleMappingDto  = null;
		if (null != ruleSubruleMapping) {
			ruleSubruleMappingDto = new RuleSubruleMappingDto();

			ruleSubruleMappingDto.setId(ruleSubruleMapping.getId());
			ruleSubruleMappingDto.setRuleId(ruleSubruleMapping.getRuleId());
			ruleSubruleMappingDto.setSubRuleId(ruleSubruleMapping.getSubRuleId());
			ruleSubruleMappingDto.setSubRuleName(ruleSubruleMapping.getSubRuleName());
			ruleSubruleMappingDto.setSubRuleDescription(ruleSubruleMapping.getSubRuleDescription());
		}
		return ruleSubruleMappingDto;
	}
	public static List<SubRuleLogicItem> convertSubRuleOperatorMappingToUI(List<SubruleOperatorMapping> subruleOperatorMappings) {

		List<SubRuleLogicItem> slItems = new ArrayList<SubRuleLogicItem>();

		for(SubruleOperatorMapping soopm :subruleOperatorMappings){

			SubRuleLogicItem srli = new SubRuleLogicItem();

			srli.setAttrMapIdOrOprMapId(soopm.getId());
			srli.setName(soopm.getOperator().getName());
			srli.setOperator(true);

			slItems.add(srli);

		}

		return slItems;
	}
	public static List<SubRuleLogicItem> convertSubruleAttributeMappingUI(List<SubruleAttributeMapping> subruleAttributeMappings) {

		List<SubRuleLogicItem> srlItem = new ArrayList<SubRuleLogicItem>();

		for(SubruleAttributeMapping srAttrmap: subruleAttributeMappings){
			SubRuleLogicItem subRuleLogicItem = new SubRuleLogicItem();

			subRuleLogicItem.setAttrMapIdOrOprMapId(srAttrmap.getId());
			subRuleLogicItem.setName(srAttrmap.getAttribute().getName());
			subRuleLogicItem.setAttribute(true);

			srlItem.add(subRuleLogicItem);
		}


		return srlItem;
	}
}


