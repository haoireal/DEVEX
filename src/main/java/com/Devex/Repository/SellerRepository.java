package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Seller;

@EnableJpaRepositories
@Repository("sellerRepository")
public interface SellerRepository extends JpaRepository<Seller, String>{

	Seller findFirstByUsername(String username);
	
}
