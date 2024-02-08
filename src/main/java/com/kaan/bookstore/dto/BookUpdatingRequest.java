/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.dto;

import lombok.Builder;

/**
 *
 * @author kaan
 */
@Builder
public record BookUpdatingRequest (String name , String author , String subject , String publisher , Long isbn , Double price) {
    
}
