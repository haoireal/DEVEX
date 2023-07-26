package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Product;

@EnableJpaRepositories
@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, String> {

	@Query("SELECT p FROM Product p WHERE p.id = ?1")
	Product findByIdProduct(String id);

	@Query("SELECT DISTINCT p FROM Product p " + "JOIN FETCH p.sellerProduct s "
			+ "WHERE s.username like ?1 OR s.shopName Like ?1")
	List<Product> findProductBySellerUsername(String sellerUsername);

	@Query("SELECT p FROM Product p WHERE p.categoryDetails.category.id = :categoryId")
	List<Product> findProductsByCategoryId(@Param("categoryId") int categoryId);

	@Query(value = "EXEC FindProductsByKeyword :keywords", nativeQuery = true)
	List<Product> findByKeywordName(@Param("keywords") String keywords);

	@Query(value = "EXEC FillerProductBy :keywords, :sortby, :sorthow", nativeQuery = true)
	List<Product> fillerProductBy(@Param("keywords") String keywords, @Param("sortby") String sortby, @Param("sorthow") String sorthow);
	
	@Query("SELECT COUNT(o) FROM Product o WHERE o.name LIKE :keywords")
	long countByKeywordName(@Param("keywords") String keywords);

}
