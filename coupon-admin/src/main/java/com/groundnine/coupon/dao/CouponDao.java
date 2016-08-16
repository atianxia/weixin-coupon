package com.groundnine.coupon.dao;

import java.util.Collection;
import java.util.List;

import com.groundnine.coupon.model.Coupon;
import org.apache.ibatis.annotations.Param;

import com.groundnine.coupon.vo.CouponInfoVo;
import com.groundnine.coupon.vo.CouponQueryVo;

public interface CouponDao {

    int insert(Coupon record);

    Coupon selectByCouponId(Long couponId);
    
    List<Coupon> queryCoupons(@Param("coupon") CouponQueryVo couponQueryVo, @Param("pageStart")int pageStart, @Param("rows") int rows);

    int updateByPrimaryKeySelective(Coupon record);

	int countCouponAmount(@Param("coupon") CouponQueryVo couponQueryVo);

	int deleteCouponByIds(@Param("couponIds") Collection<Long> couponIds);

	int batchInsert(CouponInfoVo coupon);

	int updateCouponStatusByIds(@Param("couponIds") Collection<Long> couponIdList, @Param("couponStatus") int couponStatus);

}