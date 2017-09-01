<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/jsp/taglib.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">

    <div class="right_c_tab">
        <!-- grid对应table设置 -->
        <table id="gridTable"></table>
        <div id="gridPager"></div>
        <!-- end -->
    </div>

<%--<div id="dialog" title="场所资料详情" style="display: none;width:501px">
    <table width="300" cellpadding="0" cellspacing="0" class="PublicTable">--%>
<div id="dialog" title="场所资料详情" style="display: none">
    <table width="300" cellpadding="0" cellspacing="0" class="table table-hover table-bordered">
        <tr>
            <td>场所名称：</td>
            <td><span id="placeName"></span></td>
        </tr>
        <tr>
            <td>场所类型</td>
            <td><span id="placeTypeName"></span></td>
        </tr>
        <tr>
            <td>联系人：</td>
            <td><span id="contact"></span></td>
        </tr>
        <tr>
            <td>联系电话(固话)：</td>
            <td><span id="tel"></span></td>
        </tr>
        <tr>
            <td>手机：</td>
            <td><span id="mobile"></span></td>
        </tr>
        <tr>
            <td>Email：</td>
            <td><span id="email"></span></td>
        </tr>

        <tr>
            <td>所在地区：</td>
            <td><span id="areaName"></span></td>
        </tr>
        <tr>
            <td>详细地址：</td>
            <td><span id="addr"></span></td>
        </tr>
        <tr>
            <td>备注：</td>
            <td><div id="remarks" readonly style="margin: 2px; width:200px;height: 73px;resize: none">
				 </div></td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    $(function () {
        // 选中场所ID取得
        var placeCode = $('#selPlaceCode').attr('placeCode');
        //grid加载
        $("#gridTable").jqGrid({
            url: '${ctx}/systemManage/place/bySearch.do?placeCode=' + placeCode,
            height: 'auto',
            forceFit: true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
            altRows: true,
            colNames: ['场所名称', '联系人', '联系方式', '地址', '所在地区', '场所类型', '状态', '操作', 'id', '电话号码', 'email', '备注'],
            colModel: [
                {name: 'placeName', index: 'placeName', align: 'center', width: '20'},
                {name: 'contact', index: 'contact', align: 'center', width: '10'},
                {name: 'mobile', index: 'mobile', align: 'center', width: '10'},
                {name: 'addr', index: 'addr', align: 'center', width: '20'},
                {name: 'areaName', index: 'areaName', align: 'center', width: '20'},
                {name: 'placeTypeName', index: 'placeTypeName', align: 'center', width: '10'},
                {name: 'status', index: 'status', sorttype: "string", align: 'center', width: '10'},
                {name: 'operate', index: 'operate', sortable: false, align: 'center', width: '10'},
                {name: 'id', index: 'id', hidden: true},
                {name: 'tel', index: 'tel', hidden: true},
                {name: 'email', index: 'email', hidden: true},
                {name: 'remark', index: 'remarks', hidden: true}
// 	      {name:'updatetime',index:'updatetime',hidden:true}
            ],
            rowNum: 10,
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
            /*.navButtonAdd('#gridPager',{
             caption:"",
             onClickButton: function(){
             //deleteSel();
             },
             position:"last"
             })*/;
        // 场所列表Change事件
        $("#barid").change(function () {
            // 选中场所ID取得
            var barid = $('#barid').val();
            // grid重新加载
            $("#gridTable").jqGrid('setGridParam', {
                datatype: 'json',
                page: 1,
                postData: {'goodsCategory.barid': barid}
            }).trigger('reloadGrid');
        });
    });

    // grid自适应
    $(window).resize(function () {
        $("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
    });

    /**通过场所id获取场所信息**/
    function view(id) {
        var rowData = $("#gridTable").jqGrid("getRowData", id);
        $("#placeName").html(rowData.placeName);
        $("#placeTypeName").html(rowData.placeTypeName);
        $("#contact").html(rowData.contact);
        $("#tel").html(rowData.tel);
        $("#mobile").html(rowData.mobile);
        $("#email").html(rowData.email);
        $("#areaName").html(rowData.areaName);
        $("#addr").html(rowData.addr);
        $("#remarks").val(rowData.remark);
        console.log(rowData.remark)
        $("#dialog").dialog({
            height: 440,
            width: 500,
            modal: true
        });
    }
</script>

</html>
