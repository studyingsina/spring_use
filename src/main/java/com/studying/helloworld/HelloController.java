package com.studying.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Springmvc hello world 
 * @Date: 2015年2月14日
 */
@Controller
@RequestMapping("/hello")
public class HelloController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String printHello(ModelMap model){
		model.addAttribute("message", "Hello Spring MVC Framework");
		return "hello";
	}
	
}
