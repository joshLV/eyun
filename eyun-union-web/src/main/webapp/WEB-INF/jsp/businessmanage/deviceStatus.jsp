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
		           <c:when test="${deviceStatus eq null}">
		              <img src="${pageContext.request.contextPath}/images/NotData.png" /><span>暂无数据</span>
		           </c:when>
		       </c:choose>
		    </div>
	    <%-- <c:if test="${deviceStatus eq null}">
	        <tr>
	            <td colspan="2" class="warning" align="center">暂无数据</td>
	        </tr>
	    </c:if> --%>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="Public_table2">
				<tr style="background-color: rgb(231, 240, 249);">
					<th width="20%">上传时间：</th>
					<td width="80%">${deviceStatus.captureTime}</td>
				</tr>
				<tr>
					<th>硬盘：</th>
					<td>
						<c:if test="${deviceStatus != null}">
							总${deviceStatus.diskCapacity}G,占用${deviceStatus.diskUsedCapacity}G余${deviceStatus.diskFreeCapacity}G
						</c:if>
					</td>
				</tr>
				<tr style="background-color: rgb(231, 240, 249);">
				    <th>CPU占用率：</th>
				    <td>
				        <c:if test="${deviceStatus != null}">
				           ${deviceStatus.cpuUtilization}%
				       </c:if>
				    </td>
				</tr>
				<tr>
				    <th>内存：</th>
				    <td>
				        <c:if test="${deviceStatus != null}">
							总${deviceStatus.totalMemory}G,占用${deviceStatus.usedMemory}G,余${deviceStatus.freeMemory}G
				       </c:if>
				    </td>
				</tr>
				<tr style="background-color: rgb(231, 240, 249);">
				    <th>计算机服务状态：</th>
				    <td>${deviceStatus.billingServiceStatus}</td>
				</tr>
				<tr>
				    <th>备份服务器状态：</th>
				    <td>${deviceStatus.backupServiceStatus}</td>
				</tr>
				<tr style="background-color: rgb(231, 240, 249);">
				    <th>探侦通信状态：</th>
				    <td>${deviceStatus.probeCommunicationStatus}</td>
				</tr>
		</table>
	</div>