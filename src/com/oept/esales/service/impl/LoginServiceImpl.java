package com.oept.esales.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oept.esales.dao.LoginDao;
import com.oept.esales.model.User;
import com.oept.esales.service.LoginService;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/11/16
 * Description: User login, validation and logout service implements.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDao;
	
	/**
	 * ʵ�ֽӿڵ�¼��֤
	 */
	@Override
	public User login(User user) throws Exception {
		// TODO Auto-generated method stub
		return loginDao.login2(user);
	}

	/**
	 * ��¼��¼ʱ��
	 */
	@Override
	public int lastLogin(User user) throws Exception {
		// TODO Auto-generated method stub
		return loginDao.lastLogin(user);
	}
	
}
