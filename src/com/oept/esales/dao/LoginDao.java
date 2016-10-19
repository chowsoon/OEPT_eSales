package com.oept.esales.dao;

import com.oept.esales.model.User;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/11/06
 * Description: Categories DAO interface.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾. All rights reserved.
 */
public interface LoginDao {

	/**
	 * ��¼��֤�ӿ�
	 */
	public User login2(User user) throws Exception;
	
	/**
	 * ��¼��¼�û���¼ʱ��
	 */
	public int lastLogin(User user) throws Exception;
	
	
}
