/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.controller;

import com.kaan.bookstore.dto.CardCreatingRequest;
import com.kaan.bookstore.model.Customer;
import com.kaan.bookstore.service.JwtService;
import com.kaan.bookstore.service.CardService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kaan
 */
@RestController
@RequestMapping("/card")
public class CardController {

    private CardService cardService;
    private JwtService jwtService;

    public CardController(CardService cardService, JwtService jwtService) {
        this.cardService = cardService;
        this.jwtService = jwtService;
    }

    @PostMapping("/create") // musteri icin
    public void addCard(@RequestHeader("Authorization") String token, @RequestBody CardCreatingRequest cardCreatingRequest) {
        Customer customer = (Customer) jwtService.getUserByToken(token);
        cardService.createCard(customer, cardCreatingRequest);
    }

    @DeleteMapping("/deletebynumber")// musteri icin
    public void removeCardByCardNumber(@RequestHeader("Authorization") String token, @RequestParam String cardNo) {
        Customer customer = (Customer) jwtService.getUserByToken(token);
        cardService.removeCardByCardNumber(cardNo, customer);
    }

    @DeleteMapping("/deleteall")// musteri icin
    public void removeAllCards(@RequestHeader("Authorization") String token) {
        Customer customer = (Customer) jwtService.getUserByToken(token);
        cardService.removeAllCards(customer);
    }

    @DeleteMapping("/deletebynumberandid")//moderator icin
    public void removeCardByCardNumberAndCustomerId(@RequestParam String cardNo, @RequestParam Long customerId) {
        cardService.removeCardByCardNumberAndCustomerId(cardNo, customerId);
    }

}
