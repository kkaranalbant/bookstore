/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import com.kaan.deneme.validation.CardNo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class CardRemovingDao {

    @NotNull
    @CardNo
    private String cardNo;
}
