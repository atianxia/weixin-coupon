package com.groundnine.coupon.vo;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.groundnine.coupon.util.CustomDateSerializer;

public class CouponVo {
	
	private Long couponId;
	
	private Integer couponType;
	
	private String couponName;
	
	@JsonSerialize(using = CustomDateSerializer.class) 
	private Date expireDate;
	
	private String brandLogo;

	private String usingRule;

	private String localLink;
	
	private String buyLink;

	private int amount;
	
	private int receivedTimes;
	
	private int received;
	

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getCouponType() {
		return couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getBrandLogo() {
		return brandLogo;
	}

	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}

	public String getUsingRule() {
		return usingRule;
	}

	public void setUsingRule(String usingRule) {
		this.usingRule = usingRule;
	}

	public String getLocalLink() {
		return localLink;
	}

	public void setLocalLink(String localLink) {
		this.localLink = localLink;
	}

	public String getBuyLink() {
		return buyLink;
	}

	public void setBuyLink(String buyLink) {
		this.buyLink = buyLink;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getReceivedTimes() {
		return receivedTimes;
	}

	public void setReceivedTimes(int receivedTimes) {
		this.receivedTimes = receivedTimes;
	}

	public int getReceived() {
		return received;
	}

	public void setReceived(int received) {
		this.received = received;
	}
	
	

}
