<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<tiles:insertAttribute name="header" />

<style>
a {
color: blue;
}
.tooltip {
	display:none;
	position:absolute;
	border:1px solid #333;
	background-color:#161616;
	border-radius:5px;
	padding:10px;
	color:#fff;
	font-size:12px Arial;
}


.ui-widget-header{ 
	border: 1px solid #aaaaaa; 
	background: grey 50% 50% repeat-x; 
	color: white; 
	font-weight: bold; 
}

  .loadingmsg {
	  position:fixed;
	  color: black;	  
	  padding: 10px;
	  top: 50%;
	  left: 50%;
	  z-index: 100;
    }


.makemelink{
	color:blue;
	text-decoration:underline;
	cursor: hand;
	cursor: pointer; 
}
.ruletable {
	width:99%;
	border-spacing:0;
	MARGIN: 10px; 
	border-collapse:collapse;
	table-layout:fixed;
}

.ruletabletd {
	PADDING:10px; WORD-WRAP: break-word; PADDING-TOP: 10px
}

TABLE TH {
	BACKGROUND-COLOR: grey; HEIGHT: 30px; COLOR: #ffffff; FONT-WEIGHT: normal
}

.emptyLine{
	margin-top:50px;
}



textarea {
     resize: none;
     height:150px ;
     width:380px;
     }
     
.button{
	
	float:right;
	align:right;
	padding-right:150px;	
	
	CURSOR: pointer; TEXT-DECORATION: underline;
	background:url(<c:url value="/static/images/button.png"/>);
	background-repeat:no-repeat;  
    width:80px;  
    height:30px; 
	text-decoration : none;
	text-align: center;
	display:inline-block;
	margin:2px;
	padding-top: 8px;
	color: white;
}
.button:active{
	background:url(<c:url value="/static/images/button-hover.png"/>);
	background-repeat:no-repeat;  
    width:80px;  
    height:30px;
}
.mandatory{
	color: red;	
	padding:0px;	
}

.mandatorydiv{
	float:right;
	padding-right:10px;
}

.error{
	color: red;
	border:2px solid red;
	padding:10px;
	width:400px;
	padding-left:10px;
	margin-left:120px;
}

.errorBox{
	color: red;
	border:1px solid red;
	padding:10px;
}


	.nav{
	width: 100%;
	float: left;
	margin: 0 0 3em 0;
	padding: 0;
	list-style: none;
	color:#151B54;	
	}
	.nav li {
	float: left;
	padding-left:10px;
	text-color:#151B54;

	}
	.nav a {
		color:#151B54;
		text-color:#151B54;
	}

