/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.CommentAddingDao;
import com.kaan.deneme.dao.CommentUpdatingDao;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.exception.InvalidCommentException;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.model.Comment;
import com.kaan.deneme.model.Customer;
import com.kaan.deneme.repository.CommentRepo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class CommentService {

    private CommentRepo commentRepo;
    private byte minCommentLength = 10;
    private CustomerService customerService;
    private BookService bookService;

    @Autowired
    public CommentService(CommentRepo commentRepo, CustomerService customerService, BookService bookService) {
        this.commentRepo = commentRepo;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    public void addComment(Long customerId, CommentAddingDao commentAddingDao) throws InvalidCommentException {
        String text = commentAddingDao.getText();
        Long bookId = commentAddingDao.getBookId();
        if (text.length() < minCommentLength) {
            throw new InvalidCommentException();
        }
        Comment comment = new Comment();
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        comment.setBook(bookOptional.get());
        comment.setContext(text);
        Optional<Customer> customerOptional = customerService.getCustomerById(customerId);
        comment.setCustomer(customerOptional.get());
        comment.setDateTime(LocalDateTime.now());
        commentRepo.save(comment);
    }

    public void updateComment(Long customerId, CommentUpdatingDao commentUpdatingDao) throws InvalidCommentException {
        if (commentUpdatingDao.getText().length() < minCommentLength) {
            throw new InvalidCommentException();
        }
        Long commentId = commentUpdatingDao.getCommentId();
        if (isCompatibleCustomerAndComment(customerId, commentId)) {
            Optional<Comment> commentOptional = commentRepo.findById(commentId);
            Comment comment = commentOptional.get();
            if (comment.getContext().equalsIgnoreCase(commentUpdatingDao.getText())) {
                throw new InvalidCommentException();
            }
            comment.setDateTime(LocalDateTime.now());
            comment.setContext(commentUpdatingDao.getText());
            commentRepo.save(comment);
        } else {
            throw new InvalidCommentException();
        }
    }

    public void removeComment(Long customerId, ElementIdDao elementIdDao) throws InvalidIdException {
        if (isCompatibleCustomerAndComment(customerId, elementIdDao.id())) {
            commentRepo.deleteById(elementIdDao.id());
        } else {
            throw new InvalidIdException();
        }
    }

    public List<Comment> getCommentsByCustomerId(ElementIdDao elementIdDao) throws InvalidIdException {
        if (customerService.getCustomerById(elementIdDao.id()).isPresent()) {
            return commentRepo.findAllByCustomerId(elementIdDao.id());
        }
        throw new InvalidIdException () ;
    }

    public List<Comment> getCommentsByBookId(ElementIdDao elementIdDao) throws InvalidIdException {
        if (bookService.getBookById(elementIdDao.id()).isPresent()) {
            return commentRepo.findAllByBookId(elementIdDao.id());
        }
        throw new InvalidIdException () ;
    }

    private boolean isCompatibleCustomerAndComment(Long customerId, Long commentId) {
        List<Comment> comments = commentRepo.findAllByCustomerId(customerId);
        for (Comment comment : comments) {
            if (comment.getId().longValue() == commentId.longValue()) {
                return true;
            }
        }
        return false;
    }

}
