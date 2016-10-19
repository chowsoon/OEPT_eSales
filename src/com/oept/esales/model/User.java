package com.oept.esales.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/11/13
 * Description: Categories data model.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
public class User {
	private String userName; // �û���
	private String password; // ����
	private String userId; // �û�id
	private String system; // ���
	private String firstName; // ����
	private String lastName; // ����
	private boolean active; // ״̬
	private boolean delete; // �Ƿ�ɾ��
	private String lastLogin; // ���һ�ε�¼ʱ��
	private String email; // ����
	private String address; // ��ַ
	private String createdTime; // ����ʱ��
	private String createdId; // ������
	private String updated;		//����ʱ��
	private String updatedBy;	//������id
	private String primaryPositionId;	//��ְλid
	private String primaryAddressId;	//����ַid
	private Position position;  	//ְλ����
	private Address addressObject;		//��ַ����
	
	
	
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Address getAddressObject() {
		return addressObject;
	}
	public void setAddressObject(Address addressObject) {
		this.addressObject = addressObject;
	}
	public String getPrimaryAddressId() {
		return primaryAddressId;
	}
	public void setPrimaryAddressId(String primaryAddressId) {
		this.primaryAddressId = primaryAddressId;
	}
	
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public String getPrimaryPositionId() {
		return primaryPositionId;
	}
	public void setPrimaryPositionId(String primaryPositionId) {
		this.primaryPositionId = primaryPositionId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getCreatedId() {
		return createdId;
	}
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	

}
