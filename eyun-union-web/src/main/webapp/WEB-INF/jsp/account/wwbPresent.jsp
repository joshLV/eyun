<%--
  Created by IntelliJ IDEA.
  User: sun
  Date: 2016/9/20
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/jsp/taglib.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <script type="text/javascript" src="${ctx}/common/scripts/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="${ctx}/common/scripts/jquery-ui-timepicker-addon.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/sysmanage/uploadPreview.js"></script>
    <script type="text/javascript" src="${ctx}/common/scripts/jquery.form.js"></script>
    <link rel="stylesheet" href="${ctx}/common/css/jquery-ui-timepicker-addon.min.css"/>
    <style type="text/css">
        .img-show {
            width: 200px;
            height: 121px;
            /*display: none;*/
        }
        /*美化input-file*/
        .file {
            position: relative;
            display: inline-block;
            background: #D0EEFF;
            border: 1px solid #99D3F5;
            border-radius: 4px;
            padding: 4px 12px;
            overflow: hidden;
            color: #1E88C7;
            text-decoration: none;
            text-indent: 0;
            line-height: 20px;
        }

        .file input {
            position: absolute;
            font-size: 100px;
            right: 0;
            top: 0;
            opacity: 0;
        }

        .file:hover {
            background: #AADFFD;
            border-color: #78C3F3;
            color: #004974;
            text-decoration: none;
        }
    </style>
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
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="PublicTable">
  <tr>
    <td align="right" width="8%">场所名称：</td>
    <td width="15%"><input type="text" class="PublicText" id="searchName"/></td>
      <td align="right" width="8%">操作时间：</td>
      <td>
          <div class="PublicTime" style="display:inline-block"><!--style="display:inline-block"  -->
              <input type="text" style="width:150px" class="PublicText TextTime" name="dateLeft">
              <u>至</u>
              <input type="text" style="width:150px" class="PublicText TextTime" name="dateRight">
          </div>
      </td>
      <td width="60px" align="right">
          状态:&nbsp;&nbsp;
      </td>
      <td>
          <select style="display:inline-block" id="status">
              <option value="">全部</option>
              <option value="N">未审核</option>
              <option value="Y">已审核</option>
              <option value="8">已赠送</option>
          </select>
      </td>
    <td colspan="2">
      <input type="button" class="PublicButton" value="查 询" onclick="search();">
      <input type="reset" class="PublicButton" value="重 置" onclick="resetFun()">
    </td>
  </tr>
</table>
<%--表格 part--%>
<div class="PublicC_t"><%--表格右上角 新增按钮--%>
  <ul class="operation fr" id="functionDiv"></ul>
</div>
<div class="right_c_tab">
  <!-- grid对应table设置 -->
  <table id="gridTable"></table>
  <div id="gridPager"></div>
  <!-- end -->
</div>
<%--dialog 弹出层--%>
<div id="dialogInfo" title="旺旺币个数" style="display: none;width:601px">
    <form id="myform" action="${ctx}/promotions/presentWwb.do" method="post" enctype="multipart/form-data">
        <input type="hidden"  id="presentId" name="id">
        <%--<input type="hidden"  id="certificate" name="certificate">--%>
        <table width="100%" cellpadding="0" cellspacing="0" class="table table-hover table-bordered">
        <tr>
            <td width="19%" >条件检索：</td>
            <td width="80%"><input type="text" name="eyName" id="eyName" class="PublicText" maxlength="30" placeholder="支持易盟号、场所名称、场所编号、设备序列号检索" autofocus="true"></td>
        </tr>
        <tr>
            <td width="19%" >选择场所：</td>
            <td width="80%">
                <select style="width: 250px" id="place" onchange="getDevice(this)">
                    <option value="-1">--请选择--</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>选择设备：</td>
            <td>
                <select style="width: 250px" id="device" name="placeDeviceId">
                    <option value="-1">--请选择--</option>
                </select>
            </td>
        </tr>
        <%--新增的功能 包含现金充值--%>
        <tr>
            <td>操作类型：</td>
            <td>
                <select id="payChannel" onchange="operateRow(this)" name="payChannel">
                    <option value="-1">--请选择--</option>
                    <option value="4">充值</option>
                    <option value="9">赠送</option>
                </select>
            </td>
        </tr>
        <tr id="chargeRow" style="display:none">
            <td>充值金额：</td>
            <td><input type="text" id="rmbFee" name="rmbFee" placeholder="请输入充值的金额" class="PublicText"  maxlength="8" >&nbsp;元</td>
        </tr>
        <tr>
            <td>赠送个数：</td>
            <td><input type="text" id="presentNum" name="wwbFee" placeholder="请输入要赠送的旺旺币数目" class="PublicText" maxlength="8" ></td>
        </tr>
        <tr>
            <td>凭证上传：</td>
            <td colspan="2">
                <div>
                    <a href="javascript:;" class="file">选择文件<input type="file" name="imgFile" id="fileField"
                                                                   class="file" size="24"/></a>
                    <%--上传状态提示--%>
                    <span class="uploadMess"></span>
                </div>
                <div id="imgdiv" class="mg-l-20">
                    <img id="imgView" class="img-show" src="" alt="上传的图片大小不可以大于10M"/>
                </div>
            </td>
        </tr>
        <tr>
            <td>备注：</td>
            <td><textarea id="remark" name="remark" style="margin: 0px; width: 427px; height: 50px;"></textarea></td>
        </tr>
    </table>
    </form>
