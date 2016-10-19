package com.oept.esales.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oept.esales.action.UserAct;
import com.oept.esales.dao.ServicesDao;
import com.oept.esales.model.OrderFlat;
import com.oept.esales.model.ServiceItem;
import com.oept.esales.model.Services;
import com.oept.esales.model.User;
import com.oept.esales.service.ServicesService;


/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/11/16
 * Description: User management operation service implements.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
@Service("servicesService")
public class ServicesServiceImpl implements ServicesService{
	private static final Log logger = LogFactory.getLog(UserAct.class);

	@Autowired
	private ServicesDao servicesDao;

	/**
	 * ����userId��ѯ��ǰ�û����񶩵�����(�ͻ�)(������)
	 */
	@Override
	public List<Services> selectServicesList(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectServicesList(params);
	}
	
	/**
	 * ����userId��ѯ��ǰ�û����񶩵�����(�ͷ�)(������)
	 */
	@Override
	public List<Services> selectServicesListSys(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectServicesListSys(params);
	}
	
	/**
	 * ����userId��ѯ��ǰ�û����񶩵�����(�ͻ�)(�����)
	 */
	@Override
	public List<Services> selectServicesList2(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectServicesList2(params);
	}
	
	/**
	 * ����userId��ѯ��ǰ�û����񶩵�����(�ͷ�)(�����)
	 */
	@Override
	public List<Services> selectServicesListSys2(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectServicesListSys2(params);
	}
	
