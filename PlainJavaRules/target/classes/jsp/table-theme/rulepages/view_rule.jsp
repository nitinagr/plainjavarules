<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<style type="text/css">
table.conditiontable {
	border-collapse: collapse;
}

.conditiontable td , .conditiontable th{
	border: 1px solid black;
}
</style>
<!-- <h2> View Rule : ${rule.ruleName}</h2> -->
<b><a href="../view/all"> Home </a> &gt;&gt; View Rule</b> 
<table cellspacing="25">
	<tr>
		<td><b>Rule Id</b></td>
		<td>${rule.ruleId}</td>
	</tr>
	<tr>
		<td><b>Rule Name</b></td>
		<td>${rule.ruleName}</td>
	</tr>
	<tr>
		<td><b>Rule Description</b></td>
		<td>${rule.ruleDes}</td>
	</tr>
	<tr>
		<td><b>Rule Status</b></td>
		<td>
			<c:choose>
				<c:when test="${rule.enable}">Enabled</c:when>
				<c:otherwise>Disabled</c:otherwise>
			</c:choose>		
		</td>
	</tr>
	<tr>
		<td valign="top"><b>Conditions Applied to this rule</b></td>
		<td>
			<table cellspacing="5" class="conditiontable">
				<tr>
					<td><b>Name</b>
					</td>
					<td><b>Condition Text</b>
					</td>
					<td><b>Condition Status</b>
					</td>
					<td/>
				</tr>
				
				<c:forEach items="${rule.conditionList}" var="condition">
				<tr>
					<td><c:out value="${condition.displayName }"></c:out></td>
					<td><c:out value="${condition.conditionDisplay }"></c:out></td>
					<td>
						<c:choose>
							<c:when test="${condition.enable}">Enabled</c:when>
							<c:otherwise>Disabled</c:otherwise>
						</c:choose>
					</td>
					<td>
						<a href="../../condition/view/${condition.conditionId}?ruleId=${condition.ruleId}" >View</a>
						<a href="../../condition/edit/${condition.conditionId}?ruleId=${condition.ruleId}" >Edit</a>
					</td>
					
				</tr>
				</c:forEach>
			</table>	
		</td>
	</tr>		
	<tr>		
		<td colspan="2" align="center"><A href="../edit/${rule.ruleId}" >Edit Rule</A></td>
		
	</tr>			
</table>