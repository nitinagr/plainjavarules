package com.rules.common.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rules.common.TechnicalException;
import com.rules.common.processor.RulesProcessor;

@Controller
@RequestMapping("/test")
public class TestController {
	private static final Log log = LogFactory.getLog(TestController.class);
	
	@Resource
	private RulesProcessor rulesProcessor;

	@RequestMapping(value="/isEligibleToVote" ,method = RequestMethod.GET)
	public String loadView(ModelMap model) {
		
		return "testrules";

	}
	
	
	
	
	@RequestMapping(value="/tiles")
	public String testTiles(){
		return "tilestest";		
	}
	
	@RequestMapping(value="/error")
	public String error() throws TechnicalException{
		if(true){
			throw new TechnicalException();
		}			
		return "tilestest";		
	}	

	
	@RequestMapping(value="/null")
	public String nullup(){
		String n = null;
		if(true){
			n.toString();
		}
			
		return "tilestest";		
	}	

}