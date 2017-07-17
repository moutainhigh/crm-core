package com.cafe.crm.models.property;

import java.util.List;

// wrapper for send properties to view
public class PropertyWrapper {

    private List<Property> properties;

    public PropertyWrapper() {
    }

    public PropertyWrapper(List<Property> properties) {
        this.properties = properties;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
