<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/jsp/taglib.jsp" %>
<link rel="stylesheet" href="${ctx}/css/sysmanage/material.css"/>
<script type="text/javascript" src="${ctx}/scripts/sysmanage/uploadPreview.js"></script>
<script type="text/javascript" src="${ctx}/scripts/sysmanage/mater_add.js"></script>
<script type="text/javascript" src="${ctx}/common/scripts/jquery.form.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">

<body>
<%--搜索区域--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">
    <tr>
        <td align="right" width="8%">素材名称：</td>
        <td width="15%"><input type="text" class="PublicText" id="searchName"/></td>
        <td align="right" width="8%">素材类型：</td>
        <td style="width:15%">
            <div class="select PublicSelect hack">
                <input type="text" value="请选择" readonly="readonly" id="roleSel" roleSelValue="">
                <i></i>
                <ul id="roleSelUl" style="z-index:999">
                    <li value="">请选择</li>
                    <li value="4">普通广告</li>
                    <li value="3">中心广告</li>
                    <li value="5">手机版广告</li>
                    <li value="2">logo</li>
                    <li value="1">背景图</li>
                </ul>
            </div>
        </td>
        <td align="right" width="8%">状态：</td>
        <td style="width:15%">
            <div class="select PublicSelect hack">
                <input type="text" value="请选择" readonly="readonly" id="statusSel" statusSelValue="">
                <i></i>
                <ul id="statusSelUl" style="z-index:999">
                    <li value="">请选择</li>
                    <li value="待审核">待审核</li>
                    <li value="保存未提交">保存未提交</li>
                    <li value="审核通过">审核通过</li>
                </ul>
            </div>
        </td>
        <td colspan="2">
            <input type="button" class="PublicButton" value="查 询" onclick="search();">
        </td>
    </tr>
</table>
<%--表格--%>
<div class="PublicC_t">
    <ul class="operation fr" id="functionDiv"></ul>
</div>
    <div class="right_c_tab">
        <!-- grid对应table设置 -->
        <table id="gridTable"></table>
        <div id="gridPager"></div>
        <!-- end -->
    </div>
<%--素材详细信息弹出框--%>
<div id="dialogInfo" title="认证素材详情" style="display: none;width:601px">
    <table width="100%" cellpadding="0" cellspacing="0" class="table table-hover table-bordered">
        <%--<tr>
            <td colspan="2" ><label id="materialTitle" class="mg-l-20 fw">认证素材信息</label></td>
        </tr>--%>
        <tr>
            <td width="19%">素材名称：</td>
            <td width="80%"><span id="materialNameInfo"></span></td>
        </tr>
        <tr>
            <td>素材类型：</td>
            <td><span id="materialTypeInfo"></span></td>
        </tr>
        <tr>
            <td>素材图片：</td>
            <td><img id="imgViewInfo" class="img-show mg-l-20" src=""/></td>
        </tr>
        <tr>
            <td>备注：</td>
            <td height="100"><textarea id="remarkInfo" cols="45" rows="5" maxlength="100" readonly></textarea></td>
        </tr>
    </table>
