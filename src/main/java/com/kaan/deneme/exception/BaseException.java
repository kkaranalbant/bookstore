/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.exception;

import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class BaseException extends RuntimeException {

    private String message;

    BaseException(String message) {
        this.message = message;
    }

}
