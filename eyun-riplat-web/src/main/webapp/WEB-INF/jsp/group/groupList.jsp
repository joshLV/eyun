<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/jsp/taglib.jsp"%>
<style type="text/css">
.input_wd {
	width: 200px;
}
</style>
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
<script type="text/javascript" src="${ctx}/common/scripts/area.js"></script>

<!-- 搜索区域 start -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">
	<tr>
		<td align="right" width="8%">组名称：</td>
		<td width="15%"><input type="text" class="PublicText" id="groupName" maxlength="20" search-field="name" search-type="search" search-fieldType="S" search-operation="ALIKE" /></td>
		<td align="right" width="8%">组类型：</td>
		<td style="width: 15%">
			<div class="select PublicSelect hack">
				<input type="text" readonly="readonly" id="groupType" value="请选择" groupTypeValue="-1" search-field="type" search-type="search" search-fieldType="I" search-operation="IEQ" search-valAttrName="groupTypeValue"> <i></i>
				<ul id="type" style="z-index: 999">
					<li value="-1">请选择</li>
					<li value="0">易达用户组</li>
					<li value="1">场所组</li>
				</ul>
			</div>
		</td>
		<td colspan="2"><input type="button" class="PublicButton" value="查 询" onclick="loadPostData();"></td>
	</tr>
</table>
<!-- 搜索区域 end -->

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

<!-- 组配置 -->
<div id="dialog-form" title="新增模版素材" style="display: none; height:620px">
	<form id="groupForm" method="post">
				<!-- TAB开始 -->
				<td colspan="4">
					<div id="tabs" style="height: 580px">
						<ul>
							<li id = "href_tabs_1"><a href="#tabs-1" >场所组</a></li>
							<li id = "href_tabs_2"><a href="#tabs-2">易达用户组</a></li>
						</ul>
						<input type = "hidden" id = "selectedPanel" readOnly = "readOnly" value="#tabs-1">
						<input type = "hidden" id = "groupId" readOnly = "readOnly" value = "${groupId}">
						<input type = "hidden" id = "status" readOnly = "readOnly" value = "${status }">
						<!--------------------------------------场所组  tab 开始------------------------------------------->
						<div id="tabs-1" >
							<p>															
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable" id="placeGroup">
							<tr>
								<td align="right" width="10%">场所组名称：</td>
								<input type="hidden" class="PublicText" id="placeId" />
								<td width="15%" colspan="2"><input type="text" class="PublicText" id="placeGroupName" maxlength="20" /></td>
							    <td align="right" width="10%">场所组备注：</td>
								<td width="15%" colspan="2"><input type="text" class="PublicText" id="placeGroupRemarks" maxlength="200" /></td>
							</tr>
							<tr>
								<td align="right" width="10%">场所编号：</td>
								<td width="15%"><input type="text" class="PublicText" id="placeCode" maxlength="20" search-field="placeCode" search-type="searchPlace" search-fieldType="S" search-operation="ALIKE"/></td>
								<td align="right" width="10%">场所名称：</td>
								<td width="15%"><input type="text" class="PublicText" id="placeName" maxlength="20" search-field="placeName" search-type="searchPlace" search-fieldType="S" search-operation="ALIKE"/></td>
								<td align="right" width="10%">所属区域：</td>
								<td style="width:15%">
					            <div class="select PublicSelect hack">
					               <input type="text" readonly="readonly" id="areaId" value="请选择" areaIdValue="-1" search-field="areaId" search-type="searchPlace" search-fieldType="I" search-operation="IEQ" search-valAttrName="areaIdValue">
					                <i></i>
					                <ul id="type"style="z-index:999">
					                   <li value="-1">请选择</li>
										<c:forEach var="area" items="${areaList}">
											<li value="${area.id }">${area.name }</li>
										</c:forEach>
					                </ul>
					            </div>
					       		</td>
					       		<td align="right" width="10%">状态：</td>
								<td style="width:15%">
					            <div class="select PublicSelect hack">
					               <input type="text" readonly="readonly" id="areaId" value="请选择">
					                <i></i>
					                <ul id="type"style="z-index:999">
					                   <li value="-1">请选择</li>
					                   <li value="0">已添加</li>
					                   <li value="1">未添加</li>
					                </ul>
					            </div>
					       		</td>
								
								<td colspan="2">
									<input type="button" class="PublicButton" value="查 询" onclick="loadPostDataWithSelector('searchPlace','placeGridTable');">
								</td>
							<!-- 场所grid对应table设置 -->
							</tr>
							<table id="placeGridTable"></table>
							<div id="placeGridPager"></div>
							<!-- end -->
							</table>
							</p>
							<p>
							<!-- 已选择的场所grid对应table设置 -->
							<table id="selectedPlaceGridTable"></table>
							<div id="selectedPlaceGridPager"></div>
							<!-- end -->
							</p>
						</div>
						<!--------------------------------------场所组  tab 结束------------------------------------------->
						<!--------------------------------------用户组  tab 开始------------------------------------------->
						<div id="tabs-2" >
							<p>															
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable" id="edaGroup">
							<tr>
								<td align="right" width="10%">用户组名称：</td>
								<td width="15%" colspan="2"><input type="text" class="PublicText" id="edaGroupName" maxlength="20" /></td>
								<td align="right" width="10%">用户组备注：</td>
								<td width="15%" colspan="2"><input type="text" class="PublicText" id="edaGroupRemarks" maxlength="200" /></td>
							</tr>
							<tr>
								<td align="right" width="10%">易达编号：</td>
								<td width="15%"><input type="text" class="PublicText" id="edaCode" maxlength="20" search-field="edaCode" search-type="searchEda" search-fieldType="S" search-operation="ALIKE"/></td>
								<td align="right" width="10%">用户名称：</td>
								<td width="15%"><input type="text" class="PublicText" id="edaName" maxlength="20" search-field="edaName" search-type="searchEda" search-fieldType="S" search-operation="ALIKE"/></td>
								<td align="right" width="10%">所属区域：</td>
								<td style="width:15%">
					            <div class="select PublicSelect hack">
					               <input type="text" readonly="readonly" id="edaAreaId" value="请选择" areaIdValue="-1" search-field="areaId" search-type="searchEda" search-fieldType="I" search-operation="IEQ" search-valAttrName="areaIdValue">
					                <i></i>
					                <ul id="type"style="z-index:999">
					                   <li value="-1">请选择</li>
										<c:forEach var="area" items="${areaList}">
											<li value="${area.id }">${area.name }</li>
										</c:forEach>
					                </ul>
					            </div>
					       		</td>
					       		<td align="right" width="10%">状态：</td>
								<td style="width:15%">
					            <div class="select PublicSelect hack">
					               <input type="text" readonly="readonly" id="areaId" value="请选择">
					                <i></i>
					                <ul id="type"style="z-index:999">
					                   <li value="-1">请选择</li>
					                   <li value="0">已添加</li>
					                   <li value="1">未添加</li>										
					                </ul>
					            </div>					            
					       		</td>								
								<td colspan="2">
									<input type="button" class="PublicButton" value="查 询" onclick="loadPostDataWithSelector('searchEda','edaGridTable');">
								</td>
							</tr>
							<!-- 场所grid对应table设置 -->
							<table id="edaGridTable"></table>
							<div id="edaGridPager"></div>
							<!-- end -->
							</table>								
							</p>
							</div>
							<p>
							<!-- 已选择的场所grid对应table设置 -->
							<table id="selectedEdaGridTable"></table>
							<div id="selectedEdaGridPager"></div>
							<!-- end -->
							</p>
						</div>
						<!--------------------------------------用户组  tab 结束------------------------------------------->
				</td>
			</tr>
		</table>
