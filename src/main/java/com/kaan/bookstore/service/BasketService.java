/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.service;

import com.kaan.bookstore.model.Basket;
import com.kaan.bookstore.model.Book;
import com.kaan.bookstore.model.Customer;
import com.kaan.bookstore.repo.BasketRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class BasketService {

    private BasketRepo basketRepo;
    private BookService bookService ;

    public BasketService(BasketRepo basketRepo) {
        this.basketRepo = basketRepo;
    }

    public void addBookToBasket(Customer customer, Book book) {
        Basket basket = Basket.builder()
                .customer(customer)
                .book(book)
                .build();
        basketRepo.save(basket);
    }

    public void removeBookFromBasket(Customer customer, Book book) {
        Optional<Basket> basketOptional = basketRepo.findByCustomer_IdAndBook_Id(customer.getId(), book.getId());
        basketRepo.delete(basketOptional.get());
    }

    public void emptyBasket(Customer customer) {
        List<Basket> booksOfCustomer = basketRepo.findAllByCustomer_Id(customer.getId());
        basketRepo.deleteAllInBatch(booksOfCustomer);
    }

    public Double getBasketPrice (Customer customer) {
        List<Basket> booksOfCustomer = basketRepo.findAllByCustomer_Id(customer.getId());
        Double result = 0.0;
        for (Basket basket : booksOfCustomer) {
            result+=basket.getBook().getPrice();
        }
        return result ;
    }
    
}
