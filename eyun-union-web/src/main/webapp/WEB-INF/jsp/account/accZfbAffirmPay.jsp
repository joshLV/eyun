<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
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
		<title>To ZfbPay Page
		</title>
	</head>
	<body>
	 <div class="lm_right">
	 <div class="lm_border"></div>
     <div class="lm_content">
        	<div class="lm_c_title2">
            	<table width="100%" height="25" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td class="lm_c_title2_td">&nbsp;&nbsp;&nbsp;&nbsp;确认支付</td>
                    <td align="right">&nbsp;</td>
                  </tr>
                </table>
            </div>
            <!--'${ctx}/account/accAccount/yfbCallBack.do'  "https://mapi.alipay.com/gateway.do?_input_charset=utf-8" -->
            <div class="lm_add">
            <form id="alipaysubmit" name="alipaysubmit" action="https://mapi.alipay.com/gateway.do?_input_charset=utf-8" method="post" target= "_blank">
				<input type='hidden' name='service'   value='${zfb.service }'>
				<input type='hidden' name='partner' value='${zfb.partner }' >
				<input type='hidden' name='_input_charset' value='${zfb._input_charset }' >
				<input type='hidden' name='sign_type'   value='${zfb.sign_type }' >
				<input type='hidden' name='sign'   value='${zfb.sign }' >
				<input type='hidden' name='out_trade_no'   value='${zfb.out_trade_no }' >
				<input type='hidden' name='subject'  value='${zfb.subject }' >
				<input type='hidden' name='payment_type' value='${zfb.payment_type }' >
				<input type='hidden' name='total_fee'   value='${zfb.total_fee }' >
				<input type='hidden' name='seller_id'   value='${zfb.seller_id }' >
				<input type='hidden' name='notify_url'   value='${zfb.notify_url }' >
           <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tbody><tr>
                    <td colspan="4" align="center">&nbsp;</td>
                  </tr>
                  <tr>
                    <td width="9%">订单编号：</td>
                    <td width="41%">${zfb.out_trade_no }</td>
                    <td width="9%">商品名称：</td>
                    <td>账户充值</td>
                  </tr>
                  <tr>
                    <td width="9%">商品数量：</td>
                    <td width="41%">${zfb.quantity }</td>
                    <td width="9%">金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额：</td>
                    <td><span style="color:#FF3300; font-size:18px;">${zfb.total_fee }</span></td>
                  </tr>
                  <tr>
                    
                  </tr>
                  <tr>
                    <td height="140" colspan="4" align="right">
                    <input name="input" type="submit" class="btn_style0101" value="" onclick= pay()></td>
                    
                  </tr>
                  
                  <tr>
                    <td colspan="4" align="center">&nbsp;</td>
                  </tr>
                  <tr>
                    <td colspan="4" align="center">&nbsp;</td>
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
		<span>充值完成，是否继续充值？</span>
    </div>
	
	</body>
	<script type="text/javascript">
	
	function showdiv() {            
		$("#rechargeDialog").dialog({
			autoOpen: false,
			width : '300',
			height : '150',
			modal : true,
			buttons : {
				"充值完成" : function() {
					paySuccess();
				},
				"继续充值" : function() {
					payContinue();
				}
			}
		});
		$("#rechargeDialog").dialog("open");
	}
	
	function paySuccess() {
		 closeDialog();
		  var errorMsg = '跳转异常';
			 $.ajax({
				    url: "${ctx}/account/accAccount/toAccAccount.do",
			        type : 'post',
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
	
	function payContinue() {
		 closeDialog();
		 var errorMsg = '跳转异常';
		 $.ajax({
			    url: "${ctx}/account/accAccount/toAccAccount.do",
		        type : 'post',
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
	
	
	function pay() {
		showdiv();
		$("#alipaysubmit").submit();
	}
	
	function closeDialog() {
		$("#rechargeDialog").dialog("close");
	}

	</script>
</html>
