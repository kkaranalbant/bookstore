/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kaan.deneme.repository;

import com.kaan.deneme.model.Basket;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kaan
 */
@Repository
public interface BasketRepo extends JpaRepository<Basket, Long> {

    public List<Basket> findAllByCustomerId(Long customerId);
    
    public List <Basket> findAllByBookId (Long bookId) ;

    public Optional<Basket> findByCustomerIdAndBookId(Long customerId, Long bookId);
    
    public void deleteByBookId (Long bookId) ;
    
    public void deleteByCustomerId (Long customerId) ;
    

}
