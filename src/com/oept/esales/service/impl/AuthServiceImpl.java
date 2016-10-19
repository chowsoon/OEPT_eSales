package com.oept.esales.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oept.esales.action.AuthAct;
import com.oept.esales.dao.AuthDao;
import com.oept.esales.dao.UserDao;
import com.oept.esales.model.Approval;
import com.oept.esales.model.ApprovalDetail;
import com.oept.esales.model.ApprovalItem;
import com.oept.esales.model.ApprovalItemPer;
import com.oept.esales.model.ApprovalStep;
import com.oept.esales.model.Auth;
import com.oept.esales.model.User;
import com.oept.esales.service.AuthService;
import com.oept.esales.service.EmailService;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/12/30
 * Description: Authorization info service implements.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {

	private static final Log logger = LogFactory.getLog(AuthAct.class);
	@Autowired
	private AuthDao authDao;
	
	@Autowired
	private UserDao userDao;
	
	@Qualifier("emailService")
	@Autowired
	private EmailService emailService;

	/**
	 * �Բ㼶Ϊ������ѯȨ��
	 */
	@Override
	public List<Auth> queryAuthLvl(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return authDao.queryAuthLvl(params);
	}

	/**
	 * ��ѯȨ�����㼶
	 */
	@Override
	public int queryAuthMaxLvl() throws Exception {
		// TODO Auto-generated method stub
		return authDao.queryAuthMaxLvl();
	}

	/**
	 * ��ѯ��ְλ����Ȩ��
	 */
	@Override
	public List<Auth> queryAuthItem(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return authDao.queryAuthItem(params);
	}

	/**
	 * ��ѯ��ְλ�Ƿ��Ѿ�ӵ�и�Ȩ��
	 */
	@Override
	public int queryThisAuthYoN(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return authDao.queryThisAuthYoN(params);
	}

	/**
	 * Ϊְλ���Ȩ��
	 */
	@Override
	public int addAuthItem(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return authDao.addAuthItem(params);
	}

	/**
	 * ɾ��ְλ�ĸ�Ȩ��
	 */
	@Override
	public int delAuthItem(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return authDao.delAuthItem(params);
	}

	/**
	 * ��ѯ��Ȩ��
	 */
	@Override
	public Auth queryAuth(Auth auth) throws Exception {
		// TODO Auto-generated method stub
		return authDao.queryAuth(auth);
	}

	/**
	 * ��ѯ��Ȩ��������Ȩ��
	 */
	@Override
	public List<Auth> queryAuthPar(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return authDao.queryAuthPar(params);
	}

	/**
	 * ��ѯȨ��(boolean)(position,code)
	 */
	@Override
	public boolean queryPermissions(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return authDao.queryPermissions(params);
	}

	/**
	 * �ύ��������
	 */
	@Override
	@Transactional
	public int submitApproval(Map<String,Object> params) throws Exception {
		//��ȡ���в���
		String name = String.valueOf(params.get("name"));
		String apprObject = String.valueOf(params.get("apprObject"));
		String apprObjectCd = String.valueOf(params.get("apprObjectCd"));
		String type = String.valueOf(params.get("type"));
		String typeCd = String.valueOf(params.get("typeCd"));
		String rollback = String.valueOf(params.get("rollback"));
		String rollbackCd = String.valueOf(params.get("rollbackCd"));
		boolean email = (boolean) params.get("email");
		boolean inbox = (boolean) params.get("inbox");
		String usersId = String.valueOf(params.get("usersId"));
		String method = String.valueOf(params.get("method"));
		String methodCd = String.valueOf(params.get("methodCd"));
		String userId = String.valueOf(params.get("userId"));
		
		//��ѯ��˹����Ƿ���ڣ�����Ѵ��ڣ�����ʾ��Ϣ����������ӹ���
		Object[] paramsIsTrue = new Object[]{apprObjectCd,typeCd};
		boolean trueOfalse = authDao.queryApprovalRuleBoolean(paramsIsTrue);
		if(trueOfalse == true){
			return 3;
		}else{
			Object[] paramsRule = new Object[]{
					name,apprObjectCd,apprObject,typeCd,type,rollbackCd,rollback,email,inbox,userId
				};
				//�ύ�������򣬴�����˹����rule
				int ruleId = authDao.saveApprovalRule(paramsRule);
				String userIdArray[] = usersId.split("/");
				String methodArray[] = method.split("/");
				String methodCdArray[] = methodCd.split("/");
				int itemId=0;
				int res = 0;
				for(int i=0;i<methodArray.length;i++){
					if(i == 0){
						Object[] paramsItem = new Object[]{ruleId,methodCdArray[i],methodArray[i],userId};
						//������˹���������̱�item��������̵�һ����û�и����У�
						itemId = authDao.saveApprovalRuleItemOne(paramsItem);
					}else{
						Object[] paramsItem = new Object[]{ruleId,methodCdArray[i],methodArray[i],itemId,userId};
						//������˹���������̱�item��������̵�һ����û�и����У�
						itemId = authDao.saveApprovalRuleItem(paramsItem);
					}
					
//					ApprovalItem aip = new ApprovalItem();
//					aip.setRule_id(ruleId);
//					//���ݲ���������ѯ��������id
//					List<ApprovalItem> approvalItems = authDao.queryApprovalRuleItem(aip);
//					//��ȡ������� id
//					String itemId = approvalItems.get(i).getItem_id();
					//����������̣��û��м������
					String userIds[] = userIdArray[i].split(";");
					for(int k=0;k<userIds.length;k++){
						Object[] paramsItemPer = new Object[]{itemId,userIds[k],userId};
						res = authDao.saveApprovalItemUser(paramsItemPer);
					}
				}
				return res;
		}
		
		
	}
	
	/**
	 * �ύ���º����������
	 */
	@Override
	@Transactional
	public int submitUpdateApproval(Map<String, Object> params)
			throws Exception {
		//��ȡ���в���
		String name = String.valueOf(params.get("name"));
		String apprObject = String.valueOf(params.get("apprObject"));
		String apprObjectCd = String.valueOf(params.get("apprObjectCd"));
		String type = String.valueOf(params.get("type"));
		String typeCd = String.valueOf(params.get("typeCd"));
		String rollback = String.valueOf(params.get("rollback"));
		String rollbackCd = String.valueOf(params.get("rollbackCd"));
		boolean email = (boolean) params.get("email");
		boolean inbox = (boolean) params.get("inbox");
		String usersId = String.valueOf(params.get("usersId"));
		String method = String.valueOf(params.get("method"));
		String methodCd = String.valueOf(params.get("methodCd"));
		String userId = String.valueOf(params.get("userId"));
		String ruleId = String.valueOf(params.get("id"));
		//����
		Object[] paramsRule = new Object[]{
			name,apprObjectCd,apprObject,typeCd,type,rollbackCd,rollback,email,inbox,userId,ruleId
		};
		int res = 0;
		//����rule id��������
		res = authDao.updateApprovalRule(paramsRule);
		if(res != 0){
			//����
			ApprovalItem approvalItemParams = new ApprovalItem();
			approvalItemParams.setRule_id(ruleId);
			//��ѯrule id�Ӽ�¼
			List<ApprovalItem> approvalItem = authDao.queryApprovalRuleItem(approvalItemParams);
			//����ɾ���Ӽ�¼
			for(int i=approvalItem.size();i>0;i--){
				//����
				Object paramsItemDel[] = new Object[]{approvalItem.get((i-1)).getItem_id()}; 
				//����itemIdɾ���Ӽ�¼�����
				int res1 = authDao.delApprovalItemPer(paramsItemDel);
				//����itemIdɾ���Ӽ�¼
				int res2 = authDao.delApprovalItem(paramsItemDel);
			}
			String userIdArray[] = usersId.split("/");
			String methodArray[] = method.split("/");
			String methodCdArray[] = methodCd.split("/");
			int itemId=0;
			for(int i=0;i<methodArray.length;i++){
				if(i == 0){
					Object[] paramsItem = new Object[]{ruleId,methodCdArray[i],methodArray[i],userId};
					//������˹���������̱�item��������̵�һ����û�и����У�
					itemId = authDao.saveApprovalRuleItemOne2(paramsItem);
				}else{
					Object[] paramsItem = new Object[]{ruleId,methodCdArray[i],methodArray[i],itemId,userId};
					//������˹���������̱�item��������̵�һ����û�и����У�
					itemId = authDao.saveApprovalRuleItem2(paramsItem);
				}
				
				//����������̣��û��м������
				String userIds[] = userIdArray[i].split(";");
				for(int k=0;k<userIds.length;k++){
					Object[] paramsItemPer = new Object[]{itemId,userIds[k],userId};
					res = authDao.saveApprovalItemUser(paramsItemPer);
				}
			}
		}
		return res;
	}

	/**
	 * ��ѯ�������򼯺�
	 */
	@Override
	public List<Approval> queryApprovalRuleList() throws Exception {
		// TODO Auto-generated method stub
		return authDao.queryApprovalRule();
	}

	/**
	 * ��ѯ����������Ϣ
	 */
	@Override
	public Approval queryApprovalRuleDetail(String ruleId) throws Exception {
		// TODO Auto-generated method stub
		Approval approval = new Approval();
		//����ruleId��ѯ������������Ϣ
		Object[] paramsApprovalRuleDetail = new Object[]{ruleId};
		approval = authDao.queryApprovalRuleDetail(paramsApprovalRuleDetail);
		//���ݲ�ѯ������Ϣ��ѯ�������µ�����Ϣ
		ApprovalItem approvalItemParams = new ApprovalItem();
		approvalItemParams.setRule_id(ruleId);
		List<ApprovalItem> approvalItem = new ArrayList<ApprovalItem>();
		approvalItem = authDao.queryApprovalRuleItem(approvalItemParams);
		for(int i=0;i<approvalItem.size();i++){
			String itemId = approvalItem.get(i).getItem_id();
			//����itemId��ѯ���������û�id
			ApprovalItemPer approvalItemPerParams = new ApprovalItemPer();
			approvalItemPerParams.setItem_id(itemId);
			List<ApprovalItemPer> approvalItemPer = authDao.queryApprovalItemPer(approvalItemPerParams);
			//����userId��ѯ�û���Ϣ
			for(int j=0;j<approvalItemPer.size();j++){
				String userId = approvalItemPer.get(j).getUser_id();
				approvalItemPer.get(j).setUser(userDao.selectUserDetail(userId));
			}
			approvalItem.get(i).setApprovalItemPer(approvalItemPer);
		}
		approval.setApprovalItem(approvalItem);
		return approval;
	}

	/**
	 * ɾ����������
	 */
	@Override
	public int deleteApproval(String id) throws Exception {
		// TODO Auto-generated method stub
		//����
		ApprovalItem approvalItemParams = new ApprovalItem();
		approvalItemParams.setRule_id(id);
		//��ѯrule id�Ӽ�¼
		List<ApprovalItem> approvalItem = authDao.queryApprovalRuleItem(approvalItemParams);
		//����ɾ���Ӽ�¼
		for(int i=approvalItem.size();i>0;i--){
			//����
			Object paramsItemDel[] = new Object[]{approvalItem.get((i-1)).getItem_id()}; 
			//����itemIdɾ���Ӽ�¼�����
			int res1 = authDao.delApprovalItemPer(paramsItemDel);
			//����itemIdɾ���Ӽ�¼
			int res2 = authDao.delApprovalItem(paramsItemDel);
		}
		Object[] params = new Object[]{id};
		//ɾ������
		int res = authDao.delApproval(params);
		return res;
	}

	/**
	 * �������ִ�нӿ�
	 * @param objectId
	 * @param objectName
	 * @param ActionType (Submit,Approve,Reject)
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public String ApprovalExecute(String objectId, String objectName,
			String actionType, String userId) throws Exception {
		// TODO Auto-generated method stub
		String status_cd ;
		String status_val;
		boolean process_flg;
		//ȡ��Object id ����
		String objectIdType = "";
		if(objectName.equals("Account")||objectName.equals("Product")){
			objectIdType = objectName;
		}else{
			String[] Osplit = objectName.split(" ");
			for(int i=0;i<Osplit.length;i++){
				if(i == (Osplit.length-1)){
					if(Osplit[i].equals("Order")){
						objectIdType = Osplit[i];
					}
					if(Osplit[i].equals("Requisition")){
						objectIdType = Osplit[i];
					}
					if(Osplit[i].equals("Contract")){
						objectIdType = Osplit[i];
					}
				}
			}
		}
		//�жϴ�������Ƿ���ֵ
		if(objectId!=null&&objectName!=null&&actionType!=null&&userId!=null&&!objectId.equals("")&&!objectName.equals("")&&!actionType.equals("")&&!userId.equals("")){
			//����ObjectName�ж��Ƿ������Ч����˹���code��
			Object[] params_qaar = new Object[]{objectName};
			int resValue = authDao.queryAvailApprovalRule(params_qaar);
			if(resValue != 0){
				//������Ч����˹���
				//����ObjectName��ѯ��˹���
				Approval approval = new Approval();
				approval.setObject_cd(objectName);
				Approval resApproval = authDao.queryCondApprovalRule(approval);
				//�ж�ActionType��ֵ
				if(actionType.equals("Submit")){
					status_cd = "Pending";
					status_val = "�����";
					process_flg = true;
					int resStepId = 0;
					//actionTypeΪsubmit,�ύ���
					//���ObjectId�µ������ϸ
					clearObjectIdApproval(objectId,objectName);
					//������Ӧ�Ĳ�����ϸ��¼Apprvoal
					//������˹����ѯ����ApprovalItem
					ApprovalItem approvalItem = new ApprovalItem();
					approvalItem.setRule_id(resApproval.getRule_id());
					List<ApprovalItem> resApprovalItem = authDao.queryApprovalRuleItem(approvalItem);
					//�����贴��Step
					for(int i=0;i<resApprovalItem.size();i++){
						//����step����
						if(i==0){
							//��һ�����ж�Id���ʹ�������
							//����ֵ��objectId,status_cd,status_val,method_cd,method_name,rollback_cd,rollback_name,process_flg��
							Object[] paramsNewStep = new Object[]{
									objectId,status_cd,status_val,resApprovalItem.get(i).getMethod_cd(),
									resApprovalItem.get(i).getMethod_name(),resApproval.getRollback_type_cd(),
									resApproval.getRollback_type_name(),process_flg};
							resStepId = authDao.addApprovalStep(paramsNewStep, true, objectIdType);
						}else{
							//���ǵ�һ��
							process_flg = false;
							//����ֵ��par_id,objectId,status_cd,status_val,method_cd,method_name,rollback_cd,rollback_name,process_flg��
							Object[] paramsNewStep = new Object[]{
									resStepId,objectId,status_cd,status_val,resApprovalItem.get(i).getMethod_cd(),
									resApprovalItem.get(i).getMethod_name(),resApproval.getRollback_type_cd(),
									resApproval.getRollback_type_name(),process_flg};
							resStepId = authDao.addApprovalStep(paramsNewStep, false, objectIdType);
						}
						//��ѯ�������������������Ϣ
						ApprovalItemPer approvalItemPer = new ApprovalItemPer();
						approvalItemPer.setItem_id(resApprovalItem.get(i).getItem_id());
						List<ApprovalItemPer> resApprovalItemPer = authDao.queryApprovalItemPer(approvalItemPer);
						for(int j=0;j<resApprovalItemPer.size();j++){
							//����detail���� 
							//����ֵ��objectId,status_cd,status_val,method_cd,method_name,rollback_cd,rollback_name,process_flg��
							Object[] paramsNewDetail = new Object[]{
									resStepId,status_cd,status_val,resApprovalItemPer.get(j).getUser_id(),
									resApprovalItem.get(i).getMethod_cd(),resApprovalItem.get(i).getMethod_name(),
									resApproval.getRollback_type_cd(),resApproval.getRollback_type_name()};
							authDao.addApprovalDetail(paramsNewDetail);
							try {
								//�ж�Ϊ��һ������֪ͨ��Ϣ
								if(i==0){
									//�ж�֪ͨ��Ϣ������֪ͨ��Ϣ
									//�ʼ�����
									if(resApproval.isEmail()==true){
										//��ѯ�������ϸ��Ϣ
										User user= userDao.selectUserDetail(resApprovalItemPer.get(j).getUser_id());
										emailService.createSimpleMail(user.getEmail(),"���µ�����Ҫ�������",emailService.buildApprovalEmailContent(objectId,objectName,user.getUserName()).toString());
									}
									//վ������
									if(resApproval.isInbox()==true){
										
									}
								}
							} catch (Exception e) {
								logger.info(e.getMessage());
								throw e;
							}
						}
					}
					return "Pending";
				}else if(actionType.equals("Approve")){
					//actionTypeΪapprove,�����
					//����objectId��ѯ������Ϣ
					process_flg = true;
					ApprovalStep approvalStep = new ApprovalStep();
					if(objectIdType.equals("Order")){
						approvalStep.setOrder_id(objectId);
					}else if(objectIdType.equals("Requisition")){
						approvalStep.setRequisition_id(objectId);
					}else if(objectIdType.equals("Contract")){
						approvalStep.setContract_id(objectId);
					}else if(objectIdType.equals("Account")){
						approvalStep.setAccount_id(objectId);
					}else if(objectIdType.equals("Product")){
						approvalStep.setProduct_id(objectId);
					}
					//��ѯ�Ƿ���Ч������
					boolean resValidFlag = authDao.queryApprovalStepValidFlag(approvalStep);
					if(resValidFlag == true){
						ApprovalStep resApprovalStep = authDao.queryApprovalStepFlag(approvalStep);
						//������������״̬
						status_cd = "Approved";
						status_val = "�����";
						Object[] paramsUpdateToApprove = new Object[]{status_cd,status_val,resApprovalStep.getId(),userId};
						int resUpdADVal = authDao.updateApprovalDetailToApprove(paramsUpdateToApprove);
						//�ж�method value
						if(resApprovalStep.getMethod_cd().equals("All Must Pass")){
							//��ѯ��ǰstep������detail�����״̬
							ApprovalDetail paramsApprovalDetails = new ApprovalDetail();
							paramsApprovalDetails.setStep_id(resApprovalStep.getId());
							List<ApprovalDetail> resApprovalDetails = authDao.queryApprovalDetailCond(paramsApprovalDetails);
							boolean allMustPass = true;
							for(int i=0;i<resApprovalDetails.size();i++){
								if(!resApprovalDetails.get(i).getStatus_cd().equals("Approved")){
									allMustPass = false;
								}
							}
							if(allMustPass == true){
								//���ĵ�ǰ����process_flg״̬Ϊfalse
								process_flg = false;
								status_cd = "Approved";
								status_val = "�����";
								Object[] paramsUpdateFlag = new Object[]{process_flg,status_cd,status_val,resApprovalStep.getId()};
								int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
								//��ѯ�Ƿ�����һ��
								Object[] paramsQueryNext = new Object[]{resApprovalStep.getId()};
								boolean resQueryNext = authDao.queryApprovalStepNext(paramsQueryNext);
								if(resQueryNext == true){
									//�޸���һ������process_flg״̬Ϊtrue
									process_flg = true;
									Object[] paramsUpdateNextFlag = new Object[]{process_flg,resApprovalStep.getId()};
									int resUpdNASVal = authDao.updateNextApprovalStepFlag(paramsUpdateNextFlag);
									//�ж�֪ͨ��Ϣ������֪ͨ��Ϣ
									try {
										//�ʼ�����
										if(resApproval.isEmail()==true){
											//��ѯ��һ����˲�����Ϣ
											ApprovalStep step = new ApprovalStep();
											step.setPar_id(resApprovalStep.getId());
											ApprovalStep resStep = authDao.queryApprovalStep(step);
											//��ѯ�ò�������ϸ��Ϣ
											ApprovalDetail approvalDetail = new ApprovalDetail();
											approvalDetail.setStep_id(resStep.getId());
											List<ApprovalDetail> resApprovalDetail = authDao.queryApprovalDetailCond(approvalDetail);
											for(int i=0;i<resApprovalDetail.size();i++){
												User user= userDao.selectUserDetail(resApprovalDetail.get(i).getTo_approve());
												emailService.createSimpleMail(user.getEmail(),"���µ�����Ҫ�������",emailService.buildApprovalEmailContent(objectId,objectName,user.getUserName()).toString());
											}
										}
										//վ������
										if(resApproval.isInbox()==true){
											
										}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										logger.info(e.getMessage());
										throw e;
									}
									return "Pending";
								}else{
									//��ǰ����Ϊ���һ��
									return "Approved";
								}
							}else{
								//��δȫ��ͨ��
								return "Pending";
							}
						}else{
							//���ĵ�ǰ����process_flg״̬Ϊfalse
							process_flg = false;
							status_cd = "Approved";
							status_val = "�����";
							Object[] paramsUpdateFlag = new Object[]{process_flg,status_cd,status_val,resApprovalStep.getId()};
							int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
							//��ѯ�Ƿ�����һ��
							Object[] paramsQueryNext = new Object[]{resApprovalStep.getId()};
							boolean resQueryNext = authDao.queryApprovalStepNext(paramsQueryNext);
							if(resQueryNext == true){
								//�޸���һ������process_flg״̬Ϊtrue
								process_flg = true;
								Object[] paramsUpdateNextFlag = new Object[]{process_flg,resApprovalStep.getId()};
								int resUpdNASVal = authDao.updateNextApprovalStepFlag(paramsUpdateNextFlag);
								//�ж�֪ͨ��Ϣ������֪ͨ��Ϣ
								try {
									//�ʼ�����
									if(resApproval.isEmail()==true){
										//��ѯ��һ������˲�����Ϣ
										ApprovalStep step = new ApprovalStep();
										step.setPar_id(resApprovalStep.getId());
										ApprovalStep resStep = authDao.queryApprovalStep(step);
										//��ѯ�ò�������ϸ��Ϣ
										ApprovalDetail approvalDetail = new ApprovalDetail();
										approvalDetail.setStep_id(resStep.getId());
										List<ApprovalDetail> resApprovalDetail = authDao.queryApprovalDetailCond(approvalDetail);
										for(int i=0;i<resApprovalDetail.size();i++){
											User user= userDao.selectUserDetail(resApprovalDetail.get(i).getTo_approve());
											emailService.createSimpleMail(user.getEmail(),"���µ�����Ҫ�������",emailService.buildApprovalEmailContent(objectId,objectName,user.getUserName()).toString());
										}
									}
									//վ������
									if(resApproval.isInbox()==true){
										
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									logger.info(e.getMessage());
									throw e;
								}
								return "Pending";
							}else{
								//��ǰΪ���һ��
								return "Approved";
							}
						}
					}else{
						//��������Ч����˲���
						return "ApprovalStep Not Valid";
					}
					
				}else if(actionType.equals("Reject")){
					//actionTypeΪreject,��˾ܾ�           
					process_flg = true;
					ApprovalStep approvalStep = new ApprovalStep();
					if(objectIdType.equals("Order")){
						approvalStep.setOrder_id(objectId);
					}else if(objectIdType.equals("Requisition")){
						approvalStep.setRequisition_id(objectId);
					}else if(objectIdType.equals("Contract")){
						approvalStep.setContract_id(objectId);
					}else if(objectIdType.equals("Account")){
						approvalStep.setAccount_id(objectId);
					}else if(objectIdType.equals("Product")){
						approvalStep.setProduct_id(objectId);
					}
					//��ѯ�Ƿ���Ч������
					boolean resValidFlag = authDao.queryApprovalStepValidFlag(approvalStep);
					if(resValidFlag == true){
						//������Ч�Ĳ�����
						ApprovalStep resApprovalStep = authDao.queryApprovalStepFlag(approvalStep);
						//������������״̬
						status_cd = "Rejected";
						status_val = "�Ѿܾ�";
						Object[] paramsUpdateToApprove = new Object[]{status_cd,status_val,resApprovalStep.getId(),userId};
						int resUpdADVal = authDao.updateApprovalDetailToApprove(paramsUpdateToApprove);
						//�ж�method value
						if(resApprovalStep.getMethod_cd().equals("Anyone Pass")){
							//methodΪAnyone Pass
							//�жϵ�ǰstep�µ���������˵����״̬
							ApprovalDetail paramsApprovalDetail = new ApprovalDetail();
							paramsApprovalDetail.setStep_id(resApprovalStep.getId());
							List<ApprovalDetail> resApprovalDetails =  authDao.queryApprovalDetailCond(paramsApprovalDetail);
							int resReturn = 0;
							for(int i=0;i<resApprovalDetails.size();i++){
								//��ѯdetail�����״̬
								if(resApprovalDetails.get(i).getStatus_cd().equals("Pending")){
									resReturn = 1;
								}
							}
							if(resReturn == 0){
								//�жϾܾ��ع�����rollback_type_cd��ֵ���ع�
								if(resApprovalStep.getRollback_type_cd().equals("Revert")){
									//�жϵ�ǰstep�Ƿ�Ϊ��һ��
									Object[] paramsFirstStep = new Object[]{resApprovalStep.getId()};
									boolean resFirstStep = authDao.queryApprovalStepFirst(paramsFirstStep);
									if(resFirstStep == true){
										//���ڲ���Ϊ��һ��
										process_flg = false;
										status_cd = "Rejected";
										status_val = "�Ѿܾ�";
										Object[] paramsUpdateFlag = new Object[]{process_flg,status_cd,status_val,resApprovalStep.getId()};
										int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
										return "Rejected";
									}else{
										//��ǰ���費�ǵ�һ��
										//���ĵ�ǰ����flagΪfalse
										process_flg = false;
										status_cd = "Pending";
										status_val = "�����";
										Object[] paramsUpdateFlag = new Object[]{process_flg,status_cd,status_val,resApprovalStep.getId()};
										int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
										//���ĵ�ǰStep Detail��¼Ϊ��ʼֵ�����¿�ʼ
										Object[] paramsInitDetail = new Object[]{status_cd,status_val,resApprovalStep.getId()};
										authDao.updateApprovalDetailInit(paramsInitDetail);
										//������һ������flagΪtrue
										process_flg = true;
										Object[] paramsUpdateFlag2 = new Object[]{process_flg,status_cd,status_val,resApprovalStep.getPar_id()};
										int resUpdASVal2 = authDao.updateApprovalStepFlag(paramsUpdateFlag2);
										//���ĵ�ǰStep Detail��¼Ϊ��ʼֵ�����¿�ʼ
										Object[] paramsInitDetail2 = new Object[]{status_cd,status_val,resApprovalStep.getPar_id()};
										authDao.updateApprovalDetailInit(paramsInitDetail2);
										//�ж�֪ͨ��Ϣ������֪ͨ��Ϣ
										try {
											//�ʼ�����
											if(resApproval.isEmail()==true){
												//��ѯ��һ���Ĳ�����Ϣ
												ApprovalStep step = new ApprovalStep();
												step.setId(resApprovalStep.getPar_id());
												ApprovalStep resStep = authDao.queryApprovalStep(step);
												//��ѯ�ò�������ϸ��Ϣ
												ApprovalDetail approvalDetail = new ApprovalDetail();
												approvalDetail.setStep_id(resStep.getId());
												List<ApprovalDetail> resApprovalDetail = authDao.queryApprovalDetailCond(approvalDetail);
												for(int i=0;i<resApprovalDetail.size();i++){
													User user= userDao.selectUserDetail(resApprovalDetail.get(i).getTo_approve());
													emailService.createSimpleMail(user.getEmail(),"���µ�����Ҫ�������",emailService.buildApprovalEmailContent(objectId,objectName,user.getUserName()).toString());
												}
											}
											//վ������
											if(resApproval.isInbox()==true){
												
											}
										} catch (Exception e) {
											// TODO Auto-generated catch block
											logger.info(e.getMessage());
											throw e;
										}
										return "Pending";
									}
								}else if(resApprovalStep.getRollback_type_cd().equals("Restart")){
									//�жϵ�ǰstep�Ƿ�Ϊ��һ��
									Object[] paramsFirstStep = new Object[]{resApprovalStep.getId()};
									boolean resFirstStep = authDao.queryApprovalStepFirst(paramsFirstStep);
									if(resFirstStep == true){
										//���ڲ���Ϊ��һ��
										process_flg = false;
										status_cd = "Rejected";
										status_val = "�Ѿܾ�";
										Object[] paramsUpdateFlag = new Object[]{process_flg,status_cd,status_val,resApprovalStep.getId()};
										int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
										return "Rejected";
									}else{
										//���ĳ���һ������ step��¼flagΪfalse
										List<ApprovalStep> resAllStep = authDao.queryApprovalSteps(approvalStep);
										for(int i=0;i<resAllStep.size();i++){
											if(i==0){
												//���ĵ�һ����process_flg״̬Ϊtrue
												process_flg = true;
												status_cd = "Pending";
												status_val = "�����";
												Object[] paramsUpdateFlag = new Object[]{process_flg,status_cd,status_val,resAllStep.get(i).getId()};
												int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
												//�ж�֪ͨ��Ϣ������֪ͨ��Ϣ
												try {
													//�ʼ�����
													if(resApproval.isEmail()==true){
														//����һ��������˷���֪ͨ
														//��ѯ�ò�������ϸ��Ϣ
														ApprovalDetail approvalDetail = new ApprovalDetail();
														approvalDetail.setStep_id(resAllStep.get(i).getId());
														List<ApprovalDetail> resApprovalDetail = authDao.queryApprovalDetailCond(approvalDetail);
														for(int j=0;j<resApprovalDetail.size();j++){
															User user= userDao.selectUserDetail(resApprovalDetail.get(j).getTo_approve());
															emailService.createSimpleMail(user.getEmail(),"���µ�����Ҫ�������",emailService.buildApprovalEmailContent(objectId,objectName,user.getUserName()).toString());
														}
													}
													//վ������
													if(resApproval.isInbox()==true){
														
													}
												} catch (Exception e) {
													// TODO Auto-generated catch block
													logger.info(e.getMessage());
													throw e;
												}
											}else{
												//���ĵ�ǰ����process_flg״̬Ϊfalse
												process_flg = false;
												status_cd = "Pending";
												status_val = "�����";
												Object[] paramsUpdateFlag = new Object[]{process_flg,status_cd,status_val,resAllStep.get(i).getId()};
												int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
											}
											//��������Step Detail��¼Ϊ��ʼֵ�����¿�ʼ
											status_cd = "Pending";
											status_val = "�����";
											Object[] paramsInitDetail = new Object[]{status_cd,status_val,resAllStep.get(i).getId()};
											authDao.updateApprovalDetailInit(paramsInitDetail);
										}
										return "Pending";
									}
								}else if(resApprovalStep.getRollback_type_cd().equals("Cancel")){
									//ȫ��Ϊ�ܾ�������Rejected
									process_flg = false;
									status_cd = "Rejected";
									status_val = "�Ѿܾ�";
									Object[] paramsUpdateFlag = new Object[]{process_flg,status_cd,status_val,resApprovalStep.getId()};
									int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
									return "Rejected";
									
								}else{
									//�ع����ʹ���
									return "Error:Rollback_Type";
								}
								
							}else{
								//��δ���״̬������Pending
								return "Pending";
							}
						}else{
							//MethodΪAnryone Pass and Anyone Reject��All Must Pass
							//�жϾܾ��ع�����rollback_type_cd��ֵ���ع�
							if(resApprovalStep.getRollback_type_cd().equals("Revert")){
								//��ѯ��ǰִ�в����Ƿ�Ϊ��һ��
								Object[] params = new Object[]{resApprovalStep.getId()};
								boolean resFirstStep = authDao.queryApprovalStepFirst(params);
								//�ǵ�һ��ֱ�ӷ���Rejected
								if(resFirstStep == true){
									//���Ĳ�����ϢΪ�ܾ�Rejected
									process_flg = false;
									status_cd = "Rejected";
									status_val = "�Ѿܾ�";
									Object[] paramsUpdateFlag = new Object[]{process_flg,status_cd,status_val,resApprovalStep.getId()};
									int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
									return "Rejected";
								}else{
									//���ĵ�ǰ����flagΪfalse
									process_flg = false;
									Object[] paramsUpdateFlag = new Object[]{process_flg,resApprovalStep.getId()};
									int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
									//���ĵ�ǰStep Detail��¼Ϊ��ʼֵ�����¿�ʼ
									status_cd = "Pending";
									status_val = "�����";
									Object[] paramsInitDetail = new Object[]{status_cd,status_val,resApprovalStep.getId()};
									authDao.updateApprovalDetailInit(paramsInitDetail);
									//������һ������flagΪtrue
									process_flg = true;
									Object[] paramsUpdateFlag2 = new Object[]{process_flg,resApprovalStep.getPar_id()};
									int resUpdASVal2 = authDao.updateApprovalStepFlag(paramsUpdateFlag2);
									//���ĵ�ǰStep Detail��¼Ϊ��ʼֵ�����¿�ʼ
									Object[] paramsInitDetail2 = new Object[]{status_cd,status_val,resApprovalStep.getPar_id()};
									authDao.updateApprovalDetailInit(paramsInitDetail2);
									//�ж�֪ͨ��Ϣ������֪ͨ��Ϣ
									try {
										//�ʼ�����
										if(resApproval.isEmail()==true){
											//��ѯ��һ���Ĳ�����Ϣ
											ApprovalStep step = new ApprovalStep();
											step.setId(resApprovalStep.getPar_id());
											ApprovalStep resStep = authDao.queryApprovalStep(step);
											//��ѯ�ò�������ϸ��Ϣ
											ApprovalDetail approvalDetail = new ApprovalDetail();
											approvalDetail.setStep_id(resStep.getId());
											List<ApprovalDetail> resApprovalDetail = authDao.queryApprovalDetailCond(approvalDetail);
											for(int i=0;i<resApprovalDetail.size();i++){
												User user= userDao.selectUserDetail(resApprovalDetail.get(i).getTo_approve());
												emailService.createSimpleMail(user.getEmail(),"���µ�����Ҫ�������",emailService.buildApprovalEmailContent(objectId,objectName,user.getUserName()).toString());
											}
										}
										//վ������
										if(resApproval.isInbox()==true){
											
										}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										logger.info(e.getMessage());
										throw e;
									}
									return "Pending";
								}
								
							}else if(resApprovalStep.getRollback_type_cd().equals("Restart")){
								//��ѯ��ǰִ�в����Ƿ�Ϊ��һ��
								Object[] params = new Object[]{resApprovalStep.getId()};
								boolean resFirstStep = authDao.queryApprovalStepFirst(params);
								//�ǵ�һ��ֱ�ӷ���Rejected
								if(resFirstStep == true){
									//���Ĳ�����ϢΪ�ܾ�Rejected
									process_flg = false;
									status_cd = "Rejected";
									status_val = "�Ѿܾ�";
									Object[] paramsUpdateFlag = new Object[]{process_flg,status_cd,status_val,resApprovalStep.getId()};
									int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
									return "Rejected";
								}else{
									//���ĳ���һ������ step��¼flagΪfalse
									List<ApprovalStep> resAllStep = authDao.queryApprovalSteps(approvalStep);
									for(int i=0;i<resAllStep.size();i++){
										if(i==0){
											//���ĵ�һ����process_flg״̬Ϊtrue
											process_flg = true;
											Object[] paramsUpdateFlag = new Object[]{process_flg,resAllStep.get(i).getId()};
											int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
											//�ж�֪ͨ��Ϣ������֪ͨ��Ϣ
											try {
												//�ʼ�����
												if(resApproval.isEmail()==true){
													//����һ��������˷���֪ͨ
													//��ѯ�ò�������ϸ��Ϣ
													ApprovalDetail approvalDetail = new ApprovalDetail();
													approvalDetail.setStep_id(resAllStep.get(i).getId());
													List<ApprovalDetail> resApprovalDetail = authDao.queryApprovalDetailCond(approvalDetail);
													for(int j=0;j<resApprovalDetail.size();j++){
														User user= userDao.selectUserDetail(resApprovalDetail.get(j).getTo_approve());
														emailService.createSimpleMail(user.getEmail(),"���µ�����Ҫ�������",emailService.buildApprovalEmailContent(objectId,objectName,user.getUserName()).toString());
													}
												}
												//վ������
												if(resApproval.isInbox()==true){
													
												}
											} catch (Exception e) {
												// TODO Auto-generated catch block
												logger.info(e.getMessage());
												throw e;
											}
										}else{
											//���ĵ�ǰ����process_flg״̬Ϊfalse
											process_flg = false;
											Object[] paramsUpdateFlag = new Object[]{process_flg,resAllStep.get(i).getId()};
											int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
										}
										//��������Step Detail��¼Ϊ��ʼֵ�����¿�ʼ
										status_cd = "Pending";
										status_val = "�����";
										Object[] paramsInitDetail = new Object[]{status_cd,status_val,resAllStep.get(i).getId()};
										authDao.updateApprovalDetailInit(paramsInitDetail);
									}
									return "Pending";
								}
								
							}else if(resApprovalStep.getRollback_type_cd().equals("Cancel")){
								//ȡ�������������
								//���Ĳ�����ϢΪ�ܾ�Rejected
								process_flg = false;
								status_cd = "Rejected";
								status_val = "�Ѿܾ�";
								Object[] paramsUpdateFlag = new Object[]{process_flg,status_cd,status_val,resApprovalStep.getId()};
								int resUpdASVal = authDao.updateApprovalStepFlag(paramsUpdateFlag);
								return "Rejected";
								
							}else{
								//�ع����ʹ���
								return "Error:Rollback_Type";
							}
						}
					}else{
						//resValidFlag == false,��������Ч�Ĳ���
						return "ApprovalStep Not Valid";
					}
						
				}else{
					//ִ�����ʹ���
					return "Error:AccountType";
				}
			}else{
				//��������Ч����˹���������˷���
				return "Approval Not Required";
			}
			
		}else{
			//�������ֵΪ��
			return "Params Value is Null";
		}
		
	}
	
	/**
	 * ���ObjectId�µ������ϸ���������ִ�нӿڣ�
	 */
	public void clearObjectIdApproval(String objectId, String objectName) throws Exception{
		try{
			//���� --��ѯapproval detail�����
			ApprovalStep paramsAs = new ApprovalStep();
			//ȡ��Object id ����
			if(objectName.equals("Account")){
				paramsAs.setAccount_id(objectId);
			}else if(objectName.equals("Product")){
				paramsAs.setProduct_id(objectId);
			}else{
			String[] objectNameType = objectName.split(" ");
			for(int i=0;i<objectNameType.length;i++){
				if(i == (objectNameType.length-1)){
					if(objectNameType[i].equals("Order")){
						paramsAs.setOrder_id(objectId);
					}
					if(objectNameType[i].equals("Requisition")){
						paramsAs.setRequisition_id(objectId);
					}
					if(objectNameType[i].equals("Contract")){
						paramsAs.setContract_id(objectId);
					}
				}
			}
			}
			//query approval detail data is null?
			boolean validStepValue = authDao.queryApprovalStepValidData(paramsAs);
			if(validStepValue == true){
				//����ObjectId��ѯapproval step��id
				List<ApprovalStep> as = authDao.queryApprovalStepIdDel(paramsAs);
				for(int i=as.size();i>0;i--){
					//���ݲ�ѯ����stepidɾ��������ϸ��Ϣ
					ApprovalDetail approvalDetail = new ApprovalDetail();
					approvalDetail.setStep_id(as.get((i-1)).getId());
					//��ѯdetail�Ƿ�����Ч����
					boolean validDetailValue = authDao.queryApprovalDetailValidData(approvalDetail);
					if(validDetailValue == true){
						authDao.delApprovalDetailStepId(approvalDetail);
					}else{
					}
					//ɾ����˲����
					ApprovalStep approvalStep = new ApprovalStep();
					approvalStep.setId(as.get((i-1)).getId());
					authDao.delApprovalStepId(approvalStep);
				}
			}else{

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * ��ѯ�������������˵�ǰ�����״̬
	 */
	@Override
	public ApprovalDetail queryApprovalDetailStatus(String objectId,
			String objectName, String userId) throws Exception {
		// TODO Auto-generated method stub
		//ȡ��Object id ����
		String objectIdType = "";
		if(objectName.equals("Account")||objectName.equals("Product")){
			objectIdType = objectName;
		}else{
			String[] Osplit = objectName.split(" ");
			for(int i=0;i<Osplit.length;i++){
				if(i == (Osplit.length-1)){
					if(Osplit[i].equals("Order")){
						objectIdType = Osplit[i];
					}
					if(Osplit[i].equals("Requisition")){
						objectIdType = Osplit[i];
					}
					if(Osplit[i].equals("Contract")){
						objectIdType = Osplit[i];
					}
				}
			}
		}
		ApprovalDetail returnApprovalDetail = new ApprovalDetail();
		//�жϴ�������Ƿ���ֵ
		if(objectId!=null&&objectName!=null&&userId!=null&&!objectId.equals("")&&!objectName.equals("")&&!userId.equals("")){
			//����objectId��ѯ������Ϣ
			ApprovalStep approvalStep = new ApprovalStep();
			if(objectIdType.equals("Order")){
				approvalStep.setOrder_id(objectId);
			}else if(objectIdType.equals("Requisition")){
				approvalStep.setRequisition_id(objectId);
			}else if(objectIdType.equals("Contract")){
				approvalStep.setContract_id(objectId);
			}else if(objectIdType.equals("Account")){
				approvalStep.setAccount_id(objectId);
			}else if(objectIdType.equals("Product")){
				approvalStep.setProduct_id(objectId);
			}
			//��ѯ�ö�����˵����в���
			List<ApprovalStep> resApprovalSteps = authDao.queryApprovalSteps(approvalStep);
			for(int i=0;i<resApprovalSteps.size();i++){
				ApprovalDetail approvalDetail = new ApprovalDetail();
				approvalDetail.setStep_id(resApprovalSteps.get(i).getId());
				List<ApprovalDetail> resApprovalDetails = authDao.queryApprovalDetailCond(approvalDetail);
				for(int j=0;j<resApprovalDetails.size();j++){
					if(resApprovalDetails.get(j).getTo_approve().equals(userId)){
						resApprovalDetails.get(j).setProcess_flg(resApprovalSteps.get(i).isProcess_flg());
						returnApprovalDetail = resApprovalDetails.get(j);
					}
				}
			}
			return returnApprovalDetail;
		}else{
			return returnApprovalDetail;
		}
	}

	/**
	 * ��ѯ�����ϸ��Ϣ�ӿ�
	 */
	@Override
	public List<ApprovalStep> queryApproval(String id,String type)
			throws Exception {
		// TODO Auto-generated method stub
		ApprovalStep params_as = new ApprovalStep();
		//�ж�id����
		if(type.equals("Order")){
			params_as.setOrder_id(id);
		}else if(type.equals("Requisition")){
			params_as.setRequisition_id(id);
		}else if(type.equals("Contract")){
			params_as.setContract_id(id);
		}else if(type.equals("Account")){
			params_as.setAccount_id(id);
		}else if(type.equals("Product")){
			params_as.setProduct_id(id);
		}
		//��ѯ�Ƿ�����Ч����
		boolean res = authDao.queryApprovalStepValidData(params_as);
		if(res == true){
			//��ѯ���еĲ���
			List<ApprovalStep> resSteps = authDao.queryApprovalSteps(params_as);
			//���ݲ�ѯ����step id��ѯ����detail
			for(int i=0;i<resSteps.size();i++){
				ApprovalDetail params_ad = new ApprovalDetail();
				params_ad.setStep_id(resSteps.get(i).getId());
				List<ApprovalDetail> resDetails = authDao.queryApprovalDetailCond(params_ad);
				for(int k=0;k<resDetails.size();k++){
					//��ѯ�û�����ϸ��Ϣ
					User user = userDao.selectUserDetail(resDetails.get(k).getTo_approve());
					resDetails.get(k).setUser(user);
				}
				//��ѯ���Ĳ���������벽�����
				resSteps.get(i).setStepDetails(resDetails);
			}
			return resSteps;
		}else{
			return null;
		}
	}
}
