package com.rules.common.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rules.common.TechnicalException;
import com.rules.common.dbmodel.ConditionPtyMapping;
import com.rules.common.processor.ConditionProcessor;
import com.rules.common.uidto.ConditionDto;

@Component
@RequestMapping(value="/condition")
public class ConditionController {
	
	private static final Log log = LogFactory.getLog(ConditionController.class);
	
	@Resource
	private ConditionProcessor conditionProcessor;
	
	@RequestMapping(value="/view/{conditionId}")
	public String viewCondition(@PathVariable int conditionId,@RequestParam int ruleId, Model model) throws TechnicalException{
		ConditionDto condition = conditionProcessor.getConditionWithRuleText(ruleId,conditionId);					 
		model.addAttribute("condition", condition);	
		return "view_condition";
	}
	
	
	
	@RequestMapping(value="/edit/{conditionId}")
	public String editCondition(@PathVariable int conditionId,@RequestParam int ruleId, Model model) throws TechnicalException{
			ConditionDto condition = conditionProcessor.getConditionWithRuleText(ruleId,conditionId);			
			List<ConditionPtyMapping> conditionPtyMappingList = conditionProcessor.getConditionPtyMapping(conditionId);
			log.debug("Condition :" + condition);	
			
			model.addAttribute("condition", condition);
			model.addAttribute("conditionPtyMappingList", conditionPtyMappingList);
			
		return "edit_condition";
	}
	
	
	@RequestMapping(value="/edit/save")
	public String editCondition(@ModelAttribute("condition") ConditionDto conditionDto,@RequestParam int ruleId, Model model) throws TechnicalException{
		conditionProcessor.saveCondition(ruleId,conditionDto);
		return "redirect:../view/"+conditionDto.getConditionId()+"?ruleId="+ruleId;
	}	
}
