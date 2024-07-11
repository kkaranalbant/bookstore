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
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Entity
@Table (name = "BookImage" , uniqueConstraints = {@UniqueConstraint (columnNames = {"BookId" , "Url"})})
@Data
public class BookImage {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id ; 
    
    @ManyToOne
    @JoinColumn (name = "BookID")
    private Book book ;
    
    @Column (name = "UploadDate" , nullable = false)
    private LocalDateTime uploadDate ;
    
    @Column (name = "Url" , nullable = true)
    private String url ;
}
