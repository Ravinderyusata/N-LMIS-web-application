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
public class LgaWastageController {
	@RequestMapping(value="/lga_wastage_report_page")
	public ModelAndView showLgaMinMax(HttpServletRequest request,HttpServletResponse respones){
		ModelAndView model=new ModelAndView("LgaWastageReportPage");
		return model;
	}

	@RequestMapping(value = "/get_Lga_wsatage_report_grid_data")
	public void getLgaWastageReportGridData(@RequestParam("lgaID") String lgaID,
			@RequestParam("stateId") String stateId,
											@RequestParam("filterBy") String filterBy,
											@RequestParam("year") String year, 
											@RequestParam("month") String month, 
											@RequestParam("week") String week,
											@RequestParam("dayDate") String dayDate, HttpServletResponse response
											,HttpServletRequest request){
		System.out.println("in LgaWastageController.getLgaWastageReportGridData()");
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
			if (!userBean.getX_ROLE_NAME().equals("NTO")) {
				stateId = Integer.toString(userBean.getX_WAREHOUSE_ID());
			}
			//           0,       1,   2,    3,   4,     5
			JSONArray data = new ReportServices().lgaWastageReportGridData(stateId,
				lgaID,filterBy,year,month,week,dayDate);
			// System.out.println("json lgaWastageReport: "+data.toString());
		
			PrintWriter out=response.getWriter();
			out.write(data.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				response.sendRedirect("loginPage");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}		
	}
}
