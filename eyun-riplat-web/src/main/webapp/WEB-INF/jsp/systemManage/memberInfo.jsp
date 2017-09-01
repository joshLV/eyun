<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/common/scripts/area.js"></script>
<link rel="stylesheet" href="${ctx}/css/style.css"></link>
<script type="text/javascript" src="${ctx}/scripts/systemManage/Base64.js"></script>
<link rel="stylesheet" href="${ctx}/css/systemManage/material.css"></link>
<style type="text/css">
	#clr-error{color:red;}
</style>

	<div class="PublicC_t">
		<ul class="operation fr" id="functionDiv"></ul>
	</div>
	<div class="NotData">
		<form id="signupForm" method="post">
        <fieldset>
		<input type="hidden" id="cmaterialType" value="4" />
		 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="Public_table2">
			<tr>
				<th>真实姓名：</th>
				<td><input type="text"  id="memberRealName" maxlength="20"/></td>
				<td></td>
			</tr>
			<tr style="background-color: rgb(231, 240, 249);">
				<th>联系电话(固话)：</th>
				<td><input type="text" id="tel"  readonly="readonly" maxlength="20"/></td>
				<td rowspan="3">
					
				</td>
			</tr>
			<tr>
				<th>手机：</th>
				<td><input type="text" id="mobile" maxlength="14"/></td>
			</tr>
			<tr style="background-color: rgb(231, 240, 249);">
				<th>Email：</th>
				<td><input type="text" readonly="readonly"  id="email" maxlength="40" style="width:200px"/></td>
			</tr>
			<tr style="background-color: rgb(231, 240, 249);">
				<th>所在地区：</th>
				<td id="areaId">
					<input id="areaName"  style="width:300px"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th style="width: 130px;">详细地址：</th>
				<td colspan="2" height="80"><textarea rows="3" cols="58" id="addr" maxlength="60"></textarea></td>
			</tr>
			<tr style="background-color: rgb(231, 240, 249);">
				<td colspan="3" style="text-align: center;">
					<input type="button" id="btnSave" value="保 存" role="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" class="ui-button-text"  onclick="saveMemberInfo();" style="display:none"/>
<!--  					<input type="reset" id="btnExit" value="取 消" role="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" class="ui-button-text" onclick="userInfo();"  style="display:none"/>  -->
				</td>
			</tr>
		</table>
		</fieldset>
		</form>
	</div>
	
	<div id="pswDialog" title="修改密码" style="display: none;width:601px">
		<form>
			<table>
				<tr>
					<td>当前密码：</td>
					<td><input type="password"  class="PublicText" id="currentPwd" maxlength="32" /></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" class="PublicText" id="pwsd" maxlength="32" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input type="password" class="PublicText" id="affirmPwd" maxlength="32" /></td>
				</tr>
			</table>
			<div style="display : none;"><input type="reset" id="resetPsw" /></div>
		</form>
	</div>

