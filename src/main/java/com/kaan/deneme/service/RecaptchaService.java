/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.RecaptchaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author kaan
 */
@Service
public class RecaptchaService {
    
    private static Logger logger ;
    
    @Value ("${url}")
    private String url ;
    
    @Value ("${secret-key}")
    private String secretKey ;
    
    private RestTemplate restTemplate ;
    
    static {
        logger = LoggerFactory.getLogger(RecaptchaService.class);
    }

    public RecaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public RecaptchaResponse validate (String recaptchaToken) {
        HttpHeaders headers = new HttpHeaders () ;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap <String , String> map = new LinkedMultiValueMap <> () ;
        map.add("secret",secretKey);
        map.add("response",recaptchaToken) ;
        HttpEntity <MultiValueMap<String,String>> entity = new HttpEntity <> (map,headers) ;
        ResponseEntity <RecaptchaResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, RecaptchaResponse.class);
        return response.getBody(); 
    }
    
}
