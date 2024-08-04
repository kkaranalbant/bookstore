/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.dao;

import java.util.List;
import lombok.Data;

/**
 *
 * @author kaan
 */
@Data
public class RecaptchaResponse {

    private Long id;
    private boolean success;
    private String challengeTs;
    private String hostname;
    private List<String> errorCodes;

    public RecaptchaResponse() {
    }

    public RecaptchaResponse(Long id, boolean success, String challengeTs, String hostname, List<String> errorCodes) {
        this.id = id;
        this.success = success;
        this.challengeTs = challengeTs;
        this.hostname = hostname;
        this.errorCodes = errorCodes;
    }
}
