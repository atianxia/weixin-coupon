package com.groundnine.coupon.service;

import java.util.List;

import com.groundnine.coupon.vo.CouponReceiveVo;
import com.groundnine.coupon.vo.CouponVo;

public interface CouponService {

	List<CouponVo> queryCoupons(String userId, int page, int rows);

	CouponReceiveVo receiveCoupon(String userId, Long couponId);

	CouponVo getCouponInfoById(String userId, Long couponId);

}
