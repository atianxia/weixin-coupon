package com.groundnine.coupon.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.groundnine.coupon.model.User;
import com.groundnine.coupon.service.UserService;


@Controller
public class LoginController {
	
	@Resource
	private UserService userService;

	@RequestMapping("/login")
	public ModelAndView clientLogin(HttpSession httpSession,String name,String password){
		ModelAndView mv = new ModelAndView();
		
		if(StringUtils.isBlank(name) || StringUtils.isBlank(password)){
			mv.setViewName("login");
			mv.addObject("errorInfo", "输入的用户名或密码不能为空!");
			return mv;
		}
		
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user = userService.getUser(user);
		if(user ==null){
			mv.setViewName("login");
			mv.addObject("errorInfo", "输入的用户名或密码错误!");
			return mv;
		}
		
			//登陆成功
			httpSession.setAttribute("name",name);
			mv.setViewName("redirect:/index.do");
			return mv;
	}
	
	
	@RequestMapping("/loginout")
	public String clientLoginOut(HttpSession httpSession){
		httpSession.invalidate();
		return "/login";
	}
	
}
