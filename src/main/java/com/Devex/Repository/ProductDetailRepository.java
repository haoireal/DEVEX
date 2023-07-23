package com.Devex.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Product;

@EnableJpaRepositories
@Repository("productDetailRepository")
public interface ProductDetailRepository extends JpaRepository<Product, String>{
	
////	@Query("SELECT p   FROM Product p JOIN p.imageProducts i WHERE p.id = : 101-aligqd911-10001")
////	List<Object[]> findProductDetailsById();
//	
//	
//	 Optional<Product> findById(String id);
	
}
