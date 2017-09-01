/**
 * 
 * 方法功能说明：使用hover方法，当鼠标悬浮时，帮助信息显示，鼠标离开时，帮助信息隐藏。
 * 使用方法：在页面ready方法中调用mouseHover("id"),同时在对应标签设置content属性，值为帮助信息，如：content="这里是帮助信息。"
 * @param selectors 鼠标悬浮标签ID，如果为多个，用“,”分隔即可,如：mouseHover("id1,id2");
 * @author sh_j_baoxu
 * @data 2014年9月4日 下午15:06:41
 */
function mouseHover(selectors) {
	createDiv();
	var selectors = selectors.split(",");
	// 选择器判断
	for (var i = 0; i < selectors.length; i++) {
	    if (selectors[i]) {
		    if(typeof selectors[i] === "string") {
		    	if(selectors[i].substr(0,1) !== "#") {
		    		selectors[i] = "#"+selectors[i];
		    	} 
	    	} else {
	    		selectors[i] = "#"+ $(selectors[i]).attr("id");
	    	}
		}
		$(selectors[i]).hover(
		    function (e) {
		    	$(".middle_c").html($(this).attr('content'));
		    	offset = $(this).offset();
		        xx=offset.left - ($("#helpDiv").innerWidth() / 2) + ($(this).innerWidth() / 2);
		        yy=offset.top - $("#helpDiv").innerHeight();
		        $("#helpDiv").css({ top: yy, left: xx, position : 'absolute' }).show();
		    },
		    function () {
		        $("#helpDiv").hide();
		    }
		);
	}
}

/**
 * 方法功能说明：创建帮助信息所需要的HTML并插入到页面body中
 * @author sh_j_baoxu
 * @data 2014年9月4日 下午15:06:41
 */
function createDiv() {
	if ($("#helpDiv").hasClass('exist')) {$(".middle_c").html(content); return;}
    var html = "<div id=\"helpDiv\" class=\"exist\" style=\"display:none;\"><div class=\"top\"><img src=\"../images/top.png\" /></div><div class=\"middle\">";
    html += "<div class=\"middle_c\"></div></div><div id=\"bottom\"><img src=\"../images/bottom.png\" /></div></div>"
	$("body").append(html);
}