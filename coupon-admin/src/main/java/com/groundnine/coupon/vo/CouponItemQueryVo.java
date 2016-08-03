package com.groundnine.coupon.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CouponItemQueryVo {
	
	private Long couponId;

	private String couponName;
	
    private String couponCode;

    private String userId;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date receiveStartTime;
    
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date receiveEndTime;
    
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createStartTime;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createEndTime;

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

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getReceiveStartTime() {
		return receiveStartTime;
	}

	public void setReceiveStartTime(Date receiveStartTime) {
		this.receiveStartTime = receiveStartTime;
	}

	public Date getReceiveEndTime() {
		return receiveEndTime;
	}

	public void setReceiveEndTime(Date receiveEndTime) {
		this.receiveEndTime = receiveEndTime;
	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}
    
}
