package com.salvatorecorsaro.kombuchamongo.config;

import com.salvatorecorsaro.kombuchamongo.model.Kombucha;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.index.TextIndexDefinition.TextIndexDefinitionBuilder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MongoConfig {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void createTextIndexes() {
        TextIndexDefinition textIndex = new TextIndexDefinitionBuilder()
                .onField("name")
                .onField("flavor")
                .onField("description")
                .onField("reviews.comment")
                .build();

        mongoTemplate.indexOps(Kombucha.class).ensureIndex(textIndex);
    }
}
