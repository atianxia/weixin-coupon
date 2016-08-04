package com.groundnine.coupon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.groundnine.coupon.consts.BizConst;
import com.groundnine.coupon.dao.CouponDao;
import com.groundnine.coupon.dao.CouponItemDao;
import com.groundnine.coupon.model.CouponItem;
import com.groundnine.coupon.service.CouponService;
import com.groundnine.coupon.vo.CouponVo;

@Service("couponService")
public class CouponServiceImpl implements CouponService{

	@Resource
	private CouponDao couponDao;
	
	@Resource
	private CouponItemDao couponItemDao;
	
	@Override
	public List<CouponVo> queryCoupons(int pageNum, int rows) {
		int offset = (pageNum - 1) * rows;
		return this.couponDao.selectCouponList(offset, rows);
	}

	@Transactional(propagation=Propagation.REQUIRED, transactionManager="transactionManager")
	@Override
	public String receiveCoupon(String userId, Long couponId) {
		//悲观锁 select for update
		CouponItem couponItem = this.couponItemDao.selectFirstUnusedCoupon(couponId);
		if(couponItem == null){
			return BizConst.SELL_OUT_TEXT;
		}
		this.couponDao.increaseCouponReceivedTimes(couponId);
		this.couponItemDao.updateCouponCodeToUsed(userId, couponItem.getId());
		return couponItem.getCouponCode();
	}

}
