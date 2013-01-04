package org.pjr.rulesengine.ui.processor.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.dbmodel.RuleEngineUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import org.pjr.rulesengine.ui.daos.GeneralDao;

/**
 * The Class FsmAuthenticationProvider.
 *
 * @author Sudhakar
 */
@Component("plainJavaRulesAuthenticationProvider")
public class PlainJavaRulesAuthenticationProvider implements AuthenticationProvider{

	/** The Constant logger. */
	private static final Log logger = LogFactory.getLog(PlainJavaRulesAuthenticationProvider.class);

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/** The messages. */
	//protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();


	/** The login processor. */
	@Autowired
	private LoginProcessor loginProcessor;

	@Autowired
	private GeneralDao generalDao;

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken)authentication;
		String username = userToken.getName();
		String password = (String) authentication.getCredentials();


		if (!StringUtils.hasLength(username)) {
            throw new BadCredentialsException("Empty Username");
        }

        if (null == password || password.length() == 0) {
            logger.debug("Rejecting empty password for user " + username);
            //throw new BadCredentialsException("Empty Password");
            throw new BadCredentialsException(messageSource.getMessage("login.password.empty",null,new Locale("en")));
        }
        
        boolean isLoginValid = false;

        try {
        	isLoginValid = loginProcessor.validateLogin(username, password);
        	if(!isLoginValid) throw new BadCredentialsException("Username / Password is wrong");
        	
		} catch (Exception e) {
			logger.error("Login Failed ",e);
            logger.debug("Rejecting empty password for user " + username);
            throw new BadCredentialsException("Username / Password is wrong");
		}

		RuleEngineUser pacUser = null;
		try {
			pacUser = generalDao.getPacUser(username);
			logger.debug("pacUser :"+pacUser);
		} catch (TechnicalException e) {
			logger.error("unable to fetch user data from Db",e)	;
		}



		//Construct the User Details only  on success login validation
        RuleEngineUserDetails user = new RuleEngineUserDetails();
        user.setUsername(username);
		user = (RuleEngineUserDetails) populateUserDetails(user,pacUser);

		return createSuccessfulAuthentication(userToken, user);
	}


	/**
	 * Populate user details.
	 *
	 * @param user the user
	 * @return the user details
	 * @author  Sudhakar (Infosys)
	 */
	private UserDetails populateUserDetails(RuleEngineUserDetails user ,RuleEngineUser ruleEngineUser){

		List<String> roles = new ArrayList<String>();

		if(null != ruleEngineUser && ruleEngineUser.isActive()){

			// Populate the roles accordingly 
			if(ruleEngineUser.isAdmin()){
				roles.add("ROLE_USER");
				roles.add("ROLE_ADMIN");
			}else{
				roles.add("ROLE_USER");
			}

		}

		String authString[] = new String[roles.size()];
		roles.toArray(authString);

		List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();

		for(String auth :authString ){
			GrantedAuthorityImpl grantedAuthorityImpl = new GrantedAuthorityImpl(auth);
			grantedAuthorityList.add(grantedAuthorityImpl);
		}

		logger.debug("grantedAuthorityList :"+grantedAuthorityList.size());

		user.setAuthorities(grantedAuthorityList);
		return user;
	}


	/**
	 * Creates the successful authentication.
	 *
	 * @param authentication the authentication
	 * @param user the user
	 * @return the authentication
	 * @author  Sudhakar (Infosys)
	 */
	private Authentication createSuccessfulAuthentication(UsernamePasswordAuthenticationToken authentication,
            UserDetails user) {
        Object password = authentication.getCredentials();
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }


	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
