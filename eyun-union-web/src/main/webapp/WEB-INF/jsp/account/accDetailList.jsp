<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/common/scripts/jquery.ui.datepicker-zh-CN.js"></script>
<style>
.sub_tabs .ui-widget-header {
	background: #6099d6;
}
</style>
<script>
</script>
<div class="PublicC_t" style="border: 0px solid red;padding-left:0px;">
	<ul class="operation fr" id="functionDiv"></ul>
	<ul class="PublicTime operation " style="display:inline-block; width: 40%;border: 0px solid red;">
		<input type="text" style="width:42%" class="PublicText TextTime" id="datePickerLeft"  search-field='"sendDate"' search-type="search" search-fieldType="D" search-operation="GE">
		<u>至</u>
		<input type="text" style="width:42%" class="PublicText TextTime" id="datePickerRight" search-field='"sendDate"' search-type="search" search-fieldType="D" search-operation="LE">
	</ul>
	<ul class="operation " style="display:inline-block;border: 0px solid red; margin-top: 3px;">
	<input style="border: 0px solid red; margin-left: 0px; height: 29px" type="button" class="PublicButton" value="查 询"
		onclick='refreshGrid()'/>
	</ul>
</div>
<div id="tabs" style="border:0px" class="sub_tabs">

	<ul>
		<li><a href="#tabs-1">充值记录</a></li>

		<li><a href="#tabs-2">支出记录</a></li>
	</ul>

	<!-- 购买记录列表 -->
	<div id="tabs-1">
		<table id="getAccBuyRecord">
		</table>
		<div id="gridPager0"></div>

	</div>

	<!-- 使用记录列表 -->
	<div id="tabs-2">
		<table id="getAccUseList"></table>
		<div id="gridPager1"></div>
	</div>
</div>

