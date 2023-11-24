package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Dwallet;

public interface DwalletService {

	void deleteAll();

	void deleteById(String id);

	long count();

	Optional<Dwallet> findById(String id);

	List<Dwallet> findAllById(Iterable<String> ids);

	List<Dwallet> findAll();

	Page<Dwallet> findAll(Pageable pageable);

	List<Dwallet> findAll(Sort sort);

	Dwallet save(Dwallet entity);

}