</style>
</head>
	<body >
		<div align="center">
			<table width="100%">
		 	<tr>
		  		<td valign="top" align="center" style='background:white;'>
		  			
		  				<table width="80%">
		   					<tr>
								
			
								<!-- Menu,Username,Logout Starts -->
								<td width="95%" colspan="3" align="left">
									
										<div style="border:1px solid grey;">
												
												<c:choose>
												<c:when test="${empty  logicerrors}">			
												<sec:authorize access="hasRole('ROLE_USER')">		
													<table>
														<tr>
															<td>
																<ul class="sf-menu">
																<li class="current">
																	<a href="#a">Rule</a>
																		<ul>
																		<sec:authorize access="hasRole('ROLE_ADMIN')">
																		<li>
																			<a href="<c:url value="/admin/rule/create"/>">Create</a>
																		</li>
																		</sec:authorize>
																		<li>
																			<a href="<c:url value="/rule/view/all"/>">ViewAll/Manage</a>
																		</li>
																		</ul>				
																</li>
																<li>
																<a href="#a">Sub Rule</a>
																	<ul>
																		<sec:authorize access="hasRole('ROLE_ADMIN')">
																		<li>
																			<a href="<c:url value="/admin/subrule/create"/>">Create</a>
																		</li>
																		</sec:authorize>
																		<li>
																		<a href="<c:url value="/subrule/view/all"/>">ViewAll/Manage</a>
																		</li>
																	</ul>								
																</li>
																<sec:authorize access="hasRole('ROLE_ADMIN')">
																<li>
																	<a href="#a">Attribute</a>
																<ul>
																	<li>
																		<a href="<c:url value="/admin/attribute/create"/>">Create</a>
																	</li>			
																	<li>
																		<a href="<c:url value="/admin/attribute/view/all"/>">ViewAll/Manage</a>
																	</li>		
																</ul>		
																</li>
																<li>
																	<a href="#a">Operator</a>
																<ul>												
																	<li>
																		<a href="<c:url value="/admin/operator/create"/>">Create</a>
																	</li>			
																	<li>
																		<a href="<c:url value="/admin/operator/view/all"/>">ViewAll/Manage</a>
																	</li>
																</ul>		
																</li>	
																<li>
																	<a href="#a">Model</a>
																<ul>												
																	<li>
																		<a href="<c:url value="/admin/model/create"/>">Create</a>
																	</li>			
																	<li>
																		<a href="<c:url value="/admin/model/view/all"/>">ViewAll/Manage</a>
																	</li>
																</ul>		
																</li>	
															</sec:authorize>						
																</ul>									
															</td>
														</tr>
													</table>
												</sec:authorize>
										</c:when>
										<c:otherwise>
											<sec:authorize access="hasRole('ROLE_USER')">
												<table>
													<tr>
														<td>
															<ul class="sf-menu">
															<li class="current">
																<a href="#a">Rule</a>
															<ul>
																<sec:authorize access="hasRole('ROLE_ADMIN')">
																	<li>
																		<a href="#">Create</a>
																	</li>
																</sec:authorize>
																	<li>
																		<a href="#">ViewAll/Manage</a>
																	</li>
															</ul>				
															</li>
															<li>
																<a href="#a">Sub Rule</a>
															<ul>
																<sec:authorize access="hasRole('ROLE_ADMIN')">
																	<li>
																		<a href="#">Create</a>
																	</li>
																</sec:authorize>
																	<li>
																		<a href="#">ViewAll/Manage</a>
																	</li>
															</ul>								
															</li>
															<li>
																<a href="#a">Attribute</a>
															<ul>
																<sec:authorize access="hasRole('ROLE_ADMIN')">
																	<li>
																		<a href="#">Create</a>
																	</li>
																</sec:authorize>
																	<li>
																		<a href="#">ViewAll/Manage</a>
																	</li>		
															</ul>		
															</li>
															<li>
																<a href="#a">Operator</a>
															<ul>
																<sec:authorize access="hasRole('ROLE_ADMIN')">
																	<li>
																		<a href="#">Create</a>
																	</li>
																</sec:authorize>
																	<li>
																		<a href="#">ViewAll/Manage</a>
																	</li>
															</ul>		
															</li>							
														</ul>									
													</td>
												</tr>
											</table>
									</sec:authorize>
									</c:otherwise>
								</c:choose>		
												<sec:authorize access="hasRole('ROLE_USER')">							
												<div style="float: right;padding-right:10px">
												<c:set var="username">
												<sec:authentication property="principal.username" /> 
												</c:set>
													<font style="color: black;font-weight:bold;text-transform:lowercase;"> <c:out value="${username}"/></font>
												<br/><br/>
												<c:choose>
												<c:when test="${empty  logicerrors}">
													<a 	href="<c:url value="/j_spring_security_logout" />"> Logout</a>	
												</c:when>
												<c:otherwise>
													<a 	href="#"> Logout</a>	
												</c:otherwise> 
												</c:choose>				
												</div>
												</sec:authorize>
												<h3 align="center"><tiles:insertAttribute name="flowname" ignore="true" /></h3>
												<br/>				
												<div>
													<tiles:insertAttribute name="body" />
													<div class="emptyLine" />
												</div>
												</td>
												<!-- Menu,Username,Logout Ends -->
										</div>		
		   					</tr>
		 				 </table>		  				  		
		  		</td>
		  	</tr>
		  	</table>
		  <tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>