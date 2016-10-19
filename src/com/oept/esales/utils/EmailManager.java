package com.oept.esales.utils;

import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;

import com.oept.esales.dao.SystemPrefDao;
/**
 * @author mwan
 * Version: 1.0
 * Date: 2015/12/18
 * Description:Email utility.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾. All rights reserved.
 */
public class EmailManager {
	@Autowired
	private SystemPrefDao systemPrefDao; //ϵͳ����DAO
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
	public static StringBuffer buildApprovalEmailContent(String objectId, String objectName,String userName)
			throws Exception {
		StringBuffer content = new StringBuffer();
		String internalUrl = "";
		String externalUrl = "";
		content.append(userName+" ���ã�\n\n");
		switch(objectName){
		case "Sales Order":
			objectName = "���۶���";
			internalUrl = "http://192.168.1.162/:8080/OEPT_eSales/order/so_approval_details.do?id="+objectId;
			externalUrl = "http://112.64.124.86/:8080/OEPT_eSales/order/so_approval_details.do?id="+objectId;
			break;
		case "Purchase Order":
			objectName = "�ɹ�����";
			internalUrl = "http://192.168.1.162/:8080/OEPT_eSales/order/po_approval_details.do?id="+objectId;
			externalUrl = "http://112.64.124.86/:8080/OEPT_eSales/order/po_approval_details.do?id="+objectId;
			break;
		case "Stock In Requisition":
			objectName = "��ⵥ";
			internalUrl = "http://192.168.1.162/:8080/OEPT_eSales/inventory/stock_in_approval_details.do?id="+objectId;
			externalUrl = "http://112.64.124.86/:8080/OEPT_eSales/inventory/stock_in_approval_details.do?id="+objectId;
			break;
		case "Stock Out Requisition":
			objectName = "���ⵥ";
			internalUrl = "http://192.168.1.162/:8080/OEPT_eSales/inventory/stock_out_approval_details.do?id="+objectId;
			externalUrl = "http://112.64.124.86/:8080/OEPT_eSales/inventory/stock_out_approval_details.do?id="+objectId;
			break;
		}
		content.append("����"+objectName+"��Ҫ������ˣ������������ӽ���OEPT PSSϵͳ������˲�����\n");
		content.append("������ַ��"+internalUrl+"\n");
		content.append("������ַ��"+externalUrl+"\n");
		content.append("�˷��ʼ���ϵͳ����������ظ���");
		return content;
	}
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
            throws Exception {
    	
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
    /**
    * @Method: createSimpleMail
    * @Description: ����һ��ֻ�����ı����ʼ�
    * @Author:mwan
    *
    * @param session
    * @return
    * @throws Exception
    */ 
    public static MimeMessage createSimpleMailOld(Session session)
            throws Exception {
        //�����ʼ�����
        MimeMessage message = new MimeMessage(session);
        //ָ���ʼ��ķ�����
        message.setFrom(new InternetAddress("oept_eims@oept.com.cn"));
        //ָ���ʼ����ռ��ˣ����ڷ����˺��ռ�����һ���ģ��Ǿ����Լ����Լ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("jingchuan.wan@oept.com.cn"));
        //�ʼ��ı���
        message.setSubject("ֻ�����ı��ļ��ʼ�");
        //�ʼ����ı�����
        message.setContent("��ð���", "text/html;charset=UTF-8");
        //���ش����õ��ʼ�����
        return message;
    }
    /**
    * @Method: createImageMail
    * @Description: ����һ���ʼ����Ĵ�ͼƬ���ʼ�
    * @Author:mwan
    *
    * @param session
    * @return
    * @throws Exception
    */ 
    public static MimeMessage createImageMail(Session session) throws Exception {
        //�����ʼ�
        MimeMessage message = new MimeMessage(session);
        // �����ʼ��Ļ�����Ϣ
        //������
        message.setFrom(new InternetAddress("gacl@sohu.com"));
        //�ռ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xdp_gacl@sina.cn"));
        //�ʼ�����
        message.setSubject("��ͼƬ���ʼ�");

        // ׼���ʼ�����
        // ׼���ʼ���������
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("����һ���ʼ����Ĵ�ͼƬ<img src='cid:xxx.jpg'>���ʼ�", "text/html;charset=UTF-8");
        // ׼��ͼƬ����
        MimeBodyPart image = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("src\\1.jpg"));
        image.setDataHandler(dh);
        image.setContentID("xxx.jpg");
        // �������ݹ�ϵ
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image);
        mm.setSubType("related");

