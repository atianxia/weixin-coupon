package com.groundnine.coupon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.groundnine.coupon.dao.CouponItemDao;
import com.groundnine.coupon.service.CouponItemService;
import com.groundnine.coupon.vo.CouponItemVo;

@Service("couponItemService")
public class CouponItemServiceImpl implements CouponItemService {
	
	@Resource
	private CouponItemDao couponItemDao;

	@Override
	public List<CouponItemVo> queryUserCouponItems(String userId, int pageNum, int rows) {
		int offset = (pageNum - 1) * rows;
		return this.couponItemDao.selectUserCouponItemList(userId, offset, rows);
	}

}
