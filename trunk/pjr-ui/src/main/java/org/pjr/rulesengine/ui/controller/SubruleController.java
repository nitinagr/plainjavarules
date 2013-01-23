/**
 *
 */
package org.pjr.rulesengine.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.daos.SubruleDao;
import org.pjr.rulesengine.dbmodel.Subrule;
import org.pjr.rulesengine.processor.RulesEngine;
import org.pjr.rulesengine.ui.controller.validator.EditSubruleValidator;
import org.pjr.rulesengine.ui.controller.validator.SubruleValidator;
import org.pjr.rulesengine.ui.processor.SubruleProcessor;
import org.pjr.rulesengine.ui.processor.admin.ModelAdminProcessor;
import org.pjr.rulesengine.ui.uidto.ModelDto;
import org.pjr.rulesengine.ui.uidto.SubRuleLogicItem;
import org.pjr.rulesengine.ui.uidto.SubruleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Anubhab(Infosys)
 *
 */
@Controller
@RequestMapping(value="/subrule")
public class SubruleController {
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(SubruleController.class);

	@Autowired
	private SubruleProcessor subruleProcessor;

	@Autowired
	@Qualifier("subRuleValidator")
	private SubruleValidator subruleValidator;

	@Autowired
	@Qualifier("editSubruleValidator")
	private EditSubruleValidator editSubruleValidator;

	@Autowired
	private RulesEngine rulesEngine;

	@Autowired
	private SubruleDao subruleDao;
	

	@Autowired
	private ModelAdminProcessor modelAdminProcessor;


	@RequestMapping (value="/view/all" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String viewAllSubRules(Model model,HttpServletRequest request) throws TechnicalException{
		log.info("Entered controller:viewAllSubRules");
		String modelId=request.getParameter("modelId");
		String view=null;
		List<SubruleDto> subruleList= null;
		
		if(StringUtils.isEmpty(modelId)){
			subruleList=subruleProcessor.fetchAllSubrules();
		} else {
			model.addAttribute("model",modelId);
			subruleList=subruleProcessor.fetchAllSubrulesbyModelId(modelId);
		}		
		
		//Setting models
		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();		
		model.addAttribute("modelClasses", modelClasses);
		
		model.addAttribute("subrules",subruleList);
		view="view_all_subrules";
		log.info("Exiting controller:viewAllSubRules");
		return view;
	}

	@RequestMapping (value="/view/{id}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String viewSubRule(Model model,@PathVariable String id) throws TechnicalException{
		log.info("Entered controller:viewSubRule");
		String view=null;
		SubruleDto subruleDto=subruleProcessor.fetchSubrule(id);

		if(null == subruleDto) {
			model.addAttribute("message","SubRule doesnt exist. Please check the SubRule Id in the URL");
			return "error";
		}

		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();		
		model.addAttribute("modelClasses", modelClasses);
		
		model.addAttribute("subrule", subruleDto);
		view="view_subrule";
		log.info("Exiting controller:viewSubRule");
		return view;
	}

	@RequestMapping (value="/edit/{id}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String editSubRule(Model model,@PathVariable String id) throws TechnicalException{
		log.info("Entered controller:editSubRule");
		String view=null;
		SubruleDto subruleDto=subruleProcessor.fetchSubrule(id);



		if(null == subruleDto) {
			model.addAttribute("message","SubRule doesnt exist. Please check the SubRule Id in the URL");
			return "error";
		}


		List<SubRuleLogicItem> srlItems = subruleProcessor.getAllSubRuleLogicItems(id);
		

		List<ModelDto> modelClasses = modelAdminProcessor.fetchAllModels();		
		model.addAttribute("modelClasses", modelClasses);
		model.addAttribute("subrulename", subruleDto.getName());
		model.addAttribute("subrule", subruleDto);
		model.addAttribute("srlItems", srlItems);
		view="edit_subrule";
		log.info("Exiting controller:editSubRule");
		return view;
	}

	@RequestMapping (value="/edit/save" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String saveSubRule(Model model,@ModelAttribute("subrule") SubruleDto subruleDto,Errors errors) throws TechnicalException{
		log.info("Entered controller:editsaveSubRule");
		String view="edit_subrule";

		SubruleDto subFromDb=subruleProcessor.fetchSubrule(subruleDto.getId());

		if(null!=subFromDb){
			editSubruleValidator.setOldName(subFromDb.getName());
			editSubruleValidator.validate(subruleDto, errors);
		} else {
			model.addAttribute("message","SubRule doesnt exist.Someone might have deleted the SubRule");
			return "error";
		}

		if(errors.hasFieldErrors()){
			SubruleDto srDto=subruleProcessor.fetchSubrule(subruleDto.getId());

			List<SubRuleLogicItem> srlItems = subruleProcessor.getAllSubRuleLogicItems(subruleDto.getId());
			model.addAttribute("srlItems", srlItems);

			model.addAttribute("subrulename", srDto.getName());

			srDto.setName(subruleDto.getName());
			srDto.setDescription(subruleDto.getDescription());
			srDto.setDefaultValue(subruleDto.isDefaultValue());
			srDto.setActive(subruleDto.isActive());

			model.addAttribute("errors",errors.getFieldErrors());
			model.addAttribute("subrule", srDto);
			return view;
		}


		log.debug("subruleDto :"+ subruleDto);


		subruleProcessor.updateSubrule(subruleDto);
		SubruleDto srDto=subruleProcessor.fetchSubrule(subruleDto.getId());

		Subrule subruleDb=subruleDao.fetchSubrule(subruleDto.getId());
		String errorMessage = rulesEngine.isExpressionValid(subruleDb);

		if(null != errorMessage){

			errorMessage = errorMessage + " <br/><br/><br/> <b>Please note ,the changes are saved in DB. Please update it correctly. (Untill then Menu Links / Cancel buttons are Disabled)</b>";

			SubruleDto srDto1=subruleProcessor.fetchSubrule(subruleDto.getId());

			List<SubRuleLogicItem> srlItems = subruleProcessor.getAllSubRuleLogicItems(subruleDto.getId());
			model.addAttribute("srlItems", srlItems);

			model.addAttribute("subrulename", srDto1.getName());

			srDto1.setName(subruleDto.getName());
			srDto1.setDescription(subruleDto.getDescription());
			srDto1.setDefaultValue(subruleDto.isDefaultValue());
			srDto1.setActive(subruleDto.isActive());

			FieldError fe = new FieldError("updatedLogicText","updatedLogicText",errorMessage);


			List<FieldError> fes = new ArrayList<FieldError>();
			fes.add(fe);

			model.addAttribute("logicerrors",fes);
			model.addAttribute("subrule", srDto1);
			return view;
		}



		model.addAttribute("subrule", srDto);
		model.addAttribute("message","Saved Successfully");
		view="view_subrule";

		log.info("Exiting controller:saveSubRule");
		return view;
	}
}