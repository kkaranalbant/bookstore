/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.validation;

import io.jsonwebtoken.security.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 * @author kaan
 */
public class PasswordValidator implements ConstraintValidator<com.kaan.deneme.validation.Password, String> {

    private static final byte MIN_PASS_LENGTH;
    private static final byte MAX_PASS_LENGTH;

    static {
        MIN_PASS_LENGTH = 8;
        MAX_PASS_LENGTH = 24;
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext cvc) {
        boolean hasUpperChar = false;
        boolean hasDigit = false;
        if (!(password.length() >= MIN_PASS_LENGTH && password.length() <= MAX_PASS_LENGTH)) {
            return false;
        }
        for (int i = 0; i < password.length() && (!hasUpperChar || !hasDigit); i++) {
            if (Character.isDigit(password.charAt(i))) {
                hasDigit = true;
                continue;
            }
            if (Character.isUpperCase(password.charAt(i))) {
                hasUpperChar = true;
            }
        }
        return hasDigit && hasUpperChar;
    }

}
