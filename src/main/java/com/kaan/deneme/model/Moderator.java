/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 *
 * @author kaan
 */
@Entity
@Table (name = "Moderator",uniqueConstraints = {@UniqueConstraint(columnNames = {"Name","Lastname"})})
public class Moderator extends Person{

}
