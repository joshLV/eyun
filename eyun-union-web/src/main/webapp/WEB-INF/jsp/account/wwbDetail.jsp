<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/common/scripts/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>

<style>
.visitor_list_3{ width:100%; height:35px; line-height:36px; background:#f5f5f5;}
.visitor_list_3 li{ width:12.5%; text-align:center; color:#333; float:left; font-size:12px; margin-left: 25px}
.visitor_list_3 li a{ color:#4f93c1;}
.visitor_list_3 li a:hover{ color:#4f93c1;}
.sub_tabs .ui-widget-header {background: #6099d6;}
.sle{width: 100%;height: 40px;border-style: none;padding: 0px;}
table.gridtable {font-family: verdana,arial,sans-serif;font-size:11px;color:#333333;border-width: 1px;border-color: #666666;border-collapse: collapse;}
table.gridtable th {border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #dedede;}
table.gridtable td {border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #ffffff;}
</style>
<script>
$("#dateLeft").datepicker({
		showAnim : 'slide',
		changeMonth:true,	//显示月份下拉菜单
		changeYear:true		//显示年份下拉菜单	
	});
	$("#dateRight").datepicker({
		showAnim : 'slide',
		changeMonth:true,	//显示月份下拉菜单
		changeYear:true		//显示年份下拉菜单	
	});
</script>
<div>
	<table border="0" cellspacing="0" cellpadding="0" class="PublicTable">
		<tr>
			<td style="width: 60px"> 选择场所：</td>
			<td>
				<div style="width: 230px" class="select PublicSelect hack PublicText">
					<select class="sle"  style="width: 95%;height: 100%" id="placeCode" name="placeCode" onchange="getDevice()">
						<c:forEach var="place" items="${placeList}" varStatus="status">
							<option style="z-index : 9999" value="${place.placeCode}">${place.placeName}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td style="width: 70px">&nbsp&nbsp选择设备：</td>
			<td>
				<div style="width: 240px" class="PublicSelect hack PublicText">
					<select class="sle" style="width: 90%;height: 100%" id="placeDeviceId" name="placeDeviceId" onchange="placeListQuery()">
						
					</select>
				</div>
			</td>
			<td width="45px">
				&nbsp时间：
			</td>
			<td>
				<div class="PublicTime" style="display:inline-block"><!--style="display:inline-block"  -->
					<input type="text" style="width:42%" class="PublicText TextTime" id="dateLeft">
					<u>至</u>
					<input type="text" style="width:42%" class="PublicText TextTime" id="dateRight">
				</div>
			</td>
			<td>
				<input type="button" class="PublicButton" value="查 询"
						onclick="placeListQuery();">
					<input type="hidden" id="placeSmsNumId">
					<input type="hidden" id="dateRangeId" >
			</td>
			</tr>
	  </table>
</div>
	<div id="tabs" style="border: 1px solid #dddddd;" class="sub_tabs">
		<ul>
			<li><a href="#tabs-1" >购买记录</a></li>
			
			<li><a href="#tabs-2" >使用记录</a></li>
		</ul>
	
		<!-- 购买记录列表 -->
		<div id="tabs-1" class="tb">
			<table id="getWwbBuyRecord" >
			</table>
			<div id="gridPager0"></div>
		</div>
		
		<!-- 使用记录列表 -->
		<div id="tabs-2" class="tb">
			<table id="getWwbUseRecord"></table>
			<div id="gridPager1"></div>
		</div>
	</div><!--#6099d6;  white; -->
	<%--<ul class="visitor_list_3" id="tbs" style="background:#6099d6; margin-left: 0px">
		<li id="totalBuy" style="width: 30%; color:white;">总金额：<span id="totalBu"></span></li>
		<li id="totalUse" style="width: 30%; color: white;">消费金额：<span id="totalUs"></span></li>
		<li id="balance" style="width: 30%; color: white;">余额：<span id="balan"></span></li>
	</ul>--%>
	<div id=WwbUseRecords title="旺旺币消费详细信息" class="dalog" style="display : none;">
		<table border="1" id="data" class="gridtable PublicTable" style="width: 100%">
			<tr>
				<th style="width:10%">消费人姓名</th>
				<th style="width:20%">消费人证件号</th>
				<th style="width:15%">消费人卡号</th>
				<th style="width:15%">消费人卡类型</th>
				<th style="width:10%">消费金额</th>
				<th style="width:15%">消费开始时间</th>
				<th style="width:15%">消费结束时间</th>
			</tr>
	  	</table>
    </div>
<script type="text/javascript">
 	$(function(){
 		$("#tabs").tabs();
 		defaultDates();
 		getDevice();
 		getWwbBalance()
	});
 		$("#getWwbBuyRecord").jqGrid({
 			url : '${ctx}/account/wwbDetail/getWwbBuyRecord.do',
 			mtype : "post",
 			datatype : "json",
 	      	postData : {"placeCode": $("#placeCode").val(),"placeDeviceId": $("#placeDeviceId").val(),"startTime":$("#dateLeft").val(),"endTime":$("#dateRight").val()},
 			autowidth: true, 
 			height : "auto",
 			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
 			altRows : true,
 			colNames : [ "编号", "消费人民币金额", "购买旺旺币金额", "操作人","类型","状态", "操作时间"],
 			colModel : [ 
 			{name : 'id',index : 'id',align : 'center',sorttype : "string",width : '20'}, 
 			{name : 'rMBFee',index : 'rMBFee',align : 'center',sorttype : "string",width : '50'},
 			{name : 'wWBFee',index : 'wWBFee',align : 'center',sorttype : "string",width : '20'},
 			{name : 'optorName',index : 'optorName',align : 'center',width : '50'},
 			{name : 'payChannel',index : 'payChannel',align : 'center',width : '50'},
 			{name : 'status',index : 'status',align : 'center',width : '50'},
 			{name : 'optTimeStr',index : 'optTime',sorttype : "string",align : 'center',width : '50'}
 			], 			
 			rowList : [10,20,30],
 			jsonReader : {
 				rowNum : "rowNum",
 				root : "dataRows", // 数据行（默认为：rows）
 				page : "curPage", // 当前页
 				total : "totalPages", // 总页数
 				records : "totalRecords", // 总记录数
 				repeatitems : false
 			//设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
 			},
 			pager:"#gridPager0",
 			userDataOnFooter : true
 		}).navGrid('#gridPager0',{edit:false,add:false,del:false,search:false,refresh : false});
			
		/*旺旺币消费记录  */
		$("#getWwbUseRecord").jqGrid({
			url : '${ctx}/account/wwbDetail/getWwbUseRecord.do',
			mtype : "post",
			datatype : "json",
	      	postData : {"placeCode": $("#placeCode").val(),"placeDeviceId": $("#placeDeviceId").val(),"startTime":$("#dateLeft").val(),"endTime":$("#dateRight").val()},
			autowidth: true, 
			height : "auto",
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略,"placeDeviceId"
			altRows : true,
			colNames : [ "编号", "订单号", "订单总消费金额", "订单开始时间", "订单结束时间","余额"],
			colModel : [ 
			{name : 'id',index : 'id',align : 'center',sorttype : "string",width : '150'}, 
			{name : 'orderNum',index : 'orderNum',align : 'center',sorttype : "string",width : '150'},
			{name : 'orderMoneyCount',index : 'orderMoneyCount',align : 'center',sorttype : "string",width : '150'},
			{name : 'orderStartTimeStr',index : 'orderStartTime',align : 'center',width : '200'},
			{name : 'orderEndTimeStr',index : 'orderEndTime',align : 'center',sorttype : "string",width : '200'},
			{name : 'balance',index : 'balance',sorttype : "string",align : 'center',width : '130'},
			//{name:"placeDeviceId",index:"placeDeviceId",hidden:true}
			],
			rowList : [10,20,30],
			jsonReader : {
				rowNum : "rowNum",
				root : "dataRows", // 数据行（默认为：rows）
				page : "curPage", // 当前页
				total : "totalPages", // 总页数
				records : "totalRecords", // 总记录数
				repeatitems : false
			// 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
			},
			gridComplete:function(rowid, rowdata){
				var ids = $("#getWwbUseRecord").getDataIDs();
				var deviceId = $("#placeDeviceId").val();
				for(var i=0;i<ids.length;i++){
					var rowData = $("#getWwbUseRecord").jqGrid("getRowData",ids[i]);
                    var html = "<a style=\"color:#1f60a6\" href=\"javascript:void(0)\" onclick=\" submitOrder('"+deviceId+"', '"+rowData.orderNum+"')\" >"+rowData.orderNum+"</a>";
                    jQuery("#getWwbUseRecord").jqGrid('setRowData',ids[i],{orderNum : html});
				}
			},
			pager:"#gridPager1",
			userDataOnFooter : true
		}).navGrid('#gridPager0',{edit:false,add:false,del:false,search:false,refresh : false});
			
	//按条件查询列表记录
	function placeListQuery(){
		if(checks() != false){
			getWwbBalance();
			$("#getWwbBuyRecord").jqGrid('setGridParam', {
		        page : 1,
		        postData:{"placeCode": $("#placeCode").val(),"placeDeviceId": $("#placeDeviceId").val(),"startTime":$("#dateLeft").val(),"endTime":$("#dateRight").val()},
		        }).trigger('reloadGrid');
		        
			$("#getWwbUseRecord").jqGrid('setGridParam', {
		        page : 1,
		        postData:{"placeCode": $("#placeCode").val(),"placeDeviceId": $("#placeDeviceId").val(),"startTime":$("#dateLeft").val(),"endTime":$("#dateRight").val()},
		    }).trigger('reloadGrid');
		}
	}

	//获取设备列表
	function getDevice() {
		//清除子节点
		$("#placeDeviceId").empty();
		$.ajax({
			url : '${ctx}/account/wwbAccount/getDeviceList.do',
			type : 'post',
			dataType : 'json',
			data : {"placeCode" : $("#placeCode").val(),"placeDeviceId" : $("#placeDeviceId").val()},
			async : false,
			success : function(data) {
				var arr = data.deviceList;
				$.each(arr, function(i, value) {
					$("#placeDeviceId").append("<option value=" + this.id + ">" + this.serial_num + "</option>");
				});
			}
		});
		placeListQuery()
	}
	
	/* 获取旺旺币余额 */
	function getWwbBalance(){
		var errorMsg = "账户余额获取失败!";
		$.ajax({
		    url: "${ctx}/account/wwbAccount/getWwbAccount.do",
	        type : 'post',
	        data : {"placeCode": $("#placeCode").val(),"placeDeviceId": $("#placeDeviceId").val(),"startTime":$("#datePickerLeft").val(),"endTime":$("#datePickerRight").val()},
	        async : false,
	        dataType: 'json',
	        success: function (data) {
	        	$("#totalBu").empty();
				$("#totalUs").empty();
				$("#balan").empty();
	        	if(data != null){
	        		console.log(data);
	        		$("#totalBu").text((data.totalBuyWwb == "" || data.totalBuyWwb == null) ? "0.00" : data.totalBuyWwb);
					$("#totalUs").text((data.totalUseWWB == "" || data.totalUseWWB == null) ? "0.00" : data.totalUseWwB);
					$("#balan").text((data.balance==null|| data.balance == "") ? "0.00" : data.balance);
	        	}else{
	        		$("#totalBu").text("0.00");
					$("#totalUs").text("0.00");
					$("#balan").text("0.00");
	        	}
	        },
	        error : function() {
				alert(errorMsg);
		    }
		 }); 
	}
	
	function submitOrder(Deviceid,ordernum){
		$.ajax({
			url : '${ctx}/account/wwbDetail/getWwbRecordDetail.do',
			type : 'post',
			dataType : 'json',
			data : {"orderNum" :ordernum,"placeDeviceId" : Deviceid},
			async : false,
			success : function(data) {
				if(data != ''){
					$("#data tr").each(function(j,va){
				 		if(j > 0 ){
						 	$(this).empty();
				 		}
	        		})
					$("#WwbUseRecords").dialog("open");
					$.each(data, function(i, value) {
						var entity = "<tr><td>"+this.name+"</td><td>"+this.certificateNum+"</td><td>"+this.cardNum+"</td><td>"+this.cardType+"</td><td>"+this.money+"</td><td>"+this.startTimeStr+"</td><td>"+this.endTimeStr+"</td></tr>"
						$("#data").append(entity);
					});
				}else{
					$("#WwbUseRecords").dialog("open");
				}
			}
		});
	}
	
	
	$("#WwbUseRecords").dialog({
		autoOpen: false,
		width : '1000',
		height : '300',
		modal : true,
		buttons : {
			"确认" : function() {
				$("#WwbUseRecords").dialog("close");
			},
			"关闭" : function() {
				$("#WwbUseRecords").dialog("close");
			}
		}
	});
	
	function checks(){
		var startTime = $("#dateLeft").val();
		var endTime = $("#dateRight").val();
		if(startTime=='' || endTime ==''){
			showWaittingMsg("请选择起止日期！", 4000);
			return false;
		}
		var startNum = parseInt(startTime.replace(/-/g,''),10);
		var endNum = parseInt(endTime.replace(/-/g,''),10);
		if(startNum > endNum){
			showWaittingMsg("查询起始日期不能大于截止日期！", 4000);
			return false;
		}
		var diffDate1 = GetDateDiff(endTime,startTime,"day");
		var diffDate2 = GetDateDiff(startTime,endTime,"day");
		if(diffDate1 > 90 || diffDate2 > 90) {
			showWaittingMsg("日期查询范围不能超过90天！", 4000);
			return false;  
		}
		return true;
	}
	/* 设置默认日期时间 */
	function defaultDates(){
		/*当前日期*/
		var nos = new Date();
		/*日期格式化*/  
		var no =formatDate(nos);
		$("#dateLeft").val(no);
		$("#dateRight").val(no);
	}
	function formatDate(date) { 
		var myyear = date.getFullYear(); 
		var mymonth = date.getMonth()+1; 
		var myweekday = date.getDate();
		if(mymonth < 10){ 
		mymonth = "0" + mymonth; 
		} 
		if(myweekday < 10){ 
		myweekday = "0" + myweekday; 
		} 
		return (myyear+"-"+mymonth + "-" + myweekday); 
	}
</script>
