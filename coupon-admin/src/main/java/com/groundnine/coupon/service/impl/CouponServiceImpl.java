package com.groundnine.coupon.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.groundnine.coupon.dao.CouponDao;
import com.groundnine.coupon.dao.CouponItemDao;
import com.groundnine.coupon.model.Coupon;
import com.groundnine.coupon.service.CouponService;
import com.groundnine.coupon.vo.CouponQueryVo;
import com.groundnine.coupon.vo.CouponVo;

@Service("couponService")
public class CouponServiceImpl implements CouponService {
	private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
	
	@Resource
	private CouponDao couponDao;
	
	@Resource
	private CouponItemDao couponItemDao;
	

	@Override
	public CouponVo getCouponById(String couponId) {
		Coupon coupon = this.couponDao.selectByCouponId(couponId);
		CouponVo couponVo = null;
		if(coupon != null){
			couponVo = new CouponVo();
			BeanUtils.copyProperties(coupon, couponVo);
		}
		return couponVo;
	}

	@Override
	public List<Coupon> queryCoupons(CouponQueryVo couponQueryVo, int page, int rows) {
		int pageStart = (page - 1) * rows;
		return couponDao.queryCoupons(couponQueryVo, pageStart, rows);
	}

	@Override
	public int countCouponAmout(CouponQueryVo couponQueryVo) {
		return this.couponDao.countCouponAmount(couponQueryVo);
	}

	@Override
	public int deleteCouponByIds(String couponIds) {
		Collection<Long> couponIdList = Collections2.transform(
				Splitter.on(",").omitEmptyStrings().splitToList(couponIds), new Function<String, Long>(){
			@Override
			public Long apply(String input) {
				return Long.valueOf(input);
			}
			
		});
		
		return this.couponDao.deleteCouponByIds(couponIdList);
	}

	

	@Override
	public int updateCoupon(CouponVo couponVo) {
		Coupon coupon = new Coupon();
		BeanUtils.copyProperties(couponVo, coupon);
		return this.couponDao.updateByPrimaryKeySelective(coupon);
	}

	@Override
	public int addCoupon(CouponVo couponVo) {
		Coupon coupon = new Coupon();
		BeanUtils.copyProperties(couponVo, coupon);
		return this.couponDao.insert(coupon);
	}

	@Override
	public int operateCouponStatusByIds(String couponIds, Integer operateType) {
		Collection<Long> couponIdList = Collections2.transform(
				Splitter.on(",").omitEmptyStrings().splitToList(couponIds), new Function<String, Long>(){
			@Override
			public Long apply(String input) {
				return Long.valueOf(input);
			}
			
		});
		
		return this.couponDao.updateCouponStatusByIds(couponIdList, operateType == 1 ? 1:0);
	}

}
