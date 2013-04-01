/**
 * This method accepts the ID of Logic item and validates 
 * whether the given logic is valid in terms of correct parenthesizing
 */
function checkParenthesis(text){
	//alert(text);
	//Trim the string
	text=text.replace(/^\s+|\s+$/g, '');
	var len=text.length;
	var openBrace=[];
	var closeBrace=[];
	var expressionValid=true;
	var i=0;
	var tempIndexOpen;
	var tempIndexClose;
	var firstOpenBrace=false;
	var lastCloseBrace=false;
	
	for(i=0;i<len;i++){
		var ch=text.charAt(i);
		if(ch == '('){
			openBrace.push(i);
		} else if(ch == ')'){
			closeBrace.push(i);
			tempIndexOpen=openBrace.pop();
			tempIndexClose=closeBrace.pop();
			if(openBrace.length == 0){
				firstOpenBrace=true;
			}
			if(closeBrace.length == 0){
				lastCloseBrace=true;
			}
			if((firstOpenBrace && tempIndexClose!=(len-1)) || (!firstOpenBrace && tempIndexClose==(len-1)) ){
				expressionValid=false;
			}
		}
	}
	//alert(expressionValid);
	return expressionValid;
}
/**
 * This method accepts the Logic text and validates 
 * whether the given logic is valid in terms of condition formation
 */
function checkLogic(inputText){
	var text=inputText;
	text=text.substr(0,text.length-1);//One extra "@" comes at the end.removing the extra blank elemnt from String

	var logicArray=text.split("@");
	var len=logicArray.length;
	len=len-1; 
	
	var minOneSub=/(sub-[0-9]*)+/;
	var atLeastOneSubPresent=minOneSub.test(text);
	//alert('atLeastOneSubPresent: '+atLeastOneSubPresent);
	var patternConSubs=/(sub-[0-9]*)@(sub-[0-9]*)/g;
	var isConsecutiveSubrulesPresent=patternConSubs.test(text); // Checks whether two consecutive subrules are there
	//alert('isConsecutiveSubrulesPresent:'+isConsecutiveSubrulesPresent);
		
	if(atLeastOneSubPresent && !isConsecutiveSubrulesPresent){
		//alert('OK');
		return true;
	} else {
		//alert('NOT OK');
		return false;
	}
}
/**
 * This method is used to select all checkboxes with given name
 */
function selectAll(name){
	//alert(name);
	var checkBoxes=document.getElementsByName(name);
	//alert(checkBoxes+'---'+checkBoxes.length);
	for (var i = 0; i < checkBoxes.length; i++) {
		checkBoxes[i].checked = true;
	   }
	//$("#operatorsToAssignFromRequest").attr("checked", "true");
}
/**
 * This method is used to uncheck all checkboxes with given name
 */
function clearAll(name){
	//alert(name);
	var checkBoxes=document.getElementsByName(name);
	//alert(checkBoxes+'---'+checkBoxes.length);
	for (var i = 0; i < checkBoxes.length; i++) {
		checkBoxes[i].checked = false;
	   }
	//$("#operatorsToAssignFromRequest").attr("checked", "false");
}