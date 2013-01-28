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
<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Assign Operator Definition for <font color="blue">Rule : <a href="<c:url value="/rule/view/${rule.ruleId }" />">${rule.ruleName}</a></font></b>
<br/>
<form:form modelAttribute="operators" name="editForm" id="editForm" acceptCharset="UTF-8" method="post">
<c:choose>
	<c:when test="${not empty  operatorsRefered}">
	
	<div class="error" >
		Following Operators are  used in this rule. Remove them from RulesLogic before you unassign.
		<br/>	
		<ul>
	    <c:forEach var="opr" items="${operatorsRefered}" >
	    	<li>${opr}</li>			
	    </c:forEach>
	    </ul>
    </div>	
	</c:when>
</c:choose>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/rule/view/all" />">Cancel</A>
	<A class="button" href="#" onclick="javascript:submitFormPage();" >Save</A>
</center>

<table width="100%" id="table" class="ruletable" cellspacing="0">

	<tr>
		<th style="width:300px;">Operator Name</th>
		<th style="width:300px;">Operator Value</th>
		<th style="width:200px;">Is Assigned(
		<A href="#table" style="color:white;" onclick="selectAll('operatorsToAssign');">Select All</A>
		|
		<A href="#table" style="color:white;" onclick="clearAll('operatorsToAssign');">Clear All</A>
		)
		</th>			
	</tr>	
	
	<!--  Only prints assigned -->
	<c:forEach items="${operators}" var="operator">
		<tr>
			
			<c:set var="contains" value="false" />
			<c:forEach items="${operatorsAssigned}" var="oprAssigned" >
				<c:choose>
					<c:when test="${ oprAssigned.operatorId == operator.operatorId}">
						<c:set var="contains" value="true" />
					</c:when>
				</c:choose>
			</c:forEach>			
			

			
			
			<c:choose>
				<c:when test="${contains}">
					<td class="ruletabletd" bgcolor="#FFFF60">
					<a href="<c:url value="/admin/operator/view/${operator.operatorId}"/>" ><c:out value="${operator.operatorName}"/></a>
					</td>		
					<td class="ruletabletd" bgcolor="#FFFF60">${operator.value}</td>				
					<td class="ruletabletd" bgcolor="#FFFF60">
						<input type="checkbox" name="operatorsToAssign" id="operatorsToAssign"  value="${operator.operatorId}" checked="checked"/>
					</td> 
				</c:when>
				<c:otherwise>
					<td class="ruletabletd">
					<a href="<c:url value="/admin/operator/view/${operator.operatorId}"/>" ><c:out value="${operator.operatorName}"/></a>
					</td>		
					<td class="ruletabletd">${operator.value}</td>								
					<td class="ruletabletd">
						<input type="checkbox" name="operatorsToAssign" id="operatorsToAssign"  value="${operator.operatorId}" />
					</td>
				</c:otherwise>
			</c:choose>
					
			</td>		
		</tr>
	</c:forEach>
	
</table>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
	<A class="button" href="<c:url value="/rule/view/all" />">Cancel</A>
	<A class="button" href="#" onclick="javascript:submitFormPage();" >Save</A>
	<div  id="loadingmsg" class="loadingmsg">
		<img src="<c:url value="/static/images/loading.gif"/>" height="150" width="150"/>
	</div>		
</center>
</form:form>

