package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.CategoryDetails;

@EnableJpaRepositories
@Repository("categoryDetailRepository")
public interface CategoryDetailRepository extends JpaRepository<CategoryDetails, Integer>{

	@Query("SELECT cad FROM CategoryDetails cad WHERE cad.category.id = ?1")
	List<CategoryDetails> findAllCategoryDetailsById(int id);
	
	@Query("SELECT cad FROM CategoryDetails cad WHERE cad.id = ?1")
	CategoryDetails findCategoryDetailsById(int id);
	
}
