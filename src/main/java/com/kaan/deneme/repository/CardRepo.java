/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kaan.deneme.repository;

import com.kaan.deneme.model.Card;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kaan
 */
@Repository
public interface CardRepo extends JpaRepository <Card , Long> {
    public List <Card> findAllByCustomerId (Long customerId) ;
    public Optional<Card> findByCardNo (String cardNo) ;
    public void deleteByCardNo (String cardNo) ;
    public void deleteByCustomerId (Long customerId) ;
}
