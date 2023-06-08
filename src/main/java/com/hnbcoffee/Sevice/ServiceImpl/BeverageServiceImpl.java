package com.hnbcoffee.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.hnbcoffee.Entity.Beverage;
import com.hnbcoffee.Repository.BeverageRepository;
import com.hnbcoffee.Sevice.BeverageService;

@SessionScope
@Service("beverageService")
public class BeverageServiceImpl implements BeverageService {
	@Autowired
	BeverageRepository beverageRepository;

	@Override
	public  Beverage save(Beverage entity) {
		return beverageRepository.save(entity);
	}

	@Override
	public List<Beverage> saveAll(List<Beverage> entities) {
		return beverageRepository.saveAll(entities);
	}


	@Override
	public List<Beverage> findAll(Sort sort) {
		return beverageRepository.findAll(sort);
	}

	@Override
	public Page<Beverage> findAll(Pageable pageable) {
		return beverageRepository.findAll(pageable);
	}

	@Override
	public List<Beverage> findAll() {
		return beverageRepository.findAll();
	}

	@Override
	public Page<Beverage> findAll(Example<Beverage> example, Pageable pageable) {
		return beverageRepository.findAll(example, pageable);
	}

	@Override
	public Optional<Beverage> findById(Integer id) {
		return beverageRepository.findById(id);
	}


	@Override
	public Beverage getOne(Integer id) {
		return beverageRepository.getOne(id);
	}

	@Override
	public long count() {
		return beverageRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		beverageRepository.deleteById(id);
	}

	@Override
	public Beverage getById(Integer id) {
		return beverageRepository.getById(id);
	}

	@Override
	public void delete(Beverage entity) {
		beverageRepository.delete(entity);
	}


	@Override
	public void deleteAll() {
		beverageRepository.deleteAll();
	}

	@Override
	public List<Beverage> findByType(String type) {
		return beverageRepository.findByType(type);
	}

	@Override
	public long countByType(String type) {
		return beverageRepository.countByType(type);
	}

	@Override
	public List<Beverage> findByKeywordName(String keywords) {
		return beverageRepository.findByKeywordName(keywords);
	}

	@Override
	public long countByKeywordName(String keywords) {
		return beverageRepository.countByKeywordName(keywords);
	}
	
	
	
	
	
	
	
}
