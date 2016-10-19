package com.oept.esales.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.oept.esales.dao.AuthDao;
import com.oept.esales.model.Approval;
import com.oept.esales.model.ApprovalDetail;
import com.oept.esales.model.ApprovalItem;
import com.oept.esales.model.ApprovalItemPer;
import com.oept.esales.model.ApprovalStep;
import com.oept.esales.model.Auth;

/**
 * @author zhujj
 * Version: 1.0
 * Date: 2015/12/30
 * Description: Categories DAO implements.
 * Copyright (c) 2015 �Ϻ���ԯ�Ƽ����޹�˾. All rights reserved.
 */
@Repository("authDao")
public class AuthDaoImpl implements AuthDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate get_jdbc11() {
		return jdbcTemplate;
	}
	public void set_jdbc11(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	} 
	
	/**
	 * �Բ㼶Ϊ������ѯȨ��
	 */
	@Override
	public List<Auth> queryAuthLvl(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_perm where osa_perm_lvl = ?";
		return jdbcTemplate.query(sql, params, new RowMapper<Auth>(){

			@Override
			public Auth mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Auth auth = new Auth();
				auth.setId(rs.getString("osa_perm_id"));
				auth.setPerm_code(rs.getString("osa_perm_code"));
				auth.setPerm_name(rs.getString("osa_perm_name"));
				auth.setPar_id(rs.getString("osa_par_id"));
				auth.setPerm_lvl(rs.getString("osa_perm_lvl"));
				auth.setPerm_created(rs.getString("osa_created"));
				auth.setPerm_updated(rs.getString("osa_last_updated"));
				return auth;
			}
			
		});
	}
	
	/**
	 * ��ѯȨ�����㼶
	 */
	@Override
	public int queryAuthMaxLvl() throws Exception {
		// TODO Auto-generated method stub
		String sql = "select max(osa_perm_lvl) from osa_perm";
		return jdbcTemplate.queryForInt(sql);
	}
	
	/**
	 * ��ѯְλ����Ȩ��
	 */
	@Override
	public List<Auth> queryAuthItem(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select a.*,b.osa_perm_code from osa_perm_postn a inner join osa_perm b on a.osa_perm_id=b.osa_perm_id where a.osa_position_id = ?";
		return jdbcTemplate.query(sql, params, new RowMapper<Auth>(){

			@Override
			public Auth mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Auth auth = new Auth();
				auth.setPost_id(rs.getString("osa_perm_postn_id"));
				auth.setPerm_id(rs.getString("osa_perm_id"));
				auth.setPosition_id(rs.getString("osa_position_id"));
				auth.setPost_created(rs.getString("osa_created_by"));
				auth.setPost_created_by(rs.getString("osa_created_by"));
				auth.setPerm_code(rs.getString("osa_perm_code"));
				return auth;
			}
			
		});
	}
	
	/**
	 * ��ѯ��ְλ�Ƿ��Ѿ�ӵ�и�Ȩ��
	 */
	@Override
	public int queryThisAuthYoN(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) from osa_perm_postn where osa_perm_id = ? and osa_position_id = ?";
		return jdbcTemplate.queryForInt(sql, params);
	}
	
	/**
	 * Ϊְλ���Ȩ��
	 */
	@Override
	public int addAuthItem(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "insert into osa_perm_postn(osa_perm_id,osa_position_id,osa_created,osa_created_by) values(?,?,now(),?)";
		return jdbcTemplate.update(sql, params);
	}
	
	/**
	 * ɾ��ְλ�ĸ�Ȩ��
	 */
	@Override
	public int delAuthItem(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "delete from osa_perm_postn where osa_perm_postn_id = ?";
		return jdbcTemplate.update(sql, params);
	}
	
	/**
	 * ��ѯ��Ȩ��
	 */
	@Override
	public Auth queryAuth(Auth auth) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_perm ";
		String value = "";
		if(!auth.getId().equals("")&&auth.getId() != null){
			value = " where osa_perm_id = "+ auth.getId();
			sql = sql + value;
		}
		if(!"".equals(auth.getPar_id())&&auth.getPar_id()!=null){
			value = " where osa_par_id = "+ auth.getPar_id();
			sql = sql + value;
		}
		return jdbcTemplate.queryForObject(sql, new RowMapper<Auth>(){

			@Override
			public Auth mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Auth auth = new Auth();
				auth.setId(rs.getString("osa_perm_id"));
				auth.setPerm_code(rs.getString("osa_perm_code"));
				auth.setPerm_name(rs.getString("osa_perm_name"));
				auth.setPar_id(rs.getString("osa_par_id"));
				auth.setPerm_lvl(rs.getString("osa_perm_lvl"));
				auth.setPerm_created(rs.getString("osa_created"));
				auth.setPerm_updated(rs.getString("osa_last_updated"));
				return auth;
			}
			
		});
	}
	
	/**
	 * ��ѯ��Ȩ��������Ȩ��
	 */
	@Override
	public List<Auth> queryAuthPar(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_perm where osa_par_id = ?";
		return jdbcTemplate.query(sql, params, new RowMapper<Auth>(){

			@Override
			public Auth mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Auth auth = new Auth();
				auth.setId(rs.getString("osa_perm_id"));
				auth.setPerm_code(rs.getString("osa_perm_code"));
				auth.setPerm_name(rs.getString("osa_perm_name"));
				auth.setPar_id(rs.getString("osa_par_id"));
				auth.setPerm_lvl(rs.getString("osa_perm_lvl"));
				auth.setPerm_created(rs.getString("osa_created"));
				auth.setPerm_updated(rs.getString("osa_last_updated"));
				return auth;
			}
			
		});
	}
	
	/**
	 * ��ѯȨ��(boolean)(position,code)
	 */
	@Override
	public boolean queryPermissions(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) from (select pp.*,pe.osa_perm_code,pe.osa_perm_name,pe.osa_par_id,"
				+ "pe.osa_perm_lvl,pe.osa_created osa_perm_created,pe.osa_last_updated from osa_perm_postn pp,"
				+ "osa_perm pe where pp.osa_perm_id = pe.osa_perm_id) p where p.osa_position_id = ? and"
				+ " p.osa_perm_code = ?";
		int res = jdbcTemplate.queryForInt(sql, params);
		if(res != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ��˹��򷽷�--------------------------
	 */
	
	/**
	 * ������˹���
	 */
	@Override
	public int saveApprovalRule(Object[] params) throws Exception {
		final String sql = "insert into osa_approval_rule (osa_approval_rule_name,osa_object_cd,"
				+ "osa_object_name,osa_action_cd,osa_action_name,osa_rollback_type_cd,osa_rollback_type_name,"
				+ "osa_email_notify,osa_inbox_notify,osa_created,osa_created_by) "
				+ "values(?,?,?,?,?,?,?,?,?,now(),?)";
		final Object[] op = params;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
				      throws SQLException {
					     PreparedStatement ps = jdbcTemplate.getDataSource()
					       .getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
					     for(int i = 0;i<op.length;i++){
					    	 ps.setObject(i+1, op[i]);
					     }
					     return ps;
				    }
				   }, keyHolder);
		 return keyHolder.getKey().intValue();
		
	}
	
	/**
	 * ��ѯ��������
	 */
	@Override
	public List<Approval> queryApprovalRule() throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval_rule";
		return jdbcTemplate.query(sql, new RowMapper<Approval>(){

			@Override
			public Approval mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				Approval a = new Approval();
				a.setRule_id(rs.getString("osa_approval_rule_id"));
				a.setRule_name(rs.getString("osa_approval_rule_name"));
				a.setObject_cd(rs.getString("osa_object_cd"));
				a.setObject_name(rs.getString("osa_object_name"));
				a.setAction_cd(rs.getString("osa_action_cd"));
				a.setAction_name(rs.getString("osa_action_name"));
				a.setRollback_type_cd(rs.getString("osa_rollback_type_cd"));
				a.setRollback_type_name(rs.getString("osa_rollback_type_name"));
				a.setEmail( rs.getBoolean("osa_email_notify"));
				a.setInbox( rs.getBoolean("osa_inbox_notify"));
				a.setRule_created(rs.getString("osa_created"));
				a.setRule_created_by(rs.getString("osa_created_by"));
				a.setRule_updated(rs.getString("osa_updated"));
				a.setRule_updated_by(rs.getString("osa_updated_by"));
				return a;
			}
			
		});
	}
	
	/**
	 * ��ѯ��˹���(������ѯ)(name����)
	 */
	@Override
	public List<Approval> queryApprovalRuleCond(Approval approval) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval";
		String paramsValue = "";
		if(!approval.getRule_name().equals("")&&approval.getRule_name() != null){
			paramsValue = " where osa_approval_rule_name = " + approval.getRule_name();
			sql = sql + paramsValue;
		}
		if(!approval.getObject_cd().equals("")&&approval.getObject_cd() != null){
			paramsValue = " where osa_object_cd = " + approval.getObject_cd();
			sql = sql + paramsValue;
		}
		
		return jdbcTemplate.query(sql, new RowMapper<Approval>(){

			@Override
			public Approval mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				Approval a = new Approval();
				a.setRule_id(rs.getString("osa_approval_rule_id"));
				a.setRule_name(rs.getString("osa_approval_rule_name"));
				a.setObject_cd(rs.getString("osa_object_cd"));
				a.setObject_name(rs.getString("osa_object_name"));
				a.setAction_cd(rs.getString("osa_action_cd"));
				a.setAction_name(rs.getString("osa_action_name"));
				a.setRollback_type_cd(rs.getString("osa_rollback_type_cd"));
				a.setRollback_type_name(rs.getString("osa_rollback_type_name"));
				a.setEmail( rs.getBoolean("osa_email_notify"));
				a.setInbox( rs.getBoolean("osa_inbox_notify"));
				a.setRule_created(rs.getString("osa_created"));
				a.setRule_created_by(rs.getString("osa_created_by"));
				a.setRule_updated(rs.getString("osa_updated"));
				a.setRule_updated_by(rs.getString("osa_updated_by"));
				return a;
			}
			
		});
	}
	
	/**
	 * ����������̣����̵�һ����
	 */
	@Override
	public int saveApprovalRuleItemOne(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		final String sql2 = "insert into osa_approval_rule_item (osa_approval_rule_id,osa_approval_method_cd,"
				+ "osa_approval_method_name,osa_created,osa_created_by) values (?,?,?,now(),?);";
		final Object[] op2 = params;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
				      throws SQLException {
					     PreparedStatement ps = jdbcTemplate.getDataSource()
					       .getConnection().prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
					     for(int i = 0;i<op2.length;i++){
					    	 ps.setObject(i+1, op2[i]);
					     }
					     return ps;
				    }
				   }, keyHolder);
		 return keyHolder.getKey().intValue();
	}
	
	/**
	 * ����������̣����̵�һ����
	 */
	@Override
	public int saveApprovalRuleItemOne2(Object[] params) throws Exception {
		final String sql2 = "insert into osa_approval_rule_item (osa_approval_rule_id,osa_approval_method_cd,"
				+ "osa_approval_method_name,osa_created,osa_created_by) values (?,?,?,now(),?);";
		final Object[] op2 = params;
		 KeyHolder keyHolder = new GeneratedKeyHolder();  
		    jdbcTemplate.update(new PreparedStatementCreator() {  
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {  
		              
		            //String sql_sms = "insert into  sms(title,content,date_s,form,sffs,by1,by2,by3) values (?,?,'"+dates+"',?,?,?,?,?)";   
		               PreparedStatement ps = connection.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);  
		               for(int i = 0;i<op2.length;i++){
					    	 ps.setObject(i+1, op2[i]);
					     }
		               return ps;  
		        }  
		    }, keyHolder);  
		    
		    int generatedId = keyHolder.getKey().intValue();   
		    return generatedId;  
	}
	
	/**
	 * �����������
	 */
	@Override
	public int saveApprovalRuleItem(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		final String sql3 = "insert into osa_approval_rule_item (osa_approval_rule_id,osa_approval_method_cd,"
				+ "osa_approval_method_name,osa_item_par_id,osa_created,osa_created_by) values (?,?,?,?,now(),?)";
		final Object[] op3 = params;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
				      throws SQLException {
					     PreparedStatement ps = jdbcTemplate.getDataSource()
					       .getConnection().prepareStatement(sql3,Statement.RETURN_GENERATED_KEYS);
					     for(int i = 0;i<op3.length;i++){
					    	 ps.setObject(i+1, op3[i]);
					     }
					     return ps;
				    }
				   }, keyHolder);
		 return keyHolder.getKey().intValue();
	}
	
	/**
	 * �����������
	 */
	@Override
	public int saveApprovalRuleItem2(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		final String sql3 = "insert into osa_approval_rule_item (osa_approval_rule_id,osa_approval_method_cd,"
				+ "osa_approval_method_name,osa_item_par_id,osa_created,osa_created_by) values (?,?,?,?,now(),?)";
		final Object[] op3 = params;
		KeyHolder keyHolder = new GeneratedKeyHolder();  
	    jdbcTemplate.update(new PreparedStatementCreator() {  
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {  
	              
	            //String sql_sms = "insert into  sms(title,content,date_s,form,sffs,by1,by2,by3) values (?,?,'"+dates+"',?,?,?,?,?)";   
	               PreparedStatement ps = connection.prepareStatement(sql3,Statement.RETURN_GENERATED_KEYS);  
	               for(int i = 0;i<op3.length;i++){
				    	 ps.setObject(i+1, op3[i]);
				     }
	               return ps;  
	        }  
	    }, keyHolder);  
	    
	    int generatedId = keyHolder.getKey().intValue();   
	    return generatedId;  
	}
	
	/**
	 * ��ѯ���������Ϣ��������ѯ����id���ԣ�
	 */
	@Override
	public List<ApprovalItem> queryApprovalRuleItem(ApprovalItem approvalItem) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval_rule_item ";
		String paramsValue = "";
		if(approvalItem.getRule_id() != null&&!approvalItem.getRule_id().equals("")){
			paramsValue = " where osa_approval_rule_id = " + approvalItem.getRule_id();
		}
		sql = sql + paramsValue;
		return jdbcTemplate.query(sql, new RowMapper<ApprovalItem>(){

			@Override
			public ApprovalItem mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				ApprovalItem a = new ApprovalItem();
				a.setItem_id(rs.getString("osa_approval_item_id"));
				a.setRule_id(rs.getString("osa_approval_rule_id"));
				a.setMethod_cd(rs.getString("osa_approval_method_cd"));
				a.setMethod_name(rs.getString("osa_approval_method_name"));
				a.setPar_id(rs.getString("osa_item_par_id"));
				a.setCreated(rs.getString("osa_created"));
				a.setCreated_by(rs.getString("osa_created_by"));
				a.setUpdated(rs.getString("osa_updated"));
				a.setUpdated_by(rs.getString("osa_updated_by"));
				return a;
			}
			
		});
	}
	
	/**
	 * ���������̵������
	 */
	@Override
	public int saveApprovalItemUser(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "insert into osa_approval_item_per (osa_approval_item_id,osa_approval_user_id,osa_created,osa_created_by) values(?,?,now(),?)";
		return jdbcTemplate.update(sql, params);
	}
	
	/**
	 *  ��ѯ��˹����Ƿ��Ѵ���
	 */
	@Override
	public boolean queryApprovalRuleBoolean(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) from osa_approval_rule where osa_object_cd = ? and osa_action_cd = ?";
		int count = jdbcTemplate.queryForInt(sql, params);
		if(count == 0){
			return false;
		}else{
			return true;
		}
		
	}
	
	/**
	 * ��ѯ����������Ϣ������Approval����(id����)
	 */
	@Override
	public Approval queryApprovalRuleDetail(Object[] params) throws Exception {
		String sql = "select * from osa_approval_rule where osa_approval_rule_id = ?";
		return jdbcTemplate.queryForObject(sql, params, new RowMapper<Approval>(){

			@Override
			public Approval mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				Approval a = new Approval();
				a.setRule_id(rs.getString("osa_approval_rule_id"));
				a.setRule_name(rs.getString("osa_approval_rule_name"));
				a.setObject_cd(rs.getString("osa_object_cd"));
				a.setObject_name(rs.getString("osa_object_name"));
				a.setAction_cd(rs.getString("osa_action_cd"));
				a.setAction_name(rs.getString("osa_action_name"));
				a.setRollback_type_cd(rs.getString("osa_rollback_type_cd"));
				a.setRollback_type_name(rs.getString("osa_rollback_type_name"));
				a.setEmail( rs.getBoolean("osa_email_notify"));
				a.setInbox( rs.getBoolean("osa_inbox_notify"));
				a.setRule_created(rs.getString("osa_created"));
				a.setRule_created_by(rs.getString("osa_created_by"));
				a.setRule_updated(rs.getString("osa_updated"));
				a.setRule_updated_by(rs.getString("osa_updated_by"));
				return a;
			}
			
		});
	}
	
	/**
	 * ��ѯ�������������������Ϣ
	 */
	@Override
	public List<ApprovalItemPer> queryApprovalItemPer(ApprovalItemPer approvalItemPer)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval_item_per";
		String value = "";
		if(approvalItemPer.getItem_id() != null&&!approvalItemPer.getItem_id().equals("")){
			value = " where osa_approval_item_id = "+approvalItemPer.getItem_id()+"";
		}
		sql = sql + value;
		return jdbcTemplate.query(sql, new RowMapper<ApprovalItemPer>(){

			@Override
			public ApprovalItemPer mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				ApprovalItemPer a = new ApprovalItemPer();
				a.setItem_per_id(rs.getString("osa_approval_item_per_id"));
				a.setItem_id(rs.getString("osa_approval_item_id"));
				a.setUser_id(rs.getString("osa_approval_user_id"));
				a.setPer_created(rs.getString("osa_created"));
				a.setPer_created_by(rs.getString("osa_created_by"));
				return a;
			}
			
		});
	}
	
	/**
	 * ������˹���
	 */
	@Override
	public int updateApprovalRule(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "update osa_approval_rule set osa_approval_rule_name=?,osa_object_cd=?,osa_object_name=?,"
				+ "osa_action_cd=?,osa_action_name=?,osa_rollback_type_cd=?,osa_rollback_type_name=?,"
				+ "osa_email_notify=?,osa_inbox_notify=?,osa_updated=now(),osa_updated_by=? where osa_approval_rule_id = ?";
		return jdbcTemplate.update(sql, params);
	}
	
	/**
	 * ɾ���������Ϣ
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delApprovalItemPer(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "delete from osa_approval_item_per where osa_approval_item_id = ?";
		return jdbcTemplate.update(sql, params);
	}
	
	/**
	 * ɾ���������
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delApprovalItem(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "delete from osa_approval_rule_item where osa_approval_item_id = ?";
		return jdbcTemplate.update(sql, params);
	}
	
	/**
	 * ɾ����˹���
	 */
	@Override
	public int delApproval(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "delete from osa_approval_rule where osa_approval_rule_id = ?";
		return jdbcTemplate.update(sql, params);
	}
	
	//BEGIN �������ִ�нӿ�----------------------------------------------
	
	/**
	 * ����ObjectName�ж��Ƿ������Ч����˹���code��
	 */
	@Override
	public int queryAvailApprovalRule(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) from osa_approval_rule where osa_object_cd = ?";
		return jdbcTemplate.queryForInt(sql, params);
	}
	
	/**
	 * ����ObjectId��ѯapproval Step��
	 */
	@Override
	public List<ApprovalStep> queryApprovalStepId(ApprovalStep approvalStep) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval_step ";
		
		if(approvalStep.getId()!=null&&!approvalStep.getId().equals("")){
			sql = sql + " where osa_approval_step_id = '" + approvalStep.getId() + "'";
		}
		if(approvalStep.getOrder_id()!=null&&!approvalStep.getOrder_id().equals("")){
			sql = sql + " where osa_order_id = '" + approvalStep.getOrder_id() + "'";
		}
		if(approvalStep.getRequisition_id()!=null&&!approvalStep.getRequisition_id().equals("")){
			sql = sql + " where osa_requisition_id = '" + approvalStep.getRequisition_id() + "'";
		}
		if(approvalStep.getContract_id()!=null&&!approvalStep.getContract_id().equals("")){
			sql = sql + " where osa_contract_id = '" + approvalStep.getContract_id() + "'";
		}
		return jdbcTemplate.query(sql, new RowMapper<ApprovalStep>(){

			@Override
			public ApprovalStep mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				ApprovalStep as = new ApprovalStep();
				as.setId(rs.getString("osa_approval_step_id"));
				as.setPar_id(rs.getString("osa_par_step_id"));
				as.setRequisition_id(rs.getString("osa_requisition_id"));
				as.setContract_id(rs.getString("osa_contract_id"));
				as.setOrder_id(rs.getString("osa_order_id"));
				as.setStatus_cd(rs.getString("osa_step_status_cd"));
				as.setStatus_val(rs.getString("osa_step_status_val"));
				as.setMethod_cd(rs.getString("osa_approval_method_cd"));
				as.setMethod_name(rs.getString("osa_approval_method_name"));
				as.setRollback_type_cd(rs.getString("osa_rollback_type_cd"));
				as.setRollback_type_name(rs.getString("osa_rollback_type_name"));
				as.setProcess_flg(rs.getBoolean("osa_process_flg"));
				as.setCreated(rs.getString("osa_created"));
				as.setUpdated(rs.getString("osa_updated"));
				return as;
			}
			
		});
	}
	
	/**
	 * ����ObjectId��ѯapproval Step������ɾ����ѯ
	 */
	@Override
	public List<ApprovalStep> queryApprovalStepIdDel(ApprovalStep approvalStep) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval_step ";
		
		if(approvalStep.getOrder_id()!=null&&!approvalStep.getOrder_id().equals("")){
			sql = sql + " where osa_order_id = '" + approvalStep.getOrder_id() + "'";
		}else if(approvalStep.getRequisition_id()!=null&&!approvalStep.getRequisition_id().equals("")){
			sql = sql + " where osa_requisition_id = '" + approvalStep.getRequisition_id() + "'";
		}else if(approvalStep.getContract_id()!=null&&!approvalStep.getContract_id().equals("")){
			sql = sql + " where osa_contract_id = '" + approvalStep.getContract_id() + "'";
		}else if(approvalStep.getAccount_id()!=null&&!approvalStep.getAccount_id().equals("")){
			sql = sql + " where osa_account_id = '" + approvalStep.getAccount_id() + "'";
		}else if(approvalStep.getProduct_id()!=null&&!approvalStep.getProduct_id().equals("")){
			sql = sql + " where osa_product_id = '" + approvalStep.getProduct_id() + "'";
		}else{
			sql = sql + " where 1=0 ";
		}
		
		
		return jdbcTemplate.query(sql, new RowMapper<ApprovalStep>(){

			@Override
			public ApprovalStep mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				ApprovalStep as = new ApprovalStep();
				as.setId(rs.getString("osa_approval_step_id"));
				as.setPar_id(rs.getString("osa_par_step_id"));
				as.setRequisition_id(rs.getString("osa_requisition_id"));
				as.setContract_id(rs.getString("osa_contract_id"));
				as.setOrder_id(rs.getString("osa_order_id"));
				as.setAccount_id(rs.getString("osa_account_id"));
				as.setProduct_id(rs.getString("osa_product_id"));
				as.setStatus_cd(rs.getString("osa_step_status_cd"));
				as.setStatus_val(rs.getString("osa_step_status_val"));
				as.setMethod_cd(rs.getString("osa_approval_method_cd"));
				as.setMethod_name(rs.getString("osa_approval_method_name"));
				as.setRollback_type_cd(rs.getString("osa_rollback_type_cd"));
				as.setRollback_type_name(rs.getString("osa_rollback_type_name"));
				as.setProcess_flg(rs.getBoolean("osa_process_flg"));
				as.setCreated(rs.getString("osa_created"));
				as.setUpdated(rs.getString("osa_updated"));
				return as;
			}
			
		});
	}
	
	/**
	 * ɾ�������������ݷ���
	 */
	@Override
	public int delApprovalDetailStepId(ApprovalDetail approvalDetail) throws Exception {
		// TODO Auto-generated method stub
		String sql = "delete from osa_approval_detail ";

		if(approvalDetail.getId()!=null&&!approvalDetail.getId().equals("")){
			sql = sql + " where osa_approval_detail_id = '" + approvalDetail.getId() + "'";
		}
		if(approvalDetail.getStep_id()!=null&&!approvalDetail.getStep_id().equals("")){
			sql = sql + " where osa_approval_step_id = '" + approvalDetail.getStep_id() + "'";
		}
		return jdbcTemplate.update(sql);
	}
	
	/**
	 * ɾ����˲�������ݷ���
	 */
	@Override
	public int delApprovalStepId(ApprovalStep approvalStep) throws Exception {
		// TODO Auto-generated method stub
		String sql = "delete from osa_approval_step ";

		if(approvalStep.getId()!=null&&!approvalStep.getId().equals("")){
			sql = sql + " where osa_approval_step_id = '" + approvalStep.getId() + "'";
		}
		if(approvalStep.getOrder_id()!=null&&!approvalStep.getOrder_id().equals("")){
			sql = sql + " where osa_order_id = '" + approvalStep.getOrder_id() + "'";
		}
		if(approvalStep.getRequisition_id()!=null&&!approvalStep.getRequisition_id().equals("")){
			sql = sql + " where osa_requisition_id = '" + approvalStep.getRequisition_id() + "'";
		}
		if(approvalStep.getContract_id()!=null&&!approvalStep.getContract_id().equals("")){
			sql = sql + " where osa_contract_id = '" + approvalStep.getContract_id() + "'";
		}
		if(approvalStep.getAccount_id()!=null&&!approvalStep.getAccount_id().equals("")){
			sql = sql + " where osa_account_id = '" + approvalStep.getAccount_id() + "'";
		}
		if(approvalStep.getProduct_id()!=null&&!approvalStep.getProduct_id().equals("")){
			sql = sql + " where osa_product_id = '" + approvalStep.getProduct_id() + "'";
		}
		return jdbcTemplate.update(sql);
	}
	
	/**
	 * ��ѯObjectId step�������Ƿ�Ϊ��
	 */
	@Override
	public boolean queryApprovalStepValidData(ApprovalStep approvalStep)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) from osa_approval_step ";
		
		if(approvalStep.getId()!=null&&!approvalStep.getId().equals("")){
			sql = sql + " where osa_approval_step_id = '" + approvalStep.getId() + "'";
		}
		if(approvalStep.getOrder_id()!=null&&!approvalStep.getOrder_id().equals("")){
			sql = sql + " where osa_order_id = '" + approvalStep.getOrder_id() + "'";
		}
		if(approvalStep.getRequisition_id()!=null&&!approvalStep.getRequisition_id().equals("")){
			sql = sql + " where osa_requisition_id = '" + approvalStep.getRequisition_id() + "'";
		}
		if(approvalStep.getContract_id()!=null&&!approvalStep.getContract_id().equals("")){
			sql = sql + " where osa_contract_id = '" + approvalStep.getContract_id() + "'";
		}
		if(approvalStep.getAccount_id()!=null&&!approvalStep.getAccount_id().equals("")){
			sql = sql + " where osa_account_id = '" + approvalStep.getAccount_id() + "'";
		}
		if(approvalStep.getProduct_id()!=null&&!approvalStep.getProduct_id().equals("")){
			sql = sql + " where osa_product_id = '" + approvalStep.getProduct_id() + "'";
		}
		int res = jdbcTemplate.queryForInt(sql);
		if(res !=0 ){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ��ѯObjectId Detail�������Ƿ�Ϊ��
	 */
	@Override
	public boolean queryApprovalDetailValidData(ApprovalDetail approvalDetail)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) from osa_approval_detail ";

		if(approvalDetail.getId()!=null&&!approvalDetail.getId().equals("")){
			sql = sql + " where osa_approval_detail_id = '" + approvalDetail.getId() + "'";
		}
		if(approvalDetail.getStep_id()!=null&&!approvalDetail.getStep_id().equals("")){
			sql = sql + " where osa_approval_step_id = '" + approvalDetail.getStep_id() + "'";
		}
		int res =  jdbcTemplate.queryForInt(sql);
		if(res != 0)
		return true;
		else
		return false;
	}
	
	/**
	 * ������ѯ����ѯ��˹���
	 */
	@Override
	public Approval queryCondApprovalRule(Approval approval)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval_rule ";
		String paramsValue = "";
		if(approval.getObject_cd() != null&&!approval.getObject_cd().equals("")){
			paramsValue = " where osa_object_cd = '" + approval.getObject_cd() + "'";
			sql = sql + paramsValue;
		}
		if(approval.getRule_name() != null&&!approval.getRule_name().equals("")){
			paramsValue = " where osa_approval_rule_name = '" + approval.getRule_name() + "'";
			sql = sql + paramsValue;
		}
		
		return jdbcTemplate.queryForObject(sql, new RowMapper<Approval>(){

			@Override
			public Approval mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				Approval a = new Approval();
				a.setRule_id(rs.getString("osa_approval_rule_id"));
				a.setRule_name(rs.getString("osa_approval_rule_name"));
				a.setObject_cd(rs.getString("osa_object_cd"));
				a.setObject_name(rs.getString("osa_object_name"));
				a.setAction_cd(rs.getString("osa_action_cd"));
				a.setAction_name(rs.getString("osa_action_name"));
				a.setRollback_type_cd(rs.getString("osa_rollback_type_cd"));
				a.setRollback_type_name(rs.getString("osa_rollback_type_name"));
				a.setEmail( rs.getBoolean("osa_email_notify"));
				a.setInbox( rs.getBoolean("osa_inbox_notify"));
				a.setRule_created(rs.getString("osa_created"));
				a.setRule_created_by(rs.getString("osa_created_by"));
				a.setRule_updated(rs.getString("osa_updated"));
				a.setRule_updated_by(rs.getString("osa_updated_by"));
				return a;
			}
			
		});
	}
	
	/**
	 * ������ִ�в���
	 */
	@Override
	public int addApprovalStep(Object[] params,boolean firstStep,String idType) throws Exception {
		// TODO Auto-generated method stub
		String selectSQL = "";
		if(firstStep == true){
			if(idType.equals("Order")){
				//��һ����id����Ϊorder
				selectSQL = "insert into osa_approval_step (osa_order_id,osa_step_status_cd,osa_step_status_val,"
						+ "osa_approval_method_cd,osa_approval_method_name,osa_rollback_type_cd,osa_rollback_type_name,"
						+ "osa_process_flg,osa_created) values (?,?,?,?,?,?,?,?,now())";
			}else if(idType.equals("Requisition")){
				//��һ����id����ΪRequisition
				selectSQL = "insert into osa_approval_step (osa_requisition_id,osa_step_status_cd,osa_step_status_val,"
						+ "osa_approval_method_cd,osa_approval_method_name,osa_rollback_type_cd,osa_rollback_type_name,"
						+ "osa_process_flg,osa_created) values (?,?,?,?,?,?,?,?,now())";
			}else if(idType.equals("Contract")){
				//��һ����id����ΪContract
				selectSQL = "insert into osa_approval_step (osa_contract_id,osa_step_status_cd,osa_step_status_val,"
						+ "osa_approval_method_cd,osa_approval_method_name,osa_rollback_type_cd,osa_rollback_type_name,"
						+ "osa_process_flg,osa_created) values (?,?,?,?,?,?,?,?,now())";
			}else if(idType.equals("Account")){
				//��һ����id����ΪAccount
				selectSQL = "insert into osa_approval_step (osa_account_id,osa_step_status_cd,osa_step_status_val,"
						+ "osa_approval_method_cd,osa_approval_method_name,osa_rollback_type_cd,osa_rollback_type_name,"
						+ "osa_process_flg,osa_created) values (?,?,?,?,?,?,?,?,now())";
			}else if(idType.equals("Product")){
				//��һ����id����ΪProduct
				selectSQL = "insert into osa_approval_step (osa_product_id,osa_step_status_cd,osa_step_status_val,"
						+ "osa_approval_method_cd,osa_approval_method_name,osa_rollback_type_cd,osa_rollback_type_name,"
						+ "osa_process_flg,osa_created) values (?,?,?,?,?,?,?,?,now())";
			}
		}else{
			if(idType.equals("Order")){
				//�ǵ�һ����id����ΪOrder
				selectSQL = "insert into osa_approval_step (osa_par_step_id,osa_order_id,osa_step_status_cd,osa_step_status_val,"
						+ "osa_approval_method_cd,osa_approval_method_name,osa_rollback_type_cd,osa_rollback_type_name,"
						+ "osa_process_flg,osa_created) values (?,?,?,?,?,?,?,?,?,now())";
			}else if(idType.equals("Requisition")){
				//�ǵ�һ����id����ΪRequisition
				selectSQL = "insert into osa_approval_step (osa_par_step_id,osa_requisition_id,osa_step_status_cd,osa_step_status_val,"
						+ "osa_approval_method_cd,osa_approval_method_name,osa_rollback_type_cd,osa_rollback_type_name,"
						+ "osa_process_flg,osa_created) values (?,?,?,?,?,?,?,?,?,now())";
			}else if(idType.equals("Contract")){
				//�ǵ�һ����id����ΪContract
				selectSQL = "insert into osa_approval_step (osa_par_step_id,osa_contract_id,osa_step_status_cd,osa_step_status_val,"
						+ "osa_approval_method_cd,osa_approval_method_name,osa_rollback_type_cd,osa_rollback_type_name,"
						+ "osa_process_flg,osa_created) values (?,?,?,?,?,?,?,?,?,now())";
			}else if(idType.equals("Account")){
				//�ǵ�һ����id����ΪAccount
				selectSQL = "insert into osa_approval_step (osa_par_step_id,osa_account_id,osa_step_status_cd,osa_step_status_val,"
						+ "osa_approval_method_cd,osa_approval_method_name,osa_rollback_type_cd,osa_rollback_type_name,"
						+ "osa_process_flg,osa_created) values (?,?,?,?,?,?,?,?,?,now())";
			}else if(idType.equals("Product")){
				//�ǵ�һ����id����ΪProduct
				selectSQL = "insert into osa_approval_step (osa_par_step_id,osa_product_id,osa_step_status_cd,osa_step_status_val,"
						+ "osa_approval_method_cd,osa_approval_method_name,osa_rollback_type_cd,osa_rollback_type_name,"
						+ "osa_process_flg,osa_created) values (?,?,?,?,?,?,?,?,?,now())";
			}
		}
		final String sql = selectSQL;
		final Object[] objects = params;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
				      throws SQLException {
					     PreparedStatement ps = jdbcTemplate.getDataSource()
					       .getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
					     for(int i = 0;i<objects.length;i++){
					    	 ps.setObject(i+1, objects[i]);
					     }
					     return ps;
				    }
				   }, keyHolder);
		 return keyHolder.getKey().intValue();
	}
	
	/**
	 * ������ִ�в�����ϸ��Ϣ
	 */
	@Override
	public int addApprovalDetail(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "insert into osa_approval_detail (osa_approval_step_id,osa_approval_status_cd,"
				+ "osa_approval_status_val,osa_to_approve,osa_approval_method_cd,osa_approval_method_name,"
				+ "osa_rollback_type_cd,osa_rollback_type_name,osa_created) values (?,?,?,?,?,?,?,?,now())";
		return jdbcTemplate.update(sql, params);
	}
	
	/**
	 * ��ѯobjectId���е���˲���
	 */
	@Override
	public List<ApprovalStep> queryApprovalSteps(ApprovalStep approvalStep)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval_step ";
		
		if(approvalStep.getId()!=null&&!approvalStep.getId().equals("")){
			sql = sql + " where osa_approval_step_id = '" + approvalStep.getId() + "'";
		}
		if(approvalStep.getOrder_id()!=null&&!approvalStep.getOrder_id().equals("")){
			sql = sql + " where osa_order_id = '" + approvalStep.getOrder_id() + "'";
		}
		if(approvalStep.getRequisition_id()!=null&&!approvalStep.getRequisition_id().equals("")){
			sql = sql + " where osa_requisition_id = '" + approvalStep.getRequisition_id() + "'";
		}
		if(approvalStep.getContract_id()!=null&&!approvalStep.getContract_id().equals("")){
			sql = sql + " where osa_contract_id = '" + approvalStep.getContract_id() + "'";
		}
		if(approvalStep.getAccount_id()!=null&&!approvalStep.getAccount_id().equals("")){
			sql = sql + " where osa_account_id = '" + approvalStep.getAccount_id() + "'";
		}
		if(approvalStep.getProduct_id()!=null&&!approvalStep.getProduct_id().equals("")){
			sql = sql + " where osa_product_id = '" + approvalStep.getProduct_id() + "'";
		}
		
		
		return jdbcTemplate.query(sql, new RowMapper<ApprovalStep>(){

			@Override
			public ApprovalStep mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				ApprovalStep as = new ApprovalStep();
				as.setId(rs.getString("osa_approval_step_id"));
				as.setPar_id(rs.getString("osa_par_step_id"));
				as.setRequisition_id(rs.getString("osa_requisition_id"));
				as.setContract_id(rs.getString("osa_contract_id"));
				as.setOrder_id(rs.getString("osa_order_id"));
				as.setAccount_id(rs.getString("osa_account_id"));
				as.setProduct_id(rs.getString("osa_product_id"));
				as.setStatus_cd(rs.getString("osa_step_status_cd"));
				as.setStatus_val(rs.getString("osa_step_status_val"));
				as.setMethod_cd(rs.getString("osa_approval_method_cd"));
				as.setMethod_name(rs.getString("osa_approval_method_name"));
				as.setRollback_type_cd(rs.getString("osa_rollback_type_cd"));
				as.setRollback_type_name(rs.getString("osa_rollback_type_name"));
				as.setProcess_flg(rs.getBoolean("osa_process_flg"));
				as.setCreated(rs.getString("osa_created"));
				as.setUpdated(rs.getString("osa_updated"));
				return as;
			}
			
		});
	}
	
	/**
	 * ��ѯ�Ƿ������Ч����
	 */
	@Override
	public boolean queryApprovalStepValidFlag(ApprovalStep approvalStep)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) from osa_approval_step ";
		
		if(approvalStep.getOrder_id()!=null&&!approvalStep.getOrder_id().equals("")){
			sql = sql + " where osa_process_flg = true and osa_order_id = '" + approvalStep.getOrder_id() + "'";
		}
		if(approvalStep.getRequisition_id()!=null&&!approvalStep.getRequisition_id().equals("")){
			sql = sql + " where osa_process_flg = true and osa_requisition_id = '" + approvalStep.getRequisition_id() + "'";
		}
		if(approvalStep.getContract_id()!=null&&!approvalStep.getContract_id().equals("")){
			sql = sql + " where osa_process_flg = true and osa_contract_id = '" + approvalStep.getContract_id() + "'";
		}
		if(approvalStep.getAccount_id()!=null&&!approvalStep.getAccount_id().equals("")){
			sql = sql + " where osa_process_flg = true and osa_account_id = '" + approvalStep.getAccount_id() + "'";
		}
		if(approvalStep.getProduct_id()!=null&&!approvalStep.getProduct_id().equals("")){
			sql = sql + " where osa_process_flg = true and osa_product_id = '" + approvalStep.getProduct_id() + "'";
		}
		
		int res = jdbcTemplate.queryForInt(sql);
		if(res != 0)
			return true;
		else
			return false;
	}
	
	/**
	 * ��ѯObjectId����ִ�е���˲���
	 */
	@Override
	public ApprovalStep queryApprovalStepFlag(ApprovalStep approvalStep)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval_step ";
		
		if(approvalStep.getId()!=null&&!approvalStep.getId().equals("")){
			sql = sql + " where osa_approval_step_id = '" + approvalStep.getId() + "'";
		}
		if(approvalStep.getPar_id()!=null&&!approvalStep.getPar_id().equals("")){
			sql = sql + " where osa_par_step_id = '" + approvalStep.getPar_id() + "'";
		}
		if(approvalStep.getOrder_id()!=null&&!approvalStep.getOrder_id().equals("")){
			sql = sql + " where osa_process_flg = true and osa_order_id = '" + approvalStep.getOrder_id() + "'";
		}
		if(approvalStep.getRequisition_id()!=null&&!approvalStep.getRequisition_id().equals("")){
			sql = sql + " where osa_process_flg = true and osa_requisition_id = '" + approvalStep.getRequisition_id() + "'";
		}
		if(approvalStep.getContract_id()!=null&&!approvalStep.getContract_id().equals("")){
			sql = sql + " where osa_process_flg = true and osa_contract_id = '" + approvalStep.getContract_id() + "'";
		}
		if(approvalStep.getAccount_id()!=null&&!approvalStep.getAccount_id().equals("")){
			sql = sql + " where osa_process_flg = true and osa_account_id = '" + approvalStep.getAccount_id() + "'";
		}
		if(approvalStep.getProduct_id()!=null&&!approvalStep.getProduct_id().equals("")){
			sql = sql + " where osa_process_flg = true and osa_product_id = '" + approvalStep.getProduct_id() + "'";
		}
		
		return jdbcTemplate.queryForObject(sql, new RowMapper<ApprovalStep>(){

			@Override
			public ApprovalStep mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				ApprovalStep as = new ApprovalStep();
				as.setId(rs.getString("osa_approval_step_id"));
				as.setPar_id(rs.getString("osa_par_step_id"));
				as.setRequisition_id(rs.getString("osa_requisition_id"));
				as.setContract_id(rs.getString("osa_contract_id"));
				as.setOrder_id(rs.getString("osa_order_id"));
				as.setAccount_id(rs.getString("osa_account_id"));
				as.setProduct_id(rs.getString("osa_product_id"));
				as.setStatus_cd(rs.getString("osa_step_status_cd"));
				as.setStatus_val(rs.getString("osa_step_status_val"));
				as.setMethod_cd(rs.getString("osa_approval_method_cd"));
				as.setMethod_name(rs.getString("osa_approval_method_name"));
				as.setRollback_type_cd(rs.getString("osa_rollback_type_cd"));
				as.setRollback_type_name(rs.getString("osa_rollback_type_name"));
				as.setProcess_flg(rs.getBoolean("osa_process_flg"));
				as.setCreated(rs.getString("osa_created"));
				as.setUpdated(rs.getString("osa_updated"));
				return as;
			}
			
		});
	}
	
	/**
	 * ��ѯ������Ϣ����
	 */
	@Override
	public ApprovalStep queryApprovalStep(ApprovalStep approvalStep)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval_step ";
		
		if(approvalStep.getId()!=null&&!approvalStep.getId().equals("")){
			sql = sql + " where osa_approval_step_id = '" + approvalStep.getId() + "'";
		}
		if(approvalStep.getPar_id()!=null&&!approvalStep.getPar_id().equals("")){
			sql = sql + " where osa_par_step_id = '" + approvalStep.getPar_id() + "'";
		}
		
		return jdbcTemplate.queryForObject(sql, new RowMapper<ApprovalStep>(){

			@Override
			public ApprovalStep mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				ApprovalStep as = new ApprovalStep();
				as.setId(rs.getString("osa_approval_step_id"));
				as.setPar_id(rs.getString("osa_par_step_id"));
				as.setRequisition_id(rs.getString("osa_requisition_id"));
				as.setContract_id(rs.getString("osa_contract_id"));
				as.setOrder_id(rs.getString("osa_order_id"));
				as.setStatus_cd(rs.getString("osa_step_status_cd"));
				as.setStatus_val(rs.getString("osa_step_status_val"));
				as.setMethod_cd(rs.getString("osa_approval_method_cd"));
				as.setMethod_name(rs.getString("osa_approval_method_name"));
				as.setRollback_type_cd(rs.getString("osa_rollback_type_cd"));
				as.setRollback_type_name(rs.getString("osa_rollback_type_name"));
				as.setProcess_flg(rs.getBoolean("osa_process_flg"));
				as.setCreated(rs.getString("osa_created"));
				as.setUpdated(rs.getString("osa_updated"));
				return as;
			}
			
		});
	}
	
	/**
	 * ��ѯ��˲����µ���ϸ��Ϣ
	 */
	@Override
	public List<ApprovalDetail> queryApprovalDetailCond(
			ApprovalDetail approvalDetail) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval_detail ";
		if(approvalDetail.getStep_id() != null && !approvalDetail.getStep_id().equals("")){
			sql = sql + " where osa_approval_step_id = '" + approvalDetail.getStep_id() + "'";
		}
		
		return jdbcTemplate.query(sql, new RowMapper<ApprovalDetail>(){

			@Override
			public ApprovalDetail mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				ApprovalDetail ad = new ApprovalDetail();
				ad.setId(rs.getString("osa_approval_detail_id"));
				ad.setStep_id(rs.getString("osa_approval_step_id"));
				ad.setStatus_cd(rs.getString("osa_approval_status_cd"));
				ad.setStatus_val(rs.getString("osa_approval_status_val"));
				ad.setTo_approve(rs.getString("osa_to_approve"));
				ad.setMethod_cd(rs.getString("osa_approval_method_cd"));
				ad.setMethod_name(rs.getString("osa_approval_method_name"));
				ad.setRollback_type_cd(rs.getString("osa_rollback_type_cd"));
				ad.setRollback_type_name(rs.getString("osa_rollback_type_name"));
				ad.setCreated(rs.getString("osa_created"));
				return ad;
			}
			
		});
	}
	
	/**
	 * ���������ϸ��Ϣ
	 */
	@Override
	public int updateApprovalDetailToApprove(
			Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "update osa_approval_detail set osa_approval_status_cd = ?,osa_approval_status_val = ?,osa_created = now() where osa_approval_step_id = ? and osa_to_approve = ?"; 
		
		return jdbcTemplate.update(sql,params);
	}
	
	/**
	 * ������˲������״̬
	 */
	@Override
	public int updateApprovalStepFlag(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "update osa_approval_step set osa_process_flg = ?,osa_step_status_cd = ?,osa_step_status_val = ?,osa_updated = now() where osa_approval_step_id = ?";
		return jdbcTemplate.update(sql,params);
	}
	
	/**
	 * ��ѯ�����Ƿ�����һ��
	 */
	@Override
	public boolean queryApprovalStepNext(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) from osa_approval_step where osa_par_step_id = ?";
		int res = jdbcTemplate.queryForInt(sql,params);
		if(res != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ������һ����˲������״̬
	 */
	@Override
	public int updateNextApprovalStepFlag(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "update osa_approval_step set osa_process_flg = ?,osa_updated = now() where osa_par_step_id = ?";
		return jdbcTemplate.update(sql,params);
	}
	
	/**
	 * ��ѯ�����Ƿ�Ϊ��һ��
	 */
	@Override
	public boolean queryApprovalStepFirst(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) from osa_approval_step where osa_par_step_id is null and osa_approval_step_id = ?";
		int res = jdbcTemplate.queryForInt(sql,params);
		if(res != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ��ʼ��������ϸ��¼����
	 */
	@Override
	public int updateApprovalDetailInit(Object[] params) throws Exception {
		// TODO Auto-generated method stub
		String sql = "update osa_approval_detail set osa_approval_status_cd = ?,osa_approval_status_val = ?,osa_created = now() where osa_approval_step_id = ?";
		return jdbcTemplate.update(sql, params);
	}
	
	/**
	 * ��ѯ���û���Ҫ�������������״̬
	 */
	@Override
	public ApprovalDetail queryApprovalDetailUserId(Object[] params)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from osa_approval_detail where osa_approval_step_id = ? and osa_to_approve = ?";
		return jdbcTemplate.queryForObject(sql, params, new RowMapper<ApprovalDetail>(){

			@Override
			public ApprovalDetail mapRow(ResultSet rs, int arg1)
					throws SQLException {
				// TODO Auto-generated method stub
				ApprovalDetail ad = new ApprovalDetail();
				ad.setId(rs.getString("osa_approval_detail_id"));
				ad.setStep_id(rs.getString("osa_approval_step_id"));
				ad.setStatus_cd(rs.getString("osa_approval_status_cd"));
				ad.setStatus_val(rs.getString("osa_approval_status_val"));
				ad.setTo_approve(rs.getString("osa_to_approve"));
				ad.setMethod_cd(rs.getString("osa_approval_method_cd"));
				ad.setMethod_name(rs.getString("osa_approval_method_name"));
				ad.setRollback_type_cd(rs.getString("osa_rollback_type_cd"));
				ad.setRollback_type_name(rs.getString("osa_rollback_type_name"));
				ad.setCreated(rs.getString("osa_created"));
				return ad;
			}
			
		});
	}
	
	
	
	//END �������ִ�нӿ�----------------------------------------------
}
