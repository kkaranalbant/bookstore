/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import com.kaan.deneme.model.Gender;
import com.kaan.deneme.validation.Name;
import com.kaan.deneme.validation.Password;
import com.kaan.deneme.validation.Username;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class ModeratorAddingRequest {
    @Name
    private String name ;
    @Name
    private String lastname ;
    private Gender gender ;
    @Username
    private String username ;
    @Password
    private String password ;
}
