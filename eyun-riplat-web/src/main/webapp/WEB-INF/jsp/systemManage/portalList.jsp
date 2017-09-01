<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp"%>

<link rel="stylesheet" href="${ctx}/css/systemManage/portal.css" />

<style type="text/css">
    .input_wd{
        width: 200px;
    }
</style>
<div class="PublicC_t">
    <ul class="operation fr" id="functionDiv"></ul>
		<%--+&nbsp;<a href="javascript:void(0)" id="choose-img" onclick="dialogImg()">选择图片</a>--%>
</div>

<table>
	<div class="right_c_tab">
		<!-- grid对应table设置 -->
		<table id="gridTable"></table>
		<div id="gridPager"></div>
		<!-- end -->
	</div>
</table>

<%--新增编辑素材弹出层--%>
<div id="dialog-form" title="新增模版素材" style="display: none;">
	<form id="portalForm" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" class="PublicTable">
			<tr>
				<td width="25%">场所</td>
				<td width="25%">
                    <select name="placeCode" id="placeCode" onchange="getPlaceOrDevice(this.value)">
						<option value=''>--全部--</option>
				    </select>
                </td>
                <%--设备序列号--%>
				<td width="25%">设备</td>
				<td width="25%">
                    <select name="serialNum" id="serialNum">
						<option value=''>--全部--</option>
				    </select>
                </td>
			</tr>
            <%--场所编号和设备序列号可以确定唯一关联ID--%>
			<tr>
				<td>模板名称：</td>
				<td>
                    <input name="portalModelName" id="portalModelName" type="text" placeholder="请输入模版名称" maxlength="30" class="PublicText"/>
                </td>
                <td>公告区：</td>
                <td colspan="3">
                    <input name="messages" id="messages" maxlength="9" placeholder="限9个字符，不填不显示" class="PublicText">
                </td>
			</tr>
			<tr>
                <td>Logo区：</td>
                <td colspan="2">
                    <input type="text" id="logoPic"  readonly="readonly" class="PublicText" style="width: 200px">
                    <input type="button"  id="btnChoose2" value="选择Logo" onclick="dialogImg(2,this)" class="btn page_go"/>

                </td>
                <td >
                    <input type="text" name="title" id="title"  class="PublicText" placeholder="自定义标题，不填不显示" maxlength="9" />
                </td>
			</tr>
			<tr>
				<%--tab 开始--%>
				<td colspan="4">
					<div id="tabs" style="height: 370px">
						<ul>
							<li><a href="#tabs-1">PC模板</a></li>
							<li><a href="#tabs-2">手机模板</a></li>
						</ul>
						<%--------------------------------------PC模版  tab 开始-------------------------------------------%>
						<div id="tabs-1">
							<p>
							<table>
								<tr>
									<td>背景图片：</td>
									<td colspan="2">
										<p class="mg-b-10">
											<input type="text" id="pc_background" readonly="readonly" class="PublicText input_wd" />
                                            <input type="button" id="btnChoose1" value="选择" onclick="dialogImg(1,this)" class="btn page_go"/>
										</p>
									</td>
								</tr>
								<tr>
									<td>广告轮播区：</td>
									<td colspan="2">
										<p>
											<label> 轮播一：</label> <span>
                                            <input type="text" id="pclb1" readonly="readonly" class="PublicText input_wd" />
                                            <input type="button" id="btnChoose31" value="选择" onclick="dialogImg(3,this)" class="btn page_go"/>
											</span>
										</p>

										<p>
											<label> 轮播二：</label> <span> <input type="text" id="pclb2" readonly="readonly" class="PublicText input_wd" />
                                            <input type="button" id="btnChoose32" value="选择" onclick="dialogImg(3,this)" class="btn page_go"/>
											</span>
										</p>

										<p class="chooseArea">
											<label> 轮播三：</label>
                                            <span>
                                            <input type="text" id="pclb3" readonly="readonly" class="PublicText input_wd" />
                                            <input type="button" id="btnChoose33" value="选择" onclick="dialogImg(3,this)" class="btn page_go"/>
											</span>
										</p>
									</td>
								</tr>
								<tr>
									<td>底部广告区：</td>
									<td colspan="2">
										<p class="chooseArea">
											<label>广告一：</label>
                                            <span>
                                            <input type="text" id="pcdl1" readonly="readonly" class="PublicText input_wd" />
                                            <input type="button" id="btnChoose34" value="选择" onclick="dialogImg(4,this)" class="btn page_go"/>
											</span>
										</p>

										<p class="chooseArea">
											<label> 广告二：</label>
                                            <span>
                                            <input type="text" id="pcdl2" readonly="readonly" class="PublicText input_wd" />
                                            <input type="button" id="btnChoose35" value="选择" onclick="dialogImg(4,this)" class="btn page_go"/>
											</span>
										</p>

										<p class="chooseArea">
											<label> 广告三：</label>
                                            <span>
                                            <input type="text" id="pcdl3" readonly="readonly" class="PublicText input_wd" />
                                            <input type="button" id="btnChoose36" value="选择" onclick="dialogImg(4,this)" class="btn page_go"/>
											</span>
										</p>

										<p class="chooseArea">
											<label> 广告四：</label>
                                            <span>
                                                <input type="text" id="pcdl4" readonly="readonly" class="PublicText input_wd" >
                                                <input type="button" id="btnChoose37" value="选择" onclick="dialogImg(4,this)" class="btn page_go">
											</span>
										</p>
									</td>
								</tr>
								<tr>
									<td>认证成功地址：</td>
									<td>
										<p class="chooseArea">
											<span>
                                                <input type="text" id="pcToUrl" name="pcToUrl" placeholder="比如：http://www.baidu.com" class="PublicText input_wd" />
											</span>
										</p>
									</td>
								</tr>
							</table>
							</p>
						</div>
						<%--------------------------------------PC模版  tab 结束-------------------------------------------%>
						<%--------------------------------------手机模版  tab 开始-------------------------------------------%>
						<div id="tabs-2">
							<p>
							<table>
								<tr>
									<td>背景图片：</td>
									<td colspan="2">
										<p>
											<input type="text" name="m_background" id="m_background" readonly="readonly" class="PublicText input_wd" />
                                            <input type="button" value="选择" onclick="dialogImg(1,this)" class="btn page_go"/>
										</p>
									</td>
								</tr>
								<tr>
									<td>手机轮播广告：</td>
									<td>
										<p>
											<label>广告一：</label> <span>
                                            <input type="text" id="mlb1" class="PublicText input_wd"  readonly="readonly" />
                                            <input type="button" id="btnChoose40" class="btn page_go"  value="选择" onclick="dialogImg(5,this)"/>
											</span>
										</p>

										<p>
											<label> 广告二：</label> <span> <input type="text" id="mlb2" class="PublicText input_wd" readonly="readonly" />
                                            <input type="button" id="btnChoose41" value="选择" onclick="dialogImg(5,this)"class="btn page_go"/>
											</span>
										</p>
									</td>
								</tr>
								<tr>
									<td>认证成功地址：</td>
									<td>
										<p class="chooseArea">
											<span>
                                                <input type="text" id="mobileToUrl" name="portalEdit.mobileToUrl" placeholder="比如：http://www.baidu.com" class="PublicText input_wd" >
											</span>
										</p>
									</td>
								</tr>
							</table>
							</p>
						</div>
						<%--------------------------------------手机模版  tab 结束-------------------------------------------%>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="dialog-confirm" title="是否提交审核？">
	<p>
		<span class="ui-icon ui-icon-showMsg" style="float:left; margin:0 7px 20px 0;"></span>呵呵
	</p>
