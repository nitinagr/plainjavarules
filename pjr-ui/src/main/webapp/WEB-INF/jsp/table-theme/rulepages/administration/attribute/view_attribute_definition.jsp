<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; View Attribute Definition </b>&gt;&gt; <b>${attribute.attributeName} </b>
<br/>

<form:form modelAttribute="attribute" name="viewForm" id="viewForm" acceptCharset="UTF-8" method="post">
<table width="100%" class="ruletable" cellspacing="0">

	<tr>
			<td class="ruletabletd"><b>Attribute Id:</b></td>
			<td class="ruletabletd"><b><c:out value="${attribute.attributeId}"/></b></td>
			<input type="hidden" name="attributeId" id="attributeId" value="${attribute.attributeId}"></input>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Attribute Name / Object Name:</b></td>
		<td class="ruletabletd"><b><c:out value="${attribute.attributeName }"/></b></td>
		<input type="hidden" name="attributeName" id="attributeName" value="${attribute.attributeName }"></input>		
	</tr>	
	<tr>
		<td class="ruletabletd"><b>Mvel / Object value:</b></td>
		<td class="ruletabletd"><b><c:out value="${attribute.value }"/></b></td>
		<input type="hidden" name="value" id="value" value="${attribute.value }"></input>		
	</tr>
	
	<tr>
		<td class="ruletabletd"><b>Model Class: </b><span class="mandatory" > * </span>
		</td>
		<td class="ruletabletd">
				<c:forEach items="${modelClasses}" var="modelClass" >
					<c:choose>
						<c:when test="${modelClass.model_id == attribute.modelId }">
							${modelClass.model_class_name }
						</c:when>
					</c:choose>					
				</c:forEach>
		</td>		
	</tr>		
</table>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/admin/attribute/view/all" />">Back</A>
	<a class="button" href="<c:url value="/admin/attribute/edit/${attribute.attributeId}" />" >Edit</a>
</center>
</form:form>

