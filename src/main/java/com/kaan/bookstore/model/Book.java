/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Entity
@Table(name = "books")
@Builder
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true)
    private String name;

    @Column(length = 25)
    private String author;

    @Column(length = 15)
    private String subject;

    @Column(length = 20)
    private String publisher;

    @Column(unique = true)
    private Long isbn;

    private Double price;

}
