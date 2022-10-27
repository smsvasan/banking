package com.sms.banking.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sms.banking.dispatchers.EmailSender;
import com.sms.banking.dispatchers.SMSSender;
import com.sms.banking.dispatchers.WhatsAppMsgSender;
import com.sms.banking.validator.RecipientValidator;

@RunWith(MockitoJUnitRunner.class)
public class NotificationDispatchControllerTest {

	@InjectMocks
	private NotificationDispatchController controller;

	@Mock
	private EmailSender emailSender;

	@Mock
	private SMSSender smsSender;

	@Mock
	private WhatsAppMsgSender whatsAppMsgSender;
	
	@Mock
	private RecipientValidator recipientValidator;
	
	@Test
	public void test_notifyByEmail_success() throws Exception {

		doNothing().when(this.emailSender).sendEmail(anyString(), any());
		doReturn(true).when(this.recipientValidator).validateEmailAddress(anyString());
		
        ResponseEntity<String> response = this.controller.notify("testMsg", "Email", "test@test.com");
        verify(this.emailSender,times(1)).sendEmail(anyString(), any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
	
	@Test
	public void test_notifyByEmail_failure() throws Exception {

		doReturn(false).when(this.recipientValidator).validateEmailAddress(anyString());
		
        ResponseEntity<String> response = this.controller.notify("testMsg", "Email", "test");
        assertEquals("One or more recipients invalid", response.getBody());
    }
	
	@Test
	public void test_notifyBySms_success() throws Exception {

		doNothing().when(this.smsSender).sendSMS(anyString(), any());
		doReturn(true).when(this.recipientValidator).validatePhoneNumber(anyString());
		
        ResponseEntity<String> response = this.controller.notify("testMsg", "SMS", "+911234567890");
        verify(this.smsSender,times(1)).sendSMS(anyString(), any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
	
	@Test
	public void test_notifyBySms_failure() throws Exception {

		doReturn(false).when(this.recipientValidator).validatePhoneNumber(anyString());
		
        ResponseEntity<String> response = this.controller.notify("testMsg", "SMS", "test");
        assertEquals("One or more recipients invalid", response.getBody());
    }
	
	@Test
	public void test_notifyByWhatsApp_success() throws Exception {

		doNothing().when(this.whatsAppMsgSender).sendWhatsAppMessage(anyString(), any());
		doReturn(true).when(this.recipientValidator).validatePhoneNumber(anyString());
		
        ResponseEntity<String> response = this.controller.notify("testMsg", "Whatsapp", "+911234567890");
        verify(this.whatsAppMsgSender,times(1)).sendWhatsAppMessage(anyString(), any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
	
	@Test
	public void test_notifyByWhatsApp_failure() throws Exception {

		doReturn(false).when(this.recipientValidator).validatePhoneNumber(anyString());
		
        ResponseEntity<String> response = this.controller.notify("testMsg", "Whatsapp", "test");
        assertEquals("One or more recipients invalid", response.getBody());
    }
	

}
