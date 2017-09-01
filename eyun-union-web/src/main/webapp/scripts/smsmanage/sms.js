/**
 * author：王乾
 * @param times
 */

	/*搜索*/
	function loadPostSms(ctx){
		$("#getSmsUseRecord").jqGrid('setGridParam', {
	        page : 1,
	        postData:{"placeCode":$("#selPlaceCode").attr("placeCode"),"startTime":$("#datePickerLeft").val(),"endTime":$("#datePickerRight").val()},
	     }).trigger('reloadGrid')
		
		$("#getSmsBuyRecord").jqGrid('setGridParam', {
	        page : 1,
	        postData:{"placeCode":$("#selPlaceCode").attr("placeCode"),"startTime":$("#datePickerLeft").val(),"endTime":$("#datePickerRight").val()},
	    }).trigger('reloadGrid')
	}
	function findSmsUserRecordStatc(ctx,searchValue,placeId){
	  $.ajax({
		    url: ctx+'/smsManage/sms/getUseRecSta.do',
	        type : 'post',
	        async: false,
	        dataType: 'json',
			data : {"searchValue":searchValue,"placeId":placeId},
	        success: function (data) {
                $("#SurplusSms").val(data.data.SurplusSms);
	        }
	   }); 
	}
	
	/**
	 * author：王乾
	 * @param times
	 */
//根据时间段查询
function toseach(times,ctx){
	var now = new Date(); //当前日期 
	var nowDayOfWeek = now.getDay(); //今天本周的第几天 
    var nowDay = now.getDate(); //当前日 
    var nowMonth = now.getMonth(); //当前月 
    var nowYear = now.getYear(); //当前年 
	nowYear += (nowYear < 2000) ? 1900 : 0; 
	if(check() == false){
		return
	}
	if(times == '1'){
		$(".one").addClass("selected");
		$(".serven").removeClass("selected");
		$(".thirty").removeClass("selected");
		var now =formatDate(now);
		$(".TextTime").eq(0).val(now);
		$(".TextTime").eq(1).val(now);
	}else if(times == '7'){
		$(".serven").addClass("selected");
		$(".one").removeClass("selected");
		$(".thirty").removeClass("selected");
		var weekStartDate = new Date(nowYear, nowMonth, nowDay - (nowDayOfWeek-1)); 
		weekStartDate = formatDate(weekStartDate);
		var weekEndDate = new Date(nowYear, nowMonth, nowDay + (7 - nowDayOfWeek)); 
		weekEndDate = formatDate(weekEndDate);
		$(".TextTime").eq(0).val(weekStartDate);
		$(".TextTime").eq(1).val(weekEndDate);
	}else if(times == '30'){
		$(".thirty").addClass("selected");
		$(".serven").removeClass("selected");
		$(".one").removeClass("selected");
		var monthStartDate = new Date(nowYear, nowMonth, 1); 
		monthStartDate = formatDate(monthStartDate);
		var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowYear,nowMonth)); 
		monthEndDate = formatDate(monthEndDate);
		$(".TextTime").eq(0).val(monthStartDate);
		$(".TextTime").eq(1).val(monthEndDate);
	}else if(times == 'customize'){
		$(".one").removeClass("selected");
		$(".serven").removeClass("selected");
		$(".thirty").removeClass("selected");
	}
	loadPostSms(ctx);
}

/**
 * author:王乾
 * @param date
 * @returns {String}
 */
//日期格式化
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

//获得某月的天数 
function getMonthDays(nowYear,myMonth){ 
	var monthStartDate = new Date(nowYear, myMonth, 1); 
	var monthEndDate = new Date(nowYear, myMonth + 1, 1); 
	var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24); 
	return days; 
}

/**
 * 确认购买短信
 * @param ctx
 * @returns {boolean}
 */
function submitPay(ctx) {
	if($("#placeCode").attr("placeCodeValue")){
	   var placeId = $("#placeCode").attr("placeCodeValue");
	}else{
	   var placeId = $("#placeCode").attr("placeValue");
	}
	var WWBiFee = $("#howWwbi").val();
	var smsNum = $("#smsNumId").val();
	var balance = parseFloat($("#bala").html());
	if(isNaN(balance)) {
		$("#errorMsg").html("账户余额不足请充值");
		return false;
	}

	if (WWBiFee == null || WWBiFee == "" || WWBiFee == "0") {
		$("#errorMsg").html("请输入正确购买短信数量");
		return false;
	}
	if (!(/^\+?[1-9][0-9]*$/.test(WWBiFee * 10))) {
		$("#errorMsg").html("购买短信金额只能填写数字！");
		return false;
	}
	if (parseFloat(WWBiFee) > balance) {
		$("#errorMsg").html("账户余额不足请充值");
		return false;
	}
	var mas = "确定购买？";
	if(placeId==-1){
		mas = mas+'</br></br><span style="margin-left:12%;color:red;text-align: left;">如果不选择场所，则购买的短信需要分配后才能使用</span>';
	}
	showMsg(mas, 1, function() {
		var flagCode = 8;
		$(this).dialog("close");
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : ctx +'/smsManage/sms/buySms.do',
			data : {"useMoney" : WWBiFee,"smsNum" : smsNum,"placeId" : placeId},
			async : false,
			success : function(data) {
				var ms = data.status;
				if (ms == 'SUCC') {
					flagCode = 9;
					showWaittingMsg("短信购买成功！", 4000);
					toseach('customize','${ctx}');
					$("#rechargeDialog").dialog("close");
				} else {
					showWaittingMsg("短信购买失败！", 4000);
					return;
				}
			},
			error : function() {
			   	showMsg(errorMsg);
	        },
		});
	});
}


