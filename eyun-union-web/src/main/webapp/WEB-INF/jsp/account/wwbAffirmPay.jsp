<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<script type="text/javascript" src="${ctx}/scripts/account/Base64.js"></script>
<link rel="stylesheet" href="${ctx}/css/account/wwbstyle.css" />
<style type="text/css">
.btn_style0101{ background:url(${ctx}/images/account/qrtj.jpg); width:97px; height:40px; border:0; cursor:pointer;}
.dalog{
	text-align:center;
	margin-right: auto;
	margin-left: auto;
	height:20px;
	background:#f00;
	vertical-align:middle;
	line-height:40px;
}
</style>

<html>
	<head>
		<title>To YeePay Page
		</title>
	</head>
	<body>
	 <div class="lm_right">
	 <div class="lm_border"></div>
     <div class="lm_content">
        	<div class="lm_c_title2">
            	<table width="100%" height="25" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="lm_c_title2_td">&nbsp;&nbsp;&nbsp;&nbsp;确认购买</td>
                    <td align="right">&nbsp;</td>
                  </tr>
                </table>
            </div>
            <div class="lm_add">
            <form id="yfbpaysubmit" name="yeepay" target="_blank">
             	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<input type='hidden' id="totalUseGiveWwb" name='datas' value='${wwbAccount.totalUseGiveWwb }' >
             	<input type='hidden' id="totalGiveWwb" name='datas'   value='${wwbAccount.totalGiveWwb }'>
				<input type='hidden' id="id" name='datas' value='${wwbAccount.id }' >
				<input type='hidden' id="placeCode" name='datas'   value='${wwbAccount.placeCode }' >
				<input type='hidden' id="placeDeviceId" name='datas'   value='${wwbAccount.placeDeviceId }' >
				<input type='hidden' id="serialNum" name='datas' value='${wwbAccount.serialNum }' >
				<input type='hidden' id="useType" name='datas' value='${wwbAccount.useType }' >
				<input type='hidden' id="balance" name='datas'  value='${wwbAccount.balance }' >
                <tbody><tr>
                    <td colspan="4" align="center">&nbsp;</td>
                  </tr>
                  <tr>
                  	<td width="9%">场所名称：</td>
                    <td width="30%"><font>${wwbAccount.remark}</font></td>
                    <td width="9%" colspan="2">商品名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;旺旺币</td>
                  </tr>
                  <tr>
                  	<td width="9%">场所设备：</td>
                    <td width="30%"><font>${wwbAccount.serialNum }</font></td>
                    <td width="9%">商品数量：</td>
                    <td width="20%">${wwbAccount.totalUseGiveWwb}</td>
                    <td width="9%">金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额：</td>
                    <td><span style="color:#FF3300; font-size:18px;">${wwbAccount.totalGiveWwb}.00</span>&nbsp;&nbsp;元</td>
                  </tr>
                  <tr>
                    
                  </tr>
                  <tr>
                    <td height="90" colspan="6" align="right">
                    <input name="input" type="button" class="btn_style0101" value="" onclick="pay()" ></td>
                  </tr>
                  
                  <tr>
                    <td colspan="4" align="center">&nbsp;</td>
                  </tr>
              </tbody></table>
              </form>
          </div>
        </div>
    </div>
    <div style="clear:both;"></div>
	<div id="rechargeDialog" title="旺旺币充值" class="dalog" style="display : none;">
		<span id="masg"></span>
    </div>
		
	</body>
<script type="text/javascript">      
	$("#rechargeDialog").dialog({
		//resizable: false,
		autoOpen: false,
		width : '300',
		height : '150',
		modal : true,
		buttons : {
			"继续" : function() {
				$("#rechargeDialog").dialog("close");
				paySuccess();
			},
			"取消" : function() {
				$("#rechargeDialog").dialog("close");
				payContinue();
			}
		}
	});
		
	function paySuccess() {
		$("#rechargeDialog").dialog("close");
		var errorMsg = '跳转异常';
		$.ajax({
			url : "${ctx}/account/wwbAccount/toWwbAccount.do",
			type : 'get',
			async : true,
			dataType : "html",
			success : function(data) {
				$("#main").html(data);
			},
			error : function() {
				alert(errorMsg);
			},
		});

	}
	function payContinue() {
		$("#rechargeDialog").dialog("close");
		var errorMsg = '跳转异常';
		$.ajax({
			url : "${ctx}/account/wwbAccount/toWwbAccount.do",
			type : 'get',
			async : true,
			dataType : 'html',
			success : function(data) {
				$("#main").html(data);
			},
			error : function() {
				alert(errorMsg);
			}
		});
	}
	function pay() {
		var base = new Base64();
		var dat = $("input[name='datas']");
		var data = "";
		$.each(dat, function(i, vals) {
			if (i < 1) {
				data += $(this).val();
			} else {
				data += "," + $(this).val();
			}
		})
		var enCode = base.encode(data);
		//var errorMsg = '跳转异常';
		$.ajax({
			url : "${ctx}/account/wwbAccount/wwbBuy.do",
			type : 'post',
			async : true,
			data : {"enCode" : enCode},
			dataType : 'json',
			success : function(data) {
				if (data.status == "SUCC") {
					$("#rechargeDialog").dialog("open");
					$("#masg").html(data.message)
				} else {
					$("#rechargeDialog").dialog("open");
					$("#masg").html(data.message)
				}
			},
			error : function() {
				alert(errorMsg);
			},
		});
	}

	function closeDialog() {
		$("#rechargeDialog").dialog("close");
	}
</script>
</html>
