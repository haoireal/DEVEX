package com.hnbcoffee.Repository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hnbcoffee.Entity.User;
@EnableJpaRepositories
@Repository("userRepository")
public interface UserRepository  extends CrudRepository<User, Integer>{
	public User findByUsername(String username);
	public User findByEmail(String email);
	public User findByFullname(String fullname);
}
