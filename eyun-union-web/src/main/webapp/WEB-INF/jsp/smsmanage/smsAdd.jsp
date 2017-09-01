<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<!DOCTYPE HTML>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
<script type="text/javascript" src="${ctx}/scripts/smsManage/sms.js"></script>
<script type="text/javascript" src="${ctx}/common/scripts/jquery.ui.datepicker-zh-CN.js"></script>
<link rel="stylesheet" href="${ctx}/css/systemManage/portal.css" /> 
<style>
#smsBuy{
    display: inline;
    font-size: 16px;
    float: right;
    margin-top: 5px;
    margin-right: 10px;
    cursor:pointer;
    }
.selected{
	background-color: rgb(255, 174, 94);
}

.lm_c_title{height:25px;  padding-top:20px; font-weight:bold; color:#555; border-bottom:1px #c9e5f3 solid; padding-bottom:10px;}
.lm_c_title_td{ font-size:16px;}

.lm_add{ padding:0 30px;}
.lm_add td{ padding:15px 10px; font-size:14px;}
.lm_add span{ font-size:12px; color:red;}
.td-wwbdis{
	border-right:1px dotted #ccc;
	border-bottom: 1px dotted #ccc;
}
.td-msgbuy{
	border-bottom: 1px dotted #ccc;
}
.wwbi-btn{
	border:0; 
	cursor:pointer; 
	width:97px; 
	height:40px;
	margin-top: 10px;
}
.wwbi-btn-enable{
	background:url(../images/sms/qrtj.jpg) no-repeat; 
}
.wwbi-btn-disable{
	background:url(../images/sms/qrtjbak.jpg) no-repeat;
	cursor: default;
}
</style>

<script type="text/javascript">
		$(function() {
		   	bindEvent();
		  }
		)
</script>

<div class="Stretch_warp">
	<div style="width:100%; height:30px;"></div>
    <div class="lm_add"  >
         <input type="hidden"  id = "amount" value="1000"/>
         <td style="width: 76px"> 场所选择：</td>
			<td>
				<div style="width: 300px" class="select PublicSelect hack">
					<input type="text" readonly="readonly" id="placeCode" value="全部" placeValue="-1" search-valAttrName="placeValue">
					<i></i>
					<ul class="statusUlcls" style="z-index : 9999">
					    ${placeLi}
					</ul>
				</div>
			</td>
    	<table width="100%"  border="0" cellpadding="0" cellspacing="0" style="margin-top: 10px;">
          <tr>
            <td width="50%%" height="120px" align="center" class="td-wwbdis">
	            <table width="100%" border="0" cellpadding="0" cellspacing="0">
	              <tr style="height: 90px">
	                <td width="50%" rowspan="2" align="center"><img src="${ctx}/images/sms/jb.jpg" width="103" height="94" />&nbsp; &nbsp;                        
	                <span></span></td>
	                <td width="50%"  align="left" >旺旺币余额 &nbsp;&nbsp;</td>
	              </tr>
	              <tr>
	                <td  align="left" valign="middle" >
	                	<p style="padding-bottom: 23px">
		                	<c:choose>
			                	<c:when test="${empty WwbiSurplus}">0.0</c:when>
			                	<c:otherwise>${WwbiSurplus}</c:otherwise>
		                	</c:choose> 
		                		旺旺币
	                	</p>
	                	</td>
	              </tr>
	            </table>
            </td>
      		<td width="50%" class="td-msgbuy">
			  <div style="padding-top: 20px">
      			<div style="margin-left:10px;"> <label>短信费用:&nbsp;&nbsp;&nbsp;<input readonly style="border-bottom: 0px solid #000000;border-left: 0;
		   			border-right: 0;border-top: 0;" value="0.1&nbsp;旺旺币/条" onlyread /></label></div>
		       <div style="margin-top:5px; margin-left:10px;"> <label>旺旺币金额:&nbsp;&nbsp;&nbsp;<input name="bllEntity.useWWBiMoney" style="width:60px; border-bottom: 0px solid #000000;border-left: 0;
		  			border-right: 0;border-top: 0;" value="0.1" readonly id="howWwbi"/></label>旺旺币</div>
		  		<div  style="float:left; margin-top:0px; margin-left:10px;">
      				<label>
		              <div  style="float:left">购买短信:&nbsp;&nbsp;&nbsp;<img src="${ctx}/images/sms/minus.png" id="minusWWBi" style="cursor:pointer;"/>&nbsp;&nbsp;&nbsp;</div>
		              <div  style="float:left;">
		               <input type="text"  name="bllEntity.buySMSNum" id="smsNumId" value="1" style="border:1px solid #ccc; height:25px; width:100px; color:#666; padding-left:5px; " class="input" maxlength="6" 
		               		onkeyup="keyup(event); value=value.replace(/[^\d]/g,'')" onpaste="return false" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/></div>
		             <div style="float:left; margin-top:0px; margin-right:10px;">&nbsp;&nbsp;&nbsp;<img src="${ctx}/images/sms/add.png" id="addWWBi" style="cursor:pointer;"/>&nbsp;&nbsp;&nbsp;条</div>
		             <div style="clear:both;"></div>
		              <span id="errMsg"></span>
		            </label>
		         </div>
				 </div>
            </td>
          </tr>
          <tr>
            <td height="20" align=""  style="color:#333;">
              <a href="${ctx}/WWBi/buywwbi!payWwb.do" id ="buyWWBi"></a>
            </td>
            <td height="20" align="right" >
            <div id="showbutton"> 
            	<input type="button" id="payWWBi" value=""  onclick="submitPay('${ctx}')" class="wwbi-btn wwbi-btn-enable"/>
            </div>
            </td>
          </tr>
        </table>
    </div>
</div>
