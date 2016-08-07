package com.groundnine.coupon.service;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface FileUploadService {
	
	String picFileUpload(CommonsMultipartFile file);

	int importExcel(InputStream inputStream);

	Set<String> parseCouponCodeExcel(CommonsMultipartFile couponCodeFile);

}
