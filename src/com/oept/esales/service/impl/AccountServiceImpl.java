package com.oept.esales.service.impl;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oept.esales.dao.AccountDao;
import com.oept.esales.dao.ApprovalHistoryDao;
import com.oept.esales.dao.ListOfValueDao;
import com.oept.esales.model.Account;
import com.oept.esales.model.ApprovalHistory;
import com.oept.esales.model.Product;
import com.oept.esales.service.AccountService;
import com.oept.esales.service.AuthService;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/12/21
 * Description: Account info service implements.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private ApprovalHistoryDao approvalHistoryDao; //approval history info DAO
	@Autowired
	private ListOfValueDao listOfValueDao;

	/**
	 * ���ݽڵ���ѯ�ò�����Ϣ����
	 */
	@Override
	public List<Account> selectTreeLvl(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.selectTreeLvl(params);
	}

	/**
	 * ��ѯAccount tree������
	 */
	@Override
	public int selectTreeMaxLvl() throws Exception {
		// TODO Auto-generated method stub
		return accountDao.selectTreeMaxLvl();
	}

	/**
	 * ��ѯ���и�������
	 */
	@Override
	public List<Account> selectAllParentName() throws Exception {
		// TODO Auto-generated method stub
		return accountDao.selectAllParentName();
	}

	/**
	 * ����id��ѯ��λ�ڵ���Ϣ
	 */
	@Override
	public Account selectAcDeId(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.selectAcDeId(params);
	}

	/**
	 * ��ӵ�λtree�ڵ�
	 */
	@Override
	public int addAccountCat(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.addAccountCat(params);
	}
	
	/**
	 * ���ĵ�λtree�ڵ�
	 */
	@Override
	public int updateAccountCat(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.updateAccountCat(params);
	}

	/**
	 * ����idɾ���ڵ�
	 */
	@Override
	public int deleteAcNode(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.deleteAcNode(params);
	}

	/**
	 * ����catId��ѯ���е�λ��Ϣ
	 */
	@Override
	public List<Account> selectAllAtDe(Account account) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.selectAllAtDe(account);
	}
	

	/**
	 * ����aId��ѯ��λ��Ϣ����
	 */
	@Override
	public Account selectAtDe(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.selectAtDe(params);
	}

	/**
	 * ��ӵ�λ
	 */
	@Override
	public int addAt(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		//���ǰ��ѯ�Ƿ����
		Account account = new Account();
		account.setAccountName(params[0].toString());
		account.setAccountType(params[2].toString());
		boolean res = accountDao.queryAccountExist(account);
		if(res)
			return 2;
		else
			return accountDao.addAt(params);
	}

	/**
	 * ���ĵ�λ
	 */
	@Override
	public int editAt(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.editAt(params);
	}

	/**
	 * ����aId�������Ϣ
	 */
	@Override
	public int deleteAt(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.deleteAt(params);
	}

	/**
	 * ��ѯָ�������ĵ�λ��Ϣ
	 */
	@Override
	public List<Account> getAccount(Account account)
			throws Exception {
		// TODO Auto-generated method stub
		return accountDao.getAccount(account);
	}

	/**
	 * ����catId��ѯ�ýڵ��µ��ӽڵ�
	 */
	@Override
	public List<Account> setAccountCatCnode(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.setAccountCatCnode(params);
	}

	/**
	 * ��ѯ�ýڵ����ӽڵ���Ŀ
	 */
	@Override
	public int setAccountCatCnodeCount(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.setAccountCatCnodeCount(params);
	}

	/**
	 *  ���ĵ�λ��Ϣ�����ֶ�cId
	 */
	@Override
	public int delectAtc(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.delectAtc(params);
	}
	
	@Override
	public boolean addApprovalHistory(ApprovalHistory approval_history)
			throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		approval_history.setCreated_date(nowdate);
		return approvalHistoryDao.addHistory(approval_history);
	}
	
	@Override
	public String updateAccountStatusById(Account account) throws Exception {
		// TODO Auto-generated method stub
		if(accountDao.updateAccount(account)){
			return "success";
		}else{
			return "���³����쳣";
		}
	}
	
	@Qualifier("authService")
	@Autowired
	private AuthService authService;
	@Override
	@Transactional
	public String updateAccountStatusByIdFacade(Account account) throws Exception {
		// TODO Auto-generated method stub
		try{
			String status_code = account.getAccountStatus();
			String success_msg = "";
			if(status_code.equals("submitted")){
				success_msg = "�ύ�ͻ�/��Ӧ��";
			}else if(status_code.equals("Published")){
				success_msg = "�ύ��˿ͻ�/��Ӧ��";
			}else if(status_code.equals("Rejected")){
				success_msg = "�ύ�ܾ��ͻ�/��Ӧ��";
			}else if(status_code.equals("Reopen")){
				success_msg = "�ؿ��ͻ�/��Ӧ��";
			}
			
			String ActionType = "";
			ApprovalHistory approvalHistory = new ApprovalHistory();
			switch(status_code){
			case "submitted":
				ActionType = "Submit";
				approvalHistory.setAccount_id(account.getAccountId());
				approvalHistory.setCreated_by_user_id(account.getUpdateBy());
				approvalHistory.setDescription("��"+account.getUpdateByName()+"�ύ�˿ͻ�/��Ӧ����Ϣ��");
				this.addApprovalHistory(approvalHistory);
				break;
			case "Published":
				ActionType = "Approve";
				approvalHistory.setAccount_id(account.getAccountId());
				approvalHistory.setCreated_by_user_id(account.getUpdateBy());
				approvalHistory.setDescription("��"+account.getUpdateByName()+"���ͨ���˿ͻ�/��Ӧ����Ϣ��");
				this.addApprovalHistory(approvalHistory);
				break;
			case "Rejected":
				ActionType = "Reject";
				approvalHistory.setAccount_id(account.getAccountId());
				approvalHistory.setCreated_by_user_id(account.getUpdateBy());
				approvalHistory.setDescription("��"+account.getUpdateByName()+"�ܾ��˿ͻ�/��Ӧ����Ϣ��");
				this.addApprovalHistory(approvalHistory);
				break;
			case "Reopen":
				ActionType = "Reopen";
				status_code = "Not Published";
				approvalHistory.setAccount_id(account.getAccountId());
				approvalHistory.setCreated_by_user_id(account.getUpdateBy());
				approvalHistory.setDescription("��"+account.getUpdateByName()+"�ؿ��˿ͻ�/��Ӧ����Ϣ��");
				this.addApprovalHistory(approvalHistory);
				break;
			default:
				ActionType = "Not Defined";
				break;
			}
			if(!ActionType.equals("Reopen")){
				String objectName = "Account";
				switch(authService.ApprovalExecute(account.getAccountId(), objectName, ActionType, account.getUpdateBy())){
				case "Approval Not Required":
					break;
				case "Pending":
					status_code = "Pending";
					break;
				case "Approved":
					status_code = "Published";
					break;
				case "Rejected":
					status_code = "Rejected";
					break;
				}
			}
			account.setAccountStatus(status_code);
			account.setAccountStatusVal(listOfValueDao.getValueByCodeName("Account Status", status_code));
			String update_status = this.updateAccountStatusById(account);
			if(update_status.equals("success")){
				return "{'objname':'"+success_msg+"'}";
			}else{
				//return "{'errmsg':'"+update_status+"'}";
				throw new RuntimeException(update_status);
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	@Override
	public List<ApprovalHistory> getApprovalHistories(
			ApprovalHistory approval_history, String start, String limit,
			String sortColumn, String sortDir) throws Exception {
		// TODO Auto-generated method stub
		return approvalHistoryDao.getHistories(approval_history, start, limit, sortColumn, sortDir);
	}

	/**
	 * ��ȡ��λ����
	 */
	@Override
	public int getAccountCount() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 */
	@Override
	public List<Account> getAccountsForApprover(Account account,
			String approver_id, String start, String limit, String sortColumn,
			String sortDir) throws Exception {
		// TODO Auto-generated method stub
		return accountDao.getAccountsForApprover(account, approver_id, start, limit, sortColumn, sortDir);
	}
	
}
