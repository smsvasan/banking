package com.sms.banking.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import com.sms.banking.constants.ValidatorConstants;

@Component
public class RecipientValidator {

	public boolean validateEmailAddress(String emailAddress) {
		return EmailValidator.getInstance().isValid(emailAddress);
	}

	public boolean validatePhoneNumber(String phoneNumber) {
		Pattern p = Pattern.compile(ValidatorConstants.PH_NO_REGEX);
		Matcher m = p.matcher(phoneNumber);
		return (m.matches());
	}

}
