<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" >
$(function () {
	
	$('select').jec();
});
</script>
<script>
	$(document).ready(function(){
			var divCount = 0;
	
			$('#addNewCondition').click(function(){
				divCount = divCount +1 ;
				var selectId = "select "+ divCount;
				
				var $originalSelect = $('select').first();
				var $clonedSelect = $originalSelect.clone();
				$clonedSelect.attr("id",selectId);
				$clonedSelect.attr("name",selectId);
				
				$clonedSelect.each(function(index, item) {
				     //set new select to value of old select
				     $(item).val( $originalSelect.eq(index).val() );
				});

				var removeAnchorId = "anchor "+ divCount;
				var $removeAnchor = $('#removeSelect');
				var $clonedremoveAnchor = $removeAnchor.clone();
				$clonedremoveAnchor.attr("id",removeAnchorId);
				$clonedremoveAnchor.attr("name",removeAnchorId);				
				
				    
				$clonedSelect.appendTo('#extraCondition');
				$clonedremoveAnchor.appendTo('#extraCondition');
				

				$clonedremoveAnchor.click(function(){
				    $clonedSelect.remove();
				    $clonedremoveAnchor.remove();
				});

				$clonedSelect.jec();
								
			});

			/*
			$('#addNewCondition').click(function(){
				divCount = divCount +1 ;
				var divId = "div "+ divCount;
				
				var $orginalDiv = $('#conditionDiv');
				var $clonedDiv = $orginalDiv.clone();
				$clonedDiv.attr("id",divId);
				
				//get original selects into a jq object
				var $originalSelects = $orginalDiv.find('select');
				
				$clonedDiv.find('select').each(function(index, item) {
				
				     //set new select to value of old select
				     $(item).val( $originalSelects.eq(index).val() );
				
				});
				    
				$clonedDiv.appendTo('#extraCondition');
			});
			*/
	});
</script>

<script>

	function removeSelect(id,link){
			var select = document.getElementById(id);
			select.parentNode.removeChild(select);
			link.parentNode.removeChild(link);
		}

	function generate(){
	
		var divEl = document.getElementById('allConditionDiv');
		var selectCons = divEl.getElementsByTagName('select');
		var textAr = document.getElementById('conditionDisplay');
		var userRuleFormatHidTxt = document.getElementById('conditionMvel');
	
		var tex = textAr.value;
		var objectText = "";
		
		for (var i = selectCons.length-1; i >= 0; i--) { 
				var selectCon = selectCons[i] ;						
				tex =selectCon.options[selectCon.selectedIndex].text + " " + tex;
				objectText =selectCon.options[selectCon.selectedIndex].value + " " + objectText ;				 
		}
		textAr.value=tex;
		userRuleFormatHidTxt.value= objectText;
		
	};
	
	function clearConditonBox(){
		var ele = document.getElementById('conditionDisplay');
		ele.value="";	
		var userRuleFormatTextEle = document.getElementById('conditionMvel');
		userRuleFormatTextEle.value="";	
	}	

	function verify(){
		var textAr = document.getElementById('conditionDisplay');
		textAr.disabled="";
		return true;
	}
</script>
<b><a href="../../rule/view/all"> Home </a> &gt;&gt; Edit Condition</b>
<form:form modelAttribute="condition" name="editForm" id="editForm" method="post" action="save?ruleId=${condition.ruleId}">
<table width="100%" cellspacing="25">

	<tr>
		<td><b>Rule Id</b></td>
		<td>
			<a href="../../rule/view/${condition.ruleId}" >
			${condition.ruleId}
			</a>			
		</td>		
	</tr>	
	<tr>
		<td><b>Condition Id</b></td>
		<td>
			${condition.conditionId}
			<input type="hidden" name="conditionId" value="${condition.conditionId}"/>
		</td>
		
	</tr>
	<tr>
		<td><b>Condition Name</b></td>
		<td>${condition.displayName}</td>
	</tr>
	<tr>
		<td><b>Condition Status</b>
		</td>
		<td><form:checkbox path="enable" value="${condition.enable}" />Enabled</td>
	</tr>		
	<tr>
		<td valign="top"><b>Choose Logic</b></td>
		<td valign="top">
		<div id="allConditionDiv">
				<div id="conditionDiv">
				<c:forEach items="${condition.conditionDisplayInArrayFormat}" var="conditionBusinessFriendlyTextArrayItem" varStatus="loop">				
					<select id="ops${loop.index}" name="ops" varStatus="loop">
					<c:forEach  items="${conditionPtyMappingList}" var="conditionPtyMapping" varStatus="mapLoop">
						<option value="${conditionPtyMapping.objectName}" <c:choose><c:when test="${conditionBusinessFriendlyTextArrayItem == conditionPtyMapping.displayName}"> selected="selected" </c:when></c:choose>						
						 >${conditionPtyMapping.displayName}
						 </option>						 
						 <c:if test="${loop.last && mapLoop.last}">
						 <option value=${conditionBusinessFriendlyTextArrayItem } selected="selected">${conditionBusinessFriendlyTextArrayItem}</option>
						 </c:if>
						 
					</c:forEach>
					</select>
					 
					<a href="#" id="removeSelect" <c:if test="${!loop.first}">onclick="javascript:removeSelect('ops${loop.index}',this)"</c:if>>Remove</a>					
				</c:forEach>

				</div>
				<div id="extraCondition"></div>
		</div>
		<br/>
		<input type="button" id="addNewCondition" value="Add New Condition" />	
		<input type="button" value="Generate Condition" id="generateCondition" onclick="javascript:generate();"/>		
		</td>
		
	</tr>

	<tr>
		<td>
		<b>Generated Condition</b>
		</td>
		<td>
		<textarea cols="20" rows="10" id="conditionDisplay" name="conditionDisplay"  disabled="disabled">${condition.conditionDisplay}</textarea>		
		<br/>
		<input type="button" id="clearConditionBox" value="Clear Condition Box" onclick="javascript:clearConditonBox();"/>		
		</td>
	</tr>	
	<tr>
		<td>
			<b>Condition in DB </b>
		</td>
		<td>
			<textarea cols="20" rows="10"   disabled="disabled">${condition.conditionDisplay}</textarea>
		</td>		
	</tr>		
	<tr>
		<td><b>Condition Text MVEL format</b></td>
		<td>${condition.conditionMvel}
			<input type="hidden" name="conditionMvel" id="conditionMvel"  value="${condition.conditionMvel}"/>
		</td>
		
	</tr>				
</table>
<center>
	<input type="button" onclick="javascript:window.location='../view/${condition.conditionId}?ruleId=${condition.ruleId}';" value="Cancel"></input>
	<input type="submit" value="Save" onclick="javascript:verify();"></input>
</center>
</form:form>

