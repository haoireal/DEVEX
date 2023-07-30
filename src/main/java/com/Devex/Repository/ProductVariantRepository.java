package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.User;

@EnableJpaRepositories
@Repository("productVariantRepository")
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {

	@Query("SELECT p FROM ProductVariant p WHERE p.product.id LIKE %?1%")
	List<ProductVariant> findAllProductVariantByProductId(String id);	
}
