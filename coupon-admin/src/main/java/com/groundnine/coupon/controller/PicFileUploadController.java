package com.groundnine.coupon.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.groundnine.coupon.util.MD5Util;

@Controller
public class PicFileUploadController extends BaseController{
	
	@RequestMapping(value = "coupon/picFileUpload", method = RequestMethod.POST)  
	@ResponseBody
    public ModelMap picFileUpload(@RequestParam("brandLogoPicFile") CommonsMultipartFile file, HttpServletRequest request) {  
	 
     
//	 String pic_path =  request.getSession().getServletContext().getRealPath("/images/"); 
//	 String pic_path = "D://images//";
	 String pic_path = "/home/www/images/";
	 String pic_time = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());   
     String pic_type = file.getContentType();
     String file_ture_name = MD5Util.stringMD5(pic_time);
     
     if(pic_type.equals("image/jpeg")){
         file_ture_name =   file_ture_name.concat(".jpg");
     } else if (pic_type.equals("image/png")){
         file_ture_name = file_ture_name.concat(".png");
     } else if(pic_type.equals("image/bmp")){
         file_ture_name =  file_ture_name.concat(".bmp");
     } else if(pic_type.equals("image/gif")){
         file_ture_name = file_ture_name.concat(".gif");
     } else file_ture_name = file_ture_name.concat(".jpg");
     
        // 判断文件是否存在  
        if (!file.isEmpty()) {  
            String path = pic_path +file_ture_name;
            File localFile = new File(path);  
            try {  
                file.transferTo(localFile);  
            } catch (IllegalStateException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("picUrl", file_ture_name);
        return modelMap;  
    }  
	
}
