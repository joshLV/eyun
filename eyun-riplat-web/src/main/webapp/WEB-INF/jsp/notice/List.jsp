<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<style>
.placeNodeClose {
	margin-left:3px;color:red;cursor:pointer;
}
</style>
<title>角色列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>--%>
<script src="${ctx}/common/scripts/DatePicker/WdatePicker.js"  type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">	
		<tr>
		
		<td align="right" width="8%">通知标题：</td>
		<td width="15%"><input type="text" class="PublicText" id="searchName" maxlength="20" search-field="name" search-type="search" search-fieldType="S" search-operation="ALIKE"/></td>
				
		<td align="right" width="8%">组类型：</td>
			<td style="width:15%">
            <div class="select PublicSelect hack">
               <input type="text" readonly="readonly" value="请选择"  id="noticeLevel" groupTypeValue="-1" search-field="noticeLevel" search-type="search" search-fieldType="I" search-operation="IEQ" search-valAttrName="noticeLevelValue">
                <i></i>
                <ul id="type"style="z-index:999">
                    <li value="-1">请选择</li>
                    <li value="0">普通</li>
                    <li value="1">紧急</li>
                </ul>
            </div>
        </td>       
		
		<td colspan="2">
			<input type="button" class="PublicButton" value="查 询" onclick="loadPostData();">
		</td>
		
		</tr>
	</table>
	
	<div class="PublicC_t">
    	<ul class="operation fr" id="functionDiv"></ul>
	</div>
	
	<div class="right_c_tab">
		<table id="gridTable"></table>
		<div id="gridPager"></div>
	</div>
	
	<div id="unitDialog"></div>
	<div id="noticeDialog" style="display:none" title="新建通知">
		<div id="noticeContent">
			<form id="form1" enctype="multipart/form-data" action="#" method="post">
				<table>
					<tr>
						<td>通知标题</td>
						<td><input type="text" class="field" style="width:80%;border:1px solid #c1c1c1;" name="name" ></td>
					</tr>
					<tr>
						<td>通知类型</td>
						<td>
							<select id="noticeType" name="type" class="field" style="width:80%;border:1px solid #c1c1c1;">
								<option value="0">即时</option>
								<option value="1">定时</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>接收方类型</td>
						<td>
							<select id="accpeterType" name="accpeterType" class="field" style="width:80%;border:1px solid #c1c1c1;">
								<option value="0">用户组</option>
								<option value="1">场所组</option>
								<!-- <option value="2">用户</option>
								<option value="3">场所</option> -->
							</select>
						</td>
					</tr>
					<tr>
			            <td width="15%">接收方成员：</td>
			            <td>
			            	<fieldset style="width:80%;border:1px dotted #C4C4C4;">
			            		<legend style="margin-left:15px;color:#C4C4C4;">接收方成员</legend>
			            		<div id="choicePlaceUnit">
					            	<div id="choicePlace" style="cursor:pointer;background-color:#E8F1FF;width:100%;height:33px;margin:auto;text-align: center;line-height:33px;color: blue;" >
					            		<img src="${ctx }/images/add.png" style="margin-right: 10px;"/>添加接收方成员
					            	</div>
					            	<table id="places" style="background-color:#EFEFEF;padding: 10px;width:100%;">
							            <tr class="noPlace" style="text-align:center;"><td>无接收方成员</td></tr>
					            	</table>
				            	</div>
			            	</fieldset>
			            </td>
			        </tr>
					<tr>
						<td>通知内容</td>
						<td>
						<textarea id='content' class="field" name="content" rows='5' ></textarea>
						</td>
					</tr>
					<tr>
			            <td>附件：</td>
				        <td>
				            <input id="attachment" type="text" class="field" style="width:60%;border:1px solid #c1c1c1;" name="attachment" readonly="readonly">
				            <input id="attachPath" type="hidden" class="field" style="width:60%;border:1px solid #c1c1c1;" name="attachPath">
				            <input type="button" value="上传附件" onclick="javaScript:openWindowFixedSize('${ctx}/notice/upload.do', 'new', '430','213')">
		            	</td>
		            </tr>
		            <tr>
			            <td>紧急程度：</td>
			            <td>
			            	<select id="taskLevel" name="taskLevel" class="field" style="width:30%">
			                    <option value="0" ${task.taskLevel ==0 ? 'selected' :''  }>普通</option>
			                    <option value="1" ${task.taskLevel ==1 ? 'selected' :''  }>紧急</option>
			                </select>
			            </td>
			        </tr>
			        <tr id="quartzSendTimeRow" style="display:none;">
						<td>发送时间</td>
						<td>
							<input id="quartzSendTime" type="text" name="quartzSendTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:%m + 3'})" class="field Wdate" style="width:60%;border:1px solid #c1c1c1;">
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input name="id" class="field" type="hidden" id="id" >
							<div style="display : none;">
								<input type="reset" id="resetButton">
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	
	<!-- 组 -->
	<div id="groupDialog" style="display:none;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">	
			<tr>
				<td align="right" width="8%">组名称:</td>
				<td width="25%">
					<input type="text" class="PublicText" id="searchName" maxlength="20" search-field="name" search-type="groupSearch" search-fieldType="S" search-operation="ALIKE"/>
				</td>
				<td align="right" width="15%">组备注：</td>
				<td style="width:25%">
		            <input type="text" class="PublicText" id="searchName" maxlength="20" search-field="remarks" search-type="groupSearch" search-fieldType="S" search-operation="ALIKE"/>
		        </td>       
				<td colspan="2">
					<input type="button" class="PublicButton" value="查 询" onclick="loadPostDataWithSelector('groupSearch', 'groupGridTable');">
				</td>
			</tr>
		</table>
		<div class="right_c_tab" style="width:800;height:600;">
			<table id="groupGridTable" style="width:800;height:600;"></table>
			<div id="groupGridPager"></div>
		</div>
		<div class="notice_detail" id = "notice_detail">
		</div>
	</div>
	
	<div id="platformUserDialog" style="display:none;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">	
			<tr>
				<td align="right" width="15%">用户登录名 :</td>
				<td width="25%">
					<input type="text" class="PublicText" id="searchName" maxlength="20" search-field="loginUsername" search-type="platformUserSearch" search-fieldType="S" search-operation="ALIKE"/>
				</td>
				<td align="right" width="15%">用户名称：</td>
				<td style="width:25%">
		            <input type="text" class="PublicText" id="searchName" maxlength="20" search-field="userRealname" search-type="platformUserSearch" search-fieldType="S" search-operation="ALIKE"/>
		        </td>       
				<td colspan="2">
					<input type="button" class="PublicButton" value="查 询" onclick="loadPostDataWithSelector('platformUserSearch', 'platformUserGridTable');">
				</td>
			</tr>
		</table>
		<div class="right_c_tab" id="platformUser">
			<table id="platformUserGridTable"></table>
			<div id="platformUserGridPager"></div>
		</div>
	</div>
