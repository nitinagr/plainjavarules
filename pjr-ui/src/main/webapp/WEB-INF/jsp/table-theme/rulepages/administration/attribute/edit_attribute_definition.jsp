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


<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Edit Attribute Definition</b>&gt;&gt;<c:out value="${attribute.attributeName}"/>
<br/>
<form:form commandName="attribute" name="editForm" id="editForm" acceptCharset="UTF-8" method="post" action="save">
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
			<td class="ruletabletd"><b>Attribute Id:</b></td>
			<td class="ruletabletd"><b><c:out value="${attribute.attributeId}"/></b></td>
			<input type="hidden" name="attributeId" id="attributeId" value="<c:out value="${attribute.attributeId}"/>" ></input>
		</tr>
		
		<tr>
			<td class="ruletabletd"><b>Attribute name:</b><span class="mandatory" > * </span>
			</td>
			<td class="ruletabletd"><input type="text" name="attributeName" id="attributeName" value="<c:out value="${attribute.attributeName}"/>" ></input>
			<form:errors path="attributeName" cssClass="error" />
			</td>
		</tr>		
		
		<tr>
			<td class="ruletabletd"><b>Attribute Value</b><span class="mandatory" > * </span>
			</td>
			<td class="ruletabletd"><input type="text" name="value" id="value" value="<c:out value="${attribute.value}"/>" ></input>
			<form:errors path="attributeName" cssClass="error" />
			</td>
		</tr>		
		
	<tr>
		<td class="ruletabletd"><b>Model Class: </b><span class="mandatory" > * </span>
		</td>
		<td class="ruletabletd"><b><c:out value="${attribute.modelId }"/></b></td>
		<!--<td class="ruletabletd">
				<c:forEach items="${modelClasses}" var="modelClass" >
					<c:choose>
						<c:when test="${modelClass.model_id == attribute.modelId }">
							${modelClass.model_class_name }
						</c:when>
					</c:choose>					
				</c:forEach>
		</td>	 -->			
	</tr>		
</table>

<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/admin/attribute/view/all" />">Cancel</A>
	<A class="button" id="submitButton" href="#" onclick="javascript:submit();" >Save</A>

	<div  id="loadingmsg" class="loadingmsg">
		<img src="<c:url value="/static/images/loading.gif"/>" height="150" width="150"/>
	</div>		
</center>
</form:form>

