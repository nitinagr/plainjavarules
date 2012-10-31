<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<script type="text/javascript">
$(document).ready(function() {
	//Dialog box hide
    $("#dialog").hide();
    
    //Delete confirmation starts
    $(".delete").click(function(){
    	var $input = $(this);
		$("#dialog").dialog({
			resizable: false,
			draggable: false,
			modal: true,
			show: {effect: 'fade', duration: 500},
			hide: {effect: 'fade', duration: 500},
			buttons: {
				"Yes": function() {
					url=$input.attr("name");
					window.location.href = url;
				},
				"No": function() {
					$(this).dialog("close");
				}
			}
		}); // dialog <ENDS>
     });
    //Delete confirmation ends
    
});
</script>

<b><a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; All Attribute/Object Definitions</b>
<br/>
<table cellspacing="0" width="100%" class="ruletable">
	<tr>
		<!-- <th>Attribute / Object Id</th>-->
		<th align="left" class=ruletableth style="width:250px;">Attribute Name / Object Name</th>
		<th align="left" class=ruletableth style="width:250px;">Mvel / Object value</th>
		<th align="left" class=ruletableth  style="width:100px;"></th>
		<th align="left" class=ruletableth style="width:100px;"></th>
		<th align="left"class=ruletableth style="width:100px;"></th>
	</tr>


	<c:forEach items="${attributes}" var="attribute">
		<tr>
			<!--<td class="ruletabletd">
				${attribute.attributeId}
			</td>-->				
			<td class="ruletabletd">
				<c:out value="${attribute.attributeName}"/>
			</td>	
			<td class="ruletabletd">
				<c:out value="${attribute.value}"/>
			</td>	
			<td class="ruletabletd">
				<a href="<c:url value="/admin/attribute/view/${attribute.attributeId}" />" >View</a>
			</td>
			<td class="ruletabletd">
				<a href="<c:url value="/admin/attribute/edit/${attribute.attributeId}" />" >Edit</a>
			</td>	
			<td class="ruletabletd">				
				<A class="delete" href="#" name="<c:url value="/admin/attribute/delete/${attribute.attributeId}"/>" >Delete</A>
			</td>				
		</tr>				
	</c:forEach>
</table>
<div id="dialog" title="Confirming Delete">Are you sure you want to Delete?</div>
<center>
	<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
	<br/><br/>
</center>


