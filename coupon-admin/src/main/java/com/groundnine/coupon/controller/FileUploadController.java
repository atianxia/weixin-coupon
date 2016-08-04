package com.groundnine.coupon.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.groundnine.coupon.service.CouponService;
import com.groundnine.coupon.service.FileUploadService;

@Controller
public class FileUploadController extends BaseController{
	
	@Resource
	private CouponService couponService;
	
	@Resource
	private FileUploadService fileUploadService;
	
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
	public ModelMap importExcel(@RequestParam MultipartFile uploadExcel)
			throws IOException {
		int importAmount = this.fileUploadService.importExcel(uploadExcel.getInputStream());
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("result", importAmount >0 ? "success" : "failed");
		return modelMap;

	}
	
}
