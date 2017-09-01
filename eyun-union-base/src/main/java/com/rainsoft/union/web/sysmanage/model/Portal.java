package com.rainsoft.union.web.sysmanage.model;

import com.rainsoft.base.common.model.PersistenceCommon;
import com.rainsoft.base.common.utils.StringUtil;

/**
 * 认证模版实体
 * Created with IntelliJ IDEA.
 * User: 13646223842@163.com
 * Date: 2015/06/12
 * Time: 17:24
 */
public class Portal extends PersistenceCommon {
    /**
     * 原始模版ID*
     */
    private Integer rootPortallModelId;
    /**
     * 模板名称*
     */
    private String portalModelName;
    /**
     * 场所自定义标题*
     */
    private String title;
    /**
     * 背景图地址*
     */
    private String backGroudUrl;
    private String backGroudID;
    /**
     * logo图片地址*
     */
    private String logoUrl;
    private Integer logoID;
    /**
     * PC版和手机版重定向地址都存放在该字段中*
     */
    private String successToUrl;
    /**
     * PC重定向地址*
     */
    private String pcToUrl;
    /**
     * 手机重定向地址*
     */
    private String mobileToUrl;
    /**
     * 日常活动通知*
     */
    private String messages;
    /**
     * 是否默认模版 有且只有一个是默认模版  Y：是 N：否*
     */
    private String isDefault;
    private String isDefaultName;
    /**
     * 场所编号*
     */
    private String placeCode;
    private Integer placeId;
    /**
     * 场所名称*
     */
    private String placeName;
    /**
     * 会员ID*
     */
    private Integer userId;

    private Integer position1MaterialID;
    private String position1Url;
    private Integer position2MaterialID;
    private String position2Url;
    private Integer position3MaterialID;
    private String position3Url;
    private Integer position4MaterialID;
    private String position4Url;
    private Integer position5MaterialID;
    private String position5Url;
    private Integer position6MaterialID;
    private String position6Url;
    private Integer position7MaterialID;
    private String position7Url;
    private Integer position8MaterialID;
    private String position8Url;
    private Integer position9MaterialID;
    private String position9Url;
    private String mobileMaterial1ID; // 手机广告位1 素材ID
    private String mobileMaterial1Url; // 手机广告位1 素材url
    private String mobileMaterial2ID; // 手机广告位2 素材ID
    private String mobileMaterial2Url; // 手机广告位2 素材url
    /*新增的两个广告位置*/
    private String mobileMaterial3ID; // 手机广告位3 素材ID
    private String mobileMaterial3Url; // 手机广告位3 素材url
    private String mobileMaterial4ID; // 手机广告位4 素材ID
    private String mobileMaterial4Url; // 手机广告位4 素材url


    // 编辑页面要显示的字段
    private String position1MaterialName;
    private String position2MaterialName;
    private String position3MaterialName;
    private String position4MaterialName;
    private String position5MaterialName;
    private String position6MaterialName;
    private String position7MaterialName;
    private String mobileMaterial1Name;
    private String mobileMaterial2Name;
    /*新增两个素材*/
    private String mobileMaterial3Name;
    private String mobileMaterial4Name;

    private String backGroudName;
    private String logoName;

    public String getBackGroudName() {
        return backGroudName;
    }

