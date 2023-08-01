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

import com.Devex.Entity.ProductVariant;
import com.Devex.Repository.ProductVariantRepository;
import com.Devex.Sevice.ProductVariantService;

@SessionScope
@Service("productVariantService")
public class ProductVariantServiceImpl implements ProductVariantService{
	

	@Autowired
	ProductVariantRepository productVariantRepository;

	@Override
	public Double findPriceByColor(String id, String color) {
		return productVariantRepository.findPriceByColor(id, color);
	}

	@Override
	public Double findPriceByColorAndSize(String id, String color, String size) {
		return productVariantRepository.findPriceByColorAndSize(id, color, size);
	}

	@Override
	public ProductVariant save(ProductVariant entity) {
		return productVariantRepository.save(entity);
	}

	@Override
	public List<ProductVariant> saveAll(List<ProductVariant> entities) {
		return productVariantRepository.saveAll(entities);
	}

	@Override
	public Optional<ProductVariant> findOne(Example<ProductVariant> example) {
		return productVariantRepository.findOne(example);
	}

	@Override
	public List<ProductVariant> findAll(Sort sort) {
		return productVariantRepository.findAll(sort);
	}

	@Override
	public Page<ProductVariant> findAll(Pageable pageable) {
		return productVariantRepository.findAll(pageable);
	}

	@Override
	public List<ProductVariant> findAll() {
		return productVariantRepository.findAll();
	}

	@Override
	public List<ProductVariant> findAllById(Iterable<Integer> ids) {
		return productVariantRepository.findAllById(ids);
	}

	@Override
	public Optional<ProductVariant> findById(Integer id) {
		return productVariantRepository.findById(id);
	}

	@Override
	public long count() {
		return productVariantRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		productVariantRepository.deleteById(id);
	}

	@Override
	public void delete(ProductVariant entity) {
		productVariantRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		productVariantRepository.deleteAll();
	}
	
	@Override
	public ProductVariant getById(Integer id) {
		return productVariantRepository.getById(id);
	}

	@Override
	public List<ProductVariant> findAllProductVariantByProductId(String id) {
		return productVariantRepository.findAllProductVariantByProductId(id);
	}
	
	
}
