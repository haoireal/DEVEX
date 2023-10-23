package com.Devex.Sevice;

import com.Devex.Entity.MailInfo;
import jakarta.mail.MessagingException;

public interface NotiService {
		
	void send(String userFrom,String userTo,String link, String type);
}
