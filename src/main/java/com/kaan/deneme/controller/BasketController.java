/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.service.BasketService;
import com.kaan.deneme.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kaan
 */
@RestController
@RequestMapping("/basket")
public class BasketController {
    
    private JwtService jwtService;
    private BasketService basketService;
    
    @Autowired
    public BasketController(JwtService jwtService, BasketService basketService) {
        this.jwtService = jwtService;
        this.basketService = basketService;
    }
    
    @GetMapping("/get") // musteri
    public List<Book> getBasketByCustomerId(HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        return basketService.getBasketByCustomerId(id);
        
    }
    
    @PostMapping("/truncate") //musteri
    public void truncateBasketByCustomerId(HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        basketService.truncateBasketByCustomerId(id);
    }
    
    @PostMapping("/add") // musteri
    public void addToBasket(HttpServletRequest request, ElementIdDao bookIdDao) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        basketService.addToBasketById(id, bookIdDao);
    }
    
    @DeleteMapping("/remove") //musteri
    public void removeFromBasketByBasketId(HttpServletRequest request, ElementIdDao bookIdDao) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        basketService.removeFromBasketById(id, bookIdDao);
    }
    
}
