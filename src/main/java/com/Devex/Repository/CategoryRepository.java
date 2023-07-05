package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Category;

@EnableJpaRepositories
@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
