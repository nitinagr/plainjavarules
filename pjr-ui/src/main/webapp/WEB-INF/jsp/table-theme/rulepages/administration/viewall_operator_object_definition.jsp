<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<b><a href="../../rule/view/all"> Home </a> &gt;&gt; All Operator/Object Definitions</b>
<table width="100%" cellspacing="25">
	<tr>
		<td><b>Operator / Object Id</b></td>
		<td><b>Operator Name / Object Name</b></td>
		<td><b>Mvel / Object value</b></td>
	</tr>


	<c:forEach items="${operators }" var="operator">
		<tr>
			<td>
				${operator.id }
			</td>						
			<td>
				${operator.displayName }
			</td>	
			<td>
				${operator.objectName}
			</td>	
			<td>
				<a href="edit/${operator.id }" >Edit</a>
			</td>			
		</tr>				
	</c:forEach>


</table>


