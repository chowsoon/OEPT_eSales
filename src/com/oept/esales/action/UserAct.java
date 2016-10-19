package com.oept.esales.action;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oept.esales.model.Address;
import com.oept.esales.model.Position;
import com.oept.esales.model.User;
import com.oept.esales.service.AuthService;
import com.oept.esales.service.LoginService;
import com.oept.esales.service.UserService;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/11/06
 * Description: User management operation.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾. All rights reserved.
 */
@Controller
@RequestMapping(value="/user")
public class UserAct {

	@Qualifier("userService")
	@Autowired
	private UserService userService;
	
	@Qualifier("loginService")
	@Autowired
	private LoginService loginService;
	
	@Qualifier("authService")
	@Autowired
	private AuthService authService;
	
	private static final Log logger = LogFactory.getLog(UserAct.class);
	
	
	/**
	 * ���û�����ע�᷽��
	 * @param model
	 * @param request
	 * @param session
	 * @return 
	 */
	@RequestMapping(value="/signin.do")
	@ResponseBody
	public int createUser(Model model, HttpServletRequest request, HttpSession session)throws Exception{
		//���λ�ȡrequest����
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String address = request.getParameter("address");

		//����־û���
		User user = new User();
		user.setUserName(username);
		user.setPassword(password);
		user.setLastName(lastname);
		user.setFirstName(firstname);
		user.setEmail(email);
		user.setAddress(address);
			
		//����ҵ���ע�᷽�������շ�������Ϊ����
		int results =  userService.signin(user);
		//���ע��ɹ��Զ���¼
		if(results == 1){
			try{
				//����loginservice��¼���������շ�������
				User user2 = loginService.login(user);
				if(user2 != null){
					//��¼�û���¼ʱ��
					loginService.lastLogin(user);
				}
				//���յ��ķ������ݴ���model������ǰ�˵�������
				model.addAttribute("loginMap", user2);
				//�û����������session
				session.setAttribute("username", user.getUserName());
				session.setAttribute("password", user.getPassword());
				session.setAttribute("userid", user2.getUserId());
				return 1;
			}catch(Exception e){
				logger.info(e.getMessage());
				throw e;
			}
		}else{
			//����ҳ��
			return 0;
		}
	}
	
