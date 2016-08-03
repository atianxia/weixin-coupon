package com.groundnine.coupon.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.groundnine.coupon.vo.CouponVo;

public interface CouponDao {

	List<CouponVo> selectCouponList(@Param("offset")int offset, @Param("rows")int rows);

	int increaseCouponReceivedTimes(@Param("couponId")Long couponId);

}
