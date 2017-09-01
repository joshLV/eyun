<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<html>
<head>
<title>易韵联盟-最新公告</title>
<link rel="stylesheet" href="${ctx}/css/style.css" />
<%@ include file="/common/jsp/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
body {
	text-align: center;
	font: 14px/1.25 Microsoft Yahei,Helvetica,sans-serif;
}

#center {
	margin-right: auto;
	margin-left: auto;
	height: 700px;
	/*background:#F00;*/
	width: 800px;
	vertical-align: middle;
	line-height: 30px;
}
.news_c{
	word-spacing: 2em;
	letter-spacing: .1em;
}
</style>
</head>
<body>
	<!-- news content part -->
	<div class="news_bor txt-center ft-18" id="center">
		<p class="news_title">
		<h1 class="ft-25">${news.title}</h1>
		</p>
		<p class="news_date clr-grey ft-14">发布时间：${news.createDate}</p>
		<div class="news_c">${news.content}</div>
	</div>
	<div class="txt-center">
		<span class="eyun_footer3 clr-grey ft-14">Copyright © 上海钱易文化传播有限公司 沪ICP备07031377号-2</span>
	</div>
</body>
</html>
