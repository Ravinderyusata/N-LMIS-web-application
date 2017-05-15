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

public class TransactionServices {
	SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();

	public JSONArray getJsonTransacitonRegGridData(String lgaId, String productId, String transactionTypeId,
			String fromDate, String toDate) {
		System.out.println("-- LgaStoreService.getLgaStoreListPageData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		Session session = sf.openSession();

		try {
			transactionTypeId = transactionTypeId.length() == 0 ? null : transactionTypeId;
			productId = productId.length() == 0 ? null : productId;
			fromDate = fromDate.length() == 0 ? null : fromDate;
			toDate = toDate.length() == 0 ? null : toDate;
			String x_query = " SELECT TRANSACTION_ID,  ITEM_ID,  ITEM_NUMBER,  TRANSACTION_QUANTITY,  "
					+ " TRANSACTION_UOM,  DATE_FORMAT(TRANSACTION_DATE,'%d %b %Y %h:%i %p') TRANSACTION_DATE, "
					+ " REASON,  TRANSACTION_TYPE_ID,  TRANSACTION_TYPE,  FROM_NAME,  TO_NAME,  TO_SOURCE_ID, "
					+ " REASON_TYPE,  REASON_TYPE_ID , VVM_STAGE FROM TRANSACTION_REGISTER_VW  WHERE TRANSACTION_TYPE_ID = "
					+ " IFNULL(" + transactionTypeId + ", TRANSACTION_TYPE_ID)  AND ITEM_ID=IFNULL(" + productId
					+ ",ITEM_ID) " + "  AND WAREHOUSE_ID= " + lgaId
					+ " AND TRANSACTION_DATE  BETWEEN IFNULL(STR_TO_DATE('" + fromDate
					+ "', '%d-%m-%Y'), TRANSACTION_DATE)    " + " AND IFNULL(STR_TO_DATE('" + toDate
					+ "', '%d-%m-%Y'), TRANSACTION_DATE)";
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			 System.out.println("result list: "+resultlist.size());
			 System.out.println("result list, query: "+x_query);
			array = GetJsonResultSet.getjson(resultlist);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}
}
