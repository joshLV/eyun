<%@ page language="java" pageEncoding="utf-8"%>

<style type="text/css">
.online-set-tab{width: 450px;min-width: 400px;font:12px/36px "宋体";margin: 0 auto;margin-top: 30px;}

.tip{width: 120px;line-height: 25px;height: 25px;display: inline-block; }
</style>
<form name="addForm" id="addForm" method="post" action="#">
	<input type="hidden" name="whiteListId" value="" /><%--记录主键--%>
<table class="online-set-tab" cellpadding="0" cellspacing="0">
   	<tr>
   		<td class="txt-right" width="120">场所<span class="clr-red">*</span>：</td>
   		<td width="180">
   			<select name="placeName" class="PublicText wd-200 h-30"></select>
   		</td>
   		<td width="150"><span id="placeTip" class="tip clr-error"></span></td>
   	</tr>

	<tr>
		<td class="txt-right">姓名<span class="clr-red">*</span>：</td>
		<td>
			<input type="text" name="memberName" maxlength="6" class="PublicText wd-200" placeholder="姓名，最大长度为6"/>
		</td>
		<td>
			<span id="memberNameTip" class="tip clr-error"></span>
		</td>
	</tr>
	<tr>
		<td class="txt-right">身份证号<span class="clr-red">*</span>：</td>
		<td>
			<input type="text" name="idCard" maxlength="40"
				   onkeyup="value=this.value.replace(/\W+/g,'')"
				   class="PublicText wd-200" placeholder="身份证号"/>
		</td>
		<td>
			<span id="idCardTip" class="tip clr-error"></span>
		</td>
	</tr>
   	<tr>
   		<td class="txt-right">手机号<span class="clr-red">*</span>：</td>
   		<td>
   			<input type="text" name="mobile" maxlength="11"
			onkeyup="value=this.value.replace(/\D+/g,'')" 
			onpaste="return false;"
			class="PublicText wd-200" placeholder="手机号"/>
   		</td>
   		<td>
   			<span id="mobileTip" class="tip clr-error"></span>
   		</td>
   	</tr>
	<tr>
		<td class="txt-right">设备Mac地址：</td>
		<td>
			<input type="text" name="devMac" maxlength="12"
				   onkeyup="value=this.value.replace(/[^0-9,a-z]+/g,'')"
				   onpaste="return false;"
				   class="PublicText wd-200" placeholder="mac地址，不包含分隔符的12个字符"/>
		</td>
		<td>
			<span id="devMacTip" class="tip clr-error"></span>
		</td>
	</tr>
	<%--<tr>
		<td class="txt-right">易韵账号：</td>
		<td>
			<input type="text" name="eyunId" maxlength="40"
				   onkeyup="value=this.value.replace(/\W+/g,'')"
				   onpaste=""
				   class="PublicText wd-200" placeholder="易韵账号"/>
		</td>
		<td>
			<span id="eyunIdTip" class="tip clr-error"></span>
		</td>
	</tr>--%>
</table>
</form>
<script type="text/javascript">
$(function(){
	addPage();
});
</script>
</html>
