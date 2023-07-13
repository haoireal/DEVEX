package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.CommentReply;

public interface CommentReplyService {

	void deleteAll();

	void delete(CommentReply entity);

	CommentReply getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<CommentReply> findById(Integer id);

	List<CommentReply> findAllById(Iterable<Integer> ids);

	List<CommentReply> findAll();

	Page<CommentReply> findAll(Pageable pageable);

	List<CommentReply> findAll(Sort sort);

	Optional<CommentReply> findOne(Example<CommentReply> example);

	List<CommentReply> saveAll(List<CommentReply> entities);

	CommentReply save(CommentReply entity);

}
