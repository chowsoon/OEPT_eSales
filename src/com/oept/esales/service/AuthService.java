package com.oept.esales.service;

import java.util.List;
import java.util.Map;

import com.oept.esales.model.Account;
import com.oept.esales.model.Approval;
import com.oept.esales.model.ApprovalDetail;
import com.oept.esales.model.ApprovalStep;
import com.oept.esales.model.Auth;


/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/12/30
 * Description: Authorization service interface.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
public interface AuthService {

	
	/**
	 * �Բ㼶Ϊ������ѯȨ��
	 * @return
	 * @throws Exception
	 */
	public List<Auth> queryAuthLvl(Object[] params) throws Exception;
	
	/**
	 * ��ѯȨ�����㼶
	 * @return
	 * @throws Exception
	 */
	public int queryAuthMaxLvl() throws Exception;
	
	/**
	 * ��ѯְλ����Ȩ��
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Auth> queryAuthItem(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��ְλ�Ƿ��Ѿ�ӵ�и�Ȩ�ޣ�1Ϊӵ�У�0Ϊδӵ�У�
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int queryThisAuthYoN(Object[] params) throws Exception;
	
	/**
	 * Ϊְλ���Ȩ��
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int addAuthItem(Object[] params) throws Exception;
	
	/**
	 * ɾ��ְλ�ĸ�Ȩ�ޣ�ɾ���м��
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int delAuthItem(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��Ȩ��
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Auth queryAuth(Auth auth) throws Exception;
	
	/**
	 * ��ѯ��Ȩ��������Ȩ��
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Auth> queryAuthPar(Object[] params) throws Exception;
	
	
	/**
	 * ��ѯȨ��(boolean)(position,code)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean queryPermissions(Object[] params) throws Exception;
	
	/**
	 * �ύ��������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int submitApproval(Map<String,Object> params) throws Exception;
	
	/**
	 * �ύ���º����������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int submitUpdateApproval(Map<String,Object> params) throws Exception;
	
	/**
	 * ��ѯ�������򼯺�
	 * @return
	 * @throws Exception
	 */
	public List<Approval> queryApprovalRuleList() throws Exception;
	
	/**
	 * ��ѯ����������Ϣ
	 * @return
	 * @throws Exception
	 */
	public Approval queryApprovalRuleDetail(String ruleId) throws Exception;
	
	/**
	 * ɾ����������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int deleteApproval(String id) throws Exception;
	

	/**
	 * �������ִ�нӿ�
	 * @param objectId
	 * @param objectName
	 * @param ActionType (Submit,Approve,Reject)
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String ApprovalExecute(String objectId,String objectName,String ActionType,String userId) throws Exception;
	
	/**
	 * ��ѯ�������������˵�ǰ�����״̬
	 * @param objectId
	 * @param objectName
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ApprovalDetail queryApprovalDetailStatus(String objectId,String objectName,String userId) throws Exception;
	
	/**
	 * ��ѯ�����ϸ��Ϣ�ӿ�
	 * @param id
	 * @param type (Order/Requisition/Contract)
	 * @return
	 * @throws Exception
	 */
	public List<ApprovalStep> queryApproval(String id,String type) throws Exception;
}
