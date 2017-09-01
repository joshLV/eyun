<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<%@ include file="/common/jsp/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<input type="button" value="绑定账户" onclick="openDialog();" />
	<input type="button" value="设置别名" onclick="openNickDialog();" />
	<input type="button" value="设置支付密码" onclick="openPayPswDialog();" />
	<table>
		<div class="right_c_tab">
			<!-- grid对应table设置 -->
			<table id="gridTable"></table>
			<div id="gridPager"></div>
			<!-- end -->
		</div>
		<div id="dialogAccount" title="绑定账号" style="display: none;width:601px">
			<form>
				<!-- hidden="hidden" -->
				<table>
					<tr>
						<td>账号名</td>
						<td><input type="text" id="currentMemberId" /></td>
					</tr>
					<tr>
						<td>登录密码</td>
						<td><input type="password" id="currentPassword" /></td>
					</tr>
				</table>
				<div style="display : none;"><input type="reset" id="resetBtn"></div>
			</form>
		</div>
		<div id="dialogNickName" title="设置别名" style="display: none;width:601px">
			<table>
				<tr>
					<td>别名：</td>
					<td><input type="text" onblur="judgeNickName();" id="nickName"/></td>
					<td><input type="text" id="hiddenNickName" value="${sessionScope.AuthenToken.anotherName }" hidden="hidden" /></td>
				</tr>
			</table>
		</div>
		<div id="dialogPayPsw" title="设置支付密码" style="display: none;width:601px">
			<form>
				<table>
					<tr id="tr1" style="display:none">
						<td>当前密码:</td>
						<td><input type="password" id="currentPwd" /></td>
					</tr>
					<tr style="display:block">
						<td>密码:</td>
						<td><input type="password" id="pwsd" /></td>
					</tr>
					<tr style="display:block">
						<td>确认密码:</td>
						<td><input type="password" id="affirmPwd" /></td>
					</tr>
				</table>
				<div style="display : none;"><input type="reset" id="resetPsw"></div>
			</form>
		</div>
	</table>

