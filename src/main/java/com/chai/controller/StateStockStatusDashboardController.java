package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chai.model.views.AdmUserV;
import com.chai.services.ComboBoxListServices;
import com.chai.services.DashboardServices;

@Controller
public class StateStockStatusDashboardController {
	JSONArray stateList = new JSONArray();
	JSONArray data = new JSONArray();

	@RequestMapping(value = "/state_Stock_Status_DashboardPage")
	public ModelAndView getStateStockStatusfDashboardPage(HttpServletRequest request, HttpServletResponse respones) {
		ModelAndView model = new ModelAndView("StateStockStatusDashboardPage");
		System.out.println("in StateStockStatusfDashboard.getStateStockStatusfDashboardPage()");
		try {
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			stateList = new ComboBoxListServices().getComboboxList("STATE_STORE",
					String.valueOf(userBean.getX_WAREHOUSE_ID()), "All");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				respones.sendRedirect("loginPage");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/get_state_stock_status_dashboard_data")
	public void getStateStockStatusfDashboardData(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("in StateStockStatusfDashboard.getStateStockStatusfDashboardData()");
		try {
			String lgaId = request.getParameter("lga_id");
			String week = request.getParameter("week");
			String year = request.getParameter("year");
			System.out.println("lgaId:" + lgaId + "\n" + "week:" + week + "\n" + "year:" + year);
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			data = new DashboardServices().getStateStockStatusDashboard(userBean, year, week, lgaId);
			PrintWriter out = respones.getWriter();
			out.write(data.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				respones.sendRedirect("loginPage");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	@RequestMapping(value = "/get_lga_agg_stock_dashboard_data")
	public void LgaAggStockDashboardData(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("in StateStockStatusfDashboard.getStateStockStatusfDashboardData()");
		try {
			String week = request.getParameter("week");
			String year = request.getParameter("year");
			System.out.println("\n" + "week:" + week + "\n" + "year:" + year);
			AdmUserV userBean = (AdmUserV) request.getSession().getAttribute("userBean");
			data = new DashboardServices().getLgaAggStockDashboardData(year, week);
			// System.out.println("statestockstatusperfo "+data);
			PrintWriter out = respones.getWriter();
			out.write(data.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				respones.sendRedirect("loginPage");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	@RequestMapping(value = "/get_state_stock_status_dashboard_export")
	public ModelAndView getStateStockPerfDashboardexport(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("export_data_grid in statestockPerformancedashboard ");
		ModelAndView model = new ModelAndView("excelStatestockStatusDashboard");
		model.addObject("statestockstatusData", data);
		return model;
	}

	@RequestMapping(value = "/get_lga_agg_stock_dashboard_export")
	public ModelAndView getLgaAggStockDashboardexport(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("export_data_grid in getLgaAggStockDashboardexport ");
		ModelAndView model = new ModelAndView("excelLgaAggstockeDashboard");
		model.addObject("lgaaggstockData", data);
		return model;
	}
}
