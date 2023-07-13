package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.CategoryDetails;

@EnableJpaRepositories
@Repository("categoryDetailRepository")
public interface CategoryDetailRepository extends JpaRepository<CategoryDetails, Integer>{

}
