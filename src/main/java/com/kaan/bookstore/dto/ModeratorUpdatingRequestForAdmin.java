/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.dto;

/**
 *
 * @author kaan
 */
public record ModeratorUpdatingRequestForAdmin(String name, String lastname, String username, String password, String email, String phoneNumber, boolean isEnabled, boolean accountNonLocked, boolean credentialsNonLocked, boolean accountNonExpired) {

}
