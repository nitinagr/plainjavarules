package org.pjr.rulesengine.dbmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Subrule {
	//Used for inserting and updating
	private String id;
	private String name;
	private String description;
	private boolean defaultValue;
	private boolean active;
	private String modelId;

	//Only for fetching
	private List<SubruleLogic> logic = new ArrayList<SubruleLogic>();


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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SubruleLogic> getLogic() {
		return logic;
	}

	public void setLogic(List<SubruleLogic> logic) {
		this.logic = logic;
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Subrule [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", defaultValue=");
		builder.append(defaultValue);
		builder.append(", active=");
		builder.append(active);
		builder.append(", logic=");
		builder.append(logic);
		builder.append("]");
		return builder.toString();
	}



	public String toMvelExpression(){
		StringBuffer buff = new StringBuffer();
		 buff.append(" ");
		if (this.isActive()) {

			if(null != this.getLogic()){
				Iterator<SubruleLogic> iterator = this.getLogic().iterator();

				while(iterator.hasNext()){
					SubruleLogic srl  = iterator.next();
					if( null != srl){
						if (null != srl.getAttribute()) {
							buff.append(srl.getAttribute().getValue());
							if(iterator.hasNext()) buff.append(" ");
						} else {
							buff.append(srl.getOperator().getValue());
							if(iterator.hasNext()) buff.append(" ");
						}
					}
				}
			}
		} else {
			buff.append(this.isDefaultValue());
		}
		 buff.append(" ");
		return buff.toString();
	}

}
