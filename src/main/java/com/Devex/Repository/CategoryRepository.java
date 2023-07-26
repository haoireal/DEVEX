package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Category;

@EnableJpaRepositories
@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("SELECT ca FROM Category ca " +
		       "JOIN ca.categoryDetails cad " +
		       "JOIN cad.products p " +
		       "WHERE p.id = :productId")
		Category findByProductId(@Param("productId") String productId);

	
}
