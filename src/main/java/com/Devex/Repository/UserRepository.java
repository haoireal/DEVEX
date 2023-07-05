package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.User;

@EnableJpaRepositories
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String>{

}
