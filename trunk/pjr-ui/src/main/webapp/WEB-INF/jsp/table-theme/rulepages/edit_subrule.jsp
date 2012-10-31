<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
table.conditiontable {
	border-collapse: collapse;
}

.conditiontable td , .conditiontable th{
	border: 1px solid black;
}

</style>

<script type="text/javascript" >
$(function () {
	
	$('select').jec();
});
</script>
<script>
var isGenerateButtonClicked = true;
	
	$(document).ready(function(){
		
		$('#desCount').html(1000-$('#description').val().length); //To show first time how many chars left

		/**
		For textArea validation`
		*/
		$('#description').keyup(function () {
			var t = $(this);
	        var text = t.val();
	        var limit = 1000;
	        
	      //if textarea text is greater than maxlength limit, truncate and re-set text
	        if (text.length >= limit) {
	            text = text.substring(0, limit);
	            t.val(text);
			}
	        $('#desCount').html(limit-text.length);
		});
			
			/**
			For Highlighting the corresponding select to remove		
			*/


		
		jQuery("div[id=removeSelectGen]").hover(function () {
			var removeValue ="#"+ $(this).attr("removevalue");
			$(this).animate({ backgroundColor: "yellow" }, 10);
			$(removeValue).animate({ backgroundColor: "yellow" }, 10);

		},function () {
			var removeValue ="#"+ $(this).attr("removevalue");
			$(this).animate({ backgroundColor: "white" }, 10);
			$(removeValue).animate({ backgroundColor: "white" }, 10);
		});	
	
		/**
		For Highlighting the corresponding select to remove	- END		
	*/
	
		

			/*
				POP up
			**/

			$('#pop').hide();

			$('#clickme').click(function(){
				$("#pop").dialog({ height: 400,position: [1100,935]  },{ hide: { effect: 'slide', direction: "left" } },
						{show: { effect: 'slide', direction: "left" } },{ closeOnEscape: true },{ title: "Legend" } );

			});			

		
			var divCount = 0;
	
			$('#addNewCondition').click(function(){
				divCount = divCount +1 ;
				var selectId = "select"+ divCount;

				isGenerateButtonClicked = false;
				
				var $originalSelect = $('select').first();
				var $clonedSelect = $originalSelect.clone();
				$clonedSelect.attr('id',selectId);
				$clonedSelect.attr('name',selectId);
				
				$clonedSelect.each(function(index, item) {
				     //set new select to value of old select
				     $(item).val( $originalSelect.eq(index).val() );
				});

				var removeAnchorId = "anchor "+ divCount;
				var $removeAnchor = $('#removeSelect');
				var $clonedremoveAnchor = $removeAnchor.clone();
				$clonedremoveAnchor.attr("id",removeAnchorId);
				$clonedremoveAnchor.attr("name",removeAnchorId);
				$clonedremoveAnchor.css({ 'visibility': 'visible'});
				$clonedremoveAnchor.removeAttr('onclick');		
				$clonedremoveAnchor.attr('removevalue',selectId);

				
				    
				$clonedSelect.appendTo('#extraCondition');
				$clonedremoveAnchor.appendTo('#extraCondition');
				$('#extraCondition').append('<br/>');

				$clonedremoveAnchor.click(function(){
				    $clonedSelect.remove();
				    $clonedremoveAnchor.remove();
				});


				$clonedremoveAnchor.hover(function () {
					var removeValue ="#"+ $(this).attr("removevalue");
					$(this).animate({ backgroundColor: "yellow" }, 10);
					$(removeValue).animate({ backgroundColor: "yellow" }, 10);

				},function () {
					var removeValue ="#"+ $(this).attr("removevalue");
					$(this).animate({ backgroundColor: "white" }, 10);
					$(removeValue).animate({ backgroundColor: "white" }, 10);
				});	
				
				$clonedSelect.jec();
								
			});

			
			$("#loadingmsg").hide();
			$("#logicErrorDiv").hide();
			
			//Form submit actions
			$("#submitButton").click(function(){
				
				var frstVal=$("select:first option:selected").text();
				var lastVal=$("select:last option:selected").text();
				var braceError=true;
				var generateError=true;

				$("#logicError").show();
				$("#notgenerateError").show();
				
				if(frstVal=='(' && lastVal==')'){
					$("#logicError").hide();
					braceError=false;
				}
				if(isGenerateButtonClicked){
					$("#notgenerateError").hide();
					generateError=false;
				}
				if(braceError || generateError){
					//show the dialog
					$("#logicErrorDiv").dialog({
	 						resizable: false,
	 						draggable: false,
	 						modal: true,
	 						show: {effect: 'fade', duration: 500},
	 						hide: {effect: 'fade', duration: 500},
	 						buttons: {
	 							"Ok": function() {
	 								$(this).dialog("close");
	 						}
	 					}
	 				}); //dialog ends logicError
				} else {
					$('#loadingmsg').show();
					//This code is to retain the loading image for 500ms
					setTimeout(function(){
						$('#loadingmsg').hide();
						$("#editForm").submit();
						},250);
				}
			});//Form submit action end
	});
