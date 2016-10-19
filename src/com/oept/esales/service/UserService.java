package com.oept.esales.service;

import java.util.List;
import java.util.Map;

import com.oept.esales.model.Address;
import com.oept.esales.model.Category;
import com.oept.esales.model.Position;
import com.oept.esales.model.User;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/11/16
 * Description: User management operation service interface.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
public interface UserService {

	/**
	 * �û�����ע�Ṧ��ҵ��ӿ�
	 * @param requestParam
	 * @return
	 */
	public int signin(User user) throws Exception;
	
	/**
	 * ע����֤�û����Ƿ��Ѵ���
	 * @param username
	 * @return
	 */
	public Integer testingUser(String username);
	
	/**
	 * ��ѯ�û��б�
	 * @return
	 */
	public List<User> selectUserList() throws Exception;
	
	
	/**
	 * ��ѯ�����û���Ϣ
	 * @param userId
	 * @return
	 */
	public User selectUserDetail(String userId) throws Exception;
	
	/**
	 * �����û���Ϣ������������
	 * @param params
	 * @return
	 */
	public int updateUserDetailAndPwd(Object[] params);
	
	/**
	 * �����û���Ϣ��������������
	 * @param params
	 * @return
	 */
	public int updateUserDetailNoPwd(Object[] params);
	
	/**
	 * ����idɾ��user
	 * @param userId
	 * @return
	 */
	public Integer deleteUser(String userId);
	
	/**
	 * �������û�
	 * @param params
	 * @return
	 */
	public int newUser(Object[] params);
	
	/**
	 * �����ջ���ַ
	 * @param params
	 * @return
	 */
	public int newAddress(Address address);
	
	/**
	 * ɾ����ַ
	 * @param addressId
	 * @return
	 */
	public int deleteAddress(String addressId);
	
	/**
	 * �����ջ���ַ
	 * @param params
	 * @return
	 */
	public int updateAddress(Object[] params);
	
	/**
	 * ����id��ѯ��ַ��Ϣ
	 * @param userId
	 * @return
	 */
	public Address selectAddressDetail(String addressId);
	/**
	 * ����id��ѯ��ַ��Ϣ
	 * @param userId
	 * @return
	 */
	public Map<String,Object> selectAddressDetail2(String addressId);
	
	/**
	 * ��ѯ�����û���
	 * @return
	 */
	public String[] selectAllUserName();
	
	/**
	 * �����ַ
	 * @param params
	 * @return
	 */
	public int allotAddress(Object[] params);
	
	/**
	 * ��ѯ��ַ�б�
	 * @return
	 */
	public List<Address> selectAddressLists() throws Exception;
	
	/**
	 * ���ݵ�ַid��ѯ�����û�
	 * @return
	 * @throws Exception
	 */
	public List<User> addrIdSelectUsername(String aId) throws Exception;
	
	/**
	 * ɾ���û��ջ���ַ
	 * @param address
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int deleteUserAddress(Address address,User user) throws Exception;
	
	/**
	 * ����id��ѯ��ǰ�û������е��ջ���ַ
	 * @param userId
	 * @return
	 */
	public List<Address> personalAddressList(String userId) throws Exception;
	
	/**
	 * �����û���ַ�м������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int createUserAddress(Object[] params) throws Exception;
	
	/**
	 * �����û�ɾ�������ջ���ַ������ǰҪɾ����ַû��������ʹ�þ�ɾ����
	 * @param address
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int personalDeleteAddress(Address address, User user) throws Exception;
	
	/**
	 * ����id��ȡ�û�Ĭ�ϵ�ַ
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User userDefaultAddress(String userId) throws Exception;
	
	/**
	 * ����Ĭ�ϵ�ַ
	 * @param userId
	 * @param addressId
	 * @return
	 * @throws Exception
	 */
	public int defaultAddress(String userId,String addressId) throws Exception;
	
	/**
	 * ��ȡ���루��֤��
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String verificationPassword(Object[] params) throws Exception;
	
	/**
	 * �޸�����
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updatePassword(Object[] params) throws Exception;
	
	/**
	 * ��ѯְλ����
	 * @return
	 * @throws Exception
	 */
	public List<Position> selectPositions() throws Exception;
	
	/**
	 * �����û���ְλ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateUserPrimaryPosition(Object[] params) throws Exception;
	
	/**
	 * ����ְλ����
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> personalPositionList(Object[] params) throws Exception;
	
	/**
	 * �����û�ְλ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateUserPosition(Object[] params) throws Exception;
	
	/**
	 * ���ְλ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int newUserPosition(Object[] params) throws Exception;
	
	/**
	 * ɾ���û�ְλ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int deleteUserPosition(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��ǰ�û�ְλ�Ƿ����
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int selectUserThisPosition(Object[] params) throws Exception;
	
	/**
	 * ������ְλ
	 * @param position
	 * @return
	 * @throws Exception
	 */
	public int createPosition(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��ְλ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> thisPosition(Object[] params) throws Exception;
	
	/**
	 * ��ѯ��ְλ,����Position����
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Position getPosition(Object[] params) throws Exception;
	
	/**
	 * �޸ĸ�ְλ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updatePosition(Object[] params) throws Exception;
	
	/**
	 * ɾ����ְλ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int deletePosition(Object[] params) throws Exception;
	
	/**
	 * ������ѯ�û�����
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserLists(User user) throws Exception;

}
