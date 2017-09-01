<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
	<script type="text/javascript" src="${ctx}/common/scripts/jquery.ui.datepicker-zh-CN.js"></script>
	<link rel="stylesheet" href="${ctx}/css/account/wwbstyle.css" />
<style>
select{
 	width: 100%;height: 40px;
 	border-style: 1px;/* none; */
 	padding-top: 0px;
 	background-color:#e1effe; 
 }
 .cc{ 
 	display:inline;
 	height: 10px;
 	padding-bottom: 5px;
 }
</style>
</head>
<div class="lm_right">
	<div class="lm_border"></div>
	<div class="lm_content">
		<div class="lm_c_title2" > 
			<table width="100%" height="20" class="PublicTable" border="0" cellspacing="0" 
				cellpadding="0">
				<tr height="20" width="100%" >
					<td width="45%"colspan="1"></td>
					<td width="55%" colspan="2" style="text-align: left; padding:0 0 5px 0px;"><!-- border:1px solid ; -->
						<div class="cc lm_add" id="di" style="width: 220px; border:none">
						<font style="font:12px/1.5 微软雅黑,Arial,Helvetica,sans-serif;color:#737373;">&nbsp;&nbsp;&nbsp;选择场所：</font>
						<select class="visitor_place_lesect lm_add" style="width: 35%;height: 25px; border-color: #ccc; margin:-1;" id="placeCode" name="placeCode" onchange="synchro()">
							<c:forEach var="place" items="${placeList}" varStatus="status">
								<option value="${place.placeCode}" placeId="${place.id}" placename="${place.placeName}">${place.placeName}</option>
							</c:forEach>
						</select>
						<font style="font:12px/1.5 微软雅黑,Arial,Helvetica,sans-serif;color:#737373;">&nbsp;&nbsp;选择设备：</font>
						<select class="visitor_place_lesect lm_add" style="width: 35%; border-color: #ccc;height: 25px; margin:-1;" id="placeDeviceId" name="placeDeviceId" onchange="getWwbBalance()">
							<c:forEach var="device" items="${deviceList}" varStatus="status">
								<option value="${device.id}" serialNum="${device.serial_num}">${device.serial_num}</option>
							</c:forEach>
						</select>
					</div>
					</td>
				</tr>
			</table>
		</div>

		<div class="lm_add">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="45%" height="150" align="center">
					<table
							width="100%" border="0" cellpadding="0" cellspacing="0"
							style="border-right:1px dotted #ccc;">
							<tr>
								<td width="46%" rowspan="2" align="center"><p>
										<img src="${ctx}/images/account/chongzhi.jpg" width="110"
											height="100" />&nbsp; &nbsp;
									</p> </span></span></span></td>
								<td width="50%"  align="left" >旺旺币余额 &nbsp;&nbsp;</td>
						    </tr>
						    <tr>
						    <td  align="left" valign="middle" >
				                <font id="balan"></font>&nbsp&nbsp元
				                <%-- <input type="hidden" id="accbalan" value="${accAccount.accountBal }"/> --%>
						   </td>
						   </tr>
						  </table>
					  </td>
					<td width="55%" align="center"><label>
							<div style="float:left; margin-top:1px; margin-right:10px;">
								购买数额：<img src="${ctx}/images/account/minus.png"
									id="minusWWBi" />
							</div>
							<div style="float:left;">
								<input type="text" name="entity.WWBiFee" id="WWBiFee" value="1"
									style="border:1px solid #ccc; height:25px; width:100px; color:#666; padding-left:5px;"
									maxlength="6"
									onkeyup="keyup(event); value=value.replace(/[^\d]/g,'') "
									onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />
							</div>
							<div style="float:left; margin-top:1px; margin-left:10px;">
								<img src="${ctx}/images/account/add.png" id="addWWBi" />&nbsp;&nbsp;&nbsp;元
							</div> <span id="errMsg"></span>
					</label></td>
				</tr>

				<tr>
					<td height="20" align="left"
						style="border-top:1px dotted #ccc; color:#333;">
					</td>
					<td height="20" align="right" style="border-top:1px dotted #ccc; width: 100%">
						<input type="button" id="payWWBi"  value="结&nbsp;算" onclick="wwbBuy();" disabled="disabled" 
						style="background-color:gray; border:0; cursor:pointer; width:97px; height:40px; font-size: 23px;font-style: normal;color: white;" />
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="clear:both;"></div>
</div>

