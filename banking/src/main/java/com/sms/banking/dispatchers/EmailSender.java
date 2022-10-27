package com.sms.banking.dispatchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String message, String... recipients) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(recipients);
		msg.setSubject("Notification email");
		msg.setText(message);
		javaMailSender.send(msg);
	}

}
