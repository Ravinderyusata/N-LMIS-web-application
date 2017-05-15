package com.chai.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chai.model.UserBean;
import com.chai.model.views.AdmUserV;
import com.chai.services.UserService;

@Controller
	public class LoginController {
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public ModelAndView getForm(@ModelAttribute("userBean") UserBean userBean, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("in LoginController action loginPage");
		return new ModelAndView("LoginPage");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("userBean") UserBean userBean,
			HttpServletRequest request, HttpServletResponse respones,
			RedirectAttributes redirectAttributes) throws IOException {
		Logger logger=Logger.getLogger(LoginController.class);
		
		logger.info("ok");
		String page="";
		System.out.println("in LoginController action login");
		System.out.println("login Name:   " + userBean.getX_LOGIN_NAME());
		System.out.println("password:   " + userBean.getX_PASSWORD());
		try {
			 ArrayList<Object> userdataList =
						UserService.validateUserLogin(userBean.getX_LOGIN_NAME(), userBean.getX_PASSWORD());
			if (userdataList == null) {
				System.out.println("login list is null");
				redirectAttributes.addFlashAttribute("message", "No internet Connectivity or Database Server not responding.");
				page="redirect:loginPage";
			} else {
				if (userdataList.size() == 2) {
					System.out.println("userdataList.size() == 2");
					AdmUserV userdata = (AdmUserV) userdataList.get(0);
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(1200);// 20 minute
					session.setAttribute("userBean", userdata);
					session.setAttribute("PREVIOUS_WEEK_OF_YEAR", userdataList.get(1));
					session.setAttribute("loadCount", 1);
					Date login_time = new Date();
					SimpleDateFormat ft = new SimpleDateFormat("E dd MM yyyy, hh:mm:ss a ");
					session.setAttribute("login_time", ft.format(login_time));
					page="redirect:homepage";
				} else {
					System.out.println("userdataList.size() != 2");
					page="redirect:loginPage";
					redirectAttributes.addFlashAttribute("message", "Wrong UserName Or Password");
				}
			}
		} catch (org.hibernate.exception.JDBCConnectionException | NullPointerException e) {
			page="redirect:loginPage";			
			e.printStackTrace();
			return page;
		}
		return page;
    }	
}
