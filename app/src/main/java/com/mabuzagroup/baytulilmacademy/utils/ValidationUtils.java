package com.mabuzagroup.baytulilmacademy.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationUtils {

    private ValidationUtils() {
        // Prevent instantiation
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email)
                && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidFullName(String fullName) {
        return fullName != null && fullName.trim().length() >= 3;
    }

    public static boolean passwordsMatch(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }
}