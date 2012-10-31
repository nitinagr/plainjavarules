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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AttributeDto [attributeId=").append(attributeId).append(", attributeName=").append(attributeName).append(", value=")
				.append(value).append("]");
		return builder.toString();
	}

}
