package com.groundnine.coupon.vo;

import java.util.Date;
import java.util.List;

public class CouponInfoVo {
	
	private Long couponId;
	
	private String couponName;
	
	private Date expireDate;
	
	private String brandLogo;

	private String usingRule;

	private String buyLink;
	
	private int amount;

	List<CouponItemPersistVo> couponItemPersistVos;

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
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

	public List<CouponItemPersistVo> getCouponItemPersistVos() {
		return couponItemPersistVos;
	}

	public void setCouponItemPersistVos(List<CouponItemPersistVo> couponItemPersistVos) {
		this.couponItemPersistVos = couponItemPersistVos;
	}

	
}
