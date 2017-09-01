<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>信息发布平台</title>
<%@ include file="/common/jsp/header.jsp"%>
    <script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
	<div class="top">
		<div class="main hack top_c">
			<h1>信息发布平台</h1>
			<a href="" title="易盟服务中心" class="logo fl"><p class="logo1">
					<span></span><i></i>
				</p>
				<p class="logo2"></p></a>
			<div class="UserName">
				欢迎您，${AuthenToken.loginName } !
			</div>
			<div class="LoginS">
				<a onclick="logout()" class="Exit"></a>
			</div>
		</div>
		<div class="nav">
			<dl class="hack">

			</dl>
		</div>
	</div>
	<div class="main hack" id="leftMenu">

		<div class="right fr" id="mainMenu">

			<!--编辑区开始 请勿动框架-->
			<div id="main">
				<!--table-->
			</div>
			<!--编辑区结束-->
		</div>
		<div class="blank Height10"></div>

	</div>
	<div class="foot">&copy;&nbsp;2015&nbsp;信息发布平台&nbsp;上海钱易文化传播有限公司&nbsp;沪ICP备07031377号-2&nbsp;版本号：1.0</div>
</body>
</html>
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
<script type="text/javascript">
window.history.forward(-1);
// 最后点击MenuUrl,场所切换时用
var lastClickMenu = null;
var lastResourceId = null;
$(function() {
	var topMenuArray = new Array();
	var leftMenuArray = new Array();
	var leftMap = new Array();
	var mainMenuArray = new Array();
	var mainMap = new Array();
	
    var topDefaultId = 0;
    var leftDefaultId = 0;
	
	/**
	 * 动态生成菜单
	 * @author sh_j_baoxu
     * @data 2016年01月20日 上午14:34:54
	 */
	$.each(${allResourceList}, function(i, baseResource){
		// 一级菜单处理
		if (baseResource.menuLevel == 1) {
            if (baseResource.sort == 1 || topMenuArray.length == 0) {
                topMenuArray.push("<dd id='" + baseResource.id + "' code='" + baseResource.code + "' firstLoad= 'first' class='on'>");
                topDefaultId = baseResource.id;
            } else {
                topMenuArray.push("<dd id='" + baseResource.id + "' code='" + baseResource.code + "'>");
            }
            topMenuArray.push("<a href='javascript:;' onclick=selectedLvlLink(this,'" + (baseResource.url == null ? "" : "${ctx}" + baseResource.url)  + "'," + baseResource.menuLevel + "," + baseResource.sort + "); title='" + baseResource.name+ "' id='" + baseResource.id + "'>");
            topMenuArray.push("<p>" + baseResource.name + "</p><i class='home_i'></i></a><u></u></dd><dt></dt>");
        // 二级菜单处理
        } else if (baseResource.menuLevel == 2) {
            if (baseResource.sort == 1) {
                leftMenuArray.push("<li class='on' id='" + baseResource.id + "' onclick=selectedLvlLink(this,'" + (baseResource.url == null ? "" : "${ctx}" + baseResource.url)  + "'," + baseResource.menuLevel + "," + baseResource.sort + "); id='" + baseResource.id + "'>");
                if (topDefaultId == baseResource.pid) {
                    leftDefaultId = baseResource.id;
                }
            } else {
                leftMenuArray.push("<li id='" + baseResource.id + "' onclick=selectedLvlLink(this,'" + (baseResource.url == null ? "" : "${ctx}" + baseResource.url)  + "'," + baseResource.menuLevel + "," + baseResource.sort + "); id='" + baseResource.id + "'>");
            }
            leftMenuArray.push("<a title='" + baseResource.name + "'>" + baseResource.name + "</a></li>");
            var leftFooter = "</ul></div>";
            var isShow = "display:none;";
            // 数组key存在判断
            if (!leftMap.hasOwnProperty(baseResource.pid)) {
                if (topDefaultId == baseResource.pid) {
                    isShow = "display:block;";
                }
                var leftHeader = "<div code='" + baseResource.code + "' class='left fl' style=" + isShow + " id='left_" + baseResource.pid + "'><img src='" + "${ctx}" + "/images/jygl.jpg' width='214' height='121' /><ul class='leftNav'>";
                leftMap[baseResource.pid] = leftHeader + leftMenuArray.join('') + leftFooter;
                leftMenuArray = new Array();
            } else {
                leftMap[baseResource.pid] = leftMap[baseResource.pid].replace(leftFooter, "") + leftMenuArray.join('') + leftFooter;
                leftMenuArray = new Array();
            }
        // 三级级菜单处理
        } else {
	        var mainFooter = "</div>";
	        var isShow = "display:none;";
	        // 数组key存在判断
	        if (!mainMap.hasOwnProperty(baseResource.pid)) {
	            if (leftDefaultId == baseResource.pid) {
	                isShow = "display:block;";
	            }
	            mainMenuArray.push("<a class='on' onclick=selectedLvlLink(this,'" + (baseResource.url == null ? "" : "${ctx}" + baseResource.url)  + "'," + baseResource.menuLevel + "," + baseResource.sort + "); title='" + baseResource.name + "' id='" + baseResource.id + "'>" + baseResource.name + "</a>");
	            var mainHeader = "<div class='PublicTab' style=" + isShow + " id='main_" + baseResource.pid + "' pid='" + baseResource.id + "'>";
	            mainMap[baseResource.pid] = mainHeader + mainMenuArray.join('') + mainFooter;
	            mainMenuArray = new Array();
	        } else {
	            mainMenuArray.push("<a onclick=selectedLvlLink(this,'" + (baseResource.url == null ? "" : "${ctx}" + baseResource.url)  + "'," + baseResource.menuLevel + "," + baseResource.sort + "); title='" + baseResource.name + "' id='" + baseResource.id + "'>" + baseResource.name + "</a>");
                mainMap[baseResource.pid] = mainMap[baseResource.pid].replace(mainFooter, "") + mainMenuArray.join('') + mainFooter;
	            mainMenuArray = new Array();
	        }
        }
	});
	
	leftMenuArray = new Array();
    for (var key in leftMap) {
        leftMenuArray.push(leftMap[key]);
    };
    
    mainMenuArray = new Array();
    for (var key in mainMap) {
        mainMenuArray.push(mainMap[key]);
    };
	
	// 菜单Html追加
    $(".nav").children().append(topMenuArray.join(''));
    $("#leftMenu").prepend(leftMenuArray.join(''));
    $("#mainMenu").prepend(mainMenuArray.join(''));
    
    // 一级菜单class添加，不同菜单图标不一样
    $(".hack").children("dd").each(function(i, n){
        if ($(this).attr("code") == "index") {
            $(this).children("a").prepend("<span class='home_icon'></span>");
        } else if ($(this).attr("code") == "systemManage") {
            $(this).children("a").prepend("<span class='xtgl_icon'></span>");
        }	else if ($(this).attr("code") == "noticeManage") {
            $(this).children("a").prepend("<span class='dxgl_icon'></span>");
        }	else if ($(this).attr("code") == "mgrparamManage") {
            $(this).children("a").prepend("<span class='xtgl_icon'></span>");
        }
        
    });
    
   // 场所下拉菜单点击事件绑定
    $(".select ul li").click(function() {
        $(this).parent().hide();
        $("#selPlaceId").attr("placeId", $(this).val()).val($(this).html());
        gotoPager(lastClickMenu, lastResourceId);
    });
    
    
    // 页面初期按钮点击
    $("#mainMenu").children(".PublicTab").each(function(i, n){
        if($(this).is(":visible")) {
        	$(this).children("a").each(function(i, n){
        		if ($(this).hasClass("on")) {
        			$(this).click();
        			return;
        		}
        	});
        }
    });
    $("dd[firstLoad='first']>a").click();//永远加载第一个
});

