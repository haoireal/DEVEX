package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.CartDetail;
import com.Devex.Sevice.CartDetailDTo;

@EnableJpaRepositories
@Repository("cartDetailRepository")
public interface CartDetailRespository extends JpaRepository<CartDetail, Integer>{
	@Query("SELECT new com.Devex.Sevice.CartDetailDTo(cd.id, cd.productCart.price, cd.cart.id, cd.quantity, cd.productCart.product.name,"
			+ "cd.productCart.color ,cd.productCart.size,cd.productCart.product.sellerProduct.shopName) FROM CartDetail cd")
    List<CartDetailDTo> findAllCartDTO();
	


}
