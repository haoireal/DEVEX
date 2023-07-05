package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.ProductVariant;

public interface ProductVariantService {

	ProductVariant getById(Integer id);

	void deleteAll();

	void delete(ProductVariant entity);

	void deleteById(Integer id);

	long count();

	Optional<ProductVariant> findById(Integer id);

	List<ProductVariant> findAllById(Iterable<Integer> ids);

	List<ProductVariant> findAll();

	Page<ProductVariant> findAll(Pageable pageable);

	List<ProductVariant> findAll(Sort sort);

	Optional<ProductVariant> findOne(Example<ProductVariant> example);

	List<ProductVariant> saveAll(List<ProductVariant> entities);

	ProductVariant save(ProductVariant entity);

}
