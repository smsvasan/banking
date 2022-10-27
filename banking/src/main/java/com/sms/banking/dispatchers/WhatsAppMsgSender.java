package com.sms.banking.dispatchers;

import org.springframework.stereotype.Component;

@Component
public class WhatsAppMsgSender {

	public void sendWhatsAppMessage(String message, String... recipients) {
		// Send WhatsApp msg using Twilio
	}

}
