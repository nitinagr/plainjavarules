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

import org.pjr.rulesengine.ui.controller.validator.EditModelValidator;
import org.pjr.rulesengine.ui.controller.validator.EditOperatorValidator;
import org.pjr.rulesengine.ui.controller.validator.ModelValidator;
import org.pjr.rulesengine.ui.controller.validator.OperatorValidator;
import org.pjr.rulesengine.ui.processor.RulesProcessor;
import org.pjr.rulesengine.ui.processor.SubruleProcessor;
import org.pjr.rulesengine.ui.processor.admin.ModelAdminProcessor;
import org.pjr.rulesengine.ui.processor.admin.OperatorAdminProcessor;
import org.pjr.rulesengine.ui.uidto.ModelDto;
import org.pjr.rulesengine.ui.uidto.OperatorDto;
import org.pjr.rulesengine.ui.uidto.RuleDto;
import org.pjr.rulesengine.ui.uidto.SubruleDto;

/**
 * @author Anubhab(Infosys)
 *
 */
@Controller
@RequestMapping(value="/admin/model/")
public class ModelAdminController {
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(ModelAdminController.class);

	@Autowired
	private RulesProcessor rulesProcessor;

	@Autowired
	private ModelAdminProcessor modelAdminProcessor;
	
	@Autowired
	@Qualifier("modelValidator")
	private ModelValidator modelValidator;

	@Autowired
	@Qualifier("editModelValidator")
	private EditModelValidator editModelValidator;

	@RequestMapping(value="create" , method=RequestMethod.GET)
	public String create( Model model){
		String view = null;
		view = "create_model_definition";
		return view;
	}

	@RequestMapping(value="create" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String createOperator( Model model , @ModelAttribute ModelDto modelDto,Errors errors) throws TechnicalException{
		log.debug("inside createOperator method");
		String view=null;

		
		modelValidator.validate(modelDto, errors);

		if(errors.hasFieldErrors()){
			log.debug("Operator Create page has errors");
			model.addAttribute("model",modelDto);
			model.addAttribute("errors",errors.getFieldErrors());
			view="create_model_definition";
			return view;
		}

		List<ModelDto> mdDtoList=new ArrayList<ModelDto>();
		mdDtoList.add(modelDto);
		int rows=0;
		log.info("Trying to Add new Models to DB:"+modelDto);

		rows = modelAdminProcessor.insertModel(mdDtoList);
		if(rows==mdDtoList.size()){
			log.info("Models successfully added to DB");
			List<ModelDto> allMdDtoList=new ArrayList<ModelDto>();
			//find all operators from DB
			allMdDtoList=modelAdminProcessor.fetchAllModels();
			model.addAttribute("models",allMdDtoList);
			model.addAttribute("message","New model added successfully" );
			view="viewall_model_definition";
		}
		return view;
	}

	@RequestMapping(value="edit/{operatorId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String editOperator(@PathVariable String operatorId,Model model) throws TechnicalException{
		String view = null;
		OperatorDto operator=null;
		log.info("inside edit controller");
		//operator=operatorAdminProcessor.fetchOperator(operatorId);

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
		//OperatorDto currenOp=operatorAdminProcessor.fetchOperator(operator.getOperatorId());
		OperatorDto currenOp=null;
		if(null!=currenOp){
			//Set the old values before validating
		//	editOperatorValidator.setOldName(currenOp.getOperatorName());
		//	editOperatorValidator.setOldValue(currenOp.getValue());
		//	editOperatorValidator.validate(operator, errors);
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
			boolean result = false;
		//	boolean result=operatorAdminProcessor.updateOperator(opList);
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

		//OperatorDto opr=operatorAdminProcessor.fetchOperator(operatorId);
		OperatorDto opr=null;
		if(null == opr) {
			model.addAttribute("message","Operator doesnt exist. Please check the Operator Id in the URL");
			return "error";
		}

		//Check whether it is refrenced in rule/subrule mapping
		List<String> rulesReferred=new ArrayList<String>();
		List<String> subrulesReferred=new ArrayList<String>();
	//	/rulesReferred=operatorAdminProcessor.getOprRuleRef(operatorId);
	//	subrulesReferred=operatorAdminProcessor.getOprSubruleRef(operatorId);

		if(rulesReferred.size()>0 || subrulesReferred.size()>0){
			log.debug("Could not delete operator");
			model.addAttribute("rulesReferred",rulesReferred);
			model.addAttribute("subrulesReferred",subrulesReferred);
			model.addAttribute("error","Operator Could not be deleted Successfully");
		} else {
			//delete the operator detail using operator ID
			//int i=operatorAdminProcessor.deleteOperator(operatorId);
int i=0;
			if(i==1){
				model.addAttribute("message","Operator Deleted Successfully");
			} else {
				model.addAttribute("error","Operator Could not be deleted Successfully");
			}
		}
		//model.addAttribute("operator", opr);
		view="delete_operator_status_definition";
		return view;
	}

	@RequestMapping(value="view/{operatorId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String viewOperator(@PathVariable String operatorId,Model model) throws TechnicalException{
		log.info("entering view operator");
		String view = null;
		//OperatorDto operator=operatorAdminProcessor.fetchOperator(operatorId);
		OperatorDto operator=null;

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
		List<ModelDto> models = new ArrayList<ModelDto>();
		//find all operators from DB
		models=modelAdminProcessor.fetchAllModels();
		model.addAttribute("models",models);
		view="viewall_model_definition";
		return view;
	}

}
