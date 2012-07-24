<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<b><a href="../../rule/view/all"> Home </a> &gt;&gt; View Condition</b>
<table cellspacing="25">
	<tr>
		<td><b>Rule Id</b></td>
		<td>
			<a href="../../rule/view/${ruleId}" >
			${ruleId}
			</a>			
		</td>		
	</tr>
	<tr>
		<td><b>Condition Id</b></td>
		<td>${condition.conditionId}</td>
	</tr>
	<tr>
		<td><b>Condition Name</b></td>
		<td>${condition.displayName}</b></td>
	</tr>	
	<tr>
		<td><b>Condition Text</b></td>
		<td>${condition.conditionDisplay}</td>
	</tr>
	<tr>
		<td><b>Condition Text MVEL format</b></td>
		<td>${condition.conditionMvel}</td>
	</tr>
		
	<tr>		
		<td colspan="2" align="center"><A href="../edit/${condition.conditionId}?ruleId=${ruleId}" >Edit</A></td>		
	</tr>			
</table>
