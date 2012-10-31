<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Delete Subrule Status</b>
<br/>
<form:form modelAttribute="subrule" name="statusForm" id="statusForm" acceptCharset="UTF-8">
<c:choose>
	<c:when test="${not empty  rulesReferred}">
	<div class="error" >Following Rule(s) still use this Subrule.Unassign the Subrule from following Rule(s) before you Delete.			
			<ul>
			<c:forEach items="${rulesReferred }" var="rr">
			    <li>${rr}</li>	
			</c:forEach>
			</ul>
	</div>	
	</c:when>
</c:choose>
<table width="100%" class="ruletable" cellspacing="0">
<tr>
		<td class="ruletabletd"><b>Subrule Id</b></td>
		<td class="ruletabletd"><c:out value="${subrule.id}"/></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Subrule Name</b></td>
		<td class="ruletabletd"><c:out value="${subrule.name}"/></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Subrule Description</b></td>
		<td class="ruletabletd"><c:out value="${subrule.description}"/></td>
	</tr>		
	<tr>
		<td class="ruletabletd"><b>Subrule defaultvalue</b></td>
		<td class="ruletabletd"><c:out value="${subrule.defaultValue}"/></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Subrule Status</b></td>
		<td class="ruletabletd">
			<c:choose>
				<c:when test="${subrule.active}">Enabled</c:when>
				<c:otherwise>Disabled</c:otherwise>
			</c:choose>		
		</td>
	</tr>	
</table>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/subrule/view/all" />">Back</A>
</center>
</form:form>