</div>

<script type="text/javascript">
var selectedPanel;
    $(function () {
    	bindEvent();//绑定下拉事件
    	/* list页面grid */
    	$("#gridTable").jqGrid({
			url:'${ctx}/group/loadGrid.do',
			mtype : 'post',
			datatype : "json",
			height: 'auto',
			forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:['组ID','组名称', '组备注','组状态码','使用状态','组类型','操作'],
			colModel:[
				{name:'id',index:'id', align: 'center', sorttype:"string", width : '10',hidden:true},
				{name:'name',index:'name', align: 'center', sorttype:"string", width : '10'},
				{name:'remarks',index:'remarks',align: 'center', width : '10'},
				{name:'status',index:'status',sorttype:"string",align: 'center', width : '5',hidden:true},
				{name:'statusName',index:'statusName',align: 'center', width : '7'},
				{name:'type',index:'type',align: 'center', width : '7',hidden:true},
				{name:'operate',index:'operate',sortable: false, align: 'center', width : '5'},
			],
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
				var rows = $("#gridTable").getRowData();
				for(var i=0;i<ids.length;i++){
					previewHtml = prePreviewHtm + " onclick='previewData(" + JSON.stringify(rows[i]) + ")' " + sufPreviewHtm;
					editHtml = "";
					delHtml = "";
					if(ids[i]>0){/* 排除全部用户和全部用户时 */
						editHtml = preUpdHtm + " onclick='initDate(" + JSON.stringify(rows[i]) + ")' " + sufUpdHtm;
						delHtml = preDelHtm + " onclick='delData(" + ids[i] + ")' " + sufDelHtm;
					}
					jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:previewHtml + editHtml + delHtml});
					jQuery("#gridTable").jqGrid('setRowData',ids[i],{statusName:'启用'});
				}
			},
			loadComplete: function(xhr){
				$("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
			},
			pager:"#gridPager"
		});
    	
    	
		
        $("#dialog-form").dialog({
            autoOpen: false,
            height: 695,
            width: 900,
            modal: true,
            buttons: {
                "保存配置": function () {
                	var panelIndex = $("#selectedPanel").val();
                	var groupName;
                	var selectedIds;
                	var type;
                	var groupId;
                	var remarks;
                	var status = $("#status").val();
                	if(panelIndex=='#tabs-1'){//场所组标签页
                		groupName = $("#placeGroupName").val();
                		addSelPlace($("#placeGridTable"));
                		remarks = $("#placeGroupRemarks").val();
                		type = 1;//0：用户组，1：场所组
                	}
                	if(panelIndex=='#tabs-2'){//用户组标签页
                		groupName = $("#edaGroupName").val();
                		addSelPlace($("#edaGridTable"));
                		remarks = $("#edaGroupRemarks").val();
                		type = 0;
                	}
                	groupId = $("#groupId").val();
                	selectedIds = selectIds;
                	if (typeof(groupId) == "undefined")
                	{
                		groupId=null;
                	}
                	if(checkData()){
	                	 $.ajax({
	                         type: "POST",
	                         url: '${ctx}/group/saveOrUpdateGroup.do',
	                         dataType: 'json',
	                         data: $('#groupForm').serialize()+ "&name="+groupName+"&selectedIds="+selectedIds+"&type="+type+"&id="+groupId+"&status="+status+"&remarks="+remarks,
	                         success: function (data) {
	                        	 loadPostData();
	                        	 resultHandle(data);
	                         }
	                     });
	                    $(this).dialog("close");
                	}
                },
                "取消": function () {
                	$(this).dialog("close");
                	clearData();
                }
            },
            close: function () {
                clearData();
            }
        });

        $("#functionDiv").html(addHtm);/* 添加按钮 */
        
        $("#addFunc").click(function () {/* 添加按钮事件 */
            $("#dialog-form").dialog("option", "title", "新增组配置").dialog("open");
            loadPlaceGridTable();//点击添加按钮时，加载场所的列表信息
            loadEdaGridTable();//点击添加按钮时，加载场所的列表信息
        });
        
        $( "#tabs").tabs({
        	  activate: function( event, ui ) {
        		  $("#selectedPanel").attr("value",ui.newPanel.selector);
        	  }
        	});        
    });
    
    
    /* 加载场所组面板上gridTable */
	function loadPlaceGridTable(item,flag){
		$('#placeGridTable').jqGrid({
			url:'${ctx}/place/loadGrid.do',
			mtype : 'post',
			postData : {"groupId" : item,"isSelected":flag},
			datatype : "json",
			height: 'auto',
			forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:['场所ID','场所编号','场所名称', '所属区域','状态'],
			colModel:[
				{name:'id',index:'id', align: 'center', sorttype:"string", width : '10',hidden:true},
				{name:'placeCode',index:'placeCode', align: 'center', sorttype:"string", width : '10'},
				{name:'placeName',index:'placeName', align: 'center', sorttype:"string", width : '10'},
				{name:'areaName',index:'areaName',align: 'center', width : '10'},
				{name:'isSelected',index:'isSelected',sortable: false, align: 'center', width : '5'},
			],
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
				var rows = $("#placeGridTable").getRowData();
				for(var i=0;i<rows.length;i++){
					if(rows[i].isSelected=="1"){
						$("#placeGridTable").jqGrid('setSelection',rows[i].id,true);
						jQuery('#placeGridTable').jqGrid('setRowData',rows[i].id,{isSelected:'已添加'});
					}
					if(rows[i].isSelected=="0"){
						jQuery('#placeGridTable').jqGrid('setRowData',rows[i].id,{isSelected:'未添加'});						
					}
				}
			},
			loadComplete: function(xhr){
				$('#placeGridTable').jqGrid('setGridWidth', $("#tabs-1").innerWidth()-40);
			},
			pager:'#placeGridPager',
			multiselect: true   // 复选框设置
        });
	}
	/* 加载用户组面板上gridTable */
	function loadEdaGridTable(item,flag){
		$('#edaGridTable').jqGrid({
			url:'${ctx}/edaApp/loadGrid.do',
			mtype : 'post',
			postData : {"groupId" : item,"isSelected":flag},
			datatype : "json",
			height: 'auto',
			forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:['易达ID','易达编号','地区','用户名称', '手机号码','状态'],
			colModel:[
				{name:'id',index:'id', align: 'center', sorttype:"string", width : '10',hidden:true},
				{name:'edaId',index:'edaId', align: 'center', sorttype:"string", width : '10'},
				{name:'areaName',index:'areaName', align: 'center', sorttype:"string", width : '10'},
				{name:'edaName',index:'edaName', align: 'center', sorttype:"string", width : '10'},
				{name:'mobile',index:'mobile',align: 'center', width : '10'},
				{name:'isSelected',index:'isSelected',sortable: false, align: 'center', width : '5'},
			],
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
				var rows = $("#edaGridTable").getRowData();
				for(var i=0;i<rows.length;i++){
					if(rows[i].isSelected=="1"){
						$("#edaGridTable").jqGrid('setSelection',rows[i].id,true);
						jQuery('#edaGridTable').jqGrid('setRowData',rows[i].id,{isSelected:'已添加'});
					}
					if(rows[i].isSelected=="0"){
						jQuery('#edaGridTable').jqGrid('setRowData',rows[i].id,{isSelected:'未添加'});						
					}
				}
			},
			loadComplete: function(xhr){
				$('#edaGridTable').jqGrid('setGridWidth', $("#tabs-1").innerWidth()-40);
			}, 
			pager:'#edaGridPager',
			multiselect: true   // 复选框设置
        });
	} 
	   
	/**预览时加载场所组信息对应gridTable **/
	function loadGroupPlaceGridTable(groupId){
		$('#placeGridTable').jqGrid({
			url:'${ctx}/place/loadGrid.do',
			mtype : 'post',
			postData : {"groupId" :groupId,"isSelected":"1"},
			datatype : "json",
			height: 'auto',
			forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:['场所ID','场所编号','场所名称', '所属区域'],
			colModel:[
				{name:'id',index:'id', align: 'center', sorttype:"string", width : '10',hidden:true},
				{name:'placeCode',index:'placeCode', align: 'center', sorttype:"string", width : '10'},
				{name:'placeName',index:'placeName', align: 'center', sorttype:"string", width : '10'},
				{name:'areaName',index:'areaName',align: 'center', width : '10'},
			],
			rowList: ${rowList},
			jsonReader: {
				rowNum:"rowNum",
				root:"dataRows",          // 数据行（默认为：rows）
				page: "curPage",          // 当前页
				total: "totalPages",      // 总页数
				records: "totalRecords",  // 总记录数
				repeatitems : false       // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
			},	
			
			loadComplete: function(xhr){
				$('#placeGridTable').jqGrid('setGridWidth', $("#tabs-1").innerWidth()-40);
			},
			pager:'#placeGridPager',
			multiselect: false   // 复选框设置
        });
	}
	
	/**预览时加载用户组信息对应的gridTable**/
	function loadGroupEdaGridTable(groupId){
		$('#edaGridTable').jqGrid({
			url:'${ctx}/edaApp/loadGrid.do',
			mtype : 'post',
			postData : {"groupId" :groupId,"isSelected":"1"},
			datatype : "json",
			height: 'auto',
			forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:['易达ID','易达编号','地区','用户名称','手机号码'],
			colModel:[
				{name:'id',index:'id', align: 'center', sorttype:"string", width : '10',hidden:true},
				{name:'edaId',index:'edaId', align: 'center', sorttype:"string", width : '10'},
				{name:'areaName',index:'areaName', align: 'center', sorttype:"string", width : '10'},
				{name:'edaName',index:'edaName', align: 'center', sorttype:"string", width : '10'},
				{name:'mobile',index:'mobile',align: 'center', width : '10'},
			],
			rowList: ${rowList},
			jsonReader: {
				rowNum:"rowNum",
				root:"dataRows",          // 数据行（默认为：rows）
				page: "curPage",          // 当前页
				total: "totalPages",      // 总页数
				records: "totalRecords",  // 总记录数
				repeatitems : false       // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
			},
			loadComplete: function(xhr){
				$('#edaGridTable').jqGrid('setGridWidth', $("#tabs-2").innerWidth()-40);
			}, 
			pager:'#edaGridPager',
			multiselect: false   // 复选框设置
        });
	}
	
	/**获取组信息对应的场所和用户的列表信息**/
	function previewData(json){
		if(json.type=="0"){//用户组
			loadGroupEdaGridTable(json.id);
			$("#edaGroupName").attr("disabled","disabled");
            $("#edaGroupName").val(json.name);
            $("#edaGroupRemarks").attr("disabled","disabled");
            $("#edaGroupRemarks").val(json.remarks);
            $( "#tabs").tabs({ active: 1 });
            $("#href_tabs_1").hide();
		}
		if(json.type=="1"){//场所组
			loadGroupPlaceGridTable(json.id);
			$("#placeGroupName").attr("disabled","disabled");
            $("#placeGroupName").val(json.name);
            $("#placeGroupRemarks").attr("disabled","disabled");
            $("#placeGroupRemarks").val(json.remarks);
            $( "#tabs").tabs({ active: 0 });
            $("#href_tabs_2").hide();
		}
		$(".ui-dialog-buttonset button").eq(0).hide();
		$("#dialog-form").dialog("option", "title", "查看组配置").dialog("open");
	}
	
	/**修改组信息及对应场所的信息并初始化页面文本框的值 **/
	function initDate(json) {
		if(json.type=="0"){//用户组
			loadEdaGridTable(json.id);
			$("#edaGroupName").removeAttr("disabled");
            $("#edaGroupName").val(json.name);
            $("#edaGroupRemarks").removeAttr("disabled");
            $("#edaGroupRemarks").val(json.remarks);
            $( "#tabs").tabs({ active: 1 });
            $("#href_tabs_1").hide();
		}
		if(json.type=="1"){//场所组
			loadPlaceGridTable(json.id);
			$("#placeGroupName").removeAttr("disabled");
            $("#placeGroupName").val(json.name);
            $("#placeGroupRemarks").removeAttr("disabled");
            $("#placeGroupRemarks").val(json.remarks);
            $( "#tabs").tabs({ active: 0 });
            $("#href_tabs_2").hide();
		}
		$("#groupId").val(json.id);
		$("#status").val(json.status);
		$("#dialog-form").dialog("option", "title", "编辑组配置").dialog("open");
	}
	
	function clearData(){
		/* 卸载grid */
		$.jgrid.gridUnload("#placeGridTable");/* 场所组Grid */
        $.jgrid.gridUnload("#edaGridTable");/* 用户组Grid */
		  $("#edaGroupName").removeAttr("disabled");
          $("#edaGroupName").val("");
          $("#placeGroupName").removeAttr("disabled");
          $("#placeGroupName").val("");
          $("#placeGroupRemarks").removeAttr("disabled");
          $("#placeGroupRemarks").val("");
          $("#edaGroupRemarks").removeAttr("disabled");
          $("#edaGroupRemarks").val("");
          $("#href_tabs_1").show();
          $("#href_tabs_2").show();
          $( "#tabs").tabs({ active: 0 });
          $("#selectedEdaIds").val("");
          $("#groupId").val("");
          $(".ui-dialog-buttonset button").eq(0).show();
          $("#edaCode").val("");
          $("#edaName").val("");
          $("#placeCode").val("");
          $("#placeName").val("");          
	}
	
	function checkData(){
		var selectedPanel = $('#selectedPanel').val();
		var groupName;
		if(selectedPanel=='#tabs-1'){
			groupName = $('#placeGroupName').val();
		}
		if(selectedPanel=='#tabs-2'){
			groupName = $('#edaGroupName').val();
		}
		if(groupName==undefined || groupName==null || groupName=="undefined" || groupName.length==0 ){
			showMsg("请输入有效的组名称");
			return false;
		}
		return true;
	}	
	
	/* 删除组  */
	function delData(id) {
		showMsg("确定删除该组吗？", 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : '${ctx}/group/del.do',
				type : 'post',
				dataType : 'json',
				data : {"id" : id},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	}

    // grid自适应
    $(window).resize(function () {
        $("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
    });
    
    // 已添加场所数组
    var selectIds;
    
    /**
     * 组成员添加
     */
    function addSelPlace(selector){
        var ids = $(selector).jqGrid('getGridParam', 'selarrrow');//获取已选择的ID
        if(ids == null || ids.length == 0) {
			showMsg("请选择要添加的数据！");
			return;
		}
        selectIds = new Array();
        $.each(ids, function(i, n) {
        	// 是否已添加
        	if (-1 == $.inArray(n, selectIds)) {
        		// 修改添加列值
        		$(selector).jqGrid('setCell', n, 'isSelected', '已添加');
        		selectIds.push(n);
            }
        });
    }
</script>