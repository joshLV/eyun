package com.rainsoft.union.web.sysmanage.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 认证素材资料
 *
 * @author 13646223842@163.com
 * @since 1.0.0 2015/11/30.
 */
public class Material extends PersistenceCommon {

    private String materialName;        // 素材名称
    private Integer userId;             // 拥有者ID
    private String materialType;        // 素材类别
    private String materialTypeName;    // 素材类别名称
    private String materialUrl;         // 原始素材URL
    private String materialSamllUrl;    // 素材略缩图URL
    private String webUrl_PC;           // 重定向（PC）
    private String webUrl_M;            // 重定向（移动设备）

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialTypeName() {
        return materialTypeName;
    }

    public void setMaterialTypeName(String materialTypeName) {
        this.materialTypeName = materialTypeName;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    public String getMaterialSamllUrl() {
        return materialSamllUrl;
    }

    public void setMaterialSamllUrl(String materialSamllUrl) {
        this.materialSamllUrl = materialSamllUrl;
    }

    public String getWebUrl_PC() {
        return webUrl_PC;
    }

    public void setWebUrl_PC(String webUrl_PC) {
        this.webUrl_PC = webUrl_PC;
    }

    public String getWebUrl_M() {
        return webUrl_M;
    }

    public void setWebUrl_M(String webUrl_M) {
        this.webUrl_M = webUrl_M;
    }

}
