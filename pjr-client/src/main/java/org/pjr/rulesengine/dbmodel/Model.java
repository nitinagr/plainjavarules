package org.pjr.rulesengine.dbmodel;


public class Model {

    private String model_class_name;
    private String model_id;

    public String getModel_class_name() {
        return this.model_class_name;
    }

    public void setModel_class_name(String model_class_name) {
        this.model_class_name = model_class_name;
    }

    public String getModel_id() {
        return this.model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Model [model_class_name=");
		builder.append(model_class_name);
		builder.append(", model_id=");
		builder.append(model_id);
		builder.append("]");
		return builder.toString();
	}

}