    public void setBackGroudName(String backGroudName) {
        this.backGroudName = backGroudName;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getBackGroudID() {
        return backGroudID;
    }

    public void setBackGroudID(String backGroudID) {
        this.backGroudID = backGroudID;
    }

    public Integer getLogoID() {
        return logoID;
    }

    public void setLogoID(Integer logoID) {
        this.logoID = logoID;
    }

    public String getPosition1MaterialName() {
        return position1MaterialName;
    }

    public void setPosition1MaterialName(String position1MaterialName) {
        this.position1MaterialName = position1MaterialName;
    }

    public String getPosition2MaterialName() {
        return position2MaterialName;
    }

    public void setPosition2MaterialName(String position2MaterialName) {
        this.position2MaterialName = position2MaterialName;
    }

    public String getPosition3MaterialName() {
        return position3MaterialName;
    }

    public void setPosition3MaterialName(String position3MaterialName) {
        this.position3MaterialName = position3MaterialName;
    }

    public String getPosition4MaterialName() {
        return position4MaterialName;
    }

    public void setPosition4MaterialName(String position4MaterialName) {
        this.position4MaterialName = position4MaterialName;
    }

    public String getPosition5MaterialName() {
        return position5MaterialName;
    }

    public void setPosition5MaterialName(String position5MaterialName) {
        this.position5MaterialName = position5MaterialName;
    }

    public String getPosition6MaterialName() {
        return position6MaterialName;
    }

    public void setPosition6MaterialName(String position6MaterialName) {
        this.position6MaterialName = position6MaterialName;
    }

    public String getPosition7MaterialName() {
        return position7MaterialName;
    }

    public void setPosition7MaterialName(String position7MaterialName) {
        this.position7MaterialName = position7MaterialName;
    }

    public String getMobileMaterial1Name() {
        return mobileMaterial1Name;
    }

    public void setMobileMaterial1Name(String mobileMaterial1Name) {
        this.mobileMaterial1Name = mobileMaterial1Name;
    }

    public String getMobileMaterial2Name() {
        return mobileMaterial2Name;
    }

    public void setMobileMaterial2Name(String mobileMaterial2Name) {
        this.mobileMaterial2Name = mobileMaterial2Name;
    }

    public String getMobileMaterial3Name() {
        return mobileMaterial3Name;
    }

    public void setMobileMaterial3Name(String mobileMaterial3Name) {
        this.mobileMaterial3Name = mobileMaterial3Name;
    }

    public String getMobileMaterial4Name() {
        return mobileMaterial4Name;
    }

    public void setMobileMaterial4Name(String mobileMaterial4Name) {
        this.mobileMaterial4Name = mobileMaterial4Name;
    }

    public Integer getPosition1MaterialID() {
        return position1MaterialID;
    }

    public void setPosition1MaterialID(Integer position1MaterialID) {
        this.position1MaterialID = position1MaterialID;
    }

    public String getPosition1Url() {
        return position1Url;
    }

    public void setPosition1Url(String position1Url) {
        this.position1Url = position1Url;
    }

    public Integer getPosition2MaterialID() {
        return position2MaterialID;
    }

    public void setPosition2MaterialID(Integer position2MaterialID) {
        this.position2MaterialID = position2MaterialID;
    }

    public String getPosition2Url() {
        return position2Url;
    }

    public void setPosition2Url(String position2Url) {
        this.position2Url = position2Url;
    }

    public Integer getPosition3MaterialID() {
        return position3MaterialID;
    }

    public void setPosition3MaterialID(Integer position3MaterialID) {
        this.position3MaterialID = position3MaterialID;
    }

    public String getPosition3Url() {
        return position3Url;
    }

    public void setPosition3Url(String position3Url) {
        this.position3Url = position3Url;
    }

    public Integer getPosition4MaterialID() {
        return position4MaterialID;
    }

    public void setPosition4MaterialID(Integer position4MaterialID) {
        this.position4MaterialID = position4MaterialID;
    }

    public String getPosition4Url() {
        return position4Url;
    }

    public void setPosition4Url(String position4Url) {
        this.position4Url = position4Url;
    }

    public Integer getPosition5MaterialID() {
        return position5MaterialID;
    }

    public void setPosition5MaterialID(Integer position5MaterialID) {
        this.position5MaterialID = position5MaterialID;
    }

    public String getPosition5Url() {
        return position5Url;
    }

    public void setPosition5Url(String position5Url) {
        this.position5Url = position5Url;
    }

    public Integer getPosition6MaterialID() {
        return position6MaterialID;
    }

    public void setPosition6MaterialID(Integer position6MaterialID) {
        this.position6MaterialID = position6MaterialID;
    }

    public String getPosition6Url() {
        return position6Url;
    }

    public void setPosition6Url(String position6Url) {
        this.position6Url = position6Url;
    }

    public Integer getPosition7MaterialID() {
        return position7MaterialID;
    }

    public void setPosition7MaterialID(Integer position7MaterialID) {
        this.position7MaterialID = position7MaterialID;
    }

    public String getPosition7Url() {
        return position7Url;
    }

    public void setPosition7Url(String position7Url) {
        this.position7Url = position7Url;
    }

    public Integer getPosition8MaterialID() {
        return position8MaterialID;
    }

    public void setPosition8MaterialID(Integer position8MaterialID) {
        this.position8MaterialID = position8MaterialID;
    }

    public String getPosition8Url() {
        return position8Url;
    }

    public void setPosition8Url(String position8Url) {
        this.position8Url = position8Url;
    }

    public Integer getPosition9MaterialID() {
        return position9MaterialID;
    }

    public void setPosition9MaterialID(Integer position9MaterialID) {
        this.position9MaterialID = position9MaterialID;
    }

    public String getPosition9Url() {
        return position9Url;
    }

    public void setPosition9Url(String position9Url) {
        this.position9Url = position9Url;
    }

    public String getMobileMaterial1ID() {
        return mobileMaterial1ID;
    }

    public void setMobileMaterial1ID(String mobileMaterial1ID) {
        this.mobileMaterial1ID = mobileMaterial1ID;
    }

    public String getMobileMaterial1Url() {
        return mobileMaterial1Url;
    }

    public void setMobileMaterial1Url(String mobileMaterial1Url) {
        this.mobileMaterial1Url = mobileMaterial1Url;
    }

    public String getMobileMaterial2ID() {
        return mobileMaterial2ID;
    }

    public void setMobileMaterial2ID(String mobileMaterial2ID) {
        this.mobileMaterial2ID = mobileMaterial2ID;
    }

    public String getMobileMaterial2Url() {
        return mobileMaterial2Url;
    }

    public void setMobileMaterial2Url(String mobileMaterial2Url) {
        this.mobileMaterial2Url = mobileMaterial2Url;
    }

    public String getMobileMaterial3ID() {
        return mobileMaterial3ID;
    }

    public void setMobileMaterial3ID(String mobileMaterial3ID) {
        this.mobileMaterial3ID = mobileMaterial3ID;
    }

    public String getMobileMaterial3Url() {
        return mobileMaterial3Url;
    }

    public void setMobileMaterial3Url(String mobileMaterial3Url) {
        this.mobileMaterial3Url = mobileMaterial3Url;
    }

    public String getMobileMaterial4ID() {
        return mobileMaterial4ID;
    }

    public void setMobileMaterial4ID(String mobileMaterial4ID) {
        this.mobileMaterial4ID = mobileMaterial4ID;
    }

    public String getMobileMaterial4Url() {
        return mobileMaterial4Url;
    }

    public void setMobileMaterial4Url(String mobileMaterial4Url) {
        this.mobileMaterial4Url = mobileMaterial4Url;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRootPortallModelId() {
        return rootPortallModelId;
    }

    public void setRootPortallModelId(Integer rootPortallModelId) {
        this.rootPortallModelId = rootPortallModelId;
    }

    public String getPortalModelName() {
        return portalModelName;
    }

    public void setPortalModelName(String portalModelName) {
        this.portalModelName = portalModelName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackGroudUrl() {
        return backGroudUrl;
    }

    public void setBackGroudUrl(String backGroudUrl) {
        this.backGroudUrl = backGroudUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSuccessToUrl() {
        return successToUrl;
    }

    public void setSuccessToUrl(String successToUrl) {
        this.successToUrl = successToUrl;
    }

    public String getPcToUrl() {
        return pcToUrl;
    }

    public void setPcToUrl(String pcToUrl) {
        this.pcToUrl = pcToUrl;
    }

    public String getMobileToUrl() {
        return mobileToUrl;
    }

    public void setMobileToUrl(String mobileToUrl) {
        this.mobileToUrl = mobileToUrl;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getIsDefaultName() {
        if (!StringUtil.isEmpty(this.isDefault)) {
            if (this.isDefault.equals("Y")) {
                return "是";
            } else if (this.isDefault.equals("N")) {
                return "否";
            }
        }
        return isDefaultName;
    }

    public void setIsDefaultName(String isDefaultName) {
        this.isDefaultName = isDefaultName;
    }
}
