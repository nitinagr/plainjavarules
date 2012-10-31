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

import org.pjr.rulesengine.ui.controller.validator.EditOperatorValidator;
import org.pjr.rulesengine.ui.controller.validator.OperatorValidator;
import org.pjr.rulesengine.ui.processor.RulesProcessor;
import org.pjr.rulesengine.ui.processor.SubruleProcessor;
import org.pjr.rulesengine.ui.processor.admin.OperatorAdminProcessor;
import org.pjr.rulesengine.ui.uidto.OperatorDto;
import org.pjr.rulesengine.ui.uidto.RuleDto;
import org.pjr.rulesengine.ui.uidto.SubruleDto;

/**
 * @author Anubhab(Infosys)
 *
 */
@Controller
@RequestMapping(value="/admin/operator/")
public class OperatorAdminController {
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(OperatorAdminController.class);

	@Autowired
	private OperatorAdminProcessor operatorAdminProcessor;

	@Autowired
	private RulesProcessor rulesProcessor;

	@Autowired
	private SubruleProcessor subruleProcessor;

	@Autowired
	@Qualifier("operatorValidator")
	private OperatorValidator operatorValidator;

	@Autowired
	private EditOperatorValidator editOperatorValidator;

	@RequestMapping(value="create" , method=RequestMethod.GET)
	public String create( Model model){
		String view = null;
		view = "create_operator_definition";
		return view;
	}

