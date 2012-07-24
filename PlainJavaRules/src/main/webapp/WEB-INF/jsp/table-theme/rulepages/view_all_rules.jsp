<%@ page language="java" contentType="text/html; charset=ISO-8859-1"     pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<b><a href="#">Home</a></b>
<br/>
<div align="right" style="left-padding:5 px"><a href="<c:url value="/j_spring_security_logout" />" > Logout</a></div>
<table cellspacing="25">
	<tr>
		<th colspan="6" align="left">Rule Name</th>
	</tr>	
	<c:forEach items="${rules}" var="rule">
		<tr>
			<td>${rule.ruleId}</td>
			<td>${rule.ruleName}</td>
			<td>${rule.ruleDes}</td>
			<td>
			<c:choose>
				<c:when test="${rule.enable}">Enabled</c:when>
				<c:otherwise>Disabled</c:otherwise>
			</c:choose>
			
			
			<td><A href="${rule.ruleId}" >View</A></td>
			<td><A href="../edit/${rule.ruleId}" >Edit</A></td>		
		</tr>
	</c:forEach>
</table>
