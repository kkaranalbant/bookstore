/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.exception.OutOfStockException;
import com.kaan.deneme.model.Basket;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.model.Customer;
import com.kaan.deneme.repository.BasketRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class BasketService {

    private static Logger logger;

    static {
        logger = LoggerFactory.getLogger(BasketService.class);
    }

    private BasketRepo basketRepo;
    private BookService bookService;
    private CustomerService customerService;

    @Autowired
    public BasketService(BasketRepo basketRepo, BookService bookService, CustomerService customerService) {
        this.basketRepo = basketRepo;
        this.bookService = bookService;
        this.customerService = customerService;
    }

    public List<Book> getBasketByCustomerId(Long customerId) {
        List<Basket> baskets = basketRepo.findAllByCustomerId(customerId);
        List<Book> books = new ArrayList<>();
        for (Basket basket : baskets) {
            Optional<Book> bookOptional = bookService.getBookById(basket.getBook().getId());
            books.add(bookOptional.get());
        }
        return books;
    }

    public List<Basket> getAllBasketsContainsBookId(Long bookId) {
        return basketRepo.findAllByBookId(bookId);
    }

    public void removeFromBasketById(Long customerId, ElementIdDao bookIdDao, String ip) {
        Optional<Basket> basketOptional = basketRepo.findByCustomerIdAndBookId(customerId, bookIdDao.id());
        Basket basket = basketOptional.get();
        basketRepo.delete(basket);
        logger.info("IP address " + ip + " | Book with id number " + bookIdDao.id() + " was deleted from the cart of customer with id number " + customerId + ".");
    }

    public void truncateBasketByCustomerId(Long customerId, String ip) {
        List<Basket> baskets = basketRepo.findAllByCustomerId(customerId);
        basketRepo.deleteAllInBatch(baskets);
        logger.info("IP address " + ip + " | The basket of the customer with id number " + customerId.longValue() + " was emptied.");
    }

    public void addToBasketById(Long customerId, ElementIdDao bookIdDao, String ip) throws OutOfStockException {
        Optional<Book> bookOptional = bookService.getBookById(bookIdDao.id());
        Book book = bookOptional.get();
        if (book.getStockNumber() != 0) {
            Optional<Customer> customerOptional = customerService.getCustomerById(customerId);
            Basket basket = new Basket(customerOptional.get(), bookOptional.get());
            basketRepo.save(basket);
            logger.info("IP address " + ip + " | Book with id number " + bookIdDao.id().longValue() + " was added to basket of customer with id number " + customerId.longValue() + ".");
        } else {
            throw new OutOfStockException();
        }
    }

    public void deleteFromBasketsByBookId(String username, Long bookId, String ip) {
        basketRepo.deleteByBookId(bookId);
        logger.info("IP address " + ip + " | Person with username " + username + " deleted the book with id " + bookId.longValue() + " from all baskets.");
    }

    public void deleteBasketByCustomerId(String username, Long customerId, String ip) {
        basketRepo.deleteByCustomerId(customerId);
        logger.info("IP address " + ip + " | Person with username " + username + " deleted the basket of person with id number " + customerId.longValue() + ".");
    }

    void addBasket(List<Basket> baskets) {
        basketRepo.saveAll(baskets);
    }

}
