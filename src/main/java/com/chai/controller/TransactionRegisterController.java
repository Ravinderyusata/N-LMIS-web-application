package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chai.model.LabelValueBean;
import com.chai.model.views.AdmUserV;
import com.chai.services.TransactionServices;

@Controller
public class TransactionRegisterController {
	JSONArray data = new JSONArray();
	@RequestMapping(value="/transaction_register_page")
	public ModelAndView showProductGrid(HttpServletRequest request,HttpServletResponse respones){
		ModelAndView model=new ModelAndView("TransactionRegisterPage");
		return model;
	}
	@RequestMapping(value = "/get_transaction_register_grid_data")
	public JSONArray getTransacitonRegGridData(HttpServletRequest request,HttpServletResponse respones,
										@RequestParam("lgaId") String lgaId,
										@RequestParam("productId") String productId,
										@RequestParam("transactionTypeId") String transactionTypeId,
										@RequestParam("fromDate") String fromDate,
										@RequestParam("toDate") String toDate){
		System.out.println("in TransactionRegisterController.getTransacitonRegGridData()");
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
			System.out.println("warehouse id"+lgaId);
			System.out.println("product id"+productId);
			data = new TransactionServices().getJsonTransacitonRegGridData(lgaId, productId, transactionTypeId,
					fromDate, toDate);
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

	@RequestMapping(value = "/transition_register_export")
	public ModelAndView getTransationHistoryExport(HttpServletRequest request, HttpServletResponse respones) {
		System.out.println("in TransatinRegisterController.getTransationHistoryExport()");
		ModelAndView model = new ModelAndView("CommonExelGenerator");
		ArrayList<LabelValueBean> headerOfTableList = new ArrayList<>();
		headerOfTableList.add(new LabelValueBean("ITEM_NUMBER", "Product Name"));
		headerOfTableList.add(new LabelValueBean("TRANSACTION_QUANTITY", "Quantity"));
		headerOfTableList.add(new LabelValueBean("TRANSACTION_UOM", "UOM"));
		headerOfTableList.add(new LabelValueBean("TRANSACTION_DATE", "Transaction DateTime"));
		headerOfTableList.add(new LabelValueBean("REASON", "Comment"));
		headerOfTableList.add(new LabelValueBean("TRANSACTION_TYPE", "Transaction Type"));
		headerOfTableList.add(new LabelValueBean("FROM_NAME", "Source"));
		headerOfTableList.add(new LabelValueBean("TO_NAME", "Destination"));
		headerOfTableList.add(new LabelValueBean("REASON_TYPE", "Reason"));
		headerOfTableList.add(new LabelValueBean("VVM_STAGE", "VVM Status"));
		try {
			model.addObject("export_data", data);
			model.addObject("headerOfTable", headerOfTableList);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return model;
	}
}
