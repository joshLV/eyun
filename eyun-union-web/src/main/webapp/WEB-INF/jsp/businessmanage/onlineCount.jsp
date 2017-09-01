<%--
  Created by IntelliJ IDEA.
  User: sun
  Date: 2016/3/11
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/jsp/taglib.jsp"%>

<%--
<table class="PublicTable">
  <tr>
    <td>区域(终端数)</td>
    <td>会员人数</td>
    <td>散户人</td>
    <td>总人数</td>
  </tr>
  <c:forEach items="${onlineHeadCountDetailList}" var="foo">
    <tr>
      <td>${foo.areaName}(${foo.clientCounts})</td>
      <td>${foo.vipOnlineNumbers}</td>
      <td>${foo.tempOnlineNumbers}</td>
      <td>${foo.countNumbers}</td>
    </tr>
  </c:forEach>
</table>
--%>
<table>
  <div class="right_c_tab">
    <!-- grid对应table设置 -->
    <table id="gridTable"></table>
    <div id="gridPager"></div>
    <!-- end -->
  </div>
</table>
<script type="text/javascript">
  $(function(){
    //grid加载
    $("#gridTable").jqGrid({
      datatype : 'local',
      data: ${data},
      height: 'auto',
      forceFit : true,//当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit 为false时，此属性会被忽略
      altRows : true,
      colNames:['区域','终端数','会员人数','散户人数', '总人数'],
      colModel:[
        {name: 'areaName', index: 'areaName', align: 'center', sorttype: "string", width: '20'},
        {name:'clientCounts',index:'clientCounts',align: 'center',sortable: false, width: '20'},
        {name:'vipOnlineNumbers',index:'vipOnlineNumbers', sorttype:"string", align: 'center', width: '20'},
        {name:'tempOnlineNumbers',index:'tempOnlineNumbers', align: 'center', width: '20'},
        {name:'countNumbers',index:'countNumbers', align: 'center', width: '20'},
      ],
      rowList: [10, 20, 30,40],
      loadonce:true,
      jsonReader: {
        rowNum:10,
        repeatitems : false       // 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
      },
     /* gridComplete:function(rowid, rowdata){
        var ids = $("#gridTable").getDataIDs();
        for(var i=0;i<ids.length;i++){
          var viewHtml = prePreviewHtm+"onclick='view(\"" + ids[i] + "\")'"+sufPreviewHtm;
          //jQuery("#gridTable").jqGrid('setCell',ids[i],'operate',viewHtml);
          jQuery("#gridTable").jqGrid('setRowData',ids[i],{operate:viewHtml});
        }
      },*/
      pager:"#gridPager",
      multiselect: false   // 复选框设置
    }).navGrid('#gridPager',{edit:false,add:false,del:false,search:false,refresh : false})
  });
</script>
