package com.khan.fazal.intern.utils;

/**
 * Provides utility methods to validate user input such as
 * phone numbers and email addresses using regular expressions.
 */
public class InputValidator {

    /**
     * Validates that the phone number consists of exactly 10 digits.
     *
     * @param phone the phone number string to validate
     * @return true if the phone number is valid; false otherwise
     */
    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    /**
     * Validates the format of an email address.
     *
     * @param email the email address string to validate
     * @return true if the email format is valid; false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    }
}
