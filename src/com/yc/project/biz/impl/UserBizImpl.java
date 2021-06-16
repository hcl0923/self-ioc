package com.yc.project.biz.impl;

import com.yc.project.biz.UserBiz;
import com.yc.spring.annotation.Service;
@Service
public class UserBizImpl implements UserBiz{
	@Override
	public void find() {
		System.out.println("UserBizImpl  find......");
	}
}
