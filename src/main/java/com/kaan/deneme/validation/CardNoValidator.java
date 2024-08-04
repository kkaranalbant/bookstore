/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kaan
 */
public class CardNoValidator implements ConstraintValidator <CardNo , String>{

    @Override
    public boolean isValid(String cardNo, ConstraintValidatorContext cvc) {
        String regex = "^\\d{16}$" ;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cardNo);
        return matcher.matches() ;
    }

    
    
}
