package com.groundnine.coupon.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.groundnine.coupon.model.CouponItem;
import com.groundnine.coupon.vo.CouponItemVo;

public interface CouponItemDao {

	CouponItem selectFirstUnusedCoupon(@Param("couponId") Long couponId);

	int updateCouponCodeToUsed(@Param("userId") String userId, @Param("id") Long id);

	List<CouponItemVo> selectUserCouponList(@Param("userId")String userId, @Param("offset")int offset, @Param("rows")int rows);
}
