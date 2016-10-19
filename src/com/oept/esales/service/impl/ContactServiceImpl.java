package com.oept.esales.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oept.esales.dao.ContactDao;
import com.oept.esales.model.Contact;
import com.oept.esales.service.ContactService;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/12/21
 * Description: Account info service implements.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactDao contactDao;

	/**
	 * �����ϵ����Ϣ
	 */
	@Override
	public int addContact(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return contactDao.addContact(params);
	}

	/**
	 * ��ѯ��ϵ�˼���
	 */
	@Override
	public List<Contact> queryContactList() throws Exception {
		// TODO Auto-generated method stub
		return contactDao.queryContactList();
	}

	/**
	 * ��ѯ��ϵ����ϸ��Ϣ
	 */
	@Override
	public Contact queryContactDetails(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return contactDao.queryContactDetails(params);
	}

	/**
	 * ������ϵ����Ϣ
	 */
	@Override
	public int updateContact(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return contactDao.updateContact(params);
	}

	/**
	 * ɾ����ϵ��
	 */
	@Override
	public int delContact(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return contactDao.delContact(params);
	}
	
	

}
