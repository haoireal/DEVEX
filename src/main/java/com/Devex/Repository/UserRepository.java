package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.User;

@EnableJpaRepositories
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String>{
	@Query("Select o FROM User o WHERE o.username = :username AND o.password = :password")
	User checkLogin(@Param("username") String username, @Param("password") String pass);
}
