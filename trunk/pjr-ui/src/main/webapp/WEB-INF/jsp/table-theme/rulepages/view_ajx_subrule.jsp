<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<p><b>Default value: </b><c:out value="${subrule.defaultValue}" /></p>
<p><b>Status: </b><c:choose>
				<c:when test="${subrule.active}">Enabled</c:when>
				<c:otherwise>Disabled</c:otherwise>
			</c:choose>		
</p>
<p><b>Logic: </b><font ><c:out value="${subrule.logicText}"/></font></p>

<%-- <table cellspacing="0" width="20%" class="ruletable">
	<tr>
		<td class="ruletabletd"><b>Default value:</b></td>
		<td class="ruletabletd"><c:out value="${subrule.defaultValue}" /></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Status:</b></td>
		<td class="ruletabletd">
			<c:choose>
				<c:when test="${subrule.active}">Enabled</c:when>
				<c:otherwise>Disabled</c:otherwise>
			</c:choose>		
		</td>
	</tr>				
	<tr>
		<td class="ruletabletd"><b>Current Logic:</b></td>
		<td class="ruletabletd">
			<textarea rows="20" columns="20"  disabled="disabled" readonly="readonly"><c:out value="${subrule.logicText}"/></textarea>
		</td>
	</tr>
</table> --%>