<script type="text/javascript">
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
	$(function() {
		defaultDate();
		//tab页必须调用这个方法
		$("#tabs").tabs();
		$("#getAccBuyRecord").jqGrid({
			url : '${ctx}/account/accDetail/getAccBuyRecord.do',
			mtype : "post",
			datatype : "json",
	      	postData : {"startTime":$("#datePickerLeft").val(),"endTime":$("#datePickerRight").val()},
 			height : "auto",
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames : [ "编号", "订单号", "支付方式", "充值金额（单位:元）", "操作人", "交易状态", "操作时间", "操作","payStatusRemark","wwbiFee"],
			
			colModel : [ 
			{name : 'rowNum',index : 'rowNum',align : 'center',sorttype : "string",width : '20'}, 
			{name : 'buyWwbiRecordId',index : 'buyWwbiRecordId',align : 'center',sorttype : "string",width : '50'},
			{name : 'payChannel',index : 'payChannel',align : 'center',sorttype : "string",width : '20'},
			{name : 'rmbFee',index : 'rmbFee',align : 'center',width : '50'},
			{name : 'userName',index : 'userName',sorttype : "string",align : 'center',width : '13'}, 
			{name : 'payStatusRemark',index : 'payStatusRemark',sorttype : "string",align : 'center',width : '37'}, 
			{name : 'updateTimeStr',index : 'opttime',align : 'center',width : '37'},
			{name:'operate',index:'operate',sortable: false, align: 'center', width : '20'},
			{name:"payStatus",index:"payStatus",hidden:true},
			{name:"wwbiFee",index:"wwbiFee",hidden:true}
			],
			rowList : ${rowList},
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
				var ids = $("#getAccBuyRecord").getDataIDs();//jqGrid('getDataIDs');
				for(var i=0;i<ids.length;i++){
					var rowData = $("#getAccBuyRecord").jqGrid("getRowData",ids[i]);
					var html = "----";
					if(rowData.payStatus != 8)
                    var html = "<a style=\"color:#1f60a6\" href=\"javascript:void(0)\" onclick=\" submitOrder('"+rowData.buyWwbiRecordId+"', '"+rowData.rmbFee+"', '"+rowData.wwbiFee+"', '"+rowData.payChannel+"')\" >重新支付</a>";
                    jQuery("#getAccBuyRecord").jqGrid('setRowData',ids[i],{operate:html});
				}

			},
			
			pager:"#gridPager0",
			userDataOnFooter : true
		}).navGrid('#gridPager0',{edit:false,add:false,del:false,search:false,refresh : false})
		
		$("#getAccUseList").jqGrid({
			url : '${ctx}/account/accDetail/getAccUseRecord.do',
			mtype : "post",
			datatype : "json",
			postData : {"placeCode":$("#selPlaceCode").attr("placeCode"),"startTime":$("#datePickerLeft").val(),"endTime":$("#datePickerRight").val()},
 			height : "auto",
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames : [ "编号", "会员", "场所名称", "账户支出金额（单位：元)",  "操作时间", "消费类型","placeID"],
			colModel : [ 
			{name : 'id',index : 'id',align : 'center',sorttype : "string",width : '100'}, 
			{name : 'membername',index : 'membername',align : 'center',sorttype : "string",width : '150'},
			{name : 'placename',index : 'placename',align : 'center',sorttype : "string",width : '200'},
			{name : 'useWWBiMoney',index : 'useWWBiMoney',align : 'center',width : '178'},
			{name : 'optTime',index : 'optTime',sorttype : "string",align : 'center',width : '200'}, 
			{name : 'useType',index : 'useType',sorttype : "string",align : 'center',width : '150'}, 
			{name : "placeID",index:"placeID",hidden:true}
			],
			rowList : ${rowList},
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
				var ids = $("#getAccUseList").getDataIDs();//jqGrid('getDataIDs');
				for(var i=0;i<ids.length;i++){
					var rowData = $("#getAccUseList").jqGrid("getRowData",ids[i]);
					if(rowData.placeID == -1){
                    	var html = "暂未分配场所";
                    	jQuery("#getAccUseList").jqGrid('setRowData',ids[i],{placename:html});
                    }
				}
			},
			pager:"#gridPager1",
			userDataOnFooter : true
		}).navGrid('#gridPager0',{edit:false,add:false,del:false,search:false,refresh : false})
		
	})
	
	
    function submitOrder(id, WWBiFee, RMBFee, payChannel){
		//Base64加密
		var base = new Base64();
		var userKey = id + ',' + WWBiFee + ',' + RMBFee;
		var userKeyCode = base.encode(userKey);
		
		if("易宝支付" == payChannel) {
			  var errorMsg = '跳转异常';
				 $.ajax({
					    url: "${ctx}/account/accAccount/againYfbPay.do?userKey="+userKeyCode,
				        type : 'post',
				        async : true,
				        dataType: 'html',
				        success: function (data) {
		                 $("#main").html(data);
				        },
				        error : function() {
							   alert(errorMsg);
					       }
				    }); 

		} else if("支付宝" == payChannel){
			 var errorMsg = '跳转异常';
			 $.ajax({
				    url: "${ctx}/account/accAccount/againZfbPay.do?userKey="+userKeyCode,
			        type : 'post',
			        async : true,
			        dataType: 'html',
			        success: function (data) {
		                $("#main").html(data);
			        },
			        error : function() {
						   alert(errorMsg);
				    }
			 }); 
		} else if("微信" == payChannel) {
						 var errorMsg = '跳转异常';
			 $.ajax({
				    url: "${ctx}/account/accAccount/againWxPay.do?userKey="+userKeyCode,
			        type : 'post',
			        async : true,
			        dataType: 'html',
			        success: function (data) {
		                $("#main").html(data);
			        },
			        error : function() {
						   alert(errorMsg);
				    }
			 }); 
		}
	}
	function refreshGrid(){
		if(check()){
			$("#getAccBuyRecord").jqGrid('setGridParam', {
		        page : 1,
		        postData:{"startTime":$("#datePickerLeft").val(),"endTime":$("#datePickerRight").val()},
		     }).trigger('reloadGrid')
			$("#getAccUseList").jqGrid('setGridParam', {
		        page : 1,
		        postData:{"placeCode":$("#selPlaceCode").attr("placeCode"),"startTime":$("#datePickerLeft").val(),"endTime":$("#datePickerRight").val()},
		    }).trigger('reloadGrid')
	    }
	}
	
	/* 日期格式化 */
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
	/* 检查按日期查询是否符合要求 */				
	function check(){
		var startTime = $("#datePickerLeft").val();
		var endTime = $("#datePickerRight").val();
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
	/* 设置初始化查询日期条件 */
	function defaultDate(){
		/*当前日期*/
		var now = new Date();
		/*日期格式化*/  
		var now = formatDate(now);
		$("#datePickerLeft").val(now);
		$("#datePickerRight").val(now);
	}

</script>