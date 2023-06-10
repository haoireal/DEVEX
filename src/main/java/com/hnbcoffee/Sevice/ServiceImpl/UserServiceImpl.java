package com.hnbcoffee.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Repository.UserRepository;
import com.hnbcoffee.Sevice.UserService;

@SessionScope
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;


	@Override
	public User findByEmailLike(String email) {
		return userRepository.findByEmailLike(email);
	}

	@Override
	public User findByFullname(String fullname) {
		return userRepository.findByFullname(fullname);
	}

	@Override
	public User save(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public List<User> saveAll(List<User> entities) {
		return (List<User>) userRepository.saveAll(entities);
	}

	@Override
	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return userRepository.existsById(id);
	}

	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public List<User> findAllById(List<Integer> ids) {
		return (List<User>) userRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return userRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Override
	public void deleteAllById(List<Integer> ids) {
		userRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAll(List<User> entities) {
		userRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Override
	public User checkLogin(String email, String pass) {
		return userRepository.checkLogin(email, pass);
	}


	
	
	
	

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userRepository.findByUsername(username);
//		if (user == null) {
//			throw new UsernameNotFoundException("Username not found for: " + username);
//		}
//
//		return new UserDetailsImpl(user);
//	}

}
