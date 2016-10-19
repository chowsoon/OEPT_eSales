package com.oept.esales.action;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oept.esales.model.Address;
import com.oept.esales.model.User;
import com.oept.esales.service.UserService;

/**
 * @author zhujj 
 * Version: 1.0 
 * Date: 2015/11/23 
 * Description: Personal information management. 
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾. All rights reserved.
 */
@Controller
@RequestMapping(value = "/personal")
public class PersonalAct {

	@Qualifier("userService")
	@Autowired
	private UserService userService;

	private static final Log logger = LogFactory.getLog(PersonalAct.class);

	/**
	 * ��ѯ��ǰ�û������е��ջ���ַ
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/address.do")
	public String addressList(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		try {
			String userId = String.valueOf(session.getAttribute("userid"));
			List<Address> aList = userService.personalAddressList(userId);
			model.addAttribute("aList", aList);
			//��ȡ��ǰ�û���Ĭ�ϵ�ַ
			User result = userService.userDefaultAddress(userId);
			model.addAttribute("aMap",result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		return "PIAddress";
	}

	/**
	 * �½��ջ���ַ
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createAddress.do")
	@ResponseBody
	public int createAddress(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		// ���ղ���
		String contactName = request.getParameter("contactName");
		String province = request.getParameter("location_p");
		String city = request.getParameter("location_c");
		String county = request.getParameter("location_a");
		String detailsAddress = request.getParameter("detailsAddress");
		String zipcode = request.getParameter("zipcode");
		String contactTel = request.getParameter("contactTel");
		String country = request.getParameter("country");
		String addressAllName = country+province + city + county + detailsAddress;
		String userId = String.valueOf(session.getAttribute("userid"));
		// ����־û���
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
		address.setCountry(country);
		
		int res = 0;
		try {
			// ����userService�����ջ���ַ����
			int result = userService.newAddress(address);
			if(result == 1){
				//����Object���鴫�ݲ���
				Object[] params = new Object[] {
						addressAllName,
						userId,
						userId
				};
				// ����userService�����ַ����
				res = userService.createUserAddress(params);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			throw e;
		}
		return res;
	}
	
	/**
	 * ɾ���ջ���ַ��Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAddress.do")
	@ResponseBody
	public int deleteUserIdAddress(Model model, HttpServletRequest request,
			HttpSession session)throws Exception{
		//���ղ���
		String addressId = request.getParameter("addressId");
		String userId = String.valueOf(session.getAttribute("userid"));
		try {
			//����־û���
			Address address = new Address();
			address.setAddressId(addressId);
			User user = new User();
			user.setUserId(userId);
			//����userServiceɾ���û��ջ���ַ
			return userService.personalDeleteAddress(address, user);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	/**
	 * ��ѯ��Ҫ���µĵ�ַ����
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSelectAddress.do",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateSelectAddress(Model model, HttpServletRequest request,
			HttpSession session)throws Exception{
		//���ղ���
		String addressId = request.getParameter("addressId");
		try {
			//Map����userService��ѯ��ַ��Ϣ�������ؽ��
			Map<String,Object> map = userService.selectAddressDetail2(addressId);
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
	 * ���µ�ַ����
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAddress.do")
	@ResponseBody
	public int updateAddress(Model model, HttpServletRequest request,
			HttpSession session)throws Exception{
		try {
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
			String addressAllName = country+location_p+location_c+location_a+detailsAddress;
			//����Object���鴫�ݲ���
			Object[] params = new Object[] {
					addressAllName,
					location_p,
					location_c,
					location_a,
					zipcode,
					contactName,
					contactTel,
					detailsAddress,
					country,
					addressId
			};
			//����userService���µ�ַ�������ؽ��
			return userService.updateAddress(params);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ����Ĭ�ϵ�ַ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/defaultAddress.do")
	@ResponseBody
	public int defaultAddress(Model model, HttpServletRequest request,
			HttpSession session)throws Exception{
		//���ղ���
		String addressId = request.getParameter("addressId");
		String userId = String.valueOf(session.getAttribute("userid"));
		try {
			//����userService����Ĭ�ϵ�ַ���������ؽ��
			return userService.defaultAddress(userId, addressId);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ��ȡ�û���Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/settings.do")
	public String settingsGet(Model model, HttpServletRequest request,
			HttpSession session)throws Exception{
		//���ղ���
		String userId = String.valueOf(session.getAttribute("userid"));
		try {
			User user = userService.selectUserDetail(userId);
			model.addAttribute("user",user);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		return "PIDetails";
	}
	
	/**
	 * �޸��û���Ϣ
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePersonalSetting.do")
	@ResponseBody
	public int updatePersonalSetting(Model model, HttpServletRequest request,
			HttpSession session)throws Exception{
		//���ղ���
		String username = request.getParameter("username");
		String emailaddress = request.getParameter("emailaddress");
		String address = request.getParameter("address");
		String lastname = request.getParameter("lastname");
		String firstname = request.getParameter("firstname");
		String activeX = request.getParameter("active");
		String userId = request.getParameter("userId");
		Boolean active = false;
		if(activeX == "1"){
			active = true;
		}else if(activeX == "0"){
			active = false;
		}
		try {
			//����Object���鴫�ݲ���
			Object[] params = new Object[] {
					emailaddress,address,lastname,firstname,username,active,session.getAttribute("userid"),userId
			};
			//����userService�����û���Ϣ�����������������������res������
			return userService.updateUserDetailNoPwd(params);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ��תҳ��
	 * @return
	 */
	@RequestMapping(value = "/passwordPage.do")
	public String passwordPage(Model model, HttpServletRequest request,
			HttpSession session)throws Exception{
		String userId = String.valueOf(session.getAttribute("userid"));
		System.out.println(userId);
		try {
			User user = userService.selectUserDetail(userId);
			model.addAttribute("username",user);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		return "PIPassword";
	}
	
	/**
	 * �޸�����
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePassword.do")
	@ResponseBody
	public int updatePassword(Model model, HttpServletRequest request,
			HttpSession session)throws Exception{
		//���ղ���
		String agoPassword = request.getParameter("agoPassword");
		String newPassword = request.getParameter("newPassword");
		String userId = String.valueOf(session.getAttribute("userid"));
		//����Object[]�����У�������֤����
		Object[] params = new Object[]{
				userId
		};
		//����Object[]�����У��޸Ĳ���
		Object[] params2 = new Object[]{
				newPassword,
				userId,
				userId
		};
		try {
			//����userService��֤���������շ��ز���
			String password =  userService.verificationPassword(params);
			//�Ա���ֵ���
			if(agoPassword.equals(password)){
				//���ԭ������ȷ�������޸�
				return userService.updatePassword(params2);
			}else{
				//����ȷ������Ϣ
				return 2;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
}
