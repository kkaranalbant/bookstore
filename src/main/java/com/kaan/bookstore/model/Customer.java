/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author kaan
 */
@Entity
@Table(name = "customers", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "lastname"})})
@Data
public class Customer extends BaseUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    @Lob
    private String address;

    @Builder
    public Customer(String name, String lastname, String email, String phoneNumber, String username ,String password ,boolean isEnabled, boolean accountNonLocked, boolean accountNonExpired, boolean credentialsNonExpired, Set<Role> authorities, Double balance, String address) {
        super(name, lastname, email, phoneNumber, username, password, isEnabled, accountNonLocked, accountNonExpired, credentialsNonExpired, authorities);
        this.balance = balance;
        this.address = address;
    }

}
