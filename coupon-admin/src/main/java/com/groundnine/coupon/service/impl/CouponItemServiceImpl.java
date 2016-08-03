package com.groundnine.coupon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.groundnine.coupon.service.CouponItemService;
import org.springframework.stereotype.Service;

import com.groundnine.coupon.dao.CouponItemDao;
import com.groundnine.coupon.vo.CouponItemInfo;
import com.groundnine.coupon.vo.CouponItemQueryVo;

@Service("couponItemService")
public class CouponItemServiceImpl implements CouponItemService {
	
	@Resource
	private CouponItemDao couponItemDao;

	@Override
	public List<CouponItemInfo> queryCouponItems(CouponItemQueryVo couponItemQueryVo, int page, int rows) {
		int pageStart = (page - 1) * rows;
		return this.couponItemDao.selectByCondition(couponItemQueryVo, pageStart, rows);
	}

	@Override
	public int countCouponItemsAmout(CouponItemQueryVo couponItemQueryVo) {
		return this.couponItemDao.countAmountByCondition(couponItemQueryVo);
	}

}
