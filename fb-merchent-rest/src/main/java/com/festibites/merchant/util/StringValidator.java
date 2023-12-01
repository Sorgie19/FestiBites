package com.festibites.merchant.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

import org.apache.commons.validator.routines.EmailValidator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

/**
 * Responsible for validating strings
 */
public class StringValidator
{
    /**
     * Determines if the given string is considered blank (that is, <code>null</code>, empty, or
     * consisting of only whitespace).
     * @param string the <code>String</code> value to check for as blank (may be <code>null</code>)
     * @return <code>true</code> if the given <code>String</code> is blank (that is,
     *         <code>null</code>, empty, or consisting of only whitespace), <code>false</code>
     *         otherwise
     */
    public static boolean isBlank(final String string)
    {
        if(string == null)
        {
            return true;
        }

        final int numberOfCharacters = string.length();

        if(numberOfCharacters <= 0)
        {
            return true;
        }

        int charIndex = 0;
        boolean isBlank = true;

        while(charIndex < numberOfCharacters)
        {
            // This logic is taken from the java.lang.String.trim() method rather than calling
            // java.lang.Character.isWhitespace(char) because it was 3 to 4 times faster.

            if(string.charAt(charIndex) > ' ')
            {
                isBlank = false;
                break;
            }

            ++charIndex;
        }

        return isBlank;
    }

    /**
     * Verifies that the string is not over a char limit.
     * @param string the string. Cannot be <code>null</code>, empty or blank.
     * @param limit the character limit. Must be greater than zero.
     * @return <code>true</code> if over the character limit, <code>false</code> if not
     */
    public static boolean overCharLimit(String string, int limit)
    {
        assert (!isBlank(string));
        assert (limit > 0);

        if(string.length() > limit)
        {
            return true;
        }
        return false;
    }

    /**
     * @param string validates the string does not contain any numbers. Validates the string
     *            contains a first and last name seperated by a space.
     * @return <code>true</code> if name is valid, <code>false</code> if not
     */
    public static boolean isValidName(String string)
    {
        assert (!isBlank(string));

        // If string contains a number return false
        if(string.matches(".*[0-9].*"))
        {
            return false;
        }

        // Checks if there is more than one space
        List<String> split = Arrays.asList(string.split(" "));
        if(split.size() != 2)
        {
            return false;
        }

        return split.get(0).matches("[a-zA-Z]+") && split.get(1).matches("[a-zA-Z]+");
    }
    
    
    public static boolean isValidName2(String string) {
        // You can adjust the regex pattern as needed to suit your validation requirements
        String namePattern = "^[a-zA-Z]+\\s[a-zA-Z]+$";
        return !isBlank(string) && Pattern.matches(namePattern, string);
    }

    public static boolean isValidEmail(String string) {
        return !isBlank(string) && EmailValidator.getInstance().isValid(string);
    }
    
	public static boolean isValidPhoneNumber(String string, String countryCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(string, countryCode);
            return phoneNumberUtil.isValidNumber(phoneNumber);
        } catch (NumberParseException e) {
            return false;
        }
    }
	
    public static boolean isPasswordStrong(String password) {
        return StringUtils.length(password) >= 8; // Check if the password is at least 8 characters long
        // Additional strength checks can be added here (e.g., contains numbers, uppercase, special characters, etc.)
    }
}
