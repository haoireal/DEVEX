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

import com.Devex.Entity.CategoryDetails;
import com.Devex.Repository.CategoryDetailRepository;
import com.Devex.Sevice.CategoryDetailService;

@SessionScope
@Service("categoryDetailService")
public class CategoryDetailServiceImpl implements CategoryDetailService{
	@Autowired
	CategoryDetailRepository categoryDetailRepository;

	@Override
	public CategoryDetails save(CategoryDetails entity) {
		return categoryDetailRepository.save(entity);
	}

	@Override
	public  List<CategoryDetails> saveAll(List<CategoryDetails> entities) {
		return categoryDetailRepository.saveAll(entities);
	}

	@Override
	public Optional<CategoryDetails> findOne(Example<CategoryDetails> example) {
		return categoryDetailRepository.findOne(example);
	}

	@Override
	public List<CategoryDetails> findAll(Sort sort) {
		return categoryDetailRepository.findAll(sort);
	}

	@Override
	public Page<CategoryDetails> findAll(Pageable pageable) {
		return categoryDetailRepository.findAll(pageable);
	}

	@Override
	public List<CategoryDetails> findAll() {
		return categoryDetailRepository.findAll();
	}

	@Override
	public List<CategoryDetails> findAllById(Iterable<Integer> ids) {
		return categoryDetailRepository.findAllById(ids);
	}

	@Override
	public Optional<CategoryDetails> findById(Integer id) {
		return categoryDetailRepository.findById(id);
	}

	@Override
	public long count() {
		return categoryDetailRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		categoryDetailRepository.deleteById(id);
	}

	@Override
	public CategoryDetails getById(Integer id) {
		return categoryDetailRepository.getById(id);
	}

	@Override
	public void delete(CategoryDetails entity) {
		categoryDetailRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		categoryDetailRepository.deleteAll();
	}

	@Override
	public List<CategoryDetails> findAllCategoryDetailsById(int id) {
		return categoryDetailRepository.findAllCategoryDetailsById(id);
	}

	@Override
	public CategoryDetails findCategoryDetailsById(int id) {
		return categoryDetailRepository.findCategoryDetailsById(id);
	}

	@Override
	public List<CategoryDetails> findAllCategoryDetailsBySellerUsername(String username) {
		return categoryDetailRepository.findAllCategoryDetailsBySellerUsername(username);
	}
	
	
}
