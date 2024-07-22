package com.kaan.deneme.service;

import com.kaan.deneme.dao.BookAddingRequest;
import com.kaan.deneme.dao.BookFilteringRequest;
import com.kaan.deneme.dao.BookUpdatingDao;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.exception.InvalidAddingProcessException;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.exception.InvalidUpdatingProcessException;
import com.kaan.deneme.exception.OutOfStockException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private static Logger logger;

    private BookRepo bookRepo;

    private BasketService basketService;

    private FavouriteService favouriteService;

    private CommentService commentService;

    private BookImageService bookImageService;

    static {
        logger = LoggerFactory.getLogger(BookService.class);
    }

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

    public void addBook(String username, BookAddingRequest bookAddingRequest, String ip) throws InvalidAddingProcessException, IOException {
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
        logger.info("Person with username " + username + " has added the book with id number " + book.getId() + " (book name: " + book.getName() + "). IP: " + ip);
        bookImageService.addBookImage(username, book.getId(), bookAddingRequest.getPaths(),ip);
    }

    @Transactional
    public void removeBookById(String username, ElementIdDao elementIdDao, String ip) throws InvalidIdException, IOException {
        if (bookRepo.findById(elementIdDao.id()).isEmpty()) {
            throw new InvalidIdException();
        }
        bookImageService.removeImagesByBookId(username, elementIdDao.id(),ip);
        favouriteService.deleteFavsByBookId(username, elementIdDao.id(),ip);
        basketService.deleteFromBasketsByBookId(username, elementIdDao.id(),ip);
        commentService.deleteCommentsByBookId(username, elementIdDao.id(),ip);
        bookRepo.deleteById(elementIdDao.id());
        logger.info("Person with username " + username + " deleted the book with id number " + elementIdDao.id().longValue() + ". IP: " + ip);
    }

    @Transactional
    public void updateBookById(String username, BookUpdatingDao bookUpdatingDao, String ip) throws InvalidUpdatingProcessException, InvalidIdException, IOException {
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

            removeBookById(username, new ElementIdDao(bookUpdatingDao.getOldId()), ip);

            logger.info("Information about the book with id number " + bookUpdatingDao.getOldId() + " has been updated. \n"
                    + "New id number : " + bookUpdatingDao.getId() + "\n"
                    + "Subject : " + username + ". IP: " + ip);

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

    public List<Book> getAllFilteredBooks(String username, BookFilteringRequest bookFilteringRequest, String ip) {
        Integer maxPageNumber = bookFilteringRequest.getMaxPageNumber();
        Integer minPageNumber = bookFilteringRequest.getMinPageNumber();
        Float maxPrice = bookFilteringRequest.getMaxPrice();
        Float minPrice = bookFilteringRequest.getMinPrice();
        LocalDate maxPublicationDate = bookFilteringRequest.getMaxPublicationDate();
        LocalDate minPublicationDate = bookFilteringRequest.getMinPublicationDate();
        String author = bookFilteringRequest.getAuthor();
        String publisher = bookFilteringRequest.getPublisher();
        String name = bookFilteringRequest.getName();
        List<Book> books = bookRepo.findByPriceBetweenAndPublicationDateBetweenAndPageNumberBetweenAndAuthorAndNameAndPublisher(minPrice, maxPrice, minPublicationDate, maxPublicationDate, minPageNumber, maxPageNumber, author, name, publisher);
        logger.info("Person who has username " + username + " did a filtering operation. \n"
                + "Minimum Page Number : " + minPageNumber + "\n"
                + "Maximum Page Number : " + maxPageNumber + "\n"
                + "Minimum Price : " + minPrice + "\n"
                + "Maximum Price : " + maxPrice + "\n"
                + "Minimum Publication Date : " + minPublicationDate.toString() + "\n"
                + "Maximum Publication Date : " + maxPublicationDate.toString() + "\n"
                + "Author : " + author + "\n"
                + "Publisher : " + publisher + "\n"
                + "Name : " + name + ". IP: " + ip);
        return books;
    }

    float getAllBookPrice(List<Book> books) {
        float price = 0;
        for (Book book : books) {
            price += book.getPrice();
        }
        return price;
    }

    public void decreaseStockNumberOfBooks(List<Book> books) throws OutOfStockException {
        for (Book book : books) {
            if (book.getStockNumber() == 0) {
                throw new OutOfStockException();
            }
            book.setStockNumber(book.getStockNumber() - 1);
            logger.info("New stock number of book with id number a : " + book.getStockNumber());
        }
    }

}