<script type="text/javascript">
	$(function() {
		// grid加载
		$("#gridTable").jqGrid({
			url : '${ctx}/systemManage/accountManage/findListAccount.do',
			height : 'auto',
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames : ['账户号', '场所数量(个)', '设备数量(个)', '注册时间', '录入者', '更新人',  '备注', '操作', 'userId' ],
			colModel : [{name : 'loginName',index : 'loginName',align : 'center',height:20,sorttype : "string"}, 
						{name : 'placeid',index : 'placeid',align : 'center',height:40,sorttype : "string"}, 
						{name : 'deviceid',index : 'deviceid',sorttype : "string",align : 'center'}, 
						{name : 'registerDate',index : 'registerDate',align : 'center',sorttype : "string"}, 
						{name : 'creator',index : 'creator',align : 'center',sorttype : "string"}, 
						{name : 'updator',index : 'updator',align : 'center',sorttype : "string"}, 
						{name : 'remark',index : 'remark',align : 'center',sorttype : "string"}, 
						{name : 'operate',index:'operate',sortable: false, align: 'center'},
						{name : 'userId',index : 'memberId',align : 'center',sorttype : "Integer",hidden :true}],
			rowList : ${rowList},
			jsonReader : {
				rowNum:"rowNum",
				root:"dataRows",			// 数据行（默认为：rows）
				page: "curPage",			// 当前页
				total: "totalPages",		// 总页数
				records: "totalRecords",	// 总记录数
				repeatitems : false			// 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
			},
			beforeProcessing : function(data, status, xhr) {
				var datas = data.dataRows;
				for (var i = 0; i < datas.length; i++) {
					datas[i].operate = "<input type='button' onclick='deleteAccount(" + datas[i].userId + ")' value='解绑' class='btn_img_update'/><span style='margin-right: 8px;'></span>";
				}
				xhr.dataRows = datas;
			},
			pager : "#gridPager",
			multiselect : false, // 复选框设置
		}).navGrid('#gridPager', {edit : false,add : false,del : false,search : false,refresh : false});
		// 场所列表Change事件
		$("#barid").change(function() {
			// 选中场所ID取得
			var barid = $('#barid').val();
			// grid重新加载
			$("#gridTable").jqGrid('setGridParam', {datatype : 'json',page : 1,postData : {'goodsCategory.barid' : barid}}).trigger('reloadGrid');
		});
	});

	// grid自适应
	$(window).resize(function() {
		$("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
	});

	/** 
	* 解除账号绑定 dialogjb
	*@param 参数 会员id
	*/
	function deleteAccount(id){
		showMsg("确定解除账号绑定吗?", 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : "${ctx}/systemManage/accountManage/updRelieveById.do",  
				dataType : 'json',
				data : {"userId" : id},  
				type : 'POST',  
				success : function(data) {
					showWaittingMsg(data.msg, 1000);
					if (data.status == "SUCC") {
						jQuery("#gridTable").trigger("reloadGrid");
						$("#dialogAccount").dialog('close');
					}
				}
			});
		});
	}
		
		
	/** 
	* 打开 账号绑定界面dialog
	*/
	function openDialog() {
		$("#dialogAccount").dialog({
			resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			buttons : {
				"绑定" : function() {
						bindingAccount();
				},
				"取消" : function() {
					clearData();
				}
			},
			close : function() {
				clearData();
			}
		});
	}
	
	/** 
	* 账号绑定
	* @param currentMemberId 绑定账号id 
	* @param currentPassword 绑定账号密码 （MD5加密32位字符）
	*/
	function bindingAccount(){
		var currentMemberId = $('#currentMemberId').val();
		var currentPassword = $('#currentPassword').val();
		if((currentMemberId==null || currentMemberId=="")
				||(currentPassword==null || currentPassword =="")){
			showWaittingMsg("信息不完整!", 1000);
			return ;
		}
		showMsg("确定绑定该账号吗？", 2, function() {
			$(this).dialog('close');
			$.ajax({  
				url : "${ctx}/systemManage/accountManage/callBindingAccount.do",  
				data : {  
					"currentMemberId" : currentMemberId,
					"currentPassword" : currentPassword
				},  
				type : 'POST',  
				dataType : 'json',
				success : function(data) {  
					showWaittingMsg(data.msg, 1000);
					if (data.status == "SUCC") {
						jQuery("#gridTable").trigger("reloadGrid");
						$("#dialogAccount").dialog('close');
					}
				},
			});  
		});
	}
	
	//打开别名dialog
	function openNickDialog() {
		$("#nickName").val($("#hiddenNickName").val());
		$("#dialogNickName").dialog({
			resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			buttons : {
				"确定" : function() {
						setNickName();
				},
				"取消" : function() {
					$(this).dialog('close');
				}
			}
		});
	}
	
	/**
	* 别名设置验证，如果别名为改变，则把dialog确定按钮设置为不可用
	*/
	function judgeNickName(){
			//var str = "${sessionScope.AuthenToken.anotherName}";
		var hiddenNickName =$('#hiddenNickName').val();
		var nickname =$('#nickName').val();
		if(hiddenNickName==nickname){//别名一样，不允许设置
			//dialog确定按钮设置为不可用
			$(":button:contains('确定')").attr("disabled",true);
		}else{
			$(":button:contains('确定')").attr("disabled",false);
		}
	}
	
	/**
	*别名设置
	*/
	function setNickName(){
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
		showMsg("确定设置该别名吗？", 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : "${ctx}/systemManage/accountManage/updateNickName.do",
				data : {
					"nickName" : nickname,
				},
				type : 'POST',
				success : function(data) {
					var result = JSON.parse(data); 
					showWaittingMsg(result.msg, 1000);
					if (result.status == "SUCC") {
	// 					var nickame = $("#nickName").val();
						$("#hiddenNickName").val(nickname);
						$("#dialogNickName").dialog('close');
					}
				}, 
			});
		});
	}
	
	function openPayPswDialog() {
		$("#resetPsw").click();
		openCurrentPwd();
		$("#dialogPayPsw").dialog({
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
		showMsg("确定修改支付密码吗？", 2, function() {
			$(this).dialog('close');
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
						showWaittingMsg(data.msg, 1000);
						if (data.status == "SUCC") {
							$("#dialogPayPsw").dialog('close');
							return true;
						}
					}
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
						showWaittingMsg(data.msg, 1000);
						if (data.status == "SUCC") {
							$("#dialogPayPsw").dialog('close');
							return true;
						}
					}
				});
			}
		});
	}
	
	//清除账号信息
	function clearData() {
		$("#resetBtn").click();
		$("#dialogAccount").dialog('close');
	}
		
		/**
		*跳转到别名设置页面
		*/
		function skipJsp() {
			window.location.href = "${ctx}/systemManage/accountManage/toSetNickNamePage.do";
		}
		/**
		*跳转到密码设置页面
		*/
		function setPwd() {
			window.location.href = "${ctx}/systemManage/accountManage/toSetpayPwd.do";
		}
	</script>