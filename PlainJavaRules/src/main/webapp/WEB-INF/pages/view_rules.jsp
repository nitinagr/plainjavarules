<%@ page language="java" contentType="text/html; charset=ISO-8859-1"     pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Rules</title>
</head>
<body>
	<h2> Custom Rules Engine Using MVEL</h2>
<table>
	<c:forEach items="${rulesList}" var="ruleItem">
		<tr>
			<td>Rule Name</td>
			<td>${ruleItem.userRuleName}</td>
			<td><A href="${ruleItem.userRuleId}" >View</A></td>
			<td><A href="edit/${ruleItem.userRuleId}" >Edit</A></td>		
		</tr>
	</c:forEach>
	
		

					
</table>


</body>
</html>