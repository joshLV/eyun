<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
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
	<div id="surfDialog" style="display : none;">
		<form>
			<table class="PublicTable">
				<tr>
					<td colspan="2">
						<h3></h3>
					</td>
				</tr>
				<tr>
					<td>免费上网时长：</td>
					<td>
						<div class="select PublicSelect hack">
							<input type="text" value="2小时" readonly="readonly" id="freeTime" freeTimeValue="7200">
							<i></i>
							<ul id="freeTimeUl" style="z-index:999;width:140px">
								<li value="300">5分钟</li>
								<li value="900">15分钟</li>
								<li value="1200">20分钟</li>
								<li value="1800">30分钟</li>
								<li value="3600">1小时</li>
								<li value="7200">2小时</li>
								<li value="10800">3小时</li>
								<li value="14400">4小时</li>
								<li value="18000">5小时</li>
								<li value="21600">6小时</li>
								<li value="25200">7小时</li>
								<li value="28800">8小时</li>
								<li value="32400">9小时</li>
								<li value="36000">10小时</li>
								<li value="39600">11小时</li>
								<li value="43200">12小时</li>
								<li value="46800">13小时</li>
								<li value="50400">14小时</li>
								<li value="54000">15小时</li>
								<li value="57600">16小时</li>
								<li value="61200">17小时</li>
								<li value="64800">18小时</li>
								<li value="68400">19小时</li>
								<li value="72000">20小时</li>
								<li value="75600">21小时</li>
								<li value="79200">22小时</li>
								<li value="82800">23小时</li>
								<li value="86400">24小时</li>
								<li value="999999">不限</li>
							</ul>
						</div>
					</td>
				</tr>
				<!-- <tr>
					<td>上网方式：</td>
					<td><label><input type="radio" name="surfWay" value="N" />自动上网</label> <label><input type="radio" name="surfWay" value="Y" />审批上网</label></td>
				</tr>

				<tr id="strategyTr">
					<td>上网策略：</td>
					<td>
						<p>
							<label><input type="checkbox" name="strategy1" />白名单用户自动上网</label>
						</p>
						<p>
							<label><input type="checkbox" name="strategy2" />会员自动上网</label>
						</p>
						<p>
							<label><input type="checkbox" name="strategy3" />启用黑名单</label>
						</p>
					</td>
				</tr> -->
				<tr>
					<td colspan="2">
						<div style="display : none">
							<input type="reset" id="resetButton" /> <input type="hidden" id="placeID" /> <input type="hidden" id="placeCode" /> <input type="hidden" id="placeCtrlCode" /> <input type="hidden" id="creator" value="${creator }" />
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>

