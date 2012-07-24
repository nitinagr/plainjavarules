package com.rules.testDataModel;

public class User {

	private String userName;
	private Integer age;
	private int allowedAge;
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}


	
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public int getAllowedAge() {
		return allowedAge;
	}
	public void setAllowedAge(int allowedAge) {
		this.allowedAge = allowedAge;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [userName=");
		builder.append(userName);
		builder.append(", age=");
		builder.append(age);
		builder.append(", allowedAge=");
		builder.append(allowedAge);
		builder.append("]");
		return builder.toString();
	}
	
	
}

