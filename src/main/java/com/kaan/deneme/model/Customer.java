/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Entity
@Table(name = "Customer", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"Name", "Lastname"})})
@Data
public class Customer extends Person {

    @Column(name = "BirthDate")
    private LocalDate birthDate;
    @Column(name = "address")
    private String address;
    @Column(name = "balance")
    private float balance;
    @Column(name = "Verification_Code", nullable = true, length = 64)
    private String verificationCode;
    @Column (name = "Email" , nullable = false)
    private String email ;

}
