/**
 * @Author Anubhab(Infosys)
 * Created: Sep 12, 2012
 */
package org.pjr.rulesengine.ui.controller.validator;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.daos.OperatorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.pjr.rulesengine.ui.uidto.OperatorDto;

/**
 * @author Anubhab(Infosys)
 *
 */
@Component("editOperatorValidator")
public class EditOperatorValidator implements Validator{
	/**
	 * The log
	 */
	private static final Log log = LogFactory.getLog(AttributeValidator.class);

	@Autowired
	@Qualifier(value="operatorDao")
	private OperatorDao operatorDao;

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
		return OperatorDto.class.isAssignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		log.debug("Entered Operator Validator");
		OperatorDto operatorDto=(OperatorDto)target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"operatorName","login.operatorName.empty",messageSource.getMessage("login.operatorName.empty",null,new Locale("en")));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"value","login.operator.value.empty",messageSource.getMessage("login.operator.value.empty",null,new Locale("en")));

		try {
			if (!errors.hasFieldErrors("operatorName")) {
				if(!oldName.equalsIgnoreCase(operatorDto.getOperatorName())){
					boolean nameExists=false;
					log.info("Operator name was not empty");
					nameExists = operatorDao.isOperatorNameExists(operatorDto.getOperatorName());
					if(nameExists) errors.rejectValue("operatorName","login.operatorName.alreadyexists", messageSource.getMessage("login.operatorName.alreadyexists",null,new Locale("en")));
				}
			}

			if (!errors.hasFieldErrors("value")) {
				if(!oldValue.equalsIgnoreCase(operatorDto.getValue())){
					boolean valueExists=false;
					log.info("Operator value was not empty");
					valueExists=operatorDao.isOperatorValueExists(operatorDto.getValue());
					if(valueExists) errors.rejectValue("value","login.operator.value.alreadyexists",messageSource.getMessage("login.operator.value.alreadyexists",null,new Locale("en")));
				}
			}
		} catch (Exception e) {
			log.error("",e);
		}
		log.debug("Exiting Operator Validator");
	}
}
