package com.groundnine.coupon.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.groundnine.coupon.model.Coupon;
import com.groundnine.coupon.vo.CouponVo;

public interface CouponDao {

	List<CouponVo> selectCouponList(@Param("userId")String userId, @Param("offset")int offset, @Param("rows")int rows);

	int increaseCouponReceivedTimes(@Param("couponId")Long couponId);

	Coupon selectCouponByCouponId(@Param("couponId") Long couponId);
	
	CouponVo selectUserCouponById(@Param("userId")String userId, @Param("couponId") Long couponId);

}
