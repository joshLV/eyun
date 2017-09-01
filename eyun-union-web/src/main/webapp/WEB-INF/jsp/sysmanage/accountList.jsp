<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<script type="text/javascript" src="${ctx}/scripts/account/Base64.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<div>
		
		<div class="right_c_tab">
			<div class="PublicC_t">
				<ul class="operation fr" id="functionDiv"></ul>
			</div>
			<!-- grid对应table设置 -->
			<div>
			<table id="gridTable"></table>
			<div id="gridPager"></div>
			</div>
			<!-- end -->
		</div>
		<div id="dialogAccount" title="绑定账号" style="display: none;width:601px">
			<form>
				<!-- hidden="hidden" -->
				<table>
					<tr>
						<td>账号名</td>
						<td><input type="text" id="currentMemberId" class="PublicText"/></td>
					</tr>
					<tr>
						<td>登录密码</td>
						<td><input type="password" id="currentPassword" class="PublicText"/></td>
					</tr>
				</table>
				<div style="display : none;"><input type="reset" id="resetBtn"></div>
			</form>
		</div>
	</div>
<script type="text/javascript">
        
	$(function() {
		 //添加绑定按钮
		$("#functionDiv").append(bindAccountHtm);
		$("#bindAccountFunc").click(function() {
			openDialog();
		});
		
		// grid加载
		$("#gridTable").jqGrid({
			url : '${ctx}/systemManage/accountManage/getGridList.do',
			mtype : 'post',
			datatype : "json",
			height : 'auto',
			forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
			altRows : true,
			colNames : ['id','账户号', '场所数量(个)', '设备数量(个)', '注册时间', '录入者', '更新人', '备注', 'userId' ],
			colModel : [
						{name:'id',index:'id', align: 'center', sorttype:"Integer", width : '5',hidden:true},
						{name : 'loginName',index : 'loginName',align : 'center',height:20,sorttype : "string"}, 
						{name : 'placeid',index : 'placeid',align : 'center',height:40,sorttype : "string"}, 
						{name : 'deviceid',index : 'deviceid',sorttype : "string",align : 'center'}, 
						{name : 'createTimeStr',index : 'createTimeStr',align : 'center',sorttype : "string"}, 
						{name : 'creator',index : 'creator',align : 'center',sorttype : "string"}, 
						{name : 'updator',index : 'updator',align : 'center',sorttype : "string"}, 
						{name : 'remark',index : 'remark',align : 'center',sorttype : "string"}, 
						{name : 'userId',index : 'userId',align : 'center',sorttype : "Integer",hidden :true}],
			rowList : ${rowList},
			jsonReader : {
				rowNum:"rowNum",
				root:"dataRows",			// 数据行（默认为：rows）
				page: "curPage",			// 当前页
				total: "totalPages",		// 总页数
				records: "totalRecords",	// 总记录数
				repeatitems : false			// 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
			},
// 			beforeProcessing : function(data, status, xhr) {
// 				var datas = data.dataRows;
// 				for (var i = 0; i < datas.length; i++) {
// 					datas[i].operate = "<input type='button' onclick='deleteAccount(" + datas[i].userId + ")' value='解绑' class='btn_img_update'/><span style='margin-right: 8px;'></span>";
// 				}
// 				xhr.dataRows = datas;
// 			},
// 			gridComplete:function(rowid, rowdata){
// 				var ids = $("#gridTable").getDataIDs();//jqGrid('getDataIDs');
// 				for(var i=0;i<ids.length;i++){
// 					unbindHtml = preUnbindHtm + " onclick='deleteAccount(" + ids[i] + ")' " + sufUnbindHtm;
// 					jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:unbindHtml});
// 				}
// 				异步加载js，确定拥有的权限
// 				$("body").append("<script type='text/javascript' src='${ctx}/scripts/authority.js'><\/script>");
// 			},
			pager : "#gridPager",
			multiselect: false,   // 复选框设置
		}).navGrid('#gridPager', {edit : false,add : false,del : false,search : false,refresh : false});
		// 场所列表Change事件
		$("#barid").change(function() {
			// 选中场所ID取得
			var barid = $('#barid').val();
			// grid重新加载
			$("#gridTable").jqGrid('setGridParam', {datatype : 'json',page : 1,postData : {'goodsCategory.barid' : barid}}).trigger('reloadGrid');
		});
	});

	// grid自适应
	$(window).resize(function() {
		$("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
	});

	/** 
	* 解除账号绑定 dialogjb
	*@param 参数 会员id
	*/
// 	function deleteAccount(id){
// 		showMsg("确定解除账号绑定吗?", 2, function() {
// 			$(this).dialog('close');
// 			$.ajax({
// 				url : "${ctx}/systemManage/accountManage/updRelieveById.do",  
// 				dataType : 'json',
// 				data : {"userId" : id},  
// 				type : 'POST',  
// 				success : function(data) {
// 					showWaittingMsg(data.msg, 1000);
// 					if (data.status == "SUCC") {
// 						jQuery("#gridTable").trigger("reloadGrid");
// 						$("#dialogAccount").dialog('close');
// 					}
// 				}
// 			});
// 		});
// 	}
		
		
	/** 
	* 打开 账号绑定界面dialog
	*/
	function openDialog() {
		$("#dialogAccount").dialog({
			resizable: false,
			width : 'auto',
			height : 'auto',
			modal : true,
			buttons : {
				"绑定" : function() {
						bindingAccount();
				},
				"取消" : function() {
					clearData();
				}
			},
			close : function() {
				clearData();
			}
		});
	}
	
	/** 
	* 账号绑定
	* @param currentMemberId 绑定账号id 
	* @param currentPassword 绑定账号密码 （MD5加密32位字符）
	*/
	function bindingAccount(){
		//Base64加密
		var base = new Base64();
		var currentMemberId = $("#currentMemberId").val();
		var currentPassword = base.encode($("#currentPassword").val());
		if((currentMemberId==null || currentMemberId=="")){
			showWaittingMsg("绑定账号不能为空!");
			return ;
		}
		if((currentPassword==null || currentPassword =="")){
			showWaittingMsg("绑定密码不能为空!");
			return ;
		}
		showMsg("确定绑定该账号吗？", 2, function() {
			$(this).dialog('close');
			$.ajax({  
				url : "${ctx}/systemManage/accountManage/bindingAccount.do",  
				data : {  
					"currentMemberId" : currentMemberId,
					"currentPassword" : currentPassword
				},  
				type : 'POST',  
				dataType : 'json',
				success : function(data) {  
					//showWaittingMsg(data.msg, 1000);
					if (data.status == "SUCC") {
						showWaittingMsg(data.msg, 4000);
						refresh();
						$("#dialogAccount").dialog('close');
					} else {
						showWaittingMsg(data.msg, 4000);
					}
				},
			});  
		});
	}
	
	//jqgrid刷新
	function refresh() {
		jQuery("#gridTable").trigger("reloadGrid");
	}
	
	//清除账号信息
	function clearData() {
		$("#resetBtn").click();
		$("#dialogAccount").dialog('close');
	}
	
	</script>