package com.rainsoft.base.common.model;

/**
 * Created by qianna on 2016/11/30.
 */
public enum AuthMethodEnum {
    synNetTime("/app.do?method=synNetTime"),
    netBarYiyunVip("/app.do?method=netBarYiyunVip"),
    ;

    private String value;
    private AuthMethodEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
