/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class CommentUpdatingDao {
    @NotNull
    private Long commentId ;
    
    @NotNull
    private String text ; 
    
}
