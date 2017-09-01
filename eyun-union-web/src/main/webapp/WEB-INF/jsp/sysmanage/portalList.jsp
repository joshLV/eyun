<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/jsp/taglib.jsp" %>

<link rel="stylesheet" href="${ctx}/css/sysmanage/portal.css"/>

<style type="text/css">
    .input_wd {
        width: 200px;
    }
    .msg {
        /*display:none ;*/
        color: red;
    }
</style>
<div class="PublicC_t">
    <ul class="operation fr" id="functionDiv"></ul>
</div>

    <div class="right_c_tab">
        <!-- grid对应table设置 -->
        <table id="gridTable"></table>
        <div id="gridPager"></div>
        <!-- end -->
    </div>

<%--新增编辑素材弹出层--%>
<div id="dialog-form" title="新增模版素材" style="display: none;">
    <form id="portalForm" method="post">
        <input type="reset" name="reset" style="display: none;" />
        <%--模版ID 用于判断新增还是修改--%>
            <input type="hidden" name="portalId" id="portalId">

            <table width="100%" cellspacing="0" cellpadding="0" class="PublicTable">
                <tr>
                    <td width="10%">场&emsp; &emsp;所：</td>
                    <td width="40%">
                        <select name="placeId" id="placeId" onchange="businessFlag(this.value)">
                            <option value='-1'>--请选择--</option>
                        </select>
                </td>
                <%--设备序列号--%>
                    <td width="10%">模板名称：</td>
                    <td width="40%">
                        <input name="portalModelName" id="portalModelName" type="text" placeholder="请输入模版名称"
                               maxlength="30"
                               class="PublicText"/>
                </td>
                </tr>
            <%--场所编号和设备序列号可以确定唯一关联ID--%>
                <tr>
                    <td>背景图片</td>
                    <td>
                        <input type="text" id="backPic" readonly="readonly" class="PublicText" style="width: 200px">
                        <input type="button" id="btnChoose1" value="选择" onclick="dialogImg(1,this)"
                               class="btn page_go"/>
                </td>
                <td>公告区：</td>
                    <td>
                    <input name="messages" id="messages" maxlength="9" placeholder="限9个字符，不填不显示" class="PublicText">
                </td>
                </tr>
                <tr>
                <td>Logo区：</td>
                <td colspan="2">
                    <input type="text" id="logoPic" readonly="readonly" class="PublicText" style="width: 200px">
                    <input type="button" id="btnChoose2" value="选择" onclick="dialogImg(2,this)" class="btn page_go"/>
                </td>
                    <td>
                        <input type="text" name="title" id="title" value="易韵-您的私人秘书" class="PublicText"
                               placeholder="自定义标题，不填不显示" maxlength="9"/>
                </td>
                </tr>
                <tr>
                    <%--tab 开始--%>
                    <td colspan="4">
                        <div id="tabs" style="height: 368px" class="sub_tabs">
                            <ul>
                                <li><a href="#tabs-1">PC模板</a></li>
                                <li><a href="#tabs-2">手机模板</a></li>
                            </ul>
                            <%--------------------------------------PC模版  tab 开始-------------------------------------------%>
                            <div id="tabs-1">
                                <p>
                                <table>
                                    <tr>
                                        <td>认证成功地址：</td>
                                        <td>
                                            <p class="chooseArea">
											<span>
                                                <input type="text" id="pcToUrl" name="pcToUrl"
                                                       placeholder="比如：https://www.baidu.com/"
                                                       class="PublicText input_wd"/>
											</span>
                                                <span class="msg" id="Flag12">*商业广告，不可编辑</span>

                                            </p>
                                        </td>
                                    </tr>
                                    <%--以下要求动态拼接--%>
                                    <tr>
                                        <td>广告轮播区：</td>
                                        <td colspan="2">
                                            <p>
                                                <label> 轮播一：</label> <span>
                                            <input type="text" id="pclb1" readonly="readonly"
                                                   class="PublicText input_wd"/>
                                            <input type="button" id="btnChoose31" value="选择" onclick="dialogImg(3,this)"
                                                   class="btn page_go"/>
											</span>
                                                <span class="msg" id="Flag1">*商业广告，不可编辑</span>

                                            </p>

                                            <p>
                                                <label> 轮播二：</label> <span> <input type="text" id="pclb2"
                                                                                   readonly="readonly"
                                                                                   class="PublicText input_wd"/>
                                            <input type="button" id="btnChoose32" value="选择" onclick="dialogImg(3,this)"
                                                   class="btn page_go"/>
											</span>
                                                <span class="msg" id="Flag2">*商业广告，不可编辑</span>

                                            </p>

                                            <p class="chooseArea">
                                                <label> 轮播三：</label>
                                            <span>
                                            <input type="text" id="pclb3" readonly="readonly"
                                                   class="PublicText input_wd"/>
                                            <input type="button" id="btnChoose33" value="选择" onclick="dialogImg(3,this)"
                                                   class="btn page_go"/>
											</span>
                                                <span class="msg" id="Flag3">*商业广告，不可编辑</span>

                                            </p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>底部广告区：</td>
                                        <td colspan="2">
                                            <p class="chooseArea">
                                                <label>广告一：</label>
                                            <span>
                                            <input type="text" id="pcdl1" readonly="readonly"
                                                   class="PublicText input_wd"/>
                                            <input type="button" id="btnChoose34" value="选择" onclick="dialogImg(4,this)"
                                                   class="btn page_go"/>
											</span>
                                                <span class="msg" id="Flag4">*商业广告，不可编辑</span>

                                            </p>

                                            <p class="chooseArea">
                                                <label> 广告二：</label>
                                            <span>
                                            <input type="text" id="pcdl2" readonly="readonly"
                                                   class="PublicText input_wd"/>
                                            <input type="button" id="btnChoose35" value="选择" onclick="dialogImg(4,this)"
                                                   class="btn page_go"/>
											</span>
                                                <span class="msg" id="Flag5">*商业广告，不可编辑</span>

                                            </p>

                                            <p class="chooseArea">
                                                <label> 广告三：</label>
                                            <span>
                                            <input type="text" id="pcdl3" readonly="readonly"
                                                   class="PublicText input_wd"/>
                                            <input type="button" id="btnChoose36" value="选择" onclick="dialogImg(4,this)"
                                                   class="btn page_go"/>
											</span>
                                                <span class="msg" id="Flag6">*商业广告，不可编辑</span>

                                            </p>

                                            <p class="chooseArea">
                                                <label> 广告四：</label>
                                            <span>
                                                <input type="text" id="pcdl4" readonly="readonly"
                                                       class="PublicText input_wd">
                                                <input type="button" id="btnChoose37" value="选择"
                                                       onclick="dialogImg(4,this)" class="btn page_go">
											</span>
                                                <span class="msg" id="Flag7">*商业广告，不可编辑</span>

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
                                        <td>认证成功地址：</td>
                                        <td>
                                            <p class="chooseArea">
											<span>
                                                <input type="text" id="mobileToUrl" name="portalEdit.mobileToUrl"
                                                       placeholder="比如：https://www.baidu.com/"
                                                       class="PublicText input_wd">
											</span>
                                                <span class="msg" id="Flag13">*商业广告，不可编辑</span>
                                            </p>
                                        </td>
                                    </tr>
                                    <%--以下要求动态拼接--%>
                                    <tr>
                                        <td>手机轮播广告：</td>
                                        <td>
                                            <p>
                                                <label>广告一：</label> <span>
                                            <input type="text" id="mlb1" class="PublicText input_wd"
                                                   readonly="readonly"/>
                                            <input type="button" id="btnChoose40" class="btn page_go" value="选择"
                                                   onclick="dialogImg(5,this)"/>
											</span>
                                                <span class="msg" id="Flag10">*商业广告，不可编辑</span>

                                            </p>

                                            <p>
                                                <label> 广告二：</label> <span> <input type="text" id="mlb2"
                                                                                   class="PublicText input_wd"
                                                                                   readonly="readonly"/>
                                            <input type="button" id="btnChoose41" class="btn page_go" value="选择"
                                                   onclick="dialogImg(5,this)"/>
											</span>
                                                <span class="msg" id="Flag11">*商业广告，不可编辑</span>

                                            </p>

                                            <p>
                                                <label>广告三：</label> <span>
                                            <input type="text" id="mlb3" class="PublicText input_wd"
                                                   readonly="readonly"/>
                                            <input type="button" id="btnChoose42" class="btn page_go" value="选择"
                                                   onclick="dialogImg(5,this)"/>
											</span>
                                                <span class="msg" id="Flag14">*商业广告，不可编辑</span>

                                            </p>

                                            <p>
                                                <label> 广告四：</label> <span> <input type="text" id="mlb4"
                                                                                   class="PublicText input_wd"
                                                                                   readonly="readonly"/>
                                            <input type="button" id="btnChoose43" class="btn page_go" value="选择"
                                                   onclick="dialogImg(5,this)"/>
											</span>
                                                <span class="msg" id="Flag15">*商业广告，不可编辑</span>

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

