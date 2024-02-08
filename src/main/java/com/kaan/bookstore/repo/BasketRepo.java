/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kaan.bookstore.repo;

import com.kaan.bookstore.model.Basket;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kaan
 */
@Repository
public interface BasketRepo extends JpaRepository <Basket , Long>{
    public Optional<Basket> findByCustomer_IdAndBook_Id (Long customerId , Long bookId) ;
    
    public List<Basket> findAllByCustomer_Id (Long customerId) ;
}
