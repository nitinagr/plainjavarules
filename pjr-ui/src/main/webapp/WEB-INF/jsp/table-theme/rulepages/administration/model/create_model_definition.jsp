<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Create Model Definition</b>
<br/>
<script>
$(document).ready(function(){
	$('#desCount').html(200-$('#model_class_name').val().length); //To show first time how many chars left

	/**
	For textArea validation
	*/
	$('#model_class_name').keyup(function () {
		var t = $(this);
        var text = t.val();
        var limit = 200;
        
      //if textarea text is greater than maxlength limit, truncate and re-set text
        if (text.length >= limit) {
            text = text.substring(0, limit);
            t.val(text);
		}
        $('#desCount').html(limit-text.length);
	});
	
	$("#loadingmsg").hide();
	$("#saveButton").click(function(){
		$("#loadingmsg").show();
		setTimeout(function(){
			
			$("#createForm").submit();
			},500);
		});
	
});
</script>
<form:form commandName="modelDto" name="createForm" id="createForm" method="post" acceptCharset="UTF-8" action="create">
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
		<td class="ruletabletd"><b>Model Class Name / Object Name: </b><span class="mandatory" > * </span>
		</td>
		<td class="ruletabletd">
			 <span id="desCount"></span> characters left<br />
			 <textarea id="model_class_name" name="model_class_name" rows="20" cols="40"><c:out value="${modelDto.model_class_name}"/></textarea>						
			Provide fully classified java class name.
		</td>		
	</tr>	
</table>
<center>
		<div  id="loadingmsg" class="loadingmsg">
			<img src="<c:url value="/static/images/loading.gif"/>" height="150" width="150"/>
		</div>	
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/admin/model/view/all" />">Cancel</A>
	<A class="button" href="#" id="saveButton" >Save</A>
</center>
</form:form>

