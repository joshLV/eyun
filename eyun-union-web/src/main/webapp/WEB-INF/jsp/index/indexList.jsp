<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
<!DOCTYPE HTML>

<html>
<head>
<title>易盟</title>
<link rel="stylesheet" href="${ctx}/css/style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div class="main">
		<div class="indexT">
			<div class="fl">
				<img id="cheadImgURL" class="Photo fl" />
				<div class="Balance fl">
					<p>
						<i class="Balance_wwb"></i>账户余额：<span id="cfee">
									<c:choose>
										<c:when test="${empty accountBal}">0.00</c:when>
										<c:otherwise>${accountBal}</c:otherwise>
									</c:choose>
								</span>&nbsp元
					</p>
					<c:choose>
						<c:when test="${placeType eq '50'}">
							<p>
								<i class="Balance_wwb"></i>旺旺币余额：<span id="wwbBalance">
								<c:choose>
									<c:when test="${empty wwbBalance}">0.00</c:when>
									<c:otherwise>${wwbBalance}</c:otherwise>
								</c:choose></span>&nbsp个
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<i class="Balance_dx"></i>短信余额：<span id="csmsfee">
								<c:choose>
									<c:when test="${empty smsBlance}">0</c:when>
									<c:otherwise>${smsBlance}</c:otherwise>
								</c:choose></span>&nbsp条
							</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="news fr">
				<div class="news_t hack">
					<h2 class="on">最新公告</h2>
					<h2>最新咨询</h2>
<!-- 					<a href="">更多...</a> -->
				</div>
				<div class="line01_c" id="newsList"></div>
			</div>
		</div>
		<div class="indexB hack">
			<div class="indexBList fl">
				<div class="indexBList_t">
					<h2>    欢迎您！</h2> 
					<span>    Welcome</span>
					<!--  <h2>访客动态</h2> -->
 					<!--  <span>Visitor status</span> -->
				</div>
				<div id="containerVisitor" style="min-width:400px;height:200px"></div>
			</div>
			<div class="indexBList fr">
				<div class="indexBList_t">
					<h2>费用状况</h2>
					<span>Cost status</span>
				</div>
				<div id="containerFee" style="min-width:400px;height:200px"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">


	function loadMessage(){
		
		var wwbBalance = $('#cfee').html();
		var smsBalance = $('#csmsfee').html();
		var feeArr = [Number(wwbBalance), Number(smsBalance)];
		var xAxisCategories = ['账户余额', '短信余额'];
		setColumnHighCharts('#containerFee', '费用状况', xAxisCategories, '余额', feeArr);
		
		$.ajax({
			url: "${ctx}/index/index/findNewsBy.do",
			type: "POST",
			dataType: "json",
			async : false,
			cache : false,
			success: function(data){
				$('#newsList').html(data.data.htmlStr);
			}
		});
		
		$.ajax({
			url: "${ctx}/index/index/findAccountInfo.do",
			type: "POST",
			dataType: "json",
			async : false,
			cache : false,
			success: function(data){
				var datas = data.data;
				var memberHP = datas.memberHP;
				$('#cheadImgURL').attr('src', memberHP);
			}
		});
		
// 		$.ajax({
// 			url: "${ctx}/index/index/findVisitorNumByMemberid.do",
// 			type: "POST",
// 			dataType: "json",
// 			async : false,
			
// 			success: function(data){
// 				var datas = data.data;
// 				setPieHighCharts('#containerVisitor', '访客动态', '访客人数', datas);
// 			}
// 		});
	}
	
	
	$(document).ready(function() {

		loadMessage();
		}); 
	/*$(function() {
		if(typeof(Highcharts) == "object") { 
			delete Highcharts;
		}
		loadMessage();
	});
	*/
	function setColumnHighCharts(idName, title, xAxisCategories, seriesName, data) {
		$(idName).highcharts({
			chart: {
				type: 'column',
				margin: 75,
				options3d: {
					enabled: true,
					alpha: 10,
					beta: 25,
					depth: 70
				}
			},
			title: {
				text: title
			},
			plotOptions: {
				column: {
					depth: 25
				}
			},
			credits: { 
				enabled: false	//右下角不显示highcharts的LOGO
			},
			xAxis: {
				categories: xAxisCategories
			},
			yAxis: {
				opposite: true
			},
			series: [{
				name: seriesName,
				data: data
			}]
		});
	}
	
	function setPieHighCharts(idName, title, seriesName, data) {
		$(idName).highcharts({
			chart: {
				type: 'pie',
				options3d: {
					enabled: true,
					alpha: 45,
					beta: 0
				}
			},
			title: {
				text: title
			},
			credits: { 
				enabled: false	//右下角不显示highcharts的LOGO
			},
			tooltip: { pointFormat: '{series.name}: <b>{point.y}({point.percentage:.1f}%)</b>' },
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					depth: 35,
					dataLabels: {
						enabled: true,
						format: '{point.name}'
					}
				}
			},
			series: [{
			type: 'pie',
				name: seriesName,
				data: data
			}]
		});
	}
</script>
</html>
