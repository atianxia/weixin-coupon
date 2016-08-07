package com.groundnine.coupon.weixin.factory;

import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import com.groundnine.coupon.weixin.handler.SubscribeHandler;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

/**
 * 
 * weixin 推送消息路由器
 * @author tomcat
 *
 */
public class WXMessageRouterFactoryBean  implements FactoryBean<WxMpMessageRouter>{
	private static final Logger logger = LoggerFactory.getLogger(WXMessageRouterFactoryBean.class);
	
	private WxMpService wxMpService;
		
	public WxMpService getWxMpService() {
		return wxMpService;
	}

	public void setWxMpService(WxMpService wxMpService) {
		this.wxMpService = wxMpService;
	}

	@Override
	public WxMpMessageRouter getObject() throws Exception {
		WxMpMessageRouter wxMpMessageRouter =  new WxMpMessageRouter(wxMpService);
		//处理关注事件
		wxMpMessageRouter.rule()
		.async(false)
		.msgType("event")
		.event("subscribe")
		.handler( new SubscribeHandler())
		.end();
		
		//调试
		wxMpMessageRouter.rule()
		.async(false)
		.content("tomcat&dog")
		.handler(new WxMpMessageHandler() {
			@Override
			public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
					WxSessionManager sessionManager) throws WxErrorException {
				WxMpXmlOutTextMessage message = WxMpXmlOutTextMessage.TEXT().
						fromUser(wxMessage.getToUserName())
						.toUser(wxMessage.getFromUserName())
						.content("dog")
						.build();
				return message;
			}
		})
		.end( );
		
		return wxMpMessageRouter;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return WxMpMessageRouter.class.getClass();
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
