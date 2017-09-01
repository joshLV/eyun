<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="email=no" name="format-detection" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<title>通知通告</title>
<c:if test="${userType == 'EDA_ACCOUNT'}">
	<script type="text/javascript" src="${ctx}/common/scripts/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${ctx}/common/scripts/custom_dialog.js"></script>
	<script type="text/javascript" src="${ctx}/common/scripts/jquery-ui.js"></script>
	<link rel="stylesheet" href="${ctx}/common/css/jquery-ui.css" />
</c:if>
<style>
/*公用样式*/
* {
	margin: 0;
	padding: 0;
}
img{max-width: 80%;}
	html {
	overflow: hidden;
}

.notice body {
	font-size: 16px;
	line-height: 1;
	color: #000;
}

.notice ol, ul {
	list-style: none;
}

.notice select, input, img, textarea {
	vertical-align: middle;
	outline: none;
}

.notice a {
	text-decoration: none;
	color: #000;
}

.notice a:hover {
	text-decoration: none;
}

.notice fieldset, img, input {
	border: 0;
}

.fl {
	float: left;
}

.fr {
	float: right;
}

.blank {
	clear: both;
	font-size: 0;
	height: 0;
	line-height: 0;
	overflow: hidden;
}

.hack {
	*zoom: 1;
}

.hack:after {
	clear: both;
	height: 0;
	overflow: hidden;
	display: block;
	visibility: hidden;
	content: ".";
}

.Article_c {
	padding: 1rem;
}

.Article_c_t {
	text-align: center;
	margin-bottom: 1rem;
}

.Article_c_t h1 {
	font-size: 1rem;
	line-height: 1.2rem;
	margin-bottom: 0.5rem
}

.Article_c_t p {
	font-size: 0.875rem;
	color: #737373
}

.Article_c_t p span {
	margin-left: 1rem
}

.articlecon {
	text-align: left;
	margin-bottom: 1rem;
}

.articlecon p {
	line-height: 1.5rem;
	font-size: 0.875rem;
	text-indent: 1rem;
	margin-bottom: 0.625rem;
	color: #666
}

.articleComment {
	background: #f1f1f1;
	padding-right: 4rem;
	position: relative;
}

.articleComment .articleComment_text {
	border-radius: 5px 0 0 5px;
	border: 1px solid #c7c7c7;
	border-right: 0;
	background: 0;
	height: 2.5rem;
	padding-left: 0.625rem;
	width: 100%;
	box-sizing: border-box;
	font-size: 0.875rem;
}

.articleComment .articleComment_button {
	background: #1e97d9;
	position: absolute;
	right: 0;
	top: 0;
	width: 4rem;
	height: 2.5rem;
	border-radius: 0 5px 5px 0;
	color: #fff;
	font-style: 0.875rem;
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: no;
}

.annex {
	padding: 0 1rem;
	font-size: 1rem;
	line-height: 1.6rem
}

.annex img {
	margin-right: 0.5rem
}

.annex a {
	color: #7a7a7a
}

.chat {
	padding: 1rem;
	overflow-y: auto;
	/*height: 500px;*/
}

.chat dl {
	line-height: 30px;
}

.chat dl dd span {
	color: red;
}

.chat dl dt {
	text-indent: 1rem;
	font-size: 1rem
}

.Article_b {
	padding: 1rem;
}

@media screen and (min-width:320px) and (max-width:360px) {
	html {
		font-size: 12px
	}
}

