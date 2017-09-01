<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<html>
<head>
<title>易盟</title>
<link rel="stylesheet" href="${ctx}/css/style.css" />
<%@ include file="/common/jsp/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
				<p>选择场所</p>
				<i></i>
				<ul>
					<li>选择场所</li>
					<li>选择场所</li>
					<li>选择场所</li>
					<li>选择场所</li>
					<li>选择场所</li>
					<li>选择场所</li>
					<li>选择场所</li>
					<li>选择场所</li>
				</ul>
			</div>
			<div class="topNav">
				<a href="" title="易韵网">易韵网</a><a href="" title="旺旺吧进销存">旺旺吧进销存</a><a href="" title="云备份/回复">云备份/回复</a><a href="" title="云分析">云分析</a><a href="" title="问题反馈">问题反馈</a>
			</div>
			<div class="UserName">
				欢迎您，json001<a href="">我的订单</a>
			</div>
			<div class="LoginS">
				<a href="" class="Letters"><i>10</i></a><a href="" class="Man"></a><a href="" class="Exit"></a>
			</div>
		</div>
		<div class="nav">
			<dl class="hack">
				<dd class="on">
					<a href="" title="首 页"> <span class="home_icon"></span>
						<p>首 页</p> <i class="home_i"></i>
					</a> <u></u>
				</dd>
				<dt></dt>
				<dd>
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
	<div class="main">
		<div class="indexT">
			<div class="fl">
				<img id="cheadImgURL" class="Photo fl" />
				<div class="Balance fl">
					<p>
						<i class="Balance_wwb"></i>旺旺币余额：<span id="cfee"></span>&nbsp旺旺币
					</p>
					<p>
						<i class="Balance_dx"></i>短信余额：<span id="csmsfee"></span>&nbsp条
					</p>
				</div>
			</div>
			<div class="news fr">
				<div class="news_t hack">
					<h2 class="on">最新公告</h2>
					<h2>最新咨询</h2>
					<a href="">更多...</a>
				</div>
				<div class="line01_c" id="newsList"></div>
				<!--  <ul>
        	<li><p><a href="" title="">会员消费、积分、储值、促销和优惠政策等信息管理会员消费、积分、储值、促销和优惠政策等信息管理</a></p><span>2015-12-03</span></li>
            <li><p><a href="" title="">会员消费、积分、储值、促销和优惠政策等信息管理会员消费、积分、储值、促销和优惠政策等信息管理</a></p><span>2015-12-03</span></li>
            <li><p><a href="" title="">会员消费、积分、储值、促销和优惠政策等信息管理会员消费、积分、储值、促销和优惠政策等信息管理</a></p><span>2015-12-03</span></li>
            <li><p><a href="" title="">会员消费、积分、储值、促销和优惠政策等信息管理会员消费、积分、储值、促销和优惠政策等信息管理</a></p><span>2015-12-03</span></li>
            <li><p><a href="" title="">会员消费、积分、储值、促销和优惠政策等信息管理会员消费、积分、储值、促销和优惠政策等信息管理</a></p><span>2015-12-03</span></li>
            <li><p><a href="" title="">会员消费、积分、储值、促销和优惠政策等信息管理会员消费、积分、储值、促销和优惠政策等信息管理</a></p><span>2015-12-03</span></li>
            <li><p><a href="" title="">会员消费、积分、储值、促销和优惠政策等信息管理会员消费、积分、储值、促销和优惠政策等信息管理</a></p><span>2015-12-03</span></li>
        </ul> -->
			</div>
		</div>
		<div class="indexB hack">
			<div class="indexBList fl">
				<div class="indexBList_t">
					<h2>访客动态</h2>
					<span>Visitor status</span>
				</div>
				<div>
					<!--图形插件-->
				</div>
			</div>
			<div class="indexBList fr">
				<div class="indexBList_t">
					<h2>费用状况</h2>
					<span>Cost status</span>
				</div>
				<div>
					<!--图形插件-->
				</div>
			</div>
		</div>
	</div>
	<div class="foot">上海云辰信息科技有限公司版权所有</div>
</body>
<script type="text/javascript">
	function loadMessage(){
		$.ajax({
			url: "${ctx}/index/index/findNewsBy.do",
			type: "POST",
			dataType: "json",
			async : false,
			cache : false,
			success: function(data){
				//$('#newsList').html(data.data.htmlStr);
			}
		});
		
		/* $.ajax({
			url: "${ctx}/index/index/findAccountInfo.do",
			type: "POST",
			dataType: "json",
			async : false,
			success: function(data){
				$('#cfee').html(eval("(" + data.data + ")").wwbBalance);
				$('#csmsfee').html(eval("(" + data.data + ")").noteBalance);
				$('#cheadImgURL').attr('src', eval("(" + data.data + ")").memberHP);
			}
		});  */
	}
	window.onload=loadMessage;
</script>
</html>
<!-- <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script> -->