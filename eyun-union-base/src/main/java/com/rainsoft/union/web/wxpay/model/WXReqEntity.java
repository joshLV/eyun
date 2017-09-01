package com.rainsoft.union.web.wxpay.model;


import java.io.Serializable;

/**
 * 微信实体对象
 * WXEntity
 * Created by qianna on 2016/9/21
 */
public class WXReqEntity implements Serializable{

    /**
	 * 
	 */
	/*********************下单参数*********************/
    private String appid; //公众账号ID
    private String mch_id;//商户号
    private String nonce_str; //随机字符串
    private String sign;//签名
    private String body; //商品描述
    private String out_trade_no;//商户系统的订单号，与请求一致
    private Integer total_fee;//总金额 订单总金额，单位为分，详见支付金额
    private String spbill_create_ip;//终端IP APP和网页支付提交用户端ip
    private String notify_url;//异步通知地址

    /*********************App下单参数*********************/
    private String trade_type; //交易类型，取值如下：JSAPI，NATIVE，APP

    /*********************查询订单*********************/
    private String transaction_id;
    /*********************扫码支付*********************/
    private String time_expire;
    private String auth_code; //授权码   扫码支付授权码，设备读取用户微信中的条码或者二维码信息


    public WXReqEntity() {    }


    /**
     * 查询、取消
     * @param nonce_str
     * @param out_trade_no
     * @param transaction_id
     */
    public WXReqEntity(String nonce_str, String out_trade_no, String transaction_id) {
        this.nonce_str = nonce_str;
        this.out_trade_no = out_trade_no;
        this.transaction_id = transaction_id;
    }

    public WXReqEntity(String nonce_str, String body, String out_trade_no, Integer total_fee, String spbill_create_ip, String auth_code) {
        this.nonce_str = nonce_str;
        this.body = body;
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.auth_code = auth_code;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }



    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }


}
