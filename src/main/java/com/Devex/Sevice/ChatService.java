package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import com.Devex.DTO.MessageDTO;
import com.Devex.Entity.ChatMessage;

public interface ChatService {

	void delete(ChatMessage entity);

	ChatMessage getById(Integer id);

	long count();

	Optional<ChatMessage> findById(Integer id);

	List<ChatMessage> findAll();

	ChatMessage save(ChatMessage entity);

	List<MessageDTO> findAllByUser(String username);

}
