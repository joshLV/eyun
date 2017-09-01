package com.rainsoft.union.web.sysmanage.model;

import com.rainsoft.base.common.model.PersistenceCommon;
import com.rainsoft.base.common.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WhiteEntity extends PersistenceCommon {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 630278465326108969L;
//	private int whiteListId;  //白名单列表主键
	private String placeCode; //场所编码
	private String placeName; //场所名称

	private String surfUserType;  //上网账号类型。EYUAPP，表示易韵APP用户。默认为EYUAPP。
	private String surfUserId;    //上网用户ID,易韵ID 对应bmanage.userMemberLogin.memberName
	private String userName;   //用户名
	private String idCard;    //身份证号
	private String mobile;    //手机号码
	private String devMac;  //设备mac地址
	private String sync2Auth;  //同步到认证的标识

	private Date createTime;  //创建时间

	private String optorType;//操作人类型；1:后台管理人员；2：会员自己
	private  int optorID;		//若操作人类型为1，则对应sysManager.managerID；若操作人类型为2，则对应usrMemberLogin.memberID

	private Integer outVal = -1;

	private String idStr;

	public WhiteEntity() {
	}

	public WhiteEntity(String placeCode, String userName, String idCard, String mobile, String devMac) {
		this.placeCode = placeCode;
		this.userName = userName;
		this.idCard = idCard;
		this.mobile = mobile;
		this.devMac = devMac;
	}

	/*public int getWhiteListId() {
		return whiteListId;
	}
	public void setWhiteListId(int whiteListId) {
		this.whiteListId = whiteListId;
	}*/
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
	public String getMobile() {
		if(StringUtil.isEmpty(mobile)){
			return "";
		}
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getCreateTimeStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(this.createTime!=null){
			return sdf.format(this.createTime);
		}
		return "";
	}

	public String getSurfUserType() {
		return surfUserType;
	}

	public void setSurfUserType(String surfUserType) {
		this.surfUserType = surfUserType;
	}

	public String getSurfUserId() {
		return surfUserId;
	}

	public void setSurfUserId(String surfUserId) {
		this.surfUserId = surfUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getOutVal() {
		return outVal;
	}

	public void setOutVal(Integer outVal) {
		this.outVal = outVal;
	}

	public String getOptorType() {
		return optorType;
	}

	public void setOptorType(String optorType) {
		this.optorType = optorType;
	}

	public int getOptorID() {
		return optorID;
	}

	public void setOptorID(int optorID) {
		this.optorID = optorID;
	}

	public String getDevMac() {
		if(StringUtil.isEmpty(devMac)){
			return "";
		}
		return devMac;
	}

	public void setDevMac(String devMac) {
		this.devMac = devMac;
	}

	public String getSync2Auth() {
		return sync2Auth;
	}

	public void setSync2Auth(String sync2Auth) {
		this.sync2Auth = sync2Auth;
	}
}
