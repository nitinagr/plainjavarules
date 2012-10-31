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

<form:form name="loginForm" id="loginForm" method="post" acceptCharset="UTF-8" action="j_spring_security_check" >
<h2 align="center">Plain Java Rules Engine</h2>
<center>

<table class="ruletable" width="50%" cellspacing="25">
	<tr>
		<td class="ruletabletd" colspan=2><form:errors path="*" cssClass="error" element="div"/></td>	
	</tr>
	<tr>
		<td class="ruletabletd">User Name</td>
		<td class="ruletabletd"><input  type="text" name="j_username" id="userName" size="30"></input></td>		
	</tr>
	<tr>
		<td class="ruletabletd">Password</td>
		<td class="ruletabletd"><input type="password" name="j_password" id="password" size="30"></input></td>
	</tr>
	<!-- 
	<tr>
		<td colspan=2 align="center">
			<input type="checkbox" name="loginAsAdmin" >Login As Admin</input>					
		</td>	
	</tr>
	 -->	
	<tr>
		<td class="ruletabletd" colspan=2 align="center">
			<A class="button" href="#" onclick="javascript:document.getElementById('loginForm').submit();" >Login</A>		
		</td>	
	</tr>	
	
	
</table>
</center>
<div style="float:right;padding-right:120px;">
Supports IE 7 &amp; above and Firefox.<br/>Best viewed in IE 8 & above and Firefox 3.0 & above
</div>
</form:form>