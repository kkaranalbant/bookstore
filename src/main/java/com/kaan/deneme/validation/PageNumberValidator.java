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
public class PageNumberValidator implements ConstraintValidator<PageNumber, Integer> {

    @Override
    public boolean isValid(Integer pageNumber, ConstraintValidatorContext cvc) {
        return pageNumber.intValue() <= Integer.MAX_VALUE && pageNumber > 0 ;
    }
    
}
