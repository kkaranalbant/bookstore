/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import com.kaan.deneme.model.Gender;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class CustomerInformationResponse {

    private Long id;

    private String name;
    private String lastname;
    private Gender gender;
    private LocalDate birthDate;
    private String address;
    private float balance;
    private String username;
    private String password;
}
