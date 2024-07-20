/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class BookAddingRequest {
        
    private String name ; 
    
    private String author ; 
    
    private String isbn ; 
    
    private float price ;
    
    private int pageNumber ;
    
    private LocalDate publicationDate ;
    
    private String publisher ; 
    
    private int stockNumber ; 
    
    private List <byte []> paths ;
    
}
