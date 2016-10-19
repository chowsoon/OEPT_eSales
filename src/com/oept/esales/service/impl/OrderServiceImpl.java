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

import com.oept.esales.dao.AccountDao;
import com.oept.esales.dao.ApprovalHistoryDao;
import com.oept.esales.dao.FileDao;
import com.oept.esales.dao.ListOfValueDao;
import com.oept.esales.dao.OrderDao;
import com.oept.esales.dao.OrderHistoryDao;
import com.oept.esales.dao.ProductDao;
import com.oept.esales.dao.RequisitionDao;
import com.oept.esales.dao.RequisitionHistoryDao;
import com.oept.esales.dao.StockHistoryDao;
import com.oept.esales.dao.UserDao;
import com.oept.esales.dao.WarehouseDao;
import com.oept.esales.dao.WarehouseStockDao;
import com.oept.esales.model.Account;
import com.oept.esales.model.ApprovalHistory;
import com.oept.esales.model.Attachment;
import com.oept.esales.model.OrderFlat;
import com.oept.esales.model.OrderHistory;
import com.oept.esales.model.OrderReqProdCounts;
import com.oept.esales.model.Product;
import com.oept.esales.model.RequisitionFlat;
import com.oept.esales.model.RequisitionHistory;
import com.oept.esales.model.StockHistory;
import com.oept.esales.model.User;
import com.oept.esales.model.WarehouseStock;
import com.oept.esales.service.AuthService;
import com.oept.esales.service.OrderService;
/**
 * @author mwan
 * Version: 1.3
 * Date: 2016/1/6
 * Description: Orders business service implementor.
 * Copyright (c) 2015 OEPT. All rights reserved.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao; //order DAO
	@Autowired
	private UserDao userDao; //user DAO
	@Autowired
	private ProductDao productDao; //order DAO
	@Autowired
	private WarehouseDao warehouseDao; //warehouse DAO
	@Autowired
	private WarehouseStockDao warehouseStockDao; //warehouse stock info DAO
	@Autowired
	private AccountDao accountDao; //account info DAO
	@Autowired
	private StockHistoryDao stockHistoryDao; //stock history info DAO
	@Autowired
	private OrderHistoryDao orderHistoryDao; //order history info DAO
	@Autowired
	private ApprovalHistoryDao approvalHistoryDao; //approval history info DAO
	@Autowired
	private RequisitionDao requisitionDao; //requisition DAO
	@Autowired
	private RequisitionHistoryDao requisitionHistoryDao; //requisition history DAO
	@Autowired
	private ListOfValueDao listOfValueDao; //list of values info DAO
	@Autowired
	private FileDao fileDao; //����DAO
	
	@Override
	@Transactional
	public boolean addOrderItem(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order.setItem_created_date(nowdate);
		order.setItem_update_date(nowdate);
		order.setItem_status_code("Available");
		order.setItem_status_value("����");
		
		orderDao.addOrderItem(order);
        OrderHistory order_history = new OrderHistory();
        order_history.setOrder_id(order.getOrder_id());
        order_history.setCreated_by_user_id(order.getItem_created_by_id());
        order_history.setDescription("��"+order.getItem_created_by_name()+"�����˶������ƷΪ"+order.getItem_prod_name()+" "
        		+ "�ֿ�Ϊ"+order.getItem_warehouse_name());
		
		return this.addHistory(order_history);
	}

	@Override
	@Transactional
	public boolean updateOrderItem(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order.setItem_update_date(nowdate);
		
		orderDao.updateOrderItem(order);
        OrderHistory order_history = new OrderHistory();
        order_history.setOrder_id(order.getOrder_id());
        order_history.setCreated_by_user_id(order.getItem_update_by_id());
        order_history.setDescription("��"+order.getItem_update_by_name()+"�����˶������ƷΪ"+order.getItem_prod_name()+" "
        		+ "�ֿ�Ϊ"+order.getItem_warehouse_name());
		
		return this.addHistory(order_history);
	}

	@Override
	public boolean delOrderItemById(String id, Object[] params) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		
		OrderHistory order_history = new OrderHistory();
		order_history.setOrder_id(params[2].toString());
		order_history.setCreated_by_user_id(params[0].toString());
		order_history.setDescription("��" + params[1].toString() + "ɾ���˶������ƷΪ" + params[3]);
		this.addHistory(order_history);
		
		
		
		
		return orderDao.delOrderItemById(id);
	}

	@Override
	public List<OrderFlat> getItemsByOrderId(String orderId) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.getItemsByOrderId(orderId);
	}

	@Override
	public String addOrder(OrderFlat order, String userId) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order.setCreated_date(nowdate);
		order.setUpdate_date(nowdate);
		order.setPurchase_date(nowdate);
		order.setOwner_viewed(false);
		order.setCreated_by_id(userId);
		order.setUpdate_by_id(userId);
		order.setOwner_id(userId);
		order.setStatus_code("new");
		order.setStatus_value(listOfValueDao.getValueByCodeName("Order Status", "new"));
		order.setOrder_type(listOfValueDao.getValueByCodeName("Order Type", "Sales Order"));
		order.setOrder_type_cd("Sales Order");
		
		User user = userDao.userDefaultAddress(userId);
		order.setShip_addr_id(user.getPrimaryAddressId());
		order.setBill_addr_id(user.getPrimaryAddressId());
		//Generate order number 
        SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddhhmmss");     
        timeformat.setLenient(false);;   
        String order_number = userId +"-" +timeformat.format(date); 
        order.setOrder_number(order_number);
		
		return orderDao.addOrder(order);
	}

	@Override
	public List<OrderFlat> getAllOrders() throws Exception {
		// TODO Auto-generated method stub
		return orderDao.getAllOrders();
	}

	@Override
	public boolean delOrderById(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OrderFlat getOrderById(String id) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.getOrderById(id);
	}

	@Override
	@Transactional
	public boolean updateOrderById(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order.setUpdate_date(nowdate);
		
        if(order.getHistory_desc()!=null && !"".equals(order.getHistory_desc())){
        	OrderHistory order_history = new OrderHistory();
            order_history.setOrder_id(order.getOrder_id());
            order_history.setCreated_by_user_id(order.getUpdate_by_id());
        	order_history.setDescription(order.getHistory_desc());
            this.addHistory(order_history);
        }
		
		return orderDao.updateOrder(order);
	}

	@Override
	public int getOrdersCount() throws Exception {
		// TODO Auto-generated method stub
		return orderDao.getOrdersCount();
	}

	@Override
	public List<OrderFlat> getOrders(OrderFlat order, String start, String limit,
			String sortColumn, String sortDir) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.getOrders(order, start, limit, sortColumn, sortDir);
	}

	@Override
	@Transactional
	public String updateOrderStatusById(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order.setUpdate_date(nowdate);
		List<OrderFlat> orderItemList = orderDao.getItemsByOrderId(order.getOrder_id());
		Map<String,Object> upsertResponse = new HashMap<String,Object>();
		
		if(order.getStatus_code().equals("completed")){//��ʼ�رյ��ݵ�ҵ��
			//�����������Ƿ��Ѿ�ȫ�����
			RequisitionFlat reqSearch = new RequisitionFlat();
			reqSearch.setOrder_id(order.getOrder_id());
			List<RequisitionFlat> relatedReqsList = requisitionDao.getRequisitions(reqSearch, null, null, null, null);
			for(int i=0;i<relatedReqsList.size();i++){
				RequisitionFlat relatedReq = relatedReqsList.get(i);
				if(!(relatedReq.getStatus_code().equals("completed"))){
					return "���й�������δ��ɣ�����ȫ����ɣ�";
				}
			}
			//��ɹ�������״̬���
			if(order.getOrder_type_cd().equals("Sales Order")){
				//��ʼ������۵���ҵ�����
				List<OrderReqProdCounts> order_prod_counts_list = orderDao.getOrderProdCounts(order.getOrder_id());
				for(int i=0;i<order_prod_counts_list.size();i++){
					List<OrderReqProdCounts> related_req_prod_counts = requisitionDao.getReqProdCountsForOrderId(order.getOrder_id(), order_prod_counts_list.get(i).getProduct_id());
					if(order_prod_counts_list.get(i).getQuantity() > related_req_prod_counts.get(0).getQuantity()){
						Product product = productDao.getProductById(related_req_prod_counts.get(0).getProduct_id());
						return "��Ʒ"+product.getModel()+"��û��ȫ���⣬ȫ���������ܹر���Ӧ������";
					}
				}
				//����������۵���ҵ�����
				
			}else if(order.getOrder_type_cd().equals("Purchase Order")){
				//��ʼ������۵���ҵ�����
				List<OrderReqProdCounts> order_prod_counts_list = orderDao.getOrderProdCounts(order.getOrder_id());
				for(int i=0;i<order_prod_counts_list.size();i++){
					List<OrderReqProdCounts> related_req_prod_counts = requisitionDao.getReqProdCountsForOrderId(order.getOrder_id(), order_prod_counts_list.get(i).getProduct_id());
					if(order_prod_counts_list.get(i).getQuantity() > related_req_prod_counts.get(0).getQuantity()){
						Product product = productDao.getProductById(related_req_prod_counts.get(0).getProduct_id());
						return "��Ʒ"+product.getModel()+"��û��ȫ��⣬ȫ��������ܹر���Ӧ������";
					}
				}
				//����������۵���ҵ�����
				
			}else if(order.getOrder_type_cd().equals("Sales Return Order")){
				//��ʼ��������˻�����ҵ�����
				OrderFlat original_order = orderDao.getOrderById(order.getOrder_id());
				String receive_wh_id = original_order.getReceive_wh_id();
				
				OrderFlat orderItem = new OrderFlat();
				for(int i=0;i<orderItemList.size();i++){
					orderItem = orderItemList.get(i);
					String prod_id = orderItem.getItem_prod_id();
					Product product = productDao.getProductById(prod_id);
					
					int item_quantity = orderItem.getItem_quantity();
					int original_prod_stock_in = product.getOrdered_stock_in();
					int original_prod_stock = product.getStock();
					product.setOrdered_stock_in(original_prod_stock_in-item_quantity); //��Ʒ�����������
					product.setStock(original_prod_stock+item_quantity);//��Ʒ���������
					product.setUpdate(nowdate);
					product.setUpdateById(order.getUpdate_by_id());
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
					warehouse_stock_upsert.setUpdated_date(nowdate);
					warehouse_stock_upsert.setUpdated_by_user_id(order.getUpdate_by_id());
					
					upsertResponse = this.upsertWarehouseStock("upsert_stock_in", warehouse_stock_search, warehouse_stock_upsert);
					if(!upsertResponse.get("upsertStatus").toString().equals("success")){
						return upsertResponse.get("upsertStatus").toString();
					}else{
						StockHistory stock_history = new StockHistory();
						stock_history.setCreated_date(nowdate);
						stock_history.setCreated_by_user_id(order.getUpdate_by_id());
						stock_history.setStock_type_code("Stock In");
						stock_history.setStock_type_val("���");
						stock_history.setOriginal_stock((int)upsertResponse.get("original_stock"));
						stock_history.setStock_quantity(item_quantity);
						stock_history.setOrder_type_code("Sales Return Order");
						stock_history.setOrder_type_val("�����˻�����");
						stock_history.setOrder_id(order.getOrder_id());
						stock_history.setWarehouse_id(receive_wh_id);
						stock_history.setProduct_id(prod_id);
						User user = userDao.selectUserDetail(order.getUpdate_by_id());
						stock_history.setDescription("��"+user.getUserName()+"����������˻�����");
						stockHistoryDao.addHistory(stock_history);
					}
				}//������������˻�����ҵ�����
			}else if(order.getOrder_type_cd().equals("Purchase Order not used")){
				//��ʼ��ɲɹ�����ҵ�����
				OrderFlat original_order = orderDao.getOrderById(order.getOrder_id());
				String receive_wh_id = original_order.getReceive_wh_id();
				
				OrderFlat orderItem = new OrderFlat();
				for(int i=0;i<orderItemList.size();i++){
					orderItem = orderItemList.get(i);
					String prod_id = orderItem.getItem_prod_id();
					Product product = productDao.getProductById(prod_id);
					
					int item_quantity = orderItem.getItem_quantity();
					int original_prod_stock_in = product.getOrdered_stock_in();
					int original_prod_stock = product.getStock();
					product.setOrdered_stock_in(original_prod_stock_in-item_quantity);
					product.setStock(original_prod_stock+item_quantity);
					product.setUpdate(nowdate);
					product.setUpdateById(order.getUpdate_by_id());
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
					warehouse_stock_upsert.setUpdated_date(nowdate);
					warehouse_stock_upsert.setUpdated_by_user_id(order.getUpdate_by_id());
					
					upsertResponse = this.upsertWarehouseStock("upsert_stock_in", warehouse_stock_search, warehouse_stock_upsert);
					if(!upsertResponse.get("upsertStatus").toString().equals("success")){
						return upsertResponse.get("upsertStatus").toString();
					}else{
						StockHistory stock_history = new StockHistory();
						stock_history.setCreated_date(nowdate);
						stock_history.setCreated_by_user_id(order.getUpdate_by_id());
						stock_history.setStock_type_code("Stock In");
						stock_history.setStock_type_val("���");
						stock_history.setOriginal_stock((int)upsertResponse.get("original_stock"));
						stock_history.setStock_quantity(item_quantity);
						stock_history.setOrder_type_code("Purchase Order");
						stock_history.setOrder_type_val("�ɹ�����");
						stock_history.setOrder_id(order.getOrder_id());
						stock_history.setWarehouse_id(receive_wh_id);
						stock_history.setProduct_id(prod_id);
						User user = userDao.selectUserDetail(order.getUpdate_by_id());
						stock_history.setDescription("��"+user.getUserName()+"����˲ɹ�����");
						stockHistoryDao.addHistory(stock_history);
					}
				}//������ɲɹ�����ҵ�����
			}else if(order.getOrder_type_cd().equals("Purchase Return Order")){
				//��ʼ��ɲɹ��˻�����ҵ�����
				OrderFlat original_order = orderDao.getOrderById(order.getOrder_id());
				String delivery_wh_id = original_order.getDelivery_wh_id();
				
				OrderFlat orderItem = new OrderFlat();
				for(int i=0;i<orderItemList.size();i++){
					orderItem = orderItemList.get(i);
					String prod_id = orderItem.getItem_prod_id();
					Product product = productDao.getProductById(prod_id);
					
					int item_quantity = orderItem.getItem_quantity();
					int original_prod_stock_out = product.getOrdered_stock_out();
					int original_prod_stock = product.getStock();
					
					WarehouseStock warehouse_stock_search = new WarehouseStock();
					WarehouseStock warehouse_stock_upsert = new WarehouseStock();
					
					warehouse_stock_search.setProduct_id(prod_id);
					warehouse_stock_search.setWarehouse_id(delivery_wh_id);
					
					warehouse_stock_upsert.setOrdered_stock_out(item_quantity);
					warehouse_stock_upsert.setStock(item_quantity);
					warehouse_stock_upsert.setProduct_id(prod_id);
					warehouse_stock_upsert.setProduct_name(product.getName());
					warehouse_stock_upsert.setWarehouse_id(delivery_wh_id);
					warehouse_stock_upsert.setSku(product.getSku());
					warehouse_stock_upsert.setUpdated_date(nowdate);
					warehouse_stock_upsert.setUpdated_by_user_id(order.getUpdate_by_id());
					
					upsertResponse = this.upsertWarehouseStock("upsert_stock_out", warehouse_stock_search, warehouse_stock_upsert);
					if(!upsertResponse.get("upsertStatus").toString().equals("success")){
						return upsertResponse.get("upsertStatus").toString();
					}else{
						product.setOrdered_stock_out(original_prod_stock_out-item_quantity);
						product.setStock(original_prod_stock-item_quantity);
						product.setUpdate(nowdate);
						product.setUpdateById(order.getUpdate_by_id());
						productDao.updateProduct(product);
						
						StockHistory stock_history = new StockHistory();
						stock_history.setCreated_date(nowdate);
						stock_history.setCreated_by_user_id(order.getUpdate_by_id());
						stock_history.setStock_type_code("Stock Out");
						stock_history.setStock_type_val("����");
						stock_history.setStock_quantity(item_quantity);
						stock_history.setOriginal_stock((int)upsertResponse.get("original_stock"));
						stock_history.setOrder_type_code("Purchase Return Order");
						stock_history.setOrder_type_val("�ɹ��˻�����");
						stock_history.setOrder_id(order.getOrder_id());
						stock_history.setWarehouse_id(delivery_wh_id);
						stock_history.setProduct_id(prod_id);
						User user = userDao.selectUserDetail(order.getUpdate_by_id());
						stock_history.setDescription("��"+user.getUserName()+"����˲ɹ��˻�����");
						stockHistoryDao.addHistory(stock_history);
					}
				}//������ɲɹ��˻�����ҵ�����
			}
		}else if(order.getStatus_code().equals("submit - approved") || order.getStatus_code().equals("submitted")){//��ʼ�ύ���ݵ�ҵ��
				if(order.getOrder_type_cd().equals("Sales Order")){
					//OrderFlat original_order = orderDao.getOrderById(order.getOrder_id());
					//String delivery_wh_id = original_order.getDelivery_wh_id();
					if(orderItemList.size()==0){
						return "û���κ�������޷��ύ������";
					}
					//��ʼ�ύ���۵���ҵ�����
					for(int i=0;i<orderItemList.size();i++){
						OrderFlat orderItem = orderItemList.get(i);
						String prod_id = orderItem.getItem_prod_id();
						Product product = productDao.getProductById(prod_id);
						
						int item_quantity = orderItem.getItem_quantity();
						int prod_stock = product.getStock();
						int original_prod_stock_out = product.getOrdered_stock_out();
						if(item_quantity+original_prod_stock_out > prod_stock){
							return "������Ĳ�Ʒ"+product.getName()+"��治������������������";
						}else{
							WarehouseStock warehouse_stock_search = new WarehouseStock();
							WarehouseStock warehouse_stock_upsert = new WarehouseStock();
							
							warehouse_stock_search.setProduct_id(prod_id);
							warehouse_stock_search.setWarehouse_id(orderItem.getItem_warehouse_id());
							
							warehouse_stock_upsert.setOrdered_stock_out(item_quantity);
							warehouse_stock_upsert.setProduct_id(prod_id);
							warehouse_stock_upsert.setProduct_name(product.getName());
							warehouse_stock_upsert.setWarehouse_id(orderItem.getItem_warehouse_id());
							warehouse_stock_upsert.setWarehouse_name(orderItem.getItem_warehouse_name());
							warehouse_stock_upsert.setSku(product.getSku());
							warehouse_stock_upsert.setCreated_date(nowdate);
							warehouse_stock_upsert.setCreated_by_user_id(order.getUpdate_by_id());
							warehouse_stock_upsert.setUpdated_date(nowdate);
							warehouse_stock_upsert.setUpdated_by_user_id(order.getUpdate_by_id());
							
							upsertResponse = this.upsertWarehouseStock("upsert_ordered_stock_out", warehouse_stock_search, warehouse_stock_upsert);
							if(!upsertResponse.get("upsertStatus").toString().equals("success")){
								return upsertResponse.get("upsertStatus").toString();
							}else{
								product.setOrdered_stock_out(original_prod_stock_out+item_quantity);
								product.setUpdate(nowdate);
								product.setUpdateById(order.getUpdate_by_id());
								productDao.updateProduct(product);
								
								//Insert history record
						        OrderHistory order_history = new OrderHistory();
						        order_history.setOrder_id(order.getOrder_id());
						        order_history.setCreated_by_user_id(order.getUpdate_by_id());
						        order_history.setDescription("��"+order.getUpdate_by_name()+"�ύ�����۵���");
						        this.addHistory(order_history);
							}
						}//�����ύ���۵���ҵ�����
					}
				}else if(order.getOrder_type_cd().equals("Sales Return Order")){
					//��ʼ�ύ�����˻�����ҵ�����
//					OrderFlat original_order = orderDao.getOrderById(order.getOrder_id());
//					String receive_wh_id = original_order.getReceive_wh_id();
					if(orderItemList.size()==0){
						return "û���κ��˻���޷��ύ������";
					}
					OrderFlat orderItem = new OrderFlat();
					for(int i=0;i<orderItemList.size();i++){
						orderItem = orderItemList.get(i);
						String prod_id = orderItem.getItem_prod_id();
						Product product = productDao.getProductById(prod_id);
						
						int item_quantity = orderItem.getItem_quantity();
						int original_prod_stock_in = product.getOrdered_stock_in();
						product.setOrdered_stock_in(original_prod_stock_in+item_quantity);
						product.setUpdate(nowdate);
						product.setUpdateById(order.getUpdate_by_id());
						productDao.updateProduct(product);
						
						WarehouseStock warehouse_stock_search = new WarehouseStock();
						WarehouseStock warehouse_stock_upsert = new WarehouseStock();
						
						warehouse_stock_search.setProduct_id(prod_id);
						warehouse_stock_search.setWarehouse_id(orderItem.getItem_warehouse_id());
						
						warehouse_stock_upsert.setOrdered_stock_in(item_quantity);
						warehouse_stock_upsert.setProduct_id(prod_id);
						warehouse_stock_upsert.setProduct_name(product.getName());
						warehouse_stock_upsert.setWarehouse_id(orderItem.getItem_warehouse_id());
						warehouse_stock_upsert.setWarehouse_name(orderItem.getItem_warehouse_name());
						warehouse_stock_upsert.setSku(product.getSku());
						warehouse_stock_upsert.setCreated_date(nowdate);
						warehouse_stock_upsert.setCreated_by_user_id(order.getUpdate_by_id());
						warehouse_stock_upsert.setUpdated_date(nowdate);
						warehouse_stock_upsert.setUpdated_by_user_id(order.getUpdate_by_id());
						
						upsertResponse = this.upsertWarehouseStock("upsert_ordered_stock_in", warehouse_stock_search, warehouse_stock_upsert);
						if(!upsertResponse.get("upsertStatus").toString().equals("success")){
							return upsertResponse.get("upsertStatus").toString();
						}
					}//�����ύ�����˻�����ҵ�����
				}else if(order.getOrder_type_cd().equals("Purchase Order")){
					//��ʼ�ύ�ɹ�����ҵ�����
//					OrderFlat original_order = orderDao.getOrderById(order.getOrder_id());
//					String receive_wh_id = original_order.getReceive_wh_id();
					if(orderItemList.size()==0){
						return "û���κι�����޷��ύ������";
					}
					OrderFlat orderItem = new OrderFlat();
					for(int i=0;i<orderItemList.size();i++){
						orderItem = orderItemList.get(i);
						String prod_id = orderItem.getItem_prod_id();
						Product product = productDao.getProductById(prod_id);
						
						int item_quantity = orderItem.getItem_quantity();
						int original_prod_stock_in = product.getOrdered_stock_in();
						
						WarehouseStock warehouse_stock_search = new WarehouseStock();
						WarehouseStock warehouse_stock_upsert = new WarehouseStock();
						
						warehouse_stock_search.setProduct_id(prod_id);
						warehouse_stock_search.setWarehouse_id(orderItem.getItem_warehouse_id());
						
						warehouse_stock_upsert.setOrdered_stock_in(item_quantity);
						warehouse_stock_upsert.setProduct_id(prod_id);
						warehouse_stock_upsert.setProduct_name(product.getName());
						warehouse_stock_upsert.setWarehouse_id(orderItem.getItem_warehouse_id());
						warehouse_stock_upsert.setWarehouse_name(orderItem.getItem_warehouse_name());
						warehouse_stock_upsert.setSku(product.getSku());
						warehouse_stock_upsert.setCreated_date(nowdate);
						warehouse_stock_upsert.setCreated_by_user_id(order.getUpdate_by_id());
						warehouse_stock_upsert.setUpdated_date(nowdate);
						warehouse_stock_upsert.setUpdated_by_user_id(order.getUpdate_by_id());
						
						upsertResponse = this.upsertWarehouseStock("upsert_ordered_stock_in", warehouse_stock_search, warehouse_stock_upsert);
						if(!upsertResponse.get("upsertStatus").toString().equals("success")){
							return upsertResponse.get("upsertStatus").toString();
						}else{
							product.setOrdered_stock_in(original_prod_stock_in+item_quantity);
							product.setUpdate(nowdate);
							product.setUpdateById(order.getUpdate_by_id());
							productDao.updateProduct(product);
							
							//Insert history record
					        OrderHistory order_history = new OrderHistory();
					        order_history.setOrder_id(order.getOrder_id());
					        order_history.setCreated_by_user_id(order.getUpdate_by_id());
					        order_history.setDescription("��"+order.getUpdate_by_name()+"�ύ�˲ɹ�����");
					        this.addHistory(order_history);
						}
					}//�����ύ�ɹ�����ҵ�����
				}else if(order.getOrder_type_cd().equals("Purchase Return Order")){
					//��ʼ�ύ�ɹ��˻�����ҵ�����
//					OrderFlat original_order = orderDao.getOrderById(order.getOrder_id());
//					String delivery_wh_id = original_order.getDelivery_wh_id();
					if(orderItemList.size()==0){
						return "û���κβɹ��˻���޷��ύ������";
					}
					OrderFlat orderItem = new OrderFlat();
					for(int i=0;i<orderItemList.size();i++){
						orderItem = orderItemList.get(i);
						String prod_id = orderItem.getItem_prod_id();
						Product product = productDao.getProductById(prod_id);
						
						int item_quantity = orderItem.getItem_quantity();
						int original_prod_stock_out = product.getOrdered_stock_out();
						
						WarehouseStock warehouse_stock_search = new WarehouseStock();
						WarehouseStock warehouse_stock_upsert = new WarehouseStock();
						
						warehouse_stock_search.setProduct_id(prod_id);
						warehouse_stock_search.setWarehouse_id(orderItem.getItem_warehouse_id());
						
						warehouse_stock_upsert.setOrdered_stock_out(item_quantity);
						warehouse_stock_upsert.setProduct_id(prod_id);
						warehouse_stock_upsert.setProduct_name(product.getName());
						warehouse_stock_upsert.setWarehouse_id(orderItem.getItem_warehouse_id());
						warehouse_stock_upsert.setWarehouse_name(orderItem.getItem_warehouse_name());
						warehouse_stock_upsert.setSku(product.getSku());
						warehouse_stock_upsert.setCreated_date(nowdate);
						warehouse_stock_upsert.setCreated_by_user_id(order.getUpdate_by_id());
						warehouse_stock_upsert.setUpdated_date(nowdate);
						warehouse_stock_upsert.setUpdated_by_user_id(order.getUpdate_by_id());
						
						upsertResponse = this.upsertWarehouseStock("upsert_ordered_stock_out", warehouse_stock_search, warehouse_stock_upsert);
						if(!upsertResponse.get("upsertStatus").toString().equals("success")){
							return upsertResponse.get("upsertStatus").toString();
						}else{
							product.setOrdered_stock_out(original_prod_stock_out+item_quantity);
							product.setUpdate(nowdate);
							product.setUpdateById(order.getUpdate_by_id());
							productDao.updateProduct(product);
						}
					}//�����ύ�ɹ��˻�����ҵ�����
				}
		}else if(order.getStatus_code().equals("cancelled") || order.getStatus_code().equals("submit-rejected")){//��ʼȡ�����ݵ�ҵ��
			if(order.getOrder_type_cd().equals("Sales Order")){
				//��ʼȡ�����۵���ҵ�����
//				OrderFlat original_order = orderDao.getOrderById(order.getOrder_id());
//				String delivery_wh_id = original_order.getDelivery_wh_id();
				
				OrderFlat orderItem = new OrderFlat();
				for(int i=0;i<orderItemList.size();i++){
					orderItem = orderItemList.get(i);
					String prod_id = orderItem.getItem_prod_id();
					Product product = productDao.getProductById(prod_id);
					
					int item_quantity = -orderItem.getItem_quantity();//Ϊ����
					int original_prod_stock_out = product.getOrdered_stock_out();
					
					WarehouseStock warehouse_stock_search = new WarehouseStock();
					WarehouseStock warehouse_stock_upsert = new WarehouseStock();
					
					warehouse_stock_search.setProduct_id(prod_id);
					warehouse_stock_search.setWarehouse_id(orderItem.getItem_warehouse_id());
					
					warehouse_stock_upsert.setOrdered_stock_out(item_quantity);
					warehouse_stock_upsert.setProduct_id(prod_id);
					warehouse_stock_upsert.setProduct_name(product.getName());
					warehouse_stock_upsert.setWarehouse_id(orderItem.getItem_warehouse_id());
					warehouse_stock_upsert.setWarehouse_name(orderItem.getItem_warehouse_name());
					warehouse_stock_upsert.setSku(product.getSku());
					warehouse_stock_upsert.setCreated_date(nowdate);
					warehouse_stock_upsert.setCreated_by_user_id(order.getUpdate_by_id());
					warehouse_stock_upsert.setUpdated_date(nowdate);
					warehouse_stock_upsert.setUpdated_by_user_id(order.getUpdate_by_id());
					
					upsertResponse = this.upsertWarehouseStock("upsert_ordered_stock_out", warehouse_stock_search, warehouse_stock_upsert);
					if(!upsertResponse.get("upsertStatus").toString().equals("success")){
						return upsertResponse.get("upsertStatus").toString();
					}else{
						product.setOrdered_stock_out(original_prod_stock_out+item_quantity);
						product.setUpdate(nowdate);
						product.setUpdateById(order.getUpdate_by_id());
						productDao.updateProduct(product);
					}
				}//����ȡ�����۵���ҵ�����
				
			}else if(order.getOrder_type_cd().equals("Sales Return Order")){
				//��ʼȡ�������˻�����ҵ�����
//				OrderFlat original_order = orderDao.getOrderById(order.getOrder_id());
//				String receive_wh_id = original_order.getReceive_wh_id();
				if(orderItemList.size()==0){
					return "û���κ��˻���޷��ύ������";
				}
				OrderFlat orderItem = new OrderFlat();
				for(int i=0;i<orderItemList.size();i++){
					orderItem = orderItemList.get(i);
					String prod_id = orderItem.getItem_prod_id();
					Product product = productDao.getProductById(prod_id);
					
					int item_quantity = -orderItem.getItem_quantity(); //Ϊ����
					int original_prod_stock_in = product.getOrdered_stock_in();
					product.setOrdered_stock_in(original_prod_stock_in+item_quantity);
					product.setUpdate(nowdate);
					product.setUpdateById(order.getUpdate_by_id());
					productDao.updateProduct(product);
					
					WarehouseStock warehouse_stock_search = new WarehouseStock();
					WarehouseStock warehouse_stock_upsert = new WarehouseStock();
					
					warehouse_stock_search.setProduct_id(prod_id);
					warehouse_stock_search.setWarehouse_id(orderItem.getItem_warehouse_id());
					
					warehouse_stock_upsert.setOrdered_stock_in(item_quantity);
					warehouse_stock_upsert.setProduct_id(prod_id);
					warehouse_stock_upsert.setProduct_name(product.getName());
					warehouse_stock_upsert.setWarehouse_id(orderItem.getItem_warehouse_id());
					warehouse_stock_upsert.setWarehouse_name(orderItem.getItem_warehouse_name());
					warehouse_stock_upsert.setSku(product.getSku());
					warehouse_stock_upsert.setCreated_date(nowdate);
					warehouse_stock_upsert.setCreated_by_user_id(order.getUpdate_by_id());
					warehouse_stock_upsert.setUpdated_date(nowdate);
					warehouse_stock_upsert.setUpdated_by_user_id(order.getUpdate_by_id());
					
					upsertResponse = this.upsertWarehouseStock("upsert_ordered_stock_in", warehouse_stock_search, warehouse_stock_upsert);
					if(!upsertResponse.get("upsertStatus").toString().equals("success")){
						return upsertResponse.get("upsertStatus").toString();
					}
				}//����ȡ�������˻�����ҵ�����
			}else if(order.getOrder_type_cd().equals("Purchase Order")){
				//��ʼȡ���ɹ�����ҵ�����
				OrderFlat original_order = orderDao.getOrderById(order.getOrder_id());
				if(original_order.getStatus_code().equals("submitted")){
					
					OrderFlat orderItem = new OrderFlat();
					for(int i=0;i<orderItemList.size();i++){
						orderItem = orderItemList.get(i);
						String prod_id = orderItem.getItem_prod_id();
						Product product = productDao.getProductById(prod_id);
						int item_quantity = -orderItem.getItem_quantity();//Ϊ����
						int original_prod_stock_in = product.getOrdered_stock_in();
						
						WarehouseStock warehouse_stock_search = new WarehouseStock();
						WarehouseStock warehouse_stock_upsert = new WarehouseStock();
						
						warehouse_stock_search.setProduct_id(prod_id);
						warehouse_stock_search.setWarehouse_id(orderItem.getItem_warehouse_id());
						
						warehouse_stock_upsert.setOrdered_stock_in(item_quantity);
						warehouse_stock_upsert.setProduct_id(prod_id);
						warehouse_stock_upsert.setProduct_name(product.getName());
						warehouse_stock_upsert.setWarehouse_id(orderItem.getItem_warehouse_id());
						warehouse_stock_upsert.setWarehouse_name(orderItem.getItem_warehouse_name());
						warehouse_stock_upsert.setSku(product.getSku());
						warehouse_stock_upsert.setCreated_date(nowdate);
						warehouse_stock_upsert.setCreated_by_user_id(order.getUpdate_by_id());
						warehouse_stock_upsert.setUpdated_date(nowdate);
						warehouse_stock_upsert.setUpdated_by_user_id(order.getUpdate_by_id());
						
						upsertResponse = this.upsertWarehouseStock("upsert_ordered_stock_in", warehouse_stock_search, warehouse_stock_upsert);
						if(!upsertResponse.get("upsertStatus").toString().equals("success")){
							return upsertResponse.get("upsertStatus").toString();
						}else{
							product.setOrdered_stock_in(original_prod_stock_in+item_quantity);
							product.setUpdate(nowdate);
							product.setUpdateById(order.getUpdate_by_id());
							productDao.updateProduct(product);
						}
					}
				}//����ȡ���ɹ�����ҵ�����
			}else if(order.getOrder_type_cd().equals("Purchase Return Order")){
				//��ʼȡ���ɹ��˻�����ҵ�����
				OrderFlat orderItem = new OrderFlat();
				for(int i=0;i<orderItemList.size();i++){
					orderItem = orderItemList.get(i);
					String prod_id = orderItem.getItem_prod_id();
					Product product = productDao.getProductById(prod_id);
					
					int item_quantity = -orderItem.getItem_quantity();//Ϊ����
					int original_prod_stock_out = product.getOrdered_stock_out();
					
					WarehouseStock warehouse_stock_search = new WarehouseStock();
					WarehouseStock warehouse_stock_upsert = new WarehouseStock();
					
					warehouse_stock_search.setProduct_id(prod_id);
					warehouse_stock_search.setWarehouse_id(orderItem.getItem_warehouse_id());
					
					warehouse_stock_upsert.setOrdered_stock_out(item_quantity);
					warehouse_stock_upsert.setProduct_id(prod_id);
					warehouse_stock_upsert.setProduct_name(product.getName());
					warehouse_stock_upsert.setWarehouse_id(orderItem.getItem_warehouse_id());
					warehouse_stock_upsert.setWarehouse_name(orderItem.getItem_warehouse_name());
					warehouse_stock_upsert.setSku(product.getSku());
					warehouse_stock_upsert.setCreated_date(nowdate);
					warehouse_stock_upsert.setCreated_by_user_id(order.getUpdate_by_id());
					warehouse_stock_upsert.setUpdated_date(nowdate);
					warehouse_stock_upsert.setUpdated_by_user_id(order.getUpdate_by_id());
					
					upsertResponse = this.upsertWarehouseStock("upsert_ordered_stock_out", warehouse_stock_search, warehouse_stock_upsert);
					if(!upsertResponse.get("upsertStatus").toString().equals("success")){
						return upsertResponse.get("upsertStatus").toString();
					}else{
						product.setOrdered_stock_out(original_prod_stock_out+item_quantity);
						product.setUpdate(nowdate);
						product.setUpdateById(order.getUpdate_by_id());
						productDao.updateProduct(product);
					}
				}//����ȡ�����˻�����ҵ�����
			}
		}else if(order.getStatus_code().equals("scanned")){
			//Insert history record
	        OrderHistory order_history = new OrderHistory();
	        order_history.setOrder_id(order.getOrder_id());
	        order_history.setCreated_by_user_id(order.getUpdate_by_id());
	        order_history.setDescription("��"+order.getUpdate_by_name()+"����ɨ����ش���");
	        this.addHistory(order_history);
		}else if(order.getStatus_code().equals("archived")){
			//Insert history record
	        OrderHistory order_history = new OrderHistory();
	        order_history.setOrder_id(order.getOrder_id());
	        order_history.setCreated_by_user_id(order.getUpdate_by_id());
	        order_history.setDescription("��"+order.getUpdate_by_name()+"ԭ���뵵��");
	        this.addHistory(order_history);
		}
		
		if(orderDao.updateOrderStatus(order)){
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
			//���»��������¼
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
		}else if( upsert_type.equals("upsert_ordered_stock_out")){
			//���»����������¼
			int item_quantity = warehouse_stock_upsert.getOrdered_stock_out();
			List<WarehouseStock> warehouse_stock_list = warehouseStockDao.getStockInfos(warehouse_stock_search, null, null, null, null);
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
				responseMap.put("upsertStatus",warehouse_stock_upsert.getWarehouse_name()+"û��"+warehouse_stock_upsert.getProduct_name()+"�Ŀ�棬������������");
				return responseMap;
			}
		}else if( upsert_type.equals("upsert_stock_out")){
			//���»��������¼
			int item_quantity = warehouse_stock_upsert.getStock();
			List<WarehouseStock> warehouse_stock_list = warehouseStockDao.getStockInfos(warehouse_stock_search, null, null, null, null);
			if(warehouse_stock_list.size() > 0){
				WarehouseStock warehouse_stock_original = warehouse_stock_list.get(0);
				int original_stock_out = warehouse_stock_original.getOrdered_stock_out();
				int original_stock = warehouse_stock_original.getStock();
				if( original_stock < item_quantity){
					responseMap.put("upsertStatus",warehouse_stock_upsert.getProduct_name() + "�Ĵ����������Ѿ�����"+warehouse_stock_upsert.getWarehouse_name()+"�Ŀ�棬����������");
					return responseMap;
				}
				warehouse_stock_upsert.setOrdered_stock_out(original_stock_out - item_quantity);
				warehouse_stock_upsert.setStock(original_stock - item_quantity);
				warehouse_stock_upsert.setId(warehouse_stock_original.getId());
				
				if(warehouseStockDao.updateStockInfoById(warehouse_stock_upsert)){
					responseMap.put("original_stock", original_stock);
					responseMap.put("upsertStatus","success");
					return responseMap;
				}else{
					responseMap.put("upsertStatus","���³����¼ʧ�ܣ�");
					return responseMap;
				}
			}else{
				responseMap.put("upsertStatus",warehouse_stock_upsert.getWarehouse_name()+"û��"+warehouse_stock_upsert.getProduct_name()+"�Ŀ�棬������������");
				return responseMap;
			}
		}
		
		return responseMap;
	}
	
	@Override
	public boolean updateOrderAmountsById(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order.setUpdate_date(nowdate);
		return orderDao.updateOrderAmounts(order);
	}

	@Override
	public List<OrderFlat> getAllOrders(String userid) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.getAllOrders(userid);
	}

	@Override
	public int getOrdersCount(String userid) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.getOrdersCount(userid);
	}

	@Override
	@Transactional
	public boolean updateOrderAddrById(OrderFlat order, String addr_type)
			throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order.setUpdate_date(nowdate);
		if(addr_type.equals("bill-address")){
			
			OrderHistory order_history = new OrderHistory();
	        order_history.setOrder_id(order.getOrder_id());
	        order_history.setCreated_by_user_id(order.getUpdate_by_id());
	        order_history.setDescription("��"+order.getUpdate_by_name()+"�������˵���ַ");
	        this.addHistory(order_history);
	        
			return orderDao.updateOrderBillAddr(order);
		}else if(addr_type.equals("ship-address")){
			
			OrderHistory order_history = new OrderHistory();
	        order_history.setOrder_id(order.getOrder_id());
	        order_history.setCreated_by_user_id(order.getUpdate_by_id());
	        order_history.setDescription("��"+order.getUpdate_by_name()+"�������ջ���ַ");
	        this.addHistory(order_history);
	        
			return orderDao.updateOrderShipAddr(order);
		}else if(addr_type.equals("area")){
			
			OrderHistory order_history = new OrderHistory();
	        order_history.setOrder_id(order.getOrder_id());
	        order_history.setCreated_by_user_id(order.getUpdate_by_id());
	        order_history.setDescription("��"+order.getUpdate_by_name()+"����������");
	        this.addHistory(order_history);
	        
			return orderDao.updateOrderArea(order);
		}else
			return false;
	}

	@Override
	@Transactional
	public String addPurchaseOrder(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order.setCreated_date(nowdate);
		order.setUpdate_date(nowdate);
		order.setPurchase_date(nowdate);
		order.setOwner_viewed(false);
		order.setStatus_code("new");
		order.setStatus_value(listOfValueDao.getValueByCodeName("Order Status", "new"));
		order.setOrder_type(listOfValueDao.getValueByCodeName("Order Type", "Purchase Order"));
		order.setCurrency_val(listOfValueDao.getValueByCodeName("Currency", order.getCurrency_cd()));
		order.setOrder_type_cd("Purchase Order");
		Account supplier = accountDao.selectAtDe(new Object[]{order.getSupplier_id()});
		order.setShip_addr_id(supplier.getPrimaryAddrId());
		order.setBill_addr_id(supplier.getPrimaryAddrId());
		//Generate purchase order number 
        SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddhhmmss");     
        timeformat.setLenient(false);   
        String order_number = order.getCreated_by_id()+"-" +timeformat.format(date); 
        order.setOrder_number(order_number);
        
        String new_order_id = orderDao.addOrder(order);
        OrderHistory order_history = new OrderHistory();
        order_history.setOrder_id(new_order_id);
        order_history.setCreated_by_user_id(order.getCreated_by_id());
        order_history.setDescription("��"+order.getCreated_by_name()+"����");
        this.addHistory(order_history);
		
		return new_order_id;
	}

	@Override
	@Transactional
	public String addPurchaseReturnOrder(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order.setCreated_date(nowdate);
		order.setUpdate_date(nowdate);
		order.setPurchase_date(nowdate);
		order.setOwner_viewed(false);
		order.setStatus_code("new");
		order.setStatus_value("�½�");
		order.setOrder_type("�ɹ��˻�����");
		order.setOrder_type_cd("Purchase Return Order");
		Account receive_account = accountDao.selectAtDe(new Object[]{order.getSupplier_id()});
		order.setShip_addr_id(receive_account.getPrimaryAddrId());
		order.setBill_addr_id(receive_account.getPrimaryAddrId());
		//Generate purchase return order number 
        SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddhhmmss");     
        timeformat.setLenient(false);   
        String order_number = order.getCreated_by_id()+"-" +timeformat.format(date); 
        order.setOrder_number(order_number);
        
        String new_order_id = orderDao.addOrder(order);
        OrderHistory order_history = new OrderHistory();
        order_history.setOrder_id(new_order_id);
        order_history.setCreated_by_user_id(order.getCreated_by_id());
        order_history.setDescription("��"+order.getCreated_by_name()+"����");
        this.addHistory(order_history);
		
		return new_order_id;
	}

	@Override
	@Transactional
	public String addSalesOrder(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order.setCreated_date(nowdate);
		order.setUpdate_date(nowdate);
		order.setPurchase_date(nowdate);
		order.setOwner_viewed(false);
		order.setStatus_code("new");
		order.setStatus_value(listOfValueDao.getValueByCodeName("Order Status", "new"));
		order.setOrder_type(listOfValueDao.getValueByCodeName("Order Type", "Sales Order"));
		order.setOrder_type_cd("Sales Order");
		order.setCurrency_val(listOfValueDao.getValueByCodeName("Currency", order.getCurrency_cd()));
		Account customer = accountDao.selectAtDe(new Object[]{order.getAccount_id()});
		order.setShip_addr_id(customer.getPrimaryAddrId());
		order.setBill_addr_id(customer.getPrimaryAddrId());
		//Generate sales order number 
        SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddhhmmss");     
        timeformat.setLenient(false);   
        String order_number = order.getCreated_by_id()+"-" +timeformat.format(date); 
        order.setOrder_number(order_number);
		
        String new_order_id = orderDao.addOrder(order);
        OrderHistory order_history = new OrderHistory();
        order_history.setOrder_id(new_order_id);
        order_history.setCreated_by_user_id(order.getCreated_by_id());
        order_history.setDescription("��"+order.getCreated_by_name()+"����");
        this.addHistory(order_history);
		
		return new_order_id;
	}

	@Override
	@Transactional
	public String addSalesReturnOrder(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order.setCreated_date(nowdate);
		order.setUpdate_date(nowdate);
		order.setPurchase_date(nowdate);
		order.setOwner_viewed(false);
		order.setStatus_code("new");
		order.setStatus_value("�½�");
		order.setOrder_type("�����˻�����");
		order.setOrder_type_cd("Sales Return Order");
		Account customer = accountDao.selectAtDe(new Object[]{order.getAccount_id()});
		order.setShip_addr_id(customer.getPrimaryAddrId());
		order.setBill_addr_id(customer.getPrimaryAddrId());
		//Generate sales return order number 
        SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddhhmmss");     
        timeformat.setLenient(false);   
        String order_number = order.getCreated_by_id()+"-" +timeformat.format(date); 
        order.setOrder_number(order_number);
		
        String new_order_id = orderDao.addOrder(order);
        OrderHistory order_history = new OrderHistory();
        order_history.setOrder_id(new_order_id);
        order_history.setCreated_by_user_id(order.getCreated_by_id());
        order_history.setDescription("��"+order.getCreated_by_name()+"����");
        this.addHistory(order_history);
		
		return new_order_id;
	}

	@Override
	public boolean addHistory(OrderHistory order_history) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdate = new Timestamp(date.getTime());
		order_history.setCreated_date(nowdate);
		return orderHistoryDao.addHistory(order_history);
	}

	@Override
	public boolean delHistoryById(String id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<OrderHistory> getAllHistories() throws Exception {
		// TODO Auto-generated method stub
		return orderHistoryDao.getAllHistories();
	}

	@Override
	public OrderHistory getHistoryById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderHistory> getHistories(OrderHistory order_history,
			String start, String limit, String sortColumn, String sortDir)
			throws Exception {
		// TODO Auto-generated method stub
		return orderHistoryDao.getHistories(order_history, start, limit, sortColumn, sortDir);
	}

	@Override
	public List<OrderFlat> getOrdersForApprover(OrderFlat order,String approver_id,
			String start, String limit, String sortColumn, String sortDir)
			throws Exception {
		// TODO Auto-generated method stub
		return orderDao.getOrdersForApprover(order,approver_id, start, limit, sortColumn, sortDir);
	}

	@Override
	@Transactional
	public boolean generateReq(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();       
		Timestamp nowdatetime = new Timestamp(date.getTime());
		java.sql.Date nowdate = new java.sql.Date(date.getTime());
		OrderFlat this_order = orderDao.getOrderById(order.getOrder_id());
		List<OrderFlat> this_order_items = orderDao.getItemsByOrderId(order.getOrder_id());
		List<String> warehouse_id_list = orderDao.getItemWarehouseIds(order.getOrder_id());
		
		if(this_order.getOrder_type_cd().equals("Purchase Order")){
			//�Ӳɹ���������ⵥ
			for(int i=0;i<warehouse_id_list.size();i++){
				RequisitionFlat requisition = new RequisitionFlat();
				requisition.setCreated_date(nowdatetime);
				requisition.setCreated_by_id(order.getUpdate_by_id());
				requisition.setUpdate_date(nowdatetime);
				requisition.setUpdate_by_id(order.getUpdate_by_id());
				requisition.setOrder_id(order.getOrder_id());
				requisition.setRequisition_comment("�����ɲɹ��������ɵģ�");
				requisition.setCurrency_cd(this_order.getCurrency_cd());
				requisition.setCurrency_val(this_order.getCurrency_val());
				requisition.setStatus_code("new");
				requisition.setStatus_value(listOfValueDao.getValueByCodeName("Requisition Status", "new"));
				requisition.setRequisition_type_cd("Stock In Requisition");
				requisition.setRequisition_type(listOfValueDao.getValueByCodeName("Requisition Type", "Stock In Requisition"));
				requisition.setOwner_id(order.getUpdate_by_id());
				requisition.setReceive_wh_id(warehouse_id_list.get(i));
				requisition.setReceive_date(nowdate);
				requisition.setRequisition_manual_number("SIO-Generated");
				requisition.setRequisition_subtype_cd("Purchase In");
				requisition.setRequisition_subtype_val(listOfValueDao.getValueByCodeName("Stock In Type", "Purchase In"));
				
				//Generate unique requisition number
				SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddhhmmss");     
		        timeformat.setLenient(false);   
		        String req_number = requisition.getCreated_by_id()+"-" +timeformat.format(date) + String.valueOf(i); 
		        requisition.setRequisition_number(req_number);
		        //Insert history record
		        String new_req_id = requisitionDao.addRequisition(requisition);
		        RequisitionHistory req_history = new RequisitionHistory();
		        req_history.setRequisition_id(new_req_id);
		        req_history.setCreated_date(nowdatetime);
		        req_history.setCreated_by_user_id(requisition.getCreated_by_id());
		        req_history.setDescription("��"+order.getUpdate_by_name()+"�Ӳɹ��������ɣ�");
		        requisitionHistoryDao.addHistory(req_history);
		        
		        //Generate requisition items
		        for(int j=0;j<this_order_items.size();j++){
		        	if(this_order_items.get(j).getItem_warehouse_id().equals(warehouse_id_list.get(i))){
		        		RequisitionFlat req_item = new RequisitionFlat();
		        		req_item.setItem_created_date(nowdatetime);
		        		req_item.setItem_created_by_id(order.getUpdate_by_id());
		        		req_item.setItem_update_date(nowdatetime);
		        		req_item.setItem_update_by_id(order.getUpdate_by_id());
		        		req_item.setRequisition_id(new_req_id);
		        		req_item.setItem_prod_id(this_order_items.get(j).getItem_prod_id());
		        		req_item.setItem_base_price(this_order_items.get(j).getItem_base_price());
		        		req_item.setItem_due_price(this_order_items.get(j).getItem_due_amount() / this_order_items.get(j).getItem_quantity());
		        		req_item.setItem_base_amount(this_order_items.get(j).getItem_base_price() * this_order_items.get(j).getItem_quantity());
		        		req_item.setItem_quantity(this_order_items.get(j).getItem_quantity());
		        		req_item.setItem_due_amount(this_order_items.get(j).getItem_due_amount());
		        		req_item.setItem_contract_id(this_order_items.get(j).getItem_contract_id());
		        		req_item.setItem_comment(this_order_items.get(j).getItem_comment());
		        		requisitionDao.addRequisitionItem(req_item);
		        	}
		        }
			}
			order.setStatus_code("processing");
			order.setStatus_value(listOfValueDao.getValueByCodeName("Order Status", "processing"));
			orderDao.updateOrderStatus(order);
			OrderHistory order_history = new OrderHistory();
	        order_history.setOrder_id(this_order.getOrder_id());
	        order_history.setCreated_by_user_id(order.getCreated_by_id());
	        order_history.setDescription("��"+order.getUpdate_by_name()+"��������ⵥ��");
	        this.addHistory(order_history);
		}else if(this_order.getOrder_type_cd().equals("Sales Order")){
			//�����۵����ɳ��ⵥ
			for(int i=0;i<warehouse_id_list.size();i++){
				RequisitionFlat requisition = new RequisitionFlat();
				requisition.setCreated_date(nowdatetime);
				requisition.setCreated_by_id(order.getUpdate_by_id());
				requisition.setUpdate_date(nowdatetime);
				requisition.setUpdate_by_id(order.getUpdate_by_id());
				requisition.setOrder_id(order.getOrder_id());
				requisition.setRequisition_comment("���������۶������ɵģ�");
				requisition.setAccount_id(this_order.getAccount_id());
				requisition.setCurrency_cd(this_order.getCurrency_cd());
				requisition.setCurrency_val(this_order.getCurrency_val());
				requisition.setStatus_code("new");
				requisition.setStatus_value(listOfValueDao.getValueByCodeName("Requisition Status", "new"));
				requisition.setRequisition_type_cd("Stock Out Requisition");
				requisition.setRequisition_type(listOfValueDao.getValueByCodeName("Requisition Type", "Stock Out Requisition"));
				requisition.setOwner_id(order.getUpdate_by_id());
				requisition.setDelivery_wh_id(warehouse_id_list.get(i));
				requisition.setDelivery_date(nowdate);
				requisition.setRequisition_manual_number("SOO-Generated");
				requisition.setRequisition_subtype_cd("Sales Out");
				requisition.setRequisition_subtype_val(listOfValueDao.getValueByCodeName("Stock Out Type", "Sales Out"));
				
				//Generate unique requisition number
				SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddhhmmss");     
		        timeformat.setLenient(false);   
		        String req_number = requisition.getCreated_by_id()+"-" +timeformat.format(date) + String.valueOf(i); 
		        requisition.setRequisition_number(req_number);
		        //Insert history record
		        String new_req_id = requisitionDao.addRequisition(requisition);
		        RequisitionHistory req_history = new RequisitionHistory();
		        req_history.setRequisition_id(new_req_id);
		        req_history.setCreated_date(nowdatetime);
		        req_history.setCreated_by_user_id(requisition.getCreated_by_id());
		        req_history.setDescription("��"+order.getUpdate_by_name()+"�����۶������ɣ�");
		        requisitionHistoryDao.addHistory(req_history);
		        
		        //Generate requisition items
		        for(int j=0;j<this_order_items.size();j++){
		        	if(this_order_items.get(j).getItem_warehouse_id().equals(warehouse_id_list.get(i))){
		        		RequisitionFlat req_item = new RequisitionFlat();
		        		req_item.setItem_created_date(nowdatetime);
		        		req_item.setItem_created_by_id(order.getUpdate_by_id());
		        		req_item.setItem_update_date(nowdatetime);
		        		req_item.setItem_update_by_id(order.getUpdate_by_id());
		        		req_item.setRequisition_id(new_req_id);
		        		req_item.setItem_prod_id(this_order_items.get(j).getItem_prod_id());
		        		req_item.setItem_base_price(this_order_items.get(j).getItem_base_price());
		        		req_item.setItem_due_price(this_order_items.get(j).getItem_due_amount() / this_order_items.get(j).getItem_quantity());
		        		req_item.setItem_base_amount(this_order_items.get(j).getItem_base_price() * this_order_items.get(j).getItem_quantity());
		        		req_item.setItem_quantity(this_order_items.get(j).getItem_quantity());
		        		req_item.setItem_due_amount(this_order_items.get(j).getItem_due_amount());
		        		req_item.setItem_contract_id(this_order_items.get(j).getItem_contract_id());
		        		req_item.setItem_comment(this_order_items.get(j).getItem_comment());
		        		requisitionDao.addRequisitionItem(req_item);
		        	}
		        }
			}
			order.setStatus_code("processing");
			order.setStatus_value(listOfValueDao.getValueByCodeName("Order Status", "processing"));
			orderDao.updateOrderStatus(order);
			OrderHistory order_history = new OrderHistory();
	        order_history.setOrder_id(this_order.getOrder_id());
	        order_history.setCreated_by_user_id(order.getCreated_by_id());
	        order_history.setDescription("��"+order.getUpdate_by_name()+"�����˳��ⵥ��");
	        this.addHistory(order_history);
			
		}
		
		
		return true;
	}

	@Override
	public List<ApprovalHistory> getApprovalHistories(
			ApprovalHistory approval_history, String start, String limit,
			String sortColumn, String sortDir) throws Exception {
		// TODO Auto-generated method stub
		return approvalHistoryDao.getHistories(approval_history, start, limit, sortColumn, sortDir);
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

	@Qualifier("authService")
	@Autowired
	private AuthService authService;
	
	@Override
	@Transactional
	public String updateOrderStatusByIdFacade(OrderFlat order) throws Exception {
		// TODO Auto-generated method stub
		try{
			String status_code = order.getStatus_code();
			String type_code = order.getOrder_type_cd();
			String success_msg = "";
			if(status_code.equals("hold")){
				success_msg = "���ö���";
			}else if(status_code.equals("cancelled")){
				success_msg = "ȡ������";
			}else if(status_code.equals("completed")){
				success_msg = "�رն���";
			}else if(status_code.equals("submitted")){
				success_msg = "�ύ����";
			}else if(status_code.equals("submit - approved")){
				success_msg = "�ύ��˶���";
			}else if(status_code.equals("submit - rejected")){
				success_msg = "�ύ�ܾ�����";
			}else if(status_code.equals("reopen")){
				success_msg = "�ؿ�����";
			}else if(status_code.equals("scanned")){
				success_msg = "��������ɨ����ش�";
			}else if(status_code.equals("archived")){
				success_msg = "����ԭ���뵵";
			}
			String ActionType = "";
			ApprovalHistory approvalHistory = new ApprovalHistory();
			switch(status_code){
			case "submitted":
				ActionType = "Submit";
				approvalHistory.setOrder_id(order.getOrder_id());
				approvalHistory.setCreated_by_user_id(order.getUpdate_by_id());
				approvalHistory.setDescription("��"+order.getUpdate_by_name()+"�ύ�˶�����");
				this.addApprovalHistory(approvalHistory);
				break;
			case "submit - approved":
				ActionType = "Approve";
				approvalHistory.setOrder_id(order.getOrder_id());
				approvalHistory.setCreated_by_user_id(order.getUpdate_by_id());
				approvalHistory.setDescription("��"+order.getUpdate_by_name()+"���ͨ���˶�����");
				this.addApprovalHistory(approvalHistory);
				break;
			case "submit - rejected":
				ActionType = "Reject";
				approvalHistory.setOrder_id(order.getOrder_id());
				approvalHistory.setCreated_by_user_id(order.getUpdate_by_id());
				approvalHistory.setDescription("��"+order.getUpdate_by_name()+"�ܾ��˶�����");
				this.addApprovalHistory(approvalHistory);
				break;
			default:
				ActionType = "Not Defined";
				break;
			}
			
			String objectName = type_code;
			switch(authService.ApprovalExecute(order.getOrder_id(), objectName, ActionType, order.getUpdate_by_id())){
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
			
			order.setStatus_code(status_code);
			order.setStatus_value(listOfValueDao.getValueByCodeName("Order Status", status_code));
			order.setOrder_type_cd(type_code);
			String update_status = this.updateOrderStatusById(order);
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
		file.setUpload_source("Order Attachment Uploader");
		file.setUpdate_date(nowdate);
		file.setType("Order Attachment");
		return fileDao.addFile(file);
	}

	@Override
	public List<Attachment> getAttachmentsByOrderId(String id) throws Exception {
		// TODO Auto-generated method stub
		return fileDao.getFileByOrderId(id);
	}

	@Override
	public boolean delAttachmentById(String id) throws Exception {
		// TODO Auto-generated method stub
		Attachment attachment = fileDao.getFileById(id);
		String realPath = attachment.getPath();
		String fileName = attachment.getOriginal_filename();
		
		String parsedRealPath = realPath.replace("\\","\\\\");
		attachment.setPath(parsedRealPath);
		
		List<Attachment> existAttachments = fileDao.getFiles(attachment, null, null, null, null);
		if(existAttachments.size()==1){
			File image = new File(realPath+"\\"+fileName);
			image.delete();
		}
		
		return fileDao.delFileById(id);
	}

	@Override
	public List<OrderFlat> getRecentOrders(String userid) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.getRecentOrders(userid);
	}

	@Override
	public List<OrderReqProdCounts> getOrderProdCounts(String order_id)
			throws Exception {
		// TODO Auto-generated method stub
		return orderDao.getOrderProdCounts(order_id);
	}

}
