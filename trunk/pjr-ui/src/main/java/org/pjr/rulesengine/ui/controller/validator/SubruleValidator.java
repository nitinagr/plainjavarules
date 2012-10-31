/**
 *
 */
package org.pjr.rulesengine.ui.controller.validator;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
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

@Component("subRuleValidator")
public class SubruleValidator implements Validator{

	private static final Log log = LogFactory.getLog(SubruleValidator.class);

	@Autowired
	private SubruleDao subruleDao;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	public static boolean validateInput(SubruleDto subruleDto){
		boolean result=true;
		if(null==subruleDto || subruleDto.getName().trim().isEmpty()){
			result=false;
		}
		return result;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return SubruleDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name","login.subrule.name.empty", messageSource.getMessage("login.subrule.name.empty",null,new Locale("en")));

		SubruleDto subDto = (SubruleDto)target;

		if (StringUtils.isNotBlank(subDto.getName())) {
			try {
				Subrule subDtoFromDb = subruleDao.fetchSubruleByName(subDto.getName());
				if (null != subDtoFromDb)
					errors.rejectValue("name", "login.subrule.name.alreadyexists",
							messageSource.getMessage("login.subrule.name.alreadyexists", null, new Locale("en")));
			} catch (DataLayerException e) {
				e.printStackTrace();
			}
		}

	}
}
