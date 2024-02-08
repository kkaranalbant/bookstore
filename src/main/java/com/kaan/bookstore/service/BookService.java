/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.service;

import com.kaan.bookstore.dto.BookUpdatingRequest;
import com.kaan.bookstore.exception.BookNotFoundException;
import com.kaan.bookstore.exception.InvalidBookPriceException;
import com.kaan.bookstore.exception.NotUniqueBookNameException;
import com.kaan.bookstore.model.Book;
import com.kaan.bookstore.repo.BookRepo;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class BookService {

    private BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book createBook(BookUpdatingRequest bookUpdatingRequest) throws NotUniqueBookNameException {
        Book book = Book.builder().name(bookUpdatingRequest.name())
                .author(bookUpdatingRequest.author())
                .isbn(bookUpdatingRequest.isbn())
                .price(bookUpdatingRequest.price())
                .publisher(bookUpdatingRequest.publisher())
                .subject(bookUpdatingRequest.subject())
                .build();
        return bookRepo.save(book);
    }

    public void deleteBookByBookName(String bookName) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepo.findByName(bookName);
        bookOptional.ifPresentOrElse(book -> bookRepo.deleteById(book.getId()), () -> new BookNotFoundException());
    }

    public void updateBook(BookUpdatingRequest bookUpdatingRequest) throws InvalidBookPriceException {
        throwExceptionIfInvalidPrice(bookUpdatingRequest.price());
        Book book = Book.builder()
                .author(bookUpdatingRequest.author())
                .isbn(bookUpdatingRequest.isbn())
                .name(bookUpdatingRequest.name())
                .publisher(bookUpdatingRequest.publisher())
                .subject(bookUpdatingRequest.subject())
                .price(bookUpdatingRequest.price())
                .build();
        bookRepo.save(book);
    }

    public BookUpdatingRequest getBookUpdatingRequestByBookName(String bookName) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepo.findByName(bookName);
        BookUpdatingRequest bookUpdatingRequest = null;
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            bookUpdatingRequest = BookUpdatingRequest.builder()
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .name(book.getName())
                    .price(book.getPrice())
                    .publisher(book.getPublisher())
                    .subject(book.getSubject())
                    .build();
        } else {
            throw new BookNotFoundException();
        }
        return bookUpdatingRequest;
    }
    
    public Book getBookByBookName (String bookName) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepo.findByName(bookName);
        bookOptional.orElseThrow(() -> new BookNotFoundException());
        return bookOptional.get();
    }

    private boolean isValidPrice(Double price) {
        Double minBookPrice = 0.0;
        return price >= minBookPrice;
    }

    private void throwExceptionIfInvalidPrice(Double price) throws InvalidBookPriceException {
        if (isValidPrice(price)) {
            throw new InvalidBookPriceException();
        }
    }


}
