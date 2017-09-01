<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
   
   <div>
		<input type="hidden" id="writePower" value="${writePower}" />
		<input type="hidden" id="examinePower" value="${examinePower}" />
		<input type="hidden" id="releasePower" value="${releasePower}" />
		<input type="hidden" id="delegatePower" value="${delegatePower}" />
		<input type="hidden" id="lockPower" value="${lockPower}" />
		<input type="hidden" id="recommendPower" value="${recommendPower}" />
	</div>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">
		<tr>
			<td align="right" width="8%">数据标签：</td>
			<td width="15%"><input type="text" class="PublicText" id="searchName" search-field="datalabel" search-type="search" search-fieldType="S" search-operation="ALIKE"/></td>
			<td colspan="2">
				<input type="button" class="PublicButton" value="查 询" onclick="loadPostData();">
				<input type="button" class="PublicButton" value="重置" onclick="resetSearch();"/>
			</td>
		</tr>
	</table>
	
    <div class="PublicC_t">
		<ul class="operation fr" id="functionDiv"></ul>
	</div>

<div>
	<div class="right_c_tab">
		<!-- grid对应table设置 -->
		<table id="gridTable"></table>
		<div id="gridPager"></div>
		<!-- end -->
	</div>
</div>

<div id="dictionaryDialog" style="display : none;">
	<form>
		<table class="PublicTable">
			<tr>
				<td>数据标签：</td>
				<td><input type="text" id="dataLabel" class="PublicText" maxlength="12"/></td>
			</tr>
			<tr>
				<td>编码：</td>
				<td><input type="text" id="dataCode" class="PublicText" maxlength="6"/></td>
				<td>值：</td>
				<td><input type="text" id="dataValue" class="PublicText" maxlength="20"/></td>
			</tr>
			
			<tr>
				<td>备注：</td>
				<td colspan="3"><textarea id="remark" class="PublicText" 
							style="width: 316px; height: 80px; resize: none;" maxlength="100"></textarea></td>
			</tr>
			
			<tr>
				<div style="display : none;">
					<input type="reset" id="resetButton">
				</div>
			</tr>
			
		</table>
	</form>
</div>

