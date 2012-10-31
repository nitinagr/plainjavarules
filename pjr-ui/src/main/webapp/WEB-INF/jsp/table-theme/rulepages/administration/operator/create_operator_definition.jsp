<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Create Operator Definition</b>
<br/>
<script>
$(document).ready(function(){
	$("#loadingmsg").hide();
	$("#saveButton").click(function(){
		$("#loadingmsg").show();
		setTimeout(function(){
			
			$("#createForm").submit();
			},500);
		});
	
});
</script>
<form:form commandName="operator" name="createForm" id="createForm" method="post" acceptCharset="UTF-8" action="create">
<br/><br/>
<c:choose>
<c:when test="${not empty  errors}">
	<div class="error">
	<c:forEach items="${errors}" var="err">
		${err.defaultMessage}
		<br/>
	</c:forEach>
	</div>
</c:when>
</c:choose>
<br/>
<div class="mandatorydiv">All Fields marked (<span class="mandatory" > * </span>) are manadatory.</div>
<br/>
<table width="100%" class="ruletable" cellspacing="0">

	<tr>
		<td class="ruletabletd"><b>Operator Name / Object Name: </b><span class="mandatory" > * </span>
		</td>
		<td class="ruletabletd">
			<input type="text" name="operatorName" id="operatorName" value="${operator.operatorName}"></input>
		</td>		
	</tr>	
	<tr>
		<td class="ruletabletd"><b>Mvel / Object value: </b><span class="mandatory" value="${operator.value}"> * </span>
		</td>
		<td class="ruletabletd">
			<input type="text" name="value" id="value"></input>
		</td>		
	</tr>
</table>
<center>
		<div  id="loadingmsg" class="loadingmsg">
			<img src="<c:url value="/static/images/loading.gif"/>" height="150" width="150"/>
		</div>	
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/admin/operator/view/all" />">Cancel</A>
	<A class="button" href="#" id="saveButton" >Save</A>
</center>
</form:form>

