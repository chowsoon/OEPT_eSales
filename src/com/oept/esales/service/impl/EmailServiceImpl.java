package com.oept.esales.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oept.esales.dao.SystemPrefDao;
import com.oept.esales.service.EmailService;
/**
 * @author mwan
 * Version: 1.0
 * Date: 2016/2/14
 * Description: Email manager service implements.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private SystemPrefDao systemPrefDao; //ϵͳ����DAO
	
	@Override
	public StringBuffer buildApprovalEmailContent(String objectId,
			String objectName, String userName) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer content = new StringBuffer();
		String internalUrl = "";
		String externalUrl = "";
		content.append("�𾴵�"+userName+" ���ã�");
		content.append("<br/>");
		content.append("<br/>");
		switch(objectName){
		case "Sales Order":
			objectName = "���۶���";
			internalUrl = "http://192.168.1.162:8088/OEPT_eSales/order/so_approval_details.do?id="+objectId;
			externalUrl = "http://112.64.124.86:8088/OEPT_eSales/order/so_approval_details.do?id="+objectId;
			break;
		case "Purchase Order":
			objectName = "�ɹ�����";
			internalUrl = "http://192.168.1.162:8088/OEPT_eSales/order/po_approval_details.do?id="+objectId;
			externalUrl = "http://112.64.124.86:8088/OEPT_eSales/order/po_approval_details.do?id="+objectId;
			break;
		case "Stock In Requisition":
			objectName = "��ⵥ";
			internalUrl = "http://192.168.1.162:8088/OEPT_eSales/inventory/stock_in_approval_details.do?id="+objectId;
			externalUrl = "http://112.64.124.86:8088/OEPT_eSales/inventory/stock_in_approval_details.do?id="+objectId;
			break;
		case "Stock Out Requisition":
			objectName = "���ⵥ";
			internalUrl = "http://192.168.1.162:8088/OEPT_eSales/inventory/stock_out_approval_details.do?id="+objectId;
			externalUrl = "http://112.64.124.86:8088/OEPT_eSales/inventory/stock_out_approval_details.do?id="+objectId;
			break;
		case "Product":
			objectName = "��Ʒ/����";
			internalUrl = "http://192.168.1.162:8088/OEPT_eSales/prodadmin/approval_details.do?id="+objectId;
			externalUrl = "http://112.64.124.86:8088/OEPT_eSales/prodadmin/approval_details.do?id="+objectId;
			break;
		case "Account":
			objectName = "�ͻ�/��Ӧ��";
			internalUrl = "http://192.168.1.162:8088/OEPT_eSales/account/approval_details.do?id="+objectId;
			externalUrl = "http://112.64.124.86:8088/OEPT_eSales/account/approval_details.do?id="+objectId;
			break;
		}
		content.append("����"+objectName+"��Ҫ������ˣ������������ӽ���OEPT PSSϵͳ������˲�����");
		content.append("<br/>");
		content.append("������ַ��"+internalUrl);
		content.append("<br/>");
		content.append("������ַ��"+externalUrl);
		content.append("<br/>");
		content.append("<br/>");
		content.append("********************");
		content.append("�˷��ʼ���ϵͳ����������ظ���");
		content.append("********************");
		return content;
	}

	@Override
	public boolean createSimpleMail(String toAddress, String subject,
			String content) throws Exception {
		// TODO Auto-generated method stub
	   	if( !(systemPrefDao.getPrefByCode("mail_available").getSystem_preference_value().equals("on")) ){
    		return false;
    	}
    	Properties prop = new Properties();
    	String mail_host = systemPrefDao.getPrefByCode("mail_host").getSystem_preference_value();
    	String mail_transport_protocal = systemPrefDao.getPrefByCode("mail_transport_protocol").getSystem_preference_value();
        prop.setProperty("mail.host", mail_host);
        prop.setProperty("mail.transport.protocol", mail_transport_protocal);
        String mail_smtp_auth = "";
        if(systemPrefDao.getPrefByCode("mail_smtp_auth").getSystem_preference_value().equals("on")){
        	mail_smtp_auth = "true";
        }else{
        	mail_smtp_auth = "false";
        }
        prop.setProperty("mail.smtp.auth", mail_smtp_auth);
        //ʹ��JavaMail�����ʼ���5������
        //1������session
        Session session = Session.getInstance(prop);
        //����Session��debugģʽ�������Ϳ��Բ鿴��������Email������״̬
        session.setDebug(true);
        //2��ͨ��session�õ�transport����
        Transport ts = session.getTransport();
        //3��ʹ��������û��������������ʼ��������������ʼ�ʱ����������Ҫ�ύ������û����������smtp���������û��������붼ͨ����֤֮����ܹ����������ʼ����ռ��ˡ�
        String mail_username = systemPrefDao.getPrefByCode("mail_username").getSystem_preference_value();
        String mail_password = systemPrefDao.getPrefByCode("mail_password").getSystem_preference_value();
        ts.connect(mail_host, mail_username, mail_password);
        //�����ʼ�����
        MimeMessage message = new MimeMessage(session);
        //ָ���ʼ��ķ�����
        message.setFrom(new InternetAddress(mail_username));
        //ָ���ʼ����ռ��ˣ����ڷ����˺��ռ�����һ���ģ��Ǿ����Լ����Լ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
        //�ʼ��ı���
        message.setSubject(subject);
        //�ʼ����ı�����
        message.setContent(content, "text/html;charset=UTF-8");
        //�����ʼ�
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
        
        return true;
	}

}
