/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.BookUpdatingDao;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.exception.InvalidAddingProcessException;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.exception.InvalidUpdatingProcessException;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.repository.BookRepo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class BookService {

    private BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepo.findById(id);
    }

    public void addBook(ElementIdDao elementIdDao, BookUpdatingDao bookUpdatingDao) throws InvalidAddingProcessException {
        if (elementIdDao.id() == null) {
            Book book = new Book();
            book.setName(bookUpdatingDao.getName());
            book.setAuthor(bookUpdatingDao.getAuthor());
            book.setIsbn(bookUpdatingDao.getIsbn());
            book.setPageNumber(bookUpdatingDao.getPageNumber());
            book.setPrice(bookUpdatingDao.getPageNumber());
            book.setPublicationDate(bookUpdatingDao.getPublicationDate());
            book.setPublisher(bookUpdatingDao.getPublisher());
            book.setStockNumber(bookUpdatingDao.getStockNumber());
            bookRepo.save(book);
        } else {
            Optional<Book> bookOptional = bookRepo.findById(elementIdDao.id());
            if (bookOptional.isEmpty()) {
                Book book = new Book();
                book.setId(elementIdDao.id());
                book.setName(bookUpdatingDao.getName());
                book.setAuthor(bookUpdatingDao.getAuthor());
                book.setIsbn(bookUpdatingDao.getIsbn());
                book.setPageNumber(bookUpdatingDao.getPageNumber());
                book.setPrice(bookUpdatingDao.getPageNumber());
                book.setPublicationDate(bookUpdatingDao.getPublicationDate());
                book.setPublisher(bookUpdatingDao.getPublisher());
                book.setStockNumber(bookUpdatingDao.getStockNumber());
                bookRepo.save(book);
            } else {
                throw new InvalidAddingProcessException();
            }
        }
    }

    public void removeBookById(ElementIdDao elementIdDao) throws InvalidIdException {
        if (bookRepo.findById(elementIdDao.id()).isEmpty()) {
            throw new InvalidIdException();
        }
        bookRepo.deleteById(elementIdDao.id());
    }

    public void updateBookById(ElementIdDao elementIdDao, BookUpdatingDao bookUpdatingDao) throws InvalidUpdatingProcessException, InvalidIdException {
        Optional<Book> bookOptional = bookRepo.findById(elementIdDao.id());
        if (bookOptional.isEmpty()) {
            throw new InvalidIdException();
        }
        if (bookUpdatingDao.getPageNumber() <= 0 || bookUpdatingDao.getPrice() < 0 || bookUpdatingDao.getPublicationDate().isAfter(LocalDate.now()) || bookUpdatingDao.getStockNumber() < 0 ) {
            throw new InvalidUpdatingProcessException () ;
        }
        Book book = bookOptional.get();
        book.setId(elementIdDao.id());
        book.setName(bookUpdatingDao.getName());
        book.setAuthor(bookUpdatingDao.getAuthor());
        book.setIsbn(bookUpdatingDao.getIsbn());
        book.setPageNumber(bookUpdatingDao.getPageNumber());
        book.setPrice(bookUpdatingDao.getPageNumber());
        book.setPublicationDate(bookUpdatingDao.getPublicationDate());
        book.setPublisher(bookUpdatingDao.getPublisher());
        book.setStockNumber(bookUpdatingDao.getStockNumber());
        bookRepo.save(book);
    }

    public Book getBookById(ElementIdDao bookIdDao) throws InvalidIdException {
        Optional<Book> bookOptional = bookRepo.findById(bookIdDao.id());
        Book book = bookOptional.get();
        if (book == null) {
            throw new InvalidIdException();
        }
        return book;
    }

    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    float getAllBookPrice(List<Book> books) {
        float price = 0;
        for (Book book : books) {
            price += book.getPrice();
        }
        return price;
    }

}
