package com.salvatorecorsaro.kombuchamongo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.TextIndexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String id;
    private String author;
    @TextIndexed
    private String comment;
    private double rating;
}

