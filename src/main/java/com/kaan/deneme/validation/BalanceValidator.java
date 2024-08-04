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
public class BalanceValidator implements ConstraintValidator <Balance , Float> {

    @Override
    public boolean isValid(Float balance , ConstraintValidatorContext cvc) {
        return balance > 0 && balance <= Float.MAX_VALUE ;
    }
    
    
    
}
