package com.rainsoft.base.common.model;

import com.google.common.base.CaseFormat;

public class ConditionSort {

    private String property;
    
    /**
     * DESC ASC
     */
    private String direction = "ASC";

    public String getProperty() {
        return property;
    }

    public String getPropertyStr() {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, property);
    }

    public void setProperty(String property) {
        this.property = property.trim();
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction.trim();
    }

}
