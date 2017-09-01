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
  <td>收银员</td>
  <td>控制台</td>
  <td>电话</td>
</tr>
  <c:forEach items="${currentYieldList}" var="foo">
    <tr>
      <td>${foo.cashier}</td>
      <td>${foo.console}</td>
      <td>${foo.phoneNumber}</td>
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
      colNames:['收银员','控制台','电话'],
      colModel:[
        {name: 'cashier', index: 'cashier', align: 'center', sorttype: "string", width: '20'},
        {name:'console',index:'console',align: 'center',sortable: false, width: '20'},
        {name:'phoneNumber',index:'phoneNumber', sorttype:"string", align: 'center', width: '20'},
      ],
      rowList: [10, 20, 30,40],
      loadonce:true,
      jsonReader: {
        rowNum:10,
        repeatitems : false
      },
      pager:"#gridPager",
      multiselect: false   // 复选框设置
    }).navGrid('#gridPager',{edit:false,add:false,del:false,search:false,refresh : false})
  });
</script>


