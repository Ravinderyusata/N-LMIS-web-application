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

public class HFReportService {
	SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
	public JSONArray getJsonHFBincardGridData(String hfId, String transactionType, Integer x_WAREHOUSE_ID,
			String filterBy, String year, String month, String week, String day) {
		System.out.println("-- Reportservice.getJsonLgaBincardGridData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		Session session=sf.openSession();
		try {
			String x_query = "";
			String dateCondition = "";
			if (hfId.equals("null")) {
				if(transactionType.equals("STOCK BALANCE")){
					x_query = " SELECT DISTINCT CUSTOMER_ID,CUSTOMER_NAME "
							+ " FROM hf_bin_card_stock_balance_v " + " where CUSTOMER_ID=IFNULL(" + hfId
							+ ",CUSTOMER_ID)  AND WAREHOUSE_ID="
							+ x_WAREHOUSE_ID;
				}else if(transactionType.equals("STOCK ISSUE")){
					if (!filterBy.equals("")) {
						if (filterBy.equals("YEAR")) {
							dateCondition = " date_format(STOCK_ISSUED_DATE,'%Y')='" + year + "'";
						} else if (filterBy.equals("MONTH")) {
							dateCondition = " date_format(STOCK_ISSUED_DATE,'%Y-%c')='" + year + "-" + month + "'";
						} else if (filterBy.equals("WEEK")) {
							dateCondition = " date_format(STOCK_ISSUED_DATE,'%Y-%v')='" + year + "-" + week + "'";
						} else if (filterBy.equals("DAY")) {
							dateCondition = " date_format(STOCK_ISSUED_DATE,'%m/%d/%Y')='" + day + "'";
						}
					}
					x_query = " SELECT DISTINCT CUSTOMER_ID,CUSTOMER_NAME "
							+ " FROM hf_stock_issued_new_v where  " + dateCondition + " AND CUSTOMER_ID=IFNULL(" + hfId
							+ ",CUSTOMER_ID) AND ORDER_FROM_ID=" + x_WAREHOUSE_ID;
				}

			} else {
				if(transactionType.equals("STOCK BALANCE")){
					x_query = " SELECT CONSUMPTION_ID,CUSTOMER_ID, CUSTOMER_NAME,ITEM_ID,"
							+ " ITEM_NUMBER,DATE_FORMAT(STOCK_RECEIVED_DATE,'%d-%m-%Y') AS STOCK_RECEIVED_DATE ,"
							+ " STOCK_BALANCE,WAREHOUSE_ID, " + " SHIP_FROM_SOURCE "
							+ " FROM hf_bin_card_stock_balance_v " + " where CUSTOMER_ID=IFNULL(" + hfId
							+ ",CUSTOMER_ID)  AND WAREHOUSE_ID="
							+ x_WAREHOUSE_ID;
				}else if(transactionType.equals("STOCK ISSUE")){
					if (!filterBy.equals("")) {
						if (filterBy.equals("YEAR")) {
							dateCondition = " date_format(STOCK_ISSUED_DATE,'%Y')='" + year + "'";
						} else if (filterBy.equals("MONTH")) {
							dateCondition = " date_format(STOCK_ISSUED_DATE,'%Y-%c')='" + year + "-" + month + "'";
						} else if (filterBy.equals("WEEK")) {
							dateCondition = " date_format(STOCK_ISSUED_DATE,'%Y-%v')='" + year + "-" + week + "'";
						} else if (filterBy.equals("DAY")) {
							dateCondition = " date_format(STOCK_ISSUED_DATE,'%m/%d/%Y')='" + day + "'";
						}
					}
					x_query = " SELECT STOCK_ISSUED, CUSTOMER_ID,CUSTOMER_NAME,"
							+ "ITEM_NUMBER,DATE_FORMAT(STOCK_ISSUED_DATE,'%d-%m-%Y') AS STOCK_ISSUED_DATE ,"
							+ "ITEM_ID  FROM hf_stock_issued_new_v " //
							+ " where  " + dateCondition + " AND CUSTOMER_ID=IFNULL(" + hfId
							+ ",CUSTOMER_ID) AND ORDER_FROM_ID=" + x_WAREHOUSE_ID;
				}
			}
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			// System.out.println("result list size"+resultlist.size());
			array = GetJsonResultSet.getjson(resultlist);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.close();
		}
		return array;
	}

	public JSONArray getJsonHFWastageGridData(String hfId, Integer x_WAREHOUSE_ID, String filterBy, String year,
			String month, String week, String day) {
			System.out.println("-- Reportservice.getJsonLgaBincardGridData() mehtod called: -- ");
			JSONArray array = new JSONArray();
		Session session = sf.openSession();
			try {
				String x_query = "";
				String dateCondition = "";
				if (!filterBy.equals("")) {
					if (filterBy.equals("YEAR")) {
						dateCondition = " date_format(WASTAGE_RECEIVED_DATE,'%Y')='" + year + "'";
					} else if (filterBy.equals("MONTH")) {
						dateCondition = " date_format(WASTAGE_RECEIVED_DATE,'%Y-%c')='" + year + "-" + month + "'";
					} else if (filterBy.equals("WEEK")) {
						dateCondition = " date_format(WASTAGE_RECEIVED_DATE,'%Y-%v')='" + year + "-" + week + "'";
					} else if (filterBy.equals("DAY")) {
						dateCondition = " date_format(WASTAGE_RECEIVED_DATE,'%m/%d/%Y')='" + day + "'";
					}
				}
				if (hfId.equals("null")) {
					x_query="select DISTINCT CUSTOMER_ID	,CUSTOMER_NAME FROM "
							+" DHIS2_STOCK_WASTAGES_PROCESSED_V WHERE "+dateCondition
							+" AND LGA_ID=IFNULL("+x_WAREHOUSE_ID+",LGA_ID)";
				} else {
					x_query="SELECT STATE_ID,STATE_NAME,	LGA_ID,LGA_NAME,CUSTOMER_ID,CUSTOMER_NAME,ITEM_ID,ITEM_NAME,"
				+"WASTAGE_TYPE_ID,WASTAGE_TYPE,REASON_TYPE_ID,"
				+ "REASON_TYPE,WASTAGE_QUANTITY, "
				+"DATE_FORMAT(WASTAGE_RECEIVED_DATE,'%Y-%m-%d') AS WASTAGE_RECEIVED_DATE"
						+ " FROM DHIS2_STOCK_WASTAGES_PROCESSED_V "
				+ " WHERE LGA_ID = IFNULL("+x_WAREHOUSE_ID+",LGA_ID) "
				+" AND "+dateCondition
				+" AND CUSTOMER_ID="+hfId;
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

	public JSONArray getJsonHFEmergencyAllocationData(String hfId, Integer x_WAREHOUSE_ID, String filterBy, String year,
			String month, String quarter, String day) {
		System.out.println("-- Reportservice.getJsonHFEmergencyAllocationData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		try {
			String x_query = "";
			String dateCondition = "";
			if (!filterBy.equals("")) {
				if (filterBy.equals("YEAR")) {
					dateCondition = " date_format(ALLOCATION_DATE,'%Y')='" + year + "'";
				} else if (filterBy.equals("MONTH")) {
					dateCondition = " date_format(ALLOCATION_DATE,'%Y-%c')='" + year + "-" + month + "'";
				} else if (filterBy.equals("QUARTER")) {
					if (quarter.equals("1")) {
						dateCondition = " date_format(ALLOCATION_DATE,'%Y-%m') BETWEEN '" + year + "-01' AND '" + year
								+ "-03'";
					} else if (quarter.equals("2")) {
						dateCondition = " date_format(ALLOCATION_DATE,'%Y-%m') BETWEEN '" + year + "-04' AND '" + year
								+ "-06'";
					} else if (quarter.equals("3")) {
						dateCondition = " date_format(ALLOCATION_DATE,'%Y-%m') BETWEEN '" + year + "-07' AND '" + year
								+ "-09'";
					} else if (quarter.equals("4")) {
						dateCondition = " date_format(ALLOCATION_DATE,'%Y-%m') BETWEEN '" + year + "-10' AND '" + year
								+ "-12'";
					}
				} else if (filterBy.equals("DAY")) {
					dateCondition = " date_format(ALLOCATION_DATE,'%m/%d/%Y')='" + day + "'";
				}
			}
			if (hfId.equals("null")) {
				x_query = "   select DISTINCT CUSTOMER_ID,CUSTOMER_NAME" + " from HF_EMERGENCY_STOCK_ALLOC_REPORT_V "
						+ " where " + dateCondition + " AND LGA_ID=" + x_WAREHOUSE_ID;
			} else {
				x_query = " select CUSTOMER_ID,CUSTOMER_NAME,ITEM_ID,ITEM_NUMBER,ALLOCATION,"
						+ " date_format(ALLOCATION_DATE,'%d-%m-%Y') AS ALLOCATION_DATE from HF_EMERGENCY_STOCK_ALLOC_REPORT_V"
						+ " where  " + dateCondition + " AND CUSTOMER_ID=" + hfId + " AND LGA_ID=" + x_WAREHOUSE_ID;
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


	public JSONArray getJsonHFMinMaxData(String hfId, String allocationType, String minMax, Integer x_WAREHOUSE_ID,
			String filterBy, String year, String month, String week, String day) {
		String x_QUERYFORHF = "";
		String x_MIN_STOCK_VIEW = " FROM HF_STOCK_BALANCE_MIN_REPORT_NEW_V ";
		String x_MAX_STOCK_VIEW = " FROM HF_STOCK_BALANCE_Max_REPORT_NEW_V ";
		String dateCondition="";
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		try {
			if (filterBy != null && !filterBy.equals("")) {
				switch (filterBy) {
				case "DAY":
					dateCondition = " DATE_FORMAT(STOCK_RECEIVED_DATE,'%m/%d/%Y') = '" + day + "' ";
					break;
				case "WEEK":
					dateCondition = " DATE_FORMAT(STOCK_RECEIVED_DATE,'%Y-%v') = '" + year + "-" + week + "' ";
					break;
				case "MONTH":
					dateCondition = "  DATE_FORMAT(STOCK_RECEIVED_DATE,'%Y-%c') = '" + year + "-" + month + "' ";
					break;
				case "YEAR":
					dateCondition = "  DATE_FORMAT(STOCK_RECEIVED_DATE,'%Y') = '" + year + "' ";
					break;
				}
			}
			if (minMax.equals("Minimum Stock")) {
				if (hfId.equals("null")) {
					x_QUERYFORHF = "SELECT  DISTINCT CUSTOMER_ID,CUSTOMER_NAME ";
					x_QUERYFORHF += x_MIN_STOCK_VIEW;
				} else {
					x_QUERYFORHF = " SELECT ITEM_ID, " + " ITEM_NUMBER, " + " CUSTOMER_ID, " + " CUSTOMER_NAME, "
							+ " CONSUMPTION_ID, " + " MIN_STOCK, " + " SHIP_FROM_SOURCE, " + " WAREHOUSE_ID, "
							+ " STATE_ID, " + " STATE_NAME, "
							+ " DATE_FORMAT(STOCK_RECEIVED_DATE,'%d-%m-%Y') AS STOCK_RECEIVED_DATE,"
							+ " STOCK_BALANCE, " + " DIFFERENCE ";
					x_QUERYFORHF += x_MIN_STOCK_VIEW;

				}
				} else {
				if (hfId.equals("null")) {
					x_QUERYFORHF = "SELECT  DISTINCT CUSTOMER_ID,CUSTOMER_NAME ";
					x_QUERYFORHF += x_MAX_STOCK_VIEW;
				} else {
					x_QUERYFORHF = " SELECT ITEM_ID, " + " ITEM_NUMBER, " + " CUSTOMER_ID, " + " CUSTOMER_NAME, "
							+ " CONSUMPTION_ID, " + " MAX_STOCK, " + " SHIP_FROM_SOURCE, " + " WAREHOUSE_ID, "
							+ " STATE_ID, " + " STATE_NAME, "
							+ " DATE_FORMAT(STOCK_RECEIVED_DATE,'%d-%m-%Y') AS STOCK_RECEIVED_DATE,"
							+ " STOCK_BALANCE, " + " DIFFERENCE ";
					x_QUERYFORHF += x_MAX_STOCK_VIEW;
				}

				}
			x_QUERYFORHF += " WHERE WAREHOUSE_ID=" + x_WAREHOUSE_ID + " AND CUSTOMER_ID = IFNULL(" + hfId
					+ ",CUSTOMER_ID)" + " AND " + dateCondition;
			x_QUERYFORHF += " AND ALLOCATION_TYPE=IFNULL('" + allocationType + "',ALLOCATION_TYPE)";
			SQLQuery query = session.createSQLQuery(x_QUERYFORHF);
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

}
