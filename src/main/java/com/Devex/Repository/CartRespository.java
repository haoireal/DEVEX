package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Cart;

@EnableJpaRepositories
@Repository("cartRepository")
public interface CartRespository extends JpaRepository<Cart, Integer>{

}
