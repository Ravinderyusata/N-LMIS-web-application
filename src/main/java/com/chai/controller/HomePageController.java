package com.chai.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chai.model.views.AdmUserV;

@Controller
public class HomePageController {
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	public ModelAndView showHomePage(HttpServletRequest request, HttpServletResponse respones) {
		ModelAndView modelAndView= new ModelAndView("HomePage");
		try {
			HttpSession session=request.getSession();
			AdmUserV userBean=(AdmUserV)session.getAttribute("userBean");
			String login_time=(String)session.getAttribute("login_time");
			modelAndView.addObject("userdata",userBean);
			modelAndView.addObject("login_time",login_time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				respones.sendRedirect("loginPage");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return modelAndView;
	}
}
