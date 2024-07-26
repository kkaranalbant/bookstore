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
public class StockNumberValidator implements ConstraintValidator <StockNumber , Integer>{

    @Override
    public boolean isValid(Integer stockNumber, ConstraintValidatorContext cvc) {
        return stockNumber.intValue() <= Integer.MAX_VALUE && stockNumber.intValue() >= 0  ;
    }
    
    
    
}