</div>
<%--编辑页面--%>
<div id="editDialogInfo" title="编辑充送旺旺币" style="display: none;width:601px">
    <form id="myForm1" action="${ctx}/promotions/editPresentWwb.do" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id">
        <input type="hidden" name="certificate" id="certificate">
        <input type="hidden" name="payChannel" id="payChannel2">
        <table width="100%" cellpadding="0" cellspacing="0" class="table table-hover table-bordered">
            <tr>
                <td width="19%" >场所：</td>
                <td width="80%">
                    <span id="placeSpan"></span>
                </td>
            </tr>
            <tr>
                <td>设备：</td>
                <td>
                    <span id="deviceSpan"></span>
                </td>
            </tr>
            <tr id="chargeRow2" style="display:none">
                <td>充值金额：</td>
                <td><input type="text" id="rmbFee2" name="rmbFee" placeholder="请输入充值的金额" class="PublicText"  maxlength="8" onkeyup="this.value=this.value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')"  onafterpaste="this.value=this.value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')"></td>
            </tr>
            <tr>
                <td>赠送个数：</td>
                <td><input type="text" id="editPresentNum" name="wwbFee" placeholder="请输入要赠送的旺旺币数目" class="PublicText" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="5" ></td>
            </tr>
            <tr>
                <td>凭证上传：</td>
                <td colspan="2">
                    <div>
                        <a href="javascript:;" class="file">选择文件<input type="file" name="imgFile" id="fileField2"
                                                                       class="file" size="24"/></a>
                        <%--上传状态提示--%>
                        <span class="uploadMess"></span>
                    </div>
                    <div id="imgdiv2" class="mg-l-20">
                        <img id="imgView2" class="img-show" src="" alt="上传的图片大小不可以大于10M"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>备注：</td>
                <td><textarea id="editRemark" name="remark" style="margin: 0px; width: 444px; height: 68px;"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-confirm" title="审核" style="text-align:center">
    <p>
        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0"></span>确认审核通过
    </p>
