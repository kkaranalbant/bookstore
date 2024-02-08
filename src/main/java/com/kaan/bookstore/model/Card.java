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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;

/**
 *
 * @author kaan
 */
@Entity
@Table(name = "customer_card", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"customer_id", "card_no"})})
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_no", length = 16)
    private String cardNumber;

    @ManyToMany
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(length = 3)
    private Byte cvv;

    @Column(length = 2)
    private Byte validMonth;

    @Column(length = 2)
    private Byte validYear;

}
