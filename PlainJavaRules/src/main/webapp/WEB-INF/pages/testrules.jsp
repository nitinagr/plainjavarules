<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MVEL Test</title>
</head>
<body>
<form action="votesubmit" method="post">
	
	<table>
		<tr>
			<td>User Name</td>
			<td><input type="text" id="userName" name="userName" /></td>			
		</tr>
		<tr>
			<td>Age</td>
			<td><input type="text" id="age" name="age" /></td>
		</tr>	
		<tr>
			<td>Allowed Age</td>
			<td><input type="text" id="allowedAge" name="allowedAge" /></td>
		</tr>			
	</table>
	<input type="submit" value="Submit" />
	
	<br/>
	<br/>
	Eligibility: <c:out value="${msg}"></c:out>
	
	<a href="../rules/view/edit/1">Edit the Rule Applied to this Flow</a>
	
</form>

</body>
</html>