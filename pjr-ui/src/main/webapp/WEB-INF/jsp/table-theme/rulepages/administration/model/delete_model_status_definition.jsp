<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Delete Model Status</b>
<br/>
<form:form modelAttribute="modelDto" name="statusForm" id="statusForm" acceptCharset="UTF-8">
<br><br>
<c:choose>
	<c:when test="${not empty  rulesReferred}">
	<div class="error" >Following Rule(s) still use this Model.Delete the Rule(s) before you Delete the Model.			
			<ul>
			<c:forEach items="${rulesReferred }" var="rr">
			    <li>${rr}</li>	
			</c:forEach>
			</ul>
	</div>	
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${not empty  subrulesReferred}">
	<div class="error" >Following Subrule(s) still use this Model.Delete the Subrule(s) before you Delete the Model.				
			<ul>
			<c:forEach items="${subrulesReferred }" var="sr">
			    <li>${sr}</li>	
			</c:forEach>
			</ul>
	</div>	
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${not empty  attributesReferred}">
	<div class="error" >Following Attribute(s) still use this Model.Delete the Attribute before you Delete the Model.				
			<ul>
			<c:forEach items="${attributesReferred }" var="at">
			    <li>${at}</li>	
			</c:forEach>
			</ul>
	</div>	
	</c:when>
</c:choose>
<table width="100%" class="ruletable" cellspacing="0">
	<tr>
			<td class="ruletabletd"><b>Model Id:</b></td>
			<td class="ruletabletd"><b><c:out value="${modelDto.model_id}"/></b></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Model Name / Object Name:</b></td>
		<td class="ruletabletd"><b><c:out value="${modelDto.model_class_name }"/></b></td>		
	</tr>	
</table>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/admin/model/view/all" />">Back</A>
</center>
</form:form>