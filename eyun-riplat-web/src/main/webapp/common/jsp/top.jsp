<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>易盟服务中心</title>
<link rel="stylesheet" href="${ctx}/css/style.css" />
<script type="text/javascript" src="${ctx}/common/scripts/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/common.js"></script>
<script type="text/javascript">
	/* function logout() {
		window.location.href = "${ctx }/login/logout.do";
	}
	
	function selectedLvlLink(name, id) {
	    // 删除top链接的选中样式
	    $(".Purchase_nav_line_m_hover").removeClass("Purchase_nav_line_m_hover");
	    // 设置选中样式
	    $("#toplink_" + name).addClass("Purchase_nav_line_m_hover");
	
	    if (name != "index") {
	        var url = "${ctx }/system/system!getLeftTree.do?pid=" + id;
	        window.parent.frames["leftFrame"].document.location.href = url;
	        window.parent.onLeftHideOrBlock("Block");
	        window.parent.document.getElementById("folderDiv").style.display="";
	    } else {
	        var url = "${ctx }/index/index!index.do"
	        window.parent.frames["mainFrame"].document.location.href = url;
	        window.parent.onLeftHideOrBlock("Hide");
	        window.parent.document.getElementById("folderDiv").style.display="none";
	    }
	}
	function updateUser() {
	    var username = "${user.name}";
	    parent.$.fancybox.open({href : '${ctx }/mylogin/register!toCommonTop.do?username='+username,type : 'iframe',padding : 5,maxWidth : 750,maxHeight : 280,closeBtn  : false});
	}
	
	function updateUserpass() {
	    var username = "${user.name}";
	    var userId = "${user.id}";
	    parent.$.fancybox.open({href : '${ctx }/mylogin/register!toCommonPass.do?username='+username+'&userId='+userId,type : 'iframe',padding : 5,maxWidth : 510,maxHeight : 240,closeBtn  : false});
	} */
	
	/* 解决头部默认选择及左侧树形菜单默认选择问题。
	特别是给某个角色配置权限后，非‘首页’或‘系统管理’的情况下，获取资源菜单的第一项为默认选择项。并设置左侧菜单树 */
	/* $(document).ready(
	    function() {
	        if($("li#topLink_index").length > 0) {
	            $("li#topLink_index").attr('class', 'Purchase_nav_line_m_hover');
	            selectedLvlLink('index', 7);// 调用selectedLvlLink函数，第二个参数随系统设置中的资源设置产生DB而修改。
	            return;
	        } else if($("li#toplink_systemManage").length > 0) {// 销售管理默认显示，如果要显示其它选项在此修改即可
	            $("li#toplink_systemManage").attr('class', 'Purchase_nav_line_m_hover');
	            selectedLvlLink('systemManage', 8);// 调用selectedLvlLink函数，第二个参数随系统设置中的资源设置产生DB而修改。
	            return;
	        } else {
	            $("li.topnull").each(
	                // 如果显示方式发生改变，可能需要修改此处的字符串截取规则
	                function(index, element) {
	                    //$(element).click();// 主动触发click无效，替代方案：获取字符串，截取其中的两个参数，调用selectedLvlLink函数
	                    var id = element.id;
	                    var index = id.indexOf("_");
	                    var name = id.substring(index + 1, id.length);
	                    if(name == "goodsManager") {
	                        selectedLvlLink(name, 1);
	                    } else if(name == "shelvesManager") {
	                        selectedLvlLink(name, 2);
	                    } else if(name == "salesManager") {
	                        selectedLvlLink(name, 3);
	                    } else if(name == "repertoryManager") {
	                        selectedLvlLink(name, 4);
	                    } else if(name == "queryStatistics") {
	                        selectedLvlLink(name, 5);
	                    } else if(name == "basicInfo") {
	                        selectedLvlLink(name, 6);
	                    } else if(name == "systemManage") {
	                        selectedLvlLink(name, 8);
	                    }
	                    return false;
	                }
	            );
	        }
	    }
	); */
	
	function logout() {
		parent.location.href = "${ctx}/logout.do";
    }
</script>

</head>
<body>
	<div class="top">
		<div class="main hack top_c">
			<h1>易盟服务中心</h1>
			<a href="" title="易盟服务中心" class="logo fl"><p class="logo1">
					<span></span><i></i>
				</p>
				<p class="logo2"></p></a>
			<div class="select top_select fl">
				<p>请选择场所</p>
				<i></i>
				<ul>
					<li>场所A</li>
					<li>场所B</li>
					<li>场所C</li>
					<li>场所D</li>
					<li>场所E</li>
					<li>场所F</li>
					<li>场所G</li>
					<li>场所H</li>
				</ul>
			</div>
			<div class="topNav">
				<a href="" title="易韵网">易韵网</a><a href="" title="旺旺吧进销存">旺旺吧进销存</a><a href="" title="云备份/回复">云备份/回复</a><a href="" title="云分析">云分析</a><a href="" title="问题反馈">问题反馈</a>
			</div>
			<div class="UserName">
				欢迎您，json001<a href="">我的订单</a>
			</div>
			<div class="LoginS">
				<a href="" class="Letters"><i>10</i></a><a href="" class="Man"></a><a href="" onclick="logout()" class="Exit"></a>
			</div>
		</div>
		<div class="nav">
			<dl class="hack">
				<dd>
					<a href="" title="首 页"> <span class="home_icon"></span>
						<p>首 页</p> <i class="home_i"></i>
					</a> <u></u>
				</dd>
				<dt></dt>
				<dd class="on">
					<a href="" title="经营管理"> <span class="jygl_icon"></span>
						<p>经营管理</p> <i class="jygl_i"></i>
					</a> <u></u>
				</dd>
				<dt></dt>
				<dd>
					<a href="" title="访客中心"> <span class="fkzx_icon"></span>
						<p>访客中心</p> <i class="fkzx_i"></i>
					</a> <u></u>
				</dd>
				<dt></dt>
				<dd>
					<a href="" title="短信管理"> <span class="dxgl_icon"></span>
						<p>短信管理</p> <i class="dxgl_i"></i>
					</a> <u></u>
				</dd>
				<dt></dt>
				<dd>
					<a href="" title="旺旺币"> <span class="wwb_icon"></span>
						<p>旺旺币</p> <i class="wwb_i"></i>
					</a> <u></u>
				</dd>
				<dt></dt>
				<dd>
					<a href="" title="系统管理"> <span class="xtgl_icon"></span>
						<p>系统管理</p> <i class="xtgl_i"></i>
					</a> <u></u>
				</dd>
			</dl>
		</div>
	</div>
</body>
</html>