package com.Devex.Sevice.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Customer;
import com.Devex.Entity.Role;
import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;
import com.Devex.Repository.CustomerRepository;
import com.Devex.Repository.RoleRepository;
import com.Devex.Repository.UserRepository;
import com.Devex.Repository.UserRoleRespository;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.RoleService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserService;


@SessionScope
@Service("userService")
@Transactional
public class UserServiceImpl implements  UserService{
	@Autowired
	UserRepository userRepository;

	@Override
	public User save(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public List<User> saveAll(List<User> entities) {
		return userRepository.saveAll(entities);
	}

	@Override
	public Optional<User> findOne(Example<User> example) {
		return userRepository.findOne(example);
	}

	@Override
	public List<User> findAll(Sort sort) {
		return userRepository.findAll(sort);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public List<User> findAllById(Iterable<String> ids) {
		return userRepository.findAllById(ids);
	}

	@Override
	public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
		return userRepository.findAll(example, pageable);
	}

	@Override
	public Optional<User> findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public long count() {
		return userRepository.count();
	}

	@Override
	public void deleteById(String id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getById(String id) {
		return userRepository.getById(id);
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Override
	public User checkLogin(String username, String pass) {
		return userRepository.checkLogin(username, pass);
	}

	@Override
	public User findEmail(String email) {
		return userRepository.findEmail(email);
	}

	@Override
	public User findPhone(String phone) {
		return userRepository.findPhone(phone);
	}
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRoleRespository userRoleRepository;
	
	@Override
	public void processOAuthPostLogin(String fullname, String email) {
		User existUser = userRepository.findEmail(email);
		if (existUser == null) {
			User user = new Customer();
			int atIndex = email.indexOf('@'); // Tìm vị trí của ký tự @ trong chuỗi email
			String username = atIndex != -1 ? email.substring(0, atIndex) : email;
			user.setUsername(username);
			user.setFullname(fullname);
			user.setPhone(null);
			user.setEmail(email);
			user.setPassword(passwordEncoder.encode("123"));
			user.setCreateDay(new Date());
			user.setGender("Other");
			user.setActive(true);
			user.setAvatar(null);
			userRepository.save(user);
			Customer customer = new Customer();
			customer.setUsername(username);
			customer.setFullname(fullname);
			customer.setPhone(null);
			customer.setEmail(email);
			customer.setPassword(passwordEncoder.encode("123"));
			customer.setCreateDay(new Date());
			customer.setGender("Other");
			customer.setActive(true);
			customer.setAvatar(null);
			customer.setAddress(null);
			customer.setPhoneAddress(null);
			customerRepository.save(customer);
			Role role = roleRepository.findById("CUSTOMER").orElse(null);
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);
			userRoleRepository.save(userRole);
			
			System.out.println("Created new user: " + username);
		}
		
	}


	
	

}
