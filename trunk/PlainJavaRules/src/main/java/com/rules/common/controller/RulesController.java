package com.rules.common.controller;

import static com.rules.common.ViewConstants.PAGE_EDIT_RULE;
import static com.rules.common.ViewConstants.PAGE_VIEW_ALL_RULES;
import static com.rules.common.ViewConstants.PAGE_VIEW_RULE;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rules.common.TechnicalException;
import com.rules.common.dbmodel.UserRules;
import com.rules.common.processor.RulesProcessor;
import com.rules.common.uidto.RuleDto;

@Controller
@RequestMapping(value="/rule")
public class RulesController {
	
	private static final Log log = LogFactory.getLog(RulesController.class);
	
	@Resource(name="rulesProcessor")
	private RulesProcessor rulesProcessor;
		
	
	@RequestMapping (value="/view/all" , method=RequestMethod.GET)
	public String viewAllRules(Model model) throws TechnicalException{
		List<RuleDto> ruleList = rulesProcessor.getAllRules();
		model.addAttribute("rules",ruleList);
		log.debug("Rules List :"+ ruleList);
		
		
		return PAGE_VIEW_ALL_RULES;
	}
	
	@RequestMapping(value="/view/{ruleId}" , method = RequestMethod.GET)
	public String viewRule(@PathVariable int ruleId,Model model) throws TechnicalException{		
		log.info("View Rule Method :");
		RuleDto rule = rulesProcessor.getRule(ruleId);
		log.info("Rule Fetched :"+ rule);
		model.addAttribute("rule",rule);
		return PAGE_VIEW_RULE;
	}
	
	
	@RequestMapping(value="/edit/{ruleId}" ,method = RequestMethod.GET)
	public String editRule(@PathVariable int ruleId, Model model) throws TechnicalException{
		log.info("Edit Rule Method :");
		RuleDto rule = rulesProcessor.getRule(ruleId);
		log.info("Rule Fetched :"+ rule);
		
		model.addAttribute("rule",rule);
		return PAGE_EDIT_RULE;
	}
	
	@RequestMapping(value="/edit/save" , method=RequestMethod.POST)
	public String saveRule(@ModelAttribute("rule") RuleDto rule, Model model) throws TechnicalException{
		rulesProcessor.saveRule(rule);
		return "redirect:/rule/view/"+rule.getRuleId();
	}
	
	
	@RequestMapping(value="/edit/saveconditionmappingstatus" , method=RequestMethod.POST)
	public String saveConditionMappingStatus(@ModelAttribute("userRule") UserRules userRule, Model model) throws TechnicalException{
		log.debug(" rulesConditionMappingList :"+ userRule.getRulesConditionMappingList());				
		rulesProcessor.saveRulesConditionMapping(userRule.getRulesConditionMappingList());
		return "redirect:/rule/view/"+userRule.getUserRuleId();
	}
	
	

	public RulesProcessor getRulesProcessor() {
		return rulesProcessor;
	}

	public void setRulesProcessor(RulesProcessor rulesProcessor) {
		this.rulesProcessor = rulesProcessor;
	}	
	
}
