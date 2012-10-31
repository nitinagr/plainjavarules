<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="../../rule/view/all"> Home </a> &gt;&gt; Create Condition Definition</b>
<form:form modelAttribute="condition" name="editForm" id="editForm" method="post" >
<table width="100%" cellspacing="25">

		<tr>
			<td><b>Condition Name</b></td>
			<td><input type="text" name="conditionName" id="conditionName" value="${condition.conditionName }"></input></td>
		</tr>
		
		<tr>
			<td><b>Condition Description</b></td>
			<td><input type="text" name="description" id="description" value="${condition.description }"></input></td>
		</tr>		
		
		<tr>
			<td><b>Default Value</b></td>
			<td><input type="text" name="defaultvalue" id="defaultvalue" value="${condition.defaultvalue}"></input></td>
		</tr>			
</table>

<table width="100%" cellspacing="25">

		<tr>
			<td><b>Operator Name</b></td>
			<td><b>Operator</b></td>
			<td><b>Is Applicable</b></td>
		</tr>
		
		<c:forEach items="${mappedOperators}" var="mpOps">
			<tr>
				<td>${mpOps.displayName }</td>
				<td>${mpOps.objectName}</td>
				<td><input type="checkbox" name="operators" value="${mpOps.id }" checked="checked"></input></td>
			</tr>
		</c:forEach>	
		
		
		<c:forEach items="${operators}" var="op">			
			<c:choose>
				<c:when test="${!empty mappedOperators}">
						<c:set var="contains" value="false"></c:set>
						<c:forEach items="${mappedOperators}" var="mpOps">
							<c:choose>
								<c:when test="${mpOps.id == op.id}">
									<c:set var="contains" value="true"></c:set>
								</c:when>
							</c:choose>
						</c:forEach>		
						<c:choose><c:when test="${!contains}">
							<tr>
								<td>${op.displayName }</td>
								<td>${op.objectName}</td>
								<td><input type="checkbox" name="operators" value="${op.id }">empty</input></td>
							</tr>							
						</c:when></c:choose>		
				</c:when>
				<c:otherwise>
					<tr>
						<td>${op.displayName }</td>
						<td>${op.objectName}</td>
						<td><input type="checkbox" name="operators" value="${op.id }">empty</input></td>
					</tr>					
				</c:otherwise>
			</c:choose>
		</c:forEach>				




		
		

		
					
</table>



<center>
	<input type="button" onclick="javascript:window.location='../../viewall';" value="Cancel"></input>
	<input type="submit" value="Save" ></input>
</center>
</form:form>

