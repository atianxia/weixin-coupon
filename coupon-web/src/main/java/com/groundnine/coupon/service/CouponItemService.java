package com.groundnine.coupon.service;

import java.util.List;

import com.groundnine.coupon.vo.CouponItemVo;

public interface CouponItemService {
	List<CouponItemVo> queryUserCoupons(String userId, int pageNum, int rows);
}
