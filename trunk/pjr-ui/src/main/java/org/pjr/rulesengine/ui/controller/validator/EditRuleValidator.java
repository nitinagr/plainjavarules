package org.pjr.rulesengine.ui.controller.validator;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.daos.RuleDao;
import org.pjr.rulesengine.dbmodel.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.pjr.rulesengine.ui.uidto.RuleDto;

@Component("editRuleValidator")
public class EditRuleValidator implements Validator{

	private static final Log log = LogFactory.getLog(EditRuleValidator.class);

	@Autowired
	private RuleDao ruleDao;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	@Override
	public boolean supports(Class clazz) {
		return RuleDto.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors,RuleDto currentRuleInDB) {
		validate( target,  errors);
		
		RuleDto ruleDto = (RuleDto) target;
		
		String oldRuleName = currentRuleInDB.getRuleName();
		String oldExecutionOrder = currentRuleInDB.getExecutionOrder();
		

		if(null != oldRuleName && !oldRuleName.equalsIgnoreCase(ruleDto.getRuleName())){

			Rule ruleDb = null;
			try {
				ruleDb = ruleDao.fetchRuleByName(ruleDto.getRuleName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(null != ruleDb)  errors.rejectValue( "ruleName","login.rule.name.alreadyexists", messageSource.getMessage("login.rule.name.alreadyexists",null,new Locale("en")));
		}
		
		boolean isNumberValid = true;
		try {
			Integer.parseInt(ruleDto.getExecutionOrder());
		} catch (NumberFormatException e1) {
			isNumberValid = false;
			errors.rejectValue( "executionOrder","login.executionOrder.shouldbe.number", messageSource.getMessage("login.executionOrder.shouldbe.number",null,new Locale("en")));
		}

		if(isNumberValid){
			if(!oldExecutionOrder.equalsIgnoreCase(ruleDto.getExecutionOrder()) ){
				boolean executionOrderExists = false;
				try {
					executionOrderExists = ruleDao.isExecutionOrderExists(Integer.parseInt(ruleDto.getExecutionOrder()),ruleDto.getModelId() );
					log.debug("executionOrder :"+executionOrderExists);
				} catch (DataLayerException e) {
					log.error("", e);
				}

				if(executionOrderExists) errors.rejectValue( "executionOrder","login.rule.executionorder.alreadyexists", messageSource.getMessage("login.rule.executionorder.alreadyexists",null,new Locale("en")));
			}
		}
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ruleName","login.ruleName.empty", messageSource.getMessage("login.ruleName.empty",null,new Locale("en")));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "returnValue","login.returnValue.empty", messageSource.getMessage("login.returnValue.empty",null,new Locale("en")));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "executionOrder","login.executionOrder.empty", messageSource.getMessage("login.executionOrder.empty",null,new Locale("en")));

	}

}
