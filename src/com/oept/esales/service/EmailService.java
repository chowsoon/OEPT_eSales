package com.oept.esales.service;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2016/2/14
 * Description: Email manager service interface.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
public interface EmailService {

	/**
	 * @Method: buildApprovalEmailContent
	 * @Description: ���ɴ�����ʼ�����
	 * @Author:mwan
	 *
	 * @param objectId
	 * @param objectName
	 * @param userId
	 * 
	 * @return email content
	 * @throws Exception
	 */ 
	public StringBuffer buildApprovalEmailContent(String objectId, String objectName,String userName)
			throws Exception;
	
	 /**
	  * @Method: createSimpleMail
	  * @Description: ����һ��ֻ�����ı����ʼ�
	  * @Author:mwan
	  *
	  * @param toAddress
	  * @param subject
	  * @param content
	  * 
	  * @return
	  * @throws Exception
	  */ 
	  public boolean createSimpleMail(String toAddress,String subject,String content)
	            throws Exception;
}
