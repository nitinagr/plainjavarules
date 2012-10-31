<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style type="text/css" >
.tooltip {
	display:none;
	position:absolute;
	border:1px solid #333;
	background-color:#161616;
	border-radius:5px;
	padding:10px;
	color:#fff;
	font-size:12px Arial;
}
</style>

<script type="text/javascript">
$(document).ready(function() {
	$("#loadingmsg").hide();
	
	   // Tooltip only Text
    $('.masterTooltip').hover(function(){
            // Hover over code
            var title = $(this).attr('tip');
            $(this).data('tipText', title).removeAttr('tip');
            $('<p class="tooltip"></p>')
            .text(title)
            .appendTo('body')
            .fadeIn('slow');
    }, function() {
            // Hover out code
            $(this).attr('tip', $(this).data('tipText'));
            $('.tooltip').remove();
    }).mousemove(function(e) {
            var mousex = e.pageX + 20; //Get X coordinates
            var mousey = e.pageY + 10; //Get Y coordinates
            $('.tooltip')
            .css({ top: mousey, left: mousex })
    });
});

function submitFormPage(){
	$('#loadingmsg').show();		
	setTimeout(function(){			
		$("#editForm").submit();
		},250);
}	
</script>



<b><a href="<c:url value="/rule/view/all"/>"> Home </a> &gt;&gt; Assign Subrule Definition for <font color="blue">Rule : <A href="<c:url value="/rule/view/${rule.ruleId}"/>" >${rule.ruleName}</A></font></b>
<br/>
<form:form modelAttribute="sr" name="editForm" id="editForm" method="post" acceptCharset="UTF-8">
<form:errors path="*" cssClass="error" />
<c:choose>
	<c:when test="${not empty  subRulesReferred}">
	
	<div class="error" >
	Following SubRules are  used in this rule. Remove them from RulesLogic before you unassign.
	<br/>		
		<ul>
	    <c:forEach var="sr" items="${subRulesReferred}" >
	    	<li>${sr}</li>			
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

<table width="100%" cellspacing="0" class="ruletable">

	<tr>		
		
		<th style="width:300px;">SubRule Name</th>
		<th style="width:300px;">Status</th>
		<th style="width:200px;">Is Assigned</th>
	</tr>	
	
	<!--  Only prints assigned -->
	<c:forEach items="${subRules}" var="subRule">
		<tr>
			
			<c:set var="contains" value="false" />
			<c:forEach items="${assignedSubRules}" var="assignedSubRule" >
				<c:choose>
					<c:when test="${ assignedSubRule.id  == subRule.id}">
						<c:set var="contains" value="true" />
					</c:when>
				</c:choose>
			</c:forEach>			
			<c:choose>
				<c:when test="${contains}">
					<!-- <td class="ruletabletd" bgcolor="#FFFF60">${subRule.id}</td> -->		
					<td class="ruletabletd" bgcolor="#FFFF60">
					<A href="<c:url value="/subrule/view/${subRule.id}"/>" ><c:out value="${subRule.name}"/></A>
					</td>
					<!-- <td class="ruletabletd" bgcolor="#FFFF60">${subRule.description}</td>-->
					<td class="ruletabletd" bgcolor="#FFFF60">
						<c:choose>
							<c:when test="${subRule.active}"><img src="<c:url value="/static/images/active.png"/>" height="24" width="24"  class="masterTooltip" tip="Subrule is Active" /></c:when>
							<c:otherwise><img src="<c:url value="/static/images/disabled.png"/>" height="24" width="24"  class="masterTooltip" tip="Subrule is Disabled" /></c:otherwise>
						</c:choose>
					</td>				
					<td class="ruletabletd" bgcolor="#FFFF60">
						<input type="checkbox" name="subRuleIdsToAssign" id="subRuleIdsToAssign"  value="${subRule.id}" checked="checked"/>
					</td> 
				</c:when>
				<c:otherwise>
					<!-- <td class="ruletabletd">${subRule.id}</td>-->
					<td class="ruletabletd">
					<A href="<c:url value="/subrule/view/${subRule.id}"/>" ><c:out value="${subRule.name}"/></A>
					</td>
					<!-- <td class="ruletabletd">${subRule.description}</td>-->
					<td class="ruletabletd">
						<c:choose>
							<c:when test="${subRule.active}"><img src="<c:url value="/static/images/active.png"/>" height="24" width="24" class="masterTooltip" tip="Subrule is Active" /></c:when>
							<c:otherwise><img src="<c:url value="/static/images/disabled.png"/>" height="24" width="24" class="masterTooltip" tip="Subrule is Disabled" /></c:otherwise>
						</c:choose>
					</td>				
					<td class="ruletabletd">
						<input type="checkbox" name="subRuleIdsToAssign" id="subRuleIdsToAssign"  value="${subRule.id}" />
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

