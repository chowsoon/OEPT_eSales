package com.oept.esales.service;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;

import com.oept.esales.model.OrderFlat;
import com.oept.esales.model.RequisitionFlat;

/**
 * @author mwan
 * Version: 1.0
 * Date: 2016/2/16
 * Description: MS Office export/import service interface.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
public interface MSOfficeService {

	/**
	 * @Method: exportPO
	 * @Description: �����ɹ�����excel��
	 * @Author:mwan
	 *
	 * @param objectId
	 * @throws Exception
	 */ 
	public void exportPO(String objectId, ServletOutputStream outputStream) throws Exception;
	/**
	 * @Method: exportSO
	 * @Description: �������۶���excel��
	 * @Author:mwan
	 *
	 * @param objectId
	 * @throws Exception
	 */ 
	public void exportSO(String objectId, ServletOutputStream outputStream) throws Exception;
	/**
	 * @Method: exportSIO
	 * @Description: ������ⵥexcel��
	 * @Author:mwan
	 *
	 * @param objectId
	 * @throws Exception
	 */ 
	public void exportSIO(String objectId, ServletOutputStream outputStream) throws Exception;
	/**
	 * @Method: exportSOO
	 * @Description: �������ⵥexcel��
	 * @Author:mwan
	 *
	 * @param objectId
	 * @throws Exception
	 */ 
	public void exportSOO(String objectId, ServletOutputStream outputStream) throws Exception;
	/**
	 * @Method: importPO
	 * @Description: ����ɹ�����excel��
	 * @Author:mwan
	 *
	 * @param inputStream
	 * @throws Exception
	 */ 
	public OrderFlat importPO(InputStream inputStream) throws Exception;
	/**
	 * @Method: importSO
	 * @Description: �������۶���excel��
	 * @Author:mwan
	 *
	 * @param inputStream
	 * @throws Exception
	 */ 
	public OrderFlat importSO(InputStream inputStream) throws Exception;
	/**
	 * @Method: importSIO
	 * @Description: ������ⵥexcel��
	 * @Author:mwan
	 *
	 * @param inputStream
	 * @throws Exception
	 */ 
	public RequisitionFlat importSIO(InputStream inputStream) throws Exception;
	/**
	 * @Method: importSOO
	 * @Description: ������ⵥexcel��
	 * @Author:mwan
	 *
	 * @param inputStream
	 * @throws Exception
	 */ 
	public RequisitionFlat importSOO(InputStream inputStream) throws Exception;
}