<script type="text/javascript">
	$(function(){
		$("#gridTable").jqGrid({
			url:'${ctx}/system/surfWebSet/loadGrid.do',
			mtype : 'post',
			datatype : "json",
			postData : {"creator" : ${creator}},
			height: 'auto',
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames:['场所名称','免费时长','上网方式', '操作', 'id', 'placeId', 'placeCode', 'placeCtrlCode', 'freeCtrlCode', 'freeTime'],
			colModel:[
				{name:'placeName',index:'placeName', align: 'center', sorttype:"string", width : '25'},
				{name:'freeHour',index:'freeTime',align: 'center', width : '25'},
				{name:'surfWay',index:'surfWay', align: 'center', width : '25',hidden:true},
				{name:'operate',index:'operate',sortable: false, align: 'center', width : '25'},
				{name:'id',index:'id',hidden:true},
				{name:'placeId',index:'placeId',hidden:true},
				{name:'placeCode',index:'placeCode',hidden:true},
				{name:'placeCtrlCode',index:'placeCtrlCode',hidden:true},
				{name:'feeCtrlCode',index:'feeCtrlCode',hidden:true},
				{name:'freeTime',index:'freeTime',hidden:true}
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
					editHtml = preUpdHtm + " onclick='initSurfParam(" + ids[i] + ")' " + sufUpdHtm;
					jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:editHtml});
				}
				//异步加载js，确定拥有的权限
				$("body").append("<script type='text/javascript' src='${ctx}/scripts/authority.js'><\/script>");
			},
			pager:"#gridPager",
// 			multiselect: true,	// 复选框设置
		});
		
		//是否显示策略
		/* $("input[name='surfWay']").change(function() {
			var code = $("input[name='surfWay']:checked").val();
			if("Y" == code) {
				$("#strategyTr").show();
			} else {
				$("#strategyTr").hide();
			}
		}); */
		bindEvent();
	});
	
	//打开dialog
	function openDialog() {
		$("#surfDialog").dialog({
			width : '300',
			height : '360',
			resizable : false,
			modal : true,
			buttons : {
				"保存" : function() {
					saveSurfWebSet();
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
	
	//上网设置时，打开dialog时进行数据初始化
	function initSurfParam(id) {
		var rowData = $("#gridTable").jqGrid("getRowData", id);
// 		$("#placeID").val(rowData.placeId);
// 		$("#placeCode").val(rowData.placeCode);
// 		$("#placeCtrlCode").val(rowData.placeCtrlCode);
// 		$("#freeTimeUl li[value='" + rowData.freeTime + "']").click();
// 		var surfWayCode = rowData.placeCtrlCode.substring(3,4);
// 		$("input[value=" + surfWayCode + "]").prop('checked', true);
// 		if("Y" == surfWayCode) {
// 			$("#strategyTr").show();
// 		} else {
// 			$("#strategyTr").hide();
// 		}
// 		var feeCtrlCode = rowData.feeCtrlCode;
// 		for(var i = 1; i < 4; i++) {
// 			var code = feeCtrlCode.substring(i - 1, i);
// 			if(code == "Y"){
// 				$("input[name=strategy" + i + "]").prop('checked', true);
// 			} 
// 		}
		$("#placeID").val(rowData.placeId);
		$("#placeCode").val(rowData.placeCode);
		$("#freeTimeUl li[value='" + rowData.freeTime + "']").click();
		openDialog();
	}
	
	//保存上网设置
	function saveSurfWebSet() {
		showMsg("确定保存上网设置吗？", 2, function() {
			$(this).dialog('close');
			var placeId = $("#placeID").val();
			var placeCode = $("#placeCode").val();
			var freeTime = $("#freeTime").attr("freeTimeValue");
// 			var creator = $("#creator").val();
// 			var surfWayCode = $("input[name='surfWay']:checked").val();
// 			var placeCtrlCode = $("#placeCtrlCode").val();
// 			placeCtrlCode = placeCtrlCode.substring(0, 3) + surfWayCode + placeCtrlCode.substring(4, 5);
// 			var feeCtrlCode = "";
// 			for(var i = 1; i < 4; i++) {
// 				if($("input[name=strategy" + i + "]").prop('checked') == true) {
// 					feeCtrlCode += "Y";
// 				} else {
// 					feeCtrlCode += "N";
// 				}
// 			}
			//预留字段
// 			feeCtrlCode += "NN";
			$.ajax({
				url : '${ctx}/system/surfWebSet/updateSurfWebSet.do',
				type : 'post',
				dataType : 'json',
				data : {'placeId' : placeId, 'placeCode' : placeCode, 'freeTime' : freeTime},
				success : function(data) {
					resultHandle(data);
				}
			});
		});
	}
	
	//jqgrid刷新
	function refresh() {
		jQuery("#gridTable").trigger("reloadGrid");
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
	
	//关闭dialog时清除数据
	function closeDialog() {
		$("#placeID").val("");
		$("#placeCode").val("");
		$("#placeCtrlCode").val("");
		clearData();
		$("#surfDialog").dialog("close");
	}
	
	//清除页面数据
	function clearData(){
		$("#resetButton").click();
	}
</script>

