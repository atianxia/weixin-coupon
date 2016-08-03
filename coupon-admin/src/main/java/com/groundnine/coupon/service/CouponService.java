package com.groundnine.coupon.service;

import java.io.InputStream;
import java.util.List;

import com.groundnine.coupon.model.Coupon;
import com.groundnine.coupon.vo.CouponQueryVo;
import com.groundnine.coupon.vo.CouponVo;

public interface CouponService {
	
	CouponVo getCouponById(String couponId);

	List<Coupon> queryCoupons(CouponQueryVo couponQueryVo, int page, int rows);

	int countCouponAmout(CouponQueryVo couponQueryVo);

	int deleteCouponByIds(String couponIds);

	int importExcel(InputStream inputStream);

	int updateCoupon(CouponVo couponVo);

	int addCoupon(CouponVo couponVo);

	int operateCouponStatusByIds(String couponIds, Integer operateType);
}
