/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.BookAddingRequest;
import com.kaan.deneme.dao.BookDeletingRequest;
import com.kaan.deneme.dao.BookFilteringRequest;
import com.kaan.deneme.dao.BookImageAddingRequest;
import com.kaan.deneme.dao.BookImageResponse;
import com.kaan.deneme.dao.BookUpdatingDao;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.service.BookImageService;
import com.kaan.deneme.service.BookService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author kaan
 */
@RestController
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    private BookImageService bookImageService;

    @Autowired
    public BookController(BookService bookService, BookImageService bookImageService) {
        this.bookService = bookService;
        this.bookImageService = bookImageService;
    }

    /*
    Anasayfada kitaplarin yuklenmesi icin verileri json formatinda verir . Json formatindaki verilerin bir kismi orada kullanilir.
     */
    @GetMapping("/get-all") // herkes icin
    public List<Book> getAll() {
        return bookService.getAll();
    }

    /*
    Yetkililer icin butun kitaplarin bilgilerini listelemek icin
     */
    @GetMapping("/get-all-auth")
    public ModelAndView getAllForExecutive() {
        List<Book> books = getAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("books", books);
        mv.setViewName("book-view-all");
        return mv;
    }

    /*
    kitap resminin uzerine basildiginda kitap bilgilerini gostermesi icin
     */
    @GetMapping("/get") // herkes
    public ModelAndView getBookById(@RequestParam Long id) {
        Book book = bookService.getBookById(new ElementIdDao(id));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("book-info");
        mv.addObject("book", book);
        return mv;
    }

    /*
    Belirli bir id ye sahip kitabin bilgilerinin json formatinda gelmesi icin
     */
    @GetMapping("/get-book-json")
    public Book getBookJsonById(@RequestParam Long id) {
        return bookService.getBookById(new ElementIdDao(id));
    }

    @GetMapping("/delete-panel")
    public ModelAndView getDeletePanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("delete-book");
        return mv;
    }

    @DeleteMapping("/delete") // admin , mod
    public ResponseEntity<String> deleteBookById(@RequestBody ElementIdDao bookIdDao) {
        try {
            bookService.removeBookById(bookIdDao);
        } catch (IOException ex) {
            return ResponseEntity.badRequest().body("Failure Image Process");
        }
        return ResponseEntity.ok().body("Successful");
    }

    /*
    Kitap ekleme panelini goruntulemek icin .
     */
    @GetMapping("/add-panel") //admin ve mod
    public ModelAndView getBookAddingPanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add-book");
        return mv;
    }

    /*
    Kitap ekleme islemi icin
     */
    @PostMapping("/add") // admin mod 
    public ResponseEntity<String> addBook(@RequestBody BookAddingRequest bookAddingRequest) {
        try {
            bookService.addBook(bookAddingRequest);
        } catch (IOException ex) {
            return ResponseEntity.badRequest().body("Failure Image Process");
        }
        return ResponseEntity.ok().body("Successful Process");

    }

    /*
    Guncelleme islemi icin panel 
     */
    @GetMapping("/update-panel")
    public ModelAndView getUpdatingPanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("update-book");
        return mv;
    }

    @PostMapping("/update") // admin , mod
    public ResponseEntity<String> update(@RequestBody BookUpdatingDao bookUpdatingDao) {
        try {
            bookService.updateBookById(bookUpdatingDao);
        } catch (IOException ex) {
            return ResponseEntity.badRequest().body("Failure Image Process");
        }
        return ResponseEntity.ok().body("Successful");
    }

    @GetMapping("/get-image")
    public @ResponseBody
    BookImageResponse getImage(@RequestParam Long id) {
        BookImageResponse bookImageResponse = null ;
        try {
            bookImageResponse =  bookImageService.getBookImages(id);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return bookImageResponse ;
    }

    @DeleteMapping("/delete-image")
    public void deleteImage(@RequestBody BookDeletingRequest bookDeletingRequest) {
        try {
            bookImageService.removeBookImage(bookDeletingRequest.getBookId(), bookDeletingRequest.getPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @PostMapping("/add-image")
    public void addImage (@RequestBody BookImageAddingRequest bookImageAddingRequest) {
        try {
            bookImageService.addBookImage(bookImageAddingRequest.getBookId(), bookImageAddingRequest.getImageBytes());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @PostMapping ("/filter")
    public List<Book> getFilteredBooks (@RequestBody BookFilteringRequest bookFilteringRequest) {
        return bookService.getAllFilteredBooks(bookFilteringRequest);
    }

}
