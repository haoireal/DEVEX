package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.ImageProduct;

@EnableJpaRepositories
@Repository("imageProductRepository")
public interface ImageProductRepository extends JpaRepository<ImageProduct, String>{

	@Query("SELECT p FROM ImageProduct p WHERE p.product.id LIKE %?1%")
	List<ImageProduct> findAllImageProductByProductId(String id);
	
}
