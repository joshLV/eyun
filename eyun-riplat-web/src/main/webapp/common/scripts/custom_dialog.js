/**
 * 自定义Massage Dialog
 * @param msg 消息内容
 * @param btnflg 1：message, 2:confirm
 * @param fn 确定按钮方法体
 * @param closefn 关闭按钮回调函数
 */
function showMsg(msg, btnflg, func, closefn) {
	createMsgDiv();
	// 消息设置
    $("#msg").html(msg);
    
    $("#dialog").dialog({
        bgiframe: true,
        minHeight : 50,
        title : "通知",
        height:'auto',
        dataheight: 'auto',
        scroll : false,
        position : { my: "center", at: "center", of: window },
        drag: true,
        resize: false,
        zIndex : 9999,
        modal : true,
        closeOnEscape : true,
        buttons: {
        	"关闭": function() {
                $(this).dialog('close');
            }
        }
    });
    if (btnflg) {
    	$("#dialog").dialog('option', 'buttons', { "确定": func, "取消": function() { $(this).dialog('close'); } });
    	$("#icon").removeClass("ui-icon-circle-check").addClass("ui-icon-alert");
    }
    if (closefn) {
        $("#dialog").bind('dialogclose', closefn);
    }
    $(".ui-dialog-buttonset button").eq(0).focus();
}

/**
 * 自定义Grid Massage Dialog
 * @param msg 消息内容
 * @param left dialog距左位置
 * @param top dialog距上位置
 * @param rowid 列ID
 * @param nm 列名
 */
function showGridMsg(msg, left, top, rowid, nm) {
	createMsgDiv();
	// 消息设置
    $("#msg").html(msg);
    // dialog位置设置
    var position;
    if (left) {
        position = { my: left, at: top, of: window };
    } else {
        position = { my: "center", at: "center", of: window };
    }
    $("#dialog").dialog({
        bgiframe: true,
        minHeight : 50,
        title : "错误",
        height:'auto',
        dataheight: 'auto',
        scroll : false,
        drag: true,
        resize: false,
        zIndex : 1000,
        modal : true,
        position : position,
        closeOnEscape : true,
        buttons: {
        	"关闭": function() {
                $(this).dialog('close');
            }
        },
        close : function() {
            if (rowid) {
                dialogIsOpen = false;
                checkflg = true;
                $("#" + rowid + "_" + nm).focus();
            }
        }
    });
    $(".ui-dialog-buttonset button").eq(0).focus();
}

/**
 * 自定义Grid Massage Dialog
 * @param msg 消息内容
 * @param timeOut dialog自动关闭时间
 */
function showWaittingMsg(msg, timeOut) {
	createMsgDiv();
	// 消息设置
    $("#msg").html(msg);
    $("#dialog").dialog({
        bgiframe: true,
        minHeight : 50,
        title : "提示",
        height:'auto',
        dataheight: 'auto',
        scroll : false,
        drag: true,
        resize: false,
        zIndex : 1000,
        modal : true,
        position : { my: "center", at: "center", of: window },
        closeOnEscape : true,
        buttons: {
        	"关闭": function() {
                $(this).dialog('close');
            }
        }
    });
    $(".ui-dialog-buttonset button").eq(0).focus();
    setTimeout(function() {$("#dialog").dialog('close');}, timeOut);
}

/**
 * 创建弹出框所需要的HTML并插入到页面body中
 */
function createMsgDiv() {
	if ($("#dialog").hasClass('exist')) {return;}
    var html = "<div id=\"dialog\" class=\"exist\" title=\"\" style=\"display: none;text-align:center;\"><span class=\"ui-icon ui-icon-circle-check\"";
    html += "style=\"float:left; margin:0 7px 0px 0;\" id=\"icon\"></span><p id=\"msg\"></p></div>";
	$("body").append(html);
}