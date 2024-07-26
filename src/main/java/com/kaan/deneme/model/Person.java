/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;


/**
 *
 * @author kaan
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Data
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name = "person_seq", sequenceName = "person_sequence")
    private Long id ;
    
    @Column (name = "Name" , nullable = false)
    private String name ;
    @Column (name = "Lastname" , nullable = false)
    private String lastname ;
    @Enumerated (EnumType.STRING)
    private Gender gender ;
    @Column (name = "Is_Enabled" , nullable = false)
    private Boolean enabled ;
    

    
}