        message.setContent(mm);
        message.saveChanges();
        //�������õ��ʼ�д�뵽E�����ļ�����ʽ���б���
        message.writeTo(new FileOutputStream("E:\\ImageMail.eml"));
        //���ش����õ��ʼ�
        return message;
    }
    /**
     * @Method: createAttachMail
     * @Description: ����һ����������ʼ�
     * @Author:mwan
     *
     * @param session
     * @return
     * @throws Exception
     */ 
     public static MimeMessage createAttachMail(Session session) throws Exception{
         MimeMessage message = new MimeMessage(session);
         
         //�����ʼ��Ļ�����Ϣ
         //������
         message.setFrom(new InternetAddress("gacl@sohu.com"));
         //�ռ���
         message.setRecipient(Message.RecipientType.TO, new InternetAddress("xdp_gacl@sina.cn"));
         //�ʼ�����
         message.setSubject("JavaMail�ʼ����Ͳ���");
         
         //�����ʼ����ģ�Ϊ�˱����ʼ����������������⣬��Ҫʹ��charset=UTF-8ָ���ַ�����
         MimeBodyPart text = new MimeBodyPart();
         text.setContent("ʹ��JavaMail�����Ĵ��������ʼ�", "text/html;charset=UTF-8");
         
         //�����ʼ�����
         MimeBodyPart attach = new MimeBodyPart();
         DataHandler dh = new DataHandler(new FileDataSource("src\\2.jpg"));
         attach.setDataHandler(dh);
         attach.setFileName(dh.getName());  //
         
         //���������������ݹ�ϵ
         MimeMultipart mp = new MimeMultipart();
         mp.addBodyPart(text);
         mp.addBodyPart(attach);
         mp.setSubType("mixed");
         
         message.setContent(mp);
         message.saveChanges();
         //��������Emailд�뵽E�̴洢
         message.writeTo(new FileOutputStream("E:\\attachMail.eml"));
         //�������ɵ��ʼ�
         return message;
     }
     /**
      * @Method: createMixedMail
      * @Description: ����һ��������ʹ�ͼƬ���ʼ�
      * @Author:mwan
      *
      * @param session
      * @return
      * @throws Exception
      */ 
      public static MimeMessage createMixedMail(Session session) throws Exception {
          //�����ʼ�
          MimeMessage message = new MimeMessage(session);
          
          //�����ʼ��Ļ�����Ϣ
          message.setFrom(new InternetAddress("gacl@sohu.com"));
          message.setRecipient(Message.RecipientType.TO, new InternetAddress("xdp_gacl@sina.cn"));
          message.setSubject("�������ʹ�ͼƬ�ĵ��ʼ�");
          
          //����
          MimeBodyPart text = new MimeBodyPart();
          text.setContent("xxx����Ů��xxxx<br/><img src='cid:aaa.jpg'>","text/html;charset=UTF-8");
          
          //ͼƬ
          MimeBodyPart image = new MimeBodyPart();
          image.setDataHandler(new DataHandler(new FileDataSource("src\\3.jpg")));
          image.setContentID("aaa.jpg");
          
          //����1
          MimeBodyPart attach = new MimeBodyPart();
          DataHandler dh = new DataHandler(new FileDataSource("src\\4.zip"));
          attach.setDataHandler(dh);
          attach.setFileName(dh.getName());
          
          //����2
          MimeBodyPart attach2 = new MimeBodyPart();
          DataHandler dh2 = new DataHandler(new FileDataSource("src\\����.zip"));
          attach2.setDataHandler(dh2);
          attach2.setFileName(MimeUtility.encodeText(dh2.getName()));
          
          //������ϵ:���ĺ�ͼƬ
          MimeMultipart mp1 = new MimeMultipart();
          mp1.addBodyPart(text);
          mp1.addBodyPart(image);
          mp1.setSubType("related");
          
          //������ϵ:���ĺ͸���
          MimeMultipart mp2 = new MimeMultipart();
          mp2.addBodyPart(attach);
          mp2.addBodyPart(attach2);
          
          //�������ĵ�bodypart
          MimeBodyPart content = new MimeBodyPart();
          content.setContent(mp1);
          mp2.addBodyPart(content);
          mp2.setSubType("mixed");
          
          message.setContent(mp2);
          message.saveChanges();
          
          message.writeTo(new FileOutputStream("E:\\MixedMail.eml"));
          //���ش����õĵ��ʼ�
          return message;
      }

}
