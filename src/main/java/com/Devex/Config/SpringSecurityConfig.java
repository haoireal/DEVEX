//package com.hnbcoffee.Config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig {
//
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	@Bean
//	AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setUserDetailsService(userDetailsService);
//		provider.setPasswordEncoder(new BCryptPasswordEncoder());
//		return provider;
//	}
//
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http.authorizeHttpRequests(auth -> {
//			auth.requestMatchers("/").permitAll();
//			auth.requestMatchers("/home").hasAuthority("CUSTOMER");
//			auth.requestMatchers("/admin").hasAuthority("ADMIN");
//			auth.anyRequest().authenticated();
//		}).build();
//	}
//
////	@Bean
////	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
////		http.authorizeHttpRequests()
////				.mvcMatchers("/").permitAll()
////				.mvcMatchers ("/home").hasAuthority("USER")
////				.mvcMatchers("/admin").hasAuthority("ADMIN")
////				.anyRequest().authenticated()
////				.and().httpBasic();
////				return http.build();
////	}
////	@Bean
////	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
////		return http
////				.authorizeHttpRequests()
////				.antMatchers("/")
////				.permitAll()
////				.antMatchers("/home")
////				.hasAuthority("USER")
////				.antMatchers("/admin")
////				.hasAuthority("ADMIN")
////				.anyRequest()
////				.authenticated()
////				.and()
////				.httpBasic();
////				.build();
////	}
//
//}
