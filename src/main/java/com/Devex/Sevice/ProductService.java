package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Product;

public interface ProductService {

	void deleteAll();

	void delete(Product entity);

	Product getById(String id);

	void deleteById(String id);

	long count();

	List<Product> findAllById(Iterable<String> ids);

	List<Product> findAll();
	
	List<Product> findAllProperties();
	
	Page<Product> findAll(Pageable pageable);

	Optional<Product> findOne(Example<Product> example);

	List<Product> findAll(Sort sort);

	List<Product> saveAll(List<Product> entities);

	Optional<Product> findById(String id);

	Product save(Product entity);

	List<Product> findProductBySellerUsername(String sellerUsername);

}
