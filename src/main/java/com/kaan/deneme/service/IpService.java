/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class IpService {
    
    public String getIpAddress (HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR") ;
        if (ip == null || ip.isEmpty() || ip.isBlank()) {
            ip = request.getRemoteAddr() ;
        }
        return ip ;
    } 
    
}
