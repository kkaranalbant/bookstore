/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.FavouriteUpdatingDao;
import com.kaan.deneme.model.Favourite;
import com.kaan.deneme.service.FavouriteService;
import com.kaan.deneme.service.IpService;
import com.kaan.deneme.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * author kaan
 */
@RestController
@RequestMapping("/fav")
public class FavouriteController {

    private JwtService jwtService;
    private FavouriteService favouriteService;
    private IpService ipService;

    @Autowired
    public FavouriteController(JwtService jwtService, FavouriteService favouriteService, IpService ipService) {
        this.jwtService = jwtService;
        this.favouriteService = favouriteService;
        this.ipService = ipService;
    }

    @PostMapping("/add")
    public void addFav(HttpServletRequest request, @RequestBody FavouriteUpdatingDao favouriteUpdatingDao) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        favouriteService.addFavourite(customerId, favouriteUpdatingDao, ipService.getIpAddress(request));
    }

    @DeleteMapping("/delete")
    public void removeFav(HttpServletRequest request, @RequestBody FavouriteUpdatingDao favouriteUpdatingDao) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        favouriteService.removeFavourite(customerId, favouriteUpdatingDao, ipService.getIpAddress(request));
    }

    @GetMapping("/get-all-customer")
    public ModelAndView getAllFavouriteByCustomerId(HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        List<Favourite> favs = favouriteService.getAllFavuriteByCustomerId(customerId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("favs", favs);
        mv.setViewName("customer-fav");
        return mv;
    }

    @GetMapping("/get-all-book")
    public Integer getAllFavouriteNumberByBookId(@RequestParam Long id) {
        return favouriteService.getAllFavouriteNumberByBookId(new ElementIdDao(id));
    }
}
