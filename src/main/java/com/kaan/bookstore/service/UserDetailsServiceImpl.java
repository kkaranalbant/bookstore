/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.service;

import com.kaan.bookstore.model.Customer;
import com.kaan.bookstore.model.Executive;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private CustomerService customerService;
    private ExecutiveService executiveService;

    public UserDetailsServiceImpl(CustomerService customerService, ExecutiveService executiveService) {
        this.customerService = customerService;
        this.executiveService = executiveService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = customerService.getCustomerByUsername(username);
        Optional<Executive> executiveOptional = null;
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        } else {
            executiveOptional = executiveService.getExecutiveByUsername(username);
            return executiveOptional.orElseThrow(() -> new EntityNotFoundException());
        }
    }

}
