package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;

import com.rainsoft.base.common.model.IdEntity;

public class Unit extends IdEntity implements Serializable {

    private static final long serialVersionUID = 2105156482179002569L;

    private String code;
    
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
