package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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

import com.chai.model.HealthFacilityBean;
import com.chai.model.LabelValueBean;
import com.chai.model.views.AdmUserV;
import com.chai.services.HealthFacilityService;

@Controller
public class PrimaryHealthFacilityPageController {
	JSONArray data;
	HealthFacilityService hfService = new HealthFacilityService();
@RequestMapping(value="/primaryhealthfacilitypage",method = RequestMethod.GET)
	public ModelAndView getHFGridData(HttpServletRequest request, HttpServletResponse respones,
			@ModelAttribute("hfBean") HealthFacilityBean bean) {
	System.out.println("in PrimaryHealthFacilityPage.getHFGridData()");
	ModelAndView primaryHFGridPage= new ModelAndView("PrimaryHealthFacilityPage");
	try {
		HttpSession session=request.getSession();
		AdmUserV userBean=(AdmUserV)session.getAttribute("userBean");
		primaryHFGridPage.addObject("userBean", userBean);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return primaryHFGridPage;
}
@RequestMapping(value="/getprimary_hf_list_grid")
	public void getPrimaryHfListData(HttpServletRequest request, HttpServletResponse respones) {
	System.out.println("in PrimaryHealthFacilityPage.getPrimaryHfListData()");
	try{
		String state_id=request.getParameter("state_id");
		AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
		System.out.println("warehouse id"+request.getParameter("warehouse_id"));
		String warehouse_id=request.getParameter("warehouse_id");
		System.out.println("hf_id id"+request.getParameter("hf_id"));
		String hf_id=request.getParameter("hf_id");
			data = hfService.getHFGridData(warehouse_id, hf_id, state_id);
			// System.out.println("json ======"+data.toString());
	PrintWriter out=respones.getWriter();
		out.write(data.toString());
		out.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

	@RequestMapping(value = "/get_HF_history")
	public void getHistoryOfUser(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("DB_ID") String DB_ID,
			@RequestParam("DEFAULT_STORE_ID") String DEFAULT_STORE_ID) {

		System.out.println("in PrimaryHealthFacilityPage.getHistoryOfUser()");
		try {
			PrintWriter out = respones.getWriter();
			JSONArray historyOfHF = hfService.getHFHistory(DB_ID, DEFAULT_STORE_ID);
			out.write(historyOfHF.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/get_ward_list_basedon_lga")
	public void getWardListBasedOnLga(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("LGA_ID") String LGA_ID, @RequestParam("option") String option) {

		System.out.println("in PrimaryHealthFacilityPage.getWardListBasedOnLga()");
		try {
			PrintWriter out = respones.getWriter();
			JSONArray wardList = hfService.getWardListBasedOnLga(LGA_ID, option);
			out.write(wardList.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/save_addedit_hf", method = RequestMethod.POST)
	public void saveAddEditLgaStore(HttpServletRequest request, HttpServletResponse respones,
			@ModelAttribute("hfBean") HealthFacilityBean bean) {
		AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
		System.out.println("in UserController.saveAddEditUser()");
		String action = request.getParameter("action");
		System.out.println("aciton------" + action);
		if (!bean.getX_TARGET_POPULATION().equals("") || bean.getX_TARGET_POPULATION() != null) {
			bean.setX_EDIT_DATE(LocalDate.now().toString());
		} else {
			bean.setX_EDIT_DATE(null);
		}
		System.out.println("EDIT_DATE" + bean.getX_EDIT_DATE());

		int insertUpdateFlag = 0;
		try {
			if (action.equals("add")) {
				insertUpdateFlag = hfService.saveAddEditHF(bean, action, userBean);
			} else {
				String CUSTOMER_ID = request.getParameter("CUSTOMER_ID");
				System.out.println("edit record of warehouseId" + CUSTOMER_ID);
				bean.setX_HF_ID(CUSTOMER_ID);
				insertUpdateFlag = hfService.saveAddEditHF(bean, action, userBean);
			}
			System.out.println("\ninsertUpdateFlag =" + insertUpdateFlag);
			PrintWriter out = respones.getWriter();
			if (insertUpdateFlag == 1) {
				out.write("succsess");
			} else {
				out.write("fail");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/get_state_store_id_basedon_lga_id")
	public void StateStoreIdBasedOnLgaId(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("LGA_ID") String LGA_ID) {

		System.out.println("in PrimaryHealthFacilityPage.getStateStoreIdBasedOnLgaId()");
		try {
			PrintWriter out = respones.getWriter();
			JSONArray statestoreidjson = hfService.getStateStoreIdBasedOnLgaId(LGA_ID);
			out.write(statestoreidjson.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@RequestMapping(value = "/HF_list_export")
	public ModelAndView getHFListExport(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("in PrimaryHealthFacilityPage.getLgaStoreListExport()");
		ModelAndView model = new ModelAndView("CommonExelGenerator");
		ArrayList<LabelValueBean> headerOfTableList = new ArrayList<>();
		headerOfTableList.add(new LabelValueBean("CUSTOMER_NAME", "Health Facility Name"));
		headerOfTableList.add(new LabelValueBean("customer_type_code", "Ward"));
		headerOfTableList.add(new LabelValueBean("TARGET_POPULATION", "MTP"));
		headerOfTableList.add(new LabelValueBean("EDIT_DATE", "MTP Edit Date"));
		headerOfTableList.add(new LabelValueBean("MONTHLY_PREGNANT_WOMEN_TP", "Pregnant Women MTP(TT)"));
		headerOfTableList.add(new LabelValueBean("DEFAULT_STORE", "Default Ordering Store"));
		headerOfTableList.add(new LabelValueBean("TELEPHONE_NUMBER", "TELEPHONE NUMBER"));
		headerOfTableList.add(new LabelValueBean("VACCINE_FLAG", "Fridge Available"));
		headerOfTableList.add(new LabelValueBean("STATUS", "Active"));
		headerOfTableList.add(new LabelValueBean("START_DATE", "Facility Start Date"));
		try {
			model.addObject("export_data", data);
			model.addObject("headerOfTable", headerOfTableList);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return model;
	}

}
