package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;

public class PubArea implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 406804358015021938L;

    private String areaId;
    
    private String nodePath;
    
    private String treeLevel;
    
    private String areaName;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

    public String getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(String treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    

}