</div>
</body>
<%--js part--%>
<script type="text/javascript">
  $(function () {
      // 点击图片可以查看
      $("img").bind("click",function() {
          if($(this).attr("src")!='') {
              window.open($(this).attr("src"));
          }
      });
      uploadFile();
      defaultDates();//初始化日期
      // 加载日期插件
      $("input[name='dateLeft']").datetimepicker();
      $("input[name='dateRight']").datetimepicker();
      // 初始化审核窗口
      $("#dialog-confirm").dialog({
          autoOpen: false,
          resizable: false,
          height: 150,
          modal: true,
          buttons: {
              "确定": function () {
                  var id =$("#presentId").val();
                  $.ajax({
                      //async: false,
                      cache: true,
                      type: "POST",
                      url: '${ctx}/promotions/commitPresentWwb.do',
                      data: {id: id/*, status: 'Y'*/},
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

      $("#dialogInfo").dialog({
          autoOpen: false,
          height: 600,
          width: 600,
          modal: true,
          buttons: {
              "保存": function () {
                  present();
              },
              "取消": function () {
                  $(this).dialog("close");
              }
          },
          close: function () {
              $("#myform")[0].reset();
              $("#place").empty().append("<option value='-1'>--请选择--</option>");
              $("#device").empty().append("<option value='-1'>--请选择--</option>");
          }
      });
      $("#editDialogInfo").dialog({
          autoOpen: false,
          height: 545,
          width: 600,
          modal: true,
          buttons: {
              "保存": function () {
                  // todo 编辑前要验证
                  //var id =$("#presentId").val();
                  var id = $("input[name='id']:eq(1)").val();//编辑
                  var wwbFee = parseFloat($("#editPresentNum").val());
                  var editRemark = $("#editRemark").val();
                  var rmbFee = parseFloat($("#rmbFee2").val());
                  if(isNaN(rmbFee) || rmbFee<0) {
                      showMsg("请输入正确的充值金额");
                      return;
                  }
                  if(isNaN(wwbFee) || wwbFee<0) {
                      showMsg("请输入正确的旺旺币数目");
                      return;
                  }
                  /*$.post("${ctx}/promotions/editPresentWwb.do",{id:id,wwbFee:wwbFee,remark:editRemark,rmbFee:rmbFee},function(result){
                      $("#editDialogInfo").dialog("close");//关闭对话框
                  });*/
                  $("#myForm1").ajaxSubmit(function (data) {
                      //var result = $.evalJSON(responseResult);
                      if (data >= 0) {
                          $("#editDialogInfo").dialog('close');
                          refresh();
                      } else if(data="-2"){
                          showMsg("凭证上传失败 请重新选择图片");
                      }else if(data="-3"){
                          showMsg("上传凭证图片大小不可以大于10M");
                      }else {
                          showMsg("操作失败");

                      }
                  });
                  //refresh();
              },
              "取消": function () {
                  $(this).dialog("close");
              }
          },
          close: function () {
              //dosomething
          }
      });

      /*添加按钮*/
    $("#functionDiv").append(addHtm);
    $("#addFunc").click(function () {
        $("#imgView").attr('src', "");
        $("#dialogInfo").dialog("option", {
          "title": "充送旺旺币",
          "position": {my: "center", at: "center", of: window}
      }).dialog("open");
    });
      var startTime = $("input[name='dateLeft']:eq(0)").val();
      var endTime = $("input[name='dateRight']:eq(0)").val();
      var status = $("#status").val();
    /*加载表格*/
    $("#gridTable").jqGrid({
      url: '${ctx}/promotions/bySearch.do',
      postData : {'startTime' : startTime,"endTime":endTime,"status":status,"sord":"desc"},
      height: 'auto',
      forceFit: true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
      altRows: true,
      colNames: ['场所名称', '设备序列号', '充值金额','赠送旺旺币', '操作时间','类型', '状态','操作', 'id','备注','payChannel','certificate'],
      colModel: [
        {name: 'placeName', index: 'placeName', align: 'center', sorttype: "string", width: '20'},
        {name: 'serialNum', index: 'serialNum', align: 'center', width: '20'},
        {name: 'rmbFee', index: 'rmbFee', align: 'center', width: '10'},
        {name: 'wwbFee', index: 'wwbFee', align: 'center', width: '10'},
        {name: 'presentTime', index: 'opttime', sorttype: "string", align: 'center', width: '15'},
        {name: 'channelName', align: 'center', width: '10',sortable: false},
        {name: 'status', index: 'status', sorttype: "string", align: 'center', width: '10'},
        {name: 'operate', index: 'operate', sortable: false, align: 'center', width: '15'},
        {name: 'id', index: 'id', hidden: true},
        {name: 'remark', index: 'remark', hidden: true},
        {name: 'payChannel', index: 'payChannel', hidden: true},
        {name: 'certificate', index: 'certificate', hidden: true}
      ],
      sortorder:"desc",
      sortname:"opttime",
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
            for(var i=0;i<ids.length;i++){
                var commitHtml = '', delHtml = '', editHtml = '',delegateHtml='',viewHtml='';
                var rowData = $("#gridTable").jqGrid("getRowData", ids[i]);
                viewHtml = prePreviewHtm + " onclick='viewCertificate(\"" + rowData.certificate + "\")' " + sufPreviewHtm;
                if(rowData.status =='未审核') {
                    //viewHtml = prePreviewHtm + " onclick='viewCertificate(\"" + rowData.certificate + "\")' " + sufPreviewHtm;
                    editHtml = preUpdHtm + " onclick='editPresent(" + ids[i] + ","+rowData.payChannel+")' " + sufUpdHtm;
                    delHtml = preDelHtm + " onclick='deletePresent(" + ids[i] + ")' " + sufDelHtm;
                    commitHtml = preAuditHtm+" onclick='submitWwb(" + ids[i] + ")' " + sufAuditHtm;
                }
                if(rowData.status =='已审核') {
                    delegateHtml=preDelegateHtm + " onclick='giftWwb(" + ids[i] + ")' " + sufDelegateHtm;
                }

                jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:viewHtml+editHtml + commitHtml + delHtml+delegateHtml});
            }
          $("body").append("<script type='text/javascript' src='${ctx}/scripts/authority.js'><\/script>");
          //jQuery("#gridTable").jqGrid('setRowData', ids[i], {operate: viewHtml}, {height: 35});
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
      $("#eyName").blur(function(){//keyup 改blur适应中文，因为keyup输入中文会直接拿拼音去查
          console.log("***********************************keyup");
          //$("#eyName").css("background-color","#D6D6FF");
          var eyName = this.value.trim();
          //
          if(eyName == '') {//|| eyName.length<6 考虑有中文名字
              $("#place").empty().append("<option value='-1'>--请选择--</option>");
              $("#device").empty().append("<option value='-1'>--请选择--</option>");
              return;
          }
          $.getJSON( "${ctx}/promotions/getPlace.do", { eyName: encodeURI(eyName)}, function(data){
              if(data.place.length>0) {
                  var json =data.place;
                  var placeList = "";
                  $.each(json, function(i,val){
                      placeList += "<option value='" + val.placeCode + "'>" + val.placeName + "</option>";
                  });
                  $("#place").empty().append(placeList);
                  var deviceList = '';
                  if(data.device.length>0) {
                      $.each(data.device, function(i,val){
                          deviceList += "<option value='" + val.id + "'>" + val.serial_num + "</option>";
                      });
                      $("#device").empty().append(deviceList);
                  }
              }else{
                  $("#place").empty().append("<option value='-1'>--请选择--</option>");
                  $("#device").empty().append("<option value='-1'>--请选择--</option>");
              }
          } );
      });
  });
  function editPresent(id,payChannel) {
      //alert(payChannel);
      //$("#presentId").val(id);
      $("input[name='id']:eq(1)").val(id)  ;
      var rowData = $("#gridTable").jqGrid("getRowData", id);
      //alert(rowData.refCodeName);
      if(payChannel=='4') {
          $("#rmbFee2").val(rowData.rmbFee);
          $("#chargeRow2").show()
      }else{
          $("#rmbFee2").val(0);
          $("#chargeRow2").hide()
      }
      $("#placeSpan").html(rowData.placeName);
      $("#payChannel2").val(rowData.payChannel);
      $("#deviceSpan").html(rowData.serialNum);
      $("#editPresentNum").val(rowData.wwbFee);
      $("#editRemark").val(rowData.remark);
      $("#imgView2").attr('src',rowData.certificate);
      $("#certificate").val(rowData.certificate);
      $("#editDialogInfo").dialog("open");
  }
    /**
     * 赠送旺旺币(此操作不会把钱打到账户上 需要审核)
     */
    function present() {
        var placeId = $("#place").val();
        if(placeId == '-1') {
            showMsg("请选择对应的场所");
            return;
        }
        var placeDeviceId = $("#device").val();
        if(placeDeviceId== '-1') {
            showMsg("请选择对应的设备");
            return;
        }
        // payChannel 包含赠送:9 现金充值:4
        var payChannel = $("#payChannel").val();
        var wwbFee = parseFloat($("#presentNum").val());
        var rmbFee = parseFloat($("#rmbFee").val());
        if(payChannel=='-1') {
            showMsg("请选择操作类型")
            return;
        }else if(payChannel=='4') {
            if(isNaN(rmbFee) || rmbFee<0) {
                showMsg("请输入正确的充值金额");
                return;
            }else {
                if(isNaN(wwbFee) || wwbFee<0) {
                    showMsg("请输入正确的旺旺币数目");
                    return;
                }
            }
        }else if(payChannel=='9'){
            if(isNaN(wwbFee) || wwbFee<0) {
                showMsg("请输入正确的旺旺币数目");
                return;
            }
        }
        // 备注可不填
        var remark = $("#remark").val();
        /*$.post("${ctx}/promotions/presentWwb.do",{placeDeviceId:placeDeviceId,wwbFee:wwbFee,remark:remark,payChannel:payChannel,rmbFee:rmbFee},function(result){
            $("#dialogInfo").dialog("close");//关闭对话框
            $("#gridTable").trigger('reloadGrid');//重新加载表格
        });*/
        //responseResult 为从后台返回信息，通常情况下返回的是JSON，我们工作中常使有的
        $("#myform").ajaxSubmit(function (data) {
            //var result = $.evalJSON(responseResult);
            if (data >= 0) {
                $("#dialogInfo").dialog('close');
                refresh();
            } else if(data="-2"){
                showMsg("凭证上传失败 请重新选择图片");
            }else if(data="-3"){
                showMsg("上传凭证图片大小不可以大于10M");
            }else {
                showMsg("操作失败");

            }
        });
    }
    /**
    * 获取场所对应的设备
    * @param dom
     */
    function getDevice(dom) {
        var placeCode = dom.value;
        if(dom.value == '-1') {
            return;
        }
        $.getJSON( "${ctx}/promotions/getSerialNum.do", { placeCode:placeCode}, function(data){
            var json = $.parseJSON(data);//to js json object
            if(json.length>0) {
                var deviceList = "";
                $.each(json, function(i,val){
                    deviceList += "<option value='" + val.id + "'>" + val.serial_num + "</option>";
                });
                $("#device").empty().append(deviceList);
            }else{
                $("#device").empty().append("<option value='-1'>--请选择--</option>");
            }
        });

    }

    function search() {
        if(!checks(0)) {
            return;
        }
        var placeName = $("#searchName").val();
        var startTime = $("input[name='dateLeft']:eq(0)").val();
        var endTime = $("input[name='dateRight']:eq(0)").val();
        var status = $("#status").val();
        $("#gridTable").jqGrid('setGridParam', {
            datatype: 'json',
            page: '1',
            postData: {"placeName": placeName,"startTime":startTime,"endTime":endTime,"status":status}
        }).trigger('reloadGrid');
    }
  /**提交审核操作**/
  function submitWwb(id) {
      $("#presentId").val(id);
      //$("input[name='id']")[1].val(id)  ;
      $("#dialog-confirm").dialog("open");
  }
  /**删除操作**/
  function deletePresent(id) {
      showMsg("你确定要删除这条数据?", 2, function () {
          $.ajax({
              type: 'post',
              url: '${ctx}/promotions/deletePresent.do',
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
  function giftWwb(id) {
      showMsg("你确定要赠送旺旺币?", 2, function () {
          $.ajax({
              type: 'post',
              url: '${ctx}/promotions/giftPresentWwb.do',
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

  //jqgrid刷新
  function refresh() {
      jQuery("#gridTable").trigger("reloadGrid");
  }
  /* 设置默认日期时间 */
  function defaultDates() {
      /*当前日期*/
      var nos = new Date();
      /*日期格式化*/
      var no = formatDate(nos);
      $("input[name='dateRight']").val(no + " 23:59");
      var endTime = new Date();
      endTime.setMonth(endTime.getMonth() - 1);
      var no2 = formatDate(endTime);
      $("input[name='dateLeft']").val(no2 + " 00:00");
  }
  // not my mind
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
    // reset按钮功能
    function resetFun() {
        $("#searchName").val('');
        $("#status").val('');
        defaultDates();
    }
    // 隐藏显示dom
    function operateRow(dom) {
        if(dom.value=='4') {
            $("#chargeRow").show();
        }else {
            $("#chargeRow").hide();
        }
    }

    function viewCertificate(certificate) {
        if(certificate !=null && certificate !='') {
            window.open(certificate);
        }else{
            showMsg("暂无上传凭证");
        }
    }
  /**
   * 初始化预览插件
   */
  function uploadFile() {
      new uploadPreview({UpBtn: "fileField", DivShow: "imgdiv", ImgShow: "imgView"});
      new uploadPreview({UpBtn: "fileField2", DivShow: "imgdiv2", ImgShow: "imgView2"});
  }
</script>
</html>
