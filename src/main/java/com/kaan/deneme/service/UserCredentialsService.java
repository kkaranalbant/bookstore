/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.model.UserCredentials;
import com.kaan.deneme.repository.UserCredentialsRepo;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class UserCredentialsService implements UserDetailsService {
    
    private UserCredentialsRepo userCredentialsRepo ;
    
    
    @Autowired
    public UserCredentialsService(UserCredentialsRepo userCredetialsRepo) {
        this.userCredentialsRepo = userCredetialsRepo;
    }
  
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentials> userOptional = userCredentialsRepo.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException ("User Not Found") ;
        }
        return userOptional.get() ;
    }
    
    
    public Long getIdByUsername (String username) throws UsernameNotFoundException {
        Optional<UserCredentials> userOptional = userCredentialsRepo.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException ("User Not Found") ;
        }
        return userOptional.get().getPerson().getId() ;
    }

}
