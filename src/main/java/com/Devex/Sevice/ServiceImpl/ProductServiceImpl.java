package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Product;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.ProductService;

@SessionScope
@Service("productService")
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductRepository productRepository;

	@Override
	public Product save(Product entity) {
		entity.setId("1");
		return productRepository.save(entity);
	}

	@Override
	public Optional<Product> findById(String id) {
		return productRepository.findById(id);
	}

	@Override
	public List<Product> saveAll(List<Product> entities) {
		return productRepository.saveAll(entities);
	}

	@Override
	public List<Product> findAll(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	public Optional<Product> findOne(Example<Product> example) {
		return productRepository.findOne(example);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> findAllById(Iterable<String> ids) {
		return productRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public void deleteById(String id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product getById(String id) {
		return productRepository.getById(id);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

	@Override
	public List<Product> findAllProperties() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
