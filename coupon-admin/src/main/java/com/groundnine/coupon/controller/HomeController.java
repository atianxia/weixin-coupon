package com.groundnine.coupon.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.groundnine.coupon.util.RiskControlHelper;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController  extends BaseController {
	@Resource
	private RiskControlHelper riskControlHelper;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String viewName = riskControlHelper.isIpValid(this.getIpAddr(request)) ? "index.ftl" : "accessDenied.ftl";
		mav.setViewName(viewName);
		return mav;
	}
	
	private String getIpAddr(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){ 
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    } 
	    logger.info("用户请求地址: " + ip);
	    return ip; 
	    
	}
	
	
	
	
}
