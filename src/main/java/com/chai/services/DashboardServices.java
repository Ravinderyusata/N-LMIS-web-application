package com.chai.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.chai.hibernartesessionfactory.HibernateSessionFactoryClass;
import com.chai.model.views.AdmUserV;
import com.chai.util.GetJsonResultSet;

@Repository
public class DashboardServices {
	Logger logger=Logger.getLogger(DashboardServices.class);
	SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();

	public JSONArray getLgaStockSummaryGridData(AdmUserV userBean, String year, String week, String lgaID) {
		System.out.println("-- DashboardServices.getLgaStockSummaryGridData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		try {
			String x_query = "";
			if (userBean.getX_ROLE_NAME().equals("NTO")) {
				x_query = "select STATE_ID,STATE_NAME,LGA_ID, LGA_NAME,ITEM_ID , "
						+ " ITEM_NUMBER, YEAR ,WEEK,ONHAND_QUANTITY AS ONHAND_QUANTITY,LEGEND_FLAG ,LEGEND_COLOR"
						+ " from STATE_LCCO_stock_performance_dashbord_v " + " where year=" + year + " and week=" + week
						+ " AND STATE_ID=" + lgaID + " ORDER BY LGA_NAME, ITEM_NUMBER";
			} else {
				x_query = "select STATE_ID,STATE_NAME,LGA_ID, LGA_NAME,ITEM_ID , "
						+ " ITEM_NUMBER, YEAR ,WEEK,ONHAND_QUANTITY AS ONHAND_QUANTITY,LEGEND_FLAG ,LEGEND_COLOR"
						+ " from STATE_LCCO_stock_performance_dashbord_v " + " where year=" + year + " and week=" + week
						+ " AND STATE_ID=" + userBean.getX_WAREHOUSE_ID() + " ORDER BY LGA_NAME, ITEM_NUMBER";
			}
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjson(resultlist);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}
	
	public JSONArray getHFStockSummaryGridData(String year, String week, String lgaID) {
		System.out.println("-- DashboardServices.getHFStockSummaryGridData() mehtod called: lgaID=-- "+lgaID);
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		StringBuilder queryBuilder = new StringBuilder("SELECT LGA_ID, LGA_NAME, CUSTOMER_ID, CUSTOMER_NAME, ITEM_ID, ITEM_NUMBER, STOCK_RECEIVED_DATE, STOCK_BALANCE, MIN_STOCK, MAX_STOCK, LEGEND_FLAG, LEGEND_COLOR FROM HF_STOCK_PERFORMANCE_DASHBORD_V WHERE DATE_FORMAT(STOCK_RECEIVED_DATE,'%Y-%v')='");
		  queryBuilder.append(year)
					  .append("-")
					  .append(week)
					  .append("' AND LGA_ID=")
					  .append(lgaID);	
		try {					
			SQLQuery query = session.createSQLQuery(queryBuilder.toString());
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			logger.info(queryBuilder.toString());
			List resultlist = query.list();
			array = GetJsonResultSet.getjson(resultlist);			
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();			
		}		
		return array;
	}
	
	public List activeHFWithZeroData(String year, String week, String lgaID) throws Exception{
//		StringBuilder queryBuilder = new StringBuilder("SELECT CUSTOMER_NAME FROM CUSTOMERS WHERE CUSTOMER_ID NOT IN (");
//		queryBuilder.append("SELECT DISTINCT CUST.CUSTOMER_ID AS CUSTOMER_ID FROM ((((((VERTICAL.CUSTOMER_PRODUCT_CONSUMPTION CONS LEFT JOIN VERTICAL.ORDER_LINES LINE ON(((CONS.CONSUMPTION_ID = LINE.CONSUMPTION_ID) AND (CONS.WAREHOUSE_ID = LINE.ORDER_FROM_ID) AND (CONS.CUSTOMER_ID = LINE.ORDER_TO_ID) AND (CONS.ITEM_ID = LINE.ITEM_ID) AND (WEEK(CONS.DATE,0) = WEEK(LINE.CREATED_DATE,0)) AND (YEAR(CONS.DATE) = YEAR(LINE.CREATED_DATE))))) LEFT JOIN VERTICAL.ORDER_HEADERS HDR ON(((LINE.ORDER_HEADER_ID = HDR.ORDER_HEADER_ID) AND (HDR.ORDER_STATUS_ID = 10)))) JOIN VERTICAL.CUSTOMERS CUST ON(((CONS.CUSTOMER_ID = CUST.CUSTOMER_ID) AND (CUST.STATUS = 'A')))) JOIN VERTICAL.ITEM_MASTERS ITM ON(((CONS.ITEM_ID = ITM.ITEM_ID) AND (CUST.DEFAULT_STORE_ID = ITM.WAREHOUSE_ID)))) JOIN VERTICAL.INVENTORY_WAREHOUSES INV ON((CONS.WAREHOUSE_ID = INV.WAREHOUSE_ID))) JOIN VERTICAL.INVENTORY_WAREHOUSES INV2 ON((INV.DEFAULT_ORDERING_WAREHOUSE_ID = INV2.WAREHOUSE_ID))) WHERE (CONS.VALID = 'Y') AND DATE_FORMAT(CONS.DATE,'%Y-%v')='")
//					.append(year)
//					.append("-")
//					.append(week)
//					.append("' AND CONS.WAREHOUSE_ID=")
//					.append(lgaID)
//					.append(" GROUP BY INV.DEFAULT_ORDERING_WAREHOUSE_ID,CONS.WAREHOUSE_ID,CONS.CUSTOMER_ID, CONS.ITEM_ID,YEAR(CONS.DATE),WEEK(CONS.DATE,0)")
//					.append(") AND DEFAULT_STORE_ID=")
//					.append(lgaID)
//					.append(" AND STATUS='A'");
		StringBuilder queryBuilder = new StringBuilder("SELECT CUSTOMER_NAME FROM CUSTOMERS WHERE DEFAULT_STORE_ID = ");
					  queryBuilder.append(lgaID)
					  			  .append(" AND STATUS='A' AND VACCINE_FLAG='Y'");
			SQLQuery query = sf.openSession().createSQLQuery(queryBuilder.toString());
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			logger.info(queryBuilder.toString());
		List list = query.list();
		return list;
	}
	
	public JSONArray getStateStockPerfDashboard(AdmUserV userBean,String year,String week,String lgaid){
		System.out.println("-- DashboardServices.getStateStockPerfDashboard() mehtod called: -- ");
		JSONArray array=new JSONArray();
		Session session = sf.openSession();
		String previousWeek="";
		String previousyear="";
		try {
			String passNullAsStateIdIfLIOMOH;
			if(userBean.getX_ROLE_NAME().toUpperCase().equals("LIO") || userBean.getX_ROLE_NAME().toUpperCase().equals("MOH")){
				passNullAsStateIdIfLIOMOH = null;
			}else{
				passNullAsStateIdIfLIOMOH = Integer.toString(userBean.getX_WAREHOUSE_ID());
			}
			if(week.equals("01")){
				previousyear=String.valueOf(Integer.parseInt(year)-1);
				previousWeek="52";
			}else{
				if((Integer.parseInt(week)-1)<10){
					previousWeek="0"+String.valueOf(Integer.parseInt(week)-1);
				}else{
					previousWeek=String.valueOf(Integer.parseInt(week)-1);	
				}
				previousyear=year;
			}
			String x_query="SELECT STATE_ID,"
					+" STATE_NAME,"
					+"  LGA_ID,"
					+"	 LGA_NAME,"
					+" STOCK_RECEIVED_YEAR,"
					+"  STOCK_RECEIVED_WEEK,"
					+" LESS_3_ANTIGENS_TOTAL_HF_PER,"
					+" LESS_3_ANTIGENS_TOTAL_HF_PER_FLAG,"
					+"   GREATER_2_ANTIGENS_TOTAL_HF_PER,"
					+" GREATER_2_ANTIGENS_TOTAL_HF_PER_FLAG,"
					+"   SUFFICIENT_STOCK_TOTAL_HF_PER,"
					+" SUFFICIENT_STOCK_TOTAL_HF_PER_FLAG FROM LGA_STOCK_PERFORMANCE_DASHBOARD_V "
					+" WHERE STOCK_RECEIVED_YEAR="+year
					+" AND STOCK_RECEIVED_WEEK="+week
					+" AND LGA_ID=IFNULL("+lgaid+",LGA_ID) AND STATE_ID=IFNULL("+passNullAsStateIdIfLIOMOH+",STATE_ID)";
			SQLQuery query = session.createSQLQuery(x_query);
			System.out.println("QUERY1: "+x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist1 = query.list();
			String x_query2="SELECT STATE_ID,"
					+" STATE_NAME,"
					+"  LGA_ID,"
					+"	 LGA_NAME,"
					+" STOCK_RECEIVED_YEAR,"
					+"  STOCK_RECEIVED_WEEK,"
					+" LESS_3_ANTIGENS_TOTAL_HF_PER,"
					+" LESS_3_ANTIGENS_TOTAL_HF_PER_FLAG,"
					+"   GREATER_2_ANTIGENS_TOTAL_HF_PER,"
					+" GREATER_2_ANTIGENS_TOTAL_HF_PER_FLAG,"
					+"   SUFFICIENT_STOCK_TOTAL_HF_PER,"
					+" SUFFICIENT_STOCK_TOTAL_HF_PER_FLAG FROM LGA_STOCK_PERFORMANCE_DASHBOARD_V "
					+" WHERE STOCK_RECEIVED_YEAR="+previousyear
					+" AND STOCK_RECEIVED_WEEK="+previousWeek
					+" AND LGA_ID=IFNULL("+lgaid+",LGA_ID) AND STATE_ID=IFNULL("+passNullAsStateIdIfLIOMOH+",STATE_ID)";
			System.out.println("QUERY2: "+x_query2);
			SQLQuery query1 = session.createSQLQuery(x_query2);
			query1.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist2 = query1.list();
			for (int i = 0; i < resultlist1.size(); i++) {
				JSONObject obj=new JSONObject();
				HashMap<String, String> row = (HashMap)resultlist1.get(i);
				HashMap<String, String> rowCompare = (HashMap)resultlist2.get(i);
			       for ( Object key : row.keySet()) {
			    	   obj.put((String)key, row.get(key));
			    	}
			       int currunt_green=Integer.parseInt(String.valueOf(row.get("SUFFICIENT_STOCK_TOTAL_HF_PER")));
			       int pre_green=Integer.parseInt(String.valueOf(rowCompare.get("SUFFICIENT_STOCK_TOTAL_HF_PER")));
			       int currunt_red=Integer.parseInt(String.valueOf(row.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")));
			       int pre_red=Integer.parseInt(String.valueOf(rowCompare.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")));
			       if(currunt_green>pre_green){
			    	   obj.put("rotation", 270);
			       }else if((currunt_green<pre_green) ){
			    	   obj.put("rotation", 90);			    	  
			       }else if(currunt_green==pre_green){
					// System.out.println("green same");
			    	   if(currunt_red>pre_red){			    		  
			    		   obj.put("rotation", 90);
			    	   }else if(currunt_red<pre_red){			    		  
			    		   obj.put("rotation", 270);
			    	   }else if(currunt_green==pre_green && currunt_red==pre_red){
			    		   obj.put("rotation", 0);			    		  
			    	   }			    	   
			       }
				array.put(obj);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}
	
	public JSONArray getStateStockStatusDashboard(AdmUserV userBean,String year,String week,String lgaid){
		System.out.println("-- DashboardServices.getStateStockStatusDashboard() mehtod called: -- ");
		JSONArray array=new JSONArray();
		SQLQuery query=null;
		String previousWeek="";
		String previousyear="";
		String queryForCurrentWeek = "";
		String queryForPreviousWeek = "";
		String queryForUnioun = "";
		Session session = sf.openSession();
		Transaction tx = null;
		try {
			if(week.equals("01")){
				previousyear=String.valueOf(Integer.parseInt(year)-1);
				previousWeek="52";
			}else{
				if((Integer.parseInt(week)-1)<10){
					previousWeek="0"+String.valueOf(Integer.parseInt(week)-1);
				}else{
					previousWeek=String.valueOf(Integer.parseInt(week)-1);	
				}
				previousyear=year;
			}
			if(lgaid.equals("null") || lgaid.equals("")){
//				System.out.println("selected State is All");
				queryForCurrentWeek = "SELECT STATE_ID, STATE_NAME, LGA_ID, LGA_NAME, "
						+ "REORDER_STOCK_COUNT_Y_PER, REORDER_STOCK_COUNT_Y_FLAG, "
						+ "INSUFFICIENT_STOCK_COUNT_R_PER, INSUFFICIENT_STOCK_COUNT_FLAG, "
						+ " SUFFICIENT_STOCK_COUNT_G_PER,SUFFICIENT_STOCK_COUNT_G_FLAG "
						+ " FROM NATIONAL_LCCO_ITEM_STOCK_PERFORMANCE_DASHBORD_SUMMARY_V  " 
						+ " WHERE YEAR="+year
						+ " AND WEEK="+week+" "+" AND STATE_ID=IFNULL("+lgaid+",STATE_ID) ORDER BY STATE_NAME,LGA_NAME"; 
						
//						+ " union "
//						+ " SELECT     INV.DEFAULT_ORDERING_WAREHOUSE_ID AS STATE_ID, "
//						+ "INV2.WAREHOUSE_CODE STATE_NAME,   INV.WAREHOUSE_ID AS LGA_ID,"
//						+ " INV.WAREHOUSE_CODE AS LGA_NAME,    0 REORDER_STOCK_COUNT_Y_PER,"
//						+ " '' REORDER_STOCK_COUNT_Y_FLAG,   100 INSUFFICIENT_STOCK_COUNT_R_PER,"
//						+ " 'RED' INSUFFICIENT_STOCK_COUNT_FLAG, 0 SUFFICIENT_STOCK_COUNT_G_PER,"
//						+ " '' SUFFICIENT_STOCK_COUNT_G_FLAG   "
//						+ " FROM ACTIVE_WAREHOUSES_V INV     JOIN INVENTORY_WAREHOUSES INV2 "
//						+ " ON INV.DEFAULT_ORDERING_WAREHOUSE_ID = INV2.WAREHOUSE_ID   "
//						+ " WHERE INV.DEFAULT_ORDERING_WAREHOUSE_ID<>'101' "
//						+ " AND INV.DEFAULT_ORDERING_WAREHOUSE_ID IS NOT NULL "
//						+ "AND INV.WAREHOUSE_ID    NOT IN (SELECT LGA_ID  "
//						+ "  FROM National_lcco_ITEM_stock_performance_dashbord_SUMMARY_v" + " WHERE YEAR=" + year
//						+ " AND WEEK=" + week + " AND STATE_ID=IFNULL(" + lgaid
//						+ ",STATE_ID))  ORDER BY STATE_NAME,LGA_NAME";

				queryForPreviousWeek = "SELECT STATE_ID, STATE_NAME,  LGA_ID,	 LGA_NAME, "
						+ "REORDER_STOCK_COUNT_Y_PER, REORDER_STOCK_COUNT_Y_FLAG, "
						+ "INSUFFICIENT_STOCK_COUNT_R_PER, INSUFFICIENT_STOCK_COUNT_FLAG, "
						+ " SUFFICIENT_STOCK_COUNT_G_PER,SUFFICIENT_STOCK_COUNT_G_FLAG "
						+ " FROM National_lcco_ITEM_stock_performance_dashbord_SUMMARY_v "
						+ " WHERE YEAR="+previousyear
						+"    AND WEEK="+previousWeek
						   +" AND STATE_ID=IFNULL("+lgaid+",STATE_ID) "
					  + "ORDER BY STATE_NAME,LGA_NAME";
						
//						+ " union " + " SELECT     INV.DEFAULT_ORDERING_WAREHOUSE_ID AS STATE_ID, "
//						+ "INV2.WAREHOUSE_CODE STATE_NAME,   INV.WAREHOUSE_ID AS LGA_ID,"
//						+ " INV.WAREHOUSE_CODE AS LGA_NAME,    0 REORDER_STOCK_COUNT_Y_PER,"
//						+ " '' REORDER_STOCK_COUNT_Y_FLAG,   100 INSUFFICIENT_STOCK_COUNT_R_PER,"
//						+ " 'RED' INSUFFICIENT_STOCK_COUNT_FLAG, 0 SUFFICIENT_STOCK_COUNT_G_PER,"
//						+ " '' SUFFICIENT_STOCK_COUNT_G_FLAG   "
//						+ " FROM ACTIVE_WAREHOUSES_V INV     JOIN INVENTORY_WAREHOUSES INV2 "
//						+ " ON INV.DEFAULT_ORDERING_WAREHOUSE_ID = INV2.WAREHOUSE_ID   "
//						+ " WHERE INV.DEFAULT_ORDERING_WAREHOUSE_ID<>'101' "
//						+ " AND INV.DEFAULT_ORDERING_WAREHOUSE_ID IS NOT NULL "
//						+ "AND INV.WAREHOUSE_ID    NOT IN (SELECT LGA_ID  "
//						+ "  FROM National_lcco_ITEM_stock_performance_dashbord_SUMMARY_v" + " WHERE YEAR="
//						+ previousyear + " AND WEEK=" + previousWeek + " AND STATE_ID=IFNULL(" + lgaid
//						+ ",STATE_ID))  ORDER BY STATE_NAME,LGA_NAME";
			}
			else{
//				System.out.println("Select State Is: " + lgaid);
				queryForCurrentWeek = " SELECT STATE_ID, STATE_NAME, LGA_ID, LGA_NAME, "
						+ "  REORDER_STOCK_COUNT_Y_PER, REORDER_STOCK_COUNT_Y_FLAG,  "
						+ " INSUFFICIENT_STOCK_COUNT_R_PER, INSUFFICIENT_STOCK_COUNT_FLAG,   "
						+ " SUFFICIENT_STOCK_COUNT_G_PER,SUFFICIENT_STOCK_COUNT_G_FLAG  "
						+ " FROM National_lcco_ITEM_stock_performance_dashbord_SUMMARY_v "
						+ " WHERE YEAR="+year
						+" AND WEEK="+week
						+" AND STATE_ID=IFNULL("+lgaid+",STATE_ID)";
//						+ " union "
//						+ " SELECT INV.WAREHOUSE_ID AS STATE_ID,  INV.WAREHOUSE_CODE AS STATE_NAME,  "
//						+ " INV2.WAREHOUSE_ID AS LGA_ID,  INV2.WAREHOUSE_CODE AS LGA_NAME, "
//						+ " 0 REORDER_STOCK_COUNT_Y_PER, '' REORDER_STOCK_COUNT_Y_FLAG,  "
//						+ " 100 INSUFFICIENT_STOCK_COUNT_R_PER, 'RED' INSUFFICIENT_STOCK_COUNT_FLAG, "
//						+ "    0 SUFFICIENT_STOCK_COUNT_G_PER,'' SUFFICIENT_STOCK_COUNT_G_FLAG  "
//						+ " FROM INVENTORY_WAREHOUSES INV  " + " JOIN ACTIVE_WAREHOUSES_V INV2  "
//						+ " ON INV.WAREHOUSE_ID = INV2.DEFAULT_ORDERING_WAREHOUSE_ID  "
//						+ " WHERE  INV.WAREHOUSE_ID=IFNULL(" + lgaid + ",inv.WAREHOUSE_ID)   "
//						+ " AND INV2.WAREHOUSE_ID NOT IN (SELECT LGA_ID "
//						+ " FROM National_lcco_ITEM_stock_performance_dashbord_SUMMARY_v " + " WHERE YEAR=" + year
//						+ " AND WEEK=" + week + " AND STATE_ID=IFNULL(" + lgaid + ",STATE_ID))";
				queryForPreviousWeek = " SELECT STATE_ID, STATE_NAME,  LGA_ID,	 LGA_NAME, "
						+ "  REORDER_STOCK_COUNT_Y_PER, REORDER_STOCK_COUNT_Y_FLAG,  "
						+ " INSUFFICIENT_STOCK_COUNT_R_PER, INSUFFICIENT_STOCK_COUNT_FLAG,   "
						+ " SUFFICIENT_STOCK_COUNT_G_PER,SUFFICIENT_STOCK_COUNT_G_FLAG  "
						+ " FROM National_lcco_ITEM_stock_performance_dashbord_SUMMARY_v "
						+ " WHERE YEAR="+previousyear
						   +" AND WEEK="+previousWeek
						   +" AND STATE_ID=IFNULL("+lgaid+",STATE_ID)";
//						+ " union "
//						+ " SELECT   INV.WAREHOUSE_ID AS STATE_ID,  INV.WAREHOUSE_CODE AS STATE_NAME,  "
//						+ " INV2.WAREHOUSE_ID AS LGA_ID,  INV2.WAREHOUSE_CODE AS LGA_NAME, "
//						+ " 0 REORDER_STOCK_COUNT_Y_PER, '' REORDER_STOCK_COUNT_Y_FLAG,  "
//						+ " 100 INSUFFICIENT_STOCK_COUNT_R_PER, 'RED' INSUFFICIENT_STOCK_COUNT_FLAG, "
//						+ "    0 SUFFICIENT_STOCK_COUNT_G_PER,'' SUFFICIENT_STOCK_COUNT_G_FLAG  "
//						+ " FROM INVENTORY_WAREHOUSES INV  " + " JOIN ACTIVE_WAREHOUSES_V INV2  "
//						+ " ON INV.WAREHOUSE_ID = INV2.DEFAULT_ORDERING_WAREHOUSE_ID  "
//						+ " WHERE  INV.WAREHOUSE_ID=IFNULL(" + lgaid + ",inv.WAREHOUSE_ID)   "
//						+ " AND INV2.WAREHOUSE_ID NOT IN (SELECT LGA_ID "
//						+ " FROM National_lcco_ITEM_stock_performance_dashbord_SUMMARY_v " + " WHERE YEAR=" + year
//						+ " AND WEEK=" + week + " AND STATE_ID=IFNULL(" + lgaid + ",STATE_ID))";
			}
			query = session.createSQLQuery(queryForCurrentWeek);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist1 = query.list();
			query = session.createSQLQuery(queryForPreviousWeek);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist2 = query.list();

			HashSet<String> state = new HashSet<>();
			for (int i = 0; i < resultlist1.size(); i++) {
				JSONObject obj = new JSONObject();
				HashMap<String, String> row = (HashMap) resultlist1.get(i);
				HashMap<String, String> rowCompare = (HashMap) resultlist2.get(i);
				for (Object key : row.keySet()) {
					obj.put((String) key, row.get(key));
				}
				int currunt_green = Integer.parseInt(String.valueOf(row.get("SUFFICIENT_STOCK_COUNT_G_PER")));
				int pre_green = Integer.parseInt(String.valueOf(rowCompare.get("SUFFICIENT_STOCK_COUNT_G_PER")));
				int currunt_red = Integer.parseInt(String.valueOf(row.get("INSUFFICIENT_STOCK_COUNT_R_PER")));
				int pre_red = Integer.parseInt(String.valueOf(rowCompare.get("INSUFFICIENT_STOCK_COUNT_R_PER")));
				if (currunt_green > pre_green) {
					obj.put("rotation", 270);
				} else if ((currunt_green < pre_green)) {
					obj.put("rotation", 90);
				} else if (currunt_green == pre_green) {
//					System.out.println("green same");
					if (currunt_red > pre_red) {
						obj.put("rotation", 90);
					} else if (currunt_red < pre_red) {
						obj.put("rotation", 270);
					} else if (currunt_green == pre_green && currunt_red == pre_red) {
						obj.put("rotation", 0);
					}
				}
				array.put(obj);
			}
	}catch (Exception e) {
		e.printStackTrace();
		} finally {
			session.close();
	}
		return array;
	}
	
	public JSONArray getLgaAggStockDashboardData(String year,String week){
		System.out.println("-- DashboardServices.getLgaAggStockDashboardData() mehtod called: -- ");
		JSONArray array=new JSONArray();
		SQLQuery query=null;
		String previousWeek="";
		String previousyear="";
		Session session = sf.openSession();
		try {
			if(week.equals("01")){
				previousyear=String.valueOf(Integer.parseInt(year)-1);
				previousWeek="52";
			}else{
				if((Integer.parseInt(week)-1)<10){
					previousWeek="0"+String.valueOf(Integer.parseInt(week)-1);
				}else{
					previousWeek=String.valueOf(Integer.parseInt(week)-1);	
				}
				previousyear=year;
			}
			String x_query=" SELECT STATE_ID, STATE_NAME, "
					+"   LESS_3_ANTIGENS_TOTAL_HF_PER,"
					+"   LESS_3_ANTIGENS_TOTAL_HF_PER_FLAG,  "
					+"   GREATER_2_ANTIGENS_TOTAL_HF_PER, GREATER_2_ANTIGENS_TOTAL_HF_PER_FLAG, "
					+"   SUFFICIENT_STOCK_TOTAL_HF_PER, SUFFICIENT_STOCK_TOTAL_HF_PER_FLAG"
					+"   FROM National_State_stock_performance_dashbord_SUMMARY_v "
					+" where year="+year+" AND week="+week
					+"   ORDER BY STATE_NAME";
//					+"   union"
//					+"   SELECT    "
//					+"  INV.WAREHOUSE_ID AS STATE_ID, INV.WAREHOUSE_CODE STATE_NAME,      "
//					+"  0 LESS_3_ANTIGENS_TOTAL_HF_PER, "
//					+"  '' LESS_3_ANTIGENS_TOTAL_HF_PER_FLAG,     "
//					+"  100 GREATER_2_ANTIGENS_TOTAL_HF_PER,"
//					+"  'RED' GREATER_2_ANTIGENS_TOTAL_HF_PER_FLAG,     "
//					+"  0 SUFFICIENT_STOCK_TOTAL_HF_PER,"
//					+"  '' SUFFICIENT_STOCK_TOTAL_HF_PER_FLAG    "
//					+"  FROM INVENTORY_WAREHOUSES INV   "
//					+"  WHERE INV.DEFAULT_ORDERING_WAREHOUSE_ID=101 and DEFAULT_ORDERING_WAREHOUSE_ID is NOT NULL"
//					+"  AND INV.WAREHOUSE_ID    "
//					+"  NOT IN (SELECT STATE_ID FROM National_State_stock_performance_dashbord_SUMMARY_v  "
//					+" where year="+year+" AND week="+week+") "
					
			query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist1 = query.list();
			String x_query2=" SELECT STATE_ID, STATE_NAME, "
					+"   LESS_3_ANTIGENS_TOTAL_HF_PER,"
					+"   LESS_3_ANTIGENS_TOTAL_HF_PER_FLAG,  "
					+"   GREATER_2_ANTIGENS_TOTAL_HF_PER, GREATER_2_ANTIGENS_TOTAL_HF_PER_FLAG, "
					+"   SUFFICIENT_STOCK_TOTAL_HF_PER, SUFFICIENT_STOCK_TOTAL_HF_PER_FLAG"
					+"   FROM National_State_stock_performance_dashbord_SUMMARY_v "
					+" where year="+previousyear+" AND week="+previousWeek
//					+"   union"
//					+"   SELECT    "
//					+"  INV.WAREHOUSE_ID AS STATE_ID, INV.WAREHOUSE_CODE STATE_NAME,      "
//					+"  0 LESS_3_ANTIGENS_TOTAL_HF_PER, "
//					+"  '' LESS_3_ANTIGENS_TOTAL_HF_PER_FLAG,     "
//					+"  100 GREATER_2_ANTIGENS_TOTAL_HF_PER,"
//					+"  'RED' GREATER_2_ANTIGENS_TOTAL_HF_PER_FLAG,     "
//					+"  0 SUFFICIENT_STOCK_TOTAL_HF_PER,"
//					+"  '' SUFFICIENT_STOCK_TOTAL_HF_PER_FLAG    "
//					+"  FROM INVENTORY_WAREHOUSES INV   "
//					+"  WHERE INV.DEFAULT_ORDERING_WAREHOUSE_ID=101 and DEFAULT_ORDERING_WAREHOUSE_ID is NOT NULL"
//					+"  AND INV.WAREHOUSE_ID    "
//					+"  NOT IN (SELECT STATE_ID FROM National_State_stock_performance_dashbord_SUMMARY_v  "
//					+" where year="+previousyear+" AND week="+previousWeek+") "
					+"   ORDER BY STATE_NAME";
			query = session.createSQLQuery(x_query2);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist2 = query.list();
			HashSet<String> state=new HashSet<>();
			for (int i = 0; i < resultlist1.size(); i++) {
				JSONObject obj=new JSONObject();
				HashMap<String, String> row = (HashMap)resultlist1.get(i);
				HashMap<String, String> rowCompare = (HashMap)resultlist2.get(i);
			       for ( Object key : row.keySet()) {
			    	   obj.put((String)key, row.get(key));
			    	}
			       int currunt_green=Integer.parseInt(String.valueOf(row.get("SUFFICIENT_STOCK_TOTAL_HF_PER")));
			       int pre_green=Integer.parseInt(String.valueOf(rowCompare.get("SUFFICIENT_STOCK_TOTAL_HF_PER")));
			       int currunt_red=Integer.parseInt(String.valueOf(row.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")));
			       int pre_red=Integer.parseInt(String.valueOf(rowCompare.get("GREATER_2_ANTIGENS_TOTAL_HF_PER")));
			       if(currunt_green>pre_green){
			    	    obj.put("rotation", 270);
			       }else if((currunt_green<pre_green) ){
			    	   obj.put("rotation", 90);
			    	 }else if(currunt_green==pre_green){
					// System.out.println("green same");
			    	   if(currunt_red>pre_red){
			    		   obj.put("rotation", 90);
			    	   }else if(currunt_red<pre_red){
			    		  obj.put("rotation", 270);
			    	   }else if(currunt_green==pre_green && currunt_red==pre_red){
			    		   obj.put("rotation", 0);			    		  
			    	   }			    	   
			       }
				array.put(obj);
			}
	}catch (Exception e) {
		e.printStackTrace();
	} finally {
		session.close();
	}
		return array;
	}
	
	public JSONArray getLgaStockBalanceDashboardData(AdmUserV userBean, String year, String week, String lgaID) {
		System.out.println("-- DashboardServices.getLgaStockBalanceDashboardData() mehtod called: -- ");
		JSONArray array=new JSONArray();
		Session session = sf.openSession();
		try {
			String x_query="";
			if(userBean.getX_ROLE_NAME().equals("NTO")){
			 x_query="select STATE_ID,STATE_NAME,LGA_ID, LGA_NAME,ITEM_ID , "
						+ " ITEM_NUMBER, YEAR ,WEEK,ONHAND_QUANTITY AS ONHAND_QUANTITY,LEGEND_FLAG ,LEGEND_COLOR"
						+" from STATE_LGA_STOCK_BALANCE_ONLY_FOR_DASHBOARD_V "
						+" where year="+year+" and week="+week+" AND STATE_ID="+lgaID
						+" ORDER BY LGA_NAME, ITEM_NUMBER";
			}else{
				 x_query="select STATE_ID,STATE_NAME,LGA_ID, LGA_NAME,ITEM_ID , "
						+ " ITEM_NUMBER, YEAR ,WEEK,ONHAND_QUANTITY AS ONHAND_QUANTITY,LEGEND_FLAG ,LEGEND_COLOR"
						+" from STATE_LGA_STOCK_BALANCE_ONLY_FOR_DASHBOARD_V "
						+" where year="+year+" and week="+week+" AND STATE_ID="+userBean.getX_WAREHOUSE_ID()
						+" ORDER BY LGA_NAME, ITEM_NUMBER";
			}
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjson(resultlist);
	}catch (HibernateException e) {
		e.printStackTrace();
		} finally {
			session.close();
	}
		return array;
	}
}