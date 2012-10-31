<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="../../rule/view/all"> Home </a> &gt;&gt; Create Condition Definition</b>

<table width="100%" cellspacing="25">
		<tr>
			<td><b>Condition Name</b></td>
			<td><b>Condition Description</b></td>
			<td/>
		</tr>
		
	<c:forEach items="${conditions}" var="condition">

		
		<tr>
			<td>${condition.conditionName}</td>
			<td>${condition.description}</td>
			<td>
				<a href="${condition.conditionId}">View</a>			
				<a href="define/${condition.conditionId}">Edit</a>
			</td>		
		</tr>		
	</c:forEach>


</table>



