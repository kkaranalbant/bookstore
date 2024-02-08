/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.exception;

/**
 *
 * @author kaan
 */
public class NotSufficentBalanceException extends RuntimeException{
    private static String message;

    static {
        message = "Your balance is not enough .";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
