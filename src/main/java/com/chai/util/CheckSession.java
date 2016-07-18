package com.chai.util;

import javax.servlet.http.HttpServletRequest;

public class CheckSession {
	public static String checkSession(String userBean, HttpServletRequest request) {
		if (request.getSession().getAttribute(userBean) == null) {
			return "sessionOut";
		} else {
			return "sessionIn";
		}
	}
}
