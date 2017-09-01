<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<html>
<head>
<title></title>
<%@ include file="/common/jsp/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>
	<input type="button" value="设置支付密码" onclick="openDialog();" />
	<div id="dialog1" title="设置支付密码" style="display: none;width:601px">
		<table>
			<tr id="tr1" style="display:none">
				<td>当前密码:</td>
				<td><input type="password" id="currentPwd" /><span id="currentPwsd" style="color:#FF0000 "></span></td>
			</tr>
			<tr style="display:block">
				<td>密码:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
				<td><input type="password" id="pwsd" /><span id="pwd" style="color:#FF0000 "></span></td>
			</tr>
			<tr style="display:block">
				<td>确认密码:</td>
				<td><input type="password" id="affirmPwd" /><span id="affpwd" style="color:#FF0000 "></span></td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
		/**
		* 打开 密码设置dialog
		*/
		function openDialog() {
			openCurrentPwd();
			$("#dialog1").dialog({
				resizable: false,
				width : 'auto',
				height : 'auto',
				modal : true,
				buttons : {
					"确定" : function() {
						setPassword();
					},
					"取消" : function() {
						location.reload();
					} 
				}
			});
		}
		/**
		* 密码设置
		*/
		function setPassword(){
			//拿当前密码tr标签
			var v1 = document.getElementById("tr1"); 
			var currentPwd = $("#currentPwd").val();
			var newMemberPwd = $("#pwsd").val();
			var newMemberPwd2 = $("#affirmPwd").val();
			var pwdReg = /[a-zA-Z0-9.]{6,20}/;
			if(v1.style.display == "block"){
				if(currentPwd == null || currentPwd ==""){
					showWaittingMsg("密码不能为空!", 1000);
					return false;
				}
			}
			if ((newMemberPwd == null || newMemberPwd == "")
				||(newMemberPwd2 == null || newMemberPwd2 == "") ){
				showWaittingMsg("密码不能为空!", 1000);
				return false;
			} else if (!pwdReg.test(newMemberPwd)) {
				showWaittingMsg("密码由6-20位字符组成，可由数字、字母或者符号组成", 1000);
				return false;
			} else if (newMemberPwd != newMemberPwd2) {
				showWaittingMsg("两次密码输入不一致!", 1000);
				return false;
			}
			if(v1.style.display == "block"){
				if(currentPwd == newMemberPwd){
					showWaittingMsg("新密码不能与原密码相同!", 1000);
					return false;
				}
			}
			//有当前密码
			if(v1.style.display == "block"){
				$.ajax({
					type : "POST",
					url : "${ctx}/systemManage/accountManage/payPwdSet.do",
					data : {
						"setBuyPassword" : newMemberPwd,
						"currentPwd" : currentPwd
					},
					dataType : 'json',
					success : function(data) {
						if (data.status == "SUCC") {
							$("#dialog1").dialog('close');
							showWaittingMsg(data.msg, 1000);
							return true;
						} else {
							showWaittingMsg(data.msg, 1000);
							return false;
						}
					},
				});
			}else{//没有当前密码
				$.ajax({
					type : "POST",
					url : "${ctx}/systemManage/accountManage/payPwdSet.do",
					data : {
						"setBuyPassword" : newMemberPwd,
						"currentPwd" : null
					},
					dataType : 'json',
					success : function(data) {
						if (data.status == "SUCC") {
							$("#dialog1").dialog('close');
							showWaittingMsg(data.msg, 1000);
							return true;
						} else {
							showWaittingMsg(data.msg, 1000);
							return false;
						}
					},
				});
			}
			
		}
		
		/**
		*判断用户是否有设置过密码
		*/
		function openCurrentPwd(){
			var v1 = document.getElementById("tr1"); 
			$.ajax({
				type : "POST",
				url : "${ctx}/systemManage/accountManage/judgePayPwdSet.do",
				//data : {"setBuyPassword" : newMemberPwd},
				dataType : 'json',
				success : function(data) {
					if (data.status == "SUCC") {
						v1.style.display="block";
						return true;
					} else {
						v1.style.display="none";
						return false;
					}
				},
			});
		}
	</script>
</html>
