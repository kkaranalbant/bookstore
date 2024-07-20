/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.ModeratorAddingRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    public Moderator getModById (@RequestParam Long id) {
        return modService.getModById(new ElementIdDao (id));
    }
    
    @GetMapping ("/add-panel")
    public ModelAndView getAddingPanel () {
        ModelAndView mv = new ModelAndView () ;
        mv.setViewName("add-mod");
        return mv ;
    }
    
    @PostMapping ("/add")
    public void addMod (@RequestBody ModeratorAddingRequest moderatorAddingRequest) {
        modService.add(moderatorAddingRequest);
    }
    
    @GetMapping ("/update-panel")
    public ModelAndView getUpdatingPanel () {
        ModelAndView mv = new ModelAndView () ;
        mv.setViewName("update-mod");
        return mv ;
    }
    
    @PostMapping ("/update")
    public void updateMod (@RequestBody ModeratorUpdatingDao moderatorUpdatingDao) {
        
        modService.update(moderatorUpdatingDao);
    }
    
    @GetMapping ("/delete-panel")
    public ModelAndView getDeletingPanel () {
        ModelAndView mv = new ModelAndView () ;
        mv.setViewName("delete-mod");
        return mv ;
    }
    
    @DeleteMapping ("/delete")
    public void deleteMod (@RequestBody ElementIdDao elementIdDao) {
        modService.deleteModById(elementIdDao);
    }   
    
    @GetMapping("/main-panel")
    public ModelAndView getMainPanel () {
        ModelAndView mv = new ModelAndView () ;
        mv.setViewName("mod-main-panel");
        return mv ;
    }
}
