package com.oept.esales.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oept.esales.dao.ApprovalHistoryDao;
import com.oept.esales.dao.FileDao;
import com.oept.esales.dao.ListOfValueDao;
import com.oept.esales.dao.OrderDao;
import com.oept.esales.dao.ProductDao;
import com.oept.esales.dao.RequisitionDao;
import com.oept.esales.dao.RequisitionHistoryDao;
import com.oept.esales.dao.StockHistoryDao;
import com.oept.esales.dao.UserDao;
import com.oept.esales.dao.WarehouseDao;
import com.oept.esales.dao.WarehouseStockDao;
import com.oept.esales.model.ApprovalHistory;
import com.oept.esales.model.Attachment;
import com.oept.esales.model.OrderReqProdCounts;
import com.oept.esales.model.Product;
import com.oept.esales.model.RequisitionFlat;
import com.oept.esales.model.RequisitionHistory;
import com.oept.esales.model.StockHistory;
import com.oept.esales.model.User;
import com.oept.esales.model.WarehouseFlat;
import com.oept.esales.model.WarehouseStock;
import com.oept.esales.service.AuthService;
import com.oept.esales.service.InventoryService;
/**
 * @author mwan
 * Version: 3.0
 * Date: 2016/1/11
 * Description: Inventory business service implementor.
 * Copyright (c) 2015 OEPT. All rights reserved.
 */
