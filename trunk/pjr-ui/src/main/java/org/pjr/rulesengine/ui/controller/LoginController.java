package org.pjr.rulesengine.ui.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.pjr.rulesengine.ui.processor.login.LoginProcessor;

/**
 * The Class LoginController.
 *
 * @author Sudhakar
 */
@Controller
@SuppressWarnings("unused")
public class LoginController {

	/** The Constant log. */

	private static final Log log = LogFactory.getLog(LoginController.class);

	/** The Constant IS_AUTHENTICATED. */
	public static final String IS_AUTHENTICATED="authenticated";




	/** The login processor. */
	@Autowired
	private LoginProcessor loginProcessor;

	/*

	@RequestMapping(value="/login" ,method= {RequestMethod.POST } )
	public String login(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model ,BindingResult result){
		String view = "login";
		HttpSession session =  request.getSession(true);

		log.debug("Login POST "+  session.getAttribute(IS_AUTHENTICATED));


			loginValidator.validate(model, result);

			log.debug("Has Errors "+  result.hasErrors());
		    if(result.hasErrors()){

		    	view = "login";

		    }

		    else if((null != session.getAttribute(IS_AUTHENTICATED)) || loginProcessor.validateLogin(login)){
		    	session.setAttribute(IS_AUTHENTICATED, IS_AUTHENTICATED);
				view = "redirect:/rule/view/all";
		    }


		   return view;

	}
	*/


	/**
	 * Loginerror.
	 *
	 * @param model the model
	 * @return the string
	 * @author  Sudhakar (Infosys)
	 */
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {

		model.addAttribute("error", "true");
		return "login";

	}

	/**
	 * Logout.
	 *
	 * @param model the model
	 * @return the string
	 * @author  Sudhakar (Infosys)
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {

		return "login";

	}


	/**
	 * Login_get.
	 *
	 * @param model the model
	 * @return the string
	 * @author  Sudhakar (Infosys)
	 */
	@RequestMapping(value="/login" ,method= {RequestMethod.GET} )
	public String login_get(ModelMap model){
		String view = "login";
		return view;
	}


}