<script type="text/javascript">
    
	$("#functionDiv").append(addHtm);
	/*加载数据字典列表*/
	$(function(){
		$("#gridTable").jqGrid({
			url:'${ctx}/system/dict/loadGrid.do',
			mtype : "post",
			datatype : "json",
			postData : {"creator" : ${creator}},
			height: "auto",
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:["数据标签","编码","值", "备注说明", "状态", "创建时间", "更新时间","操作"],
			colModel:[
				{name:"dataLabel",index:"dataLabel",sorttype:"string",align: "center", width : "10"},
				{name:"dataCode",index:"dataCode",sorttype:"string",align: "center", width : "10"},
				{name:"dataValue",index:"dataValue",sorttype:"string",align: "center", width : "10"},
				{name:"remark",index:"remarks",sorttype:"string", sorttype:"string", align: "center", width : "10"},
				{name:"status",index:"status",sorttype:"string", align: "center", width : "10"},
				{name:"createTimeStr",index:"createTime", sorttype:"string",align: "center", width : "20",hidden:true},
				{name:"updateTimeStr",index:"updateTime", sorttype:"string",align: "center", width : "20",hidden:true},
				{name:'operate',index:'operate',sortable: false, align: 'center', width : '10'},
			],
			sortname: "datalabel",
			sortorder: "desc", 
			rowList: ${rowList},
			jsonReader: {
				rowNum:"rowNum",
				root:"dataRows",          // 数据行（默认为：rows）
				page: "curPage",          // 当前页
				total: "totalPages",      // 总页数
				records: "totalRecords",  // 总记录数
				repeatitems : false       // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
			},
			
			gridComplete:function(rowid, rowdata){
				
				var ids = $("#gridTable").getDataIDs();//jqGrid('getDataIDs');
				for(var i=0;i<ids.length;i++){
					editHtml = preUpdHtm + " onclick='getDict(" + ids[i] + ")' " + sufUpdHtm;
					delHtml = preDelHtm + " onclick='deleteDict(" + ids[i] + ")' " + sufDelHtm;
					jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:editHtml + delHtml});
				}
				
			},
			pager:"#gridPager",
			multiselect: true,    // 复选框设置
		}).navGrid('#gridPager',{edit:false,add:false,del:false,search:false,refresh : false}) 
		.navButtonAdd('#gridPager',{  
			caption:"<span style='color:red;'><b>批量删除</b></span>",   
			onClickButton: function(){
				deleteDicts();  
			},
			position:"last"  
		});
		
		bindEvent();
	});

	/* 添加 */
	$("#addFunc").click(function() {
       /*打开数据字典保存对话框*/
		openSaveDialog();
	});
	
	/* 打开数据字典保存的对话框 */
	function openSaveDialog() {
		
		if($("#dataLabel").attr("disabled")){
			$("#dataLabel").removeAttr("disabled");
		}
		if($("#dataCode").attr("disabled")){
		  $("#dataCode").removeAttr("disabled");
		}
		
		$("#dictionaryDialog").dialog({
			resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			buttons : {
				"确定" : function() {
					saveDict();
				},
				"取消" : function() {
					closeDialog();
				} 
			},
			close : function() {
				closeDialog();
			}
		});
	}
	
 	/*数据保存*/
	function saveDict() {
		var msg = "确定保存该条数据吗？";
		var dataLabel = $("#dataLabel").val();
		var dataCode = $("#dataCode").val();
		var dataValue = $("#dataValue").val();
		var remark = $("#remark").val();
	
		
		if(dataLabel == null || dataLabel == "") {
			showMsg("数据标签不能为空！");
			return;
		}
		
		if(dataCode == null || dataCode == "") {
			showMsg("编码不能为空！");
			return;
		}
		
		if(dataValue == null || dataValue == "") {
			showMsg("值不能为空！");
			return;
		}
		
		showMsg(msg, 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : '${ctx}/system/dict/saveDict.do',
				type : 'post',
				dataType : 'json',
				data : {"dataLabel" : dataLabel, "dataCode" : dataCode, "dataValue" : dataValue, "remark" : remark},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	} 
 	
	/*获得某条数据字典的信息*/
	function getDict(id) {
		var rowData = $("#gridTable").jqGrid("getRowData",id);
		//将dataCode元素改为不可编辑
		$("#dataLabel").attr("disabled",true);
		$("#dataCode").attr("disabled",true);
		//获得需要编辑的数据
		$("#dataLabel").val(rowData.dataLabel);
		$("#dataCode").val(rowData.dataCode);
		$("#dataValue").val(rowData.dataValue);
		$("#remark").val(rowData.remark);
		
		openUpdDialog();
	}
 	
	/* 打开修改数据字典对话框 */
	function openUpdDialog(){
		$("#dictionaryDialog").dialog({
			//resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			buttons : {
				"确定" : function() {
					updateDict();
				},
				"取消" : function() {
					closeDialog();
				} 
			},
			close : function() {
				closeDialog();
			}
		 });
		}
	
    /*确认修改发送请求至后台controller*/
	function updateDict(){
		var msg = "确定修改该条数据吗？";
		var dataLabel = $("#dataLabel").val();
		var dataCode = $("#dataCode").val();
		var dataValue = $("#dataValue").val();
		var remark = $("#remark").val();
		
		
		if(dataLabel == null || dataLabel == "") {
			showMsg("数据标签不能为空！");
			return;
		}
		
		if(dataCode == null || dataCode == "") {
			showMsg("编码不能为空！");
			return;
		}
		
		if(dataValue == null || dataValue == "") {
			showMsg("值不能为空！");
			return;
		}
		
		showMsg(msg, 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : '${ctx}/system/dict/updateDict.do',
				type : 'post',
				dataType : 'json',
				data : {"dataLabel" : dataLabel, "dataCode" : dataCode, "dataValue" : dataValue, "remark" : remark},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	}
	
    /*批量删除数据字典前置工作，获取被选中数据字典id*/
	function deleteDicts() {
		var selIds = $("#gridTable").jqGrid('getGridParam','selarrrow');
		if(selIds == null || selIds.length == 0) {
			showMsg("请选择删除的数据字典！");
			return;
		}
		var ids = selIds.join(",");
		deleteDict(ids);
	}
	
    /*删除单个或批量删除数据字典*/
	function deleteDict(id){
		var ids = (id + "").split(",");
		var datas = "";
		var num = ids.length;
		
		for(var i = 0; i < num; i++) {
			datas += $("#gridTable").jqGrid("getRowData",ids[i]).dataCode+",";
		}
		
		if(datas != ""){
			datas = datas.substring(0, datas.length-1);
		}
		
		showMsg("确定要删除这" + num + "数据吗？", 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : '${ctx}/system/dict/deleteDict.do',
				type : "post",
				dataType : "json",
				data : {"searchValue" : datas},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	}
    

	/* grid自适应 */
	$(window).resize(function(){
		$("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
	});
	
	
	/* 返回结果处理 */
	function resultHandle(data) {
		if(data.status == "SUCC") {
			refresh();
			showWaittingMsg(data.msg, 4000);
			closeDialog();
		} else {
			showWaittingMsg(data.msg, 4000);
		}
	}
	
	/* jqgrid刷新 */
	function refresh() {
		jQuery("#gridTable").trigger("reloadGrid");
	}
	
	/* 关闭dialog时清除数据，以免造成保存和编辑混乱 */
	function closeDialog() {
		clearData();
		$("#dictionaryDialog").dialog("close");
	}
	
	/* 清除页面数据 */
	function clearData(){
		$("#resetButton").click();
	}
	
	//查询重置
	function resetSearch() {
		$("#searchName").val("");
		loadPostData();
	}
</script>
