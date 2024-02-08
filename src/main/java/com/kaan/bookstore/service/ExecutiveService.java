/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.bookstore.service;

import com.kaan.bookstore.dto.ModeratorUpdatingRequest;
import com.kaan.bookstore.dto.ModeratorUpdatingRequestForAdmin;
import com.kaan.bookstore.dto.UserCreatingRequest;
import com.kaan.bookstore.model.Executive;
import com.kaan.bookstore.model.Role;
import com.kaan.bookstore.repo.ExecutiveRepo;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class ExecutiveService {

    private ExecutiveRepo executiveRepo;
    private BCryptPasswordEncoder pwEncoder;

    public ExecutiveService(ExecutiveRepo executiveRepo, BCryptPasswordEncoder pwEncoder, CustomerService customerService, BookService bookService) {
        this.executiveRepo = executiveRepo;
        this.pwEncoder = pwEncoder;
    }

    public void createModerator(UserCreatingRequest userCreatingRequest) {
        Executive executive = Executive.builder()
                .name(userCreatingRequest.name())
                .lastname(userCreatingRequest.lastname())
                .email(userCreatingRequest.email())
                .phoneNumber(userCreatingRequest.phoneNumber())
                .username(userCreatingRequest.username())
                .password(pwEncoder.encode(userCreatingRequest.password()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .authorities(Set.of(Role.MODERATOR))
                .build();
        executiveRepo.save(executive);
    }

    public void updateModerator(Executive moderator, ModeratorUpdatingRequest moderatorUpdatingRequest) {
        moderator.setName(moderatorUpdatingRequest.name());
        moderator.setLastname(moderatorUpdatingRequest.lastname());
        moderator.setEmail(moderatorUpdatingRequest.email());
        moderator.setPhoneNumber(moderatorUpdatingRequest.phoneNumber());
        moderator.setPassword(pwEncoder.encode(moderatorUpdatingRequest.password()));
        executiveRepo.save(moderator);
    }

    public void updateModeratorById(Long id, ModeratorUpdatingRequestForAdmin moderatorUpdatingRequestForAdmin) {
        Optional<Executive> moderatorOptional = executiveRepo.findById(id);
        Executive moderator = moderatorOptional.get();
        moderator.setName(moderatorUpdatingRequestForAdmin.name());
        moderator.setLastname(moderatorUpdatingRequestForAdmin.lastname());
        moderator.setEmail(moderatorUpdatingRequestForAdmin.email());
        moderator.setPhoneNumber(moderatorUpdatingRequestForAdmin.phoneNumber());
        moderator.setUsername(moderatorUpdatingRequestForAdmin.username());
        moderator.setPassword(pwEncoder.encode(moderatorUpdatingRequestForAdmin.password()));
        moderator.setAccountNonExpired(moderatorUpdatingRequestForAdmin.accountNonExpired());
        moderator.setAccountNonLocked(moderatorUpdatingRequestForAdmin.accountNonLocked());
        moderator.setCredentialsNonExpired(moderatorUpdatingRequestForAdmin.accountNonExpired());
        moderator.setEnabled(moderatorUpdatingRequestForAdmin.isEnabled());
        executiveRepo.save(moderator);
    }
    
    public Optional<Executive> getExecutiveByUsername (String username) {
        return executiveRepo.findExecutiveByUsername(username);
    }

}
