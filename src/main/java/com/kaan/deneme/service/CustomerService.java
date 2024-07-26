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
import com.kaan.deneme.exception.InvalidVerificationException;
import com.kaan.deneme.exception.NotSufficentBalanceException;
import com.kaan.deneme.exception.OutOfStockException;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.model.Card;
import com.kaan.deneme.model.Customer;
import com.kaan.deneme.model.Gender;
import com.kaan.deneme.model.Role;
import com.kaan.deneme.model.UserCredentials;
import com.kaan.deneme.repository.CustomerRepo;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
    private static final short TOKEN_LENGTH;
    private static final byte LOWER_CASE;
    private static final byte UPPER_CASE;
    private static final byte DIGIT;
    private static final String VERIFY_ENDPOINT;
    private static final String RESET_PASS_ENDPOINT;

    private CustomerRepo customerRepo;
    private BasketService basketService;
    private BookService bookService;
    private CommentService commentService;
    private LoginCredentialsService loginCredentialsService;
    private FavouriteService favService;
    private CardService cardService;
    private EmailService emailService;
    private Random random;

    static {
        logger = LoggerFactory.getLogger(CustomerService.class);
        TOKEN_LENGTH = 64;
        LOWER_CASE = 0;
        UPPER_CASE = 1;
        DIGIT = 2;
        VERIFY_ENDPOINT = "http://localhost:8080/customer/verify?code=";
        RESET_PASS_ENDPOINT = "http://localhost:8080/customer/pass-reset-panel?token=";
    }

    @Autowired
    public CustomerService(CustomerRepo customerRepo, @Lazy BasketService basketService, BookService bookService, LoginCredentialsService loginCredentialsService, @Lazy CardService cardService, @Lazy FavouriteService favService, @Lazy CommentService commentService, EmailService emailService) {
        this.customerRepo = customerRepo;
        this.basketService = basketService;
        this.bookService = bookService;
        this.loginCredentialsService = loginCredentialsService;
        this.cardService = cardService;
        this.commentService = commentService;
        this.favService = favService;
        this.commentService = commentService;
        this.emailService = emailService;
        random = new Random();
    }

    @Transactional
    public void removeCustomerById(String username, ElementIdDao elementIdDao, String ip) throws InvalidIdException {
        long customerId = elementIdDao.id();
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        if (customerOptional.isPresent()) {
            loginCredentialsService.remove(username, customerOptional.get(), ip);
            favService.deleteFavsByCustomerId(username, customerId, ip);
            cardService.deleteCardByCustomerId(customerId, ip);
            commentService.deleteCommentsByCustomerId(username, customerId, ip);
            basketService.deleteBasketByCustomerId(username, customerId, ip);
            customerRepo.deleteById(customerId);
            logger.info("person with username " + username + " deleted customer with id number " + customerId + ".\nIP : " + ip);
        } else {
            throw new InvalidIdException();
        }
    }

    public void removeSelfCustomer(Long customerId, String ip) {
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        Customer customer = customerOptional.get();
        UserCredentials cred = loginCredentialsService.get(customer);
        loginCredentialsService.remove(cred.getUsername(), customer, ip);
        favService.deleteFavsByCustomerId(cred.getUsername(), customerId, ip);
        cardService.deleteCardByCustomerId(customerId, ip);
        commentService.deleteCommentsByCustomerId(cred.getUsername(), customerId, ip);
        basketService.deleteBasketByCustomerId(cred.getUsername(), customerId, ip);
        customerRepo.deleteById(customerId);
        logger.info("Customer deleted his account.\n"
                + "By : " + cred.getUsername() + "\n"
                + "IP : " + ip);
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
            loginCredentialsService.update(username, userCredentials.getUsername(), customerUpdatingDao.getUsername(), customerUpdatingDao.getPassword(), ip);
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
        loginCredentialsService.update(oldUsername, oldUsername, selfCustomerUpdateRequest.getUsername(), selfCustomerUpdateRequest.getPassword(), ip);
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
        loginCredentialsService.add(username, customerAddingRequest.getUsername(), customerAddingRequest.getPassword(), customer, Role.CUSTOMER, ip);
        logger.info("Person with username " + username + " added customer with id number " + customer.getId().longValue() + ".\n"
                + "IP : " + ip);
    }

    @Transactional
    public void addSelfCustomer(RegisterRequest registerRequest, String ip) throws InvalidAddingProcessException, MessagingException, UnsupportedEncodingException {
        Customer customer = new Customer();
        customer.setName(registerRequest.getName());
        customer.setLastname(registerRequest.getLastname());
        customer.setGender(registerRequest.getGender());
        customer.setAddress(registerRequest.getAddress());
        customer.setBalance(0);
        customer.setBirthDate(registerRequest.getBirthDate());
        customer.setEmail(registerRequest.getEmail());
        String token = createToken();
        customer.setVerificationCode(token);
        customer.setEnabled(false);
        customerRepo.save(customer);
        Optional<Customer> customerOptional = customerRepo.findByNameAndLastname(registerRequest.getName(), registerRequest.getLastname());
        customer = customerOptional.get();
        loginCredentialsService.add(registerRequest.getUsername(), registerRequest.getUsername(), registerRequest.getPassword(), customer, Role.CUSTOMER, ip);
        String url = VERIFY_ENDPOINT.concat(token);
        emailService.sendVerificationEmail(registerRequest.getEmail(), registerRequest.getName() + " " + registerRequest.getLastname(), url);
        logger.info("Customer with id number " + customer.getId().longValue() + " has registered.\n"
                + "Verification stage is not complete !"
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
            basketService.truncateBasketByCustomerId(customerId, ip);
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

    public void emailVerification(String verificationCode) throws InvalidVerificationException {
        Optional<Customer> customerOptional = customerRepo.findByVerificationCode(verificationCode);
        if (customerOptional.isEmpty() || customerOptional.get().getEnabled() || verificationCode == null) {
            throw new InvalidVerificationException();
        } else {
            customerOptional.get().setEnabled(true);
            customerOptional.get().setVerificationCode(null);
            customerRepo.save(customerOptional.get());
        }
    }

    @Transactional
    public void sendPassResetMail(String email) throws MessagingException, UnsupportedEncodingException {
        Optional<Customer> customerOptional = customerRepo.findByEmail(email);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            String token = createToken();
            customer.setVerificationCode(token);
            customerRepo.save(customer);
            emailService.sendResetMail(email, customer.getName() + " " + customer.getLastname(), RESET_PASS_ENDPOINT.concat(token));
        }
    }

    public void verifyPasswordReset(String token, String newPassword, String ip) throws InvalidVerificationException, InvalidUpdatingProcessException {
        if (token == null) {
            throw new InvalidVerificationException();
        }
        Optional<Customer> customerOptional = customerRepo.findByVerificationCode(token);
        if (customerOptional.isEmpty()) {
            throw new InvalidVerificationException();
        }
        Customer customer = customerOptional.get();
        UserCredentials credentials = loginCredentialsService.get(customer);
        loginCredentialsService.update(credentials.getUsername(), credentials.getUsername(), credentials.getUsername(), newPassword, ip);
    }

    private String createToken() {
        StringBuilder sb = new StringBuilder();
        while (sb.length() != 64) {
            int choice = random.nextInt(3);
            if (choice == LOWER_CASE) {
                sb.append(createLowerCase());
            } else if (choice == UPPER_CASE) {
                sb.append(createUpperCase());
            } else {
                sb.append(createNumber());
            }
        }
        return sb.toString();
    }

    private char createUpperCase() {
        return (char) (random.nextInt(65, 91));
    }

    private char createLowerCase() {
        return (char) (random.nextInt(97, 123));
    }

    private int createNumber() {
        return random.nextInt(48, 58);
    }
    
    public static short getTokenLength () {
        return TOKEN_LENGTH ;
    }

}
