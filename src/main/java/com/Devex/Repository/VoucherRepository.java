package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Voucher;

@EnableJpaRepositories
@Repository("voucherRepository")
public interface VoucherRepository extends JpaRepository<Voucher, Integer>{

}