</div>

    <%--素材选择弹窗--%>
<div class="dialog_bd" id="img_dialog" style="display: none"><div class="img_pick_panel inner_container_box side_l cell_layout">
    <div class="inner_main">
        <div class="img_pick_area">
            <div style="width: 677px;height:409px">
                <div class="img_pick">
                    <i class="icon_loading_small white js_loading" style="display: none;"></i>
                    <ul class="group js_list img_list" id="imgList">
                        <%--图片选择区域 不要乱动--%>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="js_pagebar">
        <div class="pagination" id="wxPagebar_1454309805382">
        <span class="page_nav_area">
        <a href="javascript:void(0);" class="btn page_first" style="display: none;"></a>
        <a href="javascript:void(0);" class="btn page_prev" style="display: inline-block;"><i class="arrow"></i></a>

            <span class="page_num">
                <label>2</label>
                <span class="num_gap">/</span>
                <label>5</label>
            </span>

        <a href="javascript:void(0);" class="btn page_next"><i class="arrow"></i></a>
        <a href="javascript:void(0);" class="btn page_last" style="display: none"></a>
    </span>
    <span class="goto_area">
        <input type="text" id="pageNum">
        <a href="javascript:void(0);" class="btn page_go" id="skipPage">跳转</a>
    </span>
        </div>
    </div>