	/**
	 * ��֤�û��Ƿ����
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/testingUesr.do",method = RequestMethod.POST)
	@ResponseBody
	public String testingUser(Model model, HttpServletRequest request, HttpSession session)throws Exception{
		//��ȡrequest����
		String username = request.getParameter("username");
		//�û���Ϊ�գ���ִ�в�ѯ
		if(username == ""){
			String res;
			res = "3";
			return res;
		}else{
		try {
			//����userServiceע����֤���������շ��ؽ��
			Integer result = userService.testingUser(username);
			String res;
			//���ݷ��ؽ���жϷ�����Ӧ���
			if(result == null){
				res = "2";
				return res;
			}else{
				res = "1";
				return res;
			}
		} catch (Exception e) {
			String res;
			res = "2";
			return res;
		}
		}
		
	}
	
	/**
	 * ��ѯ�����û�
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list.do")
	public String navToUserList(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		try{
			//����userService��ѯ�û��б���������model������
			model.addAttribute("userList",userService.selectUserList());
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		//����ҳ��
		return "userList";
	}
	
	/**
	 * �û�����ϸ��Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/details.do")
	public String navToUserDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		try{
			//����userId����
			String userId = request.getParameter("userId");
			//����userService��ѯ�û���ϸ��Ϣ����������model������
			model.addAttribute("userDetails", userService.selectUserDetail(userId));
			//����userService��ѯְλ���Ϸ���������model������
			model.addAttribute("positions", userService.selectPositions());
			Object[] params = new Object[]{
					userId
			};
			List<Map<String, Object>> personalPositions = userService.personalPositionList(params);
			//���ø���ְλ����
			model.addAttribute("pPositions", personalPositions);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		//����ҳ��
		return "userDetails";
	}
	
	/**
	 * �����û���Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateChannellist(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String res = "";
		try{
			//���ղ���
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String emailaddress = request.getParameter("emailaddress");
			String address = request.getParameter("address");
			String lastname = request.getParameter("lastname");
			String firstname = request.getParameter("firstname");
			String activeX = request.getParameter("active");
			String userId = request.getParameter("userId");
			String position = request.getParameter("position");
			String positionId = request.getParameter("positionId");
			String loginUserId = String.valueOf(session.getAttribute("userid"));
			Boolean active = false;
			if(activeX.equals("1")){
				active = true;
			}else if(activeX.equals("0")){
				active = false;
			}

			//�������Ϊ����ִ��û����������ĸ��·�������Ȼ��ִ�д���������ĸ��·���
			if(password.equals("")||password==null){
				//����Object���鴫�ݲ���
				Object[] params = new Object[] {
						emailaddress,address,lastname,firstname,username,active,loginUserId,userId
				};
				//����userService�����û���Ϣ�����������������������res������
				res = String.valueOf(userService.updateUserDetailNoPwd(params));
				//������ְλ
				if(res.equals("1")){
					Object[] params2 = new Object[]{
							position,
							userId
					};
					//��ѯ�û���ǰ��Ҫ�޸ĵ�ְλ�Ƿ���ڣ������ڷ�����Ϣ�������޸�
					int or = userService.selectUserThisPosition(params2);
					if(or != 0){
						//�����û���ְλ
						int result = userService.updateUserPrimaryPosition(params2);
						res = String.valueOf(result);
					}else {
						res = "3";
					}
					
				}
			}else{
				Object[] params = new Object[] {
						password,emailaddress,address,lastname,firstname,username,active,loginUserId,userId
				};
				//����userService�����û���Ϣ�����������������������res������
				res = String.valueOf(userService.updateUserDetailAndPwd(params));
				//������ְλ
				if(res.equals("1")){
					Object[] params2 = new Object[]{
							position,
							userId
					};
					//��ѯ�û���ǰ��Ҫ�޸ĵ�ְλ�Ƿ���ڣ������ڷ�����Ϣ�������޸�
					int or = userService.selectUserThisPosition(params2);
					if(or != 0){
						//�����û���ְλ
						int result = userService.updateUserPrimaryPosition(params2);
						res = String.valueOf(result);
					}else {
						res = "3";
					}
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		//����ajax���
		return res;
	}
	
	/**
	 * ����id����ɾ���û�
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delect.do", method = RequestMethod.POST)
	@ResponseBody
	public String delectUser(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		//��ȡcheckbox��id�ַ���
		String boxs = request.getParameter("boxs");
		String[] users = null;
		//��ȡ�ַ���������
		users = boxs.split("s");
		String result = "0";
		try {
			//��������
			for(int i=0;i<users.length;i++){
				//���θ���id����ɾ������
				Integer res = userService.deleteUser(users[i]);
				//���ؽ��
				if(res == 1){
					result = "1";
				}else{
					result = "0";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
				logger.info(e.getMessage());
				throw e;
		}
		//���ؽ��
		return result;
	}
	
	/**
	 * ֻ������ת�����û�ע��
	 */
	@RequestMapping(value="/newUserSkip.do")
	public String newUserSkip(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		//ֻ������ת
		return "userNews";
	}
	
