<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<!doctype html><html style="width:800;height:600;overflow: hidden;">

<head>
<title>场所列表</title>
<%@ include file="/common/jsp/header.jsp"%>
</head>
<body >
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">	
		<tr>
		
		<td align="right" width="8%">用户登录名 :</td>
		<td width="15%">
			<input type="text" class="PublicText" id="searchName" maxlength="20" search-field="loginUsername" search-type="search" search-fieldType="S" search-operation="ALIKE"/>
		</td>
				
		<td align="right" width="8%">用户名称：</td>
		<td style="width:15%">
            <input type="text" class="PublicText" id="searchName" maxlength="20" search-field="userRealname" search-type="search" search-fieldType="S" search-operation="ALIKE"/>
        </td>       
		
		<td colspan="2">
			<input type="button" class="PublicButton" value="查 询" onclick="loadPostData();">
		</td>
		
		</tr>
	</table>
	
	<div class="right_c_tab">
		<table id="gridTable"></table>
		<div id="gridPager"></div>
	</div>
</body>
</html>
<script type="text/javascript">
	$(function() {
		$("#gridTable").jqGrid({
			url:'${ctx}/platformUser/loadGrid.do',
			mtype : 'post',
			datatype : "json",
 			postData : {"item" : '${item}'},
			height: 'auto',
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			multiselect: true,
			gridModel: true,
			colNames:['用户登录名','用户名称', '创建时间'],
			colModel:[
				{name:'loginUsername',index:'loginUsername', align: 'center', sorttype:"string", width : '5'},
				{name:'userRealname',index:'userRealname', align: 'center', sorttype:"string", width : '8'},
				{name:'createTime',index:'createTime',align: 'center', width : '7', hidden:true},
// 				{name:'operate',index:'operate', align: 'center', width : '8'}
			],
			rowList: ${rowList},
			jsonReader: {
				rowNum:"rowNum",
				root:"dataRows",		  // 数据行（默认为：rows）
				page: "curPage",          // 当前页
				total: "totalPages",      // 总页数
				records: "totalRecords",  // 总记录数
				repeatitems : false       // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
			},
			loadComplete: function(xhr){
				$("#gridTable").jqGrid('setGridWidth', 800);
			},
			pager:"#gridPager"
		}).navGrid('#gridPager',{edit:false,add:false,del:false,search:false,refresh : false}) 
		.navButtonAdd('#gridPager',{  
			caption:"<span style='color:red;'><b>添加</b></span>",   
			onClickButton: function(){
				addPlatformUsers();  
			},
			position:"last"  
		});
		
	});
	
	function addPlatformUsers() {
		var ids = $("#gridTable").jqGrid('getGridParam','selarrrow');
		if(ids == null && ids.length == 0) {
			showMsg("请选择要添加的的平台用户！");
			return;
		}
		$.each(ids, function(i, id) {
			var rowData = $("#gridTable").jqGrid("getRowData", id);
			renderSelectPlatformUser(rowData);
		});
		showMsg("添加成功");
		window.close();
	}
	
	function renderSelectPlatformUser(row) {
		var places = $(window.opener.document).find("#places");
		var noPlaceNode = places.find(".noPlace");
		if (noPlaceNode.length > 0) {
			noPlaceNode.remove();
		}
		var trnodes = places.find("tr");
		if (trnodes.length == 0) {
			if (places.find("#" + row.loginUsername).length == 0) {
				places.append("<tr><td style='width:30%'><span class='placeItem' style='padding:10px;'><span style='margin-left:20px;' id='" + row.loginUsername + "'><input type='hidden' class='field' name='unitCodes' value='" + row.loginUsername + "'><a href='#'>" + row.userRealname + "</a></span><span class='placeNodeClose' onclick='javascript:placeClose(this)'>X</span></span></td></tr>");
			}
		} else {
			if (places.find("#" + row.loginUsername).length == 0) {
				var ltr = $(trnodes[trnodes.length-1]);
				var tdnodes = ltr.find("td");
				if (tdnodes.length % 3 == 0) {
					places.append("<tr><td style='width:30%'><span class='placeItem' style='padding:10px;'><span style='margin-left:20px;' id='" + row.loginUsername + "'><input type='hidden' class='field' name='unitCodes' value='" + row.loginUsername + "'><a href='#'>" + row.userRealname + "</a></span><span class='placeNodeClose' onclick='javascript:placeClose(this)'>X</span></span></td></tr>");
				} else {
					ltr.append("<td style='width:30%'><span class='placeItem' style='padding:10px;'><span style='margin-left:20px;' id='" + row.loginUsername + "'><input type='hidden' class='field' name='unitCodes' value='" + row.loginUsername + "'><a href='#'>" + row.userRealname + "</a></span><span class='placeNodeClose' onclick='javascript:placeClose(this)'>X</span></span></td>");
				}
			}
		}
	}
</script>

