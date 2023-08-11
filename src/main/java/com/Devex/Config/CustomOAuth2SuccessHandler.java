package com.Devex.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import com.Devex.Entity.User;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@Transactional
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
    UserService userService;
	
	@Autowired
    SessionService session;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
        // Kiểm tra trạng thái "active" của tài khoản
//        if (!authentication. .getActive()) {
//            // Nếu tài khoản không active, từ chối đăng nhập
//            response.sendRedirect("/signin?error=Tài khoản bạn đã bị khoá");
//            return;
//        }
		System.out.println("Authentication name: " + authentication.getName());
		OAuth2User  oauthUser = (OAuth2User) authentication.getPrincipal();
		CustomOAuth2User oauth2User = new CustomOAuth2User(oauthUser);
		String fullname = oauth2User.getName();
		String email = oauth2User.getEmail();
		userService.processOAuthPostLogin(fullname, email);
		User user = userService.findEmail(email);
		session.set("user", user);
        
        // Lấy thông tin người dùng từ authentication
	    List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
	    updatedAuthorities.clear();
	    updatedAuthorities.add(new SimpleGrantedAuthority("CUSTOMER"));
	    
	    // Tạo lại đối tượng Authentication với vai trò mới
	    Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
	        authentication.getPrincipal(), authentication.getCredentials(), updatedAuthorities);
	    
	    // Set lại Authentication trong SecurityContextHolder
	    SecurityContextHolder.getContext().setAuthentication(newAuthentication);
	    
		// Tiến hành phân quyền
		for (GrantedAuthority authority : updatedAuthorities) {
			System.out.println(authority.getAuthority());
			if (authority.getAuthority().equals("ADMIN")) {
				// Nếu có vai trò "ADMIN", chuyển hướng đến "/admin/home"
				response.sendRedirect("/ad/home");
				return;
			} else if (authority.getAuthority().equals("MANAGER")) {
				// Nếu có vai trò "MANAGER", chuyển hướng đến "/manager/home"
				response.sendRedirect("/manager/home");
				return;
			} else if (authority.getAuthority().equals("SELLER")) {
				// Nếu có vai trò "SELLER", chuyển hướng đến "/seller/home"
				response.sendRedirect("/seller/home");
				return;
			}
		}
		// Mặc định chuyển hướng đến "/home" cho các vai trò khác
		response.sendRedirect("/home");
	}
	
}
