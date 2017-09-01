<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<div>
		<input type="hidden" id="writePower" value="${writePower}" />
		<input type="hidden" id="examinePower" value="${examinePower}" />
		<input type="hidden" id="releasePower" value="${releasePower}" />
		<input type="hidden" id="delegatePower" value="${delegatePower}" />
		<input type="hidden" id="lockPower" value="${lockPower}" />
		<input type="hidden" id="recommendPower" value="${recommendPower}" />
	</div>
	<div>
		<table id="gridTable"></table>
		<div id="gridPager"></div>
	</div>
	<div id="systemSetDialog" style="display : none;">
		<table class="PublicTable">
			<tr>
				<td>场所名称：</td>
				<td><span id="placeName"></span></td>
			</tr>
			<tr>
				<td>是否允许加入会员：</td>
				<td><lable>
					<input type="radio" name="ifJoinMem" value="Y" checked=true />是</lable> <lable>
					<input type="radio" name="ifJoinMem" value="N" />否</lable></td>
			</tr>
			<tr id="protocolTr">
				<td>协议：</td>
				<td><textarea id="protocol" rows="" cols="" class="PublicText"></textarea></td>
			</tr>
			<tr id="discountInfoTr">
				<td>打折信息：</td>
				<td><textarea id="discountInfo" rows="" cols="" class="PublicText"></textarea></td>
			</tr>
			<tr>
				<td>进店提醒：</td>
				<td><lable>
					<input type="radio" name="ifRemind" value="Y" checked=true />是</lable> <lable>
					<input type="radio" name="ifRemind" value="N" />否</lable></td>
			</tr>
			<tr id="ifRemindTr">
				<td>提醒访客等级：</td>
				<td><lable>
					<input type="radio" name="remindLevel" value="1" checked=true />重点关注</lable> <lable>
					<input type="radio" name="remindLevel" value="2" />关注</lable> <lable>
					<input type="radio" name="remindLevel" value="3" />普通</lable></td>
			</tr>
			<tr>
				<td style="display : none;" colspan="2"><input type="reset" id="resetButton" /> <input type="hidden" id="placeID" /> <input type="hidden" id="ctrlCode" /></td>
			</tr>
		</table>
	</div>

