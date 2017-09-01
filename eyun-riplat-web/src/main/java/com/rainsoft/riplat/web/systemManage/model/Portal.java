package com.rainsoft.riplat.web.systemManage.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * Created with IntelliJ IDEA.
 * User: 13646223842@163.com
 * Date: 2015/12/11
 * Time: 17:24
 */
public class Portal extends PersistenceCommon {
    /**原始模版ID**/
    private Integer rootPortallModelId;
    /**模板名称**/
    private String portalModelName;
    /**场所自定义标题**/
    private String title;
    /**背景图地址**/
    private String backgroundUrl;
    /**logo图片地址**/
    private String logoUrl;
    /**PC版和手机版重定向地址都存放在该字段中**/
    private String successToUrl;
    /**PC重定向地址**/
    private String pcToUrl;
    /**手机重定向地址**/
    private String mobileToUrl;
    /**日常活动通知**/
    private String messages;
    /**是否默认模版 有且只有一个是默认模版  Y：是 N：否**/
    private String isDefault;
    /**场所编号**/
    private String placeCode;
    /**场所名称**/
    private String placeName;
    /**PC轮播广告数量**/
    private Integer pl_ad_N;
    /**PC轮播广告控制码**/
    private String pl_ad_Ctrl;
    /**PC轮播广告数量**/
    private Integer pd_ad_N;
    /**PC轮播广告控制码**/
    private String pd_ad_Ctrl;
    /**PC轮播广告数量**/
    private Integer ml_ad_N;
    /**PC轮播广告控制码**/
    private String ml_ad_Ctrl;

    /**会员ID**/
    private Integer userId;
    /**模版IDs 可以多个**/
    private String portalId;

    /**场所设备关联id**/
    private String placeDeviceId;

    private String serialnum ;

        private String[] arr;

    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }

    public String getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(String serialnum) {
        this.serialnum = serialnum;
    }

    public String getPlaceDeviceId() {
        return placeDeviceId;
    }

    public void setPlaceDeviceId(String placeDeviceId) {
        this.placeDeviceId = placeDeviceId;
    }

    public String getMl_ad_Ctrl() {
        return ml_ad_Ctrl;
    }

    public void setMl_ad_Ctrl(String ml_ad_Ctrl) {
        this.ml_ad_Ctrl = ml_ad_Ctrl  == null ? null : ml_ad_Ctrl.trim();
    }

    public Integer getMl_ad_N() {
        return ml_ad_N;
    }

    public void setMl_ad_N(Integer ml_ad_N) {
        this.ml_ad_N = ml_ad_N;
    }

    public String getPd_ad_Ctrl() {
        return pd_ad_Ctrl;
    }

    public void setPd_ad_Ctrl(String pd_ad_Ctrl) {
        this.pd_ad_Ctrl = pd_ad_Ctrl  == null ? null : pd_ad_Ctrl.trim();
    }

    public Integer getPd_ad_N() {
        return pd_ad_N;
    }

    public void setPd_ad_N(Integer pd_ad_N) {
        this.pd_ad_N = pd_ad_N;
    }

    public String getPl_ad_Ctrl() {
        return pl_ad_Ctrl  == null ? null : pl_ad_Ctrl.trim();
    }

    public void setPl_ad_Ctrl(String pl_ad_Ctrl) {
        this.pl_ad_Ctrl = pl_ad_Ctrl;
    }

    public Integer getPl_ad_N() {
        return pl_ad_N;
    }

    public void setPl_ad_N(Integer pl_ad_N) {
        this.pl_ad_N = pl_ad_N;
    }

    public String getPortalId() {
        return portalId;
    }

    public void setPortalId(String portalId) {
        this.portalId = portalId;
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

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
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
}
