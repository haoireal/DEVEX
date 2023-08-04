package com.Devex.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.ProductVariant;

@EnableJpaRepositories
@Repository("productVariantRepository")
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {

	@Query("SELECT p FROM ProductVariant p WHERE p.product.id LIKE %?1%")
	List<ProductVariant> findAllProductVariantByProductId(String id);

	@Modifying
	@Query("UPDATE ProductVariant pv SET pv.quantity = :quantity, pv.price = :price, pv.priceSale = :priceSale, pv.size = :size, pv.color = :color WHERE pv.id = :id")
	void updateProductVariant(@Param("id") Integer id, @Param("quantity") Integer quantity,
			@Param("price") Double price, @Param("priceSale") Double priceSale, @Param("size") String size,
			@Param("color") String color);

	@Modifying
	@Query(value = "INSERT INTO Product_Variant (quantity, price, priceSale, size, color, Product_ID) VALUES (:quantity, :price, :priceSale, :size, :color, :productId)", nativeQuery = true)
	void addProductVariant(@Param("quantity") Integer quantity, @Param("price") Double price,
			@Param("priceSale") Double priceSale, @Param("size") String size, @Param("color") String color,
			@Param("productId") String productId);

	@Modifying
	@Query(value = "DELETE FROM Product_Variant WHERE Product_ID = :productId", nativeQuery = true)
	void deleteProductVariantByProductId(@Param("productId") String productId);

	
}