<script type="text/javascript">
	$(function(){
		$("#gridTable").jqGrid({
			url:'${ctx}/system/systemSet/loadGrid.do',
			mtype : 'post',
			datatype : "json",
			postData : {"creator" : ${creator}},
			height: 'auto',
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:['场所名称','是否允许加入会员','是否开启进店提醒', '操作', 'id', 'placeId', 'placeCode', 'ctrlCode', 'protocol', 'discountInfo'],
			colModel:[
				{name:'placeName',index:'placeName', align: 'center', sorttype:"string", width : '25'},
				{name:'ifJoinMem',index:'ifJoinMem',align: 'center', width : '25'},
				{name:'ifRemind',index:'ifRemind', align: 'center', width : '25'},
				{name:'operate',index:'operate',sortable: false, align: 'center', width : '25'},
				{name:'id',index:'id',hidden:true},
				{name:'placeId',index:'placeId',hidden:true},
				{name:'placeCode',index:'placeCode',hidden:true},
				{name:'ctrlCode',index:'ctrlCode',hidden:true},
				{name:'protocol',index:'protocol',hidden:true},
				{name:'discountInfo',index:'discountInfo',hidden:true}
			],
			rowList: ${rowList},
			jsonReader: {
				rowNum:"rowNum",
				root:"dataRows",			// 数据行（默认为：rows）
				page: "curPage",			// 当前页
				total: "totalPages",		// 总页数
				records: "totalRecords",	// 总记录数
				repeatitems : false			// 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
			},
			gridComplete:function(rowid, rowdata){
				var ids = $("#gridTable").getDataIDs();//jqGrid('getDataIDs');
				for(var i=0;i<ids.length;i++){
					editHtml = preUpdHtm + " onclick='setSystem(" + ids[i] + ")' " + sufUpdHtm;
					jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:editHtml});
				}
				//异步加载js，确定拥有的权限
				$("body").append("<script type='text/javascript' src='${ctx}/scripts/authority.js'><\/script>");
			},
			pager:"#gridPager",
		});
		
		//是否显示协议和打折信息
		$("input[name='ifJoinMem']").change(function() {
			var code = $("input[name='ifJoinMem']:checked").val();
			if("Y" == code) {
				ifJoinMemTrShow();
			} else {
				ifJoinMemTrHide();
			}
		});
		
		//是否显示提醒访客等级
		$("input[name='ifRemind']").change(function() {
			var code = $("input[name='ifRemind']:checked").val();
			if("Y" == code) {
				ifRemindTrShow();
			} else {
				ifRemindTrHide();
			}
		});
	});
	
	//打开系统设置dialog
	function openDialog() {
		$("#systemSetDialog").dialog({
			width : 'auto',
			height : 'auto',
			resizable : false,
			modal : true,
			buttons : {
				"保存" : function() {
					updateSystemSet();
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
	
	//设置系统
	function setSystem(id) {
		var rowData = $("#gridTable").jqGrid("getRowData", id);
		$("#placeName").html(rowData.placeName);
		$("#placeID").val(rowData.placeId);
		$("#ctrlCode").val(rowData.ctrlCode);
		$("#protocol").html(rowData.protocol);
		$("#discountInfo").html(rowData.discountInfo);
		var ifJoinMem = rowData.ctrlCode.substring(0,1);
		var ifRemid = rowData.ctrlCode.substring(4,5);
		if("Y" == ifJoinMem) {
			$("input[name='ifJoinMem'][value='Y']").prop('checked', true);
			ifJoinMemTrShow();
		} else {
			$("input[name='ifJoinMem'][value='N']").prop('checked', true);
			ifJoinMemTrHide();
		}
		if("N" == ifRemid) {
			$("input[name='ifRemind'][value='N']").prop('checked', true);
			ifRemindTrHide();
		}else {
			$("input[name='ifRemind'][value='Y']").prop('checked', true);
			$("input[name='ifRemind'][value='" + ifRemid + "']").prop('checked', true);
			ifRemindTrShow();
		}
		openDialog();
	}
	
	//更新系统设置
	function updateSystemSet() {
		var ifJoinMem = $("input[name='ifJoinMem']:checked").val();
		var protocol = $("#protocol").val();
		var discountInfo = $("#discountInfo").val();
		var ifRemind = $("input[name='ifRemind']:checked").val();
		if("Y" == ifRemind){
			ifRemind = $("input[name='remindLevel']:checked").val();
		}
		var ctrlCode = ifJoinMem + $("#ctrlCode").val().substring(1,4) + ifRemind;
		var placeId = $("#placeID").val();
		showMsg("确定修改系统设置吗？", 2, function() {
			$(this).dialog('close');
			$.ajax({
				url : '${ctx}/system/systemSet/updateSystemSet.do',
				type : 'post',
				dataType : 'json',
				data : {"placeId" : placeId, "protocol" : protocol, "discountInfo" : discountInfo, "ctrlCode" : ctrlCode},
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
	
	//开启加入会员功能显示协议和打折信息
	function ifJoinMemTrShow(){
		$("#protocolTr").show();
		$("#discountInfoTr").show();
	}
	
	//关闭加入会员功能隐藏协议和打折信息
	function ifJoinMemTrHide(){
		$("#protocolTr").hide();
		$("#discountInfoTr").hide();
	}
	
	//开启进店提醒显示提醒访客等级
	function ifRemindTrShow() {
		$("#ifRemindTr").show();
	}
	
	//关闭进店提醒隐藏提醒访客等级
	function ifRemindTrHide() {
		$("#ifRemindTr").hide();
	}
	
	//关闭dialog时清除数据
	function closeDialog() {
		$("#placeName").html("");
		$("#placeID").val("");
		$("#ctrlCode").val("");
		clearData();
		$("#systemSetDialog").dialog("close");
	}
	
	//清除页面数据
	function clearData(){
		$("#resetButton").click();
	}
</script>

