/**
 * @Author Anubhab(Infosys)
 * Created: Sep 12, 2012
 */
package org.pjr.rulesengine.ui.controller.validator;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.pjr.rulesengine.ui.daos.AttributeDao;
import org.pjr.rulesengine.ui.uidto.AttributeDto;

/**
 * @author Anubhab(Infosys)
 *
 */
@Component("editAttributeValidator")
public class EditAttributeValidator implements Validator{
	/**
	 * The log
	 */
	private static final Log log = LogFactory.getLog(AttributeValidator.class);

	@Autowired
	@Qualifier(value="attributeDao")
	private AttributeDao attributeDao;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	private String oldName;
	private String oldValue;

	/**
	 * @return the oldName
	 */
	public String getOldName() {
		return oldName;
	}

	/**
	 * @param oldName the oldName to set
	 */
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	/**
	 * @return the oldValue
	 */
	public String getOldValue() {
		return oldValue;
	}

	/**
	 * @param oldValue the oldValue to set
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return AttributeDto.class.isAssignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		log.info("Entered Attribute Validator");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"attributeName","login.attributeName.empty",messageSource.getMessage("login.attributeName.empty",null,new Locale("en")));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"value","login.value.empty",messageSource.getMessage("login.value.empty",null,new Locale("en")));

		AttributeDto attributeDto=(AttributeDto)target;

		log.debug("Attribute got:"+attributeDto.toString());
		log.debug("errors got:"+errors.toString());

		try {
			if (!errors.hasFieldErrors("attributeName")) {
				if(!oldName.equalsIgnoreCase(attributeDto.getAttributeName())){
					boolean nameExists=false;
					log.debug("Attribute name was not empty");
					nameExists = attributeDao.isAttributeNameExists(attributeDto.getAttributeName());
					if(nameExists) errors.rejectValue("attributeName","login.attribute.name.alreadyexists", messageSource.getMessage("login.attribute.name.alreadyexists",null,new Locale("en")));
				}
			}

			if (!errors.hasFieldErrors("value")) {
				if(!oldValue.equalsIgnoreCase(attributeDto.getValue())){
					boolean valueExists=false;
					log.info("Attribute value was not empty");
					valueExists=attributeDao.isAttributeValueExists(attributeDto.getValue());
					if(valueExists) errors.rejectValue("value","login.attribute.value.alreadyexists", messageSource.getMessage("login.attribute.value.alreadyexists",null,new Locale("en")));
				}
			}
		} catch (Exception e) {
			log.error("",e);
		}
		log.info("Exiting Attribute Validator");
	}
}
