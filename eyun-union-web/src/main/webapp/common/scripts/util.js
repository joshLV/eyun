/**
 * 文本框输入"时，返回false,表示不可输入此符号
 * 如果页面只有一个文本框时，按下回车时触发form的submit,故在此拦截
 * 
 * add by baox 2014/10/17
 */
$(function(){
	$(":text").keydown(function(event) {
		var e = e||event;
		var currKey = e.keyCode||e.which||e.charCode;
		var iSize = $(":text").length;
		
		if(currKey == 222) {
			return false;
		}
		if(currKey == 13) {
			// 页面是否只有一个文本框，当只有一个文本框时，返回false
			if(iSize == 1) {
				return false;
			}
		}
	});
});

/**
 * 设置AJAX请求默认选项
 * 主要设置了AJAX请求遇到Session过期的情况
 */
jQuery(function($){ 	 
	$.ajaxSetup({   
	    contentType:"application/x-www-form-urlencoded;charset=utf-8",   
	    cache:false ,   
	    complete:function(xhr,TS){
	    	if(!xhr || typeof xhr.getResponseHeader !='function') return true;
	    	var exception = xhr.getResponseHeader('sys_exception');
	    	if(exception == 'exception'){
	    		showMsg("系统异常，请联系管理员");
	    	}
	    }
	});
});

/**
 * 判断字符串是否为空
 * @param strVal
 * @returns {Boolean}
 */
function isNotEmpty(strVal) {
	if (strVal != '' && strVal != null && strVal != undefined && strVal.length > 0) {
		return true;
	} else {
		return false;
	}
}


/**
 * 获取时间差（可获取时间差的 秒、分、小时、天）
 * @param startTime 开始日期
 * @param endTime 结束日期
 * @param diffType 获取时间差的类型 秒/分/小时/天 
 * @returns 秒 分 小时 天  
 */
function GetDateDiff(startTime,endTime,diffType){
	//将日期对象转为xxxx-xx-xx 转为xxxx/xx/xx
	startTime = startTime.replace(/-/g,"/");
	endTime = endTime.replace(/-/g,"/");
	//将计算字符转换为小写
	diffType = diffType.toLowerCase();
	var sTime = new Date(startTime);//开始时间
	var eTime = new Date(endTime);//结束时间
	var divNum = 1;
	switch(diffType){
		case "second" :
			divNum = 1000;
			break;
		case "minute":
			divNum = 1000*60;
			break;
		case "hour":
			divNum = 1000*3600;
			break;
		case "day":
			divNum = 1000*3600*24;
			break;
		default :
			break;
	}
	return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
}
String.prototype.trim = function(){
	return this.replace(/(^[\s]*)|([\s]*$)/g, "");
}

//验证格式是否正确
var custValid ={
	//验证是否是正确的日期格式
	isTimeFmt : function(str){
		var rule_time = /^\d{4}(-\d{1,2}){2} \d{2}(:\d{2}){2}$/;
		if(str && rule_time.test(str)){
			return true;
		}
		return false;
	},
	//验证是否是正确的身份证格式
	isIDCard : function(str){
		//身份证正则表达式(15位)
		var rule_iDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
		//身份证正则表达式(18位)
		var rule_iDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
		if(str && rule_iDCard1.test(str) || rule_iDCard2.test(str)){
			return true;
		}
		return false;
	},
	isPhoneNo : function(str){
		var rule_phoneNo =  /^1[3,4,5,6,7,8,9][0-9]{9}$/;
		if(str && rule_phoneNo.test(str)){
			return true;
		}
		return false;
	}

};