package com.groundnine.coupon.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.groundnine.coupon.dao.CouponDao;
import com.groundnine.coupon.model.Coupon;
import com.groundnine.coupon.service.CouponItemService;
import org.springframework.stereotype.Service;

import com.groundnine.coupon.dao.CouponItemDao;
import com.groundnine.coupon.vo.CouponItemInfo;
import com.groundnine.coupon.vo.CouponItemQueryVo;
import org.springframework.transaction.annotation.Transactional;

@Service("couponItemService")
public class CouponItemServiceImpl implements CouponItemService {
	
	@Resource
	private CouponItemDao couponItemDao;

	@Resource
	private CouponDao couponDao;

	@Override
	public List<CouponItemInfo> queryCouponItems(CouponItemQueryVo couponItemQueryVo, int page, int rows) {
		int pageStart = (page - 1) * rows;
		return this.couponItemDao.selectByCondition(couponItemQueryVo, pageStart, rows);
	}

	@Override
	public int countCouponItemsAmout(CouponItemQueryVo couponItemQueryVo) {
		return this.couponItemDao.countAmountByCondition(couponItemQueryVo);
	}

	@Transactional
	@Override
	public int batchInsertCouponItem(Long couponId, Set<String> couponCodes) {
		int dbResult = this.couponItemDao.batchInsertCouponItem(couponId, couponCodes);
		Coupon coupon = this.couponDao.selectByCouponId(couponId);
		Coupon updateCoupon = new Coupon();
		updateCoupon.setCouponId(coupon.getCouponId());
		updateCoupon.setAmount(coupon.getAmount() + dbResult);
		this.couponDao.updateByPrimaryKeySelective(updateCoupon);
		return 0;
	}

}
