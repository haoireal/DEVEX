package com.Devex.Sevice;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.CartDetail;
import com.Devex.Entity.ProductRequest;

public interface ProductRequestService {
	
	void deleteAll();

	void delete(ProductRequest entity);

	ProductRequest getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<ProductRequest> findById(Integer id);

	List<ProductRequest> findAll();

	Page<ProductRequest> findAll(Pageable pageable);

	List<ProductRequest> findAll(Sort sort);

	ProductRequest save(ProductRequest entity);

	List<ProductRequest> findAllById(Iterable<Integer> ids);

	boolean existsById(Integer id);

	List<ProductRequest> getAllProductRequestDecreaseByCreatedDay();

	ProductRequest findProductRequestById(int id);

	ProductRequest findProductRequestByProductId(String id);

	void insertProductRequest(Date createdDay, String productId, int status, String content);

	List<ProductRequest> getAllProductRequestTrueDecreaseByCreatedDay();

	List<ProductRequest> getAllProductRequestFalseDecreaseByCreatedDay();

}
