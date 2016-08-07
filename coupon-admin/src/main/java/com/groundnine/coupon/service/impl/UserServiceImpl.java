package com.groundnine.coupon.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.groundnine.coupon.dao.UserDao;
import com.groundnine.coupon.model.User;
import com.groundnine.coupon.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;
	
	@Override
	public User getUser(User user) {
		if(user ==null  || user.getName() ==null || user.getPassword()==null){
			return null;
		}
		user = userDao.selectUser(user);
		return user;
	}

}
