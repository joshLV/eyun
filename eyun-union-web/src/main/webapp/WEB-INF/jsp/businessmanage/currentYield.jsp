<%--
  Created by IntelliJ IDEA.
  User: sun
  Date: 2016/3/11
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/jsp/taglib.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<div class="NotData">
			<div class="NotData_t">
			       <c:choose>
			           <c:when test="${currentYield eq null}">
			              <img src="${pageContext.request.contextPath}/images/NotData.png" /><span>暂无数据</span>
			           </c:when>
			           <c:otherwise>
			               <td colspan="2" class="Public_table2">截止${currentYield.captureTime}收益情况(单位：元)：</td>
			           </c:otherwise>
			       </c:choose>
			</div>
		   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="Public_table2">
		    <%-- <tr>
		        <c:choose>
		            <c:when test="${currentYield eq null}">
		                <td colspan="2" class="warning" align="center">暂无数据</td>
		            </c:when>
		            <c:otherwise>
		                <td colspan="2" class="active">截止${currentYield.captureTime}收益情况(单位：分)：</td>
		            </c:otherwise>
		        </c:choose>
		    </tr> --%>
		    <tr style="background-color: rgb(231, 240, 249);">
		        <th width="20%">会员充值：</th>
		        <td width="80%">${currentYield.memberCard}</td>
		    </tr>
		    <tr>
		        <th>会员上机收益：</th>
		        <td>${currentYield.membersOnEarnings}</td>
		    </tr>
		    <tr style="background-color: rgb(231, 240, 249);">
		        <th>散户押金：</th>
		        <td>${currentYield.temporaryMoney}</td>
		    </tr>
		    <tr>
		        <th>散户上机收益：</th>
		        <td>${currentYield.temporaryOnEarnings}</td>
		    </tr>
		    <tr style="background-color: rgb(231, 240, 249);">
		        <th>其他收益：</th>
		        <td>${currentYield.otherEarnings}</td>
		    </tr>
		    <tr>
		        <th>总收益：</th>
		        <td>${currentYield.earningsCount}</td>
		    </tr>
		   <tr style="background-color: rgb(231, 240, 249);">
		        <td colspan="2" style="color:#1f60a6; padding-left:50px; border-top:1px solid #99c0ea">温馨提示：收益数据每小时定时更新，获取最新数据，请刷新页面。</td>
		   </tr>
		</table>
	</div>

