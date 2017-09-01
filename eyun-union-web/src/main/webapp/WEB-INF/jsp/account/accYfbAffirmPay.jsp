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
                    <td class="lm_c_title2_td">&nbsp;&nbsp;&nbsp;&nbsp;确认支付</td>
                    <td align="right">&nbsp;</td>
                  </tr>
                </table>
            </div>
            <!--'${ctx}/account/accAccount/yfbCallBack.do'  -->
            <div class="lm_add">
            <form id="yfbpaysubmit" name="yeepay" action='${yfb.nodeAuthorizationURL }' method='POST' target="_blank">
				<input type='hidden' name='p0_Cmd'   value='${yfb.p0_Cmd }'>
				<input type='hidden' name='p1_MerId' value='${yfb.p1_MerId }' >
				<input type='hidden' name='p2_Order' value='${yfb.p2_Order }' >
				<input type='hidden' name='p3_Amt'   value='${yfb.p3_Amt }' >
				<input type='hidden' name='p4_Cur'   value='${yfb.p4_Cur }' >
				<input type='hidden' name='p5_Pid'   value='${yfb.p5_Pid }' >
				<input type='hidden' name='p6_Pcat'  value='${yfb.p6_Pcat }' >
				<input type='hidden' name='p7_Pdesc' value='${yfb.p7_Pdesc }' >
				<input type='hidden' name='p8_Url'   value='${yfb.p8_Url }' >
				<input type='hidden' name='p9_SAF'   value='${yfb.p9_SAF }' >
				<input type='hidden' name='pa_MP'    value='${yfb.pa_MP }' >
				<input type='hidden' name='pd_FrpId' value='${yfb.pd_FrpId }' >
				<input type="hidden" name="pr_NeedResponse"  value='${yfb.pr_NeedResponse }' >
				<input type='hidden' name='hmac'     value='${yfb.hmac }' >
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tbody><tr>
                    <td colspan="4" align="center">&nbsp;</td>
                  </tr>
                  <tr>
                    <td width="9%">订单编号：</td>
                    <td width="41%">${wwbRecord.buyWWBiRecordId }</td>
                    <td width="9%">商品名称：</td>
                    <td>账户充值</td>
                  </tr>
                  <tr>
                    <td width="9%">商品数量：</td>
                    <td width="41%">${wwbRecord.wwbQtyFee }</td>
                    <td width="9%">金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额：</td>
                    <td><span style="color:#FF3300; font-size:18px;">${wwbRecord.rmbFee }</span></td>
                  </tr>
                  <tr>
                    
                  </tr>
                  <tr>
                    <td height="140" colspan="4" align="right">
                    <input name="input" type="submit" class="btn_style0101" value="" onclick="pay()" ></td>
                    
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
				//resizable: false,
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
			$("#rechargeDialog").dialog("close");
			//$(".ui-icon-closethick").trigger("click");
			
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
			$("#rechargeDialog").dialog("close");
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
