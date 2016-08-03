package com.groundnine.coupon.vo;

import java.util.Date;

public class CouponItemVo extends CouponVo{

	private String couponCode;
	
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
