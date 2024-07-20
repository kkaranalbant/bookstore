/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kaan.deneme.repository;

import com.kaan.deneme.model.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kaan
 */
@Repository
public interface CommentRepo extends JpaRepository <Comment , Long>{
    
    public List <Comment> findAllByCustomerId (Long customerId) ;
    
    public List <Comment> findAllByBookId (Long bookId) ;
    
    public void deleteByBookId (Long bookId) ;
    
    public void deleteByCustomerId (Long customerId) ;
    
}
