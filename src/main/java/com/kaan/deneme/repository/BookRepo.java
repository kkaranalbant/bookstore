/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kaan.deneme.repository;

import com.kaan.deneme.model.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kaan
 */
@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    public Optional<Book> findByName(String name);

    //public List <Book> findByPriceBetweenAndPublicationDateBetweenAndPageNumberBetweenAndAuthorAndNameAndPublisher (Float minPrice , Float maxPrice,LocalDate minPublicationDate , LocalDate maxPublicationDate,Integer minPageNumber , Integer maxPageNumber , String author , String name,String publisher) ;
    @Query("SELECT b FROM Book b "
            + "WHERE (:minPrice IS NULL OR b.price >= :minPrice) "
            + "AND (:maxPrice IS NULL OR b.price <= :maxPrice) "
            + "AND (:minPublicationDate IS NULL OR b.publicationDate >= :minPublicationDate) "
            + "AND (:maxPublicationDate IS NULL OR b.publicationDate <= :maxPublicationDate) "
            + "AND (:minPageNumber IS NULL OR b.pageNumber >= :minPageNumber) "
            + "AND (:maxPageNumber IS NULL OR b.pageNumber <= :maxPageNumber) "
            + "AND (:author IS NULL OR b.author = :author) "
            + "AND (:name IS NULL OR b.name = :name) "
            + "AND (:publisher IS NULL OR b.publisher = :publisher)")
    public List<Book> findByPriceBetweenAndPublicationDateBetweenAndPageNumberBetweenAndAuthorAndNameAndPublisher(
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            @Param("minPublicationDate") LocalDate minPublicationDate,
            @Param("maxPublicationDate") LocalDate maxPublicationDate,
            @Param("minPageNumber") Integer minPageNumber,
            @Param("maxPageNumber") Integer maxPageNumber,
            @Param("author") String author,
            @Param("name") String name,
            @Param("publisher") String publisher);

}
