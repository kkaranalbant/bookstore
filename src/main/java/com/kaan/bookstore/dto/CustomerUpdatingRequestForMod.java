/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.dto;

/**
 *
 * @author kaan
 */
public record CustomerUpdatingRequestForMod (String name , String lastname , String username , String password , String address , String email , String phoneNumber , Double balance , boolean isEnabled , boolean accountNonLocked , boolean credentialsNonLocked , boolean accountNonExpired){
    
}
