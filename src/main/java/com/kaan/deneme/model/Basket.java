/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Entity
@Table(name = "Basket", uniqueConstraints = {
@UniqueConstraint(columnNames = {"CustomerID", "BookID"})})
@Data
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CustomerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "BookID")
    private Book book;

    public Basket(Customer customer, Book book) {
        this.customer = customer;
        this.book = book;
    }

    public Basket() {

    }

}
