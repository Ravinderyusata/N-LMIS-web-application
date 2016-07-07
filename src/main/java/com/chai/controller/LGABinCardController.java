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
public class LGABinCardController {
	@RequestMapping(value="/lga_bin_card_page")
	public ModelAndView showProductGrid(HttpServletRequest request,HttpServletResponse respones){
		ModelAndView model=new ModelAndView("LGABinCardPage");
		return model;
	}
	@RequestMapping(value = "/get_lga_bincard_grid_data")
	public JSONArray getLgaBincardGridData(HttpServletRequest request,HttpServletResponse respones,
								@RequestParam("dateType")String dateType,
								@RequestParam("year")String year,
								@RequestParam("month")String month,
								@RequestParam("lgaId")String lgaId,
								@RequestParam("transactionType")String transactionType,
								@RequestParam("productType")String productType,
								@RequestParam("date")String date){
		System.out.println("in LGABinCardController.getLgaBincardGridData()");
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
		JSONArray data=new ReportServices().getJsonLgaBincardGridData(lgaId,year,
				month,dateType,transactionType,productType,date);
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
