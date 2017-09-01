<%--
  Created by IntelliJ IDEA.
  User: sun
  Date: 2016/9/23
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/jsp/taglib.jsp" %>
<html>
<head>
    <title>旺旺币优惠策略</title>
    <script type="text/javascript" src="${ctx}/common/scripts/area.js"></script>
    <script type="text/javascript" src="${ctx}/common/scripts/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="${ctx}/common/scripts/jquery-ui-timepicker-addon.min.js"></script>
    <link rel="stylesheet" href="${ctx}/common/css/jquery-ui-timepicker-addon.min.css"/>
</head>
<body>
<div>
    <input type="hidden" id="writePower" value="${writePower}" />
    <input type="hidden" id="examinePower" value="${examinePower}" />
    <input type="hidden" id="releasePower" value="${releasePower}" />
    <input type="hidden" id="delegatePower" value="${delegatePower}" />
    <input type="hidden" id="lockPower" value="${lockPower}" />
    <input type="hidden" id="recommendPower" value="${recommendPower}" />
</div>
<%--搜索区域 part--%>
<form>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">
    <tr>
        <td align="right" width="8%">选择区域：</td>
        <td width="35%">
            <div style="width: 320px" class="PublicSelect hack PublicText" id="areaId">
            </div>
        </td>
        <td width="60px" align="right">
            活动状态:&nbsp;&nbsp;
        </td>
        <td>
            <%--<div class="PublicTime" style="display:inline-block"><!--style="display:inline-block"  -->
                <input type="text" style="width:150px" class="PublicText TextTime" name="dateLeft">
                <u>至</u>
                <input type="text" style="width:150px" class="PublicText TextTime" name="dateRight">
            </div>--%>
            <select style="display:inline-block" id="validStatus">
                <option value="1">已生效</option>
                <option value="0">已过期</option>
            </select>
        </td>
        <td width="60px" align="right">
            状态:&nbsp;&nbsp;
        </td>
        <td>
            <select style="display:inline-block" id="status">
                <option value="">全部</option>
                <option value="0">未审核</option>
                <option value="8">已审核</option>

            </select>
        </td>
        <td colspan="2">
            <input type="hidden" id="strategyId">
            <input type="button" class="PublicButton" value="查 询" onclick="searchStrategy();">
            <input type="reset" class="PublicButton" value="重置">
        </td>
    </td>

    </tr>
</table>
</form>
<div class="PublicC_t"><%--表格右上角 新增按钮--%>
    <ul class="operation fr" id="functionDiv"></ul>
</div>
<div class="right_c_tab">
    <!-- grid对应table设置 -->
    <table id="gridTable"></table>
    <div id="gridPager"></div>
    <!-- end -->
</div>

