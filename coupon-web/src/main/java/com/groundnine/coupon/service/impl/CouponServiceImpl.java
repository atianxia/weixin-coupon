package com.groundnine.coupon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.groundnine.coupon.consts.BizConst;
import com.groundnine.coupon.consts.ResponseCodeEnum;
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
		CouponReceiveVo couponReceiveVo = new CouponReceiveVo();
		//判断用户是否登陆
		if(StringUtils.isBlank(userId)){
			couponReceiveVo.setResponseCode(ResponseCodeEnum.NOT_LOGIN.getCode());
			return couponReceiveVo;
		}
		
		//用户是否已经领取过
		CouponItem userCouponItem = this.couponItemDao.selectUserHasReceivedThisCoupon(userId, couponId);
		if(userCouponItem != null){
			couponReceiveVo.setCouponCode(userCouponItem.getCouponCode());
			couponReceiveVo.setResponseCode(ResponseCodeEnum.RECEIVED.getCode());
			return couponReceiveVo;
		}
		
		//悲观锁 select for update
		CouponItem couponItem = this.couponItemDao.selectFirstUnusedCoupon(couponId);
		Coupon coupon = this.couponDao.selectCouponByCouponId(couponId);
		//判断是否已经领光了
		if(couponItem == null){
			couponReceiveVo.setReceivedTimes(coupon.getReceivedTimes());
			couponReceiveVo.setCouponCode(BizConst.SELL_OUT_TEXT);
			couponReceiveVo.setResponseCode(ResponseCodeEnum.SELL_OUT.getCode());
			return couponReceiveVo;
		}
		this.couponDao.increaseCouponReceivedTimes(couponId);
		this.couponItemDao.updateCouponCodeToUsed(userId, couponItem.getId());
		couponReceiveVo.setCouponCode(couponItem.getCouponCode());
		couponReceiveVo.setReceivedTimes(coupon.getReceivedTimes()+1);
		return couponReceiveVo;
	}

	@Override
	public CouponVo getCouponInfoById(String userId, Long couponId) {
		return this.couponDao.selectUserCouponById(userId, couponId);
	}

	@Transactional
	@Override
	public int increaseClickTimes(Long couponId) {
		Coupon coupon = this.couponDao.selectCouponByCouponId(couponId);
		if(coupon.getCouponType() == 0){
			return coupon.getReceivedTimes();
		}
		this.couponDao.increaseCouponReceivedTimes(couponId);
		return coupon.getReceivedTimes() + 1;
	}

}
