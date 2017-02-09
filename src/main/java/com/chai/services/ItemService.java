package com.chai.services;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;

import com.chai.hibernartesessionfactory.HibernateSessionFactoryClass;
import com.chai.model.DeviceAssoiationGridBean;
import com.chai.model.LabelValueBean;
import com.chai.model.ProductBean;
import com.chai.model.views.AdmUserV;
import com.chai.util.CalendarUtil;
import com.chai.util.GetJsonResultSet;

public class ItemService {
	SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
	public JSONArray getDeviceAssoGridData(String warehouseId) {
	System.out.println("in ItemService.getDeviceAssoGridData()");
	JSONArray array=new JSONArray();
	SQLQuery query=null;
		Session session = sf.openSession();
	try{
	String x_query = " SELECT ASSOCIATION_ID," + "ITEM_ID, "
			+ "ITEM_NUMBER, " + "  AD_SYRINGE_ID, "
			+ "  AD_SYRINGE_NAME, " + " RECONSTITUTE_SYRNG_ID, "
			+ "CONCAT_WS(',', AD_SYRINGE_NAME, RECONSTITUTE_SYRNG_NAME) AS ASSOCIATED_DEVICES ,"
			+ " RECONSTITUTE_SYRNG_NAME,(case when (RECONSTITUTE_SYRNG_ID is null) "
			+ " THEN"
			+" 1" 
			+" ELSE"
			+" 2 "
			+" END)"
			+" AS NO_OF_ASSOCIATE_DEVICE  "
			+ " FROM SYRINGE_ASSOCIATION_V WHERE WAREHOUSE_ID = "
					+ warehouseId;
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

public List<LabelValueBean> getDropdownList(String... action){
	System.out.println("in itemServices.getDropdownList()");
	String x_QUERY="";
	SQLQuery query=null;
		Session session = sf.openSession();
	if(action[0].equals("device_asso_product")){
		 x_QUERY = " SELECT ITEM_ID, " + "	ITEM_NUMBER "
					+ " FROM VIEW_ITEM_MASTERS " + " WHERE STATUS = 'A' "
					+ " AND ITEM_TYPE_ID = F_GET_TYPE('PRODUCT','VACCINE') "
					+ " AND ITEM_NUMBER NOT IN ('ROTA VACCINE','OPV') "
					+ " AND WAREHOUSE_ID = " +action[1]
					+ " ORDER BY ITEM_NUMBER ";	
	}

	if (action[0].equals("ad_syringe")) {
		x_QUERY = " SELECT ITEM_ID, "
				+ "	UCASE(ITEM_NUMBER) AS ITEM_NUMBER, "
//				+ "	UCASE(ITEM_TYPE_CODE) AS ITEM_TYPE_CODE, "
				+ "	ITEM_TYPE_ID "
				+ " FROM VIEW_ITEM_MASTERS "
				+ " WHERE ITEM_TYPE_ID = F_GET_TYPE('PRODUCT','DEVICE')"
					+ " AND UPPER(ITEM_NUMBER) NOT IN ('2ML SYRINGE','5ML SYRINGE','MUAC STRIPS','SAFETY BOXES')"
				+ " AND WAREHOUSE_ID = " + action[1];
	} 
	
	if (action[0].equals("reconstitute_syrng")) {
		x_QUERY = " SELECT	ITEM_TYPE_ID,"
//				+" UCASE(ITEM_TYPE_CODE) AS ITEM_TYPE_CODE, "
				+ "	ITEM_ID ,"
				+ "	UCASE(ITEM_NUMBER) AS ITEM_NUMBER "
				+ " FROM VIEW_ITEM_MASTERS "
				+ " WHERE ITEM_TYPE_ID = F_GET_TYPE('PRODUCT','DEVICE') "
					+ " AND UPPER(ITEM_NUMBER) NOT IN ('0.05ML AD SYRINGE (BCG)','0.5ML AD SYRINGE','MUAC STRIPS','SAFETY BOXES') "
				+ "AND WAREHOUSE_ID = " + action[1];
	}
		//System.out.println("result list size"+resultlist.size());
		List<LabelValueBean> array = null;
		try {
			query = session.createSQLQuery(x_QUERY);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getdropList(resultlist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return array;
}
public int add_edit_deviceAsso(DeviceAssoiationGridBean bean,AdmUserV userBean){
	System.out.println("in itemServices.add_edit_deviceAsso()");
		Session session = sf.openSession();
	session.beginTransaction();
	SQLQuery query=null;
	String x_QUERY="";
	int result=0;
	try {
			if (bean.getX_ACTION().equals("Insert")) {
			 x_QUERY="INSERT INTO backup_syringe_association_11_4_15 "
					+ "(ITEM_ID, AD_SYRINGE_ID, RECONSTITUTE_SYRNG_ID," // 1-3
					+ "AD_SYRINGE_CATEGORY_ID,RC_SYRINGE_CATEGORY_ID,SYNC_FLAG,"// 4-5
					+ "CREATED_BY,CREATED_ON,LAST_UPDATED_BY,LAST_UPDATED_ON,"// 6-7
					+ "WAREHOUSE_ID) "// 8-9
					+ "VALUES(?,?,?,?,?,'N',?,now(),?,now(),?)";
			 	query=session.createSQLQuery(x_QUERY);
			 	query.setParameter(6, userBean.getX_USER_ID());
				query.setParameter(7, userBean.getX_WAREHOUSE_ID());
		}else{
			 x_QUERY="UPDATE backup_syringe_association_11_4_15 SET "
						+ "ITEM_ID=?, AD_SYRINGE_ID=?, RECONSTITUTE_SYRNG_ID=?," // 1-2-3
						+ "AD_SYRINGE_CATEGORY_ID=?,RC_SYRINGE_CATEGORY_ID=?,SYNC_FLAG='N', "// 4-5
						+ " LAST_UPDATED_BY=?,LAST_UPDATED_ON=now()"// 6
						+ " WHERE WAREHOUSE_ID=? AND ASSOCIATION_ID=?";
			 query=session.createSQLQuery(x_QUERY);
			 query.setParameter(6,  userBean.getX_WAREHOUSE_ID());
				query.setParameter(7,bean.getX_ASSOCIATION_ID());
		}
			query.setParameter(0, bean.getX_PRODUCT_ID());
			if (bean.getX_AD_SYRINGE_ID().length() > 0) {
				query.setParameter(1, bean.getX_AD_SYRINGE_ID());
			} else {
				query.setParameter(1, null);
			}
			if (bean.getX_RECONSTITUTE_SYRNG_ID().length() > 0) {
				query.setParameter(2, bean.getX_RECONSTITUTE_SYRNG_ID());	
			}else{
				query.setParameter(2, null);	
			}
			query.setParameter(3,
					getCategoryID(bean.getX_AD_SYRINGE_ID()) == null ? null : getCategoryID(bean.getX_AD_SYRINGE_ID()));
			query.setParameter(4, getCategoryID(bean.getX_RECONSTITUTE_SYRNG_ID()) == null ? null
					: getCategoryID(bean.getX_RECONSTITUTE_SYRNG_ID()));
			query.setParameter(5, userBean.getX_USER_ID());
			
			 result=query.executeUpdate();
			System.out.println("resullllllllllllllllllllllll"+result);
			session.getTransaction().commit();
	} catch (Exception e) {
		System.out.println("excepton generated in itemservice.add_edit_deviceAsso()");
		e.printStackTrace();
	}
	finally {
			session.close();
	}
		return result;
}
public String getCategoryID(String itemID) {
	System.out.println("In getCategoryID() method... ");
	String x_CAT_ID="";
		Session session = sf.openSession();
	if(itemID!=null && itemID.length()>0){
		String x_QUERY = " SELECT F_GET_CATEGORY_ID("+itemID+") AS CAT_ID";
		try {
			SQLQuery query=null;
				query = session.createSQLQuery(x_QUERY);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List result=query.list();
			Map row=(Map)result.get(0);
			x_CAT_ID=String.valueOf(row.get("CAT_ID"));
		}catch(Exception e){
			System.out.println("exception in getcatogary id");
			e.printStackTrace();
			} finally {
				session.close();
		}
		return x_CAT_ID;
	}else{
		return null;
	}
}

public  JSONArray getJsonProductMainGridData(String warehouse_id) {
	System.out.println("-- LgaStoreService.getLgaStoreListPageData() mehtod called: -- ");
	JSONArray array=new JSONArray();
		Session session = sf.openSession();

	try {
		String x_query="SELECT ITEM_ID,  ITEM_NUMBER, ITEM_NAME, ITEM_DESCRIPTION, UCASE(ITEM_TYPE_CODE) AS ITEM_TYPE_CODE, "
				+ "		UCASE(ITEM_TYPE_NAME) AS ITEM_TYPE_NAME, ITEM_TYPE_ID, WAREHOUSE_ID,WAREHOUSE_CODE, WAREHOUSE_NAME,  ITEM_SOURCE_TYPE, "
				+ "		CATEGORY_ID, CATEGORY_CODE, CATEGORY_NAME, CATEGORY_DESCRIPTION, "
				+ "		SOURCE_CODE, CATEGORY_TYPE_ID, CATEGORY_TYPE_CODE, CATEGORY_TYPE_NAME, DEFAULT_CATEGORY_ID, "
				+ "		TRANSACTION_BASE_UOM, VACCINE_PRESENTATION, DATE_FORMAT(EXPIRATION_DATE, '%d-%b-%Y') EXPIRATION_DATE, "
				+ "     YIELD_PERCENT,  "
				+ "     STATUS, DATE_FORMAT(START_DATE, '%d-%b-%Y') START_DATE, "
					+ "		DATE_FORMAT(END_DATE, '%d-%b-%Y') END_DATE, CREATED_BY,CREATED_BY_NAME, CREATED_ON, "
					+ "		UPDATED_BY,UPDATED_BY_NAME, LAST_UPDATED_ON,  "
				+ "		DOSES_PER_SCHEDULE, "
				+ "     WASTAGE_FACTOR,TARGET_COVERAGE "
				+ " FROM VIEW_ITEM_MASTERS WHERE WAREHOUSE_ID= " + warehouse_id;
			SQLQuery query = session.createSQLQuery(x_query);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List resultlist = query.list();
//	System.out.println("result list size"+resultlist.size());
		 array=GetJsonResultSet.getjson(resultlist);
	} catch (HibernateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} finally {
			session.close();
	}
	return array;
	}

public  JSONArray getitemOnHandGridData(AdmUserV userBean,String warehouse_id,String product_id) {
	if(product_id==null || product_id.length()==0){
		product_id="null";
	}
	System.out.println("-- LgaStoreService.getLgaStoreListPageData() mehtod called: -- ");
	JSONArray array=new JSONArray();
		Session session = sf.openSession();
	try {
		String x_query=" SELECT DISTINCT ITEM_ID, ITEM_NUMBER,  ITEM_SAFETY_STOCK,"
				 + " ONHAND_QUANTITY,  TRANSACTION_UOM, ITEMS_BELOW_SAFETY_STOCK " 
				 +" FROM ITEM_ONHAND_QUANTITIES_VW "
				 +" WHERE WAREHOUSE_ID =IFNULL("+warehouse_id+",WAREHOUSE_ID) "
				 +" AND ITEM_ID = IFNULL("+product_id+", ITEM_ID) "
				 +"GROUP BY ITEM_ID ";
			SQLQuery query = session.createSQLQuery(x_query);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List resultlist = query.list();
//	System.out.println("result list size"+resultlist.size());
		 array=GetJsonResultSet.getjson(resultlist);
	} catch (HibernateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} finally {
			session.close();
	}
	return array;
	}

	public JSONArray getProductTypeList() {
		System.out.println("-- ItemService.getProductTypeList() mehtod called: -- ");
		String x_QUERY = "";
		SQLQuery query = null;
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		x_QUERY = "SELECT TYPE_ID,   TYPE_CODE   " + " FROM VIEW_TYPES   WHERE SOURCE_TYPE = 'PRODUCT' "
				+ " AND TYPE_CODE IN ('DEVICE', 'DILUENT', 'VACCINE')";
		try {
			query = session.createSQLQuery(x_QUERY);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjsonCombolist(resultlist, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public JSONArray getProductCategoryTypeList(String productTypeid) {
		System.out.println("-- ItemService.getProductCategoryTypeList() mehtod called: -- ");
		String x_QUERY = "";
		SQLQuery query = null;
		Session session = sf.openSession();
		JSONArray array = new JSONArray();
		x_QUERY = "SELECT CATEGORY_ID, CATEGORY_CODE  FROM VIEW_CATEGORIES"
				+ " WHERE CATEGORY_CODE IS NOT NULL AND CATEGORY_CODE <> '' " + " AND CATEGORY_TYPE_ID="
				+ productTypeid;
		try {
			query = session.createSQLQuery(x_QUERY);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjsonCombolist(resultlist, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public int saveAddEditProduct(ProductBean bean, String action, AdmUserV userBean) {
		int result = 0;
		String x_QUERY = "";
		Session session = sf.openSession();
		session.beginTransaction();
		try {
			if (action.equals("add")) {
				x_QUERY = "INSERT INTO ITEM_MASTERS_INSERT_UPDATE_FOR_TRG "
						+ " (COMPANY_ID," 
						+ " ITEM_DESCRIPTION," // 0
						+ " ITEM_TYPE_ID," // 1
						+ " DEFAULT_CATEGORY_ID, "// 2
						+ "	TRANSACTION_BASE_UOM," // 3
						+ " VACCINE_PRESENTATION, " // 4
						+ " EXPIRATION_DATE, "// 5
						+ " YIELD_PERCENT,  " // 6
						+ " STATUS,"// 7
						+ " START_DATE," // 8
						+ " END_DATE, "// 9
						+ "	DOSES_PER_SCHEDULE, "// 10
						+ "WASTAGE_FACTOR, "// 11
						+ " TARGET_COVERAGE, " // 12
						+ "	UPDATED_BY,"// 13
						+ " ITEM_NAME, "// 14
						+ "ITEM_NUMBER ,"// 15
						+ "LAST_UPDATED_ON, "//
						+ "SYNC_FLAG," 
						+ " CREATED_BY, " // 16
						+ "CREATED_ON," 
						+ "WAREHOUSE_ID) "// 17
						+ " VALUES('21000',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),'N',?,NOW(),?)";
			} else {
				x_QUERY = "UPDATE ITEM_MASTERS SET"
						+ " COMPANY_ID='21000', " 
						+ "ITEM_DESCRIPTION=?,"// 0
						+ " ITEM_TYPE_ID=?,"// 1
						+ " DEFAULT_CATEGORY_ID=?, "// 2
						+ " TRANSACTION_BASE_UOM=?, "// 3
						+ "VACCINE_PRESENTATION=?, "// 4
						+ "EXPIRATION_DATE=?, " // 5
						+ " YIELD_PERCENT=?,  "// 6
						+ " STATUS=?,"// 7
						+ "START_DATE=?,"// 8
						+ " END_DATE=?,"// 9
						+ " DOSES_PER_SCHEDULE=?,"// 10
						+ " WASTAGE_FACTOR=?, "// 11
						+ " TARGET_COVERAGE=?,"// 12
						+ " UPDATED_BY=?, " // 13
						+ " ITEM_NAME=?, "// 14
						+ " ITEM_NUMBER=?, "// 15
						+ " LAST_UPDATED_ON=NOW()," 
						+ "SYNC_FLAG='N'" //
						+ " WHERE (WAREHOUSE_ID = " + userBean.getX_WAREHOUSE_ID() 
						+ " and ITEM_NUMBER = '"
						+ bean.getX_ITEM_NUMBER() + "') " 
						+ " OR  (WAREHOUSE_ID IN (SELECT WAREHOUSE_ID "
						+ "  FROM INVENTORY_WAREHOUSES " 
						+ " WHERE DEFAULT_ORDERING_WAREHOUSE_ID = "
						+ userBean.getX_WAREHOUSE_ID() + " ) " 
						+ " AND ITEM_NUMBER = '" 
						+ bean.getX_ITEM_NUMBER()
						+ "')";
			}
			SQLQuery query = session.createSQLQuery(x_QUERY);
			query.setParameter(0, bean.getX_ITEM_DESCRIPTION());
			query.setParameter(1, bean.getX_ITEM_TYPE_ID());
			query.setParameter(2, (bean.getX_CATEGORY_ID().equalsIgnoreCase("n/a") || bean.getX_CATEGORY_ID().equals("")) ? 0
							: bean.getX_CATEGORY_ID());
			query.setParameter(3, bean.getX_PRIMARY_UOM());
			query.setParameter(4, (bean.getX_VACCINE_PRESENTATION().equalsIgnoreCase("n/a")
					|| bean.getX_VACCINE_PRESENTATION().equals("")) ? 0 : bean.getX_VACCINE_PRESENTATION());
			if (bean.getX_EXPIRATION_DATE() == null || bean.getX_EXPIRATION_DATE().equals("")) {
				query.setParameter(5, null);
			} else {
				query.setParameter(5, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_EXPIRATION_DATE()) + " "
						+ CalendarUtil.getCurrentTime());
			}
			query.setParameter(6,
					(bean.getX_WASTAGE_RATE().equalsIgnoreCase("n/a") || bean.getX_WASTAGE_RATE().equals("")) ? 0
							: bean.getX_WASTAGE_RATE());
			query.setParameter(7, bean.getX_STATUS() == null ? "I" : bean.getX_STATUS());
			query.setParameter(8, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_START_DATE()) + " "
					+ CalendarUtil.getCurrentTime());
			if (bean.getX_END_DATE() == null || bean.getX_END_DATE().equals("")) {
				query.setParameter(9, null);
			} else {
				query.setParameter(9, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_END_DATE()) + " "
						+ CalendarUtil.getCurrentTime());
			}

			query.setParameter(10, (bean.getX_DOSES_PER_SCHEDULE().equalsIgnoreCase("n/a")
					|| bean.getX_DOSES_PER_SCHEDULE().equals("")) ? 0 : bean.getX_DOSES_PER_SCHEDULE());
			query.setParameter(11,
					(bean.getX_WASTAGE_FACTOR().equalsIgnoreCase("n/a") || bean.getX_WASTAGE_FACTOR().equals("")) ? 0
							: bean.getX_WASTAGE_FACTOR());
			query.setParameter(12,
					(bean.getX_TARGET_COVERAGE().equalsIgnoreCase("n/a") || bean.getX_TARGET_COVERAGE().equals("")) ? 0
							: bean.getX_TARGET_COVERAGE());
			query.setParameter(13, userBean.getX_USER_ID());
			query.setParameter(14, bean.getX_ITEM_NAME());
			query.setParameter(15, bean.getX_ITEM_NAME());
			if (action.equals("add")) {
				query.setParameter(16, userBean.getX_USER_ID());
				query.setParameter(17, userBean.getX_WAREHOUSE_ID());

			} else {
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

}
