package com.chai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chai.hibernartesessionfactory.HibernateSessionFactoryClass;

@Controller
public class LogOutController {
	@RequestMapping(value = "/logOutPage", method = RequestMethod.GET)
	public String getLoginPage(@RequestParam(value = "logOutFlag", required = false) String logOutFlag,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("in LogOutController getLoginPage() Controller");
		if (logOutFlag == null || logOutFlag.equals("logOut")) {
			SessionFactory sf = HibernateSessionFactoryClass.getSessionAnnotationFactory();
			sf.getCurrentSession().close();
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "0");
			request.getSession().invalidate();
			System.out.println("session invalidated");
		}
		return "redirect:loginPage";
	}
}
