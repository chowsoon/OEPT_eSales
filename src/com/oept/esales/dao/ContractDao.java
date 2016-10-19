package com.oept.esales.dao;

import java.util.List;

import com.oept.esales.model.Contract;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2016/1/11
 * Description: Contract administration DAO interface.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾. All rights reserved.
 */
public interface ContractDao {

	/**
	 * ��Ӻ�ͬ��Ϣ
	 * @param contract
	 * @return
	 * @throws Exception
	 */
	public boolean addContract(Contract contract) throws Exception;
	
	/**
	 * ��ѯ���к�ͬ����
	 * @return
	 * @throws Exception
	 */
	public List<Contract> getAllContractList() throws Exception;
	
	/**
	 * ��ѯ��ͬ��ϸ��Ϣ
	 * @param id ��ͬid
	 * @return
	 * @throws Exception
	 */
	public Contract getContractById(String id) throws Exception;
	
	/**
	 * ���º�ͬ��Ϣ
	 * @param contract
	 * @return
	 * @throws Exception
	 */
	public boolean updateContract(Contract contract) throws Exception;
	/**
	 * ���º�ͬ״̬��Ϣ
	 * @param contract
	 * @return
	 * @throws Exception
	 */
	public boolean updateContractStatus(Contract contract) throws Exception;
	
	/**
	 * ɾ����ͬ
	 * @param id ��ͬid
	 * @return
	 * @throws Exception
	 */
	public boolean delContractById(String id) throws Exception;
	/**
	 * Query specified number of contracts
	 * @param contract Contractn's object for filter parameters
	 * @param start line number of start
	 * @param limit number of data
	 * @param sortColumn column number to sort
	 * @param sortDir sort direction, asc or desc
	 * 
	 * @return return list object of queried contracts data
	 * @throws Exception 
	 */
	public List<Contract> getContracts(Contract contract,String start,String limit,String sortColumn, String sortDir) throws Exception;
}
