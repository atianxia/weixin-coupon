package com.groundnine.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.groundnine.coupon.service.MyWxMpService;
import com.groundnine.coupon.service.RedisService;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.WxMenu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

@Service("myWxMpService")
public class MyWxMpServiceImpl implements MyWxMpService {
	private static final Logger logger = LoggerFactory.getLogger(MyWxMpServiceImpl.class);
	
	
	
	@Resource
	private WxMpService wxMpService;
	
	@Value("${server.host}/${couponListRedirectUri}")
	private String couponListRedirectUri;
	
	@Value("${server.host}/${myCouponRedirectUri}")
	private String myCouponRedirectUri;
	
	@Resource
	private RedisService redisService;

	private String buildCouponListUrl() {
		return this.wxMpService.oauth2buildAuthorizationUrl(couponListRedirectUri, 
				WxConsts.OAUTH2_SCOPE_BASE, "couponList");
	}

	private String buildMyCouponUrl() {
		return this.wxMpService.oauth2buildAuthorizationUrl(myCouponRedirectUri, 
				WxConsts.OAUTH2_SCOPE_BASE, "myCoupon");
	}
	
	@Override
	public String buildCouponListRedirectUri() {
		return couponListRedirectUri;
	}

	@Override
	public void rebuildMenu() throws WxErrorException {
		//删除旧按钮
		wxMpService.menuDelete();
		
		WxMenu wxMenu = new WxMenu();
		List<WxMenuButton> buttons = new ArrayList<WxMenuButton>();
		
		WxMenuButton couponListBtn = new WxMenuButton();
		couponListBtn.setName("兑换中心");
		couponListBtn.setType("view");
		couponListBtn.setKey("couponList");
		couponListBtn.setUrl(buildCouponListUrl());
		buttons.add(couponListBtn);
			
		WxMenuButton myCouponBtn = new WxMenuButton();
		myCouponBtn.setName("我的优惠券");
		myCouponBtn.setType("view");
		myCouponBtn.setKey("myCoupon");
		myCouponBtn.setUrl(buildMyCouponUrl());
		buttons.add(myCouponBtn);
		
		wxMenu.setButtons(buttons);
		System.out.println("菜单Json为:" + wxMenu.toJson());
		
		wxMpService.menuCreate(wxMenu);
		
	}

	@Override
	public String sendMessage(String type) throws WxErrorException {
		if(type.equals("text")){
			WxMpUserList wxUserList = wxMpService.userList(null);
			List<String> userList = wxUserList.getOpenIds();
			for (String openId : userList) {
				System.out.println("openId : "+openId);
				WxMpCustomMessage message = WxMpCustomMessage.TEXT().toUser(openId).content("Hello World!").build();
				wxMpService.customMessageSend(message);
			}
			return "成功！";
		}
		return "失败！";
	}

	@Override
	public String getUserId(String userId, final String code) {
		logger.info("微信公众号code： " + code + ", 用戶Id： "+ userId + ", ");
		if(StringUtils.isNotBlank(userId)){
			return userId;
		}
		
		if(StringUtils.isBlank(code)){
			return null;
		}
		
		String redisValue = this.redisService.getRedisValue(code);
		
		if(StringUtils.isNotBlank(redisValue)){
			userId = redisValue;
		}else{
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
			try {
				wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
				userId = wxMpOAuth2AccessToken.getOpenId();
				this.redisService.setRedisValueEx(code, 3600L, userId);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}
		
		logger.info("用戶Id： "+ userId);
		return userId;
	}
	
	
	
}
