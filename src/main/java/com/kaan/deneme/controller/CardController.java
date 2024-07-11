/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.CardAddingDao;
import com.kaan.deneme.dao.CardRemovingDao;
import com.kaan.deneme.model.Card;
import com.kaan.deneme.service.CardService;
import com.kaan.deneme.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping ("/card")
public class CardController {
    
    private JwtService jwtService ;
    private CardService cardService ;

    @Autowired
    public CardController(JwtService jwtService, CardService cardService) {
        this.jwtService = jwtService;
        this.cardService = cardService;
    }
    
    @GetMapping ("/get-all") //musteri icin
    public List <Card> getAll (HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        return cardService.getCardsByCustomerId(id);
    }
    
    @PostMapping ("/add") // musteri icin
    public void addCard (HttpServletRequest request , @RequestBody CardAddingDao cardAddingDao) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        cardService.addCardById(id, cardAddingDao);
    }
    
    @DeleteMapping ("/delete") // musteri
    public void removeCard (HttpServletRequest request , @RequestBody CardRemovingDao cardRemovingDao) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        cardService.removeCardById(id, cardRemovingDao);
    } 
    
}
