/**
 *
 */
package org.pjr.rulesengine.ui.controller.administration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.pjr.rulesengine.daos.ModelClassDao;
import org.pjr.rulesengine.ui.controller.validator.SubruleValidator;
import org.pjr.rulesengine.ui.processor.RulesProcessor;
import org.pjr.rulesengine.ui.processor.SubruleProcessor;
import org.pjr.rulesengine.ui.processor.admin.ModelAdminProcessor;
import org.pjr.rulesengine.ui.processor.admin.SubruleAdminProcessor;
import org.pjr.rulesengine.ui.uidto.ModelDto;
import org.pjr.rulesengine.ui.uidto.RuleDto;
import org.pjr.rulesengine.ui.uidto.SubruleDto;

/**
 * @author Anubhab(Infosys)
 *
 */
@Controller
@RequestMapping(value="/admin/subrule/")
public class SubruleAdminController {
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(SubruleAdminController.class);

	@Autowired
	private SubruleProcessor subruleProcessor;
	@Autowired
	private SubruleAdminProcessor subruleAdminProcessor;

	@Autowired
	private RulesProcessor rulesProcessor;
	

	@Autowired
	private ModelAdminProcessor modelAdminProcessor;

	@Autowired
	private SubruleValidator validator;
	/**
	 * @return the subruleProcessor
	 */
	public SubruleProcessor getSubruleProcessor() {
		return subruleProcessor;
	}
	/**
	 * @param subruleProcessor the subruleProcessor to set
	 */
	public void setSubruleProcessor(SubruleProcessor subruleProcessor) {
		this.subruleProcessor = subruleProcessor;
	}
	/**
	 * @return the subruleAdminProcessor
	 */
	public SubruleAdminProcessor getSubruleAdminProcessor() {
		return subruleAdminProcessor;
	}
	/**
	 * @param subruleAdminProcessor the subruleAdminProcessor to set
	 */
	public void setSubruleAdminProcessor(SubruleAdminProcessor subruleAdminProcessor) {
		this.subruleAdminProcessor = subruleAdminProcessor;
	}

