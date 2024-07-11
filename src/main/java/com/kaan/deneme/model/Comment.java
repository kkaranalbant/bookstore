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
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Entity
@Table (name = "Comment") 
@Data
public class Comment {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id ; 
    
    @ManyToOne
    @JoinColumn (name = "BookID")
    private Book book ;
    
    @ManyToOne
    @JoinColumn (name = "CustomerID")
    private Customer customer ;
    
    @Column (name = "Context" , nullable = false) 
    private String context ;
    
    @Column (name = "Date" , nullable = false) 
    private LocalDateTime dateTime ;
}
