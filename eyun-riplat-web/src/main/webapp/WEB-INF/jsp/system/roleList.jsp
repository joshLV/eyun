<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
<style>
	.PublicTable tr td span{color: red;}
</style>
	<div id="power">
		<input type="hidden" id="writePower" value="${writePower}" />
		<input type="hidden" id="examinePower" value="${examinePower}" />
		<input type="hidden" id="releasePower" value="${releasePower}" />
		<input type="hidden" id="delegatePower" value="${delegatePower}" />
		<input type="hidden" id="lockPower" value="${lockPower}" />
		<input type="hidden" id="recommendPower" value="${recommendPower}" />
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">
		<tr>
			<td align="right" width="8%">角色名称：</td>
			<td width="15%"><input type="text" class="PublicText" id="searchName" search-field="name" search-type="search" search-fieldType="S" search-operation="ALIKE"/></td>
			<td align="right" width="8%">角色状态：</td>
			<td width="15%">
				<div class="select PublicSelect hack">
					<input type="text" readonly="readonly" id="status" value="请选择" statusValue="-1" search-field="status" search-type="search" search-fieldType="I" search-operation="IEQ" search-valAttrName="statusValue">
					<i></i>
					<ul id="statusUl" style="z-index : 9999">
						<li value="-1">请选择</li>
						<li value="e">启用</li>
						<li value="d">停用</li>
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
	<div>
		<div class="right_c_tab">
			<!-- grid对应table设置 -->
			<table id="gridTable"></table>
			<div id="gridPager"></div>
			<!-- end -->
		</div>
	</div>
	<div id="roleDialog" style="display: none;">
		<form>
			<table class="PublicTable">
				<tr>
					<td>
						角色名称：<span>*</span>
					</td>
					<td>
						<input type="text" name="baseRole.name" id="name" class="PublicText">
					</td>
				</tr>
				<tr>
					<td>
						备注：
					</td>
					<td>
						<input type="text" name="baseRole.remark" id="remark" class="PublicText">
					</td>
				</tr>
				<tr>
					<td>
						权限：
					</td>
					<td>
						<p>
							<label><input name="privilege" type="checkbox" value="1" />写</label>
							<label><input name="privilege" type="checkbox" value="2" />审核</label>
							<label><input name="privilege" type="checkbox" value="3" />发布</label>
						</p>
						<p>
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
					<td>
						状态：
					</td>
					<td>
						<label><input name="status" type="radio" value="e" checked="true" />启用</label>
						<label><input name="status" type="radio" value="d" />停用</label>
					</td>
				</tr>
				<tr>
					<td colspan="2" >
						<input type="hidden" name="baseRole.id" id="roleID" >
						<input type="hidden" id="updateTimeStr">
						<div style="display : none;">
							<input type="reset" id="resetButton">
						</div>
					</td>
				</tr>
			</table>
			</form>
	</div>
		
	<div id="resDialog" style="display : none;color:#1e61a9;">
		<form>
			<label style="display:none;"><input type="checkbox" name="menu" value="-2" menuLevel="0" id="-1" />主菜单</label>
			<c:forEach var="re" items="${resource}" >
				<ul style="list-style-type:none;margin:0px 15px;">
					<li><label><input type="checkbox" name="menu" value="${re.pid}" pid="-1" menuLevel="${re.menuLevel}" id="${re.id}" />${re.name}</label></li>
					<li>
						<c:forEach var="res" items="${re.subResource}" >
							<ul style="list-style-type:none;margin:0px 15px;">
								<li><label><input type="checkbox" name="menu" value="${res.pid}" pid="${re.id}" menuLevel="${res.menuLevel}" id="${res.id}" />${res.name}</label></li>
								<li>
									<c:forEach var="reso" items="${res.subResource}" >
										<ul style="list-style-type:none;margin:0px 15px;">
											<li>
												<label><input type="checkbox" name="menu" value="${reso.pid}" pid="${res.id}" menuLevel="${reso.menuLevel}" id="${reso.id}" />${reso.name}</label>
												<span style="margin-left: 15px;">
													<label><input name="${reso.id}" type="checkbox" value="1" />写</label>
													<label><input name="${reso.id}" type="checkbox" value="2" />审核</label>
													<label><input name="${reso.id}" type="checkbox" value="3" />发布</label>
													<label><input name="${reso.id}" type="checkbox" value="4" />委派</label>
													<label><input name="${reso.id}" type="checkbox" value="5" />锁定</label>
													<label><input name="${reso.id}" type="checkbox" value="6" />推荐</label>
													<!-- 暂时不用 -->
													<div style="display : none;" >
														<label><input name="${reso.id}" type="checkbox" value="7" />其他</label>
														<label><input name="${reso.id}" type="checkbox" value="8" />其他</label>
														<label><input name="${reso.id}" type="checkbox" value="9" />其他</label>
													</div>
												</span>
											</li>
										</ul>
									</c:forEach>
								</li>
							</ul>
						</c:forEach>
					</li>
				</ul>
			</c:forEach>
			<div style="display : none;">
				<input type="reset" id="resetButt">
			</div>
		</form>
	</div>
