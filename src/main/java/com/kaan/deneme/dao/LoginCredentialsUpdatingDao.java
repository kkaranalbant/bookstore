/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import com.kaan.deneme.model.Person;
import com.kaan.deneme.model.Role;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class LoginCredentialsUpdatingDao {
    
    private Person person ;
    
    private String username ;
    
    private String password ;
    
    private Role role ;
    
}