<%--弹出层--%>
<div id="dialogInfo" title="赠送旺旺币" style="display: none;width:601px">
    <form id="myform">
        <table width="100%" cellpadding="0" cellspacing="0" class="PublicTable">
            <tr>
                <td width="19%">选择区域：</td>
                <td width="80%">
                    <div style="width: 320px" class="PublicSelect hack PublicText" id="areaId2">
                    </div>
                </td>
            </tr>
            <tr>
                <td>购买个数：</td>
                <td><input type="text" id="buyWwb" placeholder="" class="PublicText"
                           onkeyup="this.value=this.value.replace(/\D/g,'')"
                           onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="5"></td>
            </tr>
            <tr>
                <td>赠送个数：</td>
                <td><input type="text" id="presentWwb" placeholder="" class="PublicText"
                           onkeyup="this.value=this.value.replace(/\D/g,'')"
                           onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="5"></td>
            </tr>
            <%--<tr>
                <td>购买次数：</td>
                <td><input type="text" id="useTimes" placeholder="" class="PublicText"
                           onkeyup="this.value=this.value.replace(/\D/g,'')"
                           onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="5"></td>
            </tr>--%>
            <tr>
                <td>有效时间：</td>
                <td>
                    <div class="PublicTime" style="display:inline-block"><!--style="display:inline-block"  -->
                        <input type="text" style="width:150px" class="PublicText TextTime" name="dateLeft" id="startTime">
                        <u>至</u>
                        <input type="text" style="width:150px" class="PublicText TextTime" name="dateRight" id="endTime">
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
<script type="text/javascript">
    //初始化
    $(function () {
        areaInit("-1");//初始化区域
        defaultDates();//初始化日期
        // 加载日期插件
        $("input[name='dateLeft']").datetimepicker();
        //$("#dateLeft").datetimepicker();
        //$("#dateRight").datetimepicker();
        $("input[name='dateRight']").datetimepicker();
        $("#dialogInfo").dialog({
            autoOpen: false,
            height: 320,
            width: 600,
            modal: true,
            buttons: {
                "保存": function () {
                    optStrategy();
                },
                "取消": function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                $("#areaId2").empty();
                $("#strategyId").val('')
                areaInit("-1");//初始化区域
                $("#myform")[0].reset();
            }
        });
        /*添加按钮*/
        $("#functionDiv").append(addHtm);
        $("#addFunc").click(function () {
            $("#areaId").empty();
            areaInit2("-1");
            $("#dialogInfo").dialog("option", {
                "title": "新增旺旺币策略",
                "position": {my: "center", at: "center", of: window}
            }).dialog("open");
        });
        var province = $("#loadProvince").val();
        var city = $("#loadCity").val();
        var county = $("#loadCounty").val();
        var refCode = '';
        if(county !='-1') {
            refCode = county;
        }
        else if(county=='-1' && city!='-1') {
            refCode = city.substring(0, 4);
        }else{
            if(province!='-1') {
                refCode = province.substring(0, 2);
            }
        }
        /*var startTime = $("input[name='dateLeft']").val();
        var endTime = $("input[name='dateRight']").val();*/
        var status = $("#status").val();

        var validStatus = $("#validStatus").val();
        /*加载表格*/
        $("#gridTable").jqGrid({
            url: '${ctx}/promotions/strategyList.do',
            postData : {"refCode": refCode,"validStatus":validStatus,"status":status},
            height: 'auto',
            forceFit: true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
            altRows: true,
            colNames: ['优惠区域', '购买旺旺币(个)', '赠送旺旺币(个)', /*'次数限制',*/ '开始时间', '结束时间', '状态','操作', 'id'],
            colModel: [
                {name: 'refCodeName', index: 'ref_Code', align: 'center', sorttype: "string", width: '30'},
                {name: 'buyWwb', index: 'buy_Wwb', align: 'center', width: '20'},
                {name: 'presentWwb', index: 'present_Wwb', align: 'center', width: '20'},
                //{name: 'useTimes', index: 'use_Times', align: 'center', width: '10'},
                {name: 'startTime', index: 'start_Time', sorttype: "string", align: 'center', width: '20'},
                {name: 'endTime', index: 'end_Time', sorttype: "string", align: 'center', width: '20'},
                {name: 'status', index: 'status', sorttype: "string", align: 'center', width: '10'},
                {name: 'operate', index: 'operate', sortable: false, align: 'center', width: '10'},
                {name: 'id', index: 'id', hidden: true}
            ],
            rowList: ${rowList},
            sortname: 'create_time',
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
                    var rowData = $("#gridTable").jqGrid("getRowData", ids[i]);
                    var editHtml = '', commitHtml = '';
                    if(rowData.status =='未审核') {
                        editHtml = preUpdHtm + "onclick='editStrategy(" + ids[i] + ")'" + sufUpdHtm;
                        commitHtml = preAuditHtm+" onclick='submitStrategy(" + ids[i] + ")' " + sufAuditHtm;
                    }
                    jQuery("#gridTable").jqGrid('setRowData', ids[i], {operate: editHtml+commitHtml}, {height: 35});
                }
                $("body").append("<script type='text/javascript' src='${ctx}/scripts/authority.js'><\/script>");

            },
            loadComplete: function (xhr) {
                $("#gridTable").jqGrid('setGridWidth', $(".right_c").innerWidth());
                var datas = xhr.dataRows;
                for (var i = 0; i < datas.length; i++) {
                    datas[i].operate = "<input type='button' onclick='edit(" + datas[i].id + ")' class='btn_img_update'/><span style='margin-right: 8px;'></span>";
                }
            },
            pager: "#gridPager"
        }).navGrid('#gridPager', {edit: false, add: false, del: false, search: false, refresh: false});
    });

    //初始化地区
    function areaInit(defaultSelects) {
        var areaArray = [];
        areaArray["appendSelectors"] = "areaId";
        areaArray["selectors"] = "loadProvince,loadCity,loadCounty";
        areaArray["styles"] = "width:80px,width:80px,width:80px";
        areaArray["defaultSelects"] = defaultSelects;
        initArea(areaArray);
    }
    function areaInit2(defaultSelects) {
        var areaArray = [];
        areaArray["appendSelectors"] = "areaId2";
        areaArray["selectors"] = "loadProvince,loadCity,loadCounty";
        areaArray["styles"] = "width:80px,width:80px,width:80px";
        areaArray["defaultSelects"] = defaultSelects;
        initArea(areaArray);
    }
    /* 设置默认日期时间 */
    function defaultDates() {
        /*当前日期*/
        var nos = new Date();
        /*日期格式化*/
        var no = formatDate(nos);
        $("input[name='dateLeft']").val(no + " 00:00");
        var endTime = new Date();
        endTime.setMonth(endTime.getMonth() + 1);
        var no2 = formatDate(endTime);
        $("input[name='dateRight']").val(no2 + " 23:59");
    }
    function formatDate(date) {
        var myyear = date.getFullYear();
        var mymonth = date.getMonth() + 1;
        var myweekday = date.getDate();
        if (mymonth < 10) {
            mymonth = "0" + mymonth;
        }
        if (myweekday < 10) {
            myweekday = "0" + myweekday;
        }
        return (myyear + "-" + mymonth + "-" + myweekday);
    }

    /**编辑策略操作**/
    function editStrategy(id) {
        $("#strategyId").val(id);
        var rowData = $("#gridTable").jqGrid("getRowData", id);
        //alert(rowData.refCodeName);
        $("#areaId2").html(rowData.refCodeName);
        $("#buyWwb").val(rowData.buyWwb);
        $("#presentWwb").val(rowData.presentWwb);
        //$("#useTimes").val(rowData.useTimes);
        $("#startTime").val(rowData.startTime);
        $("#endTime").val(rowData.endTime);
        $("#dialogInfo").dialog("option", {
            "title": "编辑旺旺币策略",
            "position": {my: "center", at: "center", of: window}
        }).dialog("open");
    }

    function optStrategy() {
        var id = $("#strategyId").val();
        var url = '${ctx}/promotions/insertStrategy.do';
        var refCode = '';
        if(id){
            url = '${ctx}/promotions/updateStrategy.do';
        }else{
            //refCode loadProvince loadCity,loadCounty

            var loadProvince = $('#loadProvince').val();
            var loadCity = $('#loadCity').val();
            var loadCounty = $('#loadCounty').val();
            if(loadCounty!='-1'&&loadCity!='-1'&&loadProvince!='-1') {
                refCode = loadCounty;
            }
            if(loadProvince!='-1'&&loadCity!='-1'&&loadCounty=='-1') {
                refCode = loadCity;
            }
            if(loadProvince!='-1'&&loadCity=='-1'&&loadCounty=='-1') {
                refCode = loadProvince;
            }
            if(loadProvince=='-1') {
                showMsg("请选择区域");
                return;
            }
        }
        var buyWwb = $("#buyWwb").val();

        var presentWwb = $("#presentWwb").val();
        //var useTimes = $("#useTimes").val();
        /*if(buyWwb==''||presentWwb==''||useTimes=='') {
            showMsg("请输入大于0的数字");
            return;
        }*/
        if(buyWwb==''||presentWwb=='') {
            showMsg("请输入大于0的数字");
            return;
        }
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        if(!checks(0)) {
            return;
        }
        var params = {id:id,refCode:refCode,buyWwb:buyWwb,presentWwb:presentWwb,useTimes:0,startTime:startTime,endTime:endTime};

        $.post(url,params,function(result){
            $("#dialogInfo").dialog("close");//关闭对话框
            $("#gridTable").trigger('reloadGrid');//重新加载表格
        });
    }

    function searchStrategy() {
        /*if(!checks(0)) {
            return;
        }*/
        var province = $("#loadProvince").val();
        var city = $("#loadCity").val();
        var county = $("#loadCounty").val();
        var refCode = '';
        if(county !='-1') {
            refCode = county;
        }
        else if(county=='-1' && city!='-1') {
            refCode = city.substring(0, 4);
        }else{
            if(province!='-1') {
                refCode = province.substring(0, 2);
            }
        }
        /*var startTime = $("input[name='dateLeft']:eq(0)").val();
        var endTime = $("input[name='dateRight']:eq(0)").val();*/
        var validStatus = $("#validStatus").val();
        var status = $("#status").val();
        $("#gridTable").jqGrid('setGridParam', {
            datatype: 'json',
            page: '1',
            postData: {"refCode": refCode,"validStatus":validStatus,"status":status}
        }).trigger('reloadGrid');
    }

    function checks(i){
        //var startTime = $("input[name='dateLeft']:eq()").val();
        var startTime = $("input[name='dateLeft']")[i].value;//dom
        var endTime = $("input[name='dateRight']")[i].value;
        if(startTime=='' || endTime ==''){
            showWaittingMsg("请选择起止日期！", 4000);
            return false;
        }
        startTime = startTime.replace(/-/g,"/");
        endTime = endTime.replace(/-/g,"/");
        var bTime = new Date(startTime);
        var eTime = new Date(endTime);
        if(bTime > eTime){
            showWaittingMsg("起始日期不能大于截止日期！", 4000);
            return false;
        }
        return true;
    }
    function submitStrategy(id) {
        showMsg("确认审核通过", 2, function () {
            $.ajax({
                type: 'post',
                url: '${ctx}/promotions/commitStrategy.do',
                data: {
                    'id': id
                },
                dataType: 'json',
                success: function (data) {
                    /*/ar msg = data.msg;
                     showWaittingMsg(msg, 2000);*/
                    $("#dialog").dialog('close');
                    refresh();
                }
            });
        });
    }
</script>
</html>
