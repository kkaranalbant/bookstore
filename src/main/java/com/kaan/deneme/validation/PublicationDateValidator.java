/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 *
 * @author kaan
 */
public class PublicationDateValidator implements ConstraintValidator<PublicationDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate publicationDate, ConstraintValidatorContext cvc) {
        return publicationDate.isBefore(LocalDate.now());
    }

}
