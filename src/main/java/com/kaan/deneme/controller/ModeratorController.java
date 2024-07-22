/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.ModeratorAddingRequest;
import com.kaan.deneme.dao.ModeratorUpdatingDao;
import com.kaan.deneme.model.Moderator;
import com.kaan.deneme.service.IpService;
import com.kaan.deneme.service.ModeratorService;
import jakarta.servlet.http.HttpServletRequest;
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
 * author kaan
 */
@RestController
@RequestMapping("/mod")
public class ModeratorController {

    private ModeratorService modService;
    private IpService ipService;

    @Autowired
    public ModeratorController(ModeratorService modService, IpService ipService) {
        this.modService = modService;
        this.ipService = ipService;
    }

    @GetMapping("/get-all")
    public List<Moderator> getAllMods(HttpServletRequest request) {
        return modService.getAllMods();
    }

    @GetMapping("/get")
    public Moderator getModById(@RequestParam Long id, HttpServletRequest request) {
        return modService.getModById(new ElementIdDao(id));
    }

    @GetMapping("/add-panel")
    public ModelAndView getAddingPanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add-mod");
        return mv;
    }

    @PostMapping("/add")
    public void addMod(@RequestBody ModeratorAddingRequest moderatorAddingRequest, HttpServletRequest request) {
        String ipAddress = ipService.getIpAddress(request);
        modService.add(moderatorAddingRequest, ipAddress);
    }

    @GetMapping("/update-panel")
    public ModelAndView getUpdatingPanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("update-mod");
        return mv;
    }

    @PostMapping("/update")
    public void updateMod(@RequestBody ModeratorUpdatingDao moderatorUpdatingDao, HttpServletRequest request) {
        String ipAddress = ipService.getIpAddress(request);
        modService.update(moderatorUpdatingDao, ipAddress);
    }

    @GetMapping("/delete-panel")
    public ModelAndView getDeletingPanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("delete-mod");
        return mv;
    }

    @DeleteMapping("/delete")
    public void deleteMod(@RequestBody ElementIdDao elementIdDao, HttpServletRequest request) {
        String ipAddress = ipService.getIpAddress(request);
        modService.deleteModById(elementIdDao, ipAddress);
    }

    @GetMapping("/main-panel")
    public ModelAndView getMainPanel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mod-main-panel");
        return mv;
    }
}
