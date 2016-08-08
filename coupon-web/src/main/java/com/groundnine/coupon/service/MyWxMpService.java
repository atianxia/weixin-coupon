package com.groundnine.coupon.service;

import me.chanjar.weixin.common.exception.WxErrorException;

public interface MyWxMpService {

	void rebuildMenu() throws WxErrorException;

	String sendMessage(String type) throws WxErrorException;
	
	String getUserId(String userId, String code);

	String buildCouponListRedirectUri();
}
