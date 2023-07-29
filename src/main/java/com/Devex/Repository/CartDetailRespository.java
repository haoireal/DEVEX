package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.CartDetail;

@EnableJpaRepositories
@Repository("cartDetailRepository")
public interface CartDetailRespository extends JpaRepository<CartDetail, Integer>{

}
