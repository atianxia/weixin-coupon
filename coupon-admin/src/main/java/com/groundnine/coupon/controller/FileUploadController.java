package com.groundnine.coupon.controller;

import com.groundnine.coupon.service.CouponItemService;
import com.groundnine.coupon.service.FileUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@Controller
public class FileUploadController extends BaseController{
	
	@Resource
	private FileUploadService fileUploadService;
	
	@Resource
	private CouponItemService couponItemService;
	
	@RequestMapping(value = "coupon/picFileUpload", method = RequestMethod.POST)  
	@ResponseBody
    public ModelMap picFileUpload(@RequestParam("brandLogoPicFile") CommonsMultipartFile file, HttpServletRequest request) {  
	 
     

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("picUrl", fileUploadService.picFileUpload(file));
        return modelMap;  
    }  
	
	/**
	 * 读取excel报表
	 */
	@RequestMapping(value = "coupon/import", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap importExcel(@RequestParam CommonsMultipartFile uploadExcel, Long couponId)
			throws IOException {
		Set<String> couponCodes = this.fileUploadService.parseCouponCodeExcel(uploadExcel);
		int importAmount = this.couponItemService.batchInsertCouponItem(couponId, couponCodes);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("result", importAmount >0 ? "success" : "failed");
		return modelMap;

	}
	
}
