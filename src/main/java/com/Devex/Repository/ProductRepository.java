package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Product;

@EnableJpaRepositories
@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, String>{

}