$(document).ready(function() {
	judgeAmount();
	$("#addWWBi").click(function() {
		var WWBiFee = $.trim($("#smsNumId").val());
		var WWBi = parseInt(WWBiFee) + 1;
		if (WWBiFee == "") {
			$("#smsNumId").val(1);
			$("#howWwbi").val(1 / 10);
		} else if (WWBi <= 999999) {
			$("#smsNumId").val(WWBi);
			$("#howWwbi").val(WWBi / 10);
		}
		judgeAmount();
	});
	// 点击减少按钮
	$("#minusWWBi").click(function() {
		var WWBiFee = parseInt($("#smsNumId").val());
		if (WWBiFee > 1) {
			WWBiFee = WWBiFee - 1;
			$("#smsNumId").val(WWBiFee);
			$("#howWwbi").val(WWBiFee / 10);
			judgeAmount();
		}
	});
	$("#errorTip").html("如果不选择场所，则购买的短信需要分配后才能使用。");
});

// 禁止输入数字以外的字符
$(function() {
	$("#smsNumId").keypress(function(event) {
		var keyCode = event.which;
		if (keyCode == 8 || (keyCode >= 48 && keyCode <= 57)) {
			return true;
		} else {
			return false;
		}
	}).focus(function() {
		this.style.imeMode = 'disabled';
	});
});

function keyup(event) {
	// 判断输入值是否合法
	var flag = false;
	var WWBiFee;
	var Fee = $("#smsNumId").val();
	if (Fee == null || Fee == "") {
		$("#howWwbi").val(0);
	} else {
		var reg = /^[1-9]\d*$/;
		if (reg.test(Fee)) {
			WWBiFee = parseInt(Fee);
			$("#howWwbi").val(WWBiFee / 10);
			flag = true;
		} else {
			$("#howWwbi").val(0.0);
			$("#smsNumId").val("");
			flag = false;
		}
	}
	if (flag) {
		$("#errorTip").html("");
		judgeAmount();
	} else {
		$("#errorTip").html("请输入正确购买短信数量");
	}
}


// 判断金额是否显示充值
function judgeAmount() {
	var WWBiFee = parseFloat($("#howWwbi").val());
	var samount = $("#amount").val();
	var amount;
	if (samount == "" || samount == null || samount == undefined) {
		amount = 0;
	} else {
		amount = parseFloat(samount);
	}

	if (WWBiFee > amount) {
		// 显示充值按钮
		$("#buyWWBi").html("账户余额不足，请点先充值");
		$("#payWWBi").addClass("wwbi-btn-disable").removeClass(
				"wwbi-btn-enable").attr("disabled", "disabled");
	} else {// hide pay button
		$("#buyWWBi").html("");
		$("#payWWBi").addClass("wwbi-btn-enable").removeClass(
				"wwbi-btn-disable").removeClass("disabled");
	}
}

/*查询短信发送明细*/	
function smsDetail(ctx, placeId, sendDate){
	$.ajax({
		url : ctx +'/smsManage/sms/getSmsUseDetail.do',
		type : 'post',
		dataType : 'json',
		data : {"placeId" :placeId,"sendDate" : sendDate},
		async : false,
		success : function(data) {
			var datas = data.data;
			$("#smsUseRecords").dialog("open");
			$("#data tr").each(function(j,va){
				 if(j > 0 ){
					 $(this).empty();
				 }
	        })
	        var entity;
			$.each(datas, function(i, value) {
				if(this.smsType == '01'){
					entity += "<tr><td>"+this.mobile+"</td><td>"+this.sendDate+"</td><td>认证</td></tr>"
				} else {
					entity += "<tr><td>"+this.mobile+"</td><td>"+this.sendDate+"</td><td>通知宣传</td></tr>"
				}
			});
			$("#data").append(entity);
		}
	});
}

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