package com.Devex.Sevice.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.Devex.DTO.MessageDTO;
import com.Devex.Entity.ChatMessage;
import com.Devex.Entity.User;
import com.Devex.Repository.ChatMessageRespository;
import com.Devex.Repository.UserRepository;
import com.Devex.Sevice.ChatService;


@Service("chatService")
public class ChatServiceImpl implements ChatService{
	@Autowired
	private ChatMessageRespository chatMessageRespository;
	
	@Autowired
    private UserRepository userRepository;
	
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Override
	public MessageDTO sendMessage(MessageDTO message, String userID) {
    	System.out.println(2);
    	System.out.println(3);
    	//Xử lí dữ liệu tin nhắn
    	ChatMessage messToSave = new ChatMessage();
    	System.out.println(4);
    	messToSave.setContent(message.getContent());
    	System.out.println(5);
    	messToSave.setCreatedAt(new Date());
    	System.out.println(6);
    	User userFrom = userRepository.findById(userID).get();
    	messToSave.setSender(userFrom);
    	System.out.println(7);
    	User userTo = userRepository.findById(message.getReceiverID()).get();
    	System.out.println(8);
    	messToSave.setReceiver(userTo);
    	System.out.println(9);
    	
        // Lưu tin nhắn vào cơ sở dữ liệu
    	chatMessageRespository.save(messToSave);
    	System.out.println(10);
//        // Gửi tin nhắn đến người nhận thông qua WebSocket
//        messagingTemplate.convertAndSendToUser(
//        		message.getReceiverID(),
//                "/topic/message",
//                message
//        );
        System.out.println(12);
        return chatMessageRespository.findNewest(userID);
    }

	
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
