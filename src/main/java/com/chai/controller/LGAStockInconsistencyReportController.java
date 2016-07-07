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
public class LGAStockInconsistencyReportController { 
	@RequestMapping(value="/lga_Inconsistency_report_page")
	public ModelAndView showInconsistencyReport(HttpServletRequest request,HttpServletResponse respones){
		ModelAndView model=new ModelAndView("LGAStockInconsistencyReportPage");
		return model;
	}
	@RequestMapping(value = "/get_lga_Inconsistency_report_grid_data")
	public JSONArray getLgaWastageReportGridData(@RequestParam("lgaID") String lgaID, 
												@RequestParam("filterBy") String filterBy,
												@RequestParam("year") String year, 
												@RequestParam("month") String month, 
												@RequestParam("week") String week,
												@RequestParam("dayDate") String dayDate,
												HttpServletResponse respones,
												HttpServletRequest request){
		System.out.println("in LGAStockInconsistencyReportController.getLgaWastageReportGridData()");
		try{

			if (lgaID == null) {
				System.out.println("lgaID is null: '"+lgaID+"'");
			}else if(lgaID.length()==0){
				System.out.println("lgaID length is 0 : '"+lgaID+"'");
			}
			if (filterBy == null) {

			}else if(filterBy.length()!=0){
				System.out.println("filterBy = "+filterBy);
				
			}
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
		JSONArray data=new ReportServices().lgaInconsistencyReportGridData(userBean.getX_WAREHOUSE_ID(),lgaID,filterBy,year,month,week,dayDate);
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
