package com.groundnine.coupon.dao;

import java.util.List;
import java.util.Set;

import com.groundnine.coupon.vo.CouponItemQueryVo;
import org.apache.ibatis.annotations.Param;

import com.groundnine.coupon.model.CouponItem;
import com.groundnine.coupon.vo.CouponInfoVo;
import com.groundnine.coupon.vo.CouponItemInfo;

public interface CouponItemDao {

    int insert(CouponItem record);
    
    int batchInsert(@Param("coupon")CouponInfoVo couponInfoVo);
    
    int batchInsertCouponItem(@Param("couponId")Long couponId, @Param("couponCodes")Set<String> couponCodes);

    List<CouponItem> selectByCouponId(@Param("couponId") Long couponId);

    int updateBySelective(@Param("record") CouponItem record);

	List<CouponItemInfo> selectByCondition(@Param("condition") CouponItemQueryVo couponItemQueryVo, @Param("pageStart")int pageStart, @Param("rows")int rows);

	int countAmountByCondition(@Param("condition") CouponItemQueryVo couponItemQueryVo);
}