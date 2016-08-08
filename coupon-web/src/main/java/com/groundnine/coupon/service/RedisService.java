package com.groundnine.coupon.service;

public interface RedisService {

	String getRedisValue(String code);

	void setRedisValueEx(String key, Long seconds, String value);

}