@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private WarehouseDao warehouseDao; //�ֿ�DAO
	@Autowired
	private WarehouseStockDao warehouseStockDao; //�ֿ�����ϢDAO
	@Autowired
	private ProductDao productDao; //��Ʒ��ϢDAO
	@Autowired
	private StockHistoryDao stockHistoryDao; //�����ʷ��ϢDAO
	@Autowired
	private RequisitionDao requisitionDao; //����ⵥ��DAO
	@Autowired
	private OrderDao orderDao; //����DAO
	@Autowired
	private RequisitionHistoryDao requisitionHistoryDao; //����ⵥ����ʷ��ϢDAO
	@Autowired
	private ApprovalHistoryDao approvalHistoryDao; //approval history info DAO
	@Autowired
	private UserDao userDao; //user DAO
	@Autowired
	private ListOfValueDao listOfValueDao; //list of values info DAO
	@Autowired
	private FileDao fileDao; //����DAO
	
	@Override
	public boolean addWarehouse(WarehouseFlat warehouse) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		warehouse.setCreated_date(nowdate);
		warehouse.setUpdated_date(nowdate);
		warehouse.setActive(true);
		
		return warehouseDao.addWarehouse(warehouse);
	}

	@Override
	public boolean updateWarehouse(WarehouseFlat warehouse) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		warehouse.setUpdated_date(nowdate);
		
		return warehouseDao.updateWarehouse(warehouse);
	}

	@Override
	public boolean delWarehouseById(String id) throws Exception {
		// TODO Auto-generated method stub
		return warehouseDao.delWarehouseById(id);
	}

	@Override
	public WarehouseFlat getWarehouseById(String id) throws Exception {
		// TODO Auto-generated method stub
		return warehouseDao.getWarehouseById(id);
	}

	@Override
	public List<WarehouseFlat> getAllWarehouses() throws Exception {
		// TODO Auto-generated method stub
		return warehouseDao.getAllWarehouses();
	}

	@Override
	public List<WarehouseFlat> getItemsByWarehouseId(String warehouseId)
			throws Exception {
		// TODO Auto-generated method stub
		return warehouseDao.getItemsByWarehouseId(warehouseId);
	}

	@Override
	public List<WarehouseFlat> getWarehouses(WarehouseFlat warehouse, String start,
			String limit, String sortColumn, String sortDir) throws Exception {
		// TODO Auto-generated method stub
		return warehouseDao.getWarehouses(warehouse, start, limit, sortColumn, sortDir);
	}

	@Override
	public boolean addStockInfo(WarehouseStock warehouse_stock)
			throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		warehouse_stock.setCreated_date(nowdate);
		warehouse_stock.setUpdated_date(nowdate);
		
		Product product = productDao.getProductById(warehouse_stock.getProduct_id());
		int original_stock = product.getStock();
		product.setStock(original_stock + warehouse_stock.getStock());
		product.setSku(warehouse_stock.getSku());
		product.setUpdate(nowdate);
		product.setUpdateById(warehouse_stock.getUpdated_by_user_id());
		productDao.updateProduct(product);
		
		return warehouseStockDao.addStockInfo(warehouse_stock);
	}

	@Override
	public boolean updateStockInfoById(WarehouseStock warehouse_stock)
			throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		warehouse_stock.setUpdated_date(nowdate);
		
		WarehouseStock original_stock_info = warehouseStockDao.getStockInfoById(warehouse_stock.getId());
		
		int delta_stock = warehouse_stock.getStock() - original_stock_info.getStock();
		Product product = productDao.getProductById(original_stock_info.getProduct_id());
		int original_product_stock = product.getStock();
		product.setStock(original_product_stock + delta_stock);
		product.setSku(warehouse_stock.getSku());
		product.setUpdate(nowdate);
		product.setUpdateById(warehouse_stock.getUpdated_by_user_id());
		productDao.updateProduct(product);
		
		return warehouseStockDao.updateStockInfoById(warehouse_stock);
	}

	@Override
	public List<WarehouseStock> getAllStockInfo() throws Exception {
		// TODO Auto-generated method stub
		return warehouseStockDao.getAllStockInfo();
	}

	@Override
	public WarehouseStock getStockInfoById(String id) throws Exception {
		// TODO Auto-generated method stub
		return warehouseStockDao.getStockInfoById(id);
	}

	@Override
	public List<WarehouseStock> getStockInfos(WarehouseStock warehouse_stock,
			String start, String limit, String sortColumn, String sortDir)
			throws Exception {
		// TODO Auto-generated method stub
		return warehouseStockDao.getStockInfos(warehouse_stock, start, limit, sortColumn, sortDir);
	}

	@Override
	public boolean delStockInfoById(String id, String user_id) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		
		WarehouseStock original_stock_info = warehouseStockDao.getStockInfoById(id);
		Product product = productDao.getProductById(original_stock_info.getProduct_id());
		int original_stock = product.getStock();
		product.setStock(original_stock - original_stock_info.getStock());
		product.setUpdate(nowdate);
		product.setUpdateById(user_id);
		productDao.updateProduct(product);
		return warehouseStockDao.delStockInfoById(id);
	}

	@Override
	public boolean addHistory(StockHistory stock_history) throws Exception {
		// TODO Auto-generated method stub
		return stockHistoryDao.addHistory(stock_history);
	}

	@Override
	public boolean delHistoryById(String id) throws Exception {
		// TODO Auto-generated method stub
		return stockHistoryDao.delHistoryById(id);
	}

	@Override
	public List<StockHistory> getAllHistories() throws Exception {
		// TODO Auto-generated method stub
		return stockHistoryDao.getAllHistories();
	}

	@Override
	public StockHistory getHistoryById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockHistory> getHistories(StockHistory stock_history,
			String start, String limit, String sortColumn, String sortDir)
			throws Exception {
		// TODO Auto-generated method stub
		return stockHistoryDao.getHistories(stock_history, start, limit, sortColumn, sortDir);
	}

	@Override
	public String addRequisition(RequisitionFlat requisition) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		requisition.setCreated_date(nowdate);
		requisition.setUpdate_date(nowdate);
		return requisitionDao.addRequisition(requisition);
	}

	@Override
	@Transactional
	public boolean updateRequisition(RequisitionFlat requisition)
			throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		requisition.setUpdate_date(nowdate);
		
		//Insert history record
		if(requisition.getHistory_desc()!=null && !"".equals(requisition.getHistory_desc())){
			RequisitionHistory req_history = new RequisitionHistory();
	        req_history.setRequisition_id(requisition.getRequisition_id());
	        req_history.setCreated_by_user_id(requisition.getUpdate_by_id());
	        req_history.setDescription("��"+requisition.getUpdate_by_name()+requisition.getHistory_desc());
	        this.addReqHistory(req_history);
		}
        
		return requisitionDao.updateRequisition(requisition);
	}

	@Override
	@Transactional
	public String updateRequisitionStatus(RequisitionFlat requisition)
			throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		requisition.setUpdate_date(nowdate);
		List<RequisitionFlat> reqItemList = requisitionDao.getItemsByReqId(requisition.getRequisition_id());
		Map<String,Object> upsertResponse = new HashMap<String,Object>();
		
		if(requisition.getStatus_code().equals("completed")){//��ʼ��ɵ��ݵ�ҵ��
			if(requisition.getRequisition_type_cd().equals("Stock Out Requisition")){
				RequisitionFlat original_req = requisitionDao.getRequisitionById(requisition.getRequisition_id());
				String delivery_wh_id = original_req.getDelivery_wh_id();
				//��ʼ��ɳ��ⵥ��ҵ�����
				for(int i=0;i<reqItemList.size();i++){
					RequisitionFlat reqItem = reqItemList.get(i);
					String prod_id = reqItem.getItem_prod_id();
					Product product = productDao.getProductById(prod_id);
					
					int item_quantity = reqItem.getItem_quantity();
					double item_price = reqItem.getItem_due_price();
					int original_prod_stock = product.getStock();
					int original_prod_stock_out = product.getOrdered_stock_out();
					WarehouseStock warehouse_stock_search = new WarehouseStock();
					WarehouseStock warehouse_stock_upsert = new WarehouseStock();

					warehouse_stock_search.setProduct_id(prod_id);
					warehouse_stock_search.setWarehouse_id(delivery_wh_id);

					warehouse_stock_upsert.setStock(item_quantity);
					warehouse_stock_upsert.setProduct_id(prod_id);
					warehouse_stock_upsert.setProduct_name(product.getName());
					warehouse_stock_upsert.setWarehouse_id(delivery_wh_id);
					warehouse_stock_upsert.setSku(product.getSku());
					warehouse_stock_upsert.setCreated_date(nowdate);
					warehouse_stock_upsert.setCreated_by_user_id(requisition.getUpdate_by_id());
					warehouse_stock_upsert.setUpdated_date(nowdate);
					warehouse_stock_upsert.setUpdated_by_user_id(requisition.getUpdate_by_id());

					if(original_req.getOrder_id()==null || "".equals(original_req.getOrder_id())){
						upsertResponse = this.upsertWarehouseStock("upsert_stock_out_directly", warehouse_stock_search, warehouse_stock_upsert);
					}else{
						upsertResponse = this.upsertWarehouseStock("upsert_stock_out", warehouse_stock_search, warehouse_stock_upsert);
					}
					
					if(!upsertResponse.get("upsertStatus").toString().equals("success")){
						return upsertResponse.get("upsertStatus").toString();
					}else{
						product.setOrdered_stock_out(original_prod_stock_out-item_quantity);
						product.setStock(original_prod_stock-item_quantity);
						product.setUpdate(nowdate);
						product.setUpdateById(requisition.getUpdate_by_id());
						productDao.updateProduct(product);
						
						StockHistory stock_history = new StockHistory();
						stock_history.setCreated_date(nowdate);
						stock_history.setCreated_by_user_id(requisition.getUpdate_by_id());
						stock_history.setStock_type_code("Stock Out");
						stock_history.setStock_type_val("����");
						stock_history.setOriginal_stock((int)upsertResponse.get("original_stock"));
						stock_history.setStock_quantity(item_quantity);
						stock_history.setRequisition_number(requisition.getRequisition_number());
						stock_history.setRequisition_id(requisition.getRequisition_id());
						stock_history.setWarehouse_id(delivery_wh_id);
						stock_history.setProduct_id(prod_id);
						stock_history.setStock_price(item_price);
						stock_history.setStock_amount(item_price*item_quantity);
						User user = userDao.selectUserDetail(requisition.getUpdate_by_id());
						stock_history.setDescription("��"+user.getUserName()+"����˳��ⵥ");
						stockHistoryDao.addHistory(stock_history);
						
						//Insert history record
				        RequisitionHistory req_history = new RequisitionHistory();
				        req_history.setRequisition_id(requisition.getRequisition_id());
				        req_history.setCreated_by_user_id(requisition.getUpdate_by_id());
				        req_history.setDescription("��"+requisition.getUpdate_by_name()+"����˳��ⵥ��");
				        this.addReqHistory(req_history);
						
					}//������ɳ��ⵥ��ҵ�����
				}
				
			}else if(requisition.getRequisition_type_cd().equals("Stock In Requisition")){
				//��ʼ�����ⵥ��ҵ�����
				RequisitionFlat original_req = requisitionDao.getRequisitionById(requisition.getRequisition_id());
				String receive_wh_id = original_req.getReceive_wh_id();
				
				RequisitionFlat reqItem = new RequisitionFlat();
				for(int i=0;i<reqItemList.size();i++){
					reqItem = reqItemList.get(i);
					String prod_id = reqItem.getItem_prod_id();
					Product product = productDao.getProductById(prod_id);
					
					int item_quantity = reqItem.getItem_quantity();
					double item_price = reqItem.getItem_due_price();
					int original_prod_stock_in = product.getOrdered_stock_in();
					int original_prod_stock = product.getStock();
					double original_prod_price = product.getPrice();
					
					//�����µĿ���Ʒƽ������
					double new_prod_price = (item_quantity*item_price + original_prod_stock*original_prod_price)/(original_prod_stock+item_quantity);
					product.setOrdered_stock_in(original_prod_stock_in-item_quantity);
					product.setStock(original_prod_stock+item_quantity);
					product.setPrice(new_prod_price);
					product.setUpdate(nowdate);
					product.setUpdateById(requisition.getUpdate_by_id());
					productDao.updateProduct(product);
					
					WarehouseStock warehouse_stock_search = new WarehouseStock();
					WarehouseStock warehouse_stock_upsert = new WarehouseStock();
					
					warehouse_stock_search.setProduct_id(prod_id);
					warehouse_stock_search.setWarehouse_id(receive_wh_id);
					
					warehouse_stock_upsert.setStock(item_quantity);
					warehouse_stock_upsert.setProduct_id(prod_id);
					warehouse_stock_upsert.setProduct_name(product.getName());
					warehouse_stock_upsert.setWarehouse_id(receive_wh_id);
					warehouse_stock_upsert.setSku(product.getSku());
					warehouse_stock_upsert.setCreated_date(nowdate);
					warehouse_stock_upsert.setCreated_by_user_id(requisition.getUpdate_by_id());
					warehouse_stock_upsert.setUpdated_date(nowdate);
					warehouse_stock_upsert.setUpdated_by_user_id(requisition.getUpdate_by_id());
					
					if(original_req.getOrder_id()==null || "".equals(original_req.getOrder_id())){
						upsertResponse = this.upsertWarehouseStock("upsert_stock_in_directly", warehouse_stock_search, warehouse_stock_upsert);
					}else{
						upsertResponse = this.upsertWarehouseStock("upsert_stock_in", warehouse_stock_search, warehouse_stock_upsert);
					}
					
					if(!upsertResponse.get("upsertStatus").toString().equals("success")){
						return upsertResponse.get("upsertStatus").toString();
					}else{
						StockHistory stock_history = new StockHistory();
						stock_history.setCreated_date(nowdate);
						stock_history.setCreated_by_user_id(requisition.getUpdate_by_id());
						stock_history.setStock_type_code("Stock In");
						stock_history.setStock_type_val("���");
						stock_history.setOriginal_stock((int)upsertResponse.get("original_stock"));
						stock_history.setStock_quantity(item_quantity);
						stock_history.setRequisition_number(requisition.getRequisition_number());
						stock_history.setRequisition_id(requisition.getRequisition_id());
						stock_history.setWarehouse_id(receive_wh_id);
						stock_history.setProduct_id(prod_id);
						stock_history.setStock_price(item_price);
						stock_history.setStock_amount(item_price*item_quantity);
						User user = userDao.selectUserDetail(requisition.getUpdate_by_id());
						stock_history.setDescription("��"+user.getUserName()+"�������ⵥ");
						stockHistoryDao.addHistory(stock_history);
						
						//Insert history record
				        RequisitionHistory req_history = new RequisitionHistory();
				        req_history.setRequisition_id(requisition.getRequisition_id());
				        req_history.setCreated_by_user_id(requisition.getUpdate_by_id());
				        req_history.setDescription("��"+requisition.getUpdate_by_name()+"�������ⵥ��");
				        this.addReqHistory(req_history);
					}
				}//���������ⵥ��ҵ�����
			}
		}else if(requisition.getStatus_code().equals("submit - pending") || requisition.getStatus_code().equals("submit - approved") || requisition.getStatus_code().equals("submitted")){
			//����check���״̬�Ľӿڴ���
			//**********************
			RequisitionFlat original_req = requisitionDao.getRequisitionById(requisition.getRequisition_id());
			if(requisition.getRequisition_type_cd().equals("Stock Out Requisition")){
				if(reqItemList.size()==0){
					return "û���κγ�����޷��ύ���ⵥ��";
				}else if(original_req.getOrder_id()==null || "".equals(original_req.getOrder_id())){
					return "��û��ѡ��������۶���������ѡ��";
				}else{
					List<OrderReqProdCounts> req_prod_counts_list = requisitionDao.getReqProdCountsForOrderId(original_req.getOrder_id());
					for(int i=0;i<req_prod_counts_list.size();i++){
						OrderReqProdCounts req_prod_counts = req_prod_counts_list.get(i);
						List<OrderReqProdCounts> order_prod_counts = orderDao.getOrderProdCounts(original_req.getOrder_id(), req_prod_counts.getProduct_id());
						if(order_prod_counts.size()==0){
							Product product = productDao.getProductById(req_prod_counts.getProduct_id());
							return "��Ʒ"+product.getModel()+"�ڹ������۶����в����ڣ�����������ύ��";
						}else if(req_prod_counts.getQuantity() > order_prod_counts.get(0).getQuantity()){
							Product product = productDao.getProductById(req_prod_counts.getProduct_id());
							return "��Ʒ"+product.getModel()+"�ĳ������������˹������۶���������������������ύ��";
						}
					}
				}
				//Insert history record
		        RequisitionHistory req_history = new RequisitionHistory();
		        req_history.setRequisition_id(requisition.getRequisition_id());
		        req_history.setCreated_by_user_id(requisition.getUpdate_by_id());
		        req_history.setDescription("��"+requisition.getUpdate_by_name()+"�ύ�˳��ⵥ��");
		        this.addReqHistory(req_history);
			}else if(requisition.getRequisition_type_cd().equals("Stock In Requisition")){
				if(reqItemList.size()==0){
					return "û���κ������޷��ύ��ⵥ��";
				}else if(original_req.getOrder_id()==null || "".equals(original_req.getOrder_id())){
					return "��û��ѡ������ɹ�����������ѡ��";
				}else{
					List<OrderReqProdCounts> req_prod_counts_list = requisitionDao.getReqProdCountsForOrderId(original_req.getOrder_id());
					for(int i=0;i<req_prod_counts_list.size();i++){
						OrderReqProdCounts req_prod_counts = req_prod_counts_list.get(i);
						List<OrderReqProdCounts> order_prod_counts = orderDao.getOrderProdCounts(original_req.getOrder_id(), req_prod_counts.getProduct_id());
						if(order_prod_counts.size()==0){
							Product product = productDao.getProductById(req_prod_counts.getProduct_id());
							return "��Ʒ"+product.getModel()+"�ڹ����ɹ������в����ڣ�����������ύ��";
						}else if(req_prod_counts.getQuantity() > order_prod_counts.get(0).getQuantity()){
							Product product = productDao.getProductById(req_prod_counts.getProduct_id());
							return "��Ʒ"+product.getModel()+"��������������˹����ɹ�����������������������ύ��";
						}
					}
				}
				//Insert history record
		        RequisitionHistory req_history = new RequisitionHistory();
		        req_history.setRequisition_id(requisition.getRequisition_id());
		        req_history.setCreated_by_user_id(requisition.getUpdate_by_id());
		        req_history.setDescription("��"+requisition.getUpdate_by_name()+"�ύ����ⵥ��");
		        this.addReqHistory(req_history);
			}
			
		}
		
		if(requisitionDao.updateRequisitionStatus(requisition)){
			return "success";
		}else{
			return "���³����쳣";
		}
	}
	//���»����ֿ����¼�ķ���
	private Map<String,Object> upsertWarehouseStock(String upsert_type, WarehouseStock warehouse_stock_search, WarehouseStock warehouse_stock_upsert) throws Exception {
		
		Map<String,Object> responseMap = new HashMap<String,Object>();
		if( upsert_type.equals("upsert_ordered_stock_in")){
			//���»���������¼
			int item_quantity = warehouse_stock_upsert.getOrdered_stock_in();
			List<WarehouseStock> warehouse_stock_list = warehouseStockDao.getStockInfos(warehouse_stock_search, null, null, null, null);
			if(warehouse_stock_list.size() > 0){
				WarehouseStock warehouse_stock_original = warehouse_stock_list.get(0);
				int original_stock_in = warehouse_stock_original.getOrdered_stock_in();
				warehouse_stock_upsert.setOrdered_stock_in(original_stock_in + item_quantity);
				warehouse_stock_upsert.setStock(-1);
				warehouse_stock_upsert.setId(warehouse_stock_original.getId());
				
				if(warehouseStockDao.updateStockInfoById(warehouse_stock_upsert)){
					responseMap.put("upsertStatus","success");
					return responseMap;
					//return "success";
				}else{
					responseMap.put("upsertStatus","���´�����¼ʧ�ܣ�");
					return responseMap;
					//return "���´�����¼ʧ�ܣ�";
				}
				 
			}else{
				if(warehouseStockDao.addStockInfo(warehouse_stock_upsert)){
					responseMap.put("upsertStatus","success");
					return responseMap;
				}else{
					responseMap.put("upsertStatus","���������¼ʧ�ܣ�");
					return responseMap;
					//return "���������¼ʧ�ܣ�";
				}
			}
		}else if( upsert_type.equals("upsert_stock_in")){
			//���»��������¼����ⵥ�ɲɹ���������
			int item_quantity = warehouse_stock_upsert.getStock();
			List<WarehouseStock> warehouse_stock_list = warehouseStockDao.getStockInfos(warehouse_stock_search, null, null, null, null);
			if(warehouse_stock_list.size() > 0){
				WarehouseStock warehouse_stock_original = warehouse_stock_list.get(0);
				int original_stock_in = warehouse_stock_original.getOrdered_stock_in();
				int original_stock = warehouse_stock_original.getStock();
				warehouse_stock_upsert.setOrdered_stock_in(original_stock_in - item_quantity);
				warehouse_stock_upsert.setStock(original_stock + item_quantity);
				warehouse_stock_upsert.setId(warehouse_stock_original.getId());
				
				if(warehouseStockDao.updateStockInfoById(warehouse_stock_upsert)){
					responseMap.put("upsertStatus","success");
					responseMap.put("original_stock", original_stock);
					return responseMap;
				}else{
					responseMap.put("upsertStatus","��������¼ʧ�ܣ�");
					return responseMap;
				}
			}else{
				if(warehouseStockDao.addStockInfo(warehouse_stock_upsert)){
					responseMap.put("upsertStatus","success");
					responseMap.put("original_stock", 0);
					return responseMap;
				}else{
					responseMap.put("upsertStatus","��������¼ʧ�ܣ�");
					return responseMap;
				}
			}
		}else if( upsert_type.equals("upsert_stock_in_directly")){
			//���»��������¼���ֶ���������ⵥ
			int item_quantity = warehouse_stock_upsert.getStock();
			List<WarehouseStock> warehouse_stock_list = warehouseStockDao.getStockInfos(warehouse_stock_search, null, null, null, null);
			if(warehouse_stock_list.size() > 0){
				WarehouseStock warehouse_stock_original = warehouse_stock_list.get(0);
				int original_stock = warehouse_stock_original.getStock();
				warehouse_stock_upsert.setOrdered_stock_in(-1);
				warehouse_stock_upsert.setStock(original_stock + item_quantity);
				warehouse_stock_upsert.setId(warehouse_stock_original.getId());
				
				if(warehouseStockDao.updateStockInfoById(warehouse_stock_upsert)){
					responseMap.put("upsertStatus","success");
					responseMap.put("original_stock", original_stock);
					return responseMap;
				}else{
					responseMap.put("upsertStatus","��������¼ʧ�ܣ�");
					return responseMap;
				}
			}else{
				if(warehouseStockDao.addStockInfo(warehouse_stock_upsert)){
					responseMap.put("upsertStatus","success");
					responseMap.put("original_stock", 0);
					return responseMap;
				}else{
					responseMap.put("upsertStatus","��������¼ʧ�ܣ�");
					return responseMap;
				}
			}
		}else if( upsert_type.equals("upsert_ordered_stock_out")){
			//���»����������¼
			int item_quantity = warehouse_stock_upsert.getOrdered_stock_out();
			List<WarehouseStock> warehouse_stock_list = warehouseStockDao.getStockInfos(warehouse_stock_search, null, null, null, null);
			WarehouseFlat warehouse = warehouseDao.getWarehouseById(warehouse_stock_upsert.getWarehouse_id());
			if(warehouse_stock_list.size() > 0){
				WarehouseStock warehouse_stock_original = warehouse_stock_list.get(0);
				int original_stock_out = warehouse_stock_original.getOrdered_stock_out();
				int original_stock = warehouse_stock_original.getStock();
				if( original_stock < (original_stock_out + item_quantity)){
					responseMap.put("upsertStatus",warehouse_stock_upsert.getProduct_name() + "�Ĵ����������Ѿ�����"+warehouse_stock_upsert.getWarehouse_name()+"�Ŀ�棬������������");
					return responseMap;
				}
				warehouse_stock_upsert.setOrdered_stock_out(original_stock_out + item_quantity);
				warehouse_stock_upsert.setStock(-1);
				warehouse_stock_upsert.setId(warehouse_stock_original.getId());
				
				if(warehouseStockDao.updateStockInfoById(warehouse_stock_upsert)){
					responseMap.put("upsertStatus","success");
					return responseMap;
				}else{
					responseMap.put("upsertStatus","���´������¼ʧ�ܣ�");
					return responseMap;
				}
			}else{
				responseMap.put("upsertStatus",warehouse.getName()+"û��"+warehouse_stock_upsert.getProduct_name()+"�Ŀ�棬������������");
				return responseMap;
			}
		}else if( upsert_type.equals("upsert_stock_out")){
			//���»��������¼�������۶������ɵĳ��ⵥ
			int item_quantity = warehouse_stock_upsert.getStock();
			List<WarehouseStock> warehouse_stock_list = warehouseStockDao.getStockInfos(warehouse_stock_search, null, null, null, null);
			WarehouseFlat warehouse = warehouseDao.getWarehouseById(warehouse_stock_upsert.getWarehouse_id());
			if(warehouse_stock_list.size() > 0){
				WarehouseStock warehouse_stock_original = warehouse_stock_list.get(0);
				int original_stock_out = warehouse_stock_original.getOrdered_stock_out();
				int original_stock = warehouse_stock_original.getStock();
				if( original_stock < item_quantity){
					responseMap.put("upsertStatus",warehouse_stock_upsert.getProduct_name() + "�ĳ��������Ѿ�����"+warehouse_stock_upsert.getWarehouse_name()+"�Ŀ�棬����������");
					return responseMap;
				}
				warehouse_stock_upsert.setOrdered_stock_out(original_stock_out - item_quantity);
				warehouse_stock_upsert.setStock(original_stock - item_quantity);
				warehouse_stock_upsert.setId(warehouse_stock_original.getId());
				
				if( (original_stock_out==item_quantity)&&(original_stock==item_quantity) ){
					warehouseStockDao.delStockInfoById(warehouse_stock_original.getId());
				}
				
				if(warehouseStockDao.updateStockInfoById(warehouse_stock_upsert)){
					responseMap.put("original_stock", original_stock);
					responseMap.put("upsertStatus","success");
					return responseMap;
				}else{
					responseMap.put("upsertStatus","���³����¼ʧ�ܣ�");
					return responseMap;
				}
			}else{
				responseMap.put("upsertStatus",warehouse.getName()+"û��"+warehouse_stock_upsert.getProduct_name()+"�Ŀ�棬������������");
				return responseMap;
			}
		}else if( upsert_type.equals("upsert_stock_out_directly")){
			//���»��������¼���ֶ������ĳ��ⵥ
			int item_quantity = warehouse_stock_upsert.getStock();
			List<WarehouseStock> warehouse_stock_list = warehouseStockDao.getStockInfos(warehouse_stock_search, null, null, null, null);
			WarehouseFlat warehouse = warehouseDao.getWarehouseById(warehouse_stock_upsert.getWarehouse_id());
			if(warehouse_stock_list.size() > 0){
				WarehouseStock warehouse_stock_original = warehouse_stock_list.get(0);
				int original_stock_out = warehouse_stock_original.getOrdered_stock_out();
				int original_stock = warehouse_stock_original.getStock();
				if( original_stock < (item_quantity+original_stock_out)){
					responseMap.put("upsertStatus",warehouse_stock_upsert.getProduct_name() + "�ĳ����������ϴ����������Ѿ�����"+warehouse_stock_upsert.getWarehouse_name()+"�Ŀ�棬����������");
					return responseMap;
				}
				warehouse_stock_upsert.setOrdered_stock_out(-1);
				warehouse_stock_upsert.setStock(original_stock - item_quantity);
				warehouse_stock_upsert.setId(warehouse_stock_original.getId());
				
				if( (original_stock_out==item_quantity)&&(original_stock==item_quantity) ){
					warehouseStockDao.delStockInfoById(warehouse_stock_original.getId());
				}
				
				if(warehouseStockDao.updateStockInfoById(warehouse_stock_upsert)){
					responseMap.put("original_stock", original_stock);
					responseMap.put("upsertStatus","success");
					return responseMap;
				}else{
					responseMap.put("upsertStatus","���³����¼ʧ�ܣ�");
					return responseMap;
				}
			}else{
				responseMap.put("upsertStatus",warehouse.getName()+"û��"+warehouse_stock_upsert.getProduct_name()+"�Ŀ�棬������������");
				return responseMap;
			}
		}
		
		return responseMap;
	}

	@Override
	public List<RequisitionFlat> getAllRequisitions() throws Exception {
		// TODO Auto-generated method stub
		return requisitionDao.getAllRequisitions();
	}

	@Override
	public RequisitionFlat getRequisitionById(String id) throws Exception {
		// TODO Auto-generated method stub
		return requisitionDao.getRequisitionById(id);
	}

	@Override
	public List<RequisitionFlat> getRequisitions(RequisitionFlat requisition,
			String start, String limit, String sortColumn, String sortDir)
			throws Exception {
		// TODO Auto-generated method stub
		return requisitionDao.getRequisitions(requisition, start, limit, sortColumn, sortDir);
	}

	@Override
	public boolean addRequisitionItem(RequisitionFlat requisition)
			throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		//test
		RequisitionHistory reqHistory = new RequisitionHistory();
		reqHistory.setRequisition_id(requisition.getRequisition_id());
		
		String create_by_id = requisition.getItem_created_by_id();
		String create_by_name = requisition.getItem_created_by_name();
		reqHistory.setDescription("��" + create_by_name + "������" + requisition.getRequisition_type() + "����Ʒ����Ϊ��"
				+ requisition.getItem_prod_name());
		reqHistory.setCreated_date(nowdate);
		reqHistory.setCreated_by_user_id(create_by_id);
		this.addReqHistory(reqHistory);
		
		requisition.setItem_created_date(nowdate);
		requisition.setItem_update_date(nowdate);
		return requisitionDao.addRequisitionItem(requisition);
	}

	@Override
	public boolean delRequisitionItemById(String id, Object[] params) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		
		RequisitionHistory reqHistory = new RequisitionHistory();
		
		/**
		 * Object[] params = new Object[]{requisition_type, requisition_id, product_name, username, userid};
		 */
		String requisition_type = params[0].toString();
		String requisition_id = params[1].toString();
		String product_name = params[2].toString();
		String username = params[3].toString();
		String userid = params[4].toString();
		
		reqHistory.setRequisition_id(requisition_id);
		reqHistory.setDescription("��" + username + "ɾ����" + requisition_type + "����Ʒ����Ϊ��" + product_name);
		reqHistory.setCreated_date(nowdate);
		reqHistory.setCreated_by_user_id(userid);
		this.addReqHistory(reqHistory);
		
		
		return requisitionDao.delRequisitionItemById(id);
	}

	@Override
	public boolean updateRequisitionItem(RequisitionFlat requisition)
			throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		RequisitionHistory reqHistory = new RequisitionHistory();
		reqHistory.setRequisition_id(requisition.getRequisition_id());
		
		reqHistory.setDescription("��" + requisition.getItem_update_by_name() + "������" + requisition.getRequisition_type() + 
				"����Ʒ����Ϊ��" + requisition.getItem_prod_name());
		reqHistory.setCreated_date(nowdate);
		reqHistory.setCreated_by_user_id(requisition.getItem_created_by_id());
		this.addReqHistory(reqHistory);
		
		
		requisition.setItem_update_date(nowdate);
		return requisitionDao.updateRequisitionItem(requisition);
	}

	@Override
	public List<RequisitionFlat> getItemsByReqId(String requisitionId)
			throws Exception {
		// TODO Auto-generated method stub
		return requisitionDao.getItemsByReqId(requisitionId);
	}

	@Override
	@Transactional
	public String addStockInReq(RequisitionFlat requisition) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		requisition.setCreated_date(nowdate);
		requisition.setUpdate_date(nowdate);
		requisition.setStatus_code("new");
		requisition.setStatus_value(listOfValueDao.getValueByCodeName("Requisition Status", "new"));
		requisition.setRequisition_type_cd("Stock In Requisition");
		requisition.setRequisition_type(listOfValueDao.getValueByCodeName("Requisition Type", "Stock In Requisition"));
		
		//Generate requisition number
		SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddhhmmss");     
        timeformat.setLenient(false);   
        String req_number = requisition.getCreated_by_id()+"-" +timeformat.format(date); 
        requisition.setRequisition_number(req_number);
        //Insert history record
        String new_req_id = requisitionDao.addRequisition(requisition);
        RequisitionHistory req_history = new RequisitionHistory();
        req_history.setRequisition_id(new_req_id);
        req_history.setCreated_by_user_id(requisition.getCreated_by_id());
        req_history.setDescription("��"+requisition.getCreated_by_name()+"����");
        this.addReqHistory(req_history);
        
		return new_req_id;
	}

	@Override
	@Transactional
	public String addStockOutReq(RequisitionFlat requisition) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		requisition.setCreated_date(nowdate);
		requisition.setUpdate_date(nowdate);
		requisition.setStatus_code("new");
		requisition.setStatus_value(listOfValueDao.getValueByCodeName("Requisition Status", "new"));
		requisition.setRequisition_type_cd("Stock Out Requisition");
		requisition.setRequisition_type(listOfValueDao.getValueByCodeName("Requisition Type", "Stock Out Requisition"));
		
		//Generate requisition number
		SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddhhmmss");     
        timeformat.setLenient(false);   
        String req_number = requisition.getCreated_by_id()+"-" +timeformat.format(date); 
        requisition.setRequisition_number(req_number);
        //Insert history record
        String new_req_id = requisitionDao.addRequisition(requisition);
        RequisitionHistory req_history = new RequisitionHistory();
        req_history.setRequisition_id(new_req_id);
        req_history.setCreated_by_user_id(requisition.getCreated_by_id());
        req_history.setDescription("��"+requisition.getCreated_by_name()+"����");
        this.addReqHistory(req_history);
        
		return new_req_id;
	}

	@Override
	public String addStockTransferReq(RequisitionFlat requisition)
			throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		requisition.setCreated_date(nowdate);
		requisition.setUpdate_date(nowdate);
		return requisitionDao.addRequisition(requisition);
	}

	@Override
	public boolean addReqHistory(RequisitionHistory requisition_history)
			throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		requisition_history.setCreated_date(nowdate);
		return requisitionHistoryDao.addHistory(requisition_history);
	}

	@Override
	public boolean delReqHistoryById(String id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RequisitionHistory> getAllReqHistories() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequisitionHistory getReqHistoryById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RequisitionHistory> getReqHistories(
			RequisitionHistory requisition_history, String start, String limit,
			String sortColumn, String sortDir) throws Exception {
		// TODO Auto-generated method stub
		return requisitionHistoryDao.getHistories(requisition_history, start, limit, sortColumn, sortDir);
	}

	@Override
	public List<RequisitionFlat> getRequisitionsForApprover(RequisitionFlat requisition,String approver_id,
			String start, String limit, String sortColumn, String sortDir)
			throws Exception {
		// TODO Auto-generated method stub
		return requisitionDao.getRequisitionsForApprover(requisition,approver_id, start, limit, sortColumn, sortDir);
	}

	@Override
	public RequisitionFlat getItemsById(String id) throws Exception {
		// TODO Auto-generated method stub
		return requisitionDao.getItemsById(id);
	}

	@Override
	public boolean addApprovalHistory(ApprovalHistory approval_history)
			throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		approval_history.setCreated_date(nowdate);
		return approvalHistoryDao.addHistory(approval_history);
	}

	@Override
	public List<ApprovalHistory> getApprovalHistories(
			ApprovalHistory approval_history, String start, String limit,
			String sortColumn, String sortDir) throws Exception {
		// TODO Auto-generated method stub
		return approvalHistoryDao.getHistories(approval_history, start, limit, sortColumn, sortDir);
	}
	
	@Qualifier("authService")
	@Autowired
	private AuthService authService;

	@Override
	@Transactional
	public String updateRequisitionStatusFacade(RequisitionFlat requisition)
			throws Exception {
		// TODO Auto-generated method stub
		try{
			String status_code = requisition.getStatus_code();
			String type_code = requisition.getRequisition_type_cd();
			String success_msg = "";

			if(status_code.equals("hold")){
				success_msg = "���õ���";
			}else if(status_code.equals("cancelled")){
				success_msg = "ȡ������";
			}else if(status_code.equals("completed")){
				success_msg = "��ɵ���";
			}else if(status_code.equals("submitted")){
				success_msg = "�ύ����";
			}else if(status_code.equals("reopen")){
				success_msg = "�ؿ�����";
			}else if(status_code.equals("submit - approved")){
				success_msg = "�ύ��˵���";
			}else if(status_code.equals("submit - rejected")){
				success_msg = "�ύ�ܾ�����";
			}

			String ActionType = "";
			ApprovalHistory approvalHistory = new ApprovalHistory();
			switch(status_code){
			case "submitted":
				ActionType = "Submit";
				approvalHistory.setRequisition_id(requisition.getRequisition_id());
				approvalHistory.setCreated_by_user_id(requisition.getUpdate_by_id());
				approvalHistory.setDescription("��"+requisition.getUpdate_by_name()+"�ύ�˵��ݣ�");
				this.addApprovalHistory(approvalHistory);
				break;
			case "submit - approved":
				ActionType = "Approve";
				approvalHistory.setRequisition_id(requisition.getRequisition_id());
				approvalHistory.setCreated_by_user_id(requisition.getUpdate_by_id());
				approvalHistory.setDescription("��"+requisition.getUpdate_by_name()+"���ͨ���˵��ݣ�");
				this.addApprovalHistory(approvalHistory);
				break;
			case "submit - rejected":
				ActionType = "Reject";
				approvalHistory.setRequisition_id(requisition.getRequisition_id());
				approvalHistory.setCreated_by_user_id(requisition.getUpdate_by_id());
				approvalHistory.setDescription("��"+requisition.getUpdate_by_name()+"�ܾ��˵��ݣ�");
				this.addApprovalHistory(approvalHistory);
				break;
			default:
				ActionType = "Not Defined";
				break;
			}

			String objectName = type_code;
			switch(authService.ApprovalExecute(requisition.getRequisition_id(), objectName, ActionType, requisition.getUpdate_by_id())){
			case "Approval Not Required":
				break;
			case "Pending":
				status_code = "submit - pending";
				break;
			case "Approved":
				status_code = "submit - approved";
				break;
			case "Rejected":
				status_code = "submit - rejected";
				break;
			}

			requisition.setStatus_code(status_code);
			requisition.setStatus_value(listOfValueDao.getValueByCodeName("Requisition Status", status_code));
			requisition.setRequisition_type_cd(type_code);
			String update_status = this.updateRequisitionStatus(requisition);
			if(update_status.equals("success")){
				return "{'objname':'"+success_msg+"'}";
			}else{
				//return "{'errmsg':'"+update_status+"'}";
				throw new RuntimeException(update_status);
			}
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public boolean addAttachment(Attachment file) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		file.setUploaded_date(nowdate);
		file.setUpload_source("Requisition Attachment Uploader");
		file.setUpdate_date(nowdate);
		file.setType("Requisition Attachment");
		return fileDao.addFile(file);
	}

	@Override
	public List<Attachment> getAttachmentsByRegId(String id) throws Exception {
		// TODO Auto-generated method stub
		return fileDao.getFileByRequisitionId(id);
	}

	@Override
	public boolean delAttachmentById(String id) throws Exception {
		// TODO Auto-generated method stub
		Attachment attachment = fileDao.getFileById(id);
		String realPath = attachment.getPath();
		String fileName = attachment.getOriginal_filename();
		File image = new File(realPath+"\\"+fileName);
		image.delete();
		
		return fileDao.delFileById(id);
	}

	@Override
	public List<RequisitionFlat> getRecentRequisitions(String userid)
			throws Exception {
		// TODO Auto-generated method stub
		return requisitionDao.getRecentRequisitions(userid);
	}

	@Override
	public List<OrderReqProdCounts> getReqProdCounts(String req_id)
			throws Exception {
		// TODO Auto-generated method stub
		return requisitionDao.getReqProdCounts(req_id);
	}

}