	/**
	 * ѡ����ѯ������Ա
	 */
	@Override
	public User allotConSerUser() throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectConSerUser();
	}
	
	/**
	 * ѡ��Ͷ�߷�����Ա
	 */
	@Override
	public User allotCompUser() throws Exception {
		return servicesDao.selectCompUser();
	}

	/**
	 * �����ѯ���񶩵�
	 */
	@Override
	public int addConSerOrder(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.addConSerOrder(params);
	}

	/**
	 * ��ѯ��ǰ�û��Ƿ�Ϊ��ѯ������Ա
	 */
	@Override
	public int selectUserPositionConsult(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectUserPositionConsult(params);
	}
	
	/**
	 * ��ѯ��ǰ�û��Ƿ�ΪͶ�߷�����Ա
	 */
	@Override
	public int selectUserPositionComplaint(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectUserPositionComplaint(params);
	}

	/**
	 * ��ѯ��ѯ���񶩵������ӵ��б�
	 */
	@Override
	public List<ServiceItem> selectServiceDetail(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectServiceDetail(params);
	}

	/**
	 * ������ѯ�����Ӷ���
	 */
	@Override
	public int createConSerItem(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.createConSerItem(params);
	}

	/**
	 * ������ѯ���񶩵�״̬
	 */
	@Override
	public int updateConSerStatus(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.updateConSerStatus(params);
	}

	/**
	 * ��ѯ��ѯ���񶩵�����
	 */
	@Override
	public Services selectConsultDetail2(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectConsultDetail2(params);
	}

	/**
	 * �����ѯ�ظ�
	 */
	@Override
	public int addReplyContent(Object[] params, int id) throws Exception {
		// TODO Auto-generated method stub
		if(id == 0){
			//useridΪ�˿�
			return servicesDao.createConSerItem2(params);
		}else if(id == 1){
			//useridΪ�ͷ�
			return servicesDao.createConSerItem(params);
		}
		return 2;
	}

	/**
	 * ����id��ѯͶ�߷���ļ���
	 */
	@Override
	public List<Services> selectComplaintList(Object[] params, int id) throws Exception {
		// TODO Auto-generated method stub
		if(id == 0){
			return servicesDao.selectComplaintList(params);
		}else if(id == 1){
			return servicesDao.selectComplaintList2(params);
		}else{
			return null;
		}
		
	}
	
	/**
	 * ����id��ѯͶ�߷���ļ���
	 */
	@Override
	public List<Services> selectComplaintListHis(Object[] params, int id) throws Exception {
		// TODO Auto-generated method stub
		if(id == 0){
			return servicesDao.selectComplaintListHis(params);
		}else if(id == 1){
			return servicesDao.selectComplaintListHis2(params);
		}else{
			return null;
		}
		
	}

	/**
	 * ��ѯid�����ж������
	 */
	@Override
	public List<OrderFlat> selectUserOrderId(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectUserOrderId(params);
	}

	/**
	 * ���Ͷ�߶���
	 */
	@Override
	public int addComplaintOrder(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.addComplaintOrder(params);
	}

	/**
	 * ����Ͷ���Ӷ���
	 */
	@Override
	public int addComplaintOrderItem(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.addComplaintOrderItem(params);
	}

	/**
	 * ����id��ѯͶ�߷��񶩵���Ϣ
	 */
	@Override
	public Services selectUserServices(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectUserServices(params);
	}

	/**
	 * ���Ͷ���Ӷ���
	 */
	@Override
	public int addReplyComplaintOrderItem(Object[] params,int id) throws Exception {
		// TODO Auto-generated method stub
		if(id == 1){
			return servicesDao.addReplyComplaintOrderItem2(params);
		}else{
			return servicesDao.addReplyComplaintOrderItem(params);
		}
	}

	/**
	 * ����id��ѯͶ�߷���ļ���
	 */
	@Override
	public List<Services> selectComplaintListLevel(Object[] params, int id) throws Exception {
		// TODO Auto-generated method stub
		if(id == 0){
			return servicesDao.selectComplaintListLevel(params);
		}else if(id == 1){
			return servicesDao.selectComplaintListLevel2(params);
		}else{
			return null;
		}
		
	}
	
	/**
	 * ���ݷ���id��ѯ�ö������Ƿ����Ӷ���
	 */
	@Override
	public int selectSrvIdCompItem(Object[] params) throws Exception {
		return servicesDao.selectSrvIdCompItem(params);
	}

	/**
	 * ��ѯorderId�¶�������
	 */
	@Override
	public OrderFlat selectOrderDetail(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectOrderDetail(params);
	}

	/**
	 * ���ҹ��������ٵ��ۺ�ͷ���Ա
	 */
	@Override
	public User selectUserIdAss() throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectUserIdAss();
	}

	/**
	 * �����˻����񶩵�
	 */
	@Override
	public int createReturnOrder(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.createReturnOrder(params);
	}

	/**
	 * �������޷��񶩵�
	 */
	@Override
	public int createRepairOrder(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.createRepairOrder(params);
	}

	/**
	 * ���Ͷ���Ӷ�������Ա��
	 */
	@Override
	public List<Services> selectReturnList(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectReturnList(params);
	}

	/**
	 * ���Ͷ���Ӷ������ͷ���
	 */
	@Override
	public List<Services> selectReturnListSys(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectReturnListSys(params);
	}

	/**
	 * ����id��ѯͶ�߷���ļ��ϣ�Ͷ�߼��𣩣���Ա��
	 */
	@Override
	public List<Services> selectRepairList(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectRepairList(params);
	}

	/**
	 * ����id��ѯͶ�߷���ļ��ϣ�Ͷ�߼��𣩣��ͷ���
	 */
	@Override
	public List<Services> selectRepairListSys(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectRepairListSys(params);
	}

	/**
	 * ���ݷ���id��ѯ�ö������Ƿ����Ӷ���
	 */
	@Override
	public List<Services> selectOverReturnAllList(Object[] params)
			throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectOverReturnAllList(params);
	}

	/**
	 * ��ѯorderId�¶�������
	 */
	@Override
	public List<Services> selectOverReturnAllListSys(Object[] params)
			throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectOverReturnAllListSys(params);
	}

	/**
	 * �����˻����񶩵�
	 */
	@Override
	public List<Services> selectOverReturnList(Object[] params)
			throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectOverReturnList(params);
	}

	/**
	 * �������޷��񶩵�
	 */
	@Override
	public List<Services> selectOverRepairList(Object[] params)
			throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectOverRepairList(params);
	}

	/**
	 * �������޷��񶩵�
	 */
	@Override
	public List<Services> selectOverReturnListSys(Object[] params)
			throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectOverReturnListSys(params);
	}

	/**
	 * ��ѯ�˻������б���Ա��
	 */
	@Override
	public List<Services> selectOverRepairListSys(Object[] params)
			throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectOverRepairListSys(params);
	}

	/**
	 *  ��ѯ��ǰUserId�Ƿ�Ϊ�ۺ������Ա
	 */
	@Override
	public int selectUserIdAss(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectUserIdAss(params);
	}

	/**
	 * ��ѯ���񶩵�����
	 */
	@Override
	public Services selectServDetail(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.selectServDetail(params);
	}

	/**
	 * �����˷�������״̬
	 */
	@Override
	public int updateRtSerStatus(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.updateRtSerStatus(params);
	}
	
	/**
	 * �����˷�������״̬
	 */
	@Override
	public int updateRtSerStatus2(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return servicesDao.updateRtSerStatus2(params);
	}

}
