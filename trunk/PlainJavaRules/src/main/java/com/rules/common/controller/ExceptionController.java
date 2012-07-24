package com.rules.common.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;



@Component
public class ExceptionController implements HandlerExceptionResolver {
	
	private static final Log log = LogFactory.getLog(ExceptionController.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		ModelAndView mv = null;
		
		log.error("Exception Occured ", ex);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		
		String entireTrace = sw.toString();
		Map errorMap = new HashMap<String, String>();
		errorMap.put("stack", entireTrace);
		
		log.debug("printing the stack ");
		log.debug(entireTrace);
		
		if(ex instanceof NoSuchMethodException){			
			mv = new ModelAndView("notfound");
		}else if (ex instanceof NoSuchRequestHandlingMethodException){
			mv = new ModelAndView("notfound");
		}else if (ex instanceof Exception){			
			mv = new ModelAndView("error" ,errorMap);
		}
		
		return mv;
	}

}
