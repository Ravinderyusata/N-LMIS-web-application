package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chai.model.DeviceAssoiationGridBean;
import com.chai.model.LabelValueBean;
import com.chai.model.ProductBean;
import com.chai.model.views.AdmUserV;
import com.chai.services.ItemService;

@Controller
public class ProductMainController {
	JSONArray data;
	ItemService itemService = new ItemService();
@RequestMapping(value="/show_product_main_grid")
	public ModelAndView showProductGrid(HttpServletRequest request, HttpServletResponse respones,
			@ModelAttribute("productBean") ProductBean bean,
			@ModelAttribute("deviceAssBean") DeviceAssoiationGridBean bean1) {
	ModelAndView model=new ModelAndView("ProductMainGrid");
	return model;
}

@RequestMapping(value = "/get_product_main_grid_data")
public JSONArray getJsonProductMainGridData(@RequestParam("warehouse_id") String warehouse_id,
		HttpServletRequest request,
		HttpServletResponse respones){
	System.out.println("in ProductMainController.getJsonProductMainGridData()");
	try{
		AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
			data = itemService.getJsonProductMainGridData(warehouse_id);
			// System.out.println("json ======" + data.toString());

			PrintWriter out = respones.getWriter();
			out.write(data.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				respones.sendRedirect("loginPage");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;

	}

	@RequestMapping(value = "/get_product_type_list")
	public JSONArray ProductTypelist(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("in ProductMainController.getProductTypelist()");
		try {
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			JSONArray productTypelist = itemService.getProductTypeList();
			// System.out.println("json ======" + productTypelist.toString());

			PrintWriter out = respones.getWriter();
			out.write(productTypelist.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				respones.sendRedirect("loginPage");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;

	}

	@RequestMapping(value = "/get_product_category_list")
	public JSONArray ProductCategoryTypelist(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("productTypeId") String productTypeId) {
		System.out.println("in ProductMainController.ProductCategoryTypelist()");
		try {
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			JSONArray productCategoryTypeList = itemService.getProductCategoryTypeList(productTypeId);
			// System.out.println("json ======" +
			// productCategoryTypeList.toString());
	
		PrintWriter out=respones.getWriter();
			out.write(productCategoryTypeList.toString());
		out.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		try {
			respones.sendRedirect("loginPage");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	return null;
	
}

	@RequestMapping(value = "/save_addedit_product", method = RequestMethod.POST)
	public void saveAddEditProduct(HttpServletRequest request, HttpServletResponse respones,
			@ModelAttribute("productBean") ProductBean bean) {
		AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
		System.out.println("in productMaincontroller.saveAddEditUser()");
		String action = request.getParameter("action");
		System.out.println("aciton------" + action);
		// System.out.println("Item_NUMBER" + bean.getX_ITEM_NUMBER());
		// System.out.println("item discription " +
		// bean.getX_ITEM_DESCRIPTION());
		// System.out.println("poduct type id " + bean.getX_ITEM_TYPE_ID());
		// System.out.println("product category " + bean.getX_CATEGORY_ID());
		// System.out.println("primary uom " + bean.getX_PRIMARY_UOM());
		// System.out.println("doses per schedule " +
		// bean.getX_DOSES_PER_SCHEDULE());
		// System.out.println("vaccine persfatin " +
		// bean.getX_VACCINE_PRESENTATION());
		// System.out.println("target coverage " + bean.getX_TARGET_COVERAGE());
		// System.out.println("STATUS" + bean.getX_STATUS());
		// System.out.println("wastage rate " + bean.getX_WASTAGE_RATE());
		// System.out.println("wastage factor " + bean.getX_WASTAGE_FACTOR());
		// System.out.println("START_DATE " + bean.getX_START_DATE());
		// System.out.println("END_DATE " + bean.getX_END_DATE());
		// System.out.println("expire date " + bean.getX_EXPIRATION_DATE());
		// System.out.println("CREATED_BY " + bean.getX_CREATED_BY());
		// System.out.println("CREATED_ON " + bean.getX_CREATED_ON());
		// System.out.println("LAST_UPDATED_ON " + bean.getX_LAST_UPDATED_ON());
		int insertUpdateFlag = 1;
		try {
			if (action.equals("add")) {
				insertUpdateFlag = itemService.saveAddEditProduct(bean, action, userBean);
			} else {
				String ITEM_ID = request.getParameter("ITEM_ID");
				System.out.println("edit record of ITEM_ID" + ITEM_ID);
				bean.setX_ITEM_ID(ITEM_ID);
				insertUpdateFlag = itemService.saveAddEditProduct(bean, action, userBean);
			}
			System.out.println("\ninsertUpdateFlag =" + insertUpdateFlag);
			PrintWriter out = respones.getWriter();
			if (insertUpdateFlag > 0) {
				out.write("succsess");
			} else {
				out.write("fail");
			}
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@RequestMapping(value = "/product_list_export")
	public ModelAndView getProductlistExport(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("in PrimaryHealthFacilityPage.getLgaStoreListExport()");
		ModelAndView model = new ModelAndView("CommonExelGenerator");
		ArrayList<LabelValueBean> headerOfTableList = new ArrayList<>();
		headerOfTableList.add(new LabelValueBean("ITEM_NUMBER", "Product Short Name"));
		headerOfTableList.add(new LabelValueBean("ITEM_DESCRIPTION", "Product Description"));
		headerOfTableList.add(new LabelValueBean("ITEM_TYPE_NAME", "Item Type"));
		headerOfTableList.add(new LabelValueBean("CATEGORY_CODE", "Category Code"));
		headerOfTableList.add(new LabelValueBean("TRANSACTION_BASE_UOM", "Primary UOM"));
		headerOfTableList.add(new LabelValueBean("TARGET_COVERAGE", "Target Coverage"));
		headerOfTableList.add(new LabelValueBean("VACCINE_PRESENTATION", "Vaccine Presentation"));
		headerOfTableList.add(new LabelValueBean("YIELD_PERCENT", "Wastage Rate"));
		headerOfTableList.add(new LabelValueBean("WASTAGE_FACTOR", "Wastage Factor"));
		headerOfTableList.add(new LabelValueBean("DOSES_PER_SCHEDULE", "Doses Per Schedule"));
		headerOfTableList.add(new LabelValueBean("EXPIRATION_DATE", "Expiration Date"));
		headerOfTableList.add(new LabelValueBean("STATUS", "STATUS"));
		headerOfTableList.add(new LabelValueBean("START_DATE", "Start Date"));

		try {
			model.addObject("export_data", data);
			model.addObject("headerOfTable", headerOfTableList);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return model;
	}

}
