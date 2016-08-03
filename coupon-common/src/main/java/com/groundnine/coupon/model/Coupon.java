package com.groundnine.coupon.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.groundnine.coupon.util.CustomDateSerializer;

public class Coupon {
    private Long couponId;

    private String couponName;
    
    private Integer couponStatus;
    
    @JsonSerialize(using = CustomDateSerializer.class)  
    private Date expireDate;

    private String usingRule;

    private String buyLink;

    private Integer amount;

    private Integer receivedTimes;
    
    private Boolean isDel;

    @JsonSerialize(using = CustomDateSerializer.class)  
    private Date createTime;

    @JsonSerialize(using = CustomDateSerializer.class)  
    private Date updateTime;

    private String brandLogo;

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

	public Integer getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(Integer couponStatus) {
		this.couponStatus = couponStatus;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getReceivedTimes() {
		return receivedTimes;
	}

	public void setReceivedTimes(Integer receivedTimes) {
		this.receivedTimes = receivedTimes;
	}

	public Boolean getIsDel() {
		return isDel;
	}

	public void setIsDel(Boolean isDel) {
		this.isDel = isDel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBrandLogo() {
		return brandLogo;
	}

	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}
    
}