package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Role;

public interface RoleService {

	void deleteAll();

	void delete(Role entity);

	Role getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<Role> findById(Integer id);

	List<Role> findAllById(Iterable<Integer> ids);

	List<Role> findAll();

	Page<Role> findAll(Pageable pageable);

	List<Role> findAll(Sort sort);

	Optional<Role> findOne(Example<Role> example);

	List<Role> saveAll(List<Role> entities);

	Role save(Role entity);

}
