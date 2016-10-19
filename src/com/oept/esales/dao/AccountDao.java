package com.oept.esales.dao;

import java.util.List;

import com.oept.esales.model.Account;
import com.oept.esales.model.Product;



/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/12/21
 * Description: Categories DAO interface.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾. All rights reserved.
 */
public interface AccountDao {

	/**
	 * ���ݽڵ���ѯ�ò�����Ϣ����
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Account> selectTreeLvl(Object[] params)throws Exception;
	
	/**
	 * ��ѯAccount tree������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int selectTreeMaxLvl()throws Exception;
	
	/**
	 * ��ѯ���и�������
	 * @return
	 * @throws Exception
	 */
	public List<Account> selectAllParentName() throws Exception;
	
	/**
	 * ����id��ѯ��λ�ڵ���Ϣ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Account selectAcDeId(Object[] params) throws Exception;
	
	/**
	 * ��ӵ�λtree�ڵ�
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int addAccountCat(Object[] params) throws Exception;
	
	/**
	 * ���ĵ�λtree�ڵ�
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateAccountCat(Object[] params) throws Exception;
	
	/**
	 * ����idɾ���ڵ�
	 * @param parmas
	 * @return
	 * @throws Exception
	 */
	public int deleteAcNode(Object[] params) throws Exception;
	
	
	/**
	 * ����catId��ѯ���е�λ��Ϣ(�ͻ�)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Account> selectAllAtDe(Account account) throws Exception;
	
	
	/**
	 * ����aId��ѯ��λ��Ϣ����
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Account selectAtDe(Object[] params) throws Exception;
	
	/**
	 * ��ӵ�λ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int addAt(Object[] params) throws Exception;
	
	/**
	 * ���ĵ�λ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int editAt(Object[] params) throws Exception;
	
	/**
	 * ����aId�������Ϣ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int deleteAt(Object[] params) throws Exception;
	
	/**
	 * ���ĵ�λ��Ϣ�����ֶ�cId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int delectAtc(Object[] params) throws Exception;
	
	/**
	 * ��ѯָ�������ĵ�λ��Ϣ
	 * @param type
	 * @param active
	 * @return
	 * @throws Exception
	 */
	public List<Account> getAccount(Account account)throws Exception;
	
	/**
	 * ����catId��ѯ�ýڵ��µ��ӽڵ�
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Account> setAccountCatCnode(Object[] params) throws Exception;
	
	/**
	 * ��ѯ�ýڵ����ӽڵ���Ŀ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int setAccountCatCnodeCount(Object[] params) throws Exception;
	
	/**
	 * ���ĵ�λ
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public boolean updateAccount(Account account) throws Exception;
	
	//��ȡ��λ����
	public int getAccountCount() throws Exception;
	
	//��ȡ��Ҫ��˵ĵ�λ
	public List<Account> getAccountsForApprover(Account account,
			String approver_id, String start, String limit, String sortColumn,
			String sortDir) throws Exception;
	
	/**
	 * ��ѯ��λ�Ƿ��Ѿ�����
	 */
	public boolean queryAccountExist(Account account) throws Exception;
}
