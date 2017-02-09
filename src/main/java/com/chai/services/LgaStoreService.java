package com.chai.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;

import com.chai.hibernartesessionfactory.HibernateSessionFactoryClass;
import com.chai.model.LgaStoreBeanForForm;
import com.chai.model.views.AdmUserV;
import com.chai.util.CalendarUtil;
import com.chai.util.GetJsonResultSet;

public class LgaStoreService {
	SQLQuery query = null;
	SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();

	public JSONArray getLgaStoreListPageData(AdmUserV userBean) {
		System.out.println("-- LgaStoreService.getLgaStoreListPageData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		String x_whereCondition = "";
		String x_query = "";
		Session session = sf.openSession();
		String loginRole = userBean.getX_ROLE_NAME();
		try {
			x_query = "SELECT COMPANY_ID,  WAREHOUSE_ID,  WAREHOUSE_CODE, "
					+ "	WAREHOUSE_NAME,  WAREHOUSE_DESCRIPTION,  WAREHOUSE_TYPE_NAME, "
					+ " WAREHOUSE_TYPE_ID,  ADDRESS1,  STATE_NAME,  COUNTRY_NAME, ADDRESS2,"
					+ "  ADDRESS3,  COUNTRY_ID,  STATE_ID,  TELEPHONE_NUMBER,  "
					+ "FAX_NUMBER,	 STATUS,  DATE_FORMAT(START_DATE, '%d-%b-%Y') START_DATE, "
					+ " DATE_FORMAT(END_DATE, '%d-%b-%Y') END_DATE, "
					+ " DEFAULT_ORDERING_WAREHOUSE_ID,  DEFAULT_ORDERING_WAREHOUSE_NAME, "
					+ "TOTAL_POPULATION,YEARLY_PREGNANT_WOMEN_TP,MONTHLY_PREGNANT_WOMEN_TP,"
					+ "YEARLY_TARGET_POPULATION,MONTHLY_TARGET_POPULATION FROM VIEW_INVENTORY_WAREHOUSES";

			if (loginRole.equals("SCCO") || loginRole.equals("SIO") || loginRole.equals("SIFP")) {
				x_whereCondition = "  WHERE WAREHOUSE_ID IN (SELECT viw.WAREHOUSE_ID "
						+ "FROM VIEW_INVENTORY_WAREHOUSES viw" + " WHERE DEFAULT_ORDERING_WAREHOUSE_ID ="
						+ userBean.getX_WAREHOUSE_ID() + " OR WAREHOUSE_ID =" + userBean.getX_WAREHOUSE_ID() + ")";
			} else if (loginRole.equals("LIO") || loginRole.equals("MOH")) {
				x_whereCondition = "  WHERE  WAREHOUSE_ID =" + userBean.getX_WAREHOUSE_ID();
			}
			x_query += x_whereCondition;
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			// System.out.println("result list size"+resultlist.size());
			array = GetJsonResultSet.getjson(resultlist);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public JSONArray getLgaStoreComboboxList(AdmUserV userBean, String... args) {
		System.out.println("-- LGAStoreService.getLgaStoreComboboxList() mehtod called: -- ");
		String x_QUERY = "";
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		if (args[0] != null) {
			if (args[0].equals("LGA_STORE_TYPE")) {
				if (userBean.getX_ROLE_NAME().equals("NTO")) {
					x_QUERY = "SELECT TYPE_ID AS STORE_TYPE_ID, TYPE_NAME AS STORE_TYPE_NAME"
							+ " FROM TYPES WHERE TYPES.SOURCE_TYPE='WAREHOUSE TYPES'";
				} else if (userBean.getX_ROLE_NAME().equals("SIO") || userBean.getX_ROLE_NAME().equals("SIFP")
						|| userBean.getX_ROLE_NAME().equals("SCCO")) {
					x_QUERY = "SELECT TYPE_ID AS STORE_TYPE_ID, TYPE_NAME AS STORE_TYPE_NAME"
							+ " FROM TYPES WHERE TYPES.SOURCE_TYPE='WAREHOUSE TYPES' "
							+ " AND TYPE_NAME <> 'Master Warehouse'";
				} else if (userBean.getX_ROLE_NAME().equals("LIO") || userBean.getX_ROLE_NAME().equals("MOH")) {
					x_QUERY = "SELECT TYPE_ID AS STORE_TYPE_ID, TYPE_NAME AS STORE_TYPE_NAME"
							+ " FROM TYPES WHERE TYPES.SOURCE_TYPE='WAREHOUSE TYPES' " + " AND TYPE_NAME ='LGA STORE'";
				}

			}
		}
		try {
			query = session.createSQLQuery(x_QUERY);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjsonCombolist(resultlist, false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public JSONArray getLgaStoreComboboxListForForm(AdmUserV userBean, String... args) {
		System.out.println("-- LGAStoreService.getLgaStoreComboboxListForForm() mehtod called: -- ");
		String x_QUERY = "";
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		if (args[0] != null) {
			if (args[0].equals("LGA_STORE_TYPE")) {
				if (userBean.getX_ROLE_NAME().equals("NTO")) {
					x_QUERY = "SELECT TYPE_ID AS STORE_TYPE_ID, TYPE_NAME AS STORE_TYPE_NAME"
							+ " FROM TYPES WHERE TYPES.SOURCE_TYPE='WAREHOUSE TYPES' "
							+ " AND TYPE_NAME <> 'Master Warehouse'";
				} else if (userBean.getX_ROLE_NAME().equals("SIO") || userBean.getX_ROLE_NAME().equals("SIFP")
						|| userBean.getX_ROLE_NAME().equals("SCCO")) {
					x_QUERY = "SELECT TYPE_ID AS STORE_TYPE_ID, TYPE_NAME AS STORE_TYPE_NAME"
							+ " FROM TYPES WHERE TYPES.SOURCE_TYPE='WAREHOUSE TYPES' " + " AND TYPE_NAME ='LGA STORE'";
				}

			}
		}
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

	public JSONArray getStoreNameAccoToStore(AdmUserV userBean, String storeTypeId) {
		System.out.println("-- LGAStoreService.getLgaStoreComboboxList() mehtod called: -- ");
		String x_QUERY = "";
		String role = userBean.getX_ROLE_NAME();
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		if (userBean.getX_ROLE_NAME().equals("NTO")) {
			x_QUERY = "SELECT WAREHOUSE_ID,WAREHOUSE_NAME " + " FROM VIEW_INVENTORY_WAREHOUSES WHERE WAREHOUSE_TYPE_ID="
					+ storeTypeId;
		} else if (userBean.getX_ROLE_NAME().equals("SIO") || userBean.getX_ROLE_NAME().equals("SIFP")
				|| userBean.getX_ROLE_NAME().equals("SCCO")) {
			x_QUERY = "SELECT WAREHOUSE_ID,WAREHOUSE_NAME "
					+ " FROM VIEW_INVENTORY_WAREHOUSES WHERE WAREHOUSE_TYPE_ID='" + storeTypeId + "'"
					+ " AND WAREHOUSE_ID IN (SELECT viw.WAREHOUSE_ID"
					+ " FROM VIEW_INVENTORY_WAREHOUSES viw WHERE DEFAULT_ORDERING_WAREHOUSE_ID ="
					+ userBean.getX_WAREHOUSE_ID() + " OR WAREHOUSE_ID =" + userBean.getX_WAREHOUSE_ID() + ")";
		} else if (userBean.getX_ROLE_NAME().equals("LIO") || userBean.getX_ROLE_NAME().equals("MOH")) {
			x_QUERY = "SELECT WAREHOUSE_ID,WAREHOUSE_NAME "
					+ " FROM VIEW_INVENTORY_WAREHOUSES WHERE WAREHOUSE_TYPE_ID='" + storeTypeId + "'"
					+ " AND  WAREHOUSE_ID =" + userBean.getX_WAREHOUSE_ID();
		}

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

	public JSONArray getStoreNameAccoToStoreForForm(AdmUserV userBean, String storeTypeLabel) {
		System.out.println("-- LGAStoreService.getStoreNameAccoToStoreForForm() mehtod called: -- ");
		String x_QUERY = "";
		String role = userBean.getX_ROLE_NAME();
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		if (userBean.getX_ROLE_NAME().equals("NTO")) {
			if (storeTypeLabel.equals("STATE COLD STORE")) {
				x_QUERY = "SELECT WAREHOUSE_ID, " + " WAREHOUSE_NAME   FROM VIEW_INVENTORY_WAREHOUSES "
						+ " WHERE STATUS = 'A' AND WAREHOUSE_TYPE_ID = F_GET_TYPE('WAREHOUSE TYPES','Master Warehouse') "
						+ " ORDER BY WAREHOUSE_NAME ASC";
			} else if (storeTypeLabel.equals("LGA STORE")) {
				x_QUERY = "SELECT WAREHOUSE_ID, " + " WAREHOUSE_NAME   FROM VIEW_INVENTORY_WAREHOUSES "
						+ " WHERE STATUS = 'A' AND WAREHOUSE_TYPE_ID = F_GET_TYPE('WAREHOUSE TYPES','STATE COLD STORE') "
						+ " ORDER BY WAREHOUSE_NAME ASC";
			}
		}

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

	public JSONArray getsearchLgaStoreData(AdmUserV userBean, String storeTypeId, String storeNameId,
			String storeTypeName) {
		System.out.println("-- LgaStoreService.getsearchLgaStoreData() mehtod called: -- ");
		JSONArray array = new JSONArray();
		Session session = sf.openSession();
		String x_QUERY = "SELECT COMPANY_ID,  WAREHOUSE_ID,  WAREHOUSE_CODE, "
				+ "	WAREHOUSE_NAME,  WAREHOUSE_DESCRIPTION,  WAREHOUSE_TYPE_NAME, "
				+ " WAREHOUSE_TYPE_ID,  ADDRESS1,  STATE_NAME,  COUNTRY_NAME, ADDRESS2,"
				+ "  ADDRESS3,  COUNTRY_ID,  STATE_ID,  TELEPHONE_NUMBER,  "
				+ "FAX_NUMBER,	 STATUS,  DATE_FORMAT(START_DATE, '%d-%b-%Y') START_DATE, "
				+ " DATE_FORMAT(END_DATE, '%d-%b-%Y') END_DATE, "
				+ " DEFAULT_ORDERING_WAREHOUSE_ID,  DEFAULT_ORDERING_WAREHOUSE_NAME, "
				+ "TOTAL_POPULATION,YEARLY_PREGNANT_WOMEN_TP,MONTHLY_PREGNANT_WOMEN_TP,"
				+ "YEARLY_TARGET_POPULATION,MONTHLY_TARGET_POPULATION "
				+ "FROM VIEW_INVENTORY_WAREHOUSES WHERE WAREHOUSE_TYPE_ID=" + storeTypeId;
		String x_whereCondition = "";
		try {
			if (userBean.getX_ROLE_NAME().equals("NTO")) {
				if (!storeNameId.equals("")) {
					if (storeTypeName.equals("STATE COLD STORE")) {
						x_whereCondition = " AND WAREHOUSE_ID=IFNULL(" + storeNameId + ",WAREHOUSE_ID)"
								+ " OR DEFAULT_ORDERING_WAREHOUSE_ID=IFNULL(" + storeNameId
								+ ",DEFAULT_ORDERING_WAREHOUSE_ID)";
					} else {
						x_whereCondition = " AND WAREHOUSE_ID=IFNULL(" + storeNameId + ",WAREHOUSE_ID)";
					}
				}
			} else if (userBean.getX_ROLE_NAME().equals("SIO") || userBean.getX_ROLE_NAME().equals("SIFP")
					|| userBean.getX_ROLE_NAME().equals("SCCO")) {
				if (storeNameId.equals("")) {
					x_whereCondition = " AND WAREHOUSE_ID IN (SELECT viw.WAREHOUSE_ID "
							+ "FROM VIEW_INVENTORY_WAREHOUSES viw" + " WHERE DEFAULT_ORDERING_WAREHOUSE_ID ="
							+ userBean.getX_WAREHOUSE_ID() + " OR WAREHOUSE_ID =" + userBean.getX_WAREHOUSE_ID() + ")";
				} else {
					x_whereCondition = " AND WAREHOUSE_ID=" + storeNameId;
				}
			} else if (userBean.getX_ROLE_NAME().equals("LIO") || userBean.getX_ROLE_NAME().equals("MOH")) {
				x_whereCondition = " AND WAREHOUSE_ID=IFNULL(" + storeNameId + ",WAREHOUSE_ID)";
			}
			x_QUERY += x_whereCondition;
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

	public JSONArray getLgaStoreHistory(String WAREHOUSE_ID) {
		System.out.println("-- LgaStoreService.getUserHistory() mehtod called: -- ");
		String x_query = "";
		Session session = sf.openSession();
		x_query = "SELECT (SELECT CONCAT(IFNULL(CUSR.FIRST_NAME,'not available'),' ',IFNULL(CUSR.LAST_NAME,'')) "
				+ " FROM ADM_USERS CUSR WHERE CUSR.USER_ID = (SELECT C.CREATED_BY "
				+ " FROM INVENTORY_WAREHOUSES C WHERE C.WAREHOUSE_ID = " + WAREHOUSE_ID + ")) CREATED_BY, "
				+ " (SELECT CONCAT(IFNULL(UUSR.FIRST_NAME,'not available'),' ', IFNULL(UUSR.LAST_NAME,'')) "
				+ " FROM ADM_USERS UUSR WHERE UUSR.USER_ID = (SELECT U.UPDATED_BY"
				+ " FROM INVENTORY_WAREHOUSES U WHERE U.WAREHOUSE_ID = " + WAREHOUSE_ID + ")) UPDATED_BY, "
				+ " DATE_FORMAT(MNTB.CREATED_ON,'%b %d %Y %h:%i %p') CREATED_ON, "
				+ " DATE_FORMAT(MNTB.LAST_UPDATED_ON,'%b %d %Y %h:%i %p') LAST_UPDATED_ON  "
				+ " FROM INVENTORY_WAREHOUSES MNTB  WHERE MNTB.WAREHOUSE_ID = " + WAREHOUSE_ID;
		// System.out.println("result list size"+resultlist.size());
		JSONArray array = new JSONArray();
		try {
			SQLQuery query = session.createSQLQuery(x_query);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List resultlist = query.list();
			array = GetJsonResultSet.getjson(resultlist);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return array;
	}

	public JSONArray getStateNameListForForm(String countryId) {
		System.out.println("-- LgaStoreService.getStateNameListForForm() mehtod called: -- ");
		String x_query = "";
		Session session = sf.openSession();
		x_query = "SELECT STATE_ID, STATE_NAME   FROM VIEW_STATES  WHERE STATE_NAME IS NOT NULL  "
				+ "AND STATE_NAME <> '' AND STATUS='A' AND COUNTRY_ID = " + countryId + " ORDER BY STATE_NAME";
		// System.out.println("result list size"+resultlist.size());
		JSONArray array = new JSONArray();
		try {
			SQLQuery query = session.createSQLQuery(x_query);
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

	public int saveAddEditLgaStore(LgaStoreBeanForForm bean, String action, AdmUserV userBean) {
		int result = 0;
		String x_QUERY = "";
		SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		try {
			if (action.equals("add")) {
				x_QUERY = "INSERT INTO INVENTORY_WAREHOUSES" + " ( WAREHOUSE_CODE, " // 0
						+ " WAREHOUSE_NAME, " // 1
						+ " WAREHOUSE_DESCRIPTION, " // 2
						+ " WAREHOUSE_TYPE_ID, " // 3
						+ " ADDRESS1, " // 4
						+ " COUNTRY_ID, " // 5
						+ " STATE_ID, " // 6
						+ " TELEPHONE_NUMBER, " // 7
						+ " STATUS, " // 8
						+ " START_DATE, " // 9
						+ " END_DATE, " // 10
						+ " UPDATED_BY, " // 11
						+ " DEFAULT_ORDERING_WAREHOUSE_ID, " // 12
						+ " MONTHLY_TARGET_POPULATION , " // 13
						+ " CREATED_BY, " // 14
						+ " CREATED_ON, " //
						+ " LAST_UPDATED_ON," //
						+ " SYNC_FLAG,COMPANY_ID)" + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),NOW(),'N','21000')";
			} else {
				x_QUERY = "UPDATE INVENTORY_WAREHOUSES SET " + " WAREHOUSE_CODE=?, " + " WAREHOUSE_NAME=?, "
						+ " WAREHOUSE_DESCRIPTION=?, " + "	WAREHOUSE_TYPE_ID=?, " + " ADDRESS1=?, " + " COUNTRY_ID=?, "
						+ " STATE_ID=?, " + " TELEPHONE_NUMBER=?, " + " STATUS=?, " + "	START_DATE=?, "
						+ " END_DATE=?, " + " UPDATED_BY=?, " + " DEFAULT_ORDERING_WAREHOUSE_ID=?, "
						+ " MONTHLY_TARGET_POPULATION =?," + " LAST_UPDATED_ON=NOW()," + " SYNC_FLAG='N' "
						+ " WHERE WAREHOUSE_ID=?";
			}
			SQLQuery query = session.createSQLQuery(x_QUERY);
			query.setParameter(0, bean.getX_WAREHOUSE_CODE());
			query.setParameter(1, bean.getX_WAREHOUSE_NAME());
			query.setParameter(2, bean.getX_WAREHOUSE_DESCRIPTION());
			query.setParameter(3, bean.getX_WAREHOUSE_TYPE_ID());
			query.setParameter(4, bean.getX_ADDRESS1());
			query.setParameter(5, bean.getX_COUNTRY_ID());
			query.setParameter(6, bean.getX_STATE_ID());
			query.setParameter(7, bean.getX_TELEPHONE_NUMBER());
			query.setParameter(8, bean.getX_STATUS());
			query.setParameter(9, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_START_DATE()) + " "
					+ CalendarUtil.getCurrentTime());
			if (bean.getX_END_DATE() == null || bean.getX_END_DATE().equals("")) {
				query.setParameter(10, null);
			} else {
				query.setParameter(10, CalendarUtil.getDateStringInMySqlInsertFormat(bean.getX_END_DATE()) + " "
						+ CalendarUtil.getCurrentTime());
			}
			query.setParameter(11, userBean.getX_USER_ID());
			query.setParameter(12, bean.getX_DEFAULT_ORDERING_WAREHOUSE_ID().equals("") ? null
					: bean.getX_DEFAULT_ORDERING_WAREHOUSE_ID());
			query.setParameter(13, bean.getX_MONTHLY_TARGET_POPULATION());
			if (action.equals("add")) {
				query.setParameter(14, userBean.getX_USER_ID());

			} else {
				query.setParameter(14, bean.getX_WAREHOUSE_ID());
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
