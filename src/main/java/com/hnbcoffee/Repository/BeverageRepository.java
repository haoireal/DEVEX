package com.hnbcoffee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hnbcoffee.Entity.Beverage;

@EnableJpaRepositories
@Repository("beverageRepository")
public interface BeverageRepository extends JpaRepository<Beverage, Integer>{
	@Query("SELECT o FROM Beverage o WHERE o.category.name LIKE :type")
	List<Beverage> findByType(@Param("type") String type);
	
	@Query("SELECT COUNT(o) FROM Beverage o WHERE o.category.name LIKE :type")
	long countByType(@Param("type") String type);
	
	@Query("SELECT o FROM Beverage o WHERE o.name LIKE :keywords")
	List<Beverage> findByKeywordName(@Param("keywords") String keywords);
	
	@Query("SELECT COUNT(o) FROM Beverage o WHERE o.name LIKE :keywords")
	long countByKeywordName(@Param("keywords") String keywords);
	
	
}
