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
	<input type="button" value="设置别名" onclick="openDialog();" />
	<div id="dialogbm" title="设置别名" style="display: none;width:601px">
		<table>
			<tr>
				<td>别名：</td>
				<td><input type="text" onblur="judgeNickName();" id="nickName" value="${sessionScope.AuthenToken.anotherName }" /></td>
				<td><input type="text" id="hiddenNickName" value="${sessionScope.AuthenToken.anotherName }" hidden="hidden" /></td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
		/**
		*打开 别名设置dialog
		*/
		function openDialog() {
			$("#dialogbm").dialog({
				resizable: false,
				width : 'auto',
				height : 'auto',
				modal : true,
				buttons : {
					"确定" : function() {
						commit();
					},
					"取消" : function() {
						$("#dialogbm").dialog('close');
					} 
				}
			});
		}
		/**
		*别名设置
		*/
		function commit(){
			var pattern =/^[\u4E00-\u9FA5A-Za-z0-9]+$/ ;
			var nickname =$('#nickName').val();
			if(nickname==null||nickname==""){
				showWaittingMsg("别名不可为空!", 1000); 
			 	return ;
			}
			if(!pattern.test(nickname)){
				showWaittingMsg("别名不可含有空格及特殊符号!", 1000); 
			 	return ;
			}
			$.ajax({
				url : "${ctx}/systemManage/accountManage/updateNickName.do",
				data : {
					"nickName" : nickname,
				},
				type : 'POST',
				success : function(date) {  
	                 var result = JSON.parse(date); 
	                 if (result.status == "SUCC") {
	                 	 var nickame =$("#nickName").val();
// 						 window.parent.parent.parent.document.getElementById("nickname1").innerText="欢迎您,"+nickame+"!" ;
	                     showWaittingMsg(result.msg, 1000);
// 	                     $("#dialogbm").dialog('close');
	                     location.reload(); 
	                     return ;
	                 } else {  
	                     showWaittingMsg(result.msg, 1000);  
	                 }  
	            }, 
			});
		}
		
		
		/**
		* 别名设置验证，如果别名为改变，则把dialog确定按钮设置为不可用
		*/
		function judgeNickName(){
// 			var str = "${sessionScope.AuthenToken.anotherName}";
			var hiddenNickName =$('#hiddenNickName').val();
			var nickname =$('#nickName').val();
			if(hiddenNickName==nickname){//别名一样，不允许设置
				//dialog确定按钮设置为不可用
				$(":button:contains('确定')").attr("disabled",true);
			}else{
				$(":button:contains('确定')").attr("disabled",false);
			}
		}
		
// 	  window.onload=openDialog;
	</script>
</html>
