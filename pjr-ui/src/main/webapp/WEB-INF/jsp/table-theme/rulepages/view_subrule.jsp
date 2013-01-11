<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<font color="#151B54">
<a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; View Subrule &gt;&gt; <b>${subrule.name} </b>
</font>
<br/><br/>
<div style="float:left;padding-left:10px;">
	<font style="font-weight:bold;color:#151B54;padding-left:10px;">Related Links</font>
	<br><br>
	<ul class="nav">
		<li>
			<A href="<c:url value="/subrule/edit/${subrule.id}"/>" >Edit</A>
		</li>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li><b>|</b></li>
		<li>			
			<A href="<c:url value="/admin/operator/view/subrule/${subrule.id}"/>" >Assign/UnAssign Operators</A>
		</li>
		<li><b>|</b></li>
		<li>			
			<A href="<c:url value="/admin/attribute/view/subrule/${subrule.id}"/>" >Assign/UnAssign Attribute</A>
		</li>
		</sec:authorize>							
	</ul>
</div>
<div class="emptyLine" ></div><br/>
<table cellspacing="0" width="100%" class="ruletable">
<!--
	<tr>
		<td class="ruletabletd"><b>Rule Id</b></td>
		<td>
			<a href="../../rule/view/${condition.ruleId}" >
			${condition.ruleId}
			</a>			
		</td>		
	</tr>
	--><tr>
		<td class="ruletabletd"><b>Subrule Id</b></td>
		<td class="ruletabletd"><c:out value="${subrule.id}" /></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Subrule Name</b></td>
		<td class="ruletabletd"><c:out value="${subrule.name}" /></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Subrule Description</b></td>
		<td class="ruletabletd"><c:out value="${subrule.description}" /></td>
	</tr>		
	<tr>
		<td class="ruletabletd"><b>Subrule defaultvalue</b></td>
		<td class="ruletabletd"><c:out value="${subrule.defaultValue}" /></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Subrule Status</b></td>
		<td class="ruletabletd">
			<c:choose>
				<c:when test="${subrule.active}">Enabled</c:when>
				<c:otherwise>Disabled</c:otherwise>
			</c:choose>		
		</td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Model Class: </b><span class="mandatory" > * </span>
		</td>
		<td class="ruletabletd">
			<c:forEach items="${modelClasses}" var="modelClass" >
				<c:choose>
					<c:when test="${modelClass.model_id == subrule.modelId }">
						${modelClass.model_class_name }							
					</c:when>						
				</c:choose>					
			</c:forEach>
		</td>		
	</tr>				
	<tr>
		<td class="ruletabletd"><b>Current Logic in DB</b></td>
		<td class="ruletabletd">
			<textarea rows="20" columns="40"  disabled="disabled"><c:out value="${subrule.logicText}"/></textarea>
		</td>
	</tr>
		
</table>
<center>
		<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
		<br/><br/>
		<A class="button" href="<c:url value="/subrule/view/all" />">Back</A>
		<A class="button" href="<c:url value="/subrule/edit/${subrule.id}"/>" >Edit</A>
</center>
