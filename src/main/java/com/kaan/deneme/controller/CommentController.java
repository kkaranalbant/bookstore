/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.controller;

import com.kaan.deneme.dao.CommentAddingDao;
import com.kaan.deneme.dao.CommentUpdatingDao;
import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.model.Comment;
import com.kaan.deneme.service.CommentService;
import com.kaan.deneme.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/comment")
public class CommentController {

    private JwtService jwtService;
    private CommentService commentService;

    @Autowired
    public CommentController(JwtService jwtService, CommentService commentService) {
        this.jwtService = jwtService;
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public void addComment(HttpServletRequest request , @RequestBody CommentAddingDao commentAddingDao) {
        String jwt = jwtService.getJwt(request) ;
        Long customerId = jwtService.getId(jwt);
        commentService.addComment(customerId, commentAddingDao);
    }
    
    @PostMapping("/update")
    public void updateComment (HttpServletRequest request , @RequestBody CommentUpdatingDao commentUpdatingDao) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        commentService.updateComment(customerId, commentUpdatingDao);
    }
    
    @DeleteMapping ("/delete") 
    public void deleteComment (HttpServletRequest request , @RequestBody ElementIdDao elementIdDao) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        commentService.removeComment(customerId, elementIdDao);
    }
    
    @GetMapping ("/getv1")
    public List <Comment> getCommentsByBookId (@RequestBody ElementIdDao elementIdDao) {
        return commentService.getCommentsByBookId(elementIdDao);
    }
    
    @GetMapping ("/getv2")
    public List <Comment> getCommentsByCustomerId (@RequestBody ElementIdDao elementIdDao) {
        return commentService.getCommentsByCustomerId(elementIdDao);
    }
    
    
    @GetMapping ("/getv3")
    public List <Comment> getSelfCommentsByCustomerId (HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        ElementIdDao elementIdDao = new ElementIdDao (customerId) ;
        return commentService.getCommentsByCustomerId(elementIdDao);
    }
    
}
