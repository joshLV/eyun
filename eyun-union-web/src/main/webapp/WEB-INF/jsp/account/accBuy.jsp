<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no" />
	<link rel="stylesheet" href="${ctx}/css/account/wwbstyle.css" />
	<script type="text/javascript" src="${ctx}/scripts/account/Base64.js"></script>
</head>


<script type="text/javascript">
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

	});
	function yfbPay() {
		//var reg = new RegExp($("#WWBiFee").attr("reg"));
		var WWBiFee = $("#WWBiFee").val();
		if (WWBiFee == '' || WWBiFee == null) {
			$("#errMsg").html("请输入旺旺币数量");
			return;
		}
		if (!(/^\+?[1-9][0-9]*$/.test(WWBiFee))) {
			$("#errMsg").html("输入金额有误");
			return;
		}
		if (WWBiFee.length > 6) {
			$("#errMsg").html("超出最多额度限制");
			return;
		}
		var RMBFee = $("#RMBFee").val() + ".00";
		//旺旺币数量
		var WWBQtyFee = $("#RMBFee").val();
		//验证 后期完善
		//if(WWBiFee != RMBFee){
		//	return;
		//}

		//$("#wwbiForm").submit();
		//Base64加密
		var base = new Base64();
		var useType = "0";
		var userKey = WWBiFee + ',' + RMBFee + ',' + WWBQtyFee + ',' + useType;
		var userKeyCode = base.encode(userKey);

		//解密
		//str = b.decode(str);  
		/* window.location = "${ctx}/account/accAccount/yfbPay.do?userKey="
				+ userKeyCode; */
	     var errorMsg = '跳转异常';
		 $.ajax({
			    url: "${ctx}/account/accAccount/yfbPay.do?userKey="
					+ userKeyCode,
		        type : 'get',
		        async : true,
		        dataType: 'html',
		        success: function (data) {
                 $("#main").html(data);
		        },
		        error : function() {
					   alert(errorMsg);
			       },
		    }); 
	}

	//禁止输入数字以外的字符
	$(function() {
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

	function keyup(event) {
		var WWBiFee = $("#WWBiFee").val();
		//var WWBiFee=parseInt($("#WWBiFee").val());
		$("#RMBFeeText").html(WWBiFee);
		$("#RMBFee").val(WWBiFee);
	}
	
	function zfbPay() {
		var WWBiFee = $("#WWBiFee").val();
		if (WWBiFee == '' || WWBiFee == null) {
			$("#errMsg").html("请输入旺旺币数量");
			return;
		}
		if (!(/^\+?[1-9][0-9]*$/.test(WWBiFee))) {
			$("#errMsg").html("输入金额有误");
			return;
		}
		if (WWBiFee.length > 6) {
			$("#errMsg").html("超出最多额度限制");
			return;
		}
		var RMBFee = $("#RMBFee").val() + ".00";
		//旺旺币数量
		var WWBQtyFee = $("#RMBFee").val();
		//验证 后期完善
		//if(WWBiFee != RMBFee){
		//	return;
		//}

		//$("#wwbiForm").submit();
		//Base64加密
		var base = new Base64();
		var useType = "0";
		var userKey = WWBiFee + ',' + RMBFee + ',' + WWBQtyFee + ',' + useType;
		var userKeyCode = base.encode(userKey);

		//解密
		//str = b.decode(str);  
		/* window.location = "${ctx}/WWBi/buywwbi!getWWBiPayOrderZFB.do?userKey="
				+ userKeyCode; */
		 var errorMsg = '跳转异常';
			 $.ajax({
				    url: "${ctx}/account/accAccount/zfbPay.do?userKey="
						+ userKeyCode,
			        type : 'get',
			        async : true,
			        dataType: 'html',
			        success: function (data) {
	                 $("#main").html(data);
			        },
			        error : function() {
						   alert(errorMsg);
				       },
			    }); 
	}
	
	function typeChange() {
		var radios = document.getElementsByName("payType");
		var type;
		for(var i = 0; i < radios.length; i++) {
			if(radios[i].checked) {
				type = i;
			}
		}
		if(type == "0") {
			$("#payWWBi").attr("onclick", "yfbPay();");
		} else if(type == '1'){
			$("#payWWBi").attr("onclick", "zfbPay();");
		} else if(type == '2'){
			$("#payWWBi").attr("onclick", "wxPay();");
		}
	}
	
	function wxPay() {
		var WWBiFee = $("#WWBiFee").val();
		if (WWBiFee == '' || WWBiFee == null) {
			$("#errMsg").html("请输入旺旺币数量");
			return;
		}
		if (!(/^\+?[1-9][0-9]*$/.test(WWBiFee))) {
			$("#errMsg").html("输入金额有误");
			return;
		}
		if (WWBiFee.length > 6) {
			$("#errMsg").html("超出最多额度限制");
			return;
		}
		var RMBFee = $("#RMBFee").val() + ".00";
		//旺旺币数量
		var WWBQtyFee = $("#RMBFee").val();
		//验证 后期完善
		//if(WWBiFee != RMBFee){
		//	return;
		//}

		//$("#wwbiForm").submit();
		//Base64加密
		var base = new Base64();
		var useType = "0";
		var userKey = WWBiFee + ',' + RMBFee + ',' + WWBQtyFee + ',' + useType;
		var userKeyCode = base.encode(userKey);

		//解密
		//str = b.decode(str);  
		/* window.location = "${ctx}/WWBi/buywwbi!getWWBiPayOrderZFB.do?userKey="
				+ userKeyCode; */
		 var errorMsg = '跳转异常';
			 $.ajax({
				    url: "${ctx}/account/accAccount/wxPay.do?userKey="
						+ userKeyCode,
			        type : 'get',
			        async : true,
			        dataType: 'html',
			        success: function (data) {
	                 $("#main").html(data);
			        },
			        error : function() {
						   alert(errorMsg);
				       },
			    }); 
	}
</script>

<div class="lm_right">
	<div class="lm_border"></div>
	<div class="lm_content">
		<div class="lm_c_title2">
			<table width="100%" height="25" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td class="lm_c_title2_td">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账户充值</td>
					<td align="right">&nbsp;</td>
				</tr>
			</table>
		</div>

		<div class="lm_add">
			<form id="wwbiForm" action="${ctx}/WWBi/buywwbi!getWWBiPayOrder.do"
				method="post">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="45%" height="150" align="center">
						<table
								width="100%" border="0" cellpadding="0" cellspacing="0"
								style="border-right:1px dotted #ccc;">
								<tr>
									<td width="46%" rowspan="2" align="center"><p>
											<img src="${ctx}/images/account/jb.jpg" width="103"
												height="94" />&nbsp; &nbsp;
										</p> </span></span></span></td>
									<td width="50%"  align="left" >账户余额 &nbsp;&nbsp;</td>
							    </tr>
							    <tr>
							    <td  align="left" valign="middle" >
					                	<p >
						                	<c:choose>
							                	<c:when test="${empty accountBal}">0.00</c:when>
							                	<c:otherwise>${accountBal}</c:otherwise>
						                	</c:choose> 
						                		元
					                	</p>
							   </td>
							   </tr>
							  </table>
						  </td>
						<td width="55%" align="center"><label>
								<div style="float:left; margin-top:1px; margin-right:10px;">
									充值金额：<img src="${ctx}/images/account/minus.png"
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
						<td height="30" colspan="2" align="right" valign="middle"
							style="border-top:1px dotted #ccc; color:#FF0000; font-weight:bold;  font-size:24px; ">￥<font
							id="RMBFeeText">1.00</font> <input type="hidden"
							name="entity.RMBFee" id="RMBFee" value="1" />
						</td>
					</tr>
					<tr>
						<td height="52" align="left"
							style="border-top:1px dotted #ccc; color:#333;">
							<form name="type">
								<label><input type="radio" name="payType" style="vertical-align:text-bottom; margin-bottom:10px; *margin-bottom:-4px;" checked="checked" onclick="typeChange();"><img src="${ctx}/images/pay/yfb.gif"" /></label>
								<label><input type="radio" name="payType" style="vertical-align:text-bottom; margin-bottom:10px; *margin-bottom:-4px;" onclick="typeChange();"><img src="${ctx}/images/pay/zfb.gif" /></label>
								<label><input type="radio" name="payType" style="vertical-align:text-bottom; margin-bottom:10px; *margin-bottom:-4px;" onclick="typeChange();"><img src="${ctx}/images/pay/wx.gif" /></label>
							</form>
						</td>
						<td height="52" align="right" style="border-top:1px dotted #ccc;">
							<input type="button" id="payWWBi" value="" onclick="yfbPay();"
							style="background:url(${ctx}/images/account/js.png) no-repeat; border:0; cursor:pointer; width:97px; height:40px;" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div style="clear:both;"></div>
</div>

