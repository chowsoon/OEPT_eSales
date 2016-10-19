package com.oept.esales.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oept.esales.dao.UserDao;
import com.oept.esales.model.Address;
import com.oept.esales.model.Position;
import com.oept.esales.model.User;
import com.oept.esales.service.UserService;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/11/16
 * Description: User management operation service implements.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
@Service("userService")
public class UserServiceImpl implements UserService{


	@Autowired
	private UserDao userDao;
	/**
	 * �û�����ע�Ṧ��ʵ��ҵ��ӿ�
	 */
	@Override
	public int signin(User user) throws Exception {
		// TODO Auto-generated method stub
		//����userDao signin���������ؽ��
		return userDao.signin(user);
	}
	
	/**
	 * ע����֤�û����Ƿ��Ѵ���
	 */
	@Override
	public Integer testingUser(String username) {
		// TODO Auto-generated method stub
		//����userDao testingUser���������ؽ��
		return userDao.testingUser(username);
	}

	/**
	 * ��ѯ�û��б�
	 */
	@Override
	public List<User> selectUserList() throws Exception {
		// TODO Auto-generated method stub
		//����userDao selectUserList���������ؽ��
		return userDao.selectUserList();
	}
	
	/**
	 * ��ѯ�����û���Ϣ
	 */
	@Override
	public User selectUserDetail(String userId) throws Exception{
		// TODO Auto-generated method stub
		//����userDao ��ѯ�û���ϸ��Ϣ���������ؽ��
		return userDao.selectUserDetail(userId);
	}

	/**
	 * �����û���Ϣ������������
	 */
	@Override
	public int updateUserDetailAndPwd(Object[] params) {
		// TODO Auto-generated method stub
		//����userDao �����û���Ϣ�����������룩�����ؽ��
		return userDao.updateUserDetailAndPwd(params);
	}

	/**
	 * �����û���Ϣ��������������
	 */
	@Override
	public int updateUserDetailNoPwd(Object[] params){
		// TODO Auto-generated method stub
		//����userDao �����û���Ϣ�����������룩�����ؽ��
		return userDao.updateUserDetailNoPwd(params);
	}

	/**
	 * ����idɾ��user
	 */
	@Override
	public Integer deleteUser(String userId) {
		// TODO Auto-generated method stub
		//����userDao ɾ���û����������ؽ��
		return userDao.deleteUser(userId);
	}

	/**
	 * �������û�
	 */
	@Override
	public int newUser(Object[] params) {
		// TODO Auto-generated method stub
		//����userDao �������û����������ؽ��
		return userDao.newUser(params);
	}

	/**
	 * �����ջ���ַ
	 */
	@Override
	public int newAddress(Address address) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			//��ѯ�½������Ƿ��ظ�
			boolean res = userDao.queryAddressExist(address);
			if(res){
				result = 2;
			}else{
				result = userDao.newAddress(address);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//����userDao �����ջ���ַ���������ؽ��
		return result;
	}

	/**
	 * ɾ����ַ
	 */
	@Override
	public int deleteAddress(String addressId) {
		// TODO Auto-generated method stub
		//����userDao ɾ�����������ؽ��
		return userDao.deleteAddress(addressId);
		
	}

	/**
	 * �����ջ���ַ
	 */
	@Override
	public int updateAddress(Object[] params) {
		// TODO Auto-generated method stub
		return userDao.updateAddress(params);
	}

	/**
	 * ����id��ѯ��ַ��Ϣ
	 */
	@Override
	public Address selectAddressDetail(String addressId) {
		// TODO Auto-generated method stub
		return userDao.selectAddressDetail(addressId);
	}

	/**
	 * ����id��ѯ��ַ��Ϣ
	 */
	@Override
	public Map<String,Object> selectAddressDetail2(String addressId) {
		// TODO Auto-generated method stub
		return userDao.selectAddressDetail2(addressId);
	}
	
	/**
	 * ��ѯ�����û���
	 */
	@Override
	public String[] selectAllUserName() {
		// TODO Auto-generated method stub
		List<Map<String,Object>> users = userDao.selectAllUserName();
		String[] userNames = new String[users.size()];
		for(int i=0;i<users.size();i++){
			userNames[i] = String.valueOf(users.get(i).get("osa_username"));
		}
		return userNames;
	}

	/**
	 * �����ַ
	 */
	@Override
	public int allotAddress(Object[] params) {
		// TODO Auto-generated method stub
		return userDao.allotAddress(params);
	}

	/**
	 * ��ѯ��ַ�б�
	 */
	@Override
	public List<Address> selectAddressLists() throws Exception {
		// TODO Auto-generated method stub
		List<Address> addressList = userDao.selectAddressLists();
		for(int i=0;i<addressList.size();i++){
			String aId = addressList.get(i).getAddressId();
			addressList.get(i).setUser(userDao.addrIdSelectUsername(aId));
		}
		return addressList;
	}

