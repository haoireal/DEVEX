package com.Devex.Sevice.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Cart;
import com.Devex.Entity.CartDetail;
import com.Devex.Entity.ProductRequest;
import com.Devex.Repository.ProductRepository;
import com.Devex.Repository.ProductRequestRespository;
import com.Devex.Sevice.ProductRequestService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("productRequestService")
public class ProductRequestServiceImpl implements ProductRequestService{
	
	@Autowired
	private ProductRequestRespository productRequestRespository;

	@Override
	public ProductRequest save(ProductRequest entity) {
		return productRequestRespository.save(entity);
	}

	@Override
	public List<ProductRequest> findAll(Sort sort) {
		return productRequestRespository.findAll(sort);
	}

	@Override
	public Page<ProductRequest> findAll(Pageable pageable) {
		return productRequestRespository.findAll(pageable);
	}

	@Override
	public List<ProductRequest> findAll() {
		return productRequestRespository.findAll();
	}

	@Override
	public List<ProductRequest> findAllById(Iterable<Integer> ids) {
		return productRequestRespository.findAllById(ids);
	}

	@Override
	public Optional<ProductRequest> findById(Integer id) {
		return productRequestRespository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return productRequestRespository.existsById(id);
	}

	@Override
	public long count() {
		return productRequestRespository.count();
	}

	@Override
	public void deleteById(Integer id) {
		productRequestRespository.deleteById(id);
	}

	@Override
	public ProductRequest getById(Integer id) {
		return productRequestRespository.getById(id);
	}

	@Override
	public void delete(ProductRequest entity) {
		productRequestRespository.delete(entity);
	}

	@Override
	public void deleteAll() {
		productRequestRespository.deleteAll();
	}

	@Override
	public List<ProductRequest> getAllProductRequestDecreaseByCreatedDay() {
		return productRequestRespository.getAllProductRequestDecreaseByCreatedDay();
	}

	@Override
	public ProductRequest findProductRequestById(int id) {
		return productRequestRespository.findProductRequestById(id);
	}

	@Override
	public ProductRequest findProductRequestByProductId(String id) {
		return productRequestRespository.findProductRequestByProductId(id);
	}

	@Transactional
	@Override
	public void insertProductRequest(Date createdDay, String productId, int status, String content) {
		productRequestRespository.insertProductRequest(createdDay, productId, status, content);
	}

	@Override
	public List<ProductRequest> getAllProductRequestFalseDecreaseByCreatedDay() {
		return productRequestRespository.getAllProductRequestFalseDecreaseByCreatedDay();
	}

	@Override
	public List<ProductRequest> getAllProductRequestTrueDecreaseByCreatedDay() {
		return productRequestRespository.getAllProductRequestTrueDecreaseByCreatedDay();
	}

}
