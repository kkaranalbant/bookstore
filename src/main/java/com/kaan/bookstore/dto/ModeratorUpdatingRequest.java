/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.dto;

/**
 *
 * @author kaan
 */
public record ModeratorUpdatingRequest (String name, String lastname, String password, String email, String phoneNumber) {
    
}
