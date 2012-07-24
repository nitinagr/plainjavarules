<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>

<script>
	$(document).ready(function(){

			var divCount = 0;
	


			$('#addNewCondition').click(function(){
				divCount = divCount +1 ;
				var selectId = "select "+ divCount;
				
				var $originalSelect = $('#ops');
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
	function generate(){
	
		var divEl = document.getElementById('allConditionDiv');
		var selectCons = divEl.getElementsByTagName('select');
		var textAr = document.getElementById('userRuleText');
		var userRuleFormatHidTxt = document.getElementById('userRuleFormatText');
	
		var tex = textAr.value;
		var objectText = "";
		for (var i = 0; i < selectCons.length; i++) { 
				var selectCon = selectCons[i] ;						
				tex = tex + " " + selectCon.options[selectCon.selectedIndex].text;
				objectText = objectText + " " + selectCon.options[selectCon.selectedIndex].value;				 
		}
		textAr.value=tex;
		userRuleFormatHidTxt.value= objectText;
		
	};
	
	function clearConditonBox(){
		var ele = document.getElementById('userRuleText');
		ele.value="";	
		var userRuleFormatTextEle = document.getElementById('userRuleFormatText');
		userRuleFormatTextEle.value="";	
	}	

	function verify(){
		var textAr = document.getElementById('userRuleText');
		textAr.disabled="";
		return true;
	}
</script>



<title>Rule : ${userRule.userRuleName}</title>
</head>
<body>
	<h2> Custom Rules Engine Using MVEL</h2>


<form name="editForm" id="editForm" method="post" action="save">

<table>
	<tr>
		<td><b>Rule Id</b></td>
		<td>${userRule.userRuleId}</td>
		<input type="hidden" name="userRuleId" value="${userRule.userRuleId}"/>
	</tr>
	<tr>
		<td><b>Rule Name</b></td>
		<td><input type="text" name="userRuleName"  value="${userRule.userRuleName}" /></td>
	</tr>
	<tr>
		<td valign="top">Condition Box:</td>
		<td valign="top">
		<div id="allConditionDiv">
				<div id="conditionDiv">
					<select id="ops" name="ops">
					<c:forEach  items="${conditionPtyMappingList}" var="conditionPtyMapping">
						<option value="${conditionPtyMapping.objectName}">${conditionPtyMapping.displayName}</option>
					</c:forEach>
					</select>
					<a href="#" id="removeSelect">Remove</a>
				</div>
				<div id="extraCondition"/>
		</div>
		<br/>
		<input type="button" id="addNewCondition" value="Add New Condition" />			
		</td>
		
		<td></td>

	</tr>
	<tr>
		<td>
			<b>Rule in DB </b>
		</td>
		<td>
			<textarea rows="5" cols="50"   disabled="disabled">${userRule.userRuleText}</textarea>
		</td>		
	</tr>
	<tr>
		<td>
		<b>Generated Condition :</b>
		</td>
		<td>
		<textarea rows="5" cols="50" id="userRuleText" name="userRuleText"  disabled="disabled"></textarea>		
				
		<input type="button" id="clearConditionBox" value="Clear Condition Box" onclick="javascript:clearConditonBox();"/>		
		</td>
	</tr>		
	<tr>
		<td><b>Rule Text MVEL format</b></td>
		<td>${userRule.userRuleFormatText}</td>
		<input type="hidden" name="userRuleFormatText" id="userRuleFormatText"  value="${userRule.userRuleFormatText}"/>
	</tr>				
</table>
<input type="button" value="Generate Condition" id="generateCondition" onclick="javascript:generate();"/>
<input type="submit" value="Save" onclick="javascript:verify();"></input>



</form>

</body>
</html>