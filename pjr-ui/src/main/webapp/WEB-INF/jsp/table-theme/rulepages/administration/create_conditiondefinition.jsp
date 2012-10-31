<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="../../rule/view/all"> Home </a> &gt;&gt; Create Condition Definition</b>
<form:form modelAttribute="condition" name="editForm" id="editForm" method="post" >
<table width="100%" cellspacing="25">

	<tr>
		<td><b>Condition Name(unique)</b></td>
		<td>
			<input type="text" name="conditionName" id="conditionName"></input>
		</td>		
	</tr>	
	<tr>
		<td><b>Condition Description</b></td>
		<td>
			<input type="text" name="description" id="description"></input>
		</td>		
	</tr>

	<tr>
		<td><b>Default value</b></td>
		<td>
			<input type="text" name="defaultvalue" id="defaultvalue"></input>
		</td>		
	</tr>		
	
</table>
<center>
	<input type="button" onclick="javascript:window.location='';" value="Cancel"></input>
	<input type="submit" value="Save" ></input>
</center>
</form:form>

