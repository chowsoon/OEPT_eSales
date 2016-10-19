package com.oept.esales.service.impl;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oept.esales.dao.OrderDao;
import com.oept.esales.dao.RequisitionDao;
import com.oept.esales.model.OrderFlat;
import com.oept.esales.model.RequisitionFlat;
import com.oept.esales.service.MSOfficeService;
/**
 * @author mwan
 * Version: 1.0
 * Date: 2016/2/16
 * Description: MS Office export/import service implements.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾��Ȩ����.
 */
@SuppressWarnings("deprecation")
@Service("msOfficeService")
public class MSOfficeServiceImpl implements MSOfficeService {

	@Autowired
	private OrderDao orderDao; //order DAO
	@Autowired
	private RequisitionDao requisitionDao; //����ⵥ��DAO
	
	@Override
	public void exportPO(String objectId,ServletOutputStream outputStream) throws Exception {
		//����HSSFWorkbook����(excel���ĵ�����)
		HSSFWorkbook wb = new HSSFWorkbook();
		//�����µ�sheet����excel�ı���
		HSSFSheet sheet=wb.createSheet("PO");
		//��sheet�ﴴ����һ�У�����Ϊ������(excel����)��������0��65535֮����κ�һ��
		HSSFRow row1=sheet.createRow(0);
		//������Ԫ��excel�ĵ�Ԫ�񣬲���Ϊ��������������0��255֮����κ�һ��
		HSSFCell cell=row1.createCell(0);
		//���õ�Ԫ������
		cell.setCellValue("ѧԱ���Գɼ�һ����");
		//�ϲ���Ԫ��CellRangeAddress����������α�ʾ��ʼ�У������У���ʼ�У� ������
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		//��sheet�ﴴ���ڶ���
		HSSFRow row2=sheet.createRow(1);   
		//������Ԫ�����õ�Ԫ������
		row2.createCell(0).setCellValue("����");
		row2.createCell(1).setCellValue("�༶");   
		row2.createCell(2).setCellValue("���Գɼ�");
		row2.createCell(3).setCellValue("���Գɼ�");   
		//��sheet�ﴴ��������
		HSSFRow row3=sheet.createRow(2);
		row3.createCell(0).setCellValue("����");
		row3.createCell(1).setCellValue("As178");
		row3.createCell(2).setCellValue(87);   
		row3.createCell(3).setCellValue(78);

		//���Excel�ļ�    
		wb.write(outputStream);
		wb.close();
		outputStream.flush();
		outputStream.close();
	}

	@Override
	public void exportSO(String objectId,ServletOutputStream outputStream) throws Exception {

	}

	@Override
	public void exportSIO(String objectId,ServletOutputStream outputStream) throws Exception {

	}

	@Override
	public void exportSOO(String objectId,ServletOutputStream outputStream) throws Exception {

	}

	@Override
	public OrderFlat importPO(InputStream inputStream) throws Exception {
		// TODO Auto-generated method stub
		//HSSFWorkbook wb = new HSSFWorkbook(inputStream);
		return null;
	}

	@Override
	public OrderFlat importSO(InputStream inputStream) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequisitionFlat importSIO(InputStream inputStream) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequisitionFlat importSOO(InputStream inputStream) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
