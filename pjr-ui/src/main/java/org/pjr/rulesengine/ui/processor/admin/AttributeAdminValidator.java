/**
 *
 */
package org.pjr.rulesengine.ui.processor.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.pjr.rulesengine.ui.uidto.AttributeDto;

/**
 * @author Anubhab(Infosys)
 *
 */
public class AttributeAdminValidator {
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(AttributeAdminValidator.class);

	public static boolean validateInput(AttributeDto attributeDto){
		log.info("Entering validateInput method");
		boolean result=true;
		if(null==attributeDto || attributeDto.getAttributeName().trim().isEmpty() || attributeDto.getValue().trim().isEmpty()){
			result=false;
	}
		log.info("return value from validateInput:"+result);
		return result;
	}
}
