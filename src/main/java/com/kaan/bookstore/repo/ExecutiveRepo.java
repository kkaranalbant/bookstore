/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kaan.bookstore.repo;

import com.kaan.bookstore.model.Executive;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kaan
 */
@Repository
public interface ExecutiveRepo extends JpaRepository<Executive, Long> {

    public Optional<Executive> findExecutiveByUsername(String username);
}
