/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kaan.bookstore.repo;

import com.kaan.bookstore.model.Card;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kaan
 */
@Repository
public interface CardRepo extends JpaRepository <Card , Long>{
    Optional <Card> findByCardNumberAndCustomer (String cardNumber , Long customerId) ;
    List<Card> findAllByCustomer(Long customerId) ;
}
