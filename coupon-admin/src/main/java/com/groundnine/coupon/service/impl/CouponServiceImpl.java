package com.groundnine.coupon.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.groundnine.coupon.dao.CouponDao;
import com.groundnine.coupon.dao.CouponItemDao;
import com.groundnine.coupon.model.Coupon;
import com.groundnine.coupon.service.CouponService;
import com.groundnine.coupon.vo.CouponInfoVo;
import com.groundnine.coupon.vo.CouponItemPersistVo;
import com.groundnine.coupon.vo.CouponQueryVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.groundnine.coupon.util.CouponExcelHelper;
import com.groundnine.coupon.vo.CouponItemVo;
import com.groundnine.coupon.vo.CouponVo;

@Service("couponService")
public class CouponServiceImpl implements CouponService {
	private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
	
	@Resource
	private CouponDao couponDao;
	
	@Resource
	private CouponItemDao couponItemDao;
	
	@Resource
	private CouponExcelHelper couponExcelHelper;

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
	public int importExcel(InputStream inputStream) {
		List<CouponItemVo> couponItemVos = couponExcelHelper.parseExcelToList(inputStream);
		this.batchInsert(convert(couponItemVos));
		return 1;

	}
	
	private void batchInsert(Collection<CouponInfoVo> couponInfoVos){
		if(CollectionUtils.isNotEmpty(couponInfoVos)){
			for(CouponInfoVo couponInfoVo : couponInfoVos){
				this.couponDao.batchInsert(couponInfoVo);
				this.couponItemDao.batchInsert(couponInfoVo);
			}
		}
	}
	
	private Collection<CouponInfoVo> convert(List<CouponItemVo> couponItemVos){
		Map<String, CouponInfoVo> couponInfoMap = new HashMap<String, CouponInfoVo>();
		if(CollectionUtils.isNotEmpty(couponItemVos)){
			for(CouponItemVo couponItemVo : couponItemVos){
				if(couponInfoMap.containsKey(couponItemVo.getCouponName())){
					CouponItemPersistVo itemPersistVo = new CouponItemPersistVo();
					itemPersistVo.setCouponCode(couponItemVo.getCouponCode());
					CouponInfoVo couponInfoVo = couponInfoMap.get(couponItemVo.getCouponName());
					couponInfoVo.getCouponItemPersistVos().add(itemPersistVo);
				}else{
					CouponInfoVo couponInfoVo = new CouponInfoVo();
					BeanUtils.copyProperties(couponItemVo, couponInfoVo);
					List<CouponItemPersistVo> itemPersistVos = new ArrayList<CouponItemPersistVo>();
					CouponItemPersistVo itemPersistVo = new CouponItemPersistVo();
					itemPersistVo.setCouponCode(couponItemVo.getCouponCode());
					itemPersistVos.add(itemPersistVo);
					couponInfoVo.setCouponItemPersistVos(itemPersistVos);
					couponInfoMap.put(couponItemVo.getCouponName(), couponInfoVo);
				}
					
			}
		}
		return couponInfoMap.values();
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
