package com.hnbcoffee.Sevice;

import java.util.List;
import java.util.Optional;

import com.hnbcoffee.Entity.User;


public interface UserService// extends UserDetailsService
{

//	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	void deleteAll();

	void deleteAll(List<User> entities);

	void deleteAllById(List<Integer> ids);

	void delete(User entity);

	void deleteById(Integer id);

	long count();

	List<User> findAllById(List<Integer> ids);

	List<User> findAll();

	boolean existsById(Integer id);

	Optional<User> findById(Integer id);

	List<User> saveAll(List<User> entities);

	User save(User entity);

	User findByFullname(String fullname);

	User findByEmailLike(String email);


	User checkLogin(String email, String pass);

	
}