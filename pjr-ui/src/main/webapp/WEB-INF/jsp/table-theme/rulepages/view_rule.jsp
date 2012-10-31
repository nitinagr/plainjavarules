<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style type="text/css">
table.conditiontable {
	border-collapse: collapse;
}

.conditiontable td , .conditiontable th{
	border: 1px solid black;
}

</style>

<script type="text/javascript">
$(document).ready(function() {
	//Dialog box hide
    $("#dialog").hide();
    
    //Delete confirmation starts
    $("#delete").click(function(){
    	var $input = $(this);
		$("#dialog").dialog({
			resizable: false,
			draggable: false,
			modal: true,
			buttons: {
				"Yes": function() {
					url=$input.attr("name");
					window.location.href = url;
				},
				"No": function() {
					$(this).dialog("close");
				}
			}
		}); // dialog <ENDS>
     });
    //Delete confirmation ends
    
});
</script>

<!-- <h2> View Rule : ${rule.ruleName}</h2> -->
<font color="#151B54">
<a href="<c:url value="/rule/view/all"/>"> Home </a> &gt;&gt; View Rule  &gt;&gt; <b>${rule.ruleName}</b>
</font> 
<br/><br/>
<form name="viewform" id="viewform" accept-charset="UTF-8" method="post">
<div style="float:left;padding-left:10px;">
	<font style="font-weight:bold;color:#151B54;padding-left:10px;">Related Links</font>
	<br><br>
	<ul class="nav">
		<li>
			<A  href="<c:url value="/rule/edit/${rule.ruleId}"/>" >Edit</A>
		</li>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li><b>|</b></li>
		<li>			
			<A   href="<c:url value="/admin/subrule/view/rule/${rule.ruleId}"/>" >Assign/ UnAssign Subrules</A>
		</li>
		<li><b>|</b></li>
		<li>			
			<A href="<c:url value="/admin/operator/view/rule/${rule.ruleId}"/>" >Assign/UnAssign Operators</A>
		</li>
		</sec:authorize>							
	</ul>
</div>
<div class="emptyLine" ></div><br/>
<table cellspacing="0" width="100%" class="ruletable">
	<tr>
		<td class="ruletabletd"><b>Rule Id</b></td>
		<td class="ruletabletd"><c:out value="${rule.ruleId}" /></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Rule Name</b></td>
		<td class="ruletabletd"><c:out value="${rule.ruleName}" /></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Rule Description</b></td>
		<td class="ruletabletd"><c:out value="${rule.ruleDes}" /></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Rule Status</b></td>
		<td class="ruletabletd">
			<c:choose>
				<c:when test="${rule.active}">Enabled</c:when>
				<c:otherwise>Disabled</c:otherwise>
			</c:choose>		
		</td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Return value</b></td>		
		<td class="ruletabletd"><c:out value="${rule.returnValue}" /></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Execution Order</b></td>		
		<td class="ruletabletd"><c:out value="${rule.executionOrder}" /></td>
	</tr>		
	
	<tr>
		<td class="ruletabletd"><b>Current Logic in DB</b></td>
		<td class="ruletabletd">
			<textarea rows="20" columns="40"   disabled="disabled"><c:out value="${rule.logicText}"/></textarea>
		</td>
	</tr>
		
</table>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/rule/edit/${rule.ruleId}"/>" >Edit</A>
	<sec:authorize access="hasRole('ROLE_ADMIN')">			
		<A class="button" id="delete" href="#" name="<c:url value="/admin/rule/delete/${rule.ruleId}"/>" >Delete</A>
	</sec:authorize>	
</center>
<div id="dialog" title="Confirming Delete">Are you sure you want to Delete?</div>
</form>