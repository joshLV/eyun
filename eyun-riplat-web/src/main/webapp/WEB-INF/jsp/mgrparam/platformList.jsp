<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<script type="text/javascript" src="${ctx}/scripts/select.js"></script>

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">
	<tr>
		<td align="right" width="8%">平台ID：</td>
		<td width="15%"><input type="text" class="PublicText" id="platformId" name="platformId" search-field="platformId" search-type="search" search-fieldType="S" search-operation="ALIKE" /></td>
		<td align="right" width="8%">平台名称：</td>
		<td width="15%"><input type="text" class="PublicText" id="platformName" name="platformName" search-field="platformName" search-type="search" search-fieldType="S" search-operation="ALIKE" /></td>
		<td align="right" width="8%">平台IP：</td>
		<td width="15%"><input type="text" class="PublicText" id="platformIp" name="platformIp" search-field="platformIp" search-type="search" search-fieldType="S" search-operation="ALIKE" /></td>
		<td colspan="2"><input type="button" class="PublicButton" value="查询" onclick="loadPostData();"></td>
	</tr>
</table>

<div class="PublicC_t">
	<ul class="operation fr" id="functionDiv"></ul>
</div>

<form id="generateForm">

	<input type="hidden" id="genPlatId" name="genPlatId" />

</form>
<div id="tabs" style="border:0pxs" class="sub_tabs">
	<div class="right_c_tab">
		<table id="gridTable"></table>
		<div id="gridPager"></div>
	</div>
