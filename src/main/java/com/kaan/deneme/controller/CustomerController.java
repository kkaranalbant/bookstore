/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.CustomerUpdatingDao;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.LoginCredentialsUpdatingDao;
import com.kaan.deneme.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kaan.deneme.model.Customer;
import com.kaan.deneme.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author kaan
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;
    private JwtService jwtService ;

    @Autowired
    public CustomerController(CustomerService customerService , JwtService jwtService) {
        this.customerService = customerService;
        this.jwtService = jwtService ;
    }

    @GetMapping("/getv1") // admin + mod
    public Customer getCustomerById(@RequestParam(name = "id", required = true) Long id) {
        return customerService.getCustomerById(id).get();
    }
    
    @GetMapping ("/getv2") // musteri
    public Customer getCustomer (HttpServletRequest request) {
        String jwt = jwtService.getJwt(request) ;
        Long id = jwtService.getId(jwt);
        return customerService.getCustomerById(id).get();
    }


    @DeleteMapping ("/deletev1") // admin
    public void  deleteCustomerById (@RequestBody ElementIdDao elementIdDao) {
        customerService.removeCustomerById(elementIdDao);
    }
    
    @DeleteMapping ("/deletev2") // musteri
    public void deleteCustomer (HttpServletRequest httpServletRequest) {
        String jwt = jwtService.getJwt(httpServletRequest);
        Long id = jwtService.getId(jwt);
        customerService.removeSelfCustomer(id);
    }
    
    @PostMapping ("/addv1") //admin ve mod
    public void addCustomer (@RequestBody ElementIdDao elementIdDao ,@RequestBody CustomerUpdatingDao customerUpdatingDao , @RequestBody LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) {
        customerService.addCustomer(elementIdDao , customerUpdatingDao , loginCredentialsUpdatingDao);
    }
    
    @PostMapping ("/addv2") //musteri 
    public void addSelfCustomer (@RequestBody CustomerUpdatingDao customerUpdatingDao , @RequestBody LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) {
        customerService.addSelfCustomer(customerUpdatingDao,loginCredentialsUpdatingDao);
    }
    
    @PutMapping ("/updatev1") //admin ve mod
    public void updateCustomer (@RequestBody ElementIdDao elementIdDao , @RequestBody CustomerUpdatingDao customerUpdatingDao, @RequestBody LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) {
        customerService.updateCustomerById(elementIdDao,customerUpdatingDao , loginCredentialsUpdatingDao);
    }
    
    @PutMapping ("/updatev2") // musteri
    public void updateSelfCustomer (HttpServletRequest request , @RequestBody CustomerUpdatingDao customerUpdatingDao, @RequestBody LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        customerService.selfUpdateCustomer(id , customerUpdatingDao,loginCredentialsUpdatingDao);
    }
    
    @PostMapping ("/purchase") // musteri
    public void purchaseItem (HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        customerService.purchase(id);
    }
    
}
