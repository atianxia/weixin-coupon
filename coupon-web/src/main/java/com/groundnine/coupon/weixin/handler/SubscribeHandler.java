package com.groundnine.coupon.weixin.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.groundnine.coupon.controller.CouponController;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;
/*
 * weixin 关注后处理逻辑
 */
public class SubscribeHandler implements WxMpMessageHandler {
	private static final Logger logger = LoggerFactory.getLogger(SubscribeHandler.class);

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {
		logger.info("处理关注事件");
		//	业务逻辑处理,用户入库,发放优惠劵
		WxMpXmlOutTextMessage m
        = WxMpXmlOutMessage.TEXT().content("发放优惠劵").fromUser(wxMessage.getToUserName())
        .toUser(wxMessage.getFromUserName()).build();
		return m;
	}
	
	
}
