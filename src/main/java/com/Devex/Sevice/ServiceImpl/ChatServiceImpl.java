package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.DTO.MessageDTO;
import com.Devex.Entity.ChatMessage;
import com.Devex.Repository.ChatMessageRespository;
import com.Devex.Sevice.ChatService;

@SessionScope
@Service("chatService")
public class ChatServiceImpl implements ChatService{
	@Autowired
	private ChatMessageRespository chatMessageRespository;

	
	@Override
	public List<MessageDTO> findAllByUser(String username) {
		return chatMessageRespository.findAllByUser(username);
	}

	@Override
	public ChatMessage save(ChatMessage entity) {
		return chatMessageRespository.save(entity);
	}

	@Override
	public List<ChatMessage> findAll() {
		return chatMessageRespository.findAll();
	}

	@Override
	public Optional<ChatMessage> findById(Integer id) {
		return chatMessageRespository.findById(id);
	}

	@Override
	public long count() {
		return chatMessageRespository.count();
	}

	@Override
	public ChatMessage getById(Integer id) {
		return chatMessageRespository.getById(id);
	}

	@Override
	public void delete(ChatMessage entity) {
		chatMessageRespository.delete(entity);
	}
	
	
}
