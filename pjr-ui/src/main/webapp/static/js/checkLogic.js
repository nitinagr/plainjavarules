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