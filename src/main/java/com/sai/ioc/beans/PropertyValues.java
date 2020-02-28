package com.sai.ioc.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

    public PropertyValues() {
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void addPropertyValue(PropertyValue propertyValue){
        if (!propertyValues.contains(propertyValue))
        {
            this.propertyValues.add(propertyValue);
        }
    }
}
