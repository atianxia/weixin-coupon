package com.groundnine.coupon.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.groundnine.coupon.service.FileUploadService;
import com.groundnine.coupon.util.MD5Util;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	
	@Value("images.directory")
	private String imagesDirectory;
	
	@Value("${server.host}/${images.path}")
	private String imagesPath;

	@Override
	public String picFileUpload(CommonsMultipartFile file) {
//		 String pic_path =  request.getSession().getServletContext().getRealPath("/images/"); 
//		 String pic_path = "D://images//";
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
	            String path = imagesDirectory +file_ture_name;
	            File localFile = new File(path);  
	            try {  
	                file.transferTo(localFile);  
	            } catch (IllegalStateException e) {  
	                e.printStackTrace();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	     return imagesPath + file_ture_name;
	}

}