<script type="text/javascript">
	var ue ;
	$(function(){
		bindEvent();
		var editoroption={
				toolbars: [[
				            'source', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', '|',
				            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
				            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
				            'directionalityltr', 'directionalityrtl', 'indent', '|',
				            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', 'simpleupload', 'attachment', '|',
				            'horizontal', 'date', 'time', 'spechars'
				            ]],
			initialContent:'请填写通知内容',
			autoClearinitialContent:true,
			autoHeightEnabled:true,
			minFrameHeight:550,
			initialFrameWidth:800,  //初始化编辑器宽度,默认1000
	        initialFrameHeight:320   //初始化编辑器高度,默认320
		};
		ue = new baidu.editor.ui.Editor(editoroption);
		// var editor = new UE.ui.Editor();
		ue.render('content');
		// ue = UE.getEditor('content');
		if ('${item}' == '0') {
//			console.log("已发送")
			$("#gridTable").jqGrid({
				url:'${ctx}/notice/loadGrid.do',
				mtype : 'post',
				datatype : "json",
	 			postData : {"item" : 0},
				height: 'auto',
				forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
				altRows : true,
				colNames:['通知ID','通知标题', '状态编码', '通知状态','发送时间', '通知级别编码' , '通知级别', '附件',"操作"],
				colModel:[
					{name:'id',index:'id', align: 'center', sorttype:"string", width : '5', hidden : true},
					{name:'name',index:'name', align: 'center', sorttype:"string", width : '8'},
					{name:'status',index:'status',align: 'center', width : '7', hidden:true},
					{name:'statusName',index:'statusName',align: 'center', width : '7'},
					{name:'sendTime',index:'sendTime',align: 'center', width : '7'},
					{name:'noticeLevel',index:'noticeLevel', align: 'center', width : '8', hidden:true},
					{name:'levelName',index:'levelName', align: 'center', width : '8'},
					{name:'attachment',index:'attachment', align: 'center', width : '8', hidden:true},
					{name:'operate',index:'operate', align: 'center', width : '8'}
				],
				sortname:"sendTime",
				sortorder:"desc",
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
					var ids = $("#gridTable").getDataIDs();
					var rows = $("#gridTable").getRowData();
					for(var i=0;i<rows.length;i++){
						previewHtml = prePreviewHtm + " onclick='preData(" + ids[i] + ")' " + sufPreviewHtm;
						delHtml = preDelHtm + " onclick='delNotice(" + ids[i] + ")' " + sufDelHtm;
						jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate: previewHtml + delHtml });
					}
				},
				loadComplete: function(xhr){
					$("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
				},
				pager:"#gridPager",				
//	 			multiselect: true,    // 复选框设置
				subGrid: true,
				subGridOptions: {
	   		        "openicon"  : "ui-icon-arrowreturn-1-e",
	   				// load the subgrid data only once
	   				// and the just show/hide
	   				"reloadOnExpand" : true,
	   				// select the row when the expand column is clicked
	   				"selectOnExpand" : true
	   			},
	            subGridRowExpanded: function(subgrid_id, row_id) {
	   			    var rowData = $( "#gridTable" ).jqGrid( "getRowData" , row_id);
	   			    var noticeId = rowData.id;
	   				var subgrid_table_id, pager_id;
	   				subgrid_table_id = subgrid_id+"_t";
	   				pager_id = "p_" + subgrid_id;
	   				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table><div id='"+pager_id+"'></div>");
					jQuery("#"+subgrid_table_id).jqGrid({
						url:"${ctx}/noticeAccpeteruser/loadGrid.do",
	  	    				mtype : "post",
	  	    				postData:{'noticeId':noticeId},
	  	    				datatype: "json",
	  						colNames: ['ID', '易达账号','易达用户名','接收状态ID','接收状态','读取时间'],
	  						autowidth:true,
	  						shrinkToFit : true,
	      					forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
	  						colModel: [
	 						{name:"id", index : "id", align: 'center', width:30, hidden:true},
	   						{name:"appuserId", index : "appuserId", align: 'center', width:10},
	   						{name:"appuserName", index : "appuserName", align: 'center', width:10},
	   						{name:"status", index : "status", align: 'center', width:10 ,hidden:true},
	   						{name:"statusName", index : "statusName", align: 'center', width:10},
	   						{name:"readTimeSDF", index : "readTime", align: 'center', width:10},
	   					],jsonReader: {
	   	    		        rowNum:"rowNum",
	   	    		        root:"dataRows",          // 数据行（默认为：rows）
	   	    		        repeatitems : false       // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
	   	    		    },
	   				   	sortname: "readTime",
	   				    sortorder: "desc",
	   				    height: '100%',
	   				 	pager:"#" + pager_id,
	                    loadComplete : function() {
// 	                        afterGridWidth = $(".right_c").innerWidth();
// 	                        $("#gridTable").jqGrid('setGridWidth',afterGridWidth);
// 	                        $("#" + subgrid_table_id).jqGrid('setGridWidth',afterGridWidth - 28);
	                    }
	  				});
	   				jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false,search:false,refresh : false});
	   			}
			});
		} 
		else {
			$("#gridTable").jqGrid({
				url:'${ctx}/notice/loadGrid.do',
				mtype : 'post',
				datatype : "json",
	 			postData : {"item" : '${item}'},
				height: 'auto',
				forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
				altRows : true,
				colNames:['通知ID','通知标题', '状态编码', '通知状态', '通知级别编码', '通知级别', '接收方类型', '附件', '操作'],
				colModel:[
					{name:'id',index:'id', align: 'center', sorttype:"string", width : '5', hidden:true},
					{name:'name',index:'name', align: 'center', sorttype:"string", width : '8'},
					{name:'status',index:'status',align: 'center', width : '7', hidden:true},
					{name:'statusName',index:'statusName',align: 'center', width : '7'},
					{name:'noticeLevel',index:'noticeLevel', align: 'center', width : '8', hidden:true},
					{name:'levelName',index:'levelName', align: 'center', width : '8'},
					{name:'accpeterType',index:'accpeterType', align: 'center', width : '8', hidden:true},
					{name:'attachment',index:'attachment', align: 'center', width : '8', hidden:true},
					{name:'operate',index:'operate', align: 'center', width : '8'}
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
				gridComplete:function(rowid, rowdata){//草稿箱列表
					var ids = $("#gridTable").getDataIDs();//jqGrid('getDataIDs');
					var rows = $("#gridTable").getRowData();
					for(var i=0;i<rows.length;i++){
						sendHtml = preSendHtm + " onclick='sendNotice(" + ids[i] + ")' " + sufSendHtm;
						editHtml = preUpdHtm + " onclick='editNotice(" + JSON.stringify(rows[i]) + ")' " + sufUpdHtm;
						delHtml = preDelHtm + " onclick='delNotice(" + ids[i] + ")' " + sufDelHtm;
						jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:sendHtml + editHtml + delHtml});
					}
				},
				loadComplete: function(xhr){
					$("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
				},
				pager:"#gridPager"
			});
		}
		
		$("#functionDiv").html(addHtm);
		
		$("#addFunc").click(function () {/* 添加按钮事件 */
			openDailog();
        });
		
		$("#choicePlace").click(function() {
			var accpeterType = $("#accpeterType").val();
			//console.log(accpeterType);
			if (accpeterType == 2) {//选择用户
				//console.log("选择用户");
				$("#platformUserDialog").dialog("option", "title", "选择平台用户").dialog("open");
				$("#platformUserGridTable").jqGrid({
					url:'${ctx}/edaApp/loadGrid.do',
					mtype : 'post',
					datatype : "json",
		 			postData : {"item" : accpeterType},
					height: 'auto',
					forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
					altRows : true,
					multiselect: true,
					gridModel: true,
					colNames:['易达id','易达号','用户名', '手机号码'],
					colModel:[
						{name:'id',index:'id', align: 'center', sorttype:"string", width : '5' ,hidden:true},
						{name:'edaId',index:'edaId', align: 'center', sorttype:"string", width : '5'},
						{name:'userName',index:'userName', align: 'center', sorttype:"string", width : '8'},
						{name:'mobile',index:'mobile',align: 'center', width : '7' },
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
						$("#platformUserGridTable").jqGrid('setGridWidth', 800);
					},
					pager:"#platformUserGridPager"
				}).navGrid('#platformUserGridPager',{edit:false,add:false,del:false,search:false,refresh : false}) 
				.navButtonAdd('#platformUserGridPager',{  
					caption:"<span style='color:red;'><b>添加</b></span>",   
					onClickButton: function(){
						addPlatformUsers();  
					},
					position:"last"
				});
				
			}else if (accpeterType == 3) {//选择场所
				//console.log("选择场所");
				$("#platformUserDialog").dialog("option", "title", "选择平台用户").dialog("open");
				$("#platformUserGridTable").jqGrid({
					url:'${ctx}/place/loadGrid.do',
					mtype : 'post',
					datatype : "json",
		 			postData : {"item" : accpeterType},
					height: 'auto',
					forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
					altRows : true,
					multiselect: true,
					gridModel: true,
					colNames:['场所id','场所编号','场所名称', '所属区域'],
					colModel:[
						{name:'id',index:'id', align: 'center', sorttype:"string", width : '5', hidden:true},
						{name:'placeCode',index:'placeCode', align: 'center', sorttype:"string", width : '5'},
						{name:'placeName',index:'placeName', align: 'center', sorttype:"string", width : '8'},
						{name:'areaName',index:'areaName',align: 'center', width : '7'},
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
						$("#platformUserGridTable").jqGrid('setGridWidth', 800);
					},
					pager:"#platformUserGridPager"
				}).navGrid('#platformUserGridPager',{edit:false,add:false,del:false,search:false,refresh : false}) 
				.navButtonAdd('#platformUserGridPager',{  
					caption:"<span style='color:red;'><b>添加</b></span>",   
					onClickButton: function(){
						addPlatformUsers();  
					},
					position:"last"  
				});
				
			} else {
				//console.log("组");
				$("#groupDialog").dialog("option", "title", "选择分组").dialog("open");
				$("#groupGridTable").jqGrid({
					url:'${ctx}/group/loadGrid.do',
					mtype : 'post',
					datatype : "json",
		 			postData : {"item" : accpeterType},
					height: 'auto',
					forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
					altRows : true,
					multiselect: true,
					colNames:['组ID','组名称', '组备注'],
					colModel:[
						{name:'id',index:'id', align: 'center', sorttype:"string", width : '5', hidden:true},
						{name:'name',index:'name', align: 'center', sorttype:"string", width : '8'},
						{name:'remarks',index:'status',align: 'center', width : '7'},
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
						$("#groupGridTable").jqGrid('setGridWidth', 800);
					},
					pager:"#groupGridPager"
				}).navGrid('#groupGridPager',{edit:false,add:false,del:false,search:false,refresh : false}) 
				.navButtonAdd('#groupGridPager',{  
					caption:"<span style='color:red;'><b>添加</b></span>",   
					onClickButton: function(){
						addGroups();  
					},
					position:"last"  
				});;
			}
		});
		
		$("#noticeType").change(function() {
			if ($(this).val() == 0) {
				$("#quartzSendTimeRow").hide();
			} else {
				$("#quartzSendTimeRow").show();
			}
		});
		
		$("#accpeterType").change(function() {
			$("#places").html('<tr class="noPlace" style="text-align:center;"><td>无接收方成员</td></tr>');
		});
	
		
		$("#platformUserDialog").dialog({
            autoOpen: false,
            height: 600,
            width: 832,
            modal: true,
            buttons: {
                "取消": function () {
                	/** jqgeid卸载 **/
                	$.jgrid.gridUnload("#platformUserGridTable");
                    $(this).dialog("close");
                }
            },
            close: function () {
                /** jqgeid卸载 **/
               $.jgrid.gridUnload("#platformUserGridTable");
            }
        });
		
		$("#groupDialog").dialog({
	           autoOpen: false,
	           height: 600,
	           width: 832,
	           modal: true,
	           buttons: {
	               "取消": function () {
	               	/** jqgeid卸载 **/
	               	$.jgrid.gridUnload("#groupGridTable");
	                   $(this).dialog("close");
	               }
	           },
	           close: function () {
	               /** jqgeid卸载 **/
	              $.jgrid.gridUnload("#groupGridTable");
	           }
	       });
		
	});
	
	function addPlatformUsers() {
		var ids = $("#platformUserGridTable").jqGrid('getGridParam','selarrrow');
		if(ids == null && ids.length == 0) {
			showMsg("请选择要添加的的平台用户！");
			return;
		}
		$.each(ids, function(i, id) {
			var rowData = $("#platformUserGridTable").jqGrid("getRowData", id);
			renderSelectPlatformUser(rowData);
		});
		showMsg("添加成功");
		 $("#platformUserDialog").dialog("close");
	}
	
	function renderSelectPlatformUser(row) {
		var places = $("#places");
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
	
	
	function addGroups() {
		var ids = $("#groupGridTable").jqGrid('getGridParam','selarrrow');
		if(ids == null && ids.length == 0) {
			showMsg("请选择要添加的的平台用户！");
			return;
		}
		$.each(ids, function(i, id) {
			var rowData = $("#groupGridTable").jqGrid("getRowData", id);
			renderSelectGroup(rowData);
		});
		showMsg("添加成功");
		// window.close();
		$("#groupDialog").dialog("close");
	}
	function renderSelectGroup(row) {
		var places = $("#places");
		var noPlaceNode = places.find(".noPlace");
		if (noPlaceNode.length > 0) {
			noPlaceNode.remove();
		}
		
		var trnodes = places.find("tr");
		if (trnodes.length == 0) {
			if (places.find("#" + row.id).length == 0) {
				places.append("<tr><td style='width:30%'><span class='placeItem' style='padding:10px;'><span style='margin-left:20px;' id='" + row.id + "'><input type='hidden' class='field' name='unitCodes' value='" + row.id + "'><a href='#'>" + row.name + "</a></span><span class='placeNodeClose' onclick='javascript:placeClose(this)'>X</span></span></td></tr>");
			}
		} else {
			if (places.find("#" + row.id).length == 0) {
				var ltr = $(trnodes[trnodes.length-1]);
				var tdnodes = ltr.find("td");
				if (tdnodes.length % 3 == 0) {
					places.append("<tr><td style='width:30%'><span class='placeItem' style='padding:10px;'><span style='margin-left:20px;' id='" + row.id + "'><input type='hidden' class='field' name='unitCodes' value='" + row.id + "'><a href='#'>" + row.name + "</a></span><span class='placeNodeClose' onclick='javascript:placeClose(this)'>X</span></span></td></tr>");
				} else {
					ltr.append("<td style='width:30%'><span class='placeItem' style='padding:10px;'><span style='margin-left:20px;' id='" + row.id + "'><input type='hidden' class='field' name='unitCodes' value='" + row.id + "'><a href='#'>" + row.name + "</a></span><span class='placeNodeClose' onclick='javascript:placeClose(this)'>X</span></span></td>");
				}
			}
		}
	}
	
	function openWindowFixedSize(url, name, iWidth, iHeight) {
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;		
		window.open(url, name+""+new Date().getTime(), "height=" + iHeight +  ",width=" + iWidth + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
	}
	function openWindow(url, name, iWidth, iHeight) {
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
		window.open(url, name+""+new Date().getTime(), "height=" + iHeight +  ",width=" + iWidth + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}
	
	//打开资源dialog
	function openDailog() {
		$("#noticeDialog").dialog({
			resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			buttons : {
				"发送" : function() {
					saveOrUpdateNotice(0);
				},
				"存草稿" : function() {
					saveOrUpdateNotice(1);
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
	
	//保存或修改资源
	function saveOrUpdateNotice(item) {
		var noticeId = $("#id").val();
		var msg = "确定保存该通知？";
		if (noticeId != '') {
			msg = "确定修改该通知？";
		}
		if(checkAccept()==false){
			return;
		}
		showMsg(msg, 2, function() {
			$(this).dialog('close');
			$("#form1").attr("action", "${ctx}/notice/saveOrUpdate.do");
			$.ajax({
				url : '${ctx}/notice/saveOrUpdate.do',
				type : 'post',
				dataType : 'json',
				data : $('.field').serialize() + "&item=" + item,
				success : function(data) {
					closeDialog();//关闭编辑窗口
					resultHandle(data);//刷新列表
				}
			});
		});
	}
	
	function editNotice(row) {
//		console.log(row);
		$.ajax({
			url : '${ctx}/notice/getNoticeById.do',
			type : 'post',
			dataType : 'json',
			data : {"id" : row.id, "accpeterType" : row.accpeterType},
			success : function(root) {
				var data = root.data;
				$("#id").val(data.id);
				$("input[name='name']").val(data.name);
				$("#noticeType option[value='" + data.type + "']").attr("selected","selected");
				$("#accpeterType option[value='" + data.accpeterType + "']").attr("selected","selected");
				if(data.content!=null && data.content!=''){
					ue.setContent(data.content);
				}
				$("#attachment").val(data.attachment);
				$("#attachPath").val(data.attachPath);
				$("#taskLevel option[value='" + data.taskLevel + "']").attr("selected","selected");
				if (data.type == 0) {
					$("#quartzSendTimeRow").hide();
				} else {
					$("#quartzSendTimeRow").show();
					$("#quartzSendTime").val(data.quartzSendTime);
				}
				var units = data.units;
				$.each(data.units, function(i, v) {
					var places = $("#places");
					var noPlaceNode = places.find(".noPlace");
					if (noPlaceNode.length > 0) {
						noPlaceNode.remove();
					}
					var trnodes = places.find("tr");
					if (trnodes.length == 0) {
						if (places.find("#" + v.code).length == 0) {
							places.append("<tr><td style='width:30%'><span class='placeItem' style='padding:10px;'><span style='margin-left:20px;' id='" + v.code + "'><input type='hidden' class='field' name='unitCodes' value='" + v.code + "'><a href='#'>" + v.name + "</a></span><span class='placeNodeClose' onclick='javascript:placeClose(this)'>X</span></span></td></tr>");
						}
					} else {
						if (places.find("#" + v.code).length == 0) {
							var ltr = $(trnodes[trnodes.length-1]);
							var tdnodes = ltr.find("td");
							if (tdnodes.length % 3 == 0) {
								places.append("<tr><td style='width:30%'><span class='placeItem' style='padding:10px;'><span style='margin-left:20px;' id='" + v.code + "'><input type='hidden' class='field' name='unitCodes' value='" + v.code + "'><a href='#'>" + v.name + "</a></span><span class='placeNodeClose' onclick='javascript:placeClose(this)'>X</span></span></td></tr>");
							} else {
								ltr.append("<td style='width:30%'><span class='placeItem' style='padding:10px;'><span style='margin-left:20px;' id='" + v.code + "'><input type='hidden' class='field' name='unitCodes' value='" + v.code + "'><a href='#'>" + v.name + "</a></span><span class='placeNodeClose' onclick='javascript:placeClose(this)'>X</span></span></td>");
							}
						}
					}
				});
			}
		});
		openDailog();
	}
	
	function sendNotice(id) {
		showMsg("发送通知？", 2, function() {
			$.ajax({
				url : '${ctx}/notice/send.do',
				type : 'post',
				dataType : 'json',
				data : {"id" : id},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	}
	
	function preData(id){
		$.ajax({
			url:'${ctx}/notice/noticeDetail.do',
			data : {"recordId" : id},
			type:'post',
			dataType:'html',
			success:function(rs) {
				$("#notice_detail").append(rs);

				//显示对话框
				$( "#notice_detail" ).dialog({
					height: 800,
					width:  1000,
					modal: true,
					close: function () {
						$("#notice_detail").empty();
					}
				});


			}
		});
		

	}

	
	//删除通知
	function delNotice(id) {
		showMsg("确定删除该通知吗？", 2, function() {
			$.ajax({
				url : '${ctx}/notice/del.do',
				type : 'post',
				dataType : 'json',
				async : true,
				data : {"id" : id},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	}
	
	//返回结果处理
	function resultHandle(data) {
		if(data.status == "SUCC") {
			refresh();
			showWaittingMsg(data.msg, 4000);
		} else {
			showWaittingMsg(data.msg, 4000);
		}
	}
	
	//jqgrid刷新
	function refresh() {
		jQuery("#gridTable").trigger("reloadGrid");
	}
	
	//关闭dialog时清除数据等，以免造成保存和编辑混乱
	function closeDialog() {
		$("#resourceId").val("");
		$("#oldName").val("");
		clearData();
		$("#noticeDialog").dialog("close");
	}
	
	//清除页面数据
	function clearData(){
		$("#resetButton").click();
		$("#places").html('<tr class="noPlace" style="text-align:center;"><td>无单位信息</td></tr>');
		ue.setContent("");
	}
	
	function placeClose(obj) {
		$(obj).parent().parent().remove();
		if ($("#places").find(".placeItem").length == 0) {
			$("#places").html('<tr class="noPlace" style="text-align:center;"><td>无单位信息</td></tr>');
		}
	}
	/* 检查接受通知的成员是否为空 */
	function checkAccept(){
		var vals = $("input[name='unitCodes']");
		if(vals.length <= 0){
			showWaittingMsg("请添加接受方成员 ！", 5000);
			return false;
		}
		return true;
	}
</script>
