<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
	.search-box td{
		line-height: 30px;
		margin-bottom: 5px;
	}
	.online-set-tab{width: 450px;min-width: 400px;font:12px/36px "宋体";margin: 0 auto;margin-top: 30px;}

	.tip{width: 120px;line-height: 25px;height: 25px;display: inline-block; }
</style>
<script type="text/javascript"> ctx = "${ctx}"</script>
<script type="text/javascript" src="${ctx}/scripts/sysmanage/place.js"></script>
<form id="searchForm" name="searchForm" action="" method="post" target="">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">
	<tr>
		<td align="right" width="8%">场所名称：</td>
		<td width="15%"><input type="text" class="PublicText" id="placeName"/></td>
		<td align="right" width="8%">场所编号：</td>
		<td><input type="text" class="PublicText" id="placeCode"/>	</td>
		<td align="right" width="8%">设备序列号：</td>
		<td><input type="text" class="PublicText" id="serialNum"/>	</td>
		<td align="right" width="60px">易盟号：	</td>
		<td>
			<input type="text" class="PublicText" id="memberName"/>
		</td>
		<td colspan="2">
			<input type="button" id="btnSearch" class="PublicButton" value="查 询" >
			<input type="button" id="btnReset" class="PublicButton" value="重 置" >
		</td>
	</tr>
</table>
</form>
<%--表格 part--%>
<div class="PublicC_t"><%--表格右上角 新增按钮--%>
	<ul class="operation fr" id="functionDiv"></ul>
</div>
<div class="right_c_tab">
	<!-- grid对应table设置 -->
	<table id="gridTable"></table>
	<div id="gridPager"></div>
	<!-- end -->
</div>

<div style="display: none;">
	<div id="addDialog">
		<%-- start 编辑页面看开始 --%>
		<form name="editForm" id="editForm" method="post" action="#">
			<table class="online-set-tab" cellpadding="0" cellspacing="0">
				<tr>
					<td class="txt-right" width="120">场所名称：</td>
					<td width="180">
						<span id="sh_placeName"></span>
					</td>
					<td width="150"></td>
				</tr>
				<tr>
					<td class="txt-right">场所编号：</td>
					<td>
						<span id="sh_placeCode"></span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td class="txt-right">设备编号：</td>
					<td>
						<span id="sh_serialNum"></span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td class="txt-right">易盟号：</td>
					<td>
						<span id="sh_memberName"></span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td class="txt-right">短信计费方式：</td>
					<td>
						<select name="smsFeeOwnerFlag" class="sel">
							<c:forEach var="item" items="${feeFlagArr }">
								<option value="${item.getCode()}">${item.getDes() }</option>
							</c:forEach>
						</select>
						<input type="hidden" name="placeDeviceId" id="placeDeviceId" />
					</td>
					<td></td>
				</tr>
			</table>
		</form>
		<%-- end 编辑页面看开始 --%>
	</div>
</div>

<script type="text/javascript">
	var pageConstants = {
		'rowList' : ${rowList}
	}

	placeFunc.init();
</script>