<script type="text/javascript">
	$("#functionDiv").append(addHtm);
	$("#addFunc").click(function() {
		openRoleDialog();
	});
	$(function(){
		$("#gridTable").jqGrid({
			url:'${ctx}/system/baserole/loadGrid.do',
			mtype : 'post',
			datatype : "json",
			height: 'auto',
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:['角色名称','角色权限','备注', '是否停用', '操作', 'id', 'searchValue', 'updateTimeStr'],
			colModel:[
				{name:'name',index:'name', align: 'center', sorttype:"string", width : '20'},
				{name:'privilege',index:'privilege',align: 'center',hidden:true},
				{name:'remark',index:'remark', align: 'center', width : '37'},
				{name:'status',index:'status', sorttype:"string", align: 'center', width : '10'},
				{name:'operate',index:'operate',sortable: false, align: 'center', width : '13'},
				{name:'id',index:'id',hidden:true},
				{name:'searchValue',index:'searchValue',hidden:true},
				{name:'updateTimeStr',index:'updateTimestr',hidden:true}
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
				for(var i=0;i<ids.length;i++){
					editHtml = preUpdHtm + " onclick='updateRole(" + ids[i] + ")' " + sufUpdHtm;
					delHtml = preDelHtm + " onclick='delRole(" + ids[i] + ")' " + sufDelHtm;
					resourceHtml = preShareHtm + " onclick='assignResource(" + ids[i] + ")' " + sufShareHtm;
					jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:editHtml + delHtml + resourceHtml});
				}
				//异步加载js，确定拥有的权限
				$("body").append("<script type='text/javascript' src='${ctx}/scripts/authority.js'><\/script>");
			},
			pager:"#gridPager",
			multiselect: true,    // 复选框设置
		}).navGrid('#gridPager',{edit:false,add:false,del:false,search:false,refresh : false}) 
		.navButtonAdd('#gridPager',{  
			caption:"<span style='color:red;'><b>批量删除</b></span>",   
			onClickButton: function(){
				delRoles();  
			},
			position:"last"  
		});
		
		//子资源选中后，父资源默认被选中，父资源选中后，其子资全部选中
		$("input[name='menu']").change(function() {
			if(this.checked) {
				selectHigherMenu(this);
				selectLowerMenu(this);
			} else {
				cancelLowerMenu(this);
				checkedHigherMenu(this);
			}
		});
		
		bindEvent();
	});
	
	//资源被选中，父资源也被选中
	function selectHigherMenu(obj) {
		var objs = $("input[name='menu'][id='" + obj.value + "']");
		if(objs.length > 0) {
			for(var i = 0; i < objs.length; i++) {
				objs[i].checked = true;
				selectHigherMenu(objs[i]);
			}
		}
	}
	
	//资源被选中，子资源默认选中
	function selectLowerMenu(obj) {
		var objs = $("input[name='menu'][pid='" + obj.id + "']");
		if(objs.length > 0) {
			for(var i = 0; i < objs.length; i++) {
				objs[i].checked = true;
				selectLowerMenu(objs[i]);
			}
		}
	}
	
	//菜单被取消后，判断上级菜单是否取消
	function checkedHigherMenu(obj) {
		var objs = $("input[name='menu'][pid='" + obj.value + "']");
		var flag = true;
		if(objs.length > 0) {
			for(var i = 0; i < objs.length; i++) {
				if(objs[i].checked) {
					flag = false;
					break;
				}
			}
			if(flag) {
				$("input[name='menu'][id='" + obj.value + "']").prop('checked', false);
				checkedHigherMenu($("input[name='menu'][id='" + obj.value + "']")[0]);
			}
		}
	}
	
	//菜单被取消后，取消所有下级菜单
	function cancelLowerMenu (obj) {
		var objs = $("input[name='menu'][value='" + obj.id + "']");
		if(objs.length > 0) {
			for(var i = 0; i < objs.length; i++) {
				objs[i].checked = false;
				cancelLowerMenu(objs[i]);
			}
		} else {
			var privileges = $("input[name='" + obj.id + "']");
			privileges.prop("checked", false);
			return false;
		}
	}

	// grid自适应
	$(window).resize(function(){
		$("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
	});
	
	//打开角色的dialog(编辑与保存)
	function openRoleDialog() {
		$("#roleDialog").dialog({
			resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			buttons : {
				"确定" : function() {
					saveOrUpdateRole();
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
	
	//打开编辑角色dialog并赋值
	function updateRole(id) {
		var rowData = $("#gridTable").jqGrid("getRowData",id);
		//获得需要编辑的roleID
		$("#roleID").val(rowData.id);
		$("#name").val(rowData.name);
		$("#oldRoleName").val(rowData.name);
		$("#remark").val(rowData.remark);
		var status = rowData.status;
		if("启用" == status) {
			$("input[value='e']").prop("checked",true);
		} else {
			$("input[value='d']").prop("checked",true);
		}
		var privilege = rowData.privilege;
		for(var i = 1; i < privilege.length; i++) {
			var checkChar = privilege.substring(i, i + 1);
			if("Y" == checkChar) {
				$("input[name='privilege'][value='" + i + "']").prop("checked",true);
			} else {
				$("input[name='privilege'][value='" + i + "']").prop("checked",false);
			}
		}
		$("#updateTimeStr").val(rowData.updateTimeStr);
		openRoleDialog();
	}
	
	//保存或编辑角色
	function saveOrUpdateRole() {
		var toUrl = "";
		var id = $("#roleID").val();
		var msg = "";
		if(id != null && id != "") {
			toUrl = '${ctx}/system/baserole/updateRole.do';
			msg = "确定修改该条数据吗？";
		} else {
			toUrl = '${ctx}/system/baserole/saveRole.do';
			msg = "确定保存该条数据吗？";
		}
		var name = $("#name").val();
		var remark = $("#remark").val();
		var status = $("input[name='status']:checked").val();
		if(status == "e") {
			status = "启用";
		}else {
			status = "禁用";
		}
		var privilege = "Y" 
							+ ($("input[name='privilege'][value='1']").prop('checked') == true ? "Y" : "N")
							+ ($("input[name='privilege'][value='2']").prop('checked') == true ? "Y" : "N")
							+ ($("input[name='privilege'][value='3']").prop('checked') == true ? "Y" : "N")
							+ ($("input[name='privilege'][value='4']").prop('checked') == true ? "Y" : "N")
							+ ($("input[name='privilege'][value='5']").prop('checked') == true ? "Y" : "N")
							+ ($("input[name='privilege'][value='6']").prop('checked') == true ? "Y" : "N")
							+ ($("input[name='privilege'][value='7']").prop('checked') == true ? "Y" : "N")
							+ ($("input[name='privilege'][value='8']").prop('checked') == true ? "Y" : "N")
							+ ($("input[name='privilege'][value='9']").prop('checked') == true ? "Y" : "N");
		if(name == null || name == "") {
			showMsg("角色名称不能为空！");
			return;
		}
		var updateTimeStr = $("#updateTimeStr").val();
		showMsg(msg, 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : toUrl,
				type : 'post',
				dataType : 'json',
				data : {"id" : id, "name" : name, "remark" : remark, "status" : status, "privilege" : privilege, "updateTimeStr" : updateTimeStr},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	}
	
	//批量删除角色前置工作：获取被选中的roleID
	function delRoles() {
		var selIds = $("#gridTable").jqGrid('getGridParam','selarrrow');
		if(selIds == null && selIds.length == 0) {
			showMsg("请选择删除的角色！");
			return;
		}
		var ids = selIds.join(",");
		delRole(ids);
	}
	
	//删除单个或批量删除角色
	function delRole(id){
		var ids = (id + "").split(",");
		var datas = [];
		var num = ids.length;
		for(var i = 0; i < num; i++) {
			datas.push($("#gridTable").jqGrid("getRowData",ids[i]));
		}
		showMsg("确定要删除这" + num + "数据吗？", 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : '${ctx}/system/baserole/delRole.do',
				type : "post",
				dataType : "json",
				data : {"searchValue" : $.toJSONString(datas)},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	}
	
	//打开分配资源Dialog
	function assignResource(id) {
		var rowData = $("#gridTable").jqGrid('getRowData', id);
		var resId_privilegesStr = rowData.searchValue;
		var resId_privileges = resId_privilegesStr.split(",");
		setResource(resId_privileges);
		openResDialog(id);
	}
	
	//打开分配资源Dialog
	function openResDialog(id) {
		$("#resDialog").dialog({
			resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			buttons : {
				"确定" : function() {
					saveResource(id);
				},
				"取消" : function() {
					$("#resetButt").click();
					$("#resDialog").dialog("close");
				} 
			},
			close : function() {
				$("#resetButt").click();
				$("#resDialog").dialog("close");
			}
		});
	}
	
	//设置角色的资源
	function setResource(resId_privileges) {
		for(var i = 0; i < resId_privileges.length; i++) {
			var resId = resId_privileges[i].substring(0, resId_privileges[i].indexOf("_"));
			$("input[name='menu'][id='" + resId + "']").prop('checked', true);
			var resPrivileges = resId_privileges[i].substring(resId_privileges[i].indexOf("_") + 1, resId_privileges[i].lastIndexOf("_"));
			var privileges = resId_privileges[i].substring(resId_privileges[i].lastIndexOf("_") + 1);
			if(privileges != null && privileges != "#" && privileges.length > 0) {
				setPrivilege(resId, resPrivileges, privileges);
			}
		}
	}
	
	//设置每个资源的权限
	function setPrivilege(resId, resPrivileges, privileges) {
		var flag = false;
		if(resPrivileges != null && resPrivileges != "#" && resPrivileges.length > 0) {
			flag = true;
		}
		for(var j = 1; j < privileges.length; j++) {
			if(!flag) {
				$("input[name='" + resId + "'][value='" + j + "']").parents("label").hide();
				continue;
			}
			var resValue = resPrivileges.substring(j, j + 1);
			var value = privileges.substring(j, j + 1);
			if("Y" == resValue) {
				if("Y" == value) {
					$("input[name='" + resId + "'][value='" + j + "']").prop("checked", true);
				} else {
					$("input[name='" + resId + "'][value='" + j + "']").prop("checked", false);
				}
			} else {
				$("input[name='" + resId + "'][value='" + j + "']").parents("label").hide();
			}
		}
	}
	
	//保存分配的资源
	function saveResource(id) {
		var objs = $("input[name='menu']:checked");
		var menuIds = "";
		for(var i = 0; i < objs.length; i++) {
			menuIds += objs[i].id + "_" + getPrivilegeByResId(objs[i].id);
			if(i != objs.length -1) {
				menuIds += ",";
			}
		}
		if(menuIds == "") {
			showMsg("请选择资源！",1);
			return;
		}
		showMsg("确定分配资源吗？", 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : '${ctx}/system/baserole/uptAuthority.do',
				type : 'post',
				dataType : 'json',
				data : {"id" : id, "searchValue" : menuIds},
				success : function(data) {
					if(data.status == "SUCC") {
						refresh();
						showWaittingMsg(data.msg, 4000);
						$("#resetButt").click();
						$("#resDialog").dialog("close");
					} else {
						showWaittingMsg(data.msg, 4000);
					}
				}
			});
		});
	}
	
	//获取该菜单的权限
	function getPrivilegeByResId(id) {
		var privilegeStr = "";
		var checkBoxLength = $("input[name='" + id + "']").length;
		if(checkBoxLength > 0) {
			privilegeStr = "Y";
			for (var i = 1; i < checkBoxLength + 1; i++) {
				var checked = $("input[name='" + id + "'][value='" + i + "']").prop("checked");
				if(true == checked) {
					privilegeStr += "Y";
				} else {
					privilegeStr += "N";
				}
			}
			return privilegeStr;
		} else {
			return "";
		}
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
	
	//关闭dialog时清除roleID等，以免造成保存和编辑混乱（后台是根据判断有没有roleID来决定保存和修改的）
	function closeDialog() {
		$("#roleID").val("");
		$("#updateTimeStr").val("");
		$("#oldRoleName").val("");
		clearData();
		$("#roleDialog").dialog("close");
	}
	
	//清除页面数据
	function clearData(){
		$("#resetButton").click();
	}
</script>
