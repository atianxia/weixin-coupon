package com.groundnine.coupon.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.groundnine.coupon.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {
	
	@Resource
    protected RedisTemplate<Serializable, Serializable> redisTemplate;
	
	@Override
	public String getRedisValue(final String code){
		return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize(code);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    return redisTemplate.getStringSerializer().deserialize(value);
                }
                return null;
            }
        });
	}
	
	@Override
	public void setRedisValueEx(final String key, final Long seconds, final String value) {
		if(StringUtils.isNotBlank(value)){
			redisTemplate.execute(new RedisCallback<Object>() {
	            @Override
	            public Object doInRedis(RedisConnection connection) throws DataAccessException {
	                connection.setEx(redisTemplate.getStringSerializer().serialize(key), seconds,
	                               redisTemplate.getStringSerializer().serialize(value));
	                return null;
	            }
	        });
		}
		
	}
}
