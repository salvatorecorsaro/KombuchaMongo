package com.salvatorecorsaro.kombuchamongo.controller;

import com.salvatorecorsaro.kombuchamongo.model.Kombucha;
import com.salvatorecorsaro.kombuchamongo.model.Review;
import com.salvatorecorsaro.kombuchamongo.service.KombuchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/kombuchas")
public class KombuchaController {

    @Autowired
    private KombuchaService kombuchaService;

    @GetMapping
    public ResponseEntity<List<Kombucha>> getAllKombuchas() {
        List<Kombucha> kombuchas = kombuchaService.getAllKombuchas();
        return new ResponseEntity<>(kombuchas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kombucha> getKombuchaById(@PathVariable String id) {
        Optional<Kombucha> kombucha = kombuchaService.getKombuchaById(id);
        return kombucha.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("between")
    public List<Kombucha> getKombuchasByCreationTime(@RequestParam(value = "start") Instant start, @RequestParam(value = "end") Instant end) {
        return kombuchaService.getKombuchasByCreationTime(start, end);
    }

    @PostMapping
    public ResponseEntity<Kombucha> saveKombucha(@RequestBody Kombucha kombucha) {
        Kombucha savedKombucha = kombuchaService.saveKombucha(kombucha);
        return new ResponseEntity<>(savedKombucha, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKombucha(@PathVariable String id) {
        kombuchaService.deleteKombucha(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<Kombucha> addReview(@PathVariable String id, @RequestBody Review review) {
        try {
            Kombucha updatedKombucha = kombuchaService.addReview(id, review);
            return new ResponseEntity<>(updatedKombucha, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Kombucha> updateStock(@PathVariable String id, @RequestParam int quantity) {
        try {
            Kombucha updatedKombucha = kombuchaService.updateStock(id, quantity);
            return new ResponseEntity<>(updatedKombucha, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Kombucha>> search(@RequestParam String query) {
        List<Kombucha> kombuchas = kombuchaService.search(query);
        return new ResponseEntity<>(kombuchas, HttpStatus.OK);
    }
}

