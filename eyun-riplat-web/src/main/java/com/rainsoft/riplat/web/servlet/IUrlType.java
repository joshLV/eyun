package com.rainsoft.riplat.web.servlet;

/**
 * 接口地址类型
 * Created by qianna on 2016/9/7.
 */
public enum IUrlType {

    //MethodName("url"),//范例/Isec_case/saveElectronicsNotes.do
    saveElectronicsNotes("/Isec_case/saveElectronicsNotes.do"),
    saveAppCollectionAp("/Isec_dailymgr/saveAppCollectionAp.do"),
    getCollectionApList("/Isec_dailymgr/getCollectionApList.do"),
    searchAlertList("/Isec_case/searchAlertList.do"),
    searchAlertHistoryList("/Isec_case/searchAlertHistoryList.do"),
    searchAlertDetail("/Isec_case/searchAlertDetail.do"),
    updateAlertStatus("/Isec_case/updateAlertStatus.do"),
    searchServiceInfoByUserType("/Isec_placeinfo/searchServiceInfoByUserType.do"),
    phoneLoginServlet("/Isec_reg/phoneLoginServlet.do");
    private String url;
    private IUrlType(String url){
        this.url = url;
    }

    public static String getUrlByMethod(String method){
        String tempUrl = "";
        for(IUrlType urlType : IUrlType.values()){
            if(urlType.name().equals(method)){
                tempUrl = urlType.getUrl();
                break;
            }
        }
        return tempUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
