/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.ImageUpdatingDao;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.exception.InvalidUpdatingProcessException;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.model.BookImage;
import com.kaan.deneme.repository.BookImageRepo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class BookImageService {

    private BookImageRepo bookImageRepo;
    private BookService bookService;

    @Autowired
    public BookImageService(BookImageRepo bookImageRepo, BookService bookService) {
        this.bookImageRepo = bookImageRepo;
        this.bookService = bookService;
    }

    public void addBookImage(Long bookId, ImageUpdatingDao imageUpdatingDao) throws InvalidIdException {
        BookImage bookImage = new BookImage();
        Optional <Book> bookOptional = bookService.getBookById(bookId);
        bookImage.setBook(bookOptional.get());
        bookImage.setUrl(imageUpdatingDao.getUrl());
        bookImage.setUploadDate(LocalDateTime.now());
        bookImageRepo.save(bookImage);
    }

    public void removeBookImage(Long bookId, ImageUpdatingDao imageUpdatingDao) throws InvalidUpdatingProcessException {
        Optional<BookImage> bookImageOptional = bookImageRepo.findByBookIdAndUrl(bookId, imageUpdatingDao.getUrl());
        if (bookImageOptional.isEmpty()) {
            throw new InvalidUpdatingProcessException();
        }
        bookImageRepo.delete(bookImageOptional.get());
    }

    public List<BookImage> getBookImages(Long bookId) throws InvalidIdException {
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        List<BookImage> result = new ArrayList();
        if (bookOptional.isEmpty()) {
            throw new InvalidIdException();
        } else {
            result = bookImageRepo.findAllByBookId(bookId);
        }
        return result;
    }

}
