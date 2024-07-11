/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.BookUpdatingDao;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kaan
 */
@RestController
@RequestMapping ("/book")
public class BookController {
    
    private BookService bookService ;
    
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping("/get-all") // herkes icin
    public List <Book> getAll () { 
        return bookService.getAll() ;
    }

    @GetMapping ("/get") // herkes
    public Book getBookById (@RequestBody ElementIdDao bookIdDao) {
        return bookService.getBookById(bookIdDao);
    }
    
    @DeleteMapping ("/delete") // admin , mod
    public void deleteBookById (@RequestBody ElementIdDao bookIdDao) {
        bookService.removeBookById(bookIdDao);
    }
    
    @PostMapping ("/add") // admin mod 
    public void addBook (@RequestBody ElementIdDao elementIdDao , @RequestBody BookUpdatingDao bookUpdatingDao) {
        bookService.addBook(elementIdDao , bookUpdatingDao);
    }
    
    @PostMapping ("/update") // admin , mod
    public void bookUpdatingDao (@RequestBody ElementIdDao elementIdDao ,@RequestBody BookUpdatingDao bookUpdatingDao) {
        bookService.updateBookById(elementIdDao , bookUpdatingDao);
    }
    
}
