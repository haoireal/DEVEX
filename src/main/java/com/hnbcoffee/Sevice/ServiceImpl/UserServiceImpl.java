package com.hnbcoffee.Sevice.ServiceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Repository.UserRepository;
import com.hnbcoffee.Sevice.UserService;

@SessionScope
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Username not found for: " + username );
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		return null;
	}

	
}