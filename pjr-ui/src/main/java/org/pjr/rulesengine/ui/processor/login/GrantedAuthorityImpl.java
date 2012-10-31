package org.pjr.rulesengine.ui.processor.login;

import org.springframework.security.core.GrantedAuthority;


/**
 * The Class GrantedAuthorityImpl.
 *
 * @author Sudhakar
 */
public class GrantedAuthorityImpl implements GrantedAuthority{

	/**
	 * 
	 */
	private static final long serialVersionUID = 646877808549489702L;
	/** The authority. */
	public String authority;

	/**
	 * Instantiates a new granted authority impl.
	 *
	 * @param auth the auth
	 * @author  Sudhakar (Infosys)
	 */
	public GrantedAuthorityImpl(String auth){
		this.authority = auth;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority() {

		return authority;
	}

}
