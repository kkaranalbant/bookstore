/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.FavouriteUpdatingDao;
import com.kaan.deneme.exception.InvalidAddingProcessException;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.exception.InvalidUpdatingProcessException;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.model.Customer;
import com.kaan.deneme.model.Favourite;
import com.kaan.deneme.repository.FavouriteRepo;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class FavouriteService {

    private FavouriteRepo favouriteRepo;

    private BookService bookService;
    
    private CustomerService customerService ;

    @Autowired
    public FavouriteService(FavouriteRepo favouriteRepo, BookService bookService , CustomerService customerService) {
        this.favouriteRepo = favouriteRepo;
        this.bookService = bookService;
        this.customerService = customerService ;
    }

    public void addFavourite(Long customerId, FavouriteUpdatingDao favouriteUpdatingDao) throws InvalidIdException, InvalidAddingProcessException {
        for (Favourite fav : getAllFavuriteByCustomerId(customerId)) {
            if (fav.getBook().getId().longValue() == favouriteUpdatingDao.getBookId().longValue()) {
                throw new InvalidAddingProcessException();
            }
        }
        Favourite fav = new Favourite () ;
        Optional <Book> bookOptional = bookService.getBookById(favouriteUpdatingDao.getBookId());
        fav.setBook(bookOptional.get());
        Optional <Customer> customerOptional = customerService.getCustomerById(customerId) ;
        fav.setCustomer(customerOptional.get());
        favouriteRepo.save(fav);
    }

    @Transactional
    public void removeFavourite(Long customerId, FavouriteUpdatingDao favouriteUpdatingDao) throws InvalidIdException, InvalidUpdatingProcessException {
        boolean found = false;
        for (Favourite fav : getAllFavuriteByCustomerId(customerId)) {
            if (fav.getBook().getId().longValue() == favouriteUpdatingDao.getBookId().longValue()) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new InvalidUpdatingProcessException();
        }
        favouriteRepo.deleteByCustomerIdAndBookId(customerId, favouriteUpdatingDao.getBookId());
    }

    public List<Favourite> getAllFavuriteByCustomerId(Long customerId) {
        return favouriteRepo.findAllByCustomerId(customerId);
    }
    
    public List <Favourite> getAllFavouriteByBookId (Long bookId) {
        return favouriteRepo.findAllByBookId(bookId) ;
    }

    public int getAllFavouriteNumberByBookId(ElementIdDao elementIdDao) throws InvalidIdException {
        if (bookService.getBookById(elementIdDao.id()).isEmpty()) {
            throw new InvalidIdException();
        }
        return favouriteRepo.findAllByBookId(elementIdDao.id()).size();
    }
    
    public void deleteFavsByBookId (Long bookId) {
        favouriteRepo.deleteByBookId(bookId);
    }
    
    public void deleteFavsByCustomerId (Long customerId) {
        favouriteRepo.deleteByCustomerId(customerId);
    }
    
    void addFav (List<Favourite> favs) {
        favouriteRepo.saveAll(favs);
    }
    

}
