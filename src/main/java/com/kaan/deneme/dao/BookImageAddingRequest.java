/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import java.util.List;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class BookImageAddingRequest {
    private Long bookId ;
    
    private List <byte[]> imageBytes ;
    
}
