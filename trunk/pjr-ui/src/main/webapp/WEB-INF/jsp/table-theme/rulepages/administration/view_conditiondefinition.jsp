<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="../../rule/view/all"> Home </a> &gt;&gt; Create Condition Definition</b>

<table width="100%" cellspacing="25">
		<tr>
			<td><b>Condition Name</b></td>
			<td>${condition.conditionName }</td>
		</tr>
		
		<tr>
			<td><b>Condition Description</b></td>
			<td>${condition.description }</td>
		</tr>		
		
		<tr>
			<td><b>Default Value</b></td>
			<td>${condition.defaultvalue}</td>
		</tr>		
		
		<tr>			
			<td colspan="2">						
				<a href="define/edit/${condition.conditionId}">Edit</a>
			</td>		
		</tr>		
</table>



