/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.model;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author kaan
 */
public enum Role implements GrantedAuthority {
    ADMIN,
    MODERATOR,
    CUSTOMER;

    @Override
    public String getAuthority() {
        return name();
    }

}
