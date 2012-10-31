<%@ page language="java" contentType="text/html; charset=ISO-8859-1"     pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<table cellspacing="25">
	<tr>
		<th colspan="4" align="left">Condition Name</th>
	</tr>	
	<c:forEach items="${conditions}" var="condition">
		<tr>
			<td>${condition.displayName}</td>
			<td>${condition.conditionId}</td>
			<td><A href="${condition.conditionId}" >View</A></td>
			<td><A href="../edit/${condition.conditionId}" >Edit</A></td>		
		</tr>
	</c:forEach>
</table>
