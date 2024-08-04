/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.validation;

import com.kaan.deneme.service.CustomerService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 * @author kaan
 */
public class TokenValidator implements ConstraintValidator<Token,String>{

    @Override
    public boolean isValid(String token, ConstraintValidatorContext cvc) {
        return token.length() == CustomerService.getTokenLength() ;
    }
    
    
    
}
