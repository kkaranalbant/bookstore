/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.controller;

import com.kaan.bookstore.dto.ModeratorUpdatingRequest;
import com.kaan.bookstore.dto.ModeratorUpdatingRequestForAdmin;
import com.kaan.bookstore.dto.UserCreatingRequest;
import com.kaan.bookstore.model.Executive;
import com.kaan.bookstore.service.JwtService;
import com.kaan.bookstore.service.ExecutiveService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kaan
 */
@RestController
@RequestMapping("/moderator")
public class ModeratorController {

    private ExecutiveService moderatorService;
    private JwtService jwtService;

    public ModeratorController(ExecutiveService moderatorService, JwtService jwtService) {
        this.moderatorService = moderatorService;
        this.jwtService = jwtService;
    }
    
    @PostMapping("/create")//admin icin
    public void createModerator (@RequestBody UserCreatingRequest userCreatingRequest) {
        moderatorService.createModerator(userCreatingRequest);
    }

    @PutMapping("/updatebyid") // admin icin
    public void updateModerator(@RequestParam Long moderatorId, @RequestBody ModeratorUpdatingRequestForAdmin modUpdatingRequest) {
        moderatorService.updateModeratorById(moderatorId, modUpdatingRequest);
    }

    @PutMapping("/update") // moderator icin
    public void updateModerator(@RequestHeader("Authorization") String token, @RequestBody ModeratorUpdatingRequest modUpdatingRequest) {
        Executive executive = (Executive) jwtService.getUserByToken(token);
        moderatorService.updateModerator(executive, modUpdatingRequest);
    }
    
}
