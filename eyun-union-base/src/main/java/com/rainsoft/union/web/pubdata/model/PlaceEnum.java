package com.rainsoft.union.web.pubdata.model;

/**
 * Created by qianna on 2016/11/28.
 */
public enum PlaceEnum {
    SMSFEE_PLACE("S","场所付费"),//由场所付费
    SMSFEE_COMPANY("Y","云辰付费"),//由公司付费
    ;
    private String code;
    private String des;
    private PlaceEnum(String code,String des){
        this.code = code;
        this.des = des;
    }

    public static String getDesByCode(String code){
        for(PlaceEnum e : PlaceEnum.values()){
            if(e.getCode().equals(code)){
                return e.getDes();
            }
        }
        return "";
    }

    public String getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
