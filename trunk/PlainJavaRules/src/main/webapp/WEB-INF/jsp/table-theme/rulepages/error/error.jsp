<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>


<style>
.errorStyle { 
   white-space: pre-wrap;      /* CSS3 */   
   white-space: -moz-pre-wrap; /* Firefox */    
   white-space: -pre-wrap;     /* Opera <7 */   
   white-space: -o-pre-wrap;   /* Opera 7 */    
   word-wrap: break-word;      /* IE */
   border:5px solid DodgerBlue;width:380pt;
}

</style>

<script type="text/javascript">
$(document).ready(function(){
	
	$('#showlink').click(function(){
		$("#errorDiv").toggle('slow');		
	});	
});
</script>
<h2><tiles:insertAttribute name="flowname" ignore="true" /></h2>

<c:if test="${not empty stack}">
	Copy the below stack trace and please email it to application support team.
	<br/>
	<a href="#" id="showlink" >Show Errors</a>
	<br/><br/>
	<div id="errorDiv" class="errorStyle" style="display:none;">
	<c:out value="${stack}"></c:out>
	</div>    
</c:if>


