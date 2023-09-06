package com.salvatorecorsaro.kombuchamongo;

import com.salvatorecorsaro.kombuchamongo.model.Kombucha;
import com.salvatorecorsaro.kombuchamongo.model.Review;
import com.salvatorecorsaro.kombuchamongo.repository.KombuchaRepository;
import com.salvatorecorsaro.kombuchamongo.service.KombuchaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataMongoTest
@Import(KombuchaService.class)
public class KombuchaServiceIntegrationTest {

    @Autowired
    private KombuchaService kombuchaService;

    @Autowired
    private KombuchaRepository kombuchaRepository;

    @BeforeEach
    void setUp() {
        kombuchaRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        kombuchaRepository.deleteAll();
    }

    @Test
    public void testAddAndGetKombucha() {
        Kombucha kombucha = new Kombucha();
        kombucha.setName("Test Kombucha");
        kombuchaService.saveKombucha(kombucha);

        List<Kombucha> kombuchas = kombuchaService.getAllKombuchas();
        assertThat(kombuchas).isNotEmpty();
        assertThat(kombuchas.get(0).getName()).isEqualTo("Test Kombucha");
    }

    @Test
    public void testAddReview() {
        Kombucha kombucha = new Kombucha();
        kombucha.setName("Test Kombucha");
        kombucha = kombuchaService.saveKombucha(kombucha);

        Review review = new Review();
        review.setAuthor("John");
        review.setComment("Great!");

        Kombucha updatedKombucha = kombuchaService.addReview(kombucha.getId(), review);
        assertThat(updatedKombucha).isNotNull();
        assertThat(updatedKombucha.getReviews()).isNotEmpty();
        assertThat(updatedKombucha.getReviews().get(0).getAuthor()).isEqualTo("John");
    }

    @Test
    public void testGetKombuchaById() {
        Kombucha kombucha = new Kombucha();
        kombucha.setName("Test Kombucha");
        Kombucha savedKombucha = kombuchaService.saveKombucha(kombucha);

        Optional<Kombucha> foundKombucha = kombuchaService.getKombuchaById(savedKombucha.getId());
        assertThat(foundKombucha).isPresent();
        assertThat(foundKombucha.get().getName()).isEqualTo("Test Kombucha");
    }

    @Test
    public void testDeleteKombucha() {
        Kombucha kombucha = new Kombucha();
        kombucha.setName("Test Kombucha");
        Kombucha savedKombucha = kombuchaService.saveKombucha(kombucha);

        kombuchaService.deleteKombucha(savedKombucha.getId());
        Optional<Kombucha> foundKombucha = kombuchaService.getKombuchaById(savedKombucha.getId());
        assertThat(foundKombucha).isNotPresent();
    }

    @Test
    public void testUpdateStock() {
        Kombucha kombucha = new Kombucha();
        kombucha.setName("Test Kombucha");
        kombucha.setStock(10);
        Kombucha savedKombucha = kombuchaService.saveKombucha(kombucha);

        Kombucha updatedKombucha = kombuchaService.updateStock(savedKombucha.getId(), 20);
        assertThat(updatedKombucha.getStock()).isEqualTo(30);  // Assuming updateStock adds to the existing stock
    }

    @Test
    public void testSearch() {
        Kombucha kombucha = new Kombucha();
        kombucha.setName("Test Kombucha");
        kombucha.setDescription("Delicious fermented tea");
        Kombucha savedKombucha = kombuchaService.saveKombucha(kombucha);

        List<Kombucha> searchResults = kombuchaService.search("Delicious");
        assertThat(searchResults).isNotEmpty();
        assertThat(searchResults.get(0).getName()).isEqualTo("Test Kombucha");
    }

    @Test
    public void testAddReviewToNonexistentKombucha() {
        Review review = new Review();
        review.setAuthor("John");
        review.setComment("Great!");

        assertThatThrownBy(() -> kombuchaService.addReview("nonexistent-id", review))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Kombucha with id nonexistent-id not found");
    }
}
