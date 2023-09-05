package com.salvatorecorsaro.kombuchamongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "kombuchas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kombucha {

    @Id
    private String id;
    @TextIndexed
    private String name;
    @TextIndexed
    private String flavor;
    @TextIndexed
    private String description;
    private List<Review> reviews = new ArrayList<>();
    private double rating;
    private int stock;

    public void addReview(Review review) {
        this.reviews.add(review);
        recalculateRating();
    }

    public void removeReview(String reviewId) {
        this.reviews.removeIf(review -> review.getId().equals(reviewId));
        recalculateRating();
    }

    private void recalculateRating() {
        // Recalculate the overall rating based on individual reviews
        // This ensures the rating always reflects the current reviews
        double totalRating = 0.0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        this.rating = reviews.isEmpty() ? 0 : totalRating / reviews.size();
    }

    public void updateStock(int quantity) {
        if (this.stock + quantity < 0) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        this.stock += quantity;
    }
}