	@RequestMapping(value="create" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String createOperator( Model model , @ModelAttribute OperatorDto operator,Errors errors) throws TechnicalException{
		log.debug("inside createOperator method");
		String view=null;

		operatorValidator.validate(operator, errors);

		if(errors.hasFieldErrors()){
			log.debug("Operator Create page has errors");
			model.addAttribute("operator",operator);
			model.addAttribute("errors",errors.getFieldErrors());
			view="create_operator_definition";
			return view;
		}

		List<OperatorDto> opDtoList=new ArrayList<OperatorDto>();
		opDtoList.add(operator);
		int rows;
		log.info("Trying to Add new Operators to DB:"+opDtoList);

		rows = operatorAdminProcessor.insertOperator(opDtoList);
		if(rows==opDtoList.size()){
			log.info("Operators successfully added to DB");
			List<OperatorDto> operatorList = new ArrayList<OperatorDto>();
			//find all operators from DB
			operatorList=operatorAdminProcessor.fetchAllOperators();
			model.addAttribute("operators",operatorList);
			model.addAttribute("message","New operator added successfully" );
			view="viewall_operator_definition";
		}
		return view;
	}

	@RequestMapping(value="edit/{operatorId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String editOperator(@PathVariable String operatorId,Model model) throws TechnicalException{
		String view = null;
		OperatorDto operator=null;
		log.info("inside edit controller");
		operator=operatorAdminProcessor.fetchOperator(operatorId);

		if(null == operator) {
			model.addAttribute("message","Operator doesnt exist. Please check the Operator Id in the URL");
			return "error";
		}

		view="edit_operator_definition";
		model.addAttribute("operator", operator);
		log.info("exiting edit controller");
		return view;
	}

	@RequestMapping(value="edit/save" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String editOperator( Model model , @ModelAttribute OperatorDto operator,Errors errors) throws TechnicalException{
		log.info("Saving operator:"+operator.toString());
		String view = null;
		OperatorDto currenOp=operatorAdminProcessor.fetchOperator(operator.getOperatorId());

		if(null!=currenOp){
			//Set the old values before validating
			editOperatorValidator.setOldName(currenOp.getOperatorName());
			editOperatorValidator.setOldValue(currenOp.getValue());
			editOperatorValidator.validate(operator, errors);
		} else {
			model.addAttribute("message","Operator doesnt exist. Someone might have Deleted the operator");
			return "error";
		}

		if(errors.hasFieldErrors()){
			log.debug("Operator Edit page has errors");
			model.addAttribute("operator",operator);
			model.addAttribute("errors",errors.getFieldErrors());
			view="edit_operator_definition";
			return view;
		}
		//if(OperatorAdminValidator.validateInput(operator)){
			List<OperatorDto> opList=new ArrayList<OperatorDto>();
			opList.add(operator);
			//save it into DB
			boolean result=operatorAdminProcessor.updateOperator(opList);
			if(result) {
				model.addAttribute("message", "Operator Saved Successfully");
				model.addAttribute("operator",operator);
				view="view_operator_definition";
			} else {
				model.addAttribute("error", "Operator edition failed");
				model.addAttribute("operator",operator);
				view="edit_operator_definition";
			}
		/*} else {
			model.addAttribute("error", "One or more required fields are empty");
			model.addAttribute("operator",operator);
			view="edit_operator_definition";
		}*/
		return view;
	}

	@RequestMapping(value="delete/{operatorId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String deleteOperator(@PathVariable String operatorId,Model model) throws TechnicalException{
		String view = null;
		log.info("inside delete operator");

		OperatorDto opr=operatorAdminProcessor.fetchOperator(operatorId);
		if(null == opr) {
			model.addAttribute("message","Operator doesnt exist. Please check the Operator Id in the URL");
			return "error";
		}

		//Check whether it is refrenced in rule/subrule mapping
		List<String> rulesReferred=new ArrayList<String>();
		List<String> subrulesReferred=new ArrayList<String>();
		rulesReferred=operatorAdminProcessor.getOprRuleRef(operatorId);
		subrulesReferred=operatorAdminProcessor.getOprSubruleRef(operatorId);

		if(rulesReferred.size()>0 || subrulesReferred.size()>0){
			log.debug("Could not delete operator");
			model.addAttribute("rulesReferred",rulesReferred);
			model.addAttribute("subrulesReferred",subrulesReferred);
			model.addAttribute("error","Operator Could not be deleted Successfully");
		} else {
			//delete the operator detail using operator ID
			int i=operatorAdminProcessor.deleteOperator(operatorId);

			if(i==1){
				model.addAttribute("message","Operator Deleted Successfully");
			} else {
				model.addAttribute("error","Operator Could not be deleted Successfully");
			}
		}
		model.addAttribute("operator", opr);
		view="delete_operator_status_definition";
		return view;
	}

	@RequestMapping(value="view/{operatorId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String viewOperator(@PathVariable String operatorId,Model model) throws TechnicalException{
		log.info("entering view operator");
		String view = null;
		OperatorDto operator=operatorAdminProcessor.fetchOperator(operatorId);


		if(null == operator) {
			model.addAttribute("message","Operator doesnt exist. Please check the Operator Id in the URL");
			return "error";

		}

		if(null!=operator){
			view="view_operator_definition";
			model.addAttribute("operator", operator);
		}
		log.info("exitinging view operator");
		return view;
	}

	@RequestMapping (value="view/all" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String viewAllOperators(Model model) throws TechnicalException{
		String view=null;
		List<OperatorDto> operatorList = new ArrayList<OperatorDto>();
		//find all operators from DB
		operatorList=operatorAdminProcessor.fetchAllOperators();
		model.addAttribute("operators",operatorList);
		view="viewall_operator_definition";
		return view;
	}


	@RequestMapping (value="view/rule/{ruleid}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String assignOperatorsToRule(@PathVariable String ruleid , Model model,@ModelAttribute OperatorDto operator, Errors errors)throws TechnicalException{

		RuleDto rule = rulesProcessor.fetchRule(ruleid);
		if(null == rule) {
			model.addAttribute("message","Rule doesnt exist. Please check the Rule Id in the URL");
			return "error";

		}

		List<OperatorDto>  operatorDtos = operatorAdminProcessor.fetchOperatorsAssignedToRule(ruleid);
		model.addAttribute("operatorsAssigned", operatorDtos);

		List<OperatorDto>  allOperatorDtos =operatorAdminProcessor.fetchAllOperators();

		model.addAttribute("rule", rule);
		model.addAttribute("operators", allOperatorDtos);

		return "assign_operators_view";
	}



	@RequestMapping (value="view/rule/{ruleid}" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String saveAssignOperatorsToRule(@PathVariable String ruleid , Model model,
			HttpServletRequest request,@RequestParam(required=false) String[] operatorsToAssign)throws TechnicalException{
		String viewName ="assign_operators_view";

		RuleDto rule = rulesProcessor.fetchRule(ruleid);
		if(null == rule) {
			model.addAttribute("message","Rule doesnt exist. Please check the Rule Id in the URL");
			return "error";
		}

		log.debug("operatorsToAssign :" + operatorsToAssign);
		if(null == operatorsToAssign) operatorsToAssign = new String [0];



		List<OperatorDto>  operatorDtosAlreadyAssignedToRule = operatorAdminProcessor.fetchOperatorsAssignedToRule(ruleid);



		List<String> operatorsToAssignList = new ArrayList<String>();

		for(String opr :operatorsToAssign){
			boolean isPresent = false;
			for(OperatorDto oprDto :operatorDtosAlreadyAssignedToRule){
				if(opr.equalsIgnoreCase(oprDto.getOperatorId())){
					isPresent = true;
					break;
				}
			}

			if(!isPresent) operatorsToAssignList.add(opr);
		}


		List<String> operatorsToUnAssignList = new ArrayList<String>();
		for(OperatorDto oprDto :operatorDtosAlreadyAssignedToRule){
			boolean isPresent = false;

			for(String opr :operatorsToAssign){
				if(opr.equalsIgnoreCase(oprDto.getOperatorId())){
					isPresent = true;
					break;
				}
			}
			if(!isPresent) operatorsToUnAssignList.add(oprDto.getOperatorId());
		}


		//Validation
		List<String>  operatorsRefered = operatorAdminProcessor.fetchOperatorsAssignedToRule(operatorsToUnAssignList, ruleid);


		if(null != operatorsRefered && operatorsRefered.size() >0){
			//errors.rejectValue("name", "operator.still.referenced.rule.logic", "Operator referenced in subrule logic");

			model.addAttribute("rule", rule);
			List<OperatorDto>  operatorDtos = operatorAdminProcessor.fetchOperatorsAssignedToRule(ruleid);
			model.addAttribute("operatorsAssigned", operatorDtos);

			List<OperatorDto>  allOperatorDtos =operatorAdminProcessor.fetchAllOperators();
			model.addAttribute("operators", allOperatorDtos);
			model.addAttribute("operatorsRefered", operatorsRefered);
			return viewName;
		}




		operatorAdminProcessor.saveAssignOperatorsToRule(ruleid,operatorsToAssignList,operatorsToUnAssignList);


		List<OperatorDto>  operatorDtos = operatorAdminProcessor.fetchOperatorsAssignedToRule(ruleid);
		model.addAttribute("operatorsAssigned", operatorDtos);

		List<OperatorDto>  allOperatorDtos =operatorAdminProcessor.fetchAllOperators();
		model.addAttribute("operators", allOperatorDtos);


		model.addAttribute("rule", rule);

		model.addAttribute("message","Successfully Saved");
		return "assign_operators_view";
	}

	@RequestMapping (value="view/subrule/{subruleid}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String assignOperatorsToSubrule(@PathVariable String subruleid , Model model,@ModelAttribute OperatorDto operator, Errors errors)throws TechnicalException{
		log.info("Entered assignOperatorsToSubrule controller");
		SubruleDto subruleDto = subruleProcessor.fetchSubrule(subruleid);

		if(null == subruleDto){
			errors.rejectValue("operatorsToAssign","subrule.not.found","SubRule doesnt exist.");
		}

		List<OperatorDto>  operatorDtos = operatorAdminProcessor.fetchOperatorsAssignedToSubrule(subruleid);
		model.addAttribute("operatorsAssigned", operatorDtos);

		List<OperatorDto>  allOperatorDtos =operatorAdminProcessor.fetchAllOperators();

		model.addAttribute("subrule", subruleDto);
		model.addAttribute("operators", allOperatorDtos);

		log.info("Exiting assignOperatorsToSubrule controller");
		return "assign_operators_subrule_view";
	}



	@RequestMapping (value="view/subrule/{subruleid}" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String saveAssignOperatorsToSubrule(@PathVariable String subruleid , Model model, HttpServletRequest request,
			@RequestParam(required=false) String[] operatorsToAssignFromRequest,@ModelAttribute OperatorDto operatorDto,Errors errors)throws TechnicalException{
		log.debug("Entered saveAssignOperatorsToSubrule controller");
		String view="assign_operators_subrule_view";

		//Check for the subrule in DB
		SubruleDto subruleDto = subruleProcessor.fetchSubrule(subruleid);
		if(null == subruleDto){
			errors.rejectValue("message","SubRule doesnt exist.Please check the subrule id in the URL");
			return "error";
		} else {
			model.addAttribute("subrule", subruleDto);
		}

		//fetch all operators
		List<OperatorDto>  allOperatorDtos =operatorAdminProcessor.fetchAllOperators();
		model.addAttribute("operators", allOperatorDtos);

		//get the previously assigned operators
		List<OperatorDto>  operatorsAlreadyAssigned = operatorAdminProcessor.fetchOperatorsAssignedToSubrule(subruleid);

		//list to have the IDs to be unassigned
		List<String> operatorIdsUnassigned=new ArrayList<String>();
		//list to have the IDs to be assigned
		List<String> operatorIdsAssigned=new ArrayList<String>();

		if(null==operatorsToAssignFromRequest) {
			log.debug("Nothing came from request");
			operatorsToAssignFromRequest=new String[0];
		}

		//get to be assigned
		for(String newop:operatorsToAssignFromRequest){
			boolean isAssigned=true;
			for(OperatorDto temp:operatorsAlreadyAssigned){
				if(newop.equals(temp.getOperatorId())){
					isAssigned=false;
					break;
				}
			}
			if(isAssigned)operatorIdsAssigned.add(newop);
		}

		//get to be unassigned
		for(OperatorDto temp:operatorsAlreadyAssigned){
			boolean isNotAssigned=true;
			for(String id:operatorsToAssignFromRequest){
				if(id.equals(temp.getOperatorId())){
					isNotAssigned=false;
					break;
				}
			}
			if(isNotAssigned) operatorIdsUnassigned.add(temp.getOperatorId());
		}

		log.debug("NEw Assigned ids: "+operatorIdsAssigned);
		log.debug("New UN Assigned ids: "+operatorIdsUnassigned);


		//check subrule_logic if the operator is referenced there

		List<String> operatorsUsedInLogic=new ArrayList<String>();

		// if some unassignment is done only then check logic table

		if(operatorIdsUnassigned.size()>0){
			log.debug("Inside unassignment done");
			operatorsUsedInLogic=operatorAdminProcessor.operatorsUsedInSubruleLogic(subruleid,operatorIdsUnassigned);
		}

		if(operatorsUsedInLogic.size()>0){ //few operators were referenced in subrule_logic
			log.debug("Some operators referenced in subrule logic "+ operatorsUsedInLogic.toString());
			errors.rejectValue("operatorName", "operator.still.referenced", "Operators are still referenced");
			model.addAttribute("operatorsReferred",operatorsUsedInLogic);
			model.addAttribute("operatorsAssigned", operatorsAlreadyAssigned);
			model.addAttribute("operators", allOperatorDtos);
			return view;
		}

		//save new data in DB
		log.debug("Trying to save operator data");
		operatorAdminProcessor.saveAssignOperatorsToSubrule(subruleid,operatorIdsAssigned,operatorIdsUnassigned);
		model.addAttribute("message","Successfully Saved");

		//fetch the oprators assigned from DB
		operatorsAlreadyAssigned = operatorAdminProcessor.fetchOperatorsAssignedToSubrule(subruleid);
		model.addAttribute("operatorsAssigned", operatorsAlreadyAssigned);

		log.debug("Exiting saveAssignOperatorsToSubrule controller");
		return view;
	}

}
