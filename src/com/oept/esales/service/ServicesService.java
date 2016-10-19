package com.oept.esales.service;

import java.util.List;

import com.oept.esales.model.OrderFlat;
import com.oept.esales.model.ServiceItem;
import com.oept.esales.model.Services;
import com.oept.esales.model.User;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/12/3
 * Description: Services management operation service interface.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
public interface ServicesService {

	/**
	 * ����userId��ѯ��ǰ�û���ѯ���񶩵�����(�ͻ�)(������)
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectServicesList(Object[] params) throws Exception;
	
	/**
	 * ����userId��ѯ��ǰ�û���ѯ���񶩵�����(�ͷ�)(������)
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectServicesListSys(Object[] params) throws Exception;
	
	/**
	 * ����userId��ѯ��ǰ�û���ѯ���񶩵�����(�ͻ�)(�����)
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectServicesList2(Object[] params) throws Exception;
	
	/**
	 * ����userId��ѯ��ǰ�û���ѯ���񶩵�����(�ͷ�)(�����)
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectServicesListSys2(Object[] params) throws Exception;
	
	/**
	 * ѡ����ѯ������Ա
	 * @return
	 * @throws Exception
	 */
	public User allotConSerUser() throws Exception;
	
	/**
	 * ѡ��Ͷ�߷�����Ա
	 * @return
	 * @throws Exception
	 */
	public User allotCompUser() throws Exception;
	
	/**
	 * �����ѯ���񶩵�
	 * @return
	 * @throws Exception
	 */
	public int addConSerOrder(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��ǰ�û��Ƿ�Ϊ��ѯ������Ա
	 * @return
	 * @throws Exception
	 */
	public int  selectUserPositionConsult(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��ǰ�û��Ƿ�ΪͶ�߷�����Ա
	 * @return
	 * @throws Exception
	 */
	public int  selectUserPositionComplaint(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��ѯ���񶩵������ӵ��б�
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<ServiceItem> selectServiceDetail(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��ѯ���񶩵�����
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Services selectConsultDetail2(Object[] params) throws Exception;
	
	/**
	 * ������ѯ�����Ӷ���
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int createConSerItem(Object[] params) throws Exception;
	
	/**
	 * ������ѯ���񶩵�״̬
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateConSerStatus(Object[] params) throws Exception;
	
	/**
	 * �����ѯ�ظ�
	 * @param params
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int addReplyContent(Object[] params,int id) throws Exception;
	
	/**
	 * ����id��ѯͶ�߷���ļ���
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectComplaintList(Object[] params, int id) throws Exception;
	
	/**
	 * ����id��ѯͶ�߷���ļ��ϣ���ʷ������
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectComplaintListHis(Object[] params, int id) throws Exception;
	
	/**
	 * ��ѯid�����ж������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<OrderFlat> selectUserOrderId(Object[] params) throws Exception;
	
	/**
	 * ��ѯorderId�¶�������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public OrderFlat selectOrderDetail(Object[] params) throws Exception;
	
	/**
	 * ���Ͷ�߶���
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int addComplaintOrder(Object[] params) throws Exception;
	
	/**
	 * ����id��ѯͶ�߷��񶩵���Ϣ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Services selectUserServices(Object[] params) throws Exception;
	
	/**
	 * ����Ͷ���Ӷ���
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int addComplaintOrderItem(Object[] params) throws Exception;
	
	/**
	 * ���Ͷ���Ӷ���
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int addReplyComplaintOrderItem(Object[] params,int id) throws Exception;
	
	/**
	 * ����id��ѯͶ�߷���ļ��ϣ�Ͷ�߼���
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectComplaintListLevel(Object[] params, int id) throws Exception;
	
	/**
	 * ���ݷ���id��ѯ�ö������Ƿ����Ӷ���
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int selectSrvIdCompItem(Object[] params) throws Exception;
	
	/**
	 * ���ҹ��������ٵ��ۺ�ͷ���Ա
	 * @return
	 * @throws Exception
	 */
	public User selectUserIdAss() throws Exception;
	
	/**
	 * �����˻����񶩵�
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int createReturnOrder(Object[] params) throws Exception;
	
	/**
	 * �������޷��񶩵�
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int createRepairOrder(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��ǰUserId�Ƿ�Ϊ�ۺ������Ա
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int selectUserIdAss(Object[] params) throws Exception;
	
	/**
	 * ��ѯ�˻������б���Ա��
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectReturnList(Object[] params) throws Exception;
	
	/**
	 * ��ѯ�˻������б��ͷ���
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectReturnListSys(Object[] params) throws Exception;
	
	/**
	 * ��ѯ���޷����б���Ա��
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectRepairList(Object[] params) throws Exception;
	
	/**
	 * ��ѯ���޷����б��ͷ���
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectRepairListSys(Object[] params) throws Exception;
	
	/**
	 * ��ѯ����������˷��޷����б���Ա��
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectOverReturnAllList(Object[] params) throws Exception;
	
	/**
	 * ��ѯ����������˷��޷����б��ͷ���
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectOverReturnAllListSys(Object[] params) throws Exception;
	
	/**
	 * ��ѯ������˻������б���Ա��
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectOverReturnList(Object[] params) throws Exception;
	
	/**
	 * ��ѯ����ɷ��޷����б���Ա��
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectOverRepairList(Object[] params) throws Exception;

	/**
	 * ��ѯ������˻������б��ͷ���
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectOverReturnListSys(Object[] params) throws Exception;
	
	/**
	 * ��ѯ����ɷ��޷����б��ͷ���
	 * @return
	 * @throws Exception
	 */
	public List<Services> selectOverRepairListSys(Object[] params) throws Exception;
	
	/**
	 * ��ѯ���񶩵�����
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Services selectServDetail(Object[] params) throws Exception;
	
	/**
	 * �����˷�������״̬
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateRtSerStatus(Object[] params) throws Exception;
	
	/**
	 * �����˷�������״̬
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateRtSerStatus2(Object[] params) throws Exception;
}
