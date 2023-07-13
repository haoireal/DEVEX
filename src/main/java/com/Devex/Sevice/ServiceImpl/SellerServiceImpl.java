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

import com.Devex.Entity.Seller;
import com.Devex.Repository.SellerRepository;
import com.Devex.Sevice.SellerService;

@SessionScope
@Service("sellerService")
public class SellerServiceImpl implements SellerService{
	@Autowired
	SellerRepository sellerRepository;

	@Override
	public Seller save(Seller entity) {
		return sellerRepository.save(entity);
	}

	@Override
	public List<Seller> saveAll(List<Seller> entities) {
		return sellerRepository.saveAll(entities);
	}

	@Override
	public Optional<Seller> findOne(Example<Seller> example) {
		return sellerRepository.findOne(example);
	}

	@Override
	public List<Seller> findAll(Sort sort) {
		return sellerRepository.findAll(sort);
	}

	@Override
	public Page<Seller> findAll(Pageable pageable) {
		return sellerRepository.findAll(pageable);
	}

	@Override
	public List<Seller> findAll() {
		return sellerRepository.findAll();
	}

	@Override
	public List<Seller> findAllById(Iterable<String> ids) {
		return sellerRepository.findAllById(ids);
	}

	@Override
	public Optional<Seller> findById(String id) {
		return sellerRepository.findById(id);
	}

	@Override
	public long count() {
		return sellerRepository.count();
	}

	@Override
	public void deleteById(String id) {
		sellerRepository.deleteById(id);
	}

	@Override
	public Seller getById(String id) {
		return sellerRepository.getById(id);
	}

	@Override
	public void delete(Seller entity) {
		sellerRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		sellerRepository.deleteAll();
	}
	
	
	
}
