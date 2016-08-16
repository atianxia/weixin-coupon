package com.groundnine.coupon.service;

import java.util.List;
import java.util.Set;

import com.groundnine.coupon.vo.CouponItemInfo;
import com.groundnine.coupon.vo.CouponItemQueryVo;

public interface CouponItemService {

	List<CouponItemInfo> queryCouponItems(CouponItemQueryVo couponItemQueryVo, int page, int rows);

	int countCouponItemsAmout(CouponItemQueryVo couponItemQueryVo);

    int batchInsertCouponItem(Long couponId, Set<String> couponCodes);
}
