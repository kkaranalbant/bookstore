/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kaan.deneme.repository;

import com.kaan.deneme.model.Person;
import com.kaan.deneme.model.UserCredentials;
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
public interface UserCredentialsRepo extends JpaRepository<UserCredentials, Long> {

    @Query("SELECT uc FROM UserCredentials uc WHERE uc.username = :username")
    Optional<UserCredentials> findByUsername(@Param("username") String username);

    public Optional<UserCredentials> findByPerson(Person person);

}
