package com.hnbcoffee.Utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class EmailUtils {
	public void setup(String sub, String text, String email) throws MessagingException {
		Properties p = new Properties();
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.ssl.protocols", "TLSv1.2");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "587");
		p.put("mail.smtp.debug", "true");
		Session s = Session.getDefaultInstance(p, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication("kenipham159@gmail.com", "pwazbjrxzobrzfgt");
			}
		});
		MimeMessage message = new MimeMessage(s);
        message.setRecipients(Message.RecipientType.TO, email);
        message.setSubject(sub);
        message.setText(text);

        // send message
        Transport.send(message);
	}
}