/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kaan.deneme.service;

import com.kaan.deneme.dao.ElementIdDao;
import com.kaan.deneme.dao.LoginCredentialsUpdatingDao;
import com.kaan.deneme.dao.ModeratorUpdatingDao;
import com.kaan.deneme.exception.InvalidAddingProcessException;
import com.kaan.deneme.exception.InvalidIdException;
import com.kaan.deneme.exception.InvalidUpdatingProcessException;
import com.kaan.deneme.model.Gender;
import com.kaan.deneme.model.Moderator;
import com.kaan.deneme.model.Role;
import com.kaan.deneme.repository.ModeratorRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaan
 */
@Service
public class ModeratorService {

    private ModeratorRepo moderatorRepo;
    
    private LoginCredentialsService loginCredentialsService ;

    @Autowired
    public ModeratorService(ModeratorRepo moderatorRepo , LoginCredentialsService loginCredentialsService) {
        this.moderatorRepo = moderatorRepo;
        this.loginCredentialsService = loginCredentialsService ;
    }

    public void add(ElementIdDao elementIdDao, ModeratorUpdatingDao moderatorUpdatingDao, LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) throws InvalidIdException, InvalidAddingProcessException {
        String name = moderatorUpdatingDao.getName();
        String lastname = moderatorUpdatingDao.getLastname();
        Gender gender = moderatorUpdatingDao.getGender();
        if (name == null || lastname == null) {
            throw new InvalidAddingProcessException();
        }
        if (elementIdDao.id() == null) {
            Moderator mod = new Moderator();
            mod.setName(name);
            mod.setLastname(lastname);
            mod.setGender(gender);
            moderatorRepo.save(mod);
            Moderator moderator = moderatorRepo.findByNameAndLastname(name, lastname).get();
            loginCredentialsUpdatingDao.setPerson(moderator);
            loginCredentialsUpdatingDao.setRole(Role.MOD);
            loginCredentialsService.add(loginCredentialsUpdatingDao);
        } else {
            Optional<Moderator> modOptional = moderatorRepo.findById(elementIdDao.id());
            if (modOptional.isPresent()) {
                throw new InvalidIdException();
            }
            Moderator mod = new Moderator();
            mod.setName(name);
            mod.setLastname(lastname);
            mod.setGender(gender);
            moderatorRepo.save(mod);
            Moderator moderator = moderatorRepo.findByNameAndLastname(name, lastname).get();
            loginCredentialsUpdatingDao.setPerson(moderator);
            loginCredentialsUpdatingDao.setRole(Role.MOD);
            loginCredentialsService.add(loginCredentialsUpdatingDao);
        }
    }

    public void update(ElementIdDao elementIdDao, ModeratorUpdatingDao moderatorUpdatingDao , LoginCredentialsUpdatingDao loginCredentialsUpdatingDao) throws InvalidUpdatingProcessException, InvalidIdException {
        String name = moderatorUpdatingDao.getName();
        String lastname = moderatorUpdatingDao.getLastname();
        Gender gender = moderatorUpdatingDao.getGender();
        if (name == null || lastname == null) {
            throw new InvalidUpdatingProcessException();
        }
        Optional<Moderator> modOptional = moderatorRepo.findById(elementIdDao.id());
        if (modOptional.isEmpty()) {
            throw new InvalidIdException();
        }
        Moderator mod = modOptional.get();
        if (mod.getName().equals(moderatorUpdatingDao.getName()) && mod.getLastname().equals(moderatorUpdatingDao.getLastname()) && mod.getGender().equals(moderatorUpdatingDao.getGender())) {
            throw new InvalidUpdatingProcessException();
        }
        mod.setName(name);
        mod.setLastname(lastname);
        mod.setGender(gender);
        moderatorRepo.save(mod);
        loginCredentialsUpdatingDao.setPerson(mod);
        loginCredentialsUpdatingDao.setRole(Role.MOD);
        loginCredentialsService.add(loginCredentialsUpdatingDao);
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

    public void deleteModById(ElementIdDao elementIdDao) throws InvalidIdException {
        Optional<Moderator> modOptional = moderatorRepo.findById(elementIdDao.id());
        if (modOptional.isEmpty()) {
            throw new InvalidIdException();
        }
        Moderator mod = moderatorRepo.findById(elementIdDao.id()).get() ;
        loginCredentialsService.remove(mod);
        moderatorRepo.deleteById(elementIdDao.id());
    }

}
