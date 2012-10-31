<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Delete Attribute Success</b>
<br/>
<form:form modelAttribute="attribute" acceptCharset="UTF-8" name="statusForm" id="statusForm" >
<c:choose>
	<c:when test="${not empty  subrulesReferred}">
	<div class="error" >Following Subrule(s) still use this Operator.Unassign the operator from following Subrule(s) before you Delete.			
			<ul>
			<c:forEach items="${subrulesReferred }" var="sr">
			    <li>${sr}</li>	
			</c:forEach>
			</ul>
	</div>	
	</c:when>
</c:choose>
<table width="100%" class="ruletable" cellspacing="0">
	<tr>
			<td class="ruletabletd"><b>Attribute Id:</b></td>
			<td class="ruletabletd"><b><c:out value="${attribute.attributeId}"/></b></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Attribute Name / Object Name:</b></td>
		<td class="ruletabletd"><b><c:out value="${attribute.attributeName }"/></b></td>		
	</tr>	
	<tr>
		<td class="ruletabletd"><b>Mvel / Object value:</b></td>
		<td class="ruletabletd"><b><c:out value="${attribute.value}"/></b></td>		
	</tr>
</table>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>	
	<A class="button" href="<c:url value="/admin/attribute/view/all" />">Back</A>
</center>
</form:form>