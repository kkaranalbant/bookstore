/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.CardAddingDao;
import com.kaan.deneme.dao.CardRemovingDao;
import com.kaan.deneme.model.Card;
import com.kaan.deneme.service.CardService;
import com.kaan.deneme.service.IpService;
import com.kaan.deneme.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * author kaan
 */
@RestController
@RequestMapping("/card")
public class CardController {

    private JwtService jwtService;
    private CardService cardService;
    private IpService ipService;

    @Autowired
    public CardController(JwtService jwtService, CardService cardService, IpService ipService) {
        this.jwtService = jwtService;
        this.cardService = cardService;
        this.ipService = ipService;
    }

    @GetMapping("/get-all") // musteri icin
    public List<Card> getAll(HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        return cardService.getCardsByCustomerId(id);
    }

    @GetMapping("/get-cards-panel")
    public ModelAndView getAllCardsPanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view-card");
        return mv;
    }

    @GetMapping("/add-panel")
    public ModelAndView getAddingCardPanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add-card");
        return mv;
    }

    @PostMapping("/add") // musteri icin
    public void addCard(HttpServletRequest request, @RequestBody CardAddingDao cardAddingDao) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        cardService.addCardById(id, cardAddingDao, ipService.getIpAddress(request));
    }

    @GetMapping("/delete-panel")
    public ModelAndView getDeletePanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("remove-card");
        return mv;
    }

    @DeleteMapping("/delete") // musteri
    public ResponseEntity<String> removeCard(HttpServletRequest request, @RequestBody CardRemovingDao cardRemovingDao) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        cardService.removeCardById(id, cardRemovingDao, ipService.getIpAddress(request));
        return ResponseEntity.ok().body("Successful");
    }
}
