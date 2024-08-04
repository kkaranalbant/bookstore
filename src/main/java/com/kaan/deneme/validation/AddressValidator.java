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
public class AddressValidator implements ConstraintValidator<Address, String> {

    @Override
    public boolean isValid(String address, ConstraintValidatorContext cvc) {
        return address.length() >= 10;
    }

}
