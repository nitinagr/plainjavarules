/**
 * @Author Anubhab(Infosys)
 * Created: Sep 13, 2012
 */
package org.pjr.rulesengine.ui.controller.validator;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.daos.SubruleDao;
import org.pjr.rulesengine.dbmodel.Subrule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.pjr.rulesengine.ui.uidto.SubruleDto;

/**
 * @author Anubhab(Infosys)
 *
 */
@Component("editSubruleValidator")
public class EditSubruleValidator implements Validator{

	private static final Log log = LogFactory.getLog(EditSubruleValidator.class);

	@Autowired
	private SubruleDao subruleDao;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	private String oldName;

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

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return SubruleDto.class.isAssignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name","login.subrule.name.empty", messageSource.getMessage("login.subrule.name.empty",null,new Locale("en")));

		SubruleDto subDto = (SubruleDto)target;

		if (!errors.hasFieldErrors("name")) {
			if(!oldName.equalsIgnoreCase(subDto.getName())){
				try {
					Subrule subDtoFromDb = subruleDao.fetchSubruleByName(subDto.getName());
					if (null != subDtoFromDb)
						errors.rejectValue("name", "login.subrule.name.alreadyexists",messageSource.getMessage("login.subrule.name.alreadyexists", null, new Locale("en")));
				} catch (DataLayerException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
