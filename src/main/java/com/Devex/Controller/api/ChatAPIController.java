package com.Devex.Controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.DTO.MessageDTO;
import com.Devex.Entity.ChatMessage;
import com.Devex.Sevice.ChatService;
import com.Devex.Sevice.SessionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/message")
public class ChatAPIController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ChatService chatService;
	
	@GetMapping("/list")
	public ResponseEntity<List<MessageDTO>> getAllMessage() {
//		User user = sessionService.get("user");
		List<MessageDTO> list = chatService.findAllByUser("baolh");
		if(list != null) {
			return ResponseEntity.ok(list);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
}
