/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author kaan
 */
@Component
public class CustomConcurrentSessionControlStrategy extends ConcurrentSessionControlAuthenticationStrategy {

    private SessionRegistry sessionRegistry;

    @Autowired
    public CustomConcurrentSessionControlStrategy(@Lazy SessionRegistry sessionRegistry) {
        super(sessionRegistry);
        setMaximumSessions(1);
    }

    @Override
    protected void allowableSessionsExceeded(List<SessionInformation> sessions, int allowableSessions,
            SessionRegistry registry) throws SessionAuthenticationException {
        // Tüm oturumları sonlandır
        for (SessionInformation sessionInfo : sessions) {
            sessionInfo.expireNow();
            registry.removeSessionInformation(sessionInfo.getSessionId());
        }
        throw new SessionAuthenticationException("Maximum sessions exceeded.");
    }
}
