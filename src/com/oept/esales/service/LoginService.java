package com.oept.esales.service;

import java.util.Map;

import com.oept.esales.model.User;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/11/16
 * Description: User login, validation and logout service interface.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
public interface LoginService {

	//��¼��֤
	public User login(User user) throws Exception;
	
	//��¼��¼ʱ��
	public int lastLogin(User user) throws Exception;
}
