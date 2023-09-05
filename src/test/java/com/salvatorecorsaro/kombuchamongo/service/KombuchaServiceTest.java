package com.salvatorecorsaro.kombuchamongo.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.salvatorecorsaro.kombuchamongo.model.Kombucha;
import com.salvatorecorsaro.kombuchamongo.model.Review;
import com.salvatorecorsaro.kombuchamongo.repository.KombuchaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class KombuchaServiceTest {

    @InjectMocks
    private KombuchaService kombuchaService;

    @Mock
    private KombuchaRepository kombuchaRepository;

    @Test
    void testGetAllKombuchas() {
        when(kombuchaRepository.findAll()).thenReturn(Arrays.asList(new Kombucha(), new Kombucha()));
        assertEquals(2, kombuchaService.getAllKombuchas().size());
    }

    @Test
    void testGetKombuchaById() {
        String id = "someId";
        when(kombuchaRepository.findById(id)).thenReturn(Optional.of(new Kombucha()));
        assertTrue(kombuchaService.getKombuchaById(id).isPresent());
    }

    @Test
    void testSaveKombucha() {
        Kombucha kombucha = new Kombucha();
        when(kombuchaRepository.save(kombucha)).thenReturn(kombucha);
        assertEquals(kombucha, kombuchaService.saveKombucha(kombucha));
    }

    @Test
    void testDeleteKombucha() {
        String id = "someId";
        doNothing().when(kombuchaRepository).deleteById(id);
        assertDoesNotThrow(() -> kombuchaService.deleteKombucha(id));
    }

    @Test
    void testAddReview() {
        String id = "someId";
        Review review = new Review();
        Kombucha kombucha = new Kombucha();
        when(kombuchaRepository.findById(id)).thenReturn(Optional.of(kombucha));
        when(kombuchaRepository.save(kombucha)).thenReturn(kombucha);
        assertNotNull(kombuchaService.addReview(id, review));
    }

    @Test
    void testUpdateStock() {
        String id = "someId";
        int quantity = 5;
        Kombucha kombucha = new Kombucha();
        when(kombuchaRepository.findById(id)).thenReturn(Optional.of(kombucha));
        when(kombuchaRepository.save(kombucha)).thenReturn(kombucha);
        assertNotNull(kombuchaService.updateStock(id, quantity));
    }

    @Test
    void testSearch() {
        String text = "someText";
        when(kombuchaRepository.search(text)).thenReturn(Arrays.asList(new Kombucha(), new Kombucha()));
        assertEquals(2, kombuchaService.search(text).size());
    }
}
