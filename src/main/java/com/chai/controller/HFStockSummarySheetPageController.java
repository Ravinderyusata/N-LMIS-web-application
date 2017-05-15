package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chai.model.LabelValueBean;
import com.chai.model.views.AdmUserV;
import com.chai.services.ComboBoxListServices;
import com.chai.services.DashboardServices;

@Controller
public class HFStockSummarySheetPageController {
	
	@Autowired private DashboardServices dashboardServices;
	@Autowired private ComboBoxListServices comboBoxListServices;
	
	List<LabelValueBean> productList = new LinkedList<LabelValueBean>();
	JSONArray griddata = new JSONArray();
	
	@RequestMapping(value = "/hf_stock_summary_sheet_page")
	public ModelAndView showHFStockSummarySheetPage(HttpServletRequest request, HttpServletResponse respones) {
//		System.out.println("HfstockSumaryPageController.showHFStockSummarySheetPage()");
		ModelAndView model = new ModelAndView("HFStockSummarySheetPage");
		return model;
	}

	@RequestMapping(value = "/getheadingTableForHfStockSummary")
	public void getheadingData(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("lgaName") String lgaName) {
		AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
		try {
			productList = comboBoxListServices.getProductListInBean("productlistbassedonlga",String.valueOf(userBean.getX_WAREHOUSE_ID()), "false");
			productList.add(0, new LabelValueBean(null, lgaName));
			JSONArray array = new JSONArray();
			for (LabelValueBean object : productList) {
				array.put(new JSONObject().put("product_name", object.getLabel()));
			}
//			System.out.println("heading data:" + array.toString());
			PrintWriter out = respones.getWriter();
			out.write(array.toString());
			out.close();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/get_hf_stock_summary_grid_data")
	public JSONArray HfstockSumaryPageController(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("year") String year, @RequestParam("week") String week, @RequestParam("lgaId") String lgaId,
			@RequestParam("lgaName") String lgaName) throws Exception {
		JSONArray arrayforjsp = new JSONArray();
		List activeHFWithZeroData = null;
//		System.out.println("in HfstockSumaryPageController.HfstockSumaryPageController()");
//		System.out.println("lgaId :" + lgaId);
//		System.out.println("year :" + year);
//		System.out.println("week :" + week);
		try {
			JSONArray data;
			data = dashboardServices.getHFStockSummaryGridData(year, week, lgaId);
			activeHFWithZeroData = dashboardServices.activeHFWithZeroData(year, week, lgaId);
//			System.out.println("lgaId: " + lgaId);
//			System.out.println("get_hf_stock_summary_grid_data data.toSring(): " + data.toString());
			SortedSet<String> hfNameList = new TreeSet<String>();
			for (Object obj : data) {
				// to generate hf name list
				JSONObject griobject = (JSONObject) obj;
				hfNameList.add(griobject.get("CUSTOMER_NAME").toString());
			}
			int i = 0;
			int j = 0;
//			System.out.println("get_hf_stock_summary_grid_data hf name list: " + hfNameList.toString());
			LabelValueBean lgaNameBeen=productList.get(0);
			productList.remove(0);// for remove lga from product name field
			for (String hf : hfNameList) {
				JSONObject rowObject = new JSONObject();
				rowObject.put("CUSTOMER_NAME", hf);
				for (int k = 0; k < productList.size(); k++) {
					LabelValueBean bean = productList.get(k);
					JSONObject colObject = new JSONObject();
					for (Object dataresult : data) {
						JSONObject dataobjectresult = (JSONObject) dataresult;
						if (String.valueOf(dataobjectresult.get("CUSTOMER_NAME")).equals(hf)
								&& bean.getLabel().equals(String.valueOf(dataobjectresult.get("ITEM_NUMBER")))) {
//							System.out.println("col" + j++);
							colObject.put("STOCK_BALANCE", String.valueOf(dataobjectresult.get("STOCK_BALANCE")));
							colObject.put("LEGEND_COLOR", String.valueOf(dataobjectresult.get("LEGEND_COLOR")));
							break;
						}
					}
					if (colObject.length() > 0) {

					} else {
//						System.out.println("col" + j++);
						colObject.put("STOCK_BALANCE", "0");
						colObject.put("LEGEND_COLOR", "red");
					}
					rowObject.put(bean.getLabel(), colObject);
				}
				arrayforjsp.put(rowObject);
			}
			productList.add(0, lgaNameBeen);//for add again lga Name
			griddata = arrayforjsp;// for global
			arrayforjsp.put(activeHFWithZeroData);
//			System.out.println("get_hf_stock_summary_grid_data jsonforjsp ======" + arrayforjsp.toString());
			PrintWriter out = respones.getWriter();
			System.out.println("arrayFor HF STOCK SUMMARY: "+arrayforjsp.toString());
			out.write(arrayforjsp.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/hf_stock_summary_export_data")
	public ModelAndView export_data_grid(HttpServletRequest request, HttpServletResponse respones) throws IOException {
		// String export_data=request.getParameter("export_data");
		System.out.println("export_data_grid in lgastocksummdrypagecont ");
		ModelAndView model = new ModelAndView("hfStockSummarySheetExelView");
		model.addObject("productListWithCustomerName", productList);
		model.addObject("export_data", griddata);
		return model;
	}
}
