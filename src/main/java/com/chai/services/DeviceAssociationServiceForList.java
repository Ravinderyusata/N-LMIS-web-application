package com.chai.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;

import com.chai.hibernartesessionfactory.HibernateSessionFactoryClass;
import com.chai.util.GetJsonResultSet;

public class DeviceAssociationServiceForList {
	SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
	public JSONArray getDropdownList(String... action) {
		System.out.println("in itemServices.getDropdownList()");
		String x_QUERY = "";
		SQLQuery query = null;
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		if (action[0].equals("device_asso_product")) {
			x_QUERY = " SELECT ITEM_ID, " + "	ITEM_NUMBER " + " FROM VIEW_ITEM_MASTERS " + " WHERE STATUS = 'A' "
					+ " AND ITEM_TYPE_ID = F_GET_TYPE('PRODUCT','VACCINE') "
					+ " AND ITEM_NUMBER NOT IN ('ROTA VACCINE','OPV') " + " AND WAREHOUSE_ID = " + action[1]
					+ " ORDER BY ITEM_NUMBER ";
		}

		if (action[0].equals("ad_syringe")) {
			x_QUERY = " SELECT ITEM_ID, " + "	UCASE(ITEM_NUMBER) AS ITEM_NUMBER "
					+ " FROM VIEW_ITEM_MASTERS "
					+ " WHERE ITEM_TYPE_ID = F_GET_TYPE('PRODUCT','DEVICE')"
					+ " AND UPPER(ITEM_NUMBER) NOT IN ('2ML SYRINGE','5ML SYRINGE','MUAC STRIPS','SAFETY BOXES')"
					+ " AND WAREHOUSE_ID = " + action[1];
		}

		if (action[0].equals("reconstitute_syrng")) {
			x_QUERY = " SELECT		ITEM_ID ," // 0
					+ "	UCASE(ITEM_NUMBER) AS ITEM_NUMBER " + " FROM VIEW_ITEM_MASTERS "
					+ " WHERE ITEM_TYPE_ID = F_GET_TYPE('PRODUCT','DEVICE') "
					+ " AND UPPER(ITEM_NUMBER) NOT IN ('0.05ML AD SYRINGE (BCG)','0.5ML AD SYRINGE','MUAC STRIPS','SAFETY BOXES') "
					+ "AND WAREHOUSE_ID = " + action[1];
		}
		try {
			query = session.createSQLQuery(x_QUERY);
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
}
