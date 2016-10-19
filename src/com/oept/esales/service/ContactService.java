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
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
public interface ContactService {

	/**
	 * �����ϵ����Ϣ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int addContact(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��ϵ�˼���
	 * @return
	 * @throws Exception
	 */
	public List<Contact> queryContactList() throws Exception;
	
	/**
	 * ��ѯ��ϵ����ϸ��Ϣ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Contact queryContactDetails(Object[] params) throws Exception;
	
	/**
	 * ������ϵ����Ϣ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateContact(Object[] params) throws Exception;
	
	/**
	 * ɾ����ϵ��
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int delContact(Object[] params) throws Exception;
}
