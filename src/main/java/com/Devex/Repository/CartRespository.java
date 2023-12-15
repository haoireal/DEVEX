package com.Devex.Repository;

import com.Devex.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Cart;

@EnableJpaRepositories
@Repository("cartRepository")
public interface CartRespository extends JpaRepository<Cart, Integer>{
    @Query("SELECT c FROM Cart c where c.person.username = :username")
    Cart getCartById(@Param("username") String username);
}
