<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>
	<table>
		<div class="right_c_tab">
			<!-- grid对应table设置 -->
			<table id="gridTable"></table>
			<div id="gridPager"></div>
			<!-- end -->
		</div>
	</table>
<%--设备详细信息弹出框--%>
	<div id="shiftInfo" title="交接班记录详情" style="display: none">
		<%--<table class="PublicTable">
			<tr><td colspan="2">交班记录</td></tr>
			<tr><td>上次交班人：</td><td>张三</td></tr>
			<tr><td>本次交班人：</td><td>李四</td></tr>
			<tr><td>上次交接班时间：</td><td>上次交班人</td></tr>
			<tr><td>交接班时间：</td><td>上次交班人</td></tr>
			<tr><td>班次名称：</td><td>上次交班人</td></tr>
			<tr><td>控制台：</td><td>上次交班人</td></tr>
			<tr><td>上下班金额：</td><td>上次交班人</td></tr>
			<tr><td>会员卡销售：</td><td>上次交班人</td></tr>
			<tr><td>会员卡（开通）：</td><td>上次交班人</td></tr>
			<tr><td>账户卡（开充值）：</td><td>上次交班人</td></tr>
			<tr><td>本班账户上机收入：</td><td>上次交班人</td></tr>
			<tr><td>账户押金：</td><td>上次交班人</td></tr>
			<tr><td>商品销售收入：</td><td>上次交班人</td></tr>
			<tr><td>商品进货收入：</td><td>上次交班人</td></tr>
			<tr><td>总现金收入：</td><td>上次交班人</td></tr>
			<tr><td>实际收入：</td><td>上次交班人</td></tr>
			<tr><td>上缴金额：</td><td>上次交班人</td></tr>
			<tr><td>留给下班：</td><td>上次交班人</td></tr>

		</table>--%>
	</div>

<script type="text/javascript">
	$(function(){
	    //grid加载
	    $("#gridTable").jqGrid({
		datatype : 'local',
		data: ${data},
	    height: 'auto',
	    forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
	    altRows : true,
	    colNames:['交班时间','控制台','总收入', '上缴','留下','id',' 场所编号','操作'],
	    colModel:[
			{name: 'endShiftTime', index: 'endShiftTime', align: 'center', sorttype: "string", width: '25'},
	      {name:'console',index:'console',align: 'center',sortable: false, width: '20'},
		  {name:'sumCashIncome',index:'sumCashIncome', sorttype:"string", align: 'center', width: '20'},
	      {name:'handOverMoney',index:'handOverMoney', align: 'center', width: '20'},
		  {name:'leaveNextMoney',index:'leaveNextMoney', align: 'center', width: '20'},
		  {name:'id',index:'id',hidden:true},
		  {name:'serviceCode',index:'serviceCode',hidden:true},
		  {name: 'operate', index: 'operate', sortable: false, align: 'center', width: '10'},
		],
	    rowList: [10, 20, 30,40],
		loadonce:true,
	    jsonReader: {
	      rowNum:10,
	      repeatitems : false       // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
	    },
	    gridComplete:function(rowid, rowdata){
	    	var ids = $("#gridTable").getDataIDs();
			for(var i=0;i<ids.length;i++){
				var viewHtml = prePreviewHtm+"onclick='view(\"" + ids[i] + "\")'"+sufPreviewHtm;
				//jQuery("#gridTable").jqGrid('setCell',ids[i],'operate',viewHtml);
				jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:viewHtml});
            }
        },
	    pager:"#gridPager",
	    multiselect: false   // 复选框设置
	    }).navGrid('#gridPager',{edit:false,add:false,del:false,search:false,refresh : false})
	});
	// grid自适应
	/*$(window).resize(function(){
	    $("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
	});*/

	/**通过场所id获取场所信息**/
	function view(id){
		//console.log(id);
		var rowData = $("#gridTable").jqGrid("getRowData",id);
		//alert(rowData.serviceCode);
		$.ajax({
			url:'${ctx}/management/operationState/toShiftDetail.do',
			type:'post',
			dataType:'html',
			data:{id:id,serviceCode:rowData.serviceCode},
			success:function(rs) {
				$("#shiftInfo").empty().append(rs);
			}
		});
		$( "#shiftInfo" ).dialog({
			height: 690,
			width:400,
			modal: true});
	}
</script>
