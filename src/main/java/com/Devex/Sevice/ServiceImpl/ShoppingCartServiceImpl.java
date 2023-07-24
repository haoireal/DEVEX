package com.Devex.Sevice.ServiceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.CartProdcut;
import com.Devex.Entity.Product;
import com.Devex.Entity.ProductVariant;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.ShoppingCartDTO;
import com.Devex.Sevice.ShoppingCartService;


@Service
@SessionScope
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartDTO cart;
    
    @Autowired
    private ProductRepository dao;

	@Override
	public CartProdcut add(String id, int SoLuong, String Size, String Color) {
		CartProdcut cartProduct = cart.getItems().get(id);
		
        if (cartProduct == null) {
            Product product = dao.findByIdProduct(id);
            if (product != null && !product.getProductVariants().isEmpty()) {
                ProductVariant variant = product.getProductVariants().get(0);
                cartProduct = new CartProdcut();
               cartProduct.setId(product.getId());
                cartProduct.setName(product.getName());
                cartProduct.setImg(product.getImageProducts().get(0).getName());
                cartProduct.setColor(variant.getColor());
                cartProduct.setSize(variant.getSize());
                cartProduct.setSoluong(SoLuong);
                cartProduct.setPrice(variant.getPriceSale());
                cartProduct.setTotal(variant.getPriceSale());
                cart.getItems().put(id, cartProduct);
            }
        } else {
            cartProduct.setSoluong(cartProduct.getSoluong() + 1);
            cartProduct.setTotal(cartProduct.getSoluong() * cartProduct.getPrice());
        }

        return cartProduct;
		
	}

	@Override
	public void remove(String id) {
		
		cart.removeItem(id);
		
	}
		
	
	@Override
	public CartProdcut update(String id, int qty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		cart.getItems().clear();
	}

	@Override
	public Collection<CartProdcut> getItems() {
		return cart.getItems().values();
	}

	@Override
	public int getCount() {
		
		return cart.getItems().values().stream().mapToInt(CartProdcut::getSoluong).sum();
	}

	@Override
	public double getAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setItems(Map<String, CartProdcut> items) {
		this.cart.setItems(items);
		
	}

   

}

