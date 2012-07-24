<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Rule : ${userRule.userRuleName}</title>
</head>
<body>
	<h2> Custom Rules Engine Using MVEL</h2>
<table border="1">
	<tr>
		<td>Rule Id</td>
		<td>${userRule.userRuleId}</td>
	</tr>
	<tr>
		<td>Rule Name</td>
		<td>${userRule.userRuleName}</td>
	</tr>
	<tr>
		<td>Rule Text</td>
		<td>${userRule.userRuleText}</td>
	</tr>
	<tr>
		<td>Rule Text MVEL format</td>
		<td>${userRule.userRuleFormatText}</td>
	</tr>	
	<tr>		
		<td><A href="edit/${userRule.userRuleId}" >Edit</A></td>
		<td/>	
	</tr>			
</table>


</body>
</html>