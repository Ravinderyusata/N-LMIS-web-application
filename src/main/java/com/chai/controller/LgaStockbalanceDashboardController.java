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
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chai.model.LabelValueBean;
import com.chai.model.views.AdmUserV;
import com.chai.services.ComboBoxListServices;
import com.chai.services.DashboardServices;

@Controller
public class LgaStockbalanceDashboardController {
	List<LabelValueBean> productList=new LinkedList<LabelValueBean>();
	JSONArray griddata=new JSONArray();
	
	@RequestMapping(value="/lga_stock_balance_dashboard_page")
	public ModelAndView showLgaStockSummGrid(HttpServletRequest request,HttpServletResponse respones){
		ModelAndView model=new ModelAndView("LgaStockbalanceDashboard");
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
		} catch (Exception e) {
			e.printStackTrace();
			
		}		
		return model;
	}

	@RequestMapping(value = "/get_lga_stock_balance_dashbaord_data")
	public JSONArray getLgaStockBalanceDashboardData(HttpServletRequest request
			,HttpServletResponse respones
			,@RequestParam(value="stateId",required=false) String stateId,
			@RequestParam(value="stateName",required=false) String stateName ){
		JSONArray arrayforjsp=new JSONArray();
		//System.out.println("in ProductMainController.getJsonitemOnHandGridData()");
		System.out.println("year"+request.getParameter("year"));
		String year=request.getParameter("year");
		System.out.println("week "+request.getParameter("week"));
		String week=request.getParameter("week");
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
			JSONArray data;
			if(stateId!=null && !stateId.equals("")){
				 productList=new ComboBoxListServices().getProductListInBean("productlistbassedonlga",
						 stateId,"false");
					productList.add(0, new LabelValueBean(null, stateName));
				data=new DashboardServices().getLgaStockBalanceDashboardData(userBean,year,week,request.getParameter("stateId"));
				System.out.println("select id: "+request.getParameter("stateId"));
			}else{
				productList=new ComboBoxListServices().getProductListInBean("productlistbassedonlga",
						String.valueOf(userBean.getX_WAREHOUSE_ID()),"false");
				//System.out.println("product list for:"+request.getParameter("lgaId"));
				productList.add(0, new LabelValueBean(null, String.valueOf(userBean.getX_WAREHOUSE_ID())));
				 data=new DashboardServices().getLgaStockBalanceDashboardData(userBean,year,week,null);
			}	
		System.out.println("getLgaStockBalanceDashboardData : "+data.toString());
		SortedSet<String> lgaNameList=new TreeSet<String>();
		for (Object obj : data) {
			JSONObject griobject=(JSONObject)obj;
			lgaNameList.add(griobject.get("LGA_NAME").toString());
		}
		int i=0;
		int j=0;
		//System.out.println("get_lga_stock_summary_grid_data LGA NAME: "+lgaNameList.toString());
		for (String lga : lgaNameList) {
			JSONObject gridObect=new JSONObject();
			gridObect.put("LGA_NAME", lga);
			//System.out.println("row"+i++);
			for (int k = 0; k < productList.size(); k++) {
				LabelValueBean bean=productList.get(k);
				for (Object dataresult : data) {
				JSONObject dataojectresult=(JSONObject)dataresult;
					if(String.valueOf(dataojectresult.get("LGA_NAME")).equals(lga)
							&& bean.getLabel().equals(String.valueOf(dataojectresult.get("ITEM_NUMBER")))){
						JSONObject datafieldwithcolor=new JSONObject();
						//System.out.println("col"+j++);
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
			// System.out.println("get_lga_stock_summary_grid_data jsonforjsp	 ======"+arrayforjsp.toString());
			PrintWriter out=respones.getWriter();
			out.write(arrayforjsp.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return null;		
	}
	
	@RequestMapping(value="/lga_Stock_balance_dashboard_export")
	public ModelAndView lgaStockDashboardDataExport(HttpServletRequest request,HttpServletResponse respones) throws IOException{
//		String export_data=request.getParameter("export_data");
		ModelAndView model=new ModelAndView("excelLgaStockSummry");
		model.addObject("productlistwithstatename", productList);
		model.addObject("export_data", griddata);
		 return model;
	}
	
}