	/**
	 * �������û�
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/newUser.do")
	@ResponseBody
	public String newUser(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		String res = "";
		try{
			//���ղ���
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String emailaddress = request.getParameter("emailaddress");
			String address = request.getParameter("address");
			String lastname = request.getParameter("lastname");
			String firstname = request.getParameter("firstname");

			//����Object���鴫�ݲ���
			Object[] params = new Object[] {
					firstname,lastname,username,password,emailaddress,address
			};
			//����userService�½��û�����������res������
			res = String.valueOf(userService.newUser(params));
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		//����ajax���
		return res;
	}
	
	/**
	 * ��ѯ���е�ַ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/address.do")
	public String navToAddressList(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		try{
			//����userService��ѯ���е�ַ
			List<Address> addressLists = userService.selectAddressLists();
			//����model����
			model.addAttribute("addressLists",addressLists);
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		//����ҳ��
		return "area_list";
	}
	
	
	/**
	 * �����ջ���ַ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/newAddress.do")
	@ResponseBody
	public String newAddress(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		//���ղ���
		String contactName = request.getParameter("contactName");
		String province = request.getParameter("location_p");
		String city = request.getParameter("location_c");
		String county = request.getParameter("location_a");
		String detailsAddress = request.getParameter("detailsAddress");
		String zipcode = request.getParameter("zipcode");
		String contactTel = request.getParameter("contactTel");
		String country = request.getParameter("country");
//		String addressAllName = country+province+city+county+detailsAddress;
		String addressAllName = country+province+city+county;
		String userId = String.valueOf(session.getAttribute("userid"));
		//����־û���
		Address address = new Address();
		address.setAllAddress(addressAllName);
		address.setProvince(province);
		address.setCity(city);
		address.setCounty(county);
		address.setStreet(detailsAddress);
		address.setZipcode(zipcode);
		address.setContactName(contactName);
		address.setContactCell(contactTel);
		address.setCreatedBy(userId);
		address.setUpdateBy(userId);
		address.setCountry(country);
		
		String res = "";
		try{
			//����userService�����ջ���ַ����������res������
			res = String.valueOf(userService.newAddress(address));
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		//����ajax���
		return res;
	}
	
	/**
	 * ����ɾ���ջ���ַ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delectAddress.do", method = RequestMethod.POST)
	@ResponseBody
	public String delectAddress(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		//��ȡcheckbox��id�ַ���
		String boxs = request.getParameter("boxs");
		String[] address = null;
		//��ȡ�ַ���������
		address = boxs.split("s");
		String result = "0";
		try {
			//��������
			for(int i=0;i<address.length;i++){
				//���θ���id����ɾ������
				result = String.valueOf(userService.deleteAddress(address[i]));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
				logger.info(e.getMessage());
				throw e;
		}
		//���ؽ��
		return result;
	}
	
	/**
	 * ����id��ѯ��ַ��Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectAddressId.do")
	public String selectAddressDetail(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		int readonly = Integer.valueOf(request.getParameter("readonly"));
		try{
			//����addressId����
			String addressId = request.getParameter("addressId");
			//����userService��ѯ��ַ��ϸ��Ϣ����������model������
			model.addAttribute("addressDetail", userService.selectAddressDetail(addressId));
			//����userService���ݵ�ַ��ѯ�����û�
			model.addAttribute("aUsers",userService.addrIdSelectUsername(addressId));
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		if(readonly == 1){
			return "allotAddress";
		}else{
			//����ҳ��
			return "area_details";
		}
	}
	
	/**
	 * �����ջ���ַ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateAddress.do")
	@ResponseBody
	public String updateAddress(Model model, HttpServletRequest request, HttpSession session) throws Exception{
	
		String res = "";
		try{
			//���ղ���
			String contactName = request.getParameter("contactName");
			String location_p = request.getParameter("location_p");
			String location_c = request.getParameter("location_c");
			String location_a = request.getParameter("location_a");
			String detailsAddress = request.getParameter("detailsAddress");
			String zipcode = request.getParameter("zipcode");
			String contactTel = request.getParameter("contactTel");
			String addressId = request.getParameter("addressId");
			String country = request.getParameter("country");
			String userid = session.getAttribute("userid").toString();
			String addressAllName = country+location_p+location_c+location_a+detailsAddress;
			//����Object���鴫�ݲ���
			Object[] params = new Object[] {
					addressAllName,location_p,location_c,location_a,zipcode,contactName,contactTel,detailsAddress,country,userid,addressId
			};
			res = String.valueOf(userService.updateAddress(params));
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		//����ajax���
		return res;
	}
	
	/**
	 * ��ȡ�û�������
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxUserNameList.do")
	@ResponseBody
	public String[] ajaxUserNameList(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		String res = "1";
		String[] usernames;
		try {
			//����userService��ȡ�û�������
			usernames = userService.selectAllUserName();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		//��������
		return usernames;
	}
	
	/**
	 * �����ַ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/allotAddress.do")
	@ResponseBody
	public int allotAddress(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		
		int res ;
		try {
			//���ղ���
			String addressId = request.getParameter("addressId");
			String username = request.getParameter("username");
			String userId = String.valueOf(session.getAttribute("userid"));
			//����Object���鴫�ݲ���
			Object[] params = new Object[] {
					addressId,username,userId
			};
			//����userService�����û��ջ���ַ
			res = userService.allotAddress(params);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		//���ؽ��
		return res;
	}

	/**
	 * ɾ����ַ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteUserAddr.do")
	@ResponseBody
	public int deleteUserAddress(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		int res;
		//���ղ���
		String addressId = request.getParameter("addressId");
		String userId = request.getParameter("userId");
		try {
			//����־û���
			Address address = new Address();
			address.setAddressId(addressId);
			User user = new User();
			user.setUserId(userId);
			//����userServiceɾ���û��ջ���ַ
			res = userService.deleteUserAddress(address, user);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		//���ؽ��
		return res;
	}
	
	/**
	 * ����û�ְλ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/newUserPostion.do")
	@ResponseBody
	public int newUserPostion(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		int res;
		//���ղ���
		String userId = request.getParameter("userId");
		String position = request.getParameter("position");
		String sysUserId = String.valueOf(session.getAttribute("userid"));
		try {
			Object[] params = new Object[]{
					userId,
					position,
					sysUserId
			};
			//����userServiceɾ���û��ջ���ַ
			res = userService.newUserPosition(params);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		//���ؽ��
		return res;
	}
	
	/**
	 * ɾ���û�ְλ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteUserPostion.do")
	@ResponseBody
	public int deleteUserPostion(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		int result = 0;
		//���ղ���
		String oppid = request.getParameter("oppId");
		String userId = request.getParameter("userId");
		int param = Integer.valueOf(request.getParameter("param"));
		try {
			if(param == 2){
				//���ɾ����Ϊ��ְλ
				Object[] params = new Object[]{
						oppid
				};
				//����userServiceɾ���û��ջ���ַ
				result =  userService.deleteUserPosition(params);
			}else if(param == 1){
				//���ɾ��Ϊ��ְҵ
				Object[] params = new Object[]{
						oppid
				};
				//����userServiceɾ���û��ջ���ַ
				int res =  userService.deleteUserPosition(params);
				if(res == 1){
					String positionNull = null;
					Object[] params2 = new Object[]{
							positionNull,
							userId
					};
					//�����û���ְλ
					result = userService.updateUserPrimaryPosition(params2);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		return result;
	}
	
	/**
	 * ������ְλ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePrimaryPosition.do")
	@ResponseBody
	public int updatePrimaryPosition(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		int result = 0;
		//���ղ���
		String oppid = request.getParameter("oppId");
		String userId = request.getParameter("userId");
		try {
			Object[] params = new Object[]{
					oppid,
					userId
			};
			//����������ְλ����
			result = userService.updateUserPrimaryPosition(params);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		return result;
	}
	
	/**
	 * ְλ����----��ѯְλ�б�
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/position.do")
	public String navToPositionList(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		try{
			//����userService��ѯְλ���Ϸ���������model������
			model.addAttribute("positionList", userService.selectPositions());
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		//����ҳ��
		return "position/positionList";
	}
	
	/**
	 * ְλ����----���ְλ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/createPosition.do")
	@ResponseBody
	public int createPosition(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		//���ղ���
		String positionName = request.getParameter("positionName");
		String parentPosition = request.getParameter("parentPosition");
		String sysUserId = String.valueOf(session.getAttribute("userid"));
		try{
			Object[] params = new Object[]{
					positionName,
					parentPosition,
					sysUserId
			};
			//����userService������ְλ����
			return userService.createPosition(params);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ְλ����----��ѯ��ְλ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/thisPosition.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String thisPosition(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		//���ղ���
		String positionId = request.getParameter("positionId");
		try{
			Object[] params = new Object[]{
					positionId
			};
			//���ò�ѯ��ְλ��Ϣ����
			Map<String,Object> thisPosition = userService.thisPosition(params);
			//����ObjectMapper����
			ObjectMapper mapper = new ObjectMapper();  
			//MapתJSON ����ajax
			return  mapper.writeValueAsString(thisPosition); 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ְλ����----�鿴ְλ����
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPosition.do")
	public String getPosition(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		//���ղ���
		String positionId = request.getParameter("id");
		try{
			Object[] params = new Object[]{
					positionId
			};
			//���ò�ѯ��ְλ��Ϣ����
			model.addAttribute("position", userService.getPosition(params));
			//����userService��ѯְλ���Ϸ���������model������
			model.addAttribute("positionList", userService.selectPositions());
			//��ѯ����Ȩ������
			Object[] params1 = new Object[]{1};
			Object[] params2 = new Object[]{2};
			Object[] params3 = new Object[]{3};
			Object[] params4 = new Object[]{4};
			model.addAttribute("auth1", authService.queryAuthLvl(params1));
			model.addAttribute("auth2", authService.queryAuthLvl(params2));
			model.addAttribute("auth3", authService.queryAuthLvl(params3));
			model.addAttribute("auth4", authService.queryAuthLvl(params4));
			
			return "position/position_details";
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ְλ����----�޸ĸ�ְλ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatePosition.do")
	@ResponseBody
	public int updatePosition(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		//���ղ���
		String positionName = request.getParameter("positionName");
		String parentPosition = request.getParameter("parentPosition");
		String sysUserId = String.valueOf(session.getAttribute("userid"));
		String positionId = request.getParameter("positionId");
		try{
			//����Object������
			Object[] params = new Object[]{
					positionName,
					parentPosition,
					sysUserId,
					positionId
			};
			//����userService���¸�ְλ���������ؽ��
			return  userService.updatePosition(params);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ְλ����----ɾ����ְλ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deletePosition.do")
	@ResponseBody
	public int deletePosition(Model model, HttpServletRequest request, HttpSession session) throws Exception{
		//���ղ���
		String positionId = request.getParameter("positionId");
		try{
			//����Object������
			Object[] params = new Object[]{
					positionId
			};
			//����userServiceɾ����ְλ���������ؽ��
			return userService.deletePosition(params);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
	}
}
