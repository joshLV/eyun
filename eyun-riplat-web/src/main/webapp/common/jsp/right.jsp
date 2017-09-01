<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" href="${ctx}/css/style.css" />
<script type="text/javascript" src="${ctx}/common/scripts/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/common.js"></script>
</head>
<!-- <SCRIPT type="text/javascript">

	var lastClickItem = null;
	var isSelect = false;
	function toJson(data) {
		return eval("(" + data + ")");
	}
			
	var menulist = toJson("[" + "${leftTrees}" + "]");
	var firstLevelMenu = new Array();

	function expandOrClose(id) {
		for (var i = 0; i < firstLevelMenu.length; i++) {
			if (firstLevelMenu[i].id == id) {
				/**
				 * 如果没有三级菜单时，点击二级菜单时调用gotoURL方法跳转至对应页面
				 *
				 * update by baox 2014/12/13
				 */
	            if(firstLevelMenu[i].link != null && firstLevelMenu[i].link != "" && firstLevelMenu[i].link.length > 0) {
	                gotoURL(firstLevelMenu[i].link, firstLevelMenu[i].id);
	            } else {
	                $("#" + id).show();
	            }
			} else {
				$("#" + firstLevelMenu[i].id).hide();
			}
		}
	}

	//根据json提供的数据组织菜单结构
	function buildMenuList() {
		$("#menu").html("");
		for (var i = 0; i < menulist.length; i++) {
			if (menulist[i].pId < 100) {
				firstLevelMenu.push(menulist[i]);
			}
		}

		var menuStr = "";
		for (var i = 0; i < firstLevelMenu.length; i++) {
			menuStr = menuStr
					+ '<li id="menu_' + firstLevelMenu[i].id + '" onclick="expandOrClose('
					+ firstLevelMenu[i].id
					+ ')"><div class="nc">'
					+ firstLevelMenu[i].name + '</div></li>';
			var subMenu = "";
			if (i == 0) {
				subMenu = "<div id='"+firstLevelMenu[i].id+"' class='list'><ul> ";
			} else {
				subMenu = "<div id='"+firstLevelMenu[i].id+"' class='list' style='display:none;'><ul> ";
			}
			var bFlag = false;
			//获取菜单
			for (var j = 0; j < menulist.length; j++) {
				//获取子菜单
				if (menulist[j].pId == firstLevelMenu[i].id) {
					bFlag = true;
					subMenu = subMenu
						+ '<li id="'+menulist[j].id+'"  style="margin-top:-3px;" ><div class="n2" style="line-height:34px;" ><img src="${ctx }/images/menu/x.png" /></div> <div class="nc" style="width:80%;line-height:34px;"><a href="javascript:gotoURL(\''
						+ menulist[j].link + '\',' + menulist[j].id
						+ ')">' + menulist[j].name
						+ '</a></div></li>';
				}
			}
			subMenu = subMenu + ' </ul></div>';
			if(bFlag) menuStr = menuStr + subMenu;
		}

		$("#menu").html(menuStr);
	}

	function gotoURL(link, id) {
		lastClickItem = $("#" + id);
        $("*").removeClass("curr");
		if (lastClickItem.children("div").eq(0).hasClass("n2")) {
	        lastClickItem.addClass("curr");
		} else {
			$("#menu_" + id).addClass("curr");
		}

		var url = link;
		if (url != null && url != "" && url != "undefined") {
			// 操作日志、场所列表 、操作用户、权限管理4个以及框架自带的系统管理设置，不用弹出场所选择
			// /users/users!userList.do
			// /roles/roles!roleList.do
			// /system/system!resList.do
			// /users/users!updateUserPassword.do
			// /systemLog/systemLog!systemLogList.do
			// /systemDict/systemDict!systemDictList.do

			// /internet/internet!list.do	场所列表
			// /log/log!logList.do			操作日志
			// /author/operateRole!list.do	权限管理
			// /operate/operateUser!list.do	操作用户

			if (url == "/users/users!userList.do"
					|| url == "/roles/roles!roleList.do"
					|| url == "/system/system!resList.do"
					|| url == "/users/users!updateUserPassword.do"
					|| url == "/systemLog/systemLog!systemLogList.do"
					|| url == "/systemDict/systemDict!systemDictList.do"
					|| url == "/internet/internet!list.do"
					|| url == "/log/log!logList.do"
					|| url == "/author/operateRole!list.do"
					// update by baox 用户没有场所时，操作用户页面不可使用
					// || url == "/operate/operateUser!list.do"  
					|| url == "/barService/noticeCTemplet!list.do"
					|| url == "/barService/noticeLTemplet!list.do"
					|| url == "/bardefserviceconf/bardefserviceconf!bardefserviceconf.do"
					|| url == "/barservicemanager/barservicemanager!barservicemanager.do"
					|| url == "/barversionconf/barversionconf!barversionconf.do"
					|| url == "/basicGoodsInfo/basicGoodsCategory!list.do"
		            || url == "/basicGoodsInfo/basicGood!list.do"
	                || url == "/basicGoodsInfo/basicUnit!list.do"
	                // add by baox 选中系统初始化时，无需弹出选择网吧
	                || url == "/sysinit/sysinit!list.do"
	                || url == "/users/users!toList.do") {
				window.parent.frames["mainFrame"].document.location.href = "${ctx }/" + url;
			} else {
				$.ajax({
					async : false,
					type : 'post',
					url : '${ctx }/system/system!judgeDefaultBar.do',
					data : {},
					dataType : 'json',
					success : function(data) {
						if (data.defaultBarId == null) {
							// 清空mainFrame
							var mainFrameObj = window.parent.frames["mainFrame"].document;
							$(mainFrameObj).empty();
							// 弹出场所选择窗口
							parent.$.fancybox.open({
								href : "${ctx}/internet/internet!getAllInternetBar.do",
								type : 'iframe',
								padding : 5,
								maxWidth : 550,
								minHeight : 175,
								closeBtn : false,
								'afterClose' : function() {
									if (isSelect) {
										window.parent.frames["mainFrame"].document.location.href = "${ctx }/" + url;
									}
								}
							});
						} else {
							window.parent.frames["mainFrame"].document.location.href = "${ctx }/" + url;
						}
					}
				});
			}
		}
	}

	$(document).ready(
		function() {
			//构建菜单 
			buildMenuList();

			var url = "";
			var fid = null;
			for (var i = 0; i < menulist.length; i++) {
				if (menulist[i].pId == firstLevelMenu[0].id) {
					lastClickItem = $("#" + menulist[i].id);
					fid = menulist[i].id;
					url = menulist[i].link;
					break;
				}
			}
			
			/**
			  * 如果没有三级菜单时，取得二级菜单中第一个菜单相应的值，然后调用gotoURL方法跳转至对应页面
			  *
			  * update by baox 2014/12/13
			  */
			if ((url == null || url == "" || url.length < 0) && firstLevelMenu.length > 0 && 
					(firstLevelMenu[0].link != null && firstLevelMenu[0].link != "" && firstLevelMenu[0].link.length > 0)) {
				fid = firstLevelMenu[0].id;
                url = firstLevelMenu[0].link;
			}
			gotoURL(url, fid);
			if (url.indexOf("index") != -1) {
				window.parent.document.getElementById("folderDiv").style.display = "none";
			} else {
				window.parent.document.getElementById("folderDiv").style.display = "";
			}
			//window.parent.frames["mainFrame"].document.location.href = "${ctx }/"+url;
	});
  </SCRIPT> -->
<body>
	<div class="PublicTab">
		<a href="" title="当前收益" class="on">当前收益</a><a href="" title="交接班记录">交接班记录</a><a href="" title="当前收银员">当前收银员</a><a href="" title="当前在线人数">当前在线人数</a><a href="" title="计费中心状态">计费中心状态</a>
	</div>
</body>
</html>
