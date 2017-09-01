<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/common/scripts/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
<style>
	.PublicTable tr td span{color: red;}
</style>

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
			<td align="right" width="8%">资源名称：</td>
			<td width="15%"><input type="text" class="PublicText" id="searchName" maxlength="20" search-field="name" search-type="search" search-fieldType="S" search-operation="ALIKE"/></td>
			<td align="right" width="5%">时间：</td>
			<td style="width:35%">
			<div class="PublicTime">
				<input type="text" style="width:42%" class="PublicText TextTime" id="datePickerLeft"  search-field="create_time" search-type="search" search-fieldType="D" search-operation="GE">
				<u>至</u>
				<input type="text" style="width:42%" class="PublicText TextTime" id="datePickerRight" search-field="create_time" search-type="search" search-fieldType="D" search-operation="LE">
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
		
	<div id="resourceDialog" style="display : none;">
		<form>
			<table class="PublicTable">
				<tr>
					<td>资源名称<span>*</span></td>
					<td>
						<input type="text" id="name" maxlength="40" class="PublicText"/>
					</td>
					<td>资源code：</td>
					<td>
						<input type="text" id="code" maxlength="40" class="PublicText"/>
					</td>
				</tr>
				<tr>
					<td>资源类型：</td>
					<td>
						<div class="select PublicSelect hack">
							<input type="text" value="URL" readonly="readonly" id="type" typeValue="0">
							<i></i>
							<ul id="typeUl" style="z-index : 9999">
								<li value="0">URL</li>
							</ul>
						</div>
					</td>
					<td>上级菜单：</td>
					<td>
						<div class="select PublicSelect hack">
							<input type="text" value="主菜单" readonly="readonly" id="pid" menuLevel="0" pid="-1">
							<i></i>
							<ul id="pidUl" style="z-index : 9999">
								
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<td>URL：</td>
					<td>
						<input type="text" id="url" maxlength="50" class="PublicText"/>
					</td>
					<td>图片</td>
					<td>
						<input type="text" id="icon" maxlength="50" class="PublicText"/>
					</td>
				</tr>
				<tr>
					<td>状态：</td>
					<td>
						<label><input name="status" type="radio" value="e" checked="true" />启用</label>
						<label><input name="status" type="radio" value="d" />停用</label>
					</td>
					<td>排序：<span>*</span></td>
					<td>
						<input type="text" maxlength="2" id="sort" class="PublicText"/>
					</td>
				</tr>
				<tr id="privilegeTr" style="display : none;">
					<td>权限：</td>
					<td class="PublicLabel" colspan="2">
						<p>
							<label><input name="privilege" type="checkbox" value="1" />写</label>
							<label><input name="privilege" type="checkbox" value="2" />审核</label>
							<label><input name="privilege" type="checkbox" value="3" />发布</label>
							<label><input name="privilege" type="checkbox" value="4" />委派</label>
							<label><input name="privilege" type="checkbox" value="5" />锁定</label>
							<label><input name="privilege" type="checkbox" value="6" />推荐</label>
						</p>
						<!-- 暂时不用 -->
						<div style="display : none;" >
							<p>
								<label><input name="privilege" type="checkbox" value="7" />其他</label>
								<label><input name="privilege" type="checkbox" value="8" />其他</label>
								<label><input name="privilege" type="checkbox" value="9" />其他</label>
							</p>
						</div>
					</td>
				</tr>
				<tr>
					<td>描述：</td>
					<td colspan="3">
						<textarea id="remark" maxlength="100" class="PublicText" style="width: 360px; height: 80px; resize: none;"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="hidden" id="resourceId" >
						<div style="display : none;">
							<input type="reset" id="resetButton">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>

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
		
		//添加按钮
		$("#functionDiv").append(addHtm);
		$("#addFunc").click(function() {
			openDailog();
		});
		
		$("#gridTable").jqGrid({
			url:'${ctx}/system/resource/getGridList.do',
			mtype : 'post',
			datatype : "json",
			height: 'auto',
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:['资源ID', '上级菜单','资源名称', '资源类型', '资源描述', '资源url', '资源图片' , '创建人', '创建时间', '更新人', '更新时间', '是否停用', '操作', 'type', 'pid', 'sort', 'menuLevel', 'code', 'privilege'],
			colModel:[
				{name:'id',index:'id', align: 'center', sorttype:"string", width : '5',hidden:true},
				{name:'preMenu',index:'preMenu', align: 'center', width : '8'},
				{name:'name',index:'name', align: 'center', sorttype:"string", width : '8'},
				{name:'typeName',index:'type',align: 'center', width : '7'},
				{name:'remark',index:'remark', align: 'center', width : '8'},
				{name:'url',index:'url', align: 'center', width : '15'},
				{name:'icon',index:'icon', align: 'center', width : '8'},
				{name:'creatorName',index:'creator', sorttype:"string", align: 'center', width : '10'},
				{name:'createTimeStr',index:'create_time', sorttype:"string", align: 'center', width : '15'},
				{name:'updatorName',index:'updator', sorttype:"string", align: 'center', width : '8'},
				{name:'updateTimeStr',index:'update_time', sorttype:"string", align: 'center', width : '15'},
				{name:'status',index:'status', sorttype:"string", align: 'center', width : '7'},
				{name:'operate',index:'operate',sortable: false, align: 'center', width : '10'},
				{name:'type',index:'type',hidden:true},
				{name:'pid',index:'pid',hidden:true},
				{name:'sort',index:'sort',hidden:true},
				{name:'menuLevel',index:'menuLevel',hidden:true},
				{name:'code',index:'code',hidden:true},
				{name:'privilege',index:'privilege',hidden:true}
			],
			rowList: ${rowList},
			sortname: "preMenu",
			sortorder: "desc", 
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
					editHtml = preUpdHtm + " onclick='initData(" + ids[i] + ")' " + sufUpdHtm;
					delHtml = preDelHtm + " onclick='delResource(" + ids[i] + ")' " + sufDelHtm;
					jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:editHtml + delHtml});
				}
				//异步加载js，确定拥有的权限
				$("body").append("<script type='text/javascript' src='${ctx}/scripts/authority.js'><\/script>");
			},
			pager:"#gridPager",
