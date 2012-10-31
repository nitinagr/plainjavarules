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

import org.pjr.rulesengine.ui.controller.validator.AttributeValidator;
import org.pjr.rulesengine.ui.controller.validator.EditAttributeValidator;
import org.pjr.rulesengine.ui.processor.SubruleProcessor;
import org.pjr.rulesengine.ui.processor.admin.AttributeAdminProcessor;
import org.pjr.rulesengine.ui.uidto.AttributeDto;
import org.pjr.rulesengine.ui.uidto.SubruleDto;

/**
 * @author Anubhab(Infosys)
 *
 */
@Controller
@RequestMapping(value="/admin/attribute/")
public class AttributeAdminController {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(AttributeAdminController.class);

	@Autowired
	private SubruleProcessor subruleProcessor;

	@Autowired
	private AttributeAdminProcessor attributeAdminProcessor;

	@Autowired
	@Qualifier("attributeValidator")
	private AttributeValidator attributeValidator;

	@Autowired
	private EditAttributeValidator editAttributeValidator;

	@RequestMapping(value="create" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String create( Model model){
		String view = null;
		view = "create_attribute_definition";
		return view;
	}

	@RequestMapping(value="create" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String createAttribute( Model model , @ModelAttribute AttributeDto attribute,Errors errors) throws TechnicalException{
		log.debug("Entering createAttribute method");
		String view=null;
		attributeValidator.validate(attribute, errors);

		if(errors.hasFieldErrors()){
			log.debug("Attribute Create form has errors");
			model.addAttribute("errors",errors.getFieldErrors());
			model.addAttribute("attribute",attribute);
			view = "create_attribute_definition";
			return view;
		}
		List<AttributeDto> attrDtoList=new ArrayList<AttributeDto>();
		attrDtoList.add(attribute);
		int rows;
		log.info("Trying to Add new attributes to DB:"+attrDtoList);
		rows = attributeAdminProcessor.insertAttribute(attrDtoList);
		if(rows==attrDtoList.size()){
			log.info("Successfully added attributes to DB..");
			List<AttributeDto> attributeList = new ArrayList<AttributeDto>();
			attributeList=attributeAdminProcessor.fetchAllAttributes();
			model.addAttribute("attributes",attributeList);
			model.addAttribute("message","New attribute added successfully");
			view="viewall_attribute_definition";
		}
		return view;
	}

	@RequestMapping(value="edit/{attributeId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String editAttribute(Model model,@PathVariable String attributeId) throws TechnicalException{
		String view = null;
		AttributeDto attribute=null;
		log.info("inside edit controller");
		attribute=attributeAdminProcessor.fetchAttribute(attributeId);

		if(null == attribute) {
			model.addAttribute("message","Attribute doesnt exist. Please check the Attribute Id in the URL");
			return "error";
		}
		log.debug("attribute :"+ attribute);

		model.addAttribute("attribute", attribute);
		log.info("exiting edit controller");
		view="edit_attribute_definition";
		return view;
	}
	@RequestMapping(value="edit/save" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String editAttribute( Model model , @ModelAttribute AttributeDto attribute,Errors errors) throws TechnicalException{
		log.info("Trying to Edit attribute:"+attribute.toString());
		String view = null;
		AttributeDto currentAttr=attributeAdminProcessor.fetchAttribute(attribute.getAttributeId());

		if(null!= currentAttr){
			//Setting the old values before validation
			editAttributeValidator.setOldName(currentAttr.getAttributeName());
			editAttributeValidator.setOldValue(currentAttr.getValue());
			editAttributeValidator.validate(attribute, errors);
		} else {
			model.addAttribute("message","Attribute doesnt exist. Someone might have deleted the Attribute.");
			return "error";
		}

		if(errors.hasFieldErrors()){
			log.debug("Attribute Create form has errors");
			model.addAttribute("errors",errors.getFieldErrors());
			model.addAttribute("attribute",attribute);
			view = "edit_attribute_definition";
			return view;
		}
		List<AttributeDto> attrList=new ArrayList<AttributeDto>();
		attrList.add(attribute);
		boolean result=attributeAdminProcessor.updateAttribute(attrList);
		if(result){
			log.info("Successfully edited attribute");
			model.addAttribute("message", "Attribute Saved Successfully");
			model.addAttribute("attribute",attribute);
			view="view_attribute_definition";
		} else {
			log.info("Some error while editing attribute:"+attribute);
			model.addAttribute("error", "Attribute Could not be saved Successfully");
			model.addAttribute("attribute",attribute);
			view="edit_attribute_definition";
		}
		return view;
	}

	@RequestMapping(value="delete/{attributeId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String deleteAttribute(@PathVariable String attributeId,Model model) throws TechnicalException{
		log.debug("Entered delete attribute controller");
		String view = null;

		AttributeDto attributeDto=attributeAdminProcessor.fetchAttribute(attributeId);
		if(null==attributeDto){
			model.addAttribute("message","Attribute doesnt exist. Please check the Attribute Id in the URL");
			return "error";
		}

		//Check whether it is refrenced in subrule mapping
		List<String> subrulesReferred=new ArrayList<String>();
		subrulesReferred=attributeAdminProcessor.getSubruleAttrRef(attributeId);
		if(subrulesReferred.size()>0){
			model.addAttribute("subrulesReferred",subrulesReferred);
			model.addAttribute("error","Operator Could not be deleted Successfully");
		} else {
			//delete attribute
			int i=attributeAdminProcessor.deleteAttribute(attributeId);
			if(i==1){
				model.addAttribute("message","Attribute Deleted Successfully");
			} else {
				model.addAttribute("error","Attribute Could not be deleted Successfully");
			}
		}
		model.addAttribute("attribute",attributeDto);
		view="delete_attribute_status_definition";
		return view;
	}

	@RequestMapping(value="view/{attributeId}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String viewAttribute(@PathVariable String attributeId,Model model) throws TechnicalException{
		String view = null;
		AttributeDto attribute=null;
		attribute=attributeAdminProcessor.fetchAttribute(attributeId);

		if(null == attribute) {
			model.addAttribute("message","Attribute doesnt exist. Please check the Attribute Id in the URL");
			return "error";

		}

		if(null!=attribute){
			model.addAttribute("attribute", attribute);
			view="view_attribute_definition";
		}
		return view;
	}

	@RequestMapping (value="view/all" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String viewAllAttributes(Model model) throws TechnicalException{
		String view=null;
		List<AttributeDto> attributeList = new ArrayList<AttributeDto>();
		attributeList=attributeAdminProcessor.fetchAllAttributes();
		model.addAttribute("attributes",attributeList);
		view="viewall_attribute_definition";
		return view;
	}
	@RequestMapping (value="view/subrule/{subruleid}" , method=RequestMethod.GET)
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String assignAttributesToSubrule(@PathVariable String subruleid , Model model,@ModelAttribute AttributeDto attribute, Errors errors)throws TechnicalException{
		log.info("Entered assignAttributesToSubrule controller");
		SubruleDto subruleDto = subruleProcessor.fetchSubrule(subruleid);

		if(null == subruleDto){
			errors.rejectValue("message","SubRule doesnt exist.Please check the subrule id in the URL");
			return "error";
		}

		List<AttributeDto>  attributeDtos = attributeAdminProcessor.fetchAttributesAssignedToSubrule(subruleid);
		model.addAttribute("attributesAssigned", attributeDtos);

		List<AttributeDto>  allattributeDtos =attributeAdminProcessor.fetchAllAttributes();

		model.addAttribute("subrule", subruleDto);
		model.addAttribute("attributes", allattributeDtos);

		log.info("Exiting assignAttributesToSubrule controller");
		return "assign_attributes_subrule_view";
	}

	@RequestMapping (value="view/subrule/{subruleid}" , method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=TechnicalException.class)
	public String saveAssignAttributesToSubrule(@PathVariable String subruleid , Model model,HttpServletRequest request,@RequestParam(required=false) String[] attributesToAssignFromRequest,@ModelAttribute AttributeDto ar,Errors errors)throws TechnicalException{
		log.debug("Entered saveAssignAttributesToSubrule controller");

		String viewName ="assign_attributes_subrule_view";

		//Check for the subrule in DB
		SubruleDto subruleDto = subruleProcessor.fetchSubrule(subruleid);
		if(null == subruleDto){
			errors.rejectValue("message","SubRule doesnt exist.Please check the subrule id in the URL");
			return "error";
		} else {
			model.addAttribute("subrule", subruleDto);
		}

		//fetch all attributes
		List<AttributeDto>  allattributeDtos =attributeAdminProcessor.fetchAllAttributes();
		model.addAttribute("attributes", allattributeDtos);

		//get the previously assigned attributes
		List<AttributeDto>  attributesAlreadyAssigned = attributeAdminProcessor.fetchAttributesAssignedToSubrule(subruleid);

		//list to have the IDs to be unassigned
		List<String> attributeIdsUnassigned=new ArrayList<String>();
		//list to have the IDs to be assigned
		List<String> attributesIdsAssigned=new ArrayList<String>();

		if(null == attributesToAssignFromRequest) attributesToAssignFromRequest=new String[0];
		//get the IDs to be assigned
		for(String toAss:attributesToAssignFromRequest){
			boolean isAssigned=true;
			for(AttributeDto temp:attributesAlreadyAssigned){
				if(toAss.equals(temp.getAttributeId())){
					isAssigned=false;
					break;
				}
			}
			if(isAssigned) attributesIdsAssigned.add(toAss);
		}

		//get the IDs to be unassigned
		for(AttributeDto temp:attributesAlreadyAssigned){
			boolean isNotAssigned=true;
			for(String toAss:attributesToAssignFromRequest){
				if(toAss.equals(temp.getAttributeId())){
					isNotAssigned=false;
					break;
				}
			}
			if(isNotAssigned) attributeIdsUnassigned.add(temp.getAttributeId());
		}

		log.debug("Assigned ids: "+attributesIdsAssigned);
		log.debug("UN Assigned ids: "+attributeIdsUnassigned);


		//check subrule_logic if the attribute is referenced there
		List<String> attributesUsedInLogic=new ArrayList<String>();
		// if some unassignment is done only then check logic table
		if(attributeIdsUnassigned.size()>0){
			log.debug("Checking subrule logic table");
			attributesUsedInLogic=attributeAdminProcessor.attributesUsedInSubruleLogic(attributeIdsUnassigned, subruleid);
		}
		if(attributesUsedInLogic.size()>0){ //Some attributes are referenced in logic
			log.debug("Some attributes referenced in subrule logic "+attributesUsedInLogic.toString());
			errors.rejectValue("attributeName", "attribute.still.referenced", "Attributes are still referenced");
			model.addAttribute("attributesReferred",attributesUsedInLogic);
			model.addAttribute("attributesAssigned", attributesAlreadyAssigned);
			return viewName;
		}

		/**
		 * save the new list
		 */

		log.debug("Adding attributes to the DB: "+attributesIdsAssigned.toString());
		attributeAdminProcessor.saveAssignAttributesToSubrule(subruleid,attributesIdsAssigned,attributeIdsUnassigned);
		model.addAttribute("message","Successfully Saved");

		//get the new list of attributes assigned
		attributesAlreadyAssigned=attributeAdminProcessor.fetchAttributesAssignedToSubrule(subruleid);
		model.addAttribute("attributesAssigned", attributesAlreadyAssigned);

		log.debug("Exiting saveAssignAttributesToSubrule controller");
		return viewName;
	}
}
