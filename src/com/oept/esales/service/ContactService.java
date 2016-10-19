package com.oept.esales.service;

import java.util.List;
import java.util.Map;

import com.oept.esales.model.Account;
import com.oept.esales.model.Contact;


/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/12/21
 * Description: User login, validation and logout service interface.
 * Copyright (c) 2015 上海基辕科技有限公司版权所有.
 */
public interface ContactService {

	/**
	 * 添加联系人信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int addContact(Object[] params) throws Exception;
	
	/**
	 * 查询联系人集合
	 * @return
	 * @throws Exception
	 */
	public List<Contact> queryContactList() throws Exception;
	
	/**
	 * 查询联系人详细信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Contact queryContactDetails(Object[] params) throws Exception;
	
	/**
	 * 更新联系人信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateContact(Object[] params) throws Exception;
	
	/**
	 * 删除联系人
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int delContact(Object[] params) throws Exception;
}
