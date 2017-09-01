package com.rainsoft.union.web.wxpay.model;


/**
 * Created by qianna on 2016/9/14.
 */
public enum WXKeyType {
    /**#*************************微信请求链接***********************************#*/
//    url_unifiedorder("https://api.mch.weixin.qq.com/pay/unifiedorder"),//统一下单
    /*url_orderquery("https://api.mch.weixin.qq.com/pay/orderquery"),  //查询订单
    url_closeorder("https://api.mch.weixin.qq.com/pay/closeorder"), //关闭订单
    url_micropay("https://api.mch.weixin.qq.com/pay/micropay"),//提交刷卡支付*/

    /**#*************************返回参数***********************************#*/
    out_trade_no(""),//商户系统的订单号，与请求一致
    charset("utf-8"),//请求使用的编码格式，如utf-8,gbk,gb2312等

    return_code("SUCCESS"),//返回状态码
    return_msg(""),//返回信息
    result_code("SUCCESS"),//业务结果
    err_code(""),//错误代码
    err_code_des(""),//错误代码描述

    nonce_str(""),//微信返回的随机字符串
    sign(""), //微信返回的签名
    prepay_id(""),//预支付交易会话标识
    transaction_id(""),//微信支付订单号
    time_end(""),//支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
    /*********************** 查询返回 ************************/
    trade_state(""),//
    /******************** 撤销订单返回 ***********************/
    recall("Y"),//是否需要继续调用撤销，Y-需要，N-不需要
    ;
    private String value ;
    private WXKeyType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
