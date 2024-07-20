/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.BookAddingRequest;
import com.kaan.deneme.dao.BookFilteringRequest;
import com.kaan.deneme.dao.BookUpdatingDao;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.exception.InvalidAddingProcessException;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.exception.InvalidUpdatingProcessException;
import com.kaan.deneme.model.Basket;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.model.Comment;
import com.kaan.deneme.model.Favourite;
import com.kaan.deneme.repository.BookRepo;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
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
public class BookService {

    private BookRepo bookRepo;

    private BasketService basketService;

    private FavouriteService favouriteService;

    private CommentService commentService;

    private BookImageService bookImageService;

    @Autowired
    public BookService(BookRepo bookRepo, @Lazy BasketService basketService, @Lazy FavouriteService favouriteService, @Lazy CommentService commentService, @Lazy BookImageService bookImageService) {
        this.bookRepo = bookRepo;
        this.basketService = basketService;
        this.favouriteService = favouriteService;
        this.commentService = commentService;
        this.bookImageService = bookImageService;
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepo.findById(id);
    }

    public void addBook(BookAddingRequest bookAddingRequest) throws InvalidAddingProcessException, IOException {
        Book book = new Book();
        book.setName(bookAddingRequest.getName());
        book.setAuthor(bookAddingRequest.getAuthor());
        book.setIsbn(bookAddingRequest.getIsbn());
        book.setPageNumber(bookAddingRequest.getPageNumber());
        book.setPrice(bookAddingRequest.getPageNumber());
        book.setPublicationDate(bookAddingRequest.getPublicationDate());
        book.setPublisher(bookAddingRequest.getPublisher());
        book.setStockNumber(bookAddingRequest.getStockNumber());
        bookRepo.save(book);
        book = bookRepo.findByName(bookAddingRequest.getName()).get();
        bookImageService.addBookImage(book.getId(), bookAddingRequest.getPaths());
    }

    @Transactional
    public void removeBookById(ElementIdDao elementIdDao) throws InvalidIdException, IOException {
        if (bookRepo.findById(elementIdDao.id()).isEmpty()) {
            throw new InvalidIdException();
        }
        bookImageService.removeImagesByBookId(elementIdDao.id());
        favouriteService.deleteFavsByBookId(elementIdDao.id());
        basketService.deleteFromBasketsByBookId(elementIdDao.id());
        commentService.deleteCommentsByBookId(elementIdDao.id());
        bookRepo.deleteById(elementIdDao.id());
    }

    @Transactional
    public void updateBookById(BookUpdatingDao bookUpdatingDao) throws InvalidUpdatingProcessException, InvalidIdException, IOException {
        Optional<Book> bookOptional = bookRepo.findById(bookUpdatingDao.getOldId());
        if (bookOptional.isEmpty()) {
            throw new InvalidIdException();
        }
        if (bookUpdatingDao.getPageNumber() <= 0 || bookUpdatingDao.getPrice() < 0 || bookUpdatingDao.getPublicationDate().isAfter(LocalDate.now()) || bookUpdatingDao.getStockNumber() < 0) {
            throw new InvalidUpdatingProcessException();
        }
        Book newBook = new Book();
        newBook.setId(bookUpdatingDao.getId());
        newBook.setName(bookUpdatingDao.getName());
        newBook.setAuthor(bookUpdatingDao.getAuthor());
        newBook.setIsbn(bookUpdatingDao.getIsbn());
        newBook.setPageNumber(bookUpdatingDao.getPageNumber());
        newBook.setPrice(bookUpdatingDao.getPageNumber());
        newBook.setPublicationDate(bookUpdatingDao.getPublicationDate());
        newBook.setPublisher(bookUpdatingDao.getPublisher());
        newBook.setStockNumber(bookUpdatingDao.getStockNumber());
        if (bookUpdatingDao.getId().longValue() != bookUpdatingDao.getOldId().longValue()) {
            List<Favourite> favs = favouriteService.getAllFavouriteByBookId(bookUpdatingDao.getOldId());
            List<Basket> baskets = basketService.getAllBasketsContainsBookId(bookUpdatingDao.getOldId());
            List<Comment> comments = commentService.getCommentsByBookId(new ElementIdDao(bookUpdatingDao.getOldId()));
            for (Favourite fav : favs) {
                fav.setBook(newBook);
            }
            for (Basket basket : baskets) {
                basket.setBook(newBook);
            }
            for (Comment comment : comments) {
                comment.setBook(newBook);
            }
            bookRepo.save(newBook);

            favouriteService.addFav(favs);

            basketService.addBasket(baskets);

            commentService.addComment(comments);

            removeBookById(new ElementIdDao(bookUpdatingDao.getOldId()));
            return;
        }

        bookRepo.save(newBook);
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

    public List<Book> getAllFilteredBooks(BookFilteringRequest bookFilteringRequest) {
        Integer maxPageNumber = bookFilteringRequest.getMaxPageNumber();
        Integer minPageNumber = bookFilteringRequest.getMinPageNumber();
        Float maxPrice = bookFilteringRequest.getMaxPrice();
        Float minPrice = bookFilteringRequest.getMinPrice();
        LocalDate maxPublicationDate = bookFilteringRequest.getMaxPublicationDate();
        LocalDate minPublicationDate = bookFilteringRequest.getMinPublicationDate();
        String author = bookFilteringRequest.getAuthor();
        String publisher = bookFilteringRequest.getPublisher();
        String name = bookFilteringRequest.getName();
        return bookRepo.findByPriceBetweenAndPublicationDateBetweenAndPageNumberBetweenAndAuthorAndNameAndPublisher(minPrice, maxPrice, minPublicationDate, maxPublicationDate, minPageNumber, maxPageNumber, author, name, publisher);
    }

    float getAllBookPrice(List<Book> books) {
        float price = 0;
        for (Book book : books) {
            price += book.getPrice();
        }
        return price;
    }

}
