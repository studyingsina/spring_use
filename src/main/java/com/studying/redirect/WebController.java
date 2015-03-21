/**
 * Copyright (c) 2010-2015 meituan.com
 * All rights reserved.
 * 酒店后台研发.
 */
package com.studying.redirect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Desc
 * @author: zhangjunwei@meituan.com
 * @Date: 2015年2月14日
 */
@Controller
public class WebController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(){
		return "index";
	}
	
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String redirect(){
		return "redirect:finalPage";
	}
	
	@RequestMapping(value = "/finalPage", method = RequestMethod.GET)
	public String finalPage(){
		return "final";
	}
	
	@RequestMapping(value = "/staticPage", method = RequestMethod.GET)
	public String staticPage(){
		return "redirect:/pages/final.htm";
	}
	
}
