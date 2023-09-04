package com.salvatorecorsaro.kombuchamongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface KombuchaRepository extends MongoRepository<Kombucha, String> {
}

