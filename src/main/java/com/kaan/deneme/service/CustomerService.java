/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.AddBalanceRequest;
import com.kaan.deneme.dao.CustomerAddingRequest;
import com.kaan.deneme.dao.CustomerUpdatingDao;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.RegisterRequest;
import com.kaan.deneme.dao.SelfCustomerUpdateRequest;
import com.kaan.deneme.exception.InvalidAddingProcessException;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.exception.InvalidUpdatingProcessException;
import com.kaan.deneme.exception.NotSufficentBalanceException;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.model.Card;
import com.kaan.deneme.model.Customer;
import com.kaan.deneme.model.Role;
import com.kaan.deneme.model.UserCredentials;
import com.kaan.deneme.repository.CustomerRepo;
import jakarta.transaction.Transactional;
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
public class CustomerService {

    private CustomerRepo customerRepo;
    private BasketService basketService;
    private BookService bookService;
    private CommentService commentService ;
    private LoginCredentialsService loginCredentialsService ;
    private FavouriteService favService ;
    private CardService cardService ;

    @Autowired
    public CustomerService(CustomerRepo customerRepo,@Lazy BasketService basketService, BookService bookService , LoginCredentialsService loginCredentialsService , @Lazy CardService cardService , @Lazy FavouriteService favService ,@Lazy CommentService commentService) {
        this.customerRepo = customerRepo;
        this.basketService = basketService;
        this.bookService = bookService;
        this.loginCredentialsService = loginCredentialsService ;
        this.cardService = cardService ;
        this.commentService =commentService ;
        this.favService = favService ;
        this.commentService = commentService ;
    }

    @Transactional
    public void removeCustomerById(ElementIdDao elementIdDao) throws InvalidIdException {
        long customerId = elementIdDao.id();
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        if (customerOptional.isPresent()) {
            loginCredentialsService.remove(customerOptional.get());
            favService.deleteFavsByCustomerId(customerId);
            cardService.deleteCardByCustomerId(customerId);
            commentService.deleteCommentsByCustomerId(customerId);
            basketService.deleteBasketByCustomerId(customerId);
            customerRepo.deleteById(customerId);
        } else {
            throw new InvalidIdException();
        }
    }

    public void removeSelfCustomer(Long customerId) {
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        loginCredentialsService.remove(customerOptional.get());
        customerRepo.deleteById(customerId);
    }

    public void updateCustomerById(CustomerUpdatingDao customerUpdatingDao) throws InvalidUpdatingProcessException, InvalidIdException {
        Long id = customerUpdatingDao.getId() ;
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            UserCredentials userCredentials = loginCredentialsService.get(customer) ;
            customer.setName(customerUpdatingDao.getName());
            customer.setLastname(customerUpdatingDao.getLastname());
            customer.setGender(customerUpdatingDao.getGender());
            customer.setBirthDate(customerUpdatingDao.getBirthDate());
            customer.setBalance(customerUpdatingDao.getBalance());
            customer.setAddress(customerUpdatingDao.getAddress());
            loginCredentialsService.update(userCredentials.getUsername(), customerUpdatingDao.getUsername(), customerUpdatingDao.getPassword());
            customerRepo.save(customer);
        } else {
            throw new InvalidIdException();
        }
    }

    public void selfUpdateCustomer(Long id, String oldUsername ,SelfCustomerUpdateRequest selfCustomerUpdateRequest) throws InvalidUpdatingProcessException {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.get();
        customer.setName(selfCustomerUpdateRequest.getName());
        customer.setLastname(selfCustomerUpdateRequest.getLastname());
        customer.setGender(selfCustomerUpdateRequest.getGender());
        customer.setBirthDate(selfCustomerUpdateRequest.getBirthDate());
        customer.setAddress(selfCustomerUpdateRequest.getAddress());
        loginCredentialsService.update(oldUsername, selfCustomerUpdateRequest.getUsername(), selfCustomerUpdateRequest.getPassword());
    }

    public void addCustomer(CustomerAddingRequest customerAddingRequest) throws InvalidAddingProcessException {
            Customer customer = new Customer();
            customer.setName(customerAddingRequest.getName());
            customer.setLastname(customerAddingRequest.getLastname());
            customer.setGender(customerAddingRequest.getGender());
            customer.setAddress(customerAddingRequest.getAddress());
            customer.setBalance(customerAddingRequest.getBalance());
            customer.setBirthDate(customerAddingRequest.getBirthDate());
            
            customerRepo.save(customer);
            Optional <Customer> customerOptional1 = customerRepo.findByNameAndLastname(customerAddingRequest.getName(), customerAddingRequest.getLastname());
            customer = customerOptional1.get() ;
            loginCredentialsService.add(customerAddingRequest.getUsername(), customerAddingRequest.getPassword(), customer, Role.CUSTOMER);
        
    }

    public void addSelfCustomer(RegisterRequest registerRequest) throws InvalidAddingProcessException {
        Customer customer = new Customer();
        customer.setName(registerRequest.getName());
        customer.setLastname(registerRequest.getLastname());
        customer.setGender(registerRequest.getGender());
        customer.setAddress(registerRequest.getAddress());
        customer.setBalance(0);
        customer.setBirthDate(registerRequest.getBirthDate());
        customerRepo.save(customer);
        Optional<Customer> customerOptional = customerRepo.findByNameAndLastname(registerRequest.getName(), registerRequest.getLastname()) ;
        customer = customerOptional.get() ;
        loginCredentialsService.add(registerRequest.getUsername() , registerRequest.getPassword() , customer , Role.CUSTOMER);
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepo.findById(customerId);
    }

    public void purchase(Long customerId) throws NotSufficentBalanceException {
        /*
            JWT claiminden musterinin ID bilgisi alinacak .
         */
        List<Book> books = basketService.getBasketByCustomerId(customerId);
        float price = bookService.getAllBookPrice(books);
        Optional<Customer> customerOptional = getCustomerById(customerId);
        Customer customer = customerOptional.get();
        if (price <= customer.getBalance()) {
            float newBalance = customer.getBalance() - price;
            customer.setBalance(newBalance);
            customerRepo.save(customer);
            basketService.truncateBasketByCustomerId(customerId);
        } else {
            throw new NotSufficentBalanceException();
        }
    }
    
    public void addBalance (Long customerId , AddBalanceRequest addBalanceRequest) throws InvalidAddingProcessException {
        if (addBalanceRequest.getAmount() <= 0) throw new InvalidAddingProcessException () ;
        List <Card> cards = cardService.getCardsByCustomerId(customerId) ;
        boolean found = false ;
        for (Card card : cards) {
            if (card.getCardNo().equals(addBalanceRequest.getCardNo())) {
                found = true ;
                break ;
            }
        }
        if (found) {
            Customer customer = customerRepo.findById(customerId).get() ;
            customer.setBalance(customer.getBalance() + addBalanceRequest.getAmount());
            customerRepo.save(customer);
        }
        else {
            throw new InvalidAddingProcessException () ;
        }
    }

}
