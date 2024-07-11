/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kaan.deneme.repository;

import com.kaan.deneme.model.BookImage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kaan
 */
@Repository
public interface BookImageRepo extends JpaRepository <BookImage , Long>{
    
    public Optional<BookImage> findByBookIdAndUrl (Long bookId , String url) ;
    
    public List <BookImage> findAllByBookId (Long bookId) ;
    
}
