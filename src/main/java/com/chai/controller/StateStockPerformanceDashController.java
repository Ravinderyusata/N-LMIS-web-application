package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chai.model.views.AdmUserV;
import com.chai.services.ComboBoxListServices;
import com.chai.services.DashboardServices;

@Controller
public class StateStockPerformanceDashController {
	JSONArray lgaList=new JSONArray();
	JSONArray data=new JSONArray();
	@RequestMapping(value="/state_stock_perfo_dashboard",method=RequestMethod.GET)
	public ModelAndView getStateStockPerfDashboardPage(HttpServletRequest request,HttpServletResponse respones){
		ModelAndView model=new ModelAndView("StateStockPerformanceDashboard");
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
			lgaList=new ComboBoxListServices().getComboboxList("lgalistBasedonstate",
				String.valueOf(userBean.getX_WAREHOUSE_ID()),"All");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				respones.sendRedirect("loginPage");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		return model;
	}
	@RequestMapping(value="/get_state_stock_perfo_dashboard_data")
	public void getStateStockPerfDashboardData(HttpServletRequest request,HttpServletResponse respones){
		try{
	String lgaId=request.getParameter("lga_id");
	String week=request.getParameter("week");
	String year=request.getParameter("year");
	System.out.println("lgaId:"+lgaId+"\n"+"week:"+week+"\n"+"year:"+year);
	AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
	data=new DashboardServices().getStateStockPerfDashboard(userBean,year,week,lgaId);
			// System.out.println("statestockperfom "+data);
	PrintWriter out=respones.getWriter();
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
	@RequestMapping(value="/get_state_stock_perfo_dashboard_export")
	public ModelAndView getStateStockPerfDashboardexport(HttpServletRequest request,HttpServletResponse respones){
		System.out.println("export_data_grid in statestockPerformancedashboard ");
		ModelAndView model=new ModelAndView("excelStatestockPerformanceDashboard");
		model.addObject("stockPerfoarmanceData", data);
		 return model;
	}
}
