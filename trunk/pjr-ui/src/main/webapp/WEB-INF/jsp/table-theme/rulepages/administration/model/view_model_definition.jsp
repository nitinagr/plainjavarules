<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; View Model Definition</b>
<br/>
<form:form modelAttribute="modelDto" name="viewForm" id="viewForm" acceptCharset="UTF-8" method="post">
<table width="100%" class="ruletable" cellspacing="0">

	<tr>
			<td class="ruletabletd"><b>Model Id:</b></td>
			<td class="ruletabletd"><b><c:out value="${modelDto.model_id}"/></b></td>
			<input type="hidden" name="model_id" id="model_id" value="${modelDto.model_id}"></input>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Model Name / Object Name:</b></td>
		<td class="ruletabletd"><b><c:out value="${modelDto.model_class_name}"/></b></td>
		<input type="hidden" name="model_class_name" id="model_class_name" value="${modelDto.model_class_name }"></input>
				
	</tr>
</table>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/admin/model/view/all" />">Back</A>
	<a class="button" href="<c:url value="/admin/model/edit/${modelDto.model_id}"/>" >Edit</a>
</center>
</form:form>

