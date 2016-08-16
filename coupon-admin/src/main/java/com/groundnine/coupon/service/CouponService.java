package com.groundnine.coupon.service;

import java.util.List;
import java.util.Set;

import com.groundnine.coupon.model.Coupon;
import com.groundnine.coupon.vo.CouponQueryVo;
import com.groundnine.coupon.vo.CouponVo;

public interface CouponService {
	
	CouponVo getCouponById(Long couponId);

	List<Coupon> queryCoupons(CouponQueryVo couponQueryVo, int page, int rows);

	int countCouponAmout(CouponQueryVo couponQueryVo);

	int deleteCouponByIds(String couponIds);

	int updateCoupon(CouponVo couponVo);

	int addCoupon(CouponVo couponVo, Set<String> couponCodes);

	int operateCouponStatusByIds(String couponIds, Integer operateType);
}
