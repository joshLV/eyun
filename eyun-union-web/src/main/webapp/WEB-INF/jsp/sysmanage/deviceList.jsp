<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/jsp/taglib.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">

    <div class="right_c_tab">
        <!-- grid对应table设置 -->
        <table id="gridTable"></table>
        <div id="gridPager"></div>
        <!-- end -->
    </div>
<%--设备详细信息弹出框--%>
<div id="dialog" title="设备资料详情" style="display: none;width:601px">
    <table width="300" cellpadding="0" cellspacing="0" class="table table-hover table-bordered">
        <tr>
            <td colspan="2"><label id="materialTitle">设备信息</label></td>
        </tr>
<div class="lm_add">
    <tr>
        <td>场所名称：</td>
        <td><span id="placeName"></span></td>
    </tr>
    <tr>
        <td>产品编号：</td>
        <td><span id="hardwareID"></span></td>
    </tr>
    <tr>
        <td>序列号：</td>
        <td><span id="serialNum"></span></td>
    </tr>

    <tr>
        <td>注册时间：</td>
        <td><span id="registerTime"></span></td>
    </tr>
    <tr>
        <td>过期时间：</td>
        <td><span id="validTime"></span></td>
    </tr>
    <tr>
        <td>备注：</td>
        <td><div id="remarks" class="input_text" style="margin: 2px; width: 200px; height: 94px;resize:none;"
                      readonly></div></td>
    </tr>
    </table>
</div>
<script type="text/javascript">
    $(function () {
        // 选中场所ID取得
        var placeCode = $('#selPlaceCode').attr('placeCode');
        //grid加载
        $("#gridTable").jqGrid({
            url: '${ctx}/systemManage/device/bySearch.do?placeCode=' + placeCode,
            height: 'auto',
            forceFit: true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
            altRows: true,
            colNames: ['产品编号', '序列号', '场所名称', '注册时间', '过期时间', '状态', '操作', 'id', '备注'],
            colModel: [
                {name: 'hardwareID', index: 'hardwareID', align: 'center', sorttype: "string", width: '10'},
                {name: 'serial_num', index: 'serialNum', align: 'center', sorttype: "string", width: '20'},
                {name: 'placeName', index: 'placeName', align: 'center', width: '10'},
                {name: 'registerTime', index: 'registerTime', align: 'center', width: '10'},
                {name: 'validTime', index: 'validTime', align: 'center', width: '10'},
                {name: 'status', index: 'status', sorttype: "string", align: 'center', width: '10'},
                {name: 'operate', index: 'operate', sortable: false, align: 'center', width: '10'},
                {name: 'id', index: 'id', hidden: true},
                {name: 'remark', index: 'remarks', hidden: true},
            ],
            //sortname: 'register_Time',
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
                    jQuery("#gridTable").jqGrid('setRowData', ids[i], {operate: viewHtml});
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
            multiselect: false   // 复选框设置
        }).navGrid('#gridPager', {edit: false, add: false, del: false, search: false, refresh: false})
    });

    // grid自适应
    $(window).resize(function () {
        $("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
    });

    /**通过场所id获取场所信息**/
    function view(id) {
        var rowData = $("#gridTable").jqGrid("getRowData", id);
        $("#placeName").html(rowData.placeName);
        $("#hardwareID").html(rowData.hardwareID);
        $("#serialNum").html(rowData.serial_num);
        $("#registerTime").html(rowData.registerTime);
        $("#validTime").html(rowData.validTime);
        $("#remarks").val(rowData.remark);
        $("#dialog").dialog({
            height: 400,
            width: 450,
            modal: true
        });
    }
</script>
