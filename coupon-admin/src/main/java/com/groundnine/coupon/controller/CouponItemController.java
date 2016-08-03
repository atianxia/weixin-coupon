package com.groundnine.coupon.controller;

import java.util.List;

import javax.annotation.Resource;

import com.groundnine.coupon.service.CouponItemService;
import com.groundnine.coupon.vo.CouponItemQueryVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.groundnine.coupon.vo.CouponItemInfo;

@Controller
public class CouponItemController extends BaseController{
	
	@Resource
	private CouponItemService couponItemService;
	
	@RequestMapping(value = "/coupon/items", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap list(CouponItemQueryVo couponItemQueryVo,
			@RequestParam(required = true) int page, 
			@RequestParam(required = true) int rows) {
		ModelMap modelMap = new ModelMap();
		List<CouponItemInfo> couponItems = this.couponItemService.queryCouponItems(couponItemQueryVo, page, rows);
        int total = this.couponItemService.countCouponItemsAmout(couponItemQueryVo);
        modelMap.addAttribute("total", total);
        modelMap.addAttribute("rows", couponItems);
		return modelMap;
	}
	
	@RequestMapping(value = "/coupon/detail", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam(required = false) Long couponId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("couponId", couponId);
		modelAndView.setViewName("coupon/couponItemList.ftl");
		return modelAndView;
	}
}
