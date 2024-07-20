/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.CardAddingDao;
import com.kaan.deneme.dao.CardRemovingDao;
import com.kaan.deneme.exception.InvalidCredentialsException;
import com.kaan.deneme.exception.NotUniqueCardException;
import com.kaan.deneme.exception.UnauthorizedCardProcessException;
import com.kaan.deneme.model.Card;
import com.kaan.deneme.model.Customer;
import com.kaan.deneme.repository.CardRepo;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class CardService {
    
    private CardRepo cardRepo ;
    private CustomerService customerService ;

    @Autowired
    public CardService(CardRepo cardRepo , @Lazy CustomerService customerService) {
        this.cardRepo = cardRepo;
        this.customerService = customerService ;
    }
    
    public void addCardById (Long customerId , CardAddingDao cardAddingDao) throws InvalidCredentialsException , NotUniqueCardException {
        String cardNo = cardAddingDao.getCardNo() ;
        if (cardNo.length() != 16) {
            throw new InvalidCredentialsException () ;
        }
        Optional<Card> cardOptional = cardRepo.findByCardNo(cardNo) ;
        if (cardOptional.isPresent()) {
            throw new NotUniqueCardException () ;
        }
        String cvv = cardAddingDao.getCvv() ;
        if (cvv.length() != 3) {
            throw new InvalidCredentialsException () ;
        }
        byte month = cardAddingDao.getMonth() ;
        if (!(month >= 1 && month <= 12)) {
            throw new InvalidCredentialsException () ;
        }
        LocalDate localDate = LocalDate.of(cardAddingDao.getYear(), month , 1) ;
        if (LocalDate.now().isAfter(localDate)) {
            throw new InvalidCredentialsException () ;
        }
        Card card = new Card () ;
        card.setCardNo(cardNo);
        card.setCvv(cvv);
        card.setMonth(month);
        card.setYear(cardAddingDao.getYear());
        Optional <Customer> customerOptional = customerService.getCustomerById(customerId);
        card.setCustomer(customerOptional.get());
        cardRepo.save(card);
    }
    
    @Transactional
    public void removeCardById (Long customerId , CardRemovingDao cardRemovingDao) throws InvalidCredentialsException , UnauthorizedCardProcessException{
        String cardNo = cardRemovingDao.getCardNo() ;
        Optional <Card> cardOptional =  cardRepo.findByCardNo(cardNo);
        if (cardOptional.isEmpty()) {
            throw new InvalidCredentialsException () ;
        }
        Card card = cardOptional.get() ;
        if (card.getCustomer().getId().longValue() != customerId.longValue()) {
            throw new UnauthorizedCardProcessException () ;
        }
        cardRepo.deleteByCardNo(cardNo);
    }
    
    public List <Card> getCardsByCustomerId (Long customerId) {
        return cardRepo.findAllByCustomerId(customerId);
    }
    
    public void deleteCardByCustomerId (Long customerId) {
        cardRepo.deleteByCustomerId(customerId);
    }
    
}