</div>
</div>


<script type="text/javascript">
    $(function () {
        /* // 当前场所默认选中
         $("#barid option[value='
        ${internetBar.id}']").attr('selected', true);
         // 选中场所ID取得
         var barid = $('#barid').val()*/
        ;
        //grid加载
        $("#gridTable").jqGrid({
            url: '${ctx}/systemManage/portal/bySearch.do',
// 	    postData : {'goodsCategory.barid' : barid},
            height: 'auto',
            sortname: "updateTime",
            forceFit: true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
            altRows: true,
            colNames: ['模版名称', '所属场所', '默认模版', '创建日期', '状态', '操作', '模版ID', '修改时间'],
            colModel: [
                {name: 'portalModelName', index: 'portalModelName', align: 'center', width: '10'},
                {name: 'placeName', index: 'placeName', align: 'center', sorttype: "string", width: '20'},
                {name: 'isDefault', index: 'isDefault', align: 'center', width: '10'},
                {name: 'createTimeStr', index: 'createTime', align: 'center', width: '10'},
                {name: 'status', index: 'status', sorttype: "string", align: 'center', width: '10'},
                {name: 'operate', index: 'operate', sortable: false, align: 'center', width: '10'},
                {name: 'portalId', index: 'portalId', hidden: true},
                {name: 'updateTimeStr', index: 'editTime', hidden: true}
            ],
            rowList: ${rowList},
            jsonReader: {
                rowNum: "rowNum",
                root: "dataRows",          // 数据行（默认为：rows）
                page: "curPage",          // 当前页
                total: "totalPages",      // 总页数
                records: "totalRecords",  // 总记录数
                repeatitems: false       // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
            },
            gridComplete: function (rowid, rowdata) {
                var ids = $("#gridTable").getDataIDs();//jqGrid('getDataIDs');
                for (var i = 0; i < ids.length; i++) {
                    var commitHtml = '', delHtml = '', editHtml = '';
                    rowData = $("#gridTable").jqGrid("getRowData", ids[i]);
                    viewHtml = prePreviewHtm + "onclick='view(" + rowData.portalId + ")'" + sufPreviewHtm;
                    if (rowData.status == '保存未提交') {
                        commitHtml = preSubmitHtm + "onclick='submit(" + rowData.portalId + ")'" + sufSubmitHtm;
                        delHtml = preDelHtm + "onclick='deleteSel(" + rowData.portalId + ")'" + sufDelHtm;
                        editHtml = preUpdHtm + "onclick='edit(" + rowData.portalId + ")'" + sufUpdHtm;
                    } else if (rowData.status == '审核未通过') {
                        delHtml = preDelHtm + "onclick='deleteSel(" + rowData.portalId + ")'" + sufDelHtm;
                        editHtml = preUpdHtm + "onclick='edit(" + rowData.portalId + ")'" + sufUpdHtm;
                    }
                    jQuery("#gridTable").jqGrid('setRowData', ids[i], {operate: viewHtml + commitHtml + editHtml + delHtml});
                }
            },
            loadComplete: function (xhr) {
                $("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
                var datas = xhr.dataRows;
                for (var i = 0; i < datas.length; i++) {
                    datas[i].operate = "<input type='button' onclick='edit(" + datas[i].id + ")' class='btn_img_update'/><span style='margin-right: 8px;'></span>";
                }
            },
            pager: "#gridPager",
            multiselect: true   // 复选框设置
        }).navGrid('#gridPager', {edit: false, add: false, del: false, search: false, refresh: false})
                .navButtonAdd('#gridPager', {
                    caption: "<span style='color:red;'><b>批量删除</b></span>",
                    onClickButton: function () {
                        deleteSel();
                    },
                    position: "last"
                });
        // 场所列表Change事件
        $("#barid").change(function () {
            // 选中场所ID取得
            var barid = $('#barid').val();
            // grid重新加载
            $("#gridTable").trigger('reloadGrid');
        });

        $("#dialog-form").dialog({
            autoOpen: false,
            height: 630,
            width: 900,
            modal: true,
            buttons: {
                "保存模板": function () {
                     //验证表单数据
                    var logoPic = $("#logoPic").data('url');//logo素材IdI
                    var pc_background = $("#pc_background").data('url')==undefined?"":$("#pc_background").data('url');//pc背景素材Id
                    var pclb1 = $("#pclb1").data('id')==undefined?0:$("#pclb1").data('id');
                    var pclb2 = $("#pclb2").data('id')==undefined?0:$("#pclb2").data('id');
                    var pclb3 = $("#pclb3").data('id')==undefined?0:$("#pclb3").data('id');
                    var pcdl1 = $("#pcdl1").data('id')==undefined?0:$("#pcdl1").data('id');
                    var pcdl2 = $("#pcdl2").data('id')==undefined?0:$("#pcdl2").data('id');
                    var pcdl3 = $("#pcdl3").data('id')==undefined?0:$("#pcdl3").data('id');
                    var pcdl4 = $("#pcdl4").data('id')==undefined?0:$("#pcdl4").data('id');
                    //手机的素材
                    var m_background = $("#m_background").data('url')==undefined?0:$("#m_background").data('url');
                    var mlb1 = $("#mlb1").data('id')==undefined?0: $("#mlb1").data('id');
                    var mlb2 = $("#mlb2").data('id')==undefined?0:$("#mlb2").data('id');
                    //数组
                    var pclb_arr = [];
                    pclb_arr.push(pclb1);
                    pclb_arr.push(pclb2);
                    pclb_arr.push(pclb3);
                    console.log(pclb_arr);
                    var pcdl_arr = [];
                    pcdl_arr.push(pcdl1);
                    pcdl_arr.push(pcdl2);
                    pcdl_arr.push(pcdl4);
                    pcdl_arr.push(pcdl3);
                    var mlb_arr = [];
                    mlb_arr.push(mlb1);
                    mlb_arr.push(mlb2);
                    showMsg(1);
                    //${ctx}/systemManage/portal/getPlace.do"
                    $.ajax({
                        type: "POST",
                        url: '${ctx}/systemManage/portal/save.do',
                        data: $('#portalForm').serialize(),
                        dataType: 'json',
                        success: function (data) {
                            showMsg(data);
                        }
                    });
                        $(this).dialog("close");
                },
                "取消": function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                /**清空div**/
            }
        });

        $("#dialog-confirm").dialog({
            autoOpen: false,
            resizable: false,
            height: 150,
            modal: true,
            buttons: {
                "确定": function () {
                    $(this).dialog("close");
                },
                "取消": function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                /**清空div**/
            }
        });

        $("#functionDiv").append(addHtm);
        $("#addFunc").click(function () {
            getPlaceOrDevice();
            $("#dialog-form").dialog("option", "title", "新增认证模板").dialog("open");

        });
        $("#tabs").tabs();
        //素材跳转页面
        $("#skipPage").on('click', function () {
            var pageNum = $("#pageNum").val();
            var reg = /^[0-9]*$/;
            if(reg.test(pageNum)){
                chooseImg(pageNum,materialTypeForPortal);
            }else{
                showWaittingMsg('请输入正确的页码', 2000);
            }
        });

        $(".page_next").on('click', function () {
            var curPage = $(".page_num").find('label').eq(0).text();
            chooseImg(parseInt(curPage)+1,materialTypeForPortal);
        });
        $(".page_prev").on('click', function () {
            var curPage = $(".page_num").find('label').eq(0).text();
            //console.log(curPage - 1);
            chooseImg(curPage-1,materialTypeForPortal);
        });
    });

    // grid自适应
    $(window).resize(function () {
        $("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
    });
    /**去除数组里相同的值**/
    Array.prototype.removeRepeatAttr=function(){
        var tmp={},a=this.slice();
        for(var i=j=0;i<a.length;i++){
            if(!tmp[a[i].id]){
                tmp[a[i].id]=!0;
                j++;
            }else{
                this.splice(j,1);
            }
        };
    }

    function getPlaceOrDevice(flag){
        $.getJSON("${ctx}/systemManage/portal/getPlace.do",function(json){
            if(flag){
                $("#serialNum").empty().append("<option value=''>--全部--</option>");//清空设备
                for(var i=0;i<json.length;i++){
                    if(json[i]["placeCode"]==flag){
                        $("#serialNum").append("<option value='"+json[i]["serialnum"]+"'>"+ json[i]["hardwareId"]+"</option>");
                    }
                }
            }else {
                $("#serialNum").empty().append("<option value=''>--全部--</option>");//清空设备
                $("#placeCode").empty().append("<option value=''>--全部--</option>");//清空场所
                /**用来存放场所编号和场所名称**/
                var newObj = [];
                for (var i = 0; i < json.length; i++) {
                    newObj[i] = {};
                    newObj[i].placeCode = json[i]["placeCode"];
                    newObj[i].placeName = json[i]["placeName"];
                }
                /**去除相同的场所名称**/
                newObj.removeRepeatAttr();
                for (var i = 0; i < newObj.length; i++) {
                    $("#placeCode").append("<option value='" + newObj[i]["placeCode"] + "'>" + newObj[i]["placeName"] + "</option>")
                }
            }
        });
    }

    /*
        图片选中区域
     */
    function chooseImg(page,type) {
        if(page == undefined) {
            page = 1;
        }
        $("#imgList").empty();
        $.getJSON("${ctx}/systemManage/material/choseImg.do?page="+page+"&materialType="+type,function(json){
            //ajson['curPage']);
            var curPage= json['curPage'];
            var totalPages = json['totalPages'];
            //赋值当前页码和总共页数
            $(".page_num").find('label').eq(0).text(curPage);
            $(".page_num").find('label').eq(1).text(totalPages);
            var list  = json['dataRows'];
            var html = '';
            for (var i = 0; i < list.length; i++) {
                html ='<li class="img_item js_imageitem" data-id="'+list[i]["id"]+'" data-name="'+list[i]["materialName"]+'"><label class="frm_checkbox_label img_item_bd"><img src="'+list[i]["materialUrl"];
                html+='" class ="pic"><span class="lbl_content">'+list[i]["materialName"]+' </span> ';
                html += '<div class="selected_mask"><div class="selected_mask_inner"></div><div class="selected_mask_icon"></div></div></label></li>';
                $("#imgList").append(html);
            }
            //图片的点击事件
            $(".js_imageitem").on('click', function(){
                if($(this).find('label').hasClass('selected')){
                    $(this).find('label').removeClass('selected')
                }else{
                    $(".js_imageitem").find('label').removeClass('selected');
                    $(this).find('label').addClass('selected');
                }
            });
        });
    }
    /*
    * 选择图片弹窗
    */
    function dialogImg(type,dom) {
        //materialType 全局变量 初始化素材类型
        materialTypeForPortal = type;
       // showMsg(dom.value);
        $("#pageNum").val('');//清空页数输入框
        chooseImg(1,type);
        $("#img_dialog").dialog({
            //autoOpen: false,
            resizable: false,
            height: 'auto',
            width: '710px',
            modal: true,
            buttons: {
                "确定": function () {
                    //素材ID
                    var materialId = $(".js_imageitem").find('.selected').parent('.js_imageitem').data('id');
                    //素材名称
                    var materialName = $(".js_imageitem").find('.selected').parent('.js_imageitem').data('name');
                    var materialUrl = $(".js_imageitem").find('.selected').children('img').attr("src");
                    //showMsg(materialUrl);

                    $(dom).prev().val(materialName);

                    $(dom).prev().data('url',materialUrl);
                    $(dom).prev().data('id',materialId);//素材id
                    //showMsg($(dom).prev().data('id'));
                    //$("#materialName1").val(materialName);
                        $(this).dialog("close");
                },
                取消: function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                /**清空div**/
            }
        });
    }



</script>