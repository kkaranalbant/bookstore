/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.BookImageResponse;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.exception.InvalidUpdatingProcessException;
import com.kaan.deneme.model.Book;
import com.kaan.deneme.model.BookImage;
import com.kaan.deneme.repository.BookImageRepo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class BookImageService {

    private static Logger logger;

    private BookImageRepo bookImageRepo;
    private BookService bookService;

    private static String imageDirectory;
    private static Random random;

    static {
        imageDirectory = "/home/kaan/Desktop/app-images/";
        random = new Random();
        logger = LoggerFactory.getLogger(BookImageService.class);
    }

    @Autowired
    public BookImageService(BookImageRepo bookImageRepo, BookService bookService) {
        this.bookImageRepo = bookImageRepo;
        this.bookService = bookService;
    }

    /*
    kitap servisinin oncelikle kitabi olusturmasi lazim . 
    kitap servisinden buraya erisilecek . 
     */
    public void addBookImage(String username, Long bookId, List<byte[]> binaryImages, String ip) throws InvalidIdException, IOException {
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        for (byte[] binaryImage : binaryImages) {
            String path = createRandomImagePath();
            createImageLocally(path, binaryImage);
            BookImage bookImage = new BookImage();
            bookImage.setBook(bookOptional.get());
            bookImage.setPath(path);
            bookImage.setUploadDate(LocalDateTime.now());
            bookImageRepo.save(bookImage);
            logger.info("IP address " + ip + " | person with username " + username + " added a photo for the book with id number " + bookId + ". \n"
                    + "Path : " + path);
        }
    }

    public void removeBookImage(String username, Long bookId, String path, String ip) throws InvalidUpdatingProcessException, IOException {
        Optional<BookImage> bookImageOptional = bookImageRepo.findByBookIdAndPath(bookId, path);
        if (bookImageOptional.isEmpty()) {
            throw new InvalidUpdatingProcessException();
        }
        deleteFileLocally(path);
        bookImageRepo.delete(bookImageOptional.get());
        logger.info("IP address " + ip + " | person with username " + username + " removed a photo for the book with id number : " + bookId + " . \n"
                + "Path : " + path);
    }

    public void removeImagesByBookId(String username, Long bookId, String ip) throws IOException {
        List<BookImage> images = bookImageRepo.findAllByBookId(bookId);
        bookImageRepo.deleteByBookId(bookId);
        for (BookImage image : images) {
            deleteFileLocally(image.getPath());
        }
        logger.info("IP address " + ip + " | All photos of the book with id number " + bookId + " have been removed by username " + username + ".");
    }

    public BookImageResponse getBookImages(Long bookId) throws InvalidIdException, IOException {
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        List<BookImage> images = new ArrayList<>();
        if (bookOptional.isEmpty()) {
            throw new InvalidIdException();
        } else {
            images = bookImageRepo.findAllByBookId(bookId);
        }
        List<byte[]> imageByteList = new ArrayList<>();
        List<String> paths = new ArrayList<>();
        for (BookImage image : images) {
            paths.add(image.getPath());
            imageByteList.add(createImageBytes(image.getPath()));
        }
        BookImageResponse bookImageResponse = new BookImageResponse();
        bookImageResponse.setImages(imageByteList);
        bookImageResponse.setPath(paths);
        return bookImageResponse;
    }

    private byte[] createImageBytes(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = null;
        byte[] result = null;
        fis = new FileInputStream(file);
        result = fis.readAllBytes();
        fis.close();
        return result;
    }

    private String createRandomImagePath() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(random.nextInt(0, 10));
        }
        for (int i = 0; i < 20; i++) {
            sb.append((char) (random.nextInt(97, 123)));
        }
        return imageDirectory + sb.toString();
    }

    private void deleteFileLocally(String path) throws IOException {
        File file = new File(path);
        file.delete();
        logger.info("File deleted : " + path);
    }

    private void createImageLocally(String path, byte[] imageBinary) throws IOException {
        File file = new File(path);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imageBinary);
        fos.close();
        logger.info("File created : " + path);
    }

}
