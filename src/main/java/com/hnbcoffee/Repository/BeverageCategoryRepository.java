package com.hnbcoffee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.hnbcoffee.Entity.BeverageCategory;


@EnableJpaRepositories
@Repository("beverageCategoryRepository")
public interface BeverageCategoryRepository extends JpaRepository<BeverageCategory, Integer> {
	
	BeverageCategory findByNameLike(String name);
}
