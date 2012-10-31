<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value="/static/js/jquery.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/jquery.jec.js"/>"></script>
<script type="text/javascript" src="<c:url value="/static/js/jquery-ui.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/jquery/jquery-ui.css"/>" />

<script type="text/javascript" src="<c:url value="/static/menu/js/hoverIntent.js"/>"></script>
<script type="text/javascript" src="<c:url value="/static/menu/js/superfish.js"/>"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/static/menu/css/superfish.css"/>" media="screen">


<script type="text/javascript">

// initialise plugins
jQuery(function(){
	jQuery('ul.sf-menu').superfish();
});

</script>
  
<style>

body {
    color: #555555;
    font-family: verdana,arial,sans-serif;
    font-size: 11px;
    text-align: center;
}
</style>