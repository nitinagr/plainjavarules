/**
 *
 */
package org.pjr.rulesengine.ui.processor.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.pjr.rulesengine.ui.uidto.OperatorDto;

/**
 * @author Anubhab(Infosys)
 *
 */
public class OperatorAdminValidator {
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(OperatorAdminValidator.class);

	public static boolean validateInput(OperatorDto operatorDto){
		log.debug("Entering validateInput method");
		boolean result=true;
		if(null==operatorDto || operatorDto.getOperatorName().trim().isEmpty() || operatorDto.getValue().trim().isEmpty()){
				result=false;
		}
		return result;
	}
}