	@RequestMapping(value="create" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String create( Model model) throws TechnicalException{
		log.info("Entered subrule create controller");
		String view = null;
		SubruleDto subruleDto=new SubruleDto();
		model.addAttribute("subrule",subruleDto);
		view = "create_subrule_definition";
		log.info("Exiting subrule create operator");
		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();		
		model.addAttribute("modelClasses",modelClasses);
		
		return view;
	}
	@RequestMapping(value="create" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String createSubrule( Model model,@ModelAttribute SubruleDto subruleDto,Errors errors) throws TechnicalException{
		log.info("Entered createSubrule controller:POST");
		String view = null;

		validator.validate(subruleDto, errors);
		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();		
		model.addAttribute("modelClasses",modelClasses);
		
		if(errors.hasFieldErrors()){
			model.addAttribute("errors",errors.getFieldErrors());
			model.addAttribute("subrule",subruleDto);
			view="create_subrule_definition";
			return view;
		}

		List<SubruleDto> subruleDtoList=new ArrayList<SubruleDto>();
		subruleDtoList.add(subruleDto);
		int i=subruleAdminProcessor.createsubruleDefinition(subruleDtoList);
		
		if(i>0){
			model.addAttribute("message","New Subrule Saved Successfully");
			List<SubruleDto> subruleList=subruleProcessor.fetchAllSubrulesbyModelId(subruleDto.getModelId());
			model.addAttribute("subrules",subruleList);
			view="view_all_subrules";
		}

		log.info("Exiting createSubrule controller:POST");
		return view;
	}
	@RequestMapping(value="delete/{id}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String deleteSubrule(Model model,@PathVariable String id) throws TechnicalException{
		log.debug("Entered deleteSubrule controller");
		String view=null;

		//validation
		SubruleDto subruleDto=subruleProcessor.fetchSubrule(id);
		if(null==subruleDto){
			model.addAttribute("message","Subrule doesnt exist. Please check the Subrule Id in the URL");
			return "error";
		}
		//Check reference in rule mapping
		List<String> ruleNames=subruleProcessor.fetchRuleNamesForSubrule(id);
		if(ruleNames.size()>0){
			log.debug("Could not delete subrule");
			model.addAttribute("rulesReferred",ruleNames);
			model.addAttribute("error","Subrule could not be deleted.");
		} else {
			List<String> ids=new ArrayList<String>();
			ids.add(id);
			boolean result=subruleAdminProcessor.deletesubruleDefinition(ids);
			if(result) model.addAttribute("message","Deleted Successfully");
			else	   model.addAttribute("error","Deletion unsuccessfull");

			//view="redirect:/subrule/view/all";
		}

		model.addAttribute("subrule",subruleDto);		
		view="delete_subrule_status";
		log.debug("Exiting deleteSubrule controller");
		return view;
	}

	@RequestMapping (value="view/rule/{ruleId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String assignSubruleToRule(Model model, @PathVariable String ruleId)throws TechnicalException{

		RuleDto rule = rulesProcessor.fetchRule(ruleId);

		if(null == rule) {
			model.addAttribute("message","Rule doesnt exist. Please check the Rule Id in the URL");
			return "error";

		}

		List<SubruleDto> assignedSubRules = subruleProcessor.fetchAllSubrules(ruleId);

		List<SubruleDto>  subRules = subruleProcessor.fetchAllSubrulesbyModelId(rule.getModelId());

		model.addAttribute("assignedSubRules", assignedSubRules);
		model.addAttribute("subRules", subRules);

		model.addAttribute("rule", rule);

		return "assign_subrule_view";

	}

	@RequestMapping (value="view/rule/{ruleId}" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String saveAssignSubruleToRule(Model model , @PathVariable String ruleId ,
						@ModelAttribute SubruleDto sr, Errors errors, HttpServletRequest request)throws TechnicalException{

		String viewName ="assign_subrule_view";

		//Prep the model
		sr = new SubruleDto();
		model.addAttribute("sr", sr);

		String[] subRuleIdsFromRequest = request.getParameterValues("subRuleIdsToAssign");
		if(null == subRuleIdsFromRequest) subRuleIdsFromRequest = new String [0];


		//Validation
		RuleDto rule = rulesProcessor.fetchRule(ruleId);
		if(null == rule) {
			model.addAttribute("message","Rule doesnt exist. Please check the Rule Id in the URL");
			return "error";

		}

		List<SubruleDto> subRulesAssignedCurrentlyToRule =  subruleProcessor.fetchAllSubrules(ruleId);
		// SubRule Ids to Assign
		List<String> subRuleIdsToAssign = new ArrayList<String>();
		for(String toAss : subRuleIdsFromRequest){
			boolean isAbsent = true;
			for(SubruleDto subruleDto :subRulesAssignedCurrentlyToRule){
				if(toAss.equals(subruleDto.getId())){
					isAbsent = false;
					break;
				}
			}

			if(isAbsent)  subRuleIdsToAssign.add(toAss);
		}

		// SubRule Ids to UnAssign
		List<String> subRuleIdsToUnAssign = new ArrayList<String>();
		for(SubruleDto subruleDto :subRulesAssignedCurrentlyToRule){
			boolean isAbsent = true;
			for(String toAss : subRuleIdsFromRequest){
				if(toAss.equals(subruleDto.getId())){
					isAbsent = false;
					break;
				}
			}

			if(isAbsent) {
				subRuleIdsToUnAssign.add(subruleDto.getId());
			}
		}

		//Check for the reference in Rule Logic
		List<String>  subRulesStillUsedInLogic = subruleAdminProcessor.subRulesUsedInRulesLogic(subRuleIdsToUnAssign,ruleId);
		if(null != subRulesStillUsedInLogic && subRulesStillUsedInLogic.size() >0){
			errors.rejectValue("name", "subrule.still.referenced", "Subrules are still referenced");

			model.addAttribute("rule", rule);
			List<SubruleDto> assignedSubRules = subruleProcessor.fetchAllSubrules(ruleId);
			List<SubruleDto>  subRules = subruleProcessor.fetchAllSubrulesbyModelId(rule.getModelId());
			model.addAttribute("assignedSubRules", assignedSubRules);
			model.addAttribute("subRules", subRules);
			model.addAttribute("subRulesReferred", subRulesStillUsedInLogic);
			return viewName;
		}

		log.debug("subRuleIdsToAssign "+ subRuleIdsToAssign);
		log.debug("subRuleIdsToUnAssign "+ subRuleIdsToUnAssign);


		subruleAdminProcessor.saveAssignSubRulesToRule(subRuleIdsToAssign,subRuleIdsToUnAssign, ruleId);

		//Populating the view
		List<SubruleDto> assignedSubRules = subruleProcessor.fetchAllSubrules(ruleId);
		List<SubruleDto>  subRules = subruleProcessor.fetchAllSubrulesbyModelId(rule.getModelId());
		model.addAttribute("assignedSubRules", assignedSubRules);
		model.addAttribute("subRules", subRules);

		model.addAttribute("rule", rule);
		model.addAttribute("message","Successfully Saved");
		return viewName;

	}
}
