package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chai.model.views.AdmUserV;
import com.chai.services.HFReportService;

@Controller
public class HFMinMaxStockLevelReportPageController {
	@RequestMapping(value = "/hf_min_max_stock_level_report_page")
	public ModelAndView hFMinMaxStockLevelReport(HttpServletRequest request, HttpServletResponse respones) {
		ModelAndView model = new ModelAndView("HFMinMaxStockLevelReportPage");
		return model;
	}

	@RequestMapping(value = "/get_hf_min_max_stock_level_report_data")
	public JSONArray minMaxStockLevelReportData(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("hfId") String hfId, @RequestParam("allocationType") String allocationType,
			@RequestParam("minMax") String minMax,
			@RequestParam("filterBy") String filterBy, @RequestParam("year") String year,
			@RequestParam("month") String month, @RequestParam("week") String week, @RequestParam("day") String day) {
		System.out.println("in HFMinMaxStockLevelReportController.minMaxStockLevelReportData()");
		try {
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			JSONArray data = new HFReportService().getJsonHFMinMaxData(hfId, allocationType,
					minMax, userBean.getX_WAREHOUSE_ID(), filterBy, year, month, week, day);
			// System.out.println("json ======"+data.toString());

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
}
