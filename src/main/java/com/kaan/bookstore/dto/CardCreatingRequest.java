/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.dto;

/**
 *
 * @author kaan
 */
public record CardCreatingRequest (String cardNo , Byte cvv , Byte validMonth , Byte validYear){
    
}
