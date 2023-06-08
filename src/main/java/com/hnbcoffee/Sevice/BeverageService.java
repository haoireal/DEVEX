package com.hnbcoffee.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.hnbcoffee.Entity.Beverage;

public interface BeverageService  {

	void deleteAll();

	void delete(Beverage entity);

	Beverage getById(Integer id);

	void deleteById(Integer id);

	long count();

	Beverage getOne(Integer id);

	Optional<Beverage> findById(Integer id);

	Page<Beverage> findAll(Example<Beverage> example, Pageable pageable);

	List<Beverage> findAll();

	Page<Beverage> findAll(Pageable pageable);

	List<Beverage> findAll(Sort sort);

	List<Beverage> saveAll(List<Beverage> entities);

	Beverage save(Beverage entity);


	List<Beverage> findByType(String type);

	long countByType(String type);

	List<Beverage> findByKeywordName(String keywords);

	long countByKeywordName(String keywords);
	
}
