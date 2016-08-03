package com.groundnine.coupon.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.groundnine.coupon.model.Coupon;
import com.groundnine.coupon.service.CouponService;
import com.groundnine.coupon.vo.CouponQueryVo;
import com.groundnine.coupon.vo.CouponVo;

@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(CouponController.class);
	
	@Resource
	private CouponService couponService;
	
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> list(CouponQueryVo couponQueryVo, 
			@RequestParam(value="page", defaultValue="1") int page, 
			@RequestParam(value="rows", defaultValue="10") int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
        List<Coupon> coupons = this.couponService.queryCoupons(couponQueryVo, page, rows);
        int total = this.couponService.countCouponAmout(couponQueryVo);
        map.put("total", total);
        map.put("rows", coupons);
        return map;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap delete(@RequestParam(value="couponIds", required=true) String couponIds) {
		ModelMap map = new ModelMap();
		int deletedRows = this.couponService.deleteCouponByIds(couponIds);
		map.addAttribute("success", deletedRows>0? true : false);
		return map;
	}
	
	@RequestMapping(value = "/operateCouponStatus", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap operateCouponStatus(@RequestParam(value="couponIds", required=true) String couponIds, 
			@RequestParam(value="operateType") Integer operateType) {
		ModelMap map = new ModelMap();
		int affectRows = this.couponService.operateCouponStatusByIds(couponIds, operateType);
		map.addAttribute("success", affectRows>0? true : false);
		return map;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap save(CouponVo couponVo) {
		ModelMap map = new ModelMap();
		int dbResult = this.couponService.updateCoupon(couponVo);
		map.addAttribute("result", dbResult >0? "success" : "failed");
		return map;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap add(CouponVo couponVo) {
		ModelMap map = new ModelMap();
		int dbResult = this.couponService.addCoupon(couponVo);
		map.addAttribute("result", dbResult >0? "success" : "failed");
		return map;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("coupon/couponList.ftl");
		return mav;
	}
	

	/**
	 * 读取excel报表
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap importExcel(@RequestParam MultipartFile uploadExcel)
			throws IOException {
		int importAmount = this.couponService.importExcel(uploadExcel.getInputStream());
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("result", importAmount >0 ? "success" : "failed");
		return modelMap;

	}
	
}
