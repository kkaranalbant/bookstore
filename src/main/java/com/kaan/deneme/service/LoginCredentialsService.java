/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.LoginCredentialsUpdatingDao;
import com.kaan.deneme.exception.InvalidAddingProcessException;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.model.Person;
import com.kaan.deneme.model.UserCredentials;
import com.kaan.deneme.repository.UserCredentialsRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class LoginCredentialsService {
    
    private UserCredentialsRepo userCredentialsRepo ;
    
    private static byte minPassLength ;
    
    private static byte maxPassLength ;
    
    
    static {
        minPassLength = 8 ;
        maxPassLength = 24 ;
    }
    
    @Autowired
    public LoginCredentialsService (UserCredentialsRepo userCredentialsRepo) {
        this.userCredentialsRepo = userCredentialsRepo ;
    }
    
    public void add (LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) throws InvalidAddingProcessException {
        if (loginCredentialsUpdatingDao.getUsername() == null || loginCredentialsUpdatingDao.getPassword() == null) {
            throw new InvalidAddingProcessException () ;
        }
        String username = loginCredentialsUpdatingDao.getUsername() ;
        Optional <UserCredentials> userCredentialsOptional = userCredentialsRepo.findByUsername(username);
        if (userCredentialsOptional.isEmpty()) {
            String password = loginCredentialsUpdatingDao.getPassword() ;
            if (isValidPass(password)) {
                UserCredentials userCredentials = new UserCredentials () ;
                userCredentials.setPerson(loginCredentialsUpdatingDao.getPerson());
                userCredentials.setUsername(username);
                userCredentials.setPassword(password);
                userCredentials.setRole(loginCredentialsUpdatingDao.getRole());
                userCredentialsRepo.save(userCredentials);
            }
        }
        else {
            throw new InvalidAddingProcessException () ;
        }
        
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
