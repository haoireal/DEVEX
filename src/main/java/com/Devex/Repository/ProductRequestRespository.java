package com.Devex.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.ProductRequest;

@EnableJpaRepositories
@Repository("productRequestRespository")
public interface ProductRequestRespository extends JpaRepository<ProductRequest, Integer>{

	@Query("SELECT pr FROM ProductRequest pr ORDER BY pr.createdDay DESC")
	List<ProductRequest> getAllProductRequestDecreaseByCreatedDay();
	
	@Query("SELECT pr FROM ProductRequest pr WHERE pr.id = ?1")
	ProductRequest findProductRequestById(int id);
	
	@Query("SELECT pr FROM ProductRequest pr WHERE pr.product.id = ?1")
	ProductRequest findProductRequestByProductId(String id);
	
	@Modifying
	@Query(value = "INSERT INTO Product_Request (Createdday, Product_ID) VALUES (:createdDay, :productId)", nativeQuery = true)
	void insertProductRequest(@Param("createdDay") Date createdDay, @Param("productId") String productId);

}
