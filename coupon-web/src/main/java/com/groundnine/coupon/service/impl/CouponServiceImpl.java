package com.groundnine.coupon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.groundnine.coupon.consts.BizConst;
import com.groundnine.coupon.dao.CouponDao;
import com.groundnine.coupon.dao.CouponItemDao;
import com.groundnine.coupon.model.Coupon;
import com.groundnine.coupon.model.CouponItem;
import com.groundnine.coupon.service.CouponService;
import com.groundnine.coupon.vo.CouponReceiveVo;
import com.groundnine.coupon.vo.CouponVo;

@Service("couponService")
public class CouponServiceImpl implements CouponService{

	@Resource
	private CouponDao couponDao;
	
	@Resource
	private CouponItemDao couponItemDao;
	
	@Override
	public List<CouponVo> queryCoupons(String userId, int pageNum, int rows) {
		int offset = (pageNum - 1) * rows;
		return this.couponDao.selectCouponList(userId, offset, rows);
	}

	@Transactional(propagation=Propagation.REQUIRED, transactionManager="transactionManager")
	@Override
	public CouponReceiveVo receiveCoupon(String userId, Long couponId) {
		//悲观锁 select for update
		CouponReceiveVo couponReceiveVo = new CouponReceiveVo();
		CouponItem couponItem = this.couponItemDao.selectFirstUnusedCoupon(couponId);
		Coupon coupon = this.couponDao.selectCouponByCouponId(couponId);
		if(couponItem == null){
			couponReceiveVo.setReceivedTimes(coupon.getReceivedTimes());
			couponReceiveVo.setCouponCode(BizConst.SELL_OUT_TEXT);
			return couponReceiveVo;
		}
		this.couponDao.increaseCouponReceivedTimes(couponId);
		this.couponItemDao.updateCouponCodeToUsed(userId, couponItem.getId());
		couponReceiveVo.setCouponCode(couponItem.getCouponCode());
		couponReceiveVo.setReceivedTimes(coupon.getReceivedTimes()+1);
		return couponReceiveVo;
	}

}
