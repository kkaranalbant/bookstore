/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.CustomerUpdatingDao;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.LoginCredentialsUpdatingDao;
import com.kaan.deneme.exception.InvalidAddingProcessException;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.exception.InvalidUpdatingProcessException;
import com.kaan.deneme.exception.NotSufficentBalanceException;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.model.Customer;
import com.kaan.deneme.model.Role;
import com.kaan.deneme.repository.CustomerRepo;
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
    private LoginCredentialsService loginCredentialsService ;

    @Autowired
    public CustomerService(CustomerRepo customerRepo,@Lazy BasketService basketService, BookService bookService) {
        this.customerRepo = customerRepo;
        this.basketService = basketService;
        this.bookService = bookService;
    }

    public void removeCustomerById(ElementIdDao elementIdDao) throws InvalidIdException {
        long customerId = elementIdDao.id();
        Optional<Customer> customerOptional = customerRepo.findById(customerId);
        if (customerOptional.isPresent()) {
            loginCredentialsService.remove(customerOptional.get());
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

    public void updateCustomerById(ElementIdDao elementIdDao, CustomerUpdatingDao customerUpdatingDao , LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) throws InvalidUpdatingProcessException, InvalidIdException {
        Long id = elementIdDao.id();
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setName(customerUpdatingDao.getName());
            customer.setLastname(customerUpdatingDao.getLastname());
            customer.setGender(customerUpdatingDao.getGender());
            customer.setBirthDate(customerUpdatingDao.getBirthDate());
            customer.setBalance(customerUpdatingDao.getBalance());
            customer.setAddress(customerUpdatingDao.getAddress());
            loginCredentialsUpdatingDao.setPerson(customer);
            loginCredentialsUpdatingDao.setRole(Role.CUSTOMER);
            loginCredentialsService.add(loginCredentialsUpdatingDao);
            customerRepo.save(customer);
        } else {
            throw new InvalidIdException();
        }
    }

    public void selfUpdateCustomer(Long id, CustomerUpdatingDao customerUpdatingDao , LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) throws InvalidUpdatingProcessException {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.get();
        customer.setName(customerUpdatingDao.getName());
        customer.setLastname(customerUpdatingDao.getLastname());
        customer.setGender(customerUpdatingDao.getGender());
        customer.setBirthDate(customerUpdatingDao.getBirthDate());
        customer.setAddress(customerUpdatingDao.getAddress());
        loginCredentialsUpdatingDao.setPerson(customer);
        loginCredentialsUpdatingDao.setRole(Role.CUSTOMER);
        loginCredentialsService.add(loginCredentialsUpdatingDao);
    }

    public void addCustomer(ElementIdDao elementIdDao, CustomerUpdatingDao customerUpdatingDao , LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) throws InvalidAddingProcessException {
        Optional<Customer> customerOptional = customerRepo.findById(elementIdDao.id());
        if (customerOptional.isPresent()) {
            throw new InvalidAddingProcessException();
        } else {
            Customer customer = new Customer();
            customer.setName(customerUpdatingDao.getName());
            customer.setLastname(customerUpdatingDao.getLastname());
            customer.setGender(customerUpdatingDao.getGender());
            customer.setAddress(customerUpdatingDao.getAddress());
            customer.setBalance(customerUpdatingDao.getBalance());
            customer.setBirthDate(customerUpdatingDao.getBirthDate());
            if (elementIdDao.id() != null) {
                customer.setId(elementIdDao.id());
            }
            customerRepo.save(customer);
            Optional <Customer> customerOptional1 = customerRepo.findByNameAndLastname(customerUpdatingDao.getName(), customerUpdatingDao.getLastname());
            customer = customerOptional1.get() ;
            loginCredentialsUpdatingDao.setPerson(customer);
            loginCredentialsUpdatingDao.setRole(Role.CUSTOMER);
            loginCredentialsService.add(loginCredentialsUpdatingDao);
        }
    }

    public void addSelfCustomer(CustomerUpdatingDao customerUpdatingDao , LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) throws InvalidAddingProcessException {
        Customer customer = new Customer();
        customer.setName(customerUpdatingDao.getName());
        customer.setLastname(customerUpdatingDao.getLastname());
        customer.setGender(customerUpdatingDao.getGender());
        customer.setAddress(customerUpdatingDao.getAddress());
        customer.setBalance(0);
        customer.setBirthDate(customerUpdatingDao.getBirthDate());
        customerRepo.save(customer);
        Optional<Customer> customerOptional = customerRepo.findByNameAndLastname(customerUpdatingDao.getName(), customerUpdatingDao.getLastname()) ;
        customer = customerOptional.get() ;
        loginCredentialsUpdatingDao.setPerson(customer);
        loginCredentialsUpdatingDao.setRole(Role.CUSTOMER);
        loginCredentialsService.add(loginCredentialsUpdatingDao);
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
        } else {
            throw new NotSufficentBalanceException();
        }
    }

}
