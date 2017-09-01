<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
			<td align="right" width="8%">用户名称：</td>
			<td width="15%"><input type="text" class="PublicText" id="searchName" search-field="name" search-type="search" search-fieldType="S" search-operation="ALIKE"/></td>
			<td align="right" width="8%">用户角色：</td>
			<td width="15%">
				<div class="select PublicSelect hack">
					<input type="text" value="请选择" readonly="readonly" id="roleSel" roleSelValue="-1" search-field="role_id" search-type="search" search-fieldType="I" search-operation="IEQ" search-valAttrName="roleSelValue">
					<i></i>
					<ul id="roleSelUl" style="z-index : 9999">
						<li value="-1">请选择</li>
						<c:forEach var="role" items="${roleList}">
							<li value="${role.id }">${role.name }</li>
						</c:forEach>
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
	<table id="gridTable"></table>
	<div id="gridPager"></div>
	<div id="OperatorDialog" style="display : none;">
		<form>
			<table class="PublicTable">
				<tr>
					<td>账号名称：<span>*</span></td>
					<td><input type="text" id="memberName" class="PublicText"/></td>
					<td>真实姓名：<span>*</span></td>
					<td><input type="text" id="memberRealName" class="PublicText"/>
				</tr>
				<tr>
					<td>登陆密码：<span>*</span></td>
					<td><input type="password" id="psw" class="PublicText"/></td>
				
					<td>确认密码：<span>*</span></td>
					<td><input type="password" id="surePsw" class="PublicText"/></td>
				</tr>
				<tr>
					<td>角色：<span>*</span></td>
					<td>
						<div class="select PublicSelect hack">
							<input type="text" value="请选择" readonly="readonly" id="role" roleValue="-1">
							<i></i>
							<ul id="roleUl" style="z-index : 9999">
								<li value="-1">请选择</li>
								<c:forEach var="role" items="${roleList}">
									<li value="${role.id }">${role.name }</li>
								</c:forEach>
							</ul>
						</div>
					</td>
					<td>是否启用：<span>*</span></td>
					<td>
						<lable><input type="radio" name="status" value="e" checked=true />启用</lable>
						<lable><input type="radio" name="status" value="d" />禁用</lable>
					</td>
				</tr>
				<tr>
					<td>所属区域：</td>
					<td colspan="3">
						<div id="areaId" class="select PublicSelect hack">
							
						</div>
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="3"><textarea id="remark" class="PublicText" style="width: 360px; height: 80px; resize: none;" ></textarea></td>
				</tr>
				<tr>
					<input type="hidden" id="operateUserID" />
					<div style="display : none;">
						<input type="reset" id="resetButton">
					</div>
				</tr>
			</table>
		</form>
	</div>
	
