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
	public  JSONArray getJsonTransacitonRegGridData(String lgaId,String productId,
							String transactionTypeId,
							String fromDate,
							String toDate) {
		System.out.println("-- LgaStoreService.getLgaStoreListPageData() mehtod called: -- ");
		JSONArray array=new JSONArray();
		try {
			SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
			Session session = sf.openSession();
			transactionTypeId=transactionTypeId.length()==0?null:transactionTypeId;
			productId=productId.length()==0?null:productId;
			fromDate=fromDate.length()==0?null:fromDate;
			toDate=toDate.length()==0?null:toDate;
			String x_query=" SELECT TRANSACTION_ID,  ITEM_ID,  ITEM_NUMBER,  TRANSACTION_QUANTITY,  "
					+ " TRANSACTION_UOM,  DATE_FORMAT(TRANSACTION_DATE,'%d %b %Y %h:%i %p') TRANSACTION_DATE, "
					+ " REASON,  TRANSACTION_TYPE_ID,  TRANSACTION_TYPE,  FROM_NAME,  TO_NAME,  TO_SOURCE_ID, "
					+ " REASON_TYPE,  REASON_TYPE_ID , VVM_STAGE FROM TRANSACTION_REGISTER_VW  WHERE TRANSACTION_TYPE_ID = "
					+ " IFNULL("+transactionTypeId+", TRANSACTION_TYPE_ID)  AND ITEM_ID=IFNULL("+productId+",ITEM_ID) "
					+ " AND (TO_SOURCE_ID=IFNULL( " +lgaId+ ",TO_SOURCE_ID)"
					+ " OR from_SOURCE_ID=IFNULL( " +lgaId+ ",TO_SOURCE_ID)) "
					+ " AND TRANSACTION_DATE  BETWEEN IFNULL(STR_TO_DATE("+fromDate+", '%m-%d-%Y'), TRANSACTION_DATE)    "
					+ " AND IFNULL(STR_TO_DATE("+toDate+", '%m-%d-%Y'), TRANSACTION_DATE)";
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
//		System.out.println("result list size"+resultlist.size());
			 array=GetJsonResultSet.getjson(resultlist);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
		}
}
