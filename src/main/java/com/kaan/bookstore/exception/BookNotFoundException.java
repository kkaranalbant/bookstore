/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.exception;

/**
 *
 * @author kaan
 */
public class BookNotFoundException extends RuntimeException {

    private static String message;

    static {
        message = "Book Not Found !";
    }

    @Override
    public String getMessage() {
        return message;
    }

}
