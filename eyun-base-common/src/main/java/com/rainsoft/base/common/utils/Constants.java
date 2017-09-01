package com.rainsoft.base.common.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static int PAGE_ROWS = 10;// 每页显示行数

    public static int MAX_EXPORT_COUNT = 5000;// 最大导出行数

    public static String ROW_LIST = "[10,20,30]";// 每页显示记录数的选择List
    
    public static String DATA_FORMAT_DD = "yyyy-MM-dd";

    public static String DATA_FORMAT_HHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static String DATA_FORMAT_HHMM = "yyyy-MM-dd HH:mm";

    public static String DATA_FORMAT_YYYYMM = "yyyy年MM月";

    public static String DATA_FORMAT_YYYY_MM = "yyyy/MM";

    public static String DATE_FORMAT_YYYY_M = "yyyy-MM";

    public static String DATA_FORMAT_YYYYMMDD = "yyyy年MM月dd日";

    public static String DATA_FORMAT_YYYY_MM_DD = "yyyy/MM/dd";

    public static String UPD_ERR_MSG = "数据已被更新，请重新修改！";

    public static String DEL_ERR_MSG = "数据已被更新，请刷新列表确认是否删除！";

    public static String DEL_ALL_ERR_MSG = "数据已被更新，请刷新页面确认是否批量删除！";

    public static String UPD_ALL_ERR_MSG = "数据已被更新，请刷新页面确认是否保存！";
    /**
     * status的值*
     */
    public static String RETURN_SUCCESS = "SUCC";

    public static String RETURN_ERROR = "ERROR";

    public static String RETURN_NOLOGIN = "NOLOGIN";

    public static String RETURN_OTHER = "OTHER";

    public static String RETURN_EXIST = "EXIST";
    
    /**
     * APP 接口的返回状态 
     * 200 OK：成功 ;
     * 500 ERROR：失败 ;
     * 401 UNAUTHORISED：消息头验证未通过 ;
     * 403 NOPERMISSION：没有权限 ;
     * 400 WRONGPARAM：参数错误
     */
    public static final String RETURN_SUCCESS_INTERFACE = "200";
    public static final String RETURN_ERROR_INTERFACE = "500";
    public static final String RETURN_UNAUTHORISED_INTERFACE = "401";
    public static final String RETURN_NOPERMISSION_INTERFACE = "403";
    public static final String RETURN_WRONGPARAM_INTERFACE = "400";

    public static String SYS_EXCEPTION = "系统异常，请联系管理员！";

    public static int AUTO_NUM = 500;// 自动补全时显示条数

    /** 图片处理使用常量 **/
    public static final int WIDTH_PORTA_BACKGROUP = 1920; // portal背景图 width
    public static final int HEIGHT_PORTA_BACKGROUP = 900; // portal背景图 height

    public static final int WIDTH_LOGO = 92; // logo width
    public static final int HEIGHT_LOGO = 75; // logo height

    public static final int WIDTH_POSTER_ONE = 628; // 主广告素材 width
    public static final int HEIGHT_POSTER_ONE = 407; // 主广告素材 height

    public static final int WIDTH_POSTER_TWO = 230; // 广告素材 width
    public static final int HEIGHT_POSTER_TWO = 130; // 广告素材 height

    public static final int WIDTH_POSTER_MOBILE = 640; // 广告素材 width
    public static final int HEIGHT_POSTER_MOBILE = 200; // 广告素材 height

    public static final double WIDTH_LESSEN = 120; // 素材略缩图 width
    public static final double HEIGHT_LESSEN = 120; // 素材略缩图 height

    public static final String SAVE_PATH = "/img/union/"; // 图片上传服务器保存路径

    public static final int WIDTH_AVATAR = 220; // 头像图片宽
    public static final int HEIGHT_AVATAR = 280; // 头像图片高

    public static final Map<String,String> PLATFORM_MAP = new HashMap<String,String>();// 其他平台信息 key:flag 0:未使用；1：已生效；2：已失效
    /**
     * 初始化平台信息
     * key :PlatFromID
     * value:map
     */
    public static final Map<String,Map<String, String>> PLATFORM_DETAIL_MAP = new HashMap<String,Map<String, String>>();
    
    public static final String ENABLE = "0";  //非激活状态
    
    public static final String DISABLE = "1";//激活状态
    
    public static final String EDA_ACCOUNT_TYPE = "EDA_ACCOUNT";
}
