<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
table.conditiontable {
	border-collapse: collapse;
}

.conditiontable td , .conditiontable th{
	border: 1px solid black;
}

</style>
<script type="text/javascript" src="<c:url value="/static/js/pjr_common.js.js" />"></script>
<script type="text/javascript" >
$(function () {
	
	$('select').jec();
});
</script>
<script>
var isGenerateButtonClicked = true;

	$(document).ready(function(){	
		
		$('#desCount').html(1000-$('#ruleDes').val().length); //To show first time how many chars left

		/**
		For textArea validation`
		*/
		$('#ruleDes').keyup(function () {
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
		var removeValue = '#'+$(this).attr("removevalue");			
		$(this).animate({ backgroundColor: "yellow" }, 10);
		$(removeValue).animate({ backgroundColor: "yellow" }, 10);
	},function () {
		var removeValue ='#'+ $(this).attr("removevalue");			
		$(this).animate({ backgroundColor: "white" }, 10);
		$(removeValue).animate({ backgroundColor: "white" }, 10);
	});	

	jQuery("div[id=insertbelow]").hover(function () {
		var removeValue = '#'+$(this).attr("belowvalue");			
		$(this).animate({ backgroundColor: "yellow" }, 10);
		$(removeValue).animate({ backgroundColor: "yellow" }, 10);
	},function () {
		var removeValue ='#'+ $(this).attr("belowvalue");			
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


			$('div.insertBelowClass').click(function(){
				var subruleid ="#"+ $(this).attr("ruleid");
				var index = $(this).attr("index");				
				$('#index').attr('value',index);

				var divEl = document.getElementById('allConditionDiv');
				var selectCons = divEl.getElementsByTagName('select');

				
				var objectText = "";
				var textAr = document.getElementById('subRulesLogic');
				for (var i = selectCons.length-1; i >= 0; i--) { 
						var selectCon = selectCons[i] ;
						objectText =selectCon.options[selectCon.selectedIndex].value + "@" + objectText ;				 
				}
					
				document.getElementById('logicChange').value= objectText;								
				document.getElementById('editForm').submit();
						
			});
			$('div.removeSelectGen').click(function(){
				var subruleid ="#"+ $(this).attr("ruleid");
				var index = $(this).attr("index");
				document.getElementById('removeindex').value= index;

				var divEl = document.getElementById('allConditionDiv');
				var selectCons = divEl.getElementsByTagName('select');
				var objectText = "";
				var textAr = document.getElementById('subRulesLogic');
				for (var i = selectCons.length-1; i >= 0; i--) { 
						var selectCon = selectCons[i] ;
						objectText =selectCon.options[selectCon.selectedIndex].value + "@" + objectText ;				 
				}
					
				document.getElementById('logicChange').value= objectText;								
				document.getElementById('editForm').submit();				
			});			

			
			$("#loadingmsg").hide();
			$("#logicErrorDiv").hide();
			$("#braceError").hide();
			$("#disableRuleDiv").hide();
			
			//Form submit actions
			$("#submitButton").click(function(){
				var logicText=document.getElementById('updatedLogicText').value;//contains the value of the logic
				var generatedLogicText=document.getElementById('subRulesLogic').value;
				
				var frstVal=$("select:first option:selected").text();
				var lastVal=$("select:last option:selected").text();
				var braceError=true;
				var invalidBrace=true;
				var generateError=true;
				var formSubmit=false;
				
				$("#logicError").show();
				$("#notgenerateError").show();
				$("#braceError").show();
				
				if(frstVal=='(' && lastVal==')'){
					$("#logicError").hide();
					braceError=false;
					if(checkParenthesis(generatedLogicText)){
						$("#braceError").hide();
						invalidBrace=false;
					}
				} 
				if(isGenerateButtonClicked){
					$("#notgenerateError").hide();
					generateError=false;
				}
				

				//This if block checks whether rule logic is blank or not
				if(logicText==null || logicText==''){
					//check if the rule is disabled

					var isEnabled=$('input:checkbox[id=enableCheck]').is(':checked');//This boolean represntsthe value of the "active" checkbox
					if(isEnabled){
						//Show error to disable the rule
						$("#disableRuleDiv").dialog({
							resizable: false,
	 						draggable: false,
	 						modal: true,
	 						show: {effect: 'fade', duration: 500},
	 						hide: {effect: 'fade', duration: 500},
	 						buttons: {
	 							"OK": function() {
	 								$(this).dialog("close");
	 							}	
	 						}
						});//disableRuleDiv dialog ends
					} else {
						//Submit the form
						formSubmit=true;
					}
				} else {
					//Rule has logic
					//Check other validations
					if(braceError || generateError || invalidBrace){
						//show the dialog
						$("#logicErrorDiv").dialog({
		 						resizable: false,
		 						draggable: false,
		 						modal: true,
		 						show: {effect: 'fade', duration: 500},
		 						hide: {effect: 'fade', duration: 500},
		 						buttons: {
		 							"OK": function() {
		 								$(this).dialog("close");
		 						}
		 					}
		 				}); //dialog ends logicError
					} else {
						formSubmit=true;
					}
				}

				//Code to actually submitting the form
				if(formSubmit){
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

	function generate(){
	
		var divEl = document.getElementById('allConditionDiv');
		var selectCons = divEl.getElementsByTagName('select');
		var textAr = document.getElementById('subRulesLogic');
		var userRuleFormatHidTxt = document.getElementById('updatedLogicText');
		isGenerateButtonClicked = true;
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

<!-- <h2>Custom Rules Engine Using MVEL</h2> -->
<font color="#151B54">
<a href="<c:url value="/rule/view/all"/>"> Home </a> &gt;&gt; Edit Rule  &gt;&gt; <b>${rule.ruleName}</b>
</font>   
<br/>
<form:form commandName="rule" name="editForm" id="editForm" acceptCharset="UTF-8"
	method="post" action="save">
<c:choose>
<c:when test="${not empty  errors}">
	<div class="error">
	<c:forEach items="${errors}" var="err">
		${err.defaultMessage}
		<br/>
	</c:forEach>
	</div>
</c:when>
</c:choose>
<br/>
<br/>
<!--<div id="notgenerateError" title="Not Generated Logic Error">Generate logic before you submit your changes</div>
-->
<div style="float:left;padding-left:10px;">
	<font style="font-weight:bold;color:#151B54;padding-left:10px;">Related Links</font>
	<br><br>
	<ul class="nav">
		<li>
			<A  href="<c:url value="/rule/view/${rule.ruleId}"/>" >View</A>
		</li>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li><b>|</b></li>
		<li>			
			<A   href="<c:url value="/admin/subrule/view/rule/${rule.ruleId}"/>" >Assign/ UnAssign Subrules</A>
		</li>
		<li><b>|</b></li>
		<li>			
			<A href="<c:url value="/admin/operator/view/rule/${rule.ruleId}"/>" >Assign/UnAssign Operators</A>
		</li>
		</sec:authorize>							
	</ul>
</div>
<div class="emptyLine" ></div><br/>
<div class="mandatorydiv">All Fields marked (<span class="mandatory" > * </span>) are manadatory.</div>
<br/>
		
	<table width="100%" cellspacing="0" class="ruletable">
		<tr>
			<td class="ruletabletd"><b>Rule Id</b>
			</td>
			<td class="ruletabletd">
				<c:out value="${rule.ruleId}"/>
				<input type="hidden" name="ruleId" value="${rule.ruleId}" />
			</td>
			
		</tr>	
		<tr>
			<td class="ruletabletd"><b>Rule Name</b><span class="mandatory" > * </span>
			<br/>(Note:System will replace all empty spaces with an "_")
			</td>
			<td class="ruletabletd"><input type="text" name="ruleName"	value="${rule.ruleName}" maxlength="30"/>
			<form:errors path="ruleName" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td class="ruletabletd"><b>Rule Description</b>
			</td>
			<td class="ruletabletd">
			<span id="desCount"></span> characters left<br />
			<textarea id="ruleDes" name="ruleDes" rows="20" columns="40" ><c:out value="${rule.ruleDes}"/></textarea>
			</td>
		</tr>
		<tr>
			<td class="ruletabletd"><b>Rule Status</b><span class="mandatory" > * </span>
			</td>
			<td class="ruletabletd"><form:checkbox id="enableCheck" path="active" value="${rule.active}" />Enabled</td>

		</tr>	
		
		<tr>
			<td class="ruletabletd"><b>Return Value</b><span class="mandatory" > * </span></td>		
			<td class="ruletabletd"><input type="text" name="returnValue" value="${rule.returnValue}" /></td>
		</tr>
		
		<tr>
			<td class="ruletabletd"><b>Execution Order</b><span class="mandatory" > * </span></td>		
			<td class="ruletabletd"><input type="text" name="executionOrder" value="${rule.executionOrder}" /></td>
		</tr>				
		<tr>
			<td class="ruletabletd"><b>Model Class: </b><span class="mandatory" > * </span>
			</td>
			<td class="ruletabletd">
					<c:forEach items="${modelClasses}" var="modelClass" >
						<c:choose>
							<c:when test="${modelClass.model_id == rule.modelId }">
								${modelClass.model_class_name }
							</c:when>
						</c:choose>					
					</c:forEach>
			</td>		
		</tr>	
		<tr>
			<td class="ruletabletd" ><b>Current Logic in DB</b></td>
			<td class="ruletabletd">
				<textarea rows="20" columns="40"  disabled="disabled">${rule.logicText}</textarea>
			</td>
		</tr>		
		
		<tr>
			<td class="ruletabletd" valign="top"><b>Choose Logic</b> <div  id="clickme" class="makemelink">(<font size="5">?</font>Help)</div></td>
			<td class="ruletabletd" valign="top">
			
			<div id="allConditionDiv">
					<div id="conditionDiv">			
						<c:choose>
							<c:when test="${not empty  rule.logic}">	
									<c:forEach items="${rule.logic}" var="logicItem" varStatus="loop">
									
									
										<select id="ops${loop.index}" name="ops${loop.index}" onChange="javascript:isGenerateButtonClicked=false;">
													<c:set var="contains" value="false" />
													<c:forEach items="${rlItems}" var="rlItem" >
														<c:choose>
															<c:when test="${ logicItem.subRuleMapIdOrOprMapId == rlItem.subRuleMapIdOrOprMapId}">
																<c:set var="contains" value="true" />
															</c:when>
														</c:choose>	
													</c:forEach>	
													
													<c:forEach  items="${rlItems}" var="lo" varStatus="mapLoop">
														<c:choose>
															<c:when test="${contains}">												
																	<c:choose>
																		<c:when test="${lo.subRuleMapIdOrOprMapId == logicItem.subRuleMapIdOrOprMapId}">
																			<option value="${lo.subRuleMapIdOrOprMapId}" selected="selected"><c:out value="${lo.name}"/></option>						
																		</c:when>
																		<c:otherwise>
																			<option value="${lo.subRuleMapIdOrOprMapId}" ><c:out value="${lo.name}"/></option>
																		</c:otherwise>									
																	</c:choose>
																			
															</c:when>	
															<c:otherwise>
																<option value="${lo.subRuleMapIdOrOprMapId}" ><c:out value="${lo.name}"/></option>										
															</c:otherwise>								
														</c:choose>
													</c:forEach>
													<c:choose>
														<c:when test="${!contains}">
															<option value="${rlItem.subRuleMapIdOrOprMapId}"  selected="selected"><c:out value="${rlItem.name}"/></option>
														</c:when>
													</c:choose>																
										
										</select>	
										<c:choose><c:when test="${!loop.first}">											
											<div  id="removeSelectGen" removevalue="ops${loop.index}" class="makemelink removeSelectGen" style="float:right; margin-right: 10px;" ruleid="${rule.ruleId }" index="${loop.index}" >Remove</div>										
										</c:when></c:choose>
											<div style="visibility: visible; float: right; margin-right: 10px;" class="makemelink insertBelowClass" id="insertbelow" belowvalue="ops${loop.index}" name="insertbelow${loop.index}"  ruleid="${rule.ruleId }" index="${loop.index}" >Insert below</div>
										<br/>				
									</c:forEach>
							</c:when>			
							<c:otherwise>
								<select id="ops" name="ops" onChange="javascript:isGenerateButtonClicked=false;">
									<c:forEach items="${rlItems}" var="rlItem">
										<option value="${rlItem.subRuleMapIdOrOprMapId}" ><c:out value="${rlItem.name}"/></option>						
									</c:forEach>				
								</select>
								<div style="visibility: visible; float: right; margin-right: 10px;" class="makemelink insertBelowClass" id="insertbelow1" name="insertbelow0"  ruleid="${rule.ruleId }" index="0" >Insert below</div>
								<br/>
							</c:otherwise>
						</c:choose>
						<div id="extraCondition"></div>				
					</div>
			</div>
			
			<br/><br/>
						<input type="hidden" name="index" id="index" value="" />
						<input type="hidden" name="removeindex" id="removeindex" value="" />
						<input type="hidden" name="logicChange" id="logicChange" value="" />
									
			<div class="makemelink"  style="font-size:15px" id="generateCondition" onclick="javascript:generate();">Generate Logic</div>
			<div class="makemelink" style="font-size:15px" id="clearlogic" onclick="javascript:clearConditonBox();">Clear Logic</div>
			
			<input type="hidden" value="${rule.mappingLogicTextFromDB }" name="updatedLogicText" id="updatedLogicText"/>
				
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
		<div id="disableRuleDiv" title="Logic Empty Warning" align="left">
			The Rule does not have any logic please <b>"Disable"</b> the rule and then save.
		</div>
		<div id="logicErrorDiv" title="Logic Formation Error(s)" align="left">
			<ul >
				<li id="logicError">The logic should always start with "(" and end with a ")".</li>
				<li id="braceError">Check for any brace "(" or ")" mismatch in Logic.</li>
				<li id="notgenerateError">Generate logic before you submit your changes</li>
			</ul>
		</div>
		<div  id="loadingmsg" class="loadingmsg">
			<img src="<c:url value="/static/images/loading.gif"/>" height="150" width="150"/>
		</div>	
</center>			
		<div class="button" href="#" onclick="javascript:window.location='<c:url value="/rule/view/${rule.ruleId}"/>';" >Cancel</div>
		<div class="button" id="submitButton" href="#">Save</div>
</form:form>	