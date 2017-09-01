package com.rainsoft.union.web.account.model;

/**
 * 支付宝业务对象
 * @author wangqian
 */
public class AccZfbBuy extends AccBuyRecord{
	public String service;//接口名称
	public String partner;//合作者身份ID
	public String _input_charset;//参数编码字符集
	public String sign_type;//签名方式
	public String sign;//签名
	//基本参数（可空）
	public String notify_url;//服务器异步通知页面路径 :支付宝服务器主动通知商户网站里指定的页面http路径
	public String return_url;//页面跳转同步通知页面路径 :支付宝处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径
	public String error_notify_url;//请求出错时的通知页面路径
	
	//业务参数（必填）
	public String out_trade_no;//商户网站唯一订单号
	public String subject;//商品名称
	public String payment_type = "1";//支付类型  默认值为：1（商品购买）
	public double total_fee;//交易金额
	public String seller_id;//卖家支付宝用户号
	//业务参数（可空）
	public String price;//商品单价(存在total_fee，就不能有price，quantity)
	public String quantity;//购买数量
	public String body;//商品描述
	public String show_url;//商品展示网址
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getError_notify_url() {
		return error_notify_url;
	}
	public void setError_notify_url(String error_notify_url) {
		this.error_notify_url = error_notify_url;
	}
	
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(double total_fee) {
		this.total_fee = total_fee;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getShow_url() {
		return show_url;
	}
	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}
	
}