// 			multiselect: true,    // 复选框设置
		});
		bindEvent();
	});
	
	//打开资源dialog
	function openDailog() {
		changeResource();
		$("#resourceDialog").dialog({
			resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			buttons : {
				"确定" : function() {
					saveOrUpdateResource();
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
	
	//编辑前置动作
	function initData(id){
		var rowData = $("#gridTable").jqGrid('getRowData', id);
		$("#resourceId").val(rowData.id);
		$("#name").val(rowData.name);
		$("#typeUl li[value=" + rowData.type + "]").click();
		$("#url").val(rowData.url);
		$("#icon").val(rowData.icon);
		$("#remark").val(rowData.remark);
		$("#sort").val(rowData.sort);
		$("#code").val(rowData.code);
		var menuLevel = rowData.menuLevel;
		if(menuLevel == '3') {
			$("#privilegeTr").show();
			setResPrivilege(rowData.privilege);
		} else {
			$("#privilegeTr").hide();
		};
		var status = rowData.status;
		if("启用" == status) {
			$("input[value='e']").prop('checked', true);
		} else {
			$("input[value='d']").prop('checked', true);
		}
		openDailog();
		$("#pidUl li[value='" + rowData.id + "']").hide();
		$("#pidUl li[pid='" + rowData.id + "']").hide();
		$("#pidUl li[value=" + rowData.pid + "]").click();
	}
	
	//设置三级菜单权限
	function setResPrivilege(privilege) {
		if(privilege != null && privilege != undefined && privilege != "") {
			for(var i = 1; i < privilege.length; i++) {
				var priCode = privilege.substring(i, i + 1);
				if("Y" == priCode) {
					$("input[name='privilege'][value=" + i + "]").prop("checked", true);
				} else {
					$("input[name='privilege'][value=" + i + "]").prop("checked", false);
				}
			}
		}
	}
	
	//保存或修改资源
	function saveOrUpdateResource() {
		var toUrl = "";
		var name = $("#name").val();
		if(name == null || name == "") {
			showMsg("请输入菜单名！");
			return;
		}
		var type = $("#type").attr("typeValue");
		var pid = $("#pid").attr("pid");
		var url = $("#url").val();
		var icon = $("#icon").val();
		var status = $("input[name='status']:checked").val();
		if("e" == status) {
			status = "启用";
		} else {
			status = "禁用";
		}
		var remark = $("#remark").val();
		var id = $("#resourceId").val();
		var msg = "";
		if(id != null && id != "") {
			toUrl = '${ctx}/system/resource/updateResource.do';
			msg = "确定修改该资源？";
		} else {
			toUrl = '${ctx}/system/resource/saveResource.do';
			msg = "确定保存该资源吗?";
		}
		var sort = $("#sort").val();
		if(sort == null || sort == "") {
			showMsg("请输入排序号！");
			return;
		}
		var sortReg = /^[0-9]{1,2}$/;
		if(!sortReg.test(sort)) {
			showMsg("排序请输入数字！");
			return;
		}
		var menuLevel = Number($("#pid").attr('menuLevel')) + 1;
		var privilege = "";
		if(menuLevel == 3) {
			privilege = "Y" 
							+ ($("input[value='1']").prop('checked') == true ? "Y" : "N")
							+ ($("input[value='2']").prop('checked') == true ? "Y" : "N")
							+ ($("input[value='3']").prop('checked') == true ? "Y" : "N")
							+ ($("input[value='4']").prop('checked') == true ? "Y" : "N")
							+ ($("input[value='5']").prop('checked') == true ? "Y" : "N")
							+ ($("input[value='6']").prop('checked') == true ? "Y" : "N")
							+ ($("input[value='7']").prop('checked') == true ? "Y" : "N")
							+ ($("input[value='8']").prop('checked') == true ? "Y" : "N")
							+ ($("input[value='9']").prop('checked') == true ? "Y" : "N");
		}
		var code = $("#code").val();
		showMsg(msg, 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : toUrl,
				type : 'post',
				dataType : 'json',
				data : {"id" : id, "name" : name, "type" : type, "pid" : pid, "url" : url, "icon" : icon, "status" : status, "remark" : remark, "sort" : sort, "menuLevel" : menuLevel, "code" : code, "privilege" : privilege},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	}
	
	//删除资源
	function delResource(id) {
		showMsg("确定删除该资源吗？", 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : '${ctx}/system/resource/delResource.do',
				type : 'post',
				dataType : 'json',
				data : {"id" : id},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	}
	
	//资源变动后重新生成下拉框
	function changeResource(){
		$.ajax({
			url : '${ctx}/system/resource/getResourceList.do',
			type : 'post',
			dataType : 'json',
			async : false,
			success : function(data) {
				$("#pidUl").children().remove();
				$("#pidUl").append("<li value= '-1' menuLevel='0' >主菜单</li>");
				for(var i = 0; i < data.length; i++) {
					if(data[i].pid == -1) {
						$("#pidUl").append("<li value= " + data[i].id + " pid=" + data[i].pid + " menuLevel=" + data[i].menuLevel + " >" + data[i].name + "</li>");
						for(var j = 0; j < data.length; j++){
							if(data[j].pid == data[i].id) {
								$("#pidUl").append("<li value= " + data[j].id + " pid=" + data[j].pid + " menuLevel=" + data[j].menuLevel + " >&nbsp;&nbsp;&nbsp;&nbsp;" + data[j].name + "</li>");
							}
						}
					}
				}
				$("#pidUl li").click(function() {
					var menuLevel = $(this).attr("menuLevel");
					if(menuLevel == "2") {
						$("#privilegeTr").show();
					} else {
						$("#privilegeTr").hide();
					}
					var obj = $(this).parent("ul").prevAll("input");
					obj.val(this.innerText.trim());
					obj.attr("menuLevel", menuLevel);
					obj.attr("pid", $(this).attr("value"));
					$(this).parent("ul").hide();
				});
			}
		});
	}
	
	//返回结果处理
	function resultHandle(data) {
		if(data.status == "SUCC") {
			refresh();
			showWaittingMsg(data.msg, 4000);
			closeDialog();
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
		$("#privilegeTr").hide();
		$("#resourceId").val("");
		clearData();
		$("#resourceDialog").dialog("close");
	}
	
	//清除页面数据
	function clearData(){
		$("#resetButton").click();
	}
</script>
