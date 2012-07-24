<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<style type="text/css">
table.conditiontable {
	border-collapse: collapse;
}

.conditiontable td , .conditiontable th{
	border: 1px solid black;
}
</style>

<script type="text/javascript">

	function submitEditConditionMappingForm(){		
			var form = document.getElementById('editConditionMappingForm');			
			form.submit();
	}
</script>

<!-- <h2>Custom Rules Engine Using MVEL</h2> -->

<b><a href="../view/all"> Home </a> &gt;&gt; Edit Rule</b>   
<form:form modelAttribute="rule" name="editForm" id="editForm"
	method="post" action="save">

	<table width="100%" cellspacing="25" >
		<tr>
			<td><b>Rule Id</b>
			</td>
			<td>${rule.ruleId}</td>
			<input type="hidden" name="ruleId" value="${rule.ruleId}" />
		</tr>	
		<tr>
			<td><b>Rule Name</b>
			</td>
			<td><input type="text" name="ruleName"
				value="${rule.ruleName}" />
			</td>
		</tr>
		<tr>
			<td><b>Rule Description</b>
			</td>
			<td><input type="text" name="ruleDes"
				value="${rule.ruleDes}" />
			</td>
		</tr>
		<tr>
			<td><b>Rule Status</b>
			</td>
			<td><form:checkbox path="enable" value="${rule.enable}" />Enabled
			</td>

		</tr>			
		</table>
	<center>
		<input type="button" onclick="javascript:window.location='../view/${rule.ruleId}';" value="Cancel"></input>
		<input type="submit" value="Save" ></input>
	</center>		


<table width="100%" cellspacing="25" >	
		<tr>
			<td valign="top"><b>Conditions Applicable</b>
			</td>
			<td valign="top">		
					<table cellspacing="5" class="conditiontable">
						<tr>
							<td><b>Name</b>
							</td>
							<td><b>Condition Text</b>
							</td>
							<td><b>Condition Enabled ?</b>
							</td>
							<td/>
						</tr>
						<c:forEach items="${rule.conditionList}"
							var="conditionVar" varStatus="mapStatus">
							<tr>
								<td>${conditionVar.displayName }								
								</td>
								<td>${conditionVar.conditionDisplay }</td>
								<td>
									<c:choose>
										<c:when test="${condition.enable}">Enabled</c:when>
										<c:otherwise>Disabled</c:otherwise>
									</c:choose>									
								</td>
								<td>
									<a href="../../condition/view/${conditionVar.conditionId}?ruleId=${conditionVar.ruleId}" >View</a>
									<a href="../../condition/edit/${conditionVar.conditionId}?ruleId=${conditionVar.ruleId}" >Edit</a>
								</td>										
							</tr>
						</c:forEach>					
		
					</table>

				<br /></td>

			<td></td>

		</tr>

	</table>						

</form:form>	