<script type="text/javascript">

	$(function(){
		//添加修改密码按钮
		$("#functionDiv").append(updPswHtm);
		$("#updPswFunc").click(function() {
			openPswDialog();
		});
		
		//添加编辑按钮
		$("#functionDiv").append(initHtm);
		$("#initFunc").click(function() {
			initData();
		});
		
	});
	/**
	*个人信息资料填充，页面初始化
	*/
	function userInfo(){
 		$.ajax({  
			url : "${ctx}/systemManage/member/getMemberInfoById.do",  
			type : 'POST', 
			dataType : 'json',
			success : function(date) {  
				if (date.status == "SUCC") {
					var datas = $.parseJSON(date.data);
					$("#memberRealName").val(datas.memberRealName);
					$("#mobile").val(datas.mobile);
					$("#tel").val(datas.tel);
					$("#email").val(datas.email);
					$("#addr").val(datas.addr);
					$("#areaName").val(datas.areaName);
					$("#memberRealName").attr("readonly",true);
					$("#mobile").attr("readonly",true);
					$("#tel").attr("readonly",true);
					$("#addr").attr("readonly",true);
					$("#email").attr("readonly",true);
					$("#areaName").attr("readonly",true);
					$("#btnSave").hide();
				}
			}
		});  
	}
	//页面初始化个人信息
	$(function() {
		userInfo();
	});
	
	//初始化地区
	function areaInit(defaultSelects) {
		var areaArray = new Array();
		areaArray["appendSelectors"] = "areaId";
		areaArray["selectors"] = "loadProvince,loadCity,loadCounty";
		areaArray["styles"] = "width:80px,width:80px,width:80px";
		areaArray["defaultSelects"] = defaultSelects;
		initArea(areaArray);
	}
	
	/**获取区域ID**/
	function getAreaID(){
 		$.ajax({  
			url : "${ctx}/systemManage/member/getMemberInfoById.do",  
			type : 'POST', 
			dataType : 'json',
			success : function(date) {  
				if (date.status == "SUCC") {
					var datas = $.parseJSON(date.data);
					areaInit(datas.areaID);
				}
			}
		});  
	}
	/**
	* 编辑信息时，放开文本框及按钮
	*/
	function initData(){
		getAreaID();//获取加载区域的下拉框
		$("#memberRealName").removeAttr("readonly");
		$("#mobile").removeAttr("readonly");
		$("#tel").removeAttr("readonly");
		$("#addr").removeAttr("readonly");
		$("#email").removeAttr("readonly");
		$("#areaName").css({display:"none"});
		$("#btnSave").show();
	}
	/**
	*上传头像，成功后再保存用户修改信息
	*/
	function saveMemberInfo(){
		//验证个人信息是否正确，正确后上传头像，再修改个人信息
		var memberRealName = $("#memberRealName").val();
		var tel = $("#tel").val();
		var mobile = $("#mobile").val();
		var email = $("#email").val();
		var areaId = $("#loadCounty").val();
		var addr = $("#addr").val();
		//电话号码与手机号码同时验证
		var telReg = /^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
		var mobileReg = /^1[3,4,5,6,7,8,9][0-9]{9}$/;
		var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		var emailReg2 = /^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
		if (tel == null || "" == tel || !telReg.test(tel)) {
			showWaittingMsg("电话号码不正确", 1000);
			return false;
		}
		if (mobile != null && "" != mobile && !mobileReg.test(mobile)) {
			showWaittingMsg("手机号码不正确", 1000);
			return false;
		}else if($.trim(mobile)==null||$.trim(mobile)==""){
			showWaittingMsg("请输入手机号码", 1000);
			return false;
		}
		if ((email != null && "" != email) && !(emailReg.test(email)|| emailReg2.test(email))) {
			showWaittingMsg("Email地址不正确", 1000);
			return false;
		}
		if(areaId == null || "" == areaId || "-1" == areaId){
			showWaittingMsg("请选择县级市", 1000);
			return false;
		} 
		 //responseResult 为从后台返回信息，通常情况下返回的是JSON
        $.ajax({
	            type: "post", // 提交方式 get/post
	            url: "${ctx}/systemManage/member/updateMemberInfo.do", // 需要提交的 url
	            data: {
						"memberRealName" : memberRealName,
						"tel" : tel,
						"mobile" : mobile,
						"email" : email,
						"areaID" : areaId,
						"addr" : addr
	            },
	            dataType : "json",
	            success: function(data) {
	                 if (data.status == "SUCC") {
	                	$("#province").val($("#loadProvince").val())
						$("#city").val($("#loadCity").val())
						$("#county").val($("#loadCounty").val());
						showWaittingMsg(data.msg, 4000);
	                    userInfo();
	                 } else {
						showWaittingMsg(data.msg, 4000);
					}
	            }
	        });
    }
	/**
	* 打开 密码设置dialog
	*/
	function openPswDialog() {
		$("#resetPsw").click();
		$("#pswDialog").dialog({
			resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			buttons : {
				"确定" : function() {
					setPassword();
				},
				"取消" : function() {
					$(this).dialog('close');
				} 
			}
		});
	}
	
	/**
	* 密码设置方法 提交
	*/
	function setPassword(){
		//Base64加密
		var base = new Base64();
		var currentPwd = base.encode($("#currentPwd").val());
		var newMemberPwd = base.encode($("#pwsd").val());
		var newMemberPwd2 = base.encode($("#affirmPwd").val());
		var pwdReg = /[a-zA-Z0-9.]{6,20}/;
		if ((newMemberPwd == null || newMemberPwd == "")
			||(newMemberPwd2 == null || newMemberPwd2 == "") 
			||(currentPwd == null || currentPwd =="") ){
			showWaittingMsg("密码不能为空!", 1000);
			return false;
		} else if (!pwdReg.test(newMemberPwd)) {
			showWaittingMsg("密码由6-20位字符组成，可由数字、字母或者符号组成", 1000);
			return false;
		} else if (newMemberPwd != newMemberPwd2) {
			showWaittingMsg("两次密码输入不一致!", 1000);
			return false;
		}
		if(currentPwd == newMemberPwd){
			showWaittingMsg("新密码不能与原密码相同!", 1000);
			return false;
		}
		$.ajax({
			type : "POST",
			url : "${ctx}/systemManage/member/userPwdSet.do",
			data : {
				"newUserPassword" : newMemberPwd,
				"currentPassword" : currentPwd
			},
			dataType : 'json',
			success : function(data) {
				if (data.status == "SUCC") {
					$("#pswDialog").dialog('close');
				}
				showWaittingMsg(data.msg, 1000);
			}
		});
	}
</script>
