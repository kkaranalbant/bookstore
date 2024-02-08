/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.service;

import com.kaan.bookstore.dto.CardCreatingRequest;
import com.kaan.bookstore.model.Card;
import com.kaan.bookstore.model.Customer;
import com.kaan.bookstore.repo.CardRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class CardService {

    private CardRepo cardRepo;
    private CustomerService customerService;

    public CardService(CardRepo cardRepo, CustomerService customerService) {
        this.cardRepo = cardRepo;
        this.customerService = customerService;
    }

    public void createCard(Customer customer, CardCreatingRequest cardCreatingRequest) {
        Card card = Card.builder()
                .cardNumber(cardCreatingRequest.cardNo())
                .cvv(cardCreatingRequest.cvv())
                .validMonth(cardCreatingRequest.validMonth())
                .validYear(cardCreatingRequest.validYear())
                .customer(customer)
                .build();
        cardRepo.save(card);
    }
    
    public List <Card> getAllCardsByCustomerId (Long customerId) {
        return cardRepo.findAllByCustomer(customerId);
    }

    public void removeCardByCardNumber(String cardNumber, Customer customer) {
        Optional<Card> cardOptional = cardRepo.findByCardNumberAndCustomer(cardNumber, customer.getId());
        cardRepo.delete(cardOptional.get());
    }

    public void removeCardByCardNumberAndCustomerId(String cardNumber, Long customerId) {
        Optional<Customer> customerOptional = customerService.getCustomerById(customerId);
        Optional<Card> cardOptional = cardRepo.findByCardNumberAndCustomer(cardNumber, customerOptional.get().getId());
        cardRepo.delete(cardOptional.get());
    }

    public void removeAllCards(Customer customer) {
        List<Card> cards = cardRepo.findAllByCustomer(customer.getId());
        cardRepo.deleteAll(cards);
    }

}
