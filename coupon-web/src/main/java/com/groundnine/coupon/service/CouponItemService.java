package com.groundnine.coupon.service;

import java.util.List;

import com.groundnine.coupon.vo.CouponItemVo;

public interface CouponItemService {
	List<CouponItemVo> queryUserCouponItems(String userId, int pageNum, int rows);
}
