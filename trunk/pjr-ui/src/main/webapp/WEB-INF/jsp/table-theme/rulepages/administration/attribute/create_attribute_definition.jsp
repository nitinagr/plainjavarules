<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Create Attribute Definition</b>
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
<form:form commandName="attribute" name="createForm" id="createForm" acceptCharset="UTF-8" method="post" action="create">
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
<div class="mandatorydiv"> All Fields marked (<span class="mandatory" > * </span>) are manadatory.</div>
<br/>

<table width="100%" class="ruletable" cellspacing="0">

	<tr>
		<td class="ruletabletd"><b>Attribute Name / Object Name: </b><span class="mandatory" > * </span>
		</td>
		<td class="ruletabletd">
			<input type="text" name="attributeName" id="attributeName" value="${attribute.attributeName}"></input>
			<!--<form:errors path="attributeName" cssClass="error" />
		--></td>		
	</tr>	
	<tr>
		<td class="ruletabletd"><b>Mvel / Object value: </b><span class="mandatory" > * </span>
		</td>
		<td class="ruletabletd">
			<input type="text" name="value" id="value" value="${attribute.value}"></input>	
		</td>		
	</tr>
	<tr>
		<td class="ruletabletd"><b>Model Class: </b><span class="mandatory" > * </span>
		</td>
		<td class="ruletabletd">		
			<select name="modelId" >
			<option id="" value=""></option>				
				<c:forEach items="${modelClasses}" var="modelClass" >
					<c:choose>
						<c:when test="${modelClass.model_id == attribute.modelId }">
							<option id="${modelClass.model_id}" value="${modelClass.model_id }" selected="selected">${modelClass.model_class_name }</option>							
						</c:when>
						<c:otherwise>
							<option id="${modelClass.model_id}" value="${modelClass.model_id }">${modelClass.model_class_name }</option>
						</c:otherwise>
					</c:choose>					
				</c:forEach>
			</select>
		</td>		
	</tr>	
	
</table>
<center>
<div  id="loadingmsg" class="loadingmsg">
			<img src="<c:url value="/static/images/loading.gif"/>" height="150" width="150"/>
		</div>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/admin/attribute/view/all" />">Cancel</A>
	<A class="button" href="#" id="saveButton" >Save</A>
</center>
</form:form>

