/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.exception;

/**
 *
 * @author kaan
 */
public class InvalidMoneyAmountException extends RuntimeException {

    private static String message;

    static {
        message = "Invalid money amount.";
    }

    @Override
    public String getMessage() {
        return message;
    }

}
