package com.groundnine.coupon.vo;

public class CouponReceiveVo {
	/**
	 * 响应码 0-成功、1-未登陆、2-已领取、3-已领完
	 */
	private int responseCode;
	
	private String couponCode;
	
	private int receivedTimes;

	public String getCouponCode() {
		return couponCode;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public int getReceivedTimes() {
		return receivedTimes;
	}

	public void setReceivedTimes(int receivedTimes) {
		this.receivedTimes = receivedTimes;
	}
	
	
}
