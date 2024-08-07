package com.kaan.deneme.service;

import com.kaan.deneme.dao.CardAddingDao;
import com.kaan.deneme.dao.CardRemovingDao;
import com.kaan.deneme.exception.InvalidCredentialsException;
import com.kaan.deneme.exception.NotUniqueCardException;
import com.kaan.deneme.exception.UnauthorizedCardProcessException;
import com.kaan.deneme.model.Card;
import com.kaan.deneme.model.Customer;
import com.kaan.deneme.repository.CardRepo;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private static Logger logger;

    private CardRepo cardRepo;
    private CustomerService customerService;

    static {
        logger = LoggerFactory.getLogger(CardService.class);
    }

    public CardService(CardRepo cardRepo, @Lazy CustomerService customerService) {
        this.cardRepo = cardRepo;
        this.customerService = customerService;
    }

    @Transactional
    public void addCardById(Long customerId, CardAddingDao cardAddingDao, String ip) throws InvalidCredentialsException, NotUniqueCardException {
        Optional<Card> cardOptional = cardRepo.findByCardNo(cardAddingDao.getCardNo());
        if (cardOptional.isPresent()) {
            throw new NotUniqueCardException();
        }
        String cvv = cardAddingDao.getCvv();
        if (cvv.length() != 3) {
            throw new InvalidCredentialsException();
        }
        byte month = cardAddingDao.getMonth();
        if (!(month >= 1 && month <= 12)) {
            throw new InvalidCredentialsException();
        }
        LocalDate localDate = LocalDate.of(cardAddingDao.getYear(), month, 1);
        if (LocalDate.now().isAfter(localDate)) {
            throw new InvalidCredentialsException();
        }
        Card card = new Card();
        card.setCardNo(cardAddingDao.getCardNo());
        card.setCvv(cvv);
        card.setMonth(month);
        card.setYear(cardAddingDao.getYear());
        Optional<Customer> customerOptional = customerService.getCustomerById(customerId);
        card.setCustomer(customerOptional.get());
        cardRepo.save(card);
        logger.info("Customer with id number " + customerId + " has added card information: " + cardAddingDao.getCardNo() + ". IP: " + ip);
    }

    @Transactional
    public void removeCardById(Long customerId, CardRemovingDao cardRemovingDao, String ip) throws InvalidCredentialsException, UnauthorizedCardProcessException {
        String cardNo = cardRemovingDao.getCardNo();
        Optional<Card> cardOptional = cardRepo.findByCardNo(cardNo);
        if (cardOptional.isEmpty()) {
            throw new InvalidCredentialsException();
        }
        Card card = cardOptional.get();
        if (card.getCustomer().getId().longValue() != customerId.longValue()) {
            throw new UnauthorizedCardProcessException();
        }
        cardRepo.deleteByCardNo(cardNo);
        logger.info("Customer with id number " + customerId + " has removed card information: " + cardRemovingDao.getCardNo() + ". IP: " + ip);
    }

    public List<Card> getCardsByCustomerId(Long customerId) {
        return cardRepo.findAllByCustomerId(customerId);
    }

    public void deleteCardByCustomerId(Long customerId, String ip) {
        cardRepo.deleteByCustomerId(customerId);
        logger.info("Customer with id number " + customerId + "'s all card information deleted. IP: " + ip);
    }

}
