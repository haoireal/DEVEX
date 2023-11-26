package com.Devex.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.Devex.DTO.MessageDTO;
import com.Devex.Sevice.ChatService;
import com.Devex.Sevice.SessionService;

@Controller
public class ChatController {
	@Autowired
    private ChatService chatService;
	
	@Autowired
	private SessionService sessionService;

//	@PostMapping("/send")
	@MessageMapping("/message")
	@SendTo("/topic/message")
	public MessageDTO sendMessage(@RequestBody MessageDTO message, Principal principal) {
	    // Lưu tin nhắn vào cơ sở dữ liệu
		System.out.println(0);
		System.out.println(1);
		
		System.out.println(11);
		return chatService.sendMessage(message, principal.getName());
	}
}
