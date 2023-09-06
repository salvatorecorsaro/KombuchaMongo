package com.salvatorecorsaro.kombuchamongo.repository;

import com.salvatorecorsaro.kombuchamongo.model.Kombucha;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface KombuchaRepository extends MongoRepository<Kombucha, String> {

    @Query("{'$text': {'$search': ?0}}")
    List<Kombucha> search(String text);
}

