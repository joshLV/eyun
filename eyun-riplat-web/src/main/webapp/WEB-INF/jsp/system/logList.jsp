<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<script type="text/javascript" src="${ctx}/common/scripts/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">
		<tr>
			<td width="6%">操作账户：</td>
			<td style="width:15%">
				<div class="select PublicSelect hack">
					<input type="text" readonly="readonly" id="account" value="请选择" accountValue="-1" search-field="optor" search-type="search" search-fieldType="I" search-operation="IEQ" search-valAttrName="accountValue">
					<i></i>
					<ul id="accountUl" style="z-index : 9999">
						<li value="-1">请选择</li>
						<c:forEach var="user" items="${userList}">
							<li value="${user.id }">${user.name }</li>
						</c:forEach>
					</ul>
				</div>
			</td>
			<td align="right" width="5%">时间：</td>
			<td style="width:35%">
				<div class="PublicTime">
					<input type="text" style="width:42%" class="PublicText TextTime" id="datePickerLeft" search-field="start_time" search-type="search" search-fieldType="D" search-operation="GE">
					<u>至</u>
					<input type="text" style="width:42%" class="PublicText TextTime" id="datePickerRight" search-field="start_time" search-type="search" search-fieldType="D" search-operation="LE">
				</div>
			</td>
			<td colspan="2">
				<input type="button" class="PublicButton" value="查 询" onclick="loadPostData();">
			</td>
		</tr>
	</table>
	<!-- grid对应table设置 -->
	<table id="gridTable"></table>
	<div id="gridPager"></div>
	<!-- end -->
	
<script type="text/javascript">
	$(function(){
		$("#datePickerLeft").datepicker({
			showAnim : 'slide',
			changeMonth:true,	//显示月份下拉菜单
			changeYear:true		//显示年份下拉菜单	
		});
		
		$("#datePickerRight").datepicker({
			showAnim : 'slide',
			changeMonth:true,	//显示月份下拉菜单
			changeYear:true		//显示年份下拉菜单	
		});
		$("#gridTable").jqGrid({
			url:'${ctx}/system/log/loadGrid.do',
			mtype : 'post',
			datatype : "json",
			height: 'auto',
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:['操作账户','操作模块', '操作行为', 'ip', '开始时间', '结束时间', '状态', 'id'],
			colModel:[
				{name:'optorName',index:'optorName', align: 'center', sorttype:"string", width : '15'},
				{name:'optModule',index:'module_name', align: 'center', width : '10'},
				{name:'optAction',index:'optAction', sorttype:"string", align: 'center', width : '15'},
				{name:'ip',index:'ip',sorttype:"string", align: 'center', width : '10'},
				{name:'startTimeStr',index:'start_time',sorttype:"string", align: 'center', width : '10'},
				{name:'endTimeStr',index:'end_time',sorttype:"string", align: 'center', width : '10'},
				{name:'status',index:'status',sorttype:"string", align: 'center', width : '10'},
				{name:'id',index:'id',hidden:true}
			],
			rowList: ${rowList},
			sortname: "start_time",
			sortorder: "desc", 
			jsonReader: {
				rowNum:"rowNum",
				root:"dataRows",          // 数据行（默认为：rows）
				page: "curPage",          // 当前页
				total: "totalPages",      // 总页数
				records: "totalRecords",  // 总记录数
				repeatitems : false       // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
			},
			pager:"#gridPager",
		});
		bindEvent();
	});
</script>
