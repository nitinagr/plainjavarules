<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
	$(document).ready(function(){

		$("#loadingmsg").hide();

		
	});
	function submitFormPage(){
		$('#loadingmsg').show();
		//This code is to retain the loading image for 500ms
		setTimeout(function(){			
			$("#editForm").submit();
			},250);
	}	

</script>
<script type="text/javascript" src="<c:url value="/static/js/pjr_common.js" />"></script>
<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Assign Operator Definition for <font color="blue">Subrule :<A href="<c:url value="/subrule/view/${subrule.id}"/>" > ${subrule.name}</A></font></b>
<br/>
<form:form modelAttribute="attributes" name="editForm" id="editForm" acceptCharset="UTF-8" method="post">
<c:choose>
	<c:when test="${not empty  attributesReferred}">
	<div class="error" >Following Attributes are still used in Subrule--> " <c:out value="${subrule.name}"/> " . Remove them from SubruleLogic before you unassign.			
			<ul>
			<c:forEach items="${attributesReferred }" var="sr">
			    <li>${sr}</li>	
			</c:forEach>
			</ul>
	</div>	
	</c:when>
</c:choose>
<br>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>	
	<br/><br/>
	<A class="button" href="#" onclick="javascript:window.location='<c:url value="/subrule/view/all" />';" >Cancel</A>
	<A class="button" href="#" onclick="javascript:submitFormPage();" >Save</A>	
</center>
<table width="100%" id="table" class="ruletable" cellspacing="0">

	<tr>		
		<th style="width:300px;"><b>Attribute Name</b></th>
		<th style="width:300px;"><b>Attribute Value </b></th>
		<th style="width:200px;">Is Assigned
		(
		<A href="#table" style="color:white;" onclick="selectAll('attributesToAssignFromRequest');">Select All</A>
		|
		<A href="#table" style="color:white;" onclick="clearAll('attributesToAssignFromRequest');">Clear All</A>
		)
		</th>		
	</tr>	
	
	<!--  Only colors the assigned -->
	<c:forEach items="${attributes}" var="attribute">
		<tr>
			
			<c:set var="contains" value="false" />
			<c:forEach items="${attributesAssigned}" var="atrAssigned" >
				<c:choose>
					<c:when test="${ atrAssigned.attributeId == attribute.attributeId}">
						<c:set var="contains" value="true" />
					</c:when>
				</c:choose>
			</c:forEach>			
			<c:choose>
				<c:when test="${contains}">
					<td class="ruletabletd" bgcolor="#FFFF60">
					<a href="<c:url value="/admin/attribute/view/${attribute.attributeId}" />" ><c:out value="${attribute.attributeName}"/></a>
					</td>		
					<td class="ruletabletd" bgcolor="#FFFF60">${attribute.value}</td>				
					<td class="ruletabletd" bgcolor="#FFFF60">
						<input type="checkbox" name="attributesToAssignFromRequest" id="attributesToAssignFromRequest"  value="${attribute.attributeId}" checked="checked"/>
					</td> 
				</c:when>
				<c:otherwise>
					<td class="ruletabletd">
					<a href="<c:url value="/admin/attribute/view/${attribute.attributeId}" />" ><c:out value="${attribute.attributeName}"/></a>
					</td>		
					<td class="ruletabletd"><c:out value="${attribute.value}"/></td>								
					<td class="ruletabletd">
						<input type="checkbox" name="attributesToAssignFromRequest" id="attributesToAssignFromRequest"  value="<c:out value="${attribute.attributeId}"/>" />
					</td>
				</c:otherwise>
			</c:choose>		
		</tr>
	</c:forEach>
	
</table>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>	
	<br/><br/>
	<A class="button" href="#" onclick="javascript:window.location='<c:url value="/subrule/view/all" />';" >Cancel</A>
	<A class="button" href="#" onclick="javascript:submitFormPage();" >Save</A>

	<div  id="loadingmsg" class="loadingmsg">
		<img src="<c:url value="/static/images/loading.gif"/>" height="150" width="150"/>
	</div>	
</center>
</form:form>

