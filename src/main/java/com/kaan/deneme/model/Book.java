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
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Entity
@Table(name = "Book")
@Data
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Column(name = "Author", nullable = false)
    private String author;

    @Column(name = "ISBN", nullable = false, unique = true)
    private String isbn;

    @Column(name = "Price", nullable = false)
    private float price;

    @Column(name = "PageNumber", nullable = false)
    private int pageNumber;

    @Column(name = "PublicationDate", nullable = false)
    private LocalDate publicationDate;

    @Column(name = "Publisher", nullable = false)
    private String publisher;

    @Column(name = "Stock", nullable = false)
    private int stockNumber;

}
