package com.hnbcoffee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hnbcoffee.Entity.BeverageCategory;


@EnableJpaRepositories
@Repository("beverageCategoryRepository")
public interface BeverageCategoryRepository extends JpaRepository<BeverageCategory, Integer> {
	
	@Query("SELECT o.id FROM BeverageCategory o WHERE o.name LIKE :name")
	BeverageCategory findByNameLike(@Param("name") String name);
}