/**
 * 菜单点击处理
 * @author sh_j_baoxu
 * @data 2016年01月20日 上午14:34:54
 */
function selectedLvlLink(selector, url, level, sort) {
	lastClickMenu = url;
	// 一级菜单处理
    if(level == 1) {
        var parent = $(selector).parent();
        parent.siblings("dd").each(function(i, n){
        	 if ($(this).hasClass("on")) {
        		 $(this).removeClass("on");
             }
        });
       
        parent.addClass("on");
        $("#leftMenu").children(".left").hide();
        $("#mainMenu").children().hide();
        if (null != url && "" != url) {
        	gotoPager(url);
        } else {
            $("#left_" + parent.attr("id")).show().children("ul").children().first().addClass(".on").click();
        }
    // 二级菜单处理
    } else if(level == 2) {
    	$(selector).siblings("li").each(function(i, n){
             if ($(this).hasClass("on")) {
                 $(this).removeClass("on");
             }
        });
       
    	$(selector).addClass("on");
        $("#mainMenu").children(".PublicTab").hide();
        $("#main_" + $(selector).attr("id")).show().children("a:first").click();
    // 三级菜单处理
    } else {
    	$(selector).siblings("a").each(function(i, n){
            if ($(this).hasClass("on")) {
                $(this).removeClass("on");
            }
       });
      
       $(selector).addClass("on");
       $("#main").empty().show();
  	$("#mainMenu").width("1051");
    	gotoPager(url, $(selector).attr("id"));
    }
}

/**
 * 页面Ajax跳转
 * @author sh_j_baoxu
 * @data 2016年01月20日 上午14:34:54
 */
function gotoPager(url, resourceId) {
	if (null == url || "" == url) return;
    $(".ui-dialog").empty();
    $(".ui-datepicker").remove();
    try {
        $.ajax({
            url : url,
               type : "post",
               data : {"placeId" : $("#selPlaceId").attr("placeId"), "resourceId" : resourceId},
               dataType : "html",
               async : false,
               error : function() {
            	    showMsg("系统异常，请联系管理员！");
               },
               success : function(msg) {
                  $("#main").html(msg);
               }
           });
           
    } catch (err) {
        showMsg("系统异常，请联系管理员！");
    }
}

/**
 * 系统退出
 */
function logout() {
    window.location.href = "${ctx}/logout.do";
}
</script>
