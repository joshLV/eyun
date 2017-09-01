package com.rainsoft.union.web.pubdata.model;

import com.rainsoft.base.common.model.PersistenceCommon;
import com.rainsoft.base.common.utils.StringUtil;

/**
 * Created by Sun on 2016/5/25.
 * 致终于来到这里的勇敢的人：
 * 你是被上帝选中的人，英勇的、不辞劳苦的、不眠不修的来修改
 * 我们这最棘手的代码的编程骑士。你，我们的救世主，人中之龙，
 * 我要对你说：永远不要放弃，永远不要对自己失望，永远不要逃走，辜负了自己。
 * 永远不要哭啼，永远不要说再见。永远不要说谎来伤害自己。
 */
public class PlaceData extends PersistenceCommon {
//    private String placeCode;           // 场所编码 ,父类已经包含
    private String placeName;           // 场所名称
    private String placeTypeName;       // 场所类型名称
    private String contact;             // 联系人
    private String tel;                 // 固定电话
    private String mobile;              // 手机号码
    private String email;               // 电子邮箱
    private String addr;                // 联系地址
    private int areaId;                 // 区域编码
    private String areaName;            // 区域全名称 格式：例如：贵州省-铜仁市-江口县
    private Integer userId;             // 场所会员ID

    //for place dao
    private String smsFeeOwnerFlag;     //场所短信付费方式 S:自费，Y：公司付费
    private Integer placeDeviceId;      //场所设备Id
    private String serialNum;  //设备序列号
    private String memberName; //用户名

    public PlaceData() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceTypeName() {
        return placeTypeName;
    }

    public void setPlaceTypeName(String placeTypeName) {
        this.placeTypeName = placeTypeName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSmsFeeOwnerFlag() {
        return smsFeeOwnerFlag;
    }
    public String getSmsFeeOwnerFlagStr() {
        if(StringUtil.isNotEmpty(smsFeeOwnerFlag)){
            return PlaceEnum.getDesByCode(smsFeeOwnerFlag);
        }
        return smsFeeOwnerFlag;
    }

    public void setSmsFeeOwnerFlag(String smsFeeOwnerFlag) {
        this.smsFeeOwnerFlag = smsFeeOwnerFlag;
    }

    public Integer getPlaceDeviceId() {
        return placeDeviceId;
    }

    public void setPlaceDeviceId(Integer placeDeviceId) {
        this.placeDeviceId = placeDeviceId;
    }

    public String getSerialNum() {

        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
