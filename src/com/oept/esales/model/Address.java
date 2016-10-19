package com.oept.esales.model;

import java.util.List;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/11/13
 * Description: Categories data model.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
public class Address {

	private String addressId;		//��ַid
	private String allAddress;		//��ַȫ��
	private String province;		//ʡ
	private String city;			//��
	private String country;			//����
	private String county;			//������
	private String zipcode;			//��������
	private String contactName;		//��ϵ��
	private String contactCell;		//��ϵ�绰
	private String street;			//��ϸ��ַ���ֵ�)
	private String created;			//����ʱ��
	private String update;		//����ʱ��
	private String createdBy;		//������
	private String updateBy;	//������

	private List<User> user;		//�û�����
	
	
	
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public List<User> getUser() {
		return user;
	}
	public void setUser(List<User> user) {
		this.user = user;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getAllAddress() {
		return allAddress;
	}
	public void setAllAddress(String allAddress) {
		this.allAddress = allAddress;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactCell() {
		return contactCell;
	}
	public void setContactCell(String contactCell) {
		this.contactCell = contactCell;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	
}
