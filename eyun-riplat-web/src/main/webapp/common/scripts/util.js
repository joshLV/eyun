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
