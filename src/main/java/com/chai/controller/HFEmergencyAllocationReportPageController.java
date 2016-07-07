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
public class HFEmergencyAllocationReportPageController {
	@RequestMapping(value = "/hf_emergency_allocation_report_page")
	public ModelAndView showHFEmergencyAllocReportPage(HttpServletRequest request, HttpServletResponse respones) {
		ModelAndView model = new ModelAndView("HFEmergencyAllocationReportPage");
		return model;
	}

	@RequestMapping(value = "/get_hf_emargency_allocation_report_grid_data")
	public JSONArray hFEmergencyAllocData(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("hfId") String hfId, @RequestParam("filterBy") String filterBy,
			@RequestParam("year") String year, @RequestParam("month") String month,
			@RequestParam("quarter") String quarter,
			@RequestParam("day") String day) {
		System.out.println("in HFBinCardController.hFEmergencyAllocData()");
		try {
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			JSONArray data = new HFReportService().getJsonHFEmergencyAllocationData(hfId, userBean.getX_WAREHOUSE_ID(),
					filterBy, year, month, quarter, day);
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
