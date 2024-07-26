/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Data
public class AddToBasketRequest {

    @NotNull
    private Long id;

    UsernamePasswordAuthenticationFilter filter ;
}
