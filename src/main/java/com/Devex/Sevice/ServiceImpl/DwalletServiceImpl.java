package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Dwallet;
import com.Devex.Repository.DwalletRepository;
import com.Devex.Sevice.DwalletService;

@SessionScope
@Service("dwalletServiceImpl")
public class DwalletServiceImpl implements DwalletService{

	@Autowired
	private DwalletRepository dwalletRepository;

	@Override
	public Dwallet save(Dwallet entity) {
		return dwalletRepository.save(entity);
	}

	@Override
	public List<Dwallet> findAll(Sort sort) {
		return dwalletRepository.findAll(sort);
	}

	@Override
	public Page<Dwallet> findAll(Pageable pageable) {
		return dwalletRepository.findAll(pageable);
	}

	@Override
	public List<Dwallet> findAll() {
		return dwalletRepository.findAll();
	}

	@Override
	public List<Dwallet> findAllById(Iterable<String> ids) {
		return dwalletRepository.findAllById(ids);
	}

	@Override
	public Optional<Dwallet> findById(String id) {
		return dwalletRepository.findById(id);
	}

	@Override
	public long count() {
		return dwalletRepository.count();
	}

	@Override
	public void deleteById(String id) {
		dwalletRepository.deleteById(id);
	}

	@Override
	public void deleteAll() {
		dwalletRepository.deleteAll();
	}
	
}
