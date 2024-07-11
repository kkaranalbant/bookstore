/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Entity
@Table(name = "Card")
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    
    @ManyToOne
    @JoinColumn (name = "CustomerID") 
    private Customer customer ;
    
    @Column (name = "CardNo" , length = 16 , nullable = false , unique = true)
    private String cardNo ;
    
    @Column (name = "CVV" , length = 3 , nullable = false)
    private String cvv ; 
    
    @Column (name = "ValidMonth" , nullable = false)
    private byte month ;
    
    @Column (name = "ValidYear" , nullable = false) 
    private int year ;
}
