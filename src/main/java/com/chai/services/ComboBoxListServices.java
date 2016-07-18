package com.chai.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONArray;

import com.chai.hibernartesessionfactory.HibernateSessionFactoryClass;
import com.chai.model.LabelValueBean;
import com.chai.util.GetJsonResultSet;

public class ComboBoxListServices {
	Transaction tx = null;
	SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
	SQLQuery query=null;
	public  JSONArray getComboboxList(String... args) {
		System.out.println("-- ComboboxlistService.getComboboxList() mehtod called: -- ");
		String x_QUERY="";
		JSONArray array=new JSONArray();
		Session session = sf.openSession();
		if(args[0]!=null){
			if(args[0].equals("lgalistBasedonstate")){
			x_QUERY="SELECT WAREHOUSE_ID, WAREHOUSE_NAME "
					+ " FROM VIEW_INVENTORY_WAREHOUSES "
					+ " WHERE DEFAULT_ORDERING_WAREHOUSE_ID ='"+args[1]+"' ORDER BY WAREHOUSE_NAME";
			}else if(args[0].equals("STATE_STORE")){
				x_QUERY = " SELECT WAREHOUSE_ID, WAREHOUSE_NAME "
						+ " FROM VIEW_INVENTORY_WAREHOUSES "
						+ " WHERE DEFAULT_ORDERING_WAREHOUSE_ID = "
						+ args[1]
						+ " ORDER BY WAREHOUSE_NAME";
			}else if(args[0].equals("hfListLgaBased")){
				x_QUERY = " SELECT IFNULL(CUSTOMER_ID,DB_ID) CUSTOMER_ID,         CUSTOMER_NAME   "
						+" FROM VIEW_CUSTOMERS  "
						+" WHERE DEFAULT_STORE_ID =" +args[1]
						+" ORDER BY CUSTOMER_NAME ";
			}else if(args[0].equals("TRANSACTION_TYPE")){
				x_QUERY = "SELECT TYPE_ID, TYPE_NAME  FROM TYPES " 
						+" WHERE SOURCE_TYPE='TRANSACTION_TYPE' "
						+" AND TYPE_ID NOT IN(F_GET_TYPE('TRANSACTION_TYPE', 'MISC_ISSUE'), "
						+" F_GET_TYPE('TRANSACTION_TYPE', 'MISC_RECEIPT'),F_GET_TYPE('TRANSACTION_TYPE', 'INTER_FACILITY'))";
			}else if(args[0].equals("REASON_TYPE")){
				x_QUERY = "SELECT TYPE_ID, TYPE_NAME  FROM TYPES  WHERE SOURCE_TYPE='STOCK ADJUSTMENTS'";
			}else if(args[0].equals("USER_TYPE")){
				x_QUERY = "SELECT TYPE_ID, TYPE_NAME 	"	 
						+" FROM VIEW_TYPES "
						+" WHERE SOURCE_TYPE = 'USER TYPES' ORDER BY TYPE_NAME ";
			}else if(args[0].equals("USER_ROLE_NAME")){
				if(args[2].equalsIgnoreCase("SCCO")
						|| args[2].equalsIgnoreCase("SIO")
						|| args[2].equalsIgnoreCase("SIFP")){
					if(args[1].equalsIgnoreCase("Admin")){
						x_QUERY = "SELECT ROLE_ID,   ROLE_NAME FROM ADM_ROLES "
								+"  WHERE STATUS = 'A'  AND ROLE_NAME <> 'NTO'  AND ROLE_NAME <> 'CCO'  ORDER BY ROLE_NAME  ";
					}else{
						x_QUERY = "SELECT ROLE_ID,   ROLE_NAME FROM ADM_ROLES "
								+" WHERE STATUS = 'A'  AND ROLE_NAME = 'CCO'  ORDER BY ROLE_NAME ";
					}
					
				}else if(args[2].equalsIgnoreCase("LIO")
						|| args[2].equalsIgnoreCase("MOH")){
					if(args[1].equalsIgnoreCase("Admin")){
						x_QUERY = "SELECT ROLE_ID,   ROLE_NAME FROM ADM_ROLES "
								+"  WHERE STATUS = 'A'    AND ROLE_NAME = '"+args[2]+"' ";
					}else{
						x_QUERY = "SELECT ROLE_ID,   ROLE_NAME FROM ADM_ROLES "
								+" WHERE STATUS = 'A'  AND ROLE_NAME = 'CCO'   ";
					}
				}else if(args[2].equalsIgnoreCase("NTO")){
					if(args[1].equalsIgnoreCase("Admin")){
						x_QUERY = "SELECT ROLE_ID,   ROLE_NAME FROM ADM_ROLES "
								+"  WHERE STATUS = 'A'    AND ROLE_NAME <> 'CCO'  ORDER BY ROLE_NAME  ";
					}else{
						x_QUERY = "SELECT ROLE_ID,   ROLE_NAME FROM ADM_ROLES "
								+" WHERE STATUS = 'A'  AND ROLE_NAME = 'CCO'  ORDER BY ROLE_NAME ";
					}
				}
				
			} else if (args[0].equals("COUNTRY_LIST")) {
				x_QUERY = "SELECT COUNTRY_ID, 		  COUNTRY_NAME "
						+ "   FROM VIEW_COUNTRIES  WHERE COUNTRY_NAME IS NOT NULL   "
						+ " AND COUNTRY_NAME <> '' AND STATUS='A'  ORDER BY COUNTRY_NAME  ";
			}
			
		}
		try {
			query = session.createSQLQuery(x_QUERY);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			if (args.length == 3) {
				if (args[2] != null && args[2].equals("All")) {
					System.out.println("list with alloption");
					array = GetJsonResultSet.getjsonCombolist(resultlist, true);
				} else {
					array = GetJsonResultSet.getjsonCombolist(resultlist, false);
				}
			}else{
				System.out.println("list with Without Alloption");
				array=GetJsonResultSet.getjsonCombolist(resultlist,false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}
		
	public  JSONArray getProductList(String... args) {
		System.out.println("-- Comboboxlistserivces.getProductList() mehtod called: -- ");
		String x_QUERY="";
		JSONArray array=new JSONArray();
		Session session = sf.openSession();
		if(args[0]!=null){
			 if(args[0].equals("productlistbassedonlga")){
				x_QUERY="SELECT ITEM_ID, "
						+ "	  ITEM_NUMBER FROM ITEM_MASTERS "
						+ " WHERE STATUS = 'A' AND WAREHOUSE_ID=IFNULL('"+args[1]+"',WAREHOUSE_ID) "
						+ "ORDER BY ITEM_NUMBER";
			}
		}
		try {
			query = session.createSQLQuery(x_QUERY);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array=GetJsonResultSet.getjsonCombolist(resultlist,Boolean.parseBoolean(args[2]));
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}
	public  List<LabelValueBean> getProductListInBean(String... args) {
		System.out.println("-- Comboboxlistserivces.getProductListInBean() mehtod called: -- ");
		String x_QUERY="";
		Session session = sf.openSession();
		List<LabelValueBean> array=null;;
		if(args[0]!=null){
			 if(args[0].equals("productlistbassedonlga")){
				x_QUERY="SELECT ITEM_ID, ITEM_NUMBER"
						+" FROM VIEW_ITEM_MASTERS "
						+" WHERE STATUS = 'A' "
						+" AND ITEM_TYPE_ID IN (F_GET_TYPE('PRODUCT','VACCINE')) "
						+" AND  warehouse_id="+args[1]+"  ORDER BY ITEM_NUMBER ";
			}
		}
		try {
			query = sf.openSession().createSQLQuery(x_QUERY);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getCombolistInBean(resultlist, Boolean.parseBoolean(args[2]));
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}
}