<script type="text/javascript">
	//搜索记录
	$(document).ready(function() {
		//输入旺旺币触发   
		$("#WWBiFee").blur(function() {
			var WWBiFee = $("#WWBiFee").val();
			$("#RMBFeeText").html(WWBiFee + ".00");
			$("#RMBFee").val(WWBiFee);
		});
		//鼠标插入动作
		$("#WWBiFee").focus(function() {
			$("#errMsg").html("");
		});
		//点击增加按钮
		$("#addWWBi").click(function() {
			var WWBiFee = parseInt($("#WWBiFee").val());
			WWBiFee = WWBiFee + 1;
			$("#WWBiFee").val(WWBiFee);
			$("#RMBFeeText").html(WWBiFee + ".00");
			$("#RMBFee").val(WWBiFee);

		});
		//点击减少按钮	
		$("#minusWWBi").click(function() {
			var WWBiFee = parseInt($("#WWBiFee").val());
			if (WWBiFee > 0) {
				WWBiFee = WWBiFee - 1;
				$("#WWBiFee").val(WWBiFee);
				$("#RMBFeeText").html(WWBiFee + ".00");
				$("#RMBFee").val(WWBiFee);
			}
		});
		/*禁止输入数字以外的值  */
		 $("#WWBiFee").keypress(function(event) {
			var keyCode = event.which;
			if (keyCode == 8 || (keyCode >= 48 && keyCode <= 57)) {
				return true;
			} else {
				return false;
			}
		}).focus(function() {
			this.style.imeMode = 'disabled';
		});
	});
	
	function wwbBuy() {
		/* 获取旺旺币余额  */
		var balan = $("#balan").html();
		//alert(balan);
		/* 场所code */
		var placeCode = $("#placeCode").val();
		/* 场所名称 */
		var placeName = $("#placeCode").find("option:selected").attr("placeName");
		/* 场所id */
		var placeId = $("#placeCode").find("option:selected").attr("placeId");
		/* 场所设备id  */
		var placeDeviceId = $("#placeDeviceId").val();
		/* 平台设备序列号  */
		var serialNum = $("#placeDeviceId").find("option:selected").attr("serialNum");
		/*购买旺旺币数额  */
		var totalUseGiveWwb = $("#WWBiFee").val();
		/* 账户消费类型 */
		var useType = "7";
		if (totalUseGiveWwb == '' || totalUseGiveWwb == null) {
			$("#errMsg").html("请输入旺旺币数量");
			return;
		}
		if (!(/^\+?[1-9][0-9]*$/.test(totalUseGiveWwb))) {
			$("#errMsg").html("输入金额有误");
			return;
		}
		if (totalUseGiveWwb.length > 6) {
			$("#errMsg").html("超出最大额度限制");
			return;
		}
		if(checkBalance(totalUseGiveWwb) == false){
			$("#errMsg").html("您的账户余额不足，请充值！");
			return;
		}
		var errorMsg = "跳转异常"
		$.ajax({
		    url: "${ctx}/account/wwbAccount/toWwbBuy.do",
	        type : 'post',
	        data : {"totalUseGiveWwb":totalUseGiveWwb,"placeCode":placeCode,"remark": placeName,"placeDeviceId":placeDeviceId,"serialNum":serialNum,"balance":balan,"id":placeId},
	        async : true,
	        dataType: 'html',
	        success: function (data) {
	        	$("#main").html(data);
	        },
	        error : function() {
				alert(errorMsg);
		    }
		 }); 
	}
	
	$(function() {
		//获取当前场所设备的旺旺币账户余额
		getWwbBalance();
		var d = $("#placeDeviceId").val();
		if(d == '' || d == '-1'){
			/* 删除事件 */
			$("#payWWBi").attr("onclick", "");
			/* 添加disabled属性 */ 
			$("#payWWBi").attr("disabled","disabled");
			$("#payWWBi").css("background-color","gray");
		} else {
			/* 添加事件 */
			$("#payWWBi").attr("onclick", "wwbBuy()")
			/* 移除disabled属性 */
			$("#payWWBi").removeAttr("disabled");
			$("#payWWBi").css("background-color","#F98A37");
		}
	});
	
	/* 获取旺旺币余额 */
	function getWwbBalance(){
	    var errorMsg = "账户余额获取失败!";
		$.ajax({
		    url: "${ctx}/account/wwbAccount/getWwbAccount.do",
	        type : 'post',
	        data : {"placeCode":$("#placeCode").val(),"placeDeviceId": $("#placeDeviceId").val()},
	        async : true,
	        dataType: 'json',
	        success: function (data) {
	        	$("#balan").empty();
	        	if(data ==null || data == ''){
		        	$("#balan").html("0");
	        	}else{
	        		$("#balan").html(data.balance);
	        	}
	        },
	        error : function() {
				alert(errorMsg);
		    }
		 }); 
	}
	
	/* 当场所发生改变时触发事件查出对应设备和余额 */
	function synchro(){
		getDevice();
		getWwbBalance();
	}
	
	/* 检查账户余额是否充足 */
	function checkBalance(balance){
		var iss = false;
		$.ajax({
		    url: "${ctx}/account/wwbAccount/checkAccBalance.do",
	        type : 'post',
	        data : {"balance":balance},
	        async : false,
	        dataType: 'json',
	        success: function (data) {
	        	if(data == '1'){
	        		iss = true;
	        	} else {
	        		iss = false;
	        	}
	        },
	        error : function() {
				alert("系统异常");
		    }
		 }); 
		 return iss;
	}
	
	/* 获取设备列表 */
	function getDevice() {
		/* 清除子节点 */
		$("#placeDeviceId").empty();
		$.ajax({
			url : '${ctx}/account/wwbAccount/getDeviceList.do',
			type : 'post',
			dataType : 'json',
			data : {"placeCode" : $("#placeCode").val()},
			async : false,
			success : function(data) {
				var arr = data.deviceList;
				$.each(arr, function(i, value) {
					$("#placeDeviceId").append("<option value=" + this.id +" serialNum=" + this.serial_num + ">" + this.serial_num + "</option>");
				});
				var d = $("#placeDeviceId").val();
				if(d == '' || d == '-1'){
					/* 删除事件 */
					$("#payWWBi").attr("onclick", "");
					/* 添加disabled属性 */ 
					$("#payWWBi").attr("disabled","disabled");
				} else {
					/* 添加事件 */
					$("#payWWBi").attr("onclick", "wwbBuy()")
					/* 移除disabled属性 */
					$("#payWWBi").removeAttr("disabled"); 
				}
			}
		});
	}
</script>

