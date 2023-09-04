package com.salvatorecorsaro.kombuchamongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kombuchas")
public class KombuchaController {

    @Autowired
    private KombuchaRepository kombuchaRepository;

    @GetMapping
    public List<Kombucha> getAllKombuchas() {
        return kombuchaRepository.findAll();
    }

    @PostMapping
    public Kombucha createKombucha(@RequestBody Kombucha kombucha) {
        return kombuchaRepository.save(kombucha);
    }
}

