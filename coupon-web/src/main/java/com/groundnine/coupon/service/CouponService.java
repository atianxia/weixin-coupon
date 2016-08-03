package com.groundnine.coupon.service;

import java.util.List;

import com.groundnine.coupon.vo.CouponVo;

public interface CouponService {

	List<CouponVo> queryCoupons(int page, int rows);

	String receiveCoupon(String userId, Long couponId);

}
