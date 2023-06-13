package com.hnbcoffee.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Sevice.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthInterceptor implements HandlerInterceptor{

	@Autowired
	SessionService session;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception{
		String uri = req.getRequestURI();
		User user = session.get("user");
		String message = "";
		if(user == null) {
			message = "Please login!";
		}else if(!user.getRole().equalsIgnoreCase("ADMIN") && uri.startsWith("/hnbcoffee/admin/")) {
			resp.sendRedirect("/hnbcoffee/404");
		}
		if(message.length() > 0) {
			session.set("security-uri", uri);
			resp.sendRedirect("/hnbcoffee/signin?message=" + message);
			return false;
		}
		return true;
	}
	
}
