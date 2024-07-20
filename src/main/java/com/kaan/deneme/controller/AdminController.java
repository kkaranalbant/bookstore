/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.service.LoginCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author kaan
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private LoginCredentialsService loginCredentialsService ;
    
    @Autowired
    public AdminController (LoginCredentialsService loginCredentialsService) {
        this.loginCredentialsService = loginCredentialsService ;
    }
    
    
    @GetMapping ("/panel")
    public ModelAndView getAdminPanel () {
        ModelAndView mv = new ModelAndView () ;
        mv.setViewName("admin-main-panel");
        return mv ;
    }
    
}
