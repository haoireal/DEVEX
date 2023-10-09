package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.stereotype.Repository;

import com.Devex.Entity.History;



@EnableJpaRepositories
@Repository("")
public interface HistoryResprository extends JpaRepository<History, Integer> {
	
	@Query("SELECT p FROM History p where p.user.username = ?1 ")
	List<History> findByIdUser(String user);
	
}
