package com.oept.esales.model;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/12/3
 * Description: Categories data model.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
public class Services {
	private String serviceId;	//���񶩵�id
	private String serviceName;	//���񶩵�����
	private String serviceDesc;	//���񶩵�����
	private String serviceType;	//���񶩵�����
	private String serviceStatus;	//���񶩵�״̬
	private String serviceOwner; 	//���񶩵�������id
	private String serviceCreated;	//���񶩵�����ʱ��
	private String serviceCreatedBy;//���񶩵�������id
	private String serviceUpdate;	//���񶩵�����ʱ��
	private String serviceUpdateBy;	//���񶩵�������id
	private String serviceComment;	//���񶩵���ϸ����
	private String serviceSubtype;	//���񶩵�Ͷ��ԭ������
	private String orderId;		//�������
	private String levelVal;	//Ͷ�߼���
	private String levelName;	//Ͷ�߼�������
	
	private OrderFlat order;	//����
	
	private String serviceOwnerName;//���񶩵��������˻���

	
	public OrderFlat getOrder() {
		return order;
	}

	public void setOrder(OrderFlat order) {
		this.order = order;
	}

	public String getServiceSubtype() {
		return serviceSubtype;
	}

	public void setServiceSubtype(String serviceSubtype) {
		this.serviceSubtype = serviceSubtype;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLevelVal() {
		return levelVal;
	}

	public void setLevelVal(String levelVal) {
		this.levelVal = levelVal;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getServiceOwner() {
		return serviceOwner;
	}

	public void setServiceOwner(String serviceOwner) {
		this.serviceOwner = serviceOwner;
	}

	public String getServiceCreated() {
		return serviceCreated;
	}

	public void setServiceCreated(String serviceCreated) {
		this.serviceCreated = serviceCreated;
	}

	public String getServiceCreatedBy() {
		return serviceCreatedBy;
	}

	public void setServiceCreatedBy(String serviceCreatedBy) {
		this.serviceCreatedBy = serviceCreatedBy;
	}

	public String getServiceUpdate() {
		return serviceUpdate;
	}

	public void setServiceUpdate(String serviceUpdate) {
		this.serviceUpdate = serviceUpdate;
	}

	public String getServiceUpdateBy() {
		return serviceUpdateBy;
	}

	public void setServiceUpdateBy(String serviceUpdateBy) {
		this.serviceUpdateBy = serviceUpdateBy;
	}

	
	public String getServiceComment() {
		return serviceComment;
	}

	public void setServiceComment(String serviceComment) {
		this.serviceComment = serviceComment;
	}

	public String getServiceOwnerName() {
		return serviceOwnerName;
	}

	public void setServiceOwnerName(String serviceOwnerName) {
		this.serviceOwnerName = serviceOwnerName;
	}
	
	
}
