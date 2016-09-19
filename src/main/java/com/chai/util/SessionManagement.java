package com.chai.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionManagement extends HandlerInterceptorAdapter {
	// private static Logger logger = Logger.getLogger(name)

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setContentType("text/html; charset=UTF-8");// For Special
															// characters
		HttpSession session = request.getSession(false);
		String uri = request.getRequestURI();

		// logger.info("request uri {}", uri);
		if (!uri.endsWith("loginPage") && !uri.endsWith("logOutPage")) {
			if (session != null) {
				return true;
			} else {
				if (request.getSession().getAttribute("userBean") == null) {
					// logger.info("session expired");
					// logger.info("resquest header {}",
					// request.getHeader("X-Requested-With"));
					if (request.getHeader("X-Requested-With") != null
							&& request.getHeader("X-Requested-With").equalsIgnoreCase("ajax")) {
						// Ajax requests
						// logger.info("inside ajax request session expired");
						response.setStatus(HttpServletResponse.SC_FORBIDDEN);
						return false;
					} else {
						// Non Ajax requests
						// logger.info("inside non ajax request session
						// expired");
						response.sendRedirect("logOutPage");
						return false;
					}
				}
			}
		}
		return true;
	}
}