package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

import com.chai.model.LabelValueBean;
import com.chai.model.UserBeanForUserForm;
import com.chai.model.views.AdmUserV;
import com.chai.services.UserService;

@Controller
public class UsersController {
	JSONArray data;
	UserService userService=new UserService();
	@RequestMapping(value = "/userpage", method = RequestMethod.GET)
	 public ModelAndView getForm(HttpServletRequest request,HttpServletResponse response,
			 @ModelAttribute("beanForUser")UserBeanForUserForm bean) throws IOException {
			ModelAndView modelAndView = new ModelAndView();
			try {
				System.out.println("in UsersController action userpage");
				HttpSession session=request.getSession();
				AdmUserV userBean=(AdmUserV)session.getAttribute("userBean");
				modelAndView.setViewName("UserPage");
				modelAndView.addObject("userBean",userBean);
//				List<AdmUserV> list=userService.getUserListPageData();
//				System.out.println("user list sizse"+list.size());
//				modelAndView.addObject("userListtableData",list);
				System.out.println("name..: "+userBean.getX_WAREHOUSE_NAME());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendRedirect("loginPage");
			}
			return modelAndView;
	}
	
	@RequestMapping(value = "/getuserlist")
	public JSONArray getJsonUserList(HttpServletRequest request, HttpServletResponse respones) throws IOException {
		System.out.println("list data");
		PrintWriter out = respones.getWriter();
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
		 data=userService.getUserListPageData(userBean);
			// System.out.println("json ======"+data.toString());
			out.write(data.toString());

		} catch (NullPointerException | org.hibernate.exception.JDBCConnectionException e) {
			out.write(data.toString());
			e.printStackTrace();
		} finally {
			out.close();
		}

