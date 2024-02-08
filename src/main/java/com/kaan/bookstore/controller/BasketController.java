/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.controller;

import com.kaan.bookstore.model.Book;
import com.kaan.bookstore.model.Customer;
import com.kaan.bookstore.service.JwtService;
import com.kaan.bookstore.service.BasketService;
import com.kaan.bookstore.service.BookService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kaan
 */
@RestController
@RequestMapping("/basket")
public class BasketController {

    private BookService bookService;
    private BasketService basketService;
    private JwtService jwtService;

    public BasketController(BookService bookService, BasketService basketService, JwtService jwtService) {
        this.bookService = bookService;
        this.basketService = basketService;
        this.jwtService = jwtService;
    }

    @DeleteMapping("/removebook")
    public void removeBookFromBasket(@RequestParam String name, @RequestHeader("Authorization") String token) {
        Book book = bookService.getBookByBookName(name);
        Customer customer = (Customer) jwtService.getUserByToken(token);
        basketService.removeBookFromBasket(customer, book);
    }

    @DeleteMapping("/emptybasket")
    public void emptyBasket(@RequestHeader("Authorization") String token) {
        Customer customer = (Customer) jwtService.getUserByToken(token);
        basketService.emptyBasket(customer);
    }

    @PostMapping("/addtobasket")
    public void addToBasket (@RequestHeader("Authorization") String token , @RequestParam String name) {
        Book book = bookService.getBookByBookName(name);
        Customer customer = (Customer) jwtService.getUserByToken(token);
        basketService.addBookToBasket(customer, book);
    }

}
