<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglib.jsp" %>
  <link rel="stylesheet" href="${ctx}/css/smsmanage/style.css"/>
  <link rel="stylesheet" href="${ctx}/css/style.css">
  <style type='text/css'>
    .input {
      padding: 0px;
      margin: 0;
      border: 1px solid #beceeb;
    }

    .clear {
      display: none;
      position: absolute;
    }

    .input::-ms-clear {
      display: none;
    }

    .input:valid+.clear {
      display: inline;
    }
  </style>
  <script type="text/javascript">
    $(function () {
      $("#dialog-modal").dialog({
        autoOpen: false,
        height: 140,
        modal: true,
        buttons: {
          "确定": function () {
            var placeNames = document.getElementsByName("placesNames");
            var allPlaceValue = document.getElementById("txtBalance").value + ',';
            for (var i = 0; i < placeNames.length; i++) {
              if (isNaN(placeNames[i].value)) {
                showMsg("输入非法的数字");
                return;
              } else if (parseInt(placeNames[i].value) < 0) {
                showMsg("输入非法的数字");
                return;
              }
              allPlaceValue = allPlaceValue + placeNames[i].id + "," + placeNames[i].value + ",";
            }
            if (placeNames.length < 1) {
              showMsg("没有场所可以分配");
              return;
            }
            allPlaceValue = allPlaceValue.substring(0, allPlaceValue.length - 1);
            var flag = "";
            $.ajax({
              type: 'post',
              url: '${ctx}/smsManage/assigna/smsTransferSetup.do',
              data: {
                "allPlaceValue": allPlaceValue
              },
              dataType: 'json',
              success: function (msg) {
                $("#dialog-modal").dialog("close");
                if (msg == "0") {
                  flag = "保存成功";
                  $(".PublicTab a").each(function () {
                    var v = $(this).attr("title");
                    if (v == "短信分配") $(this).trigger("click");
                  });
                  //window.location.reload();
                } else if (msg == "1") {
                  showMsg("短信总数不正确");
                } else if (msg == "2") {
                  showMsg("数字格式有问题");
                } else {
                  showMsg("操作失败");
                }
              }
            });
          },
          "取消": function () {
            $(this).dialog("close");
          }
        },
        close: function () {
          /**清空div**/
        }
      });
    });
    //点击减少按钮
    function minusOne(inputId){
      var smsNumber = parseInt(document.getElementById(inputId+'#').value);
      var balanceValue = parseInt(document.getElementById("txtBalance").value);
      if (smsNumber > 0) {
        document.getElementById("txtBalance").value = balanceValue + 1;
        document.getElementById("balanceValue").innerHTML = balanceValue + 1;
        document.getElementById(inputId+'#').value = smsNumber - 1;
      }
    }
    // 点击增加按钮
    function addOne(inputId){
      var smsNumber = parseInt(document.getElementById(inputId+'#').value);
      var balanceValue = parseInt(document.getElementById("txtBalance").value);
      if (balanceValue > 0) {
        document.getElementById("txtBalance").value = balanceValue - 1;
        document.getElementById("balanceValue").innerHTML = balanceValue - 1;
        document.getElementById(inputId+'#').value = smsNumber + 1;
      }
    }
    var tempValue;
    //判断输入值是否合法
    function afterChange(initValue,inputId) {
      var smsNumber = document.getElementById(inputId+'#').value;
      var balanceValue = parseInt(document.getElementById("txtBalance").value);
      if (smsNumber == null || smsNumber == "") {
        smsNumber = 0;
      }
      if(isNaN(smsNumber)){
        showMsg("不是数字");
        document.getElementById(inputId+'#').value = tempValue;
        return;
      }else if(smsNumber<0){
        showMsg("数字必须大于0");
        document.getElementById(inputId+'#').value = tempValue;
        return;
      }else if(parseInt(initValue) + balanceValue < parseInt(smsNumber)){
        showMsg("没有足够的短信数量");
        document.getElementById(inputId+'#').value = tempValue;
        return;
      }
      document.getElementById("txtBalance").value = balanceValue - parseInt(smsNumber) + parseInt(tempValue);
      document.getElementById("balanceValue").innerHTML = balanceValue - parseInt(smsNumber) + parseInt(tempValue);
    }
    //确认提交
    function submitTransfer() {
      $("#dialog-modal").dialog("option", "title", "分配短信").dialog("open");
    }
  </script>
</head>
<body>
<%--
    <form action="${ctx}/sms/smsAuth!smsPayBuy.do" method="post" id="iframeForm">
--%>
      <input name="txtBalance" id="txtBalance" type="hidden" value="${balance}"/>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="Public_table2">
          <tr>
            <td colspan="3" style="text-align: left">
              <span>
                <img src="${ctx}/images/sms/tableTitle.png" hspace="3" width="18px" align="absmiddle">
					未分配短信余额 :&nbsp;&nbsp;</span><span id="balanceValue" name="balanceValue">${balance}
              </span>
            </td>
          </tr>
          <tr>
            <th>场所名称</th>
            <th>剩余短信数量</th>
            <th>短信分配</th>
          </tr>
          <c:forEach items="${placeList}" var="item" varStatus="i">
            <c:if test="${(i.count)%2==0 }">
              <tr  style="background-color: rgb(231, 240, 249);">
            </c:if>
            <c:if test="${(i.count)%2==1 }">
              <tr>
            </c:if>
            <td style="text-align:center;width:30%;">${item.placeName}</td>
            <td style="text-align:center;width:30%;">${item.userId}</td>
            <td style="text-align:center;width:40%;">
              <img src="${ctx}/images/sms/minus.png" style="vertical-align:middle;" onclick="minusOne(${item.id})"/>&nbsp;&nbsp;&nbsp;
              <input type="text" name="placesNames" id="${item.id}#"
                     value="${item.userId}" style="border:1px solid #ccc; height:25px; width:100px; color:#666; padding-left:5px; vertical-align:middle;"
                     class="input" maxlength="6" onfocus="tempValue = this.value;" onChange="afterChange(${item.userId},${item.id});value=value.replace(/[^\d]/g,'');" onpaste="return false"
                     onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" />&nbsp;&nbsp;&nbsp;
              <img src="${ctx}/images/sms/add.png" onclick="addOne(${item.id})" style="vertical-align:middle;" />
            </td>
            </tr>
          </c:forEach>
        </table>
        <div style="text-align:right;">
          <input type="button" id="payWWBi" onclick="submitTransfer();" class="wwbi-btn wwbi-btn-enable"/>
        </div>
<%--
    </form>
--%>

    <div id="dialog-modal" title="基本的模态对话框" style="display: none;text-align:center">
      确认提交？
    </div>
</body>
</html>


