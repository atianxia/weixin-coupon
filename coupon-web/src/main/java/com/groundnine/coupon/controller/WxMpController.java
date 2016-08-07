package com.groundnine.coupon.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.groundnine.coupon.service.MyWxMpService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
@RequestMapping(value = "/wx")
public class WxMpController {
	
	private static final Logger logger = LoggerFactory.getLogger(CouponController.class);

	@Resource
	private MyWxMpService myWxMpService;

	@Resource
	private WxMpService wxMpService;
	
	@Resource
	private WxMpMessageRouter wxMpMessageRouter;
	
	@Resource
	private WxMpConfigStorage wxMpConfigStorage;

	@RequestMapping("/messagecenter")
	public void messageCenter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, WxErrorException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		String signature = request.getParameter("signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		logger.info("signature" + signature);
		logger.info("nonce" + nonce);
		System.out.println("signature" + signature);
		System.out.println("nonce" + nonce);

		if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
			// 消息签名不正确，说明不是公众平台发过来的消息
			response.getWriter().println("非法请求");
			return;
		}

		String echostr = request.getParameter("echostr");
		System.out.println("echostr:" + echostr);
		if (StringUtils.isNotBlank(echostr)) {
			// 说明是一个仅仅用来验证的请求，回显echostr
			response.getWriter().println(echostr);
			return;
		}

		String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw"
				: request.getParameter("encrypt_type");

		if ("raw".equals(encryptType)) {
			// 明文传输的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
			WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
			response.getWriter().write(outMessage.toXml());
			return;
		}

		if ("aes".equals(encryptType)) {
			// 是aes加密的消息
			String msgSignature = request.getParameter("msg_signature");
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxMpConfigStorage,
					timestamp, nonce, msgSignature);
			WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
			response.getWriter().write(outMessage.toEncryptedXml(wxMpConfigStorage));
			return;
		}

		response.getWriter().println("不可识别的加密类型");
		return;
	}

	@RequestMapping("/sendmessage")
	@ResponseBody
	public String sendMessage(String type) throws WxErrorException {
		return this.myWxMpService.sendMessage(type);
	}

	@RequestMapping("/index")
	public String getOpenId(String code, HttpServletRequest request, HttpServletResponse response)
			throws WxErrorException {
		System.out.println("weixin回调");
		;
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
		WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
		String id = wxMpUser.getOpenId();
		System.out.println("openId：" + id);
		return id;
	}

	@RequestMapping("/rebuildMenu")
	@ResponseBody
	public String rebuildMenu() throws WxErrorException {
		this.myWxMpService.rebuildMenu();
		return "sucess!";
	}
	
	/**
	 * 二维码地址
	 * https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${ticker} 
	 * @param code
	 * @param expire_seconds
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/makeQrCode")
	@ResponseBody
	public Map<String, Object> makeQrCode(@RequestParam(value="code") Integer code,
			@RequestParam(value="expire_seconds", defaultValue="3600") Integer expire_seconds, 
			HttpServletRequest request,HttpServletResponse response){
		logger.info("生成二维码");
		Map<String, Object> result = new HashMap<String, Object>();
		if(code == null){
			result.put("sucess", false);
			return result;
		}
		
		try {
			WxMpQrCodeTicket ticket = wxMpService.qrCodeCreateTmpTicket(code, expire_seconds);
			result.put("sucess", true);
			result.put("qrCode", ticket);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString());
			result.put("sucess", false);
		}finally{
			return result;
		}
	}
}