<script type="text/javascript" src="${ctx}/common/scripts/area.js"></script>
<script type="text/javascript">
	$(function() {
		//admin用户时初始化场所下拉框
		if(${not empty creatorRole} && ${creatorRole.id == 1}) {
			var areaArray = new Array();
			areaArray["appendSelectors"] = "areaId";
			areaArray["selectors"] = "loadProvince,loadCity,loadCounty";
			areaArray["styles"] = "width: 100px;padding: 6px 0;color: #1e61a9;background: #f0f8ff;,width: 100px;padding: 6px 0;color: #1e61a9;background: #f0f8ff;,width: 100px;padding: 6px 0;color: #1e61a9;background: #f0f8ff;";
			areaArray["defaultSelects"] = "";
			initArea(areaArray);
		}
		
		$("#functionDiv").append(addHtm);
		$("#addFunc").click(function() {
			openOperatorDialog();
		});
		$("#gridTable").jqGrid({
		url:'${ctx}/system/operateUser/getOperatorList.do',
		mtype : "post",
		datatype : "json",
		height: 'auto',
		forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
		altRows : true,
		colNames:['用户名','真实姓名','角色', '创建时间', '创建人', '更新时间', '更新人', '是否停用', '备注', '操作', 'roleID', 'searchValue', 'id'],
		colModel:[
			{name:'memberName',index:'name', align: 'center', sorttype:"string", width : '10'},
			{name:'memberRealName',index:'real_name',align: 'center', width : '10'},
			{name:'roleName',index:'roleName', align: 'center', width : '10'},
			{name:'createTimeStr',index:'create_time', sorttype:"date", align: 'center', width : '15'},
			{name:'creatorName',index:'creatorName', sorttype:"string", align: 'center', width : '10'},
			{name:'updateTimeStr',index:'update_time', sorttype:"date", align: 'center', width : '15'},
			{name:'updatorName',index:'updatorName', sorttype:"string", align: 'center', width : '10'},
			{name:'status',index:'status', sorttype:"string", align: 'center', width : '10'},
			{name:'remark',index:'remark', sorttype:"string", align: 'center', width : '10'},
			{name:'operate',index:'operate',sortable: false, align: 'center', width : '10'},
			{name:'roleID',index:'roleID',hidden:true},
			{name:'searchValue',index:'searchValue',hidden:true},
			{name:'id',index:'id',hidden:true}
		],
		rowList: ${rowList},
		sortname: "id",
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
				editHtml = preUpdHtm + " onclick='updateOperator(" + ids[i] + ")' " + sufUpdHtm;
				delHtml = preDelHtm + " onclick='delOperator(" + ids[i] + ")' " + sufDelHtm;
				jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:editHtml + delHtml});
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
				delOperatorByIds();  
			},
			position:"last"  
		});
		bindEvent();
	});
	
	//打开dialog
	function openOperatorDialog() {
		$("#OperatorDialog").dialog({
			resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			buttons : {
				"确定" : function() {
					addOrUpdateOperateUser();
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
	
	//编辑时打开dialog
	function updateOperator(id) {
		var rowData = $("#gridTable").jqGrid("getRowData", id);
		$("#operateUserID").val(rowData.id);
		$("#memberRealName").val(rowData.memberRealName);
		$("#memberName").val(rowData.memberName);
		$("#oldName").val(rowData.memberName);
		$("#roleUl li[value='" + rowData.roleID + "']").click();
		$("#remark").val(rowData.remark);
		var status = rowData.status;
		if("启用" == status) {
			$("input[value='e']").prop("checked",true);
		} else {
			$("input[value='d']").prop("checked",true);
		}
		var placeStr = rowData.searchValue;
		//所属区域为1（中国）时，就是默认该用户拥有所有区域
		if(placeStr == "1") {
			placeStr = "-1";
		}
		//admin用户：设置区域，非admin用户设置场所
		var areaArray = new Array();
		areaArray["appendSelectors"] = "areaId";
		areaArray["selectors"] = "loadProvince,loadCity,loadCounty";
		areaArray["styles"] = "width:80px,width:80px,width:80px";
		areaArray["defaultSelects"] = placeStr;
		initArea(areaArray);
		openOperatorDialog();
	}
	
	//添加或更新操作用户
	function addOrUpdateOperateUser(){
		var toUrl = "";
		var id = $("#operateUserID").val();
		var msg = "";
		if(id != null && id != "") {
			var toUrl = "${ctx}/system/operateUser/updateOperateUser.do";
			msg = "确定修改该条数据吗？";
		} else {
			var toUrl = "${ctx}/system/operateUser/saveOperateUser.do";
			msg = "确定保存该条数据吗？";
		}
		var memberRealName = $("#memberRealName").val();
		var memberName = $("#memberName").val();
		var psw = $("#psw").val();
		var surePsw = $("#surePsw").val();
		var roleID = $("#role").attr("roleValue");
		var remark = $("#remark").val();
		var status = $("input[name='status']:checked").val();
		if(status == "e") {
			status = "启用";
		}else {
			status = "禁用";
		}
		if(memberRealName == null || memberRealName == "") {
			showMsg("真实姓名不能为空！");
			return;
		}
		if(memberName == null || memberName == "") {
			showMsg("账号名称不能为空！");
			return;
		}
		if(psw == null || psw == "") {
			showMsg("请输入登陆密码！");
			return;
		}
		if(surePsw == null || surePsw == "") {
			showMsg("请输入确认密码！");
			return;
		}
		if(psw != surePsw) {
			showMsg("两次输入的密码不一致！");
			return;
		}
		if(roleID == '-1') {
			showMsg("请分配角色！");
			return;
		}
		var objs = $("input[name='place']:checked");
		var placeStr = "";
		//不是系统管理员admin创建拿场所， 是admin创建拿区域
		var countyCode = $("#loadCounty").val();
		var cityCode = $("#loadCity").val();
		var provinceCode = $("#loadProvince").val();
		if(countyCode != "-1") {
			placeStr = countyCode;
		} else if(cityCode != "-1") {
			placeStr = cityCode;
		} else if(provinceCode != "-1") {
			placeStr = provinceCode;
		} else {
			placeStr = "1";
		}
		if(placeStr == '1'){
			showMsg("请选择地区！");
			return;
		}
		showMsg(msg, 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : toUrl,
				type : "post",
				dataType : "json",
				data : {"id" : id, "memberRealName" : memberRealName, "memberName" : memberName, "memberPwd" : psw, "roleID" : roleID, "remark" : remark, "status" : status, "searchValue" : placeStr,"areaId" : placeStr},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	}
	
	//删除多个操作用户时，选择的记录id
	function delOperatorByIds() {
		var selIds = $("#gridTable").jqGrid("getGridParam", 'selarrrow');
		if(selIds == null && selIds.length == 0) {
			showMsg("请选择删除的用户！");
			return;
		}
		var ids = selIds.join(",");
		delOperator(ids);
	}
	
	//删除单个或多个操作用户
	function delOperator(ids) {
		var num = (ids + "").split(",").length;
		showMsg("确定要删除这" + num + "条数据吗？", 2, function() {
			$.ajax({
				url : "${ctx}/system/operateUser/deleteOperator.do",
				type : "post",
				dataType : "json",
				data : {"searchValue" : ids},
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
			closeDialog();
		} else {
			showWaittingMsg(data.msg, 4000);
		}
	}
	
	//jqgrid刷新
	function refresh() {
		jQuery("#gridTable").trigger("reloadGrid");
	}
	
	//关闭dialog时清除operateUserID等，以免造成保存和编辑混乱（后台是根据判断有没有operateUserID来决定保存和修改的）
	function closeDialog() {
		$("#operateUserID").val("");
		clearData();
		$("#OperatorDialog").dialog("close");
	}
	
	//清除页面数据
	function clearData(){
		$("#resetButton").click();
	}
</script>
