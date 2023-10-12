package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.VoucherProduct;

@EnableJpaRepositories
@Repository("voucherProductRepository")
public interface VoucherProductRepository extends JpaRepository<VoucherProduct, Integer>{

}
