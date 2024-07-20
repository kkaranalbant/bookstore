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
public class BookImageResponse {
    
    private List<String> path ; 
    
    private List <byte []> images ; 
    
}