</div>
<%--新增编辑素材弹出层--%>
<div id="dialog-form" title="新增认证素材" style="display: none;">
    <%--<p class="validateTips">所有的表单字段都是必填的。</p>--%>
        <form id="signupForm" method="post" enctype="multipart/form-data"
              action="${ctx}/systemManage/material/saveMaterial.do">
        <fieldset>
            <table width="100%" cellpadding="0" cellspacing="0" class="table table-hover table-bordered">
                <tr id=" ">
                    <td width="19%">素材名称：</td>
                    <td colspan="2"><input type="text" name="materialName" id="cmaterialName" class="PublicText"
                                           style="width: 150px"/> <%--素材ID--%> <input id="materialId" type="hidden"
                                                                                      name="id"/> <%--素材上传时生成的url--%>
                        <input type="hidden" name="materialUrl" id="cmaterialUrl"/> <span class="material_error"></span>
                    </td>
                </tr>
                <tr>
                    <td class="txt-left clr-bd-r-grey clr-bd-b-grey">素材类型：</td>
                    <td class="clr-bd-b-grey" colspan="2"><select id="cmaterialType" name="materialType"
                                                                  onchange="material_change()"
                                                                  class="mg-l-20 clr-bd-inpt inpt-sel">
                        <option value="">请选择</option>
                        <option value="4">普通广告</option>
                        <option value="3">中心广告</option>
                        <option value="5">手机版广告</option>
                        <option value="2">logo</option>
                        <option value="1">背景图</option>
                    </select>&nbsp;&nbsp;<span class="plcsize"></span></td>
                </tr>
                <tr>
                    <td>素材图片上传：</td>
                    <td colspan="2">
                        <div>
                            <a href="javascript:;" class="file">选择文件<input type="file" name="imgFile" id="fileField"
                                                                           class="file" size="24"/></a>
                            <%--上传状态提示--%>
                            <span class="uploadMess"></span>
                        </div>
                        <div id="imgdiv" class="mg-l-20">
                            <img id="imgView" class="img-show" src="" alt="请选择要上传的图片"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td height="100" colspan="2"><textarea name="remark" id="remarkId" cols="45" rows="5"
                                                           maxlength="100"></textarea></td>
                </tr>
            </table>
        </fieldset>
        <div style="display : none;">
            <input type="reset" id="clearData">
        </div>

    </form>
</div>
<div id="dialog-confirm" title="是否提交审核？" style="display: none;text-align:center">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>是否提交审核？
    </p>
</div>
</body>
<script type="text/javascript" src="${ctx}/scripts/select.js"></script>
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
            url: '${ctx}/systemManage/material/bySearch.do',
