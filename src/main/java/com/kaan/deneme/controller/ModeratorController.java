/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.LoginCredentialsUpdatingDao;
import com.kaan.deneme.dao.ModeratorUpdatingDao;
import com.kaan.deneme.model.Moderator;
import com.kaan.deneme.service.ModeratorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kaan
 */
@RestController
@RequestMapping("/mod")
public class ModeratorController {
    
    private ModeratorService modService ;

    @Autowired
    public ModeratorController(ModeratorService modService) {
        this.modService = modService;
    }
    
    
    @GetMapping ("/get-all")
    public List <Moderator> getAllMods () {
        return modService.getAllMods();
    }
    
    @GetMapping ("/get")
    public Moderator getModById (@RequestBody ElementIdDao elementIdDao) {
        return modService.getModById(elementIdDao);
    }
    
    @PostMapping ("/add")
    public void addMod (@RequestBody ElementIdDao elementIdDao , @RequestBody ModeratorUpdatingDao moderatorUpdatingDao, @RequestBody LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) {
        modService.add(elementIdDao, moderatorUpdatingDao , loginCredentialsUpdatingDao);
    }
    
    @PostMapping ("/update")
    public void updateMod (@RequestBody ElementIdDao elementIdDao , @RequestBody ModeratorUpdatingDao moderatorUpdatingDao, @RequestBody LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) {
        modService.update(elementIdDao, moderatorUpdatingDao , loginCredentialsUpdatingDao);
    }
    
    @DeleteMapping ("/delete")
    public void deleteMod (@RequestBody ElementIdDao elementIdDao) {
        modService.deleteModById(elementIdDao);
    }    
}
