package com.rainsoft.base.web.system.model;

import java.util.Date;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 功能说明：用户实体
 * 
 * @author admin
 * @created 2014年6月16日 上午6:01:02
 * @updated
 */
public class BaseUser extends PersistenceCommon {

	/** 用户登录名 */
	private String loginName;
	/** 登录密码 */
	private String password;
	/** 用户昵称 */
	private String anotherName;
	/** 是否为子账号。P：主账号；S：子账号 */
	private String accountType;
	/** 打开页面标记。 1：易韵联盟广告主页面；2：易韵联盟场所主页面；3：上网管理页面 */
	private String openUrl;
	/** 所属会员类别 1:广告主；2：场所主；3：云辰内部用 */
	private String memberType;
	/** 登录错误次数 默认为0, 当会员登录成功时将出错次数设为0 */
	private int loginErrorqty;
	/** 若是通过APP客户端注册，则传入APP对应的ID，否则传入null */
	private String appID;
	/** 会员登陆IP */
	private String loginIP;
	/** 最后登录方式, 1：web网页; 2：wap网页; 3：手机客户端；4：未知 */
	private Integer loginWay;
	/** 用户邮箱 */
	private String email;
	/** 年龄 */
	private Integer age;
	/** 出生年月日 */
	private Date birthday;
	/** 证件类型 */
	private String cardTypeString;
	/** 证件号码 */
	private String cardId;
	/** 手机号 */
	private String mobile;
	/** 电话号码 */
	private String telPhone;
	/** 省份 */
	private String province;
	/** 市 */
	private String city;
	/** 县 */
	private String county;
	/** 地址 */
	private String address;
	
	private String platformId;//平台Id
	
	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getLoginName() {

		return loginName;

	}

	public void setLoginName(String loginName) {

		this.loginName = loginName;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public String getAnotherName() {

		return anotherName;

	}

	public void setAnotherName(String anotherName) {

		this.anotherName = anotherName;

	}

	public String getEmail() {

		return email;

	}

	public void setEmail(String email) {

		this.email = email;

	}

	public Integer getLoginWay() {

		return loginWay;

	}

	public void setLoginWay(Integer loginWay) {

		this.loginWay = loginWay;

	}

	public String getMobile() {

		return mobile;

	}

	public void setMobile(String mobile) {

		this.mobile = mobile;

	}

	public String getTelPhone() {

		return telPhone;

	}

	public void setTelPhone(String telPhone) {

		this.telPhone = telPhone;

	}

	public String getProvince() {

		return province;

	}

	public void setProvince(String province) {

		this.province = province;

	}

	public String getCity() {

		return city;

	}

	public void setCity(String city) {

		this.city = city;

	}

	public String getCounty() {

		return county;

	}

	public void setCounty(String county) {

		this.county = county;

	}

	public String getAddress() {

		return address;

	}

	public void setAddress(String address) {

		this.address = address;

	}

	public Integer getAge() {

		return age;

	}

	public void setAge(Integer age) {

		this.age = age;

	}

	public Date getBirthday() {

		return birthday;

	}

	public void setBirthday(Date birthday) {

		this.birthday = birthday;

	}

	public String getCardId() {

		return cardId;

	}

	public void setCardId(String cardId) {

		this.cardId = cardId;

	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public int getLoginErrorqty() {
		return loginErrorqty;
	}

	public void setLoginErrorqty(int loginErrorqty) {
		this.loginErrorqty = loginErrorqty;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getCardTypeString() {
		return cardTypeString;
	}

	public void setCardTypeString(String cardTypeString) {
		this.cardTypeString = cardTypeString;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getOpenUrl() {
		return openUrl;
	}

	public void setOpenUrl(String openUrl) {
		this.openUrl = openUrl;
	}

}