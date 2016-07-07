package com.chai.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.chai.hibernartesessionfactory.HibernateSessionFactoryClass;
import com.chai.model.views.AdmUserV;
import com.chai.util.GetJsonResultSet;

public class DashboardServices {
	public JSONArray getLgaStockSummaryGridData(AdmUserV userBean, String year, String week, String lgaID) {
		System.out.println("-- DashboardServices.getLgaStockSummaryGridData() mehtod called: -- ");
		JSONArray array=new JSONArray();
		SQLQuery query=null;
		try {
			SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
			Session session = sf.openSession();
			String x_query="";
			if(userBean.getX_ROLE_NAME().equals("NTO")){
			 x_query="select STATE_ID,STATE_NAME,LGA_ID, LGA_NAME,ITEM_ID , "
						+" ITEM_NUMBER, YEAR ,WEEK,ONHAND_QUANTITY,LEGEND_FLAG ,LEGEND_COLOR"
						+" from STATE_LCCO_stock_performance_dashbord_v "
						+" where year="+year+" and week="+week+" AND STATE_ID="+lgaID
						+" ORDER BY LGA_NAME, ITEM_NUMBER";
			}else{
				 x_query="select STATE_ID,STATE_NAME,LGA_ID, LGA_NAME,ITEM_ID , "
						+" ITEM_NUMBER, YEAR ,WEEK,ONHAND_QUANTITY,LEGEND_FLAG ,LEGEND_COLOR"
						+" from STATE_LCCO_stock_performance_dashbord_v "
						+" where year="+year+" and week="+week+" AND STATE_ID="+userBean.getX_WAREHOUSE_ID()
						+" ORDER BY LGA_NAME, ITEM_NUMBER";
			}
			
			query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			 array=GetJsonResultSet.getjson(resultlist);
	}catch (HibernateException e) {
		e.printStackTrace();
	}
		return array;
	}
	
	public JSONArray getHFStockSummaryGridData(String year, String week, String lgaID) {
		System.out.println("-- DashboardServices.getHFStockSummaryGridData() mehtod called: lgaID=-- "+lgaID);
		JSONArray array = new JSONArray();
		SQLQuery query = null;
		try {
			SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
			String x_query = "";
			x_query = "SELECT  LGA_ID,	LGA_NAME, CUSTOMER_ID, CUSTOMER_NAME, ITEM_ID, "
					+ " ITEM_NUMBER, STOCK_RECEIVED_DATE, STOCK_BALANCE, MIN_STOCK, MAX_STOCK, "
					+ " LEGEND_FLAG, LEGEND_COLOR FROM hf_stock_performance_dashbord_v "
					+ " WHERE DATE_FORMAT(STOCK_RECEIVED_DATE,'%Y-%v') = '"+year+"-"+week+"' "+" AND LGA_ID="+lgaID
					+" union select default_store_id, '' LGA_NAME, "
					+ " CUSTOMER_ID,  CUSTOMER_NAME, '' ITEM_ID,'' ITEM_NUMBER,'' STOCK_RECEIVED_DATE, "
					+ " 0 STOCK_BALANCE, 0 MIN_STOCK, 0 MAX_STOCK, 'R' LEGEND_FLAG, 'red' LEGEND_COLOR"
					+ " from customers where customer_id not in( SELECT CUSTOMER_ID "
					+ " FROM hf_stock_performance_dashbord_v WHERE DATE_FORMAT(STOCK_RECEIVED_DATE,'%Y-%v')='"+year+"-"+week+"' "
					+ " AND LGA_ID="+lgaID+") "
					+ " AND default_store_id="+lgaID
					+ " AND STATUS='A'";

			query = sf.openSession().createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjson(resultlist);
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return array;
	}
	public JSONArray getStateStockPerfDashboard(AdmUserV userBean,String year,String week,String lgaid){
		System.out.println("-- DashboardServices.getStateStockPerfDashboard() mehtod called: -- ");
		JSONArray array=new JSONArray();
		SQLQuery query=null;
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
			SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
			Session session = sf.openSession();
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
			query = session.createSQLQuery(x_query);
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
			query = session.createSQLQuery(x_query2);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist2 = query.list();
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
			    	   System.out.println("green same");
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
			SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
			Session session = sf.openSession();
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
	}
		return array;
	}
	
	public JSONArray getLgaAggStockDashboardData(String year,String week){
		System.out.println("-- DashboardServices.getLgaAggStockDashboardData() mehtod called: -- ");
		JSONArray array=new JSONArray();
		SQLQuery query=null;
		String previousWeek="";
		String previousyear="";
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
			SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
			Session session = sf.openSession();
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
			    	   System.out.println("green same");
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
	}
		return array;
	}	
}