package com.salvatorecorsaro.kombuchamongo.repository;

import com.salvatorecorsaro.kombuchamongo.model.Kombucha;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KombuchaRepository extends MongoRepository<Kombucha, String> {
}

