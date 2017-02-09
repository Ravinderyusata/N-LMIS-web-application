package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chai.model.DeviceAssoiationGridBean;
import com.chai.model.LabelValueBean;
import com.chai.model.views.AdmUserV;
import com.chai.services.DeviceAssociationServiceForList;
import com.chai.services.ItemService;

@Controller
public class DeviceAssociationController {

	DeviceAssociationServiceForList deviceAssSerForlist = new DeviceAssociationServiceForList();

	@RequestMapping(value = "/device_association_detail", method = RequestMethod.GET)
	public ModelAndView getDeviceAssociationDetail(HttpServletRequest request, HttpServletResponse respones,
			@ModelAttribute("deviceAssBean") DeviceAssoiationGridBean deviceAssBean) throws IOException {
		System.out.println("in DeviceAssociationController.getDeviceAssociationDetail()");
		ModelAndView deviceAssociationDetail = new ModelAndView("DeviceAssociationDetail");
		HttpSession session = request.getSession();
		try {
			AdmUserV userBean = (AdmUserV) session.getAttribute("userBean");
			deviceAssociationDetail.addObject("userBean", userBean);
			List<LabelValueBean> product_list = new ItemService().getDropdownList("device_asso_product",
					String.valueOf(userBean.getX_WAREHOUSE_ID()));
			deviceAssociationDetail.addObject("product_list", product_list);

			List<LabelValueBean> ad_syringe_list = new ItemService().getDropdownList("ad_syringe",
					String.valueOf(userBean.getX_WAREHOUSE_ID()));
			deviceAssociationDetail.addObject("ad_syringe_list", ad_syringe_list);
			List<LabelValueBean> recons_syringe_list = new ItemService().getDropdownList("reconstitute_syrng",
					String.valueOf(userBean.getX_WAREHOUSE_ID()));
			deviceAssociationDetail.addObject("recons_syringe_list", recons_syringe_list);
		} catch (Exception e) {
			e.printStackTrace();
			respones.sendRedirect("loginPage");
		}
		return deviceAssociationDetail;
	}

	@RequestMapping(value = "/getdevice_association_detail")
	public void getDeviceAssociationDetail(HttpServletRequest request, HttpServletResponse respones,
			@RequestParam("warehouseId") String warehouseId) {
		System.out.println("in ItemController.getDeviceAssociationDetail()");
		try {
			JSONArray data = new ItemService().getDeviceAssoGridData(warehouseId);
			// System.out.println("json ======" + data.toString());

			PrintWriter out = respones.getWriter();
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
	}

	@RequestMapping(value = "/savedeviceAsso")
	public void addDevice(@ModelAttribute("deviceAssBean") DeviceAssoiationGridBean bean, HttpServletRequest request,
			HttpServletResponse respones, RedirectAttributes redirectAttributes) throws IOException, ServletException {
		System.out.println("in deviceAssociationcontroller.addDevice()");
		System.out.println("beanbbbbbbbbbbbbbbbbbbb" + bean.getX_ASSOCIATION_ID());
		System.out.println("beanbbbbbbbbbbbbbbbbbbb" + bean.getX_ACTION());
		HttpSession session = request.getSession();
		AdmUserV userBean = (AdmUserV) session.getAttribute("userBean");
		try {
			int insertUpdateFlag = new ItemService().add_edit_deviceAsso(bean, userBean);
			redirectAttributes.addFlashAttribute("status", bean.getX_ACTION());
			System.out.println("\ninsertUpdateFlag =" + insertUpdateFlag);
			PrintWriter out = respones.getWriter();
			if (insertUpdateFlag == 1) {
				out.write("succsess");
			} else {
				out.write("fail");
			}
			out.close();
		} catch (Exception e) {
			respones.sendRedirect("loginPage");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/device_asso_product_list")
	public void deviceAssoProductList(HttpServletRequest request, HttpServletResponse respones)
			throws IOException, ServletException {
		System.out.println("in deviceAssociationcontroller.deviceAssoProductList()");
		HttpSession session = request.getSession();
		AdmUserV userBean = (AdmUserV) session.getAttribute("userBean");
		try {
			JSONArray deviceassprodlist = deviceAssSerForlist.getDropdownList("device_asso_product",
					String.valueOf(userBean.getX_WAREHOUSE_ID()));
			// System.out.println("\ndeviceAssoProductList =" +
			// deviceassprodlist);
			PrintWriter out = respones.getWriter();
			out.write(deviceassprodlist.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/ad_syringe_list")
	public void AdSyringeList(HttpServletRequest request, HttpServletResponse respones)
			throws IOException, ServletException {
		System.out.println("in deviceAssociationcontroller.getAdSyringeList()");
		HttpSession session = request.getSession();
		AdmUserV userBean = (AdmUserV) session.getAttribute("userBean");
		try {
			JSONArray adSyringeList = deviceAssSerForlist.getDropdownList("ad_syringe",
					String.valueOf(userBean.getX_WAREHOUSE_ID()));
			// System.out.println("\nadSyringeList =" + adSyringeList);
			PrintWriter out = respones.getWriter();
			out.write(adSyringeList.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/reconstitute_syringe_list")
	public void ReconstituteSyringeList(HttpServletRequest request, HttpServletResponse respones)
			throws IOException, ServletException {
		System.out.println("in deviceAssociationcontroller.getAdSyringeList()");
		HttpSession session = request.getSession();
		AdmUserV userBean = (AdmUserV) session.getAttribute("userBean");
		try {
			JSONArray getReconstituteSyringeList = deviceAssSerForlist.getDropdownList("reconstitute_syrng",
					String.valueOf(userBean.getX_WAREHOUSE_ID()));
			// System.out.println("\ngetReconstituteSyringeList =" +
			// getReconstituteSyringeList);
			PrintWriter out = respones.getWriter();
			out.write(getReconstituteSyringeList.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
