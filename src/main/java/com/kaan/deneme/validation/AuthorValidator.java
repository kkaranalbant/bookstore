/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 * @author kaan
 */
public class AuthorValidator implements ConstraintValidator<Author, String> {

    @Override
    public boolean isValid(String author, ConstraintValidatorContext cvc) {
        for (char character : author.toCharArray()) {
            if (!Character.isDigit(character)) {
                return false ;
            }
        }
        return true ;
    }

}
