/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.controller;

import com.kaan.bookstore.dto.CustomerUpdatingRequest;
import com.kaan.bookstore.dto.CustomerUpdatingRequestForMod;
import com.kaan.bookstore.dto.UserCreatingRequest;
import com.kaan.bookstore.model.Customer;
import com.kaan.bookstore.service.JwtService;
import com.kaan.bookstore.service.CustomerService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/customer")
public class CustomerController {
    
    private CustomerService customerService ;
    private JwtService jwtService ;

    public CustomerController(CustomerService customerService, JwtService jwtService) {
        this.customerService = customerService;
        this.jwtService = jwtService;
    }
    
    @PostMapping("/create")//herkes icin
    public void createCustomer (@RequestBody UserCreatingRequest userCreatingRequest) {
        customerService.createCustomer(userCreatingRequest);
    }
    
    @PutMapping("/update")//musteri icin
    public void updateCustomer (@RequestHeader("Authorization") String token ,@RequestBody CustomerUpdatingRequest customerUpdatingRequest) {
        Customer customer = (Customer) jwtService.getUserByToken(token);
        customerService.updateCustomer(customer, customerUpdatingRequest);
    }
    
    @PutMapping("updatebyid")//moderator icin
    public void updateCustomer (Long customerId, @RequestBody CustomerUpdatingRequestForMod customerUpdatingRequestForMod) {
        customerService.updateCustomerById(customerId, customerUpdatingRequestForMod);
    }
    
    @PostMapping("/buy")//musteri icin
    public void buyBooks (@RequestHeader("Authorization") String token) {
        Customer customer = (Customer) jwtService.getUserByToken(token);
        customerService.buyBooks(customer);
    }
    
    @PatchMapping("/topup")//musteri icin
    public void topUpMoney (@RequestHeader("Authorization") String token , @RequestParam Double amount) {
        Customer customer = (Customer) jwtService.getUserByToken(token);
        customerService.topUpMoneyToAccount(customer, amount);
    }
    
}
