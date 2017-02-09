package com.chai.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;

import com.chai.hibernartesessionfactory.HibernateSessionFactoryClass;
import com.chai.model.UserBeanForUserForm;
import com.chai.model.views.AdmUserV;
import com.chai.util.CalendarUtil;
import com.chai.util.GetJsonResultSet;

public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class);
	String lastInsertUserId="";
	SessionFactory sf1 = HibernateSessionFactoryClass.getSessionAnnotationFactory();
	public static ArrayList<Object> validateUserLogin(String x_LOGIN_NAME, String x_PASSWORD) {
		System.out.println("-- UserService.validateUserLogin() mehtod called: -- ");
		SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
		Session session = sf.openSession();
		String sql = "SELECT week(date_sub(now(),INTERVAL 1 WEEK)) AS PREVIOUS_WEEK_OF_YEAR, USER_ID, "
				+ " COMPANY_ID, "
				+ " FIRST_NAME, "
				+ " LAST_NAME, "
				+ " ROLE_ID, " 
				+ "	ROLE_NAME, "
				+ "	USER_TYPE_ID, "
				+ " USER_TYPE_CODE, "  
				+ " USER_TYPE_NAME,"
				+ " WAREHOUSE_ID, "
				+ "	WAREHOUSE_NAME " 
				+ " FROM ADM_USERS_V " 
				+ " WHERE LOGIN_NAME =:loginName "
				+ "   AND PASSWORD =:password "
				+ "   AND STATUS = 'A' "
				+ " AND UPPER(ROLE_NAME) <> 'CCO' "
				+ " AND USER_TYPE_ID = F_GET_TYPE('USER TYPES','ADMIN') ";
		AdmUserV userData = new AdmUserV();
		ArrayList<Object> loginListBeanAndTimebean = new ArrayList<>();
		try {
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			query.setParameter("loginName", x_LOGIN_NAME);
			query.setParameter("password", x_PASSWORD);
			List<AdmUserV> list = query.list();
			if(list.size()==1){
				 for(Object object : list)
			     {
			        Map row = (Map)object;
			        userData.setX_USER_ID((Integer)row.get("USER_ID"));
			        userData.setX_COMPANY_ID((Integer)row.get("COMPANY_ID"));
			        userData.setX_FIRST_NAME((String)row.get("FIRST_NAME"));
			        userData.setX_LAST_NAME((String)row.get("LAST_NAME"));
			        userData.setX_ROLE_ID((Integer)row.get("ROLE_ID"));
			        userData.setX_ROLE_NAME((String)row.get("ROLE_NAME"));
			        userData.setX_USER_TYPE_ID((Integer)row.get("USER_TYPE_ID"));
			        userData.setX_USER_TYPE_CODE((String)row.get("USER_TYPE_CODE"));
			        userData.setX_USER_TYPE_NAME((String)row.get("USER_TYPE_NAME"));
			        userData.setX_WAREHOUSE_ID((Integer)row.get("WAREHOUSE_ID"));
			        userData.setX_WAREHOUSE_NAME((String)row.get("WAREHOUSE_NAME"));
					loginListBeanAndTimebean.add(userData);
					loginListBeanAndTimebean.add(row.get("PREVIOUS_WEEK_OF_YEAR"));
				}
			}
			// System.out.println("list size "+list.size());
		} catch (Exception e) {
			loginListBeanAndTimebean = null;
			e.printStackTrace();
		} finally {
			session.close();
		}
		return loginListBeanAndTimebean;
	}
	public  JSONArray getUserListPageData(AdmUserV userBean) {
		System.out.println("-- UserService.getUserListPageData mehtod called: -- ");
		Session session = sf1.openSession();
		String warehoseRole=userBean.getX_ROLE_NAME();
		String x_query="";
		if(warehoseRole.equals("SIO")
				|| warehoseRole.equals("SIFP")
				|| warehoseRole.equals("SCCO")){
		 x_query="SELECT USER_ID,	COMPANY_ID,  	FIRST_NAME,	LAST_NAME,	"
					+ "WAREHOUSE_NAME,	WAREHOUSE_ID,	LOGIN_NAME,	PASSWORD,	ACTIVATED,"
					+ "	DATE_FORMAT(ACTIVATED_ON, '%d-%b-%Y') ACTIVATED_ON,	USER_TYPE_NAME,	"
					+ "USER_TYPE_ID,	STATUS,	ROLE_ID, 	ROLE_NAME,	ROLE_DETAILS, "
					+ "	DATE_FORMAT(START_DATE, '%d-%b-%Y') START_DATE, 	"
					+ "DATE_FORMAT(END_DATE, '%d-%b-%Y') END_DATE, 	EMAIL, 	TELEPHONE_NUMBER, "
					+ "	(SELECT COUNT(*) 	   FROM ADM_USER_WAREHOUSE_ASSIGNMENTS WHA 	 "
					+ " WHERE WHA.USER_ID = USR.USER_ID 	    AND WHA.STATUS = 'A') FACILITY_FLAG "
					+ "FROM ADM_USERS_V USR   WHERE WAREHOUSE_ID IN (SELECT WAREHOUSE_ID  FROM INVENTORY_WAREHOUSES "
					+ " WHERE DEFAULT_ORDERING_WAREHOUSE_ID = "+userBean.getX_WAREHOUSE_ID()+")  OR WAREHOUSE_ID = "+userBean.getX_WAREHOUSE_ID()+" ORDER BY FIRST_NAME";
		}else if(warehoseRole.equals("LIO")
				|| warehoseRole.equals("MOH")){
			String roleNameForConditon=warehoseRole.equals("LIO")?"MOH":"LIO";
			 x_query="SELECT USER_ID,	COMPANY_ID,  	FIRST_NAME,	LAST_NAME,	"
						+ "WAREHOUSE_NAME,	WAREHOUSE_ID,	LOGIN_NAME,	PASSWORD,	ACTIVATED,"
						+ "	DATE_FORMAT(ACTIVATED_ON, '%d-%b-%Y') ACTIVATED_ON,	USER_TYPE_NAME,	"
						+ "USER_TYPE_ID,	STATUS,	ROLE_ID, 	ROLE_NAME,	ROLE_DETAILS, "
						+ "	DATE_FORMAT(START_DATE, '%d-%b-%Y') START_DATE, 	"
						+ "DATE_FORMAT(END_DATE, '%d-%b-%Y') END_DATE, 	EMAIL, 	TELEPHONE_NUMBER, "
						+ "	(SELECT COUNT(*) 	   FROM ADM_USER_WAREHOUSE_ASSIGNMENTS WHA 	 "
						+ " WHERE WHA.USER_ID = USR.USER_ID 	    AND WHA.STATUS = 'A') FACILITY_FLAG "
						+ "FROM ADM_USERS_V USR  WHERE WAREHOUSE_ID = "+userBean.getX_WAREHOUSE_ID()
						+" AND ROLE_ID <> (SELECT ROLE_ID FROM ADM_ROLES "
						+ " WHERE ROLE_NAME = '"+roleNameForConditon+"') ORDER BY FIRST_NAME";
		}else if(warehoseRole.equals("NTO")){
			 x_query="SELECT USER_ID,	COMPANY_ID,  	FIRST_NAME,	LAST_NAME,	"
						+ "WAREHOUSE_NAME,	WAREHOUSE_ID,	LOGIN_NAME,	PASSWORD,	ACTIVATED,"
						+ "	DATE_FORMAT(ACTIVATED_ON, '%d-%b-%Y') ACTIVATED_ON,	USER_TYPE_NAME,	"
						+ "USER_TYPE_ID,	STATUS,	ROLE_ID, 	ROLE_NAME,	ROLE_DETAILS, "
						+ "	DATE_FORMAT(START_DATE, '%d-%b-%Y') START_DATE, 	"
						+ "DATE_FORMAT(END_DATE, '%d-%b-%Y') END_DATE, 	EMAIL, 	TELEPHONE_NUMBER, "
						+ "	(SELECT COUNT(*) 	   FROM ADM_USER_WAREHOUSE_ASSIGNMENTS WHA 	 "
						+ " WHERE WHA.USER_ID = USR.USER_ID 	    AND WHA.STATUS = 'A') FACILITY_FLAG "
						+ "FROM ADM_USERS_V USR ";
		}
		JSONArray array = null;
		try {
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjson(resultlist);
		} catch (org.hibernate.exception.JDBCConnectionException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
//		System.out.println("result list size"+resultlist.size());

		return array;
		}
	public  JSONArray getSearchUserListPageData(String userTypeId,String roleId,
								String warehouseId,AdmUserV userBean) {
		System.out.println("-- UserService.getUserListPageData mehtod called: -- ");
		Session session = sf1.openSession();
		String x_query="";
		String whereCondition="";
		x_query="SELECT USER_ID,	COMPANY_ID,  	FIRST_NAME,	LAST_NAME,	"
				+ "WAREHOUSE_NAME,	WAREHOUSE_ID,	LOGIN_NAME,	PASSWORD,	ACTIVATED,"
				+ "	DATE_FORMAT(ACTIVATED_ON, '%d-%b-%Y') ACTIVATED_ON,	USER_TYPE_NAME,	"
				+ "USER_TYPE_ID,	STATUS,	ROLE_ID, 	ROLE_NAME,	ROLE_DETAILS, "
				+ "	DATE_FORMAT(START_DATE, '%d-%b-%Y') START_DATE, 	"
				+ "DATE_FORMAT(END_DATE, '%d-%b-%Y') END_DATE, 	EMAIL, 	TELEPHONE_NUMBER, "
				+ "	(SELECT COUNT(*) 	   FROM ADM_USER_WAREHOUSE_ASSIGNMENTS WHA 	 "
				+ " WHERE WHA.USER_ID = USR.USER_ID 	    AND WHA.STATUS = 'A') FACILITY_FLAG "
				+ "FROM ADM_USERS_V USR WHERE USER_TYPE_ID=IFNULL("+userTypeId+",USER_TYPE_ID)"
				+ " AND ROLE_ID=IFNULL("+roleId+",ROLE_ID) AND STATUS='A' ";
	
		if(userBean.getX_ROLE_NAME().equals("SCCO")
				|| userBean.getX_ROLE_NAME().equals("SIO")
				|| userBean.getX_ROLE_NAME().equals("SIFP")){
			//if user type is admin and role id and warehosue id  is null 
			if(userTypeId.equals("148433") && roleId.equals("null") && warehouseId.equals("null")){
				whereCondition="AND WAREHOUSE_ID IN (SELECT WAREHOUSE_ID  FROM INVENTORY_WAREHOUSES "
				 +" WHERE DEFAULT_ORDERING_WAREHOUSE_ID = "+userBean.getX_WAREHOUSE_ID()+") OR WAREHOUSE_ID = "+userBean.getX_WAREHOUSE_ID()+"   ORDER BY FIRST_NAME";
			}//if user type is admin and lio moh role and warehouse id  is null 
			else if(userTypeId.equals("148433") && (roleId.equals("5004") || roleId.equals("5005")) && warehouseId.equals("null")){
				whereCondition="AND WAREHOUSE_ID IN (SELECT WAREHOUSE_ID  FROM INVENTORY_WAREHOUSES "
						 +" WHERE DEFAULT_ORDERING_WAREHOUSE_ID = "+userBean.getX_WAREHOUSE_ID()+")  ORDER BY FIRST_NAME";
			}//if user type is admin and lio moh role and warehouse id  is selected
			else if(userTypeId.equals("148433") && (roleId.equals("5004") || roleId.equals("5005"))){
				whereCondition=" AND WAREHOUSE_ID  = "+warehouseId;
			}//if user type is admin and lio moh role and warehouse id  is null 
			else if(userTypeId.equals("148433") && (roleId.equals("5003") || roleId.equals("5006") || roleId.equals("5007"))){
				whereCondition=" AND WAREHOUSE_ID  =IFNULL("+warehouseId+","+userBean.getX_WAREHOUSE_ID()+")";
			}//if user type is employee and role cco and warehouse id  is null 
			else if(userTypeId.equals("148434") && warehouseId.equals("null")){
				whereCondition=" AND WAREHOUSE_ID IN (SELECT WAREHOUSE_ID  FROM INVENTORY_WAREHOUSES "
						 +" WHERE DEFAULT_ORDERING_WAREHOUSE_ID = "+userBean.getX_WAREHOUSE_ID()+")  ORDER BY FIRST_NAME";
			}//if user type is employee and role cco and warehouse id  is selected
			else if(userTypeId.equals("148434") && !warehouseId.equals("null")){
				whereCondition=" AND WAREHOUSE_ID =IFNULL( "+warehouseId+",WAREHOUSE_ID)";
			}
		}else if(userBean.getX_ROLE_NAME().equals("NTO")){
			whereCondition=" AND WAREHOUSE_ID =IFNULL( "+warehouseId+",WAREHOUSE_ID)";
		}else if(userBean.getX_ROLE_NAME().equals("LIO")
				|| userBean.getX_ROLE_NAME().equals("MOH")){
			String roleNameForConditon=userBean.getX_ROLE_NAME().equals("LIO")?"MOH":"LIO";
			whereCondition="AND  WAREHOUSE_ID = "+userBean.getX_WAREHOUSE_ID() 
					+" AND ROLE_ID <> (SELECT ROLE_ID FROM ADM_ROLES WHERE ROLE_NAME = '"+roleNameForConditon+"')" ;
		}
		 x_query+=whereCondition;
		JSONArray array = null;
		try {
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjson(resultlist);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
		}
	
	
	public  JSONArray getAssignedLgaAccoToRole(String roleId,String userTypeId,AdmUserV userBean) {
		System.out.println("-- UserService.getUserListPageData mehtod called: -- ");
		Session session = sf1.openSession();
		JSONArray array = new JSONArray();
		String x_query="";
		String wareHouseRole=userBean.getX_ROLE_NAME();
		System.out.println("roleId: "+roleId);
		System.out.println("userTypeId: "+userTypeId);
		System.out.println("userBean: "+userBean.getX_ROLE_NAME());
		if(wareHouseRole.equalsIgnoreCase("SCCO")
				|| wareHouseRole.equalsIgnoreCase("SIO")
				|| wareHouseRole.equalsIgnoreCase("SIFP")){
			if(roleId.equals("5002") 
					|| roleId.equals("5004")
					|| roleId.equals("5005")){
				 x_query=" SELECT  	WAREHOUSE_ID,WAREHOUSE_NAME FROM ADM_USERS_V "
					+" WHERE STATUS='A' AND WAREHOUSE_ID IN "
					+ " (select WAREHOUSE_ID from inventory_warehouses"
					+ " WHERE DEFAULT_ORDERING_WAREHOUSE_ID = "+userBean.getX_WAREHOUSE_ID()+")"
				 	+" AND ROLE_ID="+roleId;
			}else if(roleId.equals("5006") 
					|| roleId.equals("5007")
					|| roleId.equals("5003")){
				x_query=" SELECT  	WAREHOUSE_ID,WAREHOUSE_NAME FROM ADM_USERS_V "
						 +" WHERE STATUS='A' AND user_type_id="+userTypeId+" and role_id="+roleId
						 +" AND WAREHOUSE_ID="+userBean.getX_WAREHOUSE_ID();
	
			}
		}else if(wareHouseRole.equalsIgnoreCase("LIO")
				|| wareHouseRole.equalsIgnoreCase("MOH")){
				x_query=" SELECT  	WAREHOUSE_ID,WAREHOUSE_NAME FROM ADM_USERS_V "
						 +" WHERE STATUS='A' AND user_type_id="+userTypeId+" and role_id="+roleId
						 +" AND WAREHOUSE_ID="+userBean.getX_WAREHOUSE_ID();
		}else if(wareHouseRole.equalsIgnoreCase("NTO")){
			 x_query=" SELECT distinct 	WAREHOUSE_ID,WAREHOUSE_NAME FROM ADM_USERS_V "
						+" WHERE STATUS='A' AND  USER_TYPE_ID= "+userTypeId
					 	+" AND ROLE_ID="+roleId;
		}
		try {
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjsonCombolist(resultlist, false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
		}
	
	public JSONArray getAssignedLgaAccoToRoleForForm(String roleId, String userTypeId, AdmUserV userBean) {
		System.out.println("-- UserService.getAssignedLgaAccoToRoleForForm mehtod called: -- ");
		System.out.println("roleId: " + roleId);
		System.out.println("userTypeId: " + userTypeId);
		System.out.println("userBean: " + userBean.getX_ROLE_NAME());
		Session session = sf1.openSession();
		JSONArray array = new JSONArray();
		String x_query = "";
		String wareHouseRole = userBean.getX_ROLE_NAME();
		if (wareHouseRole.equalsIgnoreCase("SCCO") || wareHouseRole.equalsIgnoreCase("SIO")
				|| wareHouseRole.equalsIgnoreCase("SIFP")) {
			if (roleId.equals("5004") || roleId.equals("5005")) {
				x_query = "  SELECT WAREHOUSE_ID, WAREHOUSE_NAME  " + " FROM INVENTORY_WAREHOUSES  "
						+ " WHERE DEFAULT_ORDERING_WAREHOUSE_ID = " + userBean.getX_WAREHOUSE_ID();

			} else if (roleId.equals("5002")) {
				x_query = "SELECT WAREHOUSE_ID, WAREHOUSE_NAME  FROM INVENTORY_WAREHOUSES  "
						+ " WHERE DEFAULT_ORDERING_WAREHOUSE_ID = " + userBean.getX_WAREHOUSE_ID()
						+ " AND WAREHOUSE_ID NOT IN (SELECT DISTINCT WAREHOUSE_ID FROM ADM_USERS WHERE ACTIVATED IS NULL) ";
			} else if (roleId.equals("5006") || roleId.equals("5007") || roleId.equals("5003")) {
				x_query = "  SELECT WAREHOUSE_ID, WAREHOUSE_NAME  " + " FROM INVENTORY_WAREHOUSES  "
						+ " WHERE WAREHOUSE_ID = " + userBean.getX_WAREHOUSE_ID();
			}
		} else if (wareHouseRole.equalsIgnoreCase("LIO") || wareHouseRole.equalsIgnoreCase("MOH")) {
			x_query = " SELECT  	WAREHOUSE_ID,WAREHOUSE_NAME FROM ADM_USERS_V "
					+ " WHERE STATUS='A' AND user_type_id=" + userTypeId + " and role_id=" + roleId
					+ " AND WAREHOUSE_ID=" + userBean.getX_WAREHOUSE_ID();
		} else if (wareHouseRole.equalsIgnoreCase("NTO")) {
			if(userTypeId.equals("148433") && (roleId.equals("5003") || roleId.equals("5006") || roleId.equals("5007"))){
				x_query = " SELECT WAREHOUSE_ID, WAREHOUSE_NAME FROM VIEW_INVENTORY_WAREHOUSES"
						+ " WHERE STATUS='A' AND WAREHOUSE_TYPE_ID = F_GET_TYPE('WAREHOUSE TYPES','STATE COLD STORE') ";
			}else{
				x_query = " SELECT distinct WAREHOUSE_ID,WAREHOUSE_NAME FROM ADM_USERS_V "
				+ " WHERE STATUS='A' AND  USER_TYPE_ID= " + userTypeId + " AND ROLE_ID=" + roleId;
			}			
		}
		try {
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjsonCombolist(resultlist, false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public JSONArray getUserHistory(String user_id) {
		System.out.println("-- UserService.getUserHistory() mehtod called: -- ");
		Session session = sf1.openSession();
		String x_query="";
		x_query="SELECT (SELECT CONCAT(IFNULL(CUSR.FIRST_NAME,'not available'),' ',IFNULL(CUSR.LAST_NAME,'')) "
				+"FROM ADM_USERS CUSR WHERE CUSR.USER_ID = (SELECT C.CREATED_BY  "
				+"  FROM ADM_USERS C WHERE C.USER_ID = "+user_id+")) CREATED_BY,  "
				+"  (SELECT CONCAT(IFNULL(UUSR.FIRST_NAME,'not available'),' ', "
				+"   IFNULL(UUSR.LAST_NAME,''))  "
				+"     FROM ADM_USERS UUSR WHERE UUSR.USER_ID = (SELECT U.UPDATED_BY "
				+"       FROM ADM_USERS U WHERE U.USER_ID = "+user_id+")) UPDATED_BY,"
				+"        DATE_FORMAT(MNTB.CREATED_ON,'%b %d %Y %h:%i %p') CREATED_ON, "
				+"         DATE_FORMAT(MNTB.LAST_UPDATED_ON,'%b %d %Y %h:%i %p') LAST_UPDATED_ON  "
				+"FROM ADM_USERS MNTB  WHERE MNTB.USER_ID =" +user_id;
		JSONArray array = null;
		try {
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjson(resultlist);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
		}
	public int passwordChange(String user_id, String newPassword,String oldPassword) {
		Session session = sf1.openSession();
		session.beginTransaction();
		String x_query="";
		int result=0;
		try {
			x_query="UPDATE ADM_USERS "
					+ "SET PASSWORD='"+newPassword+"', SYNC_FLAG='N' "
					+ "WHERE USER_ID="+user_id+" AND PASSWORD='"+oldPassword+"'";
			SQLQuery query = session.createSQLQuery(x_query);
			 result=query.executeUpdate();
			 session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
	public int saveUserAddEdit(UserBeanForUserForm bean, String action,AdmUserV userBean) {
		int insertupdateadmUserFlag = 0;
		int insertupdateRolemapingFlag = 0;
		int insertupdateWarehouseAssimgmentFlag = 0;
		int insetUpdateUserFlag = 0;
		String x_QUERY="";
		Session session = sf1.openSession();
		session.beginTransaction();
		try {
			if (action.equals("add")) {
				x_QUERY="INSERT INTO ADM_USERS "
						+ "		 (COMPANY_ID, FIRST_NAME, LAST_NAME, LOGIN_NAME, "
						+ "		 	EMAIL, TELEPHONE_NUMBER,START_DATE, "
						+ "		 	END_DATE, UPDATED_BY, LAST_UPDATED_ON,"
						+ "  USER_TYPE_ID, SYNC_FLAG, STATUS, CREATED_BY,"
						+ " 		CREATED_ON, PASSWORD, WAREHOUSE_ID, ACTIVATED) "
						+ " VALUES (?,?,?,?,?,?,?,?,?,NOW(),?,'N','A',?,NOW(),?,?,?)";
			}else{
				x_QUERY="UPDATE ADM_USERS SET COMPANY_ID=?, "
						+ "		 FIRST_NAME=?, LAST_NAME=?, LOGIN_NAME=?, "
						+ "		   EMAIL=?, TELEPHONE_NUMBER=?, START_DATE=?, "
						+ "		 END_DATE=? ,UPDATED_BY=?, LAST_UPDATED_ON=NOW(),"
						+ "      SYNC_FLAG = 'N' "
						+ " WHERE USER_ID=?";
			}
			SQLQuery query = session.createSQLQuery(x_QUERY);
			query.setParameter(0, "21000");
			query.setParameter(1, bean.getX_FIRST_NAME());
			query.setParameter(2, bean.getX_LAST_NAME());
			query.setParameter(3, bean.getX_LOGIN_NAME());
			query.setParameter(4, bean.getX_EMAIL());
			query.setParameter(5, bean.getX_TELEPHONE_NUMBER());
			query.setParameter(6, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_START_DATE())+ " " + CalendarUtil.getCurrentTime());
			if (bean.getX_END_DATE() == null || bean.getX_END_DATE().equals("")) {
				query.setParameter(7, null);
			} else {
				query.setParameter(7, bean.getX_END_DATE()+ " " + CalendarUtil.getCurrentTime());
			}
			query.setParameter(8, userBean.getX_USER_ID());
			if (action.equals("add")) {
				query.setParameter(9, bean.getX_USER_TYPE_ID());		
				query.setParameter(10, userBean.getX_USER_ID());	
				query.setParameter(11, bean.getX_PASSWORD());	
				query.setParameter(12, bean.getX_ASSIGN_LGA_ID());				
				query.setParameter(13, (bean.getX_USER_TYPE_ID().equals("148433")?null:"N"));				
			}else{
				query.setParameter(9, bean.getX_USER_ID());	
			}
			insertupdateadmUserFlag = query.executeUpdate();
			insertupdateRolemapingFlag = saveSetRoleIDMapping(bean, action, userBean, session);
			insertupdateWarehouseAssimgmentFlag = setWarehouseIdAssingment(bean, action, userBean, session);
			if (insertupdateadmUserFlag == 1 && insertupdateRolemapingFlag == 1
					&& insertupdateWarehouseAssimgmentFlag == 1) {
				session.getTransaction().commit();
				insetUpdateUserFlag = 1;
			} else {
				session.getTransaction().rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return insetUpdateUserFlag;
	}
	
	public int saveSetRoleIDMapping(UserBeanForUserForm bean, String action, AdmUserV userBean, Session session) {
		int result=0;
		String x_QUERY="";
		String status="";
		try {
			status=(bean.getX_USER_TYPE_ID().equals("148433")?"A":"I");
			if (action.equals("add")) {
				x_QUERY="INSERT INTO ADM_USER_ROLE_MAPPINGS "
						+ "	(  	  STATUS, "
						+ "		 START_DATE, " //0
						+ "		 END_DATE," //1
						+ "		 SYNC_FLAG,"  
						+ "        WAREHOUSE_ID,"//2
						+ "        USER_ID,"//3
						+ " ROLE_ID,"//4
						+ "		COMPANY_ID) "
						+ "		VALUES ('"+status+"',?,?,'N',?,?,?,21000)";
			}else{
				x_QUERY="UPDATE ADM_USER_ROLE_MAPPINGS SET "
//						+ "	STATUS='A', "
						+ "	START_DATE=?, "//0
						+ "	END_DATE=?,"//1
						+ "	SYNC_FLAG='N' "
						+ " WHERE USER_ID=?";//2
			}
			SQLQuery query = session.createSQLQuery(x_QUERY);
			query.setParameter(0, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_START_DATE())+ " " + CalendarUtil.getCurrentTime());
			if (bean.getX_END_DATE() == null || bean.getX_END_DATE().equals("")) {
				query.setParameter(1, null);
			} else {
				query.setParameter(1, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_END_DATE())+ " " + CalendarUtil.getCurrentTime());
			}
			if(action.equals("add")){
				query.setParameter(2, bean.getX_ASSIGN_LGA_ID());
				System.out.println("getX_ASSIGN_LGA_ID: "+bean.getX_ASSIGN_LGA_ID());
				System.out.println("getX_LOGIN_NAME: "+bean.getX_LOGIN_NAME());
				System.out.println("getX_ASSIGN_LGA_ID: "+bean.getX_USER_ROLE_ID());
				lastInsertUserId=getLastInsertUserID(bean.getX_LOGIN_NAME(),bean.getX_ASSIGN_LGA_ID(),session);
				System.out.println("lastInsertUserId: "+lastInsertUserId);
				query.setParameter(3,lastInsertUserId );				
				query.setParameter(4, bean.getX_USER_ROLE_ID());				
			}else{
				query.setParameter(2, bean.getX_USER_ID());
			}
			 result=query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int setWarehouseIdAssingment(UserBeanForUserForm bean, String action, AdmUserV userBean, Session session) {
		int result=0;
		String x_QUERY="";
		String status="";
		try {
			status=(bean.getX_USER_TYPE_ID().equals("148433")?"A":"I");
			if (action.equals("add")) {
				x_QUERY="INSERT INTO ADM_USER_WAREHOUSE_ASSIGNMENTS "
						+ "		 (COMPANY_ID, "
						+ "		 START_DATE, "// 0
						+ "		 END_DATE,"// 1
						+ "        STATUS,"// 
						+ "       CREATED_ON,"
						+ "       UPDATED_BY,"// 2
						+ "       LAST_UPDATED_ON,"
						+ "		SYNC_FLAG,"
						+ "		  WAREHOUSE_ID, "// 3
						+ "		  USER_ID,	"// 4
						+ "		 CREATED_BY)"// 5) "
						+ "		VALUES (21000,?,?,'A',now(),?,now(),'N',?,?,?) ";
			}else{
				x_QUERY="UPDATE ADM_USER_WAREHOUSE_ASSIGNMENTS SET "
						+ "		 COMPANY_ID=21000, "
						+ "		 START_DATE=?, "// 0
						+ "		 END_DATE=?,"// 1
						+ "        STATUS='"+status+"',"// 
						+ "       UPDATED_BY=?,"// 2
						+ "       LAST_UPDATED_ON=now(),"
						+ "		SYNC_FLAG='N' " + " WHERE USER_ID=?";// 3
			}
			SQLQuery query = session.createSQLQuery(x_QUERY);
			if (action.equals("add")) {
				query.setParameter(3, bean.getX_ASSIGN_LGA_ID());
				query.setParameter(4, lastInsertUserId);
				query.setParameter(5, userBean.getX_USER_ID());
			}else{
				query.setParameter(3, bean.getX_USER_ID());
			}
			query.setParameter(0, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_START_DATE()) + " "
					+ CalendarUtil.getCurrentTime());
			if (bean.getX_END_DATE() == null || bean.getX_END_DATE().equals("")) {
				query.setParameter(1, null);
			} else {
				query.setParameter(1, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_END_DATE()) + " "
						+ CalendarUtil.getCurrentTime());				
			}
			query.setParameter(2, userBean.getX_USER_ID());
			 result=query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getLastInsertUserID(String user_name,String warehouse_id, Session session) {
		try {
			System.out.println("user_name: "+user_name+", warehouse_id: "+warehouse_id);
			SQLQuery query = session.createSQLQuery("Select USER_ID from adm_users"
					+ " Where LOGIN_NAME='"+user_name+"'  AND WAREHOUSE_ID="+warehouse_id+ " AND STATUS='A'");
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			System.out.println("resultlist.size(): "+resultlist.size());
			if (resultlist.size()==1) {
				HashMap<String, Object> row=(HashMap) resultlist.get(0);
				System.out.println("last insert User Id is"+(row.get("USER_ID").toString()));
				System.out.println("last insert User Id is"+String.valueOf(row.get("USER_ID")));
				return String.valueOf(row.get("USER_ID"));
			} else{
				System.out.println("resultlist.size()==1 is false, size!=1");
				System.out.println("resultlist.size(): "+resultlist.size());
				return null;
			}
		} catch (HibernateException e) {
			return null;
		} 
	}
}
