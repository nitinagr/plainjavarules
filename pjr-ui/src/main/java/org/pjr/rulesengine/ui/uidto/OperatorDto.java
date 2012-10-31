/**
 *
 */
package org.pjr.rulesengine.ui.uidto;

/**
 * This class is
 * @author Anubhab(Infosys)
 *
 */
public class OperatorDto {
	private String operatorId;
	private String operatorName;
	private String value;



	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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
		builder.append("OperatorDto [operatorId=").append(operatorId).append(", operatorName=").append(operatorName).append(", value=").append(value)
				.append("]");
		return builder.toString();
	}

}
