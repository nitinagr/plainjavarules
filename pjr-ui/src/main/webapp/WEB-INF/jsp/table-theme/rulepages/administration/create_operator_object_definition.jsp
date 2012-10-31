<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="../../rule/view/all"> Home </a> &gt;&gt; Create Condition Definition</b>
<form:form modelAttribute="operator" name="editForm" id="editForm" method="post" >
<table width="100%" cellspacing="25">

	<tr>
		<td><b>Operator Name / Object Name</b></td>
		<td>
			<input type="text" name="displayName" id="displayName" value="${operator.displayName }"></input>
		</td>		
	</tr>	
	<tr>
		<td><b>Mvel / Object value</b></td>
		<td>
			<input type="text" name="objectName" id="objectName" value="${operator.objectName }"></input>
		</td>		
	</tr>
</table>
<center>
	<input type="button" onclick="javascript:window.location='';" value="Cancel"></input>
	<input type="submit" value="Save" ></input>
</center>
</form:form>

