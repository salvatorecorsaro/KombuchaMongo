package com.salvatorecorsaro.kombuchamongo.service;

import com.salvatorecorsaro.kombuchamongo.model.Kombucha;
import com.salvatorecorsaro.kombuchamongo.model.Review;
import com.salvatorecorsaro.kombuchamongo.repository.KombuchaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class KombuchaService {

    @Autowired
    private KombuchaRepository kombuchaRepository;

    public List<Kombucha> getAllKombuchas() {
        return kombuchaRepository.findAll();
    }

    public Optional<Kombucha> getKombuchaById(String id) {
        return kombuchaRepository.findById(id);
    }

    public Kombucha saveKombucha(Kombucha kombucha) {
        return kombuchaRepository.save(kombucha);
    }

    public void deleteKombucha(String id) {
        kombuchaRepository.deleteById(id);
    }

    public Kombucha addReview(String kombuchaId, Review review) {
        Optional<Kombucha> kombuchaOptional = kombuchaRepository.findById(kombuchaId);
        if (kombuchaOptional.isPresent()) {
            Kombucha kombucha = kombuchaOptional.get();
            String currentTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String uniqueId = review.getAuthor() + "-" + currentTime;
            uniqueId = uniqueId.replace(" ", "-").toLowerCase();
            review.setId(uniqueId);
            kombucha.addReview(review);
            return kombuchaRepository.save(kombucha);
        } else {
            throw new IllegalArgumentException("Kombucha with id " + kombuchaId + " not found");
        }
    }

    public Kombucha updateStock(String kombuchaId, int quantity) {
        Optional<Kombucha> kombuchaOptional = kombuchaRepository.findById(kombuchaId);
        if (kombuchaOptional.isPresent()) {
            Kombucha kombucha = kombuchaOptional.get();
            kombucha.updateStock(quantity);
            return kombuchaRepository.save(kombucha);
        } else {
            throw new IllegalArgumentException("Kombucha with id " + kombuchaId + " not found");
        }
    }

    public List<Kombucha> search(String text) {
        return kombuchaRepository.search(text);
    }
}

