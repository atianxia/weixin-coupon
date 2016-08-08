package com.groundnine.coupon.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.groundnine.coupon.service.CouponItemService;
import com.groundnine.coupon.service.CouponService;
import com.groundnine.coupon.service.MyWxMpService;
import com.groundnine.coupon.vo.CouponItemVo;
import com.groundnine.coupon.vo.CouponReceiveVo;
import com.groundnine.coupon.vo.CouponVo;

import me.chanjar.weixin.common.exception.WxErrorException;

@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CouponController.class);
	
	@Resource
	private MyWxMpService myWxMpService;
	
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
	 * @throws WxErrorException 
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView list(
			@RequestParam(value="userId", required=false) String userId,
			@RequestParam(value="page", defaultValue="1") int page, 
			@RequestParam(value="rows", defaultValue="1000") int rows,
			@RequestParam(value="code", required=false) String code) throws WxErrorException {
		userId = this.myWxMpService.getUserId(userId, code);
		ModelAndView mav = new ModelAndView();
		List<CouponVo> couponInfos = this.couponService.queryCoupons(userId, page, rows);
        mav.addObject("couponInfos", couponInfos);
        mav.addObject("userId", userId);
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
	public CouponReceiveVo receive(@RequestParam(required= true, value="userId") String userId,
							@RequestParam(required= true, value="couponId") Long couponId, 
							@RequestParam(value="code", required=false) String code) {
		userId = this.myWxMpService.getUserId(userId, code);
		return this.couponService.receiveCoupon(userId, couponId);
	}
	
	/**
	 * 我的优惠券
	 * 
	 * @param userId
	 * @param pageNum
	 * @param rows
	 * @return
	 * @throws WxErrorException 
	 */
	@RequestMapping(value = "/myCoupon", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView myCoupon(
			@RequestParam(value="userId", required=false) String userId,
			@RequestParam(value="page", defaultValue="1") int pageNum, 
			@RequestParam(value="rows", defaultValue="1000") int rows,
			@RequestParam(value="code", required=false) String code) throws WxErrorException {
		userId = this.myWxMpService.getUserId(userId, code);
		ModelAndView mav = new ModelAndView();
		List<CouponItemVo> couponItems = this.couponItemService.queryUserCouponItems(userId, pageNum, rows);
        mav.addObject("couponItems", couponItems);
        mav.addObject("userId", userId);
        String viewName;
        
        if(couponItems.size() == 0){
        	viewName = "unclaimed.ftl";
        	mav.addObject("couponListUrl", this.myWxMpService.buildCouponListRedirectUri());
        }else{
        	viewName = "my_coupon.ftl";
        }
        	
        mav.setViewName(viewName);
        return mav;
	}
	
	@RequestMapping(value = "/detail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView detail(
			@RequestParam(value="userId", required=false) String userId,
			@RequestParam(value="couponId", required=true) Long couponId,
			@RequestParam(value="code", required=false) String code) throws WxErrorException {
		userId = this.myWxMpService.getUserId(userId, code);
		ModelAndView mav = new ModelAndView();
		CouponVo couponInfo = this.couponService.getCouponInfoById(userId, couponId);
        mav.addObject("couponInfo", couponInfo);
        mav.addObject("userId", userId);
        mav.setViewName("detail.ftl");
        return mav;
	}
	
	
	
}
