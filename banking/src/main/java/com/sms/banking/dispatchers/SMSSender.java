package com.sms.banking.dispatchers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SMSSender {

	@Value("sms.account.id")
	private String accountSid;

	@Value("sms.auth.token")
	private String authToken;

	@Value("sms.sender.phone.number")
	private String senderNumber;

	public void sendSMS(String message, String... recipients) {
		Twilio.init(accountSid, authToken);
		for (String recipient : recipients) {
			Message.creator(new PhoneNumber(recipient), new PhoneNumber(senderNumber), message).create();
		}
	}
	
}
