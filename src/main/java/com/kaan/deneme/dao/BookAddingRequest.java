/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import com.kaan.deneme.validation.Author;
import com.kaan.deneme.validation.Balance;
import com.kaan.deneme.validation.Name;
import com.kaan.deneme.validation.PageNumber;
import com.kaan.deneme.validation.PublicationDate;
import com.kaan.deneme.validation.StockNumber;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class BookAddingRequest {

    @NotNull
    @Name
    private String name;

    @NotNull
    @Author
    private String author;

    private String isbn;

    @NotNull
    @Balance
    private float price;

    @NotNull
    @PageNumber
    private int pageNumber;

    @NotNull
    @PublicationDate
    private LocalDate publicationDate;

    @NotNull
    private String publisher;

    @NotNull
    @StockNumber
    private int stockNumber;

    private List<byte[]> paths;

}
