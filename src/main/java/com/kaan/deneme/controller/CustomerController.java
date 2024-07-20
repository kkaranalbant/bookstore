/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.AddBalanceRequest;
import com.kaan.deneme.dao.CustomerAddingRequest;
import com.kaan.deneme.dao.CustomerUpdatingDao;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.RegisterRequest;
import com.kaan.deneme.dao.SelfCustomerUpdateRequest;
import com.kaan.deneme.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kaan.deneme.model.Customer;
import com.kaan.deneme.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author kaan
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;
    private JwtService jwtService;

    @Autowired
    public CustomerController(CustomerService customerService, JwtService jwtService) {
        this.customerService = customerService;
        this.jwtService = jwtService;
    }

    @GetMapping("/getv1-panel")
    public ModelAndView getV1Panel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("get-customer");
        return mv;
    }

    @GetMapping(value = "/getv1")
    public Customer getCustomerById(@RequestParam(name = "id") Long id) {
        return customerService.getCustomerById(id).get();
    }

    @GetMapping("/getv2-panel")
    public ModelAndView getV2Panel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("get-self-customer");
        return mv;
    }

    @GetMapping("/getv2") // musteri
    public Customer getCustomer(HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        return customerService.getCustomerById(id).get();
    }

    @GetMapping("/deletev1-panel")
    public ModelAndView getDeleteV1Panel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("delete-customer");
        return mv;
    }

    @DeleteMapping("/deletev1") // admin ve mod 
    public void deleteCustomerById(@RequestBody ElementIdDao elementIdDao) {
        customerService.removeCustomerById(elementIdDao);
    }

    @DeleteMapping("/deletev2") // musteri
    public void deleteCustomer(HttpServletRequest httpServletRequest) {
        String jwt = jwtService.getJwt(httpServletRequest);
        Long id = jwtService.getId(jwt);
        customerService.removeSelfCustomer(id);
    }

    @GetMapping("/addv1-panel")
    public ModelAndView getAddV1Panel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add-customer");
        return mv;
    }

    @PostMapping("/addv1") //admin ve mod
    public void addCustomer(@RequestBody CustomerAddingRequest customerAddingRequest) {
        customerService.addCustomer(customerAddingRequest);
    }

    @GetMapping("/register-panel")
    public ModelAndView getRegisterPanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add-self-customer");
        return mv;
    }

    @PostMapping("/addv2") //musteri 
    public ResponseEntity<String> addSelfCustomer(@RequestBody RegisterRequest registerRequest) {
        customerService.addSelfCustomer(registerRequest);
        return ResponseEntity.ok().body("Successful");
    }

    @GetMapping("/updatev1-panel")
    public ModelAndView getUpdateV1Panel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("update-customer");
        return mv;
    }

    @PutMapping("/updatev1") //admin ve mod
    public void updateCustomer(@RequestBody CustomerUpdatingDao customerUpdatingDao) {
        customerService.updateCustomerById(customerUpdatingDao);
    }

    @GetMapping("/updatev2-panel")
    public ModelAndView getUpdateV2Panel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("update-self-customer");
        return mv;
    }

    @PutMapping("/updatev2") // musteri
    public void updateSelfCustomer(HttpServletRequest request, @RequestBody SelfCustomerUpdateRequest selfCustomerUpdatingRequest) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        String username = jwtService.getUsername(jwt);
        customerService.selfUpdateCustomer(id, username, selfCustomerUpdatingRequest);
    }

    @PostMapping("/purchase") // musteri
    public void purchaseItem(HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long id = jwtService.getId(jwt);
        customerService.purchase(id);
    }

    @GetMapping("/add-balance-panel")
    public ModelAndView getPurchasePanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer-add-balance");
        return mv;
    }

    @PostMapping("/add-balance")
    public void addBalance(HttpServletRequest request, @RequestBody AddBalanceRequest addBalanceRequest) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        customerService.addBalance(customerId, addBalanceRequest);
    }

    @GetMapping("/main-panel")
    public ModelAndView getMainCustomerPanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer-main-panel");
        return mv;
    }

}
