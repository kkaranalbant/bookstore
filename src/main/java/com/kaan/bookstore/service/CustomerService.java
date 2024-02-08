/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.service;

import com.kaan.bookstore.dto.CustomerUpdatingRequest;
import com.kaan.bookstore.dto.CustomerUpdatingRequestForMod;
import com.kaan.bookstore.dto.UserCreatingRequest;
import com.kaan.bookstore.exception.EmptyCardListException;
import com.kaan.bookstore.exception.InvalidMoneyAmountException;
import com.kaan.bookstore.exception.NotSufficentBalanceException;
import com.kaan.bookstore.model.Card;
import com.kaan.bookstore.model.Customer;
import com.kaan.bookstore.model.Role;
import com.kaan.bookstore.repo.CustomerRepo;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class CustomerService {

    private CustomerRepo customerRepo;
    private BasketService basketService;
    private BCryptPasswordEncoder pwEncoder;
    private CardService cardService;

    public CustomerService(CustomerRepo customerRepo, BasketService basketService, BCryptPasswordEncoder pwEncoder, CardService cardService) {
        this.customerRepo = customerRepo;
        this.basketService = basketService;
        this.pwEncoder = pwEncoder;
        this.cardService = cardService;
    }

    public void createCustomer(UserCreatingRequest userAuthRequest) {
        Customer customer = Customer.builder()
                .name(userAuthRequest.name())
                .lastname(userAuthRequest.lastname())
                .email(userAuthRequest.email())
                .phoneNumber(userAuthRequest.phoneNumber())
                .username(userAuthRequest.username())
                .password(pwEncoder.encode(userAuthRequest.password()))
                .authorities(Set.of(Role.CUSTOMER))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .build();
        customerRepo.save(customer);
    }

    public void updateCustomer(Customer customer, CustomerUpdatingRequest customerUpdatingRequest) {
        customer.setName(customerUpdatingRequest.name());
        customer.setLastname(customerUpdatingRequest.lastname());
        customer.setUsername(customerUpdatingRequest.username());
        customer.setPassword(pwEncoder.encode(customerUpdatingRequest.password()));
        customer.setEmail(customerUpdatingRequest.email());
        customer.setPhoneNumber(customerUpdatingRequest.phoneNumber());
        customer.setAddress(customerUpdatingRequest.address());
        customerRepo.save(customer);
    }

    public void updateCustomerById(Long id, CustomerUpdatingRequestForMod customerUpdatingRequestForMod) throws InvalidMoneyAmountException {
        throwExceptionIfInvalidMoneyAmount(customerUpdatingRequestForMod.balance());
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.get();
        customer.setName(customerUpdatingRequestForMod.name());
        customer.setLastname(customerUpdatingRequestForMod.lastname());
        customer.setEmail(customerUpdatingRequestForMod.email());
        customer.setPhoneNumber(customerUpdatingRequestForMod.phoneNumber());
        customer.setUsername(customerUpdatingRequestForMod.username());
        customer.setPassword(pwEncoder.encode(customerUpdatingRequestForMod.password()));
        customer.setBalance(customerUpdatingRequestForMod.balance());
        customer.setAddress(customerUpdatingRequestForMod.address());
        customer.setAccountNonExpired(customerUpdatingRequestForMod.accountNonExpired());
        customer.setAccountNonLocked(customerUpdatingRequestForMod.accountNonLocked());
        customer.setCredentialsNonExpired(customerUpdatingRequestForMod.credentialsNonLocked());
        customer.setEnabled(customerUpdatingRequestForMod.isEnabled());
        customerRepo.save(customer);
    }

    public void topUpMoneyToAccount(Customer customer, Double amount) throws InvalidMoneyAmountException {
        throwExceptionIfInvalidMoneyAmount(amount);
        throwExceptionIfEmptyCardList(cardService.getAllCardsByCustomerId(customer.getId()));
        customer.setBalance(customer.getBalance() + amount);
        customerRepo.save(customer);
    }

    public void buyBooks(Customer customer) throws NotSufficentBalanceException {
        Double basketPrice = basketService.getBasketPrice(customer);
        throwExceptionIfNotSufficentMoneyAmount(customer, basketPrice);
        customer.setBalance(customer.getBalance() - basketPrice);
        basketService.emptyBasket(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepo.findById(id);
    }
    
    public Optional<Customer> getCustomerByUsername (String username) {
        return customerRepo.findCustomerByUsername(username);
    }

    private void throwExceptionIfNotSufficentMoneyAmount(Customer customer, Double basketPrice) throws NotSufficentBalanceException {
        if (isSufficentMoneyAmount(customer, basketPrice)) {
            throw new NotSufficentBalanceException();
        }
    }

    private boolean isSufficentMoneyAmount(Customer customer, Double basketPrice) {
        return customer.getBalance() >= basketPrice;
    }

    private void throwExceptionIfInvalidMoneyAmount(Double amount) throws InvalidMoneyAmountException {
        if (isValidMoneyAmount(amount)) {
            throw new InvalidMoneyAmountException();
        }
    }

    private boolean isValidMoneyAmount(Double amount) {
        return amount > 0;
    }

    private void throwExceptionIfEmptyCardList(List<Card> cards) throws EmptyCardListException {
        if (cards.isEmpty()) {
            throw new EmptyCardListException();
        }
    }

    /*
    public void addBookToBasket (Customer customer , Book book) {
        basketService.addBookToBasket(customer, book);
    }
    
    public void removeBookFromBasket (Customer customer , Book book) {
        basketService.removeBookFromBasket(customer, book);
    }
    
    public void emptyBasket (Customer customer) {
        basketService.emptyBasket(customer);
    }
     */
}
