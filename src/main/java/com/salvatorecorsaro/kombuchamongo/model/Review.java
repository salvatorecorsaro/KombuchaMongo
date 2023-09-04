package com.salvatorecorsaro.kombuchamongo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String id;
    private String author;
    private String comment;
    private double rating;
}

