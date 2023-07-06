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

import com.Devex.Entity.CommentReply;
import com.Devex.Repository.CommentReplyRepository;
import com.Devex.Sevice.CommentReplyService;

@SessionScope
@Service("commentReplyService")
public class CommentReplyServiceImpl implements CommentReplyService{
	@Autowired
	CommentReplyRepository commentReplyRepository;

	@Override
	public CommentReply save(CommentReply entity) {
		return commentReplyRepository.save(entity);
	}

	@Override
	public List<CommentReply> saveAll(List<CommentReply> entities) {
		return commentReplyRepository.saveAll(entities);
	}

	@Override
	public Optional<CommentReply> findOne(Example<CommentReply> example) {
		return commentReplyRepository.findOne(example);
	}

	@Override
	public List<CommentReply> findAll(Sort sort) {
		return commentReplyRepository.findAll(sort);
	}

	@Override
	public Page<CommentReply> findAll(Pageable pageable) {
		return commentReplyRepository.findAll(pageable);
	}

	@Override
	public List<CommentReply> findAll() {
		return commentReplyRepository.findAll();
	}

	@Override
	public List<CommentReply> findAllById(Iterable<Integer> ids) {
		return commentReplyRepository.findAllById(ids);
	}

	@Override
	public Optional<CommentReply> findById(Integer id) {
		return commentReplyRepository.findById(id);
	}

	@Override
	public long count() {
		return commentReplyRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		commentReplyRepository.deleteById(id);
	}

	@Override
	public CommentReply getById(Integer id) {
		return commentReplyRepository.getById(id);
	}

	@Override
	public void delete(CommentReply entity) {
		commentReplyRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		commentReplyRepository.deleteAll();
	}
	
	
}
