package com.oept.esales.dao;

import java.util.List;

import com.oept.esales.model.Approval;
import com.oept.esales.model.ApprovalDetail;
import com.oept.esales.model.ApprovalItem;
import com.oept.esales.model.ApprovalItemPer;
import com.oept.esales.model.ApprovalStep;
import com.oept.esales.model.Auth;




/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/12/30
 * Description: Categories DAO interface.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾. All rights reserved.
 */
public interface AuthDao {
	
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
	 * ��ѯְλ����Ȩ�ޣ�����List��
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
	 * ��˹����趨����----------------------------
	 */
	/**
	 * ������˹���
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int saveApprovalRule(Object[] params) throws Exception;
	
	/**
	 * ������˹���
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateApprovalRule(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Approval> queryApprovalRule() throws Exception;
	
	/**
	 * ��ѯ��˹����Ƿ��Ѵ���
	 * @return
	 * @throws Exception
	 */
	public boolean queryApprovalRuleBoolean(Object[] params) throws Exception;

	
	/**
	 * ��ѯ��˹���(������ѯ)(name����)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Approval> queryApprovalRuleCond(Approval approval) throws Exception;
	
	/**
	 * ��ѯ����������Ϣ������Approval����(id����)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Approval queryApprovalRuleDetail(Object[] params) throws Exception;
	
	/**
	 * ����������̣����̵�һ����
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int saveApprovalRuleItemOne (Object[] params) throws Exception;
	
	/**
	 * ����������̣����̵�һ����
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int saveApprovalRuleItemOne2 (Object[] params) throws Exception;
	/**
	 * �����������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int saveApprovalRuleItem (Object[] params) throws Exception;
	
	/**
	 * �����������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int saveApprovalRuleItem2 (Object[] params) throws Exception;
	
	/**
	 * ��ѯ���������Ϣ��������ѯ����id���ԣ�
	 * @param approval
	 * @return
	 * @throws Exception
	 */
	public List<ApprovalItem> queryApprovalRuleItem(ApprovalItem approvalItem) throws Exception;
	
	/**
	 * ���������̵������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int saveApprovalItemUser(Object[] params) throws Exception;
	
	/**
	 * ��ѯ�������������������Ϣ
	 * @param approvalItem
	 * @return
	 * @throws Exception
	 */
	public List<ApprovalItemPer> queryApprovalItemPer(ApprovalItemPer approvalItemPer) throws Exception;
	
	/**
	 * ɾ���������Ϣ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int delApprovalItemPer(Object[] params) throws Exception;
	
	/**
	 * ɾ���������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int delApprovalItem(Object[] params) throws Exception;
	
	/**
	 * ɾ����˹���
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int delApproval(Object[] params) throws Exception;
	
	//BEGIN �������ִ�нӿ�----------------------------------------------
	/**
	 * ����ObjectName�ж��Ƿ������Ч����˹���code��
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int queryAvailApprovalRule(Object[] params) throws Exception;
	
	/**
	 * ����ObjectId��ѯapproval Step��
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<ApprovalStep> queryApprovalStepId(ApprovalStep approvalStep) throws Exception;
	
	/**
	 * ����ObjectId��ѯapproval Step������ɾ����ѯ
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<ApprovalStep> queryApprovalStepIdDel(ApprovalStep approvalStep) throws Exception;
	
	/**
	 * ɾ�������������ݷ���
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int delApprovalDetailStepId(ApprovalDetail approvalDetail)throws Exception;
	
	/**
	 * ɾ����˲�������ݷ���
	 * @param approvalStep
	 * @return
	 * @throws Exception
	 */
	public int delApprovalStepId(ApprovalStep approvalStep) throws Exception;
	
	/**
	 * ��ѯObjectId step����Ч����
	 * trueΪ�������ݣ�falseΪ����������
	 * @param approvalStep
	 * @return
	 * @throws Exception
	 */
	public boolean queryApprovalStepValidData(ApprovalStep approvalStep) throws Exception;
	
	/**
	 * ��ѯObjectId Detail����Ч����
	 * trueΪ�������ݣ�falseΪ����������
	 * @param approvalStep
	 * @return
	 * @throws Exception
	 */
	public boolean queryApprovalDetailValidData(ApprovalDetail approvalDetail) throws Exception;
	
	/**
	 * ������ѯ����ѯ��˹���
	 * @param approval
	 * @return
	 * @throws Exception
	 */
	public Approval queryCondApprovalRule(Approval approval) throws Exception;
	
	/**
	 * ������ִ�в���
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int addApprovalStep(Object[] params,boolean firstStep,String idType) throws Exception;
	
	/**
	 * ������ִ�в�����ϸ��Ϣ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int addApprovalDetail(Object[] params) throws Exception;
	
	/**
	 * ��ѯobjectId���е���˲���
	 * @param approvalStep
	 * @return
	 * @throws Exception
	 */
	public List<ApprovalStep> queryApprovalSteps(ApprovalStep approvalStep) throws Exception; 
	
	/**
	 * ��ѯ�Ƿ������Ч����
	 * @param approvalStep
	 * @return
	 * @throws Exception
	 */
	public boolean queryApprovalStepValidFlag(ApprovalStep approvalStep) throws Exception;
	
	/**
	 * ��ѯObjectId����ִ�е���˲���
	 * @param approvalStep
	 * @return
	 * @throws Exception
	 */
	public ApprovalStep queryApprovalStepFlag(ApprovalStep approvalStep) throws Exception;
	
	/**
	 * ��ѯ������Ϣ����
	 * @param approvalStep
	 * @return
	 * @throws Exception
	 */
	public ApprovalStep queryApprovalStep(ApprovalStep approvalStep) throws Exception;
	
	/**
	 * ��ѯ��˲����µ���ϸ��Ϣ
	 * @param approvalDetial
	 * @return
	 * @throws Exception
	 */
	public List<ApprovalDetail> queryApprovalDetailCond(ApprovalDetail approvalDetail) throws Exception;
	
	/**
	 * ���������ϸ��Ϣ
	 * @param approvalDetail
	 * @return
	 * @throws Exception
	 */
	public int updateApprovalDetailToApprove(Object[] params) throws Exception;
	
	/**
	 * ������˲������״̬
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateApprovalStepFlag(Object[] params) throws Exception;
	
	/**
	 * ��ѯ�����Ƿ�����һ��
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean queryApprovalStepNext(Object[] params) throws Exception;
	
	/**
	 * ������һ����˲������״̬
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateNextApprovalStepFlag(Object[] params) throws Exception;
	
	/**
	 * ��ѯ�����Ƿ�Ϊ��һ��
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean queryApprovalStepFirst(Object[] params) throws Exception;
	
	/**
	 * ��ʼ��������ϸ��¼����
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateApprovalDetailInit(Object[] params) throws Exception;
	
	/**
	 * ��ѯ���û���Ҫ�������������״̬
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ApprovalDetail queryApprovalDetailUserId(Object[] params) throws Exception;
	
	
	//END �������ִ�нӿ�----------------------------------------------
}
