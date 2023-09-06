package com.salvatorecorsaro.kombuchamongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class KombuchaMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KombuchaMongoApplication.class, args);
    }

}
