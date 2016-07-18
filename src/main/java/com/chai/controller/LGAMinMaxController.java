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
public class LGAMinMaxController {
	@RequestMapping(value="/lga_min_max_page")
	public ModelAndView showLgaMinMax(HttpServletRequest request,HttpServletResponse respones){
		ModelAndView model=new ModelAndView("LGAMinMaxPage");
		return model;
	}
	@RequestMapping(value = "/get_min_max_grid_data")
	public JSONArray getLgaMinMaxGridData(@RequestParam("stateId") String stateId, @RequestParam("lgaId") String lgaId,
										@RequestParam("perioadType") String perioadType,
										@RequestParam("minMax") String minMax,
										@RequestParam("year") String year,
										@RequestParam("weekOrMonth") String weekOrMonth,
										HttpServletRequest request,
										HttpServletResponse respones){
		System.out.println("in LGAMinMaxController.getLgaMinMaxGridData()");
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
			if (!userBean.getX_ROLE_NAME().equals("NTO")) {
				stateId = Integer.toString(userBean.getX_WAREHOUSE_ID());
			}
			JSONArray data = new ReportServices().getJsonLgaMinMaxData(stateId, lgaId, minMax, perioadType, year,
					weekOrMonth);
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
