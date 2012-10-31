<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; View Operator Definition</b>
<br/>
<form:form modelAttribute="operator" name="viewForm" id="viewForm" acceptCharset="UTF-8" method="post">
<table width="100%" class="ruletable" cellspacing="0">

	<tr>
			<td class="ruletabletd"><b>Operator Id:</b></td>
			<td class="ruletabletd"><b><c:out value="${operator.operatorId}"/></b></td>
			<input type="hidden" name="operatorId" id="operatorId" value="${operator.operatorId}"></input>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Operator Name / Object Name:</b></td>
		<td class="ruletabletd"><b><c:out value="${operator.operatorName}"/></b></td>
		<input type="hidden" name="operatorName" id="operatorName" value="${operator.operatorName }"></input>
				
	</tr>	
	<tr>
		<td class="ruletabletd"><b>Mvel / Object value:</b></td>
		<td class="ruletabletd"><b><c:out value="${operator.value}"/></b></td>
		<input type="hidden" name="value" id="value" value="${operator.value }"></input>	
	</tr>
</table>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/admin/operator/view/all" />">Back</A>
	<a class="button" href="<c:url value="/admin/operator/edit/${operator.operatorId}"/>" >Edit</a>
</center>
</form:form>

