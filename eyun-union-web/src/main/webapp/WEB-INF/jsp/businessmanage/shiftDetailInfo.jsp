<%--
  Created by IntelliJ IDEA.
  User: sun
  Date: 2016/3/11
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/jsp/taglib.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
<table class="table table-hover table-bordered">
  <tr><td colspan="2">${shiftDetail.endShiftTime}交班记录</td></tr>
  <tr><td>上次交班人：</td><td>${shiftDetail.lastShiftPeople}</td></tr>
  <tr><td>本次交班人：</td><td>${shiftDetail.dutyEmployee}</td></tr>
  <tr><td>上次交接班时间：</td><td>${shiftDetail.lastShiftTime}</td></tr>
  <tr><td>交接班时间：</td><td>${shiftDetail.endShiftTime}</td></tr>
  <tr><td>班次名称：</td><td>${shiftDetail.classes}</td></tr>
  <tr><td>控制台：</td><td>${shiftDetail.console}</td></tr>
  <tr>
    <td>上下班金额(元)：</td>
    <td>${shiftDetail.atworkRemainingMoney}</td>
  </tr>
  <tr>
    <td>会员卡销售(元)：</td>
    <td>${shiftDetail.cardSaleIncome}</td>
  </tr>
  <tr>
    <td>会员卡（开通）(元)：</td>
    <td>${shiftDetail.cardUpIncome}</td>
  </tr>
  <tr>
    <td>账户卡（开充值）(元)：</td>
    <td>${shiftDetail.atworkDepositMoney}</td>
  </tr>
  <tr>
    <td>本班账户上机收入(元)：</td>
    <td>${shiftDetail.dutyTempIncome}</td>
  </tr>
  <tr>
    <td>账户押金(元)：</td>
    <td>${shiftDetail.dutyTempDeposit}</td>
  </tr>
  <tr>
    <td>商品销售收入(元)：</td>
    <td>${shiftDetail.dutyProductIncome}</td>
  </tr>
  <tr>
    <td>总现金收入(元)：</td>
    <td>${shiftDetail.sumCashIncome}</td>
  </tr>
  <tr>
    <td>实际收入(元)：</td>
    <td>${shiftDetail.dutyCashIncome}</td>
  </tr>
  <tr>
    <td>上缴金额(元)：</td>
    <td>${shiftDetail.handOverMoney}</td>
  </tr>
  <tr>
    <td>留给下班(元)：</td>
    <td>${shiftDetail.leaveNextMoney}</td>
  </tr>

</table>
