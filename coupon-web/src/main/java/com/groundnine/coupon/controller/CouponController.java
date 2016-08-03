package com.groundnine.coupon.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.groundnine.coupon.service.CouponItemService;
import com.groundnine.coupon.service.CouponService;
import com.groundnine.coupon.vo.CouponItemVo;
import com.groundnine.coupon.vo.CouponVo;

@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CouponController.class);
	
	@Resource
	private CouponService couponService;
	
	@Resource
	private CouponItemService couponItemService;
	
	/**
	 * 兑换中心
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView list(
			@RequestParam(value="page", defaultValue="1") int page, 
			@RequestParam(value="rows", defaultValue="1000") int rows) {
		ModelAndView mav = new ModelAndView();
		List<CouponVo> couponInfos = this.couponService.queryCoupons(page, rows);
        mav.addObject("couponInfos", couponInfos);
        mav.setViewName("list.ftl");
        return mav;
	}
	
	/**
	 * 领取优惠券
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/receive", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap receive(@RequestParam(required= true, value="userId") String userId,
							@RequestParam(required= true, value="couponId") Long couponId) {
		ModelMap modelMap = new ModelMap();
		String couponCode = this.couponService.receiveCoupon(userId, couponId);
		modelMap.addAttribute("couponCode", couponCode);
        return modelMap;
	}
	
	/**
	 * 我的优惠券
	 * 
	 * @param userId
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/myCoupon", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView myCoupon(
			@RequestParam(value="userId") String userId,
			@RequestParam(value="page", defaultValue="1") int pageNum, 
			@RequestParam(value="rows", defaultValue="1000") int rows) {
		ModelAndView mav = new ModelAndView();
		List<CouponItemVo> couponItems = this.couponItemService.queryUserCoupons(userId, pageNum, rows);
        mav.addObject("couponItems", couponItems);
        mav.setViewName("my_coupon.ftl");
        return mav;
	}
}
