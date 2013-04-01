package org.pjr.rulesengine.ui.controller.administration;

import java.util.List;

import org.pjr.rulesengine.NonTechnicalException;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.ui.RuleAlreadyExistException;
import org.pjr.rulesengine.ui.controller.validator.RuleValidator;
import org.pjr.rulesengine.ui.processor.RulesProcessor;
import org.pjr.rulesengine.ui.processor.admin.ModelAdminProcessor;
import org.pjr.rulesengine.ui.uidto.ModelDto;
import org.pjr.rulesengine.ui.uidto.RuleDto;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The Class RuleAdminController.
 *
 * @author Sudhakar
 */
@Controller
@RequestMapping("/admin/rule/")
public class RuleAdminController {

	/** The rules processor. */
	@Autowired
	private RulesProcessor rulesProcessor;

	/** The rule validator. */
	@Autowired
	@Qualifier("ruleValidator")
	private RuleValidator ruleValidator;
	

	@Autowired
	private ModelAdminProcessor modelAdminProcessor;

	@RequestMapping(value="duplicate" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String duplicate(Model model ,@RequestParam(value="ruleIdToCopy" , required=true) String ruleIdToCopy ) throws TechnicalException{
		String view = "duplicate_rule";
		RuleDto rule = new RuleDto();

		model.addAttribute("rule", rule);
		model.addAttribute("ruleIdToCopy", ruleIdToCopy);
		

		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();		
		model.addAttribute("modelClasses", modelClasses);
		
		return view;
	}

	@RequestMapping(value="duplicate" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public String duplicateRule(Model model , @RequestParam(value="ruleIdToCopy" , required=true) String ruleIdToCopy ,@ModelAttribute("rule") RuleDto rule, Errors errors) throws TechnicalException, NonTechnicalException{
		String view = "duplicate_rule";
		model.addAttribute("ruleIdToCopy", ruleIdToCopy);	
		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();		
		model.addAttribute("modelClasses", modelClasses);		
		
		
		ruleValidator.validate(rule, errors);

		if(errors.hasFieldErrors()){
			view = "duplicate_rule";
			model.addAttribute("errors",errors.getFieldErrors());
			model.addAttribute("rule", rule);
			return view;
		}

		try {
			rulesProcessor.duplicateRuleDefinition(rule, ruleIdToCopy);
		} catch (RuleAlreadyExistException e) {
			model.addAttribute("error", "One or more required fields are empty");
			view = "duplicate_rule";
			return view;
		}

		RuleDto ruleDtoSaved = rulesProcessor.fetchRuleByRuleName(rule.getRuleName());
		

		model.addAttribute("rule", ruleDtoSaved);
		view = "view_rule";
		return view;

	}

	/**
	 * Creates the.
	 *
	 * @param model the model
	 * @return the string
	 * @author  Sudhakar (Infosys)
	 */
	@RequestMapping(value="create" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String create( Model model) throws TechnicalException{
		String view = null;
		RuleDto rule = new RuleDto();

		model.addAttribute("rule", rule);
		view = "create_rule_definition";


		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();		
		model.addAttribute("modelClasses", modelClasses);
		
		return view;
	}

	/**
	 * Creates the rule.
	 *
	 * @param model the model
	 * @param rule the rule dto
	 * @param errors the errors
	 * @return the string
	 * @throws TechnicalException the technical exception
	 * @throws NonTechnicalException the non technical exception
	 * @author  Sudhakar (Infosys)
	 */
	@RequestMapping(value="create" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String createRule( Model model , @ModelAttribute("rule") RuleDto rule, Errors errors) throws TechnicalException, NonTechnicalException{
		String view = "create_rule_definition";

		ruleValidator.validate(rule, errors);
		List<ModelDto> modelClasses = null;
		if(errors.hasFieldErrors()){
			view = "create_rule_definition";
			model.addAttribute("errors",errors.getFieldErrors());
			model.addAttribute("rule", rule);
			modelClasses=modelAdminProcessor.fetchAllModels();
			model.addAttribute("modelClasses", modelClasses);		
			return view;
		}

		try {
			rulesProcessor.createRuleDefinition(rule);
		} catch (RuleAlreadyExistException e) {
			model.addAttribute("error", "One or more required fields are empty");
			modelClasses=modelAdminProcessor.fetchAllModels();
			model.addAttribute("modelClasses", modelClasses);
			view = "create_rule_definition";
			return view;
		}

		// Assign ( ) AND NOT 
		rulesProcessor.assignMandatoryOperatorsToRule(rule.getRuleName());
		
		RuleDto ruleDtoSaved = rulesProcessor.fetchRuleByRuleName(rule.getRuleName());
		model.addAttribute("rule", ruleDtoSaved);
		model.addAttribute("message","Successfully Added Rule");
		
		view = "view_rule";
		return view;
	}

	/**
	 * Delete rule.
	 *
	 * @param ruleId the rule id
	 * @param model the model
	 * @return the string
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	@RequestMapping(value="delete/{ruleId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String deleteRule(@PathVariable String ruleId , Model model) throws TechnicalException{		
		RuleDto rule = rulesProcessor.fetchRule(ruleId);

		rulesProcessor.deleteRule(ruleId);
		//model.addAttribute("message","Successfully Deleted.");
		model.addAttribute("rule", rule);
		return "redirect:../../../rule/view/all";
	}


}
