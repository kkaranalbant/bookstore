/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.FavouriteUpdatingDao;
import com.kaan.deneme.model.Favourite;
import com.kaan.deneme.service.FavouriteService;
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
@RequestMapping("/fav")
public class FavouriteController {

    private JwtService jwtService;

    private FavouriteService favouriteService;

    @Autowired
    public FavouriteController(JwtService jwtService, FavouriteService favouriteService) {
        this.jwtService = jwtService;
        this.favouriteService = favouriteService;
    }

    @PostMapping ("/add")
    public void addFav(HttpServletRequest request, @RequestBody FavouriteUpdatingDao favouriteUpdatingDao) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        favouriteService.addFavourite(customerId, favouriteUpdatingDao);
    }
    
    @DeleteMapping ("/delete")
    public void removeFav (HttpServletRequest request , @RequestBody FavouriteUpdatingDao favouriteUpdatingDao) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        favouriteService.removeFavourite(customerId, favouriteUpdatingDao);
    }
    
    @GetMapping ("/get-all-customer")
    public List <Favourite> getAllFavouriteByCustomerId (HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        return favouriteService.getAllFavuriteByCustomerId(customerId);
    }
    
    @GetMapping ("/get-all-book")
    public Integer getAllFavouriteNumberByBookId (@RequestBody ElementIdDao elementIdDao) {
        return favouriteService.getAllFavouriteNumberByBookId(elementIdDao);
    }

}
