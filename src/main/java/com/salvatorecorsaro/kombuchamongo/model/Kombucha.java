package com.salvatorecorsaro.kombuchamongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "kombuchas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kombucha {

    @Id
    private String id;
    private String name;
    private String flavor;
    private String brand;
    private List<Review> reviews; // Embedding reviews inside the Kombucha document
    private double rating; // Calculated from reviews
    private int stock; // Number of Kombucha bottles in stock

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
