package com.cafe.crm.models.property;

import java.util.List;

public class PropertyWrapper {

	private List<Property> properties;

	public PropertyWrapper(List<Property> properties) {
		this.properties = properties;
	}

	public PropertyWrapper() {
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
}
