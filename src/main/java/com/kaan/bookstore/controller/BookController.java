/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.controller;

import com.kaan.bookstore.dto.BookUpdatingRequest;
import com.kaan.bookstore.service.BookService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kaan
 */
@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService ;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @PostMapping("/create")
    public void createBook (@RequestBody BookUpdatingRequest bookUpdatingRequest) {
        bookService.createBook(bookUpdatingRequest);
    }
    
    @PutMapping ("/update")
    public void updateBook (@RequestBody BookUpdatingRequest bookUpdatingRequest) {
        bookService.updateBook(bookUpdatingRequest);
    }
    
    @DeleteMapping("/delete")
    public void deleteBook (@RequestParam String name) {
        bookService.deleteBookByBookName(name);
    }
    
    
}