</div>
<div id="dialog-div" title="新增平台信息" style="display: none;">
	<form action="" id = "dialog-form">
		<input type = "hidden" id = "dialogId" name = "dialogId" readonly = "readonly">
		<table class="PublicTable">
		<br>
			<tr>
				<td align="right" width="5%">平台类型：</td>
				<td width="15%">
					<div id="typeSel" class="select PublicSelect hack">
						<input type="text" id="groupType" readonly = "readonly" name="groupType" value="请选择" groupTypeValue="-1"><i></i>
						<ul id="type" style="z-index: 999">
							<li value="-1">请选择</li>
							<c:forEach var="list" items="${List}">
								<li value="${list.platformId }" >${list.platformName }</li>
							</c:forEach>
						</ul>
					</div>
					<div id="typeOne" class="select PublicSelect hack">
						<input type="text" id="typeOnes" readonly = "readonly" name="groupType" value="请选择" groupTypeValue="-1">
					</div>
				</td>
			</tr>
			<tr id="plateBind">
				<td align="right" width="5%">平台绑定码：</td>
				<td width="15%"><input type="text" class="PublicText"  placeholder="请输入8位数字和小写字母组合"  id ="addPlatformId"  name="addPlatformId" maxlength = "8"/></td>
			</tr>
			<tr>
				<td align="right" width="5%">平台名称：</td>
				<td width="15%"><input type="text" class="PublicText" id ="addPlatformName"  name="addPlatformName" maxlength = "16"/></td>
			</tr>
			<tr>
				<td align="right" width="5%">用户IP：</td>
				<td width="15%"><input type="text" placeholder="http(s)://x.x.x.x:xx" class="PublicText" id="addPlatformIp" name = "addPlatformIp" /></td>
			</tr>
			<tr>
				<td align="right" width="5%">用户姓名：</td>
				<td width="15%"><input type="text"  class="PublicText" id="addPlatformUserName" name = "addPlatformUserName" maxlength = "32"/></td>
			</tr>
			<tr>
				<td align="right" width="5%">用户电话：</td>
				<td width="15%"><input type="telephone"class="PublicText" id="addPlatformUserPhone" name = "addPlatformUserPhone" maxlength = "32"/></td>
			</tr>
			<tr>
				<td align="right"  width="5%">用户地址：</td>
				<td height="100" width="15%"><textarea id="addPlatformUserAddress" name="addPlatformUserAddress" cols="45" rows="5" maxlength="255" ></textarea></td>
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
	var idStr = '${platFormId}'
	$(function(){		
		bindEvent();//绑定下拉列表事件
		$("#functionDiv").html(addHtm);/* 添加按钮 */
		$("#addFunc").click(function () {/* 添加按钮事件 */
			$("#typeSel").show();
			$("#typeOne").hide();
			$("#addPlatformId").removeAttr("readonly");
			$("#addPlatformId").val(idStr);
            $("#dialog-div").dialog("option", "title", "新增平台信息").dialog("open");  
        });
		
		$("#gridTable").jqGrid({
			url:'${ctx}/mgrparam/platform/loadGrid.do',
			mtype : 'post',
			datatype : "json",
			height: 'auto',
			forceFit : true,//当为true时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:['ID','平台绑定码', '平台名称','平台IP','用户姓名','用户电话','用户地址','激活状态','操作'],
			colModel:[
				{name:'id',index:'id', align: 'center', sorttype:"string", width : '10',hidden:true},
				{name:'platformId',index:'platformId', align: 'center', sorttype:"string", width : '10'},
				{name:'platformName',index:'platformName', align: 'center', sorttype:"string", width : '10'},
				{name:'platformIP',index:'platformIP',align: 'center', width : '10'},
				{name:'platformUserName',index:'platformUserName',align: 'center', width : '10'},
				{name:'platformUserPhone',index:'platformUserPhone',align: 'center', width : '10'},
				{name:'platformUserAddress',index:'platformUserAddress',align: 'center', width : '10'},
				{name:'activeableName',index:'activeableName',align: 'center', width : '5'},
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
				var ids = $("#gridTable").getDataIDs();
				var rows = $("#gridTable").getRowData();
				for(var i=0;i<ids.length;i++){
// 					exportHtml = preExportHtm + " onclick='generate(" + ids[i] + ")' title = '导出文件'" + sufExportHtm;
					editHtml = preUpdHtm + " onclick='updateData(" + JSON.stringify(rows[i]) + ")' " + sufUpdHtm;
					//delHtml = preDelHtm + " onclick='delData(" + JSON.stringify(rows[i]) + ")' " + sufDelHtm;
					jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:editHtml/* +delHtml */});
				}
			},
			loadComplete: function(xhr){
				$("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
			},
			pager:"#gridPager",
			userDataOnFooter : true
		}).navGrid('#gridPager0',{edit:false,add:false,del:false,search:false,refresh : false});	
		openDailog();	
	}); 
	
	
	//打开资源dialog
	function openDailog() {
		$("#dialog-div").dialog({
            autoOpen: false,
            height: 560,
            width: 600,
            modal: true,
            buttons: {
                "保存": function () {
                	saveData();
                },
                "取消": function () {
                	/** jqgrid卸载 **/
                	closeDialog();
                }
            },
            close: function () {
                /** jqgrid卸载 **/
            	closeDialog();
            }
        });	
	}
	
	/* 检查数据合法性 */
	function checkData(id){
		var groupType = $("#groupType").attr("groupTypeValue");
		if(null == id || id == ''){
			if(groupType=="-1"){
				showMsg("请选择平台类型");
				return false;
			}
		}
		var platformName = $("#addPlatformName").val();
		if(platformName.length <=0){
			showMsg("平台名称不能为空");
			return false;
		}
		var platformUserPhone=$("#addPlatformUserPhone").val();
		if(!(/^1[3,4,5,6,7,8,9][0-9]{9}$|(^(\d{3,4}-)?\d{7,8}$)/.test(platformUserPhone))){
			showMsg("请输入合法用户电话");
			return false;
		}
		var addPlatformId=$("#addPlatformId").val();
		if(!(/^([a-z]|[0-9]){8}$/.test(addPlatformId))){
			showMsg("请输入合法平台绑定码");
			return false;
		}
		if(platformName.length >32){
			showMsg("输入的平台名称过长");
			return false;
		}
		var platformIp = $("#addPlatformIp").val();
		if(typeof(exp) != "undefined" && platformIp.length > 32 ){
			showMsg("输入的IP过长");
			return false;
		}
		if(!checkURL(platformIp)){
			showMsg("请输入合法的ip地址");
			return false;
		}
		return true;
	}
	/***檢查URL輸入是否合法**/
	function checkURL(URL){
		var str=URL;
		//判断URL地址的正则表达式为:http(s)?://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?
		//下面的代码中应用了转义字符"\"输出一个字符"/"
		var Expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
		var objExp=new RegExp(Expression);
			if(objExp.test(str)==true){
				return true;
			}else{					
				return false;
		}
	} 
	
	/* 保存平台信息 */
	function saveData(){
		var id = $("#dialogId").val();
		if(checkData(id)){
			var toUrl = "";
			var msg = "";
			var item;
			if(id != null && id != "") {
				var toUrl = "${ctx}/mgrparam/platform/updPlatform.do";
				item =  $("#typeOnes").attr("groupTypeValue");
				msg = "确定修改该条数据吗？";
			} else {
				var toUrl = "${ctx}/mgrparam/platform/savePlatform.do";
				item =  $("#groupType").attr("groupTypeValue");
				msg = "确定保存该条数据吗？";
			}			
			$.ajax({
				type :'post',
				url : toUrl,
				dataType : 'json',
				data:$('#dialog-form').serialize()+ "&addGroupType=" + item,
				success : function(data) {
					if(data.data != undefined){
						console.log(ids);
						idStr = data.data;
					}
					resultHandle(data);
				}
			});
		}
	}
	
	function updateData(row) {
		$.ajax({
			url : '${ctx}/mgrparam/platform/getPlatformById.do',
			type : 'post',
			dataType : 'json',
			data : {"id" : row.id},
			success : function(root) {
				var data = root.data;
				$("#dialogId").val(data.id);
				$("#addPlatformName").val(data.platformName);
				$("#addPlatformIp").val(data.platformIP);
				$("#addPlatformId").val(data.platformId);
				$("#addPlatformId").attr("readonly","readonly")
				$("#addPlatformUserName").val(data.platformUserName);
				$("#addPlatformUserPhone").val(data.platformUserPhone);
				$("#addPlatformUserAddress").val(data.platformUserAddress);
				var type = data.platformType;
				var typeName = data.platformTypeName;
				 $('#typeOnes').attr("groupTypeValue",type);
				 $("#typeOnes").attr("value",typeName);


				$("#dialog-div").dialog("option", "title", "修改平台信息").dialog("open");
				$("#typeSel").hide();
				$("#typeOne").show();
			}
		});

	}
	
	/* 生成Key文件 */
	/* function generate(id){
		$("#genPlatId").attr("value",id); 
		$("#generateForm").attr("action", "${ctx}/mgrparam/generateLicense.do");
		$("#generateForm").attr("method", "post");
		$("#generateForm").submit();
	} */
	
	/* 删除 */
	function delData(row) {
		showMsg("确定删除该平台信息吗？", 2, function() {
			$(this).dialog('close');
			$.ajax({
				type : 'post',
				url : '${ctx}/mgrparam/platform/delete.do',
				dataType : 'json',
				data : {"id" : row.id,"platformId":row.platformId},
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
		}else{
			showWaittingMsg(data.msg, 4000);
		}
	}
	
	//jqgrid刷新
	function refresh() {
		jQuery("#gridTable").trigger("reloadGrid");
	}
	
	//关闭dialog时清除dialogId
	function closeDialog() {
		$("#dialogId").val("");
		clearData();
		$("#dialog-div").dialog("close");
	}
	
	//清除页面数据
	function clearData(){
		$("#resetButton").click();
	}
</script>