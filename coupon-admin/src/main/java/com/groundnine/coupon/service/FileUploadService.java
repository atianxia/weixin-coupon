package com.groundnine.coupon.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface FileUploadService {
	String picFileUpload(CommonsMultipartFile file);
}
