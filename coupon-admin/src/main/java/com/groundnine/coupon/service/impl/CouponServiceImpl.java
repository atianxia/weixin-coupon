package com.groundnine.coupon.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public CouponVo getCouponById(Long couponId) {
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

	@Transactional(rollbackFor={RuntimeException.class,Exception.class})
	@Override
	public int addCoupon(CouponVo couponVo, Set<String> couponCodes) {
		Coupon coupon = new Coupon();
		BeanUtils.copyProperties(couponVo, coupon);
		coupon.setAmount(couponCodes.size());
		if(coupon.getCouponType() != 0){
			coupon.setAmount(Integer.MAX_VALUE);
		}
		int dbResut = this.couponDao.insert(coupon);
		if(dbResut>0 && CollectionUtils.isNotEmpty(couponCodes)){
			this.couponItemDao.batchInsertCouponItem(coupon.getCouponId(), couponCodes);
		}
		return dbResut;
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
