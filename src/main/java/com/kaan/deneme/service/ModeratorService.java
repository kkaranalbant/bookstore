/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.ModeratorAddingRequest;
import com.kaan.deneme.dao.ModeratorUpdatingDao;
import com.kaan.deneme.exception.InvalidAddingProcessException;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.exception.InvalidUpdatingProcessException;
import com.kaan.deneme.model.Gender;
import com.kaan.deneme.model.Moderator;
import com.kaan.deneme.model.Role;
import com.kaan.deneme.model.UserCredentials;
import com.kaan.deneme.repository.ModeratorRepo;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class ModeratorService {

    private static Logger logger;

    private ModeratorRepo moderatorRepo;

    private LoginCredentialsService loginCredentialsService;

    static {
        logger = LoggerFactory.getLogger(ModeratorService.class);
    }

    @Autowired
    public ModeratorService(ModeratorRepo moderatorRepo, LoginCredentialsService loginCredentialsService) {
        this.moderatorRepo = moderatorRepo;
        this.loginCredentialsService = loginCredentialsService;
    }

    public void add(ModeratorAddingRequest moderatorAddingRequest, String ip) throws InvalidIdException, InvalidAddingProcessException {
        String name = moderatorAddingRequest.getName();
        String lastname = moderatorAddingRequest.getLastname();
        Gender gender = moderatorAddingRequest.getGender();
        if (name == null || lastname == null) {
            throw new InvalidAddingProcessException();
        }
        Moderator mod = new Moderator();
        mod.setName(name);
        mod.setLastname(lastname);
        mod.setGender(gender);
        moderatorRepo.save(mod);
        Moderator moderator = moderatorRepo.findByNameAndLastname(name, lastname).get();
        loginCredentialsService.add("Admin", moderatorAddingRequest.getUsername(), moderatorAddingRequest.getPassword(), moderator, Role.MOD, ip);
        logger.info("IP address " + ip + " | Moderator with id number " + moderator.getId() + " has been added."
                + "\nBy : Admin");
    }

    public void update(ModeratorUpdatingDao moderatorUpdatingDao, String ip) throws InvalidUpdatingProcessException, InvalidIdException {
        String name = moderatorUpdatingDao.getName();
        String lastname = moderatorUpdatingDao.getLastname();
        Gender gender = moderatorUpdatingDao.getGender();
        if (name == null || lastname == null) {
            throw new InvalidUpdatingProcessException();
        }
        Optional<Moderator> modOptional = moderatorRepo.findById(moderatorUpdatingDao.getId());
        if (modOptional.isEmpty()) {
            throw new InvalidIdException();
        }
        Moderator mod = modOptional.get();
        if (mod.getName().equals(moderatorUpdatingDao.getName()) && mod.getLastname().equals(moderatorUpdatingDao.getLastname()) && mod.getGender().equals(moderatorUpdatingDao.getGender())) {
            throw new InvalidUpdatingProcessException();
        }
        UserCredentials credentials = loginCredentialsService.get(mod);
        mod.setName(name);
        mod.setLastname(lastname);
        mod.setGender(gender);
        moderatorRepo.save(mod);
        loginCredentialsService.update("Admin", credentials.getUsername(), moderatorUpdatingDao.getUsername(), moderatorUpdatingDao.getPassword(), ip);
        logger.info("IP address " + ip + " | Moderator with id number " + mod.getId() + " has been updated.\n"
                + "By : Admin");
    }

    public List<Moderator> getAllMods() {
        return moderatorRepo.findAll();
    }

    public Moderator getModById(ElementIdDao elementIdDao) throws InvalidIdException {
        Optional<Moderator> modOptional = moderatorRepo.findById(elementIdDao.id());
        if (modOptional.isEmpty()) {
            throw new InvalidIdException();
        }
        return modOptional.get();
    }

    public void deleteModById(ElementIdDao elementIdDao, String ip) throws InvalidIdException {
        Optional<Moderator> modOptional = moderatorRepo.findById(elementIdDao.id());
        if (modOptional.isEmpty()) {
            throw new InvalidIdException();
        }
        Moderator mod = moderatorRepo.findById(elementIdDao.id()).get();
        loginCredentialsService.remove("Admin", mod, ip);
        moderatorRepo.deleteById(elementIdDao.id());
        logger.info("IP address " + ip + " | Moderator with id number " + mod.getId() + " has been removed.\n"
                + "By : Admin");
    }

}
