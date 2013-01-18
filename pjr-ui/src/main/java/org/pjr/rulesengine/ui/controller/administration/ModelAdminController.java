/**
 *
 */
package org.pjr.rulesengine.ui.controller.administration;

import java.util.ArrayList;
import java.util.List;

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

import org.pjr.rulesengine.ui.controller.validator.EditModelValidator;

import org.pjr.rulesengine.ui.controller.validator.ModelValidator;
import org.pjr.rulesengine.ui.uidto.ModelDto;

import org.pjr.rulesengine.ui.processor.RulesProcessor;
import org.pjr.rulesengine.ui.processor.SubruleProcessor;
import org.pjr.rulesengine.ui.processor.admin.AttributeAdminProcessor;
import org.pjr.rulesengine.ui.processor.admin.ModelAdminProcessor;
import org.pjr.rulesengine.ui.uidto.AttributeDto;
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
	private ModelAdminProcessor modelAdminProcessor;
	
	@Autowired
	@Qualifier("modelValidator")
	private ModelValidator modelValidator;

	@Autowired
	@Qualifier("editModelValidator")
	private EditModelValidator editModelValidator;
	
	@Autowired
	private AttributeAdminProcessor attributeAdminProcessor;
	@Autowired
	private RulesProcessor rulesProcessor;
	@Autowired
	private SubruleProcessor subruleProcessor;


	@RequestMapping(value="create" , method=RequestMethod.GET)
	public String create( Model model){
		String view = null;
		view = "create_model_definition";
		return view;
	}

	@RequestMapping(value="create" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String createModel( Model model , @ModelAttribute ModelDto modelDto,Errors errors) throws TechnicalException{
		log.debug("inside createModel method");
		String view=null;

		
		modelValidator.validate(modelDto, errors);

		if(errors.hasFieldErrors()){
			log.debug("Model Create page has errors");
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

	@RequestMapping(value="edit/{modelId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String editModel(@PathVariable String modelId,Model model) throws TechnicalException{
		String view = null;
		log.info("inside edit controller");
		ModelDto dto = modelAdminProcessor.fetchModel(modelId);
		
		if(null == dto) {
			model.addAttribute("message","Model doesnt exist. Please check the Model Id in the URL");
			return "error";
		}

		view="edit_model_definition";
		model.addAttribute("modelDto", dto);
		log.info("exiting edit controller");
		return view;
	}

	@RequestMapping(value="edit/save" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String editModel( Model model , @ModelAttribute ModelDto modelDto,Errors errors) throws TechnicalException{
		log.info("Saving Model:"+modelDto.toString());
		String view = null;
		ModelDto currentModelInDb=null;
		currentModelInDb=modelAdminProcessor.fetchModel(modelDto.getModel_id());
		if(null!=currentModelInDb){
			//Set the old values before validating
			editModelValidator.setOldName(currentModelInDb.getModel_class_name());
			editModelValidator.validate(modelDto, errors);
		} else {
			model.addAttribute("message","Model doesnt exist. Please check the Model Id in the URL");
			return "error";
		}

		if(errors.hasFieldErrors()){
			log.debug("Operator Edit page has errors");
			model.addAttribute("modelDto", modelDto);
			model.addAttribute("errors",errors.getFieldErrors());
			view="edit_model_definition";
			return view;
		}
		
			List<ModelDto> modelDtoList=new ArrayList<ModelDto>();
			modelDtoList.add(modelDto);
			//save it into DB
			boolean result=modelAdminProcessor.updateModel(modelDtoList);
			if(result) {
				model.addAttribute("message", "Model Saved Successfully");
				model.addAttribute("modelDto", modelDto);
				view="view_model_definition";
			} else {
				model.addAttribute("error", "Model edition failed");
				model.addAttribute("modelDto", modelDto);
				view="edit_model_definition";
			}
		return view;
	}

	@RequestMapping(value="delete/{modelId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String deleteModel(@PathVariable String modelId,Model model) throws TechnicalException{
		String view = null;
		log.info("inside delete deleteModel");

		//OperatorDto opr=operatorAdminProcessor.fetchOperator(operatorId);
		ModelDto dto = modelAdminProcessor.fetchModel(modelId);
		
		if(null == dto) {
			model.addAttribute("message","Model doesnt exist. Please check the Model Id in the URL");
			return "error";
		}
		
		//TODO: Check whether it is refrenced in rule/subrule/attribute mapping
		List<String> attributes=modelAdminProcessor.fetchAttributesByName(modelId);
		List<String> subrulesReferred=modelAdminProcessor.fetchSubrulesByName(modelId);
		List<String> rulesReferred=modelAdminProcessor.fetchRulesByName(modelId);
		

		if((null!=attributes && attributes.size()>0) || (null!=rulesReferred && rulesReferred.size()>0) || (null!=subrulesReferred && subrulesReferred.size()>0)){
			log.debug("Could not delete operator");
			model.addAttribute("attributesReferred",attributes);
			model.addAttribute("rulesReferred",rulesReferred);
			model.addAttribute("subrulesReferred",subrulesReferred);
			model.addAttribute("error","Model Could not be deleted Successfully");
		} else {
			//delete the Model detail using modelId 
			int i=modelAdminProcessor.deleteModel(modelId);
			if(i==1){
				model.addAttribute("message","Model Deleted Successfully");
			} else {
				model.addAttribute("error","Model Could not be deleted Successfully");
			}
		}
		model.addAttribute("modelDto", dto);
		view="delete_model_status_definition";
		return view;
	}

	@RequestMapping(value="view/{modelId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String viewModel(@PathVariable String modelId,Model model) throws TechnicalException{
		log.info("entering view Model");
		String view = null;
		ModelDto dto=null;
		dto=modelAdminProcessor.fetchModel(modelId);
		
		if(null == dto) {
			model.addAttribute("message","Model doesnt exist. Please check the Model Id in the URL");
			return "error";
		}

		if(null!=dto){
			view="view_model_definition";
			model.addAttribute("modelDto", dto);
		}
		log.info("exitinging view Model");
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
