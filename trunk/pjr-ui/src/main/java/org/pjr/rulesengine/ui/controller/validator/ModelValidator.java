/**
 * @author Anubhab
 * Created: Jan 7, 2013
 */
package org.pjr.rulesengine.ui.controller.validator;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.ui.uidto.ModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * The ModelValidator.
 *
 * @author Anubhab(Infosys)
 *
 */
@Component("modelValidator")
public class ModelValidator implements Validator {
	/**
	 * The log
	 */
	private static final Log log = LogFactory.getLog(ModelValidator.class);

	/** The message source. */
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ModelDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		log.debug("Entered Model Validator");
		ModelDto dto=(ModelDto)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"model_class_name","login.modelName.empty",messageSource.getMessage("login.modelName.empty",null,new Locale("en")));
		log.debug("Exiting Model Validator");
	}
}
