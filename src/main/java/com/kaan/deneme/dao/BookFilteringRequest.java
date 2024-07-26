/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class BookFilteringRequest {

    @Nullable
    private String author;

    @Nullable
    private String name;

    @Nullable
    private Integer minPageNumber;

    @Nullable
    private Integer maxPageNumber;

    @Nullable
    private Float minPrice;

    @Nullable
    private Float maxPrice;

    @Nullable
    private LocalDate minPublicationDate;

    @Nullable
    private LocalDate maxPublicationDate;

    @Nullable
    private String publisher;

}
