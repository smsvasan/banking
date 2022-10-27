package com.sms.banking.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.banking.constants.Constants;
import com.sms.banking.dispatchers.EmailSender;
import com.sms.banking.dispatchers.SMSSender;
import com.sms.banking.dispatchers.WhatsAppMsgSender;
import com.sms.banking.validator.RecipientValidator;

@RestController
@RequestMapping(value = "api/v1")
public class NotificationDispatchController {

	@Autowired
	private RecipientValidator recipientValidator;
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private SMSSender smsSender;
	
	@Autowired
	private WhatsAppMsgSender whatsAppMsgSender;

	@GetMapping("/notify/{message}/{channel}/{recipients}")
	public ResponseEntity<String> notify(@PathVariable("message") String message, 
			@PathVariable("channel") String channel, @PathVariable("recipients") String... recipients) {
		String statusMsg = "Notifications dispatched to recipients successfully";
		boolean validRecipients = validateRecipients(Arrays.asList(recipients), channel);
		if (validRecipients) {
			if (Constants.NOTIFICATION_CHANNEL_EMAIL.equalsIgnoreCase(channel)) {
				this.emailSender.sendEmail(message, recipients);
			} else if (Constants.NOTIFICATION_CHANNEL_SMS.equalsIgnoreCase(channel)) {
				this.smsSender.sendSMS(message, recipients);
			} else {
				this.whatsAppMsgSender.sendWhatsAppMessage(message, recipients);
			}
		} else {
			statusMsg = "One or more recipients invalid";
		}
		
		return new ResponseEntity<String>(statusMsg, HttpStatus.OK);
	}

	private boolean validateRecipients(List<String> recipients, String channel) {
		return Constants.NOTIFICATION_CHANNEL_EMAIL.equalsIgnoreCase(channel) ? validateEmailRecipients(recipients)
				: validatePhoneNumbers(recipients);
	}

	private boolean validateEmailRecipients(List<String> recipients) {
		boolean validRecipients = true;
		for (String recipient : recipients) {
			if (!this.recipientValidator.validateEmailAddress(recipient)) {
				validRecipients = false;
				break;
			}
		}
		return validRecipients;
	}

	private boolean validatePhoneNumbers(List<String> recipients) {
		boolean validRecipients = true;
		for (String recipient : recipients) {
			if (!this.recipientValidator.validatePhoneNumber(recipient)) {
				validRecipients = false;
				break;
			}
		}
		return validRecipients;
	}

}
