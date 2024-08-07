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
import com.kaan.deneme.service.IpService;
import com.kaan.deneme.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/comment")
public class CommentController {

    private JwtService jwtService;
    private CommentService commentService;
    private IpService ipService;

    @Autowired
    public CommentController(JwtService jwtService, CommentService commentService, IpService ipService) {
        this.jwtService = jwtService;
        this.commentService = commentService;
        this.ipService = ipService;
    }

    @PostMapping("/add")
    public CommentAddingDao addComment(HttpServletRequest request, @RequestBody CommentAddingDao commentAddingDao) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        commentService.addComment(customerId, commentAddingDao, ipService.getIpAddress(request));
        return commentAddingDao;
    }

    @PostMapping("/update")
    public void updateComment(HttpServletRequest request, @RequestBody CommentUpdatingDao commentUpdatingDao) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        commentService.updateComment(customerId, commentUpdatingDao, ipService.getIpAddress(request));
    }

    @DeleteMapping("/delete")
    public void deleteComment(HttpServletRequest request, @RequestBody ElementIdDao elementIdDao) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        commentService.removeComment(customerId, elementIdDao, ipService.getIpAddress(request));
    }

    @GetMapping("/getv1")
    public List<Comment> getCommentsByBookId(@RequestParam Long id , HttpServletResponse response) {
        List <Comment> comments =  commentService.getCommentsByBookId(new ElementIdDao(id));
        byte counter = 1 ;
        for (Comment comment : comments) {
            response.addHeader(String.valueOf(counter), "CustomerName"+comment.getCustomer().getName()+"   BookName "+comment.getBook().getName());
            counter++;
        }
        return comments ;
    }

    @GetMapping("/getv2")
    public List<Comment> getCommentsByCustomerId(@RequestBody ElementIdDao elementIdDao) {
        return commentService.getCommentsByCustomerId(elementIdDao);
    }

    @GetMapping("/getv3")
    public ModelAndView getSelfCommentsByCustomerId(HttpServletRequest request) {
        String jwt = jwtService.getJwt(request);
        Long customerId = jwtService.getId(jwt);
        ElementIdDao elementIdDao = new ElementIdDao(customerId);
        ModelAndView mv = new ModelAndView();
        List<Comment> comments = commentService.getCommentsByCustomerId(elementIdDao);
        mv.addObject("comments", comments);
        mv.setViewName("customer-comment");
        return mv;
    }
}