@media screen and (min-width:361px) {
	html {
		font-size: 14px
	}
}
</style>
</head>
<body class="notice">
	<%-- start of notice block --%>
	<div class="Article_c" id = "Article_c">
		<div class="Article_c_t">
			<h1>${notice.name }</h1>
			<p>${notice.sendTime }</p>
		</div>
		<div class="articlecon">${notice.content }</div>
		<c:if test="${notice.attachPath !=null and notice.attachName !=null}">
			<div class="annex">
				<img src="${ctx }/images/annex.png" />附件：<a href="${ctx}${notice.attachPath}" download="${notice.attachName }">${notice.attachName }</a>
			</div>
		</c:if>
	</div>
	<%-- send of notice block --%>
	<%-- start of replay list block --%>
	<div class="chat" id="replyList">
		<dl id = "replyDl"></dl>
	</div>
	<%-- end of replay list block --%>
	<form id="reply">
		<input type="hidden" name="userId" value="${userId }" />
		<input type="hidden" name="userType" value="${userType }" />
		<input type="hidden" id="recordId" name="recordId" value="${notice.id }" />
		<c:if test="${isApp == 'N'}"><!-- isApp=N则为pc端用户测试 -->	
			<div class="Article_b" id = "Article_b">
				<div class="articleComment">
					<input type="text" id="replyContent" name="replyContent" class="articleComment_text" maxLength="100" /> <input type="button" class="articleComment_button" id="submit" value="回 复" onclick="replyNotice();" />
				</div>
			</div>
		</c:if>
	</form>
</body>
<script type="text/javascript">
	/**
	 *  add by qianna
	 * @type {{resizeContentImg: Function}}
	 */
	var pageFunc = {
		resizeContentImg: function () {
			$('#Article_c .articlecon').find('img').attr({'width': '300px', 'height': '300px'});
		},
		adjustWidthHeight : function () {
			//var height = $(this).height();
			var Article_C_height = $("#Article_c").height(); //notice block height
			var Article_b_height = $('#Article_b').height(); //local test replay box height
			var Article_r_height = $('#replyList').height(); //replayList block height
//			console.log( 'Article C:' + Article_C_height + 'Article B:' + Article_b_height);
			var totalH = Article_b_height + Article_C_height + Article_r_height;

			if ((totalH + 100) < 750) {
				$("#Article_c").height(Article_C_height + 650 - totalH);
			}
		},
		resultHandle : function (data) { //返回结果处理
			if(data.status == "200") {
				//showWaittingMsg(data.msg, 4000);
				pageFunc.refresh();
				$("#replyContent").val("");
			} else {
						showWaittingMsg(data.msg, 4000);
			}
		},
		/* 刷新最新评论 */
		refresh : function (){
			var recordId = $("#recordId").val();
			$.ajax({
				url : '${ctx}/NoticeService/replyHistory.do',
				type : 'post',
				dataType : 'json',
				data : {"recordId" : recordId},
				success : function(data) {
					var dataList = data.data;
					var dl = $("#replyDl");
					dl.empty();
					for(var i=0;i<dataList.length;i++){
						dl.append("<dd><span>"+dataList[i].userName+" ：</span>"+dataList[i].replyTimeStr+"</dd><dt>"+dataList[i].replyContent+"</dt>");
					}
					pageFunc.adjustWidthHeight();
				}
			});
		}
	};
$(function () {
	pageFunc.refresh();
	pageFunc.resizeContentImg();
});

//保存或修改资源
function replyNotice() {
	var replyContent = $("#replyContent").val();
	if (replyContent == '') {
		msg = "回复的内容不能为空";
		showMsg(msg);
		return;
	}
	if(replyContent!=''){
		msg = "确定回复？"
	}
	showMsg(msg, 2, function() {
		$(this).dialog('close');
		$.ajax({
			url : '${ctx}/NoticeService/replayNotice.do',
			type : 'post',
			dataType : 'json',
			data : $('#reply').serialize(),
			success : function(data) {
				pageFunc.resultHandle(data);//刷新列表
			}
		});
	});
}





/*function adjustWidthHeight() {
    var height = $(this).height();
    var Article_C_height = $("#Article_c").height()
    var Article_b_height = $('#Article_b').height();
    $("#replyList").height(height-Article_C_height-Article_b_height-100);
}*/



</script>
</html>