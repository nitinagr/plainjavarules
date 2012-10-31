<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script>
$(document).ready(function(){
	
	$('#desCount').html(1000-$('#ruleDes').val().length); //To show first time how many chars left

	/**
	For textArea validation`
	*/
	$('#ruleDes').keyup(function () {
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
<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Create Condition Definition</b>
<form:form commandName="rule" name="createForm" id="createForm" method="post" acceptCharset="UTF-8" action="create">
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
			<td class="ruletabletd"><b>Rule Name</b><span class="mandatory" > * </span>
			</td>
			<td class="ruletabletd"><input type="text" name="ruleName"	value="${rule.ruleName}" maxlength="30"/>
				<!--<form:errors path="ruleName" cssClass="error" />-->
			</td>
		</tr>
		<tr>
			<td class="ruletabletd"><b>Rule Description</b>
			</td>
			<td class="ruletabletd">
			<span id="desCount"></span> characters left<br />
			<textarea id="ruleDes" name="ruleDes" ><c:out value="${rule.ruleDes}"/></textarea>
			</td>
		</tr>
		<tr>
			<td class="ruletabletd"><b>Rule Status</b>
			</td>
			<td class="ruletabletd"><form:checkbox path="active" value="${rule.active}" />Enabled</td>

		</tr>	
		
		<tr>
			<td class="ruletabletd"><b>Return Value</b><span class="mandatory" > * </span>
			</td>		
			<td class="ruletabletd"><input type="text" name="returnValue" value="${rule.returnValue}" /></td>
		</tr>
		
		<tr>
			<td class="ruletabletd"><b>Execution Order</b><span class="mandatory" > * </span>
			</td>		
			<td class="ruletabletd"><input type="text" name="executionOrder" value="${rule.executionOrder}" /></td>
		</tr>				
		
</table>
<center>
<div  id="loadingmsg" class="loadingmsg">
			<img src="<c:url value="/static/images/loading.gif"/>" height="150" width="150"/>
		</div>	
	<A class="button" href="<c:url value="/rule/view/all" />">Cancel</A>
	<A class="button" href="#" id="saveButton" >Save</A>
</center>
</form:form>

