<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
	.search-box td{
		line-height: 30px;
		margin-bottom: 5px;
	}
</style>
<script type="text/javascript"> ctx = "${ctx}"</script>
<script type="text/javascript" src="${ctx}/scripts/sysmanage/white.js"></script>
<div id="tabs" style="border:0px" class="sub_tabs">

	<ul>
		<li><a href="#tabs-1">白名单</a></li>

		<%--<li><a href="#tabs-2">黑名单</a></li>--%>
	</ul>

	<!-- start of tabs-1 -->
	<div id="tabs-1">
		<table cellpadding="0" cellspacing="0" class="search-box">
			<tr>
				<td class="txt-right min-w-60">场所：</td>
				<td class="wd-200">
					<select name="placeOptId" id="placeOptId" class="PublicText wd-200 h-30">
						<option value="-1">--请选择--</option>
					</select>
				</td>
				<td class="txt-right min-w-60">手机号：</td>
				<td class="txt-right" >
					<input type="text" name="mobile" id="mobile" maxlength="11"
						   onkeyup="value=this.value.replace(/\D+/g,'')" class="PublicText wd-200" placeholder="手机号"
						   id="searchName" search-field="mobile" search-type="search" search-fieldType="S" search-operation="ALIKE"/>
				</td>
				<td>
					<input type="button" id="btnSearch" class="PublicButton" value="查询" />
				</td>
			</tr>
		</table>
		<%-- start of operation part --%>
		<div class="PublicC_t">
			<ul class="operation fr" id="functionDiv"></ul>
		</div>
		<%-- end of operation part --%>
		<div>
			<table id="gridTable"></table>
			<div id="gridPager"></div>
		</div>
	</div>
    <%-- end of tabs-1 --%>
	<!-- 使用记录列表 -->
	<%--<div id="tabs-2">
		<table id="blackGrid"></table>
		<div id="blackGridPager"></div>
	</div>--%>
</div>



<div style="display: none;">
	<div id="addDialog">
		<jsp:include page="addWhite.jsp"></jsp:include>
	</div>
</div>

<script type="text/javascript">
var whiteConstants = {
	'creator':${creator},
	'rowList' : ${rowList}
}

$(function(){
	whiteFunc.init();
});

</script>