</script>

<script>


/*function loadingscreen(){
  	$('#loadingmsg').show();return true;
  }

	function submitForm( frm ){

		var selectCons = frm.getElementsByTagName('select');
		
		var firstSelectBox = selectCons[0];
		var lastSelectBox =  selectCons[selectCons.length-1];

		var toSubmit = true; 

		var firstSelectValue =firstSelectBox.options[firstSelectBox.selectedIndex].text;
		var lastSelectValue =firstSelectBox.options[lastSelectBox.selectedIndex].text; 

		
		if( !(  firstSelectValue == "(" && lastSelectValue ==")" ) ) {
			alert('The logic should always start with "(" and end with a ")" .');
			toSubmit = false;
		}

		if(!isGenerateButtonClicked){
			alert('Generate logic before you submit your changes');
			toSubmit = false;
		}
		
		if(toSubmit) frm.submit();

	}*/

	function removeSelect(id,link){
		var removeVal = link.getAttribute('value');
		if(null != removeVal) {				
			id = removeVal;
		}
			
		 		
		var select = document.getElementById(id);
		select.parentNode.removeChild(select);
		link.parentNode.removeChild(link);
	}

	function generate(){
		isGenerateButtonClicked = true;
		var divEl = document.getElementById('allConditionDiv');
		var selectCons = divEl.getElementsByTagName('select');
		var textAr = document.getElementById('subRulesLogic');
		var userRuleFormatHidTxt = document.getElementById('updatedLogicText');
	
		var tex = "";
		var objectText = "";
		
		for (var i = selectCons.length-1; i >= 0; i--) { 
				var selectCon = selectCons[i] ;						
				tex =selectCon.options[selectCon.selectedIndex].text + " " + tex;
				objectText =selectCon.options[selectCon.selectedIndex].value + "@" + objectText ;				 
		}
		textAr.value=tex;
		userRuleFormatHidTxt.value= objectText;
		
	};
	
	function clearConditonBox(){
		var ele = document.getElementById('subRulesLogic');
		ele.value="";	
		var userRuleFormatTextEle = document.getElementById('updatedLogicText');
		userRuleFormatTextEle.value="";	
	}	
</script>


<font color="#151B54">
<a href="<c:url value="/rule/view/all" />"> Home </a> &gt;&gt; Edit Subrule &gt;&gt; <b>${subrulename} </b>
</font>
<br/>
<form:form commandName="subrule" name="editForm" id="editForm" method="post" action="../edit/save"  acceptCharset="UTF-8">

<c:choose>
<c:when test="${not empty  errors  || not empty logicerrors}">
	<div class="error">
	<c:forEach items="${errors}" var="err">
		${err.defaultMessage}
		<br/>
	</c:forEach>
	
	<c:forEach items="${logicerrors}" var="err">
		${err.defaultMessage}
		<br/>
	</c:forEach>	
	</div>
