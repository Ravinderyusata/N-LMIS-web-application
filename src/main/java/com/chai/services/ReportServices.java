package com.chai.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;

import com.chai.hibernartesessionfactory.HibernateSessionFactoryClass;
import com.chai.util.GetJsonResultSet;

public class ReportServices {
	SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
	public JSONArray getJsonLgaBincardGridData(String lgaId, String year,String month, 
			String dateType, String transactionType,
			String productType,String date) {
		System.out.println("-- Reportservice.getJsonLgaBincardGridData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		Session session = sf.openSession();

		try {
			String x_query = "";
			String tableCondition="";
			if (dateType.equals("MONTH/YEAR")) {
				x_query="SELECT ITEM_ID, ITEM_NUMBER, "
						+" TRANSACTION_TYPE_ID, TYPE_CODE,LGA_ID, REASON_TYPE,"
						+" TRANSACTION_MONTH, TRANSACTION_YEAR, TOTAL_TRANSACTION_QUANTITY, "
						+" REASON FROM LGA_BIN_CARD_MONTH_V  where TRANSACTION_MONTH='"+month+"'"
						+" AND TRANSACTION_YEAR='"+year+"' AND LGA_ID="+lgaId+" "
						+" AND ITEM_ID IN (IFNULL("+productType+",ITEM_ID),F_GET_DILUENT(IFNULL("+productType+",0)))"
						+" AND TRANSACTION_TYPE_ID=IFNULL("+transactionType+",TRANSACTION_TYPE_ID)";

			}else{
				x_query="SELECT ITEM_ID,ITEM_NUMBER, LGA_ID ,"
						+"LGA_NAME,FROM_SOURCE,TRANSACTION_TYPE_ID,TYPE_CODE,"
						+" TRANSACTION_QUANTITY,date_format(TRANSACTION_DATE,'%d-%m-%Y') as TRANSACTION_DATE,"
						+" REASON,REASON_TYPE,ONHAND_QUANTITY_BEFOR_TRX,ONHAND_QUANTITY_AFTER_TRX"
						+ "   FROM LGA_BIN_CARD_DAY_V  where date_format(TRANSACTION_DATE,'%d-%m-%Y')='" + date + "'"
						+" AND TRANSACTION_TYPE_ID=IFNULL("+transactionType+",TRANSACTION_TYPE_ID) "
						+" AND ITEM_ID IN (IFNULL("+productType+",ITEM_ID),F_GET_DILUENT(IFNULL("+productType+",0))) "
						+ " AND LGA_ID="+lgaId;
			}
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			// System.out.println("result list size"+resultlist.size());
			array = GetJsonResultSet.getjson(resultlist);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public JSONArray getJsonLgaMinMaxData(String stateId, String lgaId,
			String minMax,String perioadType,String year,String weekOrMonth) {
		System.out.println("-- LgaStoreService.getLgaStoreListPageData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		try {
			String x_query="";
			String fromTable="";
			String month="";
			
			if(perioadType.equals("MONTHLY")){
				month=weekOrMonth;
				if(Integer.parseInt(weekOrMonth)<10){
					month="0"+weekOrMonth;
				}
				
			}
			if(minMax.equals("Min")){
				if(perioadType.equals("MONTHLY")){
					fromTable=" FROM SCCO_LGA_STOCK_PERFORMANCE_MONTHLY_MIN_V WHERE "
							+" YEAR=IFNULL('"+year+"',YEAR)"
							+" AND MONTH=IFNULL('"+month+"',MONTH)";
				}else if(perioadType.equals("WEEKLY")){
					fromTable=" FROM SCCO_LGA_STOCK_PERFORMANCE_WEEKLY_MIN_V WHERE "
							+" YEAR=IFNULL('"+year+"',YEAR)"
							+" AND WEEK=IFNULL('"+weekOrMonth+"',WEEK)";
				}
				
			}else {
				if(perioadType.equals("MONTHLY")){
					fromTable=" FROM SCCO_LGA_STOCK_PERFORMANCE_MONTHLY_MAX_V WHERE"
							+" YEAR=IFNULL('"+year+"',YEAR)"
							+" AND MONTH=IFNULL('"+month+"',MONTH)";
				}else if(perioadType.equals("WEEKLY")){
					fromTable=" FROM SCCO_LGA_STOCK_PERFORMANCE_WEEKLY_MAX_V WHERE"
							+" YEAR=IFNULL('"+year+"',YEAR)"
							+" AND WEEK=IFNULL('"+weekOrMonth+"',WEEK)";
				}
			}
			if(lgaId!=null && lgaId.equals("null")){
				x_query="select DISTINCT LGA_ID	,LGA_NAME ";
			}else{
				if(minMax.equals("Min")){
					x_query="select LGA_ID	,LGA_NAME,	ITEM_ID	,ITEM_NUMBER,"
							+"ONHAND_QUANTITY,	MIN_STOCK_BALANCE,	DIFFERENCE";
				}else if(minMax.equals("Max")){
					x_query="select LGA_ID	,LGA_NAME,	ITEM_ID	,ITEM_NUMBER,"
							+"ONHAND_QUANTITY,	MAX_STOCK_BALANCE,	DIFFERENCE";
				}
				}
			x_query+=fromTable+" AND LGA_ID=IFNULL("+lgaId+",LGA_ID) AND STATE_ID=IFNULL('"+stateId+"',STATE_ID)";
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			// System.out.println("result list size"+resultlist.size());
			array = GetJsonResultSet.getjson(resultlist);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}
	
	public JSONArray lgaWastageReportGridData(String stateId,String... params) {
		System.out.println("-- LgaStoreService.getLgaStoreListPageData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		String x_WHERE_DAY = "";
		String x_WHERE_WEEK = "";
		String x_WHERE_MONTH = "";
		String x_WHERE_YEAR = "";
		Session session = sf.openSession();
		try {
			String x_QUERY = " SELECT TRANSACTION_ID, " + "      STATE_ID, " + "      STATE_NAME, " + "      ITEM_ID, "
					+ "      ITEM_NUMBER, " + "      LGA_ID, " + "      LGA_NAME, " + "      FROM_SOURCE, "
					+ "      FROM_SOURCE_ID, " + "      TO_SOURCE, " + "      TO_SOURCE_ID, "
					+ "      TRANSACTION_TYPE_ID, " + "      TYPE_CODE, " + "      TRANSACTION_QUANTITY, "
					+ "      DATE_FORMAT(TRANSACTION_DATE,'%d-%b-%Y') TRANSACTION_DATE, " + "      REASON, "
					+ "      REASON_TYPE, " + "      ONHAND_QUANTITY_BEFOR_TRX, " + "      ONHAND_QUANTITY_AFTER_TRX "
					+ "		 FROM LGA_WASTAGE_REPORT_V";

			if (params != null) {
				if((((String)params[0]).equals("null")?null:params[0]) == null){
					x_QUERY = "select DISTINCT LGA_ID ,LGA_NAME FROM  LGA_WASTAGE_REPORT_V ";
					x_WHERE_DAY = "Where DATE_FORMAT(TRANSACTION_DATE,'%d-%m-%Y') = '" + params[5] + "' "
							+ "  AND STATE_ID=IFNULL(" + stateId + ",STATE_ID)";
					x_WHERE_WEEK = "WHERE DATE_FORMAT(TRANSACTION_DATE,'%Y-%v') = '" + params[2] + "-" + params[4]
							+ "' " + "  AND STATE_ID=IFNULL(" + stateId + ",STATE_ID)";
					x_WHERE_MONTH = "WHERE DATE_FORMAT(TRANSACTION_DATE,'%Y-%c') = '" + params[2] + "-" + params[3]
							+ "' " + "  AND STATE_ID=IFNULL(" + stateId + ",STATE_ID)";
					x_WHERE_YEAR = "WHERE DATE_FORMAT(TRANSACTION_DATE,'%Y') = '" + params[2] + "' "
							+ "  AND STATE_ID=IFNULL(" + stateId + ",STATE_ID)";
				} else {
					x_WHERE_DAY = " WHERE LGA_ID = IFNULL(" + params[0]
							+ ",LGA_ID) AND DATE_FORMAT(TRANSACTION_DATE,'%d-%m-%Y') = '" + params[5] + "' ";
					x_WHERE_WEEK = " WHERE LGA_ID = IFNULL(" + params[0]
							+ ",LGA_ID) AND DATE_FORMAT(TRANSACTION_DATE,'%Y-%v') = '" + params[2] + "-" + params[4]
							+ "' ";
					x_WHERE_MONTH = " WHERE LGA_ID = IFNULL(" + params[0]
							+ ",LGA_ID) AND DATE_FORMAT(TRANSACTION_DATE,'%Y-%c') = '" + params[2] + "-" + params[3]
							+ "' ";
					x_WHERE_YEAR = " WHERE LGA_ID = IFNULL(" + params[0]
							+ ",LGA_ID) AND DATE_FORMAT(TRANSACTION_DATE,'%Y') = '" + params[2] + "' ";
				}
				switch (params[1]) {
				case "DAY":
					x_QUERY += x_WHERE_DAY;
					break;
				case "WEEK":
					x_QUERY += x_WHERE_WEEK;
					break;
				case "MONTH":
					x_QUERY += x_WHERE_MONTH;
					break;
				case "YEAR":
					x_QUERY += x_WHERE_YEAR;
					break;
				default:
					x_QUERY += " WHERE 1=0 ";
				}
			} else {
				x_QUERY += " WHERE 1=0 ";
			}
			SQLQuery query = session.createSQLQuery(x_QUERY);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			// System.out.println("result list size"+resultlist.size());
			array = GetJsonResultSet.getjson(resultlist);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public JSONArray lgaInconsistencyReportGridData(int stateId,String... params) {
		System.out.println("-- LgaStoreService.lgaInconsistencyReportGridData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		String x_WHERE_DAY = "";
		String x_WHERE_WEEK = "";
		String x_WHERE_MONTH = "";
		String x_WHERE_YEAR = "";
		Session session = sf.openSession();
		try {
			SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
			String x_QUERY = "select LGA_ID,WAREHOUSE_CODE,ITEM_ID,ITEM_NUMBER,"
					+ " date_format(PHYSICAL_COUNT_DATE,'%d-%m-%Y') AS PHYSICAL_COUNT_DATE,REASON,"
					+ " STOCK_BALANCE,PHYSICAL_STOCK_COUNT,DIFFERENCE " + " FROM LGA_STOCK_DISPCREPENCIES ";
			if (params != null) {
				if((((String)params[0]).equals("null")?null:params[0]) == null){
					x_QUERY = "select DISTINCT LGA_ID ,WAREHOUSE_CODE FROM  LGA_STOCK_DISPCREPENCIES ";
					x_WHERE_DAY = "Where DATE_FORMAT(PHYSICAL_COUNT_DATE,'%d-%m-%Y') = '" + params[5] + "' "
							+ "  AND STATE_ID=IFNULL(" + stateId + ",STATE_ID)";
					x_WHERE_WEEK = "WHERE DATE_FORMAT(PHYSICAL_COUNT_DATE,'%Y-%v') = '" + params[2] + "-" + params[4]
							+ "' " + "  AND STATE_ID=IFNULL(" +stateId + ",STATE_ID)";
					x_WHERE_MONTH = "WHERE DATE_FORMAT(PHYSICAL_COUNT_DATE,'%Y-%c') = '" + params[2] + "-" + params[3]
							+ "' " + "  AND STATE_ID=IFNULL(" + stateId + ",STATE_ID)";
					x_WHERE_YEAR = "WHERE DATE_FORMAT(PHYSICAL_COUNT_DATE,'%Y') = '" + params[2] + "' "
							+ "  AND STATE_ID=IFNULL(" + stateId + ",STATE_ID)";
				} else {
					x_WHERE_DAY = " WHERE LGA_ID = IFNULL(" + params[0]
							+ ",LGA_ID) AND DATE_FORMAT(PHYSICAL_COUNT_DATE,'%d-%m-%Y') = '" + params[5] + "' ";
					x_WHERE_WEEK = " WHERE LGA_ID = IFNULL(" + params[0]
							+ ",LGA_ID) AND DATE_FORMAT(PHYSICAL_COUNT_DATE,'%Y-%v') = '" + params[2] + "-" + params[4]
							+ "' ";
					x_WHERE_MONTH = " WHERE LGA_ID = IFNULL(" + params[0]
							+ ",LGA_ID) AND DATE_FORMAT(PHYSICAL_COUNT_DATE,'%Y-%c') = '" + params[2] + "-" + params[3]
							+ "' ";
					x_WHERE_YEAR = " WHERE LGA_ID = IFNULL(" + params[0]
							+ ",LGA_ID) AND DATE_FORMAT(PHYSICAL_COUNT_DATE,'%Y') = '" + params[2] + "' ";
				}
				switch (params[1]) {
				case "DAY":
					x_QUERY += x_WHERE_DAY;
					break;
				case "WEEK":
					x_QUERY += x_WHERE_WEEK;
					break;
				case "MONTH":
					x_QUERY += x_WHERE_MONTH;
					break;
				case "YEAR":
					x_QUERY += x_WHERE_YEAR;
					break;
				default:
					x_QUERY += " WHERE 1=0 ";
				}
			} else {
				x_QUERY += " WHERE 1=0 ";
			}
			SQLQuery query = session.createSQLQuery(x_QUERY);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			// System.out.println("result list size"+resultlist.size());
			array = GetJsonResultSet.getjson(resultlist);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public JSONArray getJsonLgaStockAdjustmentReportGridData(String lgaId, String year,String month, 
			String dateType, String reasonType,
			String productType,String date) {
		System.out.println("-- reportService.getJsonLgaStockAdjustmentReportGridData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		try {
			String x_query = "";
			String tableCondition="";
			if (dateType.equals("MONTH/YEAR")) {
				x_query="SELECT ITEM_ID,ITEM_NUMBER,"
						+"LGA_ID ,LGA_NAME,REASON_TYPE_ID,REASON_TYPE,"
						+"TRANSACTION_MONTH,TRANSACTION_YEAR,"
						+ " CAST(TOTAL_TRANSACTION_QUANTITY as CHAR(50)) as TOTAL_TRANSACTION_QUANTITY "
						+"FROM lga_stock_adjustments_mon_report_v  where TRANSACTION_MONTH='"+month+"' "
						+"AND TRANSACTION_YEAR='"+year+"' AND REASON_TYPE_ID=IFNULL("+reasonType+",REASON_TYPE_ID)"
						+"AND ITEM_ID IN (IFNULL("+productType+",ITEM_ID),F_GET_DILUENT(IFNULL("+productType+",0))) "
						+"AND LGA_ID=IFNULL('"+lgaId+"',LGA_ID)";

			}else{
				x_query="SELECT ITEM_ID,ITEM_NUMBER, LGA_ID , LGA_NAME,REASON_TYPE,REASON_TYPE_ID,"
							+"TRANSACTION_QUANTITY,date_format(TRANSACTION_DATE,'%d-%m-%Y') as TRANSACTION_DATE "
							+"FROM lga_stock_adjustments_day_report_v  "
						+ "where date_format(TRANSACTION_DATE,'%d-%m-%Y')='" + date + "'"
							+" AND REASON_TYPE_ID=IFNULL("+reasonType+",REASON_TYPE_ID)"
							+" AND ITEM_ID IN (IFNULL("+productType+",ITEM_ID),F_GET_DILUENT(IFNULL("+productType+",0)))"
							+" AND LGA_ID=IFNULL("+lgaId+",LGA_ID)";
			}
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			// System.out.println("result list size"+resultlist.size());
			array = GetJsonResultSet.getjson(resultlist);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public JSONArray getJsonlgaEmergencyStockIssueReportGridData(String stateID, String... params) {
		System.out.println("-- LgaStoreService.getLgaStoreListPageData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		String x_QUERY = "";
		Session session = sf.openSession();
		try {
			if (params != null) {
				String lgaIdCondition = "";
				String itemIdCondition = "";
				String andCondition = "";

				// param[1]=filterbyfiltervalue
				switch (params[1]) {
					case "DAY":
					andCondition = " date_format(ALLOCATION_DATE,'%d-%m-%Y')='" + params[5] + "'";
						break;
					case "WEEK":
						andCondition = " date_format(ALLOCATION_DATE,'%Y-%v')='" + params[2] + "-" + params[4] + "'";
						break;
					case "MONTH":
						andCondition = " date_format(ALLOCATION_DATE,'%Y-%c')='" + params[2] + "-" + params[3] + "'";
						break;
					case "YEAR":
						andCondition = " date_format(ALLOCATION_DATE,'%Y')='" + params[2] + "'";
						break;
					default:
						andCondition = " 1=0 ";
				}
				
				if((((String)params[0]).equals("null")?null:params[0]) == null){
					stateID = stateID.equals("") ? null : stateID;
					lgaIdCondition = " AND STATE_ID=IFNULL(" + stateID + ",STATE_ID)";
					x_QUERY = "select DISTINCT LGA_ID, LGA_NAME" + " from lga_emergency_stock_alloc_report_v where "
							+ andCondition + lgaIdCondition;
				}else {					
					lgaIdCondition = " AND LGA_ID=" + (((String)params[0]).length()==0?"IFNULL(null,LGA_ID)":params[0]);
					x_QUERY = "select LGA_ID, LGA_NAME,ITEM_ID,ITEM_NUMBER,CUSTOMER_NAME,"
							+ " SUM(EMERGENCY_ISSUED_QUANTITY) AS EMERGENCY_ISSUED_QUANTITY,MONTH,YEAR,"
							+ " WEEK,date_format(ALLOCATION_DATE,'%m/%d/%Y') AS ALLOCATION_DATE "
							+ " from lga_emergency_stock_alloc_report_v where " + andCondition + lgaIdCondition
							+ " GROUP BY ITEM_ID";
				}
			}
			SQLQuery query = session.createSQLQuery(x_QUERY);
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

}