	/**
	 * ���ݵ�ַid��ѯ�����û�
	 */
	@Override
	public List<User> addrIdSelectUsername(String aId) throws Exception {
		// TODO Auto-generated method stub
		return userDao.addrIdSelectUsername(aId);
	}

	/**
	 * ɾ���û��ջ���ַ
	 */
	@Override
	public int deleteUserAddress(Address address, User user) throws Exception {
		// TODO Auto-generated method stub
		return userDao.deleteUserAddress(address, user);
	}
	
	/**
	 * ����id��ѯ��ǰ�û������е��ջ���ַ
	 */
	@Override
	public List<Address> personalAddressList(String userId) throws Exception{
		// TODO Auto-generated method stub
		return userDao.personalAddressList(userId);
	}

	/**
	 * �����û���ַ�м������
	 */
	@Override
	public int createUserAddress(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return userDao.createUesrAddress(params);
	}

	/**
	 * �����û�ɾ�������ջ���ַ������ǰҪɾ����ַû��������ʹ�þ�ɾ����
	 */
	@Override
	public int personalDeleteAddress(Address address, User user)
			throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		//���ݵ�ַ�û�idɾ���м������
		int res = userDao.deleteUserAddress(address, user);
		//������سɹ�
		if(res == 1){
			//���ݵ�ַid��ѯ�м�����и�id��Ϣ
			int count = userDao.selectCountAddressUserId(address);
			//�������ֵΪ0,����idû�������û�ʹ�ã�
			if(count == 0){
				//���ݵ�ַidɾ����ַ������
				result = userDao.deleteAddressPersonal(address);
			}
		}
		//���ؽ��
		return result;
	}

	/**
	 * ����id��ȡ�û�Ĭ�ϵ�ַ
	 */
	@Override
	public User userDefaultAddress(String userId)
			throws Exception {
		// TODO Auto-generated method stub
		return userDao.userDefaultAddress(userId);
	}

	/**
	 * ����Ĭ�ϵ�ַ
	 */
	@Override
	public int defaultAddress(String userId, String addressId) throws Exception {
		// TODO Auto-generated method stub
		return userDao.defaultAddress(userId, addressId);
	}

	/**
	 * ��ȡ���루��֤��
	 */
	@Override
	public String verificationPassword(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		User user = userDao.verificationPassword(params);
		String password = user.getPassword();
		return password;
	}

	/**
	 * �޸�����
	 */
	@Override
	public int updatePassword(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return userDao.updatePersonalPassword(params);
	}

	/**
	 * ��ѯְλ����
	 */
	@Override
	public List<Position> selectPositions() throws Exception {
		// TODO Auto-generated method stub
		return userDao.selectPositions();
	}

	/**
	 * �����û���ְλ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateUserPrimaryPosition(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return userDao.updateUserPrimaryPosition(params);
	}

	/**
	 * ����ְλ����
	 */
	@Override
	public List<Map<String, Object>> personalPositionList(Object[] params)
			throws Exception {
		// TODO Auto-generated method stub
		return userDao.personalPositionList(params);
	}

	/**
	 * �����û�ְλ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateUserPosition(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return userDao.updateUserPosition(params);
	}

	/**
	 * ���ְλ
	 */
	@Override
	public int newUserPosition(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return userDao.newUserPosition(params);
	}

	/**
	 * ɾ���û�ְλ
	 */
	@Override
	public int deleteUserPosition(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return userDao.deleteUserPosition(params);
	}

	/**
	 *  ��ѯ��ǰ�û�ְλ�Ƿ����
	 */
	@Override
	public int selectUserThisPosition(Object[] params)
			throws Exception {
		// TODO Auto-generated method stub
		return userDao.selectUserThisPosition(params);
	}

	/**
	 * ������ְλ
	 */
	@Override
	public int createPosition(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return userDao.createPosition(params);
	}

	/**
	 * ��ѯ��ְλ
	 */
	@Override
	public Map<String,Object> thisPosition(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return userDao.thisPosition(params);
	}

	/**
	 * ��ѯ��ְλ,����Position����
	 */
	@Override
	public Position getPosition(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return userDao.getPosition(params);
	}
	
	/**
	 * �޸ĸ�ְλ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updatePosition(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return userDao.updatePosition(params);
	}

	/**
	 * ɾ����ְλ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deletePosition(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return userDao.deletePosition(params);
	}

	/**
	 * ������ѯ�û�����
	 */
	@Override
	public List<User> getUserLists(User user) throws Exception {
		// TODO Auto-generated method stub
		return userDao.getUserLists(user);
	}

	
}