<div id="dialog-confirm" title="是否提交审核？" style="text-align:center">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0"></span>是否提交审核？
    </p>
</div>

<%--素材选择弹窗--%>
<div class="dialog_bd" id="img_dialog" style="display: none" title="选择图片">
    <div class="img_pick_panel inner_container_box side_l cell_layout">
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
        <a href="javascript:void(0);" class="btn page_prev" style="display: inline-inline;"><i class="arrow"></i></a>

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
            mtype : 'post',
            datatype : "json",
            height: 'auto',
            //sortname: "updateTime",
            forceFit: true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
            altRows: true,
            colNames: ['模版名称', '所属场所', '默认模版', '创建日期', '状态', '操作', '模版ID', '修改时间','场所ID'],
            colModel: [
                {name: 'portalModelName', index: 'portalModelName', align: 'center', width: '10'},
                {name: 'placeName', index: 'placeName', align: 'center', sorttype: "string", width: '10'},
                {name: 'isDefaultName', index: 'isDefault', align: 'center', width: '10'},
                {name: 'createTimeStr', index: 'createTime', align: 'center', width: '10'},
                {name: 'status', index: 'status', sorttype: "string", align: 'center', width: '10'},
                {name: 'operate', index: 'operate', sortable: false, align: 'center', width: '10'},
                {name: 'id', index: 'portalId', hidden: true},
                {name: 'updateTimeStr', index: 'editTime', hidden: true},
                {name: 'placeId', index: 'placeId', hidden: true}
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
                    var commitHtml = '', delHtml = '', editHtml = '',defaultHtml="";
                    rowData = $("#gridTable").jqGrid("getRowData", ids[i]);
                    viewHtml = prePreviewHtm + "onclick='view(" + rowData.id + ")'" + sufPreviewHtm;
                    var phoneViewHtml = preMobilePreviewHtm + "onclick='viewPhone(" + rowData.id + ")'" + sufMobilePreviewHtm;
                    if (rowData.status == '保存未提交') {
                        commitHtml = preSubmitHtm + "onclick='submit(" + rowData.id + ")'" + sufSubmitHtm;
                        delHtml = preDelHtm + "onclick='deleteSel(" + rowData.id + ")'" + sufDelHtm;
                        editHtml = preUpdHtm + "onclick='edit(" + rowData.id + ")'" + sufUpdHtm;
                    } else if (rowData.status == '审核未通过') {
                        delHtml = preDelHtm + "onclick='deleteSel(" + rowData.id + ")'" + sufDelHtm;
                        editHtml = preUpdHtm + "onclick='edit(" + rowData.id + ")'" + sufUpdHtm;
                    } else if((rowData.status == '审核通过' || rowData.status == '已发布') && rowData.isDefaultName=='否') {
                        defaultHtml = preDefaultHtm + "onclick='setDeaultModel(" + rowData.id + ","+rowData.placeId+")'" +sufDefaultHtm;
                    }
                    jQuery("#gridTable").jqGrid('setRowData', ids[i], {operate: viewHtml + phoneViewHtml + commitHtml + editHtml + delHtml + defaultHtml});
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
       /* $("#barid").change(function () {
            // 选中场所ID取得
            var barid = $('#barid').val();
            // grid重新加载
            $("#gridTable").trigger('reloadGrid');
        });*/

        $("#dialog-form").dialog({
            autoOpen: false,
            height: 630,
            width: 900,
            modal: true,
            buttons: {
                "保存": function () {
                    var placeId = $("#placeId").val();
                    if (placeId == -1) {
                        showMsg("请选择场所");
                        return;
                    }
                    var portalModelName = $("#portalModelName").val();
                    if ($.trim(portalModelName) == '') {
                        showMsg("请输入模版名称");
                        return;
                    }
                    // url 判断 add on 2016年7月4日 15:52:46 by sun start-----
                    var reg = /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/;
                    var pcToUrl = $("#pcToUrl").val();
                    if($.trim(pcToUrl)!=null && $.trim(pcToUrl)!='') {
                        var r = $.trim(pcToUrl).match(reg);
                        if(r==null) {
                            showMsg("PC认证成功地址格式不对");
                            return;
                        }
                    }
                    var mobileToUrl = $("#mobileToUrl").val();
                    if($.trim(mobileToUrl)!=null && $.trim(mobileToUrl)!='') {
                        var q = $.trim(mobileToUrl).match(reg);
                        if (q == null) {
                            showMsg("手机认证成功地址格式不对");
                            return;
                        }
                    }
                    // url 判断 add on 2016年7月4日 15:52:46 by sun  end-----
                    var portal = {
                        "id":$("#portalId").val(),
                        "portalModelName": portalModelName,
                        "placeId": placeId,
                        "backGroudUrl": $("#backPic").data('url'),
                        "backGroudID": $("#backPic").data('id'),
                        "backPic": $("#backPic").data,
                        "messages": $("#messages").val(),
                        "logoUrl": $("#logoPic").data('url'),
                        "logoID": $("#logoPic").data('id'),
                        "position1MaterialID": $("#pclb1").data('id'),
                        "position1Url": $("#pclb1").data('url'),
                        "position2MaterialID": $("#pclb2").data('id'),
                        "position2Url": $("#pclb2").data('url'),
                        "position3MaterialID": $("#pclb3").data('id'),
                        "position3Url": $("#pclb3").data('url'),
                        "position4MaterialID": $("#pcdl1").data('id'),
                        "position4Url": $("#pcdl1").data('url'),
                        "position5MaterialID": $("#pcdl2").data('id'),
                        "position5Url": $("#pcdl2").data('url'),
                        "position6MaterialID": $("#pcdl3").data('id'),
                        "position6Url": $("#pcdl3").data('url'),
                        "position7MaterialID": $("#pcdl4").data('id'),
                        "position7Url": $("#pcdl4").data('url'),
                        // 8,9位预留广告位
                        "position8MaterialID": '',
                        "position8Url": '',
                        "position9MaterialID": '',
                        "position9Url": '',
                        "mobileMaterial1ID": $("#mlb1").data('id'),
                        "mobileMaterial1Url": $("#mlb1").data('url'),
                        "mobileMaterial2ID": $("#mlb2").data('id'),
                        "mobileMaterial2Url": $("#mlb2").data('url'),
                        "mobileMaterial3ID": $("#mlb3").data('id'),
                        "mobileMaterial3Url": $("#mlb3").data('url'),
                        "mobileMaterial4ID": $("#mlb4").data('id'),
                        "mobileMaterial4Url": $("#mlb4").data('url'),
                        "title": $("#title").val(),
                        "pcToUrl": pcToUrl,
                        "mobileToUrl": mobileToUrl
                    }
                    var url = "${ctx}/systemManage/portal/savePortal.do";
                    if($("#portalId").val()>0) {
                        url = "${ctx}/systemManage/portal/updatePortal.do";
                    }
                    var flag = "";
                    $.ajax({
                        async: false,
                        type: "POST",
                        url: url,
                        data: portal,
                        dataType: 'json',
                        success: function (data) {
                            flag = data;
                        }
                    });
                    if(flag >=0 ){
                        refresh();
                    }else if(flag == -2) {
                        showMsg("模版名称重复了");
                        return;
                    }else{
                        showMsg("操作失败");
                        return;
                    }
                    $(this).dialog("close");
                },
                "清空": function () {
                    businessFlag(-1);
                    $("input[type=reset]").trigger("click");
                    getPlaceList();
                    //$(this).dialog("close");
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
                    //alert("确定")
                    var id = $("#portalId").val();
                    //var keyId = 'updateStatus';
                    $.ajax({
                        //async: false,
                        cache: true,
                        type: "POST",
                        url: '${ctx}/systemManage/portal/updateStatus.do',
                        data: {id: id, status: '待审核'},
                        dataType: 'json',
                        success: function (data) {
                            refresh();
                        }
                    });
                    $(this).dialog("close");
                },
                "取消": function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                refresh();
            }
        });

        $("#functionDiv").append(addHtm);
        $("#addFunc").click(function () {
            businessFlag(-1);
            clearDiv();
            getPlaceList();
            $("#dialog-form").dialog("option", "title", "新增认证模板").dialog("open");

        });
        $("#tabs").tabs();
        //素材跳转页面
        $("#skipPage").on('click', function () {
            var pageNum = $("#pageNum").val();
            var reg = /^[0-9]*$/;
            if (reg.test(pageNum)) {
                chooseImg(pageNum, materialTypeForPortal);
            } else {
                showWaittingMsg('请输入正确的页码', 2000);
            }
        });

        $(".page_next").on('click', function () {
            var curPage = $(".page_num").find('label').eq(0).text();
            chooseImg(parseInt(curPage) + 1, materialTypeForPortal);
        });
        $(".page_prev").on('click', function () {
            var curPage = $(".page_num").find('label').eq(0).text();
            //console.log(curPage - 1);
            chooseImg(curPage - 1, materialTypeForPortal);
        });
    });

    // grid自适应
    $(window).resize(function () {
        $("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
    });
    /*
     图片选中区域
     */
    function chooseImg(page, type) {
        if (page == undefined) {
            page = 1;
        }
        $("#imgList").empty();
        $.getJSON("${ctx}/systemManage/material/choseImg.do?page=" + page + "&materialType=" + type, function (json) {
            //ajson['curPage']);
            var curPage = json['curPage'];
            var totalPages = json['totalPages'];
            //赋值当前页码和总共页数
            $(".page_num").find('label').eq(0).text(curPage);
            $(".page_num").find('label').eq(1).text(totalPages);
            var list = json['dataRows'];
            var html = '';
            for (var i = 0; i < list.length; i++) {
                html = '<li class="img_item js_imageitem" data-id="' + list[i]["id"] + '" data-name="' +
                list[i]["materialName"] + '"><label class="frm_checkbox_label img_item_bd"><img src="' +
                list[i]["materialUrl"];
                html += '" class ="pic"><span class="lbl_content">' + list[i]["materialName"] + ' </span> ';
                html += '<div class="selected_mask">' +
                '<div class="selected_mask_inner"></div><div class="selected_mask_icon"></div></div></label></li>';
                $("#imgList").append(html);
            }
            //图片的点击事件
            $(".js_imageitem").on('click', function () {
                if ($(this).find('label').hasClass('selected')) {
                    $(this).find('label').removeClass('selected')
                } else {
                    $(".js_imageitem").find('label').removeClass('selected');
                    $(this).find('label').addClass('selected');
                }
            });
        });
    }
    /*
     * 选择图片弹窗
     */
    function dialogImg(type, dom) {
        //materialType 全局变量 初始化素材类型
        materialTypeForPortal = type;
        // alert(dom.value);
        $("#pageNum").val('');//清空页数输入框
        chooseImg(1, type);
        var title = '';
        switch (type) {
            case 1: title='选择背景图片';break;
            case 2: title='选择LOGO图片';break;
            case 3: title='选择PC轮播图片';break;
            case 4: title='选择PC底部图片';break;
            case 5: title='选择手机轮播图片';break;
            //case 4: title='选择PC底部图片';break;
        }
        $("#img_dialog").dialog({
            //autoOpen: false,
            resizable: false,
            height: 'auto',
            width: '710px',
            modal: true,
            title: title,

            //option:{title:"nifegegeg"},
            buttons: {
                "确定": function () {
                    //素材ID
                    var materialId = $(".js_imageitem").find('.selected').parent('.js_imageitem').data('id');
                    //素材名称
                    var materialName = $(".js_imageitem").find('.selected').parent('.js_imageitem').data('name');
                    var materialUrl = $(".js_imageitem").find('.selected').children('img').attr("src");
                    //alert(materialUrl);
                    console.log(materialId);
                    $(dom).prev().val(materialName==undefined?"":materialName);

                    $(dom).prev().data('url', materialUrl==undefined?"":materialUrl);
                    $(dom).prev().data('id', materialId==undefined?"":materialId);//素材id
                    //alert($(dom).prev().data('id'));
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
    /**编辑模版操作**/
    function edit(id) {
        $("#portalId").val(id);
        $.ajax({
            url: '${ctx}/systemManage/portal/getPortal.do',
            type: 'post',
            dataType: "json",
            data: {id: id},
            success: function (data) {
                //console.log(data);
                // 开始在编辑页面赋值 这个要一个一个赋值
                $("#placeId").empty().append("<option value='"+data.placeId+"'>"+data.placeName+"</option>");
                businessFlag(data.placeId);
                $("#portalModelName").val(data.portalModelName);
                $("#backPic").data('url',data.backGroudUrl);
                $("#backPic").data('id',data.backGroudID);
                $("#backPic").val(data.backGroudName);
                $("#messages").val(data.messages);
                $("#logoPic").data('url',data.logoUrl);
                $("#logoPic").val(data.logoName);
                $("#logoPic").data('id',data.logoID);
                // PC轮播一
                $("#pclb1").data('id',data.position1MaterialID);
                $("#pclb1").data('url',data.position1Url);
                $("#pclb1").val(data.position1MaterialName);
                // PC轮播二
                $("#pclb2").data('id',data.position2MaterialID);
                $("#pclb2").data('url',data.position2Url);
                $("#pclb2").val(data.position2MaterialName);
                // PC轮播三
                $("#pclb3").data('id',data.position3MaterialID);
                $("#pclb3").data('url',data.position3Url);
                $("#pclb3").val(data.position3MaterialName);
                // PC底栏一
                $("#pcdl1").data('id',data.position4MaterialID);
                $("#pcdl1").data('url',data.position4Url);
                $("#pcdl1").val(data.position4MaterialName);
                // PC底栏二
                $("#pcdl2").data('id',data.position5MaterialID);
                $("#pcdl2").data('url',data.position5Url);
                $("#pcdl2").val(data.position5MaterialName);
                // PC底栏三
                $("#pcdl3").data('id',data.position6MaterialID);
                $("#pcdl3").data('url',data.position6Url);
                $("#pcdl3").val(data.position6MaterialName);
                // PC底栏四
                $("#pcdl4").data('id',data.position7MaterialID);
                $("#pcdl4").data('url',data.position7Url);
                $("#pcdl4").val(data.position7MaterialName);
                // 手机广告1
                $("#mlb1").data('id',data.mobileMaterial1ID);
                $("#mlb1").data('url',data.mobileMaterial1Url);
                $("#mlb1").val(data.mobileMaterial1Name);
                // 手机广告2
                $("#mlb2").data('id',data.mobileMaterial2ID);
                $("#mlb2").data('url',data.mobileMaterial2Url);
                $("#mlb2").val(data.mobileMaterial2Name);
                // 手机广告3
                $("#mlb3").data('id',data.mobileMaterial3ID);
                $("#mlb3").data('url',data.mobileMaterial3Url);
                $("#mlb3").val(data.mobileMaterial3Name);
                // 手机广告4
                $("#mlb4").data('id',data.mobileMaterial4ID);
                $("#mlb4").data('url',data.mobileMaterial4Url);
                $("#mlb4").val(data.mobileMaterial4Name);

                $("#title").val(data.title);
                $("#pcToUrl").val(data.pcToUrl);
                $("#mobileToUrl").val(data.mobileToUrl);

            }
        });
        $("#dialog-form").dialog("option", {
            "title": "编辑认证模版",
            "position": {my: "center", at: "center", of: window}
        }).dialog("open");
    }
    /**
     * onchange事件 by sun
     */
    function businessFlag(placeId) {
        $.ajax({
            async: false,
            url: '${ctx}/systemManage/portal/getBusinessFlag.do',
            type: 'post',
            dataType: "json",
            data: {placeId: placeId},
            success: function (data) {
                //alert(data);
                changeView(data);
            }
        });
    }
    function changeView(data) {
        if (!data) return;
        $("#backPic").data('id', '');
        $("#backPic").data('url', '');
        $("#logoPic").data('id', '');
        $("#logoPic").data('url', '');
        data = $.evalJSON(data);
        if (data.Flag1 == 'N') {
            $("#Flag1").attr("style", "display: none;");
            $("#btnChoose31").removeAttr("disabled");
            $("#materialName31").removeAttr("disabled");
        } else {
            $("#Flag1").attr("style", "display: inline;");
            $("#btnChoose31").attr("disabled", "disabled");
            $("#pclb1").attr("disabled", "disabled").val("");
            $("#pclb1").data('id', '');
            $("#pclb1").data('url', '');
        }
        if (data.Flag2 == 'N') {
            $("#Flag2").attr("style", "display: none;");
            $("#btnChoose32").removeAttr("disabled");
            $("#materialName32").removeAttr("disabled");
        } else {
            $("#Flag2").attr("style", "display: inline;");
            $("#btnChoose32").attr("disabled", "disabled");
            $("#pclb2").attr("disabled", "disabled").val("");
            $("#pclb2").data('id', '');
            $("#pclb2").data('url', '');
        }
        if (data.Flag3 == 'N') {
            $("#Flag3").attr("style", "display: none;");
            $("#btnChoose33").removeAttr("disabled");
            $("#materialName33").removeAttr("disabled");
        } else {
            $("#Flag3").attr("style", "display: inline;");
            $("#btnChoose33").attr("disabled", "disabled");
            $("#pclb3").attr("disabled", "disabled").val("");
            $("#pclb3").data('id', '');
            $("#pclb3").data('url', '');
        }
        if (data.Flag4 == 'N') {
            $("#Flag4").attr("style", "display: none;");
            $("#btnChoose34").removeAttr("disabled");
            $("#materialName34").removeAttr("disabled");
        } else {
            $("#Flag4").attr("style", "display: inline;");
            $("#btnChoose34").attr("disabled", "disabled");
            $("#pcdl1").attr("disabled", "disabled").val("");
            $("#pcdl1").data('id', '');
            $("#pcdl1").data('url', '');
        }
        if (data.Flag5 == 'N') {
            $("#Flag5").attr("style", "display: none;");
            $("#btnChoose35").removeAttr("disabled");
            $("#materialName35").removeAttr("disabled");
        } else {
            $("#Flag5").attr("style", "display: inline;");
            $("#btnChoose35").attr("disabled", "disabled");
            $("#pcdl2").attr("disabled", "disabled").val("");
            $("#pcdl2").data('id', '');
            $("#pcdl2").data('url', '');
        }
        if (data.Flag6 == 'N') {
            $("#Flag6").attr("style", "display: none;");
            $("#btnChoose36").removeAttr("disabled");
            $("#materialName36").removeAttr("disabled");
        } else {
            $("#Flag6").attr("style", "display: inline;");
            $("#btnChoose36").attr("disabled", "disabled");
            $("#pcdl3").attr("disabled", "disabled").val("");
            $("#pcdl3").data('id', '');
            $("#pcdl3").data('url', '');
        }
        if (data.Flag7 == 'N') {
            $("#Flag7").attr("style", "display: none;");
            $("#btnChoose37").removeAttr("disabled");
            $("#materialName37").removeAttr("disabled");
        } else {
            $("#Flag7").attr("style", "display: inline;");
            $("#btnChoose37").attr("disabled", "disabled");
            $("#pcdl4").attr("disabled", "disabled").val("");
            $("#pcdl4").data('id', '');
            $("#pcdl4").data('url', '');
        }
        //手机portal广告位置
        if (data.Flag10 == 'N') {//原来为8 醉了 不按文档来写
            $("#Flag10").attr("style", "display: none;");
            $("#btnChoose40").removeAttr("disabled");
            $("#materialName40").removeAttr("disabled");
        } else {
            $("#Flag10").attr("style", "display: inline;");
            $("#btnChoose40").attr("disabled", "disabled");
            $("#mlb1").attr("disabled", "disabled").val("");
            $("#mlb1").data('id', '');
            $("#mlb1").data('url', '');
        }
        if (data.Flag11 == 'N') {//原来为9
            $("#Flag11").attr("style", "display: none;");
            $("#btnChoose41").removeAttr("disabled");
            $("#materialName41").removeAttr("disabled");
        } else {
            $("#Flag11").attr("style", "display: inline;");
            $("#btnChoose41").attr("disabled", "disabled");
            $("#mlb2").attr("disabled", "disabled").val("");
            $("#mlb2").data('id', '');
            $("#mlb2").data('url', '');
        }

        /* add by sun  判断第12位和第十三为 start------------------------------分水岭*/
        if (data.Flag12 == 'N') {
            $("#Flag12").attr("style", "display: none;");
            $("#pcToUrl").removeAttr("disabled");
        } else {
            $("#Flag12").attr("style", "display: inline;");
            $("#pcToUrl").attr("disabled", "disabled").val("");
        }
        if (data.Flag13 == 'N') {
            $("#Flag13").attr("style", "display: none;");
            $("#mobileToUrl").removeAttr("disabled");
        } else {
            $("#Flag13").attr("style", "display: inline;");
            $("#mobileToUrl").attr("disabled", "disabled").val("");
        }

        /**标志位加到了15位了 觉得不是怎么合理呢**/
        if (data.Flag14 == 'N') {
            $("#Flag14").attr("style", "display: none;");
            $("#btnChoose42").removeAttr("disabled");
            $("#materialName42").removeAttr("disabled");
        } else {
            $("#Flag14").attr("style", "display: inline;");
            $("#btnChoose42").attr("disabled", "disabled");
            $("#mlb3").attr("disabled", "disabled").val("");
            $("#mlb3").data('id', '');
            $("#mlb3").data('url', '');
        }

        if (data.Flag15 == 'N') {
            $("#Flag15").attr("style", "display: none;");
            $("#btnChoose43").removeAttr("disabled");
            $("#materialName43").removeAttr("disabled");
        } else {
            $("#Flag15").attr("style", "display: inline;");
            $("#btnChoose43").attr("disabled", "disabled");
            $("#mlb4").attr("disabled", "disabled").val("");
            $("#mlb4").data('id', '');
            $("#mlb4").data('url', '');
        }
    }
    // 场所列表
    function getPlaceList() {
        $.ajax({
            url: '${ctx}/systemManage/portal/getOwnPlaceList.do',
            type: 'post',
            dataType: "json",
            success: function (json) {
                var jsonObj = eval("(" + json + ")");
                $("#placeId").empty().append("<option value='-1'>-请选择-</option>");
                $.each(jsonObj, function (i, item) {
                    $("#placeId").append("<option value=" + item.id + ">" + item.placeName + "</option>");
                });
            },
            error: function (text) {
            }
        });

    }
    /**提交审核操作**/
    function submit(id) {
        $("#portalId").val(id);
        $("#dialog-confirm").dialog("open");
    }
    //jqgrid刷新
    function refresh() {
        jQuery("#gridTable").trigger("reloadGrid");
    }
    // 清空div
    function clearDiv() {
        $("#portalId").val("");
        $("input[type=reset]").trigger("click");
    }
    /**
     * 设置默认模版
     * @param portalId
     * @param placeId
     */
    function setDeaultModel(portalId,placeId) {
        $.ajax({
            //async: false,
            type: "POST",
            url: '${ctx}/systemManage/portal/setDefaultModel.do',
            data: {id: portalId, placeId: placeId},
            dataType: 'json',
            success: function (data) {
                if(data>=0) {
                    refresh();
                }else{
                    showMsg("设置默认模版失败");
                }

            }
        });
    }

    function view(id) {
        window.open("${ctx}/systemManage/portal/portalPreview.do?id="+id,"_blank");
    }
    function viewPhone(id) {
        window.open("${ctx}/systemManage/portal/mobilePreview.do?id="+id,"_blank","height=550, width=317,left=600,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
    }

    /**批量删除按键**/
    function deleteSel(id) {
        /**id数组**/
        var rowData;
        if (id) {
            rowData = [id];
        } else {
            rowData = jQuery('#gridTable').jqGrid('getGridParam', 'selarrrow');
        }
        if (rowData.length) {
            var id, updateTime, status;
            /**ID数组**/
            var ids = new Array();
            /**updateTime数组**/
            var updateTimes = new Array();
            /**状态数组**/
            var statusArray = new Array();
            /**不可以删除的数组下标数组**/
            //var removeFlag = new Array();
            for (var i = 0; i < rowData.length; i++) {
                id = jQuery('#gridTable').jqGrid('getCell', rowData[i], 'id');//name是colModel中的一属性
                updateTime = jQuery('#gridTable').jqGrid('getCell', rowData[i], 'updateTimeStr');
                status = jQuery('#gridTable').jqGrid('getCell', rowData[i], 'status');
                /**如果素材处于待审核或者审核通过的状态 则不可以被删除**/
                if (status == '待审核' || status == '审核通过' || status == '已发布') {
                    //removeFlag[i] = i;
                    showWaittingMsg("审核中和审核通过的模版不可以删除", 2000);
                    return;
                }
                statusArray[i] = status;
                ids[i] = id;
                updateTimes[i] = updateTime;
            }
            if (ids.length) {
                showMsg("你确定要删除这" + ids.length + "条模版数据?", 2, function () {
                    $.ajax({
                        type: 'post',
                        url: '${ctx}/systemManage/portal/delPortal.do',
                        data: {
                            'ids': ids.toString(),
                            'updateTimes': updateTimes.toString()
                        },
                        dataType: 'json',
                        success: function (data) {
                            var msg = data.msg;
                            showWaittingMsg(msg, 2000);
                            refresh();
                        }
                    });
                });
            } else {
                var msg = "<spring:message code='errors.systemmange.material.warning'/>";
                showMsg(msg);
            }
        } else {
            var msg = "<spring:message code='errors.selectData'/>";
            showMsg(msg);
        }
    }
</script>