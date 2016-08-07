package com.groundnine.coupon.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CouponItemVo extends CouponVo{

	private String couponCode;
	
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date receiveTime;


	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	
	

}
