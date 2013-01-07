<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script>
$(document).ready(function(){
	
	$('#desCount').html(1000-$('#description').val().length); //To show first time how many chars left

	/**
	For textArea validation`
	*/
	$('#description').keyup(function () {
		var t = $(this);
        var text = t.val();
        var limit = 1000;
        
      //if textarea text is greater than maxlength limit, truncate and re-set text
        if (text.length >= limit) {
            text = text.substring(0, limit);
            t.val(text);
		}
        $('#desCount').html(limit-text.length);
	});
	
	//loading image display
	$("#loadingmsg").hide();
	$("#saveButton").click(function(){
		$("#loadingmsg").show();
		setTimeout(function(){
			
			$("#createForm").submit();
			},500);
		});	
});
</script>

<font color="#151B54">
<a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Create Subrule
</font>
<form:form commandName="subrule" name="createForm" id="createForm" method="post" acceptCharset="UTF-8" action="create">
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
<form:errors path="*" cssClass="error" />
<table width="100%" class="ruletable" cellspacing="0">
	<tr>
		<td class="ruletabletd"><b>Subrule Name</b><span class="mandatory" > * </span></td>
		<td class="ruletabletd"><input type="text" name="name" size="30" maxlength="100" value="${subrule.name }"/></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Subrule Description</b></td>
		<td class="ruletabletd">
		 <span id="desCount"></span> characters left<br />
		<textarea id="description" name="description" ><c:out value="${subrule.description }"/></textarea>
		</td>
	</tr>	
	<tr>
		<td class="ruletabletd"><b>Subrule Status</b>
		</td>
		<td class="ruletabletd"><form:checkbox path="active"/>Enabled</td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Subrule Default value</b>
		<td class="ruletabletd"><form:checkbox path="defaultValue"/>Default</td>
	</tr>
	
	<tr>
		<td class="ruletabletd"><b>Model Class: </b><span class="mandatory" > * </span>
		</td>
		<td class="ruletabletd">		
			<select name="modelId" >
				<option id="" value=""></option>					
				<c:forEach items="${modelClasses}" var="modelClass" >
					<c:choose>
						<c:when test="${modelClass.model_id == subrule.modelId }">
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
	<A class="button" href="<c:url value="/subrule/view/all" />">Cancel</A>
	<A class="button" href="#" id="saveButton" >Save</A>
</center>
</form:form>