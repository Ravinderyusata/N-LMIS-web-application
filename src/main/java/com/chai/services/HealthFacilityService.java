package com.chai.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;

import com.chai.hibernartesessionfactory.HibernateSessionFactoryClass;
import com.chai.model.HealthFacilityBean;
import com.chai.model.views.AdmUserV;
import com.chai.util.CalendarUtil;
import com.chai.util.GetJsonResultSet;

public class HealthFacilityService {
	SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
	public JSONArray getHFGridData(String lgaId,String hfId,String state_id){
		System.out.println("in HealthFacilityService.getHFGridData()");
		JSONArray array=new JSONArray();
		SQLQuery query=null;
		String whereCondition = "";
		Session session = sf.openSession();
		if (hfId == null || hfId.equals("")) {
			whereCondition = "WHERE DEFAULT_STORE_ID = " + lgaId + " AND (IFNULL(CUSTOMER_ID,1)= IFNULL(CUSTOMER_ID,1) "
					+ " OR DB_ID = IFNULL(null,DB_ID)) ORDER BY CUSTOMER_NAME";
		} else {
			whereCondition = "WHERE DEFAULT_STORE_ID = " + lgaId + " AND (IFNULL(CUSTOMER_ID,1)= IFNULL(" + hfId + ",1)"
					+ " OR DB_ID = IFNULL(" + hfId + ",DB_ID))  ORDER BY CUSTOMER_NAME";
		}
		try{
			String x_query = " SELECT COMPANY_ID,  DB_ID   ,   CUSTOMER_ID,    "
				+" CUSTOMER_NUMBER,	       CUSTOMER_NAME,	 "
				+" CUSTOMER_DESCRIPTION,	       STATE_NAME,	   "
				+" COUNTRY_NAME,	       STATE_ID,	       COUNTRY_ID,	"
				+" DAY_PHONE_NUMBER,	       EMAIL_ADDRESS,	       STATUS,	 "
				+" DATE_FORMAT(START_DATE, '%d-%b-%Y') START_DATE,	"
				+" DATE_FORMAT(END_DATE, '%d-%b-%Y') END_DATE, 	    "
				+" DEFAULT_STORE_ID, 	       DEFAULT_STORE, 		 "
				+" CUSTOMER_TYPE_ID,		   CUSTOMER_TYPE_CODE, 		"
				+" VACCINE_FLAG,		   TARGET_POPULATION, 		"
				+" MONTHLY_PREGNANT_WOMEN_TP, 	   "
				+" DATE_FORMAT(EDIT_DATE, '%d-%b-%Y') EDIT_DATE  "
					+ " FROM VIEW_CUSTOMERS  ";
			x_query += whereCondition;
			query = session.createSQLQuery(x_query);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List resultlist = query.list();
	//System.out.println("result list size"+resultlist.size());
		 array=GetJsonResultSet.getjson(resultlist);
	} catch (HibernateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
			session.close();
	}
	return array;
	}
	
