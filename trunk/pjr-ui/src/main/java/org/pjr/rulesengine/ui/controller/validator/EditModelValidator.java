/**
 * @author Anubhab
 * Created: Jan 7, 2013
 */
package org.pjr.rulesengine.ui.controller.validator;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.daos.ModelClassDao;
import org.pjr.rulesengine.dbmodel.Model;
import org.pjr.rulesengine.ui.uidto.ModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * The EditModelValidator.
 *
 * @author Anubhab(Infosys)
 *
 */
@Component("editModelValidator")
public class EditModelValidator implements Validator {
	/**
	 * The log
	 */
	private static final Log log = LogFactory.getLog(EditModelValidator.class);
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ModelClassDao modelDao;
	
	private String oldName;

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ModelDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		log.debug("Entered Model Validator");
		ModelDto dto=(ModelDto)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"model_class_name","login.modelName.empty",messageSource.getMessage("login.modelName.empty",null,new Locale("en")));

		try {
			if (!errors.hasFieldErrors("model_class_name") && !oldName.equals(dto.getModel_class_name())) {
				log.debug("Checking for Model class named:"+dto.getModel_class_name());
				Model md=modelDao.isModelNameAlreadyExists(dto.getModel_class_name());
				if(null!=md){
					log.info("Model Name:["+dto.getModel_class_name()+"] already exists.");
					errors.rejectValue("model_class_name","login.model.name.alreadyexists",messageSource.getMessage("login.model.name.alreadyexists",null,new Locale("en")));
				}
			}
		} catch (Exception e) {
			log.error("",e);
		}
		
		log.debug("Exiting Model Validator");
	}
	
}
