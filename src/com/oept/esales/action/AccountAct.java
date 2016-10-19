package com.oept.esales.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oept.esales.model.Account;
import com.oept.esales.model.Address;
import com.oept.esales.model.ApprovalDetail;
import com.oept.esales.model.ApprovalHistory;
import com.oept.esales.model.ApprovalStep;
import com.oept.esales.model.Category;
import com.oept.esales.model.Product;
import com.oept.esales.model.User;
import com.oept.esales.service.AccountService;
import com.oept.esales.service.AuthService;
import com.oept.esales.service.UserService;
/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/12/21
 * Description: Sales Account Info Manage
 * Copyright (c) 2015 OEPT inc. All rights reserved.
 */
@Controller
@RequestMapping(value="/account")
public class AccountAct {
	private static final Log logger = LogFactory.getLog(AccountAct.class);
	
	@Qualifier("accountService")
	@Autowired
	private AccountService accountService;
	@Qualifier("userService")
	@Autowired
	private UserService userService;
	@Qualifier("authService")
	@Autowired
	private AuthService authService;
	
	public Map<String,Object> aComm = new HashMap<String,Object>();
	public String catIdComm = "";
	
	/**
	 * ��Ӧ��ģ��
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/SupplierList.do")
	public String supplierInfoList(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String catId = request.getParameter("catId");
		try {
			//ȡ��tree������
			int length = accountService.selectTreeMaxLvl();
			//ѭ��ȡ��ÿ������
			for(int i=1;i<length+1;i++){
				Object[] params = new Object[]{i};
				List<Account> aList = accountService.selectTreeLvl(params);
				String aName = "aList"+i;
				model.addAttribute(aName,aList);
			}
			//��ѯ���и�������
			model.addAttribute("aCatName",accountService.selectAllParentName());
			String type = "��Ӧ��";
			Account account = new Account();
			account.setaCatId(catId);
			account.setAccountType(type);
			//����catId��ѯ���е�λ��Ϣ
			model.addAttribute("atDe", accountService.selectAllAtDe(account));
			
			//Page type 
			model.addAttribute("typeFlag",type);
			
			User user = new User();
			user.setActive(true);
			user.setDelete(false);
			List<User> u = userService.getUserLists(user);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		return "account/accountInfo";
	}
	
	@RequestMapping(value="/ClientList.do")
	public String accountInfoList(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String catId = request.getParameter("catId");
		try {
			//ȡ��tree������
			int length = accountService.selectTreeMaxLvl();
			//ѭ��ȡ��ÿ������
			for(int i=1;i<length+1;i++){
				Object[] params = new Object[]{i};
				List<Account> aList = accountService.selectTreeLvl(params);
				String aName = "aList"+i;
				model.addAttribute(aName,aList);
			}
			//��ѯ���и�������
			model.addAttribute("aCatName",accountService.selectAllParentName());
			String type = "�ͻ�";
			Account account = new Account();
			account.setaCatId(catId);
			account.setAccountType(type);
			//����catId��ѯ���е�λ��Ϣ
			model.addAttribute("atDe", accountService.selectAllAtDe(account));
			
			//Page type 
			model.addAttribute("typeFlag",type);
			
			User user = new User();
			user.setActive(true);
			user.setDelete(false);
			List<User> u = userService.getUserLists(user);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		return "account/accountInfo";
	}
	
	/**
	 * ���ݵ�λtree id��ѯ�ڵ���Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectAcDe.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selectAcDe(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String catId = catIdComm;
		try {
			Object[] params = new Object[]{catId};
			//����id��ѯ��λ�ڵ���Ϣ
			Account account = accountService.selectAcDeId(params);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("catParNameId", account.getCatParId());
			map.put("catName", account.getCatName());
			map.put("catId",account.getCatId());
			//����ObjectMapper����
			ObjectMapper mapper = new ObjectMapper();  
			//MapתJSON ����ajax
			return  mapper.writeValueAsString(map); 
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		
	}
	
	/**
	 * ��ӵ�λtree�ڵ�
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addAccountCat.do")
	@ResponseBody
	public int addAccountCat(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String catId = request.getParameter("catId");
		String catName = request.getParameter("catName");
		String userId = String.valueOf(session.getAttribute("userid"));
		String catLvl;
		try {
			//���ݸ���id��ѯ����㼶
			Object[] params = new Object[]{catId};
			Account account = accountService.selectAcDeId(params);
			catLvl = String.valueOf(Integer.valueOf(account.getCatLvl())+1);
			Object[] params2 = new Object[]{
					catName,catId,catLvl,userId	
			};
			//��ӵ�λtree�ڵ�
			return accountService.addAccountCat(params2); 
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		
	}
	
	/**
	 * ���ĵ�λtree�ڵ�
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateAccountCat.do")
	@ResponseBody
	public int updateAccountCat(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String catId = request.getParameter("catId");
		String catName = request.getParameter("catName");
		String userId = String.valueOf(session.getAttribute("userid"));
		String id = request.getParameter("id");
		String catLvl;
		try {
			//���ݸ���id��ѯ����㼶
			Object[] params = new Object[]{catId};
			Account account = accountService.selectAcDeId(params);
			catLvl = String.valueOf(Integer.valueOf(account.getCatLvl())+1);
			Object[] params2 = new Object[]{
					catName,catId,catLvl,userId,id
			};
			//���ĵ�λtree�ڵ�
			return accountService.updateAccountCat(params2); 
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		
	}
	
	/**
	 * ���ݵ�λtree idɾ���ڵ���Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAcNode.do")
	@ResponseBody
	public int deleteAcNode(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String catId = catIdComm;
		int delRes = 0;
		try {
			Object[] params = new Object[]{catId};
			
				//��ѯ�ýڵ��Ƿ�Ϊ���սڵ�
				int count = accountService.setAccountCatCnodeCount(params);
				if(count == 0){
					Account accountParams = new Account();
					accountParams.setaCatId(catId);
					//��ѯ���ýڵ��µ����е�λ��Ϣ
					List<Account> account = accountService.selectAllAtDe(accountParams);
					//ɾ�����иýڵ��µĵ�λ��Ϣ
					
					for(int i=0;i<account.size();i++){
						Object[] params2 = new Object[]{account.get(i).getAccountId()};
						delRes = accountService.deleteAt(params2);
					}
					if(delRes == 1){
						//����idɾ���ڵ�
						delRes = accountService.deleteAcNode(params);
					}
					
				}else if(count != 0){
					
					//��ѯ�ýڵ��µ������ӽڵ�
					List<Account> ac = accountService.setAccountCatCnode(params);
					//ɾ�����иýڵ��µĵ�λ��Ϣ

					for(int i=0;i<ac.size();i++){
						Object[] params3 = new Object[]{ac.get(i).getCatId()};
						Account accountParams = new Account();
						accountParams.setaCatId(ac.get(i).getCatId());
						//��ѯ���ýڵ��µ����е�λ��Ϣ
						List<Account> account = accountService.selectAllAtDe(accountParams);
						//ɾ�����иýڵ��µĵ�λ��Ϣ
						for(int j=0;j<account.size();j++){
							Object[] params2 = new Object[]{account.get(i).getAccountId()};
							delRes = accountService.deleteAt(params2);
						}
						if(delRes == 1){
							//����idɾ���ڵ�
							delRes = accountService.deleteAcNode(params3);
						}
					}
					if(delRes == 1){
						//����idɾ���ڵ�
						delRes = accountService.deleteAcNode(params);
					}
				}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		return delRes;
	}
	
	/**
	 * ������catId��ѯ���е�λ��Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/detail.do")
	@ResponseBody
	public int detail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String catId = request.getParameter("catId");
		try {
			Account account = new Account();
			account.setaCatId(catId);
			//����catId��ѯ���е�λ��Ϣ
			model.addAttribute("atDe", accountService.selectAllAtDe(account));
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ����aId��ѯ��λ��Ϣ����
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editAt.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String editAt(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String aId = request.getParameter("aId");
		try {
			Object[] params = new Object[]{aId};
			//����aId��ѯ��λ��Ϣ����
			Account account = accountService.selectAtDe(params);
			//���ݵ�ַid��ѯ��ַ��ϸ��Ϣ
			Address pAddr = userService.selectAddressDetail(account.getPrimaryAddrId());
			Address pShipAddr = userService.selectAddressDetail(account.getPrimaryShipaddrId());
			Address pBillAddr = userService.selectAddressDetail(account.getPrimarybilladdrId());
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("accountId", account.getAccountId());
			map.put("accountName", account.getAccountName() );
			map.put("accountNumber", account.getAccountNumber());
			map.put("accountType", account.getAccountType());
			map.put("aTel", account.getWorkphone());
			map.put("pAddr", pAddr.getAllAddress());
			map.put("pAddrId", pAddr.getAddressId());
			map.put("aFax", account.getFax());
			map.put("aEmail", account.getEmail());
			map.put("aDesc", account.getAccountDesc());
			map.put("pShipAddr", pShipAddr.getAllAddress());
			map.put("pShipAddrId", pShipAddr.getAddressId());
			map.put("pBillAddr", pBillAddr.getAllAddress());
			map.put("pBillAddrId", pBillAddr.getAddressId());
			//����ObjectMapper����
			ObjectMapper mapper = new ObjectMapper();  
			//MapתJSON ����ajax
			return  mapper.writeValueAsString(map); 
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ��ӵ�λ��Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveAdd.do")
	@ResponseBody
	public int saveAdd(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String aName = request.getParameter("aName");
		String aNumber = request.getParameter("aNumber");
		String aType = request.getParameter("aType");
		String aTel = request.getParameter("aTel");
		String aAddress = request.getParameter("aAddress");
		String aFax = request.getParameter("aFax");
		String aEmail = request.getParameter("aEmail");
		String aDesc = request.getParameter("aDesc");
		String aAddrDet = request.getParameter("aAddrDet");
		String aZipcode = request.getParameter("aZipcode");
		String userId = String.valueOf(session.getAttribute("userid"));
		String aOtherAddr1 = request.getParameter("aOtherAddr1");
		String aOtherAddr2 = request.getParameter("aOtherAddr2");
		String status_code = "Not Published";
		String status_val = "δ����";
		String catId = catIdComm;
		
		try {
			Object[] params = new Object[]{
					aName,aNumber,aType,catId,aTel,aAddress,aOtherAddr1,aOtherAddr2,aFax,aEmail,userId,aDesc,aAddrDet,aZipcode,status_code,status_val
			};
			//��ӵ�λ
			return accountService.addAt(params);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ���ĵ�λ��Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveEdit.do")
	@ResponseBody
	public int saveEdit(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String aName = request.getParameter("aName");
		String aNumber = request.getParameter("aNumber");
		String aType = request.getParameter("aType");
		String aTel = request.getParameter("aTel");
		String aAddrId = request.getParameter("aAddrId");
		String aFax = request.getParameter("aFax");
		String aEmail = request.getParameter("aEmail");
		String aDesc = request.getParameter("aDesc");
		String aAddrDet = request.getParameter("aAddrDet");
		String aZipcode = request.getParameter("aZipcode");
		String aCatId = request.getParameter("aCat");
		String aComm = request.getParameter("aComm");
		String userId = String.valueOf(session.getAttribute("userid"));
		String aId = request.getParameter("aId");
		String aOtherAddr1 = request.getParameter("aOtherAddr1");
		String aOtherAddr2 = request.getParameter("aOtherAddr2");
		try {
			Object[] params = new Object[]{
					aName,aNumber,aType,aCatId,aTel,aAddrId,aAddrDet,aFax,aEmail,aZipcode,userId,aDesc,aComm,aOtherAddr1,aOtherAddr2,aId
			};
			//���ĵ�λ
			return accountService.editAt(params);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ��ӹ�����Ϣ��model
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/loadAccount.do")
	@ResponseBody
	public List<Account> loadAccount(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		request.setCharacterEncoding("UTF-8");
		String catId = request.getParameter("catId");
    	String type= request.getParameter("typeFlag");
		try {
			if(!catId.equals(null)&&catId!=null){
				catIdComm = catId;
			}
			if(catIdComm.equals("1")){
				Account account = new Account();
				account.setAccountType(type);
				//��ѯ�����е�λ��Ϣ
				return accountService.selectAllAtDe(account);
			}else{
				Account account = new Account();
				account.setaCatId(catIdComm);
				account.setAccountType(type);
				//����catId��ѯ���е�λ��Ϣ
				return accountService.selectAllAtDe(account);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ���ص�ַ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/loadAvailAddr.do",method = RequestMethod.GET)
	@ResponseBody
	public List<Address> loadAvailAddresses(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		try{
			return userService.selectAddressLists();
		}catch(Exception e){
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ����aId�������Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAt.do")
	@ResponseBody
	public int deleteAt(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String aId = request.getParameter("aId");
		try {
			Object[] params = new Object[]{aId};
			//����aId�������Ϣ
			return accountService.deleteAt(params); 
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ��ȡ����id
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectCname.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String selectCname(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String catId = catIdComm;
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("catId", catId);
			//����ObjectMapper����
			ObjectMapper mapper = new ObjectMapper();  
			//MapתJSON ����ajax
			return  mapper.writeValueAsString(map); 
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * �༭�鿴�ͻ�/��Ӧ����ϸ��Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/accountDetail.do")
	public String accountDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String aId = request.getParameter("accountId");
		try {
			Object[] params = new Object[]{aId};
			//����aId��ѯ��λ��Ϣ����
			Account account = accountService.selectAtDe(params);
			
			
			model.addAttribute("account",account);
			
			boolean readOnlyFlag = true;
			if(account.getAccountStatus()!=null&&!account.getAccountStatus().equals("")){
				switch(account.getAccountStatus()){
				case "Not Published":
					readOnlyFlag = false;
					break;
				case "Rejected":
					readOnlyFlag = false;
					break;
				}
			}
			model.addAttribute("readOnlyFlag",readOnlyFlag);
			return "account/account_edit";
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ��˲鿴�ͻ�/��Ӧ����ϸ��Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/accountApproveDetail.do")
	public String accountApproveDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String aId = request.getParameter("accountId");
		try {
			Object[] params = new Object[]{aId};
			//����aId��ѯ��λ��Ϣ����
			Account account = accountService.selectAtDe(params);
			model.addAttribute("account",account);
			
			boolean readOnlyFlag = true;
			if(account.getAccountStatus()!=null&&!account.getAccountStatus().equals("")){
				switch(account.getAccountStatus()){
				case "Not Published":
					readOnlyFlag = false;
					break;
				case "Rejected":
					readOnlyFlag = false;
					break;
				case "Pending":
					ApprovalDetail userApprovalDetail = authService.queryApprovalDetailStatus(aId, "Account", session.getAttribute("userid").toString());
					if(userApprovalDetail.getStatus_cd().equals("Pending") && userApprovalDetail.isProcess_flg()){
						readOnlyFlag = false;
					}else{
						readOnlyFlag = true;
					}
					break;
				case "Published":
					readOnlyFlag = true;
					break;
				}
			}else{
				readOnlyFlag = false;
			}
			model.addAttribute("readOnlyFlag",readOnlyFlag);
			return "account/account_edit_approval";
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(value="/loadcat.do",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String loadCategory(HttpServletRequest request, HttpSession session) throws Exception{

		try{
			List<Account> acatList = accountService.selectAllParentName();
			ObjectMapper om = new ObjectMapper();
			String responseStr = om.writeValueAsString(acatList);	
			
			return responseStr;
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(value="/updateAccountStatus.do",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String updateAccountStatus(HttpServletRequest request, HttpSession session) throws Exception{

		try{
			Account account = new Account();
			account.setAccountId(request.getParameter("accountId"));
			account.setAccountStatus(request.getParameter("status_code"));
			account.setUpdateBy(session.getAttribute("userid").toString());
			account.setUpdateByName(session.getAttribute("username").toString());
			
			return accountService.updateAccountStatusByIdFacade(account);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			return "{'errmsg':'"+e.getMessage()+"'}";
		}
	}
	
	@RequestMapping(value="/queryApprovalStatus.do",method = RequestMethod.POST)
	@ResponseBody
	public List<ApprovalStep> queryApprovalStatus(HttpServletRequest request, HttpSession session) throws Exception{

		try{
			String accountId = request.getParameter("accountId");
			String type = request.getParameter("type");
			return authService.queryApproval(accountId, type);
		}catch(Exception e){
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(value="/approval_history_listdata.do",method = RequestMethod.POST)
	@ResponseBody
	public String approvalHistoryListData(HttpServletRequest request, HttpSession session) throws Exception{

		try{
			List<Object> data = new ArrayList<Object>();
			Map<String, Object> response = new HashMap<String, Object>();
			int length = Integer.parseInt(request.getParameter("length"));
			int start = Integer.parseInt(request.getParameter("start"));
			int sEcho = Integer.parseInt(request.getParameter("draw"));
			int sortColumn = Integer.parseInt(request.getParameter("order[0][column]"));
			String sortDir = request.getParameter("order[0][dir]");
			String action = request.getParameter("action");
			String accountId = request.getParameter("id");
			ApprovalHistory approvalHistorySearch = new ApprovalHistory();
			
			approvalHistorySearch.setAccount_id(accountId);
			int iTotalRecords = accountService.getApprovalHistories(approvalHistorySearch, null, null, null, null).size();
			length = length < 0 ? iTotalRecords : length; 
			int end = start + length;
			end = end > iTotalRecords ? iTotalRecords : end;
			int limit = end - start;
			List<ApprovalHistory> approvalHistoryList = new ArrayList<ApprovalHistory>();
			
			if(action!=null && action.equals("filter")){
				approvalHistorySearch.setCreated_date_from(request.getParameter("order_history_date_from"));
				approvalHistorySearch.setCreated_date_to(request.getParameter("order_history_date_to"));
				approvalHistorySearch.setDescription(request.getParameter("order_history_desc"));
				approvalHistorySearch.setCreated_by_user_name(request.getParameter("order_created_by_username"));
				
				approvalHistoryList = accountService.getApprovalHistories(approvalHistorySearch, String.valueOf(start), 
						String.valueOf(limit), String.valueOf(sortColumn+1), sortDir);
				response.put("iTotalDisplayRecords", accountService.getApprovalHistories(approvalHistorySearch, null, 
						null, null, null).size());
			}else{
				approvalHistoryList = accountService.getApprovalHistories(approvalHistorySearch, String.valueOf(start), 
						String.valueOf(limit), String.valueOf(sortColumn+1), sortDir);
				response.put("iTotalDisplayRecords", iTotalRecords);
			}
			
			for(int i = 0; i < approvalHistoryList.size(); i++)
	        {  
				ApprovalHistory approvalHistory = approvalHistoryList.get(i); 
				List<String> dataRow = new ArrayList<String>();
				dataRow.add(approvalHistory.getCreated_date());
				dataRow.add(approvalHistory.getDescription());
				dataRow.add(approvalHistory.getCreated_by_user_name());
				dataRow.add(" ");
				data.add(dataRow);
	        }
			
			response.put("data", data);
			response.put("draw", sEcho+1);
			response.put("recordsTotal ", iTotalRecords);
			//response.put("recordsFiltered", iTotalRecords);
			response.put("iTotalRecords", iTotalRecords);
			
			ObjectMapper om = new ObjectMapper();
			String responseStr = om.writeValueAsString(response);		
			
			return responseStr;
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(value="/approval_list.do",method = RequestMethod.GET)
	public String productApprovalList(Model model, HttpServletRequest request, HttpSession session) throws Exception{

		try{
			return "account/account_list_approval";
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	@RequestMapping(value="/listdata_approval.do",method = RequestMethod.POST)
	@ResponseBody
	public String productApprovalListData(HttpServletRequest request, HttpSession session) throws Exception{

		try{
			List<Object> data = new ArrayList<Object>();
			Map<String, Object> response = new HashMap<String, Object>();
			int length = Integer.parseInt(request.getParameter("length"));
			int start = Integer.parseInt(request.getParameter("start"));
			int sEcho = Integer.parseInt(request.getParameter("draw"));
			int sortColumn = Integer.parseInt(request.getParameter("order[0][column]"));
			String sortDir = request.getParameter("order[0][dir]");
			String approver_id = session.getAttribute("userid").toString();
//			String[] ids = request.getParameterValues("id[]");
//			String customActionType = request.getParameter("customActionType");
//			String customActionName = request.getParameter("customActionName");
			String action = request.getParameter("action");
			Product productSearch = new Product();
			Account accountSearch = new Account();
			
			int iTotalRecords = accountService.getAccountsForApprover(accountSearch, approver_id,null, 
					null, null, null).size();
			length = length < 0 ? iTotalRecords : length; 
			int end = start + length;
			end = end > iTotalRecords ? iTotalRecords : end;
			int limit = end - start;
			List<Product> productList = new ArrayList<Product>();
			List<Account> accountList = new ArrayList<Account>();
			
			if(action!=null && action.equals("filter")){
				productSearch.setNumber(request.getParameter("product_number"));
				productSearch.setName(request.getParameter("product_name"));
				productSearch.setCategoryId(request.getParameter("product_category"));
//				productSearch.setProduct_price_from(request.getParameter("product_price_from"));
//				productSearch.setProduct_price_to(request.getParameter("product_price_to"));
				productSearch.setProduct_quantity_from(request.getParameter("product_quantity_from"));
				productSearch.setProduct_quantity_to(request.getParameter("product_quantity_to"));
				productSearch.setProduct_created_from(request.getParameter("product_created_from"));
				productSearch.setProduct_created_to(request.getParameter("product_created_to"));
				productSearch.setStatus(request.getParameter("product_status"));
				
				accountSearch.setAccountNumber(request.getParameter(""));
				accountSearch.setAccountName(request.getParameter(""));
				
				accountList = accountService.getAccountsForApprover(accountSearch, approver_id,String.valueOf(start), 
						String.valueOf(limit), String.valueOf(sortColumn+1), sortDir);
				response.put("iTotalDisplayRecords", accountService.getAccountsForApprover(accountSearch, approver_id,null, 
						null, null, null).size());
			}else{
				accountList = accountService.getAccountsForApprover(accountSearch, approver_id,String.valueOf(start), 
						String.valueOf(limit), String.valueOf(sortColumn+1), sortDir);
				response.put("iTotalDisplayRecords", iTotalRecords);
			}
			
			for(int i = 0; i < accountList.size(); i++)
	        {  
				Account account = accountList.get(i); 
				List<String> dataRow = new ArrayList<String>();
				dataRow.add("<input type='checkbox' name='id[]' value="+account.getAccountId()+">");
				dataRow.add(account.getAccountNumber());
				dataRow.add(account.getAccountName());
				dataRow.add(account.getAccountType());
				//dataRow.add(String.valueOf(product.getPrice()));
				dataRow.add(account.getCatName());
				dataRow.add(account.getUpdate());
				if(!account.isActive()){
					dataRow.add("<span class=\"label label-sm label-danger\">��ɾ��</span>");
				}else if(account.getAccountStatus().equals("Not Published")){
					dataRow.add("<span class=\"label label-sm label-info\">δ����</span>");
				}else if(account.getAccountStatus().equals("Pending")){
					dataRow.add("<span class=\"label label-sm label-info\">�����</span>");
				}else if(account.getAccountStatus().equals("Rejected")){
					dataRow.add("<span class=\"label label-sm label-danger\">�Ѿܾ�</span>");
				}else{
					dataRow.add("<span class=\"label label-sm label-success\">�ѷ���</span>");
				}
				dataRow.add("<a href=\"accountApproveDetail.do?accountId="+account.getAccountId()+"\" class=\"btn btn-xs default btn-editable\"><i class=\"fa fa-pencil\"></i> �������</a>");
				data.add(dataRow);
	        }
			
			response.put("data", data);
			response.put("draw", sEcho+1);
			response.put("recordsTotal ", iTotalRecords);
			//response.put("recordsFiltered", iTotalRecords);
			response.put("iTotalRecords", iTotalRecords);
			
			ObjectMapper om = new ObjectMapper();
			String responseStr = om.writeValueAsString(response);		
			
			return responseStr;
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw e;
		}
	}
}
