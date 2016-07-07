package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chai.model.LabelValueBean;
import com.chai.model.views.AdmUserV;
import com.chai.services.ComboBoxListServices;
import com.chai.services.DashboardServices;

@Controller
public class LGAStockSummaryPageController {
	List<LabelValueBean> productList=new LinkedList<LabelValueBean>();
	JSONArray griddata=new JSONArray();
	
	@RequestMapping(value="/lga_stock_summary_grid")
	public ModelAndView showLgaStockSummGrid(HttpServletRequest request,HttpServletResponse respones){
		ModelAndView model=new ModelAndView("LGAStockSummaryPage");
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
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
	@RequestMapping(value = "/getheadingTable")
	public void getheadingData( HttpServletRequest request,HttpServletResponse respones){
		AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
		try {
			if(userBean.getX_ROLE_NAME().equals("NTO")){
				productList=new ComboBoxListServices().getProductListInBean("productlistbassedonlga",
						request.getParameter("lgaId"),"false");
				System.out.println("product list for:"+request.getParameter("lgaId"));
				productList.add(0, new LabelValueBean(null, request.getParameter("lgaName")));
			}else{
				 productList=new ComboBoxListServices().getProductListInBean("productlistbassedonlga",
							String.valueOf(userBean.getX_WAREHOUSE_ID()),"false");
					productList.add(0, new LabelValueBean(null, userBean.getX_WAREHOUSE_NAME()));
			}
			JSONArray array=new JSONArray();
				for (LabelValueBean object : productList) {
					array.put(new JSONObject().put("product_name", object.getLabel()));
				}
			// System.out.println("heading data:"+array.toString());
				PrintWriter out=respones.getWriter();
				out.write(array.toString());
				out.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/get_lga_stock_summary_grid_data")
	public JSONArray getLgaStockSummGridData(HttpServletRequest request,HttpServletResponse respones){
		JSONArray arrayforjsp=new JSONArray();
		System.out.println("in ProductMainController.getJsonitemOnHandGridData()");
		System.out.println("year"+request.getParameter("year"));
		String year=request.getParameter("year");
		System.out.println("week "+request.getParameter("week"));
		String week=request.getParameter("week");
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
			JSONArray data;
			if(request.getParameter("stateId")!=null){
				data=new DashboardServices().getLgaStockSummaryGridData(userBean,year,week,request.getParameter("stateId"));
				System.out.println("select id: "+request.getParameter("stateId"));
			}else{
				 data=new DashboardServices().getLgaStockSummaryGridData(userBean,year,week,null);
			}	
		System.out.println("get_lga_stock_summary_grid_data : "+data.toString());
		SortedSet<String> lgaNameList=new TreeSet<String>();
		for (Object obj : data) {
			JSONObject griobject=(JSONObject)obj;
			lgaNameList.add(griobject.get("LGA_NAME").toString());
		}
		int i=0;
		int j=0;
		System.out.println("get_lga_stock_summary_grid_data LGA NAME: "+lgaNameList.toString());
		for (String lga : lgaNameList) {
			JSONObject gridObect=new JSONObject();
			gridObect.put("LGA_NAME", lga);
			System.out.println("row"+i++);
			for (int k = 0; k < productList.size(); k++) {
				LabelValueBean bean=productList.get(k);
				for (Object dataresult : data) {
				JSONObject dataojectresult=(JSONObject)dataresult;
					if(String.valueOf(dataojectresult.get("LGA_NAME")).equals(lga)
							&& bean.getLabel().equals(String.valueOf(dataojectresult.get("ITEM_NUMBER")))){
						JSONObject datafieldwithcolor=new JSONObject();
						System.out.println("col"+j++);
						datafieldwithcolor.put((String.valueOf(dataojectresult.get("ITEM_NUMBER"))), String.valueOf(dataojectresult.get("ONHAND_QUANTITY")));
						datafieldwithcolor.put("LEGEND_COLOR", String.valueOf(dataojectresult.get("LEGEND_COLOR")));
						gridObect.put("data_filed_"+(String.valueOf(dataojectresult.get("ITEM_NUMBER"))), datafieldwithcolor);
					}
				}
			}		
			j=0;
			arrayforjsp.put(gridObect);
		}
		griddata=arrayforjsp;//for global
			// System.out.println("get_lga_stock_summary_grid_data jsonforjsp
			// ======"+arrayforjsp.toString());
			PrintWriter out=respones.getWriter();
			out.write(arrayforjsp.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				respones.sendRedirect("loginPage");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;		
	}
	
	@RequestMapping(value="/export_data_grid")
	public ModelAndView export_data_grid(HttpServletRequest request,HttpServletResponse respones) throws IOException{
//		String export_data=request.getParameter("export_data");
		System.out.println("export_data_grid in lgastocksummdrypagecont ");
		ModelAndView model=new ModelAndView("excelLgaStockSummry");
		model.addObject("productlistwithstatename", productList);
		model.addObject("export_data", griddata);
		 return model;
	}
}
