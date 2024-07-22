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
import com.kaan.deneme.exception.OutOfStockException;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.model.Card;
import com.kaan.deneme.model.Customer;
import com.kaan.deneme.model.Gender;
import com.kaan.deneme.model.Role;
import com.kaan.deneme.model.UserCredentials;
import com.kaan.deneme.repository.CustomerRepo;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class CustomerService {

    private static Logger logger;

    private CustomerRepo customerRepo;
    private BasketService basketService;
    private BookService bookService;
    private CommentService commentService;
    private LoginCredentialsService loginCredentialsService;
    private FavouriteService favService;
    private CardService cardService;

    static {
        logger = LoggerFactory.getLogger(CustomerService.class);
    }

    @Autowired
    public CustomerService(CustomerRepo customerRepo, @Lazy BasketService basketService, BookService bookService, LoginCredentialsService loginCredentialsService, @Lazy CardService cardService, @Lazy FavouriteService favService, @Lazy CommentService commentService) {
        this.customerRepo = customerRepo;
        this.basketService = basketService;
        this.bookService = bookService;
        this.loginCredentialsService = loginCredentialsService;
        this.cardService = cardService;
        this.commentService = commentService;
        this.favService = favService;
        this.commentService = commentService;
    }

    @Transactional
    public void removeCustomerById(String username, ElementIdDao elementIdDao,String ip) throws InvalidIdException {
        long customerId = elementIdDao.id();
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        if (customerOptional.isPresent()) {
            loginCredentialsService.remove(username, customerOptional.get(),ip);
            favService.deleteFavsByCustomerId(username, customerId,ip);
            cardService.deleteCardByCustomerId(customerId,ip);
            commentService.deleteCommentsByCustomerId(username, customerId,ip);
            basketService.deleteBasketByCustomerId(username, customerId,ip);
            customerRepo.deleteById(customerId);
            logger.info("person with username " + username + " deleted customer with id number " + customerId + ".\nIP : "+ip);
        } else {
            throw new InvalidIdException();
        }
    }

    public void removeSelfCustomer(Long customerId,String ip) {
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        Customer customer = customerOptional.get();
        UserCredentials cred = loginCredentialsService.get(customer);
        loginCredentialsService.remove(cred.getUsername(), customer,ip);
        favService.deleteFavsByCustomerId(cred.getUsername(), customerId,ip);
        cardService.deleteCardByCustomerId(customerId,ip);
        commentService.deleteCommentsByCustomerId(cred.getUsername(), customerId,ip);
        basketService.deleteBasketByCustomerId(cred.getUsername(), customerId,ip);
        customerRepo.deleteById(customerId);
        logger.info("Customer deleted his account.\n"
                + "By : " + cred.getUsername()+"\n"
                        + "IP : "+ip);
    }

    public void updateCustomerById(String username, CustomerUpdatingDao customerUpdatingDao, String ip) throws InvalidUpdatingProcessException, InvalidIdException {
        Long id = customerUpdatingDao.getId();
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            String oldName = customer.getName();
            String oldLastname = customer.getLastname();
            Float oldBalance = customer.getBalance();
            String oldAddress = customer.getAddress();
            LocalDate oldBirthDay = customer.getBirthDate();
            Gender oldGender = customer.getGender();
            UserCredentials userCredentials = loginCredentialsService.get(customer);
            String oldUsername = userCredentials.getUsername();
            customer.setName(customerUpdatingDao.getName());
            customer.setLastname(customerUpdatingDao.getLastname());
            customer.setGender(customerUpdatingDao.getGender());
            customer.setBirthDate(customerUpdatingDao.getBirthDate());
            customer.setBalance(customerUpdatingDao.getBalance());
            customer.setAddress(customerUpdatingDao.getAddress());
            loginCredentialsService.update(username, userCredentials.getUsername(), customerUpdatingDao.getUsername(), customerUpdatingDao.getPassword(),ip);
            customerRepo.save(customer);
            logger.info("A person with username " + username + " changed the information of a customer with id number " + id + ". \n"
                    + "Name : " + oldName + "->" + customerUpdatingDao.getName() + "\n"
                    + "Lastname : " + oldLastname + "->" + customerUpdatingDao.getLastname() + "\n"
                    + "Balance : " + oldBalance.floatValue() + "" + customerUpdatingDao.getBalance() + "\n"
                    + "Address : " + oldAddress + "->" + customerUpdatingDao.getAddress() + "\n"
                    + "Gender : " + oldGender.name() + "->" + customerUpdatingDao.getGender().name() + "\n"
                    + "Birth Day : " + oldBirthDay.toString() + "->" + customerUpdatingDao.getBirthDate().toString() + "\n"
                    + "Username : " + oldUsername + "->" + customerUpdatingDao.getUsername() + "\n"
                    + "IP : " + ip);
        } else {
            throw new InvalidIdException();
        }
    }

    public void selfUpdateCustomer(Long id, String oldUsername, SelfCustomerUpdateRequest selfCustomerUpdateRequest, String ip) throws InvalidUpdatingProcessException {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.get();
        customer.setId(id);
        String oldName = customer.getName();
        String oldLastname = customer.getLastname();
        String oldAddress = customer.getAddress();
        Gender oldGender = customer.getGender();
        LocalDate oldBirthDay = customer.getBirthDate();
        customer.setName(selfCustomerUpdateRequest.getName());
        customer.setLastname(selfCustomerUpdateRequest.getLastname());
        customer.setGender(selfCustomerUpdateRequest.getGender());
        customer.setBirthDate(selfCustomerUpdateRequest.getBirthDate());
        customer.setAddress(selfCustomerUpdateRequest.getAddress());
        customerRepo.save(customer);
        loginCredentialsService.update(oldUsername, oldUsername, selfCustomerUpdateRequest.getUsername(), selfCustomerUpdateRequest.getPassword(),ip);
        logger.info("The customer with id number " + id + " changed his information."
                + "Name : " + oldName + "->" + selfCustomerUpdateRequest.getName() + "\n"
                + "Lastname : " + oldLastname + "->" + selfCustomerUpdateRequest.getLastname() + "\n"
                + "Address : " + oldAddress + "->" + selfCustomerUpdateRequest.getAddress() + "\n"
                + "Gender : " + oldGender.name() + "->" + selfCustomerUpdateRequest.getGender().name() + "\n"
                + "Birth Day : " + oldBirthDay.toString() + "->" + selfCustomerUpdateRequest.getBirthDate().toString() + "\n"
                + "Username : " + oldUsername + "->" + selfCustomerUpdateRequest.getUsername() + "\n"
                + "IP :" + ip);
    }

    public void addCustomer(String username, CustomerAddingRequest customerAddingRequest, String ip) throws InvalidAddingProcessException {
        Customer customer = new Customer();
        customer.setName(customerAddingRequest.getName());
        customer.setLastname(customerAddingRequest.getLastname());
        customer.setGender(customerAddingRequest.getGender());
        customer.setAddress(customerAddingRequest.getAddress());
        customer.setBalance(customerAddingRequest.getBalance());
        customer.setBirthDate(customerAddingRequest.getBirthDate());
        customerRepo.save(customer);
        Optional<Customer> customerOptional1 = customerRepo.findByNameAndLastname(customerAddingRequest.getName(), customerAddingRequest.getLastname());
        customer = customerOptional1.get();
        loginCredentialsService.add(username, customerAddingRequest.getUsername(), customerAddingRequest.getPassword(), customer, Role.CUSTOMER,ip);
        logger.info("Person with username " + username + " added customer with id number " + customer.getId().longValue() + ".\n"
                + "IP : " + ip);
    }

    public void addSelfCustomer(RegisterRequest registerRequest, String ip) throws InvalidAddingProcessException {
        Customer customer = new Customer();
        customer.setName(registerRequest.getName());
        customer.setLastname(registerRequest.getLastname());
        customer.setGender(registerRequest.getGender());
        customer.setAddress(registerRequest.getAddress());
        customer.setBalance(0);
        customer.setBirthDate(registerRequest.getBirthDate());
        customerRepo.save(customer);
        Optional<Customer> customerOptional = customerRepo.findByNameAndLastname(registerRequest.getName(), registerRequest.getLastname());
        customer = customerOptional.get();
        loginCredentialsService.add(registerRequest.getUsername(), registerRequest.getUsername(), registerRequest.getPassword(), customer, Role.CUSTOMER,ip);
        logger.info("Customer with id number " + customer.getId().longValue() + " has registered.\n"
                + "IP : " + ip);
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepo.findById(customerId);
    }

    public void purchase(Long customerId, String ip) throws NotSufficentBalanceException, OutOfStockException {

        List<Book> books = basketService.getBasketByCustomerId(customerId);
        float price = bookService.getAllBookPrice(books);
        Optional<Customer> customerOptional = getCustomerById(customerId);
        Customer customer = customerOptional.get();
        if (price <= customer.getBalance()) {
            float newBalance = customer.getBalance() - price;
            customer.setBalance(newBalance);
            customerRepo.save(customer);
            basketService.truncateBasketByCustomerId(customerId,ip);
            bookService.decreaseStockNumberOfBooks(books);
            logger.info("A customer with id number " + customerId + " made a purchase.\n"
                    + "IP : " + ip);
        } else {
            throw new NotSufficentBalanceException();
        }
    }

    public void addBalance(Long customerId, AddBalanceRequest addBalanceRequest, String ip) throws InvalidAddingProcessException {
        if (addBalanceRequest.getAmount() <= 0) {
            throw new InvalidAddingProcessException();
        }
        List<Card> cards = cardService.getCardsByCustomerId(customerId);
        boolean found = false;
        for (Card card : cards) {
            if (card.getCardNo().equals(addBalanceRequest.getCardNo())) {
                found = true;
                break;
            }
        }
        if (found) {
            Customer customer = customerRepo.findById(customerId).get();
            customer.setBalance(customer.getBalance() + addBalanceRequest.getAmount());
            customerRepo.save(customer);
            logger.info("Customer with id number " + customerId + " has topped up his account : (" + addBalanceRequest.getAmount() + " TL)"
                    + "\n Card No : " + addBalanceRequest.getCardNo() + ""
                    + "\nIP : " + ip);
        } else {
            throw new InvalidAddingProcessException();
        }
    }

}
