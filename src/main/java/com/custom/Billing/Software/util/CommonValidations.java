package com.custom.Billing.Software.util;

public class CommonValidations {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNullOrEmpty(Long str) {
        return str == null;
    }

    public static boolean isNumeric(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        /* Email validation */
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidPhoneNumber(Long phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) {
            return false;
        }
        /* Phone number must be 10 digits and can optionally start with a '+' sign */
        String phoneRegex = "^\\+?\\d{10}$";
        return phoneNumber.toString().matches(phoneRegex);
    }

    public static boolean isValidPassword(String password) {
        if (isNullOrEmpty(password)) {
            return false;
        }
        /* Password must be at least 8 characters long and contain at least one digit,
            one uppercase letter, one lowercase letter, and one special character */
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        return password.matches(passwordRegex);
    }

    public static boolean isValidName(String name) {
        if (isNullOrEmpty(name)) {
            return false;
        }
        /* Name should only contain alphabets and spaces */
        String nameRegex = "^[a-zA-Z\\s]+$";
        return name.matches(nameRegex);
    }
}
