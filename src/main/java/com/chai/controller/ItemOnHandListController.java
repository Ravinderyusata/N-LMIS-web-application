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
import com.chai.services.ItemService;

@Controller
public class ItemOnHandListController {
@RequestMapping(value="/item_onhand_grid")
public ModelAndView showProductGrid(HttpServletRequest request,HttpServletResponse respones){
	ModelAndView model=new ModelAndView("ItemOnHandListPage");
	return model;
}
@RequestMapping(value = "/get_item_onhand_grid_data")
public JSONArray getitemOnHandGridData(HttpServletRequest request,HttpServletResponse respones){
	System.out.println("in ItemOnhandListController.getJsonitemOnHandGridData()");
	System.out.println("warehouse id"+request.getParameter("warehouse_id"));
	String warehouse_id=request.getParameter("warehouse_id");
	System.out.println("product id"+request.getParameter("product_id"));
	String product_id=request.getParameter("product_id");
	try{
		AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
	JSONArray data=new ItemService().getitemOnHandGridData(userBean,warehouse_id,product_id);
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
