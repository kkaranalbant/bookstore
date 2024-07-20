/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.exception.InvalidAddingProcessException;
import com.kaan.deneme.exception.InvalidUpdatingProcessException;
import com.kaan.deneme.model.Person;
import com.kaan.deneme.model.Role;
import com.kaan.deneme.model.UserCredentials;
import com.kaan.deneme.repository.AdminRepo;
import com.kaan.deneme.repository.UserCredentialsRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class LoginCredentialsService {
    
    private UserCredentialsRepo userCredentialsRepo ;
    
    private BCryptPasswordEncoder bCryptPasswordEncoder ;
    
    private AdminRepo adminRepo ;
    
    private static byte minPassLength ;
    
    private static byte maxPassLength ;
    
    
    static {
        minPassLength = 8 ;
        maxPassLength = 24 ;
    }
    
    @Autowired
    public LoginCredentialsService (UserCredentialsRepo userCredentialsRepo , BCryptPasswordEncoder bCryptPasswordEncoder , AdminRepo adminRepo) {
        this.userCredentialsRepo = userCredentialsRepo ;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder ;
        this.adminRepo = adminRepo ;
    }
    
    public void add (String username , String password , Person person , Role role) throws InvalidAddingProcessException {
        if (username == null || password == null) {
            throw new InvalidAddingProcessException () ;
        }
        Optional <UserCredentials> userCredentialsOptional = userCredentialsRepo.findByUsername(username);
        if (userCredentialsOptional.isEmpty()) {
            if (isValidPass(password)) {
                UserCredentials userCredentials = new UserCredentials () ;
                userCredentials.setPerson(person);
                userCredentials.setUsername(username);
                userCredentials.setPassword(bCryptPasswordEncoder.encode(password));
                userCredentials.setRole(role);
                userCredentialsRepo.save(userCredentials);
            }
        }
        else {
            throw new InvalidAddingProcessException () ;
        }
        
    }
    
    
    public void update (String oldUsername ,String username , String password ) throws InvalidUpdatingProcessException {
        if (username == null || password == null || !isValidPass(password)) {
            throw new InvalidUpdatingProcessException () ;
        }
        Optional <UserCredentials> newUsernameCredententialOptional = userCredentialsRepo.findByUsername(username) ;
        if (newUsernameCredententialOptional.isPresent()) {
            throw new InvalidUpdatingProcessException () ;
        }
        Optional <UserCredentials> userCredentialsOptional = userCredentialsRepo.findByUsername(oldUsername);
        UserCredentials oldUserCredentials = userCredentialsOptional.get() ;
        oldUserCredentials.setUsername(username);
        oldUserCredentials.setPassword(bCryptPasswordEncoder.encode(password));
        userCredentialsRepo.save(oldUserCredentials);
    }
    
    public void remove (Person person) {
        UserCredentials userCredentials = userCredentialsRepo.findByPerson(person).get() ;
        userCredentialsRepo.delete(userCredentials);
    }
    
    public UserCredentials get (Person person) {
        return userCredentialsRepo.findByPerson(person).get() ;
    }
    
    private boolean isValidPass (String password) {
        boolean hasUpperChar = false ;
        boolean hasDigit = false ;
        if (!(password.length()>= minPassLength && password.length() <= maxPassLength)) {
            return false ;
        }
        for (int i = 0 ; i < password.length() && (!hasUpperChar || !hasDigit) ; i++) {
            if (Character.isDigit(password.charAt(i))) {
                hasDigit = true ;
                continue;
            }
            if (Character.isUpperCase(password.charAt(i))) {
                hasUpperChar = true ;
            }
        }
        return hasDigit && hasUpperChar ;
    }
    
    
}
