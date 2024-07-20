/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kaan.deneme.repository;

import com.kaan.deneme.model.Favourite;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kaan
 */
@Repository
public interface FavouriteRepo extends JpaRepository <Favourite , Long> {
    
    public List <Favourite> findAllByCustomerId (Long customerId) ;
    
    public List <Favourite> findAllByBookId (Long bookId) ;
    
    public void deleteByCustomerIdAndBookId (Long customerId , Long bookId) ;
    
    public void deleteByBookId (Long bookId) ;
    
    public void deleteByCustomerId (Long customerId) ;
    
}
