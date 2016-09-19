package com.chai.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
	public void login(@RequestParam("userName") String userName, @RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse respones,
			RedirectAttributes redirectAttributes) throws IOException {
		Logger logger=Logger.getLogger(LoginController.class);
		logger.info("ok");
		System.out.println("in LoginController action login");
		System.out.println("login Name:   " + userName);
		System.out.println("password:   " + password);
		PrintWriter out = respones.getWriter();
		  ArrayList<Object> userdataList =
				UserService.validateUserLogin(userName, password);
		try {
			if (userdataList == null) {
				out.write("No internet Connectivity or Database Server not responding.");
			} else {
				if (userdataList.size() == 2) {
					AdmUserV userdata = (AdmUserV) userdataList.get(0);
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(1200);// 20 minute
					session.setAttribute("userBean", userdata);
					session.setAttribute("PREVIOUS_WEEK_OF_YEAR", userdataList.get(1));
					session.setAttribute("loadCount", 1);
					Date login_time = new Date();
					SimpleDateFormat ft = new SimpleDateFormat("E dd MM yyyy, hh:mm:ss a ");
					session.setAttribute("login_time", ft.format(login_time));
					out.write("succsess");
				} else {
					out.write("Wrong UserName Or Password");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			out.flush();
		}
		 
    }	
}
