package com.oept.esales.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oept.esales.model.OrderFlat;
import com.oept.esales.model.ServiceItem;
import com.oept.esales.model.Services;
import com.oept.esales.model.User;
import com.oept.esales.service.ServicesService;

/**
 * @author zhujj 
 * Version: 1.0 
 * Date: 2015/12/2 
 * Description: Service management operation. 
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾. 
 * All rights reserved.
 */
@Controller
@RequestMapping(value = "/service")
public class ServiceAct {

	@Qualifier("servicesService")
	@Autowired
	private ServicesService servicesService;

	private static final Log logger = LogFactory.getLog(UserAct.class);

	/**
	 * ��ѯ�б�
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list.do")
	public String serviceList(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String userId = String.valueOf(session.getAttribute("userid"));
		try {
			String type = request.getParameter("type");
			Object[] params = new Object[] { userId };
			if (type.equals("consult")) {
				try {
					// ��ѯ��ǰ�û��Ƿ�Ϊ������ѯID
					int consult = servicesService
							.selectUserPositionConsult(params);
					if (consult == 1) {
						// ��ǰ�û�Ϊ������ѯID
						// ��ѯ����������б�
						List<Services> services = servicesService
								.selectServicesListSys(params);
						// ��ѯ����������б�
						List<Services> services2 = servicesService
								.selectServicesListSys2(params);
						model.addAttribute("services", services);
						model.addAttribute("services2", services2);
						// �Ƿ�Ϊ������ѯID
						model.addAttribute("userType", consult);
					} else {
						// ��ǰ�û�
						// ��ѯ����������б�
						List<Services> services = servicesService
								.selectServicesList(params);
						// ��ѯ����������б�
						List<Services> services2 = servicesService
								.selectServicesList2(params);
						model.addAttribute("services", services);
						model.addAttribute("services2", services2);
						// �Ƿ�Ϊ������ѯID
						model.addAttribute("userType", consult);
					}
				} catch (Exception e) {
					// TODO: handle exception
					logger.info(e.getMessage());
					throw e;
				}
				return "services/serConsult";
			} else if (type.equals("complaint")) {
				try {
					// ��ѯ��ǰ�û��Ƿ�Ϊ����Ͷ��ID
					int id = servicesService
							.selectUserPositionComplaint(params);
					// ����servicesService����id��ѯͶ�߷���ļ���
					List<Services> sList = servicesService.selectComplaintList(
							params, id);
					model.addAttribute("scList", sList);
					//��ѯͶ�߷�����ʷ����
					List<Services> hsList = servicesService.selectComplaintListHis(params, id);
					model.addAttribute("hsList", hsList);
					// ����servicesService��ѯid�����ж������
					List<OrderFlat> oList = servicesService
							.selectUserOrderId(params);
					model.addAttribute("oList", oList);
					model.addAttribute("userType", id);
					return "services/services_complaint";
				} catch (Exception e) {
					// TODO: handle exception
					logger.info(e.getMessage());
					throw e;
				}

			}else if (type.equals("return")) {
				try {
					//��ѯ��ǰ�û��Ƿ�Ϊ�ۺ������Ա
					int res = servicesService.selectUserIdAss(params);
					if(res == 0){
						//��Ϊ�ͷ���Ա
						//�˻��б�
						model.addAttribute("rtList",servicesService.selectReturnList(params));
						//�����б�
						model.addAttribute("rpList",servicesService.selectRepairList(params));
						//��ʷ����
						model.addAttribute("oraList",servicesService.selectOverReturnAllList(params));
						//��ʷ�˻�����
						model.addAttribute("ortList", servicesService.selectOverReturnList(params));
						//��ʷ���޶���
						model.addAttribute("orpList", servicesService.selectOverRepairList(params));
						return "services/services_return";
					}else{
						//Ϊ�ͷ���Ա
						//�˻��б�
						model.addAttribute("rtsList",servicesService.selectReturnListSys(params));
						//�����б�
						model.addAttribute("rpsList",servicesService.selectRepairListSys(params));
						//��ʷ����
						model.addAttribute("orasList",servicesService.selectOverReturnAllListSys(params));
						//��ʷ�˻�����
						model.addAttribute("ortsList", servicesService.selectOverReturnListSys(params));
						//��ʷ���޶���
						model.addAttribute("orpsList", servicesService.selectOverRepairListSys(params));
						return "services/services_return_dispose";
					}
				} catch (Exception e) {
					// TODO: handle exception
					logger.info(e.getMessage());
					throw e;
				}
				
			}else if (type.equals("123")) {
				// ����servicesService��ѯid�����ж������
				List<OrderFlat> oList = servicesService
						.selectUserOrderId(params);
				model.addAttribute("oList", oList);
				return "services/services_return_apply";
			} else {
				return "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}

	}

	/**
	 * ������ѯ����
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/createConsultSer.do")
	@ResponseBody
	public int createConsultSer(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String serName = request.getParameter("serName");
		String serDesc = request.getParameter("serDesc");
		String serType = request.getParameter("serType");
		String serDetail = request.getParameter("serDetail");
		String userId = String.valueOf(session.getAttribute("userid"));
		int res = 0;
		try {
			// ��ѯҵ�������ٵ���ѯ����ҵ��Ա
			User user = servicesService.allotConSerUser();
			Object[] params = new Object[] { serName, serDesc, serType,
					user.getUserId(), userId, serDetail };
			// �����ѯ���񶩵�
			res = servicesService.addConSerOrder(params);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		return res;
	}

	/**
	 * ��ѯ��ѯ���񶩵�����
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/consultDetails.do")
	public String selectConsultDetails(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String serviceId = request.getParameter("serviceId");
		String userId = String.valueOf(session.getAttribute("userid"));
		int res = 0;
		try {
			Object[] params = new Object[] { userId };
			Object[] params2 = new Object[] { serviceId };
			// ��ѯ��ǰ�û��Ƿ�Ϊ������ѯID
			res = servicesService.selectUserPositionConsult(params);
			if (res == 1) {
				// ������ѯ���񶩵���ϸ��Ϣ
				List<ServiceItem> serviceItem = servicesService
						.selectServiceDetail(params2);
				Services services = servicesService
						.selectConsultDetail2(params2);
				model.addAttribute("consultDetail", serviceItem);
				model.addAttribute("services", services);
				model.addAttribute("userType", res);
				// ��ѯ�û�����
				return "services/serConsultItem";
			} else {
				// ������ѯ���񶩵���ϸ��Ϣ
				List<ServiceItem> serviceItem = servicesService
						.selectServiceDetail(params2);
				Services services = servicesService
						.selectConsultDetail2(params2);
				model.addAttribute("consultDetail", serviceItem);
				model.addAttribute("services", services);
				model.addAttribute("userType", res);
				return "services/serConsultItem";
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}

	/**
	 * ������ѯ����״̬
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/createConSerItem.do")
	@ResponseBody
	public int createConSerItem(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String serviceId = request.getParameter("serviceId");
		int res = 0;
		try {
			String status = "2";
			Object[] params3 = new Object[] { status, serviceId };
			// ������ѯ����״̬
			res = servicesService.updateConSerStatus(params3);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
		return res;
	}

	/**
	 * ����ظ�
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/replyContent.do")
	@ResponseBody
	public int replyContent(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String serviceId = request.getParameter("serviceId");
		String replyContent = request.getParameter("replyContent");
		String userId = String.valueOf(session.getAttribute("userid"));

		try {
			Object[] params = new Object[] { userId };
			Object[] params2 = new Object[] { serviceId };
			// ��ȡ��ѯ������Ϣ
			Services services = servicesService.selectConsultDetail2(params2);
			Object[] params3 = new Object[] { services.getServiceName(),
					services.getServiceDesc(), replyContent, serviceId, userId };
			// ��ѯ��ǰ�û��Ƿ�Ϊ������ѯID
			int id = servicesService.selectUserPositionConsult(params);
			return servicesService.addReplyContent(params3, id);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}

	/**
	 * ��ѯ�������Ķ���״̬
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/updateServiceStatus.do")
	@ResponseBody
	public int updateServiceStatus(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String serviceId = request.getParameter("serviceId");

		try {
			String status = "3";
			Object[] params = new Object[] { status, serviceId };
			// �����޸���ѯ����״̬
			return servicesService.updateConSerStatus(params);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}

	/**
	 * ����Ͷ��
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/createComplaint.do")
	@ResponseBody
	public int createComplaint(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {

		String orderId = request.getParameter("orderId");
		String compType = request.getParameter("compType");
		String serDesc = request.getParameter("serDesc");
		String serDetail = request.getParameter("serDetail");
		String userId = String.valueOf(session.getAttribute("userid"));
		String levelVal = request.getParameter("select_level");
		String serName = "Ͷ��";
		String serType = "Ͷ��";
		String serStatus = "1";
		String levelName;
		if (levelVal.equals("1")) {
			levelName = "һ��Ͷ��";
		} else if (levelVal.equals("2")) {
			levelName = "����Ͷ��";
		} else {
			levelName = "����Ͷ��";
		}
		try {
			// ��ѯҵ�������ٵ�Ͷ�߷���ҵ��Ա
			User user = servicesService.allotCompUser();
			Object[] params = new Object[] { serName, serDesc, serType,
					compType, serStatus, user.getUserId(), userId, serDetail,
					orderId, levelVal, levelName };
			return servicesService.addComplaintOrder(params);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}

	/**
	 * �鿴Ͷ����ϸ��Ϣ
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/complaintDetail.do")
	public String complaintOrderItem(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		int stype = Integer.valueOf(request.getParameter("stype"));
		String serviceId = request.getParameter("serviceId");
		String userId = String.valueOf(session.getAttribute("userid"));
		Object[] params = new Object[] { serviceId };
		Object[] params4 = new Object[] { userId };
		// ��ѯ��ǰ�û��Ƿ�ΪͶ�߷���ID
		int uType = servicesService.selectUserPositionComplaint(params4);
		model.addAttribute("uType", uType);
		try {
			if (stype == 1) {
				// �û�����Ϊ�ͷ�

				// ���ݷ��񶩵�id��ѯ�ö�������
				Services services = servicesService.selectUserServices(params);
				model.addAttribute("services", services);
				Object[] params2 = new Object[] { services.getServiceName(),
						services.getServiceDesc(),
						services.getServiceComment(), services.getServiceId(),
						services.getServiceCreated(),
						services.getServiceCreatedBy() };
				Object[] params6 = new Object[] { services.getServiceId() };
				// ���ݶ���id��ѯ����Ӷ����������ݾͲ����
				int count = servicesService.selectSrvIdCompItem(params6);
				if (count == 0) {
					// ����󴴽�Ͷ���Ӷ���
					int res = servicesService.addComplaintOrderItem(params2);
					if (res == 1) {
						String status = "2";
						Object[] params3 = new Object[] { status, serviceId };
						// ����Ͷ�߶���״̬
						int res2 = servicesService.updateConSerStatus(params3);
					}
				}
				// ��ѯ��Ͷ�߶����������Ӷ�����Ϣ
				List<ServiceItem> srvItem = servicesService
						.selectServiceDetail(params);
				model.addAttribute("siList", srvItem);

			} else {
				// �û�����Ϊ��Ա
				// ���ݷ��񶩵�id��ѯ�ö�������
				Services services = servicesService.selectUserServices(params);
				model.addAttribute("services", services);
				// ��ѯ��Ͷ�߶����������Ӷ�����Ϣ
				List<ServiceItem> srvItem = servicesService
						.selectServiceDetail(params);
				model.addAttribute("siList", srvItem);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}

		return "services/services_complaint_item";
	}

	/**
	 * �ظ�Ͷ��
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/replyComplaint.do")
	@ResponseBody
	public int replyComplaint(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String serviceId = request.getParameter("serviceId");
		String replyComplaint = request.getParameter("replyComplaint");
		String userId = String.valueOf(session.getAttribute("userid"));
		Object[] params = new Object[] { serviceId };
		Object[] params2 = new Object[] { userId };
		try {
			// ���ݷ��񶩵�id��ѯ�ö�������
			Services services = servicesService.selectUserServices(params);
			Object[] params3 = new Object[] { services.getServiceName(),
					services.getServiceDesc(), replyComplaint,
					services.getServiceId(), userId };
			// ��ѯ��ǰ�û��Ƿ�ΪͶ�߷���ID
			int id = servicesService.selectUserPositionComplaint(params2);
			return servicesService.addReplyComplaintOrderItem(params3, id);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;

		}
	}

	/**
	 * ��ѯ�������Ķ���״̬(Ͷ��)
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/updateServiceStatusComp.do")
	@ResponseBody
	public int updateServiceStatusComp(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String serviceId = request.getParameter("serviceId");

		try {
			String status = "3";
			Object[] params = new Object[] { status, serviceId };
			// �����޸���ѯ����״̬
			return servicesService.updateConSerStatus(params);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}

	/**
	 * ����Ͷ�߼���ͬ��ѯ��ͬ����
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/selectQuery.do")
	public String selectQuery(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String level = request.getParameter("level");
		String userId = String.valueOf(session.getAttribute("userid"));
		try {
			Object[] params = new Object[] { userId };
			Object[] params2 = new Object[] { userId, level };
			// ��ѯ��ǰ�û��Ƿ�Ϊ����Ͷ��ID
			int id = servicesService.selectUserPositionComplaint(params);
			// ����servicesService����id��ѯͶ�߷���ļ���
			List<Services> sList = servicesService.selectComplaintListLevel(
					params2, id);
			model.addAttribute("scList", sList);
			// ����servicesService��ѯid�����ж������
			List<OrderFlat> oList = servicesService.selectUserOrderId(params);
			//��ѯͶ�߷�����ʷ����
			List<Services> hsList = servicesService.selectComplaintListHis(params, id);
			model.addAttribute("hsList", hsList);
			model.addAttribute("level", level);
			model.addAttribute("oList", oList);
			model.addAttribute("userType", id);
			return "services/services_complaint";
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ����orderId��ѯ��������
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOrderDetail.do")
	public String selectOrderDetail(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String orderId = request.getParameter("orderId");
		String type = request.getParameter("type");
		try {
			Object[] params = new Object[]{
					orderId	
				};
			//����service��ѯorderId������ϸ��Ϣ��ȡ�������
			OrderFlat order = servicesService.selectOrderDetail(params);
			model.addAttribute("od", order);
			model.addAttribute("type",type);
			return "services/services_return_apply";
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * �����˻����񶩵�
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/returnApply.do")
	@ResponseBody
	public int returnApply(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String orderId = request.getParameter("orderId");
		String returnDesc = request.getParameter("returnDesc");
		String returnComm = request.getParameter("returnComm");
		String userId = String.valueOf(session.getAttribute("userid"));
		String serName = "�˷���";
		String serType = "�˻�";
		String serStatus = "1";
		try {
			//����service��ѯ���ٹ��������ۺ�ͷ���Աid
			User user = servicesService.selectUserIdAss();
			Object[] params = new Object[]{
					serName,returnDesc,serType,serStatus,user.getUserId(),userId,returnComm,orderId
			};
			//����service�����˻����񶩵�
			return servicesService.createReturnOrder(params); 
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * �������޷��񶩵�
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/repairApply.do")
	@ResponseBody
	public int repairApply(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String orderId = request.getParameter("orderId");
		String repairDesc = request.getParameter("repairDesc");
		String repairComm = request.getParameter("repairComm");
		String userId = String.valueOf(session.getAttribute("userid"));
		String serName = "�˷���";
		String serType = "����";
		String serStatus = "1";
		try {
			//����service��ѯ���ٹ��������ۺ�ͷ���Աid
			User user = servicesService.selectUserIdAss();
			Object[] params = new Object[]{
					serName,repairDesc,serType,serStatus,user.getUserId(),userId,repairComm,orderId
			};
			//����service�����˻����񶩵�
			return servicesService.createRepairOrder(params); 
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * ��ѯ���񶩵�����
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectServDetail.do")
	public String selectServDetail(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String serviceId = request.getParameter("id");
		Object[] params = new Object[]{
				serviceId
		};
		try {
			//����service��ѯ���񶩵�����
			model.addAttribute("sd", servicesService.selectServDetail(params));
			return "services/services_return_details";
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * �����˷�������
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/disposeReturn.do")
	@ResponseBody
	public int disposeReturn(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String serviceId = request.getParameter("serviceId");
		String type = request.getParameter("type");
		try {
			Object[] params = new Object[]{
					type,serviceId
			};
			return servicesService.updateRtSerStatus(params);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * �����˷�������
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/disposeReturn2.do")
	@ResponseBody
	public int disposeReturn2(Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		String serviceId = request.getParameter("serviceId");
		String type = request.getParameter("type");
		String userId = String.valueOf(session.getAttribute("userid"));
		try {
			Object[] params = new Object[]{
					type,userId,serviceId
			};
			return servicesService.updateRtSerStatus2(params);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			throw e;
		}
	}
}
