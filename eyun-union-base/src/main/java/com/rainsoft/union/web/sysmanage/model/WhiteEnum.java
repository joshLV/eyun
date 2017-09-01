package com.rainsoft.union.web.sysmanage.model;

/**
 * Created by qianna on 2016/11/24.
 */
public enum  WhiteEnum {

    //包括新增，修改，删除
    ADD_FAIL("DB_ADD","-1","操作失败"),
    DB_PARAM_INVALID("DB_ADD","-2","必须字段为空"),
    IDCARD_EXISTS("DB_ADD","-3","数据已经存在，当前场所下该身份证号已经添加过了)"),
    PLACE_DEVICE_2_MAX("DB_ADD","-4","场所免认证设备数已达上限，不可以超过10个"),
    MOBILE_DEVICE_2_MAX("DB_ADD","-5","场所手机号免认证设备数已达上限，不可以超过3个"),
    //包括新增，修改，删除
    AUTH_ADD_SUCC("AUTH","100","保存成功"),
    AUTH_SYS_EXCEPTION("AUTH","110","系统异常"),
    AUTH_PARAM_INVALID("AUTH","113","传给认证的参数必须项为空"),
    ;
    private String type; //类型，DB_ADD,AUTH
    private String code;
    private String des;
    private WhiteEnum(String type,String code ,String des){
        this.type = type;
        this.code = code;
        this.des = des;
    }

    public static String getDesByTypeCode(String type ,String code){
        String des = "";
        for(WhiteEnum white : WhiteEnum.values()){
            if(white.getType().equals(type)
                    && white.getCode().equals(code)){
                des = white.getDes();
                break;
            }
        }
        return des;
    }


    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
