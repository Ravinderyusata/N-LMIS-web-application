package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chai.model.LabelValueBean;
import com.chai.model.LgaStoreBeanForForm;
import com.chai.model.views.AdmUserV;
import com.chai.services.LgaStoreService;

@Controller
public class LgaStoreController {
	JSONArray data;
	LgaStoreService lgaStoreService = new LgaStoreService();

	@RequestMapping(value = "/lgastorepage", method = RequestMethod.GET)
	public ModelAndView getLgaStore(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("lgaStoreFormBean") LgaStoreBeanForForm bean) {
		System.out.println("in LgaStoreController.getLgaStore()");
		ModelAndView lgaStoreModel = new ModelAndView("LgaStorePage");
		HttpSession session = request.getSession();
		AdmUserV userBean = (AdmUserV) session.getAttribute("userBean");
		lgaStoreModel.addObject("userBean", userBean);
		return lgaStoreModel;
	}

	@RequestMapping(value = "/getlgastorelist")
	public JSONArray getJsonLgaStoreList(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("in LgaStoreController.getJsonLgaStoreList()");
		try {
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			data = lgaStoreService.getLgaStoreListPageData(userBean);
			PrintWriter out = respones.getWriter();
			out.write(data.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				respones.sendRedirect("loginPage");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "/get_Store_type_list", method = RequestMethod.POST)
	public void getStoreTypeList(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("in LGAlistController.getStoreTypeList()");
		try {
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			JSONArray storeTypeList = lgaStoreService.getLgaStoreComboboxList(userBean, "LGA_STORE_TYPE", null);
			// System.out.println("json LgaStoreComboboxList:
			// "+data.toString());
			PrintWriter out = respones.getWriter();
			out.write(storeTypeList.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/get_Store_type_list_for_form", method = RequestMethod.POST)
	public void getStoreTypeListForForm(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("in LGAlistController.getStoreTypeListForForm()");
		try {
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			JSONArray storeTypeList = lgaStoreService.getLgaStoreComboboxListForForm(userBean, "LGA_STORE_TYPE", null);
			// System.out.println("json LgaStoreComboboxListForForm: " +
			// storeTypeList.toString());
			PrintWriter out = respones.getWriter();
			out.write(storeTypeList.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/get_storename_acco_storetype_list", method = RequestMethod.POST)
	public void getStoreNameAccoToStoreNameList(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("storeTypeId") String storeTypeId) {
		System.out.println("in LGAlistController.getStoreTypeList()");
		try {
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			JSONArray storeNameAccoToStoreType;
			storeNameAccoToStoreType = lgaStoreService.getStoreNameAccoToStore(userBean, storeTypeId);
			// System.out.println("json StoreNameAccoToStoreNameList:
			// "+data.toString());
			PrintWriter out = respones.getWriter();
			out.write(storeNameAccoToStoreType.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/storeTypeList", method = RequestMethod.POST)
	public void getStoreNameAccoToStoreNameListForForm(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("storeTypeLabel") String storeTypeLabel) {
		System.out.println("in LGAlistController.getStoreNameAccoToStoreNameListForForm()");
		try {
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			JSONArray storeNameAccoToStoreType;
			storeNameAccoToStoreType = lgaStoreService.getStoreNameAccoToStoreForForm(userBean, storeTypeLabel);
			// System.out.println("json StoreNameAccoToStoreNameList: " +
			// data.toString());
			PrintWriter out = respones.getWriter();
			out.write(storeNameAccoToStoreType.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/get_state_name_for_form", method = RequestMethod.POST)
	public void getStateNameListForForm(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("countryId") String countryId) {
		System.out.println("in LGAlistController.getStateNameListForForm()");
		try {
			JSONArray stateNameListForForm;
			stateNameListForForm = lgaStoreService.getStateNameListForForm(countryId);
			// System.out.println("json stateNameListForForm: " +
			// stateNameListForForm.toString());
			PrintWriter out = respones.getWriter();
			out.write(stateNameListForForm.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/search_lga_store_list", method = RequestMethod.POST)
	public void searchLgaStoreList(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("storeTypeId") String storeTypeId, @RequestParam("storeNameId") String storeNameId,
			@RequestParam("storeTypeName") String storeTypeName) {
		System.out.println("in LGAStoreController.searchLgaStoreList()");
		try {
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			data = lgaStoreService.getsearchLgaStoreData(userBean, storeTypeId, storeNameId, storeTypeName);
			// System.out.println("json searchLgaStoreList: "+data.toString());
			PrintWriter out = respones.getWriter();
			out.write(data.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/get_LgaStore_history")
	public void getHistoryOfUser(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("WAREHOUSE_ID") String WAREHOUSE_ID) {
		System.out.println("in LgaStoreController.getHistoryOfUser()");
		try {
			PrintWriter out = respones.getWriter();
			JSONArray historyOfLgaStore = lgaStoreService.getLgaStoreHistory(WAREHOUSE_ID);
			// System.out.println("history of lga
			// json"+historyOfLgaStore.toString());
			out.write(historyOfLgaStore.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/save_addedit_lgastore", method = RequestMethod.POST)
	public void saveAddEditLgaStore(@ModelAttribute("lgaStoreFormBean") LgaStoreBeanForForm bean,
			HttpServletRequest request, HttpServletResponse response) {
		AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
		System.out.println("in UserController.saveAddEditUser()");
		String action = request.getParameter("action");
		System.out.println("action------" + action);
		// System.out.println("WAREHOUSE_CODE" + bean.getX_WAREHOUSE_CODE());
		// System.out.println("WAREHOUSE_NAME" + bean.getX_WAREHOUSE_NAME());
		// System.out.println("WAREHOUSE_DESCRIPTION " +
		// bean.getX_WAREHOUSE_DESCRIPTION());
//		System.out.println("labelValue : "+bean.getLabelValue().getLabel());
//		System.out.println("labelValue : "+bean.getLabelValue().getValue());
		 System.out.println("WAREHOUSE_TYPE_ID " + bean.getX_WAREHOUSE_TYPE_ID());
		// System.out.println("ADDRESS1" + bean.getX_ADDRESS1());
		// System.out.println("COUNTRY_ID" + bean.getX_COUNTRY_ID());
		// System.out.println("STATE_ID" + bean.getX_STATE_ID());
		// System.out.println("TELEPHONE_NUMBER" +
		// bean.getX_TELEPHONE_NUMBER());
		// System.out.println("STATUS" + bean.getX_STATUS());
		// System.out.println("START_DATE" + bean.getX_START_DATE());
		// System.out.println("END_DATE" + bean.getX_END_DATE());
		 System.out.println("DEFAULT_ORDERING_WAREHOUSE_ID" +
		 bean.getX_DEFAULT_ORDERING_WAREHOUSE_ID());
		// System.out.println("MONTHLY_TARGET_POPULATION" +
		// bean.getX_MONTHLY_TARGET_POPULATION());
		int insertUpdateFlag = 0;
		try {
			if (action.equals("add")) {
				insertUpdateFlag = lgaStoreService.saveAddEditLgaStore(bean, action, userBean);
			} else {
				String warehouseId = request.getParameter("warehouseId");
				System.out.println("edit record of warehouseId" + warehouseId);
				bean.setX_WAREHOUSE_ID(Integer.parseInt(warehouseId));
				insertUpdateFlag = lgaStoreService.saveAddEditLgaStore(bean, action, userBean);
			}
			System.out.println("\ninsertUpdateFlag =" + insertUpdateFlag);
			PrintWriter out = response.getWriter();
			if (insertUpdateFlag == 1) {
				out.write("success");
			} else {
				out.write("fail");
			}
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@RequestMapping(value = "/lga_store_list_export")
	public ModelAndView getLgaStoreListExport(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("in LgaStoreController.getLgaStoreListExport()");
		ModelAndView model = new ModelAndView("CommonExelGenerator");
		ArrayList<LabelValueBean> headerOfTableList = new ArrayList<>();
		headerOfTableList.add(new LabelValueBean("WAREHOUSE_NAME", "STORE NAME"));
		headerOfTableList.add(new LabelValueBean("WAREHOUSE_TYPE_NAME", "STORE NAME"));
		headerOfTableList.add(new LabelValueBean("MONTHLY_TARGET_POPULATION", "MTP"));
		headerOfTableList.add(new LabelValueBean("TELEPHONE_NUMBER", "TELEPHONE NUMBER"));
		headerOfTableList.add(new LabelValueBean("DEFAULT_ORDERING_WAREHOUSE_NAME", "DEFAULT ORDERING WAREHOUSE NAME"));
		headerOfTableList.add(new LabelValueBean("STATUS", "STATUS"));
		headerOfTableList.add(new LabelValueBean("START_DATE", "START DATE"));
		headerOfTableList.add(new LabelValueBean("END_DATE", "END DATE"));
		try {
			model.addObject("export_data", data);
			model.addObject("headerOfTable", headerOfTableList);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return model;
	}
}