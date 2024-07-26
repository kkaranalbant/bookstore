/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import com.kaan.deneme.validation.Password;
import com.kaan.deneme.validation.Token;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class PasswordResetRequest {

    @NotNull
    @Token
    private String token;

    @NotNull
    @Password
    private String password;

}
