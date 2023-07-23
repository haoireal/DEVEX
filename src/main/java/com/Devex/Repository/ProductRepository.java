package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Product;

@EnableJpaRepositories
@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, String>{
	
	
	@Query("SELECT p FROM Product p WHERE p.id = ?1")
	Product findByIdProduct(String id);

	@Query("SELECT DISTINCT p FROM Product p " +
	           "JOIN FETCH p.sellerProduct s " +
	           "WHERE s.username = ?1")
	    List<Product> findProductBySellerUsername(String sellerUsername);
	

	    @Query("SELECT p FROM Product p WHERE p.categoryDetails.category.id = :categoryId")
	    List<Product> findProductsByCategoryId(@Param("categoryId") int categoryId);
	    
	    @Query("SELECT o FROM Product o WHERE o.name LIKE :keywords")
		List<Product> findByKeywordName(@Param("keywords") String keywords);
		
		@Query("SELECT COUNT(o) FROM Product o WHERE o.name LIKE :keywords")
		long countByKeywordName(@Param("keywords") String keywords);

}
