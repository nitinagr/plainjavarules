<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style>
.error{
	color: red;
	border:2px solid red;
	padding:10px;
	
}
</style>

	<c:if test="${not empty error}">
		<div class="error">
			Your login attempt was not successful, try again.<br /> Caused :
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>

<form:form method="post" action="j_spring_security_check" >
<h2 align="center">Optimum FSM Rules</h2>
<table width="100%" cellspacing="25">
	<tr>
		<td colspan=2><form:errors path="*" cssClass="error" element="div"/></td>	
	</tr>
	<tr>
		<td>User Name</td>
		<td><input  type="text" name="j_username" id="userName"></input></td>		
	</tr>
	<tr>
		<td>Password</td>
		<td><input type="password" name="j_password" id="password"></input></td>
	</tr>
	<!-- 
	<tr>
		<td colspan=2 align="center">
			<input type="checkbox" name="loginAsAdmin" >Login As Admin</input>					
		</td>	
	</tr>
	 -->	
	<tr>
		<td colspan=2 align="center">
			<input type="submit" value="Login" ></input>		
		</td>	
	</tr>	
	
	
</table>
</form:form>