/************************** 以下是提示信息 ********************************/

/**
 * 显示错误提示信息
 * @param domId 信息填写错误的DOM对象的ID 没有的话 传如空字符串 ""
 * @param message 提示给用户的信息
 */
function showErrMsg(domId, message,divId) {
    createErrorInfoDiv();
    $("#err_content").empty().append(message);
    $(".err_div").fadeIn("slow");
    $("#" + domId).focus().css("background-color", "#FFFFD0");
}

/**
 * 显示普通提示信息
 * @param domId 要增加焦点的DOM对象的ID
 * @param message 提示信息
 */
function showInfoMsg(domId, message) {
    createNormalInfoDiv();
    //$("#tip_content").empty().append(message);
    $(".tip_div").fadeIn("slow");
    $("#" + domId).focus().css("background-color", "#FFFFD0");
}

/**
 * 延时关闭错误信息提示
 * @param message 提示信息
 * @param timeOut 等待时间 毫秒
 */
function showWaitErrMsg(message,timeOut) {
    createErrorInfoDiv();
    console.log(message);
    $("#err_content").empty().append(message);
    $(".err_div").fadeIn("slow");
    setTimeout(function() {$(".err_div").fadeOut("slow");}, timeOut);
}
/**
 * 延时关闭普通信息提示
 * @param message 提示信息
 * @param timeOut 等待时间 毫秒
 */
function showWaitInfoMsg(message,timeOut) {
    createNormalInfoDiv();
    console.log(message);
    $("#tip_content").empty().append(message);
    $(".err_div").fadeIn("slow");
    setTimeout(function() {$(".tip_div").fadeOut("slow");}, timeOut);
}


/**
 * 普通信息提示
 * @param id 可不填 备用
 */
function createNormalInfoDiv(id) {
    if (!$("#tipErrorDiv").hasClass('exist')) {
        var html = '<div id="tipInfoDiv" class="ui-state-highlight ui-corner-all tip_div exist" style="padding: 0 .7em;"><p class="tip_p"><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="tip_content"></span> </p> </div>';
        if (id) $("#" + id).prepend(html);
        else $("#main").prepend(html);
    }
}

/**
 * 错误信息提示
 * @param id 可不填 备用
 */
function createErrorInfoDiv(id) {
        if (!$("#tipErrorDiv").hasClass('exist')) {
            var html = '<div id="tipErrorDiv" class="ui-state-error ui-corner-all err_div exist"> <p class="err_p"><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span> <span id="err_content"> </span> </p> </div>';
            if (id) $("#" + id).prepend(html);
            else $("#main").prepend(html);
        }
}

//以下 是dialog
/**
 * 普通信息dialog提示
 * @param id 弹窗div的ID
 */
function createNormalDialogInfoDiv(id) {
    if (!$("#tipDialogErrorDiv").hasClass('exist')) {
        var html = '<div id="tipDialogInfoDiv" class="ui-state-highlight ui-corner-all tip_div exist" style="padding: 0 .7em;"><p class="tip_p"><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="tip_content"></span> </p> </div>';
        if (id) $("#" + id).prepend(html);
        else $("#main").prepend(html);
    }
}

/**
 * 错误信息dialog提示
 * @param id 弹窗div的ID
 */
function createErrorDialogInfoDiv(id) {
    if (!$("#tipDialogErrorDiv").hasClass('exist')) {
        var html = '<div id="tipDialogErrorDiv" class="ui-state-error ui-corner-all err_div exist"> <p class="err_p"><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span> <span id="err_content"> </span> </p> </div>';
        if (id) $("#" + id).prepend(html);
        else $("#main").prepend(html);
    }
}


/**
 * 显示弹窗错误提示信息
 * @param domId 信息填写错误的DOM对象的ID
 * @param message 提示信息
 * @param divId dialog的Div的ID
 */
function showDialogErrMsg(domId, message,divId) {
    createErrorDialogInfoDiv(divId);
    console.log(message);
    $("#"+divId).find('span').eq(1).html(message);
    $("#tipDialogErrorDiv").fadeIn("slow");
    $("#" + domId).focus().css("background-color", "#FFFFD0");
}
/**
 * 显示弹窗普通提示信息
 * @param domId 信息填写错误的DOM对象的ID
 * @param message 提示信息
 * @param divId dialog的Div的ID
 */
function showDialogInfoMsg(domId, message,divId) {
    createNormalDialogInfoDiv(divId);
    console.log(message);
    $("#"+divId).find('span').eq(1).html(message);
    $("#tipDialogInfoDiv").fadeIn("slow");
    $("#" + domId).focus().css("background-color", "#FFFFD0");
}

/**
 * 延时关闭错误信息提示
 * @param message 提示信息
 * @param timeOut 等待时间 毫秒
 */
function showDialogWaitErrMsg(message,timeOut,divId) {
    createErrorDialogInfoDiv(divId);
    console.log(message);
    //$("#err_content").empty().append(message);
    $("#"+divId).find('span').eq(1).html(message);
    $("#tipDialogErrorDiv").fadeIn("slow");
    setTimeout(function() {$("#tipDialogErrorDiv").fadeOut("slow");}, timeOut);
}
/**
 * 延时关闭普通信息提示
 * @param message 提示信息
 * @param timeOut 等待时间 毫秒
 */
function showDialogWaitInfoMsg(message,timeOut,divId) {
    createNormalDialogInfoDiv(divId);
    //$("#tip_content").empty().append(message);
    $("#"+divId).find('span').eq(1).html(message);
    $("#tipDialogInfoDiv").fadeIn("slow");
    setTimeout(function() {$("#tipDialogInfoDiv").fadeOut("slow");}, timeOut);
}


/**
 * 隐藏提示信息
 * @param domId 恢复正常显示的DOM对象的ID(可不传)
 */
function hideErrMsg(domId) {
    if(domId){
        $("#" + domId).css("background-color", "#f0f8ff");
    }
    $(".err_div").hide();
    $(".tip_div").hide();
}





