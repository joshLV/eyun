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
/* * { margin: 0;padding: 0; list-style: none;box-sizing: border-box;  } */
/*       .bgPop{ display: none;position: absolute;z-index: 9;left: 0;top: 0;width: 100%;height: 100%;background: rgba(0,0,0,.2);  } */
/*       .pop {  display: none;width: 500px;height: 300px;position: absolute; top: 0;left: 0;bottom: 0;right: 0;margin: auto;padding: 50px; */
/*                 z-index: 10;background-color: #ffe; box-shadow: 0 3px 18px rgba(0, 0, 0, .5); } */
/*       input:focus{outline:none} */
.close {
    background: url(${ctx}/images/account/wxclose.gif) no-repeat;
    font-size: 0;
    height: 15px;
    line-height: 0;
    position: absolute;
    right: 11px;
    top: 10px;
    width: 15px;
}
.qrcodearea dd {
    background: url(${ctx}/images/account/wx.png) no-repeat;
    height: 106px;
    width: 260px;
    margin: 0 auto;
    display: block;
    -webkit-margin-start: 100px;
}

.qrcodearea {
    position: fixed;
    _position: absolute;
    width: 460px;
    height: 400px;
    background: white;
    top: 50%;
    left: 50%;
    margin-top: -200px;
    margin-left: -230px;
    display: none;
    z-index: 1000;
}
.qrcodearea dt img {
    width: 200px;
    height: 200px;
    border: none;
}
.mask {
    width: 100%;
    height: 100%;
    position: fixed;
    _position: absolute;
    background-color: #000;
    opacity: 0.6;
    filter: alpha(opacity=60);
    top: 0;
    left: 0;
    display: none;
}
.qrcodearea dt {
    height: 200px;
    width: 200px;
    margin: 50px auto 10px;
    display: block;
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
            <form id="wxPaysubmit" action='account/accAccount/create2code.do' method='POST' target="_blank">
            	<input type='hidden' name='buyWWBiRecordId'   value='${wwbRecord.buyWWBiRecordId }'>
				<input type='hidden' name='rmbFee' value='${wwbRecord.rmbFee }' >
				<input type='hidden' name='wwbQtyFee' value='${wwbRecord.wwbQtyFee }' >
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
                    <input name="input" type="button" class="btn_style0101" value="" onclick="pay()" ></td>
                    
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
	<!--遮罩层-->
<!--  <div class="bgPop"></div> -->
<!--弹出框-->
<!--  <div class="pop"> -->
<!--  	<a id="wxAreaCloser" href="javascript:void(0)" class="close">关闭</a> -->
<!-- 	<img> -->
<!-- 	<dd></dd> -->
<!--  </div>	 -->
<div class="mask" id="mask" style="z-index: 99;"></div>

		<div class="qrcodearea" id="wxArea">
			<a id="wxAreaCloser" href="javascript:void(0)" class="close">关闭</a>
			<dt></dt>
			<dd></dd>
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
			$('#wxArea').show();
			$('#mask').height($(document).height()>$(window).height()?$(document).height():$(window).height()).show();

			 $.ajax({
				    url: "${ctx}/account/accAccount/wxSend.do",
			        type : 'post',
			        async : true,
			        dataType: 'json',
			        data : {"buyWWBiRecordId" : ${wwbRecord.buyWWBiRecordId },
			        	"rmbFee" : ${wwbRecord.rmbFee },
			        	"wwbQtyFee" : ${wwbRecord.wwbQtyFee }},
			        success: function (data) {
			        	if(data.status ='SUCC') {
			        		//$("img").attr("src" , "${ctx}/account/accAccount/create2code.do?codeUrl="+json.code_url);
			        		$('#wxArea dt').html('<img src="${ctx}/account/accAccount/create2code.do?codeUrl='+data.data.code_url+'" />');
			        	}
			        },
			        error : function() {
						   alert(errorMsg);
				       }
			    }); 
		}
		
		function closeDialog() {
			$("#rechargeDialog").dialog("close");
		}
		
	    $(function () {
	    	$('#wxAreaCloser').click(function(){
	    		$('#wxArea').hide();
	    		$('#mask').hide();
	    		showdiv();
	    	});

	    })
	</script>
</html>
