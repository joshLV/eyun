package com.rainsoft.riplat.web.systemManage.model;

import com.rainsoft.base.common.model.PersistenceCommon;


public class Usrmemberbase extends PersistenceCommon{
	private Integer memberid;		//对应usrMemberLogin.memberID， 一对一关系
	private String memberrealname;	//个人或公司真实名称
	private String tel;				//固定电话
	private String mobile;			//手机
	private String areaid;			//对应pubArea.areaID
	private String addr;			//地址
	private String email;			//邮箱
	private String headimgurl;		//个人头像	
	private String verstatus;		//手机邮箱验证
	private String ctrlcode;		//会员权限控制
	private String createtime;		//创建时间
	private String edittime;		//编辑时间
	private String zip;				//邮政编码
	private String cardtype;		//证件类型
	private String cardid;			//证件号码
	/*******地区实体*******/
	private int province;
	private int city;
    private String areaName;
	
	
    public int getProvince() {
		return province;
	}
	public void setProvince(int province) {
		this.province = province;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getMemberid() {
		return memberid;
	}
	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	public String getMemberrealname() {
		return memberrealname;
	}
	public void setMemberrealname(String memberrealname) {
		this.memberrealname = memberrealname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getVerstatus() {
		return verstatus;
	}
	public void setVerstatus(String verstatus) {
		this.verstatus = verstatus;
	}
	public String getCtrlcode() {
		return ctrlcode;
	}
	public void setCtrlcode(String ctrlcode) {
		this.ctrlcode = ctrlcode;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getEdittime() {
		return edittime;
	}
	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
}