		return null;
		
	}
	
	@RequestMapping(value = "/search_user_list")
	public JSONArray getSearchUserList(HttpServletRequest request,HttpServletResponse respones,
			@RequestParam("userTypeId") String userTypeId,
			@RequestParam("roleId") String roleId,
			@RequestParam("warehouseId") String warehouseId){
		System.out.println("user Type id"+userTypeId);
		System.out.println("roleId"+roleId);
		System.out.println("warehouseId"+warehouseId);
		try{
			AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
			data=userService.getSearchUserListPageData(userTypeId,roleId,warehouseId,userBean);
			// System.out.println("json ======"+data.toString());
		
			PrintWriter out=respones.getWriter();
			out.write(data.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	@RequestMapping(value = "/get_history_user")
	public void getHistoryOfUser(HttpServletRequest request,HttpServletResponse respones,
			@RequestParam("user_id") String user_id){
		System.out.println("in UserController.getHistoryOfUser()");
		try {
			PrintWriter out = respones.getWriter();
			JSONArray historyOfUser=userService.getUserHistory(user_id);
			// System.out.println("history of user
			// json"+historyOfUser.toString());
			out.write(historyOfUser.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/change_user_password")
	public void changeUserPassword(HttpServletRequest request,HttpServletResponse respones,
			@RequestParam("user_id") String user_id,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("oldPassword") String oldPassword){
		System.out.println("in UserController.changeUserPassword()");
		try {
			PrintWriter out = respones.getWriter();
			int result=userService.passwordChange(user_id,newPassword,oldPassword);
			if(result==1){
				out.write("succsess");
			}else{
				out.write("fail");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/save_addedit_user")
	public void saveAddEditUser(@ModelAttribute("beanForUser")UserBeanForUserForm bean,
			HttpServletRequest request,HttpServletResponse respones){
		AdmUserV userBean=(AdmUserV)request.getSession().getAttribute("userBean");
		System.out.println("in UserController.saveAddEditUser()");
		String action=request.getParameter("action");
		System.out.println("aciton------"+action);
		// System.out.println("getX_FIRST_NAME"+bean.getX_FIRST_NAME());
		// System.out.println("getX_LAST_NAME"+bean.getX_LAST_NAME());
		// System.out.println("LOGIN_NAME "+bean.getX_LOGIN_NAME());
		// System.out.println("user type name: "+bean.getX_USER_TYPE_NAME());
		// System.out.println("USER_TYPE_ID"+bean.getX_USER_TYPE_ID());
		// System.out.println("STATUS"+bean.getX_STATUS());
		// System.out.println("ACTIVATED"+bean.getX_ACTIVATED());
		// System.out.println("START_DATE"+bean.getX_START_DATE());
		// System.out.println("END_DATE"+bean.getX_END_DATE());
		// System.out.println("EMAIL"+bean.getX_EMAIL());
		// System.out.println("TELEPHONE_NUMBER"+bean.getX_TELEPHONE_NUMBER());
		// System.out.println("UPDATED_BY"+bean.getX_UPDATED_BY());
		// System.out.println("ACTIVATED_BY"+bean.getX_ACTIVATED_BY());
		// System.out.println("CREATED_BY"+bean.getX_CREATED_BY());
		// System.out.println("CREATED_ON"+bean.getX_CREATED_ON());
		// System.out.println("PASSWORD"+bean.getX_PASSWORD());
		// System.out.println("WAREHOUSE_ID"+bean.getX_WAREHOUSE_ID());
		int insertUpdateUserFlag = 0;
		try {
			if(action.equals("add")){
				insertUpdateUserFlag = userService.saveUserAddEdit(bean, action, userBean);
			}else{
				String userId=request.getParameter("userId");
				System.out.println("edit record of user"+userId);
				bean.setX_USER_ID(userId);
				insertUpdateUserFlag = userService.saveUserAddEdit(bean, action, userBean);
			}
			System.out.println("\ninsertUpdateUserFlag " + insertUpdateUserFlag);
			PrintWriter out = respones.getWriter();
			if (insertUpdateUserFlag == 1) {
				out.write("succsess");
			}else{
				out.write("fail");
			}
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
		
	
	@RequestMapping(value = "/user_list_export")
	public ModelAndView getUserListExport(HttpServletRequest request,HttpServletResponse respones){
		System.out.println("in UserController.getUserListExport()");
		ModelAndView model=new ModelAndView("CommonExelGenerator");
		ArrayList<LabelValueBean> headerOfTableList=new ArrayList<>();
		headerOfTableList.add(new LabelValueBean("FIRST_NAME", "FIRST NAME"));
		headerOfTableList.add(new LabelValueBean("LAST_NAME", "LAST NAME"));
		headerOfTableList.add(new LabelValueBean("LOGIN_NAME", "USERNAME"));
		headerOfTableList.add(new LabelValueBean("PASSWORD", "PASSWORD"));
		headerOfTableList.add(new LabelValueBean("STATUS", "STATUS"));
		headerOfTableList.add(new LabelValueBean("WAREHOUSE_NAME", "ASSIGNED LGA"));
		headerOfTableList.add(new LabelValueBean("USER_TYPE_NAME", "USER TYPE"));
		headerOfTableList.add(new LabelValueBean("ACTIVATED_ON", "ACTIVATED ON"));
		headerOfTableList.add(new LabelValueBean("EMAIL", "EMAIL"));
		headerOfTableList.add(new LabelValueBean("TELEPHONE_NUMBER", "TELEPHONE NUMBER"));
		headerOfTableList.add(new LabelValueBean("START_DATE", "START DATE"));
		headerOfTableList.add(new LabelValueBean("END_DATE", "END DATE"));
		headerOfTableList.add(new LabelValueBean("ROLE_NAME", "ROLE NAME"));
		headerOfTableList.add(new LabelValueBean("FIRST_NAME", "FIRST NAME"));
		headerOfTableList.add(new LabelValueBean("FIRST_NAME", "FIRST NAME"));
		headerOfTableList.add(new LabelValueBean("FIRST_NAME", "FIRST NAME"));
		headerOfTableList.add(new LabelValueBean("FACILITY_FLAG", "FACILITY FLAG"));
		try{
			model.addObject("export_data",data);
			model.addObject("headerOfTable", headerOfTableList);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return model;
	}
}
