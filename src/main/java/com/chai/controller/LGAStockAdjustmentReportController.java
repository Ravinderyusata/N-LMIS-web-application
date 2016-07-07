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
import com.chai.services.ReportServices;
@Controller
public class LGAStockAdjustmentReportController {
	@RequestMapping(value="/lga_stock_adjustment_report_page")
	public ModelAndView showInconsistencyReport(HttpServletRequest request,HttpServletResponse respones){
		ModelAndView model=new ModelAndView("LGAStockAdjustmentReportPage");
		return model;
	}
	@RequestMapping(value = "/get_lga_stock_adjustment_report_grid_data")
	public JSONArray getLgaStockAdjustmentReportGridData(HttpServletRequest request,HttpServletResponse respones,
															@RequestParam("dateType")String dateType,
															@RequestParam("year")String year,
															@RequestParam("month")String month,
															@RequestParam("lgaId")String lgaId,
															@RequestParam("reasonType")String reasonType,
															@RequestParam("productType")String productType,
															@RequestParam("date")String date){
		System.out.println("in LGAStockAdjustmentReportController.getLgaStockAdjustmentReportGridData()");
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
		JSONArray data=new ReportServices().getJsonLgaStockAdjustmentReportGridData(lgaId,year,
				month,dateType,reasonType,productType,date);
			// System.out.println("json ======"+data.toString());
		
			PrintWriter out=respones.getWriter();
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
