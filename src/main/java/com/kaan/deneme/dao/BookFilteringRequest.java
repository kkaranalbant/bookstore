/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class BookFilteringRequest {

    private String author;

    private String name;

    private Integer minPageNumber;
    
    private Integer maxPageNumber ;

    private Float minPrice;
    
    private Float maxPrice;

    private LocalDate minPublicationDate;
    
    private LocalDate maxPublicationDate;

    private String publisher;

}
