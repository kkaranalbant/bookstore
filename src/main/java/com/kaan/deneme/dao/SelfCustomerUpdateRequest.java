/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import com.kaan.deneme.model.Gender;
import com.kaan.deneme.validation.Address;
import com.kaan.deneme.validation.Name;
import com.kaan.deneme.validation.Password;
import com.kaan.deneme.validation.PublicationDate;
import com.kaan.deneme.validation.Username;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class SelfCustomerUpdateRequest {

    @NotNull
    @Name
    private String name;

    @NotNull
    @Name
    private String lastname;

    private Gender gender;

    @NotNull
    @PublicationDate
    private LocalDate birthDate;

    @NotNull
    @Address
    private String address;

    @NotNull
    @Username
    private String username;
    
    @NotNull
    @Password
    private String password;
}
