package org.pjr.rulesengine.dbmodel;

import org.springframework.stereotype.Component;

//Administration
@Component
public class Attribute {

	//Insert and Update
	private String id;
	private String name;
	private String value;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Attribute [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}


}
