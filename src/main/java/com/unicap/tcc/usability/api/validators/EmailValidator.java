package com.unicap.tcc.usability.api.validators;

import java.util.regex.Pattern;

public class EmailValidator {
 
    private Pattern pattern;
  
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isValid(String email) {
        return email.matches(EMAIL_PATTERN);
    }
}
