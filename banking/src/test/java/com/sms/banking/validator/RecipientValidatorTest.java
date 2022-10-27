package com.sms.banking.validator;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RecipientValidatorTest {
	
	@InjectMocks
	private RecipientValidator validator;
	
	@Test
	public void testValidateEmailAddress_WhenSuccess() throws Exception {
		assertTrue(this.validator.validateEmailAddress("test@test.com"));
	}
	
	@Test
	public void testValidateEmailAddress_WhenFailure() throws Exception {
		assertFalse(this.validator.validateEmailAddress("test"));
	}
	
	@Test
	public void testValidatePhoneNumber_WhenSuccess() throws Exception {
		assertTrue(this.validator.validatePhoneNumber("+1 555 777-3456"));
	}
	
	@Test
	public void testValidatePhoneNumber_WhenFailure() throws Exception {
		assertFalse(this.validator.validatePhoneNumber("test"));
	}

}
