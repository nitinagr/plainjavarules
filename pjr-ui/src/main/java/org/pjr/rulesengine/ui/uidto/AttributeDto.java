/**
 *
 */
package org.pjr.rulesengine.ui.uidto;

/**
 * @author Anubhab(Infosys)
 *
 */
public class AttributeDto {
	private String attributeId;
	private String attributeName;
	private String value;
	private String modelId;


	public String getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}
	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}
	/**
	 * @param attributeName the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AttributeDto [attributeId=");
		builder.append(attributeId);
		builder.append(", attributeName=");
		builder.append(attributeName);
		builder.append(", value=");
		builder.append(value);
		builder.append(", modelId=");
		builder.append(modelId);
		builder.append("]");
		return builder.toString();
	}

}