</c:when>
</c:choose>
<br/><br/>
<div style="float:left;padding-left:10px;">
	<font style="font-weight:bold;color:#151B54;padding-left:10px;">Related Links</font>
	<br><br>
	 <c:choose>
	 	<c:when test="${empty  logicerrors}">	
			<ul class="nav">
				<li>
					<A href="<c:url value="/subrule/view/${subrule.id}"/>" >View</A>
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
		</c:when>
		<c:otherwise>
			<ul class="nav">
				<li>
					<A href="#" >View</A>
				</li>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li><b>|</b></li>
				<li>			
					<A href="#" >Assign/UnAssign Operators</A>
				</li>
				<li><b>|</b></li>
				<li>			
					<A href="#" >Assign/UnAssign Attribute</A>
				</li>
				</sec:authorize>							
			</ul>		
		</c:otherwise>
	</c:choose>
</div>
<div class="emptyLine" ></div><br/>
<div class="mandatorydiv">All Fields marked (<span class="mandatory" > * </span>) are manadatory.</div>
<table class="ruletable" width="100%" cellspacing="0"><!--

	<tr>
		<td><b>Rule Id</b></td>
		<td>
			<a href="../../rule/view/${condition.ruleId}" >
			${condition.ruleId}
			</a>			
		</td>		
	</tr>	
	--><tr>
		<td class="ruletabletd"><b>Subrule Id</b></td>
		<td class="ruletabletd">
			<c:out value="${subrule.id}"/>
			<input type="hidden" name="id" value="${subrule.id}"/>
		</td>
		
	</tr>
	<tr>
		<td class="ruletabletd"><b>Subrule Name</b><span class="mandatory" > * </span>
		<br/>(Note:System will replace all empty spaces with an "_")
		</td>
		<td class="ruletabletd"><input type="text" name="name" value="${subrule.name}" size="30" maxlength="100"/></td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Subrule Description</b></td>
		<td class="ruletabletd">
		 <span id="desCount"></span> characters left<br />
		 <textarea id="description" name="description" rows="20" cols="40"><c:out value="${subrule.description}"/></textarea>
		 </td>
	</tr>	
	<tr>
		<td class="ruletabletd"><b>Subrule Status</b>
		</td>
		<td class="ruletabletd"><form:checkbox path="active" value="${subrule.active}" />Enabled</td>
	</tr>
	<tr>
		<td class="ruletabletd"><b>Subrule Default value</b></td>
		<td class="ruletabletd"><form:checkbox path="defaultValue" value="${subrule.defaultValue}"/>Default</td>
	</tr>

		<tr>
			<td class="ruletabletd" valign="top"><b>Choose Logic</b> <div  id="clickme" class="makemelink">(<font size="5">?</font>Help)</div></td>
			<td class="ruletabletd" valign="top">
			
			<div id="allConditionDiv">
					<div id="conditionDiv">			
						<c:choose>
							<c:when test="${not empty  subrule.logic}">	
									<c:forEach items="${subrule.logic}" var="logicItem" varStatus="loop">
									
									
										<select id="ops${loop.index}" name="ops${loop.index}" onChange="javascript:isGenerateButtonClicked=false;">
													<c:set var="contains" value="false" />
													<c:forEach items="${srlItems}" var="srlItem" >
														<c:choose>
															<c:when test="${ logicItem.attrMapIdOrOprMapId == srlItem.attrMapIdOrOprMapId}">
																<c:set var="contains" value="true" />
															</c:when>
														</c:choose>	
													</c:forEach>	
													
													<c:forEach  items="${srlItems}" var="lo" varStatus="mapLoop">
														<c:choose>
															<c:when test="${contains}">												
																	<c:choose>
																		<c:when test="${lo.attrMapIdOrOprMapId == logicItem.attrMapIdOrOprMapId}">
																			<option value="${lo.attrMapIdOrOprMapId}" selected="selected"><c:out value="${lo.name}"/></option>						
																		</c:when>
																		<c:otherwise>
																			<option value="${lo.attrMapIdOrOprMapId}" ><c:out value="${lo.name}"/></option>
																		</c:otherwise>									
																	</c:choose>
																			
															</c:when>	
															<c:otherwise>
																<option value="${lo.attrMapIdOrOprMapId}" ><c:out value="${lo.name}"/></option>										
															</c:otherwise>								
														</c:choose>
													</c:forEach>
													<c:choose>
														<c:when test="${!contains}">
															<option value="${srlItem.attrMapIdOrOprMapId}"  selected="selected"><c:out value="${srlItem.name}"/></option>
														</c:when>
													</c:choose>																
										
										</select>	
										<c:choose><c:when test="${!loop.first}"><div  id="removeSelectGen" removevalue="ops${loop.index}" class="makemelink" style="float:right;" onclick="javascript:removeSelect('ops${loop.index}',this)">Remove</div>	</c:when></c:choose>
										<br/>				
									</c:forEach>				
								
							</c:when>				
							<c:otherwise>
								<select id="ops" name="ops" onChange="javascript:isGenerateButtonClicked=false;">
									<c:forEach items="${srlItems}" var="srlItem">
										<option value="${srlItem.attrMapIdOrOprMapId}" ><c:out value="${srlItem.name}"/></option>						
									</c:forEach>				
								</select>
										
								<br/>
							</c:otherwise>
						</c:choose>	
						
						<div  id="removeSelect" class="makemelink" style="visibility:hidden;float:right;">Remove</div>
						<div id="extraCondition"></div>				
					</div>
			</div>
			
			



			<br/><br/>
			<div class="makemelink" style="font-size:15px" id="addNewCondition" >Add New Attribute/Operator</div>
			<br/>	
			<div class="makemelink" style="font-size:15px" id="generateCondition" onclick="javascript:generate();">Generate Logic</div>
			
			<input type="hidden" value="${subrule.mappingLogicTextFromDB }" name="updatedLogicText" id="updatedLogicText"/>
				
			<div id="pop" align="left">			
				<br/>
				<br/>
				<u>Framing Sub rule logic</u>
				<br/>
				<p>			
				     1. Use parenthesis wherever necessary to ensure logical precedence.
					 <font style="font-style:italic; font-weight:bold;"> (Subrule1 And Subrule2) Or Subrule3</font><br/><br/>
					
				     2.Use logic operators like Or, Equals etc to build logic
					<font style="font-style:italic; font-weight:bold;"> Subrule1 Or Subrule2</font><br/>
				</p>
				
			</div>	


			</td>
		</tr>
		<tr>
			<td class="ruletabletd"><b>Generated Logic</b></td>
			<td class="ruletabletd">
				<textarea rows="20" columns="40"  id="subRulesLogic" name="subRulesLogic" disabled="disabled"></textarea>			
			</td>
		</tr>
	
</table>
<center>
		<font style="font-weight: bold; color: green;"><c:out value="${message}"></c:out></font>
		<br/><br/>
		 <c:choose>
		 	<c:when test="${empty  logicerrors}">
		 		<A class="button"  onclick="javascript:window.location='<c:url value="/subrule/view/all" />';" >Cancel</A>
		 	</c:when>
		 	<c:otherwise>
				<div class="button"   	href="#"> Cancel</div>	
			</c:otherwise> 
		 </c:choose>		
		<div class="button" id="submitButton" href="#" >Save</div>
		<div id="logicErrorDiv" title="Logic Formation Error(s)" align="left">
			<ul >
				<li id="logicError">The logic should always start with "(" and end with a ")".</li>
				<li id="notgenerateError">Generate logic before you submit your changes</li>
			</ul>
		</div>
		<div  id="loadingmsg" class="loadingmsg">
			<img src="<c:url value="/static/images/loading.gif"/>" height="150" width="150"/>
		</div>		
</center>


</form:form>