//             postData : {'goodsCategory.barid' : barid},
            height: 'auto',
            forceFit: true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
            altRows: true,
            colNames: ['素材名称', '素材类型', '状态', '修改时间', '操作', 'id', '素材url', '素材类型', '备注'],
            colModel: [
                {name: 'materialName', index: 'materialName', align: 'center', sorttype: "string", width: '10'},
                {name: 'materialTypeName', index: 'materialTypeName', align: 'center', width: '10'},
                {name: 'status', index: 'status', sorttype: "string", align: 'center', width: '10'},
                {name: 'updateTimeStr', index: 'editTime', sorttype: "string", align: 'center', width: '10'},
                {name: 'operate', index: 'operate', sortable: false, align: 'center', width: '10'},
                {name: 'id', index: 'id', hidden: true},
                {name: 'materialUrl', index: 'url', hidden: true},
                {name: 'materialType', index: 'type', hidden: true},
                {name: 'remark', index: 'remark', hidden: true},
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
                    viewHtml = prePreviewHtm + "onclick='view(" + ids[i] + ")'" + sufPreviewHtm;
                    var commitHtml = '', delHtml = '', editHtml = '';
                    rowData = $("#gridTable").jqGrid("getRowData", ids[i]);
                    if (rowData.status == '保存未提交') {
                        commitHtml = preSubmitHtm + "onclick='submit(" + ids[i] + ")'" + sufSubmitHtm;
                        delHtml = preDelHtm + "onclick='deleteSel(" + ids[i] + ")'" + sufDelHtm;
                        editHtml = preUpdHtm + "onclick='edit(" + ids[i] + ")'" + sufUpdHtm;
                    } else if (rowData.status == '审核未通过') {
                        delHtml = preDelHtm + "onclick='deleteSel(" + ids[i] + ")'" + sufDelHtm;
                        editHtml = preUpdHtm + "onclick='edit(" + ids[i] + ")'" + sufUpdHtm;
                    }
                    jQuery("#gridTable").jqGrid('setRowData', ids[i], {operate: viewHtml + commitHtml + editHtml + delHtml}, {height: 35});
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
            height: 500,
            width: 600,
            modal: true,
            buttons: {
                "保存": function () {
                    // 素材名称
                    var materialName = $("#cmaterialName").val();
                    if (!$.trim(materialName)) {
                        showDialogErrMsg("cmaterialName", "请输入素材名称", "dialog-form");
                        //$(".material_error").html("请输入素材名称");
                        return;
                    }
                    hideErrMsg("cmaterialName");
                    // 素材类型
                    var type = $("#cmaterialType").val();
                    if (!type) {
                        //$(".uploadMess").addClass("clr-error").html("请选择素材类型！");
                        showDialogErrMsg("cmaterialType", "请选择素材类型", "dialog-form");
                        return;
                    }
                    hideErrMsg("cmaterialType");
                    // 素材Id 如果存在就是编辑页面 如果不存在就是新增页面
                    var materialId = $("#materialId").val();
                    //var materialUrl = $("#cmaterialUrl").val();
                    if (materialId != null && materialId != '') {
                        $("#signupForm").attr("action", "${ctx}/systemManage/material/updMaterial.do")
                    } else {
                        var fileField = $("#fileField").val();
                        if (!fileField) {
                            showDialogErrMsg("cfileField", "请选择上传的文件", "dialog-form");
                            return;
                        }
                        hideErrMsg("cfileField");

                    }
                    hideErrMsg("cmaterialUrl");

                    //responseResult 为从后台返回信息，通常情况下返回的是JSON，我们工作中常使有的
                    $("#signupForm").ajaxSubmit(function (responseResult) {
                        var result = $.evalJSON(responseResult);
                        if (result && result["message"] >= 0) {
                            $(".uploadMess").html("保存成功");
                            $("#dialog-form").dialog('close');
                            refresh();
                        } else if (result["message"] == '-2') {
                            $(".uploadMess").html("素材名称重复");
                        }
                    });
                },
                "取消": function () {
                    hideErrMsg();
                    $(this).dialog("close");
                }
            },
            close: function () {
                /**清空div**/
                clearDiv();
            }
        });

        $("#dialog-confirm").dialog({
            autoOpen: false,
            resizable: false,
            height: 150,
            modal: true,
            buttons: {
                "确定": function () {
                    updateMaterialStatus('${ctx}');
                    $(this).dialog("close");
                },
                "取消": function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                /**清空div**/
                clearDiv();
            }
        });
        $("#functionDiv").append(addHtm);
        $("#addFunc").click(function () {
            $("#dialog-form").dialog("option", {
                "title": "新增认证素材",
                "position": {my: "center", at: "center", of: window}
            }).dialog("open");
        });
        uploadFile();
        //下拉框
        /*$(".select ul li").click(function() {
         $(this).parent().hide();
         $("#roleSel").attr("roleSelValue", $(this).val()).val($(this).html());*/
        bindEvent();

    });

    // grid自适应
    $(window).resize(function () {
        $("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
    });
    /*xialakuang*/
    function bindEvent() {

        //下拉框选中事件
        $(".select ul li").unbind("click").click(function () {
            var obj = $(this).parent("ul").prevAll("input");
            var idName = obj.attr("id");
            obj.attr(idName + "Value", $(this).attr("value"));
            obj.val(this.innerText.trim());
            $(this).parent("ul").hide();
        });
    }
    /**查看素材信息**/
    function view(id) {
        var rowData = $("#gridTable").jqGrid("getRowData", id);
        $("#materialNameInfo").html(rowData.materialName);
        $("#materialTypeInfo").html(rowData.materialTypeName);
        $("#imgViewInfo").attr("src", rowData.materialUrl);
        $("#remarkInfo").val(rowData.remark);
        $("#dialogInfo").dialog({
            height: 400,
            width: 601,
            modal: true
        });
    }
    /**编辑素材操作**/
    function edit(id) {
        $("#materialId").val(id);
        var rowData = $("#gridTable").jqGrid("getRowData", id);
        $("#cmaterialName").val(rowData.materialName);
        $("#cmaterialType").val(rowData.materialType);
        $("#imgView").attr("src", rowData.materialUrl);
        $("#cmaterialUrl").val(rowData.materialUrl);
        $("#remarkId").val(rowData.remark);
        $("#dialog-form").dialog("option", {
            "title": "编辑认证素材",
            "position": {my: "center", at: "center", of: window}
        }).dialog("open");
    }
    /**提交审核操作**/
    function submit(id) {
        $("#materialId").val(id);
        $("#dialog-confirm").dialog("open");
    }
    /**
     *  扩展Array原型prototype 原数组函数并不支持
     *  方法:Array.remove(dx) 通过遍历,重构数组
     *  功能:删除数组元素.
     *  参数:dx删除元素的下标.
     */
    /* Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
     };*/
    /* Array.prototype.remove=function(obj){
     for(var i =0;i <this.length;i++){
     var temp = this[i];
     if(!isNaN(obj)){
     temp=i;
     }
     if(temp == obj){
     for(var j = i;j <this.length;j++){
     this[j]=this[j+1];
     }
     this.length = this.length-1;
     }
     }
     };*/
    /**批量删除按键**/
    function deleteSel(id) {
        /**id数组**/
        var rowData;
        if (id) {
            rowData = [id];
        } else {
            rowData = jQuery('#gridTable').jqGrid('getGridParam', 'selarrrow');
            console.log('sss' + rowData);
        }
        if (rowData.length) {
            var id, updateTime, status, materialUrl;
            /**ID数组**/
            var ids = new Array();
            /**updateTime数组**/
            var updateTimes = new Array();
            /**状态数组**/
            var statusArray = new Array();
            /**素材url数组**/
            var materialUrls = new Array();
            /**不可以删除的数组下标数组**/
            //var removeFlag = new Array();
            for (var i = 0; i < rowData.length; i++) {
                id = jQuery('#gridTable').jqGrid('getCell', rowData[i], 'id');//name是colModel中的一属性
                updateTime = jQuery('#gridTable').jqGrid('getCell', rowData[i], 'updateTimeStr');
                status = jQuery('#gridTable').jqGrid('getCell', rowData[i], 'status');
                /**如果素材处于待审核或者审核通过的状态 则不可以被删除**/
                if (status == '待审核' || status == '审核通过') {
                    //removeFlag[i] = i;
                    showWaittingMsg("待审核的和审核通过的素材不可以删除", 2000);
                    return;
                }
                materialUrl = jQuery('#gridTable').jqGrid('getCell', rowData[i], 'materialUrl');
                statusArray[i] = status;
                materialUrls[i] = materialUrl;
                ids[i] = id;
                updateTimes[i] = updateTime;
            }
            /**如果下标数组不为空 则移除其他数组对应下标的元素**/
            /*if (removeFlag.length > 0) {
                for (var i = 0; i < removeFlag.length; i++) {
                    materialUrls.remove(removeFlag[i]);
                    ids.remove(removeFlag[i]);
                    updateTimes.remove(removeFlag[i]);
                }
             }*/
            if (ids.length) {
                showMsg("你确定要删除这" + ids.length + "条素材数据?", 2, function () {
                    $.ajax({
                        type: 'post',
                        url: '${ctx}/systemManage/material/delMaterial.do',
                        data: {
                            'ids': ids.toString(),
                            'updateTimes': updateTimes.toString(),
                            'materialUrls': materialUrls.toString()
                        },
                        dataType: 'json',
                        success: function (data) {
                            var msg = data.msg;
                            refresh();
                            showWaittingMsg(msg, 2000);
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

    /*搜索*/
    function search() {
        var materialName = $("#searchName").val();
        //要提示的内容
        /* var str = "
        <spring:message code='errors.maxlength' arguments='10,字符串' />";
        if (materialName.length > 10) {
            showErrMsg("searchName", str);
            return;
        }
        //隐藏信息
         hideErrMsg('searchName');*/
        var type = $("#roleSel").attr("roleSelValue");
        var status = $("#statusSel").attr("statusSelValue");
        $("#gridTable").jqGrid('setGridParam', {
            datatype: 'json',
            page: '1',
            postData: {"materialName": materialName, "materialType": type, "status": status}
        }).trigger('reloadGrid');
    }
    /**清除dialog内容**/
    function clearDiv() {
        $("#clearData").click();
        $(".uploadMess").html("");
        $("#imgView").attr('src', "");
        $(".plcsize").html("");
        $("#cmaterialUrl").val('');
        $("#material_error").html('');
        $("#materialId").val('');
        $("#signupForm").attr("action", "${ctx}/systemManage/material/saveMaterial.do")
    }
</script>

