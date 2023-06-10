package com.hnbcoffee.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hnbcoffee.Entity.User;


@EnableJpaRepositories
@Repository("userRepository")
public interface UserRepository  extends CrudRepository<User, Integer>{
	
	@Query("Select o FROM User o WHERE o.email = :email")
	User findByEmailLike(@Param("email") String email);
	
	public User findByFullname(String fullname);
	
	@Query("Select o FROM User o WHERE o.email = :email AND o.password = :password")
	User checkLogin(@Param("email") String email, @Param("password") String pass);
	
}
