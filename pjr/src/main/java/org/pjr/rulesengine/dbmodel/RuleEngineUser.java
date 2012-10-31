package org.pjr.rulesengine.dbmodel;

import java.sql.Date;

public class RuleEngineUser {

	private String userId;
	private boolean active;

	private boolean admin;
	private Date enableDate;
	private Date disableDate;


	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getEnableDate() {
		return enableDate;
	}
	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}
	public Date getDisableDate() {
		return disableDate;
	}
	public void setDisableDate(Date disableDate) {
		this.disableDate = disableDate;
	}


	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleEngineUser [userId=");
		builder.append(userId);
		builder.append(", active=");
		builder.append(active);
		builder.append(", admin=");
		builder.append(admin);
		builder.append(", enableDate=");
		builder.append(enableDate);
		builder.append(", disableDate=");
		builder.append(disableDate);
		builder.append("]");
		return builder.toString();
	}
	


}