	public JSONArray getHFHistory(String DB_ID, String DEFAULT_STORE_ID) {
		System.out.println("-- LgaStoreService.getUserHistory() mehtod called: -- ");
		String x_query = "";
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		x_query = "SELECT (SELECT CONCAT(IFNULL(CUSR.FIRST_NAME,'not available')," + " ' ',IFNULL(CUSR.LAST_NAME,'')) "
				+ "FROM ADM_USERS CUSR WHERE CUSR.USER_ID = (SELECT C.CREATED_BY  "
				+ "FROM CUSTOMERS C WHERE C.DB_ID = " + DB_ID + " and default_store_id=" + DEFAULT_STORE_ID
				+ ")) CREATED_BY, "
				+ "(SELECT CONCAT(IFNULL(UUSR.FIRST_NAME,'not available'),' ', IFNULL(UUSR.LAST_NAME,'')) "
				+ "FROM ADM_USERS UUSR WHERE UUSR.USER_ID = (SELECT U.UPDATED_BY  "
				+ "FROM CUSTOMERS U WHERE U.DB_ID = " + DB_ID + " and default_store_id=" + DEFAULT_STORE_ID
				+ ")) UPDATED_BY, "
				+ "DATE_FORMAT(MNTB.CREATED_ON,'%b %d %Y %h:%i %p') CREATED_ON, "
				+ "DATE_FORMAT(MNTB.LAST_UPDATED_ON,'%b %d %Y %h:%i %p') LAST_UPDATED_ON "
				+ "FROM CUSTOMERS MNTB  WHERE MNTB.DB_ID = " + DB_ID;
		try {
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			// System.out.println("result list size"+resultlist.size());
			array = GetJsonResultSet.getjson(resultlist);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public int saveAddEditHF(HealthFacilityBean bean, String action, AdmUserV userBean) {
		int result = 0;
		String x_QUERY = "";
		Session session = sf.openSession();
		session.beginTransaction();
		try {
			if (action.equals("add")) {
				x_QUERY = "INSERT INTO CUSTOMERS" + " (COMPANY_ID," + "  CUSTOMER_NUMBER,"// 0
						+ "  CUSTOMER_NAME,"// 1
						+ "  CUSTOMER_DESCRIPTION,"// 2
						+ "  STATE_ID,"// 3
						+ "  COUNTRY_ID,"// 4
						+ "  DAY_PHONE_NUMBER,"// 5
						+ "  EMAIL_ADDRESS,"// 6
						+ "  STATUS,"// 7
						+ "  START_DATE,"// 8
						+ "  END_DATE, "// 9
						+ "  DEFAULT_STORE_ID, " // 10
						+ "  CUSTOMER_TYPE_ID, " // 11
						+ "  UPDATED_BY, " // 12
						+ "	 TARGET_POPULATION," // 13
						+ "	 EDIT_DATE,"// 14
						+ "  VACCINE_FLAG, " // 15
						+ "  CREATED_ON, " + "  LAST_UPDATED_ON," + "  SYNC_FLAG," + "  CREATED_BY) " // 16
						+ " VALUES('21000',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),NOW(),'N',?)";
			} else {
				x_QUERY = "UPDATE CUSTOMERS SET " + " CUSTOMER_NUMBER=?," // 0
						+ "	CUSTOMER_NAME=?," // 1
						+ "	CUSTOMER_DESCRIPTION=?," // 2
						+ "	STATE_ID=?," // 3
						+ "	COUNTRY_ID=?," // 4
						+ "	DAY_PHONE_NUMBER=?," // 5
						+ "	EMAIL_ADDRESS=?," // 6
						+ "	STATUS=?," // 7
						+ "	START_DATE=?," // 8
						+ "	END_DATE=?, " // 9
						+ "  DEFAULT_STORE_ID=?, " // 10
						+ "  CUSTOMER_TYPE_ID=?, " // 11
						+ " UPDATED_BY=?," // 12
						+ " TARGET_POPULATION=?, " // 13
						+ " EDIT_DATE=? ," // 14
						+ " VACCINE_FLAG=?, " // 15
						+ " LAST_UPDATED_ON=NOW()," + " SYNC_FLAG='N'" + " WHERE DB_ID=? "// 16
						+ " AND DEFAULT_STORE_ID=?";// 17
			}
			SQLQuery query = session.createSQLQuery(x_QUERY);
			query.setParameter(0, bean.getX_HF_NUMBER());
			query.setParameter(1, bean.getX_HF_NAME());
			query.setParameter(2, bean.getX_HF_DESCRIPTION());
			query.setParameter(3, bean.getX_STATE_ID());
			query.setParameter(4, bean.getX_COUNTRY_ID());
			query.setParameter(5, bean.getX_TELEPHONE_NUMBER());
			query.setParameter(6, bean.getX_EMAIL_ADDRESS());
			query.setParameter(7, bean.getX_STATUS() == null ? "I" : bean.getX_STATUS());
			query.setParameter(8, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_START_DATE()) + " "
					+ CalendarUtil.getCurrentTime());
			if (bean.getX_END_DATE() == null || bean.getX_END_DATE().equals("")) {
				query.setParameter(9, null);
			} else {
				query.setParameter(9, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_END_DATE()) + " "
						+ CalendarUtil.getCurrentTime());
			}
			query.setParameter(10, bean.getX_DEFAULT_STORE_ID());
			query.setParameter(11, bean.getX_WARD_ID());
			query.setParameter(12, userBean.getX_USER_ID());
			query.setParameter(13, bean.getX_TARGET_POPULATION().equals("") ? null : bean.getX_TARGET_POPULATION());
			query.setParameter(14, bean.getX_EDIT_DATE());
			query.setParameter(15, bean.getX_VACCINE_FLAG() == null ? "N" : bean.getX_VACCINE_FLAG());
			if (action.equals("add")) {
				query.setParameter(16, userBean.getX_USER_ID());
			} else {
				query.setParameter(16, bean.getX_DB_ID());
				query.setParameter(17, bean.getX_DEFAULT_STORE_ID());
			}
			result = query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	public JSONArray getWardListBasedOnLga(String LGA_ID, String option) {
		System.out.println("-- healthFacilityService.getWardListBasedOnLga() mehtod called: -- ");
		String x_query = "";
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		x_query = "SELECT TYPE_ID,  TYPE_CODE     FROM TYPES  " + " WHERE SOURCE_TYPE='CUSTOMER TYPE' AND STATUS='A' "
				+ " AND WAREHOUSE_ID = " + LGA_ID + "  ORDER BY TYPE_CODE";
		try {
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			// System.out.println("result list size"+resultlist.size());
			array = GetJsonResultSet.getjsonCombolist(resultlist, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public JSONArray getStateStoreIdBasedOnLgaId(String LGA_ID) {
		System.out.println("-- healthFacilityService.getWardListBasedOnLga() mehtod called: -- ");
		String x_query = "";
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		x_query = "select warehouse_id , warehouse_name " + " from inventory_warehouses "
				+ " where warehouse_id=(select default_ordering_warehouse_id from inventory_warehouses "
				+ " where warehouse_id=" + LGA_ID + "  )  ";
		try {
			SQLQuery query = sf.openSession().createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			// System.out.println("result list size"+resultlist.size());
			array = GetJsonResultSet.getjsonCombolist(resultlist, false);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

}
