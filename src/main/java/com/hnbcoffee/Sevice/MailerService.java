package com.hnbcoffee.Sevice;


import com.hnbcoffee.DTO.MailInfo;
import com.hnbcoffee.Entity.User;

import jakarta.mail.MessagingException;

public interface MailerService {

	void send(MailInfo mail) throws MessagingException;
	
	void send(String to, String subject, String body) throws MessagingException;
	
	void sendMailToFormat(String type, User user) throws MessagingException;
	
	
	void queue(MailInfo mail);
	
	void queue(String to, String subjecct, String body);
	
}
