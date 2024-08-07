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
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private static Logger logger;

    private CommentRepo commentRepo;
    private byte minCommentLength = 10;
    private CustomerService customerService;
    private BookService bookService;

    static {
        logger = LoggerFactory.getLogger(CommentService.class);
    }

    public CommentService(CommentRepo commentRepo, CustomerService customerService, BookService bookService) {
        this.commentRepo = commentRepo;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @Transactional
    public void addComment(Long customerId, CommentAddingDao commentAddingDao, String ip) throws InvalidCommentException {
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
        logger.info("Customer with id number " + customerId + " added a comment : " + commentAddingDao.getText() + ". IP: " + ip);
    }

    void addComment(List<Comment> comments) {
        commentRepo.saveAll(comments);
    }

    @Transactional
    public void updateComment(Long customerId, CommentUpdatingDao commentUpdatingDao, String ip) throws InvalidCommentException {
        if (commentUpdatingDao.getText().length() < minCommentLength) {
            throw new InvalidCommentException();
        }
        Long commentId = commentUpdatingDao.getCommentId();
        if (isCompatibleCustomerAndComment(customerId, commentId)) {
            Optional<Comment> commentOptional = commentRepo.findById(commentId);
            Comment comment = commentOptional.get();
            String oldText = comment.getContext();
            if (comment.getContext().equalsIgnoreCase(commentUpdatingDao.getText())) {
                throw new InvalidCommentException();
            }
            comment.setDateTime(LocalDateTime.now());
            comment.setContext(commentUpdatingDao.getText());
            commentRepo.save(comment);
            logger.info("Customer with id number " + customerId + " has updated his comment.\n"
                    + "Old Context : " + oldText + "\n"
                    + "New Context : " + commentUpdatingDao.getText() + ". IP: " + ip);
        } else {
            throw new InvalidCommentException();
        }
    }

    @Transactional
    public void removeComment(Long customerId, ElementIdDao elementIdDao, String ip) throws InvalidIdException {
        if (isCompatibleCustomerAndComment(customerId, elementIdDao.id())) {
            Optional<Comment> commentOptional = commentRepo.findById(elementIdDao.id());
            Comment comment = commentOptional.get();
            commentRepo.deleteById(elementIdDao.id());
            logger.info("Customer with id number " + customerId + " has removed his comment: " + comment.getContext() + ". IP: " + ip);
        } else {
            throw new InvalidIdException();
        }
    }

    @Transactional
    @Cacheable(cacheNames = "commentsByCustomer", key = "#elementIdDao.id()")
    public List<Comment> getCommentsByCustomerId(ElementIdDao elementIdDao) throws InvalidIdException {
        if (customerService.getCustomerById(elementIdDao.id()).isPresent()) {
            return commentRepo.findAllByCustomerId(elementIdDao.id());
        }
        throw new InvalidIdException();
    }

    @Transactional
    public List<Comment> getCommentsByBookId(ElementIdDao elementIdDao) throws InvalidIdException {
        if (bookService.getBookById(elementIdDao.id()).isPresent()) {
            return commentRepo.findAllByBookId(elementIdDao.id());
        }
        throw new InvalidIdException();
    }

    public void deleteCommentsByBookId(String username, Long bookId, String ip) {
        commentRepo.deleteByBookId(bookId);
        logger.info("All comments of the book with id number " + bookId + " have been deleted.\n"
                + "By : " + username + ". IP: " + ip);
    }

    public void deleteCommentsByCustomerId(String username, Long customerId, String ip) {
        commentRepo.deleteByCustomerId(customerId);
        logger.info("All comments of the customer with id number " + customerId + " have been deleted.\n"
                + "By : " + username + ". IP: " + ip);
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
