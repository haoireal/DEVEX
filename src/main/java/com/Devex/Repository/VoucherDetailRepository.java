package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.VoucherDetails;

@EnableJpaRepositories
@Repository("voucherDetailRepository")
public interface VoucherDetailRepository extends JpaRepository<VoucherDetails, Integer>{
	@Query("Select v From VoucherDetails v Where v.customerVoucherDetails.username Like :username")
	List<VoucherDetails> findAllByUsername(@Param("username") String username);
	
	

}
