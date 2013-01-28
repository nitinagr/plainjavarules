package org.pjr.rulesengine.ui.controller;





import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.ui.controller.validator.EditRuleValidator;
import org.pjr.rulesengine.ui.processor.RulesProcessor;
import org.pjr.rulesengine.ui.processor.admin.ModelAdminProcessor;
import org.pjr.rulesengine.ui.uidto.ModelDto;
import org.pjr.rulesengine.ui.uidto.RuleDto;
import org.pjr.rulesengine.ui.uidto.RuleLogicUi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The Class RulesController.
 *
 * @author Sudhakar
 */
@Controller
@RequestMapping(value="/rule")
public class RulesController {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(RulesController.class);

	/** The rules processor. */
	@Autowired
	private RulesProcessor rulesProcessor;

	@Autowired
	private ModelAdminProcessor modelAdminProcessor;

	@Autowired
	@Qualifier("editRuleValidator")
	private EditRuleValidator editRuleValidator;



	/**
	 * View all rules.
	 *
	 * @param model the model
	 * @return the string
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	@RequestMapping (value="/view/all" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String viewAllRules(Model model,HttpServletRequest request) throws TechnicalException{

		String modelId=request.getParameter("modelId");
		List<RuleDto> ruleList = null;
		
		if(StringUtils.isEmpty(modelId)){
			ruleList = rulesProcessor.fetchAllRules();
		} else {
			model.addAttribute("model",modelId);
			ruleList = rulesProcessor.fetchAllRulesByModel(modelId);
		}
		model.addAttribute("rules",ruleList);
		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();		
		model.addAttribute("modelClasses", modelClasses);
		
		return "view_all_rules";
	}

	/**
	 * View rule.
	 *
	 * @param ruleId the rule id
	 * @param model the model
	 * @return the string
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	@RequestMapping(value="/view/{ruleId}" , method = RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String viewRule(@PathVariable String ruleId,Model model) throws TechnicalException{
		log.info("View Rule Method :");
		RuleDto rule = rulesProcessor.fetchRule(ruleId);
		if(null == rule) {
			model.addAttribute("message","Rule doesnt exist. Please check the Rule Id in the URL");
			return "error";
		}


		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();		
		model.addAttribute("modelClasses", modelClasses);		
		
		log.info("Rule Fetched :"+ rule);
		model.addAttribute("rule",rule);
		return "view_rule";
	}


	/**
	 * Edits the rule.
	 *
	 * @param ruleId the rule id
	 * @param model the model
	 * @return the string
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	@RequestMapping(value="/edit/{ruleId}" ,method = RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String editRule(@PathVariable String ruleId, Model model) throws TechnicalException{
		log.info("Edit Rule Method :");
		RuleDto rule = rulesProcessor.fetchRule(ruleId);

		if(null == rule) {
			model.addAttribute("message","Rule doesnt exist. Please check the Rule Id in the URL");
			return "error";
		}

		List<RuleLogicUi> rlItems = rulesProcessor.getAllRuleLogicItems(ruleId);
		log.info("Rule Fetched :"+ rule);


		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();		
		model.addAttribute("modelClasses", modelClasses);
		
		model.addAttribute("rule",rule);
		model.addAttribute("rlItems",rlItems);
		return "edit_rule";
	}

	/**
	 * Save rule.
	 *
	 * @param rule the rule
	 * @param model the model
	 * @return the string
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	@RequestMapping(value="/edit/save" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String saveRule(Model model, @ModelAttribute("rule") RuleDto ruleDto, final Errors errors ,  HttpServletRequest request) throws TechnicalException{
		String view = "view_rule" ;
		model.addAttribute("rule",ruleDto);
		model.addAttribute("ischanged", false);
		
		RuleDto currentRuleInDB = rulesProcessor.fetchRule(ruleDto.getRuleId());
		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();

		if(null!=currentRuleInDB){
			editRuleValidator.setOldExecutionOrder(currentRuleInDB.getExecutionOrder());
			editRuleValidator.setOldRuleName(currentRuleInDB.getRuleName());
		} else {
			model.addAttribute("message","Rule doesnt exist.Someone might have deleted the Rule");
			return "error";
		}
		
		
		String index = request.getParameter("index");
		String removeIndex = request.getParameter("removeindex");		
		String logicChange = request.getParameter("logicChange");

		
		if( StringUtils.isNotBlank(logicChange) ){
			String[] logicItems = logicChange.split("@");
			
			List<RuleLogicUi> rlItems = rulesProcessor.getAllRuleLogicItems(ruleDto.getRuleId());
			List<RuleLogicUi> items = new ArrayList<RuleLogicUi>();
			for(String l : logicItems){
				for(RuleLogicUi rlItem : rlItems){
					if(l.equalsIgnoreCase(rlItem.getSubRuleMapIdOrOprMapId())){
						RuleLogicUi item = new RuleLogicUi();
						item.setSubRuleMapIdOrOprMapId(rlItem.getSubRuleMapIdOrOprMapId().replaceAll(RuleLogicUi.SUBRULE_ID_PREFIX, "")
								.replaceAll(RuleLogicUi.OPERATOR_ID_PREFIX, "").replaceAll(RuleLogicUi.PREFIX_SEPERATOR, ""));
						item.setName(rlItem.getName());
						item.setRuleLogicId(rlItem.getRuleLogicId());						
						item.setOperator(rlItem.isOperator());
						item.setSubRule(rlItem.isSubRule());
						items.add(item);						
					}
				}
			}		
			
			if(StringUtils.isNotBlank(index)){
				int insertindex = Integer.parseInt(index);
				items.add(insertindex +1 , rlItems.get( 0 ));	
			}else if(StringUtils.isNotBlank(removeIndex)){
				int remove = Integer.parseInt(removeIndex);
				items.remove(remove);
			}

			ruleDto.setLogic(items);
			view="edit_rule";			
			model.addAttribute("rule", ruleDto);
			model.addAttribute("modelClasses", modelClasses);			
			model.addAttribute("rlItems", rlItems);			
			return view;
		}
				
		
		

		editRuleValidator.validate(ruleDto, errors);
		if(errors.hasFieldErrors()){

			RuleDto rule = rulesProcessor.fetchRule(ruleDto.getRuleId());
			List<RuleLogicUi> rlItems = rulesProcessor.getAllRuleLogicItems(ruleDto.getRuleId());

			rule.setRuleName(ruleDto.getRuleName());
			rule.setRuleDes(ruleDto.getRuleDes());
			rule.setExecutionOrder(ruleDto.getExecutionOrder());
			rule.setActive(ruleDto.isActive());
			rule.setReturnValue(ruleDto.getReturnValue());



			model.addAttribute("errors",errors.getFieldErrors());
			model.addAttribute("rule",rule);
			model.addAttribute("rlItems",rlItems);

			view = "edit_rule";
			return view;
		}

		log.debug("Rule info to save :"+ruleDto );

		rulesProcessor.updateRule(ruleDto);
		RuleDto rule = rulesProcessor.fetchRule(ruleDto.getRuleId());
		

				
		model.addAttribute("modelClasses", modelClasses);
		
		//Updated
		model.addAttribute("rule",rule);
		model.addAttribute("message","Successfully Saved");

		return view;
		//return "redirect:/rule/view/"+rule.getRuleId();
	}




	/**
	 * Gets the rules processor.
	 *
	 * @return the rules processor
	 */
	public RulesProcessor getRulesProcessor() {
		return rulesProcessor;
	}

	/**
	 * Sets the rules processor.
	 *
	 * @param rulesProcessor the new rules processor
	 */
	public void setRulesProcessor(RulesProcessor rulesProcessor) {
		this.rulesProcessor = rulesProcessor;
	}

}
