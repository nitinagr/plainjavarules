<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script>	
	$(document).ready(function(){

		$("#loadingmsg").hide();

		
	});
	function submit(){
		$('#loadingmsg').show();
		//This code is to retain the loading image for 500ms
		setTimeout(function(){			
			$("#editForm").submit();
			},250);
	}	

</script>			


<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Edit Operator Definition</b>&gt;&gt;${operator.operatorName}
<br/>
<form:form commandName="operator" name="editForm" id="editForm" method="post" acceptCharset="UTF-8" action="save">
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
			<td class="ruletabletd"><b>Operator Id:</b></td>
			<td class="ruletabletd"><b>${operator.operatorId}</b></td>
			<input type="hidden" name="operatorId" id="operatorId" value="${operator.operatorId}"/></input>
		</tr>
		
		<tr>
			<td class="ruletabletd"><b>Operator name:</b><span class="mandatory" > * </span>
			</td>
			<td class="ruletabletd"><input type="text" name="operatorName" id="operatorName" value="${operator.operatorName}"></input></td>
		</tr>		
		
		<tr>
			<td class="ruletabletd"><b>Operator Description</b><span class="mandatory" > * </span>
			</td>
			<td class="ruletabletd"><input type="text" name="value" id="value" value="${operator.value}"></input></td>
		</tr>			
</table>

<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/admin/operator/view/all" />">Cancel</A>
	<A class="button" href="#" onclick="submit();" >Save</A>

	<div  id="loadingmsg" class="loadingmsg">
		<img src="<c:url value="/static/images/loading.gif"/>" height="150" width="150"/>
	</div>			
</center>
</form:form>

