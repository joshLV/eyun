<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
<script type="text/javascript" src="${ctx}/scripts/smsmanage/sms.js"></script>
<script type="text/javascript" src="${ctx}/common/scripts/jquery.ui.datepicker-zh-CN.js"></script>
<link rel="stylesheet" href="${ctx}/css/sysmanage/portal.css" />
<style>
.btn_style02{ background:url(../images/sms/btn_bg2.png); width:60px; height:27px; border:0; cursor:pointer;}
.btn_style02:enabled:hover{background:url(../images/sms/btn_bghover2.png); color:#555; }
/*  .PublicButton{margin-left:0px;padding: 0 5px;} */
.sub_tabs .ui-widget-header {background: #6099d6;}
#smsBuy{
    display: inline;
    font-size: 16px;
    float: right;
    margin-top: 5px;
    margin-right: 10px;
    cursor:pointer;
    }
.selected{
	background-color: rgb(255, 174, 94);
}

.lm_c_title{height:25px;  padding-top:20px; font-weight:bold; color:#555; border-bottom:1px #c9e5f3 solid; padding-bottom:10px;}
.lm_c_title_td{ font-size:16px;}

.lm_add{ padding:0 30px;}
.lm_add td{ padding:15px 10px; font-size:14px;}
.lm_add span{ font-size:12px; color:red;}
.td-wwbdis{
	border-right:1px dotted #ccc;
	border-bottom: 1px dotted #ccc;
}
.td-msgbuy{
	border-bottom: 1px dotted #ccc;
}

.wwbi-btn{
	border:0; 
	cursor:pointer; 
	width:97px; 
	height:40px;
	margin-top: 10px;
}
.wwbi-btn-enable{
	background:url(../wwb/images/qrtj.jpg) no-repeat; 
}
.wwbi-btn-disable{
	background:url(../wwb/images/qrtjbak.jpg) no-repeat;
	cursor: default;
}
.tab{border-collapse:collapse; border-spacing:0; border-left:0px solid #CDDEEC; border-top:0px solid #CDDEEC; }
.tdd{border-right:0px solid #CDDEEC; border-bottom:0px solid #CDDEEC; padding:3px 15px; text-align:left; color:#3C3C3C;}
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
.input_out{
/*height:16px;默认高度width: 40%;*/
padding:2px 8px 0pt 3px;
height:22px;
width:100px;
border:1px solid #CCC;
background-color:#F0F8FF;
}

</style>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">
		<tr>
			<td width="380">
				<ul class="PublicTime " style="display:inline-block; margin-right:0px; width: 420px; border: 0px solid red;">
					<input type="text" style="width:43%" class="PublicText TextTime" id="datePickerLeft"  search-field='"sendDate"' search-type="search" search-fieldType="D" search-operation="GE">
					<u>至</u>
					<input type="text" style="width:43%" class="PublicText TextTime" id="datePickerRight" search-field='"sendDate"' search-type="search" search-fieldType="D" search-operation="LE">
				</ul>
			</td>
			<td style="text-align: left;">
				<input style="border: 0px solid red; margin-left: 0px;" type="button" class="PublicButton" value="查 询" onclick='toseach("customize","${ctx}")'>
				<input type="reset" class="PublicButton" value="重 置" onclick="resetFun()">
			</td>
		</tr>
	</table>
    <div class="PublicC_t" style="border: 0px solid red;padding-left:0px;">
		<ul class="operation fr" id="functionDiv"></ul>
		<%-- <ul class="PublicTime operation " style="display:inline-block; width: 40%;border: 0px solid red;">
			<input type="text" style="width:42%" class="PublicText TextTime" id="datePickerLeft"  search-field='"sendDate"' search-type="search" search-fieldType="D" search-operation="GE">
			<u>至</u>
			<input type="text" style="width:42%" class="PublicText TextTime" id="datePickerRight" search-field='"sendDate"' search-type="search" search-fieldType="D" search-operation="LE">
		</ul>
		<ul class="operation " style="display:inline-block;border: 0px solid red; margin-top: 3px;">
		<input style="border: 0px solid red; height: 29px" type="button" class="PublicButton" value="查 询"
			onclick='toseach("customize","${ctx}")'/>
			<input type="hidden" id="SurplusSms" >
			<input type="hidden" id="placeid" >
		</ul> --%>
	</div>
	<div id="tabs" class="sub_tabs">
	
		<ul>
			<li><a href="#tabs-2">购买记录</a></li>
			<li><a href="#tabs-1" >使用记录</a></li>
		</ul>
	
		<!-- 使用记录列表 -->
		<div id="tabs-1">
			<table id="getSmsUseRecord" >
			</table>
			<div id="gridPager0"></div>
		</div>
		
		<!-- 购买记录列表 -->
		<div id="tabs-2">
			<table id="getSmsBuyRecord"></table>
			<div id="gridPager1"></div>
		</div>
	</div>
<div id="rechargeDialog" title="短信购买" class="dalog" style="display : none;">
	<table class="PublicTable tab" style=" width: 99%;height: 99%" cellpadding="0" cellspacing="0">
		<tr height="40">
			<td class="tdd" style="width: 40%; text-align: right;">选择场所：</td>
			<td class="tdd" style="width: 60%;">
				<div style="width: 300px" class="select PublicSelect hack">
					<input type="text" readonly="readonly" id="placeCode" value="请选择场所" placeValue="-1" search-valAttrName="placeValue">
					<i></i>
					<ul class="statusUlcls" style="z-index : 9999">
					    
					</ul>
				</div>
			</td>
		</tr>
		<tr height="40">
			<td class="tdd" style="text-align: right">账户余额：</td>
			<td class="tdd">
				&nbsp;&nbsp;<span style="width:70px; border-bottom: 0px solid #000000;border-left: 0;
	  			border-right: 0;border-top: 0;" readonly id="bala"></span>&nbsp;元
			</td>
		</tr>
		<tr height="40">
			<td class="tdd" style="text-align: right">短信费用：</td>
			<td class="tdd">
				&nbsp;&nbsp;<input readonly style="border-bottom: 0px solid #000000;border-left: 0;
	   			border-right: 0;border-top: 0;" value="0.1&nbsp;元 /条" onlyread />
			</td>
		</tr>
		<tr height="40">
			<td class="tdd" style="text-align: right">购买短信：</td>
			<td class="tdd">
				&nbsp;&nbsp;<input class="input_out" type="text" id="smsNumId" value="1" onkeyup="keyup(event); value=value.replace(/[^\d]/g,'')"
					onpaste="return false" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" >
			</td>
		</tr>
		<tr height="40">
			<td class="tdd" style="text-align: right">支付金额：</td>
			<td class="tdd">
				&nbsp;&nbsp;<label><input name="useWwbiMoney" style="width:60px; border-bottom: 0px solid #000000;border-left: 0;
	  			border-right: 0;border-top: 0;" value="0.1" readonly id="howWwbi"/></label>元
	  			<span style="margin-left:12%; color: red;" id=errorMsg>&nbsp;</span>
			</td>
		</tr>
	</table>
</div>
<!-- 短信使用详细信息 -->
<div id=smsUseRecords title="短信使用详细信息" class="dalog" style="display : none;">
	<table border="1" id="data" class="gridtable PublicTable" style="width: 100%">
		<tr>
			<th style="width:30%">手机号</th>
			<th style="width:40%">发送时间</th>
			<th style="width:30%">发送类型</th>
		</tr>
  	</table>
</div>


<script type="text/javascript">
$("#functionDiv").append(buyHtm);

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
		bindEvent();
		defaultDate()
		//tab页必须调用这个方法
		$("#tabs").tabs();
		$("#getSmsUseRecord").jqGrid({
			url : '${ctx}/smsManage/sms/getSmsUseRecord.do',
			mtype : "post",
			datatype : "json",
			postData : {"userId":${creator},"placeCode":$("#selPlaceCode").attr("placeCode"),"forCount":"record","startTime":$("#datePickerLeft").val(),"endTime":$("#datePickerRight").val()},
			height : "auto",
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames : [ "场所", "发送数量", "发送日期", "操作" ],
			colModel : [ {
				name : 'placeName',
				index : 'placeName',
				align : 'center',
				sorttype : "string",
				width : '300'
			}, {
				name : 'smsNum',
				index : 'smsNum',
				align : 'center',
				width : '280'
			}, {
				name : 'sendDate',
				index : 'sendDate',
				sorttype : "string",
				align : 'center',
				width : '200'
			}, {
				name : 'placeId',
				index : 'placeId',
				sorttype : "string",
				align : 'center',
				width : '200'
			}, ],
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
				var ids = $("#getSmsUseRecord").getDataIDs();
				for(var i=0;i<ids.length;i++){
					var rowData = $("#getSmsUseRecord").jqGrid("getRowData",ids[i]);
                    var html = "<a style=\"color:#1f60a6\" href=\"javascript:void(0)\" onclick=\" smsDetail('${ctx}','"+rowData.placeId+"', '"+rowData.sendDate+"')\"><img src=\"../images/preview.png\"></a>";
                    jQuery("#getSmsUseRecord").jqGrid('setRowData',ids[i],{placeId : html});
				}
			},
			
			pager:"#gridPager0",
			footerrow : false,
			userDataOnFooter : true
		}).navGrid('#gridPager0',{edit:false,add:false,del:false,search:false,refresh : false})
 

	
		
		 $("#getSmsBuyRecord").jqGrid({
			url : '${ctx}/smsManage/sms/getSmsBuyRecord.do',
			mtype : "post",
			datatype : "json",
			postData : {"userId" : ${creator},"placeCode":$("#selPlaceCode").attr("placeCode"),"startTime":$("#datePickerLeft").val(),"endTime":$("#datePickerRight").val()},
			height : "auto",
			width:"600",
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames : ["场所名称", "购买金额（单位：人民币/元）", "购买数量（单位：条）", "购买时间" ],
			colModel : [ 
			{
				name : 'placeName',
				index : 'placeName',
				align : 'center',
				sorttype : "string",
				width : '200'
			},
			{
				name : 'useWwbiMoney',
				index : 'useWwbiMoney',
				align : 'center',
				sorttype : "string",
				width : '280'
			},{
				name : 'smsNumber',
				index : 'smsNumber',
				align : 'center',
				sorttype : "string",
				width : '230'
			},{
				name : 'sendDate',
				index : 'sendDate',
				align : 'center',
				sorttype : "string",
				width : '220'
			}],
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
			pager:"#gridPager1",
		}).navGrid('#gridPager1',{edit:false,add:false,del:false,search:false,refresh : false}) 
		/*获取场所信息*/
		$.ajax({
	        url: '${ctx}/smsManage/sms/getPlaceList.do',
	        type : 'post',
	        async: false,
	        dataType: 'json',
			data : {"userId" :${creator}},
	        success: function (data) {
	        	var place = data.data;
	        	var buy="<li value='-1'>请选择场所</li>";
	        	$.each(place,function(i,vl){
	        		if(i>0){
	        			buy+="<li value="+vl.id+">"+vl.placeName+"</li>";
	        		}
	        	})
	        	$(".statusUlcls").html(buy);
	        	bindEvent();
	        }
	    })
	})
	
	/* 购买短信 */
	$("#buyFunc").click(function() {
       var errorMsg = '跳转异常';
		 $.ajax({
              url : '${ctx}/smsManage/sms/toSmsAdd.do',
              type : "post",
              data : {"userId" :${creator}},
              dataType : "json",
              async : true,
              success : function(data) {
              	var ba = data.data;
              	var dom = $("#placeCode");
              	$("#errorMsg").empty();
           	   	$("#rechargeDialog").dialog("open")
           	   	if(ba.accountBal){
					$("#bala").html(ba.accountBal);
           	   	}else{
					$("#bala").html("0.0");
				}
              },
		   	  error : function() {
			   	showMsg(errorMsg);
	          },
          })
	});
	
	/* 打开数据字典保存的对话框 */
	function openSmsBuyDialog() {
		$(".Stretch_warp").dialog({
			resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			close : function() {
				closeDialog();
			}
		});
	}
	
	/* 关闭dialog时清除数据，以免造成保存和编辑混乱 */
	function closeDialog() {
		//clearData();
		$(".Stretch_warp").dialog("close");
	}
	
	$("#rechargeDialog").dialog({
		autoOpen: false,
		width : '500',
		height : 'auto',
		modal : true,
		buttons : {
			"购买" : function() {
				submitPay('${ctx}')
			},
			"取消" : function() {
				$("#rechargeDialog").dialog("close");
			}
		}
	});
	
	
	$("#smsUseRecords").dialog({
		autoOpen: false,
		width : '700',
		height : '300',
		modal : true,
		buttons : {
			"确认" : function() {
				$("#smsUseRecords").dialog("close");
			},
			"关闭" : function() {
				$("#smsUseRecords").dialog("close");
			}
		}
	});	
	function defaultDate(){
		/*当前日期*/
		var now = new Date();
		/*日期格式化*/  
		var now = formatDate(now);
		$(".TextTime").eq(0).val(now);
		$(".TextTime").eq(1).val(now);
	}
	// 添加重置按钮
	function resetFun() {
		$(".TextTime").val('');
	}
</script>