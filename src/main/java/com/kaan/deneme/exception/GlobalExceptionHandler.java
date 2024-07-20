/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.exception;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author kaan
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIdException(InvalidIdException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid ID", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAddingProcessException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAddingProcessException(InvalidAddingProcessException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid Adding Process", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUpdatingProcessException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUpdatingProcessException(InvalidUpdatingProcessException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid Updating Process", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler (InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidUpdatingProcessException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid Credentials", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler (InvalidCommentException.class)
    public ResponseEntity <ErrorResponse> handleInvalidCommentException (InvalidCommentException ex) {
        ErrorResponse errorResponse = new ErrorResponse ("Invalid Comment" , HttpStatus.BAD_REQUEST.value()) ;
        return new ResponseEntity<> (errorResponse , HttpStatus.BAD_REQUEST);
    } 

    @ExceptionHandler(NotSufficentBalanceException.class)
    public ResponseEntity<ErrorResponse> handleNotSufficentBalanceException(NotSufficentBalanceException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Not Sufficient Balance", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler (NotUniqueCardException.class)
    public ResponseEntity <ErrorResponse> handleNotUniqueCardException (NotUniqueCardException ex) {
        ErrorResponse errorResponse = new ErrorResponse("You Can't Add This Card", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler (OutOfStockException.class)
    public ResponseEntity <ErrorResponse> handleOutOfStockException (OutOfStockException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Out Of Stock", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler (UnauthorizedCardProcessException.class)
    public ResponseEntity <ErrorResponse> handleUnauthorizedCardProcessException (UnauthorizedCardProcessException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Out Of Stock", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler (IOException.class) 
    public ResponseEntity<ErrorResponse> hangleIOException (IOException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST) ;
    }

    // Genel bir exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static class ErrorResponse {

        private String message;
        private int status;

        public ErrorResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }

    }

}
