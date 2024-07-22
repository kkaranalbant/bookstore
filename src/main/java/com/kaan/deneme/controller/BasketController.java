/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.AddToBasketRequest;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.service.BasketService;
import com.kaan.deneme.service.IpService;
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
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * author kaan
 */
@RestController
@RequestMapping("/basket")
public class BasketController {

    private JwtService jwtService;
    private BasketService basketService;
    private IpService ipService;

    @Autowired
    public BasketController(JwtService jwtService, BasketService basketService, IpService ipService) {
        this.jwtService = jwtService;
        this.basketService = basketService;
        this.ipService = ipService;
    }

    @GetMapping("/get") // musteri
    public ModelAndView getBasketByCustomerId(HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        List<Book> basket = basketService.getBasketByCustomerId(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("basket", basket);
        mv.setViewName("customer-basket");
        return mv;
    }

    @PostMapping("/truncate") // musteri
    public void truncateBasketByCustomerId(HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        basketService.truncateBasketByCustomerId(id, ipService.getIpAddress(request));
    }

    @PostMapping("/add") // musteri
    public void addToBasket(HttpServletRequest request, @RequestBody AddToBasketRequest addToBasketRequest) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        basketService.addToBasketById(customerId, new ElementIdDao(addToBasketRequest.getId()), ipService.getIpAddress(request));
    }

    @DeleteMapping("/remove") // musteri
    public void removeFromBasketByBasketId(HttpServletRequest request, @RequestBody ElementIdDao bookIdDao) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        basketService.removeFromBasketById(id, bookIdDao, ipService.getIpAddress(request));
    }
}
