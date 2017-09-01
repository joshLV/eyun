package com.rainsoft.base.common.model;

import com.google.common.base.CaseFormat;

public class ConditionFilter {

    public static final Operation DEFAULT_OPERATION = Operation.EQ;// 字段操作符 如：=
    
    public static final FieldType DEFAULT_PROPERT_YTYPE = FieldType.S;//字段类型如：String

    private String property;//字段名字

    private Object value;//字段值

    private Operation operation = DEFAULT_OPERATION;//字段判断条件

    private FieldType fieldType = DEFAULT_PROPERT_YTYPE;//字段的数据类型

    public ConditionFilter() {
    }

    public String getProperty() {
        return property;
    }

    public String getPropertyStr() {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, property);
    }

    public void setProperty(String property) {
        this.property = property.trim();
    }

    public Object getValue() {
        return value;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

	public void setValue(Object value) {
        this.value = value;
    }

    public String getValueStr() {//选择模糊查询的方式
        String valueString = "";
        switch (this.fieldType) {
        case D:
            valueString = value.toString();
            break;
        default:
            valueString = value == null ? null : value.toString();
        }
        switch (this.operation) {
        case ALIKE:
            valueString = "%" + value.toString().trim() + "%";
            break;
        case SLIKE:
            valueString = value.toString().trim() + "%";
            break;
        case ELIKE:
            valueString = "%" + value.toString().trim();
            break;
        default:
        }
        return valueString;
    }

    public boolean isIgnoreCase() {
        if (operation == Operation.ALIKE) {
            return true;
        }
        if (operation == Operation.ELIKE) {
            return true;
        }
        if (operation == Operation.SLIKE) {
            return true;
        }
        if (operation == Operation.IEQ) {
            return true;
        }
        return false;
    }
    

